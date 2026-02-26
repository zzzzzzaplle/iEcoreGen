// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

// Mock classes to simulate the file system structure based on the test specifications
class Document {
    private String name;
    private int sizeKB;
    
    public Document(String name, int sizeKB) {
        this.name = name;
        this.sizeKB = sizeKB;
    }
    
    public String getName() {
        return name;
    }
    
    public int getSizeKB() {
        return sizeKB;
    }
}

class FileSystem {
    private Map<String, Document> documents;
    
    public FileSystem() {
        this.documents = new HashMap<>();
    }
    
    public void addDocument(Document document) {
        documents.put(document.getName(), document);
    }
    
    public void removeDocument(String name) {
        documents.remove(name);
    }
    
    public int calculateTotalDocumentSize() {
        int totalSize = 0;
        for (Document document : documents.values()) {
            totalSize += document.getSizeKB();
        }
        return totalSize;
    }
}

public class CR1Test {
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_calculateTotalSizeOfMultipleDocuments() {
        // SetUp:  
        // 1. Create a FileSystem instance.
        // (Done in @Before method)
        
        // 2. Add a Document named "Document1" with size: 100 KB.
        fileSystem.addDocument(new Document("Document1", 100));
        
        // 3. Add a Document named "Document2" with size: 200 KB.
        fileSystem.addDocument(new Document("Document2", 200));
        
        // 4. Add a Document named "Document3" with size: 300 KB.
        fileSystem.addDocument(new Document("Document3", 300));
        
        // Expected Output: Total document size = 100 + 200 + 300 = 600 KB
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_calculateTotalSizeAfterDocumentRemoval() {
        // SetUp:  
        // 1. Create a FileSystem instance.
        // (Done in @Before method)
        
        // 2. Add a Document named "Document1" with size: 150 KB.
        fileSystem.addDocument(new Document("Document1", 150));
        
        // 3. Add a Document named "Document2" with size: 250 KB.
        fileSystem.addDocument(new Document("Document2", 250));
        
        // 4. Remove Document1 from the FileSystem.
        fileSystem.removeDocument("Document1");
        
        // Expected Output: Total document size = 250 KB
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_emptyFileSystemCalculation() {
        // SetUp:  
        // 1. Create a FileSystem instance with no documents added.
        // (Done in @Before method - no documents added)
        
        // Expected Output: Total document size = 0 KB
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_calculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp:  
        // 1. Create a FileSystem instance.
        // (Done in @Before method)
        
        // 2. Add a Document named "DocA" with size: 50 KB.
        fileSystem.addDocument(new Document("DocA", 50));
        
        // 3. Add a Document named "DocB" with size: 1000 KB.
        fileSystem.addDocument(new Document("DocB", 1000));
        
        // 4. Add a Document named "DocC" with size: 250 KB.
        fileSystem.addDocument(new Document("DocC", 250));
        
        // Expected Output: Total document size = 50 + 1000 + 250 = 1300 KB
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(1300, totalSize);
    }
    
    @Test
    public void testCase5_calculateTotalSizeAfterMultipleRemovals() {
        // SetUp:  
        // 1. Create a FileSystem instance.
        // (Done in @Before method)
        
        // 2. Add Document "Report" with size: 400 KB.
        fileSystem.addDocument(new Document("Report", 400));
        
        // 3. Add Document "Image" with size: 300 KB.
        fileSystem.addDocument(new Document("Image", 300));
        
        // 4. Add Document "Video" with size: 700 KB.
        fileSystem.addDocument(new Document("Video", 700));
        
        // 5. Remove "Image" (300 KB) and "Report" (400 KB).
        fileSystem.removeDocument("Image");
        fileSystem.removeDocument("Report");
        
        // Expected Output: Total document size = 700 KB
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(700, totalSize);
    }
}