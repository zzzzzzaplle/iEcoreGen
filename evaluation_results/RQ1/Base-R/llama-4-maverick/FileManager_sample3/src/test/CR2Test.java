import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR2Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        // Initialize file system and editors before each test
        fileSystem = new FileSystem();
        
        textEditor = new Editor();
        textEditor.setName("Text Editor");
        
        imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        
        videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Test Case 1: Single Editor with Multiple Documents
        // SetUp: Associate "Text Editor" with 3 documents
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
        
        // Call computeAverageSizePerEditor and verify Text Editor's average
        List<Double> averages = fileSystem.computeAverageSizePerEditor();
        
        // Find Text Editor's average (index 0 since it was added first)
        double textEditorAverage = averages.get(0);
        
        // Expected: (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, textEditorAverage, 0.001);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Test Case 2: No Documents for an Editor
        // SetUp: Associate "Image Editor" with no documents
        // Image Editor is already in the file system with no documents
        
        // Call computeAverageSizePerEditor and verify Image Editor's average
        List<Double> averages = fileSystem.computeAverageSizePerEditor();
        
        // Find Image Editor's average (index 1 since it was added second)
        double imageEditorAverage = averages.get(1);
        
        // Expected: Average size = 0
        assertEquals(0.0, imageEditorAverage, 0.001);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Test Case 3: Editor with Documents of Varying Sizes
        // SetUp: Associate "Video Editor" with 4 documents
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
        
        // Call computeAverageSizePerEditor and verify Video Editor's average
        List<Double> averages = fileSystem.computeAverageSizePerEditor();
        
        // Find Video Editor's average (index 2 since it was added third)
        double videoEditorAverage = averages.get(2);
        
        // Expected: (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5, videoEditorAverage, 0.001);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Test Case 4: Multiple Editors with Document Distribution
        // SetUp: Associate each editor with specified documents
        
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
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call computeAverageSizePerEditor
        List<Double> averages = fileSystem.computeAverageSizePerEditor();
        
        // Verify each editor's average
        // Text Editor (index 0): (100 + 200) / 2 = 150
        assertEquals(150.0, averages.get(0), 0.001);
        
        // Image Editor (index 1): (1024 + 1536 + 512) / 3 = 1024
        assertEquals(1024.0, averages.get(1), 0.001);
        
        // Video Editor (index 2): 2048
        assertEquals(2048.0, averages.get(2), 0.001);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Test Case 5: Large Number of Documents for One Editor
        // SetUp: Associate "Text Editor" with 100 documents, each 10 in size
        
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + (i + 1));
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call computeAverageSizePerEditor and verify Text Editor's average
        List<Double> averages = fileSystem.computeAverageSizePerEditor();
        
        // Find Text Editor's average (index 0 since it was added first)
        double textEditorAverage = averages.get(0);
        
        // Expected: (10 * 100) / 100 = 10
        assertEquals(10.0, textEditorAverage, 0.001);
    }
}