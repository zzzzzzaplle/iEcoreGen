import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a file system that manages documents and editors.
 * The file system allows adding/removing documents, listing documents,
 * and performing various calculations on the document collection.
 */
 class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Unparameterized constructor initializes empty lists for documents and editors.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
    }

    /**
     * Adds a document to the file system.
     * @param doc the document to add
     */
    public void addDocument(Document doc) {
        if (doc != null) {
            documents.add(doc);
        }
    }

    /**
     * Removes a document from the file system.
     * @param doc the document to remove
     */
    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    /**
     * Returns a list of all documents in the file system.
     * @return list of documents
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * @return sum of all document sizes, returns 0 if no documents exist
     */
    public int calculateTotalDocumentSize() {
        return documents.stream()
                .mapToInt(Document::getSize)
                .sum();
    }

    /**
     * Computes the average size of documents for each editor type.
     * @return map with editor type as key and average size as value
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Double> averages = documents.stream()
                .filter(doc -> doc.getEditor() != null)
                .collect(Collectors.groupingBy(
                    doc -> doc.getEditor().getName(),
                    Collectors.averagingInt(Document::getSize)
                ));
        
        // Convert Double to Float for compatibility with design model
        Map<String, Float> result = new HashMap<>();
        averages.forEach((k, v) -> result.put(k, v.floatValue()));
        return result;
    }

    /**
     * Counts documents created after the specified date.
     * @param date the cutoff date (exclusive)
     * @return number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) documents.stream()
                .filter(doc -> doc.getCreateDate().after(date))
                .count();
    }

    /**
     * Counts the number of documents for each editor type.
     * @return map with editor type as key and document count as value
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        return documents.stream()
                .filter(doc -> doc.getEditor() != null)
                .collect(Collectors.groupingBy(
                    doc -> doc.getEditor().getName(),
                    Collectors.summingInt(doc -> 1)
                ));
    }

    /**
     * Retrieves author names for documents edited with the specified editor.
     * @param editor the editor to filter by
     * @return list of unique author names
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        return documents.stream()
                .filter(doc -> doc.getEditor() != null && doc.getEditor().equals(editor))
                .map(Document::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }

    // Getters and setters for private properties
    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }
}

/**
 * Represents a document in the file system.
 */
class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;

    /**
     * Unparameterized constructor initializes with default values.
     */
    public Document() {
        this.name = "";
        this.createDate = new Date();
        this.author = "";
        this.size = 0;
        this.editor = null;
    }

    // Getters and setters for private properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
 * Abstract base class for editors.
 */
abstract class Editor {
    private String name;

    /**
     * Unparameterized constructor initializes with empty name.
     */
    public Editor() {
        this.name = "";
    }

    // Getters and setters for private properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Concrete editor implementation for text documents.
 */
class TextEditor extends Editor {
    /**
     * Unparameterized constructor calls parent constructor.
     */
    public TextEditor() {
        super();
    }
}

/**
 * Concrete editor implementation for image documents.
 */
class ImageEditor extends Editor {
    /**
     * Unparameterized constructor calls parent constructor.
     */
    public ImageEditor() {
        super();
    }
}

/**
 * Concrete editor implementation for video documents.
 */
class VideoEditor extends Editor {
    /**
     * Unparameterized constructor calls parent constructor.
     */
    public VideoEditor() {
        super();
    }
}