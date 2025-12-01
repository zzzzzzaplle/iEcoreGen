package edu.fs.fs1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;
import java.util.List;
import org.eclipse.emf.common.util.EList;

public class CR1Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Use Ecore factory pattern to create instances
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create documents with different sizes
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
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 100 + 200 + 300 = 600
        assertEquals("Total size should be 600 for three documents", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create documents and then remove one
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
        
        // Calculate total size after removal
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 250 KB (only Document2 remains)
        assertEquals("Total size should be 250 after removing Document1", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: FileSystem instance with no documents added (already empty from setUp)
        
        // Calculate total size of empty file system
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 0 KB
        assertEquals("Total size should be 0 for empty file system", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create documents with mixed sizes
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
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 50 + 1000 + 250 = 1300 KB
        assertEquals("Total size should be 1300 for mixed size documents", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create multiple documents and then remove some
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
        
        // Remove "Image" (300 KB) and "Report" (400 KB)
        fileSystem.getDocuments().remove(image);
        fileSystem.getDocuments().remove(report);
        
        // Calculate total size after multiple removals
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 700 KB (only Video remains)
        assertEquals("Total size should be 700 after removing Image and Report", 700, totalSize);
    }
}