package edu.ustb.sei.mde.mwe2;

import org.eclipse.xtext.service.AbstractGenericModule;

import com.google.inject.Binder;
import com.google.inject.Injector;

public class LanguageModule extends AbstractGenericModule {
	protected final EcoreLanguage language;
	
	
	protected ModuleParameters parameters;
	
	public ModuleParameters getParameters() {
		return parameters;
	}
	
	public void setParameters(ModuleParameters parameters) {
		this.parameters = parameters;
	}

	public void configureLanguage(Binder binder) {
		binder.bind(EcoreLanguage.class).toProvider(() -> language);
		if(parameters != null) {
			parameters.configure(binder);
		}
	}
	
	public void injectParameters(Injector injector) {
		if(parameters != null) {
			parameters.inject(injector);
		}
	}


	public LanguageModule(EcoreLanguage language) {
		this.language = language;
	}
}
