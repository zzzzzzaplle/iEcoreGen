package edu.fs.fs5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
import edu.fs.FsFactory;
import edu.fs.FsPackage;
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
        // Initialize the factory and create file system instance
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Create Text Editor
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        // Create and associate documents with Text Editor
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
        
        // Compute average sizes per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Create Image Editor with no documents
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        fileSystem.getEditors().add(imageEditor);
        
        // Compute average sizes per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Image Editor average size is 0 when no documents
        assertEquals(0.0f, result.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Create Video Editor
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        fileSystem.getEditors().add(videoEditor);
        
        // Create and associate documents with Video Editor
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
        
        // Compute average sizes per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Video Editor average size calculation
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Create all three editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        fileSystem.getEditors().add(imageEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        fileSystem.getEditors().add(videoEditor);
        
        // Create and associate documents with Text Editor
        Document textDoc1 = factory.createDocument();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        fileSystem.getDocuments().add(textDoc1);
        
        Document textDoc2 = factory.createDocument();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        fileSystem.getDocuments().add(textDoc2);
        
        // Create and associate documents with Image Editor
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
        
        // Create and associate document with Video Editor
        Document videoDoc = factory.createDocument();
        videoDoc.setSize(2048);
        videoDoc.setEditor(videoEditor);
        fileSystem.getDocuments().add(videoDoc);
        
        // Compute average sizes per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify all editor average sizes
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f);
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Create Text Editor
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        // Create 100 documents, each with size 10, associated with Text Editor
        for (int i = 0; i < 100; i++) {
            Document doc = factory.createDocument();
            doc.setName("Document" + (i + 1));
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.getDocuments().add(doc);
        }
        
        // Compute average sizes per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size with many documents
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
    }
}