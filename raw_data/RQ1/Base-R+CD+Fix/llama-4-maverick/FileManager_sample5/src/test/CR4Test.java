import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize a new FileSystem instance before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsWithMixedEditorTypes() {
        // Test Case 1: Count Documents with Mixed Editor Types
        // SetUp: Create documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        TextEditor textEditor = new TextEditor();
        textEditor.setName("TextEditor");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("ImageEditor");
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        VideoEditor videoEditor = new VideoEditor();
        videoEditor.setName("VideoEditor");
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_countDocumentsWithSingleEditorType() {
        // Test Case 2: Count Documents with Single Editor Type
        // SetUp: Create documents with only TextEditor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        TextEditor textEditor1 = new TextEditor();
        textEditor1.setName("TextEditor");
        doc1.setEditor(textEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        TextEditor textEditor2 = new TextEditor();
        textEditor2.setName("TextEditor");
        doc2.setEditor(textEditor2);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertNull("ImageEditor should not be present", result.get("ImageEditor"));
        assertNull("VideoEditor should not be present", result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_countDocumentsAfterRemoval() {
        // Test Case 3: Count Documents after Removal
        // SetUp: Create documents and then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("ImageEditor");
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        VideoEditor videoEditor = new VideoEditor();
        videoEditor.setName("VideoEditor");
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        TextEditor textEditor = new TextEditor();
        textEditor.setName("TextEditor");
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("TextEditor"));
        assertNull("ImageEditor should not be present after removal", result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // Test Case 4: Count Documents with No Editors
        // SetUp: FileSystem is empty (no documents added)
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output - empty map since no documents exist
        assertTrue("Result should be empty when no documents exist", result.isEmpty());
    }
    
    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() {
        // Test Case 5: Count Documents with All Editors Used
        // SetUp: Create documents with all editor types, including multiple TextEditor documents
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        TextEditor textEditor1 = new TextEditor();
        textEditor1.setName("TextEditor");
        doc1.setEditor(textEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.setName("ImageEditor");
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        VideoEditor videoEditor = new VideoEditor();
        videoEditor.setName("VideoEditor");
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        TextEditor textEditor2 = new TextEditor();
        textEditor2.setName("TextEditor");
        doc4.setEditor(textEditor2);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute the method under test
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("TextEditor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("ImageEditor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("VideoEditor"));
    }
}