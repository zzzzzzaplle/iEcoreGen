// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

// Mock classes to simulate the file system structure
class Document {
    private String name;
    private int size;
    
    public Document(String name, int size) {
        this.name = name;
        this.size = size;
    }
    
    public String getName() {
        return name;
    }
    
    public int getSize() {
        return size;
    }
}

class Editor {
    private String name;
    private List<Document> documents;
    
    public Editor(String name) {
        this.name = name;
        this.documents = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public List<Document> getDocuments() {
        return documents;
    }
    
    public void addDocument(Document document) {
        documents.add(document);
    }
}

class FileSystem {
    private List<Editor> editors;
    
    public FileSystem() {
        this.editors = new ArrayList<>();
    }
    
    public void addEditor(Editor editor) {
        editors.add(editor);
    }
    
    public List<Editor> getEditors() {
        return editors;
    }
    
    // Method to compute average document size for each editor
    public Map<String, Double> computeAverageDocumentSizePerEditor() {
        Map<String, Double> averages = new HashMap<>();
        
        for (Editor editor : editors) {
            List<Document> documents = editor.getDocuments();
            if (documents.isEmpty()) {
                averages.put(editor.getName(), 0.0);
            } else {
                int totalSize = 0;
                for (Document document : documents) {
                    totalSize += document.getSize();
                }
                double average = (double) totalSize / documents.size();
                averages.put(editor.getName(), average);
            }
        }
        
        return averages;
    }
}

public class CR2Test {
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new Editor("Text Editor");
        imageEditor = new Editor("Image Editor");
        videoEditor = new Editor("Video Editor");
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Setup: Create editors
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
        
        // Associate "Text Editor" with 3 documents
        textEditor.addDocument(new Document("Report", 150));
        textEditor.addDocument(new Document("Essay", 200));
        textEditor.addDocument(new Document("Proposal", 250));
        
        // Call the method to compute the average size for "Text Editor"
        Map<String, Double> averages = fileSystem.computeAverageDocumentSizePerEditor();
        
        // Expected Output: Average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0, averages.get("Text Editor"), 0.001);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Setup: Create editors
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
        
        // Associate "Image Editor" with no documents
        // No documents added to imageEditor
        
        // Call the method to compute the average size for "Image Editor"
        Map<String, Double> averages = fileSystem.computeAverageDocumentSizePerEditor();
        
        // Expected Output: Average size = 0
        assertEquals(0.0, averages.get("Image Editor"), 0.001);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Setup: Create editors
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
        
        // Associate "Video Editor" with 4 documents
        videoEditor.addDocument(new Document("Video1", 500));
        videoEditor.addDocument(new Document("Video2", 1000));
        videoEditor.addDocument(new Document("Video3", 750));
        videoEditor.addDocument(new Document("Video4", 300));
        
        // Call the method to compute the average size for "Video Editor"
        Map<String, Double> averages = fileSystem.computeAverageDocumentSizePerEditor();
        
        // Expected Output: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5, averages.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Setup: Create editors
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
        
        // Associate each editor with documents
        // "Text Editor": 2 documents: (100, 200)
        textEditor.addDocument(new Document("Doc1", 100));
        textEditor.addDocument(new Document("Doc2", 200));
        
        // "Image Editor": 3 documents: (1024, 1536, 512)
        imageEditor.addDocument(new Document("Image1", 1024));
        imageEditor.addDocument(new Document("Image2", 1536));
        imageEditor.addDocument(new Document("Image3", 512));
        
        // "Video Editor": 1 document: (2048)
        videoEditor.addDocument(new Document("Video1", 2048));
        
        // Call the method to compute the average size for each editor
        Map<String, Double> averages = fileSystem.computeAverageDocumentSizePerEditor();
        
        // Expected Output:
        // "Text Editor" Average size = (100 + 200) / 2 = 150
        assertEquals(150.0, averages.get("Text Editor"), 0.001);
        
        // "Image Editor" Average size = (1024 + 1536 + 512) / 3 = 1024
        assertEquals(1024.0, averages.get("Image Editor"), 0.001);
        
        // "Video Editor" Average size = 2048
        assertEquals(2048.0, averages.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Setup: Create editors
        fileSystem.addEditor(textEditor);
        // Note: Only "Text Editor" is needed for this test
        
        // Associate "Text Editor" with 100 documents, each 10 in size
        for (int i = 1; i <= 100; i++) {
            textEditor.addDocument(new Document("Document" + i, 10));
        }
        
        // Call the method to compute the average size for "Text Editor"
        Map<String, Double> averages = fileSystem.computeAverageDocumentSizePerEditor();
        
        // Expected Output: Average size = (10 * 100) / 100 = 10
        assertEquals(10.0, averages.get("Text Editor"), 0.001);
    }
}