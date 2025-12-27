package edu.ustb.sei.mde.mwe2.enhancement

import org.eclipse.emf.codegen.ecore.genmodel.GenModel
import edu.ustb.sei.mde.mwe2.EcoreLanguage

abstract class GenModelEnhancement extends ModelwareEnhancement {
	
	override enhance(EcoreLanguage language) {
		val gen = language.genModel
		enhanceGenmodel(gen)
		save(gen)
	}
	
	protected def void enhanceGenmodel(GenModel genmodel)
}