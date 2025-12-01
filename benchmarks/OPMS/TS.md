### CR 1: "Total budget of all projects in a company"


```
Computational requirement: "Total budget of all projects in a company" : Calculate the sum of the budget amounts of all projects in all departments of a company. 

+ Test Case 1: Single Department Budget Calculation  
    Input: Calculate the total budget for projects within a single department.  
    SetUp:  
    1. Create a company C001
    2. Create a department with ID: D001 and email: department1@company.com, and add the department to the company C001
    3. Create a project titled "Website Redevelopment" with description "Redesigning the company website", budget amount: 10000 CNY, and deadline: 2025-12-31.  The project is associated with the department D001.
    4. Create another project titled "Mobile App Development" with description "Developing a customer service app", budget amount: 15000 CNY, and deadline: 2026-01-15.  The project is associated with the department D001.
    5. Calculate the total budget for company C001
    Expected Output: Total budget = 10000 + 15000 = 25000 CNY  
---
+ Test Case 2: Multiple Departments Budget Calculation  
    Input: Calculate the total budget across multiple departments.  
    SetUp:  
    1. Create a company C002. Create Department D001 with email: department1@company.com , and add the department to the company C002
    2. Create a project in D001 titled "HR Software" with budget amount: 20000 CNY.  
    3. Create Department D002 with email: department2@company.com.  
    4. Create a project in D002 titled "Sales Training Program" with budget amount: 30000 CNY.  The project is associated with the department D002.
    5. Create another project in D002 titled "Marketing Campaign" with budget amount: 25000 CNY.  The project is associated with the department D002.
    6. Calculate the total budget for company C002 
    Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY  
---
+ Test Case 3: Budget Calculation with Zero Projects  
    Input: Calculate the total budget for a department with no projects.  
    SetUp:  
    1. Create a company C003. Create Department with ID: D003 and email: department3@company.com, and add the department to the company C003
    2. Calculate the total budget for company C003
    Expected Output: Total budget = 0 CNY  
---
+ Test Case 4: Education Project Budget with Funding Group  
    Input: Calculate the total budget including an education project with a funding group.  
    SetUp:  
    1. Create a company C004. Create a department with ID: D004 and email: department4@company.com, and add the department to the company C004
    2. Create an education project titled "Scholarship Program" with budget amount: 50000 CNY, deadline: 2026-05-31, and funding group type: government group.  The project is associated with the department D004.
    3. Create another project titled "R&D Initiative" with budget amount: 70000 CNY and deadline: 2026-07-15. The project is associated with the department D004.
    4. Create a department with ID: D005 and email: department5@company.com, and add the department to the company C004.
    5. Create another project titled "R&D5 Initiative" with budget amount: 70000 CNY and deadline: 2026-07-19. The project is associated with the department D005.
    4. Calculate the total budget for company C004.  
    Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY  
---
+ Test Case 5: Community Project Budget with Mixed Funding Group  
    Input: Calculate the total budget including a community project with a mixed funding group.  
    SetUp:  
    1. Create a company C005. Create a department with ID: D006 and email: department5@company.com, and add the department to the company C005
    2. Create a community project titled "Community Health Awareness" with budget amount: 40000 CNY, deadline: 2027-02-28, and funding group type: mixed group.  The project is associated with the department D006.
    3. Create a project titled "Environmental Clean-up Initiative" with budget amount: 60000 CNY and deadline: 2027-03-30.  The project is associated with the department D006.
    4. Calculate the total budget for company C005.  
    Expected Output: Total budget = 40000 + 60000 = 100000 CNY  
```


***


### CR 2: "Average budget of all projects in a department"
 

