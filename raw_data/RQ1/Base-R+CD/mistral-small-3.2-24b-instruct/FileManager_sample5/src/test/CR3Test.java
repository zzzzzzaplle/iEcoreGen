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
        // No documents added to fileSystem
        
        // Input: Count documents created after 2023-10-01
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // SetUp: Create a FileSystem instance and add one document
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        doc1.setEditor(fileSystem.getEditors().get(0)); // Text Editor
        
        fileSystem.addDocument(doc1);
        
        // Input: Count documents created after 2023-10-01
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // SetUp: Create a FileSystem instance and add multiple documents
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        doc1.setEditor(fileSystem.getEditors().get(0)); // Text Editor
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        doc2.setEditor(fileSystem.getEditors().get(1)); // Image Editor
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        doc3.setEditor(fileSystem.getEditors().get(2)); // Video Editor
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        doc4.setEditor(fileSystem.getEditors().get(0)); // Text Editor
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Input: Count documents created after 2023-09-15
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_countDocumentsBeforeSpecificDate() throws Exception {
        // SetUp: Create a FileSystem instance and add documents created before target date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        doc1.setEditor(fileSystem.getEditors().get(0)); // Text Editor
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        doc2.setEditor(fileSystem.getEditors().get(1)); // Image Editor
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Count documents created after 2023-09-30
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // SetUp: Create a FileSystem instance and add documents with variation in creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        doc1.setEditor(fileSystem.getEditors().get(0)); // Text Editor
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        doc2.setEditor(fileSystem.getEditors().get(1)); // Image Editor
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        doc3.setEditor(fileSystem.getEditors().get(2)); // Video Editor
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Count documents created after 2023-08-01
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals(2, result);
    }
}