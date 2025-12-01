import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a document in the file system
 */
class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;
    
    public Document() {
    }
    
    /**
     * Gets the document name
     * @return the document name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the document name
     * @param name the document name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the creation date
     * @return the creation date
     */
    public Date getCreateDate() {
        return createDate;
    }
    
    /**
     * Sets the creation date
     * @param createDate the creation date to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * Gets the author
     * @return the author
     */
    public String getAuthor() {
        return author;
    }
    
    /**
     * Sets the author
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    
    /**
     * Gets the size
     * @return the size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Sets the size
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    /**
     * Gets the editor
     * @return the editor
     */
    public Editor getEditor() {
        return editor;
    }
    
    /**
     * Sets the editor
     * @param editor the editor to set
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Abstract base class for all editors
 */
abstract class Editor {
    private String name;
    
    public Editor() {
    }
    
    /**
     * Gets the editor name
     * @return the editor name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the editor name
     * @param name the editor name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Text editor implementation
 */
class TextEditor extends Editor {
    public TextEditor() {
    }
}

/**
 * Image editor implementation
 */
class ImageEditor extends Editor {
    public ImageEditor() {
    }
}

/**
 * Video editor implementation
 */
class VideoEditor extends Editor {
    public VideoEditor() {
    }
}

/**
 * File system that manages documents and editors
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;
    
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
    }
    
    /**
     * Gets the documents list
     * @return the documents list
     */
    public List<Document> getDocuments() {
        return documents;
    }
    
    /**
     * Sets the documents list
     * @param documents the documents list to set
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
    
    /**
     * Gets the editors list
     * @return the editors list
     */
    public List<Editor> getEditors() {
        return editors;
    }
    
    /**
     * Sets the editors list
     * @param editors the editors list to set
     */
    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }
    
    /**
     * Adds a document to the file system
     * @param doc the document to add
     */
    public void addDocument(Document doc) {
        documents.add(doc);
    }
    
    /**
     * Removes a document from the file system
     * @param doc the document to remove
     */
    public void removeDocument(Document doc) {
        documents.remove(doc);
    }
    
    /**
     * Lists all documents in the file system
     * @return list of all documents
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }
    
    /**
     * Calculates the total size of all documents in the file system
     * @return the sum of all document sizes, returns 0 if there are no documents
     */
    public int calculateTotalDocumentSize() {
        return documents.stream()
                       .mapToInt(Document::getSize)
                       .sum();
    }
    
    /**
     * Computes the average size of all documents for each editor type
     * @return a map where keys are editor class names and values are average sizes
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Double> averageSizes = documents.stream()
            .filter(doc -> doc.getEditor() != null)
            .collect(Collectors.groupingBy(
                doc -> doc.getEditor().getClass().getSimpleName(),
                Collectors.averagingInt(Document::getSize)
            ));
        
        // Convert Double to Float for the return type
        Map<String, Float> result = new HashMap<>();
        averageSizes.forEach((key, value) -> result.put(key, value.floatValue()));
        return result;
    }
    
    /**
     * Counts the number of documents created after a specified date
     * @param date the date to compare against
     * @return the number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) documents.stream()
                             .filter(doc -> doc.getCreateDate().after(date))
                             .count();
    }
    
    /**
     * Counts the number of documents for each editor type
     * @return a map where keys are editor class names and values are document counts
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        return documents.stream()
            .filter(doc -> doc.getEditor() != null)
            .collect(Collectors.groupingBy(
                doc -> doc.getEditor().getClass().getSimpleName(),
                Collectors.summingInt(doc -> 1)
            ));
    }
    
    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor
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
}