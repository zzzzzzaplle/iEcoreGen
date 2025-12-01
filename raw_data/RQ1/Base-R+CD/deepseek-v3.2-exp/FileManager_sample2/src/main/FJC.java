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
     * @return sum of all document sizes, 0 if no documents exist
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
     * Counts documents for each editor type.
     * @return map with editor type as key and document count as value
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> counts = new HashMap<>();
        
        for (Document doc : documents) {
            if (doc.getEditor() != null) {
                String editorType = doc.getEditor().getClass().getSimpleName();
                counts.put(editorType, counts.getOrDefault(editorType, 0) + 1);
            }
        }
        
        // Ensure all editor types are represented
        String[] editorTypes = {"TextEditor", "ImageEditor", "VideoEditor"};
        for (String type : editorTypes) {
            counts.putIfAbsent(type, 0);
        }
        
        return counts;
    }

    /**
     * Retrieves author names for documents edited with a specific editor.
     * @param editor the editor to filter by
     * @return list of author names (unique)
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
     * Default constructor.
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
 * Abstract base class for document editors.
 */
abstract class Editor {
    private String name;

    /**
     * Default constructor.
     */
    public Editor() {
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
 * Text editor implementation.
 */
class TextEditor extends Editor {
    /**
     * Default constructor.
     */
    public TextEditor() {
    }
}

/**
 * Image editor implementation.
 */
class ImageEditor extends Editor {
    /**
     * Default constructor.
     */
    public ImageEditor() {
    }
}

/**
 * Video editor implementation.
 */
class VideoEditor extends Editor {
    /**
     * Default constructor.
     */
    public VideoEditor() {
    }
}