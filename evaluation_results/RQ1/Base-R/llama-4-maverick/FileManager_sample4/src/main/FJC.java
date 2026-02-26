import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an editor in the file system.
 */
class Editor {
    private String name;
    private List<Document> documents;

    /**
     * Constructor for Editor.
     */
    public Editor() {
        this.documents = new ArrayList<>();
    }

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

    /**
     * Gets the list of documents associated with this editor.
     * 
     * @return the documents
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the list of documents associated with this editor.
     * 
     * @param documents the documents to set
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /**
     * Adds a document to the list of documents associated with this editor.
     * 
     * @param document the document to add
     */
    public void addDocument(Document document) {
        this.documents.add(document);
    }

    /**
     * Computes the average size of documents edited by this editor.
     * 
     * @return the average size of documents
     */
    public double computeAverageDocumentSize() {
        if (documents.isEmpty()) {
            return 0;
        }
        return documents.stream().mapToDouble(Document::getSize).average().orElse(0);
    }

    /**
     * Counts the number of documents edited by this editor.
     * 
     * @return the number of documents
     */
    public int countDocuments() {
        return documents.size();
    }

    /**
     * Retrieves the names of authors whose documents are edited by this editor.
     * 
     * @return the list of author names
     */
    public List<String> getAuthorNames() {
        return documents.stream().map(Document::getAuthor).collect(Collectors.toList());
    }
}

/**
 * Represents a document in the file system.
 */
class Document {
    private String name;
    private LocalDate creationDate;
    private String author;
    private double size;
    private Editor editor;

    /**
     * Constructor for Document.
     */
    public Document() {}

    /**
     * Gets the name of the document.
     * 
     * @return the name
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
     * @return the creationDate
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the document.
     * 
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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
    public double getSize() {
        return size;
    }

    /**
     * Sets the size of the document.
     * 
     * @param size the size to set
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Gets the editor associated with this document.
     * 
     * @return the editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor associated with this document.
     * 
     * @param editor the editor to set
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Represents a file system containing documents and editors.
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Constructor for FileSystem.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
    }

    /**
     * Gets the list of documents in the file system.
     * 
     * @return the documents
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the list of documents in the file system.
     * 
     * @param documents the documents to set
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /**
     * Gets the list of editors in the file system.
     * 
     * @return the editors
     */
    public List<Editor> getEditors() {
        return editors;
    }

    /**
     * Sets the list of editors in the file system.
     * 
     * @param editors the editors to set
     */
    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    /**
     * Adds a document to the file system.
     * 
     * @param document the document to add
     */
    public void addDocument(Document document) {
        this.documents.add(document);
    }

    /**
     * Removes a document from the file system.
     * 
     * @param document the document to remove
     */
    public void removeDocument(Document document) {
        this.documents.remove(document);
    }

    /**
     * Lists all documents in the file system.
     * 
     * @return the list of documents
     */
    public List<Document> listDocuments() {
        return documents;
    }

    /**
     * Calculates the total size of all documents in the file system.
     * 
     * @return the total size
     */
    public double calculateTotalDocumentSize() {
        return documents.stream().mapToDouble(Document::getSize).sum();
    }

    /**
     * Computes the average size of documents for each editor.
     * 
     * @return a list of average sizes for each editor
     */
    public List<Double> computeAverageDocumentSizePerEditor() {
        return editors.stream().map(Editor::computeAverageDocumentSize).collect(Collectors.toList());
    }

    /**
     * Counts the number of documents created after a specified date.
     * 
     * @param date the date to compare with
     * @return the count of documents
     */
    public long countDocumentsCreatedAfter(LocalDate date) {
        return documents.stream().filter(doc -> doc.getCreationDate().isAfter(date)).count();
    }

    /**
     * Counts the number of documents for each editor.
     * 
     * @return a list of document counts for each editor
     */
    public List<Integer> countDocumentsPerEditor() {
        return editors.stream().map(Editor::countDocuments).collect(Collectors.toList());
    }

    /**
     * Retrieves the names of authors whose documents are edited with a specified editor.
     * 
     * @param editor the editor to check
     * @return the list of author names
     */
    public List<String> getAuthorNamesForEditor(Editor editor) {
        return editor.getAuthorNames();
    }
}