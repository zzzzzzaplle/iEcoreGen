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
        // SetUp: Create a Company named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add 3 employees with mixed types
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType("full-time");
        company.addEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType("part-time");
        company.addEmployee(emp2);
        
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType("full-time");
        company.addEmployee(emp3);
        
        // Call Company.countFullTimeEmployees() on "FoodExpress"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees from mixed types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // SetUp: Create a Company named "QuickDeliver" with no employees added
        company.setName("QuickDeliver");
        
        // Call Company.countFullTimeEmployees() on "QuickDeliver"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // SetUp: Create a Company named "SnackNation"
        company.setName("SnackNation");
        
        // Add 4 part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Dave");
        emp1.setType("part-time");
        company.addEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setName("Eva");
        emp2.setType("part-time");
        company.addEmployee(emp2);
        
        Employee emp3 = new Employee();
        emp3.setName("Frank");
        emp3.setType("part-time");
        company.addEmployee(emp3);
        
        Employee emp4 = new Employee();
        emp4.setName("Grace");
        emp4.setType("part-time");
        company.addEmployee(emp4);
        
        // Call Company.countFullTimeEmployees() on "SnackNation"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when only part-time employees exist", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // SetUp: Create a Company named "PizzaPro"
        company.setName("PizzaPro");
        
        // Add 5 full-time employees
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType("full-time");
        company.addEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType("full-time");
        company.addEmployee(emp2);
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType("full-time");
        company.addEmployee(emp3);
        
        Employee emp4 = new Employee();
        emp4.setName("Kate");
        emp4.setType("full-time");
        company.addEmployee(emp4);
        
        Employee emp5 = new Employee();
        emp5.setName("Liam");
        emp5.setType("full-time");
        company.addEmployee(emp5);
        
        // Call Company.countFullTimeEmployees() on "PizzaPro"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count all 5 full-time employees", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // SetUp: Create a Company named "TastyBites"
        company.setName("TastyBites");
        
        // Add 2 employees: one full-time and one part-time
        Employee emp1 = new Employee();
        emp1.setName("Mona");
        emp1.setType("full-time");
        company.addEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setName("Noah");
        emp2.setType("part-time");
        company.addEmployee(emp2);
        
        // Call Company.countFullTimeEmployees() on "TastyBites"
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee from one of each type", 1, result);
    }
}