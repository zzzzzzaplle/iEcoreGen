import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        Editor videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        doc3.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Count documents per editor
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify expected counts
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create documents with only Text Editor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        Editor textEditor2 = new Editor();
        textEditor2.setName("Text Editor");
        doc2.setEditor(textEditor2);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Count documents per editor
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify expected counts
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertNull(result.get("Image Editor"));
        assertNull(result.get("Video Editor"));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create documents with different editors
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc1.setEditor(imageEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        Editor videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove one document
        fileSystem.removeDocument(doc1);
        
        // Count documents per editor after removal
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify expected counts
        assertEquals(Integer.valueOf(1), result.get("Text Editor"));
        assertNull(result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: FileSystem is empty (no documents added)
        
        // Count documents per editor
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify expected counts - all editors should have 0 documents
        // Since no documents exist, the map should be empty
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create multiple documents with all editor types
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        Editor textEditor1 = new Editor();
        textEditor1.setName("Text Editor");
        doc1.setEditor(textEditor1);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        doc2.setEditor(imageEditor);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        Editor videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        Editor textEditor2 = new Editor();
        textEditor2.setName("Text Editor");
        doc4.setEditor(textEditor2);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Count documents per editor
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        
        // Verify expected counts
        assertEquals(Integer.valueOf(2), result.get("Text Editor"));
        assertEquals(Integer.valueOf(1), result.get("Image Editor"));
        assertEquals(Integer.valueOf(1), result.get("Video Editor"));
    }
}