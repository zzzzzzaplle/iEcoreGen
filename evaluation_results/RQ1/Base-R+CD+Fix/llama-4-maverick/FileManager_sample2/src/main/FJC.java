import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Represents a file system that manages documents and editors.
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Default constructor to initialize the file system.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
    }

    /**
     * Adds a document to the file system.
     * 
     * @param doc The document to be added.
     */
    public void addDocument(Document doc) {
        this.documents.add(doc);
    }

    /**
     * Removes a document from the file system.
     * 
     * @param doc The document to be removed.
     */
    public void removeDocument(Document doc) {
        this.documents.remove(doc);
    }

    /**
     * Lists all documents in the file system.
     * 
     * @return A list of documents.
     */
    public List<Document> listDocuments() {
        return this.documents;
    }

    /**
     * Adds an editor to the file system.
     * 
     * @param editor The editor to be added.
     */
    public void addEditor(Editor editor) {
        this.editors.add(editor);
    }

    /**
     * Calculates the total size of all documents in the file system.
     * 
     * @return The total size of all documents. Returns 0 if there are no documents.
     */
    public int calculateTotalDocumentSize() {
        return this.documents.stream().mapToInt(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     * 
     * @return A map where the key is the editor's name and the value is the average size of documents edited by that editor.
     */
    public Map<String, Float> findAverageSizePerEditor() {
        Map<String, List<Document>> docsPerEditor = this.documents.stream()
                .collect(Collectors.groupingBy(doc -> doc.getEditor().getName()));
        Map<String, Float> avgSizePerEditor = new HashMap<>();
        docsPerEditor.forEach((editorName, docs) -> {
            double sum = docs.stream().mapToInt(Document::getSize).sum();
            avgSizePerEditor.put(editorName, (float) sum / docs.size());
        });
        return avgSizePerEditor;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     * 
     * @param date The date to compare against.
     * @return The number of documents created after the specified date.
     */
    public int countDocumentsAfterDate(Date date) {
        return (int) this.documents.stream().filter(doc -> doc.getCreateDate().after(date)).count();
    }

    /**
     * Counts the number of documents for each editor type in the file system.
     * 
     * @return A map where the key is the editor's name and the value is the count of documents edited by that editor.
     */
    public Map<String, Integer> countDocumentsPerEditorType() {
        return this.documents.stream()
                .collect(Collectors.groupingBy(doc -> doc.getEditor().getName(), Collectors.summingInt(e -> 1)));
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor in the file system.
     * 
     * @param editor The editor to filter by.
     * @return A list of author names.
     */
    public List<String> getAuthorsByEditor(Editor editor) {
        return this.documents.stream()
                .filter(doc -> doc.getEditor().equals(editor))
                .map(Document::getAuthor)
                .collect(Collectors.toList());
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
}

/**
 * Represents a document with its properties.
 */
class Document {
    private String name;
    private Date createDate;
    private String author;
    private int size;
    private Editor editor;

    /**
     * Default constructor to initialize a document.
     */
    public Document() {
    }

    /**
     * Parameterized constructor to initialize a document.
     * 
     * @param name       The name of the document.
     * @param createDate The creation date of the document.
     * @param author     The author of the document.
     * @param size       The size of the document.
     * @param editor     The editor used for the document.
     */
    public Document(String name, Date createDate, String author, int size, Editor editor) {
        this.name = name;
        this.createDate = createDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
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
 * Abstract class representing an editor.
 */
abstract class Editor {
    protected String name;

    /**
     * Default constructor to initialize an editor.
     */
    public Editor() {
    }

    /**
     * Parameterized constructor to initialize an editor.
     * 
     * @param name The name of the editor.
     */
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

/**
 * Represents a text editor.
 */
class TextEditor extends Editor {
    /**
     * Default constructor to initialize a text editor.
     */
    public TextEditor() {
        super("Text Editor");
    }

    /**
     * Parameterized constructor to initialize a text editor.
     * 
     * @param name The name of the text editor.
     */
    public TextEditor(String name) {
        super(name);
    }
}

/**
 * Represents an image editor.
 */
class ImageEditor extends Editor {
    /**
     * Default constructor to initialize an image editor.
     */
    public ImageEditor() {
        super("Image Editor");
    }

    /**
     * Parameterized constructor to initialize an image editor.
     * 
     * @param name The name of the image editor.
     */
    public ImageEditor(String name) {
        super(name);
    }
}

/**
 * Represents a video editor.
 */
class VideoEditor extends Editor {
    /**
     * Default constructor to initialize a video editor.
     */
    public VideoEditor() {
        super("Video Editor");
    }

    /**
     * Parameterized constructor to initialize a video editor.
     * 
     * @param name The name of the video editor.
     */
    public VideoEditor(String name) {
        super(name);
    }
}