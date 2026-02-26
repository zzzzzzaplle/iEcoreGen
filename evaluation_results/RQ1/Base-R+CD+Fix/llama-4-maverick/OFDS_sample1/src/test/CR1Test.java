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
        // Set up Company "FoodExpress"
        company.setName("FoodExpress");
        
        // Create and configure employees
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
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        company.setEmployees(employees);
        
        // Call method under test
        int result = company.getFullTimeEmployeeCount();
        
        // Verify expected output
        assertEquals("Should count 2 full-time employees from mixed types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Set up Company "QuickDeliver" with no employees
        company.setName("QuickDeliver");
        company.setEmployees(new ArrayList<>());
        
        // Call method under test
        int result = company.getFullTimeEmployeeCount();
        
        // Verify expected output
        assertEquals("Should return 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Set up Company "SnackNation"
        company.setName("SnackNation");
        
        // Create and configure 4 part-time employees
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
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        company.setEmployees(employees);
        
        // Call method under test
        int result = company.getFullTimeEmployeeCount();
        
        // Verify expected output
        assertEquals("Should return 0 when only part-time employees exist", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Set up Company "PizzaPro"
        company.setName("PizzaPro");
        
        // Create and configure 5 full-time employees
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
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);
        company.setEmployees(employees);
        
        // Call method under test
        int result = company.getFullTimeEmployeeCount();
        
        // Verify expected output
        assertEquals("Should count 5 full-time employees when all are full-time", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Set up Company "TastyBites"
        company.setName("TastyBites");
        
        // Create and configure one of each employee type
        Employee employee1 = new Employee();
        employee1.setName("Mona");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Noah");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        // Call method under test
        int result = company.getFullTimeEmployeeCount();
        
        // Verify expected output
        assertEquals("Should count 1 full-time employee when one full-time and one part-time exist", 1, result);
    }
}