import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // SetUp: Create editors and associate "Text Editor" with 3 documents
        TextEditor textEditor = new TextEditor();
        
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
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify "Text Editor" average size = (150 + 200 + 250) / 3 = 200
        assertTrue(result.containsKey("Text Editor"));
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // SetUp: Create editors but no documents for "Image Editor"
        // The system already has default editors from constructor
        // Add a document without editor to ensure system has documents but not for Image Editor
        Document doc = new Document();
        doc.setName("Test Doc");
        doc.setSize(100);
        doc.setEditor(null); // No editor
        
        fileSystem.addDocument(doc);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Image Editor should not be in the result since it has no documents
        assertFalse(result.containsKey("Image Editor"));
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // SetUp: Create editors and associate "Video Editor" with 4 documents of varying sizes
        VideoEditor videoEditor = new VideoEditor();
        
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
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify "Video Editor" average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertTrue(result.containsKey("Video Editor"));
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // SetUp: Create editors and associate each with documents
        TextEditor textEditor = new TextEditor();
        ImageEditor imageEditor = new ImageEditor();
        VideoEditor videoEditor = new VideoEditor();
        
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document();
        textDoc1.setName("Text1");
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        
        Document textDoc2 = new Document();
        textDoc2.setName("Text2");
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document();
        imageDoc1.setName("Image1");
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        
        Document imageDoc2 = new Document();
        imageDoc2.setName("Image2");
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        
        Document imageDoc3 = new Document();
        imageDoc3.setName("Image3");
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document();
        videoDoc1.setName("Video1");
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify all expected averages
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);  // (100 + 200) / 2 = 150
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f); // (1024 + 1536 + 512) / 3 = 1024
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f); // 2048 / 1 = 2048
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // SetUp: Create "Text Editor" and associate with 100 documents, each of size 10
        TextEditor textEditor = new TextEditor();
        
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify "Text Editor" average size = (10 * 100) / 100 = 10
        assertTrue(result.containsKey("Text Editor"));
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
    }
}