### CR 1 :  "Count the number of full - time employees in a company"  
 


```
Calculational Requirement: "Count the number of full - time employees in a company"  
+ Test Case 1: Count Full-Time Employees with Mixed Types 
    Input: Count full-time employees in a company with a mix of employee types.
    SetUp:  
    1. Create a Company named "FoodExpress".
    2. Add 3 employees: 
       - Employee 1: name="Alice", EmployeeType=FULL_TIME
       - Employee 2: name="Bob", EmployeeType=PART_TIME
       - Employee 3: name="Charlie", EmployeeType=FULL_TIME
    3. Call Company.getFullTimeEmployeeCount() on "FoodExpress".
    Expected Output: Total full-time employees = 2

---
+ Test Case 2: Count Full-Time Employees with No Employees 
    Input: Count full-time employees in a company with no employees.
    SetUp:  
    1. Create a Company named "QuickDeliver" with no employees added.
    Expected Output: Total full-time employees = 0

---
+ Test Case 3: Count Full-Time Employees with Only Part-Time Employees 
    Input: Count full-time employees in a company with only part-time employees.
    SetUp:  
    1. Create a Company named "SnackNation".
    2. Add 4 part-time employees: 
       - Employee 1: name="Dave", EmployeeType=PART_TIME
       - Employee 2: name="Eva", EmployeeType=PART_TIME
       - Employee 3: name="Frank", EmployeeType=PART_TIME
       - Employee 4: name="Grace", EmployeeType=PART_TIME
       
    Expected Output: Total full-time employees = 0

---
+ Test Case 4: Count Full-Time Employees with Same Type 
    Input: Count full-time employees in a company with all employees being full-time.
    SetUp:  
    1. Create a Company named "PizzaPro".
    2. Add 5 full-time employees:
       - Employee 1: name="Henry", EmployeeType=FULL_TIME
       - Employee 2: name="Isla", EmployeeType=FULL_TIME
       - Employee 3: name="Jack", EmployeeType=FULL_TIME
       - Employee 4: name="Kate", EmployeeType=FULL_TIME
       - Employee 5: name="Liam", EmployeeType=FULL_TIME
       
    Expected Output: Total full-time employees = 5

---
+ Test Case 5: Count Full-Time Employees with One of Each Type 
    Input: Count full-time employees in a company with exactly one of each employee type.
    SetUp:  
    1. Create a Company named "TastyBites".
    2. Add 2 employees:
       - Employee 1: name="Mona", EmployeeType=FULL_TIME
       - Employee 2: name="Noah", EmployeeType=PART_TIME
       
    Expected Output: Total full-time employees = 1
```


 ***


### CR 2 :  "Find the registration numbers of all vehicles driven by part - time employees in a company"  
 


