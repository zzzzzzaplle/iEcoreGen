import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR2Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new Editor();
        textEditor.setName("Text Editor");
        imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        videoEditor = new Editor();
        videoEditor.setName("Video Editor");
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // SetUp: Create editors and associate Text Editor with 3 documents
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute average sizes
        double[] averages = fileSystem.computeAverageSizeByEditor();
        
        // Expected Output: Text Editor average = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, averages[0], 0.001); // Text Editor at index 0
        assertEquals(0.0, averages[1], 0.001);   // Image Editor at index 1
        assertEquals(0.0, averages[2], 0.001);   // Video Editor at index 2
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: Create editors but don't add any documents for Image Editor
        // No documents added to file system
        
        // Call the method to compute average sizes
        double[] averages = fileSystem.computeAverageSizeByEditor();
        
        // Expected Output: All averages should be 0 since no documents exist
        assertEquals(0.0, averages[0], 0.001); // Text Editor
        assertEquals(0.0, averages[1], 0.001); // Image Editor
        assertEquals(0.0, averages[2], 0.001); // Video Editor
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // SetUp: Associate Video Editor with 4 documents of varying sizes
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute average sizes
        double[] averages = fileSystem.computeAverageSizeByEditor();
        
        // Expected Output: Video Editor average = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(0.0, averages[0], 0.001);    // Text Editor
        assertEquals(0.0, averages[1], 0.001);    // Image Editor
        assertEquals(637.5, averages[2], 0.001);  // Video Editor at index 2
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // SetUp: Associate each editor with specified documents
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        
        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        
        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        
        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document();
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call the method to compute average sizes
        double[] averages = fileSystem.computeAverageSizeByEditor();
        
        // Expected Output: 
        // Text Editor average = (100 + 200) / 2 = 150
        // Image Editor average = (1024 + 1536 + 512) / 3 = 1024
        // Video Editor average = 2048
        assertEquals(150.0, averages[0], 0.001);   // Text Editor
        assertEquals(1024.0, averages[1], 0.001);  // Image Editor
        assertEquals(2048.0, averages[2], 0.001);  // Video Editor
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // SetUp: Associate Text Editor with 100 documents, each of size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average sizes
        double[] averages = fileSystem.computeAverageSizeByEditor();
        
        // Expected Output: Text Editor average = (10 * 100) / 100 = 10
        assertEquals(10.0, averages[0], 0.001);  // Text Editor
        assertEquals(0.0, averages[1], 0.001);   // Image Editor
        assertEquals(0.0, averages[2], 0.001);   // Video Editor
    }
}