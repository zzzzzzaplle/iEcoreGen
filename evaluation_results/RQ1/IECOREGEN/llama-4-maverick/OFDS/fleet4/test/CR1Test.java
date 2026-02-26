package edu.fleet.fleet4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.fleet.Company;
import edu.fleet.Employee;
import edu.fleet.EmployeeType;
import edu.fleet.FleetFactory;

public class CR1Test {
    
    private FleetFactory factory;
    
    @Before
    public void setUp() {
        factory = FleetFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_countFullTimeEmployeesWithMixedTypes() {
        // Create a Company named "FoodExpress"
        Company company = factory.createCompany();
        company.setName("FoodExpress");
        
        // Add 3 employees
        Employee alice = factory.createEmployee();
        alice.setName("Alice");
        alice.setType(EmployeeType.FULL_TIME);
        
        Employee bob = factory.createEmployee();
        bob.setName("Bob");
        bob.setType(EmployeeType.PART_TIME);
        
        Employee charlie = factory.createEmployee();
        charlie.setName("Charlie");
        charlie.setType(EmployeeType.FULL_TIME);
        
        company.getEmployees().add(alice);
        company.getEmployees().add(bob);
        company.getEmployees().add(charlie);
        
        // Call Company.getFullTimeEmployeeCount() on "FoodExpress"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_countFullTimeEmployeesWithNoEmployees() {
        // Create a Company named "QuickDeliver" with no employees added
        Company company = factory.createCompany();
        company.setName("QuickDeliver");
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, company.getFullTimeEmployeeCount());
    }
    
    @Test
    public void testCase3_countFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Create a Company named "SnackNation"
        Company company = factory.createCompany();
        company.setName("SnackNation");
        
        // Add 4 part-time employees
        Employee dave = factory.createEmployee();
        dave.setName("Dave");
        dave.setType(EmployeeType.PART_TIME);
        
        Employee eva = factory.createEmployee();
        eva.setName("Eva");
        eva.setType(EmployeeType.PART_TIME);
        
        Employee frank = factory.createEmployee();
        frank.setName("Frank");
        frank.setType(EmployeeType.PART_TIME);
        
        Employee grace = factory.createEmployee();
        grace.setName("Grace");
        grace.setType(EmployeeType.PART_TIME);
        
        company.getEmployees().add(dave);
        company.getEmployees().add(eva);
        company.getEmployees().add(frank);
        company.getEmployees().add(grace);
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, company.getFullTimeEmployeeCount());
    }
    
    @Test
    public void testCase4_countFullTimeEmployeesWithSameType() {
        // Create a Company named "PizzaPro"
        Company company = factory.createCompany();
        company.setName("PizzaPro");
        
        // Add 5 full-time employees
        Employee henry = factory.createEmployee();
        henry.setName("Henry");
        henry.setType(EmployeeType.FULL_TIME);
        
        Employee isla = factory.createEmployee();
        isla.setName("Isla");
        isla.setType(EmployeeType.FULL_TIME);
        
        Employee jack = factory.createEmployee();
        jack.setName("Jack");
        jack.setType(EmployeeType.FULL_TIME);
        
        Employee kate = factory.createEmployee();
        kate.setName("Kate");
        kate.setType(EmployeeType.FULL_TIME);
        
        Employee liam = factory.createEmployee();
        liam.setName("Liam");
        liam.setType(EmployeeType.FULL_TIME);
        
        company.getEmployees().add(henry);
        company.getEmployees().add(isla);
        company.getEmployees().add(jack);
        company.getEmployees().add(kate);
        company.getEmployees().add(liam);
        
        // Expected Output: Total full-time employees = 5
        assertEquals(5, company.getFullTimeEmployeeCount());
    }
    
    @Test
    public void testCase5_countFullTimeEmployeesWithOneOfEachType() {
        // Create a Company named "TastyBites"
        Company company = factory.createCompany();
        company.setName("TastyBites");
        
        // Add 2 employees
        Employee mona = factory.createEmployee();
        mona.setName("Mona");
        mona.setType(EmployeeType.FULL_TIME);
        
        Employee noah = factory.createEmployee();
        noah.setName("Noah");
        noah.setType(EmployeeType.PART_TIME);
        
        company.getEmployees().add(mona);
        company.getEmployees().add(noah);
        
        // Expected Output: Total full-time employees = 1
        assertEquals(1, company.getFullTimeEmployeeCount());
    }
}