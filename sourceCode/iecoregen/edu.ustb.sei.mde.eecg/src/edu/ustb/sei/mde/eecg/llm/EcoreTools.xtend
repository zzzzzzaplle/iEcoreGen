package edu.ustb.sei.mde.eecg.llm

import org.eclipse.emf.ecore.EAnnotation
import org.eclipse.emf.ecore.EModelElement
import org.eclipse.emf.ecore.EcoreFactory

class EcoreTools {
	static public val EcoreTools instance = new EcoreTools
	
	def EAnnotation getOrCreateEAnnotation(EModelElement element, String source) {
		element.getEAnnotation(source) ?: {
			EcoreFactory.eINSTANCE.createEAnnotation => [
				it.source = source
				element.EAnnotations += it
			]
		}
	}
	
	def String getEAnnotationDetail(EModelElement element, String source, String key) {
		val ann = element.getEAnnotation(source)
		return if(ann !== null) ann.details.get(key) else null
	}
	
	def void setEAnnotationDetail(EModelElement element, String source, String key, String value) {
		val ann = element.getOrCreateEAnnotation(source)
		ann.details.put(key, value)
	}
}