import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
        // Setup: Create 3 documents associated with Text Editor
        Document doc1 = new Document("Report", new Date(), "Author1", 150, textEditor);
        Document doc2 = new Document("Essay", new Date(), "Author2", 200, textEditor);
        Document doc3 = new Document("Proposal", new Date(), "Author3", 250, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Compute average size for each editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Text Editor average should be 200.0
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
        assertEquals(1, result.size());
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Setup: Add documents only for Text and Video editors, not Image editor
        Document doc1 = new Document("Report", new Date(), "Author1", 150, textEditor);
        Document doc2 = new Document("Video1", new Date(), "Author2", 500, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Compute average size for each editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Image Editor should not appear in results
        assertTrue(result.containsKey("Text Editor"));
        assertTrue(result.containsKey("Video Editor"));
        assertFalse(result.containsKey("Image Editor"));
        assertEquals(2, result.size());
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() throws ParseException {
        // Setup: Create 4 documents associated with Video Editor
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2023-01-01 10:00:00");
        
        Document doc1 = new Document("Video1", date, "Author1", 500, videoEditor);
        Document doc2 = new Document("Video2", date, "Author2", 1000, videoEditor);
        Document doc3 = new Document("Video3", date, "Author3", 750, videoEditor);
        Document doc4 = new Document("Video4", date, "Author4", 300, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Compute average size for each editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Video Editor average should be 637.5
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
        assertEquals(1, result.size());
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Setup: Distribute documents among all three editors
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document("Text1", new Date(), "Author1", 100, textEditor);
        Document textDoc2 = new Document("Text2", new Date(), "Author2", 200, textEditor);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document("Image1", new Date(), "Author3", 1024, imageEditor);
        Document imageDoc2 = new Document("Image2", new Date(), "Author4", 1536, imageEditor);
        Document imageDoc3 = new Document("Image3", new Date(), "Author5", 512, imageEditor);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document("Video1", new Date(), "Author6", 2048, videoEditor);
        
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Execute: Compute average size for each editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Each editor's average
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);     // (100 + 200) / 2
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f);   // (1024 + 1536 + 512) / 3
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f);   // 2048 / 1
        assertEquals(3, result.size());
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Setup: Create 100 documents for Text Editor, each with size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document("Doc" + i, new Date(), "Author", 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Execute: Compute average size for each editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Text Editor average should be 10.0
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
        assertEquals(1, result.size());
    }
}