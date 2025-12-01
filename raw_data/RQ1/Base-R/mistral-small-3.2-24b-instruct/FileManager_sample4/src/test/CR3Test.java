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
        
        // Execute the method under test
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-10-01 should be 0 when no documents exist", 
                     0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // Test Case 2: Count documents created after a specific date with one document
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        Date docDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Create and add document
        Editor editor = new Editor("Text Editor");
        Document document = new Document("Doc1", docDate, "Author1", 10240, editor);
        fileSystem.addDocument(document);
        
        // Execute the method under test
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-10-01 should be 1 when one document exists", 
                     1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // Test Case 3: Count documents created after a specific date with multiple documents
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        
        // Create and add documents
        Editor editor1 = new Editor("Text Editor");
        Editor editor2 = new Editor("Image Editor");
        Editor editor3 = new Editor("Video Editor");
        
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-09-10 00:00:00"), "Author1", 15360, editor1);
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-09-20 00:00:00"), "Author2", 20480, editor2);
        Document doc3 = new Document("Doc3", dateFormat.parse("2023-10-01 00:00:00"), "Author3", 5120, editor3);
        Document doc4 = new Document("Doc4", dateFormat.parse("2023-10-10 00:00:00"), "Author4", 25600, editor1);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute the method under test
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-09-15 should be 3 when multiple documents exist", 
                     3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // Test Case 4: Count documents created before the specific date
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // Create and add documents
        Editor editor1 = new Editor("Text Editor");
        Editor editor2 = new Editor("Image Editor");
        
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-09-28 00:00:00"), "Author1", 12288, editor1);
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-08-15 00:00:00"), "Author2", 30720, editor2);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the method under test
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-09-30 should be 0 when all documents are created before", 
                     0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // Test Case 5: Count documents with variation in creation dates
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // Create and add documents
        Editor editor1 = new Editor("Text Editor");
        Editor editor2 = new Editor("Image Editor");
        Editor editor3 = new Editor("Video Editor");
        
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-07-30 00:00:00"), "Author1", 10240, editor1);
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-08-05 00:00:00"), "Author2", 20480, editor2);
        Document doc3 = new Document("Doc3", dateFormat.parse("2023-09-15 00:00:00"), "Author3", 15360, editor3);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-08-01 should be 2 when documents have varied creation dates", 
                     2, result);
    }
}