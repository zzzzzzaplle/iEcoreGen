import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a file system that stores documents and editors.
 */
 class FileSystem {

    /** All documents stored in the system. */
    private List<Document> documents;

    /** All available editors (Text, Image, Video). */
    private List<Editor> editors;

    /** No‑argument constructor that creates an empty file system and
     *  registers the three default editors. */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
        // Register the three default editors
        this.editors.add(new TextEditor());
        this.editors.add(new ImageEditor());
        this.editors.add(new VideoEditor());
    }

    /** @return the mutable list of documents (use with care). */
    public List<Document> getDocuments() {
        return documents;
    }

    /** @param documents the documents list to set */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /** @return the list of available editors */
    public List<Editor> getEditors() {
        return editors;
    }

    /** @param editors the editors list to set */
    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

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
     * Removes a document from the file system.
     *
     * @param doc the document to remove; must not be {@code null}
     */
    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    /**
     * Returns an immutable view of all documents currently stored.
     *
     * @return list of documents
     */
    public List<Document> listDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return sum of document sizes; {@code 0} if there are no documents
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
     * @return a map where the key is the editor name (e.g., "TextEditor")
     *         and the value is the average size as {@link Float}. If an
     *         editor has no documents, its average is {@code 0.0f}.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        // Prepare accumulation maps
        Map<String, Integer> sumMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        // Initialise for each known editor
        for (Editor e : editors) {
            sumMap.put(e.getName(), 0);
            countMap.put(e.getName(), 0);
        }

        // Accumulate sizes
        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e != null) {
                String name = e.getName();
                sumMap.put(name, sumMap.get(name) + d.getSize());
                countMap.put(name, countMap.get(name) + 1);
            }
        }

        // Build result map with averages
        Map<String, Float> avgMap = new HashMap<>();
        for (String editorName : sumMap.keySet()) {
            int count = countMap.get(editorName);
            float avg = (count == 0) ? 0.0f : ((float) sumMap.get(editorName) / count);
            avgMap.put(editorName, avg);
        }
        return avgMap;
    }

    /**
     * Counts how many documents were created after the supplied date.
     *
     * @param date the reference date; documents with a creation date strictly
     *             after this value are counted; must not be {@code null}
     * @return number of documents created after {@code date}
     */
    public int countDocumentsAfterDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        int cnt = 0;
        for (Document d : documents) {
            if (d.getCreateDate().after(date)) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Counts the number of documents for each editor type.
     *
     * @return a map where the key is the editor name and the value is the
     *         number of documents that use that editor
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> countMap = new HashMap<>();
        // Initialise counters
        for (Editor e : editors) {
            countMap.put(e.getName(), 0);
        }
        // Count
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
     * Retrieves the distinct author names whose documents are edited
     * with the specified editor.
     *
     * @param editor the editor to filter by; must not be {@code null}
     * @return list of unique author names (order not guaranteed)
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("editor must not be null");
        }
        Set<String> authorSet = new HashSet<>();
        for (Document d : documents) {
            Editor docEditor = d.getEditor();
            if (docEditor != null && docEditor.getName().equals(editor.getName())) {
                authorSet.add(d.getAuthor());
            }
        }
        return new ArrayList<>(authorSet);
    }
}

/**
 * Represents a document stored in the file system.
 */
class Document {

    private String name;
    private Date createDate;
    private String author;
    private int size;               // size in arbitrary units (e.g., kilobytes)
    private Editor editor;          // optional association

    /** No‑argument constructor. */
    public Document() {
        // fields left with default values (null / 0)
    }

    /** @return the document name */
    public String getName() {
        return name;
    }

    /** @param name the document name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the creation date */
    public Date getCreateDate() {
        return createDate;
    }

    /** @param createDate the creation date to set */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /** @return the author name */
    public String getAuthor() {
        return author;
    }

    /** @param author the author name to set */
    public void setAuthor(String author) {
        this.author = author;
    }

    /** @return the size of the document */
    public int getSize() {
        return size;
    }

    /** @param size the size to set */
    public void setSize(int size) {
        this.size = size;
    }

    /** @return the editor associated with this document (may be {@code null}) */
    public Editor getEditor() {
        return editor;
    }

    /** @param editor the editor to associate with this document */
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
        // name will be set by concrete subclasses
    }

    /** @return the editor's name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Text editor implementation.
 */
class TextEditor extends Editor {

    /** Constructs a TextEditor with the appropriate name. */
    public TextEditor() {
        setName("TextEditor");
    }
}

/**
 * Image editor implementation.
 */
class ImageEditor extends Editor {

    /** Constructs an ImageEditor with the appropriate name. */
    public ImageEditor() {
        setName("ImageEditor");
    }
}

/**
 * Video editor implementation.
 */
class VideoEditor extends Editor {

    /** Constructs a VideoEditor with the appropriate name. */
    public VideoEditor() {
        setName("VideoEditor");
    }
}