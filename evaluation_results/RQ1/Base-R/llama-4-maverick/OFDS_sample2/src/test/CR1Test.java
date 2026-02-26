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
    public void testCase1_countFullTimeEmployeesWithMixedTypes() {
        // Test Case 1: Count Full-Time Employees with Mixed Types
        // SetUp: Create company and add mixed employee types
        company.setName("FoodExpress");
        
        List<Employee> employees = new ArrayList<>();
        
        FullTimeEmployee emp1 = new FullTimeEmployee();
        emp1.setName("Alice");
        employees.add(emp1);
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Bob");
        employees.add(emp2);
        
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Charlie");
        employees.add(emp3);
        
        company.setEmployees(employees);
        
        // Call method under test
        int result = company.countFullTimeEmployees();
        
        // Verify expected output
        assertEquals("Total full-time employees should be 2", 2, result);
    }
    
    @Test
    public void testCase2_countFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // SetUp: Create company with no employees
        company.setName("QuickDeliver");
        company.setEmployees(new ArrayList<>());
        
        // Call method under test
        int result = company.countFullTimeEmployees();
        
        // Verify expected output
        assertEquals("Total full-time employees should be 0", 0, result);
    }
    
    @Test
    public void testCase3_countFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // SetUp: Create company with only part-time employees
        company.setName("SnackNation");
        
        List<Employee> employees = new ArrayList<>();
        
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
        
        // Call method under test
        int result = company.countFullTimeEmployees();
        
        // Verify expected output
        assertEquals("Total full-time employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_countFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // SetUp: Create company with all full-time employees
        company.setName("PizzaPro");
        
        List<Employee> employees = new ArrayList<>();
        
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
        
        // Call method under test
        int result = company.countFullTimeEmployees();
        
        // Verify expected output
        assertEquals("Total full-time employees should be 5", 5, result);
    }
    
    @Test
    public void testCase5_countFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // SetUp: Create company with one of each employee type
        company.setName("TastyBites");
        
        List<Employee> employees = new ArrayList<>();
        
        FullTimeEmployee emp1 = new FullTimeEmployee();
        emp1.setName("Mona");
        employees.add(emp1);
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Noah");
        employees.add(emp2);
        
        company.setEmployees(employees);
        
        // Call method under test
        int result = company.countFullTimeEmployees();
        
        // Verify expected output
        assertEquals("Total full-time employees should be 1", 1, result);
    }
}