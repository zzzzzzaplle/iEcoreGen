import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Set up editors: "Text Editor", "Image Editor", "Video Editor"
        // Create documents for Text Editor
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(fileSystem.getEditors().get(0)); // Text Editor
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(fileSystem.getEditors().get(0)); // Text Editor
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(fileSystem.getEditors().get(0)); // Text Editor
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, averages.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Set up editors: "Text Editor", "Image Editor", "Video Editor"
        // No documents associated with Image Editor
        
        // Call method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify Image Editor average = 0
        assertEquals(0.0f, averages.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Set up editors: "Text Editor", "Image Editor", "Video Editor"
        // Create documents for Video Editor
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(fileSystem.getEditors().get(2)); // Video Editor
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(fileSystem.getEditors().get(2)); // Video Editor
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(fileSystem.getEditors().get(2)); // Video Editor
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(fileSystem.getEditors().get(2)); // Video Editor
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify Video Editor average = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, averages.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Set up editors: "Text Editor", "Image Editor", "Video Editor"
        
        // Create documents for Text Editor
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor(fileSystem.getEditors().get(0)); // Text Editor
        
        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor(fileSystem.getEditors().get(0)); // Text Editor
        
        // Create documents for Image Editor
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(fileSystem.getEditors().get(1)); // Image Editor
        
        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(fileSystem.getEditors().get(1)); // Image Editor
        
        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(fileSystem.getEditors().get(1)); // Image Editor
        
        // Create document for Video Editor
        Document videoDoc1 = new Document();
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(fileSystem.getEditors().get(2)); // Video Editor
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify all editor averages
        assertEquals(150.0f, averages.get("Text Editor"), 0.001f); // (100 + 200) / 2 = 150
        assertEquals(1024.0f, averages.get("Image Editor"), 0.001f); // (1024 + 1536 + 512) / 3 = 1024
        assertEquals(2048.0f, averages.get("Video Editor"), 0.001f); // 2048
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Set up editors: "Text Editor"
        // Create 100 documents for Text Editor, each of size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + (i + 1));
            doc.setSize(10);
            doc.setEditor(fileSystem.getEditors().get(0)); // Text Editor
            fileSystem.addDocument(doc);
        }
        
        // Call method to compute average sizes
        Map<String, Float> averages = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average = (10 * 100) / 100 = 10
        assertEquals(10.0f, averages.get("Text Editor"), 0.001f);
    }
}