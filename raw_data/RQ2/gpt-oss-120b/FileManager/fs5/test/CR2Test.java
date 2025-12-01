package edu.fs.fs5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Date;
import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;
import edu.fs.TextEditor;
import edu.fs.ImageEditor;
import edu.fs.VideoEditor;

public class CR2Test {
    
    private FileSystem fileSystem;
    private FsFactory factory;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        // Create factory and file system using Ecore factory pattern
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
        
        // Create editors using Ecore factory pattern
        textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        
        imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        
        videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // SetUp: Associate "Text Editor" with 3 documents
        Document doc1 = factory.createDocument();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        fileSystem.getDocuments().add(doc2);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        fileSystem.getDocuments().add(doc3);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for TextEditor = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, result.get("TextEditor"), 0.001f);
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(0.0f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // SetUp: "Image Editor" has no documents associated
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for ImageEditor = 0
        assertEquals(0.0f, result.get("TextEditor"), 0.001f);
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(0.0f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // SetUp: Associate "Video Editor" with 4 documents of varying sizes
        Document doc1 = factory.createDocument();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc2);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc3);
        
        Document doc4 = factory.createDocument();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc4);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for VideoEditor = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(0.0f, result.get("TextEditor"), 0.001f);
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(637.5f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // SetUp: Associate each editor with documents as specified
        
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = factory.createDocument();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        fileSystem.getDocuments().add(textDoc1);
        
        Document textDoc2 = factory.createDocument();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        fileSystem.getDocuments().add(textDoc2);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = factory.createDocument();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        fileSystem.getDocuments().add(imageDoc1);
        
        Document imageDoc2 = factory.createDocument();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(imageDoc2);
        
        Document imageDoc3 = factory.createDocument();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        fileSystem.getDocuments().add(imageDoc3);
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = factory.createDocument();
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        fileSystem.getDocuments().add(videoDoc1);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: 
        // Text Editor Average size = (100 + 200) / 2 = 150
        // Image Editor Average size = (1024 + 1536 + 512) / 3 = 1024
        // Video Editor Average size = 2048
        assertEquals(150.0f, result.get("TextEditor"), 0.001f);
        assertEquals(1024.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(2048.0f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // SetUp: Associate "Text Editor" with 100 documents, each 10 in size
        for (int i = 0; i < 100; i++) {
            Document doc = factory.createDocument();
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.getDocuments().add(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for TextEditor = (10 * 100) / 100 = 10
        assertEquals(10.0f, result.get("TextEditor"), 0.001f);
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(0.0f, result.get("VideoEditor"), 0.001f);
    }
}