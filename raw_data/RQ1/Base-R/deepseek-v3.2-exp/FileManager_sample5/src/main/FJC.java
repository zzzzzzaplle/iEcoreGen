import java.util.ArrayList;
import java.util.Date;
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
    private Date creationDate;
    private String author;
    private long size;
    private Editor editor;

    /**
     * Default constructor.
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
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the document.
     * @param creationDate the creation date to set
     */
    public void setCreationDate(Date creationDate) {
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
     * Gets the editor assigned to the document.
     * @return the editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor assigned to the document.
     * @param editor the editor to set
     */
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
     * Default constructor.
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
     * Default constructor.
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
     * @return the sum of all document sizes, or 0 if there are no documents
     */
    public long calculateTotalSize() {
        long totalSize = 0;
        for (Document doc : documents) {
            totalSize += doc.getSize();
        }
        return totalSize;
    }

    /**
     * Computes the average size of documents for each editor type in the file system.
     * @return a map where keys are editor names and values are average sizes
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        Map<String, List<Long>> sizesByEditor = new HashMap<>();
        
        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            sizesByEditor.putIfAbsent(editorName, new ArrayList<>());
            sizesByEditor.get(editorName).add(doc.getSize());
        }
        
        Map<String, Double> averages = new HashMap<>();
        for (Map.Entry<String, List<Long>> entry : sizesByEditor.entrySet()) {
            List<Long> sizes = entry.getValue();
            if (sizes.isEmpty()) {
                averages.put(entry.getKey(), 0.0);
            } else {
                double sum = 0;
                for (long size : sizes) {
                    sum += size;
                }
                averages.put(entry.getKey(), sum / sizes.size());
            }
        }
        return averages;
    }

    /**
     * Counts the number of documents created after a specified date.
     * @param date the cutoff date (documents created after this date will be counted)
     * @return the number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        int count = 0;
        for (Document doc : documents) {
            if (doc.getCreationDate().after(date)) {
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
        Map<String, Integer> counts = new HashMap<>();
        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            counts.put(editorName, counts.getOrDefault(editorName, 0) + 1);
        }
        return counts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     * @param editorName the name of the editor to filter by
     * @return a set of author names who have documents edited with the specified editor
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