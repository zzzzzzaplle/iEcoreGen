package edu.fs.fs2.test;

import edu.fs.Document;
import edu.fs.FileSystem;
import edu.fs.FsFactory;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR1Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // Create documents with specified sizes
        Document doc1 = factory.createDocument();
        doc1.setName("Document1");
        doc1.setSize(100);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Document2");
        doc2.setSize(200);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Document3");
        doc3.setSize(300);
        
        // Add documents to the file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify the result
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // Create documents with specified sizes
        Document doc1 = factory.createDocument();
        doc1.setName("Document1");
        doc1.setSize(150);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Document2");
        doc2.setSize(250);
        
        // Add documents to the file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        
        // Remove Document1
        fileSystem.getDocuments().remove(doc1);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify the result
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // File system is already created and empty
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify the result
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // Create documents with specified sizes
        Document docA = factory.createDocument();
        docA.setName("DocA");
        docA.setSize(50);
        
        Document docB = factory.createDocument();
        docB.setName("DocB");
        docB.setSize(1000);
        
        Document docC = factory.createDocument();
        docC.setName("DocC");
        docC.setSize(250);
        
        // Add documents to the file system
        fileSystem.getDocuments().add(docA);
        fileSystem.getDocuments().add(docB);
        fileSystem.getDocuments().add(docC);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify the result
        assertEquals(1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // Create documents with specified sizes
        Document report = factory.createDocument();
        report.setName("Report");
        report.setSize(400);
        
        Document image = factory.createDocument();
        image.setName("Image");
        image.setSize(300);
        
        Document video = factory.createDocument();
        video.setName("Video");
        video.setSize(700);
        
        // Add documents to the file system
        fileSystem.getDocuments().add(report);
        fileSystem.getDocuments().add(image);
        fileSystem.getDocuments().add(video);
        
        // Remove Report and Image
        fileSystem.getDocuments().remove(report);
        fileSystem.getDocuments().remove(image);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify the result
        assertEquals(700, totalSize);
    }
}