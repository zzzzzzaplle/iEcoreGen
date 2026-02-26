package edu.fs.fs4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;
import edu.fs.Editor;
import edu.fs.TextEditor;
import edu.fs.ImageEditor;
import edu.fs.VideoEditor;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize factory and file system using Ecore factory pattern
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_countDocumentsAfterDateWithNoDocuments() throws ParseException {
        // Test Case 1: Count documents created after a specific date with no documents
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws ParseException {
        // Test Case 2: Count documents created after a specific date with one document
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        
        // Create and add a document using Ecore factory
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.getDocuments().add(doc1);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result
        assertEquals("One document created after cutoff date should be found", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws ParseException {
        // Test Case 3: Count documents created after a specific date with multiple documents
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
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result - documents created on 2023-09-20, 2023-10-01, and 2023-10-10 should be counted
        assertEquals("Three documents created after cutoff date should be found", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws ParseException {
        // Test Case 4: Count documents created before the specific date
        Date cutoffDate = dateFormat.parse("2023-09-30 00:00:00");
        
        // Create and add documents using Ecore factory
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
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result - both documents are created before the cutoff date
        assertEquals("No documents created after cutoff date should be found", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws ParseException {
        // Test Case 5: Count documents with variation in creation dates
        Date cutoffDate = dateFormat.parse("2023-08-01 00:00:00");
        
        // Create and add documents using Ecore factory
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
        
        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result - documents created on 2023-08-05 and 2023-09-15 should be counted
        assertEquals("Two documents created after cutoff date should be found", 2, result);
    }
}