```
Calculational Requirement: "Find the registration numbers of all vehicles driven by part - time employees in a company"  
+ Test Case 1: "Single Part-Time Employee Vehicle Retrieval" 
    Input: Retrieve registration numbers for vehicles driven by part-time employees.
    SetUp: 
    1. Create a Company object named "Food Express".
    2. Create two part-time employees: 
       - Employee 1: name "Alice" with ID "E001".
       - Employee 2: name "Bob" with ID "E002".
    3. Create two full-time employees:
       - Employee 3: name "Charlie" with ID "E003".
       - Employee 4: name "Diana" with ID "E004".
    4. Create three vehicles:
       - Vehicle 1: registration number "ABC123", currentDriver: Employee 1 (part-time).
       - Vehicle 2: registration number "XYZ789", currentDriver: Employee 2 (part-time).
       - Vehicle 3: registration number "LMN456", currentDriver: Employee 3 (full-time).
    5. Assign vehicles to the Company object "Food Express".
    Expected Output: Registration numbers = ["ABC123", "XYZ789"]
---
+ Test Case 2: "No Part-Time Employees" 
    Input: Retrieve registration numbers for vehicles with no part-time employees.
    SetUp:
    1. Create a Company object named "Quick Delivery".
    2. Create three full-time employees:
       - Employee 1: name "Ethan" with ID "E005".
       - Employee 2: name "Fiona" with ID "E006".
       - Employee 3: name "George" with ID "E007".
    3. Create two vehicles:
       - Vehicle 1: registration number "QWE111", currentDriver: Employee 1.
       - Vehicle 2: registration number "RTY222", currentDriver: Employee 2.
    4. Assign vehicles to the Company object "Quick Delivery".
    Expected Output: Registration numbers = []
---
+ Test Case 3: "Mixed Employees and Vehicles" 
    Input: Retrieve registration numbers for vehicles driven by part-time employees amidst mixed employees.
    SetUp:
    1. Create a Company object named "Gourmet Delivery".
    2. Create one part-time employee: 
       - Employee 1: name "Henry" with ID "E008".
    3. Create three full-time employees:
       - Employee 2: name "Isla" with ID "E009".
       - Employee 3: name "Jack" with ID "E010".
       - Employee 4: name "Kara" with ID "E011".
    4. Create four vehicles:
       - Vehicle 1: registration number "DEF333", currentDriver: Employee 1 (part-time).
       - Vehicle 2: registration number "GHI444", currentDriver: Employee 2 (full-time).
       - Vehicle 3: registration number "JKL555", currentDriver: Employee 3 (full-time).
    5. Assign vehicles to the Company object "Gourmet Delivery".
    Expected Output: Registration numbers = ["DEF333"]
---
+ Test Case 4: "Multiple Drivers for One Vehicle" 
    Input: Retrieve registration numbers for vehicles with multiple part-time drivers.
    SetUp:
    1. Create a Company object named "City Foods".
    2. Create two part-time employees: 
       - Employee 1: name "Lily" with ID "E012".
       - Employee 2: name "Mike" with ID "E013".
    3. Create one full-time employee:
       - Employee 3: name "Nina" with ID "E014".
    4. Create two vehicles:
       - Vehicle 1: registration number "PQR777", currentDriver: Employee 1 (part-time).
       - Vehicle 2: registration number "STU888", currentDriver: Employee 2 (part-time).
       - Vehicle 3: registration number "VWX999", currentDriver: Employee 3 (full-time).
    5. Assign vehicles to the Company object "City Foods".
    Expected Output: Registration numbers = ["PQR777", "STU888"]
---
+ Test Case 5: "All Vehicles without Drivers" 
    Input: Retrieve registration numbers for vehicles that currently have no drivers assigned.
    SetUp:
    1. Create a Company object named "Rapid Deliveries".
    2. Create two part-time employees: 
       - Employee 1: name "Olivia" with ID "E015".
       - Employee 2: name "Paul" with ID "E016".
    3. Create two vehicles with no current drivers:
       - Vehicle 1: registration number "AAA000", currentDriver: null.
       - Vehicle 2: registration number "BBB111", currentDriver: null.
    4. Assign vehicles to the Company object "Rapid Deliveries".
    Expected Output: Registration numbers = []
```


 ***


### CR 3 :  "Count the number of rented vehicles in a company"  


