import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR4Test {
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;

    @Before
    public void setUp() {
        // Initialize FileSystem and editors before each test
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
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Add one document for each editor type
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        fileSystem.addDocument(doc2);

        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        fileSystem.addDocument(doc3);

        // Execute: Count documents per editor
        List<Long> result = fileSystem.countDocumentsPerEditor();

        // Verify: Each editor should have exactly 1 document
        assertEquals("TextEditor should have 1 document", Long.valueOf(1), result.get(0));
        assertEquals("ImageEditor should have 1 document", Long.valueOf(1), result.get(1));
        assertEquals("VideoEditor should have 1 document", Long.valueOf(1), result.get(2));
    }

    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Add two documents only for TextEditor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        fileSystem.addDocument(doc2);

        // Execute: Count documents per editor
        List<Long> result = fileSystem.countDocumentsPerEditor();

        // Verify: Only TextEditor should have documents (2), others should have 0
        assertEquals("TextEditor should have 2 documents", Long.valueOf(2), result.get(0));
        assertEquals("ImageEditor should have 0 documents", Long.valueOf(0), result.get(1));
        assertEquals("VideoEditor should have 0 documents", Long.valueOf(0), result.get(2));
    }

    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Add documents for all editors, then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(imageEditor);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(videoEditor);
        fileSystem.addDocument(doc2);

        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(textEditor);
        fileSystem.addDocument(doc3);

        // Remove the Image1.png document
        fileSystem.removeDocument(doc1);

        // Execute: Count documents per editor after removal
        List<Long> result = fileSystem.countDocumentsPerEditor();

        // Verify: TextEditor and VideoEditor should have 1 document each, ImageEditor should have 0
        assertEquals("TextEditor should have 1 document", Long.valueOf(1), result.get(0));
        assertEquals("ImageEditor should have 0 documents", Long.valueOf(0), result.get(1));
        assertEquals("VideoEditor should have 1 document", Long.valueOf(1), result.get(2));
    }

    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create empty FileSystem (no documents added)
        FileSystem emptyFileSystem = new FileSystem();
        
        // Add editors to file system but no documents
        emptyFileSystem.getEditors().add(textEditor);
        emptyFileSystem.getEditors().add(imageEditor);
        emptyFileSystem.getEditors().add(videoEditor);

        // Execute: Count documents per editor
        List<Long> result = emptyFileSystem.countDocumentsPerEditor();

        // Verify: All editors should have 0 documents
        assertEquals("TextEditor should have 0 documents", Long.valueOf(0), result.get(0));
        assertEquals("ImageEditor should have 0 documents", Long.valueOf(0), result.get(1));
        assertEquals("VideoEditor should have 0 documents", Long.valueOf(0), result.get(2));
    }

    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Add multiple documents with all editors used
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        fileSystem.addDocument(doc2);

        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        fileSystem.addDocument(doc3);

        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        fileSystem.addDocument(doc4);

        // Execute: Count documents per editor
        List<Long> result = fileSystem.countDocumentsPerEditor();

        // Verify: TextEditor should have 2, ImageEditor and VideoEditor should have 1 each
        assertEquals("TextEditor should have 2 documents", Long.valueOf(2), result.get(0));
        assertEquals("ImageEditor should have 1 document", Long.valueOf(1), result.get(1));
        assertEquals("VideoEditor should have 1 document", Long.valueOf(1), result.get(2));
    }
}