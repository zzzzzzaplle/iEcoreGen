import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create a FileSystem instance
        // Add documents with different editor types
        Editor textEditor = new Editor("TextEditor");
        Editor imageEditor = new Editor("ImageEditor");
        Editor videoEditor = new Editor("VideoEditor");
        
        Document doc1 = new Document("Report.docx", new Date(), "Author1", 1024, textEditor);
        Document doc2 = new Document("Image.png", new Date(), "Author2", 2048, imageEditor);
        Document doc3 = new Document("Video.mp4", new Date(), "Author3", 3072, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Count the number of documents per editor type
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Expected Output: TextEditor: 1, ImageEditor: 1, VideoEditor: 1
        assertEquals(Long.valueOf(1), result.get("TextEditor"));
        assertEquals(Long.valueOf(1), result.get("ImageEditor"));
        assertEquals(Long.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create a FileSystem instance
        // Add documents with only TextEditor
        Editor textEditor = new Editor("TextEditor");
        
        Document doc1 = new Document("Essay.docx", new Date(), "Author1", 1024, textEditor);
        Document doc2 = new Document("Notes.txt", new Date(), "Author2", 2048, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Count the number of documents per editor type
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Expected Output: TextEditor: 2, ImageEditor: 0, VideoEditor: 0
        assertEquals(Long.valueOf(2), result.get("TextEditor"));
        assertNull(result.get("ImageEditor"));
        assertNull(result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create a FileSystem instance
        // Add documents with different editor types and then remove one
        Editor textEditor = new Editor("TextEditor");
        Editor imageEditor = new Editor("ImageEditor");
        Editor videoEditor = new Editor("VideoEditor");
        
        Document doc1 = new Document("Image1.png", new Date(), "Author1", 1024, imageEditor);
        Document doc2 = new Document("Video1.mp4", new Date(), "Author2", 2048, videoEditor);
        Document doc3 = new Document("Text1.docx", new Date(), "Author3", 3072, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the document "Image1.png"
        fileSystem.removeDocument(doc1);
        
        // Input: Count the number of documents per editor type after removal
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Expected Output: TextEditor: 1, ImageEditor: 0, VideoEditor: 1
        assertEquals(Long.valueOf(1), result.get("TextEditor"));
        assertNull(result.get("ImageEditor"));
        assertEquals(Long.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create a FileSystem instance without adding any documents
        
        // Input: Count the number of documents per editor type when no documents are added
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Expected Output: TextEditor: 0, ImageEditor: 0, VideoEditor: 0
        // Since no documents are added, the result map should be empty
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create a FileSystem instance
        // Add multiple documents with all editor types used
        Editor textEditor = new Editor("TextEditor");
        Editor imageEditor = new Editor("ImageEditor");
        Editor videoEditor = new Editor("VideoEditor");
        
        Document doc1 = new Document("Doc1.txt", new Date(), "Author1", 1024, textEditor);
        Document doc2 = new Document("Pic1.jpg", new Date(), "Author2", 2048, imageEditor);
        Document doc3 = new Document("Clip1.mpg", new Date(), "Author3", 3072, videoEditor);
        Document doc4 = new Document("Doc2.txt", new Date(), "Author4", 4096, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Input: Count the number of documents per editor type
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Expected Output: TextEditor: 2, ImageEditor: 1, VideoEditor: 1
        assertEquals(Long.valueOf(2), result.get("TextEditor"));
        assertEquals(Long.valueOf(1), result.get("ImageEditor"));
        assertEquals(Long.valueOf(1), result.get("VideoEditor"));
    }
}