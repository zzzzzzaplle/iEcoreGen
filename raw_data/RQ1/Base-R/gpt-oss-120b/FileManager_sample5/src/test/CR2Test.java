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
        // Initialize file system and predefined editors before each test
        fileSystem = new FileSystem();
        textEditor = Editor.TEXT_EDITOR;
        imageEditor = Editor.IMAGE_EDITOR;
        videoEditor = Editor.VIDEO_EDITOR;
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Test Case 1: Single Editor with Multiple Documents
        // SetUp: Associate "Text Editor" with 3 documents
        
        // Create documents for Text Editor
        Document doc1 = new Document("Report", LocalDate.now(), "Author1", 150, textEditor);
        Document doc2 = new Document("Essay", LocalDate.now(), "Author2", 200, textEditor);
        Document doc3 = new Document("Proposal", LocalDate.now(), "Author3", 250, textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute average sizes
        Map<Editor, Double> result = fileSystem.getAverageSizeByEditor();
        
        // Verify Text Editor average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, result.get(textEditor), 0.001);
        // Verify other editors have 0 average size (no documents assigned)
        assertEquals(0.0, result.get(imageEditor), 0.001);
        assertEquals(0.0, result.get(videoEditor), 0.001);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Test Case 2: No Documents for an Editor
        // SetUp: Associate "Image Editor" with no documents
        
        // Call the method to compute average sizes (file system is empty)
        Map<Editor, Double> result = fileSystem.getAverageSizeByEditor();
        
        // Verify all editors have 0 average size (no documents in system)
        assertEquals(0.0, result.get(textEditor), 0.001);
        assertEquals(0.0, result.get(imageEditor), 0.001);
        assertEquals(0.0, result.get(videoEditor), 0.001);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Test Case 3: Editor with Documents of Varying Sizes
        // SetUp: Associate "Video Editor" with 4 documents of varying sizes
        
        // Create documents for Video Editor
        Document doc1 = new Document("Video1", LocalDate.now(), "Author1", 500, videoEditor);
        Document doc2 = new Document("Video2", LocalDate.now(), "Author2", 1000, videoEditor);
        Document doc3 = new Document("Video3", LocalDate.now(), "Author3", 750, videoEditor);
        Document doc4 = new Document("Video4", LocalDate.now(), "Author4", 300, videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute average sizes
        Map<Editor, Double> result = fileSystem.getAverageSizeByEditor();
        
        // Verify Video Editor average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5, result.get(videoEditor), 0.001);
        // Verify other editors have 0 average size (no documents assigned)
        assertEquals(0.0, result.get(textEditor), 0.001);
        assertEquals(0.0, result.get(imageEditor), 0.001);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Test Case 4: Multiple Editors with Document Distribution
        // SetUp: Associate each editor with documents
        
        // Create documents for Text Editor
        Document textDoc1 = new Document("Text1", LocalDate.now(), "Author1", 100, textEditor);
        Document textDoc2 = new Document("Text2", LocalDate.now(), "Author2", 200, textEditor);
        
        // Create documents for Image Editor
        Document imageDoc1 = new Document("Image1", LocalDate.now(), "Author3", 1024, imageEditor);
        Document imageDoc2 = new Document("Image2", LocalDate.now(), "Author4", 1536, imageEditor);
        Document imageDoc3 = new Document("Image3", LocalDate.now(), "Author5", 512, imageEditor);
        
        // Create document for Video Editor
        Document videoDoc1 = new Document("Video1", LocalDate.now(), "Author6", 2048, videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call the method to compute average sizes
        Map<Editor, Double> result = fileSystem.getAverageSizeByEditor();
        
        // Verify Text Editor average size = (100 + 200) / 2 = 150
        assertEquals(150.0, result.get(textEditor), 0.001);
        // Verify Image Editor average size = (1024 + 1536 + 512) / 3 = 1024
        assertEquals(1024.0, result.get(imageEditor), 0.001);
        // Verify Video Editor average size = 2048
        assertEquals(2048.0, result.get(videoEditor), 0.001);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Test Case 5: Large Number of Documents for One Editor
        // SetUp: Associate "Text Editor" with 100 documents, each 10 in size
        
        // Create 100 documents for Text Editor, each with size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document("Document" + i, LocalDate.now(), "Author" + i, 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average sizes
        Map<Editor, Double> result = fileSystem.getAverageSizeByEditor();
        
        // Verify Text Editor average size = (10 * 100) / 100 = 10
        assertEquals(10.0, result.get(textEditor), 0.001);
        // Verify other editors have 0 average size (no documents assigned)
        assertEquals(0.0, result.get(imageEditor), 0.001);
        assertEquals(0.0, result.get(videoEditor), 0.001);
    }
}