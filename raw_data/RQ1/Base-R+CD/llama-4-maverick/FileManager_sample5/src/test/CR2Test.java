import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CR2Test {
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        videoEditor = new VideoEditor();
        videoEditor.setName("Video Editor");
        
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Create documents for Text Editor
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        doc1.setCreateDate(new Date());
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        doc2.setCreateDate(new Date());
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        doc3.setCreateDate(new Date());
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, result.get("Text Editor"), 0.001);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Create documents for Text Editor only, leaving Image Editor with no documents
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        doc1.setCreateDate(new Date());
        
        fileSystem.addDocument(doc1);
        
        // Calculate average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Image Editor average size = 0
        assertEquals(0.0f, result.get("Image Editor"), 0.001);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Create documents for Video Editor
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        doc1.setCreateDate(new Date());
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        doc2.setCreateDate(new Date());
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        doc3.setCreateDate(new Date());
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        doc4.setCreateDate(new Date());
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Calculate average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Video Editor average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Create documents for Text Editor
        Document textDoc1 = new Document();
        textDoc1.setName("TextDoc1");
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        textDoc1.setCreateDate(new Date());
        
        Document textDoc2 = new Document();
        textDoc2.setName("TextDoc2");
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        textDoc2.setCreateDate(new Date());
        
        // Create documents for Image Editor
        Document imageDoc1 = new Document();
        imageDoc1.setName("ImageDoc1");
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        imageDoc1.setCreateDate(new Date());
        
        Document imageDoc2 = new Document();
        imageDoc2.setName("ImageDoc2");
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        imageDoc2.setCreateDate(new Date());
        
        Document imageDoc3 = new Document();
        imageDoc3.setName("ImageDoc3");
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        imageDoc3.setCreateDate(new Date());
        
        // Create document for Video Editor
        Document videoDoc1 = new Document();
        videoDoc1.setName("VideoDoc1");
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        videoDoc1.setCreateDate(new Date());
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Calculate average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify averages:
        // Text Editor: (100 + 200) / 2 = 150
        assertEquals(150.0f, result.get("Text Editor"), 0.001);
        // Image Editor: (1024 + 1536 + 512) / 3 = 1024
        assertEquals(1024.0f, result.get("Image Editor"), 0.001);
        // Video Editor: 2048
        assertEquals(2048.0f, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Create 100 documents for Text Editor, each with size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            doc.setCreateDate(new Date());
            fileSystem.addDocument(doc);
        }
        
        // Calculate average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (10 * 100) / 100 = 10
        assertEquals(10.0f, result.get("Text Editor"), 0.001);
    }
}