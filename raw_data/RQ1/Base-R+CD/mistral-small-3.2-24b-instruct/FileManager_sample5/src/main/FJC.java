import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
        // Initialize the three types of editors
        this.editors.add(new TextEditor("Text Editor"));
        this.editors.add(new ImageEditor("Image Editor"));
        this.editors.add(new VideoEditor("Video Editor"));
    }

    /**
     * Adds a document to the file system.
     *
     * @param doc The document to be added.
     */
    public void addDocument(Document doc) {
        if (doc != null) {
            documents.add(doc);
        }
    }

    /**
     * Removes a document from the file system.
     *
     * @param doc The document to be removed.
     */
    public void removeDocument(Document doc) {
        if (doc != null) {
            documents.remove(doc);
        }
    }

    /**
     * Lists all documents in the file system.
     *
     * @return A list of all documents.
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return The total size of all documents. Returns 0 if there is no document.
     */
    public int calculateTotalDocumentSize() {
        if (documents.isEmpty()) {
            return 0;
        }
        int totalSize = 0;
        for (Document doc : documents) {
            totalSize += doc.getSize();
        }
        return totalSize;
    }

    /**
     * Computes the average size of all documents for each editor.
     *
     * @return A map with editor names as keys and average sizes as values.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Float> averageSizes = new HashMap<>();
        Map<String, Integer> totalSizes = new HashMap<>();
        Map<String, Integer> documentCounts = new HashMap<>();

        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            totalSizes.put(editorName, totalSizes.getOrDefault(editorName, 0) + doc.getSize());
            documentCounts.put(editorName, documentCounts.getOrDefault(editorName, 0) + 1);
        }

        for (String editorName : totalSizes.keySet()) {
            float averageSize = (float) totalSizes.get(editorName) / documentCounts.get(editorName);
            averageSizes.put(editorName, averageSize);
        }

        return averageSizes;
    }

    /**
     * Counts the number of documents created after a specified date.
     *
     * @param date The date to compare against.
     * @return The number of documents created after the specified date.
     */
    public int countDocumentsAfterDate(Date date) {
        int count = 0;
        for (Document doc : documents) {
            if (doc.getCreateDate().after(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents for each editor.
     *
     * @return A map with editor names as keys and document counts as values.
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> documentCounts = new HashMap<>();
        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            documentCounts.put(editorName, documentCounts.getOrDefault(editorName, 0) + 1);
        }
        return documentCounts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     *
     * @param editor The editor to filter by.
     * @return A list of author names.
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        List<String> authors = new ArrayList<>();
        for (Document doc : documents) {
            if (doc.getEditor().getName().equals(editor.getName())) {
                authors.add(doc.getAuthor());
            }
        }
        return authors;
    }

    // Getter and Setter methods
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

class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;

    public Document() {
        // Default constructor
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

abstract class Editor {
    private String name;

    public Editor(String name) {
        this.name = name;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class TextEditor extends Editor {
    public TextEditor(String name) {
        super(name);
    }
}

class ImageEditor extends Editor {
    public ImageEditor(String name) {
        super(name);
    }
}

class VideoEditor extends Editor {
    public VideoEditor(String name) {
        super(name);
    }
}