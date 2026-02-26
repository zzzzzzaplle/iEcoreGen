package edu.fs.fs4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.fs.Document;
import edu.fs.Editor;
import edu.fs.FileSystem;
import edu.fs.FsFactory;
import edu.fs.ImageEditor;
import edu.fs.TextEditor;
import edu.fs.VideoEditor;

import java.util.Map;

public class CR2Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Create documents for Text Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Call the method to compute average sizes
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();
        
        // Verify the result for Text Editor
        assertEquals(200.0f, averageSizes.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Associate Image Editor with no documents
        
        // Call the method to compute average sizes
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();
        
        // Verify the result for Image Editor
        assertEquals(0.0f, averageSizes.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Create documents for Video Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        
        Document doc4 = factory.createDocument();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getDocuments().add(doc4);
        
        // Call the method to compute average sizes
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();
        
        // Verify the result for Video Editor
        assertEquals(637.5f, averageSizes.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Create documents for Text Editor (sizes: 100, 200)
        Document textDoc1 = factory.createDocument();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        
        Document textDoc2 = factory.createDocument();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        
        // Create documents for Image Editor (sizes: 1024, 1536, 512)
        Document imageDoc1 = factory.createDocument();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        
        Document imageDoc2 = factory.createDocument();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        
        Document imageDoc3 = factory.createDocument();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        
        // Create document for Video Editor (size: 2048)
        Document videoDoc = factory.createDocument();
        videoDoc.setSize(2048);
        videoDoc.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(textDoc1);
        fileSystem.getDocuments().add(textDoc2);
        fileSystem.getDocuments().add(imageDoc1);
        fileSystem.getDocuments().add(imageDoc2);
        fileSystem.getDocuments().add(imageDoc3);
        fileSystem.getDocuments().add(videoDoc);
        
        // Call the method to compute average sizes
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();
        
        // Verify results for all editors
        assertEquals(150.0f, averageSizes.get("Text Editor"), 0.001f);
        assertEquals(1024.0f, averageSizes.get("Image Editor"), 0.001f);
        assertEquals(2048.0f, averageSizes.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Create editor
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        // Create 100 documents, each with size 10
        for (int i = 0; i < 100; i++) {
            Document doc = factory.createDocument();
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.getDocuments().add(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Float> averageSizes = fileSystem.findAverageSizePerEditor();
        
        // Verify the result for Text Editor
        assertEquals(10.0f, averageSizes.get("Text Editor"), 0.001f);
    }
}