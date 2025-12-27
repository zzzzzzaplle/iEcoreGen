package edu.ustb.sei.mde.mwe2.enhancement

import org.eclipse.emf.ecore.EObject
import java.util.Map
import org.eclipse.emf.ecore.xmi.XMIResource
import edu.ustb.sei.mde.mwe2.EcoreLanguage
import org.apache.log4j.Logger
import edu.ustb.sei.mde.mwe2.EcoreGenerator

abstract class ModelwareEnhancement extends InjectorClient {
	
	static val protected Logger LOG = Logger.getLogger(EcoreGenerator);
	
	def void enhance(EcoreLanguage language) {
		doEnhancement(language)
	}
	
	protected def int maxRetry() {5}
	
	protected def void doEnhancement(EcoreLanguage language) {
		var retryCnt = 0
		
		val focus = language.focus
		
		if(focus === null) return;
		
		while(retryCnt < maxRetry) {	
			try {
				focus.doEnhanceModel
				focus.save
				return
			} catch (Exception ex) {
				LOG.error(ex.message)
			}
			
			retryCnt += 1
		}
		LOG.error('Failed after maximum retry')
		throw new RuntimeException('Failed after maximum retry')
	}
	
	protected def void doEnhanceModel(EObject focus)
	
	protected def EObject getFocus(EcoreLanguage language) 
	
	def void save(EObject obj) {
		obj.eResource.save(Map.of(XMIResource.OPTION_ENCODING, "utf8", XMIResource.OPTION_SCHEMA_LOCATION, true))
	}
}