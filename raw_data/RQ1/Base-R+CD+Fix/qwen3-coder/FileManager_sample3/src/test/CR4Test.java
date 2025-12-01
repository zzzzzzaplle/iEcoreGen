import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // Test Case 1: Count Documents with Mixed Editor Types
        // SetUp: Add documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(new VideoEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // Test Case 2: Count Documents with Single Editor Type
        // SetUp: Add multiple documents with TextEditor only
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertEquals(Integer.valueOf(0), result.get("Image Editor"));
        assertEquals(Integer.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // Test Case 3: Count Documents after Removal
        // SetUp: Add documents and then remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(new ImageEditor());
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(new VideoEditor());
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertEquals(Integer.valueOf(0), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // Test Case 4: Count Documents with No Editors
        // SetUp: FileSystem is already created in @Before, no documents added
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals(Integer.valueOf(0), result.get("Text Editor"));
        assertEquals(Integer.valueOf(0), result.get("Image Editor"));
        assertEquals(Integer.valueOf(0), result.get("Video Editor"));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // Test Case 5: Count Documents with All Editors Used
        // SetUp: Add multiple documents with all editor types
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(new TextEditor());
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(new ImageEditor());
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(new VideoEditor());
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(new TextEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor type
        Map<String, Integer> result = fileSystem.countDocumentsPerEditorType();
        
        // Verify expected output
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
}