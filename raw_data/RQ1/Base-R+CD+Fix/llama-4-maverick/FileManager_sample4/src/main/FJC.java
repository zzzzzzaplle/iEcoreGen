import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a file system that contains information from multiple documents.
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Constructs a new FileSystem object.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
    }

    /**
     * Adds a document to the file system.
     * 
     * @param doc The document to be added.
     */
    public void addDocument(Document doc) {
        this.documents.add(doc);
    }

    /**
     * Removes a document from the file system.
     * 
     * @param doc The document to be removed.
     */
    public void removeDocument(Document doc) {
        this.documents.remove(doc);
    }

    /**
     * Lists all documents in the file system.
     * 
     * @return A list of all documents.
     */
    public List<Document> listDocuments() {
        return this.documents;
    }

    /**
     * Adds an editor to the file system.
     * 
     * @param editor The editor to be added.
     */
    public void addEditor(Editor editor) {
        this.editors.add(editor);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * 
     * @return The total size of all documents. Returns 0 if there are no documents.
     */
    public int calculateTotalDocumentSize() {
        int totalSize = 0;
        for (Document doc : documents) {
            totalSize += doc.getSize();
        }
        return totalSize;
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     * 
     * @return A map where the keys are the editor names and the values are the average document sizes.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Float> averageSizes = new HashMap<>();
        Map<String, Integer> documentCounts = new HashMap<>();

        for (Document doc : documents) {
            Editor editor = doc.getEditor();
            if (editor != null) {
                String editorName = editor.getName();
                averageSizes.put(editorName, averageSizes.getOrDefault(editorName, 0f) + doc.getSize());
                documentCounts.put(editorName, documentCounts.getOrDefault(editorName, 0) + 1);
            }
        }

        for (String editorName : averageSizes.keySet()) {
            float averageSize = averageSizes.get(editorName) / documentCounts.get(editorName);
            averageSizes.put(editorName, averageSize);
        }

        return averageSizes;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * 
     * @param date The date to compare with the document creation dates.
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
     * Counts the number of documents for each editor in the file system.
     * 
     * @return A map where the keys are the editor names and the values are the document counts.
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> documentCounts = new HashMap<>();

        for (Document doc : documents) {
            Editor editor = doc.getEditor();
            if (editor != null) {
                String editorName = editor.getName();
                documentCounts.put(editorName, documentCounts.getOrDefault(editorName, 0) + 1);
            }
        }

        return documentCounts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     * 
     * @param editor The editor to filter documents by.
     * @return A list of author names.
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        List<String> authors = new ArrayList<>();
        for (Document doc : documents) {
            if (doc.getEditor() == editor) {
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

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }
}

/**
 * Represents a document with its metadata.
 */
class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;

    /**
     * Constructs a new Document object.
     */
    public Document() {}

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
 * Represents an editor used for creating documents.
 */
abstract class Editor {
    private String name;

    /**
     * Constructs a new Editor object.
     */
    public Editor() {}

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
     * Constructs a new TextEditor object.
     */
    public TextEditor() {}
}

/**
 * Represents an image editor.
 */
class ImageEditor extends Editor {
    /**
     * Constructs a new ImageEditor object.
     */
    public ImageEditor() {}
}

/**
 * Represents a video editor.
 */
class VideoEditor extends Editor {
    /**
     * Constructs a new VideoEditor object.
     */
    public VideoEditor() {}
}