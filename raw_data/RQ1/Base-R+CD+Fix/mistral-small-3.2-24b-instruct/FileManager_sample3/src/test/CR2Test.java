import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
        textEditor.setName("Text Editor");
        imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        videoEditor = new VideoEditor();
        videoEditor.setName("Video Editor");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() throws ParseException {
        // SetUp: Create editors and associate "Text Editor" with 3 documents
        Date createDate = dateFormat.parse("2023-01-01 10:00:00");
        
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        doc1.setCreateDate(createDate);
        doc1.setAuthor("Author1");
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        doc2.setCreateDate(createDate);
        doc2.setAuthor("Author2");
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        doc3.setCreateDate(createDate);
        doc3.setAuthor("Author3");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute the average size for "Text Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = (150 + 200 + 250) / 3 = 200
        assertTrue("Should contain Text Editor", result.containsKey("Text Editor"));
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
    }

    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: Create editors but associate "Image Editor" with no documents
        // No documents added to file system for Image Editor
        
        // Call the method to compute the average size for "Image Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = 0 (no entry in map)
        assertFalse("Should not contain Image Editor", result.containsKey("Image Editor"));
    }

    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() throws ParseException {
        // SetUp: Create editors and associate "Video Editor" with 4 documents of varying sizes
        Date createDate = dateFormat.parse("2023-01-01 10:00:00");
        
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        doc1.setCreateDate(createDate);
        doc1.setAuthor("Author1");
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        doc2.setCreateDate(createDate);
        doc2.setAuthor("Author2");
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        doc3.setCreateDate(createDate);
        doc3.setAuthor("Author3");
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        doc4.setCreateDate(createDate);
        doc4.setAuthor("Author4");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute the average size for "Video Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertTrue("Should contain Video Editor", result.containsKey("Video Editor"));
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
    }

    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() throws ParseException {
        // SetUp: Create editors and associate each with specified documents
        Date createDate = dateFormat.parse("2023-01-01 10:00:00");
        
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document();
        textDoc1.setName("Text1");
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        textDoc1.setCreateDate(createDate);
        textDoc1.setAuthor("Author1");
        
        Document textDoc2 = new Document();
        textDoc2.setName("Text2");
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        textDoc2.setCreateDate(createDate);
        textDoc2.setAuthor("Author2");
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document();
        imageDoc1.setName("Image1");
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        imageDoc1.setCreateDate(createDate);
        imageDoc1.setAuthor("Author3");
        
        Document imageDoc2 = new Document();
        imageDoc2.setName("Image2");
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        imageDoc2.setCreateDate(createDate);
        imageDoc2.setAuthor("Author4");
        
        Document imageDoc3 = new Document();
        imageDoc3.setName("Image3");
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        imageDoc3.setCreateDate(createDate);
        imageDoc3.setAuthor("Author5");
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document();
        videoDoc1.setName("Video1");
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        videoDoc1.setCreateDate(createDate);
        videoDoc1.setAuthor("Author6");
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call the method to compute the average size for each editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output:
        // - "Text Editor" Average size = (100 + 200) / 2 = 150
        // - "Image Editor" Average size = (1024 + 1536 + 512) / 3 = 1024
        // - "Video Editor" Average size = 2048
        
        assertEquals("Should have 3 entries", 3, result.size());
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f);
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f);
    }

    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() throws ParseException {
        // SetUp: Create "Text Editor" and associate with 100 documents, each size 10
        Date createDate = dateFormat.parse("2023-01-01 10:00:00");
        
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            doc.setCreateDate(createDate);
            doc.setAuthor("Author" + i);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute the average size for "Text Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = (10 * 100) / 100 = 10
        assertTrue("Should contain Text Editor", result.containsKey("Text Editor"));
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
    }
}