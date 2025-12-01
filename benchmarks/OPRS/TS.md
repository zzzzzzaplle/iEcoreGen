### CR 1: Calculate the number of unsubmitted reviews for a given reviewer. Return 0 if all reviews are submitted by the reviewer.
```
Computational Requirement: Calculate the number of unsubmitted reviews for a given reviewer. Return 0 if all reviews are submitted by the reviewer.

+ Test Case 1: "Reviewer with 3 pending reviews" 
    Input: Calculate unsubmitted reviews for Reviewer R001
    Setup:  
    1. Create Reviewer R001
    2. Create Paper P1, P2, P3
    3. Assign P1/P2/P3 to R001 without feedback and grade from R001
    Expected Output: 3
---
+ Test Case 2: "All reviews submitted"  
    Input: Calculate unsubmitted reviews for Reviewer R002
    Setup: 
    1. Create Reviewer R002
    2. Create Paper P4, P5
    3. Assign P4/P5 to R002 with grades=ACCEPT
    Expected Output: 0
---
+ Test Case 3: "Mixed submission status"  
    Input: Calculate unsubmitted reviews for Reviewer R003
    Setup:  
    1. Create Reviewer R003
    2. Create Papers P6-P10
    3. Assign P6/P7 to R003 without feedback and grade from R003
    4. Assign P8-P10 to R003 with grade=REJECT
    Expected Output: 2
---
+ Test Case 4: "No assigned papers"  
    Input: Calculate unsubmitted reviews for Reviewer R004
    Setup:  
    1. Create Reviewer R004 (no assignments)
    Expected Output: 0
---
+ Test Case 5: "Partially submitted reviews"  
    Input: Calculate unsubmitted reviews for Reviewer R005
    Setup:  
    1. Create Reviewer R005
    2. Create Papers P11-P13
    3. Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
    Expected Output: 1
```

 
***


### CR 2: Before the chair makes the final decision, check if all reviews for a paper are either exclusively Accept or exclusively Reject. Return a boolean value.
 

```
Computational Requirement:  Before the chair makes the final decision, check if all reviews for a paper are either exclusively Accept or exclusively Reject. Return a boolean value.

+ Test Case 1: "Unanimous accept from 3 reviews" 
    Input: Check consensus for Paper P14
    Setup:  
    1. Create Paper P14
    2. Create 3 reviewers
    3. Assign paper P14 to 3 reviewers
    4. 3 reviewers give reviews: "grade:ACCEPT,feedback:'revise'","grade:ACCEPT,feedback:'revise'","grade:ACCEPT,feedback:'revise'"
    Expected Output: True
---
+ Test Case 2: "Split decision 2-1"  
    Input: Check consensus for Paper P15
    Setup:  
    1. Create Paper P15
    2. Create 2 reviewers
    3. Assign paper P15 to 2 reviewers
    4. 2 reviewers give reviews: 1 with ACCEPT, 1 with REJECT
    Expected Output: False
---
+ Test Case 3: "All reject with 4 reviews"  
    Input: Check consensus for Paper P16
    Setup:  
    1. Create Paper P16
    2. Create 4 reviewers
    3. Assign paper P16 to 4 reviewers
    4. 4 reviewers give the same reviews: grade=REJECT
    Expected Output: True
---
+ Test Case 4: "Mixed grades with 3 reviews"  
    Input: Check consensus for Paper P17
    Setup:  
    1. Create Paper P17
    2. Create 3 reviewers
    3. Assign paper P17 to 3 reviewers
    4. 2 reviewers have given feedback and grade to the paper P17: 1 ACCEPT, 1 REJECT.
    5. 1 reviewer do not give feedback to the paper
    Expected Output: False
---
+ Test Case 5: "Edge case: exactly 50% acceptance"  
    Input: Check consensus for Paper P18
    Setup:  
    1. Create Paper P18
    2. Create 4 reviewers
    3. Assign paper P18 to 4 reviewers
    4. 4 reviewers give reviews:  2 ACCEPT, 2 REJECT 
    Expected Output: False
```

 
***


### CR 3: Count the total number of papers submitted by an author.
 

