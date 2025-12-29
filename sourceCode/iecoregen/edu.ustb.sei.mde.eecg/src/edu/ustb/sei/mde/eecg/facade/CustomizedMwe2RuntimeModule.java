package edu.ustb.sei.mde.eecg.facade;

import org.eclipse.emf.mwe2.language.Mwe2RuntimeModule;

public class CustomizedMwe2RuntimeModule extends Mwe2RuntimeModule {
	public CustomizedMwe2RuntimeModule(ClassLoader specifiedClassLoader) {
		super();
		this.specifiedClassLoader = specifiedClassLoader;
	}
	private ClassLoader specifiedClassLoader;
	
	public java.lang.ClassLoader bindClassLoaderToInstance() {
		return specifiedClassLoader;
	}
}
