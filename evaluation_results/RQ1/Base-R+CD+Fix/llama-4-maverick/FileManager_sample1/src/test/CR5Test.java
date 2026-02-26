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
        // Set up test data
        TextEditor textEditor = new TextEditor();
        ImageEditor imageEditor = new ImageEditor();
        
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreateDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        List<String> actualAuthors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify the expected output
        List<String> expectedAuthors = Arrays.asList("Alice", "Bob");
        assertEquals("Should retrieve authors for Text Editor documents", expectedAuthors, actualAuthors);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // Set up test data
        ImageEditor imageEditor = new ImageEditor();
        VideoEditor videoEditor = new VideoEditor();
        
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        List<String> actualAuthors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify the expected output
        List<String> expectedAuthors = Arrays.asList("Dave", "Eve");
        assertEquals("Should retrieve authors for Image Editor documents", expectedAuthors, actualAuthors);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // Set up test data
        TextEditor textEditor = new TextEditor();
        ImageEditor imageEditor = new ImageEditor();
        VideoEditor videoEditor = new VideoEditor();
        
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the method under test
        List<String> actualAuthors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Verify the expected output
        List<String> expectedAuthors = Arrays.asList();
        assertTrue("Should return empty list when no documents match the specified editor", actualAuthors.isEmpty());
        assertEquals("Should return empty list when no documents match the specified editor", expectedAuthors, actualAuthors);
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // Set up test data
        TextEditor textEditor = new TextEditor();
        VideoEditor videoEditor = new VideoEditor();
        ImageEditor imageEditor = new ImageEditor();
        
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        List<String> actualAuthors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify the expected output
        List<String> expectedAuthors = Arrays.asList("Ivy");
        assertEquals("Should retrieve only authors for Text Editor from mixed editor documents", expectedAuthors, actualAuthors);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // Set up test data
        ImageEditor imageEditor = new ImageEditor();
        TextEditor textEditor = new TextEditor();
        
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        List<String> actualAuthors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify the expected output
        List<String> expectedAuthors = Arrays.asList("Leo", "Mona");
        assertEquals("Should retrieve multiple authors for Image Editor documents", expectedAuthors, actualAuthors);
    }
}