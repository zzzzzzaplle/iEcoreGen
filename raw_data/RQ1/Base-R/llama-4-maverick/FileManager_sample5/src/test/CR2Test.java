import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new Editor("Text Editor");
        imageEditor = new Editor("Image Editor");
        videoEditor = new Editor("Video Editor");
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // SetUp: Create documents for Text Editor
        Document doc1 = new Document("Report", LocalDate.of(2022, 1, 1), "Author1", 150, textEditor);
        Document doc2 = new Document("Essay", LocalDate.of(2022, 1, 2), "Author2", 200, textEditor);
        Document doc3 = new Document("Proposal", LocalDate.of(2022, 1, 3), "Author3", 250, textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call method to compute average size per editor
        String result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size = (150 + 200 + 250) / 3 = 200
        String expected = "Text Editor: 200.0";
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // SetUp: No documents added to Image Editor
        
        // Call method to compute average size per editor
        String result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size = 0 for Image Editor
        // Since no documents exist, the result should be an empty string
        assertEquals("", result);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // SetUp: Create documents for Video Editor with varying sizes
        Document doc1 = new Document("Video1", LocalDate.of(2022, 1, 1), "Author1", 500, videoEditor);
        Document doc2 = new Document("Video2", LocalDate.of(2022, 1, 2), "Author2", 1000, videoEditor);
        Document doc3 = new Document("Video3", LocalDate.of(2022, 1, 3), "Author3", 750, videoEditor);
        Document doc4 = new Document("Video4", LocalDate.of(2022, 1, 4), "Author4", 300, videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call method to compute average size per editor
        String result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        String expected = "Video Editor: 637.5";
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // SetUp: Create documents for all three editors
        // Text Editor: 2 documents
        Document textDoc1 = new Document("Text1", LocalDate.of(2022, 1, 1), "Author1", 100, textEditor);
        Document textDoc2 = new Document("Text2", LocalDate.of(2022, 1, 2), "Author2", 200, textEditor);
        
        // Image Editor: 3 documents
        Document imageDoc1 = new Document("Image1", LocalDate.of(2022, 1, 3), "Author3", 1024, imageEditor);
        Document imageDoc2 = new Document("Image2", LocalDate.of(2022, 1, 4), "Author4", 1536, imageEditor);
        Document imageDoc3 = new Document("Image3", LocalDate.of(2022, 1, 5), "Author5", 512, imageEditor);
        
        // Video Editor: 1 document
        Document videoDoc1 = new Document("Video1", LocalDate.of(2022, 1, 6), "Author6", 2048, videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call method to compute average size per editor
        String result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: 
        // Text Editor: 150.0, Image Editor: 1024.0, Video Editor: 2048.0
        // Note: The order in the result string might vary, so we need to check all expected values
        assertTrue(result.contains("Text Editor: 150.0"));
        assertTrue(result.contains("Image Editor: 1024.0"));
        assertTrue(result.contains("Video Editor: 2048.0"));
        
        // Verify all three editors are present
        assertEquals(3, result.split(", ").length);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // SetUp: Create 100 documents for Text Editor, each with size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document("Doc" + i, LocalDate.of(2022, 1, i), "Author" + i, 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call method to compute average size per editor
        String result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size = (10 * 100) / 100 = 10
        String expected = "Text Editor: 10.0";
        assertEquals(expected, result);
    }
}