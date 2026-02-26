import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR5Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws Exception {
        // SetUp: Create documents with Text Editor and other editors
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(new TextEditor());
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreateDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(new ImageEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using Text Editor
        TextEditor textEditor = new TextEditor();
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Expected Output: Authors = ["Alice", "Bob"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
        assertFalse(authors.contains("Charlie"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // SetUp: Create documents with Image Editor and other editors
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(new ImageEditor());
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(new VideoEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using Image Editor
        ImageEditor imageEditor = new ImageEditor();
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Expected Output: Authors = ["Dave", "Eve"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
        assertFalse(authors.contains("Frank"));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // SetUp: Create documents without Video Editor
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(new ImageEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using Video Editor
        VideoEditor videoEditor = new VideoEditor();
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Expected Output: Authors = []
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // SetUp: Create documents with mixed editors
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(new VideoEditor());
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(new ImageEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using Text Editor
        TextEditor textEditor = new TextEditor();
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Expected Output: Authors = ["Ivy"]
        assertEquals(1, authors.size());
        assertEquals("Ivy", authors.get(0));
        assertFalse(authors.contains("Jack"));
        assertFalse(authors.contains("Kathy"));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // SetUp: Create documents with Image Editor and Text Editor
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(new ImageEditor());
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using Image Editor
        ImageEditor imageEditor = new ImageEditor();
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Expected Output: Authors = ["Leo", "Mona"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
        assertFalse(authors.contains("Nina"));
        
        // Verify order doesn't matter by checking both possible orders
        List<String> expectedAuthors = Arrays.asList("Leo", "Mona");
        assertTrue(authors.containsAll(expectedAuthors));
    }
}