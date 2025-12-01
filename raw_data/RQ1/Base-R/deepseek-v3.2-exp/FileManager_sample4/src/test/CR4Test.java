import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        // Initialize file system and editors before each test
        fileSystem = new FileSystem();
        
        textEditor = new Editor();
        textEditor.setName("TextEditor");
        
        imageEditor = new Editor();
        imageEditor.setName("ImageEditor");
        
        videoEditor = new Editor();
        videoEditor.setName("VideoEditor");
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create FileSystem and add documents with different editors
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create FileSystem and add documents with only TextEditor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertNull("ImageEditor should not be present", result.get("ImageEditor"));
        assertNull("VideoEditor should not be present", result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create FileSystem and add documents, then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(imageEditor);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(videoEditor);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(textEditor);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents per editor after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertNull("ImageEditor should not be present after removal", result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create empty FileSystem (no documents added)
        
        // Execute: Count documents per editor
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check that map is empty (no editors present)
        assertTrue("Result map should be empty when no documents exist", result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create FileSystem and add multiple documents with all editor types
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
}