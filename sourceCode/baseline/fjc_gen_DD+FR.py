#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
FJC Code Generator - Modular Design

Supports phased execution:
1. generate: AI generates FJC code
2. compile: Compile FJC code
3. generate_test: AI generates test code
4. test: Compile and run tests

Usage example:
    generator = FJCGenerator()
    generator.run(
        systems=["System1", "System2"],
        sample_count=5,
        work_dir=Path("RESULT/RQ2/fjc"),
        lib_dir=Path("../lib"),
        phases=['generate', 'compile', 'generate_test', 'test'],
        fjc_gen_model="gpt-4o-mini",  # FJC code generation model
        test_gen_model="gpt-4o-mini"  # Test code generation model (can be different)
    )
"""
from __future__ import annotations

import os, re, subprocess, sys
import locale
from collections import defaultdict
from concurrent.futures import ThreadPoolExecutor, as_completed
from datetime import datetime
from pathlib import Path
from typing import Dict, List, Tuple, Optional, Any
import time
import psutil
import itertools, threading  
from openai import OpenAI, APIStatusError   
# ---------------------------------------------------------------------------
# Openai Configuration
# Need to change yours
# ---------------------------------------------------------------------------
BASE_URL = "https://openrouter.ai/api/v1"
API_KEYS = [""]
MODEL_LIST = ["qwen/qwen3-coder","deepseek/deepseek-v3.2-exp"]
# MODEL_LIST = ["deepseek/deepseek-v3.2-exp","deepseek/deepseek-v3.2-exp"]
# Unified configuration: Maximum output tokens for AI (can be modified in code to adjust globally)
DEFAULT_MAX_TOKENS = 8192

MAX_MODEL_TOKENS = [16384, 16384]
# CPU configuration ---------------------------------------------------
MAX_SYSTEM_WORKERS = 4
MAX_SAMPLE_WORKERS = 2

if psutil.cpu_percent() > 80 or psutil.virtual_memory().percent > 85:
    MAX_SYSTEM_WORKERS = 1  

if not API_KEYS:
    raise RuntimeError("❌    OPENAI_API_KEYS / OPENAI_API_KEY") 

_CLIENTS = [OpenAI(api_key=k, base_url=BASE_URL) for k in API_KEYS]

# Test API connection
def test_api_connection():
    """Test if API connection is working"""
    try:
        print(f"🔍 Testing API connection...")
        print(f"   Base URL: {BASE_URL}")
        print(f"   API Keys: {len(API_KEYS)} keys")
        print(f"   Models: {MODEL_LIST}")
        
        client = _CLIENTS[0]
        # Send a simple test request
        resp = client.chat.completions.create(
            model=MODEL_LIST[0],
            messages=[
                # {"role": "user", "content": "Hello"}], change role to system:
                {"role": "user", "content": "Hello"}],
            max_tokens=10
        )
        print(f"✅ API connection test successful")
        return True
    except Exception as e:
        print(f"❌ API connection test failed: {e}")
        return False

# Test connection at startup
if not test_api_connection():
    print("⚠️  API connection test failed, but continuing...")
 
_client_cycle = itertools.cycle(_CLIENTS)
_client_lock = threading.Lock()

# Need to change path
BENCHMARK_ROOT = Path("").resolve()

# Prompt templates -----------------------------------------------------------
# System role: Role definition for FJC code generation
FJC_SYSTEM_PROMPT = """You are a Java code generation expert. Your task is to generate COMPLETE, compilable, and well-documented Java code based on domain descriptions, functional requirements"""

# User role: Complete requirements for FJC code generation
FJC_PROMPT_DD_FR = """
# Task:
Generate Java code based on the < Domain Description > and the < Functional Requirements >. 
You must generate unparameterized constructors (such as new clsname()) for each class.

# Requirements:
1. DO NOT include package declaration - start directly with import statements
2. Generate getter and setter methods for each private property of each class so that tests can add elements.
3. Generate Java code, including classes, fields, and methods. Implement the functional requirements in the < Domain Description > and the < Functional Requirements > as methods in their respective classes.
4. Add new helper/accessor/update methods and fields to defined classes to cover all the constraints mentioned in < Domain Description > and < Functional Requirements >.
5. Append detailed Javadoc comments to all methods that implement computational requirements, including:
    - A clear description of the method's purpose
    - Parameter descriptions using @param tags
    - Return value description using @return tag
    - Exception descriptions using @throws tags (if applicable) 

# Domain Description
{{ description }}

# Functional Requirements
```
{{ functional requirements }}
```

# Output
```java
<generated  Java code here>
```
"""

# JUnit test generation template - Generate by CR
# System role: Simple role definition
JUNIT_TEST_SYSTEM_PROMPT = """You are a Java testing expert. Your task is to generate COMPLETE JUnit 4 test cases."""

# User role: Complete test generation requirements
JUNIT_TEST_PROMPT_BY_CR = """
# Task:
Generate COMPLETE and fully implemented JUnit test code based on the provided <Java Source Code> and <Test Specifications> below. 
Ensure that all specified test cases are included (no additional test cases beyond the Test Specifications), and the generated code is valid, compilable Java code.

# Java Source Code:
{{ java_code }}

# Test Specifications for Computational Requirement{{ cr_name }}:
{{ test_specs }}

