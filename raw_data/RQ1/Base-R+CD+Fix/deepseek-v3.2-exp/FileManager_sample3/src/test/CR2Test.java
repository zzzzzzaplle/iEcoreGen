import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR2Test {
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Set editor names
        textEditor.setName("Text Editor");
        imageEditor.setName("Image Editor");
        videoEditor.setName("Video Editor");
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Test Case 1: Single Editor with Multiple Documents
        // Input: Calculate average document size for a single editor
        
        // SetUp
        // 1. Create editors: "Text Editor", "Image Editor", "Video Editor"
        // 2. Associate "Text Editor" with 3 documents
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
        
        // 3. Call the method to compute the average size for "Text Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = (150 + 200 + 250) / 3 = 200
        assertTrue("Result should contain TextEditor", result.containsKey("TextEditor"));
        assertEquals(200.0f, result.get("TextEditor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Test Case 2: No Documents for an Editor
        // Input: Calculate average document size for an editor with no documents
        
        // SetUp
        // 1. Create editors: "Text Editor", "Image Editor", "Video Editor"
        // 2. Associate "Image Editor" with no documents
        // (No documents associated with ImageEditor)
        
        // 3. Call the method to compute the average size for "Image Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = 0
        // Since ImageEditor has no documents, it shouldn't appear in the result
        assertFalse("ImageEditor should not be in result when no documents exist", 
                   result.containsKey("ImageEditor"));
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Test Case 3: Editor with Documents of Varying Sizes
        // Input: Calculate average document size for an editor with varying sizes
        
        // SetUp
        // 1. Create editors: "Text Editor", "Image Editor", "Video Editor"
        // 2. Associate "Video Editor" with 4 documents
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
        
        // 3. Call the method to compute the average size for "Video Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertTrue("Result should contain VideoEditor", result.containsKey("VideoEditor"));
        assertEquals(637.5f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Test Case 4: Multiple Editors with Document Distribution
        // Input: Calculate average document sizes for multiple editors with distributed documents
        
        // SetUp
        // 1. Create editors: "Text Editor", "Image Editor", "Video Editor"
        // 2. Associate each editor with documents:
        
        // "Text Editor": 2 documents: (100 , 200 )
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        
        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        
        // "Image Editor": 3 documents: (1024 , 1536 , 512 )
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        
        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        
        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        
        // "Video Editor": 1 document: (2048 )
        Document videoDoc = new Document();
        videoDoc.setSize(2048);
        videoDoc.setEditor(videoEditor);
        
        fileSystem.addDocument(videoDoc);
        
        // 3. Call the method to compute the average size for each editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: 
        // - "Text Editor" Average size = (100 + 200) / 2 = 150 
        // - "Image Editor" Average size = (1024 + 1536 + 512) / 3 = 1024
        // - "Video Editor" Average size = 2048
        
        assertTrue("Result should contain TextEditor", result.containsKey("TextEditor"));
        assertTrue("Result should contain ImageEditor", result.containsKey("ImageEditor"));
        assertTrue("Result should contain VideoEditor", result.containsKey("VideoEditor"));
        
        assertEquals(150.0f, result.get("TextEditor"), 0.001f);
        assertEquals(1024.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(2048.0f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Test Case 5: Large Number of Documents for One Editor
        // Input: Calculate average document size for editor with many documents
        
        // SetUp
        // 1. Create editors: "Text Editor"
        // 2. Associate "Text Editor" with 100 documents, each 10 in size
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // 3. Call the method to compute the average size for "Text Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size = (10 * 100) / 100 = 10
        assertTrue("Result should contain TextEditor", result.containsKey("TextEditor"));
        assertEquals(10.0f, result.get("TextEditor"), 0.001f);
    }
}