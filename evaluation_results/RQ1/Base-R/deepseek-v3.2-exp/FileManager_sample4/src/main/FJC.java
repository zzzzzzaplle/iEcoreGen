import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public Document() {
    }

    /**
     * Gets the name of the document.
     * @return the document name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the document.
     * @param name the document name to set
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
     * @return the document size
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size of the document.
     * @param size the document size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Gets the editor associated with the document.
     * @return the editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor associated with the document.
     * @param editor the editor to set
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Represents an editor that can be used to manage documents.
 */
class Editor {
    private String name;

    /**
     * Default constructor for Editor.
     */
    public Editor() {
    }

    /**
     * Gets the name of the editor.
     * @return the editor name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the editor.
     * @param name the editor name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a file system that contains multiple documents.
 */
class FileSystem {
    private List<Document> documents;

    /**
     * Default constructor for FileSystem.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
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
     * Adds a document to the file system.
     * @param document the document to add
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * Removes a document from the file system.
     * @param document the document to remove
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
     * Outputs the sum of all document sizes. Returns 0 if there is no document.
     * @return the total size of all documents, or 0 if there are no documents
     */
    public long calculateTotalSize() {
        long totalSize = 0;
        for (Document doc : documents) {
            totalSize += doc.getSize();
        }
        return totalSize;
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     * The average is calculated as total size divided by count for each editor type.
     * @return a map where keys are editor names and values are average sizes
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        Map<String, Long> totalSizePerEditor = new HashMap<>();
        Map<String, Integer> countPerEditor = new HashMap<>();
        
        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            long size = doc.getSize();
            
            totalSizePerEditor.put(editorName, totalSizePerEditor.getOrDefault(editorName, 0L) + size);
            countPerEditor.put(editorName, countPerEditor.getOrDefault(editorName, 0) + 1);
        }
        
        Map<String, Double> averageSizePerEditor = new HashMap<>();
        for (String editorName : totalSizePerEditor.keySet()) {
            long totalSize = totalSizePerEditor.get(editorName);
            int count = countPerEditor.get(editorName);
            double average = count > 0 ? (double) totalSize / count : 0.0;
            averageSizePerEditor.put(editorName, average);
        }
        
        return averageSizePerEditor;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * @param date the specified date to compare against
     * @return the count of documents created after the specified date
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
     * Counts the number of documents for each editor in the file system.
     * @return a map where keys are editor names and values are document counts
     */
    public Map<String, Integer> countDocumentsPerEditor() {
        Map<String, Integer> countPerEditor = new HashMap<>();
        
        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            countPerEditor.put(editorName, countPerEditor.getOrDefault(editorName, 0) + 1);
        }
        
        return countPerEditor;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor in the file system.
     * @param editorName the name of the editor to filter by
     * @return a set of unique author names whose documents use the specified editor
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
}