import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
        // Get the editors from the file system
        List<Editor> editors = fileSystem.getEditors();
        for (Editor editor : editors) {
            if (editor.getName().equals("Text Editor")) {
                textEditor = (TextEditor) editor;
            } else if (editor.getName().equals("Image Editor")) {
                imageEditor = (ImageEditor) editor;
            } else if (editor.getName().equals("Video Editor")) {
                videoEditor = (VideoEditor) editor;
            }
        }
    }
    
    @Test
    public void testCase1_retrieveAuthorsForTextEditorDocuments() throws Exception {
        // Set up documents as specified in test case 1
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
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Get authors for Text Editor
        List<String> result = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Alice", "Bob");
        assertEquals("Should return authors Alice and Bob for Text Editor", expected, result);
    }
    
    @Test
    public void testCase2_retrieveAuthorsForImageEditorDocuments() throws Exception {
        // Set up documents as specified in test case 2
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
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Get authors for Image Editor
        List<String> result = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Dave", "Eve");
        assertEquals("Should return authors Dave and Eve for Image Editor", expected, result);
    }
    
    @Test
    public void testCase3_noAuthorsForSpecificEditor() throws Exception {
        // Set up documents as specified in test case 3
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
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Get authors for Video Editor (which has no documents)
        List<String> result = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Verify expected output
        List<String> expected = Collections.emptyList();
        assertEquals("Should return empty list for Video Editor with no documents", expected, result);
    }
    
    @Test
    public void testCase4_retrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // Set up documents as specified in test case 4
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
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Get authors for Text Editor
        List<String> result = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Ivy");
        assertEquals("Should return author Ivy for Text Editor", expected, result);
    }
    
    @Test
    public void testCase5_multipleAuthorsForSingleDocumentType() throws Exception {
        // Set up documents as specified in test case 5
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
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Get authors for Image Editor
        List<String> result = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        List<String> expected = Arrays.asList("Leo", "Mona");
        assertEquals("Should return authors Leo and Mona for Image Editor", expected, result);
    }
}