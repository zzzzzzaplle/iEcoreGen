/**
 * Copyright (c) 2015, 2021 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package edu.ustb.sei.mde.mwe2;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.xtext.XtextStandaloneSetup;
import org.eclipse.xtext.util.MergeableManifest2;
import org.eclipse.xtext.util.Triple;
import org.eclipse.xtext.util.Tuples;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xtext.generator.CodeConfig;
import org.eclipse.xtext.xtext.generator.DefaultGeneratorModule;
import org.eclipse.xtext.xtext.generator.XtextDirectoryCleaner;
import org.eclipse.xtext.xtext.generator.XtextGeneratorStandaloneSetup;
import org.eclipse.xtext.xtext.generator.model.IXtextGeneratorFileSystemAccess;
import org.eclipse.xtext.xtext.generator.model.ManifestAccess;
import org.eclipse.xtext.xtext.generator.model.PluginXmlAccess;
import org.eclipse.xtext.xtext.generator.model.project.BundleProjectConfig;
import org.eclipse.xtext.xtext.generator.model.project.IXtextProjectConfig;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
//import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * The Xtext language infrastructure generator. Use the {@code configuration}
 * block to add general configuration for your Xtext project and the generated
 * code, e.g.
 * 
 * <pre>
 * configuration = {
 *     project = model.project.StandardProjectConfig {
 *         baseName = "org.example.language"
 *         rootPath = ".."
 *     }
 *     code = {
 *         encoding = 'ISO-8859-1'
 *     }
 * }
 * </pre>
 * 
 * You can generate code for one or more Xtext languages within the same
 * project. For each language, add a {@code language} block, e.g.
 * 
 * <pre>
 * language = StandardLanguage {
 *     name = "org.example.language.MyExampleLanguage"
 * }
 * </pre>
 * 
 * @noextend This class should not be extended by clients.
 */
public class EcoreGenerator extends AbstractWorkflowComponent2 {
	private static final Logger LOG = Logger.getLogger(EcoreGenerator.class);

	private DefaultGeneratorModule configuration = new DefaultGeneratorModule();

	private final List<EcoreLanguage> languageConfigs = new ArrayList<>();

	private XtextDirectoryCleaner cleaner = new XtextDirectoryCleaner();

	private XtextGeneratorStandaloneSetup standaloneSetup = new XtextGeneratorStandaloneSetup();

//	private String grammarEncoding;

	private Injector injector;

	@Inject
	private IXtextProjectConfig projectConfig;

//	@Inject
//	private XtextGeneratorTemplates templates;

//	@Inject
//	private XtextGeneratorNaming naming;

	@Inject
	private CodeConfig codeConfig;
	
	private Class<? extends LanguageModule> languageModule = LanguageModule.class;
	
	
	public DefaultGeneratorModule getConfiguration() {
		return configuration;
	}

	public void setConfiguration(DefaultGeneratorModule configuration) {
		this.configuration = configuration;
	}

	public List<EcoreLanguage> getLanguageConfigs() {
		return languageConfigs;
	}

	public XtextDirectoryCleaner getCleaner() {
		return cleaner;
	}

	public void setCleaner(XtextDirectoryCleaner cleaner) {
		this.cleaner = cleaner;
	}
	
	private ModuleParameters moduleParameters;
	
	public void setModuleParameters(ModuleParameters moduleParameters) {
		this.moduleParameters = moduleParameters;
	}
	
	public ModuleParameters getModuleParameters() {
		return moduleParameters;
	}

//	public XtextGeneratorStandaloneSetup getStandaloneSetup() {
//		return standaloneSetup;
//	}
//
//	public void setStandaloneSetup(XtextGeneratorStandaloneSetup standaloneSetup) {
//		this.standaloneSetup = standaloneSetup;
//	}
//
//	public String getGrammarEncoding() {
//		return grammarEncoding;
//	}
//
//	public void setGrammarEncoding(String grammarEncoding) {
//		this.grammarEncoding = grammarEncoding;
//	}

