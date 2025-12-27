package edu.ustb.sei.mde.eecg.llm.languageModule

import com.google.inject.Binder
import edu.ustb.sei.mde.eecg.llm.enhancement.CodeCompletionWithoutCompression
import edu.ustb.sei.mde.eecg.llm.enhancement.CodeFixingWithoutCompression
import edu.ustb.sei.mde.eecg.llm.enhancement.EOperationAnnotator
import edu.ustb.sei.mde.mwe2.EcoreLanguage
import edu.ustb.sei.mde.mwe2.enhancement.CodewareEnhancement
import edu.ustb.sei.mde.mwe2.enhancement.CompoundCodeEnhancement
import edu.ustb.sei.mde.mwe2.enhancement.CompoundEcoreModelEnhancement
import edu.ustb.sei.mde.mwe2.enhancement.EcoreModelEnhancement

class EnhancedEcoreGenModuleWithoutCompression extends AbstractAI4EcoreLanguageModule {
	
	new(EcoreLanguage language) {
		super(language)
	}
	
	override setupEcoreModelEnhancement(Binder binder) {
		binder.bind(EcoreModelEnhancement).toInstance(
			new CompoundEcoreModelEnhancement(
				new EOperationAnnotator(llmPort)
			)
		)
	}
	
	override setupGenModelEnhancement(Binder binder) {
	}
	
	override setupCodeEnhancement(Binder binder) {
		binder.bind(CodewareEnhancement).toInstance(
			new CompoundCodeEnhancement(
				new CodeCompletionWithoutCompression(llmPort),
				new CodeFixingWithoutCompression(llmPort)
			)
		)
	}
	
}