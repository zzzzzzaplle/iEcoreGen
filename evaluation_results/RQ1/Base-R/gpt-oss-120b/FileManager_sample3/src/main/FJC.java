import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an author of a document.
 */
 class Author {

    /** The name of the author. */
    private String name;

    /** No‑argument constructor required by the specification. */
    public Author() {
        // default constructor
    }

    /**
     * Returns the author name.
     *
     * @return the name of the author
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the author name.
     *
     * @param name the name to set for the author
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Convenience constructor. */
    public Author(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{name='" + name + '\'' + '}';
    }
}

/**
 * Represents an editor that can be assigned to a document.
 */
 class Editor {

    /** The name of the editor (e.g., "Text Editor"). */
    private String name;

    /** No‑argument constructor required by the specification. */
    public Editor() {
        // default constructor
    }

    /**
     * Returns the editor name.
     *
     * @return the name of the editor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the editor name.
     *
     * @param name the name to set for the editor
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Convenience constructor. */
    public Editor(String name) {
        this.name = name;
    }

    /**
     * Returns a textual representation of the editor.
     *
     * @return the editor name
     */
    @Override
    public String toString() {
        return name;
    }

    // Predefined editor instances as per the domain description
    public static final Editor TEXT_EDITOR = new Editor("Text Editor");
    public static final Editor IMAGE_EDITOR = new Editor("Image Editor");
    public static final Editor VIDEO_EDITOR = new Editor("Video Editor");
}

/**
 * Represents a document stored in the file system.
 */
 class Document {

    /** Document's name. */
    private String name;

    /** Creation date of the document. */
    private LocalDate creationDate;

    /** Author information. */
    private Author author;

    /** Size of the document in bytes. */
    private long size;

    /** Editor assigned to this document. */
    private Editor editor;

    /** No‑argument constructor required by the specification. */
    public Document() {
        // default constructor
    }

    /**
     * Returns the document name.
     *
     * @return the name of the document
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the document name.
     *
     * @param name the name to set for the document
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the creation date of the document.
     *
     * @return the creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the document.
     *
     * @param creationDate the creation date to set
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Returns the author of the document.
     *
     * @return the author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author of the document.
     *
     * @param author the author to set
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Returns the size of the document in bytes.
     *
     * @return the size in bytes
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size of the document.
     *
     * @param size the size in bytes to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Returns the editor assigned to this document.
     *
     * @return the editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor assigned to this document.
     *
     * @param editor the editor to assign
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    /** Convenience constructor. */
    public Document(String name, LocalDate creationDate, Author author,
                    long size, Editor editor) {
        this.name = name;
        this.creationDate = creationDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", author=" + author +
                ", size=" + size +
                ", editor=" + editor +
                '}';
    }
}

/**
 * Represents the file system that stores documents.
 * Provides operations required by the functional specifications.
 */
 class FileSystem {

    /** Internal storage for documents. */
    private final List<Document> documents = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public FileSystem() {
        // default constructor
    }

    /**
     * Returns an unmodifiable view of the stored documents.
     *
     * @return list of documents
     */
    public List<Document> getDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Adds a document to the file system.
     *
     * @param document the document to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code document} is {@code null}
     */
    public void addDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        documents.add(document);
    }

    /**
     * Removes a document from the file system.
     *
     * @param document the document to remove; must not be {@code null}
     * @return {@code true} if the document was present and removed; {@code false}
     *         otherwise
     * @throws IllegalArgumentException if {@code document} is {@code null}
     */
    public boolean removeDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        return documents.remove(document);
    }

    /**
     * Calculates the total size of all documents stored in the file system.
     *
     * @return the sum of the sizes of all documents; {@code 0} if there are no
     *         documents
     */
    public long calculateTotalSize() {
        long total = 0L;
        for (Document doc : documents) {
            total += doc.getSize();
        }
        return total;
    }

    /**
     * Computes the average size of documents for each editor type
     * (Text Editor, Image Editor, Video Editor) present in the file system.
     *
     * @return a map where the key is the editor name and the value is the
     *         average size (in bytes) of its documents. If an editor has no
     *         documents, its average size will be {@code 0.0}.
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        Map<String, Long> totalSizeByEditor = new HashMap<>();
        Map<String, Integer> countByEditor = new HashMap<>();

        // initialise for the three known editors
        totalSizeByEditor.put(Editor.TEXT_EDITOR.getName(), 0L);
        totalSizeByEditor.put(Editor.IMAGE_EDITOR.getName(), 0L);
        totalSizeByEditor.put(Editor.VIDEO_EDITOR.getName(), 0L);

        countByEditor.put(Editor.TEXT_EDITOR.getName(), 0);
        countByEditor.put(Editor.IMAGE_EDITOR.getName(), 0);
        countByEditor.put(Editor.VIDEO_EDITOR.getName(), 0);

        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            totalSizeByEditor.merge(editorName, doc.getSize(), Long::sum);
            countByEditor.merge(editorName, 1, Integer::sum);
        }

        Map<String, Double> averageByEditor = new HashMap<>();
        for (String editorName : totalSizeByEditor.keySet()) {
            long total = totalSizeByEditor.get(editorName);
            int count = countByEditor.get(editorName);
            double average = (count == 0) ? 0.0 : ((double) total) / count;
            averageByEditor.put(editorName, average);
        }
        return averageByEditor;
    }

    /**
     * Counts the number of documents that were created after the specified
     * date.
     *
     * @param date the date to compare against; must not be {@code null}
     * @return the number of documents with a creation date later than {@code date}
     * @throws IllegalArgumentException if {@code date} is {@code null}
     */
    public long countDocumentsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        long count = 0L;
        for (Document doc : documents) {
            if (doc.getCreationDate() != null && doc.getCreationDate().isAfter(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents associated with each editor.
     *
     * @return a map where the key is the editor name and the value is the number
     *         of documents using that editor
     */
    public Map<String, Long> countDocumentsPerEditor() {
        Map<String, Long> countByEditor = new HashMap<>();

        // initialise counters for the three known editors
        countByEditor.put(Editor.TEXT_EDITOR.getName(), 0L);
        countByEditor.put(Editor.IMAGE_EDITOR.getName(), 0L);
        countByEditor.put(Editor.VIDEO_EDITOR.getName(), 0L);

        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            countByEditor.merge(editorName, 1L, Long::sum);
        }
        return countByEditor;
    }

    /**
     * Retrieves the distinct names of all authors whose documents are edited
     * with the specified editor.
     *
     * @param editor the editor to filter by; must not be {@code null}
     * @return a set of author names; empty set if no matching documents are found
     * @throws IllegalArgumentException if {@code editor} is {@code null}
     */
    public Set<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("Editor cannot be null");
        }
        Set<String> authors = new HashSet<>();
        for (Document doc : documents) {
            if (editor.getName().equals(doc.getEditor().getName())) {
                Author a = doc.getAuthor();
                if (a != null && a.getName() != null) {
                    authors.add(a.getName());
                }
            }
        }
        return authors;
    }
}