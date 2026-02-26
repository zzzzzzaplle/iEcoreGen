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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_countDocumentsCreatedAfterWithNoDocuments() throws Exception {
        // SetUp: Create a FileSystem instance with no documents
        Date targetDate = dateFormat.parse("2023-10-01");
        
        // Execute: Count documents created after 2023-10-01
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() throws Exception {
        // SetUp: Create FileSystem and add one document created on 2023-10-05
        Date targetDate = dateFormat.parse("2023-10-01");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-10-05"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        fileSystem.addDocument(doc1);
        
        // Execute: Count documents created after 2023-10-01
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify: Total documents created after 2023-10-01 = 1
        assertEquals("Should find 1 document created after target date", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() throws Exception {
        // SetUp: Create FileSystem and add multiple documents with different dates
        Date targetDate = dateFormat.parse("2023-09-15");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-09-10"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-09-20"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(dateFormat.parse("2023-10-01"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreationDate(dateFormat.parse("2023-10-10"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents created after 2023-09-15
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify: Total documents created after 2023-09-15 = 3
        assertEquals("Should find 3 documents created after target date", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // SetUp: Create FileSystem and add documents created before target date
        Date targetDate = dateFormat.parse("2023-09-30");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-09-28"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-08-15"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents created after 2023-09-30
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify: Total documents created after 2023-09-30 = 0
        assertEquals("Should find 0 documents created after target date", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // SetUp: Create FileSystem and add documents with varying creation dates
        Date targetDate = dateFormat.parse("2023-08-01");
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-07-30"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-08-05"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(dateFormat.parse("2023-09-15"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents created after 2023-08-01
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify: Total documents created after 2023-08-01 = 2
        assertEquals("Should find 2 documents created after target date", 2, result);
    }
}