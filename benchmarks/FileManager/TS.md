### CR 1: Calculate the total size of all documents in the file system. Output the sum of all document sizes. Return 0 if there is no document.


```
Computational requirement: Calculate the total size of all documents in the file system. Output the sum of all document sizes. Return 0 if there is no document.

+ Test Case 1: "Calculate Total Size of Multiple Documents"  
    Input: Calculate the total size of documents in the FileSystem.
    SetUp:  
    1. Create a FileSystem instance.
    2. Add a Document named "Document1" with size: 100 KB.
    3. Add a Document named "Document2" with size: 200 KB.
    4. Add a Document named "Document3" with size: 300 KB.
    Expected Output: Total document size = 100 + 200 + 300 = 600 KB
---
+ Test Case 2: "Calculate Total Size after Document Removal"  
    Input: Calculate the total size of documents after removing one.
    SetUp:  
    1. Create a FileSystem instance.
    2. Add a Document named "Document1" with size: 150 KB.
    3. Add a Document named "Document2" with size: 250 KB.
    4. Remove Document1 from the FileSystem.
    Expected Output: Total document size = 250 KB
---
+ Test Case 3: "Empty FileSystem Calculation"  
    Input: Calculate the total size of documents in an empty FileSystem.
    SetUp:  
    1. Create a FileSystem instance with no documents added.
    Expected Output: Total document size = 0 KB
---
+ Test Case 4: "Calculate Total Size of Documents with Mixed Sizes"  
    Input: Calculate total size for mixed document sizes.
    SetUp:  
    1. Create a FileSystem instance.
    2. Add a Document named "DocA" with size: 50 KB.
    3. Add a Document named "DocB" with size: 1000 KB.
    4. Add a Document named "DocC" with size: 250 KB.
    Expected Output: Total document size = 50 + 1000 + 250 = 1300 KB
---
+ Test Case 5: "Calculate Total Size after Multiple Removals"  
    Input: Calculate total size after adding and then removing documents.
    SetUp:  
    1. Create a FileSystem instance.
    2. Add Document "Report" with size: 400 KB.
    3. Add Document "Image" with size: 300 KB.
    4. Add Document "Video" with size: 700 KB.
    5. Remove "Image" (300 KB) and "Report" (400 KB).
    Expected Output: Total document size = 700 KB
```


***

### CR 2: Compute the average document size for each editor (TextEditor, ImageEditor, VideoEditor) in the file system.


```
Computational requirement: Compute the average document size for each editor (TextEditor, ImageEditor, VideoEditor) in the file system.

+ Test Case 1: Single Editor with Multiple Documents 
    Input: Calculate average document size for a single editor.
    SetUp:  
    1. Create editors: "Text Editor", "Image Editor", "Video Editor".
    2. Associate "Text Editor" with 3 documents:
       - Document 1: Name: "Report", Size: 150
       - Document 2: Name: "Essay", Size: 200
       - Document 3: Name: "Proposal", Size: 250
    3. Call the method to compute the average size for "Text Editor".
    Expected Output: Average size = (150 + 200 + 250) / 3 = 200 
---
+ Test Case 2: No Documents for an Editor 
    Input: Calculate average document size for an editor with no documents.
    SetUp:  
    1. Create editors: "Text Editor", "Image Editor", "Video Editor".
    2. Associate "Image Editor" with no documents.
    3. Call the method to compute the average size for "Image Editor".
    Expected Output: Average size = 0
---
+ Test Case 3: Editor with Documents of Varying Sizes 
    Input: Calculate average document size for an editor with varying sizes.
    SetUp:  
    1. Create editors: "Text Editor", "Image Editor", "Video Editor".
    2. Associate "Video Editor" with 4 documents:
       - Document 1: Name: "Video1", Size: 500
       - Document 2: Name: "Video2", Size: 1000
       - Document 3: Name: "Video3", Size: 750
       - Document 4: Name: "Video4", Size: 300
    3. Call the method to compute the average size for "Video Editor".
    Expected Output: Average size = (500 + 1000 + 750 + 300) / 4 = 637.5 
---
+ Test Case 4: Multiple Editors with Document Distribution 
    Input: Calculate average document sizes for multiple editors with distributed documents.
    SetUp:  
    1. Create editors: "Text Editor", "Image Editor", "Video Editor".
    2. Associate each editor with documents:
       - "Text Editor": 2 documents: (100 , 200 )
       - "Image Editor": 3 documents: (1024 , 1536 , 512 )
       - "Video Editor": 1 document: (2048 )
    3. Call the method to compute the average size for each editor.
    Expected Output: 
    - "Text Editor" Average size = (100 + 200) / 2 = 150 
    - "Image Editor" Average size = (1024 + 1536 + 512) / 3 = 1024
    - "Video Editor" Average size = 2048 
---
+ Test Case 5: Large Number of Documents for One Editor 
    Input: Calculate average document size for editor with many documents.
    SetUp:  
    1. Create editors: "Text Editor".
    2. Associate "Text Editor" with 100 documents, each 10 in size.
    3. Call the method to compute the average size for "Text Editor".
    Expected Output: Average size = (10 * 100) / 100 = 10
```


