package edu.fs.fs2.test;

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
        
        Document doc2 = factory.createDocument();
        doc2.setName("Image.png");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor1");
        doc2.setEditor(imageEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Video.mp4");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor1");
        doc3.setEditor(videoEditor);
        
        // Add editors and documents to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals("TextEditor count should be 1", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor count should be 1", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("VideoEditor"));
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
        
        Document doc2 = factory.createDocument();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor); // Same editor for second document
        
        // Add editor and documents to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals("TextEditor count should be 2", Integer.valueOf(2), result.get("TextEditor"));
        assertNull("ImageEditor should not be present", result.get("ImageEditor"));
        assertNull("VideoEditor should not be present", result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // Test Case 3: Count Documents after Removal
        // SetUp: Create documents with different editors, then remove one
        Document doc1 = factory.createDocument();
        doc1.setName("Image1.png");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor1");
        doc1.setEditor(imageEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Video1.mp4");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor1");
        doc2.setEditor(videoEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Text1.docx");
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor1");
        doc3.setEditor(textEditor);
        
        // Add editors and documents to file system
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        fileSystem.getEditors().add(textEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Remove the image document
        fileSystem.getDocuments().remove(doc1);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals("TextEditor count should be 1", Integer.valueOf(1), result.get("TextEditor"));
        assertNull("ImageEditor should not be present after removal", result.get("ImageEditor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // Test Case 4: Count Documents with No Editors
        // SetUp: File system with no documents added
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output - empty map since no documents
        assertTrue("Result should be empty when no documents exist", result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // Test Case 5: Count Documents with All Editors Used
        // SetUp: Create multiple documents with all editor types, including multiple TextEditor documents
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1.txt");
        TextEditor textEditor1 = factory.createTextEditor();
        textEditor1.setName("TextEditor1");
        doc1.setEditor(textEditor1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Pic1.jpg");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor1");
        doc2.setEditor(imageEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Clip1.mpg");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor1");
        doc3.setEditor(videoEditor);
        
        Document doc4 = factory.createDocument();
        doc4.setName("Doc2.txt");
        TextEditor textEditor2 = factory.createTextEditor();
        textEditor2.setName("TextEditor2");
        doc4.setEditor(textEditor2);
        
        // Add editors and documents to file system
        fileSystem.getEditors().add(textEditor1);
        fileSystem.getEditors().add(textEditor2);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getDocuments().add(doc4);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals("TextEditor count should be 2", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor count should be 1", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("VideoEditor"));
    }
}