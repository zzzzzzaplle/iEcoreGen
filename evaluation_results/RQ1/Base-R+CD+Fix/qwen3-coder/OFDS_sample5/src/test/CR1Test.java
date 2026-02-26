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
        // SetUp: Create a Company named "FoodExpress" with mixed employee types
        company.setName("FoodExpress");
        
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
        assertEquals("Full-time employee count should be 2 with mixed types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // SetUp: Create a Company named "QuickDeliver" with no employees
        company.setName("QuickDeliver");
        
        // Company starts with empty employee list by default, no need to set
        
        // Call Company.getFullTimeEmployeeCount() on "QuickDeliver"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Full-time employee count should be 0 with no employees", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // SetUp: Create a Company named "SnackNation" with only part-time employees
        company.setName("SnackNation");
        
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
        
        // Call Company.getFullTimeEmployeeCount() on "SnackNation"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Full-time employee count should be 0 with only part-time employees", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // SetUp: Create a Company named "PizzaPro" with all full-time employees
        company.setName("PizzaPro");
        
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
        
        // Call Company.getFullTimeEmployeeCount() on "PizzaPro"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Full-time employee count should be 5 with all full-time employees", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // SetUp: Create a Company named "TastyBites" with one of each employee type
        company.setName("TastyBites");
        
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
        
        // Call Company.getFullTimeEmployeeCount() on "TastyBites"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Full-time employee count should be 1 with one of each type", 1, result);
    }
}