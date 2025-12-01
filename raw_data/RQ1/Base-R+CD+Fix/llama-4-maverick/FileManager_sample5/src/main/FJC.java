import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a file system that manages documents and editors.
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Constructs a new file system.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
    }

    /**
     * Adds a document to the file system.
     * 
     * @param doc the document to be added
     */
    public void addDocument(Document doc) {
        this.documents.add(doc);
    }

    /**
     * Removes a document from the file system.
     * 
     * @param doc the document to be removed
     */
    public void removeDocument(Document doc) {
        this.documents.remove(doc);
    }

    /**
     * Lists all documents in the file system.
     * 
     * @return a list of documents
     */
    public List<Document> listDocuments() {
        return this.documents;
    }

    /**
     * Calculates the total size of all documents in the file system.
     * 
     * @return the total size of all documents
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
     * @return a map where the key is the editor's name and the value is the average size
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
            float totalSize = averageSizes.get(editorName);
            int count = documentCounts.get(editorName);
            averageSizes.put(editorName, totalSize / count);
        }

        return averageSizes;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * 
     * @param date the date to compare with the document's creation date
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
     * Counts the number of documents for each editor in the file system.
     * 
     * @return a map where the key is the editor's name and the value is the count of documents
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
     * @param editor the editor to filter documents by
     * @return a list of author names
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        List<String> authors = new ArrayList<>();
        for (Document doc : documents) {
            if (doc.getEditor() != null && doc.getEditor().equals(editor)) {
                authors.add(doc.getAuthor());
            }
        }
        return authors;
    }

    /**
     * Adds an editor to the file system.
     * 
     * @param editor the editor to be added
     */
    public void addEditor(Editor editor) {
        this.editors.add(editor);
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
     * Constructs a new document.
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
 * Represents an abstract editor.
 */
abstract class Editor {
    private String name;

    /**
     * Constructs a new editor.
     */
    public Editor() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Editor editor = (Editor) obj;
        return this.name.equals(editor.name);
    }
}

/**
 * Represents a text editor.
 */
class TextEditor extends Editor {
    /**
     * Constructs a new text editor.
     */
    public TextEditor() {}
}

/**
 * Represents an image editor.
 */
class ImageEditor extends Editor {
    /**
     * Constructs a new image editor.
     */
    public ImageEditor() {}
}

/**
 * Represents a video editor.
 */
class VideoEditor extends Editor {
    /**
     * Constructs a new video editor.
     */
    public VideoEditor() {}
}