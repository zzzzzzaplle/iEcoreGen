package edu.ustb.sei.mde.eecg.ui.dialog;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import edu.ustb.sei.mde.mwe2.EMFGeneratorFragment2;
import edu.ustb.sei.mde.mwe2.EcoreGenerator;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 任务执行对话框 - 简洁版
 */
public class EECGTaskDialog extends Dialog {
    
    private static final String EECG_DIALOG_APPENDER = "EECG_DIALOG_APPENDER";
	private StyledText logText;
    private Button closeButton;
    private Label statusLabel;
    private Thread taskThread;
    private AtomicBoolean taskCompleted = new AtomicBoolean(false);
    
    private final Logger logger = Logger.getLogger(EECGTaskDialog.class);
    
    // 用户的任务接口
    public interface TaskRunnable {
        void run() throws Exception;
    }
    
    private final TaskRunnable userTask;
    
    public EECGTaskDialog(Shell parentShell, TaskRunnable userTask) {
        super(parentShell);
        this.userTask = userTask;
        // 设置对话框样式 - 去掉标题栏的关闭按钮
        setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
    }
    
    @Override
    protected Control createDialogArea(Composite parent) {
//    	parent.getShell().setText("iEcoreGen");
    	
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout(1, false);
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        layout.verticalSpacing = 10;
        container.setLayout(layout);
        
        // 状态标签
        statusLabel = new Label(container, SWT.NONE);
        statusLabel.setText("Task is running...");
        statusLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
        
        // 日志文本框
        logText = new StyledText(container, 
            SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd.heightHint = 300;
        gd.widthHint = 500;
        logText.setLayoutData(gd);
        
        // 初始化日志系统
        setupLogSystem();
        
        return container;
    }
    
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        // 只创建关闭按钮，但初始时禁用
        closeButton = createButton(parent, IDialogConstants.CLOSE_ID, 
            IDialogConstants.CLOSE_LABEL, false);
        
        closeButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                close();
            }
        });
        
        // 初始时禁用关闭按钮
        closeButton.setEnabled(true);
    }
    
    private void setupLogSystem() {
        EECGDialogAppender.setLogTextWidget(logText);

        EECGDialogAppender appender = new EECGDialogAppender();
        appender.setName(EECG_DIALOG_APPENDER);
        appender.setThreshold(Level.ALL);
        
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.addAppender(appender);
        rootLogger.setLevel(Level.ALL);
        
//        append(appender, EECGTaskDialog.class);
//        append(appender, EMFGeneratorFragment2.class);
//        append(appender, EcoreGenerator.class);
    }
    
    private void resetLogSystem() {
    	Logger rootLogger = Logger.getRootLogger();
        rootLogger.removeAppender(EECG_DIALOG_APPENDER);
        
//    	remove(EECGTaskDialog.class);
//    	remove(EMFGeneratorFragment2.class);
//    	remove(EcoreGenerator.class);
    }

	protected void append(EECGDialogAppender appender, Class<?> clazz) {
		Logger logger = Logger.getLogger(clazz);
        logger.addAppender(appender);
	}
	
	protected void remove(Class<?> clazz) {
		Logger logger = Logger.getLogger(clazz);
        logger.removeAppender(EECG_DIALOG_APPENDER);
	}
    
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("iEcoreGen");
        newShell.setSize(550, 400);
        
        // 禁用窗口的关闭按钮
        newShell.addListener(SWT.Close, event -> {
            if (!taskCompleted.get()) {
                event.doit = false; // 阻止关闭
//                logger.warn("You cannot close when task is running");
            }
        });
    }
    
    @Override
    public int open() {
    	setBlockOnOpen(false);
    	int result = super.open();
    	startTaskInBackground();
    	return result;
    }
    
    private void startTaskInBackground() {
    	closeButton.setEnabled(false);
    	
        taskThread = new Thread(() -> {
            try {
                logger.info("Task started...");
                
                // 执行用户提供的任务
                if (userTask != null) {
                    userTask.run();
                }
                
                logger.info("Task completed");
                
            } catch (Exception e) {
                logger.error("Task failed: " + e.getMessage(), e);
            } finally {
                // 任务完成，允许关闭对话框
                taskCompleted.set(true);
                
                // 在UI线程中更新状态和按钮
                Display.getDefault().asyncExec(() -> {
                    statusLabel.setText("Task completed");
                    closeButton.setEnabled(true);
                    
                    // 更新对话框标题
                    Shell shell = getShell();
                    if (shell != null && !shell.isDisposed()) {
                        shell.setText("Task completed");
                    }
                });
            }
        }, "Task-Thread");
        
//        taskThread.setDaemon(true);
        taskThread.start();
    }
    
    @Override
    public boolean close() {
        if (!taskCompleted.get()) {
            logger.warn("Task is not completed. You cannot close now.");
            return false;
        }
        
        // 清理资源
        resetLogSystem();
        EECGDialogAppender.setLogTextWidget(null);
        
        return super.close();
    }
    
    // 提供一个快捷方法在任务完成后自动关闭
    public void autoCloseOnComplete(int delayMillis) {
        new Thread(() -> {
            // 等待任务完成
            while (!taskCompleted.get()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            
            // 任务完成后等待指定的延迟时间
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            
            // 在UI线程中关闭对话框
            Display.getDefault().asyncExec(() -> {
                if (getShell() != null && !getShell().isDisposed()) {
                    close();
                }
            });
        }).start();
    }
}