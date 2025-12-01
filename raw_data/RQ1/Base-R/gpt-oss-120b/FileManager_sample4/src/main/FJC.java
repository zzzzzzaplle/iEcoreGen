import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an editor that can be associated with a {@link Document}.
 * Concrete editors (Text, Image, Video) extend this class.
 */
public abstract class Editor {

    /** Human readable name of the editor. */
    private String name;

    /** No‑argument constructor required by the specification. */
    public Editor() {
        // default name can be set by subclasses
    }

    /** Returns the name of the editor. */
    public String getName() {
        return name;
    }

    /** Sets the name of the editor. */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Text editor implementation.
 */
 class TextEditor extends Editor {

    /** No‑argument constructor required by the specification. */
    public TextEditor() {
        setName("Text Editor");
    }
}

/**
 * Image editor implementation.
 */
 class ImageEditor extends Editor {

    /** No‑argument constructor required by the specification. */
    public ImageEditor() {
        setName("Image Editor");
    }
}

/**
 * Video editor implementation.
 */
 class VideoEditor extends Editor {

    /** No‑argument constructor required by the specification. */
    public VideoEditor() {
        setName("Video Editor");
    }
}

/**
 * Represents a document stored in the file system.
 */
 class Document {

    private String name;
    private LocalDate creationDate;
    private String author;          // author information – simplified to a name
    private long size;              // size in bytes (or any unit)
    private Editor editor;          // editor used for this document

    /** No‑argument constructor required by the specification. */
    public Document() {
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

    /** Returns the size of the document. */
    public long getSize() {
        return size;
    }

    /** Sets the size of the document. */
    public void setSize(long size) {
        this.size = size;
    }

    /** Returns the editor associated with this document. */
    public Editor getEditor() {
        return editor;
    }

    /** Sets the editor associated with this document. */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * A simple in‑memory file system that stores {@link Document}s.
 * Provides various analytical operations required by the functional
 * specifications.
 */
 class FileSystem {

    /** Internal storage for documents. */
    private final List<Document> documents = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public FileSystem() {
    }

    /** Returns a mutable view of the stored documents. */
    public List<Document> getDocuments() {
        return documents;
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
     * @return {@code true} if the document was present and removed,
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code document} is {@code null}
     */
    public boolean removeDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        return documents.remove(document);
    }

    /**
     * Returns an unmodifiable list containing all documents in the file system.
     *
     * @return list of documents
     */
    public List<Document> listAllDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return the sum of the sizes of all documents; {@code 0} if there are none
     */
    public long calculateTotalSize() {
        long total = 0L;
        for (Document d : documents) {
            total += d.getSize();
        }
        return total;
    }

    /**
     * Computes the average size of documents for a specific editor.
     *
     * @param editor the editor whose documents are considered; must not be {@code null}
     * @return the average size as a {@code double}; {@code 0.0} if the editor has no documents
     * @throws IllegalArgumentException if {@code editor} is {@code null}
     */
    public double computeAverageSizeForEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("Editor cannot be null");
        }
        long sum = 0L;
        int count = 0;
        for (Document d : documents) {
            if (editor.getClass().equals(d.getEditor().getClass())) {
                sum += d.getSize();
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) sum / count;
    }

    /**
     * Computes the average size of documents for each editor type present in the system.
     *
     * @return a map where the key is the editor name and the value is the average size;
     *         editors without documents have an average of {@code 0.0}
     */
    public Map<String, Double> computeAverageSizeForAllEditors() {
        Map<String, Long> sumMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (Document d : documents) {
            String editorName = d.getEditor().getName();
            sumMap.merge(editorName, d.getSize(), Long::sum);
            countMap.merge(editorName, 1, Integer::sum);
        }

        Map<String, Double> avgMap = new HashMap<>();
        // Ensure all three editors appear even if they have no documents
        addEditorIfMissing(avgMap, sumMap, countMap, new TextEditor());
        addEditorIfMissing(avgMap, sumMap, countMap, new ImageEditor());
        addEditorIfMissing(avgMap, sumMap, countMap, new VideoEditor());

        return avgMap;
    }

    private void addEditorIfMissing(Map<String, Double> avgMap,
                                    Map<String, Long> sumMap,
                                    Map<String, Integer> countMap,
                                    Editor editor) {
        String name = editor.getName();
        long sum = sumMap.getOrDefault(name, 0L);
        int cnt = countMap.getOrDefault(name, 0);
        double avg = cnt == 0 ? 0.0 : (double) sum / cnt;
        avgMap.put(name, avg);
    }

    /**
     * Counts the number of documents that were created after the specified date.
     *
     * @param date the date to compare against; must not be {@code null}
     * @return the number of documents with a creation date later than {@code date}
     * @throws IllegalArgumentException if {@code date} is {@code null}
     */
    public int countDocumentsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        int counter = 0;
        for (Document d : documents) {
            if (d.getCreationDate() != null && d.getCreationDate().isAfter(date)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Counts the number of documents for each editor type.
     *
     * @return a map where the key is the editor name and the value is the document count
     */
    public Map<String, Integer> countDocumentsByEditor() {
        Map<String, Integer> countMap = new HashMap<>();

        for (Document d : documents) {
            String editorName = d.getEditor().getName();
            countMap.merge(editorName, 1, Integer::sum);
        }

        // Ensure all editors are present in the map
        ensureEditorKey(countMap, new TextEditor());
        ensureEditorKey(countMap, new ImageEditor());
        ensureEditorKey(countMap, new VideoEditor());

        return countMap;
    }

    private void ensureEditorKey(Map<String, Integer> map, Editor editor) {
        map.putIfAbsent(editor.getName(), 0);
    }

    /**
     * Retrieves the distinct author names whose documents are edited with the
     * specified editor.
     *
     * @param editor the editor to filter by; must not be {@code null}
     * @return a set of author names; empty if no matching documents are found
     * @throws IllegalArgumentException if {@code editor} is {@code null}
     */
    public Set<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("Editor cannot be null");
        }
        Set<String> authors = new HashSet<>();
        for (Document d : documents) {
            if (editor.getClass().equals(d.getEditor().getClass())) {
                if (d.getAuthor() != null) {
                    authors.add(d.getAuthor());
                }
            }
        }
        return authors;
    }
}