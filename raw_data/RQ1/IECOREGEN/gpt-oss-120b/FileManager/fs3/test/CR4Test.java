package edu.fs.fs3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Map;

import edu.fs.FsFactory;
import edu.fs.FileSystem;
import edu.fs.Document;
import edu.fs.TextEditor;
import edu.fs.ImageEditor;
import edu.fs.VideoEditor;

public class CR4Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory and create a new file system instance
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create documents with different editor types
        Document doc1 = factory.createDocument();
        doc1.setName("Report.docx");
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        doc1.setEditor(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Image.png");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        doc2.setEditor(imageEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Video.mp4");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        doc3.setEditor(videoEditor);
        
        // Add editors to file system first (required for containment)
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Each editor type should have exactly 1 document
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create multiple documents with only TextEditor
        Document doc1 = factory.createDocument();
        doc1.setName("Essay.docx");
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        doc1.setEditor(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor); // Same editor
        
        // Add editor to file system
        fileSystem.getEditors().add(textEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Only TextEditor should have documents
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 0 documents", Integer.valueOf(0), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create documents with different editors
        Document doc1 = factory.createDocument();
        doc1.setName("Image1.png");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        doc1.setEditor(imageEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Video1.mp4");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        doc2.setEditor(videoEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Text1.docx");
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        doc3.setEditor(textEditor);
        
        // Add editors to file system
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        fileSystem.getEditors().add(textEditor);
        
        // Add all documents initially
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Remove the Image1.png document
        fileSystem.getDocuments().remove(doc1);
        
        // Execute: Count documents per editor type after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: ImageEditor should have 0 documents after removal
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: FileSystem with no documents added
        
        // Execute: Count documents per editor type on empty file system
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: All editor types should have 0 documents
        assertEquals("TextEditor should have 0 documents", Integer.valueOf(0), result.get("TextEditor"));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 0 documents", Integer.valueOf(0), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create multiple documents with all editor types
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1.txt");
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        doc1.setEditor(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Pic1.jpg");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        doc2.setEditor(imageEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Clip1.mpg");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        doc3.setEditor(videoEditor);
        
        Document doc4 = factory.createDocument();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor); // Second document with TextEditor
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Add all documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getDocuments().add(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Correct distribution of documents across editor types
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
}