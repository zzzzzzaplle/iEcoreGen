package edu.ustb.sei.mde.eecg.facade;

import org.eclipse.emf.mwe2.language.Mwe2StandaloneSetup;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CustomizedMwe2StandaloneSetup extends Mwe2StandaloneSetup {
	public CustomizedMwe2StandaloneSetup(ClassLoader specifiedClassLoader) {
		super();
		this.specifiedClassLoader = specifiedClassLoader;
	}
	private ClassLoader specifiedClassLoader;
	
	public Injector createInjector() {
		return Guice.createInjector(new CustomizedMwe2RuntimeModule(specifiedClassLoader));
	}
}
