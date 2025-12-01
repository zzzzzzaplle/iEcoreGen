package edu.fs.fs1.test;

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
    
    private FileSystem fileSystem;
    private FsFactory factory;
    
    @Before
    public void setUp() {
        // Initialize factory and file system using Ecore factory pattern
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create documents with mixed editor types
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
        
        // Verify: Expected counts for each editor type
        assertEquals("TextEditor count should be 1", Integer.valueOf(1), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 1", Integer.valueOf(1), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create documents with only TextEditor
        Document doc1 = factory.createDocument();
        doc1.setName("Essay.docx");
        TextEditor textEditor1 = factory.createTextEditor();
        textEditor1.setName("TextEditor1");
        doc1.setEditor(textEditor1);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getEditors().add(textEditor1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Notes.txt");
        TextEditor textEditor2 = factory.createTextEditor();
        textEditor2.setName("TextEditor2");
        doc2.setEditor(textEditor2);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getEditors().add(textEditor2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Only TextEditor has documents
        assertEquals("TextEditor count should be 2", Integer.valueOf(2), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 0", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 0", Integer.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create documents with different editors
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
        
        // Remove the image document
        fileSystem.getDocuments().remove(doc1);
        
        // Execute: Count documents per editor type after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: ImageEditor count should be 0 after removal
        assertEquals("TextEditor count should be 1", Integer.valueOf(1), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 0", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: FileSystem with no documents (already set up in @Before)
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: All counts should be 0 when no documents exist
        assertEquals("TextEditor count should be 0", Integer.valueOf(0), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 0", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 0", Integer.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create multiple documents using all editor types
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
        TextEditor textEditor2 = factory.createTextEditor();
        textEditor2.setName("TextEditor2");
        doc4.setEditor(textEditor2);
        fileSystem.getDocuments().add(doc4);
        fileSystem.getEditors().add(textEditor2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Multiple documents for TextEditor, single for others
        assertEquals("TextEditor count should be 2", Integer.valueOf(2), result.get("Text Editor"));
        assertEquals("ImageEditor count should be 1", Integer.valueOf(1), result.get("Image Editor"));
        assertEquals("VideoEditor count should be 1", Integer.valueOf(1), result.get("Video Editor"));
    }
}