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
     * Adds an editor to the file system.
     * 
     * @param editor the editor to be added
     */
    public void addEditor(Editor editor) {
        this.editors.add(editor);
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
     * @return a map where the keys are editor names and the values are the average document sizes
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
     * @param date the date to compare with the document creation dates
     * @return the number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) this.documents.stream().filter(doc -> doc.getCreateDate().after(date)).count();
    }

    /**
     * Counts the number of documents for each editor in the file system.
     * 
     * @return a map where the keys are editor names and the values are the document counts
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> documentCounts = new HashMap<>();
        for (Editor editor : editors) {
            documentCounts.put(editor.getName(), (int) documents.stream()
                    .filter(doc -> doc.getEditor() != null && doc.getEditor().getName().equals(editor.getName())).count());
        }
        return documentCounts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor in the file system.
     * 
     * @param editor the editor to filter documents by
     * @return a list of author names
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        return this.documents.stream()
                .filter(doc -> doc.getEditor() != null && doc.getEditor().getName().equals(editor.getName()))
                .map(Document::getAuthor).collect(Collectors.toList());
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
    public Document() {}

    /**
     * Gets the name of the document.
     * 
     * @return the name of the document
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the document.
     * 
     * @param name the name to set
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
     * @param createDate the creation date to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the author of the document.
     * 
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the document.
     * 
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the size of the document.
     * 
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the document.
     * 
     * @param size the size to set
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
     * @param editor the editor to set
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Represents an abstract editor with a name.
 */
abstract class Editor {
    private String name;

    /**
     * Constructs a new Editor object.
     */
    public Editor() {}

    /**
     * Gets the name of the editor.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the editor.
     * 
     * @param name the name to set
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
    public TextEditor() {}
}

/**
 * Represents an image editor.
 */
class ImageEditor extends Editor {
    /**
     * Constructs a new ImageEditor object.
     */
    public ImageEditor() {}
}

/**
 * Represents a video editor.
 */
class VideoEditor extends Editor {
    /**
     * Constructs a new VideoEditor object.
     */
    public VideoEditor() {}
}