```
Computational requirement: "Average budget of all projects in a department" : Calculate the average budget amount of all projects in a given department.

+ Test Case 1: "Single Project Average Budget Calculation"
    Input: Calculate the average budget of projects in the "Marketing" department.
    SetUp: 
    1. Create a department with ID: D001 and email: marketing@company.com
    2. Create a project titled "Ads Campaign" with description "Q1 advertising budget" and budget amount: 50000 CNY, deadline: 2025-12-31 in department D001.
    3. Create a project titled "Market Research" with description "Research on consumer behavior" and budget amount: 30000 CNY, deadline: 2025-11-30 in department D001.
    4. Create another department with ID: D002 and email: development@company.com.
    5. Create a project titled "New Feature Development" with description "Developing a new feature" and budget amount: 200000 CNY, deadline: 2026-01-15 in department D002.
    6. Calculate the average budget for department D001.
    Expected Output: Average budget = (50000 + 30000) / 2 = 40000 CNY
---
+ Test Case 2: "Multiple Departments Average Budget Calculation"
    Input: Calculate the average budget of projects across multiple departments.
    SetUp: 
    1. Create a department with ID: D001 and email: hr@company.com.
    2. Create a project titled "Employee Training" with description "Training for employees" and budget amount: 20000 CNY, deadline: 2025-08-15 in department D001.
    3. Create a department with ID: D002 and email: sales@company.com.
    4. Create a project titled "Sales Strategy" with description "New sales strategy implementation" and budget amount: 40000 CNY, deadline: 2025-09-15 in department D002.
    5. Create another department with ID: D003 and email: it@company.com.
    6. Create a project titled "System Upgrade" with description "Upgrade company systems" and budget amount: 60000 CNY, deadline: 2025-10-01 in department D003.
    7. Calculate the average budget for projects in departments D001, D002, and D003, respectively.
    8. Calculate the average budget for departments D001, D002, and D003.
    Expected Output: 
    Average budget of projects in D001: 20000 CNY
    Average budget of projects in D002: 40000 CNY
    Average budget of projects in D003: 60000 CNY
    Average budget for projects in all departments D001, D002, and D003: (20000 + 40000 + 60000) / 3 = 40000 CNY
---
+ Test Case 3: "Single Project Calculation for Zero Budget"
    Input: Calculate the average budget when only one project with zero budget exists in a department.
    SetUp: 
    1. Create a department with ID: D004 and email: finance@company.com.
    2. Create a project titled "Budget Review" with description "Review of the annual budget" and budget amount: 0 CNY, deadline: 2025-07-30 in department D004.
    3. Calculate the average budget for department D004.
    Expected Output: Average budget = 0 CNY
---
+ Test Case 4: "No Projects in Department"
    Input: Calculate the average budget for a department with no projects.
    SetUp: 
    1. Create a department with ID: D005 and email: research@company.com.
    2. Calculate the average budget for department D005 (which has no projects).
    Expected Output: Average budget = 0 CNY (or handled as "No projects available")
---
+ Test Case 5: "Projects with Different Budgets Calculation"
    Input: Calculate the average budget of projects with varying budget amounts.
    SetUp: 
    1. Create a department with ID: D006 and email: product@company.com.
    2. Create a project titled "Product Launch" with description "Launching new product" and budget amount: 150000 CNY, deadline: 2025-12-01 in department D006.
    3. Create a project titled "Market Analysis" with description "Analysis of market trends" and budget amount: 75000 CNY, deadline: 2025-10-15 in department D006.
    4. Create a project titled "Client Engagement" with description "Engaging with key clients" and budget amount: 50000 CNY, deadline: 2025-11-30 in department D006.
    5. Calculate the average budget for department D006.
    Expected Output: Average budget = (150000 + 75000 + 50000) / 3 = 91666.67 CNY
```


***


### CR 3: "Number of employees working on production projects in a company"
 

