// ==version1==
```
+ "Create an IPO application". A retail customer begins an IPO application by supplying the target company, the number of shares (>0), the amount of money, and uploading the document(non-null). The system first checks that the customer is still eligible to apply for IPOs and has no approved application for the same company; only then is an application record created. It returns true on success; otherwise, false.

+ "Approve or reject an application". If the application is rejected, the bank immediately emails the customer a rejection notice. The bank approves a specific application after verifying that the customer current can apply for IPO. If the application is approved, the bank sends two information emails: one to the customer and one to the company. The operation returns true if on success; otherwise, false.

+ "Retrieve a customer's application-count summary". The customer can report how many IPO applications has filed in total, including approval and rejected applications. Applications that have not yet been reviewed by the bank are not included. It returns 0 if the customer has no recorded applications.

+ "Query total approval IPO applications amount for a customer". A customer may request an aggregate view of the customer's approved IPO purchases. Returns the sum of all approved application amounts.

+ "Cancel an pending application". Providing the specific company name, a customer can cancel an application that is neither approved nor rejected by the bank. The operation returns true if the cancellation succeeds, otherwise, false.
```
// ==end==