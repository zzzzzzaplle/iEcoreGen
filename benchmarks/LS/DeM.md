// ==version1==
```
enum UserType {
    MEMBER
    GUEST
}

class Book {
    - title : String
    - barcode : String
    - ISBN : String
    - numberOfPages : integer

}

class User {
    - name : String
    - email : String
    - address : String
    - ID : String
    - type : UserType
    - checkOuts : List<CheckOut>
}

class CheckOut {
    - startDate : Date
    - endDate : Date
    - book : Book
}

class LibrarySystem {
    - users: List<User>
    - books: List<Book>

    + totalUniqueBooksCheckedOutByUser(User user) : int
    + averageNumberOfPagesUniqueBooksByUser(User user) : double
    + uniqueBooksBorrowedByUser(User user, Date start, Date end) : int
    + countBookCheckOuts(Book book) : int
}

LibrarySystem *-- "*" User : users
LibrarySystem *-- "*" Book : books

User *-- "*" CheckOut : checkouts
CheckOut --> "1" Book : book
```
// ==end==
