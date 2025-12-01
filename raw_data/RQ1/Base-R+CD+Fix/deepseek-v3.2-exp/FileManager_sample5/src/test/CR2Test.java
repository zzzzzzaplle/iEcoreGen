import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR2Test {
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Set editor names as specified in test cases
        textEditor.setName("Text Editor");
        imageEditor.setName("Image Editor");
        videoEditor.setName("Video Editor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() throws Exception {
        // Setup: Create 3 documents for Text Editor
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        doc1.setCreateDate(dateFormat.parse("2024-01-01 10:00:00"));
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        doc2.setCreateDate(dateFormat.parse("2024-01-02 10:00:00"));
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        doc3.setCreateDate(dateFormat.parse("2024-01-03 10:00:00"));
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, averages.get("TextEditor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Setup: No documents associated with Image Editor (already set up in @Before)
        
        // Call method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify Image Editor should not be in the result map when there are no documents
        assertFalse("ImageEditor should not be in averages map when no documents exist", 
                   averages.containsKey("ImageEditor"));
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() throws Exception {
        // Setup: Create 4 documents for Video Editor with varying sizes
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        doc1.setCreateDate(dateFormat.parse("2024-01-01 10:00:00"));
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        doc2.setCreateDate(dateFormat.parse("2024-01-02 10:00:00"));
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        doc3.setCreateDate(dateFormat.parse("2024-01-03 10:00:00"));
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        doc4.setCreateDate(dateFormat.parse("2024-01-04 10:00:00"));
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify Video Editor average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, averages.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() throws Exception {
        // Setup: Create documents for all three editors with specified sizes
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document();
        textDoc1.setName("TextDoc1");
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        textDoc1.setCreateDate(dateFormat.parse("2024-01-01 10:00:00"));
        
        Document textDoc2 = new Document();
        textDoc2.setName("TextDoc2");
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        textDoc2.setCreateDate(dateFormat.parse("2024-01-02 10:00:00"));
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document();
        imageDoc1.setName("ImageDoc1");
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        imageDoc1.setCreateDate(dateFormat.parse("2024-01-01 10:00:00"));
        
        Document imageDoc2 = new Document();
        imageDoc2.setName("ImageDoc2");
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        imageDoc2.setCreateDate(dateFormat.parse("2024-01-02 10:00:00"));
        
        Document imageDoc3 = new Document();
        imageDoc3.setName("ImageDoc3");
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        imageDoc3.setCreateDate(dateFormat.parse("2024-01-03 10:00:00"));
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document();
        videoDoc1.setName("VideoDoc1");
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        videoDoc1.setCreateDate(dateFormat.parse("2024-01-01 10:00:00"));
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify all expected averages
        assertEquals(150.0f, averages.get("TextEditor"), 0.001f);  // (100 + 200) / 2 = 150
        assertEquals(1024.0f, averages.get("ImageEditor"), 0.001f); // (1024 + 1536 + 512) / 3 = 1024
        assertEquals(2048.0f, averages.get("VideoEditor"), 0.001f); // 2048 / 1 = 2048
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() throws Exception {
        // Setup: Create 100 documents for Text Editor, each with size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            doc.setCreateDate(dateFormat.parse("2024-01-" + String.format("%02d", i) + " 10:00:00"));
            fileSystem.addDocument(doc);
        }
        
        // Call method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (10 * 100) / 100 = 10
        assertEquals(10.0f, averages.get("TextEditor"), 0.001f);
    }
}