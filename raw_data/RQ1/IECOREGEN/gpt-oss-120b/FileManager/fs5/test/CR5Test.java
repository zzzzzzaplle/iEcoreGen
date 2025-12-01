package edu.fs.fs5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import edu.fs.*;

public class CR5Test {
    
    private FileSystem fileSystem;
    private FsFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws Exception {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        
        // Create documents
        Document doc1 = factory.createDocument();
        doc1.setName("Report.doc");
        doc1.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Image.png");
        doc3.setCreateDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        
        // Add to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // Create editors
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        
        // Create documents
        Document doc1 = factory.createDocument();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(videoEditor);
        
        // Add to file system
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        
        // Create documents
        Document doc1 = factory.createDocument();
        doc1.setName("Document.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        
        // Add to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        
        // Test: Get authors for Video Editor (which doesn't exist in this setup)
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        fileSystem.getEditors().add(videoEditor);
        
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Verify expected output - empty list
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // Create editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("VideoEditor");
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        
        // Create documents
        Document doc1 = factory.createDocument();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(videoEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        
        // Add to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(videoEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        assertEquals(1, authors.size());
        assertEquals("Ivy", authors.get(0));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // Create editors
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("ImageEditor");
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("TextEditor");
        
        // Create documents
        Document doc1 = factory.createDocument();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Banner.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        
        // Add to file system
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(textEditor);
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
    }
    
  
}