
### CR 1: "Create an IPO application". 

```
Computational requirement: "Create an IPO application". A retail customer begins an IPO application by supplying the target company, the number of shares (>0), the amount of money, and uploading the document(non-null). The system first checks that the customer is still eligible to apply for IPOs and has no approved application for the same company; only then is an application record created. It returns true on success; otherwise, false.

+ Test Case 1: "Standard eligible submission"
    Input: An eligible customer (ID "C001", named "John Smith", email "john.smith@example.com", phone "555-1234") applies for an IPO with company "TechCorp" (email: "techcorp@gmail.com"), requesting 100 shares with a payment of $5,000, and uploads the allowance document 'A'.
    Setup:
    1. Customer "C001" allows IPO applications.
    2. Customer "C001" has no previously approved applications for "TechCorp".
    Expected Output: True (application is successfully created)
---
+ Test Case 2: "Customer not eligible"
    Input: Customer "C002" (named "Alice Johnson", email "alice.j@example.com", phone "555-5678") attempts to apply for an IPO with company "BioMed" (email: "biomed@gmail.com"), requesting 50 shares with $2,500 payment, and uploads document 'B'.
    Setup:
    1. Customer has lost IPO eligibility after repeated failed attempts.
    2. No existing applications for "BioMed"
    Expected Output: False
---
+ Test Case 3: "Duplicate approved application"
    Input: Customer "C003" (named "Robert Chen", email "r.chen@example.com", phone "555-9012") submits another application to "GreenEnergy" (email: "greenenergy@gmail.com") for 10 shares ($300) with document 'G'.
    Setup:
    1. Customer remains eligible.
    2. submit the application to "GreenEnergy" (email: "greenenergy@gmail.com") for 10 shares ($300) with document 'G'.
    3. the bank approve the application
    Expected Output: False (only one approved application per company allowed)
---
+ Test Case 4: "Missing document"
    Input: Eligible customer "C004" (named "Emma Davis", email "emma.d@example.com", phone "555-3456") applies to "AutoFuture" (email: "autofuture@gmail.com") for 25 shares ($1,000) but forgets to upload any document.
    Setup:
    1. No prior applications for "AutoFuture"
    Expected Output: False (document upload is mandatory)
---
+ Test Case 5: "Zero-share application"
    Input: Eligible customer "C005" (named "James Wilson", email "j.wilson@example.com", phone "555-7890") applies to "NanoChip" (email: "nanotech@gmail.com") for 0 shares ($0) with document 'N'.
    Setup:
    1. No existing applications for "NanoChip"
    Expected Output: False
---
+ Test Case 6: "Negative share count"
    Input: Eligible customer "C006" (named "Sophia Martinez", email "s.m@example.com", phone "555-2345") attempts to apply to "CloudServ" (email: "cloudserv@gmail.com") for -5 shares (-$200) with document 'C'.
    Setup:
    1. No prior applications for "CloudServ"
    Expected Output: False  (negative shares/amount are invalid)
```
***


### CR 2 : "Approve or reject an application". 

```
Computational requirement: "Approve or reject an application". The bank reviews a specific application after verifying: the customer's current eligibility status. If the application is rejected, the bank immediately emails the customer a rejection notice. If the application is approved, the bank sends two information emails: one to the customer and one to the company. The operation returns true if on success; otherwise, false.

+ Test Case 1: "Approve pending request"
    Input: Bank approves application "APP-1001" for company "SolarMax"
    Setup:
    1. Customer "C007" (named "Michael Brown", email "m.brown@example.com", phone "555-1122")
        - Applied for 10 shares ($200) in "SolarMax" (email: solarmax@gmail.com)
        - Uploaded document 'S'
        - Can apply for IPO
    2. The customer is pending the application.
    Expected Output: True (status changes to APPROVAL)
---
+ Test Case 2: "Reject pending request"
    Input: Bank rejects application "APP-1002" for "HealthPlus"
    Setup:
    1. Customer "C008" (named "Olivia Lee", email "olivia.l@example.com", phone "555-3344")
        - Applied for 10 shares ($5000) in "HealthPlus" (email: healthplus@gmail.com)
        - Uploaded document 'H'
        - Can apply for IPO
    2. The customer is pending the application.
    Expected Output: True (status changes to REJECTED)
---
+ Test Case 3: "Approve already approved record"
    Input: Bank attempts to re-approve application "APP-1003"
    Setup:
    1. Customer "C009" (named "Daniel Kim", email "d.kim@example.com", phone "555-5566")
        - Existing approved application "APP-1003" for "HealthPlus" : Applied for 10 shares ($5000) in "HealthPlus" (email: healthplus@gmail.com)
        - Status = APPROVAL
        - Document 'H' on file
    Expected Output: False (cannot modify approved applications)
---
+ Test Case 4: "Reject already rejected record"
    Input: Bank tries to reject application "APP-1004"
    Setup:
    1. Customer "C010" (named "Sophie Zhang", email "s.zhang@example.com", phone "555-7788")
        - Previous application "APP-1004" marked REJECTED : Applied for 10 shares ($5000) in "Health" (email: health@gmail.com)
        - Rejection reason: "Insufficient documentation"
    Expected Output: False (cannot modify final decisions)
---
+ Test Case 5: "Approve record tied to ineligible customer"
    Input: Bank processes application "APP-1005"
    Setup:
    1. Customer "C011" (named "William Wang", email "will.w@example.com", phone "555-9900")
        - Can not apply for IPO
        - Existing pending application "APP-1005" : Applied for 10 shares ($5000) in "Cloud" (email: Cloud@gmail.com)
        - Eligibility revoked due to KYC expiration
    Expected Output: False (must maintain eligibility throughout process)
```
***