# Technical Requirements:
1. DO NOT include package statement - start directly with import statements
2. Use JUnit 4 (junit-4.13.2.jar).JUnit 4 conventions: use @Test/@Before/@After (NOT JUnit 5), assertions (assertTrue, assertFalse, assertEquals, assertNotNull), use @Before for setup if needed; 
3. Test class name MUST be: {{ cr_name }}Test (for example: CR1Test, CR2Test)
4. Method names should clearly describe test intent (use testCase1_descriptiveName format)
5. Each test case should be a separate @Test method
6. Add comments to explain test steps
7. Ensure code compiles and logic is correct
8. Date format: "yyyy-MM-dd HH:mm:ss"
9. **STRICT ADHERENCE TO TEST SPECIFICATIONS**: Follow the input-output format exactly as specified in the test cases. Do not modify the expected output.

# Output Format:
```java
// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
// other necessary imports

public class CR{cr_number}Test {
    
}
```
"""

# ---------------------------------------------------------------------------
# Helper functions 
# ---------------------------------------------------------------------------
def _extract_version_block(md_path: Path) -> str:
    """Return text between `// ==version1==` and `// ==end==`."""
    if not md_path.exists():
        return ""
    text = md_path.read_text(encoding="utf-8", errors="ignore")
    m = re.search(r"//\s*==version1==([\s\S]*?)//\s*==end==", text)
    return m.group(1).strip() if m else text.strip()


def _parse_nltc(nltc_path: Path) -> Dict[str, str]:
    """Parse *TS.md* by splitting at `***` lines.

    Each block becomes a CR, numbered in order of appearance: CR1, CR2, …
    The block text is kept verbatim to feed into test‑generation prompts.
    """
    if not nltc_path.exists():
        return {}

    raw = nltc_path.read_text(encoding="utf-8", errors="ignore")
    # Split on lines containing only asterisks (allowing surrounding whitespace)
    blocks = [b.strip() for b in re.split(r"^\s*\*\*\*\s*$", raw, flags=re.M) if b.strip()]

    return {f"CR{idx}": block for idx, block in enumerate(blocks, start=1)}


def _strip_code_fence(text: str) -> str:
    """Extract code inside first ``` block or return text unchanged."""
    # Try to match complete code block (with closing marker)
    m = re.search(r"```(\w+)?\n([\s\S]+?)```", text)
    if m:
        return m.group(2).strip()
    
    # If no closing marker, try to match opening ``` and remove it
    m_start = re.match(r"```\w*\n([\s\S]+)", text)
    if m_start:
        return m_start.group(1).strip()
    
    return text.strip()


def _remove_public_modifiers(code: str) -> str:
    """Remove `public` keyword before top‑level class/enum/interface names."""
    lines = code.splitlines()
    cleaned: List[str] = []
    for line in lines:
        cleaned.append(re.sub(r"^\s*public(\s+(class|interface|enum))", r"\1", line))
    return "\n".join(cleaned)


