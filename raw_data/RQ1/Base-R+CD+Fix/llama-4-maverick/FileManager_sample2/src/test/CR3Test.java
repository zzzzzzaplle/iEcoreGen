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
        // SetUp: Create a FileSystem instance with no documents
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute: Count documents created after 2023-10-01
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be counted when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // SetUp: Create a FileSystem instance and add one document
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        Date docDate = dateFormat.parse("2023-10-05 00:00:00");
        
        Editor editor = new TextEditor("Text Editor");
        Document doc = new Document("Doc1", docDate, "Author1", 10, editor);
        fileSystem.addDocument(doc);
        
        // Execute: Count documents created after 2023-10-01
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Total documents created after 2023-10-01 = 1
        assertEquals("One document created after target date should be counted", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // SetUp: Create a FileSystem instance and add multiple documents
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        
        Editor editor = new TextEditor("Text Editor");
        
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-09-10 00:00:00"), "Author1", 15, editor);
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-09-20 00:00:00"), "Author2", 20, editor);
        Document doc3 = new Document("Doc3", dateFormat.parse("2023-10-01 00:00:00"), "Author3", 5, editor);
        Document doc4 = new Document("Doc4", dateFormat.parse("2023-10-10 00:00:00"), "Author4", 25, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents created after 2023-09-15
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Total documents created after 2023-09-15 = 3
        assertEquals("Three documents created after target date should be counted", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // SetUp: Create a FileSystem instance with documents created before target date
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        
        Editor editor = new TextEditor("Text Editor");
        
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-09-28 00:00:00"), "Author1", 12, editor);
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-08-15 00:00:00"), "Author2", 30, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents created after 2023-09-30
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Total documents created after 2023-09-30 = 0
        assertEquals("No documents created after target date should be counted", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // SetUp: Create a FileSystem instance with documents having varied creation dates
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        
        Editor editor = new TextEditor("Text Editor");
        
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-07-30 00:00:00"), "Author1", 10, editor);
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-08-05 00:00:00"), "Author2", 20, editor);
        Document doc3 = new Document("Doc3", dateFormat.parse("2023-09-15 00:00:00"), "Author3", 15, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents created after 2023-08-01
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Total documents created after 2023-08-01 = 2
        assertEquals("Two documents created after target date should be counted", 2, result);
    }
}