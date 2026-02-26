import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        // SetUp: Create a Company named "FoodExpress" with mixed employee types
        company.setName("FoodExpress");
        
        // Create and add employees
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType("full-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType("part-time");
        
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType("full-time");
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Verify the expected output
        assertEquals("Total full-time employees should be 2", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // SetUp: Create a Company named "QuickDeliver" with no employees
        company.setName("QuickDeliver");
        
        // Call countFullTimeEmployees() method on empty company
        int result = company.countFullTimeEmployees();
        
        // Verify the expected output
        assertEquals("Total full-time employees should be 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // SetUp: Create a Company named "SnackNation" with only part-time employees
        company.setName("SnackNation");
        
        // Create and add 4 part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Dave");
        emp1.setType("part-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Eva");
        emp2.setType("part-time");
        
        Employee emp3 = new Employee();
        emp3.setName("Frank");
        emp3.setType("part-time");
        
        Employee emp4 = new Employee();
        emp4.setName("Grace");
        emp4.setType("part-time");
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Verify the expected output
        assertEquals("Total full-time employees should be 0 when only part-time employees exist", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // SetUp: Create a Company named "PizzaPro" with all full-time employees
        company.setName("PizzaPro");
        
        // Create and add 5 full-time employees
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType("full-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType("full-time");
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType("full-time");
        
        Employee emp4 = new Employee();
        emp4.setName("Kate");
        emp4.setType("full-time");
        
        Employee emp5 = new Employee();
        emp5.setName("Liam");
        emp5.setType("full-time");
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        company.addEmployee(emp5);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Verify the expected output
        assertEquals("Total full-time employees should be 5 when all employees are full-time", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // SetUp: Create a Company named "TastyBites" with one of each employee type
        company.setName("TastyBites");
        
        // Create and add employees
        Employee emp1 = new Employee();
        emp1.setName("Mona");
        emp1.setType("full-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Noah");
        emp2.setType("part-time");
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Verify the expected output
        assertEquals("Total full-time employees should be 1 when there is one full-time and one part-time employee", 1, result);
    }
}