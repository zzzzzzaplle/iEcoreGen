import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Setup: Create documents for Text Editor
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
        
        // Execute: Calculate average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify: Text Editor average should be 200
        assertEquals(200.0f, averages.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Setup: Add some documents for other editors but not for Image Editor
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);
        
        // Execute: Calculate average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify: Image Editor average should be 0
        assertEquals(0.0f, averages.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Setup: Create documents for Video Editor
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
        
        // Execute: Calculate average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify: Video Editor average should be 637.5
        assertEquals(637.5f, averages.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Setup: Create documents for Text Editor
        Document textDoc1 = new Document();
        textDoc1.setName("Text1");
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        
        Document textDoc2 = new Document();
        textDoc2.setName("Text2");
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        
        // Setup: Create documents for Image Editor
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
        
        // Setup: Create documents for Video Editor
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
        
        // Execute: Calculate average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify: Each editor has the correct average
        assertEquals(150.0f, averages.get("Text Editor"), 0.001f);
        assertEquals(1024.0f, averages.get("Image Editor"), 0.001f);
        assertEquals(2048.0f, averages.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Setup: Create 100 documents for Text Editor, each with size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Execute: Calculate average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify: Text Editor average should be 10
        assertEquals(10.0f, averages.get("Text Editor"), 0.001f);
    }
}