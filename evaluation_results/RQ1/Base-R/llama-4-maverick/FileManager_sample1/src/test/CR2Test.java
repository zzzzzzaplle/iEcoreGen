import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
    }

    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Set up: Create documents for Text Editor
        Document doc1 = new Document("Report", LocalDate.now(), "Author1", 150, textEditor);
        Document doc2 = new Document("Essay", LocalDate.now(), "Author2", 200, textEditor);
        Document doc3 = new Document("Proposal", LocalDate.now(), "Author3", 250, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call method to compute average size per editor
        List<EditorAverageSize> results = fileSystem.computeAverageSizePerEditor();
        
        // Find Text Editor result and verify
        EditorAverageSize textEditorResult = results.stream()
                .filter(result -> result.getEditor().equals(textEditor))
                .findFirst()
                .orElse(null);
        
        assertNotNull("Text Editor result should not be null", textEditorResult);
        assertEquals("Average size should be 200", 200.0, textEditorResult.getAverageSize(), 0.001);
    }

    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Set up: No documents added for Image Editor
        
        // Call method to compute average size per editor
        List<EditorAverageSize> results = fileSystem.computeAverageSizePerEditor();
        
        // Find Image Editor result and verify
        EditorAverageSize imageEditorResult = results.stream()
                .filter(result -> result.getEditor().equals(imageEditor))
                .findFirst()
                .orElse(null);
        
        assertNotNull("Image Editor result should not be null", imageEditorResult);
        assertEquals("Average size should be 0 for editor with no documents", 0.0, imageEditorResult.getAverageSize(), 0.001);
    }

    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Set up: Create documents for Video Editor with varying sizes
        Document doc1 = new Document("Video1", LocalDate.now(), "Author1", 500, videoEditor);
        Document doc2 = new Document("Video2", LocalDate.now(), "Author2", 1000, videoEditor);
        Document doc3 = new Document("Video3", LocalDate.now(), "Author3", 750, videoEditor);
        Document doc4 = new Document("Video4", LocalDate.now(), "Author4", 300, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call method to compute average size per editor
        List<EditorAverageSize> results = fileSystem.computeAverageSizePerEditor();
        
        // Find Video Editor result and verify
        EditorAverageSize videoEditorResult = results.stream()
                .filter(result -> result.getEditor().equals(videoEditor))
                .findFirst()
                .orElse(null);
        
        assertNotNull("Video Editor result should not be null", videoEditorResult);
        assertEquals("Average size should be 637.5", 637.5, videoEditorResult.getAverageSize(), 0.001);
    }

    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Set up: Create documents for all editors
        // Text Editor documents
        Document textDoc1 = new Document("Text1", LocalDate.now(), "Author1", 100, textEditor);
        Document textDoc2 = new Document("Text2", LocalDate.now(), "Author2", 200, textEditor);
        
        // Image Editor documents
        Document imageDoc1 = new Document("Image1", LocalDate.now(), "Author3", 1024, imageEditor);
        Document imageDoc2 = new Document("Image2", LocalDate.now(), "Author4", 1536, imageEditor);
        Document imageDoc3 = new Document("Image3", LocalDate.now(), "Author5", 512, imageEditor);
        
        // Video Editor document
        Document videoDoc1 = new Document("Video1", LocalDate.now(), "Author6", 2048, videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call method to compute average size per editor
        List<EditorAverageSize> results = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average
        EditorAverageSize textEditorResult = results.stream()
                .filter(result -> result.getEditor().equals(textEditor))
                .findFirst()
                .orElse(null);
        assertNotNull("Text Editor result should not be null", textEditorResult);
        assertEquals("Text Editor average should be 150", 150.0, textEditorResult.getAverageSize(), 0.001);
        
        // Verify Image Editor average
        EditorAverageSize imageEditorResult = results.stream()
                .filter(result -> result.getEditor().equals(imageEditor))
                .findFirst()
                .orElse(null);
        assertNotNull("Image Editor result should not be null", imageEditorResult);
        assertEquals("Image Editor average should be 1024", 1024.0, imageEditorResult.getAverageSize(), 0.001);
        
        // Verify Video Editor average
        EditorAverageSize videoEditorResult = results.stream()
                .filter(result -> result.getEditor().equals(videoEditor))
                .findFirst()
                .orElse(null);
        assertNotNull("Video Editor result should not be null", videoEditorResult);
        assertEquals("Video Editor average should be 2048", 2048.0, videoEditorResult.getAverageSize(), 0.001);
    }

    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Set up: Create 100 documents for Text Editor, each with size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document("Document" + i, LocalDate.now(), "Author" + i, 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call method to compute average size per editor
        List<EditorAverageSize> results = fileSystem.computeAverageSizePerEditor();
        
        // Find Text Editor result and verify
        EditorAverageSize textEditorResult = results.stream()
                .filter(result -> result.getEditor().equals(textEditor))
                .findFirst()
                .orElse(null);
        
        assertNotNull("Text Editor result should not be null", textEditorResult);
        assertEquals("Average size should be 10 for 100 documents of size 10 each", 10.0, textEditorResult.getAverageSize(), 0.001);
    }
}