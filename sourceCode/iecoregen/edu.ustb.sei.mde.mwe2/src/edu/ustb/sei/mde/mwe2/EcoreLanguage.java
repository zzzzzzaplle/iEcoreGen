package edu.ustb.sei.mde.mwe2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.xtext.generator.CompositeGeneratorFragment2;
import org.eclipse.xtext.xtext.generator.IXtextGeneratorFragment;
import org.eclipse.xtext.xtext.generator.XtextGeneratorResourceSetInitializer;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.google.inject.Injector;

import edu.ustb.sei.mde.mwe2.enhancement.CodewareEnhancement;
import edu.ustb.sei.mde.mwe2.enhancement.EcoreModelEnhancement;
import edu.ustb.sei.mde.mwe2.enhancement.GenModelEnhancement;

public class EcoreLanguage extends CompositeGeneratorFragment2 {
	private static final Logger LOG = Logger.getLogger(EcoreLanguage.class);
	
//	private String name;
	
	private List<String> fileExtensions;
	
	private String ecoreFile;
	
	private String basePackage;

	private EMFGeneratorFragment2 emfGenerator = new EMFGeneratorFragment2();

	private List<String> referencedResources = new ArrayList<>();

	private ResourceSet resourceSet;
	
	private boolean suppressInterfaces = false;
	
	private String llmConfig; // can we move it into EcoreGenerator?
	
	private List<String> classpaths = new ArrayList<>();
	
	public void addClasspath(String path) {
		classpaths.add(path);
	}
	
	public List<String> getClasspaths() {
		return classpaths;
	}
 	
//	@Inject
//	private Provider<ResourceSet> resourceSetProvider;
//	
//	@Inject
//	private IXtextProjectConfig projectConfig;
//
//	@Inject
//	private CodeConfig codeConfig;

	@Inject
	private XtextGeneratorResourceSetInitializer resourceSetInitializer;
	

	public EcoreLanguage() {
		try {
			getClass().getClassLoader().loadClass("org.eclipse.xtext.xbase.XbaseRuntimeModule");
//			addReferencedResource("platform:/resource/org.eclipse.xtext.xbase/model/Xbase.genmodel");
		} catch (ClassNotFoundException e) {
			EcoreLanguage.LOG.info("Skipping registration of Xbase genmodel. Xbase is not on the classpath.");
		}
	}
	
	public void addReferencedResource(String referencedResource) {
		referencedResources.add(referencedResource);
	}

	protected List<? extends IXtextGeneratorFragment> getImplicitFragments() {
		List<IXtextGeneratorFragment> fragments = new ArrayList<>();
		nullSafeAdd(fragments, emfGenerator);
		return fragments;
	}

	private boolean nullSafeAdd(List<IXtextGeneratorFragment> list, IXtextGeneratorFragment fragment) {
		if (fragment != null) {
			return list.add(fragment);
		}
		return false;
	}

	protected EMFGeneratorFragment2 getEmfGenerator() {
		return emfGenerator;
	}

	public void setEmfGenerator(EMFGeneratorFragment2 emfGenerator) {
		this.emfGenerator = emfGenerator;
	}
	
	public List<String> getReferencedResources() {
		return referencedResources;
	}

	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	public void setResourceSet(ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}
	
	public void initialize(Injector injector) {
		getFragments().addAll(0, getImplicitFragments());
		injector.injectMembers(this);
		if (resourceSet == null) {
//			resourceSet = resourceSetProvider.get();
			resourceSet = new ResourceSetImpl();
			resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
			resourceSet.getPackageRegistry().put(GenModelPackage.eNS_URI, GenModelPackage.eINSTANCE);
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
			
		}
		resourceSetInitializer.initialize(resourceSet, referencedResources);
		if (!resourceSet.getResources().isEmpty()) {
			int i = 0;
			int size = resourceSet.getResources().size();
			while (i < size) {
				Resource res = resourceSet.getResources().get(i);
				if (res.getContents().isEmpty()) {
					LOG.error("Error loading '" + res.getURI() + "'");
				} else {
					if (!res.getErrors().isEmpty()) {
						LOG.error("Error loading '" + res.getURI() + "':\n" + Joiner.on("\n").join(res.getErrors()));
					}
				}
				i++;
			}
			EcoreUtil.resolveAll(resourceSet);
		}
		loadEcoreModel();
		
		if(ecoreEnhancer != null) ecoreEnhancer.inject(injector);
		if(genmodelEnhancer != null) genmodelEnhancer.inject(injector);
		if(codeEnhancer != null) codeEnhancer.inject(injector);
		
		super.initialize(injector);
	}
	
	protected void loadEcoreModel() {
		Resource res = this.resourceSet.getResource(URI.createURI(this.ecoreFile, false), true);
		ecorePackage = (EPackage) res.getContents().get(0);
	}
	
	private EPackage ecorePackage;
	public EPackage getEcorePackage() {
		return ecorePackage;
	}
	
	public GenModel getGenModel() {
		for(Resource res : this.resourceSet.getResources()) {
			if(res.getURI().fileExtension().equals("genmodel")) {
				GenModel gm = (GenModel) res.getContents().get(0);
				if(gm.getGenPackages().stream().anyMatch(gp->gp.getEcorePackage()==getEcorePackage()))
					return gm;
			}
		}
		return null;
	}
	
	public String getModelName() {
		return this.getEcorePackage().getName();
	}

//	/**
//	 * The language name exactly as specified in the grammar.
//	 */
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	/**
//	 * @since 2.26
//	 */
//	public final String getName() {
//		return this.name;
//	}
	
	/**
	 * Either a single file extension or a comma-separated list of extensions
	 * for which the language shall be registered.
	 */
	public void setFileExtensions(String fileExtensions) {
		this.fileExtensions = Arrays.asList(fileExtensions.trim().split("\\s*,\\s*"));
	}
	
	public List<String> getFileExtensions() {
		if (fileExtensions == null || fileExtensions.isEmpty()) {
			LOG.info("No explicit fileExtensions configured. Using '*." + fileExtensions + "'.");
		}
		return fileExtensions;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getEcoreFile() {
		return ecoreFile;
	}

	public void setEcoreFile(String ecoreFile) {
		this.ecoreFile = ecoreFile;
	}
	
	@Inject(optional = true)
	private EcoreModelEnhancement ecoreEnhancer;
	
	@Inject(optional = true)
	private GenModelEnhancement genmodelEnhancer;
	
	@Inject(optional = true)
	private CodewareEnhancement codeEnhancer;

	public EcoreModelEnhancement getEcoreEnhancer() {
		return ecoreEnhancer;
	}

	public void setEcoreEnhancer(EcoreModelEnhancement ecoreEnhancer) {
		this.ecoreEnhancer = ecoreEnhancer;
	}

	public GenModelEnhancement getGenmodelEnhancer() {
		return genmodelEnhancer;
	}

	public void setGenmodelEnhancer(GenModelEnhancement genmodelEnhancer) {
		this.genmodelEnhancer = genmodelEnhancer;
	}

	public CodewareEnhancement getCodeEnhancer() {
		return codeEnhancer;
	}

	public void setCodeEnhancer(CodewareEnhancement codeEnhancer) {
		this.codeEnhancer = codeEnhancer;
	}

	public boolean isSuppressInterfaces() {
		return suppressInterfaces;
	}

	public void setSuppressInterfaces(boolean suppressInterfaces) {
		this.suppressInterfaces = suppressInterfaces;
	}

	public String getLlmConfig() {
		return llmConfig;
	}

	public void setLlmConfig(String llmConfig) {
		this.llmConfig = llmConfig;
	}
}
