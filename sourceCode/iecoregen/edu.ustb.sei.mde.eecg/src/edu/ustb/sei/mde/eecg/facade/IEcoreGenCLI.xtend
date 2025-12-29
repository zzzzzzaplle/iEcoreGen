package edu.ustb.sei.mde.eecg.facade

import org.apache.commons.cli.Options
import org.apache.commons.cli.Option
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.CommandLine
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.mwe2.launch.runtime.Mwe2Launcher

class IEcoreGenCLI {
	def static Options buildOpinions() {
		new Options() => [
			{
				val mode = new Option("init", "initialize", true, "initialize mwe (without extension) and conf.properties");
				mode.setRequired(false); // 设置为必需参数
	        	it.addOption(mode);
				
				val src = new Option("s", "src", true, "relative path to the source folder")
				src.required = false
				it.addOption(src)
				
				val ecore = new Option("e", "ecore", true, "full path name of ecore file")
				ecore.required = false
				it.addOption(ecore)
				
				val root = new Option("r", "root", true, "absolute or relative path to root path which is the parent of the project")
				root.required = false
				it.addOption(root)
				
				val proj = new Option("p", "project", true, "name of this project")
				proj.required = false
				it.addOption(proj)
				
				val pkg = new Option("b", "basePackage", true, "name of the base package")
				pkg.required = false
				it.addOption(pkg)
				
				val cpath = new Option("c", "classpath", true, "folder that contains necessary libraries")
				cpath.required = false
				it.addOption(cpath)
				
				val force = new Option("f", "force", false, "force to write")
				force.required = false
				it.addOption(force)
			}
        	
			{
				val mode = new Option("gen", "generate", true, "generate based on mwe");
				mode.setRequired(false); // 设置为必需参数
	        	it.addOption(mode);
			}
		]
	}
	
	def static void main(String[] args) {
		val options = buildOpinions
		
		val parser = new DefaultParser
		val formatter = new HelpFormatter
		
		try {
			val cmd = parser.parse(options, args)
			
			var mwe = ''
			
			mwe = cmd.getOptionValue('initialize')
			if(mwe !== null) {
				doInit(mwe, cmd)
			} else {
				mwe = cmd.getOptionValue('generate')
				if(mwe !== null) {
					doGenerate('file://'+mwe.replaceAll('\\\\', '/'))
				} else 
					throw new RuntimeException('No command is specified')
			}
			
			
			
		} catch(Exception e) {
			System.out.println("Command error: " + e.getMessage());
    		formatter.printHelp("iEcoreGenCLI", options);
    		System.exit(1);
		}
	}
	
	def static doGenerate(String mwe) {
		val launcher = new Mwe2Launcher()
		launcher.run(#[mwe])
	}
	
	def static doInit(String mwe, CommandLine cmd) {
		val ecoreFile = cmd.getOptionValue('ecore')
		val ecoreRes = 
			if(ecoreFile === null) {
				throw new RuntimeException('No ecore file is specified')
			} else {
				val resourceSet = new ResourceSetImpl();
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				    Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
				val uri = URI.createFileURI(ecoreFile)
				resourceSet.getResource(uri, true)
			}
		
		val src = cmd.getOptionValue('src')
		if(src === null) {
			throw new RuntimeException('No source folder is specified')
		}
		
		val root = cmd.getOptionValue('root')
		if(root === null) {
			throw new RuntimeException('No root folder is specified')
		}
		
		val proj = cmd.getOptionValue('project')
		if(proj === null) {
			throw new RuntimeException('No project folder is specified')
		}
		
		val base = cmd.getOptionValue('basePackage')
		if(base === null) {
			throw new RuntimeException('No base package name is specified')
		}
		
		val classpath = cmd.getOptionValue('classpath')
		if(classpath === null) {
			throw new RuntimeException('No classpath is specified')
		}
		
		val force = cmd.hasOption('force')
		
		val initializer = new WorkflowInitializer
		
		initializer.generateMWEFile(src, mwe, ecoreRes, root, proj, base, classpath, force)
		initializer.generateConfFile(src, root, proj, force)
	}
	
}