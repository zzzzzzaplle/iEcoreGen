package edu.ustb.sei.mde.mwe2.enhancement

import edu.ustb.sei.mde.mwe2.enhancement.EcoreModelEnhancement
import org.eclipse.emf.ecore.EPackage
import java.util.List
import edu.ustb.sei.mde.mwe2.EcoreLanguage
import com.google.inject.Injector

class CompoundEcoreModelEnhancement extends EcoreModelEnhancement {
	val List<EcoreModelEnhancement> enhancements
	
	new(EcoreModelEnhancement... enhancements) {
		this.enhancements = newArrayList(enhancements)
	}
	
	override void doEnhancement(EcoreLanguage language) {
		for(e : enhancements) {
			e.doEnhancement(language)
		}
	}
	
	override protected enhanceEcore(EPackage pkg) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override inject(Injector injector) {
		enhancements.forEach[it.inject(injector)]
	}
	
}