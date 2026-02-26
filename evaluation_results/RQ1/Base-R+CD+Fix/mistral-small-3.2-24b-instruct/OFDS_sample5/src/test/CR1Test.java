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
        // Setup: Create a Company named "FoodExpress" with mixed employee types
        company.setName("FoodExpress");
        
        List<Employee> employees = new ArrayList<>();
        
        // Employee 1: Alice (FULL_TIME)
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType(EmployeeType.FULL_TIME);
        employees.add(emp1);
        
        // Employee 2: Bob (PART_TIME)
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PART_TIME);
        employees.add(emp2);
        
        // Employee 3: Charlie (FULL_TIME)
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.FULL_TIME);
        employees.add(emp3);
        
        company.setEmployees(employees);
        
        // Call getFullTimeEmployeeCount() and verify result
        int result = company.getFullTimeEmployeeCount();
        assertEquals("Total full-time employees should be 2", 2, result);
    }
    
    @Test
    public void testCase2_CountFullTimeEmployeesWithNoEmployees() {
        // Test Case 2: Count Full-Time Employees with No Employees
        // Setup: Create a Company named "QuickDeliver" with no employees
        company.setName("QuickDeliver");
        
        // No employees added to the company
        company.setEmployees(new ArrayList<Employee>());
        
        // Call getFullTimeEmployeeCount() and verify result
        int result = company.getFullTimeEmployeeCount();
        assertEquals("Total full-time employees should be 0 when no employees exist", 0, result);
    }
    
    @Test
    public void testCase3_CountFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Test Case 3: Count Full-Time Employees with Only Part-Time Employees
        // Setup: Create a Company named "SnackNation" with only part-time employees
        company.setName("SnackNation");
        
        List<Employee> employees = new ArrayList<>();
        
        // Employee 1: Dave (PART_TIME)
        Employee emp1 = new Employee();
        emp1.setName("Dave");
        emp1.setType(EmployeeType.PART_TIME);
        employees.add(emp1);
        
        // Employee 2: Eva (PART_TIME)
        Employee emp2 = new Employee();
        emp2.setName("Eva");
        emp2.setType(EmployeeType.PART_TIME);
        employees.add(emp2);
        
        // Employee 3: Frank (PART_TIME)
        Employee emp3 = new Employee();
        emp3.setName("Frank");
        emp3.setType(EmployeeType.PART_TIME);
        employees.add(emp3);
        
        // Employee 4: Grace (PART_TIME)
        Employee emp4 = new Employee();
        emp4.setName("Grace");
        emp4.setType(EmployeeType.PART_TIME);
        employees.add(emp4);
        
        company.setEmployees(employees);
        
        // Call getFullTimeEmployeeCount() and verify result
        int result = company.getFullTimeEmployeeCount();
        assertEquals("Total full-time employees should be 0 when all employees are part-time", 0, result);
    }
    
    @Test
    public void testCase4_CountFullTimeEmployeesWithSameType() {
        // Test Case 4: Count Full-Time Employees with Same Type
        // Setup: Create a Company named "PizzaPro" with all full-time employees
        company.setName("PizzaPro");
        
        List<Employee> employees = new ArrayList<>();
        
        // Employee 1: Henry (FULL_TIME)
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType(EmployeeType.FULL_TIME);
        employees.add(emp1);
        
        // Employee 2: Isla (FULL_TIME)
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType(EmployeeType.FULL_TIME);
        employees.add(emp2);
        
        // Employee 3: Jack (FULL_TIME)
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType(EmployeeType.FULL_TIME);
        employees.add(emp3);
        
        // Employee 4: Kate (FULL_TIME)
        Employee emp4 = new Employee();
        emp4.setName("Kate");
        emp4.setType(EmployeeType.FULL_TIME);
        employees.add(emp4);
        
        // Employee 5: Liam (FULL_TIME)
        Employee emp5 = new Employee();
        emp5.setName("Liam");
        emp5.setType(EmployeeType.FULL_TIME);
        employees.add(emp5);
        
        company.setEmployees(employees);
        
        // Call getFullTimeEmployeeCount() and verify result
        int result = company.getFullTimeEmployeeCount();
        assertEquals("Total full-time employees should be 5 when all employees are full-time", 5, result);
    }
    
    @Test
    public void testCase5_CountFullTimeEmployeesWithOneOfEachType() {
        // Test Case 5: Count Full-Time Employees with One of Each Type
        // Setup: Create a Company named "TastyBites" with one of each employee type
        company.setName("TastyBites");
        
        List<Employee> employees = new ArrayList<>();
        
        // Employee 1: Mona (FULL_TIME)
        Employee emp1 = new Employee();
        emp1.setName("Mona");
        emp1.setType(EmployeeType.FULL_TIME);
        employees.add(emp1);
        
        // Employee 2: Noah (PART_TIME)
        Employee emp2 = new Employee();
        emp2.setName("Noah");
        emp2.setType(EmployeeType.PART_TIME);
        employees.add(emp2);
        
        company.setEmployees(employees);
        
        // Call getFullTimeEmployeeCount() and verify result
        int result = company.getFullTimeEmployeeCount();
        assertEquals("Total full-time employees should be 1 when there is one full-time and one part-time employee", 1, result);
    }
}