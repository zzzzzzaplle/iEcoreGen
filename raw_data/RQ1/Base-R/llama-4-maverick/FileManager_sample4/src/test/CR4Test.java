import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR4Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        // Create file system and editors before each test
        fileSystem = new FileSystem();
        
        textEditor = new Editor();
        textEditor.setName("TextEditor");
        
        imageEditor = new Editor();
        imageEditor.setName("ImageEditor");
        
        videoEditor = new Editor();
        videoEditor.setName("VideoEditor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create documents with different editor types
        Document doc1 = new Document();
        doc1.setName("Report.docx");
        doc1.setEditor(textEditor);
        textEditor.addDocument(doc1);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Image.png");
        doc2.setEditor(imageEditor);
        imageEditor.addDocument(doc2);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Video.mp4");
        doc3.setEditor(videoEditor);
        videoEditor.addDocument(doc3);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor
        List<Integer> counts = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals(3, counts.size()); // Should have counts for all 3 editors
        assertEquals(1, (int)counts.get(0)); // TextEditor: 1
        assertEquals(1, (int)counts.get(1)); // ImageEditor: 1
        assertEquals(1, (int)counts.get(2)); // VideoEditor: 1
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create documents with only TextEditor
        Document doc1 = new Document();
        doc1.setName("Essay.docx");
        doc1.setEditor(textEditor);
        textEditor.addDocument(doc1);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Notes.txt");
        doc2.setEditor(textEditor);
        textEditor.addDocument(doc2);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor
        List<Integer> counts = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals(3, counts.size()); // Should have counts for all 3 editors
        assertEquals(2, (int)counts.get(0)); // TextEditor: 2
        assertEquals(0, (int)counts.get(1)); // ImageEditor: 0
        assertEquals(0, (int)counts.get(2)); // VideoEditor: 0
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create documents with different editors and remove one
        Document doc1 = new Document();
        doc1.setName("Image1.png");
        doc1.setEditor(imageEditor);
        imageEditor.addDocument(doc1);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Video1.mp4");
        doc2.setEditor(videoEditor);
        videoEditor.addDocument(doc2);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Text1.docx");
        doc3.setEditor(textEditor);
        textEditor.addDocument(doc3);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        imageEditor.getDocuments().remove(doc1);
        
        // Execute: Count documents per editor after removal
        List<Integer> counts = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type after removal
        assertEquals(3, counts.size()); // Should have counts for all 3 editors
        assertEquals(1, (int)counts.get(0)); // TextEditor: 1
        assertEquals(0, (int)counts.get(1)); // ImageEditor: 0
        assertEquals(1, (int)counts.get(2)); // VideoEditor: 1
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create file system with no documents (already done in @Before)
        
        // Execute: Count documents per editor
        List<Integer> counts = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check that all editors have 0 documents
        assertEquals(3, counts.size()); // Should have counts for all 3 editors
        assertEquals(0, (int)counts.get(0)); // TextEditor: 0
        assertEquals(0, (int)counts.get(1)); // ImageEditor: 0
        assertEquals(0, (int)counts.get(2)); // VideoEditor: 0
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create documents with all editor types, with multiple for TextEditor
        Document doc1 = new Document();
        doc1.setName("Doc1.txt");
        doc1.setEditor(textEditor);
        textEditor.addDocument(doc1);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Pic1.jpg");
        doc2.setEditor(imageEditor);
        imageEditor.addDocument(doc2);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Clip1.mpg");
        doc3.setEditor(videoEditor);
        videoEditor.addDocument(doc3);
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document();
        doc4.setName("Doc2.txt");
        doc4.setEditor(textEditor);
        textEditor.addDocument(doc4);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor
        List<Integer> counts = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals(3, counts.size()); // Should have counts for all 3 editors
        assertEquals(2, (int)counts.get(0)); // TextEditor: 2
        assertEquals(1, (int)counts.get(1)); // ImageEditor: 1
        assertEquals(1, (int)counts.get(2)); // VideoEditor: 1
    }
}