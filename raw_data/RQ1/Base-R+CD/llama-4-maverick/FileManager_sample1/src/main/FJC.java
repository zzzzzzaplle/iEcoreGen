import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a file system that manages documents and editors.
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Constructs a new FileSystem.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
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
     * @return a list of documents
     */
    public List<Document> listDocuments() {
        return documents;
    }

    /**
     * Adds an editor to the file system.
     * 
     * @param editor the editor to add
     */
    public void addEditor(Editor editor) {
        editors.add(editor);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * 
     * @return the total size of all documents, or 0 if there are no documents
     */
    public int calculateTotalDocumentSize() {
        return documents.stream().mapToInt(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     * 
     * @return a map of editor names to average document sizes
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Float> averageSizes = new HashMap<>();
        for (Editor editor : editors) {
            List<Document> docsForEditor = documents.stream()
                    .filter(doc -> doc.getEditor() != null && doc.getEditor().getName().equals(editor.getName()))
                    .collect(Collectors.toList());
            if (!docsForEditor.isEmpty()) {
                int sum = docsForEditor.stream().mapToInt(Document::getSize).sum();
                averageSizes.put(editor.getName(), (float) sum / docsForEditor.size());
            } else {
                averageSizes.put(editor.getName(), 0.0f);
            }
        }
        return averageSizes;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * 
     * @param date the date to compare against
     * @return the number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) documents.stream().filter(doc -> doc.getCreateDate().after(date)).count();
    }

    /**
     * Counts the number of documents for each editor in the file system.
     * 
     * @return a map of editor names to document counts
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> counts = new HashMap<>();
        for (Editor editor : editors) {
            counts.put(editor.getName(), (int) documents.stream()
                    .filter(doc -> doc.getEditor() != null && doc.getEditor().getName().equals(editor.getName())).count());
        }
        return counts;
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
                .map(Document::getAuthor).collect(Collectors.toList());
    }

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
 * Represents a document with metadata.
 */
class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;

    /**
     * Constructs a new Document.
     */
    public Document() {
    }

    /**
     * Gets the name of the document.
     * 
     * @return the document name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the document.
     * 
     * @param name the new document name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the creation date of the document.
     * 
     * @return the creation date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of the document.
     * 
     * @param createDate the new creation date
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the author of the document.
     * 
     * @return the author name
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the document.
     * 
     * @param author the new author name
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the size of the document.
     * 
     * @return the document size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the document.
     * 
     * @param size the new document size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets the editor associated with the document.
     * 
     * @return the editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor associated with the document.
     * 
     * @param editor the new editor
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Represents an abstract editor.
 */
abstract class Editor {
    private String name;

    /**
     * Constructs a new Editor.
     */
    public Editor() {
    }

    /**
     * Gets the name of the editor.
     * 
     * @return the editor name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the editor.
     * 
     * @param name the new editor name
     */
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
    }
}