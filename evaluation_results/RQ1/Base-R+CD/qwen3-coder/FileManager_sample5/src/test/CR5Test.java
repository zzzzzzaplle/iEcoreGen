// IMPORTANT: Do not include package declaration
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
        // Create documents as specified in test case
        Document doc1 = createDocument("Report.doc", "2023-10-01 00:00:00", "Alice", 100, textEditor);
        Document doc2 = createDocument("Essay.doc", "2023-10-02 00:00:00", "Bob", 150, textEditor);
        Document doc3 = createDocument("Image.png", "2023-10-03 00:00:00", "Charlie", 200, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
    }
    
    @Test
    public void testCase2_retrieveAuthorsForImageEditorDocuments() throws ParseException {
        // Create documents as specified in test case
        Document doc1 = createDocument("Photo.jpg", "2023-09-15 00:00:00", "Dave", 250, imageEditor);
        Document doc2 = createDocument("Diagram.svg", "2023-09-20 00:00:00", "Eve", 300, imageEditor);
        Document doc3 = createDocument("Video.mp4", "2023-09-25 00:00:00", "Frank", 500000, videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
    }
    
    @Test
    public void testCase3_noAuthorsForSpecificEditor() throws ParseException {
        // Create documents as specified in test case
        Document doc1 = createDocument("Document.txt", "2023-10-05 00:00:00", "Grace", 50, textEditor);
        Document doc2 = createDocument("Drawing.png", "2023-10-06 00:00:00", "Heidi", 80, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Retrieve authors for Video Editor (should be empty)
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Verify expected output
        assertEquals(0, authors.size());
    }
    
    @Test
    public void testCase4_retrievingAuthorsFromMixedEditorDocuments() throws ParseException {
        // Create documents as specified in test case
        Document doc1 = createDocument("Notes.txt", "2023-10-07 00:00:00", "Ivy", 30, textEditor);
        Document doc2 = createDocument("Video.mp4", "2023-10-08 00:00:00", "Jack", 400000, videoEditor);
        Document doc3 = createDocument("Image.JPG", "2023-10-09 00:00:00", "Kathy", 120, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        assertEquals(1, authors.size());
        assertTrue(authors.contains("Ivy"));
    }
    
    @Test
    public void testCase5_multipleAuthorsForSingleDocumentType() throws ParseException {
        // Create documents as specified in test case
        Document doc1 = createDocument("Portfolio.jpg", "2023-09-30 00:00:00", "Leo", 600, imageEditor);
        Document doc2 = createDocument("Banner.png", "2023-10-01 00:00:00", "Mona", 300, imageEditor);
        Document doc3 = createDocument("Presentation.ppt", "2023-10-02 00:00:00", "Nina", 150, textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Retrieve authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
    }
    
    // Helper method to create documents with specified attributes
    private Document createDocument(String name, String createDateStr, String author, int size, Editor editor) throws ParseException {
        Document doc = new Document();
        doc.setName(name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        doc.setCreateDate(sdf.parse(createDateStr));
        doc.setAuthor(author);
        doc.setSize(size);
        doc.setEditor(editor);
        return doc;
    }
}