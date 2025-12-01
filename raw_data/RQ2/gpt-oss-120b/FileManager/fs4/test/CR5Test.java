package edu.fs.fs4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import edu.fs.*;

public class CR5Test {
    
    private FileSystem fileSystem;
    private FsFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory and create a new file system instance
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() {
        // SetUp: Create documents with mixed editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        fileSystem.getEditors().add(imageEditor);
        
        // Document 1: Text Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Report.doc");
        doc1.setCreateDate(createDate("2023-10-01 00:00:00"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        // Document 2: Text Editor
        Document doc2 = factory.createDocument();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(createDate("2023-10-02 00:00:00"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Document 3: Image Editor
        Document doc3 = factory.createDocument();
        doc3.setName("Image.png");
        doc3.setCreateDate(createDate("2023-10-03 00:00:00"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Text Editor documents
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
        assertEquals("Alice", authors.get(0)); // Sorted order
        assertEquals("Bob", authors.get(1));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() {
        // SetUp: Create documents with mixed editors
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        fileSystem.getEditors().add(imageEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        fileSystem.getEditors().add(videoEditor);
        
        // Document 1: Image Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(createDate("2023-09-15 00:00:00"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc1);
        
        // Document 2: Image Editor
        Document doc2 = factory.createDocument();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(createDate("2023-09-20 00:00:00"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Document 3: Video Editor
        Document doc3 = factory.createDocument();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(createDate("2023-09-25 00:00:00"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Image Editor documents
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
        assertEquals("Dave", authors.get(0)); // Sorted order
        assertEquals("Eve", authors.get(1));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() {
        // SetUp: Create documents without Video Editor
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        fileSystem.getEditors().add(imageEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        fileSystem.getEditors().add(videoEditor);
        
        // Document 1: Text Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Document.txt");
        doc1.setCreateDate(createDate("2023-10-05 00:00:00"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        // Document 2: Image Editor
        Document doc2 = factory.createDocument();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(createDate("2023-10-06 00:00:00"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Test: Get authors for Video Editor documents (none exist)
        List<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Verify expected output - empty list
        assertNotNull(authors);
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() {
        // SetUp: Create documents with mixed editors
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        fileSystem.getEditors().add(videoEditor);
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        fileSystem.getEditors().add(imageEditor);
        
        // Document 1: Text Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(createDate("2023-10-07 00:00:00"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        fileSystem.getDocuments().add(doc1);
        
        // Document 2: Video Editor
        Document doc2 = factory.createDocument();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(createDate("2023-10-08 00:00:00"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(videoEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Document 3: Image Editor
        Document doc3 = factory.createDocument();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(createDate("2023-10-09 00:00:00"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Text Editor documents
        List<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Verify expected output - only "Ivy"
        assertEquals(1, authors.size());
        assertEquals("Ivy", authors.get(0));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() {
        // SetUp: Create documents with multiple Image Editor documents
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        fileSystem.getEditors().add(imageEditor);
        
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);
        
        // Document 1: Image Editor
        Document doc1 = factory.createDocument();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(createDate("2023-09-30 00:00:00"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc1);
        
        // Document 2: Image Editor
        Document doc2 = factory.createDocument();
        doc2.setName("Banner.png");
        doc2.setCreateDate(createDate("2023-10-01 00:00:00"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        fileSystem.getDocuments().add(doc2);
        
        // Document 3: Text Editor
        Document doc3 = factory.createDocument();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(createDate("2023-10-02 00:00:00"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        fileSystem.getDocuments().add(doc3);
        
        // Test: Get authors for Image Editor documents
        List<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Verify expected output
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
        assertEquals("Leo", authors.get(0)); // Sorted order
        assertEquals("Mona", authors.get(1));
    }
    
    // Helper method to create Date objects from string
    private Date createDate(String dateString) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(dateString);
        } catch (java.text.ParseException e) {
            throw new RuntimeException("Invalid date format: " + dateString, e);
        }
    }
}