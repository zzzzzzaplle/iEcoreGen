import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }
    
    @Test
    public void testCase1_countDocumentsWithMixedEditorTypes() throws ParseException {
        // SetUp: Create documents with different editor types
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setCreateDate(sdf.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setCreateDate(sdf.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(200);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(sdf.parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(300);
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify the results
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_countDocumentsWithSingleEditorType() throws ParseException {
        // SetUp: Create documents with only TextEditor
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setCreateDate(sdf.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setCreateDate(sdf.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(75);
        doc2.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify the results
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertNull(result.get("Image Editor"));
        assertNull(result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_countDocumentsAfterRemoval() throws ParseException {
        // SetUp: Create documents and then remove one
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setCreateDate(sdf.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(200);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setCreateDate(sdf.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(300);
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setCreateDate(sdf.parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(100);
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove a document
        fileSystem.removeDocument(doc1);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify the results
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertNull(result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // SetUp: Create an empty FileSystem
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify the results
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() throws ParseException {
        // SetUp: Create multiple documents with various editors
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setCreateDate(sdf.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setCreateDate(sdf.parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(200);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setCreateDate(sdf.parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(300);
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setCreateDate(sdf.parse("2023-01-04 13:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(150);
        doc4.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify the results
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
}