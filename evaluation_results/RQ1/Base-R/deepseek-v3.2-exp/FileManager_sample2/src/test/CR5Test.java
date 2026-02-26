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
        // Set up test data
        Editor textEditor = new Editor("Text Editor");
        Editor imageEditor = new Editor("Image Editor");
        
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreationDate(LocalDate.of(2023, 10, 1));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreationDate(LocalDate.of(2023, 10, 2));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreationDate(LocalDate.of(2023, 10, 3));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        Set<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Verify expected results
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
        assertFalse(authors.contains("Charlie"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() {
        // Set up test data
        Editor imageEditor = new Editor("Image Editor");
        Editor videoEditor = new Editor("Video Editor");
        
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreationDate(LocalDate.of(2023, 9, 15));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreationDate(LocalDate.of(2023, 9, 20));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreationDate(LocalDate.of(2023, 9, 25));
        doc3.setAuthor("Frank");
        doc3.setSize(500000);
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        Set<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Verify expected results
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
        assertFalse(authors.contains("Frank"));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() {
        // Set up test data
        Editor textEditor = new Editor("Text Editor");
        Editor imageEditor = new Editor("Image Editor");
        
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreationDate(LocalDate.of(2023, 10, 5));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreationDate(LocalDate.of(2023, 10, 6));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the method under test
        Set<String> authors = fileSystem.getAuthorsByEditor("Video Editor");
        
        // Verify expected results - should be empty set
        assertNotNull(authors);
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // Set up test data
        Editor textEditor = new Editor("Text Editor");
        Editor videoEditor = new Editor("Video Editor");
        Editor imageEditor = new Editor("Image Editor");
        
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreationDate(LocalDate.of(2023, 10, 7));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreationDate(LocalDate.of(2023, 10, 8));
        doc2.setAuthor("Jack");
        doc2.setSize(400000);
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreationDate(LocalDate.of(2023, 10, 9));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        Set<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Verify expected results
        assertEquals(1, authors.size());
        assertTrue(authors.contains("Ivy"));
        assertFalse(authors.contains("Jack"));
        assertFalse(authors.contains("Kathy"));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() {
        // Set up test data
        Editor imageEditor = new Editor("Image Editor");
        Editor textEditor = new Editor("Text Editor");
        
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreationDate(LocalDate.of(2023, 9, 30));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreationDate(LocalDate.of(2023, 10, 1));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreationDate(LocalDate.of(2023, 10, 2));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        Set<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Verify expected results
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
        assertFalse(authors.contains("Nina"));
    }
}