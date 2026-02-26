import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

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
        // SetUp: Create file system with specified documents
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor("Text Editor");
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreationDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor("Text Editor");
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreationDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor("Image Editor");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Expected Output: Authors = ["Alice", "Bob"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // SetUp: Create file system with specified documents
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor("Image Editor");
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreationDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor("Image Editor");
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreationDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor("Video Editor");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Expected Output: Authors = ["Dave", "Eve"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // SetUp: Create file system with specified documents
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor("Text Editor");
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor("Image Editor");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Video Editor");
        
        // Expected Output: Authors = []
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // SetUp: Create file system with specified documents
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor("Text Editor");
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreationDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor("Video Editor");
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreationDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor("Image Editor");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Expected Output: Authors = ["Ivy"]
        assertEquals(1, authors.size());
        assertTrue(authors.contains("Ivy"));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // SetUp: Create file system with specified documents
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor("Image Editor");
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor("Image Editor");
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreationDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor("Text Editor");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Expected Output: Authors = ["Leo", "Mona"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
    }
}