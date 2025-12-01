package edu.fs.fs1.test;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.Date;
import edu.fs.FsFactory;
import edu.fs.FsPackage;
import edu.fs.FileSystem;
import edu.fs.Document;

public class CR1Test {
    
    private FileSystem fileSystem;
    private FsFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory and file system using Ecore factory pattern
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @After
    public void tearDown() {
        fileSystem = null;
        factory = null;
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // Test Case 1: "Calculate Total Size of Multiple Documents"
        // SetUp: Create FileSystem and add three documents with different sizes
        Document doc1 = factory.createDocument();
        doc1.setName("Document1");
        doc1.setSize(100);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Document2");
        doc2.setSize(200);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Document3");
        doc3.setSize(300);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 600 KB for three documents", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // Test Case 2: "Calculate Total Size after Document Removal"
        // SetUp: Create FileSystem, add two documents, then remove one
        Document doc1 = factory.createDocument();
        doc1.setName("Document1");
        doc1.setSize(150);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Document2");
        doc2.setSize(250);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        
        // Remove Document1 from the FileSystem
        fileSystem.getDocuments().remove(doc1);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 250 KB after removing one document", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // Test Case 3: "Empty FileSystem Calculation"
        // SetUp: Create FileSystem with no documents added
        // No documents added to file system
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 0 KB for empty file system", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // Test Case 4: "Calculate Total Size of Documents with Mixed Sizes"
        // SetUp: Create FileSystem and add three documents with mixed sizes
        Document docA = factory.createDocument();
        docA.setName("DocA");
        docA.setSize(50);
        
        Document docB = factory.createDocument();
        docB.setName("DocB");
        docB.setSize(1000);
        
        Document docC = factory.createDocument();
        docC.setName("DocC");
        docC.setSize(250);
        
        // Add documents to file system
        fileSystem.getDocuments().add(docA);
        fileSystem.getDocuments().add(docB);
        fileSystem.getDocuments().add(docC);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 1300 KB for mixed size documents", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // Test Case 5: "Calculate Total Size after Multiple Removals"
        // SetUp: Create FileSystem, add three documents, then remove two
        Document report = factory.createDocument();
        report.setName("Report");
        report.setSize(400);
        
        Document image = factory.createDocument();
        image.setName("Image");
        image.setSize(300);
        
        Document video = factory.createDocument();
        video.setName("Video");
        video.setSize(700);
        
        // Add documents to file system
        fileSystem.getDocuments().add(report);
        fileSystem.getDocuments().add(image);
        fileSystem.getDocuments().add(video);
        
        // Remove "Image" (300 KB) and "Report" (400 KB)
        fileSystem.getDocuments().remove(image);
        fileSystem.getDocuments().remove(report);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 700 KB after multiple removals", 700, totalSize);
    }
}