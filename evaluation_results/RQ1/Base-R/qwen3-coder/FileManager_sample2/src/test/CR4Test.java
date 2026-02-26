import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create FileSystem instance and add documents with different editors
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        Editor textEditor1 = new Editor();
        textEditor1.setName("Text Editor");
        doc1.setEditor(textEditor1);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Image.png");
        Editor imageEditor1 = new Editor();
        imageEditor1.setName("Image Editor");
        doc2.setEditor(imageEditor1);
        fileSystem.addDocument(doc2);

        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        Editor videoEditor1 = new Editor();
        videoEditor1.setName("Video Editor");
        doc3.setEditor(videoEditor1);
        fileSystem.addDocument(doc3);

        // Execute: Count documents by editor type
        int[] result = fileSystem.countDocumentsByEditor();

        // Verify: Each editor type should have 1 document
        assertEquals("Text Editor count should be 1", 1, result[0]);
        assertEquals("Image Editor count should be 1", 1, result[1]);
        assertEquals("Video Editor count should be 1", 1, result[2]);
    }

    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create FileSystem instance and add documents with only Text Editor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        Editor textEditor1 = new Editor();
        textEditor1.setName("Text Editor");
        doc1.setEditor(textEditor1);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        Editor textEditor2 = new Editor();
        textEditor2.setName("Text Editor");
        doc2.setEditor(textEditor2);
        fileSystem.addDocument(doc2);

        // Execute: Count documents by editor type
        int[] result = fileSystem.countDocumentsByEditor();

        // Verify: Only Text Editor should have documents
        assertEquals("Text Editor count should be 2", 2, result[0]);
        assertEquals("Image Editor count should be 0", 0, result[1]);
        assertEquals("Video Editor count should be 0", 0, result[2]);
    }

    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create FileSystem instance and add documents, then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        Editor imageEditor1 = new Editor();
        imageEditor1.setName("Image Editor");
        doc1.setEditor(imageEditor1);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        Editor videoEditor1 = new Editor();
        videoEditor1.setName("Video Editor");
        doc2.setEditor(videoEditor1);
        fileSystem.addDocument(doc2);

        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        Editor textEditor1 = new Editor();
        textEditor1.setName("Text Editor");
        doc3.setEditor(textEditor1);
        fileSystem.addDocument(doc3);

        // Remove the Image1.png document
        fileSystem.removeDocument(doc1);

        // Execute: Count documents by editor type after removal
        int[] result = fileSystem.countDocumentsByEditor();

        // Verify: Text Editor and Video Editor should have 1 each, Image Editor should be 0
        assertEquals("Text Editor count should be 1", 1, result[0]);
        assertEquals("Image Editor count should be 0", 0, result[1]);
        assertEquals("Video Editor count should be 1", 1, result[2]);
    }

    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create FileSystem instance with no documents
        // No documents added to fileSystem

        // Execute: Count documents by editor type
        int[] result = fileSystem.countDocumentsByEditor();

        // Verify: All editor types should have 0 documents
        assertEquals("Text Editor count should be 0", 0, result[0]);
        assertEquals("Image Editor count should be 0", 0, result[1]);
        assertEquals("Video Editor count should be 0", 0, result[2]);
    }

    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create FileSystem instance and add documents with all editor types
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        Editor textEditor1 = new Editor();
        textEditor1.setName("Text Editor");
        doc1.setEditor(textEditor1);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        Editor imageEditor1 = new Editor();
        imageEditor1.setName("Image Editor");
        doc2.setEditor(imageEditor1);
        fileSystem.addDocument(doc2);

        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        Editor videoEditor1 = new Editor();
        videoEditor1.setName("Video Editor");
        doc3.setEditor(videoEditor1);
        fileSystem.addDocument(doc3);

        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        Editor textEditor2 = new Editor();
        textEditor2.setName("Text Editor");
        doc4.setEditor(textEditor2);
        fileSystem.addDocument(doc4);

        // Execute: Count documents by editor type
        int[] result = fileSystem.countDocumentsByEditor();

        // Verify: Text Editor should have 2, others should have 1 each
        assertEquals("Text Editor count should be 2", 2, result[0]);
        assertEquals("Image Editor count should be 1", 1, result[1]);
        assertEquals("Video Editor count should be 1", 1, result[2]);
    }
}