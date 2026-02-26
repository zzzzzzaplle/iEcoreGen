import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create a FileSystem instance and add documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(new VideoEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts
        assertEquals("TextEditor count should be 1", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor count should be 1", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create a FileSystem instance and add documents with only TextEditor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts
        assertEquals("TextEditor count should be 2", Integer.valueOf(2), result.get("TextEditor"));
        assertNull("ImageEditor should not be present", result.get("ImageEditor"));
        assertNull("VideoEditor should not be present", result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create a FileSystem instance, add documents, then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(new ImageEditor());
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(new VideoEditor());
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the Image1.png document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents per editor type after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts
        assertEquals("TextEditor count should be 1", Integer.valueOf(1), result.get("TextEditor"));
        assertNull("ImageEditor should not be present", result.get("ImageEditor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create a FileSystem instance with no documents added
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts - map should be empty since no documents with editors exist
        assertTrue("Result map should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create a FileSystem instance and add documents using all editor types
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(new VideoEditor());
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts
        assertEquals("TextEditor count should be 2", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor count should be 1", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("VideoEditor"));
    }
}