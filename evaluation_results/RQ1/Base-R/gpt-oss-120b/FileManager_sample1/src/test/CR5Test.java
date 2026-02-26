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
        // SetUp: Create documents with Text Editor and Image Editor
        Document doc1 = new Document("Report.doc", LocalDate.of(2023, 10, 1), "Alice", 100L, new TextEditor());
        Document doc2 = new Document("Essay.doc", LocalDate.of(2023, 10, 2), "Bob", 150L, new TextEditor());
        Document doc3 = new Document("Image.png", LocalDate.of(2023, 10, 3), "Charlie", 200L, new ImageEditor());
        
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
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() {
        // SetUp: Create documents with Image Editor and Video Editor
        Document doc1 = new Document("Photo.jpg", LocalDate.of(2023, 9, 15), "Dave", 250L, new ImageEditor());
        Document doc2 = new Document("Diagram.svg", LocalDate.of(2023, 9, 20), "Eve", 300L, new ImageEditor());
        Document doc3 = new Document("Video.mp4", LocalDate.of(2023, 9, 25), "Frank", 500000L, new VideoEditor());
        
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
    public void testCase3_NoAuthorsForASpecificEditor() {
        // SetUp: Create documents with Text Editor and Image Editor only
        Document doc1 = new Document("Document.txt", LocalDate.of(2023, 10, 5), "Grace", 50L, new TextEditor());
        Document doc2 = new Document("Drawing.png", LocalDate.of(2023, 10, 6), "Heidi", 80L, new ImageEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Video Editor");
        
        // Expected Output: Authors = []
        assertTrue(authors.isEmpty());
    }

    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // SetUp: Create documents with mixed editors
        Document doc1 = new Document("Notes.txt", LocalDate.of(2023, 10, 7), "Ivy", 30L, new TextEditor());
        Document doc2 = new Document("Video.mp4", LocalDate.of(2023, 10, 8), "Jack", 400000L, new VideoEditor());
        Document doc3 = new Document("Image.JPG", LocalDate.of(2023, 10, 9), "Kathy", 120L, new ImageEditor());
        
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
    public void testCase5_MultipleAuthorsForASingleDocumentType() {
        // SetUp: Create multiple documents with Image Editor
        Document doc1 = new Document("Portfolio.jpg", LocalDate.of(2023, 9, 30), "Leo", 600L, new ImageEditor());
        Document doc2 = new Document("Banner.png", LocalDate.of(2023, 10, 1), "Mona", 300L, new ImageEditor());
        Document doc3 = new Document("Presentation.ppt", LocalDate.of(2023, 10, 2), "Nina", 150L, new TextEditor());
        
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