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
        fileSystem = new FileSystem();
        textEditor = new Editor("Text Editor");
        imageEditor = new Editor("Image Editor");
        videoEditor = new Editor("Video Editor");
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() {
        // SetUp: Create file system with specified documents
        Document doc1 = new Document("Report.doc", LocalDate.of(2023, 10, 1), "Alice", 100, textEditor);
        Document doc2 = new Document("Essay.doc", LocalDate.of(2023, 10, 2), "Bob", 150, textEditor);
        Document doc3 = new Document("Image.png", LocalDate.of(2023, 10, 3), "Charlie", 200, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        List<String> result = fileSystem.getAuthorsForEditor("Text Editor");
        
        // Expected Output: Authors = ["Alice", "Bob"]
        List<String> expected = Arrays.asList("Alice", "Bob");
        assertEquals("Should return authors Alice and Bob for Text Editor", expected, result);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() {
        // SetUp: Create file system with specified documents
        Document doc1 = new Document("Photo.jpg", LocalDate.of(2023, 9, 15), "Dave", 250, imageEditor);
        Document doc2 = new Document("Diagram.svg", LocalDate.of(2023, 9, 20), "Eve", 300, imageEditor);
        Document doc3 = new Document("Video.mp4", LocalDate.of(2023, 9, 25), "Frank", 500, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        List<String> result = fileSystem.getAuthorsForEditor("Image Editor");
        
        // Expected Output: Authors = ["Dave", "Eve"]
        List<String> expected = Arrays.asList("Dave", "Eve");
        assertEquals("Should return authors Dave and Eve for Image Editor", expected, result);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() {
        // SetUp: Create file system with specified documents
        Document doc1 = new Document("Document.txt", LocalDate.of(2023, 10, 5), "Grace", 50, textEditor);
        Document doc2 = new Document("Drawing.png", LocalDate.of(2023, 10, 6), "Heidi", 80, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        List<String> result = fileSystem.getAuthorsForEditor("Video Editor");
        
        // Expected Output: Authors = []
        List<String> expected = Arrays.asList();
        assertEquals("Should return empty list when no documents use Video Editor", expected, result);
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // SetUp: Create file system with specified documents
        Document doc1 = new Document("Notes.txt", LocalDate.of(2023, 10, 7), "Ivy", 30, textEditor);
        Document doc2 = new Document("Video.mp4", LocalDate.of(2023, 10, 8), "Jack", 400, videoEditor);
        Document doc3 = new Document("Image.JPG", LocalDate.of(2023, 10, 9), "Kathy", 120, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        List<String> result = fileSystem.getAuthorsForEditor("Text Editor");
        
        // Expected Output: Authors = ["Ivy"]
        List<String> expected = Arrays.asList("Ivy");
        assertEquals("Should return author Ivy for Text Editor from mixed editor documents", expected, result);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() {
        // SetUp: Create file system with specified documents
        Document doc1 = new Document("Portfolio.jpg", LocalDate.of(2023, 9, 30), "Leo", 600, imageEditor);
        Document doc2 = new Document("Banner.png", LocalDate.of(2023, 10, 1), "Mona", 300, imageEditor);
        Document doc3 = new Document("Presentation.ppt", LocalDate.of(2023, 10, 2), "Nina", 150, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        List<String> result = fileSystem.getAuthorsForEditor("Image Editor");
        
        // Expected Output: Authors = ["Leo", "Mona"]
        List<String> expected = Arrays.asList("Leo", "Mona");
        assertEquals("Should return authors Leo and Mona for Image Editor", expected, result);
    }
}