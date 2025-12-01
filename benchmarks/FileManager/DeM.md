// ==version1==
```
class FileSystem {
    + addDocument(doc : Document) : void
    + removeDocument(doc : Document) : void
    + listDocuments() : List<Document>
    + calculateTotalDocumentSize() : int 
    + findAverageSizePerEditor() : Map<String, Float>
    + countDocumentsAfterDate(date : Date) : int
    + countDocumentsPerEditorType() : Map<String, Integer>
    + getAuthorsByEditor(editor : Editor) : List<String>
}

class Document {
    - name : String
    - createDate : Date
    - author : String
    - size : integer
}

abstract class Editor {
    - name : String
}

class TextEditor extends Editor
class ImageEditor extends Editor
class VideoEditor extends Editor

FileSystem *-- "*" Document : documents
FileSystem *-- "*" Editor : editors
Document "*" -- "0..1" Editor : editor
```
// ==end==
