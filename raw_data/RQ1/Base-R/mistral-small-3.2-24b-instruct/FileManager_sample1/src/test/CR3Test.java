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
        // Set up: FileSystem contains no documents
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute the method
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result
        assertEquals("Total documents created after 2023-10-01 should be 0", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // Set up: Add one document created after the target date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10 * 1024); // 10KB
        
        // Create and assign an editor
        Editor editor = new Editor();
        editor.setName("Text Editor");
        doc1.setEditor(editor);
        
        fileSystem.addDocument(doc1);
        
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute the method
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result
        assertEquals("Total documents created after 2023-10-01 should be 1", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // Set up: Add multiple documents with different creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15 * 1024); // 15KB
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20 * 1024); // 20KB
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5 * 1024); // 5KB
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreationDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25 * 1024); // 25KB
        
        // Create and assign editors
        Editor editor1 = new Editor();
        editor1.setName("Text Editor");
        doc1.setEditor(editor1);
        doc2.setEditor(editor1);
        
        Editor editor2 = new Editor();
        editor2.setName("Image Editor");
        doc3.setEditor(editor2);
        doc4.setEditor(editor2);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        
        // Execute the method
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result
        assertEquals("Total documents created after 2023-09-15 should be 3", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // Set up: Add documents created before the target date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12 * 1024); // 12KB
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30 * 1024); // 30KB
        
        // Create and assign editors
        Editor editor = new Editor();
        editor.setName("Text Editor");
        doc1.setEditor(editor);
        doc2.setEditor(editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // Execute the method
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result
        assertEquals("Total documents created after 2023-09-30 should be 0", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // Set up: Add documents with variation in creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10 * 1024); // 10KB
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20 * 1024); // 20KB
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15 * 1024); // 15KB
        
        // Create and assign editors
        Editor editor = new Editor();
        editor.setName("Text Editor");
        doc1.setEditor(editor);
        doc2.setEditor(editor);
        doc3.setEditor(editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // Execute the method
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result
        assertEquals("Total documents created after 2023-08-01 should be 2", 2, result);
    }
}