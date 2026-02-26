package edu.fs.fs2.test;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
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
        textEditor.setName("Text Editor");
        
        imageEditor = factory.createImageEditor();
        imageEditor.setName("Image Editor");
        
        videoEditor = factory.createVideoEditor();
        videoEditor.setName("Video Editor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
    }
    
    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date: " + dateStr, e);
        }
    }
    
    @Test
    public void testCase1_retrieveAuthorsForTextEditorDocuments() {
        // Setup: Create documents as specified
        Document doc1 = factory.createDocument();
        doc1.setName("Report.doc");
        doc1.setCreateDate(parseDate("2023-10-01"));
        doc1.setAuthor("Alice");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Essay.doc");
        doc2.setCreateDate(parseDate("2023-10-02"));
        doc2.setAuthor("Bob");
        doc2.setSize(150);
        doc2.setEditor(textEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Image.png");
        doc3.setCreateDate(parseDate("2023-10-03"));
        doc3.setAuthor("Charlie");
        doc3.setSize(200);
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Execute: Get authors for Text Editor
        List<String> authors = ((FileSystem) fileSystem).getAuthorsByEditor(textEditor);
        
        // Verify: Should contain Alice and Bob
        assertNotNull("Authors list should not be null", authors);
        assertEquals("Should have 2 authors", 2, authors.size());
        assertTrue("Should contain Alice", authors.contains("Alice"));
        assertTrue("Should contain Bob", authors.contains("Bob"));
    }
    
    @Test
    public void testCase2_retrieveAuthorsForImageEditorDocuments() {
        // Setup: Create documents as specified
        Document doc1 = factory.createDocument();
        doc1.setName("Photo.jpg");
        doc1.setCreateDate(parseDate("2023-09-15"));
        doc1.setAuthor("Dave");
        doc1.setSize(250);
        doc1.setEditor(imageEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Diagram.svg");
        doc2.setCreateDate(parseDate("2023-09-20"));
        doc2.setAuthor("Eve");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Video.mp4");
        doc3.setCreateDate(parseDate("2023-09-25"));
        doc3.setAuthor("Frank");
        doc3.setSize(500000); // 500MB in KB
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Execute: Get authors for Image Editor
        List<String> authors = ((FileSystem) fileSystem).getAuthorsByEditor(imageEditor);
        
        // Verify: Should contain Dave and Eve
        assertNotNull("Authors list should not be null", authors);
        assertEquals("Should have 2 authors", 2, authors.size());
        assertTrue("Should contain Dave", authors.contains("Dave"));
        assertTrue("Should contain Eve", authors.contains("Eve"));
    }
    
    @Test
    public void testCase3_noAuthorsForSpecificEditor() {
        // Setup: Create documents as specified
        Document doc1 = factory.createDocument();
        doc1.setName("Document.txt");
        doc1.setCreateDate(parseDate("2023-10-05"));
        doc1.setAuthor("Grace");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Drawing.png");
        doc2.setCreateDate(parseDate("2023-10-06"));
        doc2.setAuthor("Heidi");
        doc2.setSize(80);
        doc2.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        
        // Execute: Get authors for Video Editor (no documents use this editor)
        List<String> authors = ((FileSystem) fileSystem).getAuthorsByEditor(videoEditor);
        
        // Verify: Should be empty list
        assertNotNull("Authors list should not be null", authors);
        assertEquals("Should have 0 authors", 0, authors.size());
    }
    
    @Test
    public void testCase4_retrievingAuthorsFromMixedEditorDocuments() {
        // Setup: Create documents as specified
        Document doc1 = factory.createDocument();
        doc1.setName("Notes.txt");
        doc1.setCreateDate(parseDate("2023-10-07"));
        doc1.setAuthor("Ivy");
        doc1.setSize(30);
        doc1.setEditor(textEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Video.mp4");
        doc2.setCreateDate(parseDate("2023-10-08"));
        doc2.setAuthor("Jack");
        doc2.setSize(400000); // 400MB in KB
        doc2.setEditor(videoEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Image.JPG");
        doc3.setCreateDate(parseDate("2023-10-09"));
        doc3.setAuthor("Kathy");
        doc3.setSize(120);
        doc3.setEditor(imageEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Execute: Get authors for Text Editor
        List<String> authors = ((FileSystem) fileSystem).getAuthorsByEditor(textEditor);
        
        // Verify: Should contain only Ivy
        assertNotNull("Authors list should not be null", authors);
        assertEquals("Should have 1 author", 1, authors.size());
        assertTrue("Should contain Ivy", authors.contains("Ivy"));
    }
    
    @Test
    public void testCase5_multipleAuthorsForSingleDocumentType() {
        // Setup: Create documents as specified
        Document doc1 = factory.createDocument();
        doc1.setName("Portfolio.jpg");
        doc1.setCreateDate(parseDate("2023-09-30"));
        doc1.setAuthor("Leo");
        doc1.setSize(600);
        doc1.setEditor(imageEditor);
        
        Document doc2 = factory.createDocument();
        doc2.setName("Banner.png");
        doc2.setCreateDate(parseDate("2023-10-01"));
        doc2.setAuthor("Mona");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        Document doc3 = factory.createDocument();
        doc3.setName("Presentation.ppt");
        doc3.setCreateDate(parseDate("2023-10-02"));
        doc3.setAuthor("Nina");
        doc3.setSize(150);
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.getDocuments().add(doc1);
        fileSystem.getDocuments().add(doc2);
        fileSystem.getDocuments().add(doc3);
        
        // Execute: Get authors for Image Editor
        List<String> authors = ((FileSystem) fileSystem).getAuthorsByEditor(imageEditor);
        
        // Verify: Should contain Leo and Mona
        assertNotNull("Authors list should not be null", authors);
        assertEquals("Should have 2 authors", 2, authors.size());
        assertTrue("Should contain Leo", authors.contains("Leo"));
        assertTrue("Should contain Mona", authors.contains("Mona"));
    }
}