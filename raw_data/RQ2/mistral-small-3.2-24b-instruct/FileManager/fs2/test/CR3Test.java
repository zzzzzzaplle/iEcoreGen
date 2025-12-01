package edu.fs.fs2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;

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
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify expected output: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be counted when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_CountDocumentsAfterDateWithOneDocument() throws Exception {
        // Input: Count documents created after 2023-10-01
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Create and add one document using Ecore factory
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.getDocuments().add(doc1);
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify expected output: Total documents created after 2023-10-01 = 1
        assertEquals("One document created after cutoff date should be counted", 1, result);
    }
    
    @Test
    public void testCase3_CountDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // Input: Count documents created after 2023-09-15
        Date cutoffDate = dateFormat.parse("2023-09-15 00:00:00");
        
        // Create and add multiple documents using Ecore factory
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.getDocuments().add(doc2);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        fileSystem.getDocuments().add(doc3);
        
        Document doc4 = factory.createDocument();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        fileSystem.getDocuments().add(doc4);
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify expected output: Total documents created after 2023-09-15 = 3
        assertEquals("Three documents created after cutoff date should be counted", 3, result);
    }
    
    @Test
    public void testCase4_CountDocumentsCreatedBeforeSpecificDate() throws Exception {
        // Input: Count documents created after 2023-09-30
        Date cutoffDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // Create and add documents with dates before cutoff using Ecore factory
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        fileSystem.getDocuments().add(doc2);
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify expected output: Total documents created after 2023-09-30 = 0
        assertEquals("No documents created after cutoff date should be counted", 0, result);
    }
    
    @Test
    public void testCase5_CountDocumentsWithVariationInCreationDates() throws Exception {
        // Input: Count documents created after 2023-08-01
        Date cutoffDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // Create and add documents with varied creation dates using Ecore factory
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.getDocuments().add(doc2);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        fileSystem.getDocuments().add(doc3);
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify expected output: Total documents created after 2023-08-01 = 2
        assertEquals("Two documents created after cutoff date should be counted", 2, result);
    }
}