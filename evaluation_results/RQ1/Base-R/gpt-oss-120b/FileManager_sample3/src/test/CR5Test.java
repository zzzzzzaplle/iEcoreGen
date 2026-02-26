import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Set;

public class CR5Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize a fresh FileSystem before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Report.doc", LocalDate.of(2023, 10, 1), 
                                    new Author("Alice"), 100 * 1024, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Essay.doc", LocalDate.of(2023, 10, 2), 
                                    new Author("Bob"), 150 * 1024, Editor.TEXT_EDITOR);
        Document doc3 = new Document("Image.png", LocalDate.of(2023, 10, 3), 
                                    new Author("Charlie"), 200 * 1024, Editor.IMAGE_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor(Editor.TEXT_EDITOR);
        
        // Expected Output: Authors = ["Alice", "Bob"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Photo.jpg", LocalDate.of(2023, 9, 15), 
                                    new Author("Dave"), 250 * 1024, Editor.IMAGE_EDITOR);
        Document doc2 = new Document("Diagram.svg", LocalDate.of(2023, 9, 20), 
                                    new Author("Eve"), 300 * 1024, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Video.mp4", LocalDate.of(2023, 9, 25), 
                                    new Author("Frank"), 500 * 1024 * 1024, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor(Editor.IMAGE_EDITOR);
        
        // Expected Output: Authors = ["Dave", "Eve"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Document.txt", LocalDate.of(2023, 10, 5), 
                                    new Author("Grace"), 50 * 1024, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Drawing.png", LocalDate.of(2023, 10, 6), 
                                    new Author("Heidi"), 80 * 1024, Editor.IMAGE_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor(Editor.VIDEO_EDITOR);
        
        // Expected Output: Authors = []
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Notes.txt", LocalDate.of(2023, 10, 7), 
                                    new Author("Ivy"), 30 * 1024, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Video.mp4", LocalDate.of(2023, 10, 8), 
                                    new Author("Jack"), 400 * 1024 * 1024, Editor.VIDEO_EDITOR);
        Document doc3 = new Document("Image.JPG", LocalDate.of(2023, 10, 9), 
                                    new Author("Kathy"), 120 * 1024, Editor.IMAGE_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor(Editor.TEXT_EDITOR);
        
        // Expected Output: Authors = ["Ivy"]
        assertEquals(1, authors.size());
        assertTrue(authors.contains("Ivy"));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Portfolio.jpg", LocalDate.of(2023, 9, 30), 
                                    new Author("Leo"), 600 * 1024, Editor.IMAGE_EDITOR);
        Document doc2 = new Document("Banner.png", LocalDate.of(2023, 10, 1), 
                                    new Author("Mona"), 300 * 1024, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Presentation.ppt", LocalDate.of(2023, 10, 2), 
                                    new Author("Nina"), 150 * 1024, Editor.TEXT_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor(Editor.IMAGE_EDITOR);
        
        // Expected Output: Authors = ["Leo", "Mona"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
    }

}