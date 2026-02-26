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
        // SetUp: Create a FileSystem instance
        // Add a Document named "Document1" with size: 100 KB
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        fileSystem.addDocument(doc1);

        // Add a Document named "Document2" with size: 200 KB
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        doc2.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        fileSystem.addDocument(doc2);

        // Add a Document named "Document3" with size: 300 KB
        Document doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        doc3.setCreateDate(dateFormat.parse("2023-01-03 10:00:00"));
        fileSystem.addDocument(doc3);

        // Expected Output: Total document size = 100 + 200 + 300 = 600 KB
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(600, totalSize);
    }

    @Test
    public void testCase2_calculateTotalSizeAfterDocumentRemoval() throws ParseException {
        // SetUp: Create a FileSystem instance
        // Add a Document named "Document1" with size: 150 KB
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        fileSystem.addDocument(doc1);

        // Add a Document named "Document2" with size: 250 KB
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        doc2.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        fileSystem.addDocument(doc2);

        // Remove Document1 from the FileSystem
        fileSystem.removeDocument(doc1);

        // Expected Output: Total document size = 250 KB
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(250, totalSize);
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
        // Add a Document named "DocA" with size: 50 KB
        Document docA = new Document();
        docA.setName("DocA");
        docA.setSize(50);
        docA.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        fileSystem.addDocument(docA);

        // Add a Document named "DocB" with size: 1000 KB
        Document docB = new Document();
        docB.setName("DocB");
        docB.setSize(1000);
        docB.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        fileSystem.addDocument(docB);

        // Add a Document named "DocC" with size: 250 KB
        Document docC = new Document();
        docC.setName("DocC");
        docC.setSize(250);
        docC.setCreateDate(dateFormat.parse("2023-01-03 10:00:00"));
        fileSystem.addDocument(docC);

        // Expected Output: Total document size = 50 + 1000 + 250 = 1300 KB
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(1300, totalSize);
    }

    @Test
    public void testCase5_calculateTotalSizeAfterMultipleRemovals() throws ParseException {
        // SetUp: Create a FileSystem instance
        // Add Document "Report" with size: 400 KB
        Document report = new Document();
        report.setName("Report");
        report.setSize(400);
        report.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        fileSystem.addDocument(report);

        // Add Document "Image" with size: 300 KB
        Document image = new Document();
        image.setName("Image");
        image.setSize(300);
        image.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        fileSystem.addDocument(image);

        // Add Document "Video" with size: 700 KB
        Document video = new Document();
        video.setName("Video");
        video.setSize(700);
        video.setCreateDate(dateFormat.parse("2023-01-03 10:00:00"));
        fileSystem.addDocument(video);

        // Remove "Image" (300 KB) and "Report" (400 KB)
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);

        // Expected Output: Total document size = 700 KB
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(700, totalSize);
    }
}