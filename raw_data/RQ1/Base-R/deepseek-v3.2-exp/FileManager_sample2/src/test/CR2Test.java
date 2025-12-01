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
        textEditor = new Editor("Text Editor");
        imageEditor = new Editor("Image Editor");
        videoEditor = new Editor("Video Editor");
    }

    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Create documents for Text Editor
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

        // Compute average sizes per editor
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();

        // Verify average size for Text Editor
        assertEquals(200.0, averageSizes.get("Text Editor"), 0.001);
    }

    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // No documents added for Image Editor

        // Compute average sizes per editor
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();

        // Verify average size for Image Editor is 0
        assertEquals(0.0, averageSizes.get("Image Editor"), 0.001);
    }

    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Create documents for Video Editor
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

        // Compute average sizes per editor
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();

        // Verify average size for Video Editor
        assertEquals(637.5, averageSizes.get("Video Editor"), 0.001);
    }

    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Create documents for Text Editor
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);

        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);

        // Create documents for Image Editor
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);

        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);

        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);

        // Create document for Video Editor
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

        // Compute average sizes per editor
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();

        // Verify average sizes for all editors
        assertEquals(150.0, averageSizes.get("Text Editor"), 0.001);
        assertEquals(1024.0, averageSizes.get("Image Editor"), 0.001);
        assertEquals(2048.0, averageSizes.get("Video Editor"), 0.001);
    }

    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Create 100 documents for Text Editor, each with size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }

        // Compute average sizes per editor
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();

        // Verify average size for Text Editor
        assertEquals(10.0, averageSizes.get("Text Editor"), 0.001);
    }
}