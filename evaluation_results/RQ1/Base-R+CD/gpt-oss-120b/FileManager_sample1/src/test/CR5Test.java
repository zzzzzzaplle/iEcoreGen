import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class CR5Test {
    
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
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
        Date date1 = dateFormat.parse("2023-10-01");
        Date date2 = dateFormat.parse("2023-10-02");
        Date date3 = dateFormat.parse("2023-10-03");
        
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreateDate(date1);
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(date2);
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreateDate(date3);
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
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
        Date date1 = dateFormat.parse("2023-09-15");
        Date date2 = dateFormat.parse("2023-09-20");
        Date date3 = dateFormat.parse("2023-09-25");
        
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(date1);
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(date2);
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(date3);
        doc3.setAuthor("Frank");
        doc3.setSize(500);
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
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
        Date date1 = dateFormat.parse("2023-10-05");
        Date date2 = dateFormat.parse("2023-10-06");
        
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreateDate(date1);
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(date2);
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        
        // Add documents to file system
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
        Date date1 = dateFormat.parse("2023-10-07");
        Date date2 = dateFormat.parse("2023-10-08");
        Date date3 = dateFormat.parse("2023-10-09");
        
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(date1);
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(date2);
        doc2.setAuthor("Jack");
        doc2.setSize(400);
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(date3);
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
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
        Date date1 = dateFormat.parse("2023-09-30");
        Date date2 = dateFormat.parse("2023-10-01");
        Date date3 = dateFormat.parse("2023-10-02");
        
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(date1);
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreateDate(date2);
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(date3);
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        
        // Add documents to file system
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