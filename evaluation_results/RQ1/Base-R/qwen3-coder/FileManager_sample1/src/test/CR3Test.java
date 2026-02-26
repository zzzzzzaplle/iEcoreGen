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
    public void testCase1_countDocumentsCreatedAfterWithNoDocuments() throws Exception {
        // SetUp: Create a FileSystem instance with no documents
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute the countDocumentsCreatedAfter method
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-10-01 should be 0 when file system is empty", 
                     0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() throws Exception {
        // SetUp: Create a FileSystem instance with one document created after target date
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        fileSystem.addDocument(doc1);
        
        // Execute the countDocumentsCreatedAfter method
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-10-01 should be 1 when one document exists after the date", 
                     1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() throws Exception {
        // SetUp: Create a FileSystem instance with multiple documents
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreationDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute the countDocumentsCreatedAfter method
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-09-15 should be 3 when 3 out of 4 documents are after the date", 
                     3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // SetUp: Create a FileSystem instance with documents created before target date
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the countDocumentsCreatedAfter method
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-09-30 should be 0 when all documents are before the date", 
                     0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // SetUp: Create a FileSystem instance with documents having varied creation dates
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the countDocumentsCreatedAfter method
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-08-01 should be 2 when 2 out of 3 documents are after the date", 
                     2, result);
    }
}