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
        company = null;
    }
    
    @Test
    public void testCase1_CountFullTimeEmployeesWithMixedTypes() {
        // Test Case 1: Count Full-Time Employees with Mixed Types
        // Input: Count full-time employees in a company with a mix of employee types.
        
        // SetUp: Create a Company named "FoodExpress"
        company = new Company("FoodExpress");
        
        // Add 3 employees with mixed types
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", true));  // FULL_TIME
        employees.add(new Employee("Bob", false));   // PART_TIME
        employees.add(new Employee("Charlie", true)); // FULL_TIME
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() on "FoodExpress"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Company with mixed employee types should have 2 full-time employees", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Input: Count full-time employees in a company with no employees.
        
        // SetUp: Create a Company named "QuickDeliver" with no employees
        company = new Company("QuickDeliver");
        // Employees list is empty by default
        
        // Call countFullTimeEmployees() on "QuickDeliver"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Company with no employees should have 0 full-time employees", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Input: Count full-time employees in a company with only part-time employees.
        
        // SetUp: Create a Company named "SnackNation"
        company = new Company("SnackNation");
        
        // Add 4 part-time employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Dave", false));   // PART_TIME
        employees.add(new Employee("Eva", false));    // PART_TIME
        employees.add(new Employee("Frank", false));  // PART_TIME
        employees.add(new Employee("Grace", false));  // PART_TIME
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() on "SnackNation"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Company with only part-time employees should have 0 full-time employees", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Input: Count full-time employees in a company with all employees being full-time.
        
        // SetUp: Create a Company named "PizzaPro"
        company = new Company("PizzaPro");
        
        // Add 5 full-time employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Henry", true));  // FULL_TIME
        employees.add(new Employee("Isla", true));   // FULL_TIME
        employees.add(new Employee("Jack", true));   // FULL_TIME
        employees.add(new Employee("Kate", true));   // FULL_TIME
        employees.add(new Employee("Liam", true));   // FULL_TIME
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() on "PizzaPro"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Company with all full-time employees should have 5 full-time employees", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Input: Count full-time employees in a company with exactly one of each employee type.
        
        // SetUp: Create a Company named "TastyBites"
        company = new Company("TastyBites");
        
        // Add 2 employees: one full-time and one part-time
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Mona", true));   // FULL_TIME
        employees.add(new Employee("Noah", false));  // PART_TIME
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() on "TastyBites"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Company with one full-time and one part-time employee should have 1 full-time employee", 1, result);
    }
}