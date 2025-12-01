package edu.fs.fs2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;
import edu.fs.Editor;
import edu.fs.TextEditor;
import edu.fs.ImageEditor;
import edu.fs.VideoEditor;

import java.util.Map;
import org.eclipse.emf.common.util.EMap;

public class CR2Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
        textEditor = factory.createTextEditor();
        imageEditor = factory.createImageEditor();
        videoEditor = factory.createVideoEditor();
        
        textEditor.setName("Text Editor");
        imageEditor.setName("Image Editor");
        videoEditor.setName("Video Editor");
        
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Setup: Create 3 documents for Text Editor
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
        
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Execute: Compute average size for each editor
        EMap<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Text Editor average should be 200.0
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Setup: Image Editor has no documents
        // All editors are already created and added to file system in setUp()
        // No documents are associated with any editor
        
        // Execute: Compute average size for each editor
        EMap<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Image Editor average should be 0.0
        assertEquals(0.0f, result.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Setup: Create 4 documents for Video Editor
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
        
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getDocuments().add(doc4);
        
        // Execute: Compute average size for each editor
        EMap<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Video Editor average should be 637.5
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Setup: Text Editor with 2 documents
        Document textDoc1 = factory.createDocument();
        textDoc1.setName("Text1");
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        
        Document textDoc2 = factory.createDocument();
        textDoc2.setName("Text2");
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        
        // Setup: Image Editor with 3 documents
        Document imageDoc1 = factory.createDocument();
        imageDoc1.setName("Image1");
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        
        Document imageDoc2 = factory.createDocument();
        imageDoc2.setName("Image2");
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        
        Document imageDoc3 = factory.createDocument();
        imageDoc3.setName("Image3");
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        
        // Setup: Video Editor with 1 document
        Document videoDoc1 = factory.createDocument();
        videoDoc1.setName("Video1");
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        
        fileSystem.getDocuments().add(textDoc1);
        fileSystem.getDocuments().add(textDoc2);
        fileSystem.getDocuments().add(imageDoc1);
        fileSystem.getDocuments().add(imageDoc2);
        fileSystem.getDocuments().add(imageDoc3);
        fileSystem.getDocuments().add(videoDoc1);
        
        // Execute: Compute average size for each editor
        EMap<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Each editor's average size
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f);
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Setup: Create 100 documents for Text Editor, each with size 10
        for (int i = 0; i < 100; i++) {
            Document doc = factory.createDocument();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.getDocuments().add(doc);
        }
        
        // Execute: Compute average size for each editor
        EMap<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Text Editor average should be 10.0
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
    }
}