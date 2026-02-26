import java.util.*;
import java.util.stream.Collectors;

class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

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

    public void addDocument(Document doc) {
        documents.add(doc);
    }

    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * Sums up the size of all documents. Returns 0 if there are no documents.
     * 
     * @return the total size of all documents, or 0 if no documents exist
     */
    public int calculateTotalDocumentSize() {
        return documents.stream()
                .mapToInt(Document::getSize)
                .sum();
    }

    /**
     * Computes the average size of documents for each editor type in the file system.
     * Returns a map where keys are editor type names and values are average sizes.
     * If no documents exist for an editor type, the average will be 0.0.
     * 
     * @return a map containing editor type names and their corresponding average document sizes
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, List<Document>> docsByEditor = documents.stream()
                .filter(doc -> doc.getEditor() != null)
                .collect(Collectors.groupingBy(doc -> doc.getEditor().getName()));

        Map<String, Float> averageSizes = new HashMap<>();
        for (Map.Entry<String, List<Document>> entry : docsByEditor.entrySet()) {
            String editorName = entry.getKey();
            List<Document> editorDocs = entry.getValue();
            double average = editorDocs.stream()
                    .mapToInt(Document::getSize)
                    .average()
                    .orElse(0.0);
            averageSizes.put(editorName, (float) average);
        }
        
        // Ensure all editor types are included even if they have no documents
        for (Editor editor : editors) {
            averageSizes.putIfAbsent(editor.getName(), 0.0f);
        }
        
        return averageSizes;
    }

    /**
     * Counts the number of documents created after the specified date.
     * 
     * @param date the cutoff date (documents created after this date will be counted)
     * @return the number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) documents.stream()
                .filter(doc -> doc.getCreateDate().after(date))
                .count();
    }

    /**
     * Counts the number of documents for each editor type in the file system.
     * Returns a map where keys are editor type names and values are document counts.
     * 
     * @return a map containing editor type names and their corresponding document counts
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> countMap = documents.stream()
                .filter(doc -> doc.getEditor() != null)
                .collect(Collectors.groupingBy(
                    doc -> doc.getEditor().getName(),
                    Collectors.summingInt(doc -> 1)
                ));
        
        // Ensure all editor types are included even if they have no documents
        for (Editor editor : editors) {
            countMap.putIfAbsent(editor.getName(), 0);
        }
        
        return countMap;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with the specified editor.
     * 
     * @param editor the editor to filter documents by
     * @return a list of unique author names whose documents use the specified editor
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        return documents.stream()
                .filter(doc -> doc.getEditor() != null && doc.getEditor().equals(editor))
                .map(Document::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }
}

class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;

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

abstract class Editor {
    private String name;

    public Editor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class TextEditor extends Editor {
    public TextEditor() {
    }
}

class ImageEditor extends Editor {
    public ImageEditor() {
    }
}

class VideoEditor extends Editor {
    public VideoEditor() {
    }
}