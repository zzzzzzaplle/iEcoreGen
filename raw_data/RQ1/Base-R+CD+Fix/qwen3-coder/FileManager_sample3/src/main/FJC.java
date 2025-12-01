import java.util.*;

/**
 * Represents a file system that manages documents and editors.
 */
 class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Constructs a new FileSystem with empty documents and editors lists.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
        
        // Initialize with the three required editors
        this.editors.add(new TextEditor());
        this.editors.add(new ImageEditor());
        this.editors.add(new VideoEditor());
    }

    /**
     * Adds a document to the file system.
     * 
     * @param doc the document to add
     */
    public void addDocument(Document doc) {
        this.documents.add(doc);
    }

    /**
     * Removes a document from the file system.
     * 
     * @param doc the document to remove
     */
    public void removeDocument(Document doc) {
        this.documents.remove(doc);
    }

    /**
     * Lists all documents in the file system.
     * 
     * @return a list of all documents
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(this.documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * 
     * @return the sum of all document sizes, or 0 if there are no documents
     */
    public int calculateTotalDocumentSize() {
        int totalSize = 0;
        for (Document doc : documents) {
            totalSize += doc.getSize();
        }
        return totalSize;
    }

    /**
     * Computes the average size of all documents for each editor type.
     * 
     * @return a map with editor names as keys and average sizes as values
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Integer> totalSizes = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        Map<String, Float> averages = new HashMap<>();

        // Initialize maps with all editor types
        for (Editor editor : editors) {
            totalSizes.put(editor.getName(), 0);
            counts.put(editor.getName(), 0);
        }

        // Calculate totals and counts
        for (Document doc : documents) {
            if (doc.getEditor() != null) {
                String editorName = doc.getEditor().getName();
                totalSizes.put(editorName, totalSizes.get(editorName) + doc.getSize());
                counts.put(editorName, counts.get(editorName) + 1);
            }
        }

        // Calculate averages
        for (Editor editor : editors) {
            String editorName = editor.getName();
            if (counts.get(editorName) > 0) {
                float average = (float) totalSizes.get(editorName) / counts.get(editorName);
                averages.put(editorName, average);
            } else {
                averages.put(editorName, 0.0f);
            }
        }

        return averages;
    }

    /**
     * Counts the number of documents created after a specified date.
     * 
     * @param date the date to compare against
     * @return the number of documents created after the specified date
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
     * Counts the number of documents for each editor type.
     * 
     * @return a map with editor names as keys and document counts as values
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> counts = new HashMap<>();

        // Initialize with all editor types
        for (Editor editor : editors) {
            counts.put(editor.getName(), 0);
        }

        // Count documents for each editor
        for (Document doc : documents) {
            if (doc.getEditor() != null) {
                String editorName = doc.getEditor().getName();
                counts.put(editorName, counts.get(editorName) + 1);
            }
        }

        return counts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     * 
     * @param editor the editor to filter by
     * @return a list of author names
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        Set<String> authors = new HashSet<>();
        for (Document doc : documents) {
            if (doc.getEditor() != null && doc.getEditor().getName().equals(editor.getName())) {
                authors.add(doc.getAuthor());
            }
        }
        return new ArrayList<>(authors);
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

/**
 * Represents a document in the file system.
 */
class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;

    /**
     * Constructs a new Document with default values.
     */
    public Document() {
        this.name = "";
        this.createDate = new Date();
        this.author = "";
        this.size = 0;
        this.editor = null;
    }

    // Getters and setters
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

/**
 * Abstract base class for editors.
 */
abstract class Editor {
    protected String name;

    /**
     * Constructs a new Editor with a default name.
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
 * Represents a text editor.
 */
class TextEditor extends Editor {
    /**
     * Constructs a new TextEditor.
     */
    public TextEditor() {
        this.name = "Text Editor";
    }
}

/**
 * Represents an image editor.
 */
class ImageEditor extends Editor {
    /**
     * Constructs a new ImageEditor.
     */
    public ImageEditor() {
        this.name = "Image Editor";
    }
}

/**
 * Represents a video editor.
 */
class VideoEditor extends Editor {
    /**
     * Constructs a new VideoEditor.
     */
    public VideoEditor() {
        this.name = "Video Editor";
    }
}