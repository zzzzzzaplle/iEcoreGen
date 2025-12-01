import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a file system that stores {@link Document}s and manages
 * operations related to the documents and their editors.
 */
 class FileSystem {

    /** The collection of documents stored in this file system. */
    private List<Document> documents;

    /** The collection of editors known to this file system. */
    private Set<Editor> editors;

    /** No‑argument constructor – creates an empty file system with the three default editors. */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new HashSet<>();
        // Initialise the three supported editors
        this.editors.add(new TextEditor());
        this.editors.add(new ImageEditor());
        this.editors.add(new VideoEditor());
    }

    /**
     * Adds a document to the file system.
     *
     * @param doc the document to be added; if {@code null} the method does nothing
     */
    public void addDocument(Document doc) {
        if (doc != null) {
            documents.add(doc);
            // keep the editor collection up‑to‑date (optional)
            if (doc.getEditor() != null) {
                editors.add(doc.getEditor());
            }
        }
    }

    /**
     * Removes a document from the file system.
     *
     * @param doc the document to be removed; if {@code null} the method does nothing
     */
    public void removeDocument(Document doc) {
        if (doc != null) {
            documents.remove(doc);
        }
    }

    /**
     * Returns an unmodifiable view of all documents stored in the file system.
     *
     * @return list of documents
     */
    public List<Document> listDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return the sum of the {@code size} fields of all stored documents; {@code 0}
     *         if no documents are present
     */
    public int calculateTotalDocumentSize() {
        return documents.stream()
                .mapToInt(Document::getSize)
                .sum();
    }

    /**
     * Computes the average size of documents for each editor type (Text, Image, Video).
     *
     * @return a map whose key is the editor name (e.g., "TextEditor") and value is the
     *         average document size for that editor. Editors without documents map to {@code 0.0f}.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Float> averages = new HashMap<>();

        // Initialise map with all known editors to guarantee presence of each key
        for (Editor e : editors) {
            averages.put(e.getName(), 0.0f);
        }

        // Group documents by editor name
        Map<String, List<Document>> byEditor = documents.stream()
                .filter(d -> d.getEditor() != null)
                .collect(Collectors.groupingBy(d -> d.getEditor().getName()));

        // Compute average for each group
        for (Map.Entry<String, List<Document>> entry : byEditor.entrySet()) {
            float avg = (float) entry.getValue().stream()
                    .mapToInt(Document::getSize)
                    .average()
                    .orElse(0.0);
            averages.put(entry.getKey(), avg);
        }

        return averages;
    }

    /**
     * Counts how many documents were created after the specified date.
     *
     * @param date the date to compare against; if {@code null} the method returns {@code 0}
     * @return number of documents with a creation date later than {@code date}
     */
    public int countDocumentsAfterDate(Date date) {
        if (date == null) {
            return 0;
        }
        return (int) documents.stream()
                .filter(d -> d.getCreateDate() != null && d.getCreateDate().after(date))
                .count();
    }

    /**
     * Counts the number of documents for each editor type.
     *
     * @return a map where the key is the editor name and the value is the count of documents
     *         using that editor. Editors without documents map to {@code 0}.
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> counts = new HashMap<>();

        // Initialise map with all editors
        for (Editor e : editors) {
            counts.put(e.getName(), 0);
        }

        // Count documents per editor
        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e != null) {
                counts.merge(e.getName(), 1, Integer::sum);
            }
        }

        return counts;
    }

    /**
     * Retrieves the list of author names whose documents are edited with the given editor.
     *
     * @param editor the editor to filter by; if {@code null} returns an empty list
     * @return a list of author names (duplicates are removed) for documents associated
     *         with {@code editor}
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            return Collections.emptyList();
        }
        return documents.stream()
                .filter(d -> editor.equals(d.getEditor()))
                .map(Document::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------
    // Getters / Setters for the internal collections (mainly for testing)
    // -------------------------------------------------------------------------

    /**
     * Returns the mutable list of documents (useful for unit‑tests).
     *
     * @return the internal documents list
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the internal documents list.
     *
     * @param documents the list to replace the current one; if {@code null} an empty list is used
     */
    public void setDocuments(List<Document> documents) {
        this.documents = (documents != null) ? documents : new ArrayList<>();
    }

    /**
     * Returns the set of editors known to this file system.
     *
     * @return the internal editors set
     */
    public Set<Editor> getEditors() {
        return editors;
    }

    /**
     * Sets the editors known to this file system.
     *
     * @param editors the set to replace the current one; if {@code null} an empty set is used
     */
    public void setEditors(Set<Editor> editors) {
        this.editors = (editors != null) ? editors : new HashSet<>();
    }
}

/**
 * Represents a document stored in the {@link FileSystem}.
 */
class Document {

    /** Document name. */
    private String name;

    /** Creation date of the document. */
    private Date createDate;

    /** Author of the document. */
    private String author;

    /** Size of the document (in arbitrary units). */
    private int size;

    /** Editor used for this document; may be {@code null}. */
    private Editor editor;

    /** No‑argument constructor. */
    public Document() {
        // fields are left with default values (null / 0)
    }

    /**
     * Returns the document name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the document name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the creation date.
     *
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date.
     *
     * @param createDate the date to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns the author name.
     *
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author name.
     *
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the size of the document.
     *
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the document.
     *
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the editor associated with this document.
     *
     * @return editor (may be {@code null})
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor for this document.
     *
     * @param editor the editor to associate
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Abstract base class for all editors.
 */
abstract class Editor {

    /** Name of the editor (e.g., "TextEditor"). */
    private String name;

    /** No‑argument constructor. */
    public Editor() {
        // subclasses should set the name
    }

    /**
     * Returns the editor name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the editor name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Editor used for plain text documents.
 */
class TextEditor extends Editor {

    /** No‑argument constructor that sets the appropriate name. */
    public TextEditor() {
        setName("TextEditor");
    }
}

/**
 * Editor used for image documents.
 */
class ImageEditor extends Editor {

    /** No‑argument constructor that sets the appropriate name. */
    public ImageEditor() {
        setName("ImageEditor");
    }
}

/**
 * Editor used for video documents.
 */
class VideoEditor extends Editor {

    /** No‑argument constructor that sets the appropriate name. */
    public VideoEditor() {
        setName("VideoEditor");
    }
}