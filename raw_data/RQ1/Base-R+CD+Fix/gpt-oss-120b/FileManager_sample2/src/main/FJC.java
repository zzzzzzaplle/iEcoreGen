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
 * Represents a file system that manages documents and editors.
 */
 class FileSystem {

    /** List of all documents stored in the file system. */
    private List<Document> documents;

    /** List of editors known to the system (Text, Image, Video). */
    private List<Editor> editors;

    /** Creates a new {@code FileSystem} with empty document collection
     *  and three default editors (Text, Image, Video). */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();

        // Initialise the three supported editors
        this.editors.add(new TextEditor());
        this.editors.add(new ImageEditor());
        this.editors.add(new VideoEditor());
    }

    /** @return the mutable list of documents (for testing purposes). */
    public List<Document> getDocuments() {
        return documents;
    }

    /** @param documents the list of documents to set. */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /** @return the mutable list of editors (for testing purposes). */
    public List<Editor> getEditors() {
        return editors;
    }

    /** @param editors the list of editors to set. */
    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    /**
     * Adds a document to the file system.
     *
     * @param doc the document to be added; must not be {@code null}
     */
    public void addDocument(Document doc) {
        if (doc != null) {
            documents.add(doc);
        }
    }

    /**
     * Removes a document from the file system.
     *
     * @param doc the document to be removed; must not be {@code null}
     */
    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    /**
     * Returns an unmodifiable view of all documents stored in the system.
     *
     * @return list of documents
     */
    public List<Document> listDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return the sum of the {@code size} field of every document; {@code 0}
     *         if there are no documents.
     */
    public int calculateTotalDocumentSize() {
        return documents.stream()
                .mapToInt(Document::getSize)
                .sum();
    }

    /**
     * Computes the average size of documents for each editor type
     * (Text Editor, Image Editor, Video Editor) present in the file system.
     *
     * @return a map where the key is the editor name and the value is the
     *         average size (as {@code Float}). Editors without documents
     *         are omitted from the map.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, List<Document>> byEditor = documents.stream()
                .filter(d -> d.getEditor() != null)
                .collect(Collectors.groupingBy(d -> d.getEditor().getName()));

        Map<String, Float> averages = new HashMap<>();
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
     * @param date the reference date; documents with a creation date strictly
     *             after this value are counted. If {@code null}, the method
     *             returns {@code 0}.
     * @return the number of documents created after {@code date}
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
     * Counts the number of documents associated with each editor type.
     *
     * @return a map where the key is the editor name and the value is the
     *         number of documents using that editor. Editors without documents
     *         are omitted.
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Long> counts = documents.stream()
                .filter(d -> d.getEditor() != null)
                .collect(Collectors.groupingBy(d -> d.getEditor().getName(),
                        Collectors.counting()));

        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            result.put(entry.getKey(), entry.getValue().intValue());
        }
        return result;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with the
     * specified editor.
     *
     * @param editor the editor to filter by; must not be {@code null}
     * @return a list of author names (may contain duplicates if an author
     *         has multiple documents with the same editor). Returns an empty
     *         list if no matching documents are found or {@code editor} is
     *         {@code null}.
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            return Collections.emptyList();
        }
        return documents.stream()
                .filter(d -> editor.equals(d.getEditor()))
                .map(Document::getAuthor)
                .filter(author -> author != null)
                .collect(Collectors.toList());
    }
}

/**
 * Represents a document stored in the file system.
 */
class Document {

    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor; // optional association

    /** No‑argument constructor. */
    public Document() {
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

    /** @return the author's name */
    public String getAuthor() {
        return author;
    }

    /** @param author the author's name to set */
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

    /** @return the associated editor (may be {@code null}) */
    public Editor getEditor() {
        return editor;
    }

    /** @param editor the editor to associate with this document */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Abstract base class for editors.
 */
abstract class Editor {

    private String name;

    /** No‑argument constructor. */
    public Editor() {
    }

    /** @return the editor name */
    public String getName() {
        return name;
    }

    /** @param name the editor name to set */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Text editor implementation.
 */
class TextEditor extends Editor {

    /** Constructs a {@code TextEditor} with a predefined name. */
    public TextEditor() {
        setName("Text Editor");
    }
}

/**
 * Image editor implementation.
 */
class ImageEditor extends Editor {

    /** Constructs an {@code ImageEditor} with a predefined name. */
    public ImageEditor() {
        setName("Image Editor");
    }
}

/**
 * Video editor implementation.
 */
class VideoEditor extends Editor {

    /** Constructs a {@code VideoEditor} with a predefined name. */
    public VideoEditor() {
        setName("Video Editor");
    }
}