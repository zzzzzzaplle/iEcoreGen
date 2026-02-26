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
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Total documents created after 2023-10-01 = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // SetUp: Create a FileSystem instance and add one document
        Date docDate = dateFormat.parse("2023-10-05 00:00:00");
        Document document = new Document("Doc1", docDate, "Author1", 10240, new Editor("Text Editor"));
        fileSystem.addDocument(document);
        
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute: Count documents created after 2023-10-01
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Total documents created after 2023-10-01 = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // SetUp: Create a FileSystem instance and add multiple documents
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-09-10 00:00:00"), "Author1", 15360, new Editor("Text Editor"));
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-09-20 00:00:00"), "Author2", 20480, new Editor("Image Editor"));
        Document doc3 = new Document("Doc3", dateFormat.parse("2023-10-01 00:00:00"), "Author3", 5120, new Editor("Video Editor"));
        Document doc4 = new Document("Doc4", dateFormat.parse("2023-10-10 00:00:00"), "Author4", 25600, new Editor("Text Editor"));
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        
        // Execute: Count documents created after 2023-09-15
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Total documents created after 2023-09-15 = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // SetUp: Create a FileSystem instance and add documents created before the target date
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-09-28 00:00:00"), "Author1", 12288, new Editor("Text Editor"));
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-08-15 00:00:00"), "Author2", 30720, new Editor("Image Editor"));
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // Execute: Count documents created after 2023-09-30
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Total documents created after 2023-09-30 = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // SetUp: Create a FileSystem instance and add documents with varied creation dates
        Document doc1 = new Document("Doc1", dateFormat.parse("2023-07-30 00:00:00"), "Author1", 10240, new Editor("Text Editor"));
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-08-05 00:00:00"), "Author2", 20480, new Editor("Image Editor"));
        Document doc3 = new Document("Doc3", dateFormat.parse("2023-09-15 00:00:00"), "Author3", 15360, new Editor("Video Editor"));
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // Execute: Count documents created after 2023-08-01
        long result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Total documents created after 2023-08-01 = 2
        assertEquals(2, result);
    }
}