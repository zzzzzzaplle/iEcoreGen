import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // SetUp: Create editors and associate "Text Editor" with 3 documents
        Editor textEditor = null;
        for (Editor editor : fileSystem.getEditors()) {
            if (editor.getName().equals("Text Editor")) {
                textEditor = editor;
                break;
            }
        }
        
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for "Text Editor" = 200
        assertEquals(200.0f, averages.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: Create editors - "Image Editor" has no documents
        // Editors are already initialized in FileSystem constructor
        
        // Call the method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for "Image Editor" = 0
        assertEquals(0.0f, averages.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // SetUp: Associate "Video Editor" with 4 documents of varying sizes
        Editor videoEditor = null;
        for (Editor editor : fileSystem.getEditors()) {
            if (editor.getName().equals("Video Editor")) {
                videoEditor = editor;
                break;
            }
        }
        
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for "Video Editor" = 637.5
        assertEquals(637.5f, averages.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // SetUp: Associate each editor with documents as specified
        Editor textEditor = null;
        Editor imageEditor = null;
        Editor videoEditor = null;
        
        for (Editor editor : fileSystem.getEditors()) {
            switch (editor.getName()) {
                case "Text Editor":
                    textEditor = editor;
                    break;
                case "Image Editor":
                    imageEditor = editor;
                    break;
                case "Video Editor":
                    videoEditor = editor;
                    break;
            }
        }
        
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        fileSystem.addDocument(textDoc1);
        
        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        fileSystem.addDocument(textDoc2);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        fileSystem.addDocument(imageDoc1);
        
        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        fileSystem.addDocument(imageDoc2);
        
        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        fileSystem.addDocument(imageDoc3);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = new Document();
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        fileSystem.addDocument(videoDoc1);
        
        // Call the method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: 
        // "Text Editor" Average size = 150
        // "Image Editor" Average size = 1024
        // "Video Editor" Average size = 2048
        assertEquals(150.0f, averages.get("Text Editor"), 0.001f);
        assertEquals(1024.0f, averages.get("Image Editor"), 0.001f);
        assertEquals(2048.0f, averages.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // SetUp: Associate "Text Editor" with 100 documents, each size 10
        Editor textEditor = null;
        for (Editor editor : fileSystem.getEditors()) {
            if (editor.getName().equals("Text Editor")) {
                textEditor = editor;
                break;
            }
        }
        
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for "Text Editor" = 10
        assertEquals(10.0f, averages.get("Text Editor"), 0.001f);
    }
}