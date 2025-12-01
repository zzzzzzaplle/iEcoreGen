### CR 1: Count the number of times a specific book has been checked out

```
Computational Requirement: Count the number of times a specific book has been checked out.

+ Test Case 1: "Counting Checkouts for a Book with Multiple Checkouts"
    Input: Count the number of times the book with title "Java Programming" has been checked out.
    SetUp: 
    1. Create a Book object with title: "Java Programming", barcode: "123456", ISBN: "978-3-16-148410-0", pages: 500.
    2. Checkout this book by Member M001 (start date: 2023-01-01, end date: 2023-01-15).
    3. Checkout this book by Member M001 (start date: 2023-02-01, end date: 2023-02-15).
    4. Checkout this book by Member M001 (start date: 2023-03-01, end date: 2023-03-15).
    Expected Output: The book has been checked out 3 times.

---
+ Test Case 2: "Counting Checkouts for a Book with No Checkouts"
    Input: Count the number of times the book with title "Python Basics" has been checked out.
    SetUp: 
    1. Create a Book object with title: "Python Basics", barcode: "654321", ISBN: "978-1-23-456789-0", pages: 400.
    2. No User check out this Book.
    Expected Output: The book has been checked out 0 time.

---
+ Test Case 3: "Counting Checkouts for a Book with One Checkout"
    Input: Count the number of times the book with title "Algorithms" has been checked out.
    SetUp: 
    1. Create a Book object with title: "Algorithms", barcode: "789012", ISBN: "978-0-12-345678-9", pages: 700.
    2. Checkout this book by Member M001 (start date: 2023-04-01, end date: 2023-04-15).
    Expected Output: The book has been checked out 1 time.

---
+ Test Case 4: "Counting Checkouts for a Book with Mix of Members and Guests"
    Input: Count the number of times the book with title "Data Structures" has been checked out.
    SetUp: 
    1. Create a Book object with title: "Data Structures", barcode: "101112", ISBN: "978-9-87-654321-0", pages: 600.
    2. Checkout this book by Member M001 (start date: 2023-05-01, end date: 2023-05-15).
    3. Checkout this book by Guest G001 (start date: 2023-06-01, end date: 2023-06-15).
    4. Checkout this book by Member M002 (start date: 2023-07-01, end date: 2023-07-15).
    Expected Output: The book has been checked out 3 times.

---
+ Test Case 5: "Counting Checkouts for a Book with Same User"
    Input: Count the number of times the book with title "Web Development" has been checked out.
    SetUp: 
    1. Create a Book object with title: "Web Development", barcode: "131415", ISBN: "978-2-36-547891-0", pages: 450.
    2. Checkout this book by Member M001 (start date: 2023-08-01, end date: 2023-08-15).
    3. Checkout this book by Member M001 (start date: 2023-08-16, end date: 2023-08-30).
    Expected Output: The book has been checked out 2 times.
```

***


### CR 2: Determine how many unique books a particular user has borrowed during a specified period.


