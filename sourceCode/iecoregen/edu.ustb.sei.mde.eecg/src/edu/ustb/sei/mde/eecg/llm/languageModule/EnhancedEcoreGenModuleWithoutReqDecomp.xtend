package edu.ustb.sei.mde.eecg.llm.languageModule

import com.google.inject.Binder
import edu.ustb.sei.mde.eecg.llm.enhancement.CodeCompletionWithoutMethodSpec
import edu.ustb.sei.mde.eecg.llm.enhancement.CodeFixing
import edu.ustb.sei.mde.eecg.llm.enhancement.EOperationMarker
import edu.ustb.sei.mde.mwe2.EcoreLanguage
import edu.ustb.sei.mde.mwe2.enhancement.CodewareEnhancement
import edu.ustb.sei.mde.mwe2.enhancement.CompoundCodeEnhancement
import edu.ustb.sei.mde.mwe2.enhancement.CompoundEcoreModelEnhancement
import edu.ustb.sei.mde.mwe2.enhancement.EcoreModelEnhancement

class EnhancedEcoreGenModuleWithoutReqDecomp extends AbstractAI4EcoreLanguageModule {
	
	new(EcoreLanguage language) {
		super(language)
	}
	
	override setupEcoreModelEnhancement(Binder binder) {
		binder.bind(EcoreModelEnhancement).toInstance(
			new CompoundEcoreModelEnhancement(
				new EOperationMarker(llmPort)
			)
		)
	}
	
	override setupGenModelEnhancement(Binder binder) {
	}
	
	override setupCodeEnhancement(Binder binder) {
		binder.bind(CodewareEnhancement).toInstance(
			new CompoundCodeEnhancement(
				new CodeCompletionWithoutMethodSpec(llmPort),
				new CodeFixing(llmPort)
			)
		)
	}
	
}