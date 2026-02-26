import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Set;

public class CR5Test {
    
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
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Report.doc", LocalDate.of(2023, 10, 1), "Alice", 102400, textEditor);
        Document doc2 = new Document("Essay.doc", LocalDate.of(2023, 10, 2), "Bob", 153600, textEditor);
        Document doc3 = new Document("Image.png", LocalDate.of(2023, 10, 3), "Charlie", 204800, imageEditor);
        
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
        // SetUp: Create documents as specified
        Document doc1 = new Document("Photo.jpg", LocalDate.of(2023, 9, 15), "Dave", 256000, imageEditor);
        Document doc2 = new Document("Diagram.svg", LocalDate.of(2023, 9, 20), "Eve", 307200, imageEditor);
        Document doc3 = new Document("Video.mp4", LocalDate.of(2023, 9, 25), "Frank", 524288000, videoEditor);
        
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
        // SetUp: Create documents as specified
        Document doc1 = new Document("Document.txt", LocalDate.of(2023, 10, 5), "Grace", 51200, textEditor);
        Document doc2 = new Document("Drawing.png", LocalDate.of(2023, 10, 6), "Heidi", 81920, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Video Editor");
        
        // Expected Output: Authors = []
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Notes.txt", LocalDate.of(2023, 10, 7), "Ivy", 30720, textEditor);
        Document doc2 = new Document("Video.mp4", LocalDate.of(2023, 10, 8), "Jack", 419430400, videoEditor);
        Document doc3 = new Document("Image.JPG", LocalDate.of(2023, 10, 9), "Kathy", 122880, imageEditor);
        
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
        // SetUp: Create documents as specified
        Document doc1 = new Document("Portfolio.jpg", LocalDate.of(2023, 9, 30), "Leo", 614400, imageEditor);
        Document doc2 = new Document("Banner.png", LocalDate.of(2023, 10, 1), "Mona", 307200, imageEditor);
        Document doc3 = new Document("Presentation.ppt", LocalDate.of(2023, 10, 2), "Nina", 153600, textEditor);
        
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