***


### CR 3: Count the number of documents in the file system that were created after a specified date.



```
Computational requirement: Count the number of documents in the file system that were created after a specified date.


+ Test Case 1: Count documents created after a specific date with no documents 
    Input: Count the number of documents created after 2023-10-01.
    SetUp: 
    1. Create a FileSystem instance.
    2. The FileSystem contains no documents.
    Expected Output: Total documents created after 2023-10-01 = 0
---
+ Test Case 2: Count documents created after a specific date with one document 
    Input: Count the number of documents created after 2023-10-01.
    SetUp: 
    1. Create a FileSystem instance.
    2. Add a document named "Doc1" created on 2023-10-05, author "Author1", size 10KB.
    Expected Output: Total documents created after 2023-10-01 = 1
---
+ Test Case 3: Count documents created after a specific date with multiple documents 
    Input: Count the number of documents created after 2023-09-15.
    SetUp: 
    1. Create a FileSystem instance.
    2. Add a document named "Doc1" created on 2023-09-10, author "Author1", size 15KB.
    3. Add a document named "Doc2" created on 2023-09-20, author "Author2", size 20KB.
    4. Add a document named "Doc3" created on 2023-10-01, author "Author3", size 5KB.
    5. Add a document named "Doc4" created on 2023-10-10, author "Author4", size 25KB.
    Expected Output: Total documents created after 2023-09-15 = 3
---
+ Test Case 4: Count documents created before the specific date 
    Input: Count the number of documents created after 2023-09-30.
    SetUp: 
    1. Create a FileSystem instance.
    2. Add a document named "Doc1" created on 2023-09-28, author "Author1", size 12KB.
    3. Add a document named "Doc2" created on 2023-08-15, author "Author2", size 30KB.
    Expected Output: Total documents created after 2023-09-30 = 0
---
+ Test Case 5: Count documents with variation in creation dates
    Input: Count the number of documents created after 2023-08-01.
    SetUp: 
    1. Create a FileSystem instance.
    2. Add a document named "Doc1" created on 2023-07-30, author "Author1", size 10KB.
    3. Add a document named "Doc2" created on 2023-08-05, author "Author2", size 20KB.
    4. Add a document named "Doc3" created on 2023-09-15, author "Author3", size 15KB.
    Expected Output: Total documents created after 2023-08-01 = 2
```


***


### CR 4: Count the number of documents for each editor (Text Editor, Image Editor, Video Editor) in the file system.

```
Computational requirement: Count the number of documents for each editor (TextEditor, ImageEditor, VideoEditor) in the file system.

+ Test Case 1: Count Documents with Mixed Editor Types  
    Input: Count the number of documents per editor type in the FileSystem.  
    SetUp:  
    1. Create a FileSystem instance.  
    2. Add a document named "Report.docx" using TextEditor.  
    3. Add a document named "Image.png" using ImageEditor.  
    4. Add a document named "Video.mp4" using VideoEditor.  
    Expected Output:  
    - TextEditor: 1  
    - ImageEditor: 1  
    - VideoEditor: 1  

---
+ Test Case 2: Count Documents with Single Editor Type  
    Input: Count the number of documents per editor type in the FileSystem.  
    SetUp:  
    1. Create a FileSystem instance.  
    2. Add a document named "Essay.docx" using TextEditor.  
    3. Add a document named "Notes.txt" using TextEditor.  
    Expected Output:  
    - TextEditor: 2  
    - ImageEditor: 0  
    - VideoEditor: 0  

---
+ Test Case 3: Count Documents after Removal  
    Input: Count the number of documents per editor type in the FileSystem after removing some documents.  
    SetUp:  
    1. Create a FileSystem instance.  
    2. Add a document named "Image1.png" using ImageEditor.  
    3. Add a document named "Video1.mp4" using VideoEditor.  
    4. Add a document named "Text1.docx" using TextEditor.  
    5. Remove the document "Image1.png".  
    Expected Output:  
    - TextEditor: 1  
    - ImageEditor: 0  
    - VideoEditor: 1  

---
+ Test Case 4: Count Documents with No Editors  
    Input: Count the number of documents per editor type in the FileSystem when no documents are added.  
    SetUp:  
    1. Create a FileSystem instance.  
    Expected Output:  
    - TextEditor: 0  
    - ImageEditor: 0  
    - VideoEditor: 0  

---
+ Test Case 5: Count Documents with All Editors Used  
    Input: Count the number of documents per editor type in the FileSystem.  
    SetUp:  
    1. Create a FileSystem instance.  
    2. Add three documents: "Doc1.txt" with TextEditor, "Pic1.jpg" with ImageEditor, "Clip1.mpg" with VideoEditor.  
    3. Add another document "Doc2.txt" with TextEditor.  
    Expected Output:  
    - TextEditor: 2  
    - ImageEditor: 1  
    - VideoEditor: 1  
```

