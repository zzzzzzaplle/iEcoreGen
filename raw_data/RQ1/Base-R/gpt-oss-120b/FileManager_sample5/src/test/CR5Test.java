import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.*;

public class CR5Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() {
        // Set up: Create file system with specified documents
        Document doc1 = new Document("Report.doc", LocalDate.of(2023, 10, 1), "Alice", 100 * 1024, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Essay.doc", LocalDate.of(2023, 10, 2), "Bob", 150 * 1024, Editor.TEXT_EDITOR);
        Document doc3 = new Document("Image.png", LocalDate.of(2023, 10, 3), "Charlie", 200 * 1024, Editor.IMAGE_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        Set<String> result = fileSystem.getAuthorsByEditor(Editor.TEXT_EDITOR);
        
        // Expected Output: Authors = ["Alice", "Bob"]
        Set<String> expected = new HashSet<>(Arrays.asList("Alice", "Bob"));
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() {
        // Set up: Create file system with specified documents
        Document doc1 = new Document("Photo.jpg", LocalDate.of(2023, 9, 15), "Dave", 250 * 1024, Editor.IMAGE_EDITOR);
        Document doc2 = new Document("Diagram.svg", LocalDate.of(2023, 9, 20), "Eve", 300 * 1024, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Video.mp4", LocalDate.of(2023, 9, 25), "Frank", 500 * 1024 * 1024, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        Set<String> result = fileSystem.getAuthorsByEditor(Editor.IMAGE_EDITOR);
        
        // Expected Output: Authors = ["Dave", "Eve"]
        Set<String> expected = new HashSet<>(Arrays.asList("Dave", "Eve"));
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() {
        // Set up: Create file system with specified documents
        Document doc1 = new Document("Document.txt", LocalDate.of(2023, 10, 5), "Grace", 50 * 1024, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Drawing.png", LocalDate.of(2023, 10, 6), "Heidi", 80 * 1024, Editor.IMAGE_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        Set<String> result = fileSystem.getAuthorsByEditor(Editor.VIDEO_EDITOR);
        
        // Expected Output: Authors = []
        Set<String> expected = new HashSet<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // Set up: Create file system with specified documents
        Document doc1 = new Document("Notes.txt", LocalDate.of(2023, 10, 7), "Ivy", 30 * 1024, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Video.mp4", LocalDate.of(2023, 10, 8), "Jack", 400 * 1024 * 1024, Editor.VIDEO_EDITOR);
        Document doc3 = new Document("Image.JPG", LocalDate.of(2023, 10, 9), "Kathy", 120 * 1024, Editor.IMAGE_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        Set<String> result = fileSystem.getAuthorsByEditor(Editor.TEXT_EDITOR);
        
        // Expected Output: Authors = ["Ivy"]
        Set<String> expected = new HashSet<>(Arrays.asList("Ivy"));
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() {
        // Set up: Create file system with specified documents
        Document doc1 = new Document("Portfolio.jpg", LocalDate.of(2023, 9, 30), "Leo", 600 * 1024, Editor.IMAGE_EDITOR);
        Document doc2 = new Document("Banner.png", LocalDate.of(2023, 10, 1), "Mona", 300 * 1024, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Presentation.ppt", LocalDate.of(2023, 10, 2), "Nina", 150 * 1024, Editor.TEXT_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        Set<String> result = fileSystem.getAuthorsByEditor(Editor.IMAGE_EDITOR);
        
        // Expected Output: Authors = ["Leo", "Mona"]
        Set<String> expected = new HashSet<>(Arrays.asList("Leo", "Mona"));
        assertEquals(expected, result);
    }

}