import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Date;
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
        // SetUp: Create editors and associate TextEditor with 3 documents
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
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for TextEditor = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, averages.get("TextEditor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: Create editors but only associate documents with TextEditor and VideoEditor
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video1");
        doc2.setSize(500);
        doc2.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Call the method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for ImageEditor = 0 (no documents)
        assertEquals(0.0f, averages.get("ImageEditor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // SetUp: Associate VideoEditor with 4 documents of varying sizes
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
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for VideoEditor = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, averages.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // SetUp: Associate each editor with specified documents
        // TextEditor: 2 documents (100, 200)
        Document doc1 = new Document();
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        
        // ImageEditor: 3 documents (1024, 1536, 512)
        Document doc3 = new Document();
        doc3.setSize(1024);
        doc3.setEditor(imageEditor);
        
        Document doc4 = new Document();
        doc4.setSize(1536);
        doc4.setEditor(imageEditor);
        
        Document doc5 = new Document();
        doc5.setSize(512);
        doc5.setEditor(imageEditor);
        
        // VideoEditor: 1 document (2048)
        Document doc6 = new Document();
        doc6.setSize(2048);
        doc6.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        fileSystem.addDocument(doc5);
        fileSystem.addDocument(doc6);
        
        // Call the method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Expected Outputs:
        // TextEditor Average size = (100 + 200) / 2 = 150
        assertEquals(150.0f, averages.get("TextEditor"), 0.001f);
        
        // ImageEditor Average size = (1024 + 1536 + 512) / 3 = 1024
        assertEquals(1024.0f, averages.get("ImageEditor"), 0.001f);
        
        // VideoEditor Average size = 2048
        assertEquals(2048.0f, averages.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // SetUp: Associate TextEditor with 100 documents, each of size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + (i + 1));
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for TextEditor = (10 * 100) / 100 = 10
        assertEquals(10.0f, averages.get("TextEditor"), 0.001f);
    }
}