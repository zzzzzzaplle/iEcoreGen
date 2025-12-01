import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
        fileSystem.setEditors(Arrays.asList(textEditor, imageEditor, videoEditor));
        
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
        
        fileSystem.setDocuments(Arrays.asList(doc1, doc2, doc3));
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
        // Verify other editors have 0 average
        assertEquals(0.0f, result.get("Image Editor"), 0.001f);
        assertEquals(0.0f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: Create editors but associate Image Editor with no documents
        fileSystem.setEditors(Arrays.asList(textEditor, imageEditor, videoEditor));
        
        // No documents added to the file system
        fileSystem.setDocuments(new ArrayList<>());
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify all editors have 0 average size
        assertEquals(0.0f, result.get("Text Editor"), 0.001f);
        assertEquals(0.0f, result.get("Image Editor"), 0.001f);
        assertEquals(0.0f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // SetUp: Create editors and associate Video Editor with 4 documents of varying sizes
        fileSystem.setEditors(Arrays.asList(textEditor, imageEditor, videoEditor));
        
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
        
        fileSystem.setDocuments(Arrays.asList(doc1, doc2, doc3, doc4));
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Video Editor average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
        // Verify other editors have 0 average
        assertEquals(0.0f, result.get("Text Editor"), 0.001f);
        assertEquals(0.0f, result.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // SetUp: Create editors and associate each with documents as specified
        fileSystem.setEditors(Arrays.asList(textEditor, imageEditor, videoEditor));
        
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
        
        List<Document> allDocuments = Arrays.asList(
            textDoc1, textDoc2, imageDoc1, imageDoc2, imageDoc3, videoDoc1
        );
        fileSystem.setDocuments(allDocuments);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify all averages
        assertEquals(150.0f, result.get("Text Editor"), 0.001f); // (100 + 200) / 2 = 150
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f); // (1024 + 1536 + 512) / 3 = 1024
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f); // 2048 / 1 = 2048
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // SetUp: Create only Text Editor and associate with 100 documents of size 10 each
        fileSystem.setEditors(Arrays.asList(textEditor));
        
        List<Document> documents = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setSize(10);
            doc.setEditor(textEditor);
            documents.add(doc);
        }
        fileSystem.setDocuments(documents);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (10 * 100) / 100 = 10
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
    }
}