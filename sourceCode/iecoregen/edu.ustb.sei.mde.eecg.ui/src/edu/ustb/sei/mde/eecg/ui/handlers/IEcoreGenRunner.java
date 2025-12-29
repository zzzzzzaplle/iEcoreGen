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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.mwe2.launch.runtime.Mwe2Launcher;
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
import org.eclipse.xtext.linking.impl.DefaultLinkingService;

import edu.ustb.sei.mde.eecg.facade.CustomizedMwe2Launcher;
import edu.ustb.sei.mde.eecg.facade.WorkflowInitializer;
import edu.ustb.sei.mde.eecg.ui.Activator;
import edu.ustb.sei.mde.eecg.ui.dialog.EECGTaskDialog;
import edu.ustb.sei.mde.eecg.ui.dialog.EECGTaskDialog.TaskRunnable;
import edu.ustb.sei.mde.eecg.ui.wizard.MWEConfigurationWizard;
import edu.ustb.sei.mde.mwe2.EcoreGenerator;
import jakarta.inject.Named;

/** <b>Warning</b> : 
  As explained in <a href="http://wiki.eclipse.org/Eclipse4/RCP/FAQ#Why_aren.27t_my_handler_fields_being_re-injected.3F">this wiki page</a>, it is not recommended to define @Inject fields in a handler. <br/><br/>
  <b>Inject the values in the @Execute methods</b>
*/
@SuppressWarnings("unused")
public class IEcoreGenRunner {
	
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell s) {
		IResource selectedResource = getSelectedResource();
        
        if (selectedResource == null) {
            MessageDialog.openInformation(s, "Info", 
                "Please select an .mwe2 or .ecore file in the Project Explorer or open it in the editor.");
            return;
        }
        
        if (!(selectedResource instanceof IFile) || 
            (!selectedResource.getName().endsWith(".mwe2") && !selectedResource.getName().endsWith(".ecore"))) {
            MessageDialog.openInformation(s, "Info", 
                "Please select an .mwe2 or .ecore file. The selected resource is not an mwe2 nor an ecore file.");
            return;
        }
        
        if(selectedResource instanceof IFile) {
        	String name = selectedResource.getName();
        	if(name.endsWith(".mwe2")) {
        		IFile mwe2File = (IFile) selectedResource;
                String filePath = mwe2File.getLocation().toFile().getAbsolutePath();
				runWorkflow(s, filePath);
				return;
        	} else if(name.endsWith(".ecore")) {
        		IFile ecoreFile = (IFile) selectedResource;
                String ecorePath = ecoreFile.getLocation().toFile().getAbsolutePath();
                
                ResourceSet resourceSet = Activator.getResourceSet();
        		URI uri = URI.createFileURI(ecorePath);
        		
        		Resource ecoreRes = resourceSet.getResource(uri, true);
        		EPackage pkg = (EPackage) ecoreRes.getContents().get(0);
        		
        		String mwePath = EcoreUtil.getAnnotation(pkg, "workflow", "last");
        		if(mwePath == null) {
        			MessageDialog.openInformation(s, "Info", "No workflow file is stored. Run initializer first!");
        	        return;
        		} else {
        			ecoreRes.unload();
        			runWorkflow(s, mwePath);
        			return;
        		}
        	}
        }
        
        MessageDialog.openInformation(s, "Info", "Please select an .mwe2 or .ecore file. The selected resource is not an mwe nor an ecore file.");
        return;
	}


	protected void runWorkflow(Shell shell, String filePath) {
		TaskRunnable task = new TaskRunnable() {
			@Override
			public void run() throws Exception {
				String mwe = "file://"+filePath.replaceAll("\\\\", "/");
				
				Mwe2Launcher launcher = new CustomizedMwe2Launcher();
				launcher.run(new String[] {mwe});
			}
		};
		
		EECGTaskDialog dialog = new EECGTaskDialog(shell, task);
		dialog.open();
	}


	private IResource getSelectedResource() {
        IResource resourceFromSelection = getResourceFromSelectionService();
        if (resourceFromSelection != null) {
            return resourceFromSelection;
        }
        
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
