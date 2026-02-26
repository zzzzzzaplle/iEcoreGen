import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
    }

    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Set up: Create documents for Text Editor
        Document doc1 = new Document("Report", LocalDate.of(2022, 1, 1), "Author1", 150, textEditor);
        Document doc2 = new Document("Essay", LocalDate.of(2022, 1, 2), "Author2", 200, textEditor);
        Document doc3 = new Document("Proposal", LocalDate.of(2022, 1, 3), "Author3", 250, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call method to compute average size per editor
        String result = fileSystem.computeAverageSizePerEditor();
        
        // Extract Text Editor's average from result
        String[] lines = result.split("\n");
        double textEditorAverage = 0;
        for (String line : lines) {
            if (line.startsWith("Text Editor:")) {
                textEditorAverage = Double.parseDouble(line.split(": ")[1]);
                break;
            }
        }
        
        // Expected output: Average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, textEditorAverage, 0.001);
    }

    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Set up: No documents associated with Image Editor
        
        // Call method to compute average size per editor
        String result = fileSystem.computeAverageSizePerEditor();
        
        // Extract Image Editor's average from result
        String[] lines = result.split("\n");
        double imageEditorAverage = 0;
        for (String line : lines) {
            if (line.startsWith("Image Editor:")) {
                imageEditorAverage = Double.parseDouble(line.split(": ")[1]);
                break;
            }
        }
        
        // Expected output: Average size = 0
        assertEquals(0.0, imageEditorAverage, 0.001);
    }

    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Set up: Create documents with varying sizes for Video Editor
        Document doc1 = new Document("Video1", LocalDate.of(2022, 1, 1), "Author1", 500, videoEditor);
        Document doc2 = new Document("Video2", LocalDate.of(2022, 1, 2), "Author2", 1000, videoEditor);
        Document doc3 = new Document("Video3", LocalDate.of(2022, 1, 3), "Author3", 750, videoEditor);
        Document doc4 = new Document("Video4", LocalDate.of(2022, 1, 4), "Author4", 300, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call method to compute average size per editor
        String result = fileSystem.computeAverageSizePerEditor();
        
        // Extract Video Editor's average from result
        String[] lines = result.split("\n");
        double videoEditorAverage = 0;
        for (String line : lines) {
            if (line.startsWith("Video Editor:")) {
                videoEditorAverage = Double.parseDouble(line.split(": ")[1]);
                break;
            }
        }
        
        // Expected output: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5, videoEditorAverage, 0.001);
    }

    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Set up: Create documents for all editors
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document("TextDoc1", LocalDate.of(2022, 1, 1), "Author1", 100, textEditor);
        Document textDoc2 = new Document("TextDoc2", LocalDate.of(2022, 1, 2), "Author2", 200, textEditor);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document("ImageDoc1", LocalDate.of(2022, 1, 3), "Author3", 1024, imageEditor);
        Document imageDoc2 = new Document("ImageDoc2", LocalDate.of(2022, 1, 4), "Author4", 1536, imageEditor);
        Document imageDoc3 = new Document("ImageDoc3", LocalDate.of(2022, 1, 5), "Author5", 512, imageEditor);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document("VideoDoc1", LocalDate.of(2022, 1, 6), "Author6", 2048, videoEditor);
        
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call method to compute average size per editor
        String result = fileSystem.computeAverageSizePerEditor();
        
        // Parse the result string to extract averages for each editor
        String[] lines = result.split("\n");
        double textEditorAverage = 0;
        double imageEditorAverage = 0;
        double videoEditorAverage = 0;
        
        for (String line : lines) {
            if (line.startsWith("Text Editor:")) {
                textEditorAverage = Double.parseDouble(line.split(": ")[1]);
            } else if (line.startsWith("Image Editor:")) {
                imageEditorAverage = Double.parseDouble(line.split(": ")[1]);
            } else if (line.startsWith("Video Editor:")) {
                videoEditorAverage = Double.parseDouble(line.split(": ")[1]);
            }
        }
        
        // Expected outputs:
        // Text Editor Average size = (100 + 200) / 2 = 150
        // Image Editor Average size = (1024 + 1536 + 512) / 3 = 1024
        // Video Editor Average size = 2048
        assertEquals(150.0, textEditorAverage, 0.001);
        assertEquals(1024.0, imageEditorAverage, 0.001);
        assertEquals(2048.0, videoEditorAverage, 0.001);
    }

    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Set up: Create 100 documents for Text Editor, each with size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document("Doc" + i, LocalDate.of(2022, 1, i), "Author" + i, 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call method to compute average size per editor
        String result = fileSystem.computeAverageSizePerEditor();
        
        // Extract Text Editor's average from result
        String[] lines = result.split("\n");
        double textEditorAverage = 0;
        for (String line : lines) {
            if (line.startsWith("Text Editor:")) {
                textEditorAverage = Double.parseDouble(line.split(": ")[1]);
                break;
            }
        }
        
        // Expected output: Average size = (10 * 100) / 100 = 10
        assertEquals(10.0, textEditorAverage, 0.001);
    }
}