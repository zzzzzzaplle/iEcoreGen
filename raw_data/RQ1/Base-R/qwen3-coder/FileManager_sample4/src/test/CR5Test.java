import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
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
        // SetUp: Create documents with Text Editors and other editors
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreationDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(new TextEditor());
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreationDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(new ImageEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        Set<String> result = fileSystem.getAuthorsByEditor(new TextEditor());
        
        // Expected Output: Authors = ["Alice", "Bob"]
        Set<String> expected = new HashSet<>();
        expected.add("Alice");
        expected.add("Bob");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // SetUp: Create documents with Image Editors and other editors
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(new ImageEditor());
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreationDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreationDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(new VideoEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        Set<String> result = fileSystem.getAuthorsByEditor(new ImageEditor());
        
        // Expected Output: Authors = ["Dave", "Eve"]
        Set<String> expected = new HashSet<>();
        expected.add("Dave");
        expected.add("Eve");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // SetUp: Create documents without Video Editor
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(new ImageEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        Set<String> result = fileSystem.getAuthorsByEditor(new VideoEditor());
        
        // Expected Output: Authors = []
        Set<String> expected = new HashSet<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // SetUp: Create documents with mixed editors
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreationDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(new VideoEditor());
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreationDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(new ImageEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        Set<String> result = fileSystem.getAuthorsByEditor(new TextEditor());
        
        // Expected Output: Authors = ["Ivy"]
        Set<String> expected = new HashSet<>();
        expected.add("Ivy");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // SetUp: Create multiple documents with Image Editor
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(new ImageEditor());
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreationDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        Set<String> result = fileSystem.getAuthorsByEditor(new ImageEditor());
        
        // Expected Output: Authors = ["Leo", "Mona"]
        Set<String> expected = new HashSet<>();
        expected.add("Leo");
        expected.add("Mona");
        
        assertEquals(expected, result);
    }
}