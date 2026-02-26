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
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;

    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }

    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws Exception {
        // Set up test data
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

        // Execute method under test
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);

        // Verify results
        List<String> expected = Arrays.asList("Alice", "Bob");
        assertEquals("Should return authors for Text Editor documents", expected.size(), authors.size());
        assertTrue("Should contain Alice", authors.contains("Alice"));
        assertTrue("Should contain Bob", authors.contains("Bob"));
        assertFalse("Should not contain Charlie", authors.contains("Charlie"));
    }

    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // Set up test data
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
        doc3.setSize(500000);
        doc3.setEditor(videoEditor);

        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);

        // Execute method under test
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);

        // Verify results
        List<String> expected = Arrays.asList("Dave", "Eve");
        assertEquals("Should return authors for Image Editor documents", expected.size(), authors.size());
        assertTrue("Should contain Dave", authors.contains("Dave"));
        assertTrue("Should contain Eve", authors.contains("Eve"));
        assertFalse("Should not contain Frank", authors.contains("Frank"));
    }

    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // Set up test data
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

        // Execute method under test
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);

        // Verify results
        assertTrue("Should return empty list when no documents match the editor", authors.isEmpty());
    }

    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // Set up test data
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
        doc2.setSize(400000);
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

        // Execute method under test
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);

        // Verify results
        List<String> expected = Arrays.asList("Ivy");
        assertEquals("Should return only authors for Text Editor documents", expected.size(), authors.size());
        assertTrue("Should contain Ivy", authors.contains("Ivy"));
        assertFalse("Should not contain Jack", authors.contains("Jack"));
        assertFalse("Should not contain Kathy", authors.contains("Kathy"));
    }

    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // Set up test data
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

        // Execute method under test
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);

        // Verify results
        List<String> expected = Arrays.asList("Leo", "Mona");
        assertEquals("Should return all authors for Image Editor documents", expected.size(), authors.size());
        assertTrue("Should contain Leo", authors.contains("Leo"));
        assertTrue("Should contain Mona", authors.contains("Mona"));
        assertFalse("Should not contain Nina", authors.contains("Nina"));
    }

}