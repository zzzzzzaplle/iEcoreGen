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
        textEditor = new Editor();
        textEditor.setName("Text Editor");
        imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        videoEditor = new Editor();
        videoEditor.setName("Video Editor");
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Set up documents for Text Editor
        Document doc1 = createDocument("Report", 150, textEditor);
        Document doc2 = createDocument("Essay", 200, textEditor);
        Document doc3 = createDocument("Proposal", 250, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average size
        assertEquals(200.0, result.get("Text Editor"), 0.001);
    }
    
    @Test
    public void testCase2_NoDocumentsForEditor() {
        // No documents added for Image Editor
        // Compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Image Editor should not be in the result map since it has no documents
        assertFalse(result.containsKey("Image Editor"));
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Set up documents for Video Editor
        Document doc1 = createDocument("Video1", 500, videoEditor);
        Document doc2 = createDocument("Video2", 1000, videoEditor);
        Document doc3 = createDocument("Video3", 750, videoEditor);
        Document doc4 = createDocument("Video4", 300, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify Video Editor average size
        assertEquals(637.5, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Set up documents for Text Editor
        Document textDoc1 = createDocument("Text1", 100, textEditor);
        Document textDoc2 = createDocument("Text2", 200, textEditor);
        
        // Set up documents for Image Editor
        Document imageDoc1 = createDocument("Image1", 1024, imageEditor);
        Document imageDoc2 = createDocument("Image2", 1536, imageEditor);
        Document imageDoc3 = createDocument("Image3", 512, imageEditor);
        
        // Set up document for Video Editor
        Document videoDoc1 = createDocument("Video1", 2048, videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify all editor average sizes
        assertEquals(150.0, result.get("Text Editor"), 0.001);
        assertEquals(1024.0, result.get("Image Editor"), 0.001);
        assertEquals(2048.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Set up 100 documents for Text Editor, each with size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = createDocument("Document" + i, 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average size
        assertEquals(10.0, result.get("Text Editor"), 0.001);
    }
    
    // Helper method to create documents with common properties
    private Document createDocument(String name, long size, Editor editor) {
        Document doc = new Document();
        doc.setName(name);
        doc.setSize(size);
        doc.setEditor(editor);
        doc.setCreationDate(LocalDate.now());
        doc.setAuthor("Test Author");
        return doc;
    }
}