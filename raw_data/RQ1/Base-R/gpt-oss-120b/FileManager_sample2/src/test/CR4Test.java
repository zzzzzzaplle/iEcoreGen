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
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create FileSystem and add documents with different editors
        Document doc1 = new Document("Report.docx", LocalDate.now(), "Author1", 1024, textEditor);
        Document doc2 = new Document("Image.png", LocalDate.now(), "Author2", 2048, imageEditor);
        Document doc3 = new Document("Video.mp4", LocalDate.now(), "Author3", 4096, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Each editor should have exactly 1 document
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create FileSystem and add documents with only TextEditor
        Document doc1 = new Document("Essay.docx", LocalDate.now(), "Author1", 1024, textEditor);
        Document doc2 = new Document("Notes.txt", LocalDate.now(), "Author2", 512, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Only TextEditor should have documents (2), others should be 0
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertEquals(Integer.valueOf(0), result.get("Image Editor"));
        assertEquals(Integer.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create FileSystem and add documents, then remove one
        Document doc1 = new Document("Image1.png", LocalDate.now(), "Author1", 2048, imageEditor);
        Document doc2 = new Document("Video1.mp4", LocalDate.now(), "Author2", 4096, videoEditor);
        Document doc3 = new Document("Text1.docx", LocalDate.now(), "Author3", 1024, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents per editor type after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: TextEditor and VideoEditor should have 1 each, ImageEditor should be 0
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertEquals(Integer.valueOf(0), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create empty FileSystem (no documents added)
        
        // Execute: Count documents per editor type on empty system
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: All editors should have 0 documents
        assertEquals(Integer.valueOf(0), result.get("Text Editor"));
        assertEquals(Integer.valueOf(0), result.get("Image Editor"));
        assertEquals(Integer.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create FileSystem and add documents with all editor types
        Document doc1 = new Document("Doc1.txt", LocalDate.now(), "Author1", 1024, textEditor);
        Document doc2 = new Document("Pic1.jpg", LocalDate.now(), "Author2", 2048, imageEditor);
        Document doc3 = new Document("Clip1.mpg", LocalDate.now(), "Author3", 4096, videoEditor);
        Document doc4 = new Document("Doc2.txt", LocalDate.now(), "Author4", 512, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: TextEditor should have 2, others should have 1 each
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
}