import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CR4Test {
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }
    
    @Test
    public void testCase1_countDocumentsWithMixedEditorTypes() {
        // Create documents with different editors
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify counts
        assertEquals(1, (int) result.get("Text Editor"));
        assertEquals(1, (int) result.get("Image Editor"));
        assertEquals(1, (int) result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_countDocumentsWithSingleEditorType() {
        // Create documents with TextEditor only
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify counts
        assertEquals(2, (int) result.get("Text Editor"));
        assertEquals(0, (int) result.get("Image Editor"));
        assertEquals(0, (int) result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_countDocumentsAfterRemoval() {
        // Create documents
        Document imageDoc = new Document();
        imageDoc.setName("Image1.png");
        imageDoc.setEditor(imageEditor);
        
        Document videoDoc = new Document();
        videoDoc.setName("Video1.mp4");
        videoDoc.setEditor(videoEditor);
        
        Document textDoc = new Document();
        textDoc.setName("Text1.docx");
        textDoc.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(imageDoc);
        fileSystem.addDocument(videoDoc);
        fileSystem.addDocument(textDoc);
        
        // Remove one document
        fileSystem.removeDocument(imageDoc);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify counts
        assertEquals(1, (int) result.get("Text Editor"));
        assertEquals(0, (int) result.get("Image Editor"));
        assertEquals(1, (int) result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // Count documents per editor type with empty file system
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify counts
        assertEquals(0, (int) result.get("Text Editor"));
        assertEquals(0, (int) result.get("Image Editor"));
        assertEquals(0, (int) result.get("Video Editor"));
    }
    
    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() {
        // Create documents
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify counts
        assertEquals(2, (int) result.get("Text Editor"));
        assertEquals(1, (int) result.get("Image Editor"));
        assertEquals(1, (int) result.get("Video Editor"));
    }
}