package edu.fleet.fleet2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.fleet.Company;
import edu.fleet.Employee;
import edu.fleet.EmployeeType;
import edu.fleet.FleetFactory;
import edu.fleet.FleetPackage;

public class CR1Test {
    
    private FleetFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = FleetFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_CountFullTimeEmployeesWithMixedTypes() {
        // Test Case 1: Count Full-Time Employees with Mixed Types
        // Input: Count full-time employees in a company with a mix of employee types.
        
        // SetUp:
        // 1. Create a Company named "FoodExpress"
        Company company = factory.createCompany();
        company.setName("FoodExpress");
        
        // 2. Add 3 employees with mixed types
        Employee employee1 = factory.createEmployee();
        employee1.setName("Alice");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Bob");
        employee2.setType(EmployeeType.PART_TIME);
        
        Employee employee3 = factory.createEmployee();
        employee3.setName("Charlie");
        employee3.setType(EmployeeType.FULL_TIME);
        
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        
        // 3. Call Company.getFullTimeEmployeeCount() on "FoodExpress"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 2
        assertEquals("Should count 2 full-time employees from mixed types", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Input: Count full-time employees in a company with no employees.
        
        // SetUp:
        // 1. Create a Company named "QuickDeliver" with no employees added
        Company company = factory.createCompany();
        company.setName("QuickDeliver");
        // No employees added - using default empty list
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Input: Count full-time employees in a company with only part-time employees.
        
        // SetUp:
        // 1. Create a Company named "SnackNation"
        Company company = factory.createCompany();
        company.setName("SnackNation");
        
        // 2. Add 4 part-time employees
        Employee employee1 = factory.createEmployee();
        employee1.setName("Dave");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Eva");
        employee2.setType(EmployeeType.PART_TIME);
        
        Employee employee3 = factory.createEmployee();
        employee3.setName("Frank");
        employee3.setType(EmployeeType.PART_TIME);
        
        Employee employee4 = factory.createEmployee();
        employee4.setName("Grace");
        employee4.setType(EmployeeType.PART_TIME);
        
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        company.getEmployees().add(employee4);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 0
        assertEquals("Should return 0 when only part-time employees exist", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Input: Count full-time employees in a company with all employees being full-time.
        
        // SetUp:
        // 1. Create a Company named "PizzaPro"
        Company company = factory.createCompany();
        company.setName("PizzaPro");
        
        // 2. Add 5 full-time employees
        Employee employee1 = factory.createEmployee();
        employee1.setName("Henry");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Isla");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = factory.createEmployee();
        employee3.setName("Jack");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = factory.createEmployee();
        employee4.setName("Kate");
        employee4.setType(EmployeeType.FULL_TIME);
        
        Employee employee5 = factory.createEmployee();
        employee5.setName("Liam");
        employee5.setType(EmployeeType.FULL_TIME);
        
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        company.getEmployees().add(employee4);
        company.getEmployees().add(employee5);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 5
        assertEquals("Should count all 5 full-time employees", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Input: Count full-time employees in a company with exactly one of each employee type.
        
        // SetUp:
        // 1. Create a Company named "TastyBites"
        Company company = factory.createCompany();
        company.setName("TastyBites");
        
        // 2. Add 2 employees: one full-time and one part-time
        Employee employee1 = factory.createEmployee();
        employee1.setName("Mona");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Noah");
        employee2.setType(EmployeeType.PART_TIME);
        
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        
        // Call Company.getFullTimeEmployeeCount()
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 1
        assertEquals("Should count 1 full-time employee from mixed types", 1, result);
    }
}