# ---------------------------------------------------------------------------
# FJCGenerator Class - Modular Design
# ---------------------------------------------------------------------------
class FJCGenerator:
    """FJC Code Generator - Modular design supporting phased execution"""
    
    def __init__(self):
        """Initialize FJC generator"""
        # ============================================================
        # 🔧 Configuration Area
        # ============================================================
        
        # System root directory
        self.benchmark_root = BENCHMARK_ROOT
        
        # Work directory (result output directory)
        self.work_dir = None  # Will be set at runtime
        
        # System list
        self.systems = []  # Will be set at runtime or auto-scanned
        
        # Sample count
        self.sample_count = 5
        
        # JAR package directory
        self.lib_dir = None  # Will be set at runtime
        self.jar_files = []  # Will be set at runtime
        
        # ============================================================
        # 🤖 AI Configuration
        # ============================================================
        # FJC code generation model configuration
        self.fjc_gen_base_url = BASE_URL
        self.fjc_gen_api_keys = API_KEYS
        self.fjc_gen_model = MODEL_LIST[0] if MODEL_LIST else "gpt-4o-mini"
        
        # Test code generation model configuration (can be different)
        self.test_gen_base_url = BASE_URL
        self.test_gen_api_keys = API_KEYS
        self.test_gen_model = MODEL_LIST[1] if MODEL_LIST else "gpt-4o-mini"
        
        # Initialize AI clients
        self._fjc_clients = None
        self._test_clients = None
        self._fjc_client_cycle = None
        self._test_client_cycle = None
        self._fjc_client_lock = threading.Lock()
        self._test_client_lock = threading.Lock()
        
        # Parallelism configuration
        self.max_system_workers = MAX_SYSTEM_WORKERS
        self.max_sample_workers = MAX_SAMPLE_WORKERS
        
        # API call configuration
        self.max_tokens = DEFAULT_MAX_TOKENS  # Unified max_tokens configuration, can be modified at runtime
    
    def _initialize_ai_clients(self):
        """Initialize AI clients"""
        # FJC generation client
        if self._fjc_clients is None:
            self._fjc_clients = [OpenAI(api_key=k, base_url=self.fjc_gen_base_url) 
                                for k in self.fjc_gen_api_keys]
            self._fjc_client_cycle = itertools.cycle(self._fjc_clients)
        
        # Test generation client (if using different API configuration)
        if self._test_clients is None:
            if (self.test_gen_base_url == self.fjc_gen_base_url and 
                self.test_gen_api_keys == self.fjc_gen_api_keys):
                # Use the same client
                self._test_clients = self._fjc_clients
                self._test_client_cycle = self._fjc_client_cycle
            else:
                # Use different client
                self._test_clients = [OpenAI(api_key=k, base_url=self.test_gen_base_url) 
                                     for k in self.test_gen_api_keys]
                self._test_client_cycle = itertools.cycle(self._test_clients)
    
    def _next_fjc_client(self) -> OpenAI:
        """Thread-safely get next FJC generation client"""
        if self._fjc_clients is None:
            self._initialize_ai_clients()
        with self._fjc_client_lock:
            return next(self._fjc_client_cycle)
    
    def _next_test_client(self) -> OpenAI:
        """Thread-safely get next test generation client"""
        if self._test_clients is None:
            self._initialize_ai_clients()
        with self._test_client_lock:
            return next(self._test_client_cycle)
    
    def _call_openai(self, prompt: str, model: str, system_prompt: str = None, 
                     use_test_client: bool = False, max_retry: int = None, 
                     max_tokens: int = None) -> str:
        """Call OpenAI API"""
        max_retry = max_retry or (len(self.fjc_gen_api_keys) * 2 if not use_test_client 
                                 else len(self.test_gen_api_keys) * 2)
        # If max_tokens is not provided, prioritize model configuration; otherwise fall back to class-level/global default
        if max_tokens is None:
            # 1) Find corresponding index by model name in MODEL_LIST
            resolved = None
            try:
                idx = MODEL_LIST.index(model)
                if 0 <= idx < len(MAX_MODEL_TOKENS):
                    candidate = MAX_MODEL_TOKENS[idx]
                    if isinstance(candidate, int) and candidate > 0:
                        resolved = candidate
            except ValueError:
                resolved = None
            # 2) Prioritize model-specific value, then class-level config self.max_tokens (if exists), finally DEFAULT_MAX_TOKENS
            if resolved is None:
                if hasattr(self, "max_tokens") and isinstance(self.max_tokens, int) and self.max_tokens > 0:
                    resolved = self.max_tokens
                else:
                    resolved = DEFAULT_MAX_TOKENS
            max_tokens = resolved
        backoff = 1.0
        
        client_getter = self._next_test_client if use_test_client else self._next_fjc_client
        
        for attempt in range(max_retry):
            client = client_getter()
            try:
                messages = []
                if system_prompt:
                    messages.append({"role": "system", "content": system_prompt})
                messages.append({"role": "user", "content": prompt})
                
                resp = client.chat.completions.create(
                    model=model,
                    messages=messages,
                    temperature=0.7,
                    max_tokens=max_tokens,
                )
                
                if hasattr(resp, 'usage') and resp.usage:
                    print(f"✅ API call successful (model: {model})")
                    print(f"📊 Token usage: input={resp.usage.prompt_tokens}, "
                          f"output={resp.usage.completion_tokens}, "
                          f"total={resp.usage.total_tokens}")
                
                return resp.choices[0].message.content
                
            except APIStatusError as e:
                if e.status_code in (400, 429, 500, 502, 503):
                    time.sleep(backoff)
                    backoff = min(backoff * 2, 16)
                    continue
                raise
            except Exception as e:
                time.sleep(backoff)
                backoff = min(backoff * 2, 16)
                continue
        
        raise RuntimeError(f"OpenAI API call failed after {max_retry} retries")
    
    def run_generate_phase(self, system_name: str, sample_id: int) -> Tuple[bool, str, List[str]]:
        """
        Phase 1: Generate FJC code
        
        Args:
            system_name: System name
            sample_id: Sample ID
            
        Returns:
            (success, output_path, CR_list)
        """
        sys_dir = self.benchmark_root / system_name
        sample_root = self.work_dir / f"{system_name}_sample{sample_id}"
        src_root = sample_root / "src" / "main"
        src_root.mkdir(parents=True, exist_ok=True)
        
        print(f"  📝 Step 1: Generate FJC code (model: {self.fjc_gen_model})...")
        
        try:
            req_text = _extract_version_block(sys_dir / "DD.md")
            cr_text = _extract_version_block(sys_dir / "FR.md")
            
            prompt = FJC_PROMPT_DD_FR.replace("{{ description }}", req_text)\
                                     .replace("{{ functional requirements }}", cr_text)
            
            code = self._call_openai(prompt=prompt, model=self.fjc_gen_model, 
                                    system_prompt=FJC_SYSTEM_PROMPT, use_test_client=False)
            code = _strip_code_fence(code)
            code = _remove_public_modifiers(code)
            
            out_path = src_root / "FJC.java"
            out_path.write_text(code, encoding="utf-8")
            print(f"  ✅ FJC code saved to: {out_path}")
            
            # Parse CR list
            cr_sections = _parse_nltc(sys_dir / "TS.md")
            cr_list = list(cr_sections.keys())
            
            return True, str(out_path), cr_list
            
        except Exception as e:
            print(f"  ❌ FJC code generation failed: {e}")
            return False, "", []
    
    def run_compile_phase(self, system_name: str, sample_id: int) -> Tuple[bool, str]:
        """
        Phase 2: Compile FJC code
        
        Args:
            system_name: System name
            sample_id: Sample ID
            
        Returns:
            (success, compile_output)
        """
        sample_root = self.work_dir / f"{system_name}_sample{sample_id}"
        src_root = sample_root / "src" / "main"
        classes_root = sample_root / "classes"
        classes_root.mkdir(parents=True, exist_ok=True)
        
        print(f"  🔨 Step 2: Compile FJC.java...")
        
        fjc_path = src_root / "FJC.java"
        if not fjc_path.exists():
            print(f"  ⚠️ FJC.java does not exist: {fjc_path}")
            return False, "FJC.java file does not exist"
        
        success, output = compile_fjc_java(fjc_path, classes_root, self.jar_files)
        
        if success:
            print(f"  ✅ FJC compilation successful")
        else:
            print(f"  ✗ FJC compilation failed")
        
        return success, output
    
    def run_generate_test_phase(self, system_name: str, sample_id: int) -> Tuple[bool, int]:
        """
        Phase 3: Generate JUnit test code
        
        Args:
            system_name: System name
            sample_id: Sample ID
            
        Returns:
            (success, number_of_test_classes_generated)
        """
        sys_dir = self.benchmark_root / system_name
        sample_root = self.work_dir / f"{system_name}_sample{sample_id}"
        src_root = sample_root / "src" / "main"
        test_root = sample_root / "src" / "test"
        test_root.mkdir(parents=True, exist_ok=True)
        
        print(f"  🧪 Step 3: Generate JUnit test code (model: {self.test_gen_model})...")
        
        try:
            # Read FJC.java code
            fjc_path = src_root / "FJC.java"
            if not fjc_path.exists():
                print(f"  ⚠️ FJC.java not found at {fjc_path}")
                return False, 0
            
            java_code = fjc_path.read_text(encoding="utf-8")
            
            # Parse TS.md grouped by CR
            ts_path = sys_dir / "TS.md"
            if not ts_path.exists():
                print(f"  ⚠️ TS.md not found at {ts_path}")
                return False, 0
            
            cr_sections = _parse_nltc(ts_path)
            if not cr_sections:
                print(f"  ⚠️ Cannot parse CR sections from TS.md")
                return False, 0
            
            print(f"    Found {len(cr_sections)} CRs, will generate {len(cr_sections)} test classes")
            
            generated_count = 0
            for cr_name, cr_spec in cr_sections.items():
                print(f"     🔄 Generating {cr_name}Test.java...")
                
                cr_lines = cr_spec.strip().split('\n')
                cr_description = cr_lines[0] if cr_lines else cr_name
                
                prompt = JUNIT_TEST_PROMPT_BY_CR.replace("{{ cr_name }}", cr_name)
                prompt = prompt.replace("{{ cr_description }}", cr_description)
                prompt = prompt.replace("{{ java_code }}", java_code)
                prompt = prompt.replace("{{ test_specs }}", cr_spec)
                
                try:
                    test_code = self._call_openai(prompt=prompt, model=self.test_gen_model, 
                                                 system_prompt=JUNIT_TEST_SYSTEM_PROMPT, 
                                                 use_test_client=True)
                    test_code = _strip_code_fence(test_code)
                    
                    test_file_path = test_root / f"{cr_name}Test.java"
                    test_file_path.write_text(test_code, encoding="utf-8")
                    
                    generated_count += 1
                    print(f"     ✅ {cr_name}Test.java generated")
                except Exception as e:
                    print(f"     ❌ {cr_name}Test.java generation failed: {e}")
            
            print(f"  ✅ Successfully generated {generated_count}/{len(cr_sections)} test classes")
            return True, generated_count
            
        except Exception as e:
            print(f"  ❌ Test code generation failed: {e}")
            return False, 0
    
    def run_test_phase(self, system_name: str, sample_id: int) -> List[Dict[str, Any]]:
        """
        Phase 4: Compile and run tests
        
        Args:
            system_name: System name
            sample_id: Sample ID
            
        Returns:
            List of test results
        """
        sample_root = self.work_dir / f"{system_name}_sample{sample_id}"
        test_root = sample_root / "src" / "test"
        classes_root = sample_root / "classes"
        
        print(f"  🔧 Step 4: Compile and run tests...")
        
        test_files = sorted(test_root.glob("CR*Test.java")) if test_root.exists() else []
        test_results = []
        
        for idx, test_file in enumerate(test_files, 1):
            test_name = test_file.stem
            print(f"    [{idx}/{len(test_files)}] {test_name}...", end=" ")
            
            result = {
                'test_name': test_name,
                'compile_success': False,
                'compile_output': '',
                'run_success': False,
                'tests_run': 0,
                'tests_failed': 0,
                'run_output': ''
            }
            
            # Compile test class
            compile_success, compile_output = compile_test_class(test_file, classes_root, self.jar_files)
            result['compile_success'] = compile_success
            result['compile_output'] = compile_output
            
            if compile_success:
                # Run tests
                run_success, tests_run, tests_failed, run_output = run_test_class(
                    test_name, classes_root, self.jar_files)
                result['run_success'] = run_success
                result['tests_run'] = tests_run
                result['tests_failed'] = tests_failed
                result['run_output'] = run_output
                
                if run_success:
                    if tests_run == 0:
                        print(f"⚠️ (0/0 - No test methods!)")
                    else:
                        print(f"✓ ({tests_run}/{tests_run})")
                else:
                    print(f"✗ ({tests_run - tests_failed}/{tests_run})")
            else:
                print(f"✗ Compilation failed")
            
            test_results.append(result)
        
        return test_results
    
    def run(self, systems: List[str] = None, sample_count: int = 5, 
            work_dir: Path = None, lib_dir: Path = None,
            phases: List[str] = ['generate', 'compile', 'generate_test', 'test'],
            fjc_gen_model: str = None, test_gen_model: str = None):
        """
        Run FJC generation workflow
        
        Args:
            systems: System list (if None, auto-scan)
            sample_count: Number of samples
            work_dir: Work directory (result output directory)
            lib_dir: JAR package directory
            phases: List of phases to run, options: ['generate', 'compile', 'generate_test', 'test']
            fjc_gen_model: FJC code generation model (if None, use default)
            test_gen_model: Test code generation model (if None, use default)
        """
        # Set configuration
        if systems:
            self.systems = systems
        
        # Set model first (for building path)
        if fjc_gen_model:
            self.fjc_gen_model = fjc_gen_model
        if test_gen_model:
            self.test_gen_model = test_gen_model
        
        # Then set work directory (needs model name)
        if work_dir:
            self.work_dir = Path(work_dir)
        else:
            model_name = self.fjc_gen_model if hasattr(self, 'fjc_gen_model') and self.fjc_gen_model else "gpt-4o-mini"
            SCRIPT_DIR = Path(__file__).resolve().parent
            REPO_ROOT = SCRIPT_DIR.parent.parent
            self.work_dir = (REPO_ROOT / "RESULT" / "RQ2" / model_name).resolve()
            self.work_dir.mkdir(parents=True, exist_ok=True)
        
        if lib_dir:
            self.lib_dir = Path(lib_dir)
            self.jar_files = _collect_jars(self.lib_dir)
        else:
            self.jar_files = []
        
        if sample_count:
            self.sample_count = sample_count
        
        # Initialize AI clients
        self._initialize_ai_clients()
        
        # If no systems specified, auto-scan
        if not self.systems:
            if self.benchmark_root.exists():
                self.systems = [d.name for d in self.benchmark_root.iterdir() 
                              if d.is_dir() and (d / "DD.md").exists()]
            else:
                print(f"❌ System root directory does not exist: {self.benchmark_root}")
                return
        
        print(f"\n{'='*80}")
        print(f"🚀 FJC Code Generator - Modular Execution")
        print(f"{'='*80}")
        print(f"📁 System root directory: {self.benchmark_root}")
        print(f"📁 Work directory: {self.work_dir}")
        print(f"🎯 System list: {', '.join(self.systems)}")
        print(f"📊 Sample count: {self.sample_count}")
        print(f"🤖 FJC generation model: {self.fjc_gen_model}")
        print(f"🤖 Test generation model: {self.test_gen_model}")
        print(f"📦 Execution phases: {', '.join(phases)}")
        print(f"{'='*80}\n")
        
        # Process each system
        all_system_results = []
        
        for system_idx, system_name in enumerate(self.systems, 1):
            print(f"\n{'#'*80}")
            print(f"[{system_idx}/{len(self.systems)}] System: {system_name}")
            print(f"{'#'*80}")
            
            system_dir = self.benchmark_root / system_name
            if not system_dir.exists():
                print(f"⚠️ System directory does not exist: {system_dir}")
                continue
            
            all_sample_results = []
            
            # Process each sample
            with ThreadPoolExecutor(max_workers=self.max_sample_workers) as executor:
                futures = []
                for sample_id in range(1, self.sample_count + 1):
                    futures.append(executor.submit(
                        self._process_sample_with_phases,
                        system_name, sample_id, phases
                    ))
                
                for future in as_completed(futures):
                    sample_result = future.result()
                    all_sample_results.append(sample_result)
            
            # Sort by sample_id
            all_sample_results.sort(key=lambda x: x['sample_id'])
            
            # Generate system summary report
            generate_system_summary(self.work_dir, system_name, all_sample_results)
            
            all_system_results.extend(all_sample_results)
        
        print(f"\n{'='*80}")
        print(f"✅ All systems processed!")
        print(f"{'='*80}")
        
        return all_system_results
    
    def _process_sample_with_phases(self, system_name: str, sample_id: int, 
                                    phases: List[str]) -> Dict[str, Any]:
        """Process a single sample, execute according to specified phases"""
        sample_root = self.work_dir / f"{system_name}_sample{sample_id}"
        src_root = sample_root / "src" / "main"
        test_root = sample_root / "src" / "test"
        classes_root = sample_root / "classes"
        
        print(f"\n{'='*60}")
        print(f"[{sample_id}] Processing {system_name}_sample{sample_id}")
        print(f"{'='*60}")
        
        result = {
            'sample_id': sample_id,
            'fjc_compile_success': False,
            'fjc_compile_output': '',
            'test_results': [],
            'all_passed': False
        }
        
        # Phase 1: Generate FJC code
        if 'generate' in phases:
            success, fjc_path, cr_list = self.run_generate_phase(system_name, sample_id)
            if not success:
                print(f"  ✗ FJC code generation failed, skipping subsequent phases")
                return result
        
        # Phase 2: Compile FJC code
        if 'compile' in phases:
            fjc_compile_success, fjc_compile_output = self.run_compile_phase(system_name, sample_id)
            result['fjc_compile_success'] = fjc_compile_success
            result['fjc_compile_output'] = fjc_compile_output
            
            if not fjc_compile_success:
                print(f"  ✗ FJC compilation failed, skipping test generation and execution")
                generate_sample_report(sample_root, system_name, sample_id, 
                                     fjc_compile_success, fjc_compile_output, [])
                return result
        
        # Phase 3: Generate test code
        if 'generate_test' in phases:
            success, test_count = self.run_generate_test_phase(system_name, sample_id)
            if not success:
                print(f"  ✗ Test code generation failed")
                generate_sample_report(sample_root, system_name, sample_id, 
                                     result['fjc_compile_success'], 
                                     result['fjc_compile_output'], [])
                return result
        
        # Phase 4: Compile and run tests
        if 'test' in phases:
            test_results = self.run_test_phase(system_name, sample_id)
            result['test_results'] = test_results
            
            # Generate sample report
            print(f"  📊 Step 5: Generate test report...")
            generate_sample_report(sample_root, system_name, sample_id, 
                                 result['fjc_compile_success'], 
                                 result['fjc_compile_output'], test_results)
            
            # Check if all passed
            all_passed = result['fjc_compile_success'] and all(
                r['compile_success'] and r['run_success'] for r in test_results
            )
            result['all_passed'] = all_passed
            
            if all_passed:
                print(f"  ✅ Sample {sample_id} - All passed!")
            else:
                print(f"  ⚠️  Sample {sample_id} - Has failures")
        
        return result



