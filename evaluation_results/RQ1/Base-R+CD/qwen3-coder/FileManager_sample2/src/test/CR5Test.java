// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

// Mock classes to simulate the file system and documents
class Document {
    private String name;
    private String creationDate;
    private String author;
    private String size;
    private String editor;

    public Document(String name, String creationDate, String author, String size, String editor) {
        this.name = name;
        this.creationDate = creationDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    public String getEditor() {
        return editor;
    }

    public String getAuthor() {
        return author;
    }
}

class FileSystem {
    private List<Document> documents;

    public FileSystem() {
        this.documents = new ArrayList<>();
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public List<String> getAuthorsByEditor(String editor) {
        List<String> authors = new ArrayList<>();
        for (Document doc : documents) {
            if (doc.getEditor().equals(editor)) {
                authors.add(doc.getAuthor());
            }
        }
        return authors;
    }
}

public class CR5Test {
    private FileSystem fileSystem;

    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }

    @Test
    public void testCase1_retrieveAuthorsForTextEditorDocuments() {
        // SetUp: Create a file system with specified documents
        fileSystem.addDocument(new Document("Report.doc", "2023-10-01 00:00:00", "Alice", "100KB", "Text Editor"));
        fileSystem.addDocument(new Document("Essay.doc", "2023-10-02 00:00:00", "Bob", "150KB", "Text Editor"));
        fileSystem.addDocument(new Document("Image.png", "2023-10-03 00:00:00", "Charlie", "200KB", "Image Editor"));

        // Input: Get authors for documents using "Text Editor"
        List<String> result = fileSystem.getAuthorsByEditor("Text Editor");

        // Expected Output: Authors = ["Alice", "Bob"]
        List<String> expected = Arrays.asList("Alice", "Bob");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }

    @Test
    public void testCase2_retrieveAuthorsForImageEditorDocuments() {
        // SetUp: Create a file system with specified documents
        fileSystem.addDocument(new Document("Photo.jpg", "2023-09-15 00:00:00", "Dave", "250KB", "Image Editor"));
        fileSystem.addDocument(new Document("Diagram.svg", "2023-09-20 00:00:00", "Eve", "300KB", "Image Editor"));
        fileSystem.addDocument(new Document("Video.mp4", "2023-09-25 00:00:00", "Frank", "500MB", "Video Editor"));

        // Input: Get authors for documents using "Image Editor"
        List<String> result = fileSystem.getAuthorsByEditor("Image Editor");

        // Expected Output: Authors = ["Dave", "Eve"]
        List<String> expected = Arrays.asList("Dave", "Eve");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }

    @Test
    public void testCase3_noAuthorsForSpecificEditor() {
        // SetUp: Create a file system with specified documents
        fileSystem.addDocument(new Document("Document.txt", "2023-10-05 00:00:00", "Grace", "50KB", "Text Editor"));
        fileSystem.addDocument(new Document("Drawing.png", "2023-10-06 00:00:00", "Heidi", "80KB", "Image Editor"));

        // Input: Get authors for documents using "Video Editor"
        List<String> result = fileSystem.getAuthorsByEditor("Video Editor");

        // Expected Output: Authors = []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }

    @Test
    public void testCase4_retrieveAuthorsFromMixedEditorDocuments() {
        // SetUp: Create a file system with specified documents
        fileSystem.addDocument(new Document("Notes.txt", "2023-10-07 00:00:00", "Ivy", "30KB", "Text Editor"));
        fileSystem.addDocument(new Document("Video.mp4", "2023-10-08 00:00:00", "Jack", "400MB", "Video Editor"));
        fileSystem.addDocument(new Document("Image.JPG", "2023-10-09 00:00:00", "Kathy", "120KB", "Image Editor"));

        // Input: Get authors for documents using "Text Editor"
        List<String> result = fileSystem.getAuthorsByEditor("Text Editor");

        // Expected Output: Authors = ["Ivy"]
        List<String> expected = Arrays.asList("Ivy");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }

    @Test
    public void testCase5_multipleAuthorsForSingleDocumentType() {
        // SetUp: Create a file system with specified documents
        fileSystem.addDocument(new Document("Portfolio.jpg", "2023-09-30 00:00:00", "Leo", "600KB", "Image Editor"));
        fileSystem.addDocument(new Document("Banner.png", "2023-10-01 00:00:00", "Mona", "300KB", "Image Editor"));
        fileSystem.addDocument(new Document("Presentation.ppt", "2023-10-02 00:00:00", "Nina", "150KB", "Text Editor"));

        // Input: Get authors for documents using "Image Editor"
        List<String> result = fileSystem.getAuthorsByEditor("Image Editor");

        // Expected Output: Authors = ["Leo", "Mona"]
        List<String> expected = Arrays.asList("Leo", "Mona");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }
}