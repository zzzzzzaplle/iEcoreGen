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
        fileSystem = new FileSystem();
        textEditor = new Editor();
        imageEditor = new Editor();
        videoEditor = new Editor();
        
        textEditor.setName("Text Editor");
        imageEditor.setName("Image Editor");
        videoEditor.setName("Video Editor");
        
        fileSystem.setEditors(Arrays.asList(textEditor, imageEditor, videoEditor));
    }

    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() {
        // Set up documents for Text Editor
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreationDate(LocalDate.of(2023, 10, 1));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        textEditor.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreationDate(LocalDate.of(2023, 10, 2));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        textEditor.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreationDate(LocalDate.of(2023, 10, 3));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        imageEditor.addDocument(doc3);
        
        fileSystem.setDocuments(Arrays.asList(doc1, doc2, doc3));
        
        // Test retrieving authors for Text Editor
        List<String> result = fileSystem.getAuthorNamesForEditor(textEditor);
        List<String> expected = Arrays.asList("Alice", "Bob");
        
        assertEquals("Should return authors Alice and Bob for Text Editor", expected, result);
    }

    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() {
        // Set up documents for Image Editor
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreationDate(LocalDate.of(2023, 9, 15));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        imageEditor.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreationDate(LocalDate.of(2023, 9, 20));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        imageEditor.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreationDate(LocalDate.of(2023, 9, 25));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(videoEditor);
        videoEditor.addDocument(doc3);
        
        fileSystem.setDocuments(Arrays.asList(doc1, doc2, doc3));
        
        // Test retrieving authors for Image Editor
        List<String> result = fileSystem.getAuthorNamesForEditor(imageEditor);
        List<String> expected = Arrays.asList("Dave", "Eve");
        
        assertEquals("Should return authors Dave and Eve for Image Editor", expected, result);
    }

    @Test
    public void testCase3_NoAuthorsForSpecificEditor() {
        // Set up documents without any Video Editor documents
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreationDate(LocalDate.of(2023, 10, 5));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        textEditor.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreationDate(LocalDate.of(2023, 10, 6));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        imageEditor.addDocument(doc2);
        
        fileSystem.setDocuments(Arrays.asList(doc1, doc2));
        
        // Test retrieving authors for Video Editor (should be empty)
        List<String> result = fileSystem.getAuthorNamesForEditor(videoEditor);
        List<String> expected = Arrays.asList();
        
        assertEquals("Should return empty list for Video Editor with no documents", expected, result);
    }

    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // Set up mixed editor documents
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreationDate(LocalDate.of(2023, 10, 7));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        textEditor.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreationDate(LocalDate.of(2023, 10, 8));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(videoEditor);
        videoEditor.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreationDate(LocalDate.of(2023, 10, 9));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        imageEditor.addDocument(doc3);
        
        fileSystem.setDocuments(Arrays.asList(doc1, doc2, doc3));
        
        // Test retrieving authors for Text Editor
        List<String> result = fileSystem.getAuthorNamesForEditor(textEditor);
        List<String> expected = Arrays.asList("Ivy");
        
        assertEquals("Should return author Ivy for Text Editor", expected, result);
    }

    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() {
        // Set up multiple Image Editor documents
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreationDate(LocalDate.of(2023, 9, 30));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        imageEditor.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreationDate(LocalDate.of(2023, 10, 1));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        imageEditor.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreationDate(LocalDate.of(2023, 10, 2));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        textEditor.addDocument(doc3);
        
        fileSystem.setDocuments(Arrays.asList(doc1, doc2, doc3));
        
        // Test retrieving authors for Image Editor
        List<String> result = fileSystem.getAuthorNamesForEditor(imageEditor);
        List<String> expected = Arrays.asList("Leo", "Mona");
        
        assertEquals("Should return authors Leo and Mona for Image Editor", expected, result);
    }
}