# ---------------------------------------------------------------------------
# Compilation and Test Execution Related Functions
# ---------------------------------------------------------------------------

def _build_classpath_for_compilation(jars: List[Path], classes_dir: Path) -> str:
    """Build classpath for compilation"""
    elements = [str(jar) for jar in jars] + [str(classes_dir)]
    return ";".join(elements)  # Windows uses semicolon

def _get_system_encoding() -> str:
    """
    Get system default encoding (for correctly reading javac/java output)
    On Windows, javac error messages usually use system default encoding (GBK), not UTF-8
    """
    # Try to get system encoding
    try:
        # Windows system encoding
        if sys.platform == 'win32':
            encoding = locale.getpreferredencoding()
            # Windows Chinese systems are usually 'cp936' or 'gbk'
            if encoding.lower() in ['cp936', 'gbk', 'gb2312']:
                return 'gbk'
        # Other systems or UTF-8 systems
        return 'utf-8'
    except:
        return 'utf-8'

def compile_fjc_java(fjc_path: Path, classes_dir: Path, jars: List[Path]) -> Tuple[bool, str]:
    """Compile FJC.java
    
    Args:
        fjc_path: FJC.java file path
        classes_dir: Compilation output directory
        jars: List of dependent jar packages
        
    Returns:
        (success, compile_output)
    """
    try:
        classes_dir.mkdir(parents=True, exist_ok=True)
        classpath = _build_classpath_for_compilation(jars, classes_dir)
        
        cmd = [
            "javac",
            "-encoding", "UTF-8",
            "-cp", classpath,
            "-d", str(classes_dir),
            str(fjc_path)
        ]
        
        # Use system encoding to read javac output (usually GBK on Windows)
        system_encoding = _get_system_encoding()
        result = subprocess.run(
            cmd,
            capture_output=True,
            text=True,
            encoding=system_encoding,
            errors='replace',  # Use 'replace' instead of 'ignore' to preserve undecodable characters
            timeout=60
        )
        
        success = result.returncode == 0
        output = result.stdout + result.stderr
        
        # Debug output: ensure error messages are correctly captured
        if not success:
            print(f"  🔍 DEBUG: javac return code={result.returncode}")
            print(f"  🔍 DEBUG: stdout length={len(result.stdout)}, stderr length={len(result.stderr)}")
            print(f"  🔍 DEBUG: combined output length={len(output)}")
            if output.strip():
                print(f"  🔍 DEBUG: output preview: {output[:200]}...")
            else:
                print(f"  🔍 DEBUG: output is empty!")
        
        return success, output
        
    except subprocess.TimeoutExpired:
        return False, "Compilation timeout (exceeded 60 seconds)"
    except Exception as e:
        return False, f"Compilation exception: {str(e)}"

