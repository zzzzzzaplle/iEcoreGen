import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a file system that manages documents and editors.
 * The file system allows adding/removing documents and editors,
 * and provides various statistical operations on the documents.
 */
 class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Unparameterized constructor for FileSystem.
     * Initializes empty lists for documents and editors.
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
        documents.add(doc);
    }

    /**
     * Removes a document from the file system.
     * @param doc the document to remove
     */
    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    /**
     * Lists all documents in the file system.
     * @return a list of all documents
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * @return the sum of all document sizes, or 0 if there are no documents
     */
    public int calculateTotalDocumentSize() {
        return documents.stream()
                .mapToInt(Document::getSize)
                .sum();
    }

    /**
     * Computes the average size of documents for each editor type.
     * @return a map where keys are editor names and values are average sizes
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, List<Integer>> sizesByEditor = new HashMap<>();
        
        for (Document doc : documents) {
            if (doc.getEditor() != null) {
                String editorName = doc.getEditor().getName();
                sizesByEditor.computeIfAbsent(editorName, k -> new ArrayList<>())
                            .add(doc.getSize());
            }
        }
        
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
     * @return the number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) documents.stream()
                .filter(doc -> doc.getCreateDate().after(date))
                .count();
    }

    /**
     * Counts documents by editor type.
     * @return a map where keys are editor names and values are document counts
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> counts = new HashMap<>();
        
        for (Document doc : documents) {
            if (doc.getEditor() != null) {
                String editorName = doc.getEditor().getName();
                counts.put(editorName, counts.getOrDefault(editorName, 0) + 1);
            }
        }
        
        return counts;
    }

    /**
     * Retrieves author names for documents edited with a specific editor.
     * @param editor the editor to filter by
     * @return a list of unique author names
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        return documents.stream()
                .filter(doc -> doc.getEditor() != null && doc.getEditor().equals(editor))
                .map(Document::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }

    // Getters and setters
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
     * Unparameterized constructor for Document.
     */
    public Document() {
    }

    // Getters and setters
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
     * Unparameterized constructor for Editor.
     */
    public Editor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Text editor implementation.
 */
class TextEditor extends Editor {
    /**
     * Unparameterized constructor for TextEditor.
     */
    public TextEditor() {
    }
}

/**
 * Image editor implementation.
 */
class ImageEditor extends Editor {
    /**
     * Unparameterized constructor for ImageEditor.
     */
    public ImageEditor() {
    }
}

/**
 * Video editor implementation.
 */
class VideoEditor extends Editor {
    /**
     * Unparameterized constructor for VideoEditor.
     */
    public VideoEditor() {
    }
}