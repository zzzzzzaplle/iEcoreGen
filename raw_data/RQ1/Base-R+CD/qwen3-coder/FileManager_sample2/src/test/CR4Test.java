// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

// Mock classes to simulate the file system and editors
class FileSystem {
    private Map<String, String> documents = new HashMap<>();
    
    public void addDocument(String name, String editorType) {
        documents.put(name, editorType);
    }
    
    public void removeDocument(String name) {
        documents.remove(name);
    }
    
    public Map<String, Integer> countDocumentsPerEditor() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("TextEditor", 0);
        counts.put("ImageEditor", 0);
        counts.put("VideoEditor", 0);
        
        for (String editorType : documents.values()) {
            counts.put(editorType, counts.get(editorType) + 1);
        }
        
        return counts;
    }
}

class TextEditor {}
class ImageEditor {}
class VideoEditor {}

public class CR4Test {
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsWithMixedEditorTypes() {
        // SetUp: 
        // 1. Create a FileSystem instance. (Done in @Before)
        // 2. Add a document named "Report.docx" using TextEditor.
        fileSystem.addDocument("Report.docx", "TextEditor");
        // 3. Add a document named "Image.png" using ImageEditor.
        fileSystem.addDocument("Image.png", "ImageEditor");
        // 4. Add a document named "Video.mp4" using VideoEditor.
        fileSystem.addDocument("Video.mp4", "VideoEditor");
        
        // Expected Output:
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        assertEquals(1, (int) result.get("TextEditor"));
        assertEquals(1, (int) result.get("ImageEditor"));
        assertEquals(1, (int) result.get("VideoEditor"));
    }
    
    @Test
    public void testCase2_countDocumentsWithSingleEditorType() {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in @Before)
        // 2. Add a document named "Essay.docx" using TextEditor.
        fileSystem.addDocument("Essay.docx", "TextEditor");
        // 3. Add a document named "Notes.txt" using TextEditor.
        fileSystem.addDocument("Notes.txt", "TextEditor");
        
        // Expected Output:
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        assertEquals(2, (int) result.get("TextEditor"));
        assertEquals(0, (int) result.get("ImageEditor"));
        assertEquals(0, (int) result.get("VideoEditor"));
    }
    
    @Test
    public void testCase3_countDocumentsAfterRemoval() {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in @Before)
        // 2. Add a document named "Image1.png" using ImageEditor.
        fileSystem.addDocument("Image1.png", "ImageEditor");
        // 3. Add a document named "Video1.mp4" using VideoEditor.
        fileSystem.addDocument("Video1.mp4", "VideoEditor");
        // 4. Add a document named "Text1.docx" using TextEditor.
        fileSystem.addDocument("Text1.docx", "TextEditor");
        // 5. Remove the document "Image1.png".
        fileSystem.removeDocument("Image1.png");
        
        // Expected Output:
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        assertEquals(1, (int) result.get("TextEditor"));
        assertEquals(0, (int) result.get("ImageEditor"));
        assertEquals(1, (int) result.get("VideoEditor"));
    }
    
    @Test
    public void testCase4_countDocumentsWithNoEditors() {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in @Before)
        
        // Expected Output:
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        assertEquals(0, (int) result.get("TextEditor"));
        assertEquals(0, (int) result.get("ImageEditor"));
        assertEquals(0, (int) result.get("VideoEditor"));
    }
    
    @Test
    public void testCase5_countDocumentsWithAllEditorsUsed() {
        // SetUp:
        // 1. Create a FileSystem instance. (Done in @Before)
        // 2. Add three documents: "Doc1.txt" with TextEditor, "Pic1.jpg" with ImageEditor, "Clip1.mpg" with VideoEditor.
        fileSystem.addDocument("Doc1.txt", "TextEditor");
        fileSystem.addDocument("Pic1.jpg", "ImageEditor");
        fileSystem.addDocument("Clip1.mpg", "VideoEditor");
        // 3. Add another document "Doc2.txt" with TextEditor.
        fileSystem.addDocument("Doc2.txt", "TextEditor");
        
        // Expected Output:
        Map<String, Integer> result = fileSystem.countDocumentsPerEditor();
        assertEquals(2, (int) result.get("TextEditor"));
        assertEquals(1, (int) result.get("ImageEditor"));
        assertEquals(1, (int) result.get("VideoEditor"));
    }
}