import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_calculateTotalSizeOfMultipleDocuments() throws ParseException {
        // Create documents with specified sizes
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        TextEditor editor1 = new TextEditor();
        editor1.setName("TextEditor1");
        doc1.setEditor(editor1);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        doc2.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        doc2.setAuthor("Author2");
        TextEditor editor2 = new TextEditor();
        editor2.setName("TextEditor2");
        doc2.setEditor(editor2);
        
        Document doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        doc3.setCreateDate(dateFormat.parse("2023-01-03 10:00:00"));
        doc3.setAuthor("Author3");
        TextEditor editor3 = new TextEditor();
        editor3.setName("TextEditor3");
        doc3.setEditor(editor3);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected output: 100 + 200 + 300 = 600 KB
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_calculateTotalSizeAfterDocumentRemoval() throws ParseException {
        // Create documents
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        TextEditor editor1 = new TextEditor();
        editor1.setName("TextEditor1");
        doc1.setEditor(editor1);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        doc2.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        doc2.setAuthor("Author2");
        TextEditor editor2 = new TextEditor();
        editor2.setName("TextEditor2");
        doc2.setEditor(editor2);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1
        fileSystem.removeDocument(doc1);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected output: 250 KB
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_emptyFileSystemCalculation() {
        // Create empty file system (no documents added)
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected output: 0 KB
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_calculateTotalSizeOfDocumentsWithMixedSizes() throws ParseException {
        // Create documents with mixed sizes
        Document docA = new Document();
        docA.setName("DocA");
        docA.setSize(50);
        docA.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        docA.setAuthor("AuthorA");
        TextEditor editorA = new TextEditor();
        editorA.setName("TextEditorA");
        docA.setEditor(editorA);
        
        Document docB = new Document();
        docB.setName("DocB");
        docB.setSize(1000);
        docB.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        docB.setAuthor("AuthorB");
        TextEditor editorB = new TextEditor();
        editorB.setName("TextEditorB");
        docB.setEditor(editorB);
        
        Document docC = new Document();
        docC.setName("DocC");
        docC.setSize(250);
        docC.setCreateDate(dateFormat.parse("2023-01-03 10:00:00"));
        docC.setAuthor("AuthorC");
        TextEditor editorC = new TextEditor();
        editorC.setName("TextEditorC");
        docC.setEditor(editorC);
        
        // Add documents to file system
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected output: 50 + 1000 + 250 = 1300 KB
        assertEquals(1300, totalSize);
    }
    
    @Test
    public void testCase5_calculateTotalSizeAfterMultipleRemovals() throws ParseException {
        // Create documents
        Document report = new Document();
        report.setName("Report");
        report.setSize(400);
        report.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        report.setAuthor("Author1");
        TextEditor editor1 = new TextEditor();
        editor1.setName("TextEditor1");
        report.setEditor(editor1);
        
        Document image = new Document();
        image.setName("Image");
        image.setSize(300);
        image.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        image.setAuthor("Author2");
        ImageEditor editor2 = new ImageEditor();
        editor2.setName("ImageEditor2");
        image.setEditor(editor2);
        
        Document video = new Document();
        video.setName("Video");
        video.setSize(700);
        video.setCreateDate(dateFormat.parse("2023-01-03 10:00:00"));
        video.setAuthor("Author3");
        VideoEditor editor3 = new VideoEditor();
        editor3.setName("VideoEditor3");
        video.setEditor(editor3);
        
        // Add documents to file system
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Remove Image and Report
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected output: 700 KB
        assertEquals(700, totalSize);
    }
}