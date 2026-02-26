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
    
    /**
     * Default constructor
     */
    public Document() {}
    
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
     * Gets the document creation date
     * @return the creation date
     */
    public Date getCreateDate() {
        return createDate;
    }
    
    /**
     * Sets the document creation date
     * @param createDate the creation date to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * Gets the document author
     * @return the author
     */
    public String getAuthor() {
        return author;
    }
    
    /**
     * Sets the document author
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    
    /**
     * Gets the document size
     * @return the size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Sets the document size
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    /**
     * Gets the editor assigned to this document
     * @return the editor
     */
    public Editor getEditor() {
        return editor;
    }
    
    /**
     * Sets the editor assigned to this document
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
    
    /**
     * Default constructor
     */
    public Editor() {}
    
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
    /**
     * Default constructor
     */
    public TextEditor() {}
}

/**
 * Image editor implementation
 */
class ImageEditor extends Editor {
    /**
     * Default constructor
     */
    public ImageEditor() {}
}

/**
 * Video editor implementation
 */
class VideoEditor extends Editor {
    /**
     * Default constructor
     */
    public VideoEditor() {}
}

/**
 * Represents a file system that manages documents and editors
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;
    
    /**
     * Default constructor
     */
    public FileSystem() {
        documents = new ArrayList<>();
        editors = new ArrayList<>();
    }
    
    /**
     * Gets the list of documents
     * @return the list of documents
     */
    public List<Document> getDocuments() {
        return documents;
    }
    
    /**
     * Sets the list of documents
     * @param documents the list of documents to set
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
    
    /**
     * Gets the list of editors
     * @return the list of editors
     */
    public List<Editor> getEditors() {
        return editors;
    }
    
    /**
     * Sets the list of editors
     * @param editors the list of editors to set
     */
    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }
    
    /**
     * Adds a document to the file system
     * @param doc the document to add
     */
    public void addDocument(Document doc) {
        if (doc != null) {
            documents.add(doc);
        }
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
     * @return a list of all documents
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
        Map<String, List<Integer>> sizesByEditor = new HashMap<>();
        
        for (Document doc : documents) {
            if (doc.getEditor() != null) {
                String editorType = doc.getEditor().getClass().getSimpleName();
                sizesByEditor.computeIfAbsent(editorType, k -> new ArrayList<>())
                           .add(doc.getSize());
            }
        }
        
        Map<String, Float> averages = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : sizesByEditor.entrySet()) {
            List<Integer> sizes = entry.getValue();
            if (!sizes.isEmpty()) {
                float average = (float) sizes.stream().mapToInt(Integer::intValue).sum() / sizes.size();
                averages.put(entry.getKey(), average);
            }
        }
        
        return averages;
    }
    
    /**
     * Counts the number of documents created after a specified date
     * @param date the cutoff date (documents created after this date will be counted)
     * @return the number of documents created after the specified date
     * @throws IllegalArgumentException if the date parameter is null
     */
    public int countDocumentsAfterDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        
        return (int) documents.stream()
                .filter(doc -> doc.getCreateDate() != null && doc.getCreateDate().after(date))
                .count();
    }
    
    /**
     * Counts the number of documents for each editor type
     * @return a map where keys are editor class names and values are document counts
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> counts = new HashMap<>();
        
        for (Document doc : documents) {
            if (doc.getEditor() != null) {
                String editorType = doc.getEditor().getClass().getSimpleName();
                counts.put(editorType, counts.getOrDefault(editorType, 0) + 1);
            }
        }
        
        // Ensure all editor types are represented even if count is 0
        counts.putIfAbsent("TextEditor", 0);
        counts.putIfAbsent("ImageEditor", 0);
        counts.putIfAbsent("VideoEditor", 0);
        
        return counts;
    }
    
    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor
     * @param editor the editor to filter by
     * @return a list of unique author names whose documents use the specified editor
     * @throws IllegalArgumentException if the editor parameter is null
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("Editor cannot be null");
        }
        
        return documents.stream()
                .filter(doc -> doc.getEditor() != null && doc.getEditor().equals(editor))
                .map(Document::getAuthor)
                .filter(author -> author != null && !author.trim().isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
}