	public EcoreGenerator() {
		// FIXME: check if it is useless
		new XtextStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	/**
	 * Add a language configuration to be included in the code generation
	 * process.
	 */
	public void addLanguage(final EcoreLanguage language) {
		languageConfigs.add(language);
	}

	@Override
	protected void checkConfigurationInternal(Issues issues) {
		initialize();
	}

	public void initialize() {
		if (injector == null) {
			EcoreGenerator.LOG.info("Initializing Xtext generator");
//			new StandaloneSetup().addRegisterGeneratedEPackage("org.eclipse.xtext.common.types.TypesPackage");
//			initializeEncoding();
			injector = createInjector();
			injector.injectMembers(this);
			injector.getInstance(CodeConfig.class).initialize(injector);
			projectConfig.initialize(injector);
			cleaner.initialize(injector);
			standaloneSetup.initialize(injector);
			for (EcoreLanguage language : languageConfigs) {
				Injector languageInjector = createLanguageInjector(injector, language);
				language.initialize(languageInjector);
			}
		}
	}


	protected Injector createInjector() {
		return Guice.createInjector(configuration);
	}

	protected Injector createLanguageInjector(Injector parent, EcoreLanguage language) {
		LanguageModule module = createModule(language);
		module.setParameters(moduleParameters);
		Injector child = parent.createChildInjector(module);
		module.injectParameters(child);
		return child;
	}

	protected LanguageModule createModule(EcoreLanguage language) throws RuntimeException {
		try {
			LanguageModule module = languageModule.getConstructor(EcoreLanguage.class).newInstance(language);
			return module;			
		} catch (Exception e) {
			throw new RuntimeException("Cannot create language module", e);
		}
	}

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues) {
		initialize();
		try {
			cleaner.clean();
			for (EcoreLanguage language : languageConfigs) {
				try {
					EcoreGenerator.LOG.info("Generating " + language);
					language.generate();
				} catch (Exception e) {
					handleException(e, issues);
				}
			}
			EcoreGenerator.LOG.info("Generating common infrastructure");
			generatePluginXmls();
			generateManifests();
		} catch (Exception e) {
			handleException(e, issues);
		}
	}

	private void handleException(Exception ex, Issues issues) {
		System.out.println(ex);
	}

