import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // SetUp: Create editors and associate "Text Editor" with 3 documents
        Document doc1 = new Document("Report", LocalDate.now(), "Author1", 150, textEditor);
        Document doc2 = new Document("Essay", LocalDate.now(), "Author2", 200, textEditor);
        Document doc3 = new Document("Proposal", LocalDate.now(), "Author3", 250, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizeByEditor();
        
        // Verify only "Text Editor" is present in results
        assertEquals(1, result.size());
        assertTrue(result.containsKey("Text Editor"));
        
        // Expected Output: Average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, result.get("Text Editor"), 0.001);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // SetUp: Create editors but associate "Image Editor" with no documents
        // No documents added to file system
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizeByEditor();
        
        // Expected Output: No editors should be present in results
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // SetUp: Associate "Video Editor" with 4 documents of varying sizes
        Document doc1 = new Document("Video1", LocalDate.now(), "Author1", 500, videoEditor);
        Document doc2 = new Document("Video2", LocalDate.now(), "Author2", 1000, videoEditor);
        Document doc3 = new Document("Video3", LocalDate.now(), "Author3", 750, videoEditor);
        Document doc4 = new Document("Video4", LocalDate.now(), "Author4", 300, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizeByEditor();
        
        // Verify only "Video Editor" is present in results
        assertEquals(1, result.size());
        assertTrue(result.containsKey("Video Editor"));
        
        // Expected Output: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // SetUp: Associate each editor with specified documents
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document("Text1", LocalDate.now(), "Author1", 100, textEditor);
        Document textDoc2 = new Document("Text2", LocalDate.now(), "Author2", 200, textEditor);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document("Image1", LocalDate.now(), "Author3", 1024, imageEditor);
        Document imageDoc2 = new Document("Image2", LocalDate.now(), "Author4", 1536, imageEditor);
        Document imageDoc3 = new Document("Image3", LocalDate.now(), "Author5", 512, imageEditor);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document("Video1", LocalDate.now(), "Author6", 2048, videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizeByEditor();
        
        // Verify all three editors are present in results
        assertEquals(3, result.size());
        
        // Expected Output: 
        // "Text Editor" Average size = (100 + 200) / 2 = 150
        assertEquals(150.0, result.get("Text Editor"), 0.001);
        
        // "Image Editor" Average size = (1024 + 1536 + 512) / 3 = 1024
        assertEquals(1024.0, result.get("Image Editor"), 0.001);
        
        // "Video Editor" Average size = 2048
        assertEquals(2048.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // SetUp: Associate "Text Editor" with 100 documents, each 10 in size
        for (int i = 0; i < 100; i++) {
            Document doc = new Document("Doc" + i, LocalDate.now(), "Author" + i, 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizeByEditor();
        
        // Verify only "Text Editor" is present in results
        assertEquals(1, result.size());
        assertTrue(result.containsKey("Text Editor"));
        
        // Expected Output: Average size = (10 * 100) / 100 = 10
        assertEquals(10.0, result.get("Text Editor"), 0.001);
    }
}