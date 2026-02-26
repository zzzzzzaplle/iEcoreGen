import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR4Test {
    
    private FileSystem fileSystem;
    private LocalDate testDate;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        testDate = LocalDate.of(2023, 1, 1);
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create FileSystem and add documents with different editors
        Document doc1 = new Document("Report.docx", testDate, "Author1", 1024L, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Image.png", testDate, "Author2", 2048L, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Video.mp4", testDate, "Author3", 4096L, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents by editor
        Map<Editor, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify: Each editor should have exactly 1 document
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get(Editor.TEXT_EDITOR));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get(Editor.IMAGE_EDITOR));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get(Editor.VIDEO_EDITOR));
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create FileSystem and add documents only with TextEditor
        Document doc1 = new Document("Essay.docx", testDate, "Author1", 1024L, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Notes.txt", testDate, "Author2", 512L, Editor.TEXT_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents by editor
        Map<Editor, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify: Only TextEditor should have documents
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get(Editor.TEXT_EDITOR));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get(Editor.IMAGE_EDITOR));
        assertEquals("VideoEditor should have 0 documents", Integer.valueOf(0), result.get(Editor.VIDEO_EDITOR));
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create FileSystem and add documents with different editors
        Document doc1 = new Document("Image1.png", testDate, "Author1", 2048L, Editor.IMAGE_EDITOR);
        Document doc2 = new Document("Video1.mp4", testDate, "Author2", 4096L, Editor.VIDEO_EDITOR);
        Document doc3 = new Document("Text1.docx", testDate, "Author3", 1024L, Editor.TEXT_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove Image1.png document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents by editor after removal
        Map<Editor, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify: TextEditor and VideoEditor should have 1 document each, ImageEditor should have 0
        assertEquals("TextEditor should have 1 document", Integer.valueOf(1), result.get(Editor.TEXT_EDITOR));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get(Editor.IMAGE_EDITOR));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get(Editor.VIDEO_EDITOR));
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: Create empty FileSystem (no documents added)
        
        // Execute: Count documents by editor
        Map<Editor, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify: All editors should have 0 documents
        assertEquals("TextEditor should have 0 documents", Integer.valueOf(0), result.get(Editor.TEXT_EDITOR));
        assertEquals("ImageEditor should have 0 documents", Integer.valueOf(0), result.get(Editor.IMAGE_EDITOR));
        assertEquals("VideoEditor should have 0 documents", Integer.valueOf(0), result.get(Editor.VIDEO_EDITOR));
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create FileSystem and add multiple documents using all editors
        Document doc1 = new Document("Doc1.txt", testDate, "Author1", 1024L, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Pic1.jpg", testDate, "Author2", 2048L, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Clip1.mpg", testDate, "Author3", 4096L, Editor.VIDEO_EDITOR);
        Document doc4 = new Document("Doc2.txt", testDate, "Author4", 512L, Editor.TEXT_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents by editor
        Map<Editor, Integer> result = fileSystem.countDocumentsByEditor();
        
        // Verify: TextEditor should have 2 documents, others should have 1 each
        assertEquals("TextEditor should have 2 documents", Integer.valueOf(2), result.get(Editor.TEXT_EDITOR));
        assertEquals("ImageEditor should have 1 document", Integer.valueOf(1), result.get(Editor.IMAGE_EDITOR));
        assertEquals("VideoEditor should have 1 document", Integer.valueOf(1), result.get(Editor.VIDEO_EDITOR));
    }
}