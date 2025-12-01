import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
    public void testCase1_CountDocumentsWithMixedEditorTypes() throws ParseException {
        // SetUp: Create a FileSystem instance
        fileSystem = new FileSystem();
        
        // SetUp: Add documents with different editor types
        Date date1 = dateFormat.parse("2024-01-01 10:00:00");
        Date date2 = dateFormat.parse("2024-01-02 11:00:00");
        Date date3 = dateFormat.parse("2024-01-03 12:00:00");
        
        fileSystem.addDocument(new Document("Report.docx", date1, "Alice", 1024, new TextEditor()));
        fileSystem.addDocument(new Document("Image.png", date2, "Bob", 2048, new ImageEditor()));
        fileSystem.addDocument(new Document("Video.mp4", date3, "Charlie", 4096, new VideoEditor()));
        
        // Input: Count the number of documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output: TextEditor: 1, ImageEditor: 1, VideoEditor: 1
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() throws ParseException {
        // SetUp: Create a FileSystem instance
        fileSystem = new FileSystem();
        
        // SetUp: Add documents with only TextEditor
        Date date1 = dateFormat.parse("2024-01-01 10:00:00");
        Date date2 = dateFormat.parse("2024-01-02 11:00:00");
        
        fileSystem.addDocument(new Document("Essay.docx", date1, "Alice", 1024, new TextEditor()));
        fileSystem.addDocument(new Document("Notes.txt", date2, "Bob", 512, new TextEditor()));
        
        // Input: Count the number of documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output: TextEditor: 2, ImageEditor: 0, VideoEditor: 0
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertNull(result.get("Image Editor"));
        assertNull(result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() throws ParseException {
        // SetUp: Create a FileSystem instance
        fileSystem = new FileSystem();
        
        // SetUp: Add documents with different editor types
        Date date1 = dateFormat.parse("2024-01-01 10:00:00");
        Date date2 = dateFormat.parse("2024-01-02 11:00:00");
        Date date3 = dateFormat.parse("2024-01-03 12:00:00");
        
        Document doc1 = new Document("Image1.png", date1, "Alice", 2048, new ImageEditor());
        Document doc2 = new Document("Video1.mp4", date2, "Bob", 4096, new VideoEditor());
        Document doc3 = new Document("Text1.docx", date3, "Charlie", 1024, new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // SetUp: Remove the document "Image1.png"
        fileSystem.removeDocument(doc1);
        
        // Input: Count the number of documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output: TextEditor: 1, ImageEditor: 0, VideoEditor: 1
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertNull(result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create a FileSystem instance (no documents added)
        fileSystem = new FileSystem();
        
        // Input: Count the number of documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output: TextEditor: 0, ImageEditor: 0, VideoEditor: 0
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() throws ParseException {
        // SetUp: Create a FileSystem instance
        fileSystem = new FileSystem();
        
        // SetUp: Add three documents with different editor types plus one additional TextEditor document
        Date date1 = dateFormat.parse("2024-01-01 10:00:00");
        Date date2 = dateFormat.parse("2024-01-02 11:00:00");
        Date date3 = dateFormat.parse("2024-01-03 12:00:00");
        Date date4 = dateFormat.parse("2024-01-04 13:00:00");
        
        fileSystem.addDocument(new Document("Doc1.txt", date1, "Alice", 1024, new TextEditor()));
        fileSystem.addDocument(new Document("Pic1.jpg", date2, "Bob", 2048, new ImageEditor()));
        fileSystem.addDocument(new Document("Clip1.mpg", date3, "Charlie", 4096, new VideoEditor()));
        fileSystem.addDocument(new Document("Doc2.txt", date4, "Diana", 512, new TextEditor()));
        
        // Input: Count the number of documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output: TextEditor: 2, ImageEditor: 1, VideoEditor: 1
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
}