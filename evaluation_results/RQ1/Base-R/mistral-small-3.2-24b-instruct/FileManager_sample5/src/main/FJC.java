import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Document {
    private String name;
    private Date creationDate;
    private String author;
    private int size;
    private Editor editor;

    /**
     * Default constructor for the Document class.
     */
    public Document() {
    }

    /**
     * Constructor for the Document class with all fields.
     * @param name The name of the document.
     * @param creationDate The creation date of the document.
     * @param author The author of the document.
     * @param size The size of the document.
     * @param editor The editor used for the document.
     */
    public Document(String name, Date creationDate, String author, int size, Editor editor) {
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

class Editor {
    private String name;

    /**
     * Default constructor for the Editor class.
     */
    public Editor() {
    }

    /**
     * Constructor for the Editor class with the editor's name.
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
    private Map<String, Editor> editors;

    /**
     * Default constructor for the FileSystem class.
     */
    public FileSystem() {
        documents = new ArrayList<>();
        editors = new HashMap<>();
        initializeEditors();
    }

    /**
     * Initializes the default editors.
     */
    private void initializeEditors() {
        editors.put("Text Editor", new Editor("Text Editor"));
        editors.put("Image Editor", new Editor("Image Editor"));
        editors.put("Video Editor", new Editor("Video Editor"));
    }

    /**
     * Adds a document to the file system.
     * @param document The document to add.
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * Removes a document from the file system.
     * @param document The document to remove.
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Lists all documents in the file system.
     * @return A list of all documents.
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * @return The sum of all document sizes. Returns 0 if there are no documents.
     */
    public int calculateTotalSize() {
        if (documents.isEmpty()) {
            return 0;
        }
        return documents.stream().mapToInt(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor.
     * @return A map with editor names as keys and average sizes as values.
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        Map<String, Double> averageSizes = new HashMap<>();
        Map<String, List<Document>> editorDocuments = new HashMap<>();

        for (Document document : documents) {
            String editorName = document.getEditor().getName();
            editorDocuments.computeIfAbsent(editorName, k -> new ArrayList<>()).add(document);
        }

        for (Map.Entry<String, List<Document>> entry : editorDocuments.entrySet()) {
            List<Document> docs = entry.getValue();
            double average = docs.stream().mapToInt(Document::getSize).average().orElse(0.0);
            averageSizes.put(entry.getKey(), average);
        }

        return averageSizes;
    }

    /**
     * Counts the number of documents created after a specified date.
     * @param date The date to compare against.
     * @return The number of documents created after the specified date.
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) documents.stream().filter(doc -> doc.getCreationDate().after(date)).count();
    }

    /**
     * Counts the number of documents for each editor.
     * @return A map with editor names as keys and document counts as values.
     */
    public Map<String, Integer> countDocumentsPerEditor() {
        Map<String, Integer> documentCounts = new HashMap<>();

        for (Document document : documents) {
            String editorName = document.getEditor().getName();
            documentCounts.put(editorName, documentCounts.getOrDefault(editorName, 0) + 1);
        }

        return documentCounts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     * @param editorName The name of the editor.
     * @return A list of author names.
     */
    public List<String> getAuthorsByEditor(String editorName) {
        List<String> authors = new ArrayList<>();
        Editor editor = editors.get(editorName);

        if (editor != null) {
            authors = documents.stream()
                    .filter(doc -> doc.getEditor().equals(editor))
                    .map(Document::getAuthor)
                    .distinct()
                    .collect(Collectors.toList());
        }

        return authors;
    }
}