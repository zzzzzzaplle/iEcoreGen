import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR3Test {
    
    private FileSystem fileSystem;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsCreatedAfterSpecificDateWithNoDocuments() {
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. The FileSystem contains no documents.
        LocalDateTime date = LocalDateTime.parse("2023-10-01 00:00:00", formatter);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        int result = fileSystem.countDocumentsCreatedAfter(date);
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterSpecificDateWithOneDocument() {
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-10-05, author "Author1", size 10KB.
        LocalDateTime date = LocalDateTime.parse("2023-10-01 00:00:00", formatter);
        LocalDateTime docDate = LocalDateTime.parse("2023-10-05 00:00:00", formatter);
        Document doc1 = new Document("Doc1", docDate, "Author1", 10);
        fileSystem.addDocument(doc1);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        int result = fileSystem.countDocumentsCreatedAfter(date);
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterSpecificDateWithMultipleDocuments() {
        // Input: Count the number of documents created after 2023-09-15
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-09-10, author "Author1", size 15KB.
        // 3. Add a document named "Doc2" created on 2023-09-20, author "Author2", size 20KB.
        // 4. Add a document named "Doc3" created on 2023-10-01, author "Author3", size 5KB.
        // 5. Add a document named "Doc4" created on 2023-10-10, author "Author4", size 25KB.
        LocalDateTime date = LocalDateTime.parse("2023-09-15 00:00:00", formatter);
        
        Document doc1 = new Document("Doc1", LocalDateTime.parse("2023-09-10 00:00:00", formatter), "Author1", 15);
        Document doc2 = new Document("Doc2", LocalDateTime.parse("2023-09-20 00:00:00", formatter), "Author2", 20);
        Document doc3 = new Document("Doc3", LocalDateTime.parse("2023-10-01 00:00:00", formatter), "Author3", 5);
        Document doc4 = new Document("Doc4", LocalDateTime.parse("2023-10-10 00:00:00", formatter), "Author4", 25);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        int result = fileSystem.countDocumentsCreatedAfter(date);
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeTheSpecificDate() {
        // Input: Count the number of documents created after 2023-09-30
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-09-28, author "Author1", size 12KB.
        // 3. Add a document named "Doc2" created on 2023-08-15, author "Author2", size 30KB.
        LocalDateTime date = LocalDateTime.parse("2023-09-30 00:00:00", formatter);
        
        Document doc1 = new Document("Doc1", LocalDateTime.parse("2023-09-28 00:00:00", formatter), "Author1", 12);
        Document doc2 = new Document("Doc2", LocalDateTime.parse("2023-08-15 00:00:00", formatter), "Author2", 30);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        int result = fileSystem.countDocumentsCreatedAfter(date);
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Input: Count the number of documents created after 2023-08-01
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-07-30, author "Author1", size 10KB.
        // 3. Add a document named "Doc2" created on 2023-08-05, author "Author2", size 20KB.
        // 4. Add a document named "Doc3" created on 2023-09-15, author "Author3", size 15KB.
        LocalDateTime date = LocalDateTime.parse("2023-08-01 00:00:00", formatter);
        
        Document doc1 = new Document("Doc1", LocalDateTime.parse("2023-07-30 00:00:00", formatter), "Author1", 10);
        Document doc2 = new Document("Doc2", LocalDateTime.parse("2023-08-05 00:00:00", formatter), "Author2", 20);
        Document doc3 = new Document("Doc3", LocalDateTime.parse("2023-09-15 00:00:00", formatter), "Author3", 15);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        int result = fileSystem.countDocumentsCreatedAfter(date);
        assertEquals(2, result);
    }
}