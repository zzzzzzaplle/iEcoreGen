import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create FileSystem and add documents with different editors
        Document doc1 = new Document("Report.docx", LocalDate.now(), new Author("Author1"), 1000L, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Image.png", LocalDate.now(), new Author("Author2"), 2000L, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Video.mp4", LocalDate.now(), new Author("Author3"), 3000L, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Verify expected counts for each editor type
        assertEquals(Long.valueOf(1), result.get("Text Editor"));
        assertEquals(Long.valueOf(1), result.get("Image Editor"));
        assertEquals(Long.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create FileSystem and add documents with only TextEditor
        Document doc1 = new Document("Essay.docx", LocalDate.now(), new Author("Author1"), 1500L, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Notes.txt", LocalDate.now(), new Author("Author2"), 800L, Editor.TEXT_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Verify only TextEditor has documents
        assertEquals(Long.valueOf(2), result.get("Text Editor"));
        assertEquals(Long.valueOf(0), result.get("Image Editor"));
        assertEquals(Long.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create FileSystem and add documents, then remove one
        Document doc1 = new Document("Image1.png", LocalDate.now(), new Author("Author1"), 2500L, Editor.IMAGE_EDITOR);
        Document doc2 = new Document("Video1.mp4", LocalDate.now(), new Author("Author2"), 5000L, Editor.VIDEO_EDITOR);
        Document doc3 = new Document("Text1.docx", LocalDate.now(), new Author("Author3"), 1200L, Editor.TEXT_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the ImageEditor document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents per editor after removal
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Verify counts after removal
        assertEquals(Long.valueOf(1), result.get("Text Editor"));
        assertEquals(Long.valueOf(0), result.get("Image Editor"));
        assertEquals(Long.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create FileSystem without adding any documents
        // No documents added to fileSystem
        
        // Execute: Count documents per editor on empty file system
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Verify all editors have zero counts
        assertEquals(Long.valueOf(0), result.get("Text Editor"));
        assertEquals(Long.valueOf(0), result.get("Image Editor"));
        assertEquals(Long.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create FileSystem and add documents using all editor types
        Document doc1 = new Document("Doc1.txt", LocalDate.now(), new Author("Author1"), 1000L, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Pic1.jpg", LocalDate.now(), new Author("Author2"), 3000L, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Clip1.mpg", LocalDate.now(), new Author("Author3"), 7000L, Editor.VIDEO_EDITOR);
        Document doc4 = new Document("Doc2.txt", LocalDate.now(), new Author("Author4"), 1500L, Editor.TEXT_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Verify counts for all editors
        assertEquals(Long.valueOf(2), result.get("Text Editor"));
        assertEquals(Long.valueOf(1), result.get("Image Editor"));
        assertEquals(Long.valueOf(1), result.get("Video Editor"));
    }
}