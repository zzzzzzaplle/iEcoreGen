import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new Editor();
        imageEditor = new Editor();
        videoEditor = new Editor();
        
        textEditor.setName("Text Editor");
        imageEditor.setName("Image Editor");
        videoEditor.setName("Video Editor");
        
        fileSystem.setEditors(Arrays.asList(textEditor, imageEditor, videoEditor));
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // SetUp: Associate "Text Editor" with 3 documents
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        
        textEditor.addDocument(doc1);
        textEditor.addDocument(doc2);
        textEditor.addDocument(doc3);
        
        // Call the method to compute the average size for "Text Editor"
        double averageSize = textEditor.computeAverageDocumentSize();
        
        // Expected Output: Average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, averageSize, 0.001);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: Associate "Image Editor" with no documents
        // No documents added to imageEditor
        
        // Call the method to compute the average size for "Image Editor"
        double averageSize = imageEditor.computeAverageDocumentSize();
        
        // Expected Output: Average size = 0
        assertEquals(0.0, averageSize, 0.001);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // SetUp: Associate "Video Editor" with 4 documents
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        
        videoEditor.addDocument(doc1);
        videoEditor.addDocument(doc2);
        videoEditor.addDocument(doc3);
        videoEditor.addDocument(doc4);
        
        // Call the method to compute the average size for "Video Editor"
        double averageSize = videoEditor.computeAverageDocumentSize();
        
        // Expected Output: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5, averageSize, 0.001);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // SetUp: Associate each editor with documents
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textEditor.addDocument(textDoc1);
        textEditor.addDocument(textDoc2);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageEditor.addDocument(imageDoc1);
        imageEditor.addDocument(imageDoc2);
        imageEditor.addDocument(imageDoc3);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document();
        videoDoc1.setSize(2048);
        videoEditor.addDocument(videoDoc1);
        
        // Call the method to compute the average size for each editor
        double textAverage = textEditor.computeAverageDocumentSize();
        double imageAverage = imageEditor.computeAverageDocumentSize();
        double videoAverage = videoEditor.computeAverageDocumentSize();
        
        // Expected Output: 
        // "Text Editor" Average size = (100 + 200) / 2 = 150
        assertEquals(150.0, textAverage, 0.001);
        
        // "Image Editor" Average size = (1024 + 1536 + 512) / 3 = 1024
        assertEquals(1024.0, imageAverage, 0.001);
        
        // "Video Editor" Average size = 2048
        assertEquals(2048.0, videoAverage, 0.001);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // SetUp: Associate "Text Editor" with 100 documents, each 10 in size
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setSize(10);
            textEditor.addDocument(doc);
        }
        
        // Call the method to compute the average size for "Text Editor"
        double averageSize = textEditor.computeAverageDocumentSize();
        
        // Expected Output: Average size = (10 * 100) / 100 = 10
        assertEquals(10.0, averageSize, 0.001);
    }
}