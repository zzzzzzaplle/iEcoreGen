import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    private Date testDate1;
    private Date testDate2;
    private Date testDate3;
    private Date testDate4;
    private Date testDate5;

    @Before
    public void setUp() throws Exception {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Initialize test dates
        testDate1 = dateFormat.parse("2023-10-01 00:00:00");
        testDate2 = dateFormat.parse("2023-09-15 00:00:00");
        testDate3 = dateFormat.parse("2023-09-30 00:00:00");
        testDate4 = dateFormat.parse("2023-08-01 00:00:00");
    }

    @Test
    public void testCase1_countDocumentsAfterDateWithNoDocuments() throws Exception {
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: FileSystem contains no documents
        
        // Execute the method
        int result = fileSystem.countDocumentsAfterDate(testDate1);
        
        // Verify the result
        assertEquals("Total documents created after 2023-10-01 should be 0", 0, result);
    }

    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: Add a document named "Doc1" created on 2023-10-05
        
        // Create and set up the document
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        doc1.setEditor(new TextEditor());
        
        // Add document to file system
        fileSystem.addDocument(doc1);
        
        // Execute the method
        int result = fileSystem.countDocumentsAfterDate(testDate1);
        
        // Verify the result
        assertEquals("Total documents created after 2023-10-01 should be 1", 1, result);
    }

    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15
        // SetUp: Add documents with various creation dates
        
        // Create and set up documents
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        doc3.setEditor(new VideoEditor());
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        doc4.setEditor(new TextEditor());
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute the method
        int result = fileSystem.countDocumentsAfterDate(testDate2);
        
        // Verify the result
        assertEquals("Total documents created after 2023-09-15 should be 3", 3, result);
    }

    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30
        // SetUp: Add documents created before 2023-09-30
        
        // Create and set up documents
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        doc2.setEditor(new ImageEditor());
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the method
        int result = fileSystem.countDocumentsAfterDate(testDate3);
        
        // Verify the result
        assertEquals("Total documents created after 2023-09-30 should be 0", 0, result);
    }

    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01
        // SetUp: Add documents with creation dates around 2023-08-01
        
        // Create and set up documents
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        doc3.setEditor(new VideoEditor());
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method
        int result = fileSystem.countDocumentsAfterDate(testDate4);
        
        // Verify the result
        assertEquals("Total documents created after 2023-08-01 should be 2", 2, result);
    }
}