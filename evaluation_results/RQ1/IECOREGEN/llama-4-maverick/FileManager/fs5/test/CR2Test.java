package edu.fs.fs5.test;

import edu.fs.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;

public class CR2Test {

    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;

    @Before
    public void setUp() {
        // Create a new file system
        fileSystem = FsFactory.eINSTANCE.createFileSystem();
        
        // Create editors
        textEditor = FsFactory.eINSTANCE.createTextEditor();
        textEditor.setName("Text Editor");
        
        imageEditor = FsFactory.eINSTANCE.createImageEditor();
        imageEditor.setName("Image Editor");
        
        videoEditor = FsFactory.eINSTANCE.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
    }

    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Setup: Associate "Text Editor" with 3 documents
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        
        Document doc3 = FsFactory.eINSTANCE.createDocument();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Execute: Call the method to compute the average size for "Text Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
    }

    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Setup: Associate "Image Editor" with no documents
        
        // Execute: Call the method to compute the average size for "Image Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Average size = 0
        assertEquals(0.0f, result.get("Image Editor"), 0.001f);
    }

    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Setup: Associate "Video Editor" with 4 documents
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        
        Document doc3 = FsFactory.eINSTANCE.createDocument();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        
        Document doc4 = FsFactory.eINSTANCE.createDocument();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getDocuments().add(doc4);
        
        // Execute: Call the method to compute the average size for "Video Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
    }

    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Setup: Associate each editor with documents
        
        // "Text Editor": 2 documents: (100, 200)
        Document textDoc1 = FsFactory.eINSTANCE.createDocument();
        textDoc1.setName("Text1");
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        
        Document textDoc2 = FsFactory.eINSTANCE.createDocument();
        textDoc2.setName("Text2");
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        
        // "Image Editor": 3 documents: (1024, 1536, 512)
        Document imageDoc1 = FsFactory.eINSTANCE.createDocument();
        imageDoc1.setName("Image1");
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        
        Document imageDoc2 = FsFactory.eINSTANCE.createDocument();
        imageDoc2.setName("Image2");
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        
        Document imageDoc3 = FsFactory.eINSTANCE.createDocument();
        imageDoc3.setName("Image3");
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        
        // "Video Editor": 1 document: (2048)
        Document videoDoc1 = FsFactory.eINSTANCE.createDocument();
        videoDoc1.setName("Video1");
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        
        // Add all documents to file system
        fileSystem.getDocuments().add(textDoc1);
        fileSystem.getDocuments().add(textDoc2);
        fileSystem.getDocuments().add(imageDoc1);
        fileSystem.getDocuments().add(imageDoc2);
        fileSystem.getDocuments().add(imageDoc3);
        fileSystem.getDocuments().add(videoDoc1);
        
        // Execute: Call the method to compute the average size for each editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify:
        // "Text Editor" Average size = (100 + 200) / 2 = 150
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);
        // "Image Editor" Average size = (1024 + 1536 + 512) / 3 = 1024
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f);
        // "Video Editor" Average size = 2048
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f);
    }

    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Setup: Associate "Text Editor" with 100 documents, each 10 in size
        for (int i = 0; i < 100; i++) {
            Document doc = FsFactory.eINSTANCE.createDocument();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.getDocuments().add(doc);
        }
        
        // Execute: Call the method to compute the average size for "Text Editor"
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Average size = (10 * 100) / 100 = 10
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
    }
}