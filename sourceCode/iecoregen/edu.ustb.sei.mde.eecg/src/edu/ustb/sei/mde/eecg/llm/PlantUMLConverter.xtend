package edu.ustb.sei.mde.eecg.llm

import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EEnum

class PlantUMLConverter {
	def String convertToPlantUML(EPackage ePack) '''
	    @startuml
	    
	    title «ePack.name» Model
	    
	    «FOR eClass : ePack.EClassifiers.filter(EClass).sortBy[name]»
	        «eClass.toPlantUML»
	    «ENDFOR»
	    «FOR eEnum : ePack.EClassifiers.filter(EEnum).sortBy[name]»
	    	«eEnum.toPlantUML»
	    «ENDFOR»
	    
	    «FOR eClass : ePack.EClassifiers.filter(EClass).sortBy[name]»
	        «FOR superType : eClass.ESuperTypes»
	            «eClass.name» --|> «superType.name»
	        «ENDFOR»
	        «FOR reference : eClass.EReferences.sortBy[name]»
	            «IF reference.containment»
	                «eClass.name» *-- «reference.multiplicity»«reference.EType.name» : «reference.name»«IF reference.EOpposite!==null»  'opposite: «reference.EOpposite.name»«ENDIF»
	            «ELSE»
	                «eClass.name» --> «reference.multiplicity»«reference.EType.name» : «reference.name»«IF reference.EOpposite!==null»  'opposite: «reference.EOpposite.name»«ENDIF»
	            «ENDIF»
	        «ENDFOR»
	    «ENDFOR»
	    @enduml
	'''
	
	def toPlantUML(EClass eClass) '''
	    «IF eClass.abstract»abstract «ENDIF»class «eClass.name» {
	        «FOR attr : eClass.EAttributes.sortBy[name]»
	            «attr.toPlantUMLFeature»
	        «ENDFOR»
	        «FOR ref : eClass.EReferences.sortBy[name]»
	            «ref.toPlantUMLFeature»
	        «ENDFOR»
	        «FOR op : eClass.EOperations.sortBy[name]»
	            «op.toPlantUMLFeature»
	        «ENDFOR»
	    }
	'''
	
	def toPlantUML(EEnum eEnum) '''
	    enum «eEnum.name» {
	        «FOR lit : eEnum.ELiterals.sortBy[value]»
	            «lit.name»
	        «ENDFOR»
	    }
	'''

	def dispatch toPlantUMLFeature(EAttribute attr) {
	    '''«attr.name» : «attr.EType.toFeatureType(attr.isMany)»'''
	}
	
	def dispatch toPlantUMLFeature(EReference ref) {
	    '''«ref.name» : «ref.EType.toFeatureType(ref.isMany)»'''
	}
	
	def dispatch toPlantUMLFeature(EOperation op) {
	    '''«op.name»(«FOR param : op.EParameters SEPARATOR ', '»«param.name» : «param.EType.toFeatureType(param.isMany)»«ENDFOR») : «IF op.EType !== null»«op.EType.toFeatureType(op.isMany)»«ELSE»void«ENDIF»'''
	}
	
	def multiplicity(EStructuralFeature feature) {
	    if (feature.upperBound == 1) 
	        "" 
	    else 
	        "\"*\" "
	}
	
	def toFeatureType(EClassifier type, boolean isMany) {
		if(isMany) '''List<«type.typeName»>'''
		else type.typeName
	}
	
	def dispatch typeName(EClassifier classifier) {
	    classifier.name
	}
	
	def dispatch typeName(EDataType dataType) {
	    switch dataType.name {
	        case "EString": "String"
	        case "EInt": "int"
	        case "EBoolean": "boolean"
	        case "EDouble": "double"
	        case "EFloat": "float"
	        case "ELong": "long"
	        case "EByte": "byte"
	        case "EShort": "short"
	        case "EChar": "char"
	        default: dataType.name
	    }
	}
}