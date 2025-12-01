### CR 1: Calculate the past-due amount for a video rental. If return date ≤ due date → overdue amount = 0. If return date > due date → overdue days = (return date - due date), overdue fee = overdue days × $0.5. If the tape has not been returned (return date is null) → overdue day = current date – due date; overdue fee = overdueDays × $0.50. Dates are in "yyyy-MM-dd" format. Return the fee rounded to two decimal places.


```
Computational requirement: Calculate the past-due amount for a video rental. If return date ≤ due date → overdue amount = 0. If return date > due date → overdue days = (return date - due date), overdue fee = overdue days × $0.5. If the tape has not been returned (return date is null) → overdue day = current date – due date; overdue fee = overdueDays × $0.50. Dates are in "yyyy-MM-dd" format. Return the fee rounded to two decimal places.

+ Test Case 1: "Returned 1 day late" 
    Input: return_date="2025-01-02"
    Setup: 
    1. Create a customer c1
    2. c1 add a VideoRental V001 with rental date "2025-01-01"
    3. Add Set return_date="2025-01-09"
    Expected Output: 0.50  # 1 day * $0.5
---
+ Test Case 2: "Unreturned and 3 days overdue"  
    Input: return_date=null, current_date="2025-01-12"
    Setup:
    1. Create a customer c2
    2. c2 add a VideoRental V002 with rental date "2025-01-01"
    Expected Output: 1.50  # 3 days * $0.5
---
+ Test Case 3: "Returned on due date"  
    Input: return_date="2025-01-06", current_date="2025-01-10"
    Setup:
    1.Create a customer c3
    2.c3 add a  VideoRental V003 with rental date "2025-01-01"
    2. Set return_date="2025-01-06"
    Expected Output: 0.00
---
+ Test Case 4: "Unreturned but not overdue"  
    Input: return_date=null, due_date="2025-01-10", current_date="2025-01-06"
    Setup:
    1.Create a customer c4
    2.c4 add a VideoRental V004 with rental date "2025-01-05"
    Expected Output: 0.00
---
+ Test Case 5: "Returned 5 days late"  
    Input: return_date=null, due_date="2025-01-08", current_date="2025-01-10"
    Setup:
    1.Create a customer c5
    2.c5 add a VideoRental V005 with rental date "2025-01-01"
    Expected Output: 3.00  # 6 days * $0.5

```


***


### CR 2: Check tape availability for a given date. For the given current date, a tape is unavailable if it belongs to any active rental whose return date is null or whose return date is after the given date. Return true if the tape is available; otherwise, false.


```
Computational requirement:  Check tape availability for a given date. For the given current date, a tape is unavailable if it belongs to any active rental whose return date is null or whose return date is after the given date. Return true if the tape is available; otherwise, false.

+ Test Case 1: "Tape is available"  
    Input: tape_id="T001", current_date="2025-01-01"
    Setup:
    1. Create Tape with id="T001"
    2. No active rentals for T001
    Expected Output: True
---
+ Test Case 2: "Tape is rented out"  
    Input: tape_id="T002", current_date="2025-01-01"
    Setup:
    1. Create Tape with id="T002". Create Customer C001. 
    2. C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
    Expected Output: False
---
+ Test Case 3: "Tape was rented but returned"  
    Input: tape_id="T003", current_date="2025-01-01"
    Setup:
    1. Create Tape with id="T003". Create Customer C002.
    2. C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
    Expected Output: True
---
+ Test Case 4: "Tape exists but has overdue rental"  
    Input: tape_id="T004", current_date="2025-01-10"
    Setup:
    1. Create Tape with id="T004". Create Customer C003.
    2. C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
    Expected Output: False
---
+ Test Case 5: "Tape was rented but returned by multiple customers"
    Input: tape_id="T005", current_date="2025-01-10"
    Setup: 
    1. Create Tape with id="T005". Create Customer C004, C005.
    2. C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
    3. C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
    Expected Output: The first creation: True. The second creation: False.
```


***


### CR 3: Add a video tape rental. Verify customer has <20 rentals and no past - due amount. And Verify the tape is availability. Returns true if all checks pass and the rental is processed; otherwise, returns false.


```
Computational requirement: Add a video tape rental. Verify customer has <20 rentals and no past - due amount. And Verify the tape is availability. Returns true if all checks pass and the rental is processed; otherwise, returns false.

+ Test Case 1: "Successful rental"  
    Input: C001 rents tape "T001" with current_date="2025-01-01"
    Setup:
    1. Create Customer C001 with 5 active rentals (rental dates: "2025-01-01" to "2025-01-05", all unreturned, due dates 7 days after rental).
    2. Create available Tape T001 with no active rentals.
    Expected Output: True
---
+ Test Case 2: "Customer has 20 rentals (max limit)"  
    Input: C002 rents tape "T002" with current_date="2025-01-01"
    Setup:
    1. Create Customer C002 with 20 active rentals (all unreturned, due dates 7 days after rental).
    2. Create available Tape T002
    Expected Output: False
---
+ Test Case 3: "Customer has overdue fees"  
    Input: C003 rents tape "T003" with current_date="2025-01-05"
    Setup:
    1. Create Customer C003 with 1 active rental, due_date="2025-01-05", return_date=null (3 days overdue).
    2. Create available Tape T003
    Expected Output: False
---
+ Test Case 4: "Tape is unavailable"  
    Input: C004 rents tape "T004" with current_date="2025-01-01"
    Setup:
    1. Create Customer C004 with 0 rentals
    2. Create Tape T004 with active rental (rented by another customer C010, return_date=null, due_date="2025-01-05").
    Expected Output: False
---
+ Test Case 5: "All conditions fail"  
    Input: C005 rents tape "T005" with current_date="2025-01-01"
    Setup:
    1. Create Customer C005 with 20 active rentals and one overdue rental (due_date="2024-12-31", return_date=null, overdue_amount $5.00).
    2. Create Tape T005 with active rental (rented by another customer C011, return_date=null, due_date="2025-01-05").
    Expected Output: False
```


