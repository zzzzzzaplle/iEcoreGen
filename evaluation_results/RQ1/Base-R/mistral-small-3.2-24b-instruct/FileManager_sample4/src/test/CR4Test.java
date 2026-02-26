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
        // SetUp: Create a FileSystem instance and add documents with mixed editor types
        Date creationDate = dateFormat.parse("2023-01-01 10:00:00");
        
        // Add document "Report.docx" using TextEditor
        Document doc1 = new Document("Report.docx", creationDate, "Author1", 1024L, 
                                   new Editor("Text Editor"));
        fileSystem.addDocument(doc1);
        
        // Add document "Image.png" using ImageEditor
        Document doc2 = new Document("Image.png", creationDate, "Author2", 2048L, 
                                   new Editor("Image Editor"));
        fileSystem.addDocument(doc2);
        
        // Add document "Video.mp4" using VideoEditor
        Document doc3 = new Document("Video.mp4", creationDate, "Author3", 4096L, 
                                   new Editor("Video Editor"));
        fileSystem.addDocument(doc3);
        
        // Input: Count the number of documents per editor type
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Expected Output: TextEditor: 1, ImageEditor: 1, VideoEditor: 1
        assertEquals(Long.valueOf(1), result.get("Text Editor"));
        assertEquals(Long.valueOf(1), result.get("Image Editor"));
        assertEquals(Long.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() throws ParseException {
        // SetUp: Create a FileSystem instance and add documents with single editor type
        Date creationDate = dateFormat.parse("2023-01-01 10:00:00");
        
        // Add document "Essay.docx" using TextEditor
        Document doc1 = new Document("Essay.docx", creationDate, "Author1", 1024L, 
                                   new Editor("Text Editor"));
        fileSystem.addDocument(doc1);
        
        // Add document "Notes.txt" using TextEditor
        Document doc2 = new Document("Notes.txt", creationDate, "Author2", 512L, 
                                   new Editor("Text Editor"));
        fileSystem.addDocument(doc2);
        
        // Input: Count the number of documents per editor type
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Expected Output: TextEditor: 2, ImageEditor: 0, VideoEditor: 0
        assertEquals(Long.valueOf(2), result.get("Text Editor"));
        assertEquals(Long.valueOf(0), result.get("Image Editor"));
        assertEquals(Long.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() throws ParseException {
        // SetUp: Create a FileSystem instance, add documents, then remove one
        Date creationDate = dateFormat.parse("2023-01-01 10:00:00");
        
        // Add document "Image1.png" using ImageEditor
        Document doc1 = new Document("Image1.png", creationDate, "Author1", 2048L, 
                                   new Editor("Image Editor"));
        fileSystem.addDocument(doc1);
        
        // Add document "Video1.mp4" using VideoEditor
        Document doc2 = new Document("Video1.mp4", creationDate, "Author2", 4096L, 
                                   new Editor("Video Editor"));
        fileSystem.addDocument(doc2);
        
        // Add document "Text1.docx" using TextEditor
        Document doc3 = new Document("Text1.docx", creationDate, "Author3", 1024L, 
                                   new Editor("Text Editor"));
        fileSystem.addDocument(doc3);
        
        // Remove the document "Image1.png"
        fileSystem.removeDocument(doc1);
        
        // Input: Count the number of documents per editor type after removal
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Expected Output: TextEditor: 1, ImageEditor: 0, VideoEditor: 1
        assertEquals(Long.valueOf(1), result.get("Text Editor"));
        assertEquals(Long.valueOf(0), result.get("Image Editor"));
        assertEquals(Long.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create a FileSystem instance with no documents added
        // Input: Count the number of documents per editor type when no documents are added
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Expected Output: TextEditor: 0, ImageEditor: 0, VideoEditor: 0
        assertEquals(Long.valueOf(0), result.get("Text Editor"));
        assertEquals(Long.valueOf(0), result.get("Image Editor"));
        assertEquals(Long.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() throws ParseException {
        // SetUp: Create a FileSystem instance and add documents using all editors
        Date creationDate = dateFormat.parse("2023-01-01 10:00:00");
        
        // Add three documents: "Doc1.txt" with TextEditor, "Pic1.jpg" with ImageEditor, "Clip1.mpg" with VideoEditor
        Document doc1 = new Document("Doc1.txt", creationDate, "Author1", 1024L, 
                                   new Editor("Text Editor"));
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document("Pic1.jpg", creationDate, "Author2", 2048L, 
                                   new Editor("Image Editor"));
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document("Clip1.mpg", creationDate, "Author3", 4096L, 
                                   new Editor("Video Editor"));
        fileSystem.addDocument(doc3);
        
        // Add another document "Doc2.txt" with TextEditor
        Document doc4 = new Document("Doc2.txt", creationDate, "Author4", 512L, 
                                   new Editor("Text Editor"));
        fileSystem.addDocument(doc4);
        
        // Input: Count the number of documents per editor type
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Expected Output: TextEditor: 2, ImageEditor: 1, VideoEditor: 1
        assertEquals(Long.valueOf(2), result.get("Text Editor"));
        assertEquals(Long.valueOf(1), result.get("Image Editor"));
        assertEquals(Long.valueOf(1), result.get("Video Editor"));
    }
}