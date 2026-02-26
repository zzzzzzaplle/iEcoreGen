import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;

    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        // Get the editors from the file system
        List<Editor> editors = fileSystem.getEditors();
        for (Editor editor : editors) {
            if (editor.getName().equals("Text Editor")) {
                textEditor = (TextEditor) editor;
            } else if (editor.getName().equals("Image Editor")) {
                imageEditor = (ImageEditor) editor;
            } else if (editor.getName().equals("Video Editor")) {
                videoEditor = (VideoEditor) editor;
            }
        }
    }

    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Set up: Create and add 3 documents associated with Text Editor
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
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();

        // Verify the average size for Text Editor
        assertEquals(200.0f, averageSizes.get("Text Editor"), 0.001f);
    }

    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Set up: No documents associated with Image Editor
        // Call the method to compute average sizes
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();

        // Verify that Image Editor is not in the result map (no average calculated for editors with no documents)
        assertFalse(averageSizes.containsKey("Image Editor"));
    }

    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Set up: Create and add 4 documents associated with Video Editor
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
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();

        // Verify the average size for Video Editor
        assertEquals(637.5f, averageSizes.get("Video Editor"), 0.001f);
    }

    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Set up: Create and add documents for all three editors
        // Text Editor documents
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);

        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);

        // Image Editor documents
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);

        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);

        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);

        // Video Editor document
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

        // Call the method to compute average sizes
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();

        // Verify average sizes for all editors
        assertEquals(150.0f, averageSizes.get("Text Editor"), 0.001f);
        assertEquals(1024.0f, averageSizes.get("Image Editor"), 0.001f);
        assertEquals(2048.0f, averageSizes.get("Video Editor"), 0.001f);
    }

    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Set up: Create and add 100 documents, each with size 10, associated with Text Editor
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }

        // Call the method to compute average sizes
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();

        // Verify the average size for Text Editor
        assertEquals(10.0f, averageSizes.get("Text Editor"), 0.001f);
    }
}