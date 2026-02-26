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
        // Initialize FileSystem and editors before each test
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create two documents with TextEditor only
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 0 documents", Integer.valueOf(0), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create three documents with different editors
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts after removal
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: FileSystem is already created with no documents (via @Before)
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify all editors have 0 documents
        assertEquals("TextEditor should have 0 documents", Integer.valueOf(0), result.get("TextEditor"));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 0 documents", Integer.valueOf(0), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create four documents with all editors used
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected counts
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
}