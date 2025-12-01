import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR5Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Get the default editors from the file system
        List<Editor> editors = fileSystem.getEditors();
        for (Editor editor : editors) {
            switch (editor.getName()) {
                case "Text Editor":
                    textEditor = (TextEditor) editor;
                    break;
                case "Image Editor":
                    imageEditor = (ImageEditor) editor;
                    break;
                case "Video Editor":
                    videoEditor = (VideoEditor) editor;
                    break;
            }
        }
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws Exception {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Report.doc", dateFormat.parse("2023-10-01 00:00:00"), "Alice", 100, textEditor);
        Document doc2 = new Document("Essay.doc", dateFormat.parse("2023-10-02 00:00:00"), "Bob", 150, textEditor);
        Document doc3 = new Document("Image.png", dateFormat.parse("2023-10-03 00:00:00"), "Charlie", 200, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Expected Output: Authors = ["Alice", "Bob"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
        assertEquals("Alice", authors.get(0));
        assertEquals("Bob", authors.get(1));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Photo.jpg", dateFormat.parse("2023-09-15 00:00:00"), "Dave", 250, imageEditor);
        Document doc2 = new Document("Diagram.svg", dateFormat.parse("2023-09-20 00:00:00"), "Eve", 300, imageEditor);
        Document doc3 = new Document("Video.mp4", dateFormat.parse("2023-09-25 00:00:00"), "Frank", 500000, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Expected Output: Authors = ["Dave", "Eve"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
        assertEquals("Dave", authors.get(0));
        assertEquals("Eve", authors.get(1));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Document.txt", dateFormat.parse("2023-10-05 00:00:00"), "Grace", 50, textEditor);
        Document doc2 = new Document("Drawing.png", dateFormat.parse("2023-10-06 00:00:00"), "Heidi", 80, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Expected Output: Authors = []
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Notes.txt", dateFormat.parse("2023-10-07 00:00:00"), "Ivy", 30, textEditor);
        Document doc2 = new Document("Video.mp4", dateFormat.parse("2023-10-08 00:00:00"), "Jack", 400000, videoEditor);
        Document doc3 = new Document("Image.JPG", dateFormat.parse("2023-10-09 00:00:00"), "Kathy", 120, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Expected Output: Authors = ["Ivy"]
        assertEquals(1, authors.size());
        assertEquals("Ivy", authors.get(0));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // SetUp: Create documents as specified
        Document doc1 = new Document("Portfolio.jpg", dateFormat.parse("2023-09-30 00:00:00"), "Leo", 600, imageEditor);
        Document doc2 = new Document("Banner.png", dateFormat.parse("2023-10-01 00:00:00"), "Mona", 300, imageEditor);
        Document doc3 = new Document("Presentation.ppt", dateFormat.parse("2023-10-02 00:00:00"), "Nina", 150, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Expected Output: Authors = ["Leo", "Mona"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
        assertEquals("Leo", authors.get(0));
        assertEquals("Mona", authors.get(1));
    }
}