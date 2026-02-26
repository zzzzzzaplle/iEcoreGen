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
    private int size;
    private String editor;

    /**
     * Default constructor for Document.
     */
    public Document() {
        this.name = "";
        this.creationDate = new Date();
        this.author = "";
        this.size = 0;
        this.editor = "";
    }

    // Getters and setters
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

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}

/**
 * Represents an editor that can be used to create documents.
 */
class Editor {
    private String name;

    /**
     * Default constructor for Editor.
     */
    public Editor() {
        this.name = "";
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a file system containing documents.
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
        initializeEditors();
    }

    /**
     * Initializes the system with the three required editors.
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
     * Adds a document to the file system.
     *
     * @param document the document to add
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * Removes a document from the file system.
     *
     * @param document the document to remove
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Lists all documents in the file system.
     *
     * @return a list of all documents
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return the sum of all document sizes, or 0 if there are no documents
     */
    public int calculateTotalSize() {
        int totalSize = 0;
        for (Document document : documents) {
            totalSize += document.getSize();
        }
        return totalSize;
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     *
     * @return a map where keys are editor names and values are the average sizes of documents
     */
    public Map<String, Double> computeAverageSizeByEditor() {
        Map<String, Integer> totalSizes = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        Map<String, Double> averages = new HashMap<>();

        // Initialize maps with all editors
        for (Editor editor : editors) {
            totalSizes.put(editor.getName(), 0);
            counts.put(editor.getName(), 0);
            averages.put(editor.getName(), 0.0);
        }

        // Calculate totals and counts
        for (Document document : documents) {
            String editorName = document.getEditor();
            if (totalSizes.containsKey(editorName)) {
                totalSizes.put(editorName, totalSizes.get(editorName) + document.getSize());
                counts.put(editorName, counts.get(editorName) + 1);
            }
        }

        // Calculate averages
        for (String editorName : totalSizes.keySet()) {
            if (counts.get(editorName) > 0) {
                averages.put(editorName, (double) totalSizes.get(editorName) / counts.get(editorName));
            }
        }

        return averages;
    }

    /**
     * Counts the number of documents that were created after a specified date.
     *
     * @param date the date to compare against
     * @return the count of documents created after the specified date
     */
    public int countDocumentsCreatedAfter(Date date) {
        int count = 0;
        for (Document document : documents) {
            if (document.getCreationDate().after(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents for each editor in the file system.
     *
     * @return a map where keys are editor names and values are the counts of documents
     */
    public Map<String, Integer> countDocumentsByEditor() {
        Map<String, Integer> counts = new HashMap<>();

        // Initialize counts with all editors
        for (Editor editor : editors) {
            counts.put(editor.getName(), 0);
        }

        // Count documents for each editor
        for (Document document : documents) {
            String editorName = document.getEditor();
            if (counts.containsKey(editorName)) {
                counts.put(editorName, counts.get(editorName) + 1);
            }
        }

        return counts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     *
     * @param editorName the name of the editor to filter by
     * @return a set of author names whose documents use the specified editor
     */
    public Set<String> getAuthorsByEditor(String editorName) {
        Set<String> authors = new HashSet<>();
        for (Document document : documents) {
            if (document.getEditor().equals(editorName)) {
                authors.add(document.getAuthor());
            }
        }
        return authors;
    }

    // Getters and setters
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