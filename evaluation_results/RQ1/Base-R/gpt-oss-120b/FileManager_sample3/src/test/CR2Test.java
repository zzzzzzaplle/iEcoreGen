import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR2Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = Editor.TEXT_EDITOR;
        imageEditor = Editor.IMAGE_EDITOR;
        videoEditor = Editor.VIDEO_EDITOR;
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // SetUp: Create editors and associate "Text Editor" with 3 documents
        Author author1 = new Author("Author1");
        Author author2 = new Author("Author2");
        Author author3 = new Author("Author3");
        
        Document doc1 = new Document("Report", LocalDate.now(), author1, 150L, textEditor);
        Document doc2 = new Document("Essay", LocalDate.now(), author2, 200L, textEditor);
        Document doc3 = new Document("Proposal", LocalDate.now(), author3, 250L, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, result.get("Text Editor"), 0.001);
        assertEquals(0.0, result.get("Image Editor"), 0.001);
        assertEquals(0.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: Create editors with no documents for Image Editor
        Author author1 = new Author("Author1");
        Document doc1 = new Document("Report", LocalDate.now(), author1, 150L, textEditor);
        fileSystem.addDocument(doc1);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Image Editor average size = 0
        assertEquals(0.0, result.get("Image Editor"), 0.001);
        assertEquals(150.0, result.get("Text Editor"), 0.001);
        assertEquals(0.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // SetUp: Associate "Video Editor" with 4 documents of varying sizes
        Author author1 = new Author("Author1");
        Author author2 = new Author("Author2");
        Author author3 = new Author("Author3");
        Author author4 = new Author("Author4");
        
        Document doc1 = new Document("Video1", LocalDate.now(), author1, 500L, videoEditor);
        Document doc2 = new Document("Video2", LocalDate.now(), author2, 1000L, videoEditor);
        Document doc3 = new Document("Video3", LocalDate.now(), author3, 750L, videoEditor);
        Document doc4 = new Document("Video4", LocalDate.now(), author4, 300L, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5, result.get("Video Editor"), 0.001);
        assertEquals(0.0, result.get("Text Editor"), 0.001);
        assertEquals(0.0, result.get("Image Editor"), 0.001);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // SetUp: Associate each editor with documents as specified
        Author author1 = new Author("Author1");
        Author author2 = new Author("Author2");
        Author author3 = new Author("Author3");
        Author author4 = new Author("Author4");
        Author author5 = new Author("Author5");
        Author author6 = new Author("Author6");
        
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document("Text1", LocalDate.now(), author1, 100L, textEditor);
        Document textDoc2 = new Document("Text2", LocalDate.now(), author2, 200L, textEditor);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document("Image1", LocalDate.now(), author3, 1024L, imageEditor);
        Document imageDoc2 = new Document("Image2", LocalDate.now(), author4, 1536L, imageEditor);
        Document imageDoc3 = new Document("Image3", LocalDate.now(), author5, 512L, imageEditor);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document("Video1", LocalDate.now(), author6, 2048L, videoEditor);
        
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output:
        // - "Text Editor" Average size = (100 + 200) / 2 = 150
        // - "Image Editor" Average size = (1024 + 1536 + 512) / 3 = 1024
        // - "Video Editor" Average size = 2048
        assertEquals(150.0, result.get("Text Editor"), 0.001);
        assertEquals(1024.0, result.get("Image Editor"), 0.001);
        assertEquals(2048.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // SetUp: Associate "Text Editor" with 100 documents, each 10 in size
        for (int i = 1; i <= 100; i++) {
            Author author = new Author("Author" + i);
            Document doc = new Document("Document" + i, LocalDate.now(), author, 10L, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Expected Output: Average size = (10 * 100) / 100 = 10
        assertEquals(10.0, result.get("Text Editor"), 0.001);
        assertEquals(0.0, result.get("Image Editor"), 0.001);
        assertEquals(0.0, result.get("Video Editor"), 0.001);
    }
}