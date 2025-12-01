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
        // Setup: Create documents with different editor types
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createDate = sdf.parse("2023-01-01 10:00:00");
        
        Document report = new Document("Report.docx", createDate, "Author1", 100, textEditor);
        Document image = new Document("Image.png", createDate, "Author2", 200, imageEditor);
        Document video = new Document("Video.mp4", createDate, "Author3", 300, videoEditor);
        
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Each editor should have exactly one document
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_countDocumentsWithSingleEditorType() throws ParseException {
        // Setup: Create documents with only TextEditor
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createDate = sdf.parse("2023-01-01 10:00:00");
        
        Document essay = new Document("Essay.docx", createDate, "Author1", 150, textEditor);
        Document notes = new Document("Notes.txt", createDate, "Author2", 50, textEditor);
        
        fileSystem.addDocument(essay);
        fileSystem.addDocument(notes);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Only TextEditor should have documents
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertFalse(result.containsKey("Image Editor"));
        assertFalse(result.containsKey("Video Editor"));
    }
    
    @Test
    public void testCase3_countDocumentsAfterRemoval() throws ParseException {
        // Setup: Create documents with all editor types
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createDate = sdf.parse("2023-01-01 10:00:00");
        
        Document imageDoc = new Document("Image1.png", createDate, "Author1", 250, imageEditor);
        Document videoDoc = new Document("Video1.mp4", createDate, "Author2", 500, videoEditor);
        Document textDoc = new Document("Text1.docx", createDate, "Author3", 100, textEditor);
        
        fileSystem.addDocument(imageDoc);
        fileSystem.addDocument(videoDoc);
        fileSystem.addDocument(textDoc);
        
        // Remove one document
        fileSystem.removeDocument(imageDoc);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: ImageEditor count should be 0 after removal
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
        assertFalse(result.containsKey("Image Editor"));
    }
    
    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // Setup: Empty file system (no documents added)
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: No documents for any editor
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() throws ParseException {
        // Setup: Create multiple documents with different editor types
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createDate = sdf.parse("2023-01-01 10:00:00");
        
        Document doc1 = new Document("Doc1.txt", createDate, "Author1", 100, textEditor);
        Document pic1 = new Document("Pic1.jpg", createDate, "Author2", 300, imageEditor);
        Document clip1 = new Document("Clip1.mpg", createDate, "Author3", 1000, videoEditor);
        Document doc2 = new Document("Doc2.txt", createDate, "Author4", 150, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(pic1);
        fileSystem.addDocument(clip1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: TextEditor should have 2 docs, others 1 each
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
}