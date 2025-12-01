import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR5Test {
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws Exception {
        // SetUp: Create documents as specified
        Date date1 = dateFormat.parse("2023-10-01 00:00:00");
        Date date2 = dateFormat.parse("2023-10-02 00:00:00");
        Date date3 = dateFormat.parse("2023-10-03 00:00:00");
        
        Document doc1 = new Document("Report.doc", date1, "Alice", 100L, new Editor("Text Editor"));
        Document doc2 = new Document("Essay.doc", date2, "Bob", 150L, new Editor("Text Editor"));
        Document doc3 = new Document("Image.png", date3, "Charlie", 200L, new Editor("Image Editor"));
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        List<String> result = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Expected Output: Authors = ["Alice", "Bob"]
        List<String> expected = Arrays.asList("Alice", "Bob");
        assertEquals("Should return authors for Text Editor documents", expected, result);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // SetUp: Create documents as specified
        Date date1 = dateFormat.parse("2023-09-15 00:00:00");
        Date date2 = dateFormat.parse("2023-09-20 00:00:00");
        Date date3 = dateFormat.parse("2023-09-25 00:00:00");
        
        Document doc1 = new Document("Photo.jpg", date1, "Dave", 250L, new Editor("Image Editor"));
        Document doc2 = new Document("Diagram.svg", date2, "Eve", 300L, new Editor("Image Editor"));
        Document doc3 = new Document("Video.mp4", date3, "Frank", 500000L, new Editor("Video Editor"));
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        List<String> result = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Expected Output: Authors = ["Dave", "Eve"]
        List<String> expected = Arrays.asList("Dave", "Eve");
        assertEquals("Should return authors for Image Editor documents", expected, result);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // SetUp: Create documents as specified
        Date date1 = dateFormat.parse("2023-10-05 00:00:00");
        Date date2 = dateFormat.parse("2023-10-06 00:00:00");
        
        Document doc1 = new Document("Document.txt", date1, "Grace", 50L, new Editor("Text Editor"));
        Document doc2 = new Document("Drawing.png", date2, "Heidi", 80L, new Editor("Image Editor"));
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        List<String> result = fileSystem.getAuthorsByEditor("Video Editor");
        
        // Expected Output: Authors = []
        assertTrue("Should return empty list when no documents use the specified editor", result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // SetUp: Create documents as specified
        Date date1 = dateFormat.parse("2023-10-07 00:00:00");
        Date date2 = dateFormat.parse("2023-10-08 00:00:00");
        Date date3 = dateFormat.parse("2023-10-09 00:00:00");
        
        Document doc1 = new Document("Notes.txt", date1, "Ivy", 30L, new Editor("Text Editor"));
        Document doc2 = new Document("Video.mp4", date2, "Jack", 400000L, new Editor("Video Editor"));
        Document doc3 = new Document("Image.JPG", date3, "Kathy", 120L, new Editor("Image Editor"));
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        List<String> result = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Expected Output: Authors = ["Ivy"]
        List<String> expected = Arrays.asList("Ivy");
        assertEquals("Should return only authors for Text Editor from mixed document types", expected, result);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // SetUp: Create documents as specified
        Date date1 = dateFormat.parse("2023-09-30 00:00:00");
        Date date2 = dateFormat.parse("2023-10-01 00:00:00");
        Date date3 = dateFormat.parse("2023-10-02 00:00:00");
        
        Document doc1 = new Document("Portfolio.jpg", date1, "Leo", 600L, new Editor("Image Editor"));
        Document doc2 = new Document("Banner.png", date2, "Mona", 300L, new Editor("Image Editor"));
        Document doc3 = new Document("Presentation.ppt", date3, "Nina", 150L, new Editor("Text Editor"));
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        List<String> result = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Expected Output: Authors = ["Leo", "Mona"]
        List<String> expected = Arrays.asList("Leo", "Mona");
        assertEquals("Should return multiple authors for Image Editor documents", expected, result);
    }
}