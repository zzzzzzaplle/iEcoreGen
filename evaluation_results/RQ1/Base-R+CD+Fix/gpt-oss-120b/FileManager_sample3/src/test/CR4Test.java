import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
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
        // SetUp: Create documents with different editor types
        Document doc1 = new Document("Report.docx", new Date(), "Author1", 100, textEditor);
        Document doc2 = new Document("Image.png", new Date(), "Author2", 200, imageEditor);
        Document doc3 = new Document("Video.mp4", new Date(), "Author3", 300, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Each editor type should have exactly 1 document
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create documents with only TextEditor
        Document doc1 = new Document("Essay.docx", new Date(), "Author1", 100, textEditor);
        Document doc2 = new Document("Notes.txt", new Date(), "Author2", 150, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Only TextEditor should have documents
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertNull(result.get("Image Editor"));
        assertNull(result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create documents with different editors and remove one
        Document doc1 = new Document("Image1.png", new Date(), "Author1", 200, imageEditor);
        Document doc2 = new Document("Video1.mp4", new Date(), "Author2", 300, videoEditor);
        Document doc3 = new Document("Text1.docx", new Date(), "Author3", 100, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents per editor type after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: ImageEditor should have 0 documents after removal
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertNull(result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: FileSystem with no documents added
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: All editor counts should be null (not present in map)
        assertNull(result.get("Text Editor"));
        assertNull(result.get("Image Editor"));
        assertNull(result.get("Video Editor"));
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create documents using all editor types with multiple text documents
        Document doc1 = new Document("Doc1.txt", new Date(), "Author1", 100, textEditor);
        Document doc2 = new Document("Pic1.jpg", new Date(), "Author2", 200, imageEditor);
        Document doc3 = new Document("Clip1.mpg", new Date(), "Author3", 300, videoEditor);
        Document doc4 = new Document("Doc2.txt", new Date(), "Author4", 150, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: TextEditor should have 2, others should have 1
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
}