def compile_test_class(test_path: Path, classes_dir: Path, jars: List[Path]) -> Tuple[bool, str]:
    """Compile test class
    
    Args:
        test_path: Test class source file path
        classes_dir: Compilation output directory
        jars: List of dependent jar packages
        
    Returns:
        (success, compile_output)
    """
    try:
        classpath = _build_classpath_for_compilation(jars, classes_dir)
        
        cmd = [
            "javac",
            "-encoding", "UTF-8",
            "-cp", classpath,
            "-d", str(classes_dir),
            str(test_path)
        ]
        
        # Use system encoding to read javac output (usually GBK on Windows)
        system_encoding = _get_system_encoding()
        result = subprocess.run(
            cmd,
            capture_output=True,
            text=True,
            encoding=system_encoding,
            errors='replace',  # Use 'replace' instead of 'ignore' to preserve undecodable characters
            timeout=60
        )
        
        success = result.returncode == 0
        output = result.stdout + result.stderr
        return success, output
        
    except subprocess.TimeoutExpired:
        return False, "Compilation timeout (exceeded 60 seconds)"
    except Exception as e:
        return False, f"Compilation exception: {str(e)}"

def run_test_class(test_class_name: str, classes_dir: Path, jars: List[Path]) -> Tuple[bool, int, int, str]:
    """Run test class
    
    Args:
        test_class_name: Test class name (without .class suffix)
        classes_dir: Directory of compiled class files
        jars: List of dependent jar packages
        
    Returns:
        (all_passed, total_tests, failed_tests, detailed_output)
    """
    try:
        classpath = _build_classpath_for_compilation(jars, classes_dir)
        
        cmd = [
            "java",
            "-Dfile.encoding=UTF-8",
            "-cp", classpath,
            "org.junit.runner.JUnitCore",
            test_class_name
        ]
        
        # Use system encoding to read java output (usually GBK on Windows)
        system_encoding = _get_system_encoding()
        result = subprocess.run(
            cmd,
            capture_output=True,
            text=True,
            encoding=system_encoding,
            errors='replace',  # Use 'replace' instead of 'ignore' to preserve undecodable characters
            timeout=60
        )
        
        output = result.stdout + result.stderr
        
        # Parse JUnit output
        test_info = _parse_junit_output(output)
        
        success = test_info['failures'] == 0 and test_info['errors'] == 0
        total_tests = test_info['tests_run']
        failed_tests = test_info['failures'] + test_info['errors']
        
        return success, total_tests, failed_tests, output
        
    except subprocess.TimeoutExpired:
        return False, 0, 0, "Test execution timeout (exceeded 60 seconds)"
    except Exception as e:
        return False, 0, 0, f"Execution exception: {str(e)}"