***


### CR 5: Retrieve the names of all authors whose documents are edited with a specified editor in the file system.

```
Computational requirement: Retrieve the names of all authors whose documents are edited with a specified editor in the file system.

+ Test Case 1: Retrieve Authors for Text Editor Documents
    Input: Get authors for documents using "Text Editor"
    SetUp: 
    1. Create a file system with the following documents:
        - Document 1: Name: "Report.doc", Creation Date: 2023-10-01, Author: "Alice", Size: 100KB, Editor: "Text Editor"
        - Document 2: Name: "Essay.doc", Creation Date: 2023-10-02, Author: "Bob", Size: 150KB, Editor: "Text Editor"
        - Document 3: Name: "Image.png", Creation Date: 2023-10-03, Author: "Charlie", Size: 200KB, Editor: "Image Editor"
    Expected Output: Authors = ["Alice", "Bob"]
---
+ Test Case 2: Retrieve Authors for Image Editor Documents
    Input: Get authors for documents using "Image Editor"
    SetUp: 
    1. Create a file system with the following documents:
        - Document 1: Name: "Photo.jpg", Creation Date: 2023-09-15, Author: "Dave", Size: 250KB, Editor: "Image Editor"
        - Document 2: Name: "Diagram.svg", Creation Date: 2023-09-20, Author: "Eve", Size: 300KB, Editor: "Image Editor"
        - Document 3: Name: "Video.mp4", Creation Date: 2023-09-25, Author: "Frank", Size: 500MB, Editor: "Video Editor"
    Expected Output: Authors = ["Dave", "Eve"]
---
+ Test Case 3: No Authors for a Specific Editor
    Input: Get authors for documents using "Video Editor"
    SetUp: 
    1. Create a file system with the following documents:
        - Document 1: Name: "Document.txt", Creation Date: 2023-10-05, Author: "Grace", Size: 50KB, Editor: "Text Editor"
        - Document 2: Name: "Drawing.png", Creation Date: 2023-10-06, Author: "Heidi", Size: 80KB, Editor: "Image Editor"
    Expected Output: Authors = []
---
+ Test Case 4: Retrieving Authors from Mixed Editor Documents
    Input: Get authors for documents using "Text Editor"
    SetUp: 
    1. Create a file system with the following documents:
        - Document 1: Name: "Notes.txt", Creation Date: 2023-10-07, Author: "Ivy", Size: 30KB, Editor: "Text Editor"
        - Document 2: Name: "Video.mp4", Creation Date: 2023-10-08, Author: "Jack", Size: 400MB, Editor: "Video Editor"
        - Document 3: Name: "Image.JPG", Creation Date: 2023-10-09, Author: "Kathy", Size: 120KB, Editor: "Image Editor"
    Expected Output: Authors = ["Ivy"]
---
+ Test Case 5: Multiple Authors for a Single Document Type
    Input: Get authors for documents using "Image Editor"
    SetUp: 
    1. Create a file system with the following documents:
        - Document 1: Name: "Portfolio.jpg", Creation Date: 2023-09-30, Author: "Leo", Size: 600KB, Editor: "Image Editor"
        - Document 2: Name: "Banner.png", Creation Date: 2023-10-01, Author: "Mona", Size: 300KB, Editor: "Image Editor"
        - Document 3: Name: "Presentation.ppt", Creation Date: 2023-10-02, Author: "Nina", Size: 150KB, Editor: "Text Editor"
    Expected Output: Authors = ["Leo", "Mona"]
```

***
