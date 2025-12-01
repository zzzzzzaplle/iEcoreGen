package edu.fs.fs5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import edu.fs.FsFactory;
import edu.fs.FsPackage;
import edu.fs.FileSystem;
import edu.fs.Document;
import edu.fs.Editor;
import edu.fs.TextEditor;
import edu.fs.ImageEditor;
import edu.fs.VideoEditor;

public class CR5Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory and file system
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws Exception {
        // Create documents with different editors
        Document doc1 = factory.createDocument();
        doc1.setName("Report.doc");
        doc1.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Image.png");
        doc3.setCreateDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        
        // Create editors
        TextEditor textEditor1 = factory.createTextEditor();
        textEditor1.setName("Text Editor");
        
        ImageEditor imageEditor1 = factory.createImageEditor();
        imageEditor1.setName("Image Editor");
        
        // Set editor references
        doc1.setEditor(textEditor1);
        doc2.setEditor(textEditor1);
        doc3.setEditor(imageEditor1);
        
        // Add to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(textEditor1);
        fileSystem.getEditors().add(imageEditor1);
        
        // Test: Retrieve authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor1);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
        assertFalse(authors.contains("Charlie"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // Create documents with different editors
        Document doc1 = factory.createDocument();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        
        // Create editors
        ImageEditor imageEditor1 = factory.createImageEditor();
        imageEditor1.setName("Image Editor");
        
        VideoEditor videoEditor1 = factory.createVideoEditor();
        videoEditor1.setName("Video Editor");
        
        // Set editor references
        doc1.setEditor(imageEditor1);
        doc2.setEditor(imageEditor1);
        doc3.setEditor(videoEditor1);
        
        // Add to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(imageEditor1);
        fileSystem.getEditors().add(videoEditor1);
        
        // Test: Retrieve authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor1);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
        assertFalse(authors.contains("Frank"));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // Create documents with different editors
        Document doc1 = factory.createDocument();
        doc1.setName("Document.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        
        // Create editors
        TextEditor textEditor1 = factory.createTextEditor();
        textEditor1.setName("Text Editor");
        
        ImageEditor imageEditor1 = factory.createImageEditor();
        imageEditor1.setName("Image Editor");
        
        VideoEditor videoEditor1 = factory.createVideoEditor();
        videoEditor1.setName("Video Editor");
        
        // Set editor references
        doc1.setEditor(textEditor1);
        doc2.setEditor(imageEditor1);
        
        // Add to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getEditors().add(textEditor1);
        fileSystem.getEditors().add(imageEditor1);
        fileSystem.getEditors().add(videoEditor1);
        
        // Test: Retrieve authors for Video Editor (no documents)
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor1);
        
        // Verify expected output - empty list
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // Create documents with different editors
        Document doc1 = factory.createDocument();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        
        Document doc3 = factory.createDocument();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        
        // Create editors
        TextEditor textEditor1 = factory.createTextEditor();
        textEditor1.setName("Text Editor");
        
        VideoEditor videoEditor1 = factory.createVideoEditor();
        videoEditor1.setName("Video Editor");
        
        ImageEditor imageEditor1 = factory.createImageEditor();
        imageEditor1.setName("Image Editor");
        
        // Set editor references
        doc1.setEditor(textEditor1);
        doc2.setEditor(videoEditor1);
        doc3.setEditor(imageEditor1);
        
        // Add to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(textEditor1);
        fileSystem.getEditors().add(videoEditor1);
        fileSystem.getEditors().add(imageEditor1);
        
        // Test: Retrieve authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor1);
        
        // Verify expected output
        assertEquals(1, authors.size());
        assertTrue(authors.contains("Ivy"));
        assertFalse(authors.contains("Jack"));
        assertFalse(authors.contains("Kathy"));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // Create documents with different editors
        Document doc1 = factory.createDocument();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Banner.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        
        // Create editors
        ImageEditor imageEditor1 = factory.createImageEditor();
        imageEditor1.setName("Image Editor");
        
        TextEditor textEditor1 = factory.createTextEditor();
        textEditor1.setName("Text Editor");
        
        // Set editor references
        doc1.setEditor(imageEditor1);
        doc2.setEditor(imageEditor1);
        doc3.setEditor(textEditor1);
        
        // Add to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(imageEditor1);
        fileSystem.getEditors().add(textEditor1);
        
        // Test: Retrieve authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor1);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
        assertFalse(authors.contains("Nina"));
    }
}