```
Computational Requirement: Determine how many unique books a particular user has borrowed during a specified period.

+ Test Case 1: "Single User Borrowing Calculation for a Member"  
    Input: "Calculate the number of books borrowed by a MEMBER user over a specific period from 2023-01-01 to 2023-12-31."
    SetUp:  
    1. Create a MEMBER user with ID: U001, name: "Alice", email: "alice@example.com", address: "123 Main St."
    2. Create books: Book1 (ISBN: 978-3-16-148410-0), Book2 (ISBN: 978-1-4028-9467-7)
    3. Record CheckOuts: 
        - CheckOut1 for Book1 from 2023-01-10 to 2023-01-15
        - CheckOut2 for Book2 from 2023-03-05 to 2023-03-10
        - CheckOut3 for Book1 from 2023-05-20 to 2023-05-25
    4. Execute the method to count unique book borrowings for user U001 in the defined period.
    Expected Output: Total unique books borrowed = 2

---
+ Test Case 2: "Single User Borrowing Calculation for a Guest"  
    Input: "Calculate the number of books borrowed by a GUEST user over the period from 2023-06-01 to 2023-06-30."
    SetUp:  
    1. Create a GUEST user with ID: U002, name: "Bob", email: "bob@example.com", address: "456 Oak St."
    2. Create books: Book3 (ISBN: 978-0-1234-5678-9), Book4 (ISBN: 978-1-2345-6789-7)
    3. Record CheckOuts: 
        - CheckOut1 for Book3 from 2023-06-10 to 2023-06-15
        - CheckOut2 for Book4 from 2023-06-20 to 2023-06-25
    4. Execute the method to count unique book borrowings for user U002 in the defined period.
    Expected Output: Total unique books borrowed = 2

---
+ Test Case 3: "No Borrowing Activity within Date Range"  
    Input: "Calculate the number of books borrowed by a MEMBER user over a period with no checkouts."
    SetUp:  
    1. Create a MEMBER user with ID: U003, name: "Charlie", email: "charlie@example.com", address: "789 Pine St."
    2. Execute the method to count unique book borrowings for user U003 for the period from 2023-07-01 to 2023-07-31.
    Expected Output: Total unique books borrowed = 0

---
+ Test Case 4: "Overlapping Checkout Period"  
    Input: "Calculate the number of books borrowed by a MEMBER user with overlapping checkout periods."
    SetUp:  
    1. Create a MEMBER user with ID: U004, name: "Diana", email: "diana@example.com", address: "101 Maple St."
    2. Create a book: Book5 (ISBN: 978-3-16-148410-1)
    3. Record CheckOuts: 
        - CheckOut1 for Book5 from 2023-08-01 to 2023-08-10
        - CheckOut2 for Book5 again from 2023-08-15 to 2023-08-25
    4. Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10.
    Expected Output: Total unique books borrowed = 1

---
+ Test Case 5: "Multiple Users with Different Borrowing Activities"  
    Input: "Calculate the number of books borrowed by multiple users to check for unique counting."
    SetUp:  
    1. Create a MEMBER user with ID: U005, name: "Eve", email: "eve@example.com", address: "202 Birch St."
    2. Create a GUEST user with ID: U006, name: "Frank", email: "frank@example.com", address: "303 Cedar St."
    3. Create books: Book6 (ISBN: 978-0-321-56789-1), Book7 (ISBN: 978-0-12-345678-9)
    4. Record CheckOuts: 
        - CheckOut1 for Book6 by U005 from 2023-09-01 to 2023-09-10
        - CheckOut2 for Book7 by U005 from 2023-09-15 to 2023-09-20
        - CheckOut3 for Book6 by U006 from 2023-09-05 to 2023-09-15
    5. Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30.
    Expected Output: Total unique books borrowed by U005 = 2
```

***

### CR 3: Calculate the average page count of the unique books borrowed by a specific user.

```
Computational Requirement: Calculate the average page count of the unique books borrowed by a specific user.

+ Test Case 1: Average Pages Calculation for Single Book Borrowed  
    Input: Calculate the average number of pages for a user who has borrowed a single book.  
    SetUp:  
    1. Create a user with ID: U001, Name: "Alice", Email: "alice@example.com", Address: "123 Main St."  
    2. Create a book B001 with title: "Java Programming", ISBN: "123456789", barcode: "B001", number of pages: 300.  
    3. Create a CheckOut record for User U001 with the book B001, start date: "2023-10-01", end date: "2023-10-15".  
    Expected Output: Average number of pages = 300 / 1 = 300.0 pages.

---
+ Test Case 2: Average Pages Calculation for Multiple Books Borrowed  
    Input: Calculate the average number of pages for a user who has borrowed multiple books.  
    SetUp:  
    1. Create a user with ID: U002, Name: "Bob", Email: "bob@example.com", Address: "456 Elm St."  
    2. Create books:  
       - Book 1: title: "Data Structures", ISBN: "987654321", barcode: "B002", number of pages: 500.  
       - Book 2: title: "Algorithms", ISBN: "123123123", barcode: "B003", number of pages: 600.  
    3. Create CheckOut records for User U002:  
       - CheckOut for B002, start date: "2023-09-20", end date: "2023-09-25".  
       - CheckOut for B002, start date: "2023-10-20", end date: "2023-10-25". 
       - CheckOut for B003, start date: "2023-09-30", end date: "2023-10-05".  
    Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages.

---
+ Test Case 3: No Books Borrowed  
    Input: Calculate the average number of pages for a user with no borrowed books.  
    SetUp:  
    1. Create a user with ID: U003, Name: "Charlie", Email: "charlie@example.com", Address: "789 Oak St."  
    Expected Output: Average number of pages = 0 / 0 = 0.0 pages (or handle as undefined).

---
+ Test Case 4: Average Pages Calculation for Books with Different Page Counts  
    Input: Calculate the average number of pages for a user who borrowed books with varying page counts.  
    SetUp:  
    1. Create a user with ID: U004, Name: "Daisy", Email: "daisy@example.com", Address: "101 Maple St."  
    2. Create books:  
       - Book 1: title: "Web Development", ISBN: "321321321", barcode: "B004", number of pages: 250.  
       - Book 2: title: "Machine Learning", ISBN: "456456456", barcode: "B005", number of pages: 350.  
       - Book 3: title: "Database Systems", ISBN: "654654654", barcode: "B006", number of pages: 450.  
    3. Create CheckOut records for User U004:  
       - CheckOut for B004, start date: "2023-09-15", end date: "2023-09-22".  
       - CheckOut for B005, start date: "2023-09-25", end date: "2023-10-02".  
       - CheckOut for B006, start date: "2023-10-03", end date: "2023-10-10".  
    Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages.

---
+ Test Case 5: Average Pages Calculation for Guest User  
    Input: Calculate the average number of pages for a guest user who has borrowed books.  
    SetUp:  
    1. Create a guest user with ID: U005, Name: "Eve", Email: "eve@example.com", Address: "202 Birch St."  
    2. Create books:  
       - Book 1: title: "Networking", ISBN: "111111111", barcode: "B007", number of pages: 400.  
       - Book 2: title: "Cybersecurity", ISBN: "222222222", barcode: "B008", number of pages: 350.  
    3. Create CheckOut records for User U005:  
       - CheckOut for B007, start date: "2023-08-20", end date: "2023-08-30".  
       - CheckOut for B008, start date: "2023-09-01", end date: "2023-09-10".  
    Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages.
```

