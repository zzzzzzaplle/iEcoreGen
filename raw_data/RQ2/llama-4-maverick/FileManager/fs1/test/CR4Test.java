package edu.fs.fs1.test;

import edu.fs.Document;
import edu.fs.FileSystem;
import edu.fs.FsFactory;
import edu.fs.ImageEditor;
import edu.fs.TextEditor;
import edu.fs.VideoEditor;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.eclipse.emf.common.util.EMap;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR4Test {
    
    private FsFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = FsFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_countDocumentsWithMixedEditorTypes() throws ParseException {
        // Create a FileSystem instance
        FileSystem fileSystem = factory.createFileSystem();
        
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        fileSystem.getEditors().add(textEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        fileSystem.getEditors().add(imageEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        fileSystem.getEditors().add(videoEditor);
        
        // Add a document named "Report.docx" using TextEditor
        Document reportDoc = factory.createDocument();
        reportDoc.setName("Report.docx");
        reportDoc.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        reportDoc.setAuthor("Author1");
        reportDoc.setSize(1024);
        reportDoc.setEditor(textEditor);
        fileSystem.getDocuments().add(reportDoc);
        
        // Add a document named "Image.png" using ImageEditor
        Document imageDoc = factory.createDocument();
        imageDoc.setName("Image.png");
        imageDoc.setCreateDate(dateFormat.parse("2023-01-02 11:00:00"));
        imageDoc.setAuthor("Author2");
        imageDoc.setSize(2048);
        imageDoc.setEditor(imageEditor);
        fileSystem.getDocuments().add(imageDoc);
        
        // Add a document named "Video.mp4" using VideoEditor
        Document videoDoc = factory.createDocument();
        videoDoc.setName("Video.mp4");
        videoDoc.setCreateDate(dateFormat.parse("2023-01-03 12:00:00"));
        videoDoc.setAuthor("Author3");
        videoDoc.setSize(4096);
        videoDoc.setEditor(videoEditor);
        fileSystem.getDocuments().add(videoDoc);
        
        // Execute the operation
        EMap<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify the results
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(1), result.get("TextEditor"));
        assertEquals(Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_countDocumentsWithSingleEditorType() throws ParseException {
        // Create a FileSystem instance
        FileSystem fileSystem = factory.createFileSystem();
        
        // Create editor
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        fileSystem.getEditors().add(textEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        fileSystem.getEditors().add(imageEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        fileSystem.getEditors().add(videoEditor);
        
        // Add a document named "Essay.docx" using TextEditor
        Document essayDoc = factory.createDocument();
        essayDoc.setName("Essay.docx");
        essayDoc.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        essayDoc.setAuthor("Author1");
        essayDoc.setSize(1024);
        essayDoc.setEditor(textEditor);
        fileSystem.getDocuments().add(essayDoc);
        
        // Add a document named "Notes.txt" using TextEditor
        Document notesDoc = factory.createDocument();
        notesDoc.setName("Notes.txt");
        notesDoc.setCreateDate(dateFormat.parse("2023-01-02 11:00:00"));
        notesDoc.setAuthor("Author1");
        notesDoc.setSize(512);
        notesDoc.setEditor(textEditor);
        fileSystem.getDocuments().add(notesDoc);
        
        // Execute the operation
        EMap<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify the results
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(2), result.get("TextEditor"));
        assertEquals(Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(0), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_countDocumentsAfterRemoval() throws ParseException {
        // Create a FileSystem instance
        FileSystem fileSystem = factory.createFileSystem();
        
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        fileSystem.getEditors().add(textEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        fileSystem.getEditors().add(imageEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        fileSystem.getEditors().add(videoEditor);
        
        // Add a document named "Image1.png" using ImageEditor
        Document imageDoc = factory.createDocument();
        imageDoc.setName("Image1.png");
        imageDoc.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        imageDoc.setAuthor("Author1");
        imageDoc.setSize(2048);
        imageDoc.setEditor(imageEditor);
        fileSystem.getDocuments().add(imageDoc);
        
        // Add a document named "Video1.mp4" using VideoEditor
        Document videoDoc = factory.createDocument();
        videoDoc.setName("Video1.mp4");
        videoDoc.setCreateDate(dateFormat.parse("2023-01-02 11:00:00"));
        videoDoc.setAuthor("Author2");
        videoDoc.setSize(4096);
        videoDoc.setEditor(videoEditor);
        fileSystem.getDocuments().add(videoDoc);
        
        // Add a document named "Text1.docx" using TextEditor
        Document textDoc = factory.createDocument();
        textDoc.setName("Text1.docx");
        textDoc.setCreateDate(dateFormat.parse("2023-01-03 12:00:00"));
        textDoc.setAuthor("Author3");
        textDoc.setSize(1024);
        textDoc.setEditor(textEditor);
        fileSystem.getDocuments().add(textDoc);
        
        // Remove the document "Image1.png"
        imageEditor.getDocuments().remove(imageDoc);
        
        // Execute the operation
        EMap<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify the results
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(1), result.get("TextEditor"));
        assertEquals(Integer.valueOf(0), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // Create a FileSystem instance
        FileSystem fileSystem = factory.createFileSystem();
        
        // Execute the operation
        EMap<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify the results
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() throws ParseException {
        // Create a FileSystem instance
        FileSystem fileSystem = factory.createFileSystem();
        
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        fileSystem.getEditors().add(textEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        fileSystem.getEditors().add(imageEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        fileSystem.getEditors().add(videoEditor);
        
        // Add three documents: "Doc1.txt" with TextEditor, "Pic1.jpg" with ImageEditor, "Clip1.mpg" with VideoEditor
        Document doc1 = factory.createDocument();
        doc1.setName("Doc1.txt");
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(1024);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        Document pic1 = factory.createDocument();
        pic1.setName("Pic1.jpg");
        pic1.setCreateDate(dateFormat.parse("2023-01-02 11:00:00"));
        pic1.setAuthor("Author2");
        pic1.setSize(2048);
        pic1.setEditor(imageEditor);
        fileSystem.getDocuments().add(pic1);
        
        Document clip1 = factory.createDocument();
        clip1.setName("Clip1.mpg");
        clip1.setCreateDate(dateFormat.parse("2023-01-03 12:00:00"));
        clip1.setAuthor("Author3");
        clip1.setSize(4096);
        clip1.setEditor(videoEditor);
        fileSystem.getDocuments().add(clip1);
        
        // Add another document "Doc2.txt" with TextEditor
        Document doc2 = factory.createDocument();
        doc2.setName("Doc2.txt");
        doc2.setCreateDate(dateFormat.parse("2023-01-04 13:00:00"));
        doc2.setAuthor("Author1");
        doc2.setSize(512);
        doc2.setEditor(textEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Execute the operation
        EMap<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify the results
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(2), result.get("TextEditor"));
        assertEquals(Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals(Integer.valueOf(1), result.get("VideoEditor"));
    }
}