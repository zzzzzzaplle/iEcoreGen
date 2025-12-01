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
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01.
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. The FileSystem contains no documents.
        
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals("Should return 0 when no documents exist", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01.
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-10-05, author "Author1", size 10KB.
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        fileSystem.addDocument(doc1);
        
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals("Should return 1 when one document is created after cutoff date", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15.
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-09-10, author "Author1", size 15KB.
        // 3. Add a document named "Doc2" created on 2023-09-20, author "Author2", size 20KB.
        // 4. Add a document named "Doc3" created on 2023-10-01, author "Author3", size 5KB.
        // 5. Add a document named "Doc4" created on 2023-10-10, author "Author4", size 25KB.
        
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
        
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals("Should return 3 when three documents are created after cutoff date", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30.
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-09-28, author "Author1", size 12KB.
        // 3. Add a document named "Doc2" created on 2023-08-15, author "Author2", size 30KB.
        
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
        
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals("Should return 0 when all documents are created before cutoff date", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01.
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-07-30, author "Author1", size 10KB.
        // 3. Add a document named "Doc2" created on 2023-08-05, author "Author2", size 20KB.
        // 4. Add a document named "Doc3" created on 2023-09-15, author "Author3", size 15KB.
        
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
        
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals("Should return 2 when two documents are created after cutoff date", 2, result);
    }
}