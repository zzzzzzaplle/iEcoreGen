package edu.fs.fs4.test;

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
        // Test Case 1: Count Documents with Mixed Editor Types
        // SetUp: Create documents with different editor types
        Document doc1 = factory.createDocument();
        doc1.setName("Report.docx");
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor1");
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getEditors().add(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Image.png");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor1");
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getEditors().add(imageEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Video.mp4");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor1");
        doc3.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(videoEditor);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Each editor type should have exactly 1 document
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // Test Case 2: Count Documents with Single Editor Type
        // SetUp: Create multiple documents with only TextEditor
        Document doc1 = factory.createDocument();
        doc1.setName("Essay.docx");
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor1");
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getEditors().add(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor); // Same TextEditor instance
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
        // Test Case 3: Count Documents after Removal
        // SetUp: Create documents with different editor types
        Document doc1 = factory.createDocument();
        doc1.setName("Image1.png");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor1");
        doc1.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getEditors().add(imageEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Video1.mp4");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor1");
        doc2.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getEditors().add(videoEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Text1.docx");
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor1");
        doc3.setEditor(textEditor);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(textEditor);
        
        // Remove the Image document
        fileSystem.getDocuments().remove(doc1);
        
        // Execute: Count documents per editor type after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: ImageEditor should have 0 documents after removal
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor should have 0 documents after removal", Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // Test Case 4: Count Documents with No Editors
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
        // Test Case 5: Count Documents with All Editors Used
        // SetUp: Create multiple documents with all editor types
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1.txt");
        TextEditor textEditor1 = factory.createTextEditor();
        textEditor1.setName("TextEditor1");
        doc1.setEditor(textEditor1);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getEditors().add(textEditor1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Pic1.jpg");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor1");
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getEditors().add(imageEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Clip1.mpg");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor1");
        doc3.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(videoEditor);
        
        Document doc4 = factory.createDocument();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor1); // Same TextEditor instance
        fileSystem.getDocuments().add(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: TextEditor should have 2 documents, others 1 each
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
}