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
     * Default constructor for Document.
     */
    public Document() {}

    /**
     * Constructs a document with the given details.
     * @param name The name of the document.
     * @param creationDate The creation date of the document.
     * @param author The author of the document.
     * @param size The size of the document.
     * @param editor The editor used for the document.
     */
    public Document(String name, LocalDate creationDate, String author, long size, Editor editor) {
        this.name = name;
        this.creationDate = creationDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
    public Editor getEditor() { return editor; }
    public void setEditor(Editor editor) { this.editor = editor; }
}

/**
 * Represents an editor in the file system.
 */
class Editor {
    private String name;

    /**
     * Default constructor for Editor.
     */
    public Editor() {}

    /**
     * Constructs an editor with the given name.
     * @param name The name of the editor.
     */
    public Editor(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

/**
 * Represents the file system containing documents and editors.
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Default constructor for FileSystem.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
    }

    /**
     * Adds a document to the file system.
     * @param document The document to be added.
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * Removes a document from the file system.
     * @param document The document to be removed.
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Lists all documents in the file system.
     * @return A list of all documents.
     */
    public List<Document> listDocuments() {
        return documents;
    }

    /**
     * Adds an editor to the file system.
     * @param editor The editor to be added.
     */
    public void addEditor(Editor editor) {
        editors.add(editor);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * @return The total size of all documents.
     */
    public long calculateTotalSize() {
        return documents.stream().mapToLong(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor.
     * @return A list of editors with their average document sizes.
     */
    public List<EditorAverageSize> computeAverageSizePerEditor() {
        return editors.stream().map(editor -> {
            double averageSize = documents.stream()
                    .filter(doc -> doc.getEditor().equals(editor))
                    .mapToLong(Document::getSize)
                    .average()
                    .orElse(0.0);
            return new EditorAverageSize(editor, averageSize);
        }).collect(Collectors.toList());
    }

    /**
     * Counts the number of documents created after a specified date.
     * @param date The date after which documents were created.
     * @return The number of documents created after the specified date.
     */
    public long countDocumentsCreatedAfter(LocalDate date) {
        return documents.stream()
                .filter(doc -> doc.getCreationDate().isAfter(date))
                .count();
    }

    /**
     * Counts the number of documents for each editor.
     * @return A list of editors with their document counts.
     */
    public List<EditorDocumentCount> countDocumentsPerEditor() {
        return editors.stream().map(editor -> {
            long count = documents.stream()
                    .filter(doc -> doc.getEditor().equals(editor))
                    .count();
            return new EditorDocumentCount(editor, count);
        }).collect(Collectors.toList());
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     * @param editor The editor for which to retrieve authors.
     * @return A list of author names.
     */
    public List<String> getAuthorsForEditor(Editor editor) {
        return documents.stream()
                .filter(doc -> doc.getEditor().equals(editor))
                .map(Document::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }

    // Getters and setters
    public List<Document> getDocuments() { return documents; }
    public void setDocuments(List<Document> documents) { this.documents = documents; }
    public List<Editor> getEditors() { return editors; }
    public void setEditors(List<Editor> editors) { this.editors = editors; }
}

/**
 * Helper class to hold an editor and its average document size.
 */
class EditorAverageSize {
    private Editor editor;
    private double averageSize;

    /**
     * Constructs an EditorAverageSize object.
     * @param editor The editor.
     * @param averageSize The average size of documents for the editor.
     */
    public EditorAverageSize(Editor editor, double averageSize) {
        this.editor = editor;
        this.averageSize = averageSize;
    }

    // Getters and setters
    public Editor getEditor() { return editor; }
    public void setEditor(Editor editor) { this.editor = editor; }
    public double getAverageSize() { return averageSize; }
    public void setAverageSize(double averageSize) { this.averageSize = averageSize; }
}

/**
 * Helper class to hold an editor and its document count.
 */
class EditorDocumentCount {
    private Editor editor;
    private long count;

    /**
     * Constructs an EditorDocumentCount object.
     * @param editor The editor.
     * @param count The number of documents for the editor.
     */
    public EditorDocumentCount(Editor editor, long count) {
        this.editor = editor;
        this.count = count;
    }

    // Getters and setters
    public Editor getEditor() { return editor; }
    public void setEditor(Editor editor) { this.editor = editor; }
    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }
}