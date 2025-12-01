import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
        // SetUp: Create file system with specified documents
        TextEditor textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        
        // Document 1: "Report.doc" with Text Editor
        Document doc1 = new Document();
        doc1.setName("Report.doc");
        doc1.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        // Document 2: "Essay.doc" with Text Editor
        Document doc2 = new Document();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        
        // Document 3: "Image.png" with Image Editor
        Document doc3 = new Document();
        doc3.setName("Image.png");
        doc3.setCreateDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        List<String> result = fileSystem.getAuthorsByEditor(textEditor);
        
        // Expected Output: Authors = ["Alice", "Bob"]
        List<String> expected = Arrays.asList("Alice", "Bob");
        assertEquals("Should return Alice and Bob for Text Editor", expected, result);
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // SetUp: Create file system with specified documents
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        
        VideoEditor videoEditor = new VideoEditor();
        videoEditor.setName("Video Editor");
        
        // Document 1: "Photo.jpg" with Image Editor
        Document doc1 = new Document();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        
        // Document 2: "Diagram.svg" with Image Editor
        Document doc2 = new Document();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        // Document 3: "Video.mp4" with Video Editor
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        List<String> result = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Expected Output: Authors = ["Dave", "Eve"]
        List<String> expected = Arrays.asList("Dave", "Eve");
        assertEquals("Should return Dave and Eve for Image Editor", expected, result);
    }
    
    @Test
    public void testCase3_NoAuthorsForASpecificEditor() throws Exception {
        // SetUp: Create file system with specified documents
        TextEditor textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        
        VideoEditor videoEditor = new VideoEditor();
        videoEditor.setName("Video Editor");
        
        // Document 1: "Document.txt" with Text Editor
        Document doc1 = new Document();
        doc1.setName("Document.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        
        // Document 2: "Drawing.png" with Image Editor
        Document doc2 = new Document();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Get authors for documents using "Video Editor"
        List<String> result = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Expected Output: Authors = []
        assertTrue("Should return empty list for Video Editor", result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // SetUp: Create file system with specified documents
        TextEditor textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        
        VideoEditor videoEditor = new VideoEditor();
        videoEditor.setName("Video Editor");
        
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        
        // Document 1: "Notes.txt" with Text Editor
        Document doc1 = new Document();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        
        // Document 2: "Video.mp4" with Video Editor
        Document doc2 = new Document();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(videoEditor);
        
        // Document 3: "Image.JPG" with Image Editor
        Document doc3 = new Document();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Text Editor"
        List<String> result = fileSystem.getAuthorsByEditor(textEditor);
        
        // Expected Output: Authors = ["Ivy"]
        List<String> expected = Arrays.asList("Ivy");
        assertEquals("Should return only Ivy for Text Editor", expected, result);
    }
    
    @Test
    public void testCase5_MultipleAuthorsForASingleDocumentType() throws Exception {
        // SetUp: Create file system with specified documents
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        
        TextEditor textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        
        // Document 1: "Portfolio.jpg" with Image Editor
        Document doc1 = new Document();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        
        // Document 2: "Banner.png" with Image Editor
        Document doc2 = new Document();
        doc2.setName("Banner.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        // Document 3: "Presentation.ppt" with Text Editor
        Document doc3 = new Document();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Get authors for documents using "Image Editor"
        List<String> result = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Expected Output: Authors = ["Leo", "Mona"]
        List<String> expected = Arrays.asList("Leo", "Mona");
        assertEquals("Should return Leo and Mona for Image Editor", expected, result);
    }
}