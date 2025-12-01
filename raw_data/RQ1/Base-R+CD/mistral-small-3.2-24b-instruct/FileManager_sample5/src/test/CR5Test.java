import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CR5Test {
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        textEditor = new TextEditor("Text Editor");
        imageEditor = new ImageEditor("Image Editor");
        videoEditor = new VideoEditor("Video Editor");
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws ParseException {
        // Set up documents as specified
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreateDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        assertEquals("Should return 2 authors for Text Editor", 2, authors.size());
        assertTrue("Should contain Alice", authors.contains("Alice"));
        assertTrue("Should contain Bob", authors.contains("Bob"));
        assertFalse("Should not contain Charlie", authors.contains("Charlie"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws ParseException {
        // Set up documents as specified
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB (assuming 1MB = 1000KB for this test)
        doc3.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        assertEquals("Should return 2 authors for Image Editor", 2, authors.size());
        assertTrue("Should contain Dave", authors.contains("Dave"));
        assertTrue("Should contain Eve", authors.contains("Eve"));
        assertFalse("Should not contain Frank", authors.contains("Frank"));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws ParseException {
        // Set up documents as specified
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Retrieve authors for Video Editor (no documents with this editor)
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Verify expected output
        assertTrue("Should return empty list for Video Editor", authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws ParseException {
        // Set up documents as specified
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        assertEquals("Should return 1 author for Text Editor", 1, authors.size());
        assertEquals("Should contain Ivy", "Ivy", authors.get(0));
        assertFalse("Should not contain Jack", authors.contains("Jack"));
        assertFalse("Should not contain Kathy", authors.contains("Kathy"));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws ParseException {
        // Set up documents as specified
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        assertEquals("Should return 2 authors for Image Editor", 2, authors.size());
        assertTrue("Should contain Leo", authors.contains("Leo"));
        assertTrue("Should contain Mona", authors.contains("Mona"));
        assertFalse("Should not contain Nina", authors.contains("Nina"));
    }
}