***


### CR 4: Calculate the total number of unique books checked out by a specific user.

```
Computational Requirement: Calculate the total number of unique books checked out by a specific user.

+ Test Case 1: Calculate Total Checkouts for Member User
    Input: Calculate the total number of unique books checked out by Member users.
    SetUp:  
    1. Create a Member user with ID: M001, Name: "Alice", Email: "alice@example.com", Address: "123 Main St"
    2. Create a Member user with ID: M002, Name: "Bob", Email: "bob@example.com", Address: "456 Elm St"
    3. Check Out 3 different books (B001, B002, B003) with user M001 (Booking: start date: 2023-01-01, end date: 2023-01-15; start date: 2023-02-01, end date: 2023-02-10; start date: 2023-03-15, end date: 2023-03-30)
    4. Check Out 2 same book (B001) with user M002 (Booking: start date: 2023-04-01, end date: 2023-04-12; start date: 2023-04-15, end date: 2023-04-20)
    Expected Output: Total Member Checkouts for M001 = 3; Total Member Checkouts for M002 = 1; 

---
+ Test Case 2: Calculate Total Checkouts for Guest User
    Input: Calculate the total number of unique books checked out by Guest users.
    SetUp:  
    1. Create a Guest user with ID: G001, Name: "Charlie", Email: "charlie@example.com", Address: "789 Pine St"
    2. Check Out 1 book (B001) with user G001 (Booking: start date: 2023-05-01, end date: 2023-05-10)
    Expected Output: Total Guest Checkouts for G001  = 1;

---
+ Test Case 3: Mixed User Types with No Checkouts
    Input: Calculate total checkouts for users with no checkouts.
    SetUp:  
    1. Create a Member user with ID: M003, Name: "Eve", Email: "eve@example.com", Address: "654 Maple St"
    2. 0 Check Out  with user M003
    Expected Output: Total Checkouts for M003 = 0

---
+ Test Case 4: Calculate Total Checkouts for Multiple Users with Mixed Types
    Input: Calculate the total number of unique books checked out by Member user
    SetUp:  
    1. Create a Member user with ID: M004, Name: "George", Email: "george@example.com", Address: "135 Cedar St"
    2. Check out 4 different books (B001, B002, B003, B004) with user M004 (booking dates: 2023-06-01 to 2023-07-01)
    Expected Output: Total Checkouts for M004 = 4;

---
+ Test Case 5: Calculate Total Checkouts for Multiple Users with Mixed Types
    Input: Calculate the total number of unique books checked out by Guest user
    SetUp:  
    1. Create a Guest user with ID: G002, Name: "Hannah", Email: "hannah@example.com", Address: "246 Spruce St"
    2. Check out 2 same book (B001) with user G002 (booking dates: 2023-07-15 to 2023-08-01, 2023-08-15 to 2023-09-01)
    3. Check out B004 3 times with user G002 (booking dates: 2024-06-01 to 2024-07-01, 2024-07-02 to 2024-07-11, 2024-08-01 to 2024-09-01)
    Expected Output: Total Checkouts for G002 = 2; 

```

***
