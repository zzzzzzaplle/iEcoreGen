package edu.ustb.sei.mde.eecg.ui.dialog;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 简单对话框日志Appender
 */
public class EECGDialogAppender extends AppenderSkeleton {
    
    private static StyledText logTextWidget;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static volatile boolean enabled = true;
    
    public static void setLogTextWidget(StyledText widget) {
        logTextWidget = widget;
        enabled = (widget != null);
    }
    
    public static void setEnabled(boolean enabled) {
        EECGDialogAppender.enabled = enabled;
    }
    
    @Override
    protected void append(LoggingEvent event) {
        if (!enabled || logTextWidget == null || logTextWidget.isDisposed()) {
            return;
        }
        
        String formattedMessage = formatMessage(event);
        
        // 在UI线程中更新文本框
        Display.getDefault().asyncExec(() -> {
            if (logTextWidget != null && !logTextWidget.isDisposed()) {
                try {
                	int metaStart = logTextWidget.getCharCount();
                	int firstLineLength = formattedMessage.indexOf('\n');
                	int detailStart = metaStart + firstLineLength;
                	int detailLength = formattedMessage.length() - firstLineLength - 1;
                	
                    logTextWidget.append(formattedMessage);
                    
                    StyleRange metaStyle = new StyleRange();
                    metaStyle.start = metaStart;
                    metaStyle.length = firstLineLength;
                    metaStyle.foreground = logTextWidget.getDisplay().getSystemColor(SWT.COLOR_BLUE);
                    metaStyle.fontStyle = SWT.BOLD;
                    logTextWidget.setStyleRange(metaStyle);
                    
                    if(detailLength > 0) {
                    	StyleRange detailStyle = new StyleRange();
                    	detailStyle.start = detailStart;
                    	detailStyle.length = detailLength;
                    	detailStyle.foreground = logTextWidget.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
                    	detailStyle.fontStyle = SWT.ITALIC;
                    	logTextWidget.setStyleRange(detailStyle);
                    }
                    
                    
//                    // 简单限制行数
//                    if (logTextWidget.getLineCount() > 1000) {
//                        String text = logTextWidget.getText();
//                        int firstNewLine = text.indexOf('\n');
//                        if (firstNewLine > 0) {
//                            logTextWidget.setText(text.substring(firstNewLine + 1));
//                        }
//                    }
//                    
                    // 自动滚动到底部
                    logTextWidget.setTopIndex(logTextWidget.getLineCount() - 1);
                } catch (Exception e) {
                    // 忽略异常
                }
            }
        });
    }
    
    private String formatMessage(LoggingEvent event) {
        StringBuilder sb = new StringBuilder();
        
        // 时间戳
        sb.append("[");
        sb.append(dateFormat.format(new Date(event.timeStamp)));
        sb.append("] ");
        
        // 日志级别
        sb.append(event.getLevel().toString().charAt(0)); // 只显示第一个字母
        sb.append(" ");
        
        // 消息内容
        String message = event.getRenderedMessage();
        if (message != null) {
            sb.append(message);
        }
        
        // 异常信息（简化）
        String[] throwableStrRep = event.getThrowableStrRep();
        if (throwableStrRep != null && throwableStrRep.length > 0) {
            sb.append(" (");
            sb.append(throwableStrRep[0]);
            sb.append(")");
        }
        
        sb.append("\n");
        return sb.toString();
    }
    
    @Override
    public void close() {
        logTextWidget = null;
        enabled = false;
    }
    
    @Override
    public boolean requiresLayout() {
        return false;
    }
}