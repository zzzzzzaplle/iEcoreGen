package edu.ustb.sei.mde.eecg.llm.enhancement

import com.google.inject.Inject
import edu.ustb.sei.mde.eecg.llm.CodeGenConstants
import edu.ustb.sei.mde.eecg.llm.EcoreTools
import edu.ustb.sei.mde.eecg.tools.ClassLens
import edu.ustb.sei.mde.eecg.tools.CodeTools
import edu.ustb.sei.mde.eecg.tools.MethodLens
import edu.ustb.sei.mde.mwe2.EcoreLanguage
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.util.List
import java.util.Map
import java.util.regex.Pattern
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EOperation
import org.eclipse.jdt.core.compiler.batch.BatchCompiler
import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite
import org.eclipse.jface.text.Document
import edu.ustb.sei.ai.LLMPort

/**
 * For experiment only
 */
class CodeFixingWithoutCompression extends ContextCodeEnhancement {
	@Inject
	var LLMPort port
	
	new() {
		this.port = null
	}
	
	new(LLMPort port) {
		this.port = port
	}
	
	extension EcoreTools = EcoreTools.instance
	extension CodeTools = CodeTools.instance
	
	
	override protected isFocused(EClassifier cls) {
		if(cls instanceof EClass) {
			cls.EOperations.exists[it.isFocused]
		} else false
	}
	
	protected def boolean isFocused(EOperation op) {
		op.getEAnnotationDetail(CodeGenConstants.EECG, CodeGenConstants.EECG_GEN) == 'true'
	}
	
	protected def String constructJavacCommand(EcoreLanguage language) {
		'''-11 -d «language.llmBinFolder» -classpath "«FOR libpath : language.classpaths SEPARATOR File.pathSeparator»«libpath»«ENDFOR»" «language.sourceInterfaceFolder»/«IF language.suppressInterfaces===false» «language.sourceClassFolder»/«ENDIF»'''
	}
	
	override enhance(EcoreLanguage language) {
		var retry = 0

        while(retry < MAX_RETRY) {
            val errors = getCompilationError(language)
		
            if(errors.isEmpty === false) {
                errors.forEach[
                    LOG.error(it)
                ]
                language.fixErrors(errors.filter[it.errorType.compareToIgnoreCase('WARNING')!==0].toList)
            } else {
            	LOG.info("There is more compilation error")
            	return;
            }
            
            retry += 1
        }
	}
	
	val Map<EClass, List<ErrorInfo>> errorMap = newHashMap
	
	static val MAX_RETRY = 3
	
	def void fixErrors(EcoreLanguage language, List<ErrorInfo> errors) {
		errorMap.clear
		
		val fileErrMap = errors.groupBy[it.filePath]
		
		fileErrMap.forEach[file, errs|
			val filePath = new File(file)
			val parentPathName = filePath.parentFile.name
			val fileName = filePath.name
			
			val eClassName =
				if(parentPathName === 'impl') { // magic word!!
					if(fileName.endsWith('Impl.java')) fileName.substring(0, fileName.length - 9)
					else fileName.substring(0, fileName.length - 5)
				} else {
					fileName.substring(0, fileName.length - 5)
				}
			
			val eClassifier = language.ecorePackage.getEClassifier(eClassName)
			
			if(eClassifier instanceof EClass) {
				errorMap.put(eClassifier, errs)
				doEnhancementWithRetry(language, eClassifier)
			} else {
				doGenericFix(file)
			}
		]
	}
	

	
	protected def List<ErrorInfo> getCompilationError(EcoreLanguage language) {
		val errors = <ErrorInfo>newArrayList
		
		val outstr = new StringWriter
		val out = new PrintWriter(outstr)
		
		val erroutstr = new StringWriter
		val errout = new PrintWriter(erroutstr)
		
		val cmd = language.constructJavacCommand
		LOG.info('''BatchCompiler «cmd»''')
		
		try {
			val isOk = BatchCompiler.compile(cmd, out, errout, null)
			
			if(!isOk) {
				val errMsg = erroutstr.toString
				
				LOG.info(outstr.toString)
				LOG.error(errMsg)
				
				val lines = errMsg.lines.toList
				
				val pattern = Pattern.compile("^([0-9]+)\\.\\s([A-Za-z]+)\\sin\\s(.+)\\s\\(at line ([0-9]+)\\)$")
				var ErrorInfo currentError = null
				
				for(line : lines) {
					val matcher = pattern.matcher(line.trim)
					if(matcher.find) {
						currentError = new ErrorInfo
						currentError.filePath = matcher.group(3);
			            currentError.lineNumber = Integer.parseInt(matcher.group(4));
			            currentError.errorType = matcher.group(2);
					} else if (currentError !== null && line.trim().endsWith("^")) {
			            currentError.caretPosition = line
			        } else if (currentError !== null && !line.blank && line!='----------') {
			        	if(currentError.codeLine === null)
				        	currentError.codeLine = line
				        else currentError.message = line
				    } else if(line == '----------') {
				    	if(currentError !== null) {
				    		errors.add(currentError)
				    		currentError = null
				    	}
				    }
				}
				if (currentError !== null) errors.add(currentError);
			} else {
				LOG.info(outstr.toString)
				LOG.error(erroutstr.toString)
			}
		
		} catch(Exception e){
			LOG.error(e.message)
		}
		
		out.close
		errout.close
		
		return errors
	}
	
