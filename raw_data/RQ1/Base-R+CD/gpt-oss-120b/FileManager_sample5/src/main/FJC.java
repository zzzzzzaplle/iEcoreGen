import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a simple file system that stores {@link Document}s and the
 * {@link Editor}s used to create them.
 */
 class FileSystem {

    /** All documents stored in this file system. */
    private List<Document> documents;

    /** All editors known to this file system (populated automatically when a document
     *  is added). */
    private Set<Editor> editors;

    /** No‑argument constructor – creates empty collections. */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new HashSet<>();
    }

    /**
     * Adds a document to the file system.
     *
     * @param doc the document to add; must not be {@code null}
     */
    public void addDocument(Document doc) {
        if (doc == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        documents.add(doc);
        if (doc.getEditor() != null) {
            editors.add(doc.getEditor());
        }
    }

    /**
     * Removes a document from the file system.
     *
     * @param doc the document to remove; if the document is not present nothing
     *            happens
     */
    public void removeDocument(Document doc) {
        if (doc == null) {
            return;
        }
        documents.remove(doc);
        // Optional: clean up editors set if no document uses an editor any more
        if (doc.getEditor() != null) {
            boolean stillUsed = documents.stream()
                    .anyMatch(d -> doc.getEditor().equals(d.getEditor()));
            if (!stillUsed) {
                editors.remove(doc.getEditor());
            }
        }
    }

    /**
     * Returns an immutable view of all documents stored in the file system.
     *
     * @return list of documents
     */
    public List<Document> listDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return sum of the {@code size} field of every document; {@code 0} if there
     *         are no documents
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
     * @return a map where the key is the editor name (e.g. "Text Editor") and the
     *         value is the average size of all documents edited with that editor.
     *         Editors with no documents are omitted from the map.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Integer> sumMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (Document doc : documents) {
            Editor editor = doc.getEditor();
            if (editor == null) {
                continue; // skip documents without an editor
            }
            String editorName = editor.getName();
            sumMap.put(editorName, sumMap.getOrDefault(editorName, 0) + doc.getSize());
            countMap.put(editorName, countMap.getOrDefault(editorName, 0) + 1);
        }

        Map<String, Float> avgMap = new HashMap<>();
        for (String editorName : sumMap.keySet()) {
            int sum = sumMap.get(editorName);
            int count = countMap.get(editorName);
            avgMap.put(editorName, (float) sum / count);
        }
        return avgMap;
    }

    /**
     * Counts the number of documents that were created after the specified date.
     *
     * @param date the reference date; documents with a creation date strictly
     *             after this date are counted
     * @return number of documents created after {@code date}
     */
    public int countDocumentsAfterDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        int cnt = 0;
        for (Document doc : documents) {
            if (doc.getCreateDate().after(date)) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Counts how many documents belong to each editor type.
     *
     * @return a map where the key is the editor name and the value is the number
     *         of documents that use that editor. Editors with zero documents are
     *         omitted.
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> countMap = new HashMap<>();
        for (Document doc : documents) {
            Editor editor = doc.getEditor();
            if (editor == null) {
                continue;
            }
            String name = editor.getName();
            countMap.put(name, countMap.getOrDefault(name, 0) + 1);
        }
        return countMap;
    }

    /**
     * Retrieves the distinct author names of all documents edited with the given
     * editor.
     *
     * @param editor the editor to filter by; must not be {@code null}
     * @return list of distinct author names; empty list if none match
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("Editor cannot be null");
        }
        Set<String> authors = new HashSet<>();
        for (Document doc : documents) {
            if (editor.equals(doc.getEditor())) {
                authors.add(doc.getAuthor());
            }
        }
        return new ArrayList<>(authors);
    }

    // -------------------------------------------------------------------------
    // Getters & Setters for the internal collections (mainly for testing)
    // -------------------------------------------------------------------------

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents != null ? documents : new ArrayList<>();
    }

    public Set<Editor> getEditors() {
        return editors;
    }

    public void setEditors(Set<Editor> editors) {
        this.editors = editors != null ? editors : new HashSet<>();
    }
}

/**
 * Represents a document stored in the file system.
 */
class Document {

    private String name;
    private Date createDate;
    private String author;
    private int size;               // size in arbitrary units (e.g., bytes)
    private Editor editor;          // the editor used to create the document (optional)

    /** No‑argument constructor. */
    public Document() {
        // fields remain null / 0 until set via setters
    }

    /** Full constructor for convenience (not required by the specification). */
    public Document(String name, Date createDate, String author, int size, Editor editor) {
        this.name = name;
        this.createDate = createDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    /** No‑argument constructor. */
    public Editor() {
        // subclasses should set the name
    }

    /** Constructor allowing the name to be set. */
    public Editor(String name) {
        this.name = name;
    }

    // -------------------------------------------------------------------------
    // Getter / Setter
    // -------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Equality based on name – useful for collections
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Editor)) return false;
        Editor other = (Editor) obj;
        return name != null ? name.equals(other.name) : other.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

/**
 * Concrete editor for plain text documents.
 */
class TextEditor extends Editor {

    /** Constructs a {@code TextEditor} with the default name "Text Editor". */
    public TextEditor() {
        super("Text Editor");
    }
}

/**
 * Concrete editor for image documents.
 */
class ImageEditor extends Editor {

    /** Constructs an {@code ImageEditor} with the default name "Image Editor". */
    public ImageEditor() {
        super("Image Editor");
    }
}

/**
 * Concrete editor for video documents.
 */
class VideoEditor extends Editor {

    /** Constructs a {@code VideoEditor} with the default name "Video Editor". */
    public VideoEditor() {
        super("Video Editor");
    }
}