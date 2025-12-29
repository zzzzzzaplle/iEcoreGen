package edu.ustb.sei.mde.eecg.ui.wizard;

import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MWEConfigurationWizard extends Wizard {

    private MainPage mainPage;
    private Map<String, String> configuration = new HashMap<>();

    public MWEConfigurationWizard(Map<String, String> defaultParameters) {
        setWindowTitle("Configuration Wizard");
        this.configuration.putAll(defaultParameters);
    }

    @Override
    public void addPages() {
        mainPage = new MainPage("mainPage", configuration);
        addPage(mainPage);
    }

    @Override
    public boolean performFinish() {
        configuration.putAll(mainPage.getConfiguration());
//        System.out.println("Configuration: " + configuration);
        return true;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    class MainPage extends WizardPage {
        private Text mweFileNameText;
        private Text sourceFolderText;
        private Text ecoreFileText;
        private Text projectNameText;
        private Text basePackageText;
        private Text classpathFolderText;
        private Button overwriteCheckbox;
        private Map<String, String> configuration;

        protected MainPage(String pageName, Map<String, String> configuration) {
            super(pageName);
            setTitle("iEcoreGen Configuration");
            setDescription("Configure iEcoreGen workflow parameters");
            this.configuration = configuration;
        }

        @Override
        public void createControl(Composite parent) {
            Composite container = new Composite(parent, SWT.NONE);
            setControl(container);
            
            GridLayout layout = new GridLayout(3, false);
            layout.marginWidth = 10;
            layout.marginHeight = 10;
            layout.verticalSpacing = 8;
            layout.horizontalSpacing = 8;
            container.setLayout(layout);

            // 1. MWE File Name
            createLabel(container, "MWE File Name:");
            mweFileNameText = createTextField(container);
            ((GridData) mweFileNameText.getLayoutData()).horizontalSpan = 2;
//            createFileBrowseButton(container, "Browse...", mweFileNameText, 
//                new String[]{"*.mwe", "*.mwe2", "*.xml", "*.*"});

            // 2. Ecore File
            createLabel(container, "Ecore File:");
            ecoreFileText = createTextField(container);
            ecoreFileText.setText(this.configuration.getOrDefault("ecoreFile", ""));
            createFileBrowseButton(container, "Browse...", ecoreFileText,
            		new String[]{"*.ecore", "*.emf", "*.xmi", "*.*"});
            
            // 3. Project Folder
            createLabel(container, "Project Name:");
            projectNameText = createTextField(container);
            projectNameText.setText(this.configuration.getOrDefault("project", ""));
            createFolderBrowseButton(container, "Browse...", projectNameText, false);
            
            // 4. Source Folder
            createLabel(container, "Source Folder:");
            sourceFolderText = createTextField(container);
            sourceFolderText.setText("src");
            createFolderBrowseButton(container, "Browse...", sourceFolderText, true);


            // 5. Base Package
            createLabel(container, "Base Package:");
            basePackageText = createTextField(container);
            GridData basePackageGd = new GridData(SWT.FILL, SWT.CENTER, true, false);
            basePackageGd.horizontalSpan = 2;
            basePackageText.setLayoutData(basePackageGd);

            // 6. Classpath Folder
            createLabel(container, "Classpath Folder:");
            classpathFolderText = createTextField(container);
            createFolderBrowseButton(container, "Browse...", classpathFolderText, false);

            // 7. Overwrite Checkbox
            new Label(container, SWT.NONE); // 空标签
            overwriteCheckbox = new Button(container, SWT.CHECK);
            overwriteCheckbox.setText("Overwrite existing files");
            GridData checkboxGd = new GridData(SWT.FILL, SWT.CENTER, true, false);
            checkboxGd.horizontalSpan = 2;
            overwriteCheckbox.setLayoutData(checkboxGd);

            setInitialValues();
            setupValidation();
        }

        private Label createLabel(Composite parent, String text) {
            Label label = new Label(parent, SWT.NONE);
            label.setText(text);
            GridData gd = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
            label.setLayoutData(gd);
            return label;
        }

        private Text createTextField(Composite parent) {
            Text text = new Text(parent, SWT.BORDER);
            GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
            gd.widthHint = 200;
            text.setLayoutData(gd);
            return text;
        }

        private void createFileBrowseButton(Composite parent, String text, Text targetText, String[] extensions) {
            Button button = new Button(parent, SWT.PUSH);
            button.setText(text);
            
            GridData gd = new GridData();
            gd.widthHint = 70;
            button.setLayoutData(gd);
            
            button.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
                    dialog.setText("Select File");
                    if (extensions != null) {
                        dialog.setFilterExtensions(extensions);
                    }
                    String path = dialog.open();
                    if (path != null) {
                        targetText.setText(path);
                        validatePage();
                    }
                }
            });
        }

        private void createFolderBrowseButton(Composite parent, String text, Text targetText, boolean nameOnly) {
            Button button = new Button(parent, SWT.PUSH);
            button.setText(text);
            
            GridData gd = new GridData();
            gd.widthHint = 70;
            button.setLayoutData(gd);
            
            button.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
                    dialog.setText("Select Directory");
                    dialog.setMessage("Please select a directory");
                    String path = dialog.open();
                    if (path != null) {
                    	if(nameOnly) {
                    		File file = new File(path);
                    		targetText.setText(file.getName());
                    	} else {
                    		targetText.setText(path);
                    	}
                        validatePage();
                    }
                }
            });
        }

        private void setInitialValues() {
            if (configuration != null) {
                mweFileNameText.setText(configuration.getOrDefault("mweFileName", ""));
                sourceFolderText.setText(configuration.getOrDefault("sourceFolder", ""));
                ecoreFileText.setText(configuration.getOrDefault("ecoreFile", ""));
                basePackageText.setText(configuration.getOrDefault("basePackage", ""));
                classpathFolderText.setText(configuration.getOrDefault("classpathFolder", ""));
                overwriteCheckbox.setSelection(Boolean.parseBoolean(
                    configuration.getOrDefault("overwrite", "false")));
            }
        }

        private void setupValidation() {
            ModifyListener modifyListener = e -> validatePage();
            mweFileNameText.addModifyListener(modifyListener);
            sourceFolderText.addModifyListener(modifyListener);
            ecoreFileText.addModifyListener(modifyListener);
            validatePage();
        }

        private void validatePage() {
            String errorMsg = null;
            if (mweFileNameText.getText().trim().isEmpty()) {
                errorMsg = "MWE File Name is required";
            } else if (sourceFolderText.getText().trim().isEmpty()) {
                errorMsg = "Source Folder is required";
            } else if (ecoreFileText.getText().trim().isEmpty()) {
                errorMsg = "Ecore File is required";
            }
            setPageComplete(errorMsg == null);
            setErrorMessage(errorMsg);
        }

        public Map<String, String> getConfiguration() {
            Map<String, String> config = new HashMap<>();
            config.put("mweFileName", mweFileNameText.getText().trim());
            config.put("sourceFolder", sourceFolderText.getText().trim());
            config.put("project", projectNameText.getText().trim());
            config.put("ecoreFile", ecoreFileText.getText().trim());
            config.put("basePackage", basePackageText.getText().trim());
            config.put("classpathFolder", classpathFolderText.getText().trim());
            config.put("overwrite", Boolean.toString(overwriteCheckbox.getSelection()));
            return config;
        }
    }

    // 修改后的main方法
    public static void main(String[] args) {
        // 在程序启动时设置系统属性，避免SVG错误
    	System.setProperty("org.eclipse.swt.internal.image.SVGRasterizer", "disabled");
        
        Display display = null;
        try {
            display = new Display();
            Shell shell = new Shell(display);
            shell.setText("MWE Configuration Wizard");
            
            MWEConfigurationWizard wizard = new MWEConfigurationWizard(Collections.emptyMap());
            WizardDialog dialog = new WizardDialog(shell, wizard);
            dialog.setPageSize(500, 350);
            
            if (dialog.open() == Window.OK) {
                System.out.println("Configuration saved: " + wizard.getConfiguration());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (display != null) {
                display.dispose();
            }
        }
    }
}