	protected def String getEClassCodeFixPrompt(EClass eClass, String code, List<ErrorInfo> errors, String context) {
		'''
		# Task
		You will be given a **Java Class** that is originally generated by EMF code generator with `suppressInterfaces=true` and later is completed by LLMs. Your task is to fix the **Compilation Errors** by taking additional **Context** information into account.
		
		Complete this task by following the guidelines below.
		1. Read the error messages carefully.
		2. Do your best to fix the errors locally without introducing new types, fields, and methods.
		3. You should not remove faulty methods to eliminate compilation errors.
		4. Do not change parts without errors.
		
		# Java Class
		```java
		«code»
		```
		
		# Compilation Errors
		```
		«FOR err : errors SEPARATOR '\n'»
		- «err.errorType»: «err.message»
		«err.codeLine»
		«err.caretPosition»
		«ENDFOR»
		```
		
		# Context
		«context»
		
		# Output Format
		Just put the Java class you fixed into a single block as follows.
		
		```result
		(fixed Java class)
		```
		'''
	}
	
	override protected doEnhancement(EcoreLanguage language, EClassifier target) {
		val eClass = target as EClass
		val code = getSourceCode(language, eClass)
		val srcCode = if(code.get(1) === null) code.get(0) else code.get(1)
		
		val oldCodeBlock = cache.computeIfAbsent(eClass) [it.createClassLens(srcCode)]
		
		val context = language.extractContext(eClass)
		val errs = errorMap.get(eClass)
		
		// TODO: fix methods by err lines
		val errMethods = oldCodeBlock.methods.filter[m| errs.exists[e| m.startLine <= e.lineNumber && e.lineNumber <= m.endLine]].toList
		val errMethodNames = errMethods.map[it.name].toSet
		
		// TODO: generate a short version
		val shortenVer = srcCode
		
		val prompt = getEClassCodeFixPrompt(eClass, shortenVer, errs, context)
		
		println(prompt)
		
		val result = port.queryString(prompt)
		
		val ans = port.extractOutputFromBlocks(result, #['result'])
		
		val genCode = ans.get('result').join('\n')
		
		println('fixed code:')
		
		println(genCode)
		
		val genCodeBlock = eClass.createClassLens(genCode)
		
		val newCode = oldCodeBlock.merge(genCodeBlock, errs, errMethods)
		
		if(code.get(1) === null) {
			language.saveInterfaceCode(eClass, newCode)
		} else {
			language.saveClassCode(eClass, newCode)
		}
	}
	
	protected def String merge(ClassLens oldCodeBlock, ClassLens newCodeBlock, List<ErrorInfo> errs, List<MethodLens> errMethods) {
		val rewrite = ASTRewrite.create(oldCodeBlock.unitAST)
		
		ClassLens.mergeImports(rewrite, oldCodeBlock, newCodeBlock)
		rewrite.mergeFixedMethods(oldCodeBlock, newCodeBlock, errMethods)
		
		val doc = new Document(oldCodeBlock.source)
		val edits = rewrite.rewriteAST(doc, null)
		edits.apply(doc)
		
		return doc.get
	}
	
	static protected def void mergeFixedMethods(ASTRewrite rewrite, ClassLens oldCodeBlock, ClassLens newCodeBlock, List<MethodLens> errMethods) {
		for(MethodLens ml : errMethods) {
			val fixed = newCodeBlock.getMethodByName(ml.name)
			if(fixed === null) {
				rewrite.remove(ml.method, null)
			} else {
				val code = rewrite.createStringPlaceholder(fixed.methodString, ASTNode.METHOD_DECLARATION)
				rewrite.replace(ml.method, code, null)
			}
		}
	}
	
	protected def void doGenericFix(String string) {
		// do nothing now
	}
}