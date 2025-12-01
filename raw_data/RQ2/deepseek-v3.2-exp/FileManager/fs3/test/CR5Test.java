package edu.fs.fs3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import edu.fs.FsFactory;
import edu.fs.FsPackage;
import edu.fs.FileSystem;
import edu.fs.Document;
import edu.fs.TextEditor;
import edu.fs.ImageEditor;
import edu.fs.VideoEditor;
import org.eclipse.emf.common.util.EList;

public class CR5Test {
    
    private FsFactory factory;
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize the factory and file system using Ecore factory pattern
        factory = FsFactory.eINSTANCE;
        fileSystem = factory.createFileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RetrieveAuthorsForTextEditorDocuments() throws Exception {
        // SetUp: Create file system with documents
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
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        // Set editor references
        doc1.setEditor(textEditor);
        doc2.setEditor(textEditor);
        doc3.setEditor(imageEditor);
        
        // Add documents and editors to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        
        // Input: Get authors for documents using "Text Editor"
        EList<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Expected Output: Authors = ["Alice", "Bob"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Alice"));
        assertTrue(authors.contains("Bob"));
        assertFalse(authors.contains("Charlie"));
    }
    
    @Test
    public void testCase2_RetrieveAuthorsForImageEditorDocuments() throws Exception {
        // SetUp: Create file system with documents
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
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        // Set editor references
        doc1.setEditor(imageEditor);
        doc2.setEditor(imageEditor);
        doc3.setEditor(videoEditor);
        
        // Add documents and editors to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Input: Get authors for documents using "Image Editor"
        EList<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Expected Output: Authors = ["Dave", "Eve"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Dave"));
        assertTrue(authors.contains("Eve"));
        assertFalse(authors.contains("Frank"));
    }
    
    @Test
    public void testCase3_NoAuthorsForSpecificEditor() throws Exception {
        // SetUp: Create file system with documents
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
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        // Set editor references
        doc1.setEditor(textEditor);
        doc2.setEditor(imageEditor);
        
        // Add documents and editors to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
        
        // Input: Get authors for documents using "Video Editor"
        EList<String> authors = fileSystem.getAuthorsByEditor(videoEditor);
        
        // Expected Output: Authors = []
        assertTrue(authors.isEmpty());
    }
    
    @Test
    public void testCase4_RetrievingAuthorsFromMixedEditorDocuments() throws Exception {
        // SetUp: Create file system with documents
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
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        
        VideoEditor videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        // Set editor references
        doc1.setEditor(textEditor);
        doc2.setEditor(videoEditor);
        doc3.setEditor(imageEditor);
        
        // Add documents and editors to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(videoEditor);
        fileSystem.getEditors().add(imageEditor);
        
        // Input: Get authors for documents using "Text Editor"
        EList<String> authors = fileSystem.getAuthorsByEditor(textEditor);
        
        // Expected Output: Authors = ["Ivy"]
        assertEquals(1, authors.size());
        assertTrue(authors.contains("Ivy"));
        assertFalse(authors.contains("Jack"));
        assertFalse(authors.contains("Kathy"));
    }
    
    @Test
    public void testCase5_MultipleAuthorsForSingleDocumentType() throws Exception {
        // SetUp: Create file system with documents
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
        ImageEditor imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        TextEditor textEditor = factory.createTextEditor();
        textEditor.setName("Text Editor");
        
        // Set editor references
        doc1.setEditor(imageEditor);
        doc2.setEditor(imageEditor);
        doc3.setEditor(textEditor);
        
        // Add documents and editors to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(textEditor);
        
        // Input: Get authors for documents using "Image Editor"
        EList<String> authors = fileSystem.getAuthorsByEditor(imageEditor);
        
        // Expected Output: Authors = ["Leo", "Mona"]
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Leo"));
        assertTrue(authors.contains("Mona"));
        assertFalse(authors.contains("Nina"));
    }
}