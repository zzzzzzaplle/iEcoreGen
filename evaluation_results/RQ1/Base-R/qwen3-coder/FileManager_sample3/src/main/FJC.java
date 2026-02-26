import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * Represents a document in the file system
 */
class Document {
    private String name;
    private LocalDate creationDate;
    private String author;
    private int size;
    private String editor;

    /**
     * Default constructor for Document
     */
    public Document() {
        this.name = "";
        this.creationDate = LocalDate.now();
        this.author = "";
        this.size = 0;
        this.editor = "";
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
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

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}

/**
 * Represents an editor that can be used to create documents
 */
class Editor {
    private String name;

    /**
     * Default constructor for Editor
     */
    public Editor() {
        this.name = "";
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a file system that manages documents
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Default constructor for FileSystem
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
        initializeEditors();
    }

    /**
     * Initializes the system with the three required editors
     */
    private void initializeEditors() {
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        editors.add(textEditor);

        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        editors.add(imageEditor);

        Editor videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        editors.add(videoEditor);
    }

    /**
     * Adds a document to the file system
     * @param document the document to add
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * Removes a document from the file system
     * @param document the document to remove
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Lists all documents in the file system
     * @return a list of all documents
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system
     * @return the sum of all document sizes, or 0 if there are no documents
     */
    public int calculateTotalSize() {
        return documents.stream()
                .mapToInt(Document::getSize)
                .sum();
    }

    /**
     * Computes the average size of all documents for each editor in the file system
     * @return a map with editor names as keys and average sizes as values
     */
    public Map<String, Double> computeAverageSizeByEditor() {
        Map<String, Double> averageSizes = new HashMap<>();
        
        // Initialize all editors with 0.0 to ensure all are included in result
        for (Editor editor : editors) {
            averageSizes.put(editor.getName(), 0.0);
        }
        
        // Group documents by editor and calculate averages
        Map<String, List<Document>> documentsByEditor = documents.stream()
                .collect(Collectors.groupingBy(Document::getEditor));
        
        for (Map.Entry<String, List<Document>> entry : documentsByEditor.entrySet()) {
            String editorName = entry.getKey();
            List<Document> editorDocuments = entry.getValue();
            
            double average = editorDocuments.stream()
                    .mapToInt(Document::getSize)
                    .average()
                    .orElse(0.0);
            
            averageSizes.put(editorName, average);
        }
        
        return averageSizes;
    }

    /**
     * Counts the number of documents created after a specified date
     * @param date the date to compare against
     * @return the count of documents created after the specified date
     */
    public int countDocumentsCreatedAfter(LocalDate date) {
        return (int) documents.stream()
                .filter(document -> document.getCreationDate().isAfter(date))
                .count();
    }

    /**
     * Counts the number of documents for each editor in the file system
     * @return a map with editor names as keys and document counts as values
     */
    public Map<String, Integer> countDocumentsByEditor() {
        Map<String, Integer> documentCounts = new HashMap<>();
        
        // Initialize all editors with 0 to ensure all are included in result
        for (Editor editor : editors) {
            documentCounts.put(editor.getName(), 0);
        }
        
        // Count documents for each editor
        Map<String, Long> counts = documents.stream()
                .collect(Collectors.groupingBy(Document::getEditor, Collectors.counting()));
        
        // Convert Long to Integer and update the result map
        counts.forEach((editor, count) -> documentCounts.put(editor, count.intValue()));
        
        return documentCounts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor
     * @param editorName the name of the editor to filter by
     * @return a set of author names
     */
    public Set<String> getAuthorsByEditor(String editorName) {
        return documents.stream()
                .filter(document -> document.getEditor().equals(editorName))
                .map(Document::getAuthor)
                .collect(Collectors.toSet());
    }

    // Getters and Setters
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