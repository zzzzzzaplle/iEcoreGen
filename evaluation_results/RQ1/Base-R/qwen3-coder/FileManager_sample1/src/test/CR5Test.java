import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws Exception {
        // SetUp: Create documents with Text Editor and Image Editor
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        Editor editor1 = new Editor();
        editor1.setName("Text Editor");
        doc1.setEditor(editor1);

        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreationDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        Editor editor2 = new Editor();
        editor2.setName("Text Editor");
        doc2.setEditor(editor2);

        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreationDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        Editor editor3 = new Editor();
        editor3.setName("Image Editor");
        doc3.setEditor(editor3);

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
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // SetUp: Create documents with Image Editor and Video Editor
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        Editor editor1 = new Editor();
        editor1.setName("Image Editor");
        doc1.setEditor(editor1);

        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreationDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        Editor editor2 = new Editor();
        editor2.setName("Image Editor");
        doc2.setEditor(editor2);

        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreationDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        Editor editor3 = new Editor();
        editor3.setName("Video Editor");
        doc3.setEditor(editor3);

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
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // SetUp: Create documents with Text Editor and Image Editor (no Video Editor)
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        Editor editor1 = new Editor();
        editor1.setName("Text Editor");
        doc1.setEditor(editor1);

        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        Editor editor2 = new Editor();
        editor2.setName("Image Editor");
        doc2.setEditor(editor2);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);

        // Input: Get authors for documents using "Video Editor"
        Set<String> result = fileSystem.getAuthorsByEditor("Video Editor");

        // Expected Output: Authors = []
        Set<String> expected = new HashSet<>();
        assertEquals(expected, result);
    }

    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // SetUp: Create documents with mixed editors
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreationDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        Editor editor1 = new Editor();
        editor1.setName("Text Editor");
        doc1.setEditor(editor1);

        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreationDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        Editor editor2 = new Editor();
        editor2.setName("Video Editor");
        doc2.setEditor(editor2);

        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreationDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        Editor editor3 = new Editor();
        editor3.setName("Image Editor");
        doc3.setEditor(editor3);

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
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // SetUp: Create multiple documents with Image Editor
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreationDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        Editor editor1 = new Editor();
        editor1.setName("Image Editor");
        doc1.setEditor(editor1);

        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        Editor editor2 = new Editor();
        editor2.setName("Image Editor");
        doc2.setEditor(editor2);

        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreationDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        Editor editor3 = new Editor();
        editor3.setName("Text Editor");
        doc3.setEditor(editor3);

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