import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }
    
    @Test
    public void testCase1_calculateTotalSizeOfMultipleDocuments() throws ParseException {
        // SetUp: Create a FileSystem instance
        // Add a Document named "Document1" with size: 100 KB (102400 bytes)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse("2023-01-01 10:00:00");
        Document doc1 = new Document("Document1", date1, "Author1", 102400, textEditor);
        fileSystem.addDocument(doc1);
        
        // Add a Document named "Document2" with size: 200 KB (204800 bytes)
        Date date2 = sdf.parse("2023-01-02 10:00:00");
        Document doc2 = new Document("Document2", date2, "Author2", 204800, imageEditor);
        fileSystem.addDocument(doc2);
        
        // Add a Document named "Document3" with size: 300 KB (307200 bytes)
        Date date3 = sdf.parse("2023-01-03 10:00:00");
        Document doc3 = new Document("Document3", date3, "Author3", 307200, videoEditor);
        fileSystem.addDocument(doc3);
        
        // Expected Output: Total document size = 100 + 200 + 300 = 600 KB (614400 bytes)
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(614400, totalSize);
    }
    
    @Test
    public void testCase2_calculateTotalSizeAfterDocumentRemoval() throws ParseException {
        // SetUp: Create a FileSystem instance
        // Add a Document named "Document1" with size: 150 KB (153600 bytes)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse("2023-01-01 10:00:00");
        Document doc1 = new Document("Document1", date1, "Author1", 153600, textEditor);
        fileSystem.addDocument(doc1);
        
        // Add a Document named "Document2" with size: 250 KB (256000 bytes)
        Date date2 = sdf.parse("2023-01-02 10:00:00");
        Document doc2 = new Document("Document2", date2, "Author2", 256000, imageEditor);
        fileSystem.addDocument(doc2);
        
        // Remove Document1 from the FileSystem
        fileSystem.removeDocument(doc1);
        
        // Expected Output: Total document size = 250 KB (256000 bytes)
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(256000, totalSize);
    }
    
    @Test
    public void testCase3_emptyFileSystemCalculation() {
        // SetUp: Create a FileSystem instance with no documents added
        // Expected Output: Total document size = 0 KB
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_calculateTotalSizeOfDocumentsWithMixedSizes() throws ParseException {
        // SetUp: Create a FileSystem instance
        // Add a Document named "DocA" with size: 50 KB (51200 bytes)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse("2023-01-01 10:00:00");
        Document docA = new Document("DocA", date1, "AuthorA", 51200, textEditor);
        fileSystem.addDocument(docA);
        
        // Add a Document named "DocB" with size: 1000 KB (1024000 bytes)
        Date date2 = sdf.parse("2023-01-02 10:00:00");
        Document docB = new Document("DocB", date2, "AuthorB", 1024000, imageEditor);
        fileSystem.addDocument(docB);
        
        // Add a Document named "DocC" with size: 250 KB (256000 bytes)
        Date date3 = sdf.parse("2023-01-03 10:00:00");
        Document docC = new Document("DocC", date3, "AuthorC", 256000, videoEditor);
        fileSystem.addDocument(docC);
        
        // Expected Output: Total document size = 50 + 1000 + 250 = 1300 KB (1331200 bytes)
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(1331200, totalSize);
    }
    
    @Test
    public void testCase5_calculateTotalSizeAfterMultipleRemovals() throws ParseException {
        // SetUp: Create a FileSystem instance
        // Add Document "Report" with size: 400 KB (409600 bytes)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse("2023-01-01 10:00:00");
        Document report = new Document("Report", date1, "Author1", 409600, textEditor);
        fileSystem.addDocument(report);
        
        // Add Document "Image" with size: 300 KB (307200 bytes)
        Date date2 = sdf.parse("2023-01-02 10:00:00");
        Document image = new Document("Image", date2, "Author2", 307200, imageEditor);
        fileSystem.addDocument(image);
        
        // Add Document "Video" with size: 700 KB (716800 bytes)
        Date date3 = sdf.parse("2023-01-03 10:00:00");
        Document video = new Document("Video", date3, "Author3", 716800, videoEditor);
        fileSystem.addDocument(video);
        
        // Remove "Image" (300 KB) and "Report" (400 KB)
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Expected Output: Total document size = 700 KB (716800 bytes)
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(716800, totalSize);
    }
}