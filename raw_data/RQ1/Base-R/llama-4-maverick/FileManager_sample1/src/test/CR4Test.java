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
        // Initialize FileSystem and create editor instances before each test
        fileSystem = new FileSystem();
        textEditor = new Editor("TextEditor");
        imageEditor = new Editor("ImageEditor");
        videoEditor = new Editor("VideoEditor");
        
        // Add all editors to the file system
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);
    }
    
    @Test
    public void testCase1_CountDocumentsWithMixedEditorTypes() {
        // SetUp: Create documents with different editor types
        Document doc1 = new Document("Report.docx", LocalDate.now(), "Author1", 1024, textEditor);
        Document doc2 = new Document("Image.png", LocalDate.now(), "Author2", 2048, imageEditor);
        Document doc3 = new Document("Video.mp4", LocalDate.now(), "Author3", 4096, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Count documents per editor
        List<EditorDocumentCount> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals(3, result.size());
        
        // Find counts for each editor
        long textEditorCount = 0;
        long imageEditorCount = 0;
        long videoEditorCount = 0;
        
        for (EditorDocumentCount edc : result) {
            if (edc.getEditor().getName().equals("TextEditor")) {
                textEditorCount = edc.getCount();
            } else if (edc.getEditor().getName().equals("ImageEditor")) {
                imageEditorCount = edc.getCount();
            } else if (edc.getEditor().getName().equals("VideoEditor")) {
                videoEditorCount = edc.getCount();
            }
        }
        
        // Expected Output: TextEditor: 1, ImageEditor: 1, VideoEditor: 1
        assertEquals(1, textEditorCount);
        assertEquals(1, imageEditorCount);
        assertEquals(1, videoEditorCount);
    }
    
    @Test
    public void testCase2_CountDocumentsWithSingleEditorType() {
        // SetUp: Create documents with only TextEditor
        Document doc1 = new Document("Essay.docx", LocalDate.now(), "Author1", 1024, textEditor);
        Document doc2 = new Document("Notes.txt", LocalDate.now(), "Author2", 512, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute: Count documents per editor
        List<EditorDocumentCount> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals(3, result.size());
        
        // Find counts for each editor
        long textEditorCount = 0;
        long imageEditorCount = 0;
        long videoEditorCount = 0;
        
        for (EditorDocumentCount edc : result) {
            if (edc.getEditor().getName().equals("TextEditor")) {
                textEditorCount = edc.getCount();
            } else if (edc.getEditor().getName().equals("ImageEditor")) {
                imageEditorCount = edc.getCount();
            } else if (edc.getEditor().getName().equals("VideoEditor")) {
                videoEditorCount = edc.getCount();
            }
        }
        
        // Expected Output: TextEditor: 2, ImageEditor: 0, VideoEditor: 0
        assertEquals(2, textEditorCount);
        assertEquals(0, imageEditorCount);
        assertEquals(0, videoEditorCount);
    }
    
    @Test
    public void testCase3_CountDocumentsAfterRemoval() {
        // SetUp: Create documents with different editors and remove one
        Document doc1 = new Document("Image1.png", LocalDate.now(), "Author1", 2048, imageEditor);
        Document doc2 = new Document("Video1.mp4", LocalDate.now(), "Author2", 4096, videoEditor);
        Document doc3 = new Document("Text1.docx", LocalDate.now(), "Author3", 1024, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove the image document
        fileSystem.removeDocument(doc1);
        
        // Execute: Count documents per editor after removal
        List<EditorDocumentCount> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals(3, result.size());
        
        // Find counts for each editor
        long textEditorCount = 0;
        long imageEditorCount = 0;
        long videoEditorCount = 0;
        
        for (EditorDocumentCount edc : result) {
            if (edc.getEditor().getName().equals("TextEditor")) {
                textEditorCount = edc.getCount();
            } else if (edc.getEditor().getName().equals("ImageEditor")) {
                imageEditorCount = edc.getCount();
            } else if (edc.getEditor().getName().equals("VideoEditor")) {
                videoEditorCount = edc.getCount();
            }
        }
        
        // Expected Output: TextEditor: 1, ImageEditor: 0, VideoEditor: 1
        assertEquals(1, textEditorCount);
        assertEquals(0, imageEditorCount);
        assertEquals(1, videoEditorCount);
    }
    
    @Test
    public void testCase4_CountDocumentsWithNoEditors() {
        // SetUp: FileSystem is already created with no documents added
        
        // Execute: Count documents per editor
        List<EditorDocumentCount> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals(3, result.size());
        
        // Find counts for each editor
        long textEditorCount = 0;
        long imageEditorCount = 0;
        long videoEditorCount = 0;
        
        for (EditorDocumentCount edc : result) {
            if (edc.getEditor().getName().equals("TextEditor")) {
                textEditorCount = edc.getCount();
            } else if (edc.getEditor().getName().equals("ImageEditor")) {
                imageEditorCount = edc.getCount();
            } else if (edc.getEditor().getName().equals("VideoEditor")) {
                videoEditorCount = edc.getCount();
            }
        }
        
        // Expected Output: TextEditor: 0, ImageEditor: 0, VideoEditor: 0
        assertEquals(0, textEditorCount);
        assertEquals(0, imageEditorCount);
        assertEquals(0, videoEditorCount);
    }
    
    @Test
    public void testCase5_CountDocumentsWithAllEditorsUsed() {
        // SetUp: Create multiple documents with all editor types
        Document doc1 = new Document("Doc1.txt", LocalDate.now(), "Author1", 1024, textEditor);
        Document doc2 = new Document("Pic1.jpg", LocalDate.now(), "Author2", 2048, imageEditor);
        Document doc3 = new Document("Clip1.mpg", LocalDate.now(), "Author3", 4096, videoEditor);
        Document doc4 = new Document("Doc2.txt", LocalDate.now(), "Author4", 512, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute: Count documents per editor
        List<EditorDocumentCount> result = fileSystem.countDocumentsPerEditor();
        
        // Verify: Check counts for each editor type
        assertEquals(3, result.size());
        
        // Find counts for each editor
        long textEditorCount = 0;
        long imageEditorCount = 0;
        long videoEditorCount = 0;
        
        for (EditorDocumentCount edc : result) {
            if (edc.getEditor().getName().equals("TextEditor")) {
                textEditorCount = edc.getCount();
            } else if (edc.getEditor().getName().equals("ImageEditor")) {
                imageEditorCount = edc.getCount();
            } else if (edc.getEditor().getName().equals("VideoEditor")) {
                videoEditorCount = edc.getCount();
            }
        }
        
        // Expected Output: TextEditor: 2, ImageEditor: 1, VideoEditor: 1
        assertEquals(2, textEditorCount);
        assertEquals(1, imageEditorCount);
        assertEquals(1, videoEditorCount);
    }
}