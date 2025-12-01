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
    public void testCase1_countDocumentsCreatedAfterSpecificDateWithNoDocuments() throws ParseException {
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. The FileSystem contains no documents.
        Date specifiedDate = dateFormat.parse("2023-10-01");
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        assertEquals(0, result);
    }

    @Test
    public void testCase2_countDocumentsCreatedAfterSpecificDateWithOneDocument() throws ParseException {
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-10-05, author "Author1", size 10KB.
        Date specifiedDate = dateFormat.parse("2023-10-01");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.addDocument(doc1);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        assertEquals(1, result);
    }

    @Test
    public void testCase3_countDocumentsCreatedAfterSpecificDateWithMultipleDocuments() throws ParseException {
        // Input: Count the number of documents created after 2023-09-15
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-09-10, author "Author1", size 15KB.
        // 3. Add a document named "Doc2" created on 2023-09-20, author "Author2", size 20KB.
        // 4. Add a document named "Doc3" created on 2023-10-01, author "Author3", size 5KB.
        // 5. Add a document named "Doc4" created on 2023-10-10, author "Author4", size 25KB.
        Date specifiedDate = dateFormat.parse("2023-09-15");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        fileSystem.addDocument(doc4);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        assertEquals(3, result);
    }

    @Test
    public void testCase4_countDocumentsCreatedBeforeTheSpecificDate() throws ParseException {
        // Input: Count the number of documents created after 2023-09-30
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-09-28, author "Author1", size 12KB.
        // 3. Add a document named "Doc2" created on 2023-08-15, author "Author2", size 30KB.
        Date specifiedDate = dateFormat.parse("2023-09-30");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        fileSystem.addDocument(doc2);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        assertEquals(0, result);
    }

    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws ParseException {
        // Input: Count the number of documents created after 2023-08-01
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-07-30, author "Author1", size 10KB.
        // 3. Add a document named "Doc2" created on 2023-08-05, author "Author2", size 20KB.
        // 4. Add a document named "Doc3" created on 2023-09-15, author "Author3", size 15KB.
        Date specifiedDate = dateFormat.parse("2023-08-01");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        fileSystem.addDocument(doc3);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        assertEquals(2, result);
    }
}