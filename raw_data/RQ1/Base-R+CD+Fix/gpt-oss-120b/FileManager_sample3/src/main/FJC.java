import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a simple file system that stores documents and editors.
 * Provides operations to add/remove/list documents and to compute
 * various statistics about the stored documents.
 */
 class FileSystem {

    /** List of all documents stored in the file system. */
    private List<Document> documents = new ArrayList<>();

    /** List of all editors available in the system. */
    private List<Editor> editors = new ArrayList<>();

    /**
     * Constructs a new {@code FileSystem} and creates the three default editors:
     * TextEditor, ImageEditor and VideoEditor.
     */
    public FileSystem() {
        // initialise the three default editors
        editors.add(new TextEditor());
        editors.add(new ImageEditor());
        editors.add(new VideoEditor());
    }

    /**
     * Adds a document to the file system.
     *
     * @param doc the document to be added; if {@code null} nothing happens
     */
    public void addDocument(Document doc) {
        if (doc != null) {
            documents.add(doc);
        }
    }

    /**
     * Removes a document from the file system.
     *
     * @param doc the document to be removed; if the document is not present,
     *            the method does nothing
     */
    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    /**
     * Returns a copy of the list containing all documents in the file system.
     *
     * @return a new {@code List} with all stored documents
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents stored in the file system.
     *
     * @return the sum of the {@code size} field of every document; {@code 0}
     *         if there are no documents
     */
    public int calculateTotalDocumentSize() {
        int total = 0;
        for (Document doc : documents) {
            total += doc.getSize();
        }
        return total;
    }

    /**
     * Computes the average size of documents for each editor type.
     *
     * @return a {@code Map} where the key is the editor name and the value
     *         is the average document size (as {@code Float}); editors with
     *         no documents are omitted from the map
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Integer> sumMap   = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (Document doc : documents) {
            Editor editor = doc.getEditor();
            if (editor != null) {
                String name = editor.getName();
                sumMap.merge(name, doc.getSize(), Integer::sum);
                countMap.merge(name, 1, Integer::sum);
            }
        }

        Map<String, Float> averageMap = new HashMap<>();
        for (String editorName : sumMap.keySet()) {
            int sum   = sumMap.get(editorName);
            int count = countMap.get(editorName);
            averageMap.put(editorName, (float) sum / count);
        }
        return averageMap;
    }

    /**
     * Counts how many documents were created after the specified date.
     *
     * @param date the reference date; documents with a {@code createDate}
     *             later than this date are counted
     * @return the number of documents created after {@code date}
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
     * Counts the number of documents associated with each editor type.
     *
     * @return a {@code Map} where the key is the editor name and the value
     *         is the number of documents that use that editor
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> map = new HashMap<>();
        for (Document doc : documents) {
            Editor editor = doc.getEditor();
            if (editor != null) {
                String name = editor.getName();
                map.merge(name, 1, Integer::sum);
            }
        }
        return map;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with the
     * specified editor.
     *
     * @param editor the editor to filter by; if {@code null} an empty list is returned
     * @return a {@code List} of author names; duplicates are preserved
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        List<String> authors = new ArrayList<>();
        if (editor == null) {
            return authors;
        }
        String targetName = editor.getName();
        for (Document doc : documents) {
            Editor docEditor = doc.getEditor();
            if (docEditor != null && targetName.equals(docEditor.getName())) {
                authors.add(doc.getAuthor());
            }
        }
        return authors;
    }

    /* -------------------  Accessors for internal collections  ------------------- */

    /**
     * Returns the internal list of editors (read‑only view).
     *
     * @return the list of editors managed by this file system
     */
    public List<Editor> getEditors() {
        return new ArrayList<>(editors);
    }

    /**
     * Sets the internal list of editors. Mainly for testing purposes.
     *
     * @param editors the new list of editors; {@code null} is ignored
     */
    public void setEditors(List<Editor> editors) {
        if (editors != null) {
            this.editors = new ArrayList<>(editors);
        }
    }
}

/**
 * Represents a document stored in the file system.
 */
class Document {

    private String name;
    private Date   createDate;
    private String author;
    private int    size;          // size in arbitrary units (e.g., kilobytes)
    private Editor editor;        // optional association to an editor

    /** Default constructor required for reflection / testing. */
    public Document() {
    }

    /** Full constructor for convenience (not required by the specification). */
    public Document(String name, Date createDate, String author, int size, Editor editor) {
        this.name = name;
        this.createDate = createDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    // ------------------- getters and setters -------------------

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
 * Abstract base class for all editors.
 */
abstract class Editor {

    private String name;

    /** Default constructor. */
    public Editor() {
    }

    /** Constructor that sets the editor name. */
    public Editor(String name) {
        this.name = name;
    }

    // ------------------- getters and setters -------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Returns a textual representation of the editor. */
    @Override
    public String toString() {
        return "Editor{name='" + name + "'}";
    }
}

/**
 * Editor specialised for plain text documents.
 */
class TextEditor extends Editor {

    /** Constructs a {@code TextEditor} with the default name "Text Editor". */
    public TextEditor() {
        super("Text Editor");
    }
}

/**
 * Editor specialised for image documents.
 */
class ImageEditor extends Editor {

    /** Constructs an {@code ImageEditor} with the default name "Image Editor". */
    public ImageEditor() {
        super("Image Editor");
    }
}

/**
 * Editor specialised for video documents.
 */
class VideoEditor extends Editor {

    /** Constructs a {@code VideoEditor} with the default name "Video Editor". */
    public VideoEditor() {
        super("Video Editor");
    }
}