```
Computational requirement: "Number of employees working on production projects in a company" : Count the total number of employees working on production projects across all departments in a company. 

+ Test Case 1: Count Employees in Single Department with Production Projects in a company
    Input: Calculate the number of employees working on production projects in a company
    SetUp:  
    1. Create a company C001.  Create a department with ID: D001 and email: department1@example.com, and add the department to the company C001.
    2. Add a production project titled "Product Launch" with site code "PL123" to the department.
    3. Hire 3 permanent employees named Alice, Bob, and Charlie with employee IDs: E001, E002, and E003, respectively, for the project.
    4. Hire 2 temporary employees named David and Eve with employee IDs: E004 and E005, respectively, for the project.
    Expected Output: Total number of employees = 5

---
+ Test Case 2: Count Employees Across Multiple Departments in a company
    Input: Calculate the number of employees working on production projects across multiple departments in a company
    SetUp:  
    1. Create a company C002. Create two departments: D001 (email: department1@example.com) and D002 (email: department2@example.com), and add the department to the company C002.
    2. Add a production project titled "Factory Upgrade" with site code "FU456" to D001 and hire 4 permanent employees.
    3. Add a production project titled "New Product Development" with site code "NPD789" to D002 and hire 3 temporary employees.
    Expected Output: Total number of employees = 7

---
+ Test Case 3: Count Employees with No Production Projects 
    Input: Calculate the number of employees working on production projects in a department with no production project.
    SetUp:  
    1. Create a company C003. Create a department with ID: D003 and email: department3@example.com, and add the department to the company C003.
    2. Add a research project titled "Market Research" (no production project).
    3. Hire 2 permanent employees named Frank and Grace with employee IDs: E006 and E007, respectively.
    Expected Output: Total number of employees = 0

---
+ Test Case 4: Count Employees with Mixed Project Types in a company
    Input: Calculate the number of employees working on production projects in a department with mixed project types.
    SetUp:  
    1.  Create a company C004. Create a department with ID: D004 and email: department4@example.com, and add the department to the company C004.
    2. Add a production project titled "Process Optimization" with site code "PO101" and hire 2 temporary employees.
    3. Add a community project and an education project (no production project employees).
    Expected Output: Total number of employees = 2

---
+ Test Case 5: Count Employees in Department Without Active Projects  in a company
    Input: Calculate the number of employees working on production projects in a department that has no active projects.
    SetUp:  
    1.  Create a company C005. Create a department with ID: D005 and email: department5@example.com, and add the department to the company C005.
    2. The department has previously hired 3 permanent employees named Henry, Ian, and Jack with employee IDs: E008, E009, and E010, respectively.
    3. No projects are currently ongoing in this department.
    Expected Output: Total number of employees = 0
```


***


### CR 4: "Funding group type of all community projects in a department"


```
Computational requirement: "Funding group type of all community projects in a department" : Retrieve the funding group type of all community projects within a given department.

+ Test Case 1: Retrieve Funding Group Type for Single Community Project 
    Input: Retrieve funding group type for community projects in department with ID: D001.
    SetUp: 
    1. Create a department with ID: D001 and email: "department1@example.com".
    2. Create a community project in department D001 titled "Community Clean-Up" with a description "A project to clean the local park", budget amount: 5000 CNY, and deadline: "2025-12-31".
    3. Assign the funding group type "Government Group" to the community project.
    Expected Output: There is 1 Funding group type: "Government Group"
---
+ Test Case 2: Retrieve Funding Group Type for Multiple Community Projects
    Input: Retrieve funding group types for community projects in department with ID: D002.
    SetUp: 
    1. Create a department with ID: D002 and email: "department2@example.com".
    2. Create two community projects in department D002: 
       - Title: "Food Drive", Description: "Collect food for the needy", Budget: 3000 CNY, Deadline: "2025-11-15" with funding group type "Private Group".
       - Title: "Health Awareness Campaign", Description: "Promote health screenings", Budget: 2000 CNY, Deadline: "2025-10-20" with funding group type "Mixed Group".
    Expected Output: There are 2 Funding group type: "Private Group", "Mixed Group".
---
+ Test Case 3: No Community Projects in Department
    Input: Retrieve funding group type for community projects in department with ID: D003.
    SetUp: 
    1. Create a department with ID: D003 and email: "department3@example.com".
    2. Ensure no community projects are created in department D003.
    Expected Output: There is 0 Funding group type.
---
+ Test Case 4: Retrieve Funding Group Type with Multiple Departments
    Input: Retrieve funding group types for community projects in department with ID: D004.
    SetUp: 
    1. Create a department with ID: D004 and email: "department4@example.com".
    2. Create a community project titled "Neighborhood Beautification" with a description "Enhancing community space", budget amount: 7500 CNY, and deadline: "2025-12-01" with funding group type "Mixed Group".
    3. Create a different department D005 with a community project to validate isolation:
       - ID: D005, email: "department5@example.com", project titled "Local Library Improvement", funding group type "Government Group".
    Expected Output: There is 1 Funding group type in D004 : "Mixed Group". There is 1 Funding group type in D005 : "Government Group"
---
+ Test Case 5: Funding Group Type with Invalid Department ID
    Input: Retrieve funding group types for community projects in department with an invalid ID: D999.
    SetUp: 
    1. Assume no department exists with ID: D999.
    Expected Output: There is 0 Funding group type.
```


***
