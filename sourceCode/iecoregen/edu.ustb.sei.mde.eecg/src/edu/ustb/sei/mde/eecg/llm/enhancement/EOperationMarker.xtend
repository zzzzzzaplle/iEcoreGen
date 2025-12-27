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

/**
 * For experiment only
 */
class EOperationMarker extends EcoreModelEnhancement {
	@Inject
	var LLMPort port
	
	new() {
		this.port = null
	}
	
	extension EcoreTools = EcoreTools.instance
	
	new(LLMPort port) {
		this.port = port
	}
	
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

        ePack.EClassifiers.filter(EClass).forEach[c|
            c.EOperations.forEach[op|
                op.setEAnnotationDetail(CodeGenConstants.EECG, CodeGenConstants.EECG_GEN, "true")
                op.setEAnnotationDetail(CodeGenConstants.GEN_MODEL, CodeGenConstants.GEN_MODEL__BODY, EMPTY_METHOD_BODY)
            ]
        ]
	}
	
	static val EMPTY_METHOD_BODY = 
	'''
	// TODO: Please generate a body of this unimplemented method based on the docstring
	// TODO: Replace the current body with the generated body 
	// TODO: Ensure that you mark it @generated NOT after code completion
	throw new UnsupportedOperationException();
	'''
}