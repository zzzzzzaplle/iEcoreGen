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
        // SetUp: Create Company "FoodExpress" with mixed employee types
        company.setName("FoodExpress");
        
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType(EmployeeType.FULL_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PART_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.FULL_TIME);
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        // Call Company.countFullTimeEmployees() on "FoodExpress"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees from mixed types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // SetUp: Create Company "QuickDeliver" with no employees
        company.setName("QuickDeliver");
        
        // Call Company.countFullTimeEmployees() on "QuickDeliver"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when company has no employees", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // SetUp: Create Company "SnackNation" with only part-time employees
        company.setName("SnackNation");
        
        Employee emp1 = new Employee();
        emp1.setName("Dave");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Eva");
        emp2.setType(EmployeeType.PART_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("Frank");
        emp3.setType(EmployeeType.PART_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Grace");
        emp4.setType(EmployeeType.PART_TIME);
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        // Call Company.countFullTimeEmployees() on "SnackNation"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when all employees are part-time", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // SetUp: Create Company "PizzaPro" with all full-time employees
        company.setName("PizzaPro");
        
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType(EmployeeType.FULL_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType(EmployeeType.FULL_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType(EmployeeType.FULL_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Kate");
        emp4.setType(EmployeeType.FULL_TIME);
        
        Employee emp5 = new Employee();
        emp5.setName("Liam");
        emp5.setType(EmployeeType.FULL_TIME);
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        company.addEmployee(emp5);
        
        // Call Company.countFullTimeEmployees() on "PizzaPro"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count all 5 full-time employees", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // SetUp: Create Company "TastyBites" with one of each employee type
        company.setName("TastyBites");
        
        Employee emp1 = new Employee();
        emp1.setName("Mona");
        emp1.setType(EmployeeType.FULL_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Noah");
        emp2.setType(EmployeeType.PART_TIME);
        
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        
        // Call Company.countFullTimeEmployees() on "TastyBites"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee from mixed types", 1, result);
    }
}