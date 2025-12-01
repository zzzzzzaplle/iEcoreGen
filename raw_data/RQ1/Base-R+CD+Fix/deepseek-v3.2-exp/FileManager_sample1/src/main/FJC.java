import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a file system that manages documents and editors.
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
     * @return the sum of all document sizes, returns 0 if there are no documents
     */
    public int calculateTotalDocumentSize() {
        return documents.stream()
                .mapToInt(Document::getSize)
                .sum();
    }

    /**
     * Computes the average size of all documents for each editor type.
     * @return a map where keys are editor names and values are average document sizes
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, List<Integer>> sizesByEditor = documents.stream()
                .filter(doc -> doc.getEditor() != null)
                .collect(Collectors.groupingBy(
                    doc -> doc.getEditor().getName(),
                    Collectors.mapping(Document::getSize, Collectors.toList())
                ));

        Map<String, Float> averageSizes = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : sizesByEditor.entrySet()) {
            List<Integer> sizes = entry.getValue();
            float average = (float) sizes.stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0.0);
            averageSizes.put(entry.getKey(), average);
        }
        
        // Ensure all editor types are included even if they have no documents
        editors.forEach(editor -> {
            averageSizes.putIfAbsent(editor.getName(), 0.0f);
        });
        
        return averageSizes;
    }

    /**
     * Counts the number of documents created after a specified date.
     * @param date the cutoff date
     * @return the count of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) documents.stream()
                .filter(doc -> doc.getCreateDate().after(date))
                .count();
    }

    /**
     * Counts the number of documents for each editor type.
     * @return a map where keys are editor names and values are document counts
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> counts = documents.stream()
                .filter(doc -> doc.getEditor() != null)
                .collect(Collectors.groupingBy(
                    doc -> doc.getEditor().getName(),
                    Collectors.summingInt(doc -> 1)
                ));
        
        // Ensure all editor types are included even if they have no documents
        editors.forEach(editor -> {
            counts.putIfAbsent(editor.getName(), 0);
        });
        
        return counts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     * @param editor the editor to filter by
     * @return a list of author names
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        return documents.stream()
                .filter(doc -> editor.equals(doc.getEditor()))
                .map(Document::getAuthor)
                .distinct()
                .collect(Collectors.toList());
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
 * Abstract base class for all editors.
 */
abstract class Editor {
    private String name;

    /**
     * Default constructor.
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
 * Represents a text editor.
 */
class TextEditor extends Editor {
    /**
     * Default constructor sets the editor name to "Text Editor".
     */
    public TextEditor() {
        setName("Text Editor");
    }
}

/**
 * Represents an image editor.
 */
class ImageEditor extends Editor {
    /**
     * Default constructor sets the editor name to "Image Editor".
     */
    public ImageEditor() {
        setName("Image Editor");
    }
}

/**
 * Represents a video editor.
 */
class VideoEditor extends Editor {
    /**
     * Default constructor sets the editor name to "Video Editor".
     */
    public VideoEditor() {
        setName("Video Editor");
    }
}