package edu.fs.fs3.test;

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
        // SetUp: 
        // 1. Create a FileSystem instance. (Done in setUp)
        // 2. Add a document named "Report.docx" using TextEditor.
        Document doc1 = factory.createDocument();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        textEditor.getDocuments().add(doc1);
        
        // 3. Add a document named "Image.png" using ImageEditor.
        Document doc2 = factory.createDocument();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        imageEditor.getDocuments().add(doc2);
        
        // 4. Add a document named "Video.mp4" using VideoEditor.
        Document doc3 = factory.createDocument();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc3);
        videoEditor.getDocuments().add(doc3);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output:
        // - TextEditor: 1
        // - ImageEditor: 1
        // - VideoEditor: 1
        assertEquals(Integer.valueOf(1), result.get("TextEditor"));
        assertEquals(Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_countDocumentsWithSingleEditorType() {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in setUp)
        // 2. Add a document named "Essay.docx" using TextEditor.
        Document doc1 = factory.createDocument();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        textEditor.getDocuments().add(doc1);
        
        // 3. Add a document named "Notes.txt" using TextEditor.
        Document doc2 = factory.createDocument();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        fileSystem.getDocuments().add(doc2);
        textEditor.getDocuments().add(doc2);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output:
        // - TextEditor: 2
        // - ImageEditor: 0
        // - VideoEditor: 0
        assertEquals(Integer.valueOf(2), result.get("TextEditor"));
        assertEquals(Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(0), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_countDocumentsAfterRemoval() {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in setUp)
        // 2. Add a document named "Image1.png" using ImageEditor.
        Document doc1 = factory.createDocument();
        doc1.setName("Image1.png");
        doc1.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc1);
        imageEditor.getDocuments().add(doc1);
        
        // 3. Add a document named "Video1.mp4" using VideoEditor.
        Document doc2 = factory.createDocument();
        doc2.setName("Video1.mp4");
        doc2.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc2);
        videoEditor.getDocuments().add(doc2);
        
        // 4. Add a document named "Text1.docx" using TextEditor.
        Document doc3 = factory.createDocument();
        doc3.setName("Text1.docx");
        doc3.setEditor(textEditor);
        fileSystem.getDocuments().add(doc3);
        textEditor.getDocuments().add(doc3);
        
        // 5. Remove the document "Image1.png".
        fileSystem.getDocuments().remove(doc1);
        imageEditor.getDocuments().remove(doc1);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output:
        // - TextEditor: 1
        // - ImageEditor: 0
        // - VideoEditor: 1
        assertEquals(Integer.valueOf(1), result.get("TextEditor"));
        assertEquals(Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in setUp)
        // Note: No documents are added to the file system
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output:
        // - TextEditor: 0
        // - ImageEditor: 0
        // - VideoEditor: 0
        assertEquals(Integer.valueOf(0), result.get("TextEditor"));
        assertEquals(Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(0), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in setUp)
        // 2. Add three documents: "Doc1.txt" with TextEditor, "Pic1.jpg" with ImageEditor, "Clip1.mpg" with VideoEditor.
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        textEditor.getDocuments().add(doc1);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        imageEditor.getDocuments().add(doc2);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc3);
        videoEditor.getDocuments().add(doc3);
        
        // 3. Add another document "Doc2.txt" with TextEditor.
        Document doc4 = factory.createDocument();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        fileSystem.getDocuments().add(doc4);
        textEditor.getDocuments().add(doc4);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Expected Output:
        // - TextEditor: 2
        // - ImageEditor: 1
        // - VideoEditor: 1
        assertEquals(Integer.valueOf(2), result.get("TextEditor"));
        assertEquals(Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
}