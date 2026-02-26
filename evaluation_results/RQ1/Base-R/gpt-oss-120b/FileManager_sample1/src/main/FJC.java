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
 * Base class for all editors. An editor is identified only by its name.
 */
 class Editor {
    private String name;

    /** Unparameterized constructor */
    public Editor() {
        this.name = "";
    }

    /**
     * Creates an editor with the given name.
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

    /** @param name the name to set for the editor */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on editor name */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Editor)) return false;
        Editor other = (Editor) obj;
        return name != null && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

/**
 * Concrete editor for text documents.
 */
 class TextEditor extends Editor {
    /** Unparameterized constructor – sets the editor name to "Text Editor". */
    public TextEditor() {
        super("Text Editor");
    }
}

/**
 * Concrete editor for image documents.
 */
 class ImageEditor extends Editor {
    /** Unparameterized constructor – sets the editor name to "Image Editor". */
    public ImageEditor() {
        super("Image Editor");
    }
}

/**
 * Concrete editor for video documents.
 */
 class VideoEditor extends Editor {
    /** Unparameterized constructor – sets the editor name to "Video Editor". */
    public VideoEditor() {
        super("Video Editor");
    }
}

/**
 * Represents a document stored in the file system.
 */
 class Document {
    private String name;
    private LocalDate creationDate;
    private String author;
    private long size;               // size in bytes (or any unit)
    private Editor editor;           // editor used for this document

    /** Unparameterized constructor */
    public Document() {
        this.name = "";
        this.creationDate = LocalDate.now();
        this.author = "";
        this.size = 0L;
        this.editor = new Editor();
    }

    /**
     * Creates a document with all required fields.
     *
     * @param name         the document name
     * @param creationDate the date the document was created
     * @param author       the author of the document
     * @param size         the size of the document
     * @param editor       the editor associated with the document
     */
    public Document(String name, LocalDate creationDate, String author, long size, Editor editor) {
        this.name = name;
        this.creationDate = creationDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    /** @return document name */
    public String getName() {
        return name;
    }

    /** @param name the document name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return creation date */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /** @param creationDate the creation date to set */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /** @return author name */
    public String getAuthor() {
        return author;
    }

    /** @param author the author name to set */
    public void setAuthor(String author) {
        this.author = author;
    }

    /** @return document size */
    public long getSize() {
        return size;
    }

    /** @param size the document size to set */
    public void setSize(long size) {
        this.size = size;
    }

    /** @return associated editor */
    public Editor getEditor() {
        return editor;
    }

    /** @param editor the editor to associate with the document */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Central class that manages a collection of documents.
 */
 class FileSystem {
    private final List<Document> documents = new ArrayList<>();

    /** Unparameterized constructor */
    public FileSystem() {
        // No additional initialization required
    }

    /**
     * Adds a document to the file system.
     *
     * @param doc the document to add
     */
    public void addDocument(Document doc) {
        if (doc != null) {
            documents.add(doc);
        }
    }

    /**
     * Removes a document from the file system.
     *
     * @param doc the document to remove
     * @return {@code true} if the document was present and removed, {@code false} otherwise
     */
    public boolean removeDocument(Document doc) {
        return documents.remove(doc);
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
     * @return the sum of document sizes; returns 0 if there are no documents
     */
    public long calculateTotalSize() {
        return documents.stream()
                        .mapToLong(Document::getSize)
                        .sum();
    }

    /**
     * Computes the average size of documents for each editor type (Text, Image, Video).
     *
     * @return a map where the key is the editor name and the value is the average size;
     *         editors with no documents are omitted from the map
     */
    public Map<String, Double> computeAverageSizeByEditor() {
        // Group documents by editor name and compute average size per group
        return documents.stream()
                .collect(Collectors.groupingBy(
                        doc -> doc.getEditor().getName(),
                        Collectors.averagingLong(Document::getSize)));
    }

    /**
     * Counts how many documents were created after the specified date.
     *
     * @param date the date to compare with (exclusive)
     * @return number of documents whose creation date is after {@code date}
     */
    public long countDocumentsCreatedAfter(LocalDate date) {
        if (date == null) {
            return 0L;
        }
        return documents.stream()
                .filter(doc -> doc.getCreationDate().isAfter(date))
                .count();
    }

    /**
     * Counts the number of documents associated with each editor.
     *
     * @return a map where the key is the editor name and the value is the document count;
     *         editors with zero documents are omitted from the map
     */
    public Map<String, Long> countDocumentsByEditor() {
        return documents.stream()
                .collect(Collectors.groupingBy(
                        doc -> doc.getEditor().getName(),
                        Collectors.counting()));
    }

    /**
     * Retrieves the distinct author names of all documents edited with the specified editor.
     *
     * @param editorName the name of the editor (e.g., "Text Editor")
     * @return a set of author names; empty if no documents match the editor
     */
    public Set<String> getAuthorsByEditor(String editorName) {
        if (editorName == null) {
            return Collections.emptySet();
        }
        return documents.stream()
                .filter(doc -> editorName.equals(doc.getEditor().getName()))
                .map(Document::getAuthor)
                .collect(Collectors.toSet());
    }
}