import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CR5Test {
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws ParseException {
        // SetUp: Create documents as specified
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        Editor textEditor1 = new Editor();
        textEditor1.setName("Text Editor");
        doc1.setEditor(textEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreationDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        Editor textEditor2 = new Editor();
        textEditor2.setName("Text Editor");
        doc2.setEditor(textEditor2);
        
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreationDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc3.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        Set<String> result = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Expected Output: Authors = ["Alice", "Bob"]
        Set<String> expected = new HashSet<>();
        expected.add("Alice");
        expected.add("Bob");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws ParseException {
        // SetUp: Create documents as specified
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        Editor imageEditor1 = new Editor();
        imageEditor1.setName("Image Editor");
        doc1.setEditor(imageEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreationDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        Editor imageEditor2 = new Editor();
        imageEditor2.setName("Image Editor");
        doc2.setEditor(imageEditor2);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreationDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        Editor videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        doc3.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        Set<String> result = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Expected Output: Authors = ["Dave", "Eve"]
        Set<String> expected = new HashSet<>();
        expected.add("Dave");
        expected.add("Eve");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws ParseException {
        // SetUp: Create documents as specified
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc2.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        Set<String> result = fileSystem.getAuthorsByEditor("Video Editor");
        
        // Expected Output: Authors = []
        Set<String> expected = new HashSet<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws ParseException {
        // SetUp: Create documents as specified
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreationDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        Editor videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreationDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc3.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        Set<String> result = fileSystem.getAuthorsByEditor("Text Editor");
        
        // Expected Output: Authors = ["Ivy"]
        Set<String> expected = new HashSet<>();
        expected.add("Ivy");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws ParseException {
        // SetUp: Create documents as specified
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        Editor imageEditor1 = new Editor();
        imageEditor1.setName("Image Editor");
        doc1.setEditor(imageEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        Editor imageEditor2 = new Editor();
        imageEditor2.setName("Image Editor");
        doc2.setEditor(imageEditor2);
        
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreationDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        doc3.setEditor(textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        Set<String> result = fileSystem.getAuthorsByEditor("Image Editor");
        
        // Expected Output: Authors = ["Leo", "Mona"]
        Set<String> expected = new HashSet<>();
        expected.add("Leo");
        expected.add("Mona");
        
        assertEquals(expected, result);
    }
}