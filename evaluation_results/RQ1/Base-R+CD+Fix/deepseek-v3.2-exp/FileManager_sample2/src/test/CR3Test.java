import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_countDocumentsAfterDateWithNoDocuments() throws Exception {
        // SetUp: Create a FileSystem instance with no documents
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute: Count documents created after 2023-10-01
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify: Expected output = 0
        assertEquals("No documents should be counted when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // SetUp: Create a FileSystem instance with one document created on 2023-10-05
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        fileSystem.addDocument(doc1);
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute: Count documents created after 2023-10-01
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify: Expected output = 1
        assertEquals("One document should be counted when it was created after cutoff date", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // SetUp: Create a FileSystem instance with multiple documents
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        Date cutoffDate = dateFormat.parse("2023-09-15 00:00:00");
        
        // Execute: Count documents created after 2023-09-15
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify: Expected output = 3 (Doc2, Doc3, Doc4)
        assertEquals("Three documents should be counted when created after cutoff date", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // SetUp: Create a FileSystem instance with documents created before the cutoff date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        Date cutoffDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // Execute: Count documents created after 2023-09-30
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify: Expected output = 0
        assertEquals("No documents should be counted when all were created before cutoff date", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // SetUp: Create a FileSystem instance with documents having various creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        Date cutoffDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // Execute: Count documents created after 2023-08-01
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify: Expected output = 2 (Doc2, Doc3)
        assertEquals("Two documents should be counted when created after cutoff date", 2, result);
    }
}