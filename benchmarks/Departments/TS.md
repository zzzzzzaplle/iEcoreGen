### CR 1: Assign an office as headquarters for a department. The office must belong to the department. Returns true if successful, false if the office doesn't exist in the department or if the department already has a headquarters.

```
Computational requirement: Assign an office as headquarters for a department. The office must belong to the department. Returns true if successful, false if the office doesn't exist in the department or if the department already has a headquarters.

+ Test Case 1: "Assign Valid Headquarter to Department Without Existing Headquarter"
    SetUp:
    1. Create Company with Department "Engineering"
    2. Add Office "OfficeA" to Engineering
    Action: Set "OfficeA" as headquarter for "Engineering"
    Expected Output: true
    Verification: OfficeA is the headquarter of Engineering.
---
+ Test Case 2: "Assign Office Not Belonging to Department"
    SetUp:
    1. Create Company with Departments "Engineering" and "Marketing"
    2. Add Office "OfficeA" to Marketing
    Action: Set "OfficeA" as headquarter for "Engineering"
    Expected Output: false
    Verification: Engineering do not have headquarter
---
+ Test Case 3: "Reassign Existing Headquarter"
    SetUp:
    1. Create Company with Department "Engineering"
    2. Add Offices "OfficeA" and "OfficeB" to Engineering
    3. Set "OfficeA" as headquarter
    Action: Set "OfficeB" as new headquarter
    Expected Output: false
    Verification: Engineering's headquarter remains OfficeA
---
+ Test Case 4: "Assign to Department with Single Office"
    SetUp:
    1. Create Company with Department "HR"
    2. Add only Office "HR-Main" to HR
    Action: Set "HR-Main" as headquarter
    Expected Output: true
    Verification: HR's headquarter is HR-Main
---
+ Test Case 5: "Assign to Non-Existent Department"
    SetUp:
    1. Create Company with no departments
    2. Create Department "ghostDept"
    Action: Set "OfficeX" as headquarter for "ghostDept"
    Expected Output: false (because the department "ghostDept" does not belong to the Company)
```

***


### CR 2: Assign an employee as manager of a specified department. The employee must belong to the company. Returns true if successful, false if the department already has a manager or the employee doesn't exist in the company.

```
Computational requirement: Assign an employee as manager of a specified department. The employee must belong to the company. Returns true if successful, false if the department already has a manager or the employee doesn't exist in the company.

+ Test Case 1: "Assign Valid Employee to Managerless Department"
    SetUp:
    1. Create Company with Department "Sales"
    2. Add Employee "E001" to Company
    Action: Assign "E001" as manager to "Sales"
    Expected Output: true
    Verification: Sales's manager == E001
---
+ Test Case 2: "Assign Non-Existent Employee"
    SetUp:
    1. Create Company with Department "Finance"
    2. Create Employee "E999"
    Action: Assign "E999" (non-existent) as manager
    Expected Output: false
    Verification: Finance do not have a manager
---
+ Test Case 3: "Reassign to Department with Existing Manager"
    SetUp:
    1. Create Company with Department "IT"
    2. Add Employees "E002" and "E003" to Company
    3. Assign "E002" as IT manager
    Action: Assign "E003" as new manager
    Expected Output: false
    Verification: IT's manager remains E002
---
+ Test Case 4: "Assign Employee from Different Department"
    SetUp:
    1. Create Company with Departments "R&D" and "Support"
    2. Add Employee "E004" to R&D
    Action: Assign "E004" as Support manager
    Expected Output: true (since employee belongs to company)
    Verification: Support's manager is E004
---
+ Test Case 5: "Assign to Non-Existent Department"
    SetUp:
    1. Create empty Company
    2. Create department "GhostDept"
    Action: Assign Employee "E001" to "GhostDept"
    Expected Output: false
```
***


### CR 3: Delete a department: Remove the department and its offices (including headquarters), and then remove employees (including manager) from the company. Returns true if successful, false if the department doesn't exist.

