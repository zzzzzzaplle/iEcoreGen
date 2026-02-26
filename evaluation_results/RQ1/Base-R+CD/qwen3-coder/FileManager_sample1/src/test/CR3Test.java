import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void testCase1_CountDocumentsCreatedAfterSpecificDateWithNoDocuments() throws ParseException {
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. The FileSystem contains no documents.
        // Expected Output: Total documents created after 2023-10-01 = 0
        
        Date specifiedDate = dateFormat.parse("2023-10-01");
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        assertEquals(0, result);
    }

    @Test
    public void testCase2_CountDocumentsCreatedAfterSpecificDateWithOneDocument() throws ParseException {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-10-05, author "Author1", size 10KB.
        // Expected Output: Total documents created after 2023-10-01 = 1
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        fileSystem.addDocument(doc1);
        
        Date specifiedDate = dateFormat.parse("2023-10-01");
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        assertEquals(1, result);
    }

    @Test
    public void testCase3_CountDocumentsCreatedAfterSpecificDateWithMultipleDocuments() throws ParseException {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-09-10, author "Author1", size 15KB.
        // 3. Add a document named "Doc2" created on 2023-09-20, author "Author2", size 20KB.
        // 4. Add a document named "Doc3" created on 2023-10-01, author "Author3", size 5KB.
        // 5. Add a document named "Doc4" created on 2023-10-10, author "Author4", size 25KB.
        // Expected Output: Total documents created after 2023-09-15 = 3
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        Date specifiedDate = dateFormat.parse("2023-09-15");
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        assertEquals(3, result);
    }

    @Test
    public void testCase4_CountDocumentsCreatedBeforeTheSpecificDate() throws ParseException {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-09-28, author "Author1", size 12KB.
        // 3. Add a document named "Doc2" created on 2023-08-15, author "Author2", size 30KB.
        // Expected Output: Total documents created after 2023-09-30 = 0
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        Date specifiedDate = dateFormat.parse("2023-09-30");
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        assertEquals(0, result);
    }

    @Test
    public void testCase5_CountDocumentsWithVariationInCreationDates() throws ParseException {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-07-30, author "Author1", size 10KB.
        // 3. Add a document named "Doc2" created on 2023-08-05, author "Author2", size 20KB.
        // 4. Add a document named "Doc3" created on 2023-09-15, author "Author3", size 15KB.
        // Expected Output: Total documents created after 2023-08-01 = 2
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        Date specifiedDate = dateFormat.parse("2023-08-01");
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        assertEquals(2, result);
    }
}