import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR2Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new Editor();
        textEditor.setName("Text Editor");
        
        imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        
        videoEditor = new Editor();
        videoEditor.setName("Video Editor");
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Set up documents for Text Editor
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        doc1.setCreationDate(LocalDate.now());
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        doc2.setCreationDate(LocalDate.now());
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        doc3.setCreationDate(LocalDate.now());
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average size
        assertEquals(200.0, averageSizes.get("Text Editor"), 0.001);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Set up documents for Text Editor and Video Editor (but not Image Editor)
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        doc1.setCreationDate(LocalDate.now());
        
        Document doc2 = new Document();
        doc2.setName("Video1");
        doc2.setSize(500);
        doc2.setEditor(videoEditor);
        doc2.setCreationDate(LocalDate.now());
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify Image Editor average size is 0 (no documents)
        assertEquals(0.0, averageSizes.get("Image Editor"), 0.001);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Set up documents for Video Editor with varying sizes
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        doc1.setCreationDate(LocalDate.now());
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        doc2.setCreationDate(LocalDate.now());
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        doc3.setCreationDate(LocalDate.now());
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        doc4.setCreationDate(LocalDate.now());
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify Video Editor average size
        assertEquals(637.5, averageSizes.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Set up documents for Text Editor
        Document textDoc1 = new Document();
        textDoc1.setName("Doc1");
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        textDoc1.setCreationDate(LocalDate.now());
        
        Document textDoc2 = new Document();
        textDoc2.setName("Doc2");
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        textDoc2.setCreationDate(LocalDate.now());
        
        // Set up documents for Image Editor
        Document imageDoc1 = new Document();
        imageDoc1.setName("Image1");
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        imageDoc1.setCreationDate(LocalDate.now());
        
        Document imageDoc2 = new Document();
        imageDoc2.setName("Image2");
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        imageDoc2.setCreationDate(LocalDate.now());
        
        Document imageDoc3 = new Document();
        imageDoc3.setName("Image3");
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        imageDoc3.setCreationDate(LocalDate.now());
        
        // Set up document for Video Editor
        Document videoDoc1 = new Document();
        videoDoc1.setName("Video1");
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        videoDoc1.setCreationDate(LocalDate.now());
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify all editor average sizes
        assertEquals(150.0, averageSizes.get("Text Editor"), 0.001);
        assertEquals(1024.0, averageSizes.get("Image Editor"), 0.001);
        assertEquals(2048.0, averageSizes.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Create 100 documents for Text Editor, each with size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            doc.setCreationDate(LocalDate.now());
            fileSystem.addDocument(doc);
        }
        
        // Compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify Text Editor average size
        assertEquals(10.0, averageSizes.get("Text Editor"), 0.001);
    }
}