def _parse_junit_output(output: str) -> Dict[str, int]:
    """Parse JUnit command line output
    
    Returns:
        Dictionary containing test statistics
    """
    result = {
        'tests_run': 0,
        'failures': 0,
        'errors': 0,
    }
    
    # Method 1: Match "Tests run: X,  Failures: Y" (format when there are failures)
    match = re.search(r'Tests run:\s*(\d+),\s*Failures:\s*(\d+)', output)
    if match:
        result['tests_run'] = int(match.group(1))
        result['failures'] = int(match.group(2))
    else:
        # Method 2: Match "OK (X tests)" or "OK (X test)" (format when all succeed)
        ok_match = re.search(r'OK\s*\((\d+)\s+tests?\)', output)
        if ok_match:
            result['tests_run'] = int(ok_match.group(1))
            result['failures'] = 0
    
    # Check for errors
    if 'There was' in output or 'There were' in output:
        error_match = re.search(r'There (?:was|were) (\d+)', output)
        if error_match:
            total_failures = int(error_match.group(1))
            result['errors'] = total_failures - result['failures']
    
    return result

def generate_sample_report(sample_root: Path, system_name: str, sample_id: int, 
                          fjc_compile_success: bool, fjc_compile_output: str,
                          test_results: List[Dict[str, Any]]) -> str:
    """Generate detailed sample report
    
    Args:
        sample_root: Sample root directory
        system_name: System name
        sample_id: Sample ID
        fjc_compile_success: Whether FJC compilation succeeded
        fjc_compile_output: FJC compilation output
        test_results: List of test results
        
    Returns:
        Report file path
    """
    # Check if all passed
    all_passed = fjc_compile_success and all(
        r.get('compile_success', False) and r.get('run_success', False) 
        for r in test_results
    )
    
    report_filename = "test_report_detail.txt"
    report_path = sample_root / report_filename
    
    lines = []
    lines.append("=" * 60)
    lines.append(f"{system_name} - Sample {sample_id} Detailed Test Report")
    lines.append("=" * 60)
    lines.append(f"Generated at: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    lines.append("")
    
    # FJC compilation result
    lines.append("[FJC Compilation]")
    if fjc_compile_success:
        lines.append("Status: ✓ Success")
    else:
        lines.append("Status: ✗ Failed")
        lines.append("")
        lines.append("Compilation errors:")
        lines.append("-" * 56)
        error_lines = fjc_compile_output.strip().split('\n')[:20]
        for line in error_lines:
            lines.append(f"  {line}")
        lines.append("-" * 56)
    
    lines.append("")
    
    # Test execution results
    if fjc_compile_success and test_results:
        lines.append("[Test Execution Results]")
        lines.append("=" * 60)
        lines.append("")
        
        for result in test_results:
            test_name = result['test_name']
            
            if not result['compile_success']:
                lines.append(f"[✗ COMPILE ERROR] {test_name}.java")
                lines.append("  Compile: ✗ Failed")
                lines.append("  Run: - Not executed")
                lines.append("")
                lines.append("  Compilation errors:")
                lines.append("  " + "-" * 52)
                error_lines = result['compile_output'].strip().split('\n')[:10]
                for line in error_lines:
                    lines.append(f"  {line}")
                lines.append("  " + "-" * 52)
                lines.append("")
            elif result['run_success']:
                lines.append(f"[✓ PASS] {test_name}.java")
                lines.append("  Compile: ✓ Success")
                lines.append("  Run: ✓ Passed")
                lines.append(f"  Tests: {result['tests_run']}/{result['tests_run']} passed")
                lines.append("")
            else:
                lines.append(f"[✗ FAIL] {test_name}.java")
                lines.append("  Compile: ✓ Success")
                lines.append("  Run: ✗ Failed")
                lines.append(f"  Tests: {result['tests_run'] - result['tests_failed']}/{result['tests_run']} passed, {result['tests_failed']} failed")
                lines.append("")
                lines.append("  Failure details:")
                lines.append("  " + "-" * 52)
                
                output = result['run_output']
                if 'There was' in output or 'There were' in output:
                    failure_start = output.find('There w')
                    if failure_start != -1:
                        failure_section = output[failure_start:]
                        failure_lines = failure_section.split('\n')[:30]
                        for line in failure_lines:
                            lines.append(f"  {line}")
                else:
                    output_lines = output.strip().split('\n')[:15]
                    for line in output_lines:
                        lines.append(f"  {line}")
                
                lines.append("  " + "-" * 52)
                lines.append("")
    
    # Statistics summary
    lines.append("=" * 60)
    lines.append("Statistics Summary")
    lines.append("=" * 60)
    lines.append(f"- FJC Compilation: {'✓ Success' if fjc_compile_success else '✗ Failed'}")
    
    if fjc_compile_success and test_results:
        total_tests = len(test_results)
        compile_success_count = sum(1 for r in test_results if r.get('compile_success', False))
        run_success_count = sum(1 for r in test_results if r.get('compile_success', False) and r.get('run_success', False))
        
        lines.append(f"- Total test classes: {total_tests}")
        lines.append(f"- Compilation successful: {compile_success_count}/{total_tests}")
        if compile_success_count > 0:
            lines.append(f"- Tests passed: {run_success_count}/{compile_success_count}")
            lines.append(f"- Tests failed: {compile_success_count - run_success_count}/{compile_success_count}")
    
    lines.append("=" * 60)
    
    # Write to file
    report_content = "\n".join(lines)
    with open(report_path, 'w', encoding='utf-8') as f:
        f.write(report_content)
    
    return str(report_path)

def generate_system_summary(work_dir: Path, system_name: str, 
                           all_sample_results: List[Dict[str, Any]]) -> str:
    """Generate system summary report
    
    Args:
        work_dir: Work directory
        system_name: System name
        all_sample_results: List of all sample results
        
    Returns:
        Report file path
    """
    # Check if all passed
    all_passed = all(r['all_passed'] for r in all_sample_results)
    
    report_filename = f"{system_name}_test_summary_{'T' if all_passed else 'F'}.txt"
    report_path = work_dir / report_filename
    
    lines = []
    lines.append("=" * 60)
    lines.append(f"{system_name} System Test Summary Report")
    lines.append("=" * 60)
    lines.append(f"Generated at: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    
    total_samples = len(all_sample_results)
    passed_samples = sum(1 for r in all_sample_results if r['all_passed'])
    failed_samples = total_samples - passed_samples
    
    lines.append(f"Total samples: {total_samples}")
    lines.append(f"All passed: {passed_samples}/{total_samples} ({100*passed_samples/total_samples:.1f}%)")
    lines.append(f"Has failures: {failed_samples}/{total_samples} ({100*failed_samples/total_samples:.1f}%)")
    lines.append("")
    
    lines.append("=" * 60)
    lines.append("Sample Detailed Results")
    lines.append("=" * 60)
    lines.append("")
    
    for result in all_sample_results:
        sample_id = result['sample_id']
        fjc_success = result['fjc_compile_success']
        test_results = result.get('test_results', [])
        
        if result['all_passed']:
            lines.append(f"[✓] Sample {sample_id} - All passed")
            test_passed = sum(1 for r in test_results if r.get('run_success', False))
            lines.append(f"    FJC Compile: ✓ | Tests passed: {test_passed}/{len(test_results)}")
        elif not fjc_success:
            lines.append(f"[✗] Sample {sample_id} - FJC compilation failed")
            lines.append(f"    FJC Compile: ✗")
            # Display first 5 lines of compilation errors (increased display lines)
            fjc_output = result.get('fjc_compile_output', '')
            error_lines = fjc_output.strip().split('\n')[:5]
            if error_lines:
                lines.append(f"    Errors:")
                for error_line in error_lines:
                    # Remove 60 character limit, display full error
                    lines.append(f"      {error_line}")
        else:
            lines.append(f"[✗] Sample {sample_id} - Has failures")
            test_passed = sum(1 for r in test_results if r.get('compile_success', False) and r.get('run_success', False))
            lines.append(f"    FJC Compile: ✓ | Tests passed: {test_passed}/{len(test_results)}")
            lines.append("    Failure details:")
            
            for r in test_results:
                if not r.get('compile_success', False):
                    lines.append(f"      • {r['test_name']}: Compilation failed")
                    error_lines = r.get('compile_output', '').strip().split('\n')[:1]
                    if error_lines and error_lines[0].strip():
                        lines.append(f"        {error_lines[0][:70]}")
                elif not r.get('run_success', False):
                    lines.append(f"      • {r['test_name']}: {r['tests_failed']}/{r['tests_run']} tests failed")
        
        lines.append("")
    
    # Statistics summary
    lines.append("=" * 60)
    lines.append("Statistics Summary")
    lines.append("=" * 60)
    
    fjc_success_count = sum(1 for r in all_sample_results if r['fjc_compile_success'])
    lines.append(f"- FJC compilation success rate: {fjc_success_count}/{total_samples} ({100*fjc_success_count/total_samples:.1f}%)")
    
    if fjc_success_count > 0:
        total_test_classes = sum(len(r.get('test_results', [])) for r in all_sample_results if r['fjc_compile_success'])
        total_passed_tests = sum(
            sum(1 for t in r.get('test_results', []) if t.get('compile_success') and t.get('run_success'))
            for r in all_sample_results if r['fjc_compile_success']
        )
        if total_test_classes > 0:
            lines.append(f"- Average test pass rate: {100*total_passed_tests/total_test_classes:.1f}% ({total_passed_tests}/{total_test_classes} tests passed)")
    
    lines.append("=" * 60)
    
    # Write to file
    report_content = "\n".join(lines)
    with open(report_path, 'w', encoding='utf-8') as f:
        f.write(report_content)
    
    print(f"\n📊 System summary report generated: {report_path}")
    return str(report_path)

# ---------------------------------------------------------------------------
# Helper Functions
# ---------------------------------------------------------------------------
def _collect_jars(lib_dir: Path | None) -> List[Path]:
    """Return list of *.jar files inside *lib_dir* (may be empty)."""
    if not lib_dir or not lib_dir.exists():
        return []
    return [p for p in lib_dir.iterdir() if p.suffix == ".jar"]

if __name__ == "__main__":
    # ============================================================
    # Method 1: Use new modular class (recommended)
    # ============================================================
    # Create FJC generator instance
    generator = FJCGenerator()
    
    # Configure system list (if None, will auto-scan)
    systems = None  # Or specify: ["System1", "System2"]
    
    # Configure JAR package directory (relative to script location: code/lib)
    SCRIPT_DIR = Path(__file__).resolve().parent
    lib_dir = (SCRIPT_DIR.parent / "lib").resolve()
    print(f"📦 JAR package directory: {lib_dir}")
    
    # Configure AI models (can be different)
    fjc_gen_model = MODEL_LIST[0] if MODEL_LIST else "gpt-4o-mini"
    test_gen_model = MODEL_LIST[1] if len(MODEL_LIST) > 1 else (MODEL_LIST[0] if MODEL_LIST else "gpt-4o-mini")
    
    # Automatically build work directory based on FJC generation model
    SCRIPT_DIR_FOR_WORK = Path(__file__).resolve().parent
    REPO_ROOT = SCRIPT_DIR_FOR_WORK.parent.parent
    work_dir = (REPO_ROOT / "RESULT" / "RQ2" / fjc_gen_model).resolve()
    work_dir.mkdir(parents=True, exist_ok=True)
    print(f"📁 Work directory: {work_dir}")
    
    # Run different phase combinations
    generator.run(
        systems=systems,
        sample_count=5,
        work_dir=work_dir,
        lib_dir=lib_dir,
        phases=['generate', 'compile', 'generate_test', 'test'],
        fjc_gen_model=fjc_gen_model,
        test_gen_model=test_gen_model
    )
    