package edu.fs.fs1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Create a FileSystem instance using the factory
        fileSystem = FsFactory.eINSTANCE.createFileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_CountDocumentsCreatedAfterSpecificDateWithNoDocuments() throws ParseException {
        // Input: Count the number of documents created after 2023-10-01
        Date specifiedDate = dateFormat.parse("2023-10-01");
        
        // Setup: FileSystem contains no documents (already empty by default)
        
        // Execute the operation
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_CountDocumentsCreatedAfterSpecificDateWithOneDocument() throws ParseException {
        // Input: Count the number of documents created after 2023-10-01
        Date specifiedDate = dateFormat.parse("2023-10-01");
        
        // Setup: Add a document named "Doc1" created on 2023-10-05, author "Author1", size 10KB
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.getDocuments().add(doc1);
        
        // Execute the operation
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_CountDocumentsCreatedAfterSpecificDateWithMultipleDocuments() throws ParseException {
        // Input: Count the number of documents created after 2023-09-15
        Date specifiedDate = dateFormat.parse("2023-09-15");
        
        // Setup:
        // 1. Add a document named "Doc1" created on 2023-09-10, author "Author1", size 15KB
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        fileSystem.getDocuments().add(doc1);
        
        // 2. Add a document named "Doc2" created on 2023-09-20, author "Author2", size 20KB
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.getDocuments().add(doc2);
        
        // 3. Add a document named "Doc3" created on 2023-10-01, author "Author3", size 5KB
        Document doc3 = FsFactory.eINSTANCE.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        fileSystem.getDocuments().add(doc3);
        
        // 4. Add a document named "Doc4" created on 2023-10-10, author "Author4", size 25KB
        Document doc4 = FsFactory.eINSTANCE.createDocument();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        fileSystem.getDocuments().add(doc4);
        
        // Execute the operation
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_CountDocumentsCreatedBeforeTheSpecificDate() throws ParseException {
        // Input: Count the number of documents created after 2023-09-30
        Date specifiedDate = dateFormat.parse("2023-09-30");
        
        // Setup:
        // 1. Add a document named "Doc1" created on 2023-09-28, author "Author1", size 12KB
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        fileSystem.getDocuments().add(doc1);
        
        // 2. Add a document named "Doc2" created on 2023-08-15, author "Author2", size 30KB
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        fileSystem.getDocuments().add(doc2);
        
        // Execute the operation
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_CountDocumentsWithVariationInCreationDates() throws ParseException {
        // Input: Count the number of documents created after 2023-08-01
        Date specifiedDate = dateFormat.parse("2023-08-01");
        
        // Setup:
        // 1. Add a document named "Doc1" created on 2023-07-30, author "Author1", size 10KB
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.getDocuments().add(doc1);
        
        // 2. Add a document named "Doc2" created on 2023-08-05, author "Author2", size 20KB
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.getDocuments().add(doc2);
        
        // 3. Add a document named "Doc3" created on 2023-09-15, author "Author3", size 15KB
        Document doc3 = FsFactory.eINSTANCE.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        fileSystem.getDocuments().add(doc3);
        
        // Execute the operation
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals(2, result);
    }
}