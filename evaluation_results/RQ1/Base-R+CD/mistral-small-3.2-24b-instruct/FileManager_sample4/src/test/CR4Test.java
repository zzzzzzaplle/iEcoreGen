import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create FileSystem instance and add documents with mixed editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(new TextEditor());
        ((TextEditor) doc1.getEditor()).setName("TextEditor");
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(new ImageEditor());
        ((ImageEditor) doc2.getEditor()).setName("ImageEditor");
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(new VideoEditor());
        ((VideoEditor) doc3.getEditor()).setName("VideoEditor");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Count the number of documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output: TextEditor: 1, ImageEditor: 1, VideoEditor: 1
        assertEquals(Integer.valueOf(1), result.get("TextEditor"));
        assertEquals(Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create FileSystem instance and add documents with only TextEditor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(new TextEditor());
        ((TextEditor) doc1.getEditor()).setName("TextEditor");
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(new TextEditor());
        ((TextEditor) doc2.getEditor()).setName("TextEditor");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Count the number of documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output: TextEditor: 2, ImageEditor: 0, VideoEditor: 0
        assertEquals(Integer.valueOf(2), result.get("TextEditor"));
        assertNull(result.get("ImageEditor"));
        assertNull(result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create FileSystem instance, add documents, then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(new ImageEditor());
        ((ImageEditor) doc1.getEditor()).setName("ImageEditor");
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(new VideoEditor());
        ((VideoEditor) doc2.getEditor()).setName("VideoEditor");
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(new TextEditor());
        ((TextEditor) doc3.getEditor()).setName("TextEditor");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the Image1.png document
        fileSystem.removeDocument(doc1);
        
        // Input: Count the number of documents per editor type after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output: TextEditor: 1, ImageEditor: 0, VideoEditor: 1
        assertEquals(Integer.valueOf(1), result.get("TextEditor"));
        assertNull(result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create FileSystem instance with no documents
        // No documents added to fileSystem
        
        // Input: Count the number of documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output: TextEditor: 0, ImageEditor: 0, VideoEditor: 0
        // Since no documents exist, the result map should be empty
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create FileSystem instance and add multiple documents using all editor types
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(new TextEditor());
        ((TextEditor) doc1.getEditor()).setName("TextEditor");
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(new ImageEditor());
        ((ImageEditor) doc2.getEditor()).setName("ImageEditor");
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(new VideoEditor());
        ((VideoEditor) doc3.getEditor()).setName("VideoEditor");
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(new TextEditor());
        ((TextEditor) doc4.getEditor()).setName("TextEditor");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Input: Count the number of documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output: TextEditor: 2, ImageEditor: 1, VideoEditor: 1
        assertEquals(Integer.valueOf(2), result.get("TextEditor"));
        assertEquals(Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
}