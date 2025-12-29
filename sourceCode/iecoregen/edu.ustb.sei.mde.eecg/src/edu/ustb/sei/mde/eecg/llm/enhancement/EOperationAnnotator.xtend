package edu.ustb.sei.mde.eecg.llm.enhancement

import edu.ustb.sei.mde.mwe2.enhancement.EcoreModelEnhancement
import java.util.List
import java.util.Map
import java.util.regex.Pattern
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import edu.ustb.sei.mde.eecg.llm.EcoreTools
import edu.ustb.sei.mde.eecg.llm.CodeGenConstants
import edu.ustb.sei.mde.eecg.llm.PlantUMLConverter
import com.google.inject.Inject
import edu.ustb.sei.ai.LLMPort

class EOperationAnnotator extends EcoreModelEnhancement {
	@Inject
	var LLMPort port
	
	new() {
		this.port = null
	}
	
	extension EcoreTools = EcoreTools.instance
	
	new(LLMPort port) {
		this.port = port
	}
	
	def String getPrompt(String req, String cd) 
	'''
	# Task
	You will be given a **Requirement** and a **Class Diagram** in PlantUML. Your task is to annotate every operation in the class diagram with a specfication.
	
	An operation specification must describe the following sections about the operation:
	1. Summary: the summary of functionality;
	2. Algorithm: algorithm or pesudo code;
	3. Related Features: a list of structural or behavior features within this class that shall be accessed;
	4. Related Classes: a list of classes and data types defined in **Class Diagram** that shall be accessed;
	5. Input: the input parameters, including the meaning, the format, and the value ranges if necessary;
	6. Output: output value, including the meaning, the format, and the value ranges if necessary;
	7. Pre-condition and Post-condition: the pre-/post-conditions if necessary.
	
	Finish this task by following the guidelines below.
	1. Take the principle of modularization into account.
	2. For each operation, determine its functional boundary based on the given requirement. Ensure high cohesion and low coupling.
	3. If there is any unspecified details, such as undefined constants, rates, and ratios, then you must infer them.
	4. Make sure that all requirements can be covered.
	
	# Requirement
	```text
	«req»
	```
	
	# Class Diagram
	```plantuml
	«cd»
	```
	
	# Output Format
	Put all your results in a single block as illustrated below:
	
	```result
	
	class_1::operation_1
	/*
	 * your description for operation_1
	 */
	
	class_1::operation_2
	/*
	 * your description for this operation_2
	 */
	
	...
	```
	'''
	
	protected def void cleanAnnotations(EPackage ePack) {
		ePack.EClassifiers.filter(EClass).forEach[
			it.EOperations.forEach[
				val isGenerated = it.getEAnnotationDetail(CodeGenConstants.EECG, CodeGenConstants.EECG_GEN)
				if(isGenerated == 'true') {
					it.EAnnotations.clear
				}
			]
		]
	}
	
	override protected enhanceEcore(EPackage ePack) {
		LOG.info('Annotating EOperations')
		
		ePack.cleanAnnotations
		
		val converter = new PlantUMLConverter

		val sysreq = ePack.getEAnnotationDetail(CodeGenConstants.EECG, CodeGenConstants.EECG_REG)

		val plantuml = converter.convertToPlantUML(ePack)
		
		val prompt = getPrompt(sysreq, plantuml)

		LOG.info('Prompt:\n'+prompt)

		val result = port.queryString(prompt)

		val ans = port.extractOutputFromBlocks(result, #['result'])

		val opDocMap = extractOperationAnnotation(ePack, ans.get('result').join('\n'))

		LOG.info('LLM Response:\n'+opDocMap)

		opDocMap.forEach [ op, doc |
			op.setEAnnotationDetail(CodeGenConstants.EECG, CodeGenConstants.EECG_GEN, "true")
			op.setEAnnotationDetail(CodeGenConstants.GEN_MODEL, CodeGenConstants.GEN_MODEL__DOCUMENTATION, doc)
			op.setEAnnotationDetail(CodeGenConstants.GEN_MODEL, CodeGenConstants.GEN_MODEL__BODY, EMPTY_METHOD_BODY)
		]
	}
	
	static val EMPTY_METHOD_BODY = 
	'''
	// TODO: Please generate a body of this unimplemented method based on the docstring
	// TODO: Replace the current body with the generated body 
	// TODO: Ensure that you mark it @generated NOT after code completion
	throw new UnsupportedOperationException();
	'''
	
	val operationPattern = Pattern.compile("([A-Za-z0-9_]+)::([A-Za-z0-9_]+)")
	val docStartPattern = Pattern.compile("\\s*/\\*\\s*", Pattern.DOTALL)
	val docEndPattern = Pattern.compile("\\s*\\*/\\s*", Pattern.DOTALL)
	val docBodyPattern = Pattern.compile("\\s*\\* (.*)", Pattern.DOTALL)
	
	
	protected def Map<EOperation, String> extractOperationAnnotation(EPackage ePack, String result) {
		val map = newHashMap
		
		val lines = result.lines.toList
		
		var EOperation currentOperation = null
		var List<String> operationDocs = null
		
		for(line : lines) {
			if(currentOperation === null) {
				val matcher = operationPattern.matcher(line)
				if(matcher.find) {
					val eClassName = matcher.group(1)
					val operationName = matcher.group(2)
					
					val eClass = ePack.getEClassifier(eClassName) as EClass
					if(eClass !== null) {
						currentOperation = eClass.EOperations.findFirst[it.name==operationName]
					}
				}
			} else {
				if(operationDocs === null) {
					if(docStartPattern.asMatchPredicate.test(line)) {
						operationDocs = newArrayList
					}
				} else {
					if(docEndPattern.asMatchPredicate.test(line)) {
						map.put(currentOperation, operationDocs.join('\n'))
						currentOperation = null
						operationDocs = null
					} else {
						val bodyMatcher = docBodyPattern.matcher(line)
						if(bodyMatcher.matches) {
							val body = bodyMatcher.group(1)
							operationDocs += body
						}
					}
				}
			}
			
		}
		
		return map
	}
	
}