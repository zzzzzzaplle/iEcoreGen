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
        fileSystem = new FileSystem();
        // Get the default editors created in FileSystem constructor
        textEditor = new Editor("Text Editor");
        imageEditor = new Editor("Image Editor");
        videoEditor = new Editor("Video Editor");
    }

    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Create documents for Text Editor
        Document doc1 = new Document("Report", new Date(), "Author1", 150, textEditor);
        Document doc2 = new Document("Essay", new Date(), "Author2", 200, textEditor);
        Document doc3 = new Document("Proposal", new Date(), "Author3", 250, textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average size
        assertEquals(200.0, averageSizes.get("Text Editor"), 0.001);
    }

    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // No documents added for Image Editor
        
        // Compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify Image Editor average size is 0
        assertEquals(0.0, averageSizes.get("Image Editor"), 0.001);
    }

    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Create documents for Video Editor with varying sizes
        Document doc1 = new Document("Video1", new Date(), "Author1", 500, videoEditor);
        Document doc2 = new Document("Video2", new Date(), "Author2", 1000, videoEditor);
        Document doc3 = new Document("Video3", new Date(), "Author3", 750, videoEditor);
        Document doc4 = new Document("Video4", new Date(), "Author4", 300, videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify Video Editor average size
        assertEquals(637.5, averageSizes.get("Video Editor"), 0.001);
    }

    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Create documents for Text Editor
        Document textDoc1 = new Document("Doc1", new Date(), "Author1", 100, textEditor);
        Document textDoc2 = new Document("Doc2", new Date(), "Author2", 200, textEditor);
        
        // Create documents for Image Editor
        Document imageDoc1 = new Document("Image1", new Date(), "Author3", 1024, imageEditor);
        Document imageDoc2 = new Document("Image2", new Date(), "Author4", 1536, imageEditor);
        Document imageDoc3 = new Document("Image3", new Date(), "Author5", 512, imageEditor);
        
        // Create document for Video Editor
        Document videoDoc1 = new Document("Video1", new Date(), "Author6", 2048, videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify all editor average sizes
        assertEquals(150.0, averageSizes.get("Text Editor"), 0.001);
        assertEquals(1024.0, averageSizes.get("Image Editor"), 0.001);
        assertEquals(2048.0, averageSizes.get("Video Editor"), 0.001);
    }

    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Create 100 documents for Text Editor, each with size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document("Document" + i, new Date(), "Author" + i, 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average size
        assertEquals(10.0, averageSizes.get("Text Editor"), 0.001);
    }
}