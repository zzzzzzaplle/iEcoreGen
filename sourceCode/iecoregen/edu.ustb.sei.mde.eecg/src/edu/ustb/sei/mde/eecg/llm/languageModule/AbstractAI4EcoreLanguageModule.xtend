package edu.ustb.sei.mde.eecg.llm.languageModule

import edu.ustb.sei.mde.mwe2.LanguageModule
import edu.ustb.sei.mde.mwe2.EcoreLanguage
import com.google.inject.Binder
import java.util.Properties
import java.io.File
import java.io.FileReader
import java.nio.charset.Charset
import edu.ustb.sei.ai.LLMPort
import edu.ustb.sei.ai.spring.port.OpenAIPort

abstract class AbstractAI4EcoreLanguageModule extends LanguageModule {
	new(EcoreLanguage language) {
		super(language)
		
		this.llmPort = createDeepSeekPort()
	}
	
	protected val LLMPort llmPort
	
	static val default_url = 'https://api.deepseek.com/'
	static val default_model = 'deepseek-chat'
	static val default_timeout = '600'
	
	def LLMPort createDeepSeekPort() {
		val confPath = language.llmConfig
		
		val file = new File(confPath)
		
		val reader = new FileReader(file, Charset.forName("utf8"))
		
		val properties = new Properties
		
		properties.load(reader)
		
		val model = properties.getOrDefault('model', default_model) as String
		val url = properties.getOrDefault('url', default_url) as String
		val timeout = Integer.parseInt(properties.getOrDefault('timeout', default_timeout) as String)
		val apikey = properties.get('apikey') as String
		
		return new OpenAIPort(apikey,
			new LLMPort.Options().setUrl(url).setModel(model).setTimeout(timeout)
		)
	}
	
	def void setupEcoreModelEnhancement(Binder binder)
	def void setupGenModelEnhancement(Binder binder)
	def void setupCodeEnhancement(Binder binder)
	
	
	override configureLanguage(Binder binder) {
		super.configureLanguage(binder)
		
		binder.bind(LLMPort).toInstance(llmPort)
		
		if(parameters === null) {
			binder.setupEcoreModelEnhancement
			binder.setupGenModelEnhancement
			binder.setupCodeEnhancement
		}
		
	}
}