import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR5Test {
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws Exception {
        // Set up editors
        Editor textEditor = new Editor("Text Editor");
        Editor imageEditor = new Editor("Image Editor");
        
        // Set up documents as specified in test case 1
        Document doc1 = new Document("Report.doc", dateFormat.parse("2023-10-01"), "Alice", 100, textEditor);
        Document doc2 = new Document("Essay.doc", dateFormat.parse("2023-10-02"), "Bob", 150, textEditor);
        Document doc3 = new Document("Image.png", dateFormat.parse("2023-10-03"), "Charlie", 200, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Alice", "Bob");
        assertEquals("Should retrieve authors for Text Editor documents", expectedAuthors, authors);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // Set up editors
        Editor imageEditor = new Editor("Image Editor");
        Editor videoEditor = new Editor("Video Editor");
        
        // Set up documents as specified in test case 2
        Document doc1 = new Document("Photo.jpg", dateFormat.parse("2023-09-15"), "Dave", 250, imageEditor);
        Document doc2 = new Document("Diagram.svg", dateFormat.parse("2023-09-20"), "Eve", 300, imageEditor);
        Document doc3 = new Document("Video.mp4", dateFormat.parse("2023-09-25"), "Frank", 500000, videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Dave", "Eve");
        assertEquals("Should retrieve authors for Image Editor documents", expectedAuthors, authors);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // Set up editors
        Editor textEditor = new Editor("Text Editor");
        Editor imageEditor = new Editor("Image Editor");
        
        // Set up documents as specified in test case 3
        Document doc1 = new Document("Document.txt", dateFormat.parse("2023-10-05"), "Grace", 50, textEditor);
        Document doc2 = new Document("Drawing.png", dateFormat.parse("2023-10-06"), "Heidi", 80, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor("Video Editor");
        
        // Verify expected output (empty list)
        assertTrue("Should return empty list when no documents use the specified editor", authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // Set up editors
        Editor textEditor = new Editor("Text Editor");
        Editor videoEditor = new Editor("Video Editor");
        Editor imageEditor = new Editor("Image Editor");
        
        // Set up documents as specified in test case 4
        Document doc1 = new Document("Notes.txt", dateFormat.parse("2023-10-07"), "Ivy", 30, textEditor);
        Document doc2 = new Document("Video.mp4", dateFormat.parse("2023-10-08"), "Jack", 400000, videoEditor);
        Document doc3 = new Document("Image.JPG", dateFormat.parse("2023-10-09"), "Kathy", 120, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Ivy");
        assertEquals("Should retrieve only authors for Text Editor from mixed documents", expectedAuthors, authors);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // Set up editors
        Editor imageEditor = new Editor("Image Editor");
        Editor textEditor = new Editor("Text Editor");
        
        // Set up documents as specified in test case 5
        Document doc1 = new Document("Portfolio.jpg", dateFormat.parse("2023-09-30"), "Leo", 600, imageEditor);
        Document doc2 = new Document("Banner.png", dateFormat.parse("2023-10-01"), "Mona", 300, imageEditor);
        Document doc3 = new Document("Presentation.ppt", dateFormat.parse("2023-10-02"), "Nina", 150, textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Leo", "Mona");
        assertEquals("Should retrieve multiple authors for Image Editor documents", expectedAuthors, authors);
    }
}