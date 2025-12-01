import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an editor in the file system.
 */
class Editor {
    private String name;

    /**
     * Constructs a new Editor.
     */
    public Editor() {}

    /**
     * Constructs a new Editor with the given name.
     * @param name The name of the editor.
     */
    public Editor(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the editor.
     * @return The name of the editor.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the editor.
     * @param name The name of the editor.
     */
    public void setName(String name) {
        this.name = name;
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
     * Constructs a new Document.
     */
    public Document() {}

    /**
     * Constructs a new Document with the given name, creation date, author, size, and editor.
     * @param name The name of the document.
     * @param creationDate The creation date of the document.
     * @param author The author of the document.
     * @param size The size of the document.
     * @param editor The editor used to create the document.
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
     * @return The name of the document.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the document.
     * @param name The name of the document.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the creation date of the document.
     * @return The creation date of the document.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the document.
     * @param creationDate The creation date of the document.
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the author of the document.
     * @return The author of the document.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the document.
     * @param author The author of the document.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the size of the document.
     * @return The size of the document.
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size of the document.
     * @param size The size of the document.
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Gets the editor used to create the document.
     * @return The editor used to create the document.
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor used to create the document.
     * @param editor The editor used to create the document.
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Represents a file system that contains documents and editors.
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
     * @param document The document to add.
     */
    public void addDocument(Document document) {
        this.documents.add(document);
    }

    /**
     * Removes a document from the file system.
     * @param document The document to remove.
     */
    public void removeDocument(Document document) {
        this.documents.remove(document);
    }

    /**
     * Lists all documents in the file system.
     * @return A list of all documents in the file system.
     */
    public List<Document> listDocuments() {
        return this.documents;
    }

    /**
     * Adds an editor to the file system.
     * @param editor The editor to add.
     */
    public void addEditor(Editor editor) {
        this.editors.add(editor);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * @return The total size of all documents in the file system.
     */
    public long calculateTotalSize() {
        return documents.stream().mapToLong(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     * @return A string representation of the average size of documents for each editor.
     */
    public String computeAverageSizePerEditor() {
        StringBuilder result = new StringBuilder();
        for (Editor editor : editors) {
            double averageSize = documents.stream()
                    .filter(document -> document.getEditor().equals(editor))
                    .mapToLong(Document::getSize)
                    .average()
                    .orElse(0);
            result.append(editor.getName()).append(": ").append(averageSize).append("\n");
        }
        return result.toString();
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * @param date The date to compare with the creation date of documents.
     * @return The number of documents created after the specified date.
     */
    public long countDocumentsCreatedAfter(LocalDate date) {
        return documents.stream()
                .filter(document -> document.getCreationDate().isAfter(date))
                .count();
    }

    /**
     * Counts the number of documents for each editor in the file system.
     * @return A string representation of the count of documents for each editor.
     */
    public String countDocumentsPerEditor() {
        StringBuilder result = new StringBuilder();
        for (Editor editor : editors) {
            long count = documents.stream()
                    .filter(document -> document.getEditor().equals(editor))
                    .count();
            result.append(editor.getName()).append(": ").append(count).append("\n");
        }
        return result.toString();
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor in the file system.
     * @param editor The editor to filter documents by.
     * @return A list of authors whose documents are edited with the specified editor.
     */
    public List<String> getAuthorsForEditor(Editor editor) {
        return documents.stream()
                .filter(document -> document.getEditor().equals(editor))
                .map(Document::getAuthor)
                .collect(Collectors.toList());
    }
}

 class Main {
    public static void main(String[] args) {
        // Example usage:
        FileSystem fileSystem = new FileSystem();

        Editor textEditor = new Editor("Text Editor");
        Editor imageEditor = new Editor("Image Editor");
        Editor videoEditor = new Editor("Video Editor");

        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);

        Document doc1 = new Document("Doc1", LocalDate.of(2022, 1, 1), "Author1", 100, textEditor);
        Document doc2 = new Document("Doc2", LocalDate.of(2022, 1, 15), "Author2", 200, imageEditor);
        Document doc3 = new Document("Doc3", LocalDate.of(2022, 2, 1), "Author3", 300, videoEditor);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);

        System.out.println("Total size: " + fileSystem.calculateTotalSize());
        System.out.println("Average size per editor:\n" + fileSystem.computeAverageSizePerEditor());
        System.out.println("Documents created after 2022-01-10: " + fileSystem.countDocumentsCreatedAfter(LocalDate.of(2022, 1, 10)));
        System.out.println("Documents per editor:\n" + fileSystem.countDocumentsPerEditor());
        System.out.println("Authors for Text Editor: " + fileSystem.getAuthorsForEditor(textEditor));
    }
}