```
Calculational Requirement: "Count the number of rented vehicles in a company"  
+ Test Case 1: Count Rented Vehicles with No Rented Vehicles  
    Input: Count the number of rented vehicles in the company.  
    SetUp:  
    1. Create a Company object named "FastDeliveryInc".  
    2. Add three vehicles to the company:  
       - A owned vehicle with registration number "ABC123".  
       - A owned vehicle with registration number "XYZ789".  
       - A owned vehicle with registration number "LMN456".  
    Expected Output: Rented vehicle count = 0  

---
+ Test Case 2: Count Rented Vehicles with One Rented Vehicle  
    Input: Count the number of rented vehicles in the company.  
    SetUp:  
    1. Create a Company object named "QuickEats".  
    2. Add four vehicles to the company:  
       - A owned vehicle with registration number "GHI101".  
       - A rented vehicle with registration number "JKL202".  
       - A owned vehicle with registration number "MNO303".  
       - A rented vehicle with registration number "PQR404".  
    Expected Output: Rented vehicle count = 2  

---
+ Test Case 3: Count Rented Vehicles All Rented  
    Input: Count the number of rented vehicles in the company.  
    SetUp:  
    1. Create a Company object named "FoodExpress".  
    2. Add five vehicles to the company:  
       - A rented vehicle with registration number "STU505".  
       - A rented vehicle with registration number "VWX606".  
       - A rented vehicle with registration number "YZA707".  
       - A rented vehicle with registration number "BCD808".  
       - A rented vehicle with registration number "EFG909".  
    Expected Output: Rented vehicle count = 5  

---
+ Test Case 4: Count Rented Vehicles with Mixed Vehicle Types  
    Input: Count the number of rented vehicles in the company.  
    SetUp:  
    1. Create a Company object named "DailyDeliveries".  
    2. Add six vehicles to the company:  
       - A owned vehicle with registration number "HIJ010".  
       - A rented vehicle with registration number "KLM111".  
       - A owned vehicle with registration number "NOP222".  
       - A rented vehicle with registration number "QRS333".  
       - A owned vehicle with registration number "TUV444".  
       - A owned vehicle with registration number "WXY555".  
    Expected Output: Rented vehicle count = 2  

---
+ Test Case 5: Count Rented Vehicles with No Vehicles  
    Input: Count the number of rented vehicles in the company.  
    SetUp:  
    1. Create a Company object named "DeliveriesRUs".  
    2. Do not add any vehicles to the company.  
    Expected Output: Rented vehicle count = 0  

```


 ***


### CR 4 :  "Find the names of all employees who are currently driving a vehicle in a company" 
 


```
Calculational Requirement:  "Find the names of all employees who are currently driving a vehicle in a company" 

+ Test Case 1: Single Employee with a Vehicle  
    Input: Retrieve the current drivers' names for a company with a single employee assigned to a vehicle.
    SetUp:  
    1. Create a Company named "Food Express".
    2. Create an Employee named "John Doe" of type Full-Time.
    3. Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver.
    
    Expected Output: ["John Doe"]
---
+ Test Case 2: Multiple Employees, Multiple Vehicles  
    Input: Retrieve the current drivers' names for a company with multiple employees and vehicles.
    SetUp:  
    1. Create a Company named "Quick Delivery".
    2. Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time).
    3. Create Vehicles: 
        - Registration "XYZ789" with driver "Alice Smith".
        - Registration "LMN456" with driver "Bob Johnson".
        
    Expected Output: ["Alice Smith", "Bob Johnson"]
---
+ Test Case 3: No Current Drivers  
    Input: Retrieve the current drivers' names for a company with no current drivers assigned to any vehicles.
    SetUp:  
    1. Create a Company named "Gourmet Delivery".
    2. Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time).
    3. Create Vehicles: 
        - Registration "DEF321" not assigned to any driver.
        - Registration "JKL654" not assigned to any driver.
        
    Expected Output: []
---
+ Test Case 4: Mixed Vehicles State  
    Input: Retrieve the current drivers' names for a company with some vehicles assigned to drivers and some not.
    SetUp:  
    1. Create a Company named "Fast Meals Co.".
    2. Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time).
    3. Create Vehicles: 
        - Registration "RST234" with driver "Eva Green".
        - Registration "UVW567" not assigned to any driver.
        - Registration "OPQ890" with driver "Frank Wright".
        
    Expected Output: ["Eva Green", "Frank Wright"]
---
+ Test Case 5: Employees Without Vehicles  
    Input: Retrieve the current drivers' names for a company where employees are not assigned any vehicles.
    SetUp:  
    1. Create a Company named "Delicious Delivery".
    2. Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles.
    
    Expected Output: []
```


 ***
