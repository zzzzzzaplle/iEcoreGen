import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = null;
    }
    
    @Test
    public void testCase1_CountFullTimeEmployeesWithMixedTypes() {
        // Test Case 1: Count Full-Time Employees with Mixed Types
        // Input: Count full-time employees in a company with a mix of employee types.
        
        // SetUp:
        // 1. Create a Company named "FoodExpress"
        company = new Company("FoodExpress");
        
        // 2. Add 3 employees with mixed types
        Employee employee1 = new Employee("Alice", EmployeeType.FULL_TIME);
        Employee employee2 = new Employee("Bob", EmployeeType.PART_TIME);
        Employee employee3 = new Employee("Charlie", EmployeeType.FULL_TIME);
        
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        
        // 3. Call Company.getFullTimeEmployeeCount() on "FoodExpress"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees from mixed types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Input: Count full-time employees in a company with no employees.
        
        // SetUp:
        // 1. Create a Company named "QuickDeliver" with no employees added
        company = new Company("QuickDeliver");
        
        // Call Company.getFullTimeEmployeeCount() on "QuickDeliver"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Input: Count full-time employees in a company with only part-time employees.
        
        // SetUp:
        // 1. Create a Company named "SnackNation"
        company = new Company("SnackNation");
        
        // 2. Add 4 part-time employees
        Employee employee1 = new Employee("Dave", EmployeeType.PART_TIME);
        Employee employee2 = new Employee("Eva", EmployeeType.PART_TIME);
        Employee employee3 = new Employee("Frank", EmployeeType.PART_TIME);
        Employee employee4 = new Employee("Grace", EmployeeType.PART_TIME);
        
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        
        // Call Company.getFullTimeEmployeeCount() on "SnackNation"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when only part-time employees exist", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Input: Count full-time employees in a company with all employees being full-time.
        
        // SetUp:
        // 1. Create a Company named "PizzaPro"
        company = new Company("PizzaPro");
        
        // 2. Add 5 full-time employees
        Employee employee1 = new Employee("Henry", EmployeeType.FULL_TIME);
        Employee employee2 = new Employee("Isla", EmployeeType.FULL_TIME);
        Employee employee3 = new Employee("Jack", EmployeeType.FULL_TIME);
        Employee employee4 = new Employee("Kate", EmployeeType.FULL_TIME);
        Employee employee5 = new Employee("Liam", EmployeeType.FULL_TIME);
        
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        company.addEmployee(employee5);
        
        // Call Company.getFullTimeEmployeeCount() on "PizzaPro"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count all 5 full-time employees", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Input: Count full-time employees in a company with exactly one of each employee type.
        
        // SetUp:
        // 1. Create a Company named "TastyBites"
        company = new Company("TastyBites");
        
        // 2. Add 2 employees: one full-time and one part-time
        Employee employee1 = new Employee("Mona", EmployeeType.FULL_TIME);
        Employee employee2 = new Employee("Noah", EmployeeType.PART_TIME);
        
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        
        // Call Company.getFullTimeEmployeeCount() on "TastyBites"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee from one of each type", 1, result);
    }
}