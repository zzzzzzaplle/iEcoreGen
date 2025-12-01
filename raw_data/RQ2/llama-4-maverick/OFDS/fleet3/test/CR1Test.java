package edu.fleet.fleet3.test;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.fleet.Company;
import edu.fleet.Employee;
import edu.fleet.EmployeeType;
import edu.fleet.FleetFactory;

public class CR1Test {
    
    private FleetFactory factory;
    
    @Test
    public void testCase1_countFullTimeEmployeesWithMixedTypes() {
        // Create a Company named "FoodExpress"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("FoodExpress");
        
        // Add 3 employees
        Employee employee1 = FleetFactory.eINSTANCE.createEmployee();
        employee1.setName("Alice");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = FleetFactory.eINSTANCE.createEmployee();
        employee2.setName("Bob");
        employee2.setType(EmployeeType.PART_TIME);
        
        Employee employee3 = FleetFactory.eINSTANCE.createEmployee();
        employee3.setName("Charlie");
        employee3.setType(EmployeeType.FULL_TIME);
        
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        
        // Call Company.getFullTimeEmployeeCount() on "FoodExpress"
        int result = company.getFullTimeEmployeeCount();
        
        // Expected Output: Total full-time employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_countFullTimeEmployeesWithNoEmployees() {
        // Create a Company named "QuickDeliver" with no employees added
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("QuickDeliver");
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, company.getFullTimeEmployeeCount());
    }
    
    @Test
    public void testCase3_countFullTimeEmployeesWithOnlyPartTimeEmployees() {
        // Create a Company named "SnackNation"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("SnackNation");
        
        // Add 4 part-time employees
        Employee employee1 = FleetFactory.eINSTANCE.createEmployee();
        employee1.setName("Dave");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = FleetFactory.eINSTANCE.createEmployee();
        employee2.setName("Eva");
        employee2.setType(EmployeeType.PART_TIME);
        
        Employee employee3 = FleetFactory.eINSTANCE.createEmployee();
        employee3.setName("Frank");
        employee3.setType(EmployeeType.PART_TIME);
        
        Employee employee4 = FleetFactory.eINSTANCE.createEmployee();
        employee4.setName("Grace");
        employee4.setType(EmployeeType.PART_TIME);
        
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        company.getEmployees().add(employee4);
        
        // Expected Output: Total full-time employees = 0
        assertEquals(0, company.getFullTimeEmployeeCount());
    }
    
    @Test
    public void testCase4_countFullTimeEmployeesWithSameType() {
        // Create a Company named "PizzaPro"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("PizzaPro");
        
        // Add 5 full-time employees
        Employee employee1 = FleetFactory.eINSTANCE.createEmployee();
        employee1.setName("Henry");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = FleetFactory.eINSTANCE.createEmployee();
        employee2.setName("Isla");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = FleetFactory.eINSTANCE.createEmployee();
        employee3.setName("Jack");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = FleetFactory.eINSTANCE.createEmployee();
        employee4.setName("Kate");
        employee4.setType(EmployeeType.FULL_TIME);
        
        Employee employee5 = FleetFactory.eINSTANCE.createEmployee();
        employee5.setName("Liam");
        employee5.setType(EmployeeType.FULL_TIME);
        
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        company.getEmployees().add(employee4);
        company.getEmployees().add(employee5);
        
        // Expected Output: Total full-time employees = 5
        assertEquals(5, company.getFullTimeEmployeeCount());
    }
    
    @Test
    public void testCase5_countFullTimeEmployeesWithOneOfEachType() {
        // Create a Company named "TastyBites"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("TastyBites");
        
        // Add 2 employees
        Employee employee1 = FleetFactory.eINSTANCE.createEmployee();
        employee1.setName("Mona");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = FleetFactory.eINSTANCE.createEmployee();
        employee2.setName("Noah");
        employee2.setType(EmployeeType.PART_TIME);
        
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        
        // Expected Output: Total full-time employees = 1
        assertEquals(1, company.getFullTimeEmployeeCount());
    }
}