import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_retrieveAuthorsForTextEditorDocuments() throws ParseException {
        // Set up documents for Text Editor test
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreationDate(dateFormat.parse("2023-10-01"));
        doc1.setAuthor("Alice");
        doc1.setSize(100 * 1024); // 100KB in bytes
        doc1.setEditor(fileSystem.getDocuments().get(0).getEditor()); // Get Text Editor
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreationDate(dateFormat.parse("2023-10-02"));
        doc2.setAuthor("Bob");
        doc2.setSize(150 * 1024); // 150KB in bytes
        doc2.setEditor(fileSystem.getDocuments().get(0).getEditor()); // Get Text Editor
        
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
        
        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Alice", "Bob");
        assertEquals("Should retrieve authors for Text Editor documents", expectedAuthors, authors);
    }
    
    @Test
    public void testCase2_retrieveAuthorsForImageEditorDocuments() throws ParseException {
        // Set up documents for Image Editor test
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-15"));
        doc1.setAuthor("Dave");
        doc1.setSize(250 * 1024); // 250KB in bytes
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreationDate(dateFormat.parse("2023-09-20"));
        doc2.setAuthor("Eve");
        doc2.setSize(300 * 1024); // 300KB in bytes
        doc2.setEditor(imageEditor);
        
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
        
        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Dave", "Eve");
        assertEquals("Should retrieve authors for Image Editor documents", expectedAuthors, authors);
    }
    
    @Test
    public void testCase3_noAuthorsForSpecificEditor() throws ParseException {
        // Set up documents with no Video Editor documents
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-05"));
        doc1.setAuthor("Grace");
        doc1.setSize(50 * 1024); // 50KB in bytes
        doc1.setEditor(fileSystem.getDocuments().get(0).getEditor()); // Get Text Editor
        
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
        
        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor("Video Editor");
        
        // Verify expected output - empty list
        assertTrue("Should return empty list when no documents use specified editor", authors.isEmpty());
        assertEquals("Should return empty list when no documents use specified editor", 0, authors.size());
    }
    
    @Test
    public void testCase4_retrievingAuthorsFromMixedEditorDocuments() throws ParseException {
        // Set up mixed editor documents
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-07"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30 * 1024); // 30KB in bytes
        doc1.setEditor(fileSystem.getDocuments().get(0).getEditor()); // Get Text Editor
        
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
        
        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Verify expected output
        List<String> expectedAuthors = Arrays.asList("Ivy");
        assertEquals("Should retrieve only author for Text Editor from mixed documents", expectedAuthors, authors);
    }
    
    @Test
    public void testCase5_multipleAuthorsForSingleDocumentType() throws ParseException {
        // Set up multiple authors for Image Editor
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-30"));
        doc1.setAuthor("Leo");
        doc1.setSize(600 * 1024); // 600KB in bytes
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-01"));
        doc2.setAuthor("Mona");
        doc2.setSize(300 * 1024); // 300KB in bytes
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreationDate(dateFormat.parse("2023-10-02"));
        doc3.setAuthor("Nina");
        doc3.setSize(150 * 1024); // 150KB in bytes
        doc3.setEditor(fileSystem.getDocuments().get(0).getEditor()); // Get Text Editor
        
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