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
        // Create a Company named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add 3 employees
        Employee alice = new Employee("Alice", EmployeeType.FULL_TIME);
        Employee bob = new Employee("Bob", EmployeeType.PART_TIME);
        Employee charlie = new Employee("Charlie", EmployeeType.FULL_TIME);
        
        company.addEmployee(alice);
        company.addEmployee(bob);
        company.addEmployee(charlie);
        
        // Call Company.getFullTimeEmployeeCount() on "FoodExpress"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_countFullTimeEmployeesWithNoEmployees() {
        // Create a Company named "QuickDeliver" with no employees added
        company.setName("QuickDeliver");
        // No employees added
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_countFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Create a Company named "SnackNation"
        company.setName("SnackNation");
        
        // Add 4 part-time employees
        Employee dave = new Employee("Dave", EmployeeType.PART_TIME);
        Employee eva = new Employee("Eva", EmployeeType.PART_TIME);
        Employee frank = new Employee("Frank", EmployeeType.PART_TIME);
        Employee grace = new Employee("Grace", EmployeeType.PART_TIME);
        
        company.addEmployee(dave);
        company.addEmployee(eva);
        company.addEmployee(frank);
        company.addEmployee(grace);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_countFullTimeEmployeesWithSameType() {
        // Create a Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // Add 5 full-time employees
        Employee henry = new Employee("Henry", EmployeeType.FULL_TIME);
        Employee isla = new Employee("Isla", EmployeeType.FULL_TIME);
        Employee jack = new Employee("Jack", EmployeeType.FULL_TIME);
        Employee kate = new Employee("Kate", EmployeeType.FULL_TIME);
        Employee liam = new Employee("Liam", EmployeeType.FULL_TIME);
        
        company.addEmployee(henry);
        company.addEmployee(isla);
        company.addEmployee(jack);
        company.addEmployee(kate);
        company.addEmployee(liam);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_countFullTimeEmployeesWithOneOfEachType() {
        // Create a Company named "TastyBites"
        company.setName("TastyBites");
        
        // Add 2 employees
        Employee mona = new Employee("Mona", EmployeeType.FULL_TIME);
        Employee noah = new Employee("Noah", EmployeeType.PART_TIME);
        
        company.addEmployee(mona);
        company.addEmployee(noah);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 1
        assertEquals(1, result);
    }
}