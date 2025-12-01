import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CR5Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        // Initialize file system and editors before each test
        fileSystem = new FileSystem();
        textEditor = new Editor("Text Editor");
        imageEditor = new Editor("Image Editor");
        videoEditor = new Editor("Video Editor");
        
        // Add editors to file system
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() {
        // Set up documents as specified in test case 1
        Document doc1 = new Document("Report.doc", LocalDate.of(2023, 10, 1), "Alice", 100, textEditor);
        Document doc2 = new Document("Essay.doc", LocalDate.of(2023, 10, 2), "Bob", 150, textEditor);
        Document doc3 = new Document("Image.png", LocalDate.of(2023, 10, 3), "Charlie", 200, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Text Editor
        List<String> authors = fileSystem.getAuthorsForEditor(textEditor);
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Alice", "Bob");
        assertEquals("Should retrieve authors Alice and Bob for Text Editor", expectedAuthors, authors);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() {
        // Set up documents as specified in test case 2
        Document doc1 = new Document("Photo.jpg", LocalDate.of(2023, 9, 15), "Dave", 250, imageEditor);
        Document doc2 = new Document("Diagram.svg", LocalDate.of(2023, 9, 20), "Eve", 300, imageEditor);
        Document doc3 = new Document("Video.mp4", LocalDate.of(2023, 9, 25), "Frank", 500000, videoEditor); // 500MB = 500000KB
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Image Editor
        List<String> authors = fileSystem.getAuthorsForEditor(imageEditor);
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Dave", "Eve");
        assertEquals("Should retrieve authors Dave and Eve for Image Editor", expectedAuthors, authors);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() {
        // Set up documents as specified in test case 3
        Document doc1 = new Document("Document.txt", LocalDate.of(2023, 10, 5), "Grace", 50, textEditor);
        Document doc2 = new Document("Drawing.png", LocalDate.of(2023, 10, 6), "Heidi", 80, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Retrieve authors for Video Editor (which has no documents)
        List<String> authors = fileSystem.getAuthorsForEditor(videoEditor);
        
        // Verify expected output (empty list)
        assertTrue("Should return empty list when no documents exist for the specified editor", authors.isEmpty());
        assertEquals("Should return empty list when no documents exist for the specified editor", 0, authors.size());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // Set up documents as specified in test case 4
        Document doc1 = new Document("Notes.txt", LocalDate.of(2023, 10, 7), "Ivy", 30, textEditor);
        Document doc2 = new Document("Video.mp4", LocalDate.of(2023, 10, 8), "Jack", 400000, videoEditor); // 400MB = 400000KB
        Document doc3 = new Document("Image.JPG", LocalDate.of(2023, 10, 9), "Kathy", 120, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Text Editor
        List<String> authors = fileSystem.getAuthorsForEditor(textEditor);
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Ivy");
        assertEquals("Should retrieve only author Ivy for Text Editor", expectedAuthors, authors);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() {
        // Set up documents as specified in test case 5
        Document doc1 = new Document("Portfolio.jpg", LocalDate.of(2023, 9, 30), "Leo", 600, imageEditor);
        Document doc2 = new Document("Banner.png", LocalDate.of(2023, 10, 1), "Mona", 300, imageEditor);
        Document doc3 = new Document("Presentation.ppt", LocalDate.of(2023, 10, 2), "Nina", 150, textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Image Editor
        List<String> authors = fileSystem.getAuthorsForEditor(imageEditor);
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Leo", "Mona");
        assertEquals("Should retrieve authors Leo and Mona for Image Editor", expectedAuthors, authors);
    }
}