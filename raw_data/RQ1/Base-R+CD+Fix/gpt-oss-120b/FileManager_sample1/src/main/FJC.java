import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a generic editor with a name.
 */
abstract class Editor {
    /** Name of the editor (e.g., "Text Editor"). */
    private String name;

    /** No‑argument constructor. */
    public Editor() {
        // default constructor
    }

    /**
     * Returns the name of this editor.
     *
     * @return editor name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this editor.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Editor for plain‑text documents.
 */
class TextEditor extends Editor {
    /** No‑argument constructor. */
    public TextEditor() {
        setName("Text Editor");
    }
}

/**
 * Editor for image documents.
 */
class ImageEditor extends Editor {
    /** No‑argument constructor. */
    public ImageEditor() {
        setName("Image Editor");
    }
}

/**
 * Editor for video documents.
 */
class VideoEditor extends Editor {
    /** No‑argument constructor. */
    public VideoEditor() {
        setName("Video Editor");
    }
}

/**
 * Represents a document stored in the file system.
 */
class Document {
    /** Document name. */
    private String name;

    /** Creation date of the document. */
    private Date createDate;

    /** Author name of the document. */
    private String author;

    /** Size of the document (in arbitrary units). */
    private int size;

    /** Editor that created/handles this document (may be null). */
    private Editor editor;

    /** No‑argument constructor. */
    public Document() {
        // default constructor
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

    /** @return the editor associated with this document */
    public Editor getEditor() {
        return editor;
    }

    /** @param editor the editor to associate with this document */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * FileSystem manages a collection of documents and editors.
 */
class FileSystem {
    /** All documents stored in the system. */
    private List<Document> documents = new ArrayList<>();

    /** All editors known to the system. */
    private List<Editor> editors = new ArrayList<>();

    /** No‑argument constructor that registers the three default editors. */
    public FileSystem() {
        // Register default editors
        editors.add(new TextEditor());
        editors.add(new ImageEditor());
        editors.add(new VideoEditor());
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
        // ensure the editor is known to the system
        Editor e = doc.getEditor();
        if (e != null && editors.stream().noneMatch(ed -> ed.getName().equals(e.getName()))) {
            editors.add(e);
        }
    }

    /**
     * Removes a document from the file system.
     *
     * @param doc the document to remove; must not be {@code null}
     * @return {@code true} if the document was present and removed, {@code false} otherwise
     */
    public boolean removeDocument(Document doc) {
        if (doc == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        return documents.remove(doc);
    }

    /**
     * Returns an immutable view of all documents stored in the system.
     *
     * @return list of documents
     */
    public List<Document> listDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return sum of the sizes of all stored documents; {@code 0} if none are present
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
     *         the value is the average size of documents handled by that editor.
     *         Editors with no associated documents are omitted from the map.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Integer> sumMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e == null) {
                continue; // skip documents without an editor
            }
            String editorName = e.getName();
            sumMap.put(editorName, sumMap.getOrDefault(editorName, 0) + d.getSize());
            countMap.put(editorName, countMap.getOrDefault(editorName, 0) + 1);
        }

        Map<String, Float> avgMap = new HashMap<>();
        for (String editorName : sumMap.keySet()) {
            int sum = sumMap.get(editorName);
            int cnt = countMap.get(editorName);
            avgMap.put(editorName, cnt == 0 ? 0f : (float) sum / cnt);
        }
        return avgMap;
    }

    /**
     * Counts the number of documents created after a specified date.
     *
     * @param date the reference date; documents with a creation date strictly after
     *             this value are counted; must not be {@code null}
     * @return number of documents created after the given date
     * @throws IllegalArgumentException if {@code date} is {@code null}
     */
    public int countDocumentsAfterDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
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
     * Counts how many documents belong to each editor type.
     *
     * @return a map where the key is the editor name and the value is the number
     *         of documents that use that editor. Editors with zero documents are omitted.
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
     * Retrieves the names of all distinct authors whose documents are edited
     * with the specified editor.
     *
     * @param editor the editor to filter by; must not be {@code null}
     * @return list of distinct author names; empty list if no matching documents exist
     * @throws IllegalArgumentException if {@code editor} is {@code null}
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("Editor cannot be null");
        }
        Set<String> authorSet = new HashSet<>();
        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e != null && e.getName().equals(editor.getName())) {
                authorSet.add(d.getAuthor());
            }
        }
        return new ArrayList<>(authorSet);
    }

    /** @return the list of registered editors (unmodifiable) */
    public List<Editor> getEditors() {
        return Collections.unmodifiableList(editors);
    }

    /** @param editors the list of editors to set (replaces current list) */
    public void setEditors(List<Editor> editors) {
        if (editors == null) {
            throw new IllegalArgumentException("Editors list cannot be null");
        }
        this.editors = new ArrayList<>(editors);
    }

    /** @return the internal mutable list of documents (for internal use only) */
    protected List<Document> getDocumentsInternal() {
        return documents;
    }

    /** @param documents the internal document list to replace (for internal use only) */
    protected void setDocumentsInternal(List<Document> documents) {
        this.documents = documents;
    }
}