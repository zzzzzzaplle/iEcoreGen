import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

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
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01.
        // SetUp: FileSystem contains no documents.
        
        Date specifiedDate = dateFormat.parse("2023-10-01 00:00:00");
        
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01.
        // SetUp: Add a document named "Doc1" created on 2023-10-05.
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10 * 1024); // 10KB
        
        fileSystem.addDocument(doc1);
        
        Date specifiedDate = dateFormat.parse("2023-10-01 00:00:00");
        
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals("Should find 1 document created after specified date", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15.
        // SetUp: Add documents with various creation dates
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15 * 1024); // 15KB
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20 * 1024); // 20KB
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5 * 1024); // 5KB
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreationDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25 * 1024); // 25KB
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        Date specifiedDate = dateFormat.parse("2023-09-15 00:00:00");
        
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals("Should find 3 documents created after specified date", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30.
        // SetUp: Add documents created before the specified date
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12 * 1024); // 12KB
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30 * 1024); // 30KB
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        Date specifiedDate = dateFormat.parse("2023-09-30 00:00:00");
        
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals("Should find 0 documents when all are created before specified date", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01.
        // SetUp: Add documents with creation dates spanning different months
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10 * 1024); // 10KB
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20 * 1024); // 20KB
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15 * 1024); // 15KB
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        Date specifiedDate = dateFormat.parse("2023-08-01 00:00:00");
        
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals("Should find 2 documents created after specified date", 2, result);
    }
}