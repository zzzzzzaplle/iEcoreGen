import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
    public void testCase1_countDocumentsWithMixedEditorTypes() throws ParseException {
        // Create documents with different editors
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        doc1.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 10:00:00"));
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        doc2.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-02 10:00:00"));
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        doc3.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-03 10:00:00"));
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Count documents per editor type
        Map<String, Integer> counts = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts
        assertEquals(1, (int) counts.get("Text Editor"));
        assertEquals(1, (int) counts.get("Image Editor"));
        assertEquals(1, (int) counts.get("Video Editor"));
    }
    
    @Test
    public void testCase2_countDocumentsWithSingleEditorType() throws ParseException {
        // Create documents with TextEditor only
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        doc1.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 10:00:00"));
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        doc2.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-02 10:00:00"));
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Count documents per editor type
        Map<String, Integer> counts = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts
        assertEquals(2, (int) counts.get("Text Editor"));
        assertEquals(0, (int) counts.get("Image Editor"));
        assertEquals(0, (int) counts.get("Video Editor"));
    }
    
    @Test
    public void testCase3_countDocumentsAfterRemoval() throws ParseException {
        // Create documents
        Document imageDoc = new Document();
        imageDoc.setName("Image1.png");
        imageDoc.setEditor(imageEditor);
        imageDoc.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 10:00:00"));
        
        Document videoDoc = new Document();
        videoDoc.setName("Video1.mp4");
        videoDoc.setEditor(videoEditor);
        videoDoc.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-02 10:00:00"));
        
        Document textDoc = new Document();
        textDoc.setName("Text1.docx");
        textDoc.setEditor(textEditor);
        textDoc.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-03 10:00:00"));
        
        // Add documents to file system
        fileSystem.addDocument(imageDoc);
        fileSystem.addDocument(videoDoc);
        fileSystem.addDocument(textDoc);
        
        // Remove image document
        fileSystem.removeDocument(imageDoc);
        
        // Count documents per editor type
        Map<String, Integer> counts = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts after removal
        assertEquals(1, (int) counts.get("Text Editor"));
        assertEquals(0, (int) counts.get("Image Editor"));
        assertEquals(1, (int) counts.get("Video Editor"));
    }
    
    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // Count documents per editor type in empty file system
        Map<String, Integer> counts = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts (all zeros)
        assertEquals(0, (int) counts.get("Text Editor"));
        assertEquals(0, (int) counts.get("Image Editor"));
        assertEquals(0, (int) counts.get("Video Editor"));
    }
    
    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() throws ParseException {
        // Create documents
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        doc1.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 10:00:00"));
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        doc2.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-02 10:00:00"));
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        doc3.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-03 10:00:00"));
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        doc4.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-04 10:00:00"));
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Count documents per editor type
        Map<String, Integer> counts = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts
        assertEquals(2, (int) counts.get("Text Editor"));
        assertEquals(1, (int) counts.get("Image Editor"));
        assertEquals(1, (int) counts.get("Video Editor"));
    }
}