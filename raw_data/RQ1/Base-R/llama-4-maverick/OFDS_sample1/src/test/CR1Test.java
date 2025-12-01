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
        // Test Case 1: Count Full-Time Employees with Mixed Types
        // SetUp: Create Company "FoodExpress" with mixed employee types
        company.setName("FoodExpress");
        
        List<Employee> employees = new ArrayList<>();
        
        // Employee 1: FullTimeEmployee
        FullTimeEmployee alice = new FullTimeEmployee();
        alice.setName("Alice");
        employees.add(alice);
        
        // Employee 2: PartTimeEmployee
        PartTimeEmployee bob = new PartTimeEmployee();
        bob.setName("Bob");
        employees.add(bob);
        
        // Employee 3: FullTimeEmployee
        FullTimeEmployee charlie = new FullTimeEmployee();
        charlie.setName("Charlie");
        employees.add(charlie);
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees from mixed types", 2, result);
    }
    
    @Test
    public void testCase2_countFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // SetUp: Create Company "QuickDeliver" with no employees
        company.setName("QuickDeliver");
        company.setEmployees(new ArrayList<>());
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_countFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // SetUp: Create Company "SnackNation" with only part-time employees
        company.setName("SnackNation");
        
        List<Employee> employees = new ArrayList<>();
        
        // Add 4 part-time employees
        PartTimeEmployee dave = new PartTimeEmployee();
        dave.setName("Dave");
        employees.add(dave);
        
        PartTimeEmployee eva = new PartTimeEmployee();
        eva.setName("Eva");
        employees.add(eva);
        
        PartTimeEmployee frank = new PartTimeEmployee();
        frank.setName("Frank");
        employees.add(frank);
        
        PartTimeEmployee grace = new PartTimeEmployee();
        grace.setName("Grace");
        employees.add(grace);
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when only part-time employees exist", 0, result);
    }
    
    @Test
    public void testCase4_countFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // SetUp: Create Company "PizzaPro" with all full-time employees
        company.setName("PizzaPro");
        
        List<Employee> employees = new ArrayList<>();
        
        // Add 5 full-time employees
        FullTimeEmployee henry = new FullTimeEmployee();
        henry.setName("Henry");
        employees.add(henry);
        
        FullTimeEmployee isla = new FullTimeEmployee();
        isla.setName("Isla");
        employees.add(isla);
        
        FullTimeEmployee jack = new FullTimeEmployee();
        jack.setName("Jack");
        employees.add(jack);
        
        FullTimeEmployee kate = new FullTimeEmployee();
        kate.setName("Kate");
        employees.add(kate);
        
        FullTimeEmployee liam = new FullTimeEmployee();
        liam.setName("Liam");
        employees.add(liam);
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count all 5 full-time employees", 5, result);
    }
    
    @Test
    public void testCase5_countFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // SetUp: Create Company "TastyBites" with one of each employee type
        company.setName("TastyBites");
        
        List<Employee> employees = new ArrayList<>();
        
        // Employee 1: FullTimeEmployee
        FullTimeEmployee mona = new FullTimeEmployee();
        mona.setName("Mona");
        employees.add(mona);
        
        // Employee 2: PartTimeEmployee
        PartTimeEmployee noah = new PartTimeEmployee();
        noah.setName("Noah");
        employees.add(noah);
        
        company.setEmployees(employees);
        
        // Call countFullTimeEmployees() method
        int result = company.countFullTimeEmployees();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee from mixed types", 1, result);
    }
}