```
Computational Requirement: Count the total number of papers submitted by an author.

+ Test Case 1: "Author with 5 papers" 
    Input: Count papers for Author A001
    Setup:  
    1. Create Author A001
    2. Create Papers P19-P23 linked to A001
    Expected Output: 5
---
+ Test Case 2: "New author with 0 papers"  
    Input: Count papers for Author A002
    Setup:  
    1. Create Author A002 (no papers)
    Expected Output: 0
---
+ Test Case 3: "Single-paper author"  
    Input: Count papers for Author A003
    Setup:  
    1. Create Author A003
    2. Create Paper P24 linked to A003
    Expected Output: 1
---
+ Test Case 4: "Multi-role user (author+reviewer)"  
    Input: Count papers for Author A004
    Setup:  
    1. Create User U1 with Author and Reviewer roles
    2. Create Papers P25-P27 linked to U1
    Expected Output: 3
---
+ Test Case 5: "Paper ownership validation"  
    Input: Count papers for Author A005
    Setup:  
    1. Create Author A005
    2. Create Papers P28 (A005)
    3. Create Author A006
    4. Create Papers P29 (A006)
    Expected Output: 1
```

 
***


### CR 4: Calculate the acceptance rate of papers for an author. Example: If an author submitted 10 papers with 6 accepted, return 0.60.  

```
Computational Requirement: Calculate the acceptance rate of papers for an author. Example: If an author submitted 10 papers with 6 accepted, return 0.60.  

+ Test Case 1: "Perfect acceptance rate"
    Input: Calculate acceptance rate for Author A006
    Setup:  
    1. Create Author A006
    2. Create Papers P30-P32 with final decision=ACCEPT
    Expected Output: 1.00
---
+ Test Case 2: "50% acceptance rate"  
    Input: Calculate acceptance rate for Author A007
    Setup:  
    1. Create Author A007
    2. Create Papers P33 (ACCEPT), P34 (REJECT)
    Expected Output: 0.50
---
+ Test Case 3: "No accepted papers"  
    Input: Calculate acceptance rate for Author A008
    Setup:  
    1. Create Author A008
    2. Create Papers P35-P37 with REJECT decisions
    Expected Output: 0.00
---
+ Test Case 4: "Mixed decisions with 1 acceptance"  
    Input: Calculate acceptance rate for Author A009
    Setup:  
    1. Create Author A009
    2. Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
    Expected Output: 0.33
---
+ Test Case 5: "Single paper author"  
    Input: Calculate acceptance rate for Author A010
    Setup:  
    1. Create Author A010
    2. Create Paper P41 with ACCEPT decision
    Expected Output: 1.00

```

 
***
### CR 5: Convert a reviewer’s historical grades (Accept = 1, Reject = 0) into a numerical average score between 0.0 and 1.0. Example: 3 Accept and 1 Reject → (3×1 + 1×0)/4 = 0.75.  

```
Computational Requirement: Convert a reviewer’s historical grades (Accept = 1, Reject = 0) into a numerical average score between 0.0 and 1.0. Example: 3 Accept and 1 Reject → (3×1 + 1×0)/4 = 0.75.  

+ Test Case 1: "All acceptances" 
    Input: Calculate rating tendency for Reviewer R006
    Setup:  
    1. Create Reviewer R006
    2. Assign 5 reviews to 5 papers with ACCEPT grades, respectively
    Expected Output: 1.00
---
+ Test Case 2: "Balanced 50-50 ratio"  
    Input: Calculate rating tendency for Reviewer R007
    Setup:  
    1. Create Reviewer R007
    2. Assign 3 ACCEPT, 3 REJECT reviews to 6 papers, respectively
    Expected Output: 0.50
---
+ Test Case 3: "No completed reviews"  
    Input: Calculate rating tendency for Reviewer R008
    Setup:  
    1. Create Reviewer R008 (no completed reviews)
    Expected Output: 0.00
---
+ Test Case 4: "Recent reject tendency"  
    Input: Calculate rating tendency for Reviewer R009
    Setup:  
    1. Create Reviewer R009
    2. Assign 1 ACCEPT, 4 REJECT reviews to 5 papers, respectively
    Expected Output: 0.20
---
+ Test Case 5: "Single review case"  
    Input: Calculate rating tendency for Reviewer R010
    Setup:  
    1. Create Reviewer R010
    2. Assign 1 REJECT review to 1 paper
    Expected Output: 0.00


```

 
***