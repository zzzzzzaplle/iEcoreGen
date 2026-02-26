import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a simple in‑memory file system that manages {@link Document}s and
 * {@link Editor}s. It provides operations to add/remove documents, list them,
 * and perform various statistical queries required by the functional
 * specifications.
 */
 class FileSystem {

    /** Documents stored in the file system. */
    private List<Document> documents;

    /** Editors known to the system (Text, Image and Video). */
    private List<Editor> editors;

    /**
     * Creates a new {@code FileSystem} instance. By default it contains three
     * editors: TextEditor, ImageEditor and VideoEditor.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
        // initialise the three standard editors
        this.editors.add(new TextEditor());
        this.editors.add(new ImageEditor());
        this.editors.add(new VideoEditor());
    }

    /** Adds a document to the file system. */
    public void addDocument(Document doc) {
        if (doc != null) {
            documents.add(doc);
        }
    }

    /** Removes a document from the file system. */
    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    /**
     * Returns an immutable view of the documents currently stored.
     *
     * @return list of {@link Document}s
     */
    public List<Document> listDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return sum of the {@code size} fields of all documents, or {@code 0}
     *         if no document is stored
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
     * @return a map where the key is the editor name (e.g. "Text Editor") and
     *         the value is the average size (as {@link Float}) of all documents
     *         edited with that editor. Editors with no documents are omitted.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Integer> sumMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e == null) {
                continue; // skip documents without an editor assignment
            }
            String editorName = e.getName();
            sumMap.put(editorName, sumMap.getOrDefault(editorName, 0) + d.getSize());
            countMap.put(editorName, countMap.getOrDefault(editorName, 0) + 1);
        }

        Map<String, Float> avgMap = new HashMap<>();
        for (String editorName : sumMap.keySet()) {
            int sum = sumMap.get(editorName);
            int count = countMap.get(editorName);
            avgMap.put(editorName, sum / (float) count);
        }
        return avgMap;
    }

    /**
     * Counts the number of documents created after the supplied date.
     *
     * @param date the reference date; documents with a {@code createDate}
     *             later than this are counted
     * @return number of documents created after {@code date}
     * @throws IllegalArgumentException if {@code date} is {@code null}
     */
    public int countDocumentsAfterDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        int cnt = 0;
        for (Document d : documents) {
            if (d.getCreateDate() != null && d.getCreateDate().after(date)) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Counts the number of documents for each editor type.
     *
     * @return a map where the key is the editor name and the value is the
     *         number of documents associated with that editor. Editors with
     *         zero documents are omitted.
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> countMap = new HashMap<>();
        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e == null) {
                continue;
            }
            String editorName = e.getName();
            countMap.put(editorName, countMap.getOrDefault(editorName, 0) + 1);
        }
        return countMap;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with the
     * specified editor.
     *
     * @param editor the editor to filter documents by; must not be {@code null}
     * @return list of distinct author names; empty list if none match
     * @throws IllegalArgumentException if {@code editor} is {@code null}
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("editor must not be null");
        }
        Set<String> authors = new HashSet<>();
        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e != null && e.getName().equals(editor.getName())) {
                authors.add(d.getAuthor());
            }
        }
        return new ArrayList<>(authors);
    }

    /** Getter for the internal list of editors (read‑only). */
    public List<Editor> getEditors() {
        return Collections.unmodifiableList(editors);
    }

    /** Setter to replace the editor list – mainly for testing purposes. */
    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    /** Setter to replace the document list – mainly for testing purposes. */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}

/**
 * Represents a document stored in the {@link FileSystem}.
 */
class Document {

    private String name;
    private Date createDate;
    private String author;
    private int size;               // size in arbitrary units (e.g., kilobytes)
    private Editor editor;          // editor used to create/edit this document (optional)

    /** No‑argument constructor required by the task. */
    public Document() {
        // fields left with default values (null / 0)
    }

    /** Full constructor for convenience (not required by the task). */
    public Document(String name, Date createDate, String author, int size, Editor editor) {
        this.name = name;
        this.createDate = createDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    // ----- getters and setters -----
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
 * Abstract base class for all editors. Each editor has a name.
 */
abstract class Editor {

    private String name;

    /** No‑argument constructor. */
    public Editor() {
        // subclasses should set the name
    }

    /** Constructor that sets the editor's name. */
    public Editor(String name) {
        this.name = name;
    }

    /** Returns the editor's name. */
    public String getName() {
        return name;
    }

    /** Sets the editor's name. */
    public void setName(String name) {
        this.name = name;
    }
}

/** Concrete editor for plain‑text documents. */
class TextEditor extends Editor {

    /** Creates a {@code TextEditor} with the default name "Text Editor". */
    public TextEditor() {
        super("Text Editor");
    }
}

/** Concrete editor for image documents. */
class ImageEditor extends Editor {

    /** Creates an {@code ImageEditor} with the default name "Image Editor". */
    public ImageEditor() {
        super("Image Editor");
    }
}

/** Concrete editor for video documents. */
class VideoEditor extends Editor {

    /** Creates a {@code VideoEditor} with the default name "Video Editor". */
    public VideoEditor() {
        super("Video Editor");
    }
}