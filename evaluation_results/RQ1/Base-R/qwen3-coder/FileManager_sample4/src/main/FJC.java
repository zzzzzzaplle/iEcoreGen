import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a document in the file system.
 */
class Document {
    private String name;
    private Date creationDate;
    private String author;
    private int size;
    private Editor editor;

    /**
     * Default constructor for Document.
     */
    public Document() {
        this.name = "";
        this.creationDate = new Date();
        this.author = "";
        this.size = 0;
        this.editor = new TextEditor();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Abstract class representing an editor.
 */
abstract class Editor {
    private String name;

    /**
     * Default constructor for Editor.
     */
    public Editor() {
        this.name = "";
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a text editor.
 */
class TextEditor extends Editor {
    /**
     * Default constructor for TextEditor.
     */
    public TextEditor() {
        super();
        setName("Text Editor");
    }
}

/**
 * Represents an image editor.
 */
class ImageEditor extends Editor {
    /**
     * Default constructor for ImageEditor.
     */
    public ImageEditor() {
        super();
        setName("Image Editor");
    }
}

/**
 * Represents a video editor.
 */
class VideoEditor extends Editor {
    /**
     * Default constructor for VideoEditor.
     */
    public VideoEditor() {
        super();
        setName("Video Editor");
    }
}

/**
 * Represents a file system containing documents.
 */
class FileSystem {
    private List<Document> documents;

    /**
     * Default constructor for FileSystem.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
    }

    // Getters and setters
    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /**
     * Adds a document to the file system.
     *
     * @param document the document to add
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * Removes a document from the file system.
     *
     * @param document the document to remove
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Lists all documents in the file system.
     *
     * @return a list of all documents
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return the sum of all document sizes, or 0 if there are no documents
     */
    public int calculateTotalSize() {
        int totalSize = 0;
        for (Document document : documents) {
            totalSize += document.getSize();
        }
        return totalSize;
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     *
     * @return an array containing the average sizes for Text Editor, Image Editor, and Video Editor respectively
     */
    public double[] computeAverageSizeByEditor() {
        int textEditorCount = 0;
        int imageEditorCount = 0;
        int videoEditorCount = 0;
        int textEditorSize = 0;
        int imageEditorSize = 0;
        int videoEditorSize = 0;

        for (Document document : documents) {
            if (document.getEditor() instanceof TextEditor) {
                textEditorCount++;
                textEditorSize += document.getSize();
            } else if (document.getEditor() instanceof ImageEditor) {
                imageEditorCount++;
                imageEditorSize += document.getSize();
            } else if (document.getEditor() instanceof VideoEditor) {
                videoEditorCount++;
                videoEditorSize += document.getSize();
            }
        }

        double[] averages = new double[3];
        averages[0] = textEditorCount > 0 ? (double) textEditorSize / textEditorCount : 0;
        averages[1] = imageEditorCount > 0 ? (double) imageEditorSize / imageEditorCount : 0;
        averages[2] = videoEditorCount > 0 ? (double) videoEditorSize / videoEditorCount : 0;

        return averages;
    }

    /**
     * Counts the number of documents that were created after a specified date.
     *
     * @param date the date to compare against
     * @return the number of documents created after the specified date
     */
    public int countDocumentsCreatedAfter(Date date) {
        int count = 0;
        for (Document document : documents) {
            if (document.getCreationDate().after(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents for each editor in the file system.
     *
     * @return an array containing the counts for Text Editor, Image Editor, and Video Editor respectively
     */
    public int[] countDocumentsByEditor() {
        int[] counts = new int[3]; // Text Editor, Image Editor, Video Editor
        for (Document document : documents) {
            if (document.getEditor() instanceof TextEditor) {
                counts[0]++;
            } else if (document.getEditor() instanceof ImageEditor) {
                counts[1]++;
            } else if (document.getEditor() instanceof VideoEditor) {
                counts[2]++;
            }
        }
        return counts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     *
     * @param editor the editor to filter by
     * @return a set of author names
     */
    public Set<String> getAuthorsByEditor(Editor editor) {
        Set<String> authors = new HashSet<>();
        for (Document document : documents) {
            if (document.getEditor().getClass().equals(editor.getClass())) {
                authors.add(document.getAuthor());
            }
        }
        return authors;
    }
}