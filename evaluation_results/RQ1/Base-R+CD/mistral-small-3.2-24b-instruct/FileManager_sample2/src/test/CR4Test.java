import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        textEditor.setName("TextEditor");
        imageEditor = new ImageEditor();
        imageEditor.setName("ImageEditor");
        videoEditor = new VideoEditor();
        videoEditor.setName("VideoEditor");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_countDocumentsWithMixedEditorTypes() {
        // SetUp: Create documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        doc1.setSize(100);
        doc1.setCreateDate(new Date());
        doc1.setAuthor("Author1");
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        doc2.setSize(200);
        doc2.setCreateDate(new Date());
        doc2.setAuthor("Author2");
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        doc3.setSize(300);
        doc3.setCreateDate(new Date());
        doc3.setAuthor("Author3");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Each editor type should have exactly 1 document
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }

    @Test
    public void testCase2_countDocumentsWithSingleEditorType() {
        // SetUp: Create documents with only TextEditor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        doc1.setSize(150);
        doc1.setCreateDate(new Date());
        doc1.setAuthor("Author1");
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        doc2.setSize(50);
        doc2.setCreateDate(new Date());
        doc2.setAuthor("Author2");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Only TextEditor should have documents
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertNull("ImageEditor should not be present", result.get("ImageEditor"));
        assertNull("VideoEditor should not be present", result.get("VideoEditor"));
    }

    @Test
    public void testCase3_countDocumentsAfterRemoval() {
        // SetUp: Create documents and then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(imageEditor);
        doc1.setSize(250);
        doc1.setCreateDate(new Date());
        doc1.setAuthor("Author1");
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(videoEditor);
        doc2.setSize(500);
        doc2.setCreateDate(new Date());
        doc2.setAuthor("Author2");
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(textEditor);
        doc3.setSize(100);
        doc3.setCreateDate(new Date());
        doc3.setAuthor("Author3");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.removeDocument(doc1); // Remove the image document
        
        // Execute: Count documents per editor type after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: ImageEditor should have 0 documents after removal
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertNull("ImageEditor should not be present", result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }

    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // SetUp: FileSystem is empty (no setup needed beyond default)
        
        // Execute: Count documents per editor type when no documents exist
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Result map should be empty since no documents exist
        assertTrue("Result map should be empty when no documents exist", result.isEmpty());
    }

    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() {
        // SetUp: Create documents with all editor types, including multiple TextEditor documents
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        doc1.setSize(100);
        doc1.setCreateDate(new Date());
        doc1.setAuthor("Author1");
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        doc2.setSize(300);
        doc2.setCreateDate(new Date());
        doc2.setAuthor("Author2");
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        doc3.setSize(800);
        doc3.setCreateDate(new Date());
        doc3.setAuthor("Author3");
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        doc4.setSize(150);
        doc4.setCreateDate(new Date());
        doc4.setAuthor("Author4");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: TextEditor has 2, others have 1 each
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
}