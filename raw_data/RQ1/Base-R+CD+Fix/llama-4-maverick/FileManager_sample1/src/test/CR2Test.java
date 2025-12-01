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
        // SetUp: Create editors and associate Text Editor with 3 documents
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
        
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute average size per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: Create editors but associate Image Editor with no documents
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
        
        // Call the method to compute average size per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Image Editor average size = 0 (no documents)
        assertEquals(0.0f, result.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // SetUp: Create editors and associate Video Editor with 4 documents of varying sizes
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
        
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute average size per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Video Editor average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // SetUp: Create editors and associate each with specified documents
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
        
        // Text Editor: 2 documents (100, 200)
        Document doc1 = new Document();
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        fileSystem.addDocument(doc2);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document doc3 = new Document();
        doc3.setSize(1024);
        doc3.setEditor(imageEditor);
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document();
        doc4.setSize(1536);
        doc4.setEditor(imageEditor);
        fileSystem.addDocument(doc4);
        
        Document doc5 = new Document();
        doc5.setSize(512);
        doc5.setEditor(imageEditor);
        fileSystem.addDocument(doc5);
        
        // Video Editor: 1 document (2048)
        Document doc6 = new Document();
        doc6.setSize(2048);
        doc6.setEditor(videoEditor);
        fileSystem.addDocument(doc6);
        
        // Call the method to compute average size per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify all editors' average sizes
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);  // (100 + 200) / 2 = 150
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f); // (1024 + 1536 + 512) / 3 = 1024
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f); // 2048 / 1 = 2048
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // SetUp: Create Text Editor and associate with 100 documents, each of size 10
        fileSystem.addEditor(textEditor);
        
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average size per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (10 * 100) / 100 = 10
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
    }
}