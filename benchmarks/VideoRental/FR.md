// ==version1==
```
+ Calculate the past-due amount for a video rental. If return date ≤ due date → overdue amount = 0. If return date > due date → overdue days = (return date - due date), overdue fee = overdue days × $0.5. If the tape has not been returned (return date is null) → overdue day = current date – due date; overdue fee = overdueDays × $0.50. Dates are in "yyyy-MM-dd" format. Return the fee rounded to two decimal places.

+ Check tape availability for a given date. For the given current date, a tape is unavailable if it belongs to any active rental whose return date is null. Return true if the tape is available; otherwise, false.

+ Add a video tape rental. Verify customer has <20 rentals and no past - due amount. And Verify the tape is availability. Returns true if all checks pass and the rental is processed; otherwise, returns false.

+ Calculate the total price for a customer's rental transaction. There maybe multiple active rentals for a transaction. Each rental total price = base rental fee + overdue fee. Base rental fee = rental days × $1 per day. Any partial rental day counts as a full day. Return the total rounded to two decimal places.

+ List unreturned tapes for a customer. Retrieves a list of all tapes id rented by a customer that have not been returned. Return an empty list if the customer has no active rentals.
```
// ==end==