```
Computational requirement: Delete a department: Remove the department and its offices (including headquarters), and then remove employees (including manager) from the company. Returns true if successful, false if the department doesn't exist.

+ Test Case 1: "Delete Existing Department with Offices and Employees"
    SetUp:
    1. Create Company with Department "Legal"
    2. Add Offices "L1", "L2" to Legal
    3. Set "L1" as headquarter
    4. Assign "E101" as manager to Legal
    Action: Delete "Legal" department
    Expected Output: true
    Verification: 
      - Company's departments excludes "Legal"
      - Company's employees excludes "E101"
      - Offices "L1", "L2" removed
---
+ Test Case 2: "Delete Non-Existent Department"
    SetUp:
    1. Create empty Company
    2. Create department "Phantom"
    Action: Delete "Phantom" department
    Expected Output: false
---
+ Test Case 3: "Delete Department with Only Offices"
    SetUp:
    1. Create Company with Department "EmptyDept"
    2. Add Office "O1" to EmptyDept
    Action: Delete "EmptyDept"
    Expected Output: true
    Verification: Company's departments excludes "EmptyDept"
---
+ Test Case 4: "Delete Last Remaining Department"
    SetUp:
    1. Create Company with single Department "Solo"
    Action: Delete "Solo"
    Expected Output: true
    Verification: Company's departments is empty
---
+ Test Case 5: "Delete Department with Manager but No Offices"
    SetUp:
    1. Create Company with Department "Remote"
    2. Add Employee "E201" to Remote
    3. Assign "E201" as manager
    Action: Delete "Remote"
    Expected Output: true
    Verification: Company's employees excludes "E201"
```

***


### CR 4: Verify whether a department has a manager assigned: Check if a specified department has a manager assigned (returns true/false).


```
Computational requirement: Verify whether a department has a manager assigned: Check if a specified department has a manager assigned (returns true/false).

+ Test Case 1: "Check Department with Assigned Manager"
    SetUp:
    1. Create Company with Department "Verified"
    2. Add Employee "E301" and assign as manager
    Action: Verify manager assignment for "Verified"
    Expected Output: true
---
+ Test Case 2: "Check Managerless Department"
    SetUp:
    1. Create Company with Department "Unmanaged"
    Action: Verify manager assignment for "Unmanaged"
    Expected Output: false
---
+ Test Case 3: "Check After Manager Removal"
    SetUp:
    1. Create Company with Department "Temp"
    2. Add and assign manager "E302"
    3. Delete "E302" from company
    Action: Verify manager assignment
    Expected Output: false
---
+ Test Case 4: "Check Non-Existent Department"
    SetUp:
    1. Create empty Company
    Action: Verify manager for "Missing"
    Expected Output: false
---
+ Test Case 5: "Check Recently Assigned Manager"
    SetUp:
    1. Create Company with Department "NewDept"
    2. Add Employee "E303" and immediately assign as manager
    Action: Verify assignment
    Expected Output: true
```

***


### CR 5: List the departments of the company that have not been assigned a manager. If all departments have already been assigned a manager, return null.


```
Computational requirement: List the departments of the company that have not been assigned a manager. If all departments have already been assigned a manager, return null.

+ Test Case 1: "Company with Both Managed and Unmanaged Departments"
    SetUp:
    1. Create Company with Departments:
       - "Managed" (has manager M1)
       - "Unmanaged" (no manager)
       - "New" (no manager)
    Action: List departments without manager
    Expected Output: ["Unmanaged", "New"]
---
+ Test Case 2: "Fully Managed Company"
    SetUp:
    1. Create Company with 3 departments
    2. Assign manager M1, M2, M3 to each department
    Action: List departments without manager
    Expected Output: null
---
+ Test Case 3: "Empty Company"
    SetUp:
    1. Create Company with no departments
    Action: List departments without manager
    Expected Output: null
---
+ Test Case 4: "Single Unmanaged Department"
    SetUp:
    1. Create Company with only "Orphan" department
    Action: List departments without manager
    Expected Output: ["Orphan"]
---
+ Test Case 5: "After Manager Removal"
    SetUp:
    1. Create Company with Department "Volatile"
    2. Assign manager M1 and then remove manager M1
    Action: List departments without manager
    Expected Output: ["Volatile"]
```
***