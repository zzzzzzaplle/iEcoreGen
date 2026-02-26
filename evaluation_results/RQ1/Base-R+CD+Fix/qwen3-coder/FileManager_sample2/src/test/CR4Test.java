import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR4Test {
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;

    @Before
    public void setUp() {
        // Initialize fresh FileSystem and editors before each test
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }

    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // Test Case 1: Count Documents with Mixed Editor Types
        // SetUp: Create documents with all three editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Each editor should have exactly 1 document
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("Text Editor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("Image Editor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("Video Editor"));
    }

    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // Test Case 2: Count Documents with Single Editor Type
        // SetUp: Create two documents using only TextEditor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: Only TextEditor should have documents
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("Text Editor"));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor should have 0 documents", Integer.valueOf(0), result.get("Video Editor"));
    }

    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // Test Case 3: Count Documents after Removal
        // SetUp: Create three documents with different editors
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(textEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents per editor type after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: ImageEditor document removed, others remain
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get("Text Editor"));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("Video Editor"));
    }

    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // Test Case 4: Count Documents with No Editors
        // SetUp: FileSystem created with no documents added
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: All editors should have 0 documents
        assertEquals("TextEditor should have 0 documents", Integer.valueOf(0), result.get("Text Editor"));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get("Image Editor"));
        assertEquals("VideoEditor should have 0 documents", Integer.valueOf(0), result.get("Video Editor"));
    }

    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // Test Case 5: Count Documents with All Editors Used
        // SetUp: Create four documents with mixed editor distribution
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify: TextEditor has 2, others have 1 each
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get("Text Editor"));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get("Image Editor"));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get("Video Editor"));
    }
}