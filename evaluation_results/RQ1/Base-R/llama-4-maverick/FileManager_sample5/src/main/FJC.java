import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * Unparameterized constructor for Document.
     */
    public Document() {}

    /**
     * Constructor for Document.
     * 
     * @param name         the name of the document
     * @param creationDate the creation date of the document
     * @param author       the author of the document
     * @param size         the size of the document
     * @param editor       the editor used for the document
     */
    public Document(String name, LocalDate creationDate, String author, long size, Editor editor) {
        this.name = name;
        this.creationDate = creationDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

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
     * @return the creation date of the document
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the document.
     * 
     * @param creationDate the creation date to set
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the author of the document.
     * 
     * @return the author of the document
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
     * @return the size of the document
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size of the document.
     * 
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Gets the editor used for the document.
     * 
     * @return the editor used for the document
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor used for the document.
     * 
     * @param editor the editor to set
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Represents an editor in the file system.
 */
class Editor {
    private String name;

    /**
     * Unparameterized constructor for Editor.
     */
    public Editor() {}

    /**
     * Constructor for Editor.
     * 
     * @param name the name of the editor
     */
    public Editor(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the editor.
     * 
     * @return the name of the editor
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
 * Represents the file system.
 */
class FileSystem {
    private List<Document> documents;

    /**
     * Unparameterized constructor for FileSystem.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
    }

    /**
     * Adds a document to the file system.
     * 
     * @param document the document to add
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * Removes a document from the file system.
     * 
     * @param document the document to remove
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Lists all documents in the file system.
     * 
     * @return a list of all documents
     */
    public List<Document> listDocuments() {
        return documents;
    }

    /**
     * Calculates the total size of all documents in the file system.
     * 
     * @return the total size of all documents
     */
    public long calculateTotalSize() {
        return documents.stream().mapToLong(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor.
     * 
     * @return a string representation of the average size for each editor
     */
    public String computeAverageSizePerEditor() {
        return documents.stream()
                .collect(Collectors.groupingBy(doc -> doc.getEditor().getName(), Collectors.averagingLong(Document::getSize)))
                .entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
    }

    /**
     * Counts the number of documents created after a specified date.
     * 
     * @param date the date to compare with
     * @return the number of documents created after the specified date
     */
    public long countDocumentsCreatedAfter(LocalDate date) {
        return documents.stream().filter(doc -> doc.getCreationDate().isAfter(date)).count();
    }

    /**
     * Counts the number of documents for each editor.
     * 
     * @return a string representation of the count for each editor
     */
    public String countDocumentsPerEditor() {
        return documents.stream()
                .collect(Collectors.groupingBy(doc -> doc.getEditor().getName(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     * 
     * @param editorName the name of the editor
     * @return a list of author names
     */
    public List<String> getAuthorsForEditor(String editorName) {
        return documents.stream()
                .filter(doc -> doc.getEditor().getName().equals(editorName))
                .map(Document::getAuthor)
                .collect(Collectors.toList());
    }
}

 class Main {
    public static void main(String[] args) {
        // Example usage
        FileSystem fileSystem = new FileSystem();

        Editor textEditor = new Editor("Text Editor");
        Editor imageEditor = new Editor("Image Editor");
        Editor videoEditor = new Editor("Video Editor");

        Document doc1 = new Document("Doc1", LocalDate.of(2022, 1, 1), "Author1", 100, textEditor);
        Document doc2 = new Document("Doc2", LocalDate.of(2022, 1, 15), "Author2", 200, imageEditor);
        Document doc3 = new Document("Doc3", LocalDate.of(2022, 2, 1), "Author3", 300, videoEditor);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);

        System.out.println("Total size: " + fileSystem.calculateTotalSize());
        System.out.println("Average size per editor: " + fileSystem.computeAverageSizePerEditor());
        System.out.println("Documents created after 2022-01-10: " + fileSystem.countDocumentsCreatedAfter(LocalDate.of(2022, 1, 10)));
        System.out.println("Documents per editor: " + fileSystem.countDocumentsPerEditor());
        System.out.println("Authors for Text Editor: " + fileSystem.getAuthorsForEditor("Text Editor"));
    }
}