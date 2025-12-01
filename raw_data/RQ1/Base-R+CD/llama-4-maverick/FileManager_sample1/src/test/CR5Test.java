import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        videoEditor = new VideoEditor();
        videoEditor.setName("Video Editor");
        
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
    }
    
    @Test
    public void testCase1_retrieveAuthorsForTextEditorDocuments() throws ParseException {
        // Create documents
        Document doc1 = createDocument("Report.doc", "2023-10-01", "Alice", 100, textEditor);
        Document doc2 = createDocument("Essay.doc", "2023-10-02", "Bob", 150, textEditor);
        Document doc3 = createDocument("Image.png", "2023-10-03", "Charlie", 200, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute method
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify results
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
    }
    
    @Test
    public void testCase2_retrieveAuthorsForImageEditorDocuments() throws ParseException {
        // Create documents
        Document doc1 = createDocument("Photo.jpg", "2023-09-15", "Dave", 250, imageEditor);
        Document doc2 = createDocument("Diagram.svg", "2023-09-20", "Eve", 300, imageEditor);
        Document doc3 = createDocument("Video.mp4", "2023-09-25", "Frank", 500000, videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute method
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify results
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
    }
    
    @Test
    public void testCase3_noAuthorsForSpecificEditor() throws ParseException {
        // Create documents
        Document doc1 = createDocument("Document.txt", "2023-10-05", "Grace", 50, textEditor);
        Document doc2 = createDocument("Drawing.png", "2023-10-06", "Heidi", 80, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute method
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Verify results
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_retrievingAuthorsFromMixedEditorDocuments() throws ParseException {
        // Create documents
        Document doc1 = createDocument("Notes.txt", "2023-10-07", "Ivy", 30, textEditor);
        Document doc2 = createDocument("Video.mp4", "2023-10-08", "Jack", 400000, videoEditor);
        Document doc3 = createDocument("Image.JPG", "2023-10-09", "Kathy", 120, imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute method
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify results
        assertEquals(1, authors.size());
        assertTrue(authors.contains("Ivy"));
    }
    
    @Test
    public void testCase5_multipleAuthorsForSingleDocumentType() throws ParseException {
        // Create documents
        Document doc1 = createDocument("Portfolio.jpg", "2023-09-30", "Leo", 600, imageEditor);
        Document doc2 = createDocument("Banner.png", "2023-10-01", "Mona", 300, imageEditor);
        Document doc3 = createDocument("Presentation.ppt", "2023-10-02", "Nina", 150, textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute method
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify results
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
    }
    
    // Helper method to create documents with formatted dates
    private Document createDocument(String name, String dateStr, String author, int size, Editor editor) throws ParseException {
        Document doc = new Document();
        doc.setName(name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);
        doc.setCreateDate(date);
        doc.setAuthor(author);
        doc.setSize(size);
        doc.setEditor(editor);
        return doc;
    }
}