import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
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
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType(EmployeeType.FULL_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PART_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.FULL_TIME);
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        // 3. Call Company.getFullTimeEmployeeCount() on "FoodExpress"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees from mixed employee types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Input: Count full-time employees in a company with no employees.
        
        // SetUp:
        // 1. Create a Company named "QuickDeliver" with no employees added
        company.setName("QuickDeliver");
        // No employees added - using default empty list from constructor
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when company has no employees", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Input: Count full-time employees in a company with only part-time employees.
        
        // SetUp:
        // 1. Create a Company named "SnackNation"
        company.setName("SnackNation");
        
        // 2. Add 4 part-time employees:
        Employee emp1 = new Employee();
        emp1.setName("Dave");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Eva");
        emp2.setType(EmployeeType.PART_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("Frank");
        emp3.setType(EmployeeType.PART_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Grace");
        emp4.setType(EmployeeType.PART_TIME);
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when all employees are part-time", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Input: Count full-time employees in a company with all employees being full-time.
        
        // SetUp:
        // 1. Create a Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // 2. Add 5 full-time employees:
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType(EmployeeType.FULL_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType(EmployeeType.FULL_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType(EmployeeType.FULL_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Kate");
        emp4.setType(EmployeeType.FULL_TIME);
        
        Employee emp5 = new Employee();
        emp5.setName("Liam");
        emp5.setType(EmployeeType.FULL_TIME);
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        company.addEmployee(emp5);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count all 5 full-time employees when all are full-time", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Input: Count full-time employees in a company with exactly one of each employee type.
        
        // SetUp:
        // 1. Create a Company named "TastyBites"
        company.setName("TastyBites");
        
        // 2. Add 2 employees:
        Employee emp1 = new Employee();
        emp1.setName("Mona");
        emp1.setType(EmployeeType.FULL_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Noah");
        emp2.setType(EmployeeType.PART_TIME);
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee when there is one full-time and one part-time", 1, result);
    }
}