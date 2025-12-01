import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR5Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws ParseException {
        // SetUp: Create a file system with the specified documents
        Editor textEditor = new TextEditor();
        Editor imageEditor = new ImageEditor();
        
        Document doc1 = new Document("Report.doc", dateFormat.parse("2023-10-01 00:00:00"), "Alice", 100, textEditor);
        Document doc2 = new Document("Essay.doc", dateFormat.parse("2023-10-02 00:00:00"), "Bob", 150, textEditor);
        Document doc3 = new Document("Image.png", dateFormat.parse("2023-10-03 00:00:00"), "Charlie", 200, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Expected Output: Authors = ["Alice", "Bob"]
        List<String> expectedAuthors = Arrays.asList("Alice", "Bob");
        assertEquals(expectedAuthors, authors);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws ParseException {
        // SetUp: Create a file system with the specified documents
        Editor imageEditor = new ImageEditor();
        Editor videoEditor = new VideoEditor();
        
        Document doc1 = new Document("Photo.jpg", dateFormat.parse("2023-09-15 00:00:00"), "Dave", 250, imageEditor);
        Document doc2 = new Document("Diagram.svg", dateFormat.parse("2023-09-20 00:00:00"), "Eve", 300, imageEditor);
        Document doc3 = new Document("Video.mp4", dateFormat.parse("2023-09-25 00:00:00"), "Frank", 500000, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Expected Output: Authors = ["Dave", "Eve"]
        List<String> expectedAuthors = Arrays.asList("Dave", "Eve");
        assertEquals(expectedAuthors, authors);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws ParseException {
        // SetUp: Create a file system with the specified documents
        Editor textEditor = new TextEditor();
        Editor imageEditor = new ImageEditor();
        Editor videoEditor = new VideoEditor();
        
        Document doc1 = new Document("Document.txt", dateFormat.parse("2023-10-05 00:00:00"), "Grace", 50, textEditor);
        Document doc2 = new Document("Drawing.png", dateFormat.parse("2023-10-06 00:00:00"), "Heidi", 80, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Expected Output: Authors = []
        List<String> expectedAuthors = Collections.emptyList();
        assertEquals(expectedAuthors, authors);
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws ParseException {
        // SetUp: Create a file system with the specified documents
        Editor textEditor = new TextEditor();
        Editor videoEditor = new VideoEditor();
        Editor imageEditor = new ImageEditor();
        
        Document doc1 = new Document("Notes.txt", dateFormat.parse("2023-10-07 00:00:00"), "Ivy", 30, textEditor);
        Document doc2 = new Document("Video.mp4", dateFormat.parse("2023-10-08 00:00:00"), "Jack", 400000, videoEditor);
        Document doc3 = new Document("Image.JPG", dateFormat.parse("2023-10-09 00:00:00"), "Kathy", 120, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Expected Output: Authors = ["Ivy"]
        List<String> expectedAuthors = Arrays.asList("Ivy");
        assertEquals(expectedAuthors, authors);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws ParseException {
        // SetUp: Create a file system with the specified documents
        Editor imageEditor = new ImageEditor();
        Editor textEditor = new TextEditor();
        
        Document doc1 = new Document("Portfolio.jpg", dateFormat.parse("2023-09-30 00:00:00"), "Leo", 600, imageEditor);
        Document doc2 = new Document("Banner.png", dateFormat.parse("2023-10-01 00:00:00"), "Mona", 300, imageEditor);
        Document doc3 = new Document("Presentation.ppt", dateFormat.parse("2023-10-02 00:00:00"), "Nina", 150, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Expected Output: Authors = ["Leo", "Mona"]
        List<String> expectedAuthors = Arrays.asList("Leo", "Mona");
        assertEquals(expectedAuthors, authors);
    }
}