### CR 3 : "Retrieve a customer's application-count summary".

```
Computational requirement: "Retrieve a customer's application-count summary". The customer can report how many IPO applications has filed in total, including approval and rejected applications. Applications that have not yet been reviewed by the bank are not included. It returns 0 if the customer has no recorded applications.

+ Test Case 1: "No applications at all"
    Input: Customer "C101" requests a count summary.
    Setup:
    1. Customer "C101" (named "Thomas Anderson", email "t.anderson@example.com", phone "555-0101", can apply for IPO) 
    2. No IPO requests have ever been filed.
    Expected Output: 0
---
+ Test Case 2: "Single pending request"
    Input: Customer "C102" asks for the total number of filings.
    Setup:
    1. Customer "C102" (named "Lisa Rodriguez", email "l.rodriguez@example.com", phone "555-0202", can apply for IPO)
    2. One record exists in pending status:
        Application ID: "APP-2024-001"
        Company: "QuantumTech" (quantumtech@gmail.com)
        Shares: 50 ($2,500)
        Document: 'QT-2024-FormA'
        Status: approval
    Expected Output: 1
---
+ Test Case 3: "Mix of approved and rejected"
    Input: Customer "C103" checks total filings.
    Setup:
    1. Customer "C103" (named "David Kim", email "d.kim@example.com", phone "555-0303", can not apply for IPO) 
    2. Two APPROVAL records and one REJECTED record are stored:
    - Approved applications:
        "APP-2023-101" (Neuralink, 100 shares/$10,000, Document: 'QT-22023-101')
        "APP-2023-102" (SpaceY, 30 shares/$15,000, Document: 'QT-2023-102')
    - Rejected application:
        "APP-2024-002" (BioGen, 20 shares/$1,000, Document: 'QT-2024-002')
    Expected Output: 3
---
+ Test Case 4: "Five historical requests"
    Input: Customer "C104" queries the overall count.
    Setup:
    1. Customer "C104" (named "Emma Wilson", email "e.wilson@example.com", phone "555-0404", can apply for IPO)
    2. Five records exist: 1 APPROVAL, 2 REJECTED, 2 pending:
    - APPROVED: "APP-2023-105" (RoboCorp, 100 shares/$10,000, Document: 'QT-2023-105')
    - REJECTED:
        "APP-2023-106" (AI Ventures, 100 shares/$10,000, Document: 'QT-2023-106')
        "APP-2024-003" (NanoMed, 100 shares/$10,000, Document: 'QT-2024-003')
    - PENDING:
        "APP-2024-004" (GreenEnergy, 100 shares/$10,000, Document: 'QT-2024-004')
        "APP-2024-005" (CloudScale, 100 shares/$10,000, Document: 'QT-2024-005')
    Expected Output: 3
---
+ Test Case 5: "All requests canceled"
    Input: Customer "C105" asks for the figure.
    Setup:
    1. Customer "C105" (named "James Chen", email "j.chen@example.com", phone "555-0505", can apply for IPO)
    2. Create a pending application "APP-1010" : Applied for 10 shares ($5000) in "Cloud" (email: Cloud@gmail.com), Document: 'QT-1010'
    3. Cancel application "APP-1010" to "Cloud"
    Expected Output: 0
```
***

### CR 4 : "Query total approval IPO applications amount for a customer". 

