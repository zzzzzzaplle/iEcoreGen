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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_countDocumentsAfterDateWithNoDocuments() throws Exception {
        // Setup: FileSystem with no documents
        Date targetDate = dateFormat.parse("2023-10-01");
        
        // Execute the method
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result
        assertEquals("With no documents, count should be 0", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // Setup: FileSystem with one document created after target date
        Editor editor = new Editor("Test Editor");
        Date docDate = dateFormat.parse("2023-10-05");
        Document doc = new Document("Doc1", docDate, "Author1", 10240, editor);
        fileSystem.addDocument(doc);
        
        Date targetDate = dateFormat.parse("2023-10-01");
        
        // Execute the method
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result
        assertEquals("With one document created after target date, count should be 1", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // Setup: FileSystem with multiple documents
        Editor editor = new Editor("Test Editor");
        
        // Document before target date
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-09-10"), "Author1", 15360, editor);
        // Document after target date
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-09-20"), "Author2", 20480, editor);
        // Document on target date (should not be counted as "after")
        Document doc3 = new Document("Doc3", dateFormat.parse("2023-10-01"), "Author3", 5120, editor);
        // Document after target date
        Document doc4 = new Document("Doc4", dateFormat.parse("2023-10-10"), "Author4", 25600, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        Date targetDate = dateFormat.parse("2023-09-15");
        
        // Execute the method
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result (doc2, doc3, doc4 should be counted)
        assertEquals("With 3 documents created after target date, count should be 3", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // Setup: FileSystem with documents created before target date
        Editor editor = new Editor("Test Editor");
        
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-09-28"), "Author1", 12288, editor);
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-08-15"), "Author2", 30720, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        Date targetDate = dateFormat.parse("2023-09-30");
        
        // Execute the method
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result (no documents after target date)
        assertEquals("With no documents created after target date, count should be 0", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // Setup: FileSystem with documents having various creation dates
        Editor editor = new Editor("Test Editor");
        
        // Document before target date
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-07-30"), "Author1", 10240, editor);
        // Document after target date
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-08-05"), "Author2", 20480, editor);
        // Document after target date
        Document doc3 = new Document("Doc3", dateFormat.parse("2023-09-15"), "Author3", 15360, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        Date targetDate = dateFormat.parse("2023-08-01");
        
        // Execute the method
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result (doc2 and doc3 should be counted)
        assertEquals("With 2 documents created after target date, count should be 2", 2, result);
    }
}