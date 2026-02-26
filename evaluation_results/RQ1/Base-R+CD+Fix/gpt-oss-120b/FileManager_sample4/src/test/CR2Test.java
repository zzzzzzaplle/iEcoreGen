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
        List<Editor> editors = new ArrayList<>();
        editors.add(textEditor);
        editors.add(imageEditor);
        editors.add(videoEditor);
        fileSystem.setEditors(editors);
        
        List<Document> documents = new ArrayList<>();
        
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        documents.add(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        documents.add(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        documents.add(doc3);
        
        fileSystem.setDocuments(documents);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: TextEditor average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, result.get("TextEditor"), 0.001f);
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(0.0f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: Create editors and associate ImageEditor with no documents
        List<Editor> editors = new ArrayList<>();
        editors.add(textEditor);
        editors.add(imageEditor);
        editors.add(videoEditor);
        fileSystem.setEditors(editors);
        
        List<Document> documents = new ArrayList<>();
        fileSystem.setDocuments(documents);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: All editors should have average size = 0
        assertEquals(0.0f, result.get("TextEditor"), 0.001f);
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(0.0f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // SetUp: Create editors and associate VideoEditor with 4 documents of varying sizes
        List<Editor> editors = new ArrayList<>();
        editors.add(textEditor);
        editors.add(imageEditor);
        editors.add(videoEditor);
        fileSystem.setEditors(editors);
        
        List<Document> documents = new ArrayList<>();
        
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        documents.add(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        documents.add(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        documents.add(doc3);
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        documents.add(doc4);
        
        fileSystem.setDocuments(documents);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: VideoEditor average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, result.get("VideoEditor"), 0.001f);
        assertEquals(0.0f, result.get("TextEditor"), 0.001f);
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // SetUp: Create editors and associate each with documents
        List<Editor> editors = new ArrayList<>();
        editors.add(textEditor);
        editors.add(imageEditor);
        editors.add(videoEditor);
        fileSystem.setEditors(editors);
        
        List<Document> documents = new ArrayList<>();
        
        // TextEditor: 2 documents (100, 200)
        Document doc1 = new Document();
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        documents.add(doc1);
        
        Document doc2 = new Document();
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        documents.add(doc2);
        
        // ImageEditor: 3 documents (1024, 1536, 512)
        Document doc3 = new Document();
        doc3.setSize(1024);
        doc3.setEditor(imageEditor);
        documents.add(doc3);
        
        Document doc4 = new Document();
        doc4.setSize(1536);
        doc4.setEditor(imageEditor);
        documents.add(doc4);
        
        Document doc5 = new Document();
        doc5.setSize(512);
        doc5.setEditor(imageEditor);
        documents.add(doc5);
        
        // VideoEditor: 1 document (2048)
        Document doc6 = new Document();
        doc6.setSize(2048);
        doc6.setEditor(videoEditor);
        documents.add(doc6);
        
        fileSystem.setDocuments(documents);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output:
        // TextEditor average size = (100 + 200) / 2 = 150
        // ImageEditor average size = (1024 + 1536 + 512) / 3 = 1024
        // VideoEditor average size = 2048
        assertEquals(150.0f, result.get("TextEditor"), 0.001f);
        assertEquals(1024.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(2048.0f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // SetUp: Create only TextEditor and associate it with 100 documents of size 10 each
        List<Editor> editors = new ArrayList<>();
        editors.add(textEditor);
        fileSystem.setEditors(editors);
        
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
        
        // Expected Output: TextEditor average size = (10 * 100) / 100 = 10
        assertEquals(10.0f, result.get("TextEditor"), 0.001f);
    }
}