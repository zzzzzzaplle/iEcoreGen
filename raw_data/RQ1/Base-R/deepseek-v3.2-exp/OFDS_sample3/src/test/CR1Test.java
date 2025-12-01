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
        // Create a Company named "FoodExpress"
        company = new Company();
        company.setName("FoodExpress");
        
        // Add 3 employees with mixed types
        List<Employee> employees = new ArrayList<>();
        
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setFullTime(true); // FULL_TIME
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setFullTime(false); // PART_TIME
        
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setFullTime(true); // FULL_TIME
        
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() and verify result
        int result = company.countFullTimeEmployees();
        assertEquals("Total full-time employees should be 2", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Create a Company named "QuickDeliver" with no employees
        company = new Company();
        company.setName("QuickDeliver");
        
        // No employees added - use empty list
        company.setEmployees(new ArrayList<Employee>());
        
        // Call countFullTimeEmployees() and verify result
        int result = company.countFullTimeEmployees();
        assertEquals("Total full-time employees should be 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Create a Company named "SnackNation"
        company = new Company();
        company.setName("SnackNation");
        
        // Add 4 part-time employees
        List<Employee> employees = new ArrayList<>();
        
        Employee emp1 = new Employee();
        emp1.setName("Dave");
        emp1.setFullTime(false); // PART_TIME
        
        Employee emp2 = new Employee();
        emp2.setName("Eva");
        emp2.setFullTime(false); // PART_TIME
        
        Employee emp3 = new Employee();
        emp3.setName("Frank");
        emp3.setFullTime(false); // PART_TIME
        
        Employee emp4 = new Employee();
        emp4.setName("Grace");
        emp4.setFullTime(false); // PART_TIME
        
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() and verify result
        int result = company.countFullTimeEmployees();
        assertEquals("Total full-time employees should be 0 when all employees are part-time", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Create a Company named "PizzaPro"
        company = new Company();
        company.setName("PizzaPro");
        
        // Add 5 full-time employees
        List<Employee> employees = new ArrayList<>();
        
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setFullTime(true); // FULL_TIME
        
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setFullTime(true); // FULL_TIME
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setFullTime(true); // FULL_TIME
        
        Employee emp4 = new Employee();
        emp4.setName("Kate");
        emp4.setFullTime(true); // FULL_TIME
        
        Employee emp5 = new Employee();
        emp5.setName("Liam");
        emp5.setFullTime(true); // FULL_TIME
        
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        employees.add(emp5);
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() and verify result
        int result = company.countFullTimeEmployees();
        assertEquals("Total full-time employees should be 5 when all employees are full-time", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Create a Company named "TastyBites"
        company = new Company();
        company.setName("TastyBites");
        
        // Add 2 employees: one full-time and one part-time
        List<Employee> employees = new ArrayList<>();
        
        Employee emp1 = new Employee();
        emp1.setName("Mona");
        emp1.setFullTime(true); // FULL_TIME
        
        Employee emp2 = new Employee();
        emp2.setName("Noah");
        emp2.setFullTime(false); // PART_TIME
        
        employees.add(emp1);
        employees.add(emp2);
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() and verify result
        int result = company.countFullTimeEmployees();
        assertEquals("Total full-time employees should be 1 when there is one full-time and one part-time employee", 1, result);
    }
}