import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an editor for documents.
 */
class Editor {
    private String name;

    /**
     * Constructs a new Editor with the specified name.
     *
     * @param name the name of the editor
     */
    public Editor(String name) {
        this.name = name;
    }

    /**
     * Default constructor for Editor.
     */
    public Editor() {
    }

    /**
     * Gets the name of the editor.
     *
     * @return the name of the editor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the editor.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a document in the file system.
 */
class Document {
    private String name;
    private Date creationDate;
    private String author;
    private long size;
    private Editor editor;

    /**
     * Constructs a new Document with the specified parameters.
     *
     * @param name the name of the document
     * @param creationDate the creation date of the document
     * @param author the author of the document
     * @param size the size of the document
     * @param editor the editor used for the document
     */
    public Document(String name, Date creationDate, String author, long size, Editor editor) {
        this.name = name;
        this.creationDate = creationDate;
        this.author = author;
        this.size = size;
        this.editor = editor;
    }

    /**
     * Default constructor for Document.
     */
    public Document() {
    }

    /**
     * Gets the name of the document.
     *
     * @return the name of the document
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the document.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the creation date of the document.
     *
     * @return the creation date of the document
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the document.
     *
     * @param creationDate the creation date to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the author of the document.
     *
     * @return the author of the document
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the document.
     *
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the size of the document.
     *
     * @return the size of the document
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size of the document.
     *
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Gets the editor used for the document.
     *
     * @return the editor used for the document
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor used for the document.
     *
     * @param editor the editor to set
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }
}

/**
 * Represents a file system that manages documents and editors.
 */
class FileSystem {
    private List<Document> documents;
    private List<Editor> editors;

    /**
     * Constructs a new FileSystem with empty lists for documents and editors.
     */
    public FileSystem() {
        this.documents = new ArrayList<>();
        this.editors = new ArrayList<>();
    }

    /**
     * Adds a document to the file system.
     *
     * @param document the document to add
     */
    public void addDocument(Document document) {
        documents.add(document);
    }

    /**
     * Removes a document from the file system.
     *
     * @param document the document to remove
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Lists all documents in the file system.
     *
     * @return a list of all documents
     */
    public List<Document> listDocuments() {
        return documents;
    }

    /**
     * Adds an editor to the file system.
     *
     * @param editor the editor to add
     */
    public void addEditor(Editor editor) {
        editors.add(editor);
    }

    /**
     * Removes an editor from the file system.
     *
     * @param editor the editor to remove
     */
    public void removeEditor(Editor editor) {
        editors.remove(editor);
    }

    /**
     * Lists all editors in the file system.
     *
     * @return a list of all editors
     */
    public List<Editor> listEditors() {
        return editors;
    }

    /**
     * Calculates the total size of all documents in the file system.
     *
     * @return the total size of all documents, or 0 if there are no documents
     */
    public long calculateTotalSize() {
        if (documents.isEmpty()) {
            return 0;
        }
        return documents.stream().mapToLong(Document::getSize).sum();
    }

    /**
     * Computes the average size of all documents for each editor in the file system.
     *
     * @return a map of editor names to their average document sizes
     */
    public Map<String, Double> computeAverageSizePerEditor() {
        Map<String, List<Long>> editorToSizes = new HashMap<>();
        documents.forEach(document -> {
            String editorName = document.getEditor().getName();
            editorToSizes.computeIfAbsent(editorName, k -> new ArrayList<>()).add(document.getSize());
        });

        Map<String, Double> editorToAverageSize = new HashMap<>();
        editorToSizes.forEach((editorName, sizes) -> {
            double averageSize = sizes.stream().mapToLong(Long::longValue).average().orElse(0);
            editorToAverageSize.put(editorName, averageSize);
        });

        return editorToAverageSize;
    }

    /**
     * Counts the number of documents in the file system that were created after a specified date.
     *
     * @param date the date to compare against
     * @return the number of documents created after the specified date
     */
    public long countDocumentsAfterDate(Date date) {
        return documents.stream().filter(document -> document.getCreationDate().after(date)).count();
    }

    /**
     * Counts the number of documents for each editor in the file system.
     *
     * @return a map of editor names to their document counts
     */
    public Map<String, Long> countDocumentsPerEditor() {
        Map<String, Long> editorToCount = new HashMap<>();
        documents.forEach(document -> {
            String editorName = document.getEditor().getName();
            editorToCount.merge(editorName, 1L, Long::sum);
        });
        return editorToCount;
    }

    /**
     * Retrieves the names of all authors whose documents are edited with a specified editor in the file system.
     *
     * @param editorName the name of the editor to filter by
     * @return a list of author names
     */
    public List<String> getAuthorsByEditor(String editorName) {
        return documents.stream()
                .filter(document -> document.getEditor().getName().equals(editorName))
                .map(Document::getAuthor)
                .distinct()
                .toList();
    }
}

 class Main {
    public static void main(String[] args) {
        // Example usage
        FileSystem fileSystem = new FileSystem();

        // Create editors
        Editor textEditor = new Editor("Text Editor");
        Editor imageEditor = new Editor("Image Editor");
        Editor videoEditor = new Editor("Video Editor");

        // Add editors to the file system
        fileSystem.addEditor(textEditor);
        fileSystem.addEditor(imageEditor);
        fileSystem.addEditor(videoEditor);

        // Create documents
        Document doc1 = new Document("Document 1", new Date(122, 0, 1), "Author 1", 1024, textEditor);
        Document doc2 = new Document("Document 2", new Date(122, 1, 1), "Author 2", 2048, imageEditor);
        Document doc3 = new Document("Document 3", new Date(122, 2, 1), "Author 3", 3072, videoEditor);

        // Add documents to the file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);

        // Example method calls
        System.out.println("Total Size: " + fileSystem.calculateTotalSize());
        System.out.println("Average Size per Editor: " + fileSystem.computeAverageSizePerEditor());
        System.out.println("Documents after date: " + fileSystem.countDocumentsAfterDate(new Date(122, 0, 1)));
        System.out.println("Documents per Editor: " + fileSystem.countDocumentsPerEditor());
        System.out.println("Authors by Editor (Text Editor): " + fileSystem.getAuthorsByEditor("Text Editor"));
    }
}