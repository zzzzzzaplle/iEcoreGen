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
        // SetUp: Create Company named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add 3 employees with mixed types
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
        
        // Call Company.countFullTimeEmployees() on "FoodExpress"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // SetUp: Create Company named "QuickDeliver" with no employees
        company.setName("QuickDeliver");
        company.setEmployees(new ArrayList<>());
        
        // Call Company.countFullTimeEmployees()
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // SetUp: Create Company named "SnackNation"
        company.setName("SnackNation");
        
        // Add 4 part-time employees
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
        
        // Call Company.countFullTimeEmployees()
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // SetUp: Create Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // Add 5 full-time employees
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
        
        // Call Company.countFullTimeEmployees()
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // SetUp: Create Company named "TastyBites"
        company.setName("TastyBites");
        
        // Add 2 employees: one full-time and one part-time
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
        
        // Call Company.countFullTimeEmployees()
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals(1, result);
    }
}