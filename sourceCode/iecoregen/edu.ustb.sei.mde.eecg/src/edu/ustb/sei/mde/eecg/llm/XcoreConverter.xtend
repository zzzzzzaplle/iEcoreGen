package edu.ustb.sei.mde.eecg.llm

import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EEnum

class XcoreConverter {
    
    def String convert(EPackage ePackage) '''
        package «ePackage.name»
        
        «FOR classifier : ePackage.EClassifiers»
            «classifier.convertTypeDef»
        «ENDFOR»
    '''
    
    def dispatch convertTypeDef(EClass eClass) '''
        class «eClass.name»«IF !eClass.ESuperTypes.empty» extends «eClass.ESuperTypes.map[name].join(', ')»«ENDIF» {
            «FOR feature : eClass.EStructuralFeatures»
                «feature.convertFeature»
            «ENDFOR»
            «FOR operation : eClass.EOperations»
                «operation.convertFeature»
            «ENDFOR»
        }
    '''

    def dispatch convertTypeDef(EEnum eEnum) '''
        enum «eEnum.name» {
        	«FOR lit : eEnum.ELiterals.sortBy[value]»
        	«lit.name»
        	«ENDFOR»
        }
    '''

    def dispatch convertFeature(EAttribute attr) {
        '''«IF attr.derived»derived «ENDIF»«IF attr.ID»id «ENDIF»«attr.EType.convertType»«attr.convertMultiplicity» «attr.name»«IF attr.defaultValueLiteral !== null» = «attr.defaultValueLiteral»«ENDIF»'''
    }
    
    def dispatch convertFeature(EReference ref) {
        '''«IF ref.containment»contains «ENDIF»«IF ref.derived»derived «ENDIF»refers «ref.EType.name»«ref.convertMultiplicity» «ref.name»«IF ref.EOpposite !== null» opposite «ref.EOpposite.name»«ENDIF»'''
    }
    
    def dispatch convertFeature(EOperation op) '''
        op «IF op.EType !== null»«op.EType.convertType»«convertMultiplicity(op.lowerBound, op.upperBound)» «ENDIF»«op.name»(«FOR param : op.EParameters SEPARATOR ', '»«param.EType.convertType»«convertMultiplicity(param.lowerBound, param.upperBound)» «param.name»«ENDFOR»)
    '''
    
    def dispatch convertTypeDef(EDataType dataType) '''
        type «dataType.name» wraps «dataType.instanceTypeName»
    '''
    
    def dispatch convertType(EClassifier classifier) {
        classifier.name
    }
    
    def dispatch convertType(EDataType dataType) {
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
            case "EDate": "Date"
            default: dataType.name
        }
    }
    
    def convertMultiplicity(EStructuralFeature feature) {
        return convertMultiplicity(feature.lowerBound, feature.upperBound)
    }
    
    def convertMultiplicity(int lowerBound, int upperBound) {
        if (upperBound == 1 && lowerBound == 1) 
            "" 
        else 
            '''[«IF lowerBound != 0»«lowerBound»«ENDIF»..«IF upperBound < 0»*«ELSE»«upperBound»«ENDIF»]'''
    }
}