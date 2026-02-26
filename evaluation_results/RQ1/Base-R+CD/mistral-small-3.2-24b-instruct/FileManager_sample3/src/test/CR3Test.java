import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

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
        
        // Verify: Expected output = 0
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // SetUp: Create FileSystem and add one document created on 2023-10-05
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        doc1.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute: Count documents created after 2023-10-01
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Expected output = 1
        assertEquals("One document created after target date should be found", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // SetUp: Create FileSystem and add multiple documents with different dates
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
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        
        // Execute: Count documents created after 2023-09-15
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Expected output = 3 (doc2, doc3, doc4)
        assertEquals("Three documents created after target date should be found", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeTargetDate() throws Exception {
        // SetUp: Create FileSystem and add documents created before target date
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
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // Execute: Count documents created after 2023-09-30
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Expected output = 0 (both documents are before target date)
        assertEquals("No documents created after target date should be found", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // SetUp: Create FileSystem and add documents with varied creation dates
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
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // Execute: Count documents created after 2023-08-01
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify: Expected output = 2 (doc2 and doc3)
        assertEquals("Two documents created after target date should be found", 2, result);
    }
}