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
        // Input: Count full-time employees in a company with a mix of employee types.
        
        // SetUp: Create a Company named "FoodExpress"
        company.setName("FoodExpress");
        
        // SetUp: Add 3 employees with mixed types
        List<Employee> employees = new ArrayList<>();
        
        Employee employee1 = new Employee();
        employee1.setName("Alice");
        employee1.setType(EmployeeType.FULL_TIME);
        employees.add(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setType(EmployeeType.PART_TIME);
        employees.add(employee2);
        
        Employee employee3 = new Employee();
        employee3.setName("Charlie");
        employee3.setType(EmployeeType.FULL_TIME);
        employees.add(employee3);
        
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount() on "FoodExpress"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Input: Count full-time employees in a company with no employees.
        
        // SetUp: Create a Company named "QuickDeliver" with no employees added
        company.setName("QuickDeliver");
        company.setEmployees(new ArrayList<Employee>()); // Empty list
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Input: Count full-time employees in a company with only part-time employees.
        
        // SetUp: Create a Company named "SnackNation"
        company.setName("SnackNation");
        
        // SetUp: Add 4 part-time employees
        List<Employee> employees = new ArrayList<>();
        
        Employee employee1 = new Employee();
        employee1.setName("Dave");
        employee1.setType(EmployeeType.PART_TIME);
        employees.add(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Eva");
        employee2.setType(EmployeeType.PART_TIME);
        employees.add(employee2);
        
        Employee employee3 = new Employee();
        employee3.setName("Frank");
        employee3.setType(EmployeeType.PART_TIME);
        employees.add(employee3);
        
        Employee employee4 = new Employee();
        employee4.setName("Grace");
        employee4.setType(EmployeeType.PART_TIME);
        employees.add(employee4);
        
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Input: Count full-time employees in a company with all employees being full-time.
        
        // SetUp: Create a Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // SetUp: Add 5 full-time employees
        List<Employee> employees = new ArrayList<>();
        
        Employee employee1 = new Employee();
        employee1.setName("Henry");
        employee1.setType(EmployeeType.FULL_TIME);
        employees.add(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Isla");
        employee2.setType(EmployeeType.FULL_TIME);
        employees.add(employee2);
        
        Employee employee3 = new Employee();
        employee3.setName("Jack");
        employee3.setType(EmployeeType.FULL_TIME);
        employees.add(employee3);
        
        Employee employee4 = new Employee();
        employee4.setName("Kate");
        employee4.setType(EmployeeType.FULL_TIME);
        employees.add(employee4);
        
        Employee employee5 = new Employee();
        employee5.setName("Liam");
        employee5.setType(EmployeeType.FULL_TIME);
        employees.add(employee5);
        
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Input: Count full-time employees in a company with exactly one of each employee type.
        
        // SetUp: Create a Company named "TastyBites"
        company.setName("TastyBites");
        
        // SetUp: Add 2 employees - one of each type
        List<Employee> employees = new ArrayList<>();
        
        Employee employee1 = new Employee();
        employee1.setName("Mona");
        employee1.setType(EmployeeType.FULL_TIME);
        employees.add(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Noah");
        employee2.setType(EmployeeType.PART_TIME);
        employees.add(employee2);
        
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 1
        assertEquals(1, result);
    }
}