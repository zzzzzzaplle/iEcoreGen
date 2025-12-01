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
        // Set up documents as specified
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        TextEditor textEditor1 = new TextEditor();
        textEditor1.setName("Text Editor");
        doc1.setEditor(textEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        TextEditor textEditor2 = new TextEditor();
        textEditor2.setName("Text Editor");
        doc2.setEditor(textEditor2);
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreateDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Create test editor
        TextEditor testEditor = new TextEditor();
        testEditor.setName("Text Editor");
        
        // Get authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(testEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Alice", "Bob");
        assertEquals("Should retrieve authors Alice and Bob for Text Editor", expected, authors);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // Set up documents as specified
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        ImageEditor imageEditor1 = new ImageEditor();
        imageEditor1.setName("Image Editor");
        doc1.setEditor(imageEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        ImageEditor imageEditor2 = new ImageEditor();
        imageEditor2.setName("Image Editor");
        doc2.setEditor(imageEditor2);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB (assuming size is in KB)
        VideoEditor videoEditor = new VideoEditor();
        videoEditor.setName("Video Editor");
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Create test editor
        ImageEditor testEditor = new ImageEditor();
        testEditor.setName("Image Editor");
        
        // Get authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(testEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Dave", "Eve");
        assertEquals("Should retrieve authors Dave and Eve for Image Editor", expected, authors);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // Set up documents as specified
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        TextEditor textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        doc2.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Create test editor
        VideoEditor testEditor = new VideoEditor();
        testEditor.setName("Video Editor");
        
        // Get authors for Video Editor
        List<String> authors = fileSystem.getAuthorsByEditor(testEditor);
        
        // Verify expected output
        assertTrue("Should return empty list when no documents match the editor", authors.isEmpty());
        assertEquals("Should have 0 authors for Video Editor", 0, authors.size());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // Set up documents as specified
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        TextEditor textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB (assuming size is in KB)
        VideoEditor videoEditor = new VideoEditor();
        videoEditor.setName("Video Editor");
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Create test editor
        TextEditor testEditor = new TextEditor();
        testEditor.setName("Text Editor");
        
        // Get authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(testEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Ivy");
        assertEquals("Should retrieve only author Ivy for Text Editor", expected, authors);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // Set up documents as specified
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        ImageEditor imageEditor1 = new ImageEditor();
        imageEditor1.setName("Image Editor");
        doc1.setEditor(imageEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        ImageEditor imageEditor2 = new ImageEditor();
        imageEditor2.setName("Image Editor");
        doc2.setEditor(imageEditor2);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        TextEditor textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Create test editor
        ImageEditor testEditor = new ImageEditor();
        testEditor.setName("Image Editor");
        
        // Get authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(testEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Leo", "Mona");
        assertEquals("Should retrieve authors Leo and Mona for Image Editor", expected, authors);
    }
}