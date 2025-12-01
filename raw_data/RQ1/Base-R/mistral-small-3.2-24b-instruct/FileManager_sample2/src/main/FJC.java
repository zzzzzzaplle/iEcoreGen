import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Document {
    private String name;
    private Date creationDate;
    private String author;
    private long size;
    private Editor editor;

    /**
     * Default constructor for Document.
     */
    public Document() {
    }

    /**
     * Constructor for Document with all fields.
     * @param name The name of the document.
     * @param creationDate The creation date of the document.
     * @param author The author of the document.
     * @param size The size of the document.
     * @param editor The editor used for the document.
     */
    public Document(String name, Date creationDate, String author, long size, Editor editor) {
        this.name = name;
        this.creationDate = creationDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

class Editor {
    private String name;

    /**
     * Default constructor for Editor.
     */
    public Editor() {
    }

    /**
     * Constructor for Editor with name.
     * @param name The name of the editor.
     */
    public Editor(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Default constructor for FileSystem.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
        // Initialize default editors
        this.editors.add(new Editor("Text Editor"));
        this.editors.add(new Editor("Image Editor"));
        this.editors.add(new Editor("Video Editor"));
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
     * @return A list of all documents.
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(this.documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * @return The total size of all documents, or 0 if there are no documents.
     */
    public long calculateTotalSize() {
        if (documents.isEmpty()) {
            return 0;
        }
        return documents.stream().mapToLong(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     * @return A map with editor names as keys and average sizes as values.
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        Map<String, List<Long>> editorSizes = new HashMap<>();
        for (Editor editor : editors) {
            editorSizes.put(editor.getName(), new ArrayList<>());
        }

        for (Document document : documents) {
            String editorName = document.getEditor().getName();
            editorSizes.get(editorName).add(document.getSize());
        }

        Map<String, Double> averageSizes = new HashMap<>();
        for (Map.Entry<String, List<Long>> entry : editorSizes.entrySet()) {
            List<Long> sizes = entry.getValue();
            if (!sizes.isEmpty()) {
                double average = sizes.stream().mapToLong(Long::longValue).average().orElse(0.0);
                averageSizes.put(entry.getKey(), average);
            } else {
                averageSizes.put(entry.getKey(), 0.0);
            }
        }

        return averageSizes;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * @param date The date to compare against.
     * @return The number of documents created after the specified date.
     */
    public long countDocumentsAfterDate(Date date) {
        return documents.stream().filter(doc -> doc.getCreationDate().after(date)).count();
    }

    /**
     * Counts the number of documents for each editor in the file system.
     * @return A map with editor names as keys and document counts as values.
     */
    public Map<String, Long> countDocumentsPerEditor() {
        Map<String, Long> documentCounts = new HashMap<>();
        for (Editor editor : editors) {
            documentCounts.put(editor.getName(), 0L);
        }

        for (Document document : documents) {
            String editorName = document.getEditor().getName();
            documentCounts.put(editorName, documentCounts.get(editorName) + 1);
        }

        return documentCounts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor in the file system.
     * @param editorName The name of the editor to filter by.
     * @return A list of author names.
     */
    public List<String> getAuthorsByEditor(String editorName) {
        List<String> authors = new ArrayList<>();
        for (Document document : documents) {
            if (document.getEditor().getName().equals(editorName)) {
                authors.add(document.getAuthor());
            }
        }
        return authors;
    }
}