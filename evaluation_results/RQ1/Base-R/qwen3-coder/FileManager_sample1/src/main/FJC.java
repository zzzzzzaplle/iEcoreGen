import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
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
        this.editor = new Editor();
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
 * Represents an editor that can be used to create documents.
 */
class Editor {
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
        double[] averages = new double[3]; // Text, Image, Video
        int[] counts = new int[3];
        int[] sizes = new int[3];

        for (Document document : documents) {
            String editorName = document.getEditor().getName();
            if ("Text Editor".equals(editorName)) {
                sizes[0] += document.getSize();
                counts[0]++;
            } else if ("Image Editor".equals(editorName)) {
                sizes[1] += document.getSize();
                counts[1]++;
            } else if ("Video Editor".equals(editorName)) {
                sizes[2] += document.getSize();
                counts[2]++;
            }
        }

        for (int i = 0; i < 3; i++) {
            averages[i] = counts[i] > 0 ? (double) sizes[i] / counts[i] : 0;
        }

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
        int[] counts = new int[3]; // Text, Image, Video

        for (Document document : documents) {
            String editorName = document.getEditor().getName();
            if ("Text Editor".equals(editorName)) {
                counts[0]++;
            } else if ("Image Editor".equals(editorName)) {
                counts[1]++;
            } else if ("Video Editor".equals(editorName)) {
                counts[2]++;
            }
        }

        return counts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     *
     * @param editorName the name of the editor to filter by
     * @return a set of author names
     */
    public Set<String> getAuthorsByEditor(String editorName) {
        Set<String> authors = new HashSet<>();
        for (Document document : documents) {
            if (editorName.equals(document.getEditor().getName())) {
                authors.add(document.getAuthor());
            }
        }
        return authors;
    }
}