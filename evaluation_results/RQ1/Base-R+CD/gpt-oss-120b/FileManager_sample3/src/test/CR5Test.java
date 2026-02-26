import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
    public void testCase1_retrieveAuthorsForTextEditorDocuments() throws ParseException {
        // Setup documents
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Document doc1 = new Document("Report.doc", sdf.parse("2023-10-01"), "Alice", 102400, textEditor);
        Document doc2 = new Document("Essay.doc", sdf.parse("2023-10-02"), "Bob", 153600, textEditor);
        Document doc3 = new Document("Image.png", sdf.parse("2023-10-03"), "Charlie", 204800, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute method
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify result
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
    }
    
    @Test
    public void testCase2_retrieveAuthorsForImageEditorDocuments() throws ParseException {
        // Setup documents
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Document doc1 = new Document("Photo.jpg", sdf.parse("2023-09-15"), "Dave", 256000, imageEditor);
        Document doc2 = new Document("Diagram.svg", sdf.parse("2023-09-20"), "Eve", 307200, imageEditor);
        Document doc3 = new Document("Video.mp4", sdf.parse("2023-09-25"), "Frank", 524288000, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute method
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify result
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
    }
    
    @Test
    public void testCase3_noAuthorsForSpecificEditor() throws ParseException {
        // Setup documents
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Document doc1 = new Document("Document.txt", sdf.parse("2023-10-05"), "Grace", 51200, textEditor);
        Document doc2 = new Document("Drawing.png", sdf.parse("2023-10-06"), "Heidi", 81920, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute method
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Verify result
        assertEquals(0, authors.size());
    }
    
    @Test
    public void testCase4_retrievingAuthorsFromMixedEditorDocuments() throws ParseException {
        // Setup documents
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Document doc1 = new Document("Notes.txt", sdf.parse("2023-10-07"), "Ivy", 30720, textEditor);
        Document doc2 = new Document("Video.mp4", sdf.parse("2023-10-08"), "Jack", 419430400, videoEditor);
        Document doc3 = new Document("Image.JPG", sdf.parse("2023-10-09"), "Kathy", 122880, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute method
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify result
        assertEquals(1, authors.size());
        assertTrue(authors.contains("Ivy"));
    }
    
    @Test
    public void testCase5_multipleAuthorsForSingleDocumentType() throws ParseException {
        // Setup documents
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Document doc1 = new Document("Portfolio.jpg", sdf.parse("2023-09-30"), "Leo", 614400, imageEditor);
        Document doc2 = new Document("Banner.png", sdf.parse("2023-10-01"), "Mona", 307200, imageEditor);
        Document doc3 = new Document("Presentation.ppt", sdf.parse("2023-10-02"), "Nina", 153600, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute method
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify result
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
    }
}