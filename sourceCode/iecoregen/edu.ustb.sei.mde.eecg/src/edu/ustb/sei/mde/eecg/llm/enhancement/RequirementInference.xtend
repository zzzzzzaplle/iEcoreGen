package edu.ustb.sei.mde.eecg.llm.enhancement

import edu.ustb.sei.mde.mwe2.enhancement.EcoreModelEnhancement
import org.eclipse.emf.ecore.EPackage
import edu.ustb.sei.mde.eecg.llm.EcoreTools
import edu.ustb.sei.mde.eecg.llm.CodeGenConstants
import edu.ustb.sei.mde.eecg.llm.PlantUMLConverter
import com.google.inject.Inject
import edu.ustb.sei.ai.LLMPort

class RequirementInference extends EcoreModelEnhancement {
	extension EcoreTools = EcoreTools.instance
	
	@Inject
	var LLMPort port
	
	new() {
		port = null;
	}
	
	new(LLMPort port) {
		this.port = port
	}
	
	
	override protected enhanceEcore(EPackage ePack) {
		if(ePack.getEAnnotation(CodeGenConstants.EECG) !== null) return;
		
		LOG.info('Inferring Requirements')
		
		val converter = new PlantUMLConverter
		
		val plantuml = converter.convertToPlantUML(ePack)
		val prompt = getPrompt(plantuml)
		
		LOG.info('Prompt:\n'+prompt)
		
		val result = port.queryString(prompt)
		
		LOG.info('LLM Response:\n'+result)
		
		val ans = port.extractOutputFromBlocks(result, #['result'])
		
		ePack.setEAnnotationDetail(CodeGenConstants.EECG, CodeGenConstants.EECG_REG, ans.get('result').join('\n'))
	}
	
	def String getPrompt(String cd)
	'''
	# Task
	You will be given a **Class Diagram** in PlantUML. Your task is to infer a system requirement based on the class diagram.
	
	Finish this task by following the guidelines below.
	1. Read the class diagram carefully.
	2. Write a system requirement in plain English, including domain description and functional requirements.
	3. Make sure that the requirement is equivalent to the meaning of the class diagram.
	4. Make sure that the requirement covers all classes, attributes, references, enumerations, and operations.
	
	# Class Diagram
	```plantuml
	«cd»
	```
	
	# Output Format
	Put all your result in a single block as illustrated below:
	
	```result
	(system requirement)
	```
	'''
	
}