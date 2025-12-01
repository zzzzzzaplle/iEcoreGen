import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Set;

public class CR5Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() {
        // Set up documents as specified in test case 1
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreationDate(LocalDate.of(2023, 10, 1));
        doc1.setAuthor("Alice");
        doc1.setSize(100 * 1024); // Convert KB to bytes
        Editor editor1 = new Editor();
        editor1.setName("Text Editor");
        doc1.setEditor(editor1);
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreationDate(LocalDate.of(2023, 10, 2));
        doc2.setAuthor("Bob");
        doc2.setSize(150 * 1024);
        Editor editor2 = new Editor();
        editor2.setName("Text Editor");
        doc2.setEditor(editor2);
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreationDate(LocalDate.of(2023, 10, 3));
        doc3.setAuthor("Charlie");
        doc3.setSize(200 * 1024);
        Editor editor3 = new Editor();
        editor3.setName("Image Editor");
        doc3.setEditor(editor3);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        Set<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Verify the expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
        assertFalse(authors.contains("Charlie"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() {
        // Set up documents as specified in test case 2
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreationDate(LocalDate.of(2023, 9, 15));
        doc1.setAuthor("Dave");
        doc1.setSize(250 * 1024);
        Editor editor1 = new Editor();
        editor1.setName("Image Editor");
        doc1.setEditor(editor1);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreationDate(LocalDate.of(2023, 9, 20));
        doc2.setAuthor("Eve");
        doc2.setSize(300 * 1024);
        Editor editor2 = new Editor();
        editor2.setName("Image Editor");
        doc2.setEditor(editor2);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreationDate(LocalDate.of(2023, 9, 25));
        doc3.setAuthor("Frank");
        doc3.setSize(500 * 1024 * 1024); // Convert MB to bytes
        Editor editor3 = new Editor();
        editor3.setName("Video Editor");
        doc3.setEditor(editor3);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        Set<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Verify the expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
        assertFalse(authors.contains("Frank"));
    }
    
    @Test
    public void testCase3_NoAuthorsForASpecificEditor() {
        // Set up documents as specified in test case 3
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreationDate(LocalDate.of(2023, 10, 5));
        doc1.setAuthor("Grace");
        doc1.setSize(50 * 1024);
        Editor editor1 = new Editor();
        editor1.setName("Text Editor");
        doc1.setEditor(editor1);
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreationDate(LocalDate.of(2023, 10, 6));
        doc2.setAuthor("Heidi");
        doc2.setSize(80 * 1024);
        Editor editor2 = new Editor();
        editor2.setName("Image Editor");
        doc2.setEditor(editor2);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the method under test
        Set<String> authors = fileSystem.getAuthorsByEditor("Video Editor");
        
        // Verify the expected output - empty set
        assertNotNull(authors);
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // Set up documents as specified in test case 4
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreationDate(LocalDate.of(2023, 10, 7));
        doc1.setAuthor("Ivy");
        doc1.setSize(30 * 1024);
        Editor editor1 = new Editor();
        editor1.setName("Text Editor");
        doc1.setEditor(editor1);
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreationDate(LocalDate.of(2023, 10, 8));
        doc2.setAuthor("Jack");
        doc2.setSize(400 * 1024 * 1024);
        Editor editor2 = new Editor();
        editor2.setName("Video Editor");
        doc2.setEditor(editor2);
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreationDate(LocalDate.of(2023, 10, 9));
        doc3.setAuthor("Kathy");
        doc3.setSize(120 * 1024);
        Editor editor3 = new Editor();
        editor3.setName("Image Editor");
        doc3.setEditor(editor3);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        Set<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Verify the expected output
        assertEquals(1, authors.size());
        assertTrue(authors.contains("Ivy"));
        assertFalse(authors.contains("Jack"));
        assertFalse(authors.contains("Kathy"));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForASingleDocumentType() {
        // Set up documents as specified in test case 5
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreationDate(LocalDate.of(2023, 9, 30));
        doc1.setAuthor("Leo");
        doc1.setSize(600 * 1024);
        Editor editor1 = new Editor();
        editor1.setName("Image Editor");
        doc1.setEditor(editor1);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreationDate(LocalDate.of(2023, 10, 1));
        doc2.setAuthor("Mona");
        doc2.setSize(300 * 1024);
        Editor editor2 = new Editor();
        editor2.setName("Image Editor");
        doc2.setEditor(editor2);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreationDate(LocalDate.of(2023, 10, 2));
        doc3.setAuthor("Nina");
        doc3.setSize(150 * 1024);
        Editor editor3 = new Editor();
        editor3.setName("Text Editor");
        doc3.setEditor(editor3);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        Set<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Verify the expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
        assertFalse(authors.contains("Nina"));
    }

}