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
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-10-01 should be 0", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // SetUp: Create a FileSystem instance with one document created after the cutoff date
        Date docDate = dateFormat.parse("2023-10-05 00:00:00");
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(docDate);
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        fileSystem.addDocument(doc1);
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-10-01 should be 1", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // SetUp: Create a FileSystem instance with multiple documents
        Date doc1Date = dateFormat.parse("2023-09-10 00:00:00");
        Date doc2Date = dateFormat.parse("2023-09-20 00:00:00");
        Date doc3Date = dateFormat.parse("2023-10-01 00:00:00");
        Date doc4Date = dateFormat.parse("2023-10-10 00:00:00");
        Date cutoffDate = dateFormat.parse("2023-09-15 00:00:00");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(doc1Date);
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(doc2Date);
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(doc3Date);
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreationDate(doc4Date);
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the expected output (doc2, doc3, and doc4 are after 2023-09-15)
        assertEquals("Total documents created after 2023-09-15 should be 3", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // SetUp: Create a FileSystem instance with documents created before the cutoff date
        Date doc1Date = dateFormat.parse("2023-09-28 00:00:00");
        Date doc2Date = dateFormat.parse("2023-08-15 00:00:00");
        Date cutoffDate = dateFormat.parse("2023-09-30 00:00:00");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(doc1Date);
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(doc2Date);
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the expected output (both documents are before 2023-09-30)
        assertEquals("Total documents created after 2023-09-30 should be 0", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // SetUp: Create a FileSystem instance with documents having varied creation dates
        Date doc1Date = dateFormat.parse("2023-07-30 00:00:00");
        Date doc2Date = dateFormat.parse("2023-08-05 00:00:00");
        Date doc3Date = dateFormat.parse("2023-09-15 00:00:00");
        Date cutoffDate = dateFormat.parse("2023-08-01 00:00:00");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(doc1Date);
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(doc2Date);
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(doc3Date);
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the expected output (doc2 and doc3 are after 2023-08-01)
        assertEquals("Total documents created after 2023-08-01 should be 2", 2, result);
    }
}