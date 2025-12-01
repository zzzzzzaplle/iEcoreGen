package edu.fs.fs3.test;

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
        // SetUp: Create file system with documents
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        
        // Document 1: Text Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Report.doc");
        doc1.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        // Document 2: Text Editor
        Document doc2 = factory.createDocument();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Document 3: Image Editor
        Document doc3 = factory.createDocument();
        doc3.setName("Image.png");
        doc3.setCreateDate(dateFormat.parse("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        assertEquals("Should have 2 authors for Text Editor", 2, authors.size());
        assertTrue("Should contain Alice", authors.contains("Alice"));
        assertTrue("Should contain Bob", authors.contains("Bob"));
        assertFalse("Should not contain Charlie", authors.contains("Charlie"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // SetUp: Create file system with documents
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Document 1: Image Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc1);
        
        // Document 2: Image Editor
        Document doc2 = factory.createDocument();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Document 3: Video Editor
        Document doc3 = factory.createDocument();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(dateFormat.parse("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        assertEquals("Should have 2 authors for Image Editor", 2, authors.size());
        assertTrue("Should contain Dave", authors.contains("Dave"));
        assertTrue("Should contain Eve", authors.contains("Eve"));
        assertFalse("Should not contain Frank", authors.contains("Frank"));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // SetUp: Create file system with documents (no Video Editor documents)
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Document 1: Text Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Document.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        // Document 2: Image Editor
        Document doc2 = factory.createDocument();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Test: Get authors for Video Editor (no documents)
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Verify expected output
        assertTrue("Should return empty list for Video Editor with no documents", authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // SetUp: Create file system with mixed editor documents
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(videoEditor);
        fileSystem.getEditors().add(imageEditor);
        
        // Document 1: Text Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(dateFormat.parse("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        // Document 2: Video Editor
        Document doc2 = factory.createDocument();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(dateFormat.parse("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Document 3: Image Editor
        Document doc3 = factory.createDocument();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(dateFormat.parse("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Text Editor
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        assertEquals("Should have 1 author for Text Editor", 1, authors.size());
        assertEquals("Should contain only Ivy", "Ivy", authors.get(0));
        assertFalse("Should not contain Jack", authors.contains("Jack"));
        assertFalse("Should not contain Kathy", authors.contains("Kathy"));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // SetUp: Create file system with multiple Image Editor documents
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(textEditor);
        
        // Document 1: Image Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(dateFormat.parse("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc1);
        
        // Document 2: Image Editor
        Document doc2 = factory.createDocument();
        doc2.setName("Banner.png");
        doc2.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Document 3: Text Editor
        Document doc3 = factory.createDocument();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(dateFormat.parse("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Image Editor
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        assertEquals("Should have 2 authors for Image Editor", 2, authors.size());
        assertTrue("Should contain Leo", authors.contains("Leo"));
        assertTrue("Should contain Mona", authors.contains("Mona"));
        assertFalse("Should not contain Nina", authors.contains("Nina"));
    }
}