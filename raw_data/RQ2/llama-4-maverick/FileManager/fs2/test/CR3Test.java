package edu.fs.fs2.test;

import edu.fs.Document;
import edu.fs.FileSystem;
import edu.fs.FsFactory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {

    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        // Initialize the file system using the factory pattern
        fileSystem = FsFactory.eINSTANCE.createFileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void testCase1_countDocumentsCreatedAfterSpecificDateWithNoDocuments() throws ParseException {
        // Input: Count the number of documents created after 2023-10-01
        // Setup:
        // 1. Create a FileSystem instance (done in setUp)
        // 2. The FileSystem contains no documents (default state)

        Date referenceDate = dateFormat.parse("2023-10-01");

        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(referenceDate);

        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals(0, result);
    }

    @Test
    public void testCase2_countDocumentsCreatedAfterSpecificDateWithOneDocument() throws ParseException {
        // Input: Count the number of documents created after 2023-10-01
        // Setup:
        // 1. Create a FileSystem instance (done in setUp)
        // 2. Add a document named "Doc1" created on 2023-10-05, author "Author1", size 10KB

        // Create and configure the document
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);

        // Add document to file system
        fileSystem.getDocuments().add(doc1);

        Date referenceDate = dateFormat.parse("2023-10-01");

        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(referenceDate);

        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals(1, result);
    }

    @Test
    public void testCase3_countDocumentsCreatedAfterSpecificDateWithMultipleDocuments() throws ParseException {
        // Input: Count the number of documents created after 2023-09-15
        // Setup:
        // 1. Create a FileSystem instance (done in setUp)
        // 2. Add a document named "Doc1" created on 2023-09-10, author "Author1", size 15KB
        // 3. Add a document named "Doc2" created on 2023-09-20, author "Author2", size 20KB
        // 4. Add a document named "Doc3" created on 2023-10-01, author "Author3", size 5KB
        // 5. Add a document named "Doc4" created on 2023-10-10, author "Author4", size 25KB

        // Create and add Doc1
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        fileSystem.getDocuments().add(doc1);

        // Create and add Doc2
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.getDocuments().add(doc2);

        // Create and add Doc3
        Document doc3 = FsFactory.eINSTANCE.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        fileSystem.getDocuments().add(doc3);

        // Create and add Doc4
        Document doc4 = FsFactory.eINSTANCE.createDocument();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        fileSystem.getDocuments().add(doc4);

        Date referenceDate = dateFormat.parse("2023-09-15");

        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(referenceDate);

        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals(3, result);
    }

    @Test
    public void testCase4_countDocumentsCreatedBeforeTheSpecificDate() throws ParseException {
        // Input: Count the number of documents created after 2023-09-30
        // Setup:
        // 1. Create a FileSystem instance (done in setUp)
        // 2. Add a document named "Doc1" created on 2023-09-28, author "Author1", size 12KB
        // 3. Add a document named "Doc2" created on 2023-08-15, author "Author2", size 30KB

        // Create and add Doc1
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        fileSystem.getDocuments().add(doc1);

        // Create and add Doc2
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        fileSystem.getDocuments().add(doc2);

        Date referenceDate = dateFormat.parse("2023-09-30");

        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(referenceDate);

        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals(0, result);
    }

    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws ParseException {
        // Input: Count the number of documents created after 2023-08-01
        // Setup:
        // 1. Create a FileSystem instance (done in setUp)
        // 2. Add a document named "Doc1" created on 2023-07-30, author "Author1", size 10KB
        // 3. Add a document named "Doc2" created on 2023-08-05, author "Author2", size 20KB
        // 4. Add a document named "Doc3" created on 2023-09-15, author "Author3", size 15KB

        // Create and add Doc1
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.getDocuments().add(doc1);

        // Create and add Doc2
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.getDocuments().add(doc2);

        // Create and add Doc3
        Document doc3 = FsFactory.eINSTANCE.createDocument();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        fileSystem.getDocuments().add(doc3);

        Date referenceDate = dateFormat.parse("2023-08-01");

        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(referenceDate);

        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals(2, result);
    }
}