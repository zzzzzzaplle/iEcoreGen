package edu.fs.fs5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.fs.*;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = FsFactory.eINSTANCE.createFileSystem();
        
        // Create editors
        textEditor = FsFactory.eINSTANCE.createTextEditor();
        textEditor.setName("TextEditor");
        fileSystem.getEditors().add(textEditor);
        
        imageEditor = FsFactory.eINSTANCE.createImageEditor();
        imageEditor.setName("ImageEditor");
        fileSystem.getEditors().add(imageEditor);
        
        videoEditor = FsFactory.eINSTANCE.createVideoEditor();
        videoEditor.setName("VideoEditor");
        fileSystem.getEditors().add(videoEditor);
    }
    
    @Test
    public void testCase1_countDocumentsWithMixedEditorTypes() throws ParseException {
        // SetUp: 
        // 1. Create a FileSystem instance. (Done in setUp)
        // 2. Add a document named "Report.docx" using TextEditor.
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Report.docx");
        doc1.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(1024);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        // 3. Add a document named "Image.png" using ImageEditor.
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Image.png");
        doc2.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(2048);
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        
        // 4. Add a document named "Video.mp4" using VideoEditor.
        Document doc3 = FsFactory.eINSTANCE.createDocument();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(4096);
        doc3.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc3);
        
        // Execute the operation
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
    public void testCase2_countDocumentsWithSingleEditorType() throws ParseException {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in setUp)
        // 2. Add a document named "Essay.docx" using TextEditor.
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Essay.docx");
        doc1.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(1024);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        // 3. Add a document named "Notes.txt" using TextEditor.
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Notes.txt");
        doc2.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(512);
        doc2.setEditor(textEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Execute the operation
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
    public void testCase3_countDocumentsAfterRemoval() throws ParseException {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in setUp)
        // 2. Add a document named "Image1.png" using ImageEditor.
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Image1.png");
        doc1.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(2048);
        doc1.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc1);
        
        // 3. Add a document named "Video1.mp4" using VideoEditor.
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Video1.mp4");
        doc2.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(4096);
        doc2.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc2);
        
        // 4. Add a document named "Text1.docx" using TextEditor.
        Document doc3 = FsFactory.eINSTANCE.createDocument();
        doc3.setName("Text1.docx");
        doc3.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(1024);
        doc3.setEditor(textEditor);
        fileSystem.getDocuments().add(doc3);
        
        // 5. Remove the document "Image1.png".
        imageEditor.getDocuments().remove(doc1);
        
        // Execute the operation
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
        // (No documents added)
        
        // Execute the operation
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
    public void testCase5_countDocumentsWithAllEditorsUsed() throws ParseException {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in setUp)
        // 2. Add three documents: "Doc1.txt" with TextEditor, "Pic1.jpg" with ImageEditor, "Clip1.mpg" with VideoEditor.
        Document doc1 = FsFactory.eINSTANCE.createDocument();
        doc1.setName("Doc1.txt");
        doc1.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(1024);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        Document doc2 = FsFactory.eINSTANCE.createDocument();
        doc2.setName("Pic1.jpg");
        doc2.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-02 11:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(2048);
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        
        Document doc3 = FsFactory.eINSTANCE.createDocument();
        doc3.setName("Clip1.mpg");
        doc3.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-03 12:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(4096);
        doc3.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc3);
        
        // 3. Add another document "Doc2.txt" with TextEditor.
        Document doc4 = FsFactory.eINSTANCE.createDocument();
        doc4.setName("Doc2.txt");
        doc4.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-01-04 13:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(512);
        doc4.setEditor(textEditor);
        fileSystem.getDocuments().add(doc4);
        
        // Execute the operation
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