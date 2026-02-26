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

import java.util.Date;
import java.util.Map;

public class CR4Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
        
        // Create editors
        textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        
        imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        
        videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
    }
    
    @Test
    public void testCase1_countDocumentsWithMixedEditorTypes() {
        // Setup: Add documents with different editors
        Document doc1 = factory.createDocument();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        doc1.setSize(100);
        doc1.setCreateDate(new Date());
        doc1.setAuthor("Author1");
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        doc2.setSize(200);
        doc2.setCreateDate(new Date());
        doc2.setAuthor("Author2");
        fileSystem.getDocuments().add(doc2);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        doc3.setSize(300);
        doc3.setCreateDate(new Date());
        doc3.setAuthor("Author3");
        fileSystem.getDocuments().add(doc3);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Each editor should have 1 document
        assertEquals(Integer.valueOf(1), result.get("TextEditor"));
        assertEquals(Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_countDocumentsWithSingleEditorType() {
        // Setup: Add two documents using TextEditor
        Document doc1 = factory.createDocument();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        doc1.setSize(150);
        doc1.setCreateDate(new Date());
        doc1.setAuthor("Author1");
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        doc2.setSize(75);
        doc2.setCreateDate(new Date());
        doc2.setAuthor("Author2");
        fileSystem.getDocuments().add(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: TextEditor should have 2 documents, others 0
        assertEquals(Integer.valueOf(2), result.get("TextEditor"));
        assertEquals(Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(0), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_countDocumentsAfterRemoval() {
        // Setup: Add documents
        Document doc1 = factory.createDocument();
        doc1.setName("Image1.png");
        doc1.setEditor(imageEditor);
        doc1.setSize(250);
        doc1.setCreateDate(new Date());
        doc1.setAuthor("Author1");
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Video1.mp4");
        doc2.setEditor(videoEditor);
        doc2.setSize(500);
        doc2.setCreateDate(new Date());
        doc2.setAuthor("Author2");
        fileSystem.getDocuments().add(doc2);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Text1.docx");
        doc3.setEditor(textEditor);
        doc3.setSize(120);
        doc3.setCreateDate(new Date());
        doc3.setAuthor("Author3");
        fileSystem.getDocuments().add(doc3);
        
        // Setup: Remove the image document
        imageEditor.getDocuments().remove(doc1);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: TextEditor and VideoEditor should have 1 document each, ImageEditor 0
        assertEquals(Integer.valueOf(1), result.get("TextEditor"));
        assertEquals(Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // Setup: FileSystem with no documents (already created in setUp)
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: All editors should have 0 documents
        assertEquals(Integer.valueOf(0), result.get("TextEditor"));
        assertEquals(Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(0), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() {
        // Setup: Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        fileSystem.getEditors().add(textEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        fileSystem.getEditors().add(imageEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        fileSystem.getEditors().add(videoEditor);
        
        // Setup: Add documents
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        doc1.setSize(100);
        doc1.setCreateDate(new Date());
        doc1.setAuthor("Author1");
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        doc2.setSize(300);
        doc2.setCreateDate(new Date());
        doc2.setAuthor("Author2");
        fileSystem.getDocuments().add(doc2);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        doc3.setSize(800);
        doc3.setCreateDate(new Date());
        doc3.setAuthor("Author3");
        fileSystem.getDocuments().add(doc3);
        
        Document doc4 = factory.createDocument();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        doc4.setSize(150);
        doc4.setCreateDate(new Date());
        doc4.setAuthor("Author4");
        fileSystem.getDocuments().add(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: TextEditor should have 2 documents, others 1 each
        assertEquals(Integer.valueOf(2), result.get("TextEditor"));
        assertEquals(Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
}