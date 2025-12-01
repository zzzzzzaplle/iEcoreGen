package edu.fs.fs1.test;

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
        
        // Verify the result
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_CountDocumentsAfterDateWithOneDocument() throws Exception {
        // Create a document with creation date after the cutoff
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        // Add document to file system
        fileSystem.getDocuments().add(doc1);
        
        // Input: Count documents created after 2023-10-01
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result
        assertEquals("One document should be found created after the cutoff date", 1, result);
    }
    
    @Test
    public void testCase3_CountDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // Create multiple documents with different creation dates
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        
        Document doc4 = factory.createDocument();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        
        // Add all documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getDocuments().add(doc4);
        
        // Input: Count documents created after 2023-09-15
        Date cutoffDate = dateFormat.parse("2023-09-15 00:00:00");
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result - documents created on 2023-09-20, 2023-10-01, and 2023-10-10
        assertEquals("Three documents should be found created after the cutoff date", 3, result);
    }
    
    @Test
    public void testCase4_CountDocumentsCreatedBeforeSpecificDate() throws Exception {
        // Create documents with creation dates before the cutoff
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        
        // Input: Count documents created after 2023-09-30
        Date cutoffDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result - both documents are created before the cutoff date
        assertEquals("No documents should be found created after the cutoff date", 0, result);
    }
    
    @Test
    public void testCase5_CountDocumentsWithVariationInCreationDates() throws Exception {
        // Create documents with various creation dates
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Input: Count documents created after 2023-08-01
        Date cutoffDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // Execute the count operation
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result - documents created on 2023-08-05 and 2023-09-15
        assertEquals("Two documents should be found created after the cutoff date", 2, result);
    }
}