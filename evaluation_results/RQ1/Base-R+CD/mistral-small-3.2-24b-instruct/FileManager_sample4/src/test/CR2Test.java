import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR2Test {
    
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        videoEditor = new VideoEditor();
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
        
        // Call the method to compute average size for Text Editor
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, averageSizes.get("Text Editor"), 0.01f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: Create editors but associate Image Editor with no documents
        // Only add empty editor list, no documents for Image Editor
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor); // Only Text Editor has documents
        
        fileSystem.addDocument(doc1);
        
        // Call the method to compute average sizes
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();
        
        // Image Editor should not be in the result map since it has no documents
        assertFalse(averageSizes.containsKey("Image Editor"));
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
        
        // Call the method to compute average size for Video Editor
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, averageSizes.get("Video Editor"), 0.01f);
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
        
        // Call the method to compute average sizes for each editor
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: 
        // Text Editor Average size = (100 + 200) / 2 = 150
        // Image Editor Average size = (1024 + 1536 + 512) / 3 = 1024
        // Video Editor Average size = 2048
        assertEquals(150.0f, averageSizes.get("Text Editor"), 0.01f);
        assertEquals(1024.0f, averageSizes.get("Image Editor"), 0.01f);
        assertEquals(2048.0f, averageSizes.get("Video Editor"), 0.01f);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // SetUp: Associate Text Editor with 100 documents, each 10 in size
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average size for Text Editor
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = (10 * 100) / 100 = 10
        assertEquals(10.0f, averageSizes.get("Text Editor"), 0.01f);
    }
}