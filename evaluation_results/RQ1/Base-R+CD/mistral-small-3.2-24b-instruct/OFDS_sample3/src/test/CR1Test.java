import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType(EmployeeType.FULL_TIME);
        employees.add(emp1);
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PART_TIME);
        employees.add(emp2);
        
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.FULL_TIME);
        employees.add(emp3);
        
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount() on "FoodExpress"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Full-time employee count should be 2 when there are 2 full-time and 1 part-time employees", 
                     2, result);
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
        assertEquals("Full-time employee count should be 0 when there are no employees", 
                     0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Input: Count full-time employees in a company with only part-time employees.
        
        // SetUp: Create a Company named "SnackNation"
        company.setName("SnackNation");
        
        // SetUp: Add 4 part-time employees
        List<Employee> employees = new ArrayList<>();
        
        Employee emp1 = new Employee();
        emp1.setName("Dave");
        emp1.setType(EmployeeType.PART_TIME);
        employees.add(emp1);
        
        Employee emp2 = new Employee();
        emp2.setName("Eva");
        emp2.setType(EmployeeType.PART_TIME);
        employees.add(emp2);
        
        Employee emp3 = new Employee();
        emp3.setName("Frank");
        emp3.setType(EmployeeType.PART_TIME);
        employees.add(emp3);
        
        Employee emp4 = new Employee();
        emp4.setName("Grace");
        emp4.setType(EmployeeType.PART_TIME);
        employees.add(emp4);
        
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Full-time employee count should be 0 when all employees are part-time", 
                     0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Input: Count full-time employees in a company with all employees being full-time.
        
        // SetUp: Create a Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // SetUp: Add 5 full-time employees
        List<Employee> employees = new ArrayList<>();
        
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType(EmployeeType.FULL_TIME);
        employees.add(emp1);
        
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType(EmployeeType.FULL_TIME);
        employees.add(emp2);
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType(EmployeeType.FULL_TIME);
        employees.add(emp3);
        
        Employee emp4 = new Employee();
        emp4.setName("Kate");
        emp4.setType(EmployeeType.FULL_TIME);
        employees.add(emp4);
        
        Employee emp5 = new Employee();
        emp5.setName("Liam");
        emp5.setType(EmployeeType.FULL_TIME);
        employees.add(emp5);
        
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Full-time employee count should be 5 when all employees are full-time", 
                     5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Input: Count full-time employees in a company with exactly one of each employee type.
        
        // SetUp: Create a Company named "TastyBites"
        company.setName("TastyBites");
        
        // SetUp: Add 2 employees: one full-time and one part-time
        List<Employee> employees = new ArrayList<>();
        
        Employee emp1 = new Employee();
        emp1.setName("Mona");
        emp1.setType(EmployeeType.FULL_TIME);
        employees.add(emp1);
        
        Employee emp2 = new Employee();
        emp2.setName("Noah");
        emp2.setType(EmployeeType.PART_TIME);
        employees.add(emp2);
        
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Full-time employee count should be 1 when there is one full-time and one part-time employee", 
                     1, result);
    }
}