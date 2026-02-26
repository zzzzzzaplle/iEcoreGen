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
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Set up: Create editors and associate "Text Editor" with 3 documents
        fileSystem.addDocument(new Document("Report", LocalDate.now(), "Author1", 150L, textEditor));
        fileSystem.addDocument(new Document("Essay", LocalDate.now(), "Author2", 200L, textEditor));
        fileSystem.addDocument(new Document("Proposal", LocalDate.now(), "Author3", 250L, textEditor));
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, result.get("Text Editor"), 0.001);
        // Verify other editors have 0 average
        assertEquals(0.0, result.get("Image Editor"), 0.001);
        assertEquals(0.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Set up: Create editors but only add documents for Text and Video Editors
        fileSystem.addDocument(new Document("Doc1", LocalDate.now(), "Author1", 100L, textEditor));
        fileSystem.addDocument(new Document("Doc2", LocalDate.now(), "Author2", 2000L, videoEditor));
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify Image Editor has 0 average (no documents)
        assertEquals(0.0, result.get("Image Editor"), 0.001);
        // Verify other editors have their respective averages
        assertEquals(100.0, result.get("Text Editor"), 0.001);
        assertEquals(2000.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Set up: Associate "Video Editor" with 4 documents of varying sizes
        fileSystem.addDocument(new Document("Video1", LocalDate.now(), "Author1", 500L, videoEditor));
        fileSystem.addDocument(new Document("Video2", LocalDate.now(), "Author2", 1000L, videoEditor));
        fileSystem.addDocument(new Document("Video3", LocalDate.now(), "Author3", 750L, videoEditor));
        fileSystem.addDocument(new Document("Video4", LocalDate.now(), "Author4", 300L, videoEditor));
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify Video Editor average = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5, result.get("Video Editor"), 0.001);
        // Verify other editors have 0 average
        assertEquals(0.0, result.get("Text Editor"), 0.001);
        assertEquals(0.0, result.get("Image Editor"), 0.001);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Set up: Associate each editor with documents as specified
        fileSystem.addDocument(new Document("Doc1", LocalDate.now(), "Author1", 100L, textEditor));
        fileSystem.addDocument(new Document("Doc2", LocalDate.now(), "Author2", 200L, textEditor));
        
        fileSystem.addDocument(new Document("Img1", LocalDate.now(), "Author3", 1024L, imageEditor));
        fileSystem.addDocument(new Document("Img2", LocalDate.now(), "Author4", 1536L, imageEditor));
        fileSystem.addDocument(new Document("Img3", LocalDate.now(), "Author5", 512L, imageEditor));
        
        fileSystem.addDocument(new Document("Vid1", LocalDate.now(), "Author6", 2048L, videoEditor));
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify all editor averages
        assertEquals(150.0, result.get("Text Editor"), 0.001);        // (100 + 200) / 2 = 150
        assertEquals(1024.0, result.get("Image Editor"), 0.001);     // (1024 + 1536 + 512) / 3 = 1024
        assertEquals(2048.0, result.get("Video Editor"), 0.001);     // 2048 / 1 = 2048
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Set up: Associate "Text Editor" with 100 documents, each of size 10
        for (int i = 0; i < 100; i++) {
            fileSystem.addDocument(new Document("Doc" + i, LocalDate.now(), "Author" + i, 10L, textEditor));
        }
        
        // Call the method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average = (10 * 100) / 100 = 10
        assertEquals(10.0, result.get("Text Editor"), 0.001);
        // Verify other editors have 0 average
        assertEquals(0.0, result.get("Image Editor"), 0.001);
        assertEquals(0.0, result.get("Video Editor"), 0.001);
    }
}