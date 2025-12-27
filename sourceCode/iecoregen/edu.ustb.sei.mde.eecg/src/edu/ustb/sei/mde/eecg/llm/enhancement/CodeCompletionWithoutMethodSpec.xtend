package edu.ustb.sei.mde.eecg.llm.enhancement

import edu.ustb.sei.mde.eecg.llm.CodeGenConstants
import edu.ustb.sei.mde.eecg.llm.EcoreTools
import edu.ustb.sei.mde.eecg.tools.CodeTools
import edu.ustb.sei.mde.mwe2.EcoreLanguage
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EOperation
import com.google.inject.Inject
import edu.ustb.sei.ai.LLMPort

/**
 * For experiment only
 */
class CodeCompletionWithoutMethodSpec extends ContextCodeEnhancement {
	@Inject
	var LLMPort port
	
	new() {
		this.port = null
	}
	
	extension EcoreTools = EcoreTools.instance
	extension CodeTools = CodeTools.instance
	
	new(LLMPort port) {
		this.port = port
	}
	
	override protected isFocused(EClassifier cls) {
		if(cls instanceof EClass) {
			cls.EOperations.exists[it.isFocused]
		} else false
	}
	
	protected def boolean isFocused(EOperation op) {
		op.getEAnnotationDetail(CodeGenConstants.EECG, CodeGenConstants.EECG_GEN) == 'true'
	}
	
	override protected doEnhancement(EcoreLanguage language, EClassifier target) {
		val eClass = target as EClass

		val ePack = eClass.EPackage

		val sysreq = ePack.getEAnnotationDetail(CodeGenConstants.EECG, CodeGenConstants.EECG_REG)
		
		val focusedOperationNames = eClass.EOperations.filter[it.isFocused].map[it.name].toSet
		
		val code = getSourceCode(language, eClass)
		
		val srcCode = if(code.get(1) === null) code.get(0) else code.get(1)
		
		val orgCodeBlock = cache.computeIfAbsent(eClass) [it.createClassLens(srcCode)]
		
		val shortenVer = orgCodeBlock.minimizeOperations(focusedOperationNames)
		
		val context = language.extractContext(eClass)
		
		val prompt = getPrompt(shortenVer, context, sysreq)
		
		LOG.info('Prompt:\n'+prompt)
		
		val result = port.queryString(prompt)
		
		val ans = port.extractOutputFromBlocks(result, #['java'])
		
		val genCode = ans.get('java').join('\n')
		
		LOG.info('LLM Response:\n'+genCode)
		
		val genCodeBlock = eClass.createClassLens(genCode)
		
		val newCode = orgCodeBlock.merge(genCodeBlock, focusedOperationNames)
		
		if(code.get(1) === null) {
			language.saveInterfaceCode(eClass, newCode)
		} else {
			language.saveClassCode(eClass, newCode)
		}
	}
	
	protected def String getPrompt(String code, String context, String sysreq)
	'''
	# Task
	You will be given a **Java Class** generated from an Ecore model by EMF code generator with `suppressInterfaces=true`. Your task is to implement the Java methods unimplemented by taking **System Requirement** and additional **Context** information into account.
	
	Complete this task by following the guidelines below.
	1. A method is unimplemented if its body contains `// TODO: Please complete this Java method based on the docstring`.
	2. For every unimplemented method, 
	  2.1 You must read the **System Requirement**;
	  2.2 Generate your code strictly according to **System Requirement**; Be aware of the EMF code style and APIs; Note that the getter of a boolean attribute `bv` looks like `isBv()`, e.g., `isIsTrue()` for `isTrue`;
	  2.3 You should not change the method signature;
	  2.4 After generation, replace the current body with the one you generate;
	  2.5 Keep and update the docstring by changing `@generated` to `@generated NOT`.
	3. Check the import list. Add missing imports if needed. Collection types provided by EMF is listed below
	  - BasicEList: `org.eclipse.emf.common.util.BasicEList`;
	4. Do not change other part.
	5. Be aware of the method signatures, particularly, the input and output types, provided in the **Context**.
	
	# Java Class
	```java
	«code»
	```
	
	# Context
	«context»

	# System Requirement
	«sysreq»
	
	# Output Format
	Put the new Java class code into a single block as follows.
	
	```java
	(completed Java class)
	```
	'''
	
}