package edu.fs.fs3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;
import edu.fs.Editor;

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
    public void testCase1_CountDocumentsAfterDateWithNoDocuments() throws ParseException {
        // Input: Count documents created after 2023-10-01
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify expected output: 0 documents
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_CountDocumentsAfterDateWithOneDocument() throws ParseException {
        // Input: Count documents created after 2023-10-01
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // SetUp: Create and add one document created on 2023-10-05
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10240); // 10KB in bytes
        
        fileSystem.getDocuments().add(doc1);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify expected output: 1 document
        assertEquals("One document created after target date should be counted", 1, result);
    }
    
    @Test
    public void testCase3_CountDocumentsAfterDateWithMultipleDocuments() throws ParseException {
        // Input: Count documents created after 2023-09-15
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        
        // SetUp: Create and add multiple documents with different creation dates
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15360); // 15KB
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20480); // 20KB
        
        Document doc3 = factory.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5120); // 5KB
        
        Document doc4 = factory.createDocument();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25600); // 25KB
        
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getDocuments().add(doc4);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify expected output: 3 documents (doc2, doc3, doc4)
        assertEquals("Three documents created after target date should be counted", 3, result);
    }
    
    @Test
    public void testCase4_CountDocumentsCreatedBeforeSpecificDate() throws ParseException {
        // Input: Count documents created after 2023-09-30
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // SetUp: Create and add documents created before the target date
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12288); // 12KB
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30720); // 30KB
        
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify expected output: 0 documents
        assertEquals("No documents created after target date should be found", 0, result);
    }
    
    @Test
    public void testCase5_CountDocumentsWithVariationInCreationDates() throws ParseException {
        // Input: Count documents created after 2023-08-01
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // SetUp: Create and add documents with creation dates spanning the target date
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10240); // 10KB
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20480); // 20KB
        
        Document doc3 = factory.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15360); // 15KB
        
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(targetDate);
        
        // Verify expected output: 2 documents (doc2 and doc3)
        assertEquals("Two documents created after target date should be counted", 2, result);
    }
}