import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        
        // SetUp:
        // 1. Create a Company named "FoodExpress"
        company.setName("FoodExpress");
        
        // 2. Add 3 employees:
        //    - Employee 1: name="Alice", EmployeeType=FULL_TIME
        FullTimeEmployee alice = new FullTimeEmployee();
        alice.setName("Alice");
        company.addEmployee(alice);
        
        //    - Employee 2: name="Bob", EmployeeType=PART_TIME
        PartTimeEmployee bob = new PartTimeEmployee();
        bob.setName("Bob");
        company.addEmployee(bob);
        
        //    - Employee 3: name="Charlie", EmployeeType=FULL_TIME
        FullTimeEmployee charlie = new FullTimeEmployee();
        charlie.setName("Charlie");
        company.addEmployee(charlie);
        
        // 3. Call Company.countFullTimeEmployees() on "FoodExpress"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees with mixed types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Input: Count full-time employees in a company with no employees.
        
        // SetUp:
        // 1. Create a Company named "QuickDeliver" with no employees added
        company.setName("QuickDeliver");
        // No employees added to the company
        
        // Call Company.countFullTimeEmployees() on "QuickDeliver"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when no employees are present", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Input: Count full-time employees in a company with only part-time employees.
        
        // SetUp:
        // 1. Create a Company named "SnackNation"
        company.setName("SnackNation");
        
        // 2. Add 4 part-time employees:
        //    - Employee 1: name="Dave", EmployeeType=PART_TIME
        PartTimeEmployee dave = new PartTimeEmployee();
        dave.setName("Dave");
        company.addEmployee(dave);
        
        //    - Employee 2: name="Eva", EmployeeType=PART_TIME
        PartTimeEmployee eva = new PartTimeEmployee();
        eva.setName("Eva");
        company.addEmployee(eva);
        
        //    - Employee 3: name="Frank", EmployeeType=PART_TIME
        PartTimeEmployee frank = new PartTimeEmployee();
        frank.setName("Frank");
        company.addEmployee(frank);
        
        //    - Employee 4: name="Grace", EmployeeType=PART_TIME
        PartTimeEmployee grace = new PartTimeEmployee();
        grace.setName("Grace");
        company.addEmployee(grace);
        
        // Call Company.countFullTimeEmployees() on "SnackNation"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when only part-time employees are present", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Input: Count full-time employees in a company with all employees being full-time.
        
        // SetUp:
        // 1. Create a Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // 2. Add 5 full-time employees:
        //    - Employee 1: name="Henry", EmployeeType=FULL_TIME
        FullTimeEmployee henry = new FullTimeEmployee();
        henry.setName("Henry");
        company.addEmployee(henry);
        
        //    - Employee 2: name="Isla", EmployeeType=FULL_TIME
        FullTimeEmployee isla = new FullTimeEmployee();
        isla.setName("Isla");
        company.addEmployee(isla);
        
        //    - Employee 3: name="Jack", EmployeeType=FULL_TIME
        FullTimeEmployee jack = new FullTimeEmployee();
        jack.setName("Jack");
        company.addEmployee(jack);
        
        //    - Employee 4: name="Kate", EmployeeType=FULL_TIME
        FullTimeEmployee kate = new FullTimeEmployee();
        kate.setName("Kate");
        company.addEmployee(kate);
        
        //    - Employee 5: name="Liam", EmployeeType=FULL_TIME
        FullTimeEmployee liam = new FullTimeEmployee();
        liam.setName("Liam");
        company.addEmployee(liam);
        
        // Call Company.countFullTimeEmployees() on "PizzaPro"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count all 5 full-time employees when all are full-time", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Input: Count full-time employees in a company with exactly one of each employee type.
        
        // SetUp:
        // 1. Create a Company named "TastyBites"
        company.setName("TastyBites");
        
        // 2. Add 2 employees:
        //    - Employee 1: name="Mona", EmployeeType=FULL_TIME
        FullTimeEmployee mona = new FullTimeEmployee();
        mona.setName("Mona");
        company.addEmployee(mona);
        
        //    - Employee 2: name="Noah", EmployeeType=PART_TIME
        PartTimeEmployee noah = new PartTimeEmployee();
        noah.setName("Noah");
        company.addEmployee(noah);
        
        // Call Company.countFullTimeEmployees() on "TastyBites"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee when mixed with one part-time", 1, result);
    }
}