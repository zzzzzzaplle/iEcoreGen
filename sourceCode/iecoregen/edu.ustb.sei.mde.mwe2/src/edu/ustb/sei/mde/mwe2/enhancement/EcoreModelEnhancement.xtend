package edu.ustb.sei.mde.mwe2.enhancement

import edu.ustb.sei.mde.mwe2.EcoreLanguage
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage

abstract class EcoreModelEnhancement extends ModelwareEnhancement {
	
	
	override enhance(EcoreLanguage language) {
		doEnhancement(language)
	}
	
	
	protected def void enhanceEcore(EPackage pkg)
	
	override getFocus(EcoreLanguage language) {language.ecorePackage}
	
	override doEnhanceModel(EObject focus) {
		(focus as EPackage).enhanceEcore
	}
	
}