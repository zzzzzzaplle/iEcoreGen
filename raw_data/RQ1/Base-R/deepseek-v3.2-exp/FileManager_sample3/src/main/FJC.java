import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a document in the file system
 */
class Document {
    private String name;
    private LocalDate creationDate;
    private String author;
    private long size;
    private Editor editor;

    /**
     * Default constructor
     */
    public Document() {
    }

    /**
     * Gets the document name
     * @return the document name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the document name
     * @param name the document name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the creation date
     * @return the creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date
     * @param creationDate the creation date to set
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the author
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the size
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Gets the editor
     * @return the editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor
     * @param editor the editor to set
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Represents an editor for documents
 */
class Editor {
    private String name;

    /**
     * Default constructor
     */
    public Editor() {
    }

    /**
     * Gets the editor name
     * @return the editor name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the editor name
     * @param name the editor name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents the file system containing documents
 */
class FileSystem {
    private List<Document> documents;

    /**
     * Default constructor
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
    }

    /**
     * Gets the documents list
     * @return the documents list
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the documents list
     * @param documents the documents list to set
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
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
     * @return true if document was removed, false otherwise
     */
    public boolean removeDocument(Document document) {
        return documents.remove(document);
    }

    /**
     * Lists all documents in the file system
     * @return list of all documents
     */
    public List<Document> listAllDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system
     * @return the sum of all document sizes, returns 0 if there are no documents
     */
    public long calculateTotalSize() {
        if (documents.isEmpty()) {
            return 0;
        }
        
        long totalSize = 0;
        for (Document doc : documents) {
            totalSize += doc.getSize();
        }
        return totalSize;
    }

    /**
     * Computes the average size of all documents for each editor type
     * @return a map with editor names as keys and average sizes as values
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        Map<String, List<Long>> editorSizes = new HashMap<>();
        
        // Group document sizes by editor name
        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            editorSizes.putIfAbsent(editorName, new ArrayList<>());
            editorSizes.get(editorName).add(doc.getSize());
        }
        
        // Calculate average for each editor
        Map<String, Double> averageSizes = new HashMap<>();
        for (Map.Entry<String, List<Long>> entry : editorSizes.entrySet()) {
            String editorName = entry.getKey();
            List<Long> sizes = entry.getValue();
            
            if (sizes.isEmpty()) {
                averageSizes.put(editorName, 0.0);
            } else {
                double sum = 0;
                for (long size : sizes) {
                    sum += size;
                }
                averageSizes.put(editorName, sum / sizes.size());
            }
        }
        
        return averageSizes;
    }

    /**
     * Counts the number of documents created after a specified date
     * @param date the cutoff date (documents created after this date will be counted)
     * @return the number of documents created after the specified date
     * @throws IllegalArgumentException if date is null
     */
    public int countDocumentsAfterDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        
        int count = 0;
        for (Document doc : documents) {
            if (doc.getCreationDate().isAfter(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents for each editor type
     * @return a map with editor names as keys and document counts as values
     */
    public Map<String, Integer> countDocumentsPerEditor() {
        Map<String, Integer> editorCounts = new HashMap<>();
        
        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            editorCounts.put(editorName, editorCounts.getOrDefault(editorName, 0) + 1);
        }
        
        return editorCounts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor
     * @param editorName the name of the editor to filter by
     * @return a set of unique author names who have documents edited with the specified editor
     * @throws IllegalArgumentException if editorName is null or empty
     */
    public Set<String> getAuthorsByEditor(String editorName) {
        if (editorName == null || editorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Editor name cannot be null or empty");
        }
        
        Set<String> authors = new HashSet<>();
        for (Document doc : documents) {
            if (editorName.equals(doc.getEditor().getName())) {
                authors.add(doc.getAuthor());
            }
        }
        return authors;
    }
}