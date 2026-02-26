import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;

public class CR2Test {
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() throws Exception {
        // Set up editors
        textEditor = new TextEditor("Text Editor");
        imageEditor = new ImageEditor("Image Editor");
        videoEditor = new VideoEditor("Video Editor");
        
        // Create documents for Text Editor
        Date date1 = dateFormat.parse("2024-01-01 10:00:00");
        Date date2 = dateFormat.parse("2024-01-02 11:00:00");
        Date date3 = dateFormat.parse("2024-01-03 12:00:00");
        
        Document doc1 = new Document("Report", date1, "Author1", 150, textEditor);
        Document doc2 = new Document("Essay", date2, "Author2", 200, textEditor);
        Document doc3 = new Document("Proposal", date3, "Author3", 250, textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Compute average size for Text Editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify result
        assertTrue("Result should contain Text Editor", result.containsKey("Text Editor"));
        assertEquals("Average size should be 200", 200.0f, result.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() throws Exception {
        // Set up editors
        textEditor = new TextEditor("Text Editor");
        imageEditor = new ImageEditor("Image Editor");
        videoEditor = new VideoEditor("Video Editor");
        
        // Create a document for Text Editor (but not for Image Editor)
        Date date = dateFormat.parse("2024-01-01 10:00:00");
        Document doc = new Document("Report", date, "Author1", 150, textEditor);
        fileSystem.addDocument(doc);
        
        // Compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Image Editor should not be in the result since it has no documents
        assertFalse("Image Editor should not be in result", result.containsKey("Image Editor"));
        assertTrue("Text Editor should be in result", result.containsKey("Text Editor"));
        
        // Verify Text Editor average
        assertEquals("Text Editor average should be 150", 150.0f, result.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() throws Exception {
        // Set up editors
        textEditor = new TextEditor("Text Editor");
        imageEditor = new ImageEditor("Image Editor");
        videoEditor = new VideoEditor("Video Editor");
        
        // Create documents for Video Editor with varying sizes
        Date date1 = dateFormat.parse("2024-01-01 10:00:00");
        Date date2 = dateFormat.parse("2024-01-02 11:00:00");
        Date date3 = dateFormat.parse("2024-01-03 12:00:00");
        Date date4 = dateFormat.parse("2024-01-04 13:00:00");
        
        Document doc1 = new Document("Video1", date1, "Author1", 500, videoEditor);
        Document doc2 = new Document("Video2", date2, "Author2", 1000, videoEditor);
        Document doc3 = new Document("Video3", date3, "Author3", 750, videoEditor);
        Document doc4 = new Document("Video4", date4, "Author4", 300, videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Compute average size for Video Editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify result
        assertTrue("Result should contain Video Editor", result.containsKey("Video Editor"));
        assertEquals("Average size should be 637.5", 637.5f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() throws Exception {
        // Set up editors
        textEditor = new TextEditor("Text Editor");
        imageEditor = new ImageEditor("Image Editor");
        videoEditor = new VideoEditor("Video Editor");
        
        // Create documents for each editor
        Date date = dateFormat.parse("2024-01-01 10:00:00");
        
        // Text Editor documents
        Document doc1 = new Document("Doc1", date, "Author1", 100, textEditor);
        Document doc2 = new Document("Doc2", date, "Author2", 200, textEditor);
        
        // Image Editor documents
        Document doc3 = new Document("Image1", date, "Author3", 1024, imageEditor);
        Document doc4 = new Document("Image2", date, "Author4", 1536, imageEditor);
        Document doc5 = new Document("Image3", date, "Author5", 512, imageEditor);
        
        // Video Editor document
        Document doc6 = new Document("Video1", date, "Author6", 2048, videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        fileSystem.addDocument(doc5);
        fileSystem.addDocument(doc6);
        
        // Compute average sizes for all editors
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify results for all editors
        assertTrue("Result should contain Text Editor", result.containsKey("Text Editor"));
        assertTrue("Result should contain Image Editor", result.containsKey("Image Editor"));
        assertTrue("Result should contain Video Editor", result.containsKey("Video Editor"));
        
        assertEquals("Text Editor average should be 150", 150.0f, result.get("Text Editor"), 0.001f);
        assertEquals("Image Editor average should be 1024", 1024.0f, result.get("Image Editor"), 0.001f);
        assertEquals("Video Editor average should be 2048", 2048.0f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() throws Exception {
        // Set up editor
        textEditor = new TextEditor("Text Editor");
        
        // Create 100 documents, each with size 10
        Date date = dateFormat.parse("2024-01-01 10:00:00");
        
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document("Document" + i, date, "Author" + i, 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Compute average size for Text Editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify result
        assertTrue("Result should contain Text Editor", result.containsKey("Text Editor"));
        assertEquals("Average size should be 10", 10.0f, result.get("Text Editor"), 0.001f);
    }
}