import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR2Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // SetUp: Create editors and associate "Text Editor" with 3 documents
        Editor textEditor = new Editor("Text Editor");
        
        Document doc1 = new Document("Report", new Date(), "Author1", 150L, textEditor);
        Document doc2 = new Document("Essay", new Date(), "Author2", 200L, textEditor);
        Document doc3 = new Document("Proposal", new Date(), "Author3", 250L, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify "Text Editor" average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, result.get("Text Editor"), 0.001);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // SetUp: Create editors but add no documents for "Image Editor"
        // No documents need to be added since fileSystem is initialized with empty document list
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify "Image Editor" average size = 0
        assertEquals(0.0, result.get("Image Editor"), 0.001);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // SetUp: Create editors and associate "Video Editor" with 4 documents of varying sizes
        Editor videoEditor = new Editor("Video Editor");
        
        Document doc1 = new Document("Video1", new Date(), "Author1", 500L, videoEditor);
        Document doc2 = new Document("Video2", new Date(), "Author2", 1000L, videoEditor);
        Document doc3 = new Document("Video3", new Date(), "Author3", 750L, videoEditor);
        Document doc4 = new Document("Video4", new Date(), "Author4", 300L, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify "Video Editor" average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // SetUp: Create editors and associate each with documents
        Editor textEditor = new Editor("Text Editor");
        Editor imageEditor = new Editor("Image Editor");
        Editor videoEditor = new Editor("Video Editor");
        
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document("Doc1", new Date(), "Author1", 100L, textEditor);
        Document textDoc2 = new Document("Doc2", new Date(), "Author2", 200L, textEditor);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document("Image1", new Date(), "Author3", 1024L, imageEditor);
        Document imageDoc2 = new Document("Image2", new Date(), "Author4", 1536L, imageEditor);
        Document imageDoc3 = new Document("Image3", new Date(), "Author5", 512L, imageEditor);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document("Video1", new Date(), "Author6", 2048L, videoEditor);
        
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify expected averages
        assertEquals(150.0, result.get("Text Editor"), 0.001);  // (100 + 200) / 2 = 150
        assertEquals(1024.0, result.get("Image Editor"), 0.001); // (1024 + 1536 + 512) / 3 = 1024
        assertEquals(2048.0, result.get("Video Editor"), 0.001); // 2048 / 1 = 2048
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // SetUp: Create "Text Editor" and associate with 100 documents, each of size 10
        Editor textEditor = new Editor("Text Editor");
        
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document("Document" + i, new Date(), "Author" + i, 10L, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify "Text Editor" average size = (10 * 100) / 100 = 10
        assertEquals(10.0, result.get("Text Editor"), 0.001);
    }
}