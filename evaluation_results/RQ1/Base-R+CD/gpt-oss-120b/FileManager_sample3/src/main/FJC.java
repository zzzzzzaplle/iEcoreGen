package com.example.filesystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a simple in‑memory file system that stores {@link Document}
 * objects and provides various statistics and queries about them.
 */
 class FileSystem {

    /** All documents stored in the file system. */
    private List<Document> documents;

    /** All editors known to the system (Text, Image, Video). */
    private List<Editor> editors;

    /**
     * Creates an empty {@code FileSystem} and initialises the three default editors.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
        // initialise the three supported editors
        this.editors.add(new TextEditor());
        this.editors.add(new ImageEditor());
        this.editors.add(new VideoEditor());
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
     * @param doc the document to remove; if {@code null} nothing happens
     * @return {@code true} if the document was present and removed, {@code false} otherwise
     */
    public boolean removeDocument(Document doc) {
        if (doc == null) {
            return false;
        }
        return documents.remove(doc);
    }

    /**
     * Returns an unmodifiable view of all stored documents.
     *
     * @return list of documents
     */
    public List<Document> listDocuments() {
        return Collections.unmodifiableList(documents);
    }

    /**
     * Calculates the total size (in bytes) of all documents in the file system.
     *
     * @return the sum of the sizes of all documents; {@code 0} if there are none
     */
    public int calculateTotalDocumentSize() {
        int total = 0;
        for (Document d : documents) {
            total += d.getSize();
        }
        return total;
    }

    /**
     * Computes the average document size for each editor type.
     *
     * @return a map where the key is the editor name (e.g. "Text Editor")
     *         and the value is the average size of documents edited by that editor.
     *         Editors with no documents are omitted from the map.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Integer> sumMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e == null) {
                continue; // document without an editor is ignored for this statistic
            }
            String editorName = e.getName();
            sumMap.merge(editorName, d.getSize(), Integer::sum);
            countMap.merge(editorName, 1, Integer::sum);
        }

        Map<String, Float> averageMap = new HashMap<>();
        for (String editorName : sumMap.keySet()) {
            int sum = sumMap.get(editorName);
            int cnt = countMap.get(editorName);
            averageMap.put(editorName, (float) sum / cnt);
        }
        return averageMap;
    }

    /**
     * Counts how many documents were created after the supplied date.
     *
     * @param date the date to compare against; must not be {@code null}
     * @return the number of documents with a creation date later than {@code date}
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
     * Counts the number of documents per editor type.
     *
     * @return a map where the key is the editor name and the value is the
     *         number of documents that use that editor. Editors with zero
     *         documents are omitted.
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> countMap = new HashMap<>();
        for (Document d : documents) {
            Editor e = d.getEditor();
            if (e == null) {
                continue;
            }
            String editorName = e.getName();
            countMap.merge(editorName, 1, Integer::sum);
        }
        return countMap;
    }

    /**
     * Retrieves the distinct author names whose documents are edited with the
     * specified editor.
     *
     * @param editor the editor to filter by; must not be {@code null}
     * @return a list of distinct author names; empty list if none match
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("editor must not be null");
        }
        Set<String> authors = new HashSet<>();
        for (Document d : documents) {
            Editor e = d.getEditor();
            if (editor.equals(e) && d.getAuthor() != null) {
                authors.add(d.getAuthor());
            }
        }
        return new ArrayList<>(authors);
    }

    // -------------------------------------------------------------------------
    // Getters / Setters for the internal collections (mainly for testing)
    // -------------------------------------------------------------------------

    /**
     * Returns the mutable list of documents (use with care). Primarily provided
     * for unit‑test manipulation.
     *
     * @return the internal document list
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the internal document list. Replaces any existing documents.
     *
     * @param documents the new list of documents; may be {@code null}
     */
    public void setDocuments(List<Document> documents) {
        this.documents = (documents != null) ? documents : new ArrayList<>();
    }

    /**
     * Returns the list of editors known to the system.
     *
     * @return list of editors
     */
    public List<Editor> getEditors() {
        return editors;
    }

    /**
     * Sets the list of editors. If {@code null} a new empty list is created.
     *
     * @param editors the new list of editors
     */
    public void setEditors(List<Editor> editors) {
        this.editors = (editors != null) ? editors : new ArrayList<>();
    }
}

/**
 * Represents a document stored in the {@link FileSystem}.
 */
class Document {

    /** Document name, e.g. "report.pdf". */
    private String name;

    /** Date the document was created. */
    private Date createDate;

    /** Author of the document. */
    private String author;

    /** Size of the document in bytes. */
    private int size;

    /** Editor used to create the document; may be {@code null}. */
    private Editor editor;

    /**
     * No‑argument constructor required for reflective or testing usage.
     * All fields are initialised to their default values (null / 0).
     */
    public Document() {
        // default constructor
    }

    /**
     * Creates a {@code Document} with the supplied values.
     *
     * @param name       the document name
     * @param createDate the creation date
     * @param author     the author name
     * @param size       the size in bytes
     * @param editor     the editor responsible for this document
     */
    public Document(String name, Date createDate, String author, int size, Editor editor) {
        this.name = name;
        this.createDate = createDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * @return the document name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the document name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the creation date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the creation date to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the author name
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author name to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the size in bytes
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size in bytes to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the editor assigned to this document
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * @param editor the editor to assign to this document
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Abstract base class for editors. Each editor has a name.
 */
abstract class Editor {

    /** Human readable name of the editor (e.g., "Text Editor"). */
    private String name;

    /**
     * No‑argument constructor required for reflective usage.
     */
    public Editor() {
        // default constructor
    }

    /**
     * Creates an {@code Editor} with the supplied name.
     *
     * @param name the name of the editor
     */
    public Editor(String name) {
        this.name = name;
    }

    /**
     * @return the editor name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the editor name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    // Equality based on name – useful for map look‑ups and collections
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Editor)) return false;
        Editor other = (Editor) obj;
        return name != null ? name.equals(other.name) : other.name == null;
    }

    @Override
    public int hashCode() {
        return (name != null) ? name.hashCode() : 0;
    }
}

/**
 * Concrete editor for plain‑text documents.
 */
class TextEditor extends Editor {

    /**
     * Constructs a {@code TextEditor} with the default name "Text Editor".
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
     * Constructs an {@code ImageEditor} with the default name "Image Editor".
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
     * Constructs a {@code VideoEditor} with the default name "Video Editor".
     */
    public VideoEditor() {
        super("Video Editor");
    }
}