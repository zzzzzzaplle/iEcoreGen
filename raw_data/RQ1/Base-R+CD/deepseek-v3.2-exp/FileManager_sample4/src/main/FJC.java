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
     * Default constructor initializes empty lists for documents and editors.
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
     * @return map with editor names as keys and average sizes as values
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, List<Integer>> sizesByEditor = documents.stream()
                .filter(doc -> doc.getEditor() != null)
                .collect(Collectors.groupingBy(
                    doc -> doc.getEditor().getName(),
                    Collectors.mapping(Document::getSize, Collectors.toList())
                ));
        
        Map<String, Float> averages = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : sizesByEditor.entrySet()) {
            List<Integer> sizes = entry.getValue();
            float average = (float) sizes.stream().mapToInt(Integer::intValue).average().orElse(0.0);
            averages.put(entry.getKey(), average);
        }
        
        return averages;
    }
    
    /**
     * Counts documents created after a specified date.
     * @param date the cutoff date
     * @return number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        if (date == null) return 0;
        
        return (int) documents.stream()
                .filter(doc -> doc.getCreateDate().after(date))
                .count();
    }
    
    /**
     * Counts the number of documents for each editor type.
     * @return map with editor names as keys and document counts as values
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
     * Retrieves unique author names for documents edited with a specific editor.
     * @param editor the editor to filter by
     * @return list of author names
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        if (editor == null) return new ArrayList<>();
        
        return documents.stream()
                .filter(doc -> editor.equals(doc.getEditor()))
                .map(Document::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }
    
    // Getters and setters
    public List<Document> getDocuments() { return new ArrayList<>(documents); }
    public void setDocuments(List<Document> documents) { 
        this.documents = new ArrayList<>(documents != null ? documents : new ArrayList<>());
    }
    
    public List<Editor> getEditors() { return new ArrayList<>(editors); }
    public void setEditors(List<Editor> editors) { 
        this.editors = new ArrayList<>(editors != null ? editors : new ArrayList<>());
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
     * Default constructor initializes with default values.
     */
    public Document() {
        this.name = "";
        this.createDate = new Date();
        this.author = "";
        this.size = 0;
        this.editor = null;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name != null ? name : ""; }
    
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { 
        this.createDate = createDate != null ? createDate : new Date(); 
    }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author != null ? author : ""; }
    
    public int getSize() { return size; }
    public void setSize(int size) { this.size = Math.max(size, 0); }
    
    public Editor getEditor() { return editor; }
    public void setEditor(Editor editor) { this.editor = editor; }
}

/**
 * Abstract base class for document editors.
 */
abstract class Editor {
    private String name;
    
    /**
     * Default constructor initializes with empty name.
     */
    public Editor() {
        this.name = "";
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name != null ? name : ""; }
}

/**
 * Text editor implementation.
 */
class TextEditor extends Editor {
    /**
     * Default constructor calls parent constructor.
     */
    public TextEditor() {
        super();
    }
}

/**
 * Image editor implementation.
 */
class ImageEditor extends Editor {
    /**
     * Default constructor calls parent constructor.
     */
    public ImageEditor() {
        super();
    }
}

/**
 * Video editor implementation.
 */
class VideoEditor extends Editor {
    /**
     * Default constructor calls parent constructor.
     */
    public VideoEditor() {
        super();
    }
}