package edu.fs.fs4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.fs.FsFactory;
import edu.fs.FsPackage;
import edu.fs.FileSystem;
import edu.fs.Document;
import edu.fs.TextEditor;
import edu.fs.ImageEditor;
import edu.fs.VideoEditor;
import edu.fs.Editor;

import java.util.Map;

public class CR2Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory and create file system instance
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Test Case 1: Single Editor with Multiple Documents
        // SetUp: Create Text Editor with 3 documents
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        // Create documents for Text Editor
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
        
        // Verify Text Editor average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, result.get("TextEditor"), 0.001f);
        
        // Verify other editors have 0 average (no documents)
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(0.0f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Test Case 2: No Documents for an Editor
        // SetUp: Create all three editors but only associate documents with Text and Video editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        fileSystem.getEditors().add(imageEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        fileSystem.getEditors().add(videoEditor);
        
        // Create documents for Text Editor and Video Editor only
        Document textDoc = factory.createDocument();
        textDoc.setName("TextDoc");
        textDoc.setSize(100);
        textDoc.setEditor(textEditor);
        fileSystem.getDocuments().add(textDoc);
        
        Document videoDoc = factory.createDocument();
        videoDoc.setName("VideoDoc");
        videoDoc.setSize(500);
        videoDoc.setEditor(videoEditor);
        fileSystem.getDocuments().add(videoDoc);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Image Editor has average size = 0 (no documents)
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
        
        // Verify other editors have correct averages
        assertEquals(100.0f, result.get("TextEditor"), 0.001f);
        assertEquals(500.0f, result.get("VideoEditor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Test Case 3: Editor with Documents of Varying Sizes
        // SetUp: Create Video Editor with 4 documents of varying sizes
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        fileSystem.getEditors().add(videoEditor);
        
        // Create documents for Video Editor
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
        
        // Verify Video Editor average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, result.get("VideoEditor"), 0.001f);
        
        // Verify other editors have 0 average (no documents)
        assertEquals(0.0f, result.get("TextEditor"), 0.001f);
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Test Case 4: Multiple Editors with Document Distribution
        // SetUp: Create all three editors with distributed documents
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        fileSystem.getEditors().add(imageEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        fileSystem.getEditors().add(videoEditor);
        
        // Create documents for Text Editor: 2 documents
        Document textDoc1 = factory.createDocument();
        textDoc1.setName("Text1");
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        fileSystem.getDocuments().add(textDoc1);
        
        Document textDoc2 = factory.createDocument();
        textDoc2.setName("Text2");
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        fileSystem.getDocuments().add(textDoc2);
        
        // Create documents for Image Editor: 3 documents
        Document imageDoc1 = factory.createDocument();
        imageDoc1.setName("Image1");
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        fileSystem.getDocuments().add(imageDoc1);
        
        Document imageDoc2 = factory.createDocument();
        imageDoc2.setName("Image2");
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(imageDoc2);
        
        Document imageDoc3 = factory.createDocument();
        imageDoc3.setName("Image3");
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        fileSystem.getDocuments().add(imageDoc3);
        
        // Create documents for Video Editor: 1 document
        Document videoDoc = factory.createDocument();
        videoDoc.setName("Video1");
        videoDoc.setSize(2048);
        videoDoc.setEditor(videoEditor);
        fileSystem.getDocuments().add(videoDoc);
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify all editors have correct average sizes
        assertEquals(150.0f, result.get("TextEditor"), 0.001f);        // (100 + 200) / 2 = 150
        assertEquals(1024.0f, result.get("ImageEditor"), 0.001f);     // (1024 + 1536 + 512) / 3 = 1024
        assertEquals(2048.0f, result.get("VideoEditor"), 0.001f);     // 2048 / 1 = 2048
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Test Case 5: Large Number of Documents for One Editor
        // SetUp: Create Text Editor with 100 documents, each of size 10
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        // Create 100 documents for Text Editor
        for (int i = 1; i <= 100; i++) {
            Document doc = factory.createDocument();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.getDocuments().add(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (10 * 100) / 100 = 10
        assertEquals(10.0f, result.get("TextEditor"), 0.001f);
        
        // Verify other editors have 0 average (no documents)
        assertEquals(0.0f, result.get("ImageEditor"), 0.001f);
        assertEquals(0.0f, result.get("VideoEditor"), 0.001f);
    }
}