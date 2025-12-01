import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Date;

public class CR1Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // Test Case 1: Calculate Total Size of Multiple Documents
        // SetUp: Create FileSystem and add 3 documents with sizes 100, 200, 300 KB
        Editor textEditor = new TextEditor("Default Editor");
        
        Document doc1 = new Document("Document1", new Date(), "Author1", 100, textEditor);
        Document doc2 = new Document("Document2", new Date(), "Author2", 200, textEditor);
        Document doc3 = new Document("Document3", new Date(), "Author3", 300, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 600 KB for documents with sizes 100, 200, 300", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // Test Case 2: Calculate Total Size after Document Removal
        // SetUp: Create FileSystem, add 2 documents, then remove one
        Editor textEditor = new TextEditor("Default Editor");
        
        Document doc1 = new Document("Document1", new Date(), "Author1", 150, textEditor);
        Document doc2 = new Document("Document2", new Date(), "Author2", 250, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 250 KB after removing 150 KB document", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // Test Case 3: Empty FileSystem Calculation
        // SetUp: Create FileSystem with no documents
        // FileSystem is already empty from @Before setup
        
        // Calculate total size and verify expected output is 0
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 0 for empty file system", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // Test Case 4: Calculate Total Size of Documents with Mixed Sizes
        // SetUp: Create FileSystem and add 3 documents with mixed sizes
        Editor textEditor = new TextEditor("Default Editor");
        
        Document docA = new Document("DocA", new Date(), "AuthorA", 50, textEditor);
        Document docB = new Document("DocB", new Date(), "AuthorB", 1000, textEditor);
        Document docC = new Document("DocC", new Date(), "AuthorC", 250, textEditor);
        
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 1300 KB for documents with sizes 50, 1000, 250", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // Test Case 5: Calculate Total Size after Multiple Removals
        // SetUp: Create FileSystem, add 3 documents, then remove 2 of them
        Editor textEditor = new TextEditor("Default Editor");
        
        Document report = new Document("Report", new Date(), "Author1", 400, textEditor);
        Document image = new Document("Image", new Date(), "Author2", 300, textEditor);
        Document video = new Document("Video", new Date(), "Author3", 700, textEditor);
        
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after removals and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 700 KB after removing 300 KB and 400 KB documents", 700, totalSize);
    }
}