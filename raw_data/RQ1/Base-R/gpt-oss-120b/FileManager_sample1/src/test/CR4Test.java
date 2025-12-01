import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        // Initialize FileSystem and editors before each test
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create documents with different editor types
        Document doc1 = new Document("Report.docx", LocalDate.now(), "Author1", 1024L, textEditor);
        Document doc2 = new Document("Image.png", LocalDate.now(), "Author2", 2048L, imageEditor);
        Document doc3 = new Document("Video.mp4", LocalDate.now(), "Author3", 4096L, videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Count documents by editor
        Map<String, Long> result = fileSystem.countDocumentsByEditor();
        
        // Verify expected counts
        assertEquals(Long.valueOf(1), result.get("Text Editor"));
        assertEquals(Long.valueOf(1), result.get("Image Editor"));
        assertEquals(Long.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create documents with only TextEditor
        Document doc1 = new Document("Essay.docx", LocalDate.now(), "Author1", 1024L, textEditor);
        Document doc2 = new Document("Notes.txt", LocalDate.now(), "Author2", 512L, textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Count documents by editor
        Map<String, Long> result = fileSystem.countDocumentsByEditor();
        
        // Verify only TextEditor has documents
        assertEquals(Long.valueOf(2), result.get("Text Editor"));
        assertNull(result.get("Image Editor"));
        assertNull(result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create and add documents
        Document doc1 = new Document("Image1.png", LocalDate.now(), "Author1", 2048L, imageEditor);
        Document doc2 = new Document("Video1.mp4", LocalDate.now(), "Author2", 4096L, videoEditor);
        Document doc3 = new Document("Text1.docx", LocalDate.now(), "Author3", 1024L, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove one document
        fileSystem.removeDocument(doc1);
        
        // Count documents by editor after removal
        Map<String, Long> result = fileSystem.countDocumentsByEditor();
        
        // Verify counts after removal
        assertEquals(Long.valueOf(1), result.get("Text Editor"));
        assertNull(result.get("Image Editor")); // Removed document
        assertEquals(Long.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: FileSystem is empty (no documents added)
        
        // Count documents by editor
        Map<String, Long> result = fileSystem.countDocumentsByEditor();
        
        // Verify all editors have 0 documents (map should be empty)
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create documents with all editor types
        Document doc1 = new Document("Doc1.txt", LocalDate.now(), "Author1", 1024L, textEditor);
        Document doc2 = new Document("Pic1.jpg", LocalDate.now(), "Author2", 2048L, imageEditor);
        Document doc3 = new Document("Clip1.mpg", LocalDate.now(), "Author3", 4096L, videoEditor);
        Document doc4 = new Document("Doc2.txt", LocalDate.now(), "Author4", 512L, textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Count documents by editor
        Map<String, Long> result = fileSystem.countDocumentsByEditor();
        
        // Verify expected counts
        assertEquals(Long.valueOf(2), result.get("Text Editor"));
        assertEquals(Long.valueOf(1), result.get("Image Editor"));
        assertEquals(Long.valueOf(1), result.get("Video Editor"));
    }
}