```
Computational requirement: "Query total approval IPO applications amount for a customer". A customer may request an aggregate view of the customer's approved IPO purchases. Returns the sum of all approved application amounts.

+ Test Case 1: "No approved requests"
    Input: Customer "C201" requests total approved amount
    Setup:
    1. Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212") . Customer can apply for IPO
    2. Application history:
    - "APP-3001": PENDING (10 shares, $1,500, TechInc, document: "QT-3001")
    - "APP-3002": REJECTED (10 shares, $2,000, BioMed, document: "QT-3002"). Rejection reason: "Incomplete documentation"
    Expected Output: 0
---
+ Test Case 2: "Single approval"
    Input: Customer "C202"  checks the total approved sum.
    Setup:
    1. Customer "C202" (named "Robert Johnson", email "r.johnson@example.com", phone "555-2323")
    2. Approved application:
        ID: "APP-3003"
        Company: "SolarMax" (solarmax@gmail.com)
        Amount: $4,200
        Shares: 84
        Document: 'SM-2024-Q1'
    Expected Output: 4,200 (display: "Total approved amount: $4,200")
---
+ Test Case 3: "Multiple approvals different firms"
    Input: Customer "C203"  asks for the combined figure.
    Setup:
    1. Customer "C203" (named "Sophia Williams", email "s.williams@example.com", phone "555-3434")
    2. Approved applications:
    - "APP-3004":
        Company: "QuantumTech"
        Amount: $2,000
        Shares: 40
        Document: 'SM-2024-Q3004'
    - "APP-3005":
        Company: "Neuralink"
        Amount: $3,500
        Shares: 70
        Document: 'SM-2024-Q3005'
    Expected Output: 5,500 (display: "Total approved amount: $5,500")
---
+ Test Case 4: "Large portfolio"
    Input: Customer "C204" requests total amount.
    Setup:
    1. Customer "C204" (named "James Wilson", email "j.wilson@vip.example.com", phone "555-4545") 
    2. Approved applications (all $10,000 each):
        "APP-3006": TechGiant (200 shares, Document: 'SM-3006')
        "APP-3007": AutoFuture (250 shares, Document: 'SM-3007')
        "APP-3008": AeroSpace (125 shares, Document: 'SM-3008')
        "APP-3009": BioGenius (500 shares, Document: 'SM-3009')
        "APP-3010": GreenEnergy (200 shares, Document: 'SM-3010')
    Expected Output: 50,000 (display: "Total approved amount: $50,000")
---
+ Test Case 5: "Approvals plus pending"
    Input: Customer "C205" asks for aggregate approved spending.
    Setup:
    1. Customer "C205" (named "Olivia Brown", email "o.brown@example.com", phone "555-5656")
    2. Approved applications:
    - "APP-3011": 100 shares, $3,000 (CloudServ), Document: 'SM-3011'
    - "APP-3012": 20 shares, $2,750 (DataCore), Document: 'SM-3012'
    - "APP-3013": 30 shares, $3,000 (AI Ventures), Document: 'SM-3013'
    3. Pending applications:
    - "APP-3014": 10 shares, $600 (NanoTech), Document: 'SM-3014'
    - "APP-3015": 50 shares, $600 (RoboWorks), Document: 'SM-3015'
    Expected Output: 8,750 (display: "Total approved amount: $8,750 | 2 pending applications")
```
***

### CR 5 : "Cancel an application". 

```
Computational requirement: "Cancel an pending application". Providing the specific company name, a customer can cancel an application that is neither approved nor rejected by the bank. The operation returns true if the cancellation succeeds, otherwise, false.

+ Test Case 1: "Cancel still-pending request"
    Input: Customer "C301"  requests cancellation for "EcoWave"
    Setup:
    1. Customer "C301" (named "Benjamin Taylor", email "b.taylor@example.com", phone "555-1010") has a pending application for "EcoWave" (ecowave@gmail.com): 
    - Pending Application:
        ID: "APP-4001"
        Shares: 15 ($750)
        Document: 'EW-2024-03'
        Status: pending
    Expected Output: True
---
+ Test Case 2: "Cancel approved request"
    Input: Customer "C302"  tries to cancel IPO for  "SmartGrid".
    Setup:
    1. Customer "C302" (named "Charlotte Lee", email "c.lee@example.com", phone "555-2020") has an approved application for "SmartGrid" (smartgrid@business.com)
    - Approved Application:
        ID: "APP-4002"
        Shares: 30 ($3,000)
        Document: 'SG-2024-01'
        Status: approval
    Expected Output: False (Cannot cancel approved applications)
---
+ Test Case 3: "Cancel rejected request"
    Input: Customer "C303" tries to cancel the filing for "MedLife".
    Setup:
    1. Customer "C303" (named "Lucas Martin", email "l.martin@example.com", phone "555-3030") has a rejected application "MedLife" (medlife@health.com) application
    - Rejected Application:
        ID: "APP-4003"
        Shares: 20 ($1,000)
        Rejection Reason: "Insufficient funds"
        Document: 'SG-2024-03'
        Status: rejected
    Expected Output: False (Application already finalized)
---
+ Test Case 4: "Cancel nonexistent company"
    Input: Customer "C304" requests cancellation for "UnknownCorp".
    Setup:
    1. Customer "C304" (named "Amelia Clark", email "a.clark@example.com", phone "555-4040") requests cancellation for "UnknownCorp"
    1. No filing exists IPO for "UnknownCorp".
    Expected Output: False (No application found for specified company)
---
+ Test Case 5: "Cancel after prior cancellation"
    Input: Customer "C306" cancels "UrbanTech" (urbantech@innovate.com) filing
    Setup:
    1. Customer "C305" "C306" (named "Mia Anderson", email "m.anderson@example.com", phone "555-6060") has two pending IPO.
    2. Pending Applications:
    - "APP-4005":
        Company: "UrbanTech"
        Shares: 25 ($1,250)
        Document: 'SG-2024-005'

    - "APP-4006":
        Company: "AgroSeed"
        Shares: 40 ($2,000)
        Document: 'SG-2024-006'
    Expected Output: True ("UrbanTech" application canceled, "AgroSeed" remains unaffected)
```
***
