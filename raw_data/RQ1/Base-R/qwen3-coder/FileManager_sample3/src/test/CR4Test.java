import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize a fresh FileSystem instance before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Add documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor("Text Editor");
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor("Image Editor");
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor("Video Editor");
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents by editor
        Map<String, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify: Check counts for each editor type
        assertEquals("TextEditor count should be 1", Integer.valueOf(1), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 1", Integer.valueOf(1), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Add two documents with TextEditor only
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor("Text Editor");
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor("Text Editor");
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents by editor
        Map<String, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify: Only TextEditor should have documents
        assertEquals("TextEditor count should be 2", Integer.valueOf(2), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 0", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 0", Integer.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Add documents and then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor("Image Editor");
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor("Video Editor");
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor("Text Editor");
        fileSystem.addDocument(doc3);
        
        // Remove the ImageEditor document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents by editor after removal
        Map<String, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify: ImageEditor count should be 0 after removal
        assertEquals("TextEditor count should be 1", Integer.valueOf(1), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 0", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: No documents added to FileSystem
        
        // Execute: Count documents by editor when no documents exist
        Map<String, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify: All editors should have 0 documents
        assertEquals("TextEditor count should be 0", Integer.valueOf(0), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 0", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 0", Integer.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Add multiple documents with all editor types
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor("Text Editor");
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor("Image Editor");
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor("Video Editor");
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor("Text Editor");
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents by editor
        Map<String, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify: Correct counts for all editor types
        assertEquals("TextEditor count should be 2", Integer.valueOf(2), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 1", Integer.valueOf(1), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("Video Editor"));
    }
}