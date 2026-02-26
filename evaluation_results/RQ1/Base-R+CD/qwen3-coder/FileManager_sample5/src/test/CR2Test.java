import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR2Test {
    
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() throws ParseException {
        // Setup: Create documents for Text Editor
        Document doc1 = createDocument("Report", 150, textEditor, "Author1");
        Document doc2 = createDocument("Essay", 200, textEditor, "Author2");
        Document doc3 = createDocument("Proposal", 250, textEditor, "Author3");
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Calculate average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Text Editor should have average of 200
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() throws ParseException {
        // Setup: Add documents only for Text and Video editors
        Document doc1 = createDocument("Report", 150, textEditor, "Author1");
        Document doc2 = createDocument("Video1", 500, videoEditor, "Author2");
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Calculate average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Image Editor should have average of 0
        assertEquals(0.0f, result.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() throws ParseException {
        // Setup: Create documents for Video Editor
        Document doc1 = createDocument("Video1", 500, videoEditor, "Author1");
        Document doc2 = createDocument("Video2", 1000, videoEditor, "Author2");
        Document doc3 = createDocument("Video3", 750, videoEditor, "Author3");
        Document doc4 = createDocument("Video4", 300, videoEditor, "Author4");
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Calculate average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Video Editor should have average of 637.5
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() throws ParseException {
        // Setup: Create documents for all editors
        // Text Editor: 2 documents (100, 200)
        Document textDoc1 = createDocument("Text1", 100, textEditor, "Author1");
        Document textDoc2 = createDocument("Text2", 200, textEditor, "Author2");
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Document imageDoc1 = createDocument("Image1", 1024, imageEditor, "Author3");
        Document imageDoc2 = createDocument("Image2", 1536, imageEditor, "Author4");
        Document imageDoc3 = createDocument("Image3", 512, imageEditor, "Author5");
        
        // Video Editor: 1 document (2048)
        Document videoDoc1 = createDocument("Video1", 2048, videoEditor, "Author6");
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Execute: Calculate average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify expected averages
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);     // (100 + 200) / 2
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f);   // (1024 + 1536 + 512) / 3
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f);   // 2048 / 1
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() throws ParseException {
        // Setup: Create 100 documents for Text Editor, each with size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = createDocument("Document" + i, 10, textEditor, "Author");
            fileSystem.addDocument(doc);
        }
        
        // Execute: Calculate average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify: Text Editor should have average of 10
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
    }
    
    // Helper method to create documents with consistent date formatting
    private Document createDocument(String name, int size, Editor editor, String author) throws ParseException {
        Document doc = new Document();
        doc.setName(name);
        doc.setSize(size);
        doc.setEditor(editor);
        doc.setAuthor(author);
        
        // Set a fixed creation date for consistency
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            doc.setCreateDate(sdf.parse("2023-01-01 12:00:00"));
        } catch (ParseException e) {
            // Default to current date if parsing fails
            doc.setCreateDate(new Date());
        }
        
        return doc;
    }
}