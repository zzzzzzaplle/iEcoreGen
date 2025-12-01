import java.util.ArrayList;
import java.util.Date;
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
    private Date creationDate;
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
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date
     * @param creationDate the creation date to set
     */
    public void setCreationDate(Date creationDate) {
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
 * Represents an editor for managing documents
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
 * Represents the file system containing multiple documents
 */
class FileSystem {
    private List<Document> documents;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;

    /**
     * Default constructor - initializes editors and document list
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.textEditor = new Editor();
        this.textEditor.setName("Text Editor");
        this.imageEditor = new Editor();
        this.imageEditor.setName("Image Editor");
        this.videoEditor = new Editor();
        this.videoEditor.setName("Video Editor");
    }

    /**
     * Gets all documents in the file system
     * @return list of all documents
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
     * Gets the text editor
     * @return the text editor
     */
    public Editor getTextEditor() {
        return textEditor;
    }

    /**
     * Sets the text editor
     * @param textEditor the text editor to set
     */
    public void setTextEditor(Editor textEditor) {
        this.textEditor = textEditor;
    }

    /**
     * Gets the image editor
     * @return the image editor
     */
    public Editor getImageEditor() {
        return imageEditor;
    }

    /**
     * Sets the image editor
     * @param imageEditor the image editor to set
     */
    public void setImageEditor(Editor imageEditor) {
        this.imageEditor = imageEditor;
    }

    /**
     * Gets the video editor
     * @return the video editor
     */
    public Editor getVideoEditor() {
        return videoEditor;
    }

    /**
     * Sets the video editor
     * @param videoEditor the video editor to set
     */
    public void setVideoEditor(Editor videoEditor) {
        this.videoEditor = videoEditor;
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
     * @return list of all document names
     */
    public List<String> listAllDocuments() {
        List<String> documentNames = new ArrayList<>();
        for (Document doc : documents) {
            documentNames.add(doc.getName());
        }
        return documentNames;
    }

    /**
     * Calculates the total size of all documents in the file system.
     * Outputs the sum of all document sizes. Returns 0 if there is no document.
     * @return the total size of all documents, or 0 if no documents exist
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
     * @return a map containing editor names as keys and average document sizes as values
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        Map<String, Long> totalSizePerEditor = new HashMap<>();
        Map<String, Integer> countPerEditor = new HashMap<>();
        Map<String, Double> averageSizePerEditor = new HashMap<>();

        // Initialize maps for each editor type
        totalSizePerEditor.put(textEditor.getName(), 0L);
        totalSizePerEditor.put(imageEditor.getName(), 0L);
        totalSizePerEditor.put(videoEditor.getName(), 0L);
        
        countPerEditor.put(textEditor.getName(), 0);
        countPerEditor.put(imageEditor.getName(), 0);
        countPerEditor.put(videoEditor.getName(), 0);

        // Calculate total size and count for each editor
        for (Document doc : documents) {
            if (doc.getEditor() != null) {
                String editorName = doc.getEditor().getName();
                long currentTotal = totalSizePerEditor.getOrDefault(editorName, 0L);
                int currentCount = countPerEditor.getOrDefault(editorName, 0);
                
                totalSizePerEditor.put(editorName, currentTotal + doc.getSize());
                countPerEditor.put(editorName, currentCount + 1);
            }
        }

        // Calculate average for each editor
        for (Map.Entry<String, Long> entry : totalSizePerEditor.entrySet()) {
            String editorName = entry.getKey();
            long totalSize = entry.getValue();
            int count = countPerEditor.get(editorName);
            
            double average = count > 0 ? (double) totalSize / count : 0.0;
            averageSizePerEditor.put(editorName, average);
        }

        return averageSizePerEditor;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * @param specifiedDate the date to compare against
     * @return the count of documents created after the specified date
     * @throws IllegalArgumentException if specifiedDate is null
     */
    public int countDocumentsAfterDate(Date specifiedDate) {
        if (specifiedDate == null) {
            throw new IllegalArgumentException("Specified date cannot be null");
        }
        
        int count = 0;
        for (Document doc : documents) {
            if (doc.getCreationDate() != null && doc.getCreationDate().after(specifiedDate)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents for each editor in the file system.
     * @return a map containing editor names as keys and document counts as values
     */
    public Map<String, Integer> countDocumentsPerEditor() {
        Map<String, Integer> countPerEditor = new HashMap<>();
        
        // Initialize counts for each editor type
        countPerEditor.put(textEditor.getName(), 0);
        countPerEditor.put(imageEditor.getName(), 0);
        countPerEditor.put(videoEditor.getName(), 0);

        // Count documents for each editor
        for (Document doc : documents) {
            if (doc.getEditor() != null) {
                String editorName = doc.getEditor().getName();
                int currentCount = countPerEditor.getOrDefault(editorName, 0);
                countPerEditor.put(editorName, currentCount + 1);
            }
        }

        return countPerEditor;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor in the file system.
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
            if (doc.getEditor() != null && 
                doc.getEditor().getName().equals(editorName) && 
                doc.getAuthor() != null) {
                authors.add(doc.getAuthor());
            }
        }
        return authors;
    }
}