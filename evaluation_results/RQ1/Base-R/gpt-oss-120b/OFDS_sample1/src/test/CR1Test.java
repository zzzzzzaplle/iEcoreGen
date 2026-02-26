import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        // SetUp: Create a Company named "FoodExpress" with mix of employee types
        company.setName("FoodExpress");
        
        // Create employees with mixed types
        Employee employee1 = new Employee();
        employee1.setName("Alice");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setType(EmployeeType.PART_TIME);
        
        Employee employee3 = new Employee();
        employee3.setName("Charlie");
        employee3.setType(EmployeeType.FULL_TIME);
        
        // Add employees to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        
        // Call countFullTimeEmployees() and verify result
        int result = company.countFullTimeEmployees();
        assertEquals("Total full-time employees should be 2", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // SetUp: Create a Company named "QuickDeliver" with no employees
        company.setName("QuickDeliver");
        
        // Call countFullTimeEmployees() on empty company
        int result = company.countFullTimeEmployees();
        assertEquals("Total full-time employees should be 0 when no employees", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // SetUp: Create a Company named "SnackNation" with only part-time employees
        company.setName("SnackNation");
        
        // Create 4 part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Dave");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Eva");
        employee2.setType(EmployeeType.PART_TIME);
        
        Employee employee3 = new Employee();
        employee3.setName("Frank");
        employee3.setType(EmployeeType.PART_TIME);
        
        Employee employee4 = new Employee();
        employee4.setName("Grace");
        employee4.setType(EmployeeType.PART_TIME);
        
        // Add employees to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        
        // Call countFullTimeEmployees() and verify result
        int result = company.countFullTimeEmployees();
        assertEquals("Total full-time employees should be 0 when only part-time employees", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // SetUp: Create a Company named "PizzaPro" with all full-time employees
        company.setName("PizzaPro");
        
        // Create 5 full-time employees
        Employee employee1 = new Employee();
        employee1.setName("Henry");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Isla");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = new Employee();
        employee3.setName("Jack");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = new Employee();
        employee4.setName("Kate");
        employee4.setType(EmployeeType.FULL_TIME);
        
        Employee employee5 = new Employee();
        employee5.setName("Liam");
        employee5.setType(EmployeeType.FULL_TIME);
        
        // Add employees to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        company.addEmployee(employee5);
        
        // Call countFullTimeEmployees() and verify result
        int result = company.countFullTimeEmployees();
        assertEquals("Total full-time employees should be 5 when all employees are full-time", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // SetUp: Create a Company named "TastyBites" with one of each employee type
        company.setName("TastyBites");
        
        // Create one full-time and one part-time employee
        Employee employee1 = new Employee();
        employee1.setName("Mona");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Noah");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Add employees to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        
        // Call countFullTimeEmployees() and verify result
        int result = company.countFullTimeEmployees();
        assertEquals("Total full-time employees should be 1 when one full-time and one part-time", 1, result);
    }
}