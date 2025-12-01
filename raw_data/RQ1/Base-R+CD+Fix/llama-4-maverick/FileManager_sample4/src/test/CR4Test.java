import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() throws Exception {
        // SetUp: Create a FileSystem instance and add documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setCreateDate(dateFormat.parse("2024-01-15 10:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(1024);
        TextEditor textEditor1 = new TextEditor();
        textEditor1.setName("TextEditor");
        doc1.setEditor(textEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setCreateDate(dateFormat.parse("2024-01-16 11:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(2048);
        ImageEditor imageEditor1 = new ImageEditor();
        imageEditor1.setName("ImageEditor");
        doc2.setEditor(imageEditor1);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2024-01-17 12:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(4096);
        VideoEditor videoEditor1 = new VideoEditor();
        videoEditor1.setName("VideoEditor");
        doc3.setEditor(videoEditor1);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals(Integer.valueOf(1), result.get("TextEditor"));
        assertEquals(Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() throws Exception {
        // SetUp: Create a FileSystem instance and add documents with only TextEditor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setCreateDate(dateFormat.parse("2024-01-18 09:00:00"));
        doc1.setAuthor("David");
        doc1.setSize(512);
        TextEditor textEditor1 = new TextEditor();
        textEditor1.setName("TextEditor");
        doc1.setEditor(textEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setCreateDate(dateFormat.parse("2024-01-19 10:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(256);
        TextEditor textEditor2 = new TextEditor();
        textEditor2.setName("TextEditor");
        doc2.setEditor(textEditor2);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals(Integer.valueOf(2), result.get("TextEditor"));
        assertNull(result.get("ImageEditor"));
        assertNull(result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() throws Exception {
        // SetUp: Create a FileSystem instance, add documents, then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setCreateDate(dateFormat.parse("2024-01-20 11:00:00"));
        doc1.setAuthor("Frank");
        doc1.setSize(1536);
        ImageEditor imageEditor1 = new ImageEditor();
        imageEditor1.setName("ImageEditor");
        doc1.setEditor(imageEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setCreateDate(dateFormat.parse("2024-01-21 12:00:00"));
        doc2.setAuthor("Grace");
        doc2.setSize(3072);
        VideoEditor videoEditor1 = new VideoEditor();
        videoEditor1.setName("VideoEditor");
        doc2.setEditor(videoEditor1);
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setCreateDate(dateFormat.parse("2024-01-22 13:00:00"));
        doc3.setAuthor("Henry");
        doc3.setSize(768);
        TextEditor textEditor1 = new TextEditor();
        textEditor1.setName("TextEditor");
        doc3.setEditor(textEditor1);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the document "Image1.png"
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals(Integer.valueOf(1), result.get("TextEditor"));
        assertNull(result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create a FileSystem instance without adding any documents
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output - empty map since no documents with editors
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() throws Exception {
        // SetUp: Create a FileSystem instance and add multiple documents with all editor types
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setCreateDate(dateFormat.parse("2024-01-23 14:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(640);
        TextEditor textEditor1 = new TextEditor();
        textEditor1.setName("TextEditor");
        doc1.setEditor(textEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setCreateDate(dateFormat.parse("2024-01-24 15:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(1792);
        ImageEditor imageEditor1 = new ImageEditor();
        imageEditor1.setName("ImageEditor");
        doc2.setEditor(imageEditor1);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setCreateDate(dateFormat.parse("2024-01-25 16:00:00"));
        doc3.setAuthor("Karen");
        doc3.setSize(3584);
        VideoEditor videoEditor1 = new VideoEditor();
        videoEditor1.setName("VideoEditor");
        doc3.setEditor(videoEditor1);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setCreateDate(dateFormat.parse("2024-01-26 17:00:00"));
        doc4.setAuthor("Leo");
        doc4.setSize(896);
        TextEditor textEditor2 = new TextEditor();
        textEditor2.setName("TextEditor");
        doc4.setEditor(textEditor2);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals(Integer.valueOf(2), result.get("TextEditor"));
        assertEquals(Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
}