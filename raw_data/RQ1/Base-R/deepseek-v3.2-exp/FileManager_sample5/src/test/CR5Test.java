import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

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
        // Set up documents for Text Editor test case
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreationDate(dateFormat.parse("2023-10-01"));
        doc1.setAuthor("Alice");
        doc1.setSize(100 * 1024); // 100KB in bytes
        Editor textEditor1 = new Editor();
        textEditor1.setName("Text Editor");
        doc1.setEditor(textEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreationDate(dateFormat.parse("2023-10-02"));
        doc2.setAuthor("Bob");
        doc2.setSize(150 * 1024); // 150KB in bytes
        Editor textEditor2 = new Editor();
        textEditor2.setName("Text Editor");
        doc2.setEditor(textEditor2);
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreationDate(dateFormat.parse("2023-10-03"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200 * 1024); // 200KB in bytes
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Test: Get authors for documents using "Text Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
        assertFalse(authors.contains("Charlie"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // Set up documents for Image Editor test case
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-15"));
        doc1.setAuthor("Dave");
        doc1.setSize(250 * 1024); // 250KB in bytes
        Editor imageEditor1 = new Editor();
        imageEditor1.setName("Image Editor");
        doc1.setEditor(imageEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreationDate(dateFormat.parse("2023-09-20"));
        doc2.setAuthor("Eve");
        doc2.setSize(300 * 1024); // 300KB in bytes
        Editor imageEditor2 = new Editor();
        imageEditor2.setName("Image Editor");
        doc2.setEditor(imageEditor2);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreationDate(dateFormat.parse("2023-09-25"));
        doc3.setAuthor("Frank");
        doc3.setSize(500 * 1024 * 1024); // 500MB in bytes
        Editor videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Test: Get authors for documents using "Image Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
        assertFalse(authors.contains("Frank"));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // Set up documents for Video Editor test case (no matching documents)
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-05"));
        doc1.setAuthor("Grace");
        doc1.setSize(50 * 1024); // 50KB in bytes
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-06"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80 * 1024); // 80KB in bytes
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc2.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Test: Get authors for documents using "Video Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Video Editor");
        
        // Verify expected output - empty set
        assertTrue(authors.isEmpty());
        assertEquals(0, authors.size());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // Set up documents for mixed editor test case
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-07"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30 * 1024); // 30KB in bytes
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreationDate(dateFormat.parse("2023-10-08"));
        doc2.setAuthor("Jack");
        doc2.setSize(400 * 1024 * 1024); // 400MB in bytes
        Editor videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreationDate(dateFormat.parse("2023-10-09"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120 * 1024); // 120KB in bytes
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Test: Get authors for documents using "Text Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Verify expected output - only "Ivy"
        assertEquals(1, authors.size());
        assertTrue(authors.contains("Ivy"));
        assertFalse(authors.contains("Jack"));
        assertFalse(authors.contains("Kathy"));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // Set up documents for multiple authors with Image Editor
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-30"));
        doc1.setAuthor("Leo");
        doc1.setSize(600 * 1024); // 600KB in bytes
        Editor imageEditor1 = new Editor();
        imageEditor1.setName("Image Editor");
        doc1.setEditor(imageEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-01"));
        doc2.setAuthor("Mona");
        doc2.setSize(300 * 1024); // 300KB in bytes
        Editor imageEditor2 = new Editor();
        imageEditor2.setName("Image Editor");
        doc2.setEditor(imageEditor2);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreationDate(dateFormat.parse("2023-10-02"));
        doc3.setAuthor("Nina");
        doc3.setSize(150 * 1024); // 150KB in bytes
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Test: Get authors for documents using "Image Editor"
        Set<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Verify expected output - "Leo" and "Mona"
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
        assertFalse(authors.contains("Nina"));
    }
}