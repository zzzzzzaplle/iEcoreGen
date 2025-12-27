package edu.ustb.sei.mde.eecg.llm.enhancement

import edu.ustb.sei.mde.mwe2.enhancement.CodewareEnhancement
import java.util.Map
import org.eclipse.emf.ecore.EClass
import edu.ustb.sei.mde.eecg.tools.ClassLens
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EStructuralFeature
import edu.ustb.sei.mde.mwe2.EcoreLanguage
import edu.ustb.sei.mde.eecg.tools.CodeTools

abstract class ContextCodeEnhancement extends CodewareEnhancement {
	extension CodeTools = CodeTools.instance
	
	protected def String extractContext(EcoreLanguage language, EClass target) {
		val refs = target.EAllReferences
		val ops = target.EAllOperations
		
		val related = (
			target.EAllSuperTypes 
			+ refs.filter[it.EReferenceType !== target].map[it.EReferenceType]
			+ ops.map[it.EType].filter(EClass)
			+ ops.map[it.EParameters].flatMap[it.map[it.EType].filter(EClass)]
		).toSet
		
		'''
		The following classes' information may be helpful for you. **DO NOT** call methods that are not listed.
		«FOR rc : related»
		«language.generateClassDoc(rc)»
		«ENDFOR»
		'''
	}
	
	
	protected def String generateClassDoc(EcoreLanguage language, EClass eClass) {
		val allOperations = eClass.EAllOperations
		val allFeatures = eClass.EAllStructuralFeatures
		val code = getSourceCode(language, eClass)
		
		val srcCode = if(code.get(1) === null) code.get(0) else code.get(1)
		
		val classBlock = cache.computeIfAbsent(eClass)[eClass.createClassLens(srcCode)]
		
		'''
		- Class `«eClass.name»` contains the following methods.
		  + Instance creation: «IF eClass.abstract ||  eClass.interface»Can only be used as an «IF eClass.interface»interface«ELSE»abstract type«ENDIF»«ELSE»Use `«eClass.EPackage.name.toFirstUpper»Factory.eINSTANCE.create«eClass.name.toFirstUpper»()`«ENDIF»
		«IF !allOperations.isEmpty»  + Public methods: «FOR op : allOperations SEPARATOR ', '»«op.generateMethSig(classBlock)»«ENDFOR»«ENDIF»
		«IF !allFeatures.isEmpty  »  + Getter methods: «FOR at : allFeatures SEPARATOR ', '»«at.generateGetterSig(classBlock)»«ENDFOR»«ENDIF»
		«IF !allFeatures.isEmpty  »  + Setter methods: «FOR at : allFeatures.filter[!derived && !isMany && changeable] SEPARATOR ', '»«at.generateSetterSig(classBlock)»«ENDFOR»«ENDIF»
		'''
	}
	
	def String generateSetterSig(EStructuralFeature feature, ClassLens block) {
		block.getAccessorMethodByFeatureName(feature, false)?.signature ?: ""
	}
	
	def String generateGetterSig(EStructuralFeature feature, ClassLens block) {
		block.getAccessorMethodByFeatureName(feature, true)?.signature ?: ""
	}
	
	def String generateMethSig(EOperation operation, ClassLens block) {
		block.getMethodByName(operation.name)?.signature ?: ""
	}
	
	override protected clean() {
		cache.clear
	}
	
	protected val Map<EClass, ClassLens> cache = newHashMap
	
}