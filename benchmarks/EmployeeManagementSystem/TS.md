### CR 1: Calculate the total salary of all employees in the company. Total salary = sum(workers' salary + sales people's salary + managers' salary). Workers' salary = weeklyWorkingHour * hourlyRates + [holiday premiums for shift workers]. Sales people's salary = salary + amountOfSales * commissionPercentage. Managers' salary = salary. 


```
Computational Requirement: Calculate the total salary of all employees in the company. Total salary = sum(workers' salary + sales people's salary + managers' salary). Workers' salary = weeklyWorkingHour * hourlyRates + [holiday premiums for shift workers]. Sales people's salary = salary + amountOfSales * commissionPercentage. Managers' salary = salary. 

+ Test Case 1: "Calculate Total Salary for Company with Single Worker" 
    Input: "A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00"
    Expected Output: "The total salary of all employees in the company is 800.00 (40.00 * 20.00)."
---
+ Test Case 2: "Calculate Total Salary for Company with Single SalesPerson" 
    Input: "A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10"
    Expected Output: "The total salary of all employees in the company is 3200.00."
---
+ Test Case 3: "Calculate Total Salary for Company with Single Manager" 
    Input: "A Company with 1 Manager. Manager details: salary = 5000.00"
    Expected Output: "The total salary of all employees in the company is 5000.00."
---
+ Test Case 4: "Calculate Total Salary for Company with Worker, SalesPerson and Manager" 
    Input: "A Company with 1 Worker, 1 SalesPerson and 1 Manager. Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00. SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15. Manager details: salary = 4800.00"
    Expected Output: "The total salary of all employees in the company is 8595.00."
---
+ Test Case 5: "Calculate Total Salary for Company with Multiple Workers" 
    Input: "A Company with 2 Workers, 1 shift worker. First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00. Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00. Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00"
    Expected Output: "The total salary of all employees in the company is 2528.00."
```


***


### CR 2: Find average working hours per week for all workers in the delivery department.  Return 0 if there are no workers in the delivery department.


```
Computational Requirement: Find average working hours per week for all workers in the delivery department.  Return 0 if there are no workers in the delivery department.

+ Test Case 1: "Single worker in delivery department" 
    Input: "One worker in the Delivery department with a weeklyWorkingHour of 40 hours."
    Expected Output: "The average working hours per week is 40 hours."
---
+ Test Case 2: "Multiple workers in delivery department with same hours" 
    Input: "Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours."
    Expected Output: "The average working hours per week is 35 hours."
---
+ Test Case 3: "Multiple workers in delivery department with different hours" 
    Input: "Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours."
    Expected Output: "The average working hours per week is 25 hours."
---
+ Test Case 4: "Delivery department with no workers" 
    Input: "There are zero workers in the Delivery department."
    Expected Output: "The average working hours per week is 0 hours (as there are no workers)."
---
+ Test Case 5: "Delivery department mixed with other departments" 
    Input: "There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively."
    Expected Output: "The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours."
```


***


### CR 3: Determine the total commission amount for all salespeople in the company. Sum(amountOfSales * commissionPercentage) for all salespeople. 


```
Computational Requirement: Determine the total commission amount for all salespeople in the company. Sum(amountOfSales * commissionPercentage) for all salespeople. 

+ Test Case 1: "Single Salesperson with Non - zero Sales" 
    Input: "A Company object with one SalesPerson where amountOfSales = 1000.00 and commissionPercentage = 0.10"
    Expected Output: "The total commission amount is 100.00"
---
+ Test Case 2: "Zero Salespersons in Company" 
    Input: "A Company object with no SalesPerson objects."
    Expected Output: "The total commission amount is 0"
---
+ Test Case 3: "Multiple Salespersons with Non - zero Sales" 
    Input: "A Company object with two SalesPerson objects. First has amountOfSales = 2000.00 and commissionPercentage = 0.15, second has amountOfSales = 3000.00 and commissionPercentage = 0.20."
    Expected Output: "The total commission amount is 900.00"
---
+ Test Case 4: "Single Salesperson with Zero Sales" 
    Input: "A Company object with one SalesPerson where amountOfSales = 0 and commissionPercentage = 0.12"
    Expected Output: "The total commission amount is 0 * 0.12 = 0"
---
+ Test Case 5: "Multiple Salespersons with Mixed Sales" 
    Input: "A Company object with three SalesPerson objects. First has amountOfSales = 1500.00 and commissionPercentage = 0.08, second has amountOfSales = 0 and commissionPercentage = 0.10, third has amountOfSales = 4000.00 and commissionPercentage = 0.25."
    Expected Output: "The total commission amount is 1120.00"
```


***


### CR 4: Calculate total holiday premiums paid to all shift workers in the company.  Return 0 if there are no shift workers in the delivery department.

```
Computational Requirement: Calculate total holiday premiums paid to all shift workers in the company.  Return 0 if there are no shift workers in the delivery department.

+ Test Case 1: "No shift workers in the company" 
    Input: "A Company object with an empty list of ShiftWorkers"
    Expected Output: "The total holiday premiums should be 0"
---
+ Test Case 2: "One shift worker in the company" 
    Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
    Expected Output: "The total holiday premiums should be 500.00"
---
+ Test Case 3: "Multiple shift workers with different premiums" 
    Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
    Expected Output: "The total holiday premiums should be 900.00"
---
+ Test Case 4: "Multiple shift workers with some zero premiums" 
    Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
    Expected Output: "The total holiday premiums should be 400.00"
---
+ Test Case 5: "Single shift worker with zero premium" 
    Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
    Expected Output: "The total holiday premiums should be 0"
```


***


### CR 5: Get the number of direct subordinate employees for each manager. 


```
Computational  Requirement: Get the number of direct subordinate employees for each manager. 

+ Test Case 1: "Single manager with multiple subordinates" 
    Input: "A manager with 3 direct subordinate employees:1 Worker, 1 SalesPerson and 1 Manager."
    Setup:
        Create Manager M1.
        Assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates to M1.
    Expected Output: "The number of direct subordinates for Manager M1 is 3."
---
+ Test Case 2: "Manager with no subordinates" 
    Input: "A manager with no direct subordinate employees."
    Setup:
        Create Manager M1.
        Do not assign any subordinates.
    Expected Output: "The number of subordinates for the manager is 0."
---
+ Test Case 3: "Multiple managers with different number of subordinates" 
    Input: "Manager A has 3 subordinate employees, Manager B has 7 subordinate employees."
    Setup:
        Create Manager A and assign 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople).
        Create Manager B and assign 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople).
    Expected Output: "The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7."
---
+ Test Case 4: "Manager with a single subordinate" 
    Input: "A manager with 1 direct subordinate employee:1 Worker."
    Setup:
        Create Manager M1.
        Assign Worker W1 as subordinate.
    Expected Output: "The number of subordinates for the manager is 1."
---
+ Test Case 5: "Multiple managers with different number of subordinates" 
    Input: "Manager A has 5 subordinate employees: 1 Worker, 3 SalesPerson and 1 Manager B. Manager B has 1 subordinate employee: Worker W1."
    Setup:
        Create Manager A and assign worker W1, SalesPerson (S1, S2, S3), Manager B as direct subordinates.
        Create Manager B and assign 1 subordinate employee: Worker W1.
    Expected Output: "The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1."
```


***
