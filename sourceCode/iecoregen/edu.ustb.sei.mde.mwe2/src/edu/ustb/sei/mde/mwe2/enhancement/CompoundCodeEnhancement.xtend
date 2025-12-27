package edu.ustb.sei.mde.mwe2.enhancement

import edu.ustb.sei.mde.mwe2.EcoreLanguage
import java.util.List
import org.eclipse.emf.ecore.EClassifier
import com.google.inject.Injector

class CompoundCodeEnhancement extends CodewareEnhancement {
	val List<CodewareEnhancement> enhancements
	
	new(CodewareEnhancement... enhancements) {
		this.enhancements = newArrayList(enhancements)
	}
	
	override enhance(EcoreLanguage language) {
		for(enhancement : enhancements) {
			enhancement.enhance(language)
		}
	}
	
	override protected isFocused(EClassifier cls) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override protected doEnhancement(EcoreLanguage language, EClassifier target) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override inject(Injector injector) {
		enhancements.forEach[it.inject(injector)]
	}
	
}