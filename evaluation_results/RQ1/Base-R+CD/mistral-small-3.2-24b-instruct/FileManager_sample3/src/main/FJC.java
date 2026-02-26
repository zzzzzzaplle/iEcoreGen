import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Editor {
    private String name;

    public Editor(String name) {
        this.name = name;
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
        super("Text Editor");
    }
}

class ImageEditor extends Editor {
    public ImageEditor() {
        super("Image Editor");
    }
}

class VideoEditor extends Editor {
    public VideoEditor() {
        super("Video Editor");
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

class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
        this.editors.add(new TextEditor());
        this.editors.add(new ImageEditor());
        this.editors.add(new VideoEditor());
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
     *
     * @return the sum of all document sizes, or 0 if there are no documents
     */
    public int calculateTotalDocumentSize() {
        if (documents.isEmpty()) {
            return 0;
        }
        int totalSize = 0;
        for (Document doc : documents) {
            totalSize += doc.getSize();
        }
        return totalSize;
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     *
     * @return a map where the key is the editor name and the value is the average size
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, Float> averageSizes = new HashMap<>();
        Map<String, Integer> totalSizes = new HashMap<>();
        Map<String, Integer> documentCounts = new HashMap<>();

        for (Editor editor : editors) {
            totalSizes.put(editor.getName(), 0);
            documentCounts.put(editor.getName(), 0);
        }

        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            totalSizes.put(editorName, totalSizes.get(editorName) + doc.getSize());
            documentCounts.put(editorName, documentCounts.get(editorName) + 1);
        }

        for (String editorName : totalSizes.keySet()) {
            if (documentCounts.get(editorName) > 0) {
                averageSizes.put(editorName, (float) totalSizes.get(editorName) / documentCounts.get(editorName));
            } else {
                averageSizes.put(editorName, 0f);
            }
        }

        return averageSizes;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     *
     * @param date the date to compare against
     * @return the number of documents created after the specified date
     */
    public int countDocumentsAfterDate(Date date) {
        int count = 0;
        for (Document doc : documents) {
            if (doc.getCreateDate().after(date)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of documents for each editor in the file system.
     *
     * @return a map where the key is the editor name and the value is the count of documents
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        Map<String, Integer> documentCounts = new HashMap<>();
        for (Editor editor : editors) {
            documentCounts.put(editor.getName(), 0);
        }

        for (Document doc : documents) {
            String editorName = doc.getEditor().getName();
            documentCounts.put(editorName, documentCounts.get(editorName) + 1);
        }

        return documentCounts;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor in the file system.
     *
     * @param editor the editor to filter by
     * @return a list of author names
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        List<String> authors = new ArrayList<>();
        for (Document doc : documents) {
            if (doc.getEditor().getName().equals(editor.getName())) {
                authors.add(doc.getAuthor());
            }
        }
        return authors;
    }
}