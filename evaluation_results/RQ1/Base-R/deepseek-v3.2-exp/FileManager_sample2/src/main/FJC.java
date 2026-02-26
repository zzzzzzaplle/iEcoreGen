import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a file system that contains and manages documents.
 */
 class FileSystem {
    private List<Document> documents;

    /**
     * Constructs a new FileSystem with an empty list of documents.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
    }

    /**
     * Adds a document to the file system.
     * @param document the document to be added
     */
    public void addDocument(Document document) {
        if (document != null) {
            documents.add(document);
        }
    }

    /**
     * Removes a document from the file system.
     * @param document the document to be removed
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Lists all documents in the file system.
     * @return a list of all documents
     */
    public List<Document> listAllDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * @return the sum of all document sizes, 0 if there are no documents
     */
    public long calculateTotalSize() {
        long totalSize = 0;
        for (Document doc : documents) {
            totalSize += doc.getSize();
        }
        return totalSize;
    }

    /**
     * Computes the average size of documents for each editor type.
     * @return a map where keys are editor names and values are average sizes
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        Map<String, List<Long>> sizesByEditor = new HashMap<>();
        
        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            sizesByEditor.putIfAbsent(editorName, new ArrayList<>());
            sizesByEditor.get(editorName).add(doc.getSize());
        }
        
        Map<String, Double> averageSizes = new HashMap<>();
        for (Map.Entry<String, List<Long>> entry : sizesByEditor.entrySet()) {
            String editorName = entry.getKey();
            List<Long> sizes = entry.getValue();
            double average = sizes.stream().mapToLong(Long::longValue).average().orElse(0.0);
            averageSizes.put(editorName, average);
        }
        
        return averageSizes;
    }

    /**
     * Counts the number of documents created after a specified date.
     * @param date the cutoff date (documents created after this date are counted)
     * @return the number of documents created after the specified date
     */
    public int countDocumentsAfterDate(LocalDate date) {
        int count = 0;
        for (Document doc : documents) {
            if (doc.getCreationDate().isAfter(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents for each editor type.
     * @return a map where keys are editor names and values are document counts
     */
    public Map<String, Integer> countDocumentsPerEditor() {
        Map<String, Integer> countByEditor = new HashMap<>();
        
        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            countByEditor.put(editorName, countByEditor.getOrDefault(editorName, 0) + 1);
        }
        
        return countByEditor;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     * @param editorName the name of the editor to filter by
     * @return a set of author names
     */
    public Set<String> getAuthorsByEditor(String editorName) {
        Set<String> authors = new HashSet<>();
        for (Document doc : documents) {
            if (doc.getEditor().getName().equals(editorName)) {
                authors.add(doc.getAuthor());
            }
        }
        return authors;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
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
     * Constructs a new Document with default values.
     */
    public Document() {
        this.name = "";
        this.creationDate = LocalDate.now();
        this.author = "";
        this.size = 0;
        this.editor = null;
    }

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

/**
 * Represents an editor that can be assigned to documents.
 */
 class Editor {
    private String name;

    /**
     * Constructs a new Editor with a default name.
     */
    public Editor() {
        this.name = "";
    }

    /**
     * Constructs a new Editor with the specified name.
     * @param name the name of the editor
     */
    public Editor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}