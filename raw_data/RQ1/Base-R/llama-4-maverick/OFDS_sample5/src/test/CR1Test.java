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
        // SetUp: Create a Company named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add 3 employees with mixed types
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
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees from mixed employee types", 2, result);
    }
    
    @Test
    public void testCase2_countFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // SetUp: Create a Company named "QuickDeliver" with no employees
        company.setName("QuickDeliver");
        company.setEmployees(new ArrayList<>());
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when there are no employees", 0, result);
    }
    
    @Test
    public void testCase3_countFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // SetUp: Create a Company named "SnackNation"
        company.setName("SnackNation");
        
        // Add 4 part-time employees
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
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when all employees are part-time", 0, result);
    }
    
    @Test
    public void testCase4_countFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // SetUp: Create a Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // Add 5 full-time employees
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
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count all 5 employees when all are full-time", 5, result);
    }
    
    @Test
    public void testCase5_countFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // SetUp: Create a Company named "TastyBites"
        company.setName("TastyBites");
        
        // Add 2 employees: one full-time and one part-time
        List<Employee> employees = new ArrayList<>();
        
        FullTimeEmployee emp1 = new FullTimeEmployee();
        emp1.setName("Mona");
        employees.add(emp1);
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Noah");
        employees.add(emp2);
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee when there is one of each type", 1, result);
    }
}