	protected void generateManifests() {
		try {
			// Filter null values and merge duplicate entries
			Iterable<BundleProjectConfig> bundleProjects = Iterables.filter(projectConfig.getEnabledProjects(),
					BundleProjectConfig.class);
			List<Triple<ManifestAccess, IXtextGeneratorFileSystemAccess, String>> manifests = IterableExtensions
					.toList(Iterables.transform(bundleProjects, (BundleProjectConfig it) -> {
						return Tuples.create(it.getManifest(), it.getMetaInf(), it.getName());
					}));
			HashMap<URI, ManifestAccess> uri2Manifest = Maps.newHashMapWithExpectedSize(manifests.size());
			ListIterator<Triple<ManifestAccess, IXtextGeneratorFileSystemAccess, String>> manifestIter = manifests
					.listIterator();
			while (manifestIter.hasNext()) {
				Triple<ManifestAccess, IXtextGeneratorFileSystemAccess, String> entry = manifestIter.next();
				ManifestAccess manifest = entry.getFirst();
				IXtextGeneratorFileSystemAccess metaInf = entry.getSecond();
				if (manifest == null || metaInf == null) {
					manifestIter.remove();
				} else {
//					if (manifest.getActivator() == null && manifest == projectConfig.getEclipsePlugin().getManifest()) {
//						manifest.setActivator(naming.getEclipsePluginActivator());
//					}
					URI uri = metaInf.getURI(manifest.getPath());
					if (uri2Manifest.containsKey(uri)) {
						uri2Manifest.get(uri).merge(manifest);
						manifestIter.remove();
					} else {
						uri2Manifest.put(uri, manifest);
					}
				}
			}
			for (Triple<ManifestAccess, IXtextGeneratorFileSystemAccess, String> entry : manifests) {
				ManifestAccess manifest = entry.getFirst();
				IXtextGeneratorFileSystemAccess metaInf = entry.getSecond();
				if (manifest.getBundleName() == null) {
					manifest.setBundleName(entry.getThird());
				}
				if (metaInf.isFile(manifest.getPath())) {
					if (manifest.isMerge()) {
						mergeManifest(manifest, metaInf);
					} else {
						if (manifest.getPath().endsWith(".MF")) {
							manifest.setPath(manifest.getPath() + "_gen");
							manifest.writeTo(metaInf);
						}
					}
				} else {
					manifest.writeTo(metaInf);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	protected void mergeManifest(ManifestAccess manifest, IXtextGeneratorFileSystemAccess metaInf) throws IOException {
		InputStream in = null;
		try {
			in = metaInf.readBinaryFile(manifest.getPath());
			MergeableManifest2 merge = new MergeableManifest2(in, manifest.getBundleName());
			merge.setLineDelimiter(codeConfig.getLineDelimiter());
			merge.addExportedPackages(manifest.getExportedPackages());
			merge.addRequiredBundles(manifest.getRequiredBundles());
			merge.addImportedPackages(manifest.getImportedPackages());
			if (manifest.getActivator() != null && Strings.isNullOrEmpty(merge.getBundleActivator())) {
				merge.setBundleActivator(manifest.getActivator().getName());
			}
			if (merge.isModified()) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				merge.write(out);
				metaInf.generateFile(manifest.getPath(), new ByteArrayInputStream(out.toByteArray()));
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	protected void generatePluginXmls() {
		// Filter null values and merge duplicate entries
		Iterable<BundleProjectConfig> bundleProjects = Iterables.filter(projectConfig.getEnabledProjects(),
				BundleProjectConfig.class);
		List<Pair<PluginXmlAccess, IXtextGeneratorFileSystemAccess>> pluginXmls = IterableExtensions
				.toList(Iterables.transform(bundleProjects, (BundleProjectConfig it) -> {
					return Pair.of(it.getPluginXml(), it.getRoot());
				}));
		HashMap<URI, PluginXmlAccess> uri2PluginXml = Maps
				.newHashMapWithExpectedSize(pluginXmls.size());
		ListIterator<Pair<PluginXmlAccess, IXtextGeneratorFileSystemAccess>> pluginXmlIter = pluginXmls.listIterator();
		while (pluginXmlIter.hasNext()) {
			Pair<PluginXmlAccess, IXtextGeneratorFileSystemAccess> entry = pluginXmlIter.next();
			PluginXmlAccess pluginXml = entry.getKey();
			IXtextGeneratorFileSystemAccess root = entry.getValue();
			if (pluginXml == null || root == null) {
				pluginXmlIter.remove();
			} else {
				URI uri = root.getURI(pluginXml.getPath());
				if (uri2PluginXml.containsKey(uri)) {
					uri2PluginXml.get(uri).merge(pluginXml);
					pluginXmlIter.remove();
				} else {
					uri2PluginXml.put(uri, pluginXml);
				}
			}
		}
		for (Pair<PluginXmlAccess, IXtextGeneratorFileSystemAccess> entry : pluginXmls) {
			PluginXmlAccess pluginXml = entry.getKey();
			IXtextGeneratorFileSystemAccess root = entry.getValue();
			if (root.isFile(pluginXml.getPath())) {
				// only write plugin.xml_gen if entries exist and content
				// differs
				if (!pluginXml.getEntries().isEmpty()) {
					String textFileContent = null;
					CharSequence textFile = root.readTextFile(pluginXml.getPath());
					if (textFile != null) {
						textFileContent = textFile.toString();
					}
					if (!Objects.equals(textFileContent, pluginXml.getContentString())) {
						if (pluginXml.getPath().endsWith(".xml")) {
							pluginXml.setPath(pluginXml.getPath() + "_gen");
							pluginXml.writeTo(root);
						}
					}
				}
			} else {
				pluginXml.writeTo(root);
			}
		}
	}

	public Class<? extends LanguageModule> getLanguageModule() {
		return languageModule;
	}

	public void setLanguageModuleClass(Class<? extends LanguageModule> languageModule) {
		this.languageModule = languageModule;
	}
	
	@SuppressWarnings("unchecked")
	public void setLanguageModule(String languageModuleClass) {
		try {
			this.languageModule = (Class<? extends LanguageModule>) this.getClass().getClassLoader().loadClass(languageModuleClass);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
