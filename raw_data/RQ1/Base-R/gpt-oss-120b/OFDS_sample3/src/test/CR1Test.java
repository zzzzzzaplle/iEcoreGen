import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CountFullTimeEmployeesWithMixedTypes() {
        // Test Case 1: Count Full-Time Employees with Mixed Types
        // Input: Count full-time employees in a company with a mix of employee types.
        
        // SetUp:
        // 1. Create a Company named "FoodExpress"
        company.setName("FoodExpress");
        
        // 2. Add 3 employees:
        //    - Employee 1: name="Alice", EmployeeType=FULL_TIME
        //    - Employee 2: name="Bob", EmployeeType=PART_TIME  
        //    - Employee 3: name="Charlie", EmployeeType=FULL_TIME
        Employee alice = new Employee("Alice", EmployeeType.FULL_TIME);
        Employee bob = new Employee("Bob", EmployeeType.PART_TIME);
        Employee charlie = new Employee("Charlie", EmployeeType.FULL_TIME);
        
        company.addEmployee(alice);
        company.addEmployee(bob);
        company.addEmployee(charlie);
        
        // 3. Call Company.countFullTimeEmployees() on "FoodExpress"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees from mixed types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Input: Count full-time employees in a company with no employees.
        
        // SetUp:
        // 1. Create a Company named "QuickDeliver" with no employees added
        company.setName("QuickDeliver");
        // No employees added - using default empty list
        
        // Call Company.countFullTimeEmployees()
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Input: Count full-time employees in a company with only part-time employees.
        
        // SetUp:
        // 1. Create a Company named "SnackNation"
        company.setName("SnackNation");
        
        // 2. Add 4 part-time employees:
        //    - Employee 1: name="Dave", EmployeeType=PART_TIME
        //    - Employee 2: name="Eva", EmployeeType=PART_TIME
        //    - Employee 3: name="Frank", EmployeeType=PART_TIME
        //    - Employee 4: name="Grace", EmployeeType=PART_TIME
        Employee dave = new Employee("Dave", EmployeeType.PART_TIME);
        Employee eva = new Employee("Eva", EmployeeType.PART_TIME);
        Employee frank = new Employee("Frank", EmployeeType.PART_TIME);
        Employee grace = new Employee("Grace", EmployeeType.PART_TIME);
        
        company.addEmployee(dave);
        company.addEmployee(eva);
        company.addEmployee(frank);
        company.addEmployee(grace);
        
        // Call Company.countFullTimeEmployees()
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when only part-time employees exist", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Input: Count full-time employees in a company with all employees being full-time.
        
        // SetUp:
        // 1. Create a Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // 2. Add 5 full-time employees:
        //    - Employee 1: name="Henry", EmployeeType=FULL_TIME
        //    - Employee 2: name="Isla", EmployeeType=FULL_TIME
        //    - Employee 3: name="Jack", EmployeeType=FULL_TIME
        //    - Employee 4: name="Kate", EmployeeType=FULL_TIME
        //    - Employee 5: name="Liam", EmployeeType=FULL_TIME
        Employee henry = new Employee("Henry", EmployeeType.FULL_TIME);
        Employee isla = new Employee("Isla", EmployeeType.FULL_TIME);
        Employee jack = new Employee("Jack", EmployeeType.FULL_TIME);
        Employee kate = new Employee("Kate", EmployeeType.FULL_TIME);
        Employee liam = new Employee("Liam", EmployeeType.FULL_TIME);
        
        company.addEmployee(henry);
        company.addEmployee(isla);
        company.addEmployee(jack);
        company.addEmployee(kate);
        company.addEmployee(liam);
        
        // Call Company.countFullTimeEmployees()
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count all 5 full-time employees", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Input: Count full-time employees in a company with exactly one of each employee type.
        
        // SetUp:
        // 1. Create a Company named "TastyBites"
        company.setName("TastyBites");
        
        // 2. Add 2 employees:
        //    - Employee 1: name="Mona", EmployeeType=FULL_TIME
        //    - Employee 2: name="Noah", EmployeeType=PART_TIME
        Employee mona = new Employee("Mona", EmployeeType.FULL_TIME);
        Employee noah = new Employee("Noah", EmployeeType.PART_TIME);
        
        company.addEmployee(mona);
        company.addEmployee(noah);
        
        // Call Company.countFullTimeEmployees()
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee from one of each type", 1, result);
    }
}