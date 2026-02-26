import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CR2Test {
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;

    @Before
    public void setUp() {
        // Initialize file system with default editors before each test
        fileSystem = new FileSystem();
        textEditor = fileSystem.getTextEditor();
        imageEditor = fileSystem.getImageEditor();
        videoEditor = fileSystem.getVideoEditor();
    }

    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Set up: Associate "Text Editor" with 3 documents
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call method to compute average size per editor
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify average size for Text Editor is 200
        assertEquals(200.0, result.get("Text Editor"), 0.001);
        // Verify other editors have 0 average (no documents assigned)
        assertEquals(0.0, result.get("Image Editor"), 0.001);
        assertEquals(0.0, result.get("Video Editor"), 0.001);
    }

    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Set up: No documents associated with Image Editor
        // FileSystem is already initialized with empty document list
        
        // Call method to compute average size per editor
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify all editors have 0 average (no documents assigned)
        assertEquals(0.0, result.get("Text Editor"), 0.001);
        assertEquals(0.0, result.get("Image Editor"), 0.001);
        assertEquals(0.0, result.get("Video Editor"), 0.001);
    }

    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Set up: Associate "Video Editor" with 4 documents of varying sizes
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call method to compute average size per editor
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify average size for Video Editor is 637.5
        assertEquals(637.5, result.get("Video Editor"), 0.001);
        // Verify other editors have 0 average (no documents assigned)
        assertEquals(0.0, result.get("Text Editor"), 0.001);
        assertEquals(0.0, result.get("Image Editor"), 0.001);
    }

    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Set up: Associate each editor with documents as specified
        
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        
        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        
        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        
        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document();
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call method to compute average size per editor
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify average sizes for all editors
        assertEquals(150.0, result.get("Text Editor"), 0.001);    // (100 + 200) / 2 = 150
        assertEquals(1024.0, result.get("Image Editor"), 0.001);  // (1024 + 1536 + 512) / 3 = 1024
        assertEquals(2048.0, result.get("Video Editor"), 0.001);  // 2048 / 1 = 2048
    }

    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Set up: Associate "Text Editor" with 100 documents, each of size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call method to compute average size per editor
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify average size for Text Editor is 10.0
        assertEquals(10.0, result.get("Text Editor"), 0.001);
        // Verify other editors have 0 average (no documents assigned)
        assertEquals(0.0, result.get("Image Editor"), 0.001);
        assertEquals(0.0, result.get("Video Editor"), 0.001);
    }
}