import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR2Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        // Create a new FileSystem and editors before each test
        fileSystem = new FileSystem();
        textEditor = new Editor("Text Editor");
        imageEditor = new Editor("Image Editor");
        videoEditor = new Editor("Video Editor");
        
        // Add all editors to the file system
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Test Case 1: Single Editor with Multiple Documents
        // SetUp: Associate "Text Editor" with 3 documents
        Document doc1 = new Document("Report", new Date(), "Author1", 150, textEditor);
        Document doc2 = new Document("Essay", new Date(), "Author2", 200, textEditor);
        Document doc3 = new Document("Proposal", new Date(), "Author3", 250, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size for "Text Editor" = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, result.get("Text Editor"), 0.001);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Test Case 2: No Documents for an Editor
        // SetUp: Associate "Image Editor" with no documents
        // (No documents added for Image Editor)
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size for "Image Editor" = 0
        assertEquals(0.0, result.get("Image Editor"), 0.001);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Test Case 3: Editor with Documents of Varying Sizes
        // SetUp: Associate "Video Editor" with 4 documents
        Document doc1 = new Document("Video1", new Date(), "Author1", 500, videoEditor);
        Document doc2 = new Document("Video2", new Date(), "Author2", 1000, videoEditor);
        Document doc3 = new Document("Video3", new Date(), "Author3", 750, videoEditor);
        Document doc4 = new Document("Video4", new Date(), "Author4", 300, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size for "Video Editor" = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Test Case 4: Multiple Editors with Document Distribution
        // SetUp: Associate each editor with documents
        
        // Text Editor: 2 documents (100, 200)
        Document doc1 = new Document("Doc1", new Date(), "Author1", 100, textEditor);
        Document doc2 = new Document("Doc2", new Date(), "Author2", 200, textEditor);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document doc3 = new Document("Doc3", new Date(), "Author3", 1024, imageEditor);
        Document doc4 = new Document("Doc4", new Date(), "Author4", 1536, imageEditor);
        Document doc5 = new Document("Doc5", new Date(), "Author5", 512, imageEditor);
        
        // Video Editor: 1 document (2048)
        Document doc6 = new Document("Doc6", new Date(), "Author6", 2048, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        fileSystem.addDocument(doc5);
        fileSystem.addDocument(doc6);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output:
        // "Text Editor" Average size = (100 + 200) / 2 = 150
        // "Image Editor" Average size = (1024 + 1536 + 512) / 3 = 1024
        // "Video Editor" Average size = 2048
        assertEquals(150.0, result.get("Text Editor"), 0.001);
        assertEquals(1024.0, result.get("Image Editor"), 0.001);
        assertEquals(2048.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Test Case 5: Large Number of Documents for One Editor
        // SetUp: Associate "Text Editor" with 100 documents, each 10 in size
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document("Document" + i, new Date(), "Author" + i, 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size for "Text Editor" = (10 * 100) / 100 = 10
        assertEquals(10.0, result.get("Text Editor"), 0.001);
    }
}