import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an editor used for creating and managing documents.
 */
class Editor {
    private String name;
    private List<Document> documents;

    /**
     * Constructs a new Editor object.
     */
    public Editor() {
        this.documents = new ArrayList<>();
    }

    /**
     * Gets the name of the editor.
     * @return the name of the editor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the editor.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of documents associated with this editor.
     * @return the list of documents
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the list of documents associated with this editor.
     * @param documents the list of documents to set
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /**
     * Adds a document to the list of documents associated with this editor.
     * @param document the document to add
     */
    public void addDocument(Document document) {
        this.documents.add(document);
    }

    /**
     * Calculates the total size of all documents associated with this editor.
     * @return the total size of all documents
     */
    public long calculateTotalSize() {
        return documents.stream().mapToLong(Document::getSize).sum();
    }

    /**
     * Calculates the average size of all documents associated with this editor.
     * @return the average size of all documents, or 0 if there are no documents
     */
    public double calculateAverageSize() {
        return documents.isEmpty() ? 0 : (double) calculateTotalSize() / documents.size();
    }

    /**
     * Counts the number of documents associated with this editor that were created after a specified date.
     * @param date the date to compare with the creation date of documents
     * @return the number of documents created after the specified date
     */
    public long countDocumentsCreatedAfter(LocalDate date) {
        return documents.stream().filter(doc -> doc.getCreationDate().isAfter(date)).count();
    }

    /**
     * Retrieves the names of all authors whose documents are edited with this editor.
     * @return a list of author names
     */
    public List<String> getAuthors() {
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
    private long size;
    private Editor editor;

    /**
     * Constructs a new Document object.
     */
    public Document() {}

    /**
     * Gets the name of the document.
     * @return the name of the document
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the document.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the creation date of the document.
     * @return the creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the document.
     * @param creationDate the creation date to set
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the author of the document.
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the document.
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the size of the document.
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size of the document.
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Gets the editor associated with this document.
     * @return the editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor associated with this document.
     * @param editor the editor to set
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
        editor.addDocument(this);
    }
}

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
     * Gets the list of documents in the file system.
     * @return the list of documents
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the list of documents in the file system.
     * @param documents the list of documents to set
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /**
     * Gets the list of editors in the file system.
     * @return the list of editors
     */
    public List<Editor> getEditors() {
        return editors;
    }

    /**
     * Sets the list of editors in the file system.
     * @param editors the list of editors to set
     */
    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    /**
     * Adds a document to the file system.
     * @param document the document to add
     */
    public void addDocument(Document document) {
        this.documents.add(document);
    }

    /**
     * Removes a document from the file system.
     * @param document the document to remove
     */
    public void removeDocument(Document document) {
        this.documents.remove(document);
    }

    /**
     * Lists all documents in the file system.
     * @return a list of all documents
     */
    public List<Document> listDocuments() {
        return documents;
    }

    /**
     * Calculates the total size of all documents in the file system.
     * @return the total size of all documents, or 0 if there are no documents
     */
    public long calculateTotalSize() {
        return documents.stream().mapToLong(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     * @return a list of average sizes for each editor
     */
    public List<Double> computeAverageSizePerEditor() {
        return editors.stream().map(Editor::calculateAverageSize).collect(Collectors.toList());
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * @param date the date to compare with the creation date of documents
     * @return the number of documents created after the specified date
     */
    public long countDocumentsCreatedAfter(LocalDate date) {
        return documents.stream().filter(doc -> doc.getCreationDate().isAfter(date)).count();
    }

    /**
     * Counts the number of documents for each editor in the file system.
     * @return a list of document counts for each editor
     */
    public List<Long> countDocumentsPerEditor() {
        return editors.stream().map(editor -> (long) editor.getDocuments().size()).collect(Collectors.toList());
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     * @param editor the editor to retrieve authors for
     * @return a list of author names
     */
    public List<String> getAuthorsForEditor(Editor editor) {
        return editor.getAuthors();
    }
}

 class Main {
    public static void main(String[] args) {
        // Example usage
        FileSystem fileSystem = new FileSystem();

        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        fileSystem.getEditors().add(textEditor);

        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        fileSystem.getEditors().add(imageEditor);

        Editor videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        fileSystem.getEditors().add(videoEditor);

        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2022, 1, 1));
        doc1.setAuthor("Author1");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2022, 1, 15));
        doc2.setAuthor("Author2");
        doc2.setSize(200);
        doc2.setEditor(imageEditor);
        fileSystem.addDocument(doc2);

        System.out.println("Total size: " + fileSystem.calculateTotalSize());
        System.out.println("Average size per editor: " + fileSystem.computeAverageSizePerEditor());
        System.out.println("Documents created after 2022-01-10: " + fileSystem.countDocumentsCreatedAfter(LocalDate.of(2022, 1, 10)));
        System.out.println("Documents per editor: " + fileSystem.countDocumentsPerEditor());
        System.out.println("Authors for Text Editor: " + fileSystem.getAuthorsForEditor(textEditor));
    }
}