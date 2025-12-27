package edu.ustb.sei.mde.mwe2.enhancement

import com.google.inject.Inject
import edu.ustb.sei.mde.mwe2.EcoreGenerator
import edu.ustb.sei.mde.mwe2.EcoreLanguage
import java.io.File
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.xtext.xtext.generator.model.project.IXtextProjectConfig

abstract class CodewareEnhancement extends InjectorClient {
	static val protected Logger LOG = Logger.getLogger(EcoreGenerator);
	
	extension FileTools = FileTools.instance
	
	@Inject
	var IXtextProjectConfig projectConfig;
	
	protected def IXtextProjectConfig getProjectConfig() {
		return projectConfig;
	}
	
	def void enhance(EcoreLanguage language) {
		language.ecorePackage.EClassifiers.filter(EClassifier).filter[it.isFocused].forEach[
			language.doEnhancementWithRetry(it)
		]
		
		clean()
	}
	
	protected def void clean() {}
	
	protected def boolean isFocused(EClassifier cls)
	
	protected def int maxRetry() {5}
	
	protected def void doEnhancementWithRetry(EcoreLanguage language, EClassifier target) {
		var retry = 0
		
		while(retry < maxRetry) {
			try {
				doEnhancement(language, target)
				return
			} catch(Exception ex) {
				LOG.error(ex.message)
			}
			retry += 1
		}
		
		LOG.error('Failed after maximum retry')
		
		throw new RuntimeException('Failed after maximum retry')
	}
	
	protected def void doEnhancement(EcoreLanguage language, EClassifier target)
	
	var String interfaceFolder
	def String sourceInterfaceFolder(EcoreLanguage language) {
		if(interfaceFolder === null) {
			val srcGenPath = projectConfig.runtime.srcGen.path
			interfaceFolder = srcGenPath + '/' + language.basePackage.replaceAll('\\.', '/') + '/' + language.ecorePackage.name
		}
		return interfaceFolder
	}
	
	var String classFolder
	def String sourceClassFolder(EcoreLanguage language) {
		if(classFolder === null) {
			val srcGenPath = projectConfig.runtime.srcGen.path
			classFolder = srcGenPath + '/' + language.basePackage.replaceAll('\\.', '/') + '/' + language.ecorePackage.name + '/impl'
		}
		return classFolder
	}
	
	var String llmBinFolder
	def String llmBinFolder(EcoreLanguage language) {
		if(llmBinFolder === null) {
			val srcGenPath = projectConfig.runtime.srcGen.path
			llmBinFolder = srcGenPath + '/../llm-bin'
		}
		return llmBinFolder
	}
	
	final def List<String> getSourceCode(EcoreLanguage language, EClass eClass) {
		val iPath = language.sourceInterfaceFolder + '/' + eClass.name + '.java'
		
		val iFile = new File(iPath)
		
		if(iFile.exists) {
			val iCode = iFile.loadFile
			val cPath = language.sourceClassFolder + '/' + eClass.name + 'Impl.java'
		
			val cFile = new File(cPath)
			
			val cCode = 
				if(cFile.exists) {
					cFile.loadFile
				} else null
			
			return #[iCode, cCode]
		} else return null
	}
	
	final def void saveInterfaceCode(EcoreLanguage language, EClass eClass, String code) {
		val iPath = language.sourceInterfaceFolder + '/' + eClass.name + '.java'
		val iFile = new File(iPath)
		
		if(iFile.exists) {
			iFile.saveFile(code)
		}
	}
	
	final def void saveClassCode(EcoreLanguage language, EClass eClass, String code) {
		val iPath = language.sourceClassFolder + '/' + eClass.name + 'Impl.java'
		val iFile = new File(iPath)
		
		if(iFile.exists) {
			iFile.saveFile(code)
		}
	}
}