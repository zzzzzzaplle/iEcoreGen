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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_countDocumentsAfterDateWithNoDocuments() throws ParseException {
        // SetUp: Create a FileSystem instance with no documents
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute countDocumentsAfterDate method
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify expected output
        assertEquals("Total documents created after 2023-10-01 should be 0 when no documents exist", 
                     0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws ParseException {
        // SetUp: Create FileSystem and add one document created after cutoff date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        fileSystem.addDocument(doc1);
        
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute countDocumentsAfterDate method
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify expected output
        assertEquals("Total documents created after 2023-10-01 should be 1", 
                     1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws ParseException {
        // SetUp: Create FileSystem and add multiple documents with varying creation dates
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
        
        // Execute countDocumentsAfterDate method
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify expected output
        assertEquals("Total documents created after 2023-09-15 should be 3", 
                     3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws ParseException {
        // SetUp: Create FileSystem and add documents created before cutoff date
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
        
        // Execute countDocumentsAfterDate method
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify expected output
        assertEquals("Total documents created after 2023-09-30 should be 0 when all documents are created before", 
                     0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws ParseException {
        // SetUp: Create FileSystem and add documents with creation dates spanning the cutoff date
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
        
        // Execute countDocumentsAfterDate method
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify expected output
        assertEquals("Total documents created after 2023-08-01 should be 2", 
                     2, result);
    }
}