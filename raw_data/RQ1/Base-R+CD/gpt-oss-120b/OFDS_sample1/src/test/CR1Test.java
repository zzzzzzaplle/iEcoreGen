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
        // SetUp: Create a Company named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add 3 employees
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setType(EmployeeType.FULL_TIME);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setType(EmployeeType.PART_TIME);
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setType(EmployeeType.FULL_TIME);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        employees.add(charlie);
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount() on "FoodExpress"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_countFullTimeEmployeesWithNoEmployees() {
        // SetUp: Create a Company named "QuickDeliver" with no employees added
        company.setName("QuickDeliver");
        company.setEmployees(new ArrayList<>());
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_countFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // SetUp: Create a Company named "SnackNation"
        company.setName("SnackNation");
        
        // Add 4 part-time employees
        Employee dave = new Employee();
        dave.setName("Dave");
        dave.setType(EmployeeType.PART_TIME);
        
        Employee eva = new Employee();
        eva.setName("Eva");
        eva.setType(EmployeeType.PART_TIME);
        
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setType(EmployeeType.PART_TIME);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setType(EmployeeType.PART_TIME);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(dave);
        employees.add(eva);
        employees.add(frank);
        employees.add(grace);
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_countFullTimeEmployeesWithSameType() {
        // SetUp: Create a Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // Add 5 full-time employees
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setType(EmployeeType.FULL_TIME);
        
        Employee isla = new Employee();
        isla.setName("Isla");
        isla.setType(EmployeeType.FULL_TIME);
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setType(EmployeeType.FULL_TIME);
        
        Employee kate = new Employee();
        kate.setName("Kate");
        kate.setType(EmployeeType.FULL_TIME);
        
        Employee liam = new Employee();
        liam.setName("Liam");
        liam.setType(EmployeeType.FULL_TIME);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(henry);
        employees.add(isla);
        employees.add(jack);
        employees.add(kate);
        employees.add(liam);
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase5_countFullTimeEmployeesWithOneOfEachType() {
        // SetUp: Create a Company named "TastyBites"
        company.setName("TastyBites");
        
        // Add 2 employees
        Employee mona = new Employee();
        mona.setName("Mona");
        mona.setType(EmployeeType.FULL_TIME);
        
        Employee noah = new Employee();
        noah.setName("Noah");
        noah.setType(EmployeeType.PART_TIME);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(mona);
        employees.add(noah);
        company.setEmployees(employees);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 1
        assertEquals(1, result);
    }
}