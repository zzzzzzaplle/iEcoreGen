import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CR4Test {
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        textEditor.setName("TextEditor");
        imageEditor = new ImageEditor();
        imageEditor.setName("ImageEditor");
        videoEditor = new VideoEditor();
        videoEditor.setName("VideoEditor");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        doc1.setSize(100);
        doc1.setAuthor("Author1");
        try {
            doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        doc2.setSize(200);
        doc2.setAuthor("Author2");
        try {
            doc2.setCreateDate(dateFormat.parse("2023-01-02 11:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        doc3.setSize(300);
        doc3.setAuthor("Author3");
        try {
            doc3.setCreateDate(dateFormat.parse("2023-01-03 12:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        doc1.setSize(150);
        doc1.setAuthor("Author1");
        try {
            doc1.setCreateDate(dateFormat.parse("2023-02-01 10:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        doc2.setSize(50);
        doc2.setAuthor("Author2");
        try {
            doc2.setCreateDate(dateFormat.parse("2023-02-02 11:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertNull("ImageEditor should not be present in result", result.get("ImageEditor"));
        assertNull("VideoEditor should not be present in result", result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(imageEditor);
        doc1.setSize(250);
        doc1.setAuthor("Author1");
        try {
            doc1.setCreateDate(dateFormat.parse("2023-03-01 10:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(videoEditor);
        doc2.setSize(500);
        doc2.setAuthor("Author2");
        try {
            doc2.setCreateDate(dateFormat.parse("2023-03-02 11:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(textEditor);
        doc3.setSize(100);
        doc3.setAuthor("Author3");
        try {
            doc3.setCreateDate(dateFormat.parse("2023-03-03 12:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.removeDocument(doc1); // Remove Image1.png
        
        // Execute
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertNull("ImageEditor should not be present in result", result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: No documents added to file system
        
        // Execute
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify
        assertTrue("Result map should be empty when no documents exist", result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        doc1.setSize(120);
        doc1.setAuthor("Author1");
        try {
            doc1.setCreateDate(dateFormat.parse("2023-04-01 10:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        doc2.setSize(300);
        doc2.setAuthor("Author2");
        try {
            doc2.setCreateDate(dateFormat.parse("2023-04-02 11:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        doc3.setSize(800);
        doc3.setAuthor("Author3");
        try {
            doc3.setCreateDate(dateFormat.parse("2023-04-03 12:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        doc4.setSize(180);
        doc4.setAuthor("Author4");
        try {
            doc4.setCreateDate(dateFormat.parse("2023-04-04 13:00:00"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
}