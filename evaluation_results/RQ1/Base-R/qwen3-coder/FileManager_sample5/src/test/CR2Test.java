import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CR2Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() throws Exception {
        // Set up: Create documents for Text Editor
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor("Text Editor");
        doc1.setCreationDate(dateFormat.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor("Text Editor");
        doc2.setCreationDate(dateFormat.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor("Text Editor");
        doc3.setCreationDate(dateFormat.parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Compute average sizes
        Map<String, Double> averages = fileSystem.computeAverageSizeByEditor();
        
        // Verify Text Editor average size
        assertEquals(200.0, averages.get("Text Editor"), 0.001);
        // Verify other editors have 0 average (no documents)
        assertEquals(0.0, averages.get("Image Editor"), 0.001);
        assertEquals(0.0, averages.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() throws Exception {
        // Set up: No documents added to Image Editor
        
        // Compute average sizes
        Map<String, Double> averages = fileSystem.computeAverageSizeByEditor();
        
        // Verify Image Editor average size is 0 (no documents)
        assertEquals(0.0, averages.get("Image Editor"), 0.001);
        // Verify all editors have 0 average (no documents)
        assertEquals(0.0, averages.get("Text Editor"), 0.001);
        assertEquals(0.0, averages.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() throws Exception {
        // Set up: Create documents for Video Editor with varying sizes
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor("Video Editor");
        doc1.setCreationDate(dateFormat.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor("Video Editor");
        doc2.setCreationDate(dateFormat.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor("Video Editor");
        doc3.setCreationDate(dateFormat.parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor("Video Editor");
        doc4.setCreationDate(dateFormat.parse("2023-01-04 13:00:00"));
        doc4.setAuthor("Author4");
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Compute average sizes
        Map<String, Double> averages = fileSystem.computeAverageSizeByEditor();
        
        // Verify Video Editor average size
        assertEquals(637.5, averages.get("Video Editor"), 0.001);
        // Verify other editors have 0 average (no documents)
        assertEquals(0.0, averages.get("Text Editor"), 0.001);
        assertEquals(0.0, averages.get("Image Editor"), 0.001);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() throws Exception {
        // Set up: Create documents for all three editors
        
        // Text Editor documents
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setSize(100);
        doc1.setEditor("Text Editor");
        doc1.setCreationDate(dateFormat.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setSize(200);
        doc2.setEditor("Text Editor");
        doc2.setCreationDate(dateFormat.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        
        // Image Editor documents
        Document doc3 = new Document();
        doc3.setName("Image1");
        doc3.setSize(1024);
        doc3.setEditor("Image Editor");
        doc3.setCreationDate(dateFormat.parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        
        Document doc4 = new Document();
        doc4.setName("Image2");
        doc4.setSize(1536);
        doc4.setEditor("Image Editor");
        doc4.setCreationDate(dateFormat.parse("2023-01-04 13:00:00"));
        doc4.setAuthor("Author4");
        
        Document doc5 = new Document();
        doc5.setName("Image3");
        doc5.setSize(512);
        doc5.setEditor("Image Editor");
        doc5.setCreationDate(dateFormat.parse("2023-01-05 14:00:00"));
        doc5.setAuthor("Author5");
        
        // Video Editor document
        Document doc6 = new Document();
        doc6.setName("Video1");
        doc6.setSize(2048);
        doc6.setEditor("Video Editor");
        doc6.setCreationDate(dateFormat.parse("2023-01-06 15:00:00"));
        doc6.setAuthor("Author6");
        
        // Add all documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        fileSystem.addDocument(doc5);
        fileSystem.addDocument(doc6);
        
        // Compute average sizes
        Map<String, Double> averages = fileSystem.computeAverageSizeByEditor();
        
        // Verify all editor average sizes
        assertEquals(150.0, averages.get("Text Editor"), 0.001);
        assertEquals(1024.0, averages.get("Image Editor"), 0.001);
        assertEquals(2048.0, averages.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() throws Exception {
        // Set up: Create 100 documents for Text Editor, each of size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor("Text Editor");
            doc.setCreationDate(dateFormat.parse("2023-01-" + String.format("%02d", i) + " 10:00:00"));
            doc.setAuthor("Author" + i);
            fileSystem.addDocument(doc);
        }
        
        // Compute average sizes
        Map<String, Double> averages = fileSystem.computeAverageSizeByEditor();
        
        // Verify Text Editor average size
        assertEquals(10.0, averages.get("Text Editor"), 0.001);
        // Verify other editors have 0 average (no documents)
        assertEquals(0.0, averages.get("Image Editor"), 0.001);
        assertEquals(0.0, averages.get("Video Editor"), 0.001);
    }
}