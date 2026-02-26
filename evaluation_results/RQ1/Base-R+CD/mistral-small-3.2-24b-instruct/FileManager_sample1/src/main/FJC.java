import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FileSystem {
    private List<Document> documents = new ArrayList<>();
    private List<Editor> editors = new ArrayList<>();

    /**
     * Adds a document to the file system.
     * @param doc The document to be added.
     */
    public void addDocument(Document doc) {
        documents.add(doc);
    }

    /**
     * Removes a document from the file system.
     * @param doc The document to be removed.
     */
    public void removeDocument(Document doc) {
        documents.remove(doc);
    }

    /**
     * Lists all documents in the file system.
     * @return A list of all documents.
     */
    public List<Document> listDocuments() {
        return new ArrayList<>(documents);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * @return The sum of all document sizes. Returns 0 if there are no documents.
     */
    public int calculateTotalDocumentSize() {
        if (documents.isEmpty()) {
            return 0;
        }
        return documents.stream().mapToInt(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     * @return A map with editor names as keys and average document sizes as values.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Float> averageSizes = new HashMap<>();
        Map<String, Integer> totalSizes = new HashMap<>();
        Map<String, Integer> documentCounts = new HashMap<>();

        for (Document doc : documents) {
            Editor editor = doc.getEditor();
            if (editor != null) {
                String editorName = editor.getName();
                totalSizes.put(editorName, totalSizes.getOrDefault(editorName, 0) + doc.getSize());
                documentCounts.put(editorName, documentCounts.getOrDefault(editorName, 0) + 1);
            }
        }

        for (String editorName : totalSizes.keySet()) {
            averageSizes.put(editorName, (float) totalSizes.get(editorName) / documentCounts.get(editorName));
        }

        return averageSizes;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * @param date The date to compare against.
     * @return The number of documents created after the specified date.
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) documents.stream().filter(doc -> doc.getCreateDate().after(date)).count();
    }

    /**
     * Counts the number of documents for each editor in the file system.
     * @return A map with editor names as keys and document counts as values.
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> documentCounts = new HashMap<>();

        for (Document doc : documents) {
            Editor editor = doc.getEditor();
            if (editor != null) {
                String editorName = editor.getName();
                documentCounts.put(editorName, documentCounts.getOrDefault(editorName, 0) + 1);
            }
        }

        return documentCounts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor.
     * @param editor The editor to filter by.
     * @return A list of author names.
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        List<String> authors = new ArrayList<>();

        for (Document doc : documents) {
            if (doc.getEditor() != null && doc.getEditor().getName().equals(editor.getName())) {
                authors.add(doc.getAuthor());
            }
        }

        return authors;
    }

    // Getters and Setters
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

class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;

    public Document() {
    }

    // Getters and Setters
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

    // Getters and Setters
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