import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_CountFullTimeEmployeesWithMixedTypes() {
        // Test Case 1: Count Full-Time Employees with Mixed Types
        // SetUp: Create Company "FoodExpress" with mix of employee types
        company.setName("FoodExpress");
        
        List<Employee> employees = new ArrayList<>();
        
        // Create and add full-time employee Alice
        FullTimeEmployee emp1 = new FullTimeEmployee();
        emp1.setName("Alice");
        employees.add(emp1);
        
        // Create and add part-time employee Bob
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Bob");
        employees.add(emp2);
        
        // Create and add full-time employee Charlie
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Charlie");
        employees.add(emp3);
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Full-time employee count should be 2 for mixed types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // SetUp: Create Company "QuickDeliver" with no employees
        company.setName("QuickDeliver");
        
        // No employees added to the company
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Full-time employee count should be 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // SetUp: Create Company "SnackNation" with only part-time employees
        company.setName("SnackNation");
        
        List<Employee> employees = new ArrayList<>();
        
        // Create and add 4 part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Dave");
        employees.add(emp1);
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Eva");
        employees.add(emp2);
        
        PartTimeEmployee emp3 = new PartTimeEmployee();
        emp3.setName("Frank");
        employees.add(emp3);
        
        PartTimeEmployee emp4 = new PartTimeEmployee();
        emp4.setName("Grace");
        employees.add(emp4);
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Full-time employee count should be 0 when only part-time employees exist", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // SetUp: Create Company "PizzaPro" with all full-time employees
        company.setName("PizzaPro");
        
        List<Employee> employees = new ArrayList<>();
        
        // Create and add 5 full-time employees
        FullTimeEmployee emp1 = new FullTimeEmployee();
        emp1.setName("Henry");
        employees.add(emp1);
        
        FullTimeEmployee emp2 = new FullTimeEmployee();
        emp2.setName("Isla");
        employees.add(emp2);
        
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Jack");
        employees.add(emp3);
        
        FullTimeEmployee emp4 = new FullTimeEmployee();
        emp4.setName("Kate");
        employees.add(emp4);
        
        FullTimeEmployee emp5 = new FullTimeEmployee();
        emp5.setName("Liam");
        employees.add(emp5);
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Full-time employee count should be 5 when all employees are full-time", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // SetUp: Create Company "TastyBites" with one of each employee type
        company.setName("TastyBites");
        
        List<Employee> employees = new ArrayList<>();
        
        // Create and add one full-time employee
        FullTimeEmployee emp1 = new FullTimeEmployee();
        emp1.setName("Mona");
        employees.add(emp1);
        
        // Create and add one part-time employee
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Noah");
        employees.add(emp2);
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Full-time employee count should be 1 when one full-time and one part-time employee exist", 1, result);
    }
}