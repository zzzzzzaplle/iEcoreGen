package edu.ustb.sei.mde.eecg.llm.enhancement

import edu.ustb.sei.mde.mwe2.enhancement.CodewareEnhancement
import java.util.Map
import org.eclipse.emf.ecore.EClass
import edu.ustb.sei.mde.eecg.tools.ClassLens
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EStructuralFeature
import edu.ustb.sei.mde.mwe2.EcoreLanguage
import edu.ustb.sei.mde.eecg.tools.CodeTools
import edu.ustb.sei.mde.eecg.llm.EcoreTools
import edu.ustb.sei.mde.eecg.llm.CodeGenConstants
import java.util.regex.Pattern
import org.eclipse.emf.ecore.EEnum

abstract class ContextCodeEnhancement extends CodewareEnhancement {
	extension CodeTools = CodeTools.instance
	extension EcoreTools = EcoreTools.instance
	
	val related_class_pattern = Pattern.compile('''Related Classes: ([a-zA-Z0-9_,]+)$''')
	
	protected def String extractContext(EcoreLanguage language, EClass target) {
		val refs = target.EAllReferences
		val ops = target.EAllOperations

		val ePkg = target.EPackage // assume that there is only one package but can be generalized
		val related_types = target.EAllOperations.map[it.getEAnnotationDetail(CodeGenConstants.GEN_MODEL, CodeGenConstants.GEN_MODEL__DOCUMENTATION)].filterNull.map[
			val matcher = related_class_pattern.matcher(it)
			if(matcher.find) {
				val list = matcher.group(1)
				list.split(',|;|.').map[it.trim]
			} else #[]
		].flatten.map[ePkg.getEClassifier(it)].filterNull
		
		val related = (
			target.EAllSuperTypes 
			+ refs.filter[it.EReferenceType !== target].map[it.EReferenceType]
			+ ops.map[it.EType]
			+ ops.map[it.EParameters].flatMap[it.map[it.EType]]
			+ related_types
		).toSet
		
		'''
		«IF related.filter(EClass).isEmpty===false»The following classes' information may be helpful for you. **DO NOT** call methods that are not listed.«ENDIF»
		«FOR rc : related.filter(EClass)»
		«language.generateClassDoc(rc)»
		«ENDFOR»
		«IF related.filter(EEnum).isEmpty===false»The following enumeration' information may be helpful for you.«ENDIF»
		«FOR rc : related.filter(EEnum)»
		«language.generateEnumDoc(rc)»
		«ENDFOR»
		'''
	}
	
	protected def String generateEnumDoc(EcoreLanguage language, EEnum eEnum) {
		'''
		- Enum `«eEnum.name»` contains the following literals: «FOR l : eEnum.ELiterals SEPARATOR ', '»«l.name»«ENDFOR». Use `«eEnum.name».get("<literal>")` to get the enum literal.
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