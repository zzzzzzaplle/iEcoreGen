import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CR5Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        // Initialize file system and editors before each test
        fileSystem = new FileSystem();
        textEditor = new Editor();
        textEditor.setName("Text Editor");
        imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        
        fileSystem.getEditors().addAll(Arrays.asList(textEditor, imageEditor, videoEditor));
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() {
        // SetUp: Create documents as specified
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreationDate(LocalDate.of(2023, 10, 1));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreationDate(LocalDate.of(2023, 10, 2));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreationDate(LocalDate.of(2023, 10, 3));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Test: Retrieve authors for Text Editor
        List<String> result = fileSystem.getAuthorsForEditor(textEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Alice", "Bob");
        assertEquals("Should retrieve authors Alice and Bob for Text Editor", expected, result);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() {
        // SetUp: Create documents as specified
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreationDate(LocalDate.of(2023, 9, 15));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreationDate(LocalDate.of(2023, 9, 20));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreationDate(LocalDate.of(2023, 9, 25));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Test: Retrieve authors for Image Editor
        List<String> result = fileSystem.getAuthorsForEditor(imageEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Dave", "Eve");
        assertEquals("Should retrieve authors Dave and Eve for Image Editor", expected, result);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() {
        // SetUp: Create documents as specified (no documents for Video Editor)
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreationDate(LocalDate.of(2023, 10, 5));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreationDate(LocalDate.of(2023, 10, 6));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Test: Retrieve authors for Video Editor (which has no documents)
        List<String> result = fileSystem.getAuthorsForEditor(videoEditor);
        
        // Verify expected output (empty list)
        assertTrue("Should return empty list when no documents for specified editor", result.isEmpty());
        assertEquals("Size should be 0 for editor with no documents", 0, result.size());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // SetUp: Create documents as specified
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreationDate(LocalDate.of(2023, 10, 7));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreationDate(LocalDate.of(2023, 10, 8));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreationDate(LocalDate.of(2023, 10, 9));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Test: Retrieve authors for Text Editor
        List<String> result = fileSystem.getAuthorsForEditor(textEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Ivy");
        assertEquals("Should retrieve only author Ivy for Text Editor", expected, result);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() {
        // SetUp: Create documents as specified
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreationDate(LocalDate.of(2023, 9, 30));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreationDate(LocalDate.of(2023, 10, 1));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreationDate(LocalDate.of(2023, 10, 2));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Test: Retrieve authors for Image Editor
        List<String> result = fileSystem.getAuthorsForEditor(imageEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Leo", "Mona");
        assertEquals("Should retrieve authors Leo and Mona for Image Editor", expected, result);
    }
}