package edu.fs.fs2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;

public class CR1Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize factory and file system using Ecore factory pattern
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // Test Case 1: Calculate Total Size of Multiple Documents
        // SetUp: Create documents with sizes 100, 200, 300 KB
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
        assertEquals("Total size should be 600 KB", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // Test Case 2: Calculate Total Size after Document Removal
        // SetUp: Create documents with sizes 150, 250 KB, then remove one
        Document doc1 = factory.createDocument();
        doc1.setName("Document1");
        doc1.setSize(150);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Document2");
        doc2.setSize(250);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        
        // Remove Document1 from file system
        fileSystem.getDocuments().remove(doc1);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 250 KB after removal", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // Test Case 3: Empty FileSystem Calculation
        // SetUp: FileSystem with no documents
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 0 for empty file system", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // Test Case 4: Calculate Total Size of Documents with Mixed Sizes
        // SetUp: Create documents with sizes 50, 1000, 250 KB
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
        assertEquals("Total size should be 1300 KB for mixed sizes", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // Test Case 5: Calculate Total Size after Multiple Removals
        // SetUp: Create documents with sizes 400, 300, 700 KB, then remove two
        Document report = factory.createDocument();
        report.setName("Report");
        report.setSize(400);
        
        Document image = factory.createDocument();
        image.setName("Image");
        image.setSize(300);
        
        Document video = factory.createDocument();
        video.setName("Video");
        video.setSize(700);
        
        // Add all documents to file system
        fileSystem.getDocuments().add(report);
        fileSystem.getDocuments().add(image);
        fileSystem.getDocuments().add(video);
        
        // Remove "Image" and "Report" from file system
        fileSystem.getDocuments().remove(image);
        fileSystem.getDocuments().remove(report);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 700 KB after multiple removals", 700, totalSize);
    }
}