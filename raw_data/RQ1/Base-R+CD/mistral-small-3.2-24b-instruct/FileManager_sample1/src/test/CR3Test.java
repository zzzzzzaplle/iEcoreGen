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
        // Initialize FileSystem and date format before each test
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_countDocumentsAfterDateWithNoDocuments() throws Exception {
        // Test Case 1: Count documents created after a specific date with no documents
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Count documents created after 2023-10-01
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify that result is 0 when there are no documents
        assertEquals("Total documents created after 2023-10-01 should be 0 when no documents exist", 
                     0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // Test Case 2: Count documents created after a specific date with one document
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Create and add document created on 2023-10-05
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.addDocument(doc1);
        
        // Count documents created after 2023-10-01
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify that result is 1 when one document exists after the target date
        assertEquals("Total documents created after 2023-10-01 should be 1", 
                     1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // Test Case 3: Count documents created after a specific date with multiple documents
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        
        // Create and add documents with different creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        fileSystem.addDocument(doc4);
        
        // Count documents created after 2023-09-15
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify that result is 3 (doc2, doc3, doc4 are after 2023-09-15)
        assertEquals("Total documents created after 2023-09-15 should be 3", 
                     3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // Test Case 4: Count documents created before the specific date
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // Create and add documents created before the target date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        fileSystem.addDocument(doc2);
        
        // Count documents created after 2023-09-30
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify that result is 0 when all documents are created before the target date
        assertEquals("Total documents created after 2023-09-30 should be 0", 
                     0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // Test Case 5: Count documents with variation in creation dates
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // Create and add documents with varied creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        fileSystem.addDocument(doc3);
        
        // Count documents created after 2023-08-01
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify that result is 2 (doc2 and doc3 are after 2023-08-01)
        assertEquals("Total documents created after 2023-08-01 should be 2", 
                     2, result);
    }
}