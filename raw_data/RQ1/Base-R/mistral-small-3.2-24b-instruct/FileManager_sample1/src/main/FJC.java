import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Get the name of the document.
     * @return The name of the document.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the document.
     * @param name The name of the document.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the creation date of the document.
     * @return The creation date of the document.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Set the creation date of the document.
     * @param creationDate The creation date of the document.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Get the author of the document.
     * @return The author of the document.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the author of the document.
     * @param author The author of the document.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get the size of the document.
     * @return The size of the document.
     */
    public long getSize() {
        return size;
    }

    /**
     * Set the size of the document.
     * @param size The size of the document.
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Get the editor of the document.
     * @return The editor of the document.
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Set the editor of the document.
     * @param editor The editor of the document.
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Represents an editor in the file system.
 */
class Editor {
    private String name;

    /**
     * Default constructor.
     */
    public Editor() {
    }

    /**
     * Get the name of the editor.
     * @return The name of the editor.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the editor.
     * @param name The name of the editor.
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents the file system.
 */
class FileSystem {
    private List<Document> documents;
    private Map<String, Editor> editors;

    /**
     * Default constructor.
     */
    public FileSystem() {
        documents = new ArrayList<>();
        editors = new HashMap<>();
        initializeEditors();
    }

    /**
     * Initialize the default editors.
     */
    private void initializeEditors() {
        Editor textEditor = new Editor();
        textEditor.setName("Text Editor");
        editors.put("Text Editor", textEditor);

        Editor imageEditor = new Editor();
        imageEditor.setName("Image Editor");
        editors.put("Image Editor", imageEditor);

        Editor videoEditor = new Editor();
        videoEditor.setName("Video Editor");
        editors.put("Video Editor", videoEditor);
    }

    /**
     * Add a document to the file system.
     * @param document The document to add.
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * Remove a document from the file system.
     * @param document The document to remove.
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Get all documents in the file system.
     * @return The list of documents.
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Calculate the total size of all documents in the file system.
     * @return The total size of all documents.
     */
    public long calculateTotalSize() {
        long totalSize = 0;
        for (Document document : documents) {
            totalSize += document.getSize();
        }
        return totalSize;
    }

    /**
     * Compute the average size of all documents for each editor in the file system.
     * @return A map with editor names as keys and average sizes as values.
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        Map<String, Double> averageSizes = new HashMap<>();
        Map<String, Long> totalSizes = new HashMap<>();
        Map<String, Integer> documentCounts = new HashMap<>();

        for (Document document : documents) {
            String editorName = document.getEditor().getName();
            totalSizes.put(editorName, totalSizes.getOrDefault(editorName, 0L) + document.getSize());
            documentCounts.put(editorName, documentCounts.getOrDefault(editorName, 0) + 1);
        }

        for (Map.Entry<String, Long> entry : totalSizes.entrySet()) {
            String editorName = entry.getKey();
            long totalSize = entry.getValue();
            int count = documentCounts.getOrDefault(editorName, 0);
            if (count > 0) {
                averageSizes.put(editorName, (double) totalSize / count);
            }
        }

        return averageSizes;
    }

    /**
     * Count the number of documents in the file system that were created after a specified date.
     * @param date The date to compare.
     * @return The count of documents created after the specified date.
     */
    public int countDocumentsAfterDate(Date date) {
        int count = 0;
        for (Document document : documents) {
            if (document.getCreationDate().after(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Count the number of documents for each editor in the file system.
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
     * Retrieve the names of all authors whose documents are edited with a specified editor in the file system.
     * @param editorName The name of the editor.
     * @return The list of author names.
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