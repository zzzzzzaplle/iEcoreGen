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
        // Set up: Create documents for Text Editor
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
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average
        assertEquals(200.0, result.get("Text Editor"), 0.001);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Set up: Create only the editors but no documents for Image Editor
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video1");
        doc2.setSize(500);
        doc2.setEditor(videoEditor);
        
        // Add only documents for Text and Video editors
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Since Image Editor has no documents, it should not appear in the result
        assertFalse(result.containsKey("Image Editor"));
        
        // Alternatively, if we want to explicitly test that an editor with no documents returns 0,
        // we need to add a document with Image Editor and then remove it
        fileSystem = new FileSystem(); // Reset
        Document imageDoc = new Document();
        imageDoc.setEditor(imageEditor);
        imageDoc.setSize(100);
        fileSystem.addDocument(imageDoc);
        fileSystem.removeDocument(imageDoc);
        
        result = fileSystem.computeAverageSizePerEditor();
        // Image Editor should not appear since it has no documents after removal
        assertFalse(result.containsKey("Image Editor"));
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Set up: Create documents for Video Editor with varying sizes
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
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify Video Editor average
        assertEquals(637.5, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Set up: Create documents for all three editors
        // Text Editor documents
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        
        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        
        // Image Editor documents
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        
        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        
        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        
        // Video Editor document
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
        
        // Compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify all editor averages
        assertEquals(150.0, result.get("Text Editor"), 0.001);
        assertEquals(1024.0, result.get("Image Editor"), 0.001);
        assertEquals(2048.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Set up: Create 100 documents for Text Editor, each with size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + (i + 1));
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average
        assertEquals(10.0, result.get("Text Editor"), 0.001);
    }
}