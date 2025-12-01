import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR4Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        // Initialize common test objects before each test
        fileSystem = new FileSystem();
        textEditor = new Editor("TextEditor");
        imageEditor = new Editor("ImageEditor");
        videoEditor = new Editor("VideoEditor");
        
        // Add editors to file system for all tests
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Add documents with different editor types
        Document doc1 = new Document("Report.docx", LocalDate.now(), "Author1", 100, textEditor);
        Document doc2 = new Document("Image.png", LocalDate.now(), "Author2", 200, imageEditor);
        Document doc3 = new Document("Video.mp4", LocalDate.now(), "Author3", 300, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor
        String result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check expected counts for each editor
        assertTrue(result.contains("TextEditor: 1"));
        assertTrue(result.contains("ImageEditor: 1"));
        assertTrue(result.contains("VideoEditor: 1"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Add documents with only TextEditor
        Document doc1 = new Document("Essay.docx", LocalDate.now(), "Author1", 100, textEditor);
        Document doc2 = new Document("Notes.txt", LocalDate.now(), "Author2", 200, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor
        String result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check expected counts (TextEditor: 2, others: 0)
        assertTrue(result.contains("TextEditor: 2"));
        assertTrue(result.contains("ImageEditor: 0"));
        assertTrue(result.contains("VideoEditor: 0"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Add documents and then remove one
        Document doc1 = new Document("Image1.png", LocalDate.now(), "Author1", 100, imageEditor);
        Document doc2 = new Document("Video1.mp4", LocalDate.now(), "Author2", 200, videoEditor);
        Document doc3 = new Document("Text1.docx", LocalDate.now(), "Author3", 300, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents per editor after removal
        String result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check expected counts after removal
        assertTrue(result.contains("TextEditor: 1"));
        assertTrue(result.contains("ImageEditor: 0"));
        assertTrue(result.contains("VideoEditor: 1"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: FileSystem is empty (no documents added)
        // Execute: Count documents per editor on empty file system
        String result = fileSystem.countDocumentsPerEditor();
        
        // Verify: All editors should have 0 documents
        assertTrue(result.contains("TextEditor: 0"));
        assertTrue(result.contains("ImageEditor: 0"));
        assertTrue(result.contains("VideoEditor: 0"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Add multiple documents with all editor types
        Document doc1 = new Document("Doc1.txt", LocalDate.now(), "Author1", 100, textEditor);
        Document doc2 = new Document("Pic1.jpg", LocalDate.now(), "Author2", 200, imageEditor);
        Document doc3 = new Document("Clip1.mpg", LocalDate.now(), "Author3", 300, videoEditor);
        Document doc4 = new Document("Doc2.txt", LocalDate.now(), "Author4", 400, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor
        String result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check expected counts
        assertTrue(result.contains("TextEditor: 2"));
        assertTrue(result.contains("ImageEditor: 1"));
        assertTrue(result.contains("VideoEditor: 1"));
    }
}