import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize FileSystem before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor("Text Editor");
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor("Image Editor");
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor("Video Editor");
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Count documents by editor
        Map<String, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify expected counts
        assertEquals("TextEditor count should be 1", Integer.valueOf(1), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 1", Integer.valueOf(1), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create documents with only Text Editor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor("Text Editor");
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor("Text Editor");
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Count documents by editor
        Map<String, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify expected counts
        assertEquals("TextEditor count should be 2", Integer.valueOf(2), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 0", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 0", Integer.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create documents with different editors and remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor("Image Editor");
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor("Video Editor");
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor("Text Editor");
        
        // Add all documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the Image Editor document
        fileSystem.removeDocument(doc1);
        
        // Count documents by editor
        Map<String, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify expected counts after removal
        assertEquals("TextEditor count should be 1", Integer.valueOf(1), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 0", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: No documents added to file system
        
        // Count documents by editor
        Map<String, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify all editors have 0 documents
        assertEquals("TextEditor count should be 0", Integer.valueOf(0), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 0", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 0", Integer.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create multiple documents with different editors
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor("Text Editor");
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor("Image Editor");
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor("Video Editor");
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor("Text Editor");
        
        // Add all documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Count documents by editor
        Map<String, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify expected counts
        assertEquals("TextEditor count should be 2", Integer.valueOf(2), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 1", Integer.valueOf(1), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("Video Editor"));
    }
}