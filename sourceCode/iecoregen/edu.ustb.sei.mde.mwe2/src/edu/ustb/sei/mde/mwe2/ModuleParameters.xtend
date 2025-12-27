package edu.ustb.sei.mde.mwe2

import com.google.inject.Binder
import edu.ustb.sei.mde.mwe2.enhancement.EcoreModelEnhancement
import java.util.List
import edu.ustb.sei.mde.mwe2.enhancement.GenModelEnhancement
import edu.ustb.sei.mde.mwe2.enhancement.CodewareEnhancement
import edu.ustb.sei.mde.mwe2.enhancement.CompoundEcoreModelEnhancement
import edu.ustb.sei.mde.mwe2.enhancement.CompoundCodeEnhancement
import com.google.inject.Injector

class ModuleParameters {
	def void inject(Injector injector) {
		ecoreEnhancers.forEach[
			it.inject(injector)
		]
		
		genmodelEnhancers.forEach[
			it.inject(injector)
		]
		
		codeEnhancers.forEach[
			it.inject(injector)
		]
	}
	
	def void configure(Binder binder) {
		if(getEcoreEnhancers.empty === false) {
			
			binder.bind(EcoreModelEnhancement).toInstance(
				if(getEcoreEnhancers.size === 1) getEcoreEnhancers.get(0)
				else new CompoundEcoreModelEnhancement(getEcoreEnhancers)
			)
		}
		
		if(getGenmodelEnhancers.empty === false) {
			println("Do not support genmodel enhancement now")
//			binder.bind(GenModelEnhancement).toInstance(
//				new CompoundEcoreModelEnhancement(getEcoreEnhancers)
//			)
		}
		
		if(codeEnhancers.empty === false) {
			binder.bind(CodewareEnhancement).toInstance(
				if(getCodeEnhancers.size === 1) getCodeEnhancers.get(0)
				else new CompoundCodeEnhancement(getCodeEnhancers)
			)
			
		}
		
	}
	
	
	val List<EcoreModelEnhancement> ecoreEnhancers = newArrayList
	val List<GenModelEnhancement> genmodelEnhancers = newArrayList
	val List<CodewareEnhancement> codeEnhancers = newArrayList
	
	
	def getEcoreEnhancers() {
		ecoreEnhancers
	}
	
	def getGenmodelEnhancers() {
		genmodelEnhancers
	}
	
	def getCodeEnhancers() {
		codeEnhancers
	}
	
	def addEcoreEnhancer(EcoreModelEnhancement e) {
		ecoreEnhancers += e
	}
	
	def addGenmodelEnhancer(GenModelEnhancement e) {
		genmodelEnhancers += e
	}
	
	def addCodeEnhancer(CodewareEnhancement e) {
		codeEnhancers += e
	}
}