***


### CR 4: Calculate the total price for a customer's rental transaction. Base rental fee = rentalDays × $1 per day. Total price = base rental fee + Σ current overdue fees for all of the customer's rentals. Return the total rounded to two decimal places.

```
Computational requirement: Calculate the total price for a customer's rental transaction. Base rental fee = rentalDays × $1 per day. Total price = base rental fee + Σ current overdue fees for all of the customer's rentals. Return the total rounded to two decimal places.


+ Test Case 1: "No overdue fees"  
    Input: customer_id="C001", current date is "2025-01-20"
    Setup:
    1. Create Customer C001
    2. Add Rental 1:
       - Tape ID="T001", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-03" (returned early, overdue fee=0)
    3. Add Rental 2:
       - Tape ID="T002", rental_date="2025-01-01", due_date="2025-01-15", return_date="2025-01-12" (returned early, overdue fee=0)
    Expected Output: 
    - Rental 1 price: 2 + 0 = 2
    - Rental 2 price: 11 + 0 = 11
    - total price = 13
---
+ Test Case 2: "One overdue rental"  
    Input: customer_id="C002", current date is "2025-01-20"
    Setup:
    1. Create Customer C002
    2. Add Rental 1:
       - Tape ID="T003", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-12" (7 days overdue × $0.5 = $3.50, rental duration = 11 days)
    Expected Output: Total price = 11 (base fee) + 3.50 (overdue) = $14.50
---
+ Test Case 3: "Multiple overdue rentals"  
    Input: customer_id="C003", current_date="2025-01-20"
    Setup:
    1. Create Customer C003
    2. Add Rental 1:
       - Tape ID="T004", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-09" (4 days overdue × $0.5 = $2.00, rental duration = 8 days)
    3. Add Rental 2:
       - Tape ID="T005", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-18" (3 days overdue × $0.5 = $1.50, rental duration = 8 days)
    Expected Output: Total price = 8+8 base fees + 2+1.5 overdue = $19.50
---
+ Test Case 4: "Mixed overdue and on-time rentals"  
    Input: customer_id="C004", current_date="2025-01-20"
    Setup:
    1. Create Customer C004
    2. Add Rental 1:
       - Tape ID="T006", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-07" (2 days overdue × $0.5 = $1.00, rental duration = 6 days)
    3. Add Rental 2:
       - Tape ID="T007", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-14" (on-time, overdue fee=0, rental duration = 4 days)
    Expected Output: Total price = (6+4 base) + 1 overdue = $11.00
---
+ Test Case 5: "Unreturned tape with current date overdue"  
    Input: customer_id="C006", current_date="2025-01-10"
    Setup:
    1. Create Customer C006
    2. Add Rental 1:
       - Tape ID="T008", rental_date="2025-01-01", due_date="2025-01-05", return_date=null (5 days overdue × $0.5 = $2.50, rental duration = 9 days)
    Expected Output: Total price = 9 (base fee) + 2.50 (overdue) = $11.50
```


***


### CR 5: List unreturned tapes for a customer. Retrieves a list of all tapes id rented by a customer that have not been returned. Return an empty list if the customer has no active rentals.


```
Computational requirement: List unreturned tapes for a customer. Retrieves a list of all tapes id rented by a customer that have not been returned. Return an empty list if the customer has no active rentals.

+  Test Case 1: "No rentals exist"  
    Input: customer_id="C001"
    Setup: 
    1. Create Customer C001 with empty rentals list
    Expected Output: Empty list, no active rentals.

+ Test Case 2: "All tapes returned"  
    Input: customer_id="C002"
    Setup:
    1. Create Customer C002
    2. Create Tape T001
    3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C002 and T001
    Expected Output: Empty list, all rentals returned.

+ Test Case 3: "One unreturned tape"  
    Input: customer_id="C003"
    Setup:
    1. Create Customer C003
    2. Create Tape T001
    3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C003 and T001
    Expected Output: List containing T001.

+ Test Case 4: "Mixed returned/unreturned"  
    Input: customer_id="C004"
    Setup:
    1. Create Customer C004
    2. Create Tape T001 and T002
    3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01", associated with C004
    4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C004
    Expected Output: List containing T002.

+ Test Case 5: "Multiple unreturned tapes"  
    Input: customer_id="C005"
    Setup:
    1. Create Customer C005
    2. Create Tapes T001 and T002
    3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", return_date=null, associated with C005
    4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", return_date=null, associated with C005
    Expected Output: List containing T001 and T002.
```
***