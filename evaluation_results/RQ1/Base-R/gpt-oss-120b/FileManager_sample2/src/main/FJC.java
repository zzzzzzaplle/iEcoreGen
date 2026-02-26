import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents an editor that can be assigned to a {@link Document}.
 * Concrete editors are {@link TextEditor}, {@link ImageEditor} and {@link VideoEditor}.
 */
 class Editor {

    /** Human‑readable name of the editor (e.g. "Text Editor"). */
    private String name;

    /** Default (no‑arg) constructor. */
    public Editor() {
        // intentionally left blank
    }

    /** Creates an editor with a given name. */
    public Editor(String name) {
        this.name = name;
    }

    /** Returns the name of the editor. */
    public String getName() {
        return name;
    }

    /** Sets the name of the editor. */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on the editor's name. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Editor)) return false;
        Editor other = (Editor) obj;
        return name != null && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    @Override
    public String toString() {
        return "Editor{name='" + name + "'}";
    }
}

/**
 * Text editor implementation.
 */
class TextEditor extends Editor {

    /** Default (no‑arg) constructor. */
    public TextEditor() {
        super("Text Editor");
    }
}

/**
 * Image editor implementation.
 */
class ImageEditor extends Editor {

    /** Default (no‑arg) constructor. */
    public ImageEditor() {
        super("Image Editor");
    }
}

/**
 * Video editor implementation.
 */
class VideoEditor extends Editor {

    /** Default (no‑arg) constructor. */
    public VideoEditor() {
        super("Video Editor");
    }
}

/**
 * Represents a document stored in the file system.
 */
class Document {

    /** Document's name (e.g. "Report.docx"). */
    private String name;

    /** Date on which the document was created. */
    private LocalDate creationDate;

    /** Author of the document (could be a simple string). */
    private String author;

    /** Size of the document in bytes. */
    private long size;

    /** Editor assigned to this document. */
    private Editor editor;

    /** Default (no‑arg) constructor. */
    public Document() {
        // intentionally left blank
    }

    /** Full constructor for convenience. */
    public Document(String name, LocalDate creationDate, String author, long size, Editor editor) {
        this.name = name;
        this.creationDate = creationDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    /** Returns the document's name. */
    public String getName() {
        return name;
    }

    /** Sets the document's name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the creation date of the document. */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /** Sets the creation date of the document. */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /** Returns the author of the document. */
    public String getAuthor() {
        return author;
    }

    /** Sets the author of the document. */
    public void setAuthor(String author) {
        this.author = author;
    }

    /** Returns the size of the document in bytes. */
    public long getSize() {
        return size;
    }

    /** Sets the size of the document in bytes. */
    public void setSize(long size) {
        this.size = size;
    }

    /** Returns the editor assigned to this document. */
    public Editor getEditor() {
        return editor;
    }

    /** Sets the editor assigned to this document. */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    @Override
    public String toString() {
        return "Document{name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", author='" + author + '\'' +
                ", size=" + size +
                ", editor=" + (editor != null ? editor.getName() : "null") +
                '}';
    }
}

/**
 * Manages a collection of {@link Document} objects.
 * Provides operations required by the functional specifications.
 */
 class FileSystem {

    /** Internal storage for documents. */
    private final List<Document> documents = new ArrayList<>();

    /** Default (no‑arg) constructor. */
    public FileSystem() {
        // intentionally left blank
    }

    /**
     * Adds a document to the file system.
     *
     * @param document the document to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code document} is {@code null}
     */
    public void addDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }
        documents.add(document);
    }

    /**
     * Removes a document from the file system.
     *
     * @param document the document to remove; must not be {@code null}
     * @return {@code true} if the document was present and removed, {@code false} otherwise
     * @throws IllegalArgumentException if {@code document} is {@code null}
     */
    public boolean removeDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }
        return documents.remove(document);
    }

    /**
     * Returns an unmodifiable view of all documents stored in the file system.
     *
     * @return list of documents (read‑only)
     */
    public List<Document> listDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return the sum of the sizes of all documents; {@code 0} if no documents exist
     */
    public long calculateTotalSize() {
        return documents.stream()
                .mapToLong(Document::getSize)
                .sum();
    }

    /**
     * Computes the average size of documents for each editor type (Text, Image, Video).
     *
     * @return a map where the key is the editor name and the value is the average document size.
     *         If an editor has no documents, its average is reported as {@code 0.0}.
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        // Group documents by editor name
        Map<String, List<Document>> byEditor = documents.stream()
                .filter(d -> d.getEditor() != null)
                .collect(Collectors.groupingBy(d -> d.getEditor().getName()));

        // Prepare result map with all three editors, ensuring presence even if empty
        Map<String, Double> result = new HashMap<>();
        result.put("Text Editor", 0.0);
        result.put("Image Editor", 0.0);
        result.put("Video Editor", 0.0);

        for (Map.Entry<String, List<Document>> entry : byEditor.entrySet()) {
            double avg = entry.getValue().stream()
                    .mapToLong(Document::getSize)
                    .average()
                    .orElse(0.0);
            result.put(entry.getKey(), avg);
        }
        return result;
    }

    /**
     * Counts how many documents were created after the specified date.
     *
     * @param date the cutoff date (exclusive); must not be {@code null}
     * @return number of documents whose creation date is later than {@code date}
     * @throws IllegalArgumentException if {@code date} is {@code null}
     */
    public int countDocumentsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        return (int) documents.stream()
                .filter(d -> d.getCreationDate() != null && d.getCreationDate().isAfter(date))
                .count();
    }

    /**
     * Counts the number of documents for each editor type.
     *
     * @return a map where the key is the editor name and the value is the document count.
     *         Editors without documents are represented with a count of {@code 0}.
     */
    public Map<String, Integer> countDocumentsPerEditor() {
        Map<String, Integer> result = new HashMap<>();
        result.put("Text Editor", 0);
        result.put("Image Editor", 0);
        result.put("Video Editor", 0);

        documents.stream()
                .filter(d -> d.getEditor() != null)
                .forEach(d -> {
                    String name = d.getEditor().getName();
                    result.put(name, result.getOrDefault(name, 0) + 1);
                });

        return result;
    }

    /**
     * Retrieves the distinct names of authors whose documents are edited with the specified editor.
     *
     * @param editorName the name of the editor (e.g., "Text Editor"); must not be {@code null}
     * @return a set of author names; empty if no matching documents are found
     * @throws IllegalArgumentException if {@code editorName} is {@code null}
     */
    public Set<String> getAuthorsByEditor(String editorName) {
        if (editorName == null) {
            throw new IllegalArgumentException("Editor name cannot be null.");
        }
        return documents.stream()
                .filter(d -> d.getEditor() != null && editorName.equals(d.getEditor().getName()))
                .map(Document::getAuthor)
                .filter(author -> author != null && !author.isEmpty())
                .collect(Collectors.toSet());
    }
}