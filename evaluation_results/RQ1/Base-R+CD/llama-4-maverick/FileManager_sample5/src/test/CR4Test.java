import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

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
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_countDocumentsWithMixedEditorTypes() throws ParseException {
        // Create documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(1024);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setCreateDate(dateFormat.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(2048);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(4096);
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify counts
        assertEquals(1, (int) result.get("TextEditor"));
        assertEquals(1, (int) result.get("ImageEditor"));
        assertEquals(1, (int) result.get("VideoEditor"));
    }

    @Test
    public void testCase2_countDocumentsWithSingleEditorType() throws ParseException {
        // Create documents with TextEditor only
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(1024);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setCreateDate(dateFormat.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(512);
        doc2.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify counts
        assertEquals(2, (int) result.get("TextEditor"));
        assertEquals(0, (int) result.get("ImageEditor"));
        assertEquals(0, (int) result.get("VideoEditor"));
    }

    @Test
    public void testCase3_countDocumentsAfterRemoval() throws ParseException {
        // Create documents
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(2048);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setCreateDate(dateFormat.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(4096);
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setCreateDate(dateFormat.parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(1024);
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove one document
        fileSystem.removeDocument(doc1);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify counts
        assertEquals(1, (int) result.get("TextEditor"));
        assertEquals(0, (int) result.get("ImageEditor"));
        assertEquals(1, (int) result.get("VideoEditor"));
    }

    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // Count documents per editor type without adding any documents
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify counts are all zero
        assertEquals(0, (int) result.get("TextEditor"));
        assertEquals(0, (int) result.get("ImageEditor"));
        assertEquals(0, (int) result.get("VideoEditor"));
    }

    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() throws ParseException {
        // Create documents with various editor types
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(1024);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setCreateDate(dateFormat.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(2048);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setCreateDate(dateFormat.parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(4096);
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setCreateDate(dateFormat.parse("2023-01-04 13:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(512);
        doc4.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify counts
        assertEquals(2, (int) result.get("TextEditor"));
        assertEquals(1, (int) result.get("ImageEditor"));
        assertEquals(1, (int) result.get("VideoEditor"));
    }
}