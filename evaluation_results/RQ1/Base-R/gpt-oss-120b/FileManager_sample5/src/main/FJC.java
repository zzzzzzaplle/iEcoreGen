import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an editor that can be assigned to a {@link Document}.
 * Three predefined editors are available: Text, Image and Video.
 */
 class Editor {

    /** Name of the editor (e.g., "Text Editor"). */
    private String name;

    /** Predefined Text Editor instance. */
    public static final Editor TEXT_EDITOR = new Editor("Text Editor");

    /** Predefined Image Editor instance. */
    public static final Editor IMAGE_EDITOR = new Editor("Image Editor");

    /** Predefined Video Editor instance. */
    public static final Editor VIDEO_EDITOR = new Editor("Video Editor");

    /**
     * No‑argument constructor required for reflection / testing.
     * The {@code name} field will be {@code null} until set via the setter.
     */
    public Editor() {
    }

    /**
     * Creates an {@code Editor} with the given name.
     *
     * @param name the name of the editor
     */
    public Editor(String name) {
        this.name = name;
    }

    /** @return the name of the editor */
    public String getName() {
        return name;
    }

    /** @param name the name to set for this editor */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on the editor's name. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Editor)) return false;
        Editor other = (Editor) obj;
        return name != null ? name.equals(other.name) : other.name == null;
    }

    /** Hash code based on the editor's name. */
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    /** String representation of the editor. */
    @Override
    public String toString() {
        return "Editor{name='" + name + '\'' + '}';
    }
}

/**
 * Represents a document stored in the file system.
 */
 class Document {

    /** Document's name. */
    private String name;

    /** Date when the document was created. */
    private LocalDate creationDate;

    /** Author of the document. */
    private String author;

    /** Size of the document in bytes. */
    private long size;

    /** Editor assigned to this document. */
    private Editor editor;

    /**
     * No‑argument constructor required for reflection / testing.
     */
    public Document() {
    }

    /**
     * Constructs a {@code Document} with all required fields.
     *
     * @param name          document name
     * @param creationDate  creation date
     * @param author        author name
     * @param size          size in bytes
     * @param editor        editor responsible for the document
     */
    public Document(String name, LocalDate creationDate, String author, long size, Editor editor) {
        this.name = name;
        this.creationDate = creationDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    /** @return the document's name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the creation date */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /** @param creationDate the creation date to set */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /** @return the author's name */
    public String getAuthor() {
        return author;
    }

    /** @param author the author to set */
    public void setAuthor(String author) {
        this.author = author;
    }

    /** @return the size in bytes */
    public long getSize() {
        return size;
    }

    /** @param size the size to set */
    public void setSize(long size) {
        this.size = size;
    }

    /** @return the editor assigned to this document */
    public Editor getEditor() {
        return editor;
    }

    /** @param editor the editor to assign */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    /** String representation of a document. */
    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", author='" + author + '\'' +
                ", size=" + size +
                ", editor=" + editor +
                '}';
    }
}

/**
 * Represents a simple in‑memory file system that stores {@link Document} objects.
 * Provides methods to query and aggregate information about the stored documents.
 */
 class FileSystem {

    /** Internal storage for documents. */
    private List<Document> documents;

    /**
     * No‑argument constructor required for reflection / testing.
     * Initializes the internal document list.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
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
     * @return {@code true} if the document was present and removed, {@code false} otherwise
     */
    public boolean removeDocument(Document doc) {
        if (doc != null) {
            return documents.remove(doc);
        }
        return false;
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
     * Calculates the total size (in bytes) of all documents in the file system.
     *
     * @return sum of the sizes of all documents; {@code 0} if the file system is empty
     */
    public long getTotalSize() {
        long sum = 0L;
        for (Document d : documents) {
            sum += d.getSize();
        }
        return sum;
    }

    /**
     * Computes the average size of documents for each editor (Text, Image, Video).
     *
     * @return a map where the key is an {@link Editor} and the value is the average size
     *         (as a {@code double}) of documents edited with that editor.
     *         Editors with no documents will have an average size of {@code 0.0}.
     */
    public Map<Editor, Double> getAverageSizeByEditor() {
        Map<Editor, Long> totalSizeMap = new HashMap<>();
        Map<Editor, Integer> countMap = new HashMap<>();

        // Initialise maps for the three known editors
        Editor[] editors = {Editor.TEXT_EDITOR, Editor.IMAGE_EDITOR, Editor.VIDEO_EDITOR};
        for (Editor e : editors) {
            totalSizeMap.put(e, 0L);
            countMap.put(e, 0);
        }

        // Aggregate sizes and counts
        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e == null) continue; // skip documents without editor
            totalSizeMap.put(e, totalSizeMap.getOrDefault(e, 0L) + d.getSize());
            countMap.put(e, countMap.getOrDefault(e, 0) + 1);
        }

        // Build result map with averages
        Map<Editor, Double> avgMap = new HashMap<>();
        for (Editor e : editors) {
            long total = totalSizeMap.getOrDefault(e, 0L);
            int cnt = countMap.getOrDefault(e, 0);
            double avg = cnt == 0 ? 0.0 : ((double) total) / cnt;
            avgMap.put(e, avg);
        }
        return avgMap;
    }

    /**
     * Counts the number of documents that were created after the specified date.
     *
     * @param date the date to compare against; must not be {@code null}
     * @return number of documents with a creation date later than {@code date}
     */
    public int countDocumentsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date must not be null");
        }
        int count = 0;
        for (Document d : documents) {
            if (d.getCreationDate() != null && d.getCreationDate().isAfter(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents for each editor (Text, Image, Video).
     *
     * @return a map where the key is an {@link Editor} and the value is the count of
     *         documents assigned to that editor. Editors with no documents will have a count of {@code 0}.
     */
    public Map<Editor, Integer> countDocumentsByEditor() {
        Map<Editor, Integer> countMap = new HashMap<>();
        Editor[] editors = {Editor.TEXT_EDITOR, Editor.IMAGE_EDITOR, Editor.VIDEO_EDITOR};
        for (Editor e : editors) {
            countMap.put(e, 0);
        }

        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e != null) {
                countMap.put(e, countMap.getOrDefault(e, 0) + 1);
            }
        }
        return countMap;
    }

    /**
     * Retrieves the distinct author names of all documents that are edited with the specified editor.
     *
     * @param editor the editor to filter documents by; must not be {@code null}
     * @return a {@link Set} containing unique author names; empty set if no matching documents exist
     */
    public Set<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("Editor must not be null");
        }
        Set<String> authors = new HashSet<>();
        for (Document d : documents) {
            if (editor.equals(d.getEditor()) && d.getAuthor() != null) {
                authors.add(d.getAuthor());
            }
        }
        return authors;
    }

    /** Getter for the internal document list (useful for testing). */
    public List<Document> getDocuments() {
        return documents;
    }

    /** Setter for the internal document list (useful for testing). */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}