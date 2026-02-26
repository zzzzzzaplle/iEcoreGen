import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a simple file system that can store documents and editors.
 * Provides various analytical operations over the stored documents.
 */
 class FileSystem {

    /** List of all documents stored in the file system. */
    private List<Document> documents;

    /** List of all editors available in the system (Text, Image, Video). */
    private List<Editor> editors;

    /**
     * Creates a new {@code FileSystem} instance.
     * The constructor initializes the internal collections and creates the three default editors.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();

        // Create the three default editors and add them to the system
        this.editors.add(new TextEditor());
        this.editors.add(new ImageEditor());
        this.editors.add(new VideoEditor());
    }

    /**
     * Adds a document to the file system.
     *
     * @param doc the document to be added; must not be {@code null}
     */
    public void addDocument(Document doc) {
        if (doc != null) {
            this.documents.add(doc);
        }
    }

    /**
     * Removes a document from the file system.
     *
     * @param doc the document to be removed; must not be {@code null}
     */
    public void removeDocument(Document doc) {
        if (doc != null) {
            this.documents.remove(doc);
        }
    }

    /**
     * Returns an unmodifiable view of all documents currently stored.
     *
     * @return list of documents
     */
    public List<Document> listDocuments() {
        return Collections.unmodifiableList(this.documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return the sum of the {@code size} field of every document; returns {@code 0} if there are no documents
     */
    public int calculateTotalDocumentSize() {
        int total = 0;
        for (Document doc : documents) {
            total += doc.getSize();
        }
        return total;
    }

    /**
     * Computes the average size of documents for each editor type (Text, Image, Video).
     *
     * @return a map where the key is the editor name and the value is the average size (as {@code Float});
     *         editors with no documents will have an average of {@code 0.0f}
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Integer> sumMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        // Initialise maps for each known editor
        for (Editor editor : editors) {
            sumMap.put(editor.getName(), 0);
            countMap.put(editor.getName(), 0);
        }

        // Accumulate sizes and counts
        for (Document doc : documents) {
            Editor editor = doc.getEditor();
            if (editor != null) {
                String name = editor.getName();
                sumMap.put(name, sumMap.get(name) + doc.getSize());
                countMap.put(name, countMap.get(name) + 1);
            }
        }

        // Build the result map with averages
        Map<String, Float> averageMap = new HashMap<>();
        for (String editorName : sumMap.keySet()) {
            int count = countMap.get(editorName);
            float average = count == 0 ? 0.0f : (float) sumMap.get(editorName) / count;
            averageMap.put(editorName, average);
        }

        return averageMap;
    }

    /**
     * Counts how many documents were created after the specified date.
     *
     * @param date the reference date; documents with a creation date strictly after this value are counted
     * @return number of documents created after {@code date}
     * @throws IllegalArgumentException if {@code date} is {@code null}
     */
    public int countDocumentsAfterDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        int count = 0;
        for (Document doc : documents) {
            if (doc.getCreateDate().after(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents for each editor type.
     *
     * @return a map where the key is the editor name and the value is the number of documents using that editor
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> countMap = new HashMap<>();

        // Initialise counts for each editor
        for (Editor editor : editors) {
            countMap.put(editor.getName(), 0);
        }

        // Count documents per editor
        for (Document doc : documents) {
            Editor editor = doc.getEditor();
            if (editor != null) {
                String name = editor.getName();
                countMap.put(name, countMap.get(name) + 1);
            }
        }

        return countMap;
    }

    /**
     * Retrieves the distinct author names whose documents are edited with the specified editor.
     *
     * @param editor the editor to filter documents by; must not be {@code null}
     * @return list of unique author names; empty list if no matching documents are found
     * @throws IllegalArgumentException if {@code editor} is {@code null}
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("Editor cannot be null");
        }
        Set<String> authorSet = new HashSet<>();
        for (Document doc : documents) {
            Editor docEditor = doc.getEditor();
            if (docEditor != null && docEditor.getName().equals(editor.getName())) {
                authorSet.add(doc.getAuthor());
            }
        }
        return new ArrayList<>(authorSet);
    }

    // -------------------------------------------------------------------------
    // Accessors for the internal collections (useful for testing)
    // -------------------------------------------------------------------------

    /**
     * Returns the internal list of editors. The list is unmodifiable to preserve encapsulation.
     *
     * @return list of editors
     */
    public List<Editor> getEditors() {
        return Collections.unmodifiableList(this.editors);
    }

    /**
     * Sets the internal list of editors. Primarily intended for testing purposes.
     *
     * @param editors the list of editors to replace the current one; may be {@code null}
     */
    public void setEditors(List<Editor> editors) {
        this.editors = editors == null ? new ArrayList<>() : editors;
    }

    /**
     * Replaces the current document collection with the supplied list.
     *
     * @param documents the new list of documents; may be {@code null}
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents == null ? new ArrayList<>() : documents;
    }
}

/**
 * Represents a document stored in the file system.
 */
class Document {

    /** Name of the document. */
    private String name;

    /** Creation date of the document. */
    private Date createDate;

    /** Author of the document. */
    private String author;

    /** Size of the document in bytes (or any unit). */
    private int size;

    /** The editor used to create / edit this document; may be {@code null}. */
    private Editor editor;

    /**
     * Creates an empty {@code Document} instance.
     */
    public Document() {
        // default constructor – fields can be set via setters
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the document name.
     *
     * @return document name
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
     * @return creation date
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
     * @return author name
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
     * Returns the document size.
     *
     * @return size in integer units
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the document size.
     *
     * @param size size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the editor associated with this document.
     *
     * @return editor instance or {@code null} if none assigned
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Assigns an editor to this document.
     *
     * @param editor the editor to associate; may be {@code null}
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Abstract base class for all editors.
 */
abstract class Editor {

    /** Human‑readable name of the editor (e.g., "Text Editor"). */
    private String name;

    /**
     * Default constructor.
     */
    public Editor() {
        // Sub‑classes should set the concrete name
    }

    /**
     * Constructs an editor with the given name.
     *
     * @param name the editor name
     */
    public Editor(String name) {
        this.name = name;
    }

    /**
     * Returns the editor's name.
     *
     * @return name of the editor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the editor's name.
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Concrete editor for plain text documents.
 */
class TextEditor extends Editor {

    /**
     * Creates a {@code TextEditor} with the default name "Text Editor".
     */
    public TextEditor() {
        super("Text Editor");
    }
}

/**
 * Concrete editor for image documents.
 */
class ImageEditor extends Editor {

    /**
     * Creates an {@code ImageEditor} with the default name "Image Editor".
     */
    public ImageEditor() {
        super("Image Editor");
    }
}

/**
 * Concrete editor for video documents.
 */
class VideoEditor extends Editor {

    /**
     * Creates a {@code VideoEditor} with the default name "Video Editor".
     */
    public VideoEditor() {
        super("Video Editor");
    }
}