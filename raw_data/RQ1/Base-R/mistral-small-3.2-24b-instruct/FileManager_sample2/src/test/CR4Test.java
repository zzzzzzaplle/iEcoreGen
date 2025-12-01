import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        // Initialize FileSystem and default editors before each test
        fileSystem = new FileSystem();
        textEditor = new Editor("Text Editor");
        imageEditor = new Editor("Image Editor");
        videoEditor = new Editor("Video Editor");
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create FileSystem instance and add documents with different editor types
        Document doc1 = new Document("Report.docx", new Date(), "Author1", 1024L, textEditor);
        Document doc2 = new Document("Image.png", new Date(), "Author2", 2048L, imageEditor);
        Document doc3 = new Document("Video.mp4", new Date(), "Author3", 4096L, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor type
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Each editor should have exactly 1 document
        assertEquals("TextEditor should have 1 document", Long.valueOf(1), result.get("Text Editor"));
        assertEquals("ImageEditor should have 1 document", Long.valueOf(1), result.get("Image Editor"));
        assertEquals("VideoEditor should have 1 document", Long.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create FileSystem instance and add documents with only TextEditor
        Document doc1 = new Document("Essay.docx", new Date(), "Author1", 1024L, textEditor);
        Document doc2 = new Document("Notes.txt", new Date(), "Author2", 512L, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Only TextEditor should have documents
        assertEquals("TextEditor should have 2 documents", Long.valueOf(2), result.get("Text Editor"));
        assertEquals("ImageEditor should have 0 documents", Long.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor should have 0 documents", Long.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create FileSystem instance, add documents, then remove one
        Document doc1 = new Document("Image1.png", new Date(), "Author1", 2048L, imageEditor);
        Document doc2 = new Document("Video1.mp4", new Date(), "Author2", 4096L, videoEditor);
        Document doc3 = new Document("Text1.docx", new Date(), "Author3", 1024L, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.removeDocument(doc1); // Remove the image document
        
        // Execute: Count documents per editor type after removal
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: ImageEditor should have 0 documents after removal
        assertEquals("TextEditor should have 1 document", Long.valueOf(1), result.get("Text Editor"));
        assertEquals("ImageEditor should have 0 documents", Long.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor should have 1 document", Long.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create FileSystem instance without adding any documents
        // Execute: Count documents per editor type
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: All editors should have 0 documents
        assertEquals("TextEditor should have 0 documents", Long.valueOf(0), result.get("Text Editor"));
        assertEquals("ImageEditor should have 0 documents", Long.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor should have 0 documents", Long.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create FileSystem instance and add documents using all editor types
        Document doc1 = new Document("Doc1.txt", new Date(), "Author1", 1024L, textEditor);
        Document doc2 = new Document("Pic1.jpg", new Date(), "Author2", 2048L, imageEditor);
        Document doc3 = new Document("Clip1.mpg", new Date(), "Author3", 4096L, videoEditor);
        Document doc4 = new Document("Doc2.txt", new Date(), "Author4", 512L, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Long> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: TextEditor should have 2 documents, others should have 1 each
        assertEquals("TextEditor should have 2 documents", Long.valueOf(2), result.get("Text Editor"));
        assertEquals("ImageEditor should have 1 document", Long.valueOf(1), result.get("Image Editor"));
        assertEquals("VideoEditor should have 1 document", Long.valueOf(1), result.get("Video Editor"));
    }
}