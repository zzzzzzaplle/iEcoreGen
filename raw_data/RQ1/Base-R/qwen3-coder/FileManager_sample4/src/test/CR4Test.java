import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR4Test {
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsWithMixedEditorTypes() {
        // SetUp: Create FileSystem and add documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(new VideoEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents by editor type
        int[] counts = fileSystem.countDocumentsByEditor();
        
        // Verify: Each editor type should have exactly 1 document
        assertEquals("TextEditor count should be 1", 1, counts[0]);
        assertEquals("ImageEditor count should be 1", 1, counts[1]);
        assertEquals("VideoEditor count should be 1", 1, counts[2]);
    }
    
    @Test
    public void testCase2_countDocumentsWithSingleEditorType() {
        // SetUp: Create FileSystem and add documents with only TextEditor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents by editor type
        int[] counts = fileSystem.countDocumentsByEditor();
        
        // Verify: Only TextEditor should have documents (2), others should be 0
        assertEquals("TextEditor count should be 2", 2, counts[0]);
        assertEquals("ImageEditor count should be 0", 0, counts[1]);
        assertEquals("VideoEditor count should be 0", 0, counts[2]);
    }
    
    @Test
    public void testCase3_countDocumentsAfterRemoval() {
        // SetUp: Create FileSystem and add documents, then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(new ImageEditor());
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(new VideoEditor());
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the ImageEditor document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents by editor type after removal
        int[] counts = fileSystem.countDocumentsByEditor();
        
        // Verify: ImageEditor count should be 0 after removal
        assertEquals("TextEditor count should be 1", 1, counts[0]);
        assertEquals("ImageEditor count should be 0", 0, counts[1]);
        assertEquals("VideoEditor count should be 1", 1, counts[2]);
    }
    
    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // SetUp: Create empty FileSystem (no documents added)
        // No setup needed beyond the @Before method
        
        // Execute: Count documents by editor type on empty file system
        int[] counts = fileSystem.countDocumentsByEditor();
        
        // Verify: All editor counts should be 0 when no documents exist
        assertEquals("TextEditor count should be 0", 0, counts[0]);
        assertEquals("ImageEditor count should be 0", 0, counts[1]);
        assertEquals("VideoEditor count should be 0", 0, counts[2]);
    }
    
    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() {
        // SetUp: Create FileSystem and add documents using all editor types
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(new VideoEditor());
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents by editor type
        int[] counts = fileSystem.countDocumentsByEditor();
        
        // Verify: TextEditor should have 2, others should have 1 each
        assertEquals("TextEditor count should be 2", 2, counts[0]);
        assertEquals("ImageEditor count should be 1", 1, counts[1]);
        assertEquals("VideoEditor count should be 1", 1, counts[2]);
    }
}