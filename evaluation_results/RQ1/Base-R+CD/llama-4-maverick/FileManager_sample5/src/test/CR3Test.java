import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        // Input: Count the number of documents created after 2023-10-01
        // Setup: Create a FileSystem instance with no documents
        Date specifiedDate = dateFormat.parse("2023-10-01");
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals(0, result);
    }

    @Test
    public void testCase2_CountDocumentsCreatedAfterSpecificDateWithOneDocument() throws ParseException {
        // Input: Count the number of documents created after 2023-10-01
        // Setup: Create a FileSystem instance and add one document created on 2023-10-05
        Date specifiedDate = dateFormat.parse("2023-10-01");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.addDocument(doc1);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals(1, result);
    }

    @Test
    public void testCase3_CountDocumentsCreatedAfterSpecificDateWithMultipleDocuments() throws ParseException {
        // Input: Count the number of documents created after 2023-09-15
        // Setup: Create a FileSystem instance and add multiple documents
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
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals(3, result);
    }

    @Test
    public void testCase4_CountDocumentsCreatedBeforeTheSpecificDate() throws ParseException {
        // Input: Count the number of documents created after 2023-09-30
        // Setup: Create a FileSystem instance and add documents created before the specified date
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
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals(0, result);
    }

    @Test
    public void testCase5_CountDocumentsWithVariationInCreationDates() throws ParseException {
        // Input: Count the number of documents created after 2023-08-01
        // Setup: Create a FileSystem instance and add documents with various creation dates
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
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals(2, result);
    }
}