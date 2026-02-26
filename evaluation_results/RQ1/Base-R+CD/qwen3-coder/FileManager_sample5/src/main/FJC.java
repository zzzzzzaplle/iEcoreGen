import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a file system that manages documents and editors.
 */
 class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Constructs a new FileSystem with empty documents and editors lists.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
        // Initialize the three required editors
        this.editors.add(new TextEditor());
        this.editors.add(new ImageEditor());
        this.editors.add(new VideoEditor());
    }

    /**
     * Adds a document to the file system.
     *
     * @param doc the document to add
     */
    public void addDocument(Document doc) {
        documents.add(doc);
    }

    /**
     * Removes a document from the file system.
     *
     * @param doc the document to remove
     */
    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    /**
     * Lists all documents in the file system.
     *
     * @return a list of all documents
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return the sum of all document sizes, or 0 if there are no documents
     */
    public int calculateTotalDocumentSize() {
        return documents.stream().mapToInt(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor type.
     *
     * @return a map where keys are editor names and values are average sizes
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Float> result = new HashMap<>();
        
        // Group documents by editor name
        Map<String, List<Document>> documentsByEditor = documents.stream()
                .filter(doc -> doc.getEditor() != null)
                .collect(Collectors.groupingBy(doc -> doc.getEditor().getName()));
        
        // Calculate average for each editor
        for (Map.Entry<String, List<Document>> entry : documentsByEditor.entrySet()) {
            String editorName = entry.getKey();
            List<Document> docs = entry.getValue();
            float average = (float) docs.stream().mapToInt(Document::getSize).average().orElse(0.0);
            result.put(editorName, average);
        }
        
        // Include editors with no documents
        for (Editor editor : editors) {
            if (!result.containsKey(editor.getName())) {
                result.put(editor.getName(), 0.0f);
            }
        }
        
        return result;
    }

    /**
     * Counts the number of documents created after a specified date.
     *
     * @param date the date to compare against
     * @return the count of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) documents.stream()
                .filter(doc -> doc.getCreateDate().after(date))
                .count();
    }

    /**
     * Counts the number of documents for each editor type.
     *
     * @return a map where keys are editor names and values are document counts
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> result = new HashMap<>();
        
        // Initialize all editors with 0 count
        for (Editor editor : editors) {
            result.put(editor.getName(), 0);
        }
        
        // Count documents for each editor
        Map<String, Long> counts = documents.stream()
                .filter(doc -> doc.getEditor() != null)
                .collect(Collectors.groupingBy(
                    doc -> doc.getEditor().getName(),
                    Collectors.counting()
                ));
        
        // Update the counts in result map
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            result.put(entry.getKey(), entry.getValue().intValue());
        }
        
        return result;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     *
     * @param editor the editor to filter by
     * @return a list of author names
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        return documents.stream()
                .filter(doc -> doc.getEditor() != null && doc.getEditor().getName().equals(editor.getName()))
                .map(Document::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }

    // Getters and setters
    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }
}

/**
 * Represents a document in the file system.
 */
class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;

    /**
     * Constructs a new Document with default values.
     */
    public Document() {
        this.name = "";
        this.createDate = new Date();
        this.author = "";
        this.size = 0;
        this.editor = null;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Abstract base class for editors.
 */
abstract class Editor {
    private String name;

    /**
     * Constructs a new Editor with default values.
     */
    public Editor() {
        this.name = "";
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a text editor.
 */
class TextEditor extends Editor {
    /**
     * Constructs a new TextEditor.
     */
    public TextEditor() {
        super();
        setName("Text Editor");
    }
}

/**
 * Represents an image editor.
 */
class ImageEditor extends Editor {
    /**
     * Constructs a new ImageEditor.
     */
    public ImageEditor() {
        super();
        setName("Image Editor");
    }
}

/**
 * Represents a video editor.
 */
class VideoEditor extends Editor {
    /**
     * Constructs a new VideoEditor.
     */
    public VideoEditor() {
        super();
        setName("Video Editor");
    }
}