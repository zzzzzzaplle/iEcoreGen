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
        // Create a Company named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add 3 employees with mixed types
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
        
        // Call countFullTimeEmployees() on "FoodExpress"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees from mixed types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Create a Company named "QuickDeliver" with no employees added
        company.setName("QuickDeliver");
        
        // Call countFullTimeEmployees() on "QuickDeliver"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Create a Company named "SnackNation"
        company.setName("SnackNation");
        
        // Add 4 part-time employees
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
        
        // Call countFullTimeEmployees() on "SnackNation"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when only part-time employees exist", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Create a Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // Add 5 full-time employees
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
        
        // Call countFullTimeEmployees() on "PizzaPro"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count all 5 full-time employees", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Create a Company named "TastyBites"
        company.setName("TastyBites");
        
        // Add 2 employees: one full-time and one part-time
        Employee emp1 = new Employee();
        emp1.setName("Mona");
        emp1.setType("full-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Noah");
        emp2.setType("part-time");
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        
        // Call countFullTimeEmployees() on "TastyBites"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee when one of each type exists", 1, result);
    }
}