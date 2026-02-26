package edu.fs.fs3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Date;
import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;
import edu.fs.Editor;
import edu.fs.TextEditor;
import edu.fs.ImageEditor;
import edu.fs.VideoEditor;

public class CR2Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize factory and file system using Ecore factory pattern
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
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
        
        // Compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // No documents associated with Image Editor
        
        // Compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Image Editor average is 0 when no documents
        assertEquals(0.0f, result.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Create documents for Video Editor with varying sizes
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
        
        // Compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Video Editor average calculation
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Create documents for Text Editor
        Document doc1 = factory.createDocument();
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Create documents for Image Editor
        Document doc3 = factory.createDocument();
        doc3.setSize(1024);
        doc3.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc3);
        
        Document doc4 = factory.createDocument();
        doc4.setSize(1536);
        doc4.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc4);
        
        Document doc5 = factory.createDocument();
        doc5.setSize(512);
        doc5.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc5);
        
        // Create document for Video Editor
        Document doc6 = factory.createDocument();
        doc6.setSize(2048);
        doc6.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc6);
        
        // Compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify all editor averages
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f);
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Create Text Editor only
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
        
        // Compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify average calculation for large dataset
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
    }
}