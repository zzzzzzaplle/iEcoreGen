package edu.fs.fs2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;
import edu.fs.Editor;
import edu.fs.TextEditor;
import edu.fs.ImageEditor;
import edu.fs.VideoEditor;

public class CR3Test {
    
    private FileSystem fileSystem;
    private FsFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize factory and file system using Ecore factory pattern
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountDocumentsAfterDateWithNoDocuments() throws Exception {
        // Input: Count documents created after 2023-10-01
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result
        assertEquals("No documents should be counted when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_CountDocumentsAfterDateWithOneDocument() throws Exception {
        // Create and add a document created on 2023-10-05
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10240); // 10KB in bytes
        
        fileSystem.getDocuments().add(doc1);
        
        // Input: Count documents created after 2023-10-01
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result
        assertEquals("One document created after 2023-10-01 should be counted", 1, result);
    }
    
    @Test
    public void testCase3_CountDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // Create and add multiple documents with different creation dates
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15360); // 15KB in bytes
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20480); // 20KB in bytes
        
        Document doc3 = factory.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5120); // 5KB in bytes
        
        Document doc4 = factory.createDocument();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25600); // 25KB in bytes
        
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getDocuments().add(doc4);
        
        // Input: Count documents created after 2023-09-15
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result - documents created on 2023-09-20, 2023-10-01, and 2023-10-10
        assertEquals("Three documents created after 2023-09-15 should be counted", 3, result);
    }
    
    @Test
    public void testCase4_CountDocumentsCreatedBeforeSpecificDate() throws Exception {
        // Create and add documents created before the target date
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12288); // 12KB in bytes
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30720); // 30KB in bytes
        
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        
        // Input: Count documents created after 2023-09-30
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result - no documents created after 2023-09-30
        assertEquals("No documents created after 2023-09-30 should be counted", 0, result);
    }
    
    @Test
    public void testCase5_CountDocumentsWithVariationInCreationDates() throws Exception {
        // Create and add documents with varied creation dates
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10240); // 10KB in bytes
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20480); // 20KB in bytes
        
        Document doc3 = factory.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15360); // 15KB in bytes
        
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Input: Count documents created after 2023-08-01
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify the result - documents created on 2023-08-05 and 2023-09-15
        assertEquals("Two documents created after 2023-08-01 should be counted", 2, result);
    }
}