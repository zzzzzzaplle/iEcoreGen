import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Represents a file system that contains information from multiple documents.
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Constructs a new FileSystem object.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
    }

    /**
     * Adds a document to the file system.
     * 
     * @param doc the document to be added
     */
    public void addDocument(Document doc) {
        this.documents.add(doc);
    }

    /**
     * Removes a document from the file system.
     * 
     * @param doc the document to be removed
     */
    public void removeDocument(Document doc) {
        this.documents.remove(doc);
    }

    /**
     * Lists all documents in the file system.
     * 
     * @return a list of documents
     */
    public List<Document> listDocuments() {
        return this.documents;
    }

    /**
     * Calculates the total size of all documents in the file system.
     * 
     * @return the total size of all documents
     */
    public int calculateTotalDocumentSize() {
        return this.documents.stream().mapToInt(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     * 
     * @return a map where the key is the editor's name and the value is the average size
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, List<Document>> docsPerEditor = this.documents.stream()
                .collect(Collectors.groupingBy(doc -> doc.getEditor().getName()));
        Map<String, Float> avgSizePerEditor = new HashMap<>();
        for (Map.Entry<String, List<Document>> entry : docsPerEditor.entrySet()) {
            int sum = entry.getValue().stream().mapToInt(Document::getSize).sum();
            float avg = (float) sum / entry.getValue().size();
            avgSizePerEditor.put(entry.getKey(), avg);
        }
        return avgSizePerEditor;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * 
     * @param date the date to compare with the document's creation date
     * @return the number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) this.documents.stream().filter(doc -> doc.getCreateDate().after(date)).count();
    }

    /**
     * Counts the number of documents for each editor in the file system.
     * 
     * @return a map where the key is the editor's name and the value is the number of documents
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        return this.documents.stream()
                .collect(Collectors.groupingBy(doc -> doc.getEditor().getName(), Collectors.summingInt(x -> 1)));
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor in the file system.
     * 
     * @param editor the editor to filter the documents
     * @return a list of authors' names
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        return this.documents.stream()
                .filter(doc -> doc.getEditor().equals(editor))
                .map(Document::getAuthor)
                .collect(Collectors.toList());
    }

    /**
     * Gets the list of documents.
     * 
     * @return the list of documents
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the list of documents.
     * 
     * @param documents the list of documents to set
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /**
     * Gets the list of editors.
     * 
     * @return the list of editors
     */
    public List<Editor> getEditors() {
        return editors;
    }

    /**
     * Sets the list of editors.
     * 
     * @param editors the list of editors to set
     */
    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }
}

/**
 * Represents a document with its properties.
 */
class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;

    /**
     * Constructs a new Document object.
     */
    public Document() {
    }

    /**
     * Gets the document's name.
     * 
     * @return the document's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the document's name.
     * 
     * @param name the document's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the document's creation date.
     * 
     * @return the document's creation date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the document's creation date.
     * 
     * @param createDate the document's creation date to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the document's author.
     * 
     * @return the document's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the document's author.
     * 
     * @param author the document's author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the document's size.
     * 
     * @return the document's size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the document's size.
     * 
     * @param size the document's size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets the document's editor.
     * 
     * @return the document's editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the document's editor.
     * 
     * @param editor the document's editor to set
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Represents an editor with its name.
 */
abstract class Editor {
    private String name;

    /**
     * Constructs a new Editor object.
     */
    public Editor() {
    }

    /**
     * Gets the editor's name.
     * 
     * @return the editor's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the editor's name.
     * 
     * @param name the editor's name to set
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
     * Constructs a new TextEditor object.
     */
    public TextEditor() {
    }
}

/**
 * Represents an image editor.
 */
class ImageEditor extends Editor {
    /**
     * Constructs a new ImageEditor object.
     */
    public ImageEditor() {
    }
}

/**
 * Represents a video editor.
 */
class VideoEditor extends Editor {
    /**
     * Constructs a new VideoEditor object.
     */
    public VideoEditor() {
    }
}