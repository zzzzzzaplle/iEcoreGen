import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private FileSystem fileSystem;
    private Document doc1, doc2, doc3;
    private Editor textEditor, imageEditor, videoEditor;
    
    @Before
    public void setUp() {
        // Initialize editors
        textEditor = new Editor();
        textEditor.setName("Text Editor");
        
        imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        
        videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        
        // Initialize file system
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create a FileSystem instance and add documents with different editor types
        doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        
        doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        
        doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents by editor type
        int[] result = fileSystem.countDocumentsByEditor();
        
        // Verify: Check counts for each editor type
        assertEquals("TextEditor count should be 1", 1, result[0]);
        assertEquals("ImageEditor count should be 1", 1, result[1]);
        assertEquals("VideoEditor count should be 1", 1, result[2]);
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create a FileSystem instance and add documents with only TextEditor
        doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        
        doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents by editor type
        int[] result = fileSystem.countDocumentsByEditor();
        
        // Verify: Check counts for each editor type
        assertEquals("TextEditor count should be 2", 2, result[0]);
        assertEquals("ImageEditor count should be 0", 0, result[1]);
        assertEquals("VideoEditor count should be 0", 0, result[2]);
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create a FileSystem instance, add documents, then remove one
        doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(imageEditor);
        
        doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(videoEditor);
        
        doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents by editor type after removal
        int[] result = fileSystem.countDocumentsByEditor();
        
        // Verify: Check counts for each editor type
        assertEquals("TextEditor count should be 1", 1, result[0]);
        assertEquals("ImageEditor count should be 0", 0, result[1]);
        assertEquals("VideoEditor count should be 1", 1, result[2]);
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create a FileSystem instance with no documents
        // No documents added to fileSystem
        
        // Execute: Count documents by editor type
        int[] result = fileSystem.countDocumentsByEditor();
        
        // Verify: Check counts for each editor type (all should be 0)
        assertEquals("TextEditor count should be 0", 0, result[0]);
        assertEquals("ImageEditor count should be 0", 0, result[1]);
        assertEquals("VideoEditor count should be 0", 0, result[2]);
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create a FileSystem instance and add multiple documents with all editor types
        doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        
        doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        
        doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents by editor type
        int[] result = fileSystem.countDocumentsByEditor();
        
        // Verify: Check counts for each editor type
        assertEquals("TextEditor count should be 2", 2, result[0]);
        assertEquals("ImageEditor count should be 1", 1, result[1]);
        assertEquals("VideoEditor count should be 1", 1, result[2]);
    }
}