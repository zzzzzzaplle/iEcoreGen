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
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;

    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        videoEditor = new VideoEditor();
        videoEditor.setName("Video Editor");
    }

    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws ParseException {
        // Setup documents as specified in test case 1
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreateDate(dateFormat.parse("2023-10-01"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);

        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(dateFormat.parse("2023-10-02"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);

        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreateDate(dateFormat.parse("2023-10-03"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);

        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);

        // Verify expected output
        List<String> expected = Arrays.asList("Alice", "Bob");
        assertEquals("Should return authors for Text Editor documents only", expected, authors);
    }

    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws ParseException {
        // Setup documents as specified in test case 2
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-15"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);

        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(dateFormat.parse("2023-09-20"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);

        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2023-09-25"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(videoEditor);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);

        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);

        // Verify expected output
        List<String> expected = Arrays.asList("Dave", "Eve");
        assertEquals("Should return authors for Image Editor documents only", expected, authors);
    }

    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws ParseException {
        // Setup documents as specified in test case 3
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-05"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);

        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-06"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);

        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);

        // Verify expected output
        assertTrue("Should return empty list when no documents match the specified editor", authors.isEmpty());
    }

    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws ParseException {
        // Setup documents as specified in test case 4
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-07"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);

        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(dateFormat.parse("2023-10-08"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(videoEditor);

        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(dateFormat.parse("2023-10-09"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);

        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);

        // Verify expected output
        List<String> expected = Arrays.asList("Ivy");
        assertEquals("Should return only author for Text Editor document in mixed collection", expected, authors);
    }

    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws ParseException {
        // Setup documents as specified in test case 5
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-30"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);

        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-01"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);

        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(dateFormat.parse("2023-10-02"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);

        // Execute the method under test
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);

        // Verify expected output
        List<String> expected = Arrays.asList("Leo", "Mona");
        assertEquals("Should return all authors for Image Editor documents", expected, authors);
    }
}