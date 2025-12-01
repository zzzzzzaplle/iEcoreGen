import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a file system that stores documents and the editors used to create them.
 */
class FileSystem {

    /** Holds all documents added to the file system. */
    private List<Document> documents = new ArrayList<>();

    /** Holds all editors that can be used in the system. */
    private List<Editor> editors = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public FileSystem() {
        // Initialize the three default editors.
        editors.add(new TextEditor());
        editors.add(new ImageEditor());
        editors.add(new VideoEditor());
    }

    /* --------------------------------------------------------------------- */
    /*                     CRUD operations for documents                     */
    /* --------------------------------------------------------------------- */

    /**
     * Adds a document to the file system.
     *
     * @param doc the document to add; must not be {@code null}
     */
    public void addDocument(Document doc) {
        if (doc != null) {
            documents.add(doc);
        }
    }

    /**
     * Removes a document from the file system. If the document does not exist,
     * the method does nothing.
     *
     * @param doc the document to remove; must not be {@code null}
     */
    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    /**
     * Returns an unmodifiable view of all documents stored in the file system.
     *
     * @return list of documents
     */
    public List<Document> listDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /* --------------------------------------------------------------------- */
    /*                Functional requirements implementation                 */
    /* --------------------------------------------------------------------- */

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return the sum of the {@code size} fields of all documents, or {@code 0}
     *         if there are no documents
     */
    public int calculateTotalDocumentSize() {
        int total = 0;
        for (Document d : documents) {
            total += d.getSize();
        }
        return total;
    }

    /**
     * Computes the average size of documents for each editor type.
     *
     * @return a map where the key is the editor name (e.g., "Text Editor") and
     *         the value is the average document size for that editor. If an
     *         editor has no documents, its average is {@code 0.0f}.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Integer> sumMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        // Initialize maps for the three known editors
        for (Editor e : editors) {
            sumMap.put(e.getName(), 0);
            countMap.put(e.getName(), 0);
        }

        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e != null) {
                String name = e.getName();
                sumMap.put(name, sumMap.get(name) + d.getSize());
                countMap.put(name, countMap.get(name) + 1);
            }
        }

        Map<String, Float> averageMap = new HashMap<>();
        for (String editorName : sumMap.keySet()) {
            int count = countMap.get(editorName);
            float average = (count == 0) ? 0.0f : ((float) sumMap.get(editorName) / count);
            averageMap.put(editorName, average);
        }
        return averageMap;
    }

    /**
     * Counts how many documents were created after a given date.
     *
     * @param date the reference date; documents with a {@code createDate}
     *             strictly after this date are counted
     * @return the number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        if (date == null) {
            return 0;
        }
        int count = 0;
        for (Document d : documents) {
            if (d.getCreateDate() != null && d.getCreateDate().after(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents for each editor type.
     *
     * @return a map where the key is the editor name and the value is the
     *         number of documents that use that editor
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> countMap = new HashMap<>();

        // Initialise counts for the three default editors
        for (Editor e : editors) {
            countMap.put(e.getName(), 0);
        }

        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e != null) {
                String name = e.getName();
                countMap.put(name, countMap.get(name) + 1);
            }
        }
        return countMap;
    }

    /**
     * Retrieves the list of distinct author names whose documents are edited
     * with the specified editor.
     *
     * @param editor the editor to filter by; must not be {@code null}
     * @return a list of unique author names; the list may be empty if no
     *         documents use the supplied editor
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            return Collections.emptyList();
        }
        Set<String> authors = new HashSet<>();
        for (Document d : documents) {
            if (editor.equals(d.getEditor())) {
                authors.add(d.getAuthor());
            }
        }
        return new ArrayList<>(authors);
    }

    /* --------------------------------------------------------------------- */
    /*                     Accessors for internal collections                */
    /* --------------------------------------------------------------------- */

    /**
     * Returns the list of editors known to the file system.
     *
     * @return unmodifiable list of editors
     */
    public List<Editor> getEditors() {
        return Collections.unmodifiableList(editors);
    }

    /**
     * Adds a new editor to the system. Duplicate names are allowed but not
     * recommended.
     *
     * @param editor the editor to add; must not be {@code null}
     */
    public void addEditor(Editor editor) {
        if (editor != null) {
            editors.add(editor);
        }
    }
}

/**
 * Represents a document stored in the file system.
 */
class Document {

    private String name;
    private Date createDate;
    private String author;
    private int size;                 // size in arbitrary units (e.g., kilobytes)
    private Editor editor;            // editor used to create the document (optional)

    /** No‑argument constructor required by the specification. */
    public Document() {
    }

    /* --------------------------- Getters & Setters --------------------------- */

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

    /** No‑argument constructor required by the specification. */
    public Editor() {
        this.name = "Unnamed Editor";
    }

    /**
     * Creates an editor with the given name.
     *
     * @param name the name of the editor
     */
    public Editor(String name) {
        this.name = name;
    }

    /* --------------------------- Getters & Setters --------------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

/**
 * Text editor implementation.
 */
class TextEditor extends Editor {

    /** No‑argument constructor sets a default name. */
    public TextEditor() {
        super("Text Editor");
    }
}

/**
 * Image editor implementation.
 */
class ImageEditor extends Editor {

    /** No‑argument constructor sets a default name. */
    public ImageEditor() {
        super("Image Editor");
    }
}

/**
 * Video editor implementation.
 */
class VideoEditor extends Editor {

    /** No‑argument constructor sets a default name. */
    public VideoEditor() {
        super("Video Editor");
    }
}