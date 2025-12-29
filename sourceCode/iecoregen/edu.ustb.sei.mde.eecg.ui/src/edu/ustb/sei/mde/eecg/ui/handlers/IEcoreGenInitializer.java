package edu.ustb.sei.mde.eecg.ui.handlers;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import edu.ustb.sei.mde.eecg.facade.WorkflowInitializer;
import edu.ustb.sei.mde.eecg.ui.Activator;
import edu.ustb.sei.mde.eecg.ui.wizard.MWEConfigurationWizard;
import jakarta.inject.Named;

/** <b>Warning</b> : 
  As explained in <a href="http://wiki.eclipse.org/Eclipse4/RCP/FAQ#Why_aren.27t_my_handler_fields_being_re-injected.3F">this wiki page</a>, it is not recommended to define @Inject fields in a handler. <br/><br/>
  <b>Inject the values in the @Execute methods</b>
*/
public class IEcoreGenInitializer {
	
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell s) {
		IResource selectedResource = getSelectedResource();
        
        if (selectedResource == null) {
            MessageDialog.openInformation(s, "Info", 
                "Please select an .ecore file in the Project Explorer or open it in the editor.");
            return;
        }
        
        // 检查是否为.ecore文件
        if (!(selectedResource instanceof IFile) || 
            !selectedResource.getName().endsWith(".ecore")) {
            MessageDialog.openInformation(s, "Info", 
                "Please select an .ecore file. The selected resource is not an Ecore file.");
            return;
        }
        
        IFile ecoreFile = (IFile) selectedResource;
        IProject project = ecoreFile.getProject();
        
        String ecorePath = ecoreFile.getLocation().toFile().getAbsolutePath();
        String projectPath = project.getLocation().toFile().getAbsolutePath();
        
        String pluginAbsolutePath = getEclipsePluginDirectory().getAbsolutePath();
        
        MWEConfigurationWizard wizard = new MWEConfigurationWizard(Map.of("ecoreFile", ecorePath, "project", projectPath, "sourceFolder", "src", "classpathFolder", pluginAbsolutePath));
        WizardDialog dialog = new WizardDialog(s, wizard);
        dialog.setPageSize(500, 350);
        
        if (dialog.open() == Window.OK) {
        	Map<String, String> config = wizard.getConfiguration();
        	try {
        		initializeWorkflow(config);
        		MessageDialog.openInformation(s, "Info", "Operation succeeded");
        	} catch (Exception e) {
        		MessageDialog.openInformation(s, "Info", "Operation failed");
			}
        }
	}


	protected void initializeWorkflow(Map<String, String> config) {
		String ecorePath;
		String projectPath;
		
		WorkflowInitializer initializer = new WorkflowInitializer();
		
		String src = config.get("sourceFolder");
		String mwe = config.get("mweFileName");
		ecorePath = config.get("ecoreFile");
		projectPath = config.get("project");
		File project = new File(projectPath);
		String root = project.getParent();
		String proj = project.getName();
		String base = config.get("basePackage");
		String classpath = config.get("classpathFolder");
		boolean force = Boolean.parseBoolean(config.get("overwrite"));
		
		ResourceSet resourceSet = Activator.getResourceSet();
		URI uri = URI.createFileURI(ecorePath);
		Resource ecoreRes = resourceSet.getResource(uri, true);
		
		String mwePath = initializer.generateMWEFile(src, mwe, ecoreRes, root, proj, base, classpath, force);
		initializer.generateConfFile(src, root, proj, force);
		
		if(mwePath != null)
			initializer.saveLastMweToEcore(ecoreRes, new File(mwePath));
		
		ecoreRes.unload();
	}


	private File getEclipsePluginDirectory() {
        try {
            // 1. 获取Eclipse平台安装目录的URL
            URL installUrl = Platform.getInstallLocation().getURL();
            
            // 2. 将URL转换为本地文件路径
            // 注意：URL.getPath()在Windows下会返回如“/F:/eclipse/”的路径，需要处理开头的斜杠
            String installPath = new File(installUrl.getPath()).getAbsolutePath();
            
            // 3. 拼接“plugins”子目录
            File pluginDir = new File(installPath, "plugins");
            
            // 4. 验证目录是否存在
            if (pluginDir.exists() && pluginDir.isDirectory()) {
                return pluginDir;
            } else {
                // 在某些配置（如OSGi运行时）或产品化RCP应用中，plugins可能在其他位置
                System.err.println("No plugin folder: " + pluginDir.getAbsolutePath());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	private IResource getSelectedResource() {
        // 方式1: 从当前选择中获取（Project Explorer中的选择）
        IResource resourceFromSelection = getResourceFromSelectionService();
        if (resourceFromSelection != null) {
            return resourceFromSelection;
        }
        
        // 方式2: 从当前编辑器中获取
        IResource resourceFromEditor = getResourceFromActiveEditor();
        if (resourceFromEditor != null) {
            return resourceFromEditor;
        }
        
        return null;
    }
	
    private IResource getResourceFromSelectionService() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window == null) {
            return null;
        }
        
        ISelectionService selectionService = window.getSelectionService();
        ISelection selection = selectionService.getSelection();
        
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            
            if (!structuredSelection.isEmpty()) {
                Object firstElement = structuredSelection.getFirstElement();
                IResource resource = Adapters.adapt(firstElement, IResource.class);
                return resource;
            }
        }
        
        return null;
    }
    
    private IResource getResourceFromActiveEditor() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window == null) {
            return null;
        }
        
        IWorkbenchPage page = window.getActivePage();
        if (page == null) {
            return null;
        }
        
        IEditorPart editor = page.getActiveEditor();
        if (editor != null && editor.getEditorInput() instanceof IFileEditorInput) {
            IFileEditorInput fileInput = (IFileEditorInput) editor.getEditorInput();
            return fileInput.getFile();
        }
        
        return null;
    }
}
