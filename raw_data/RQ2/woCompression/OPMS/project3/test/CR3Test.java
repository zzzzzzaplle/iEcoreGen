package edu.project.project3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.project.ProjectFactory;
import edu.project.ProjectPackage;
import edu.project.Company;
import edu.project.Department;
import edu.project.ProductionProject;
import edu.project.Employee;
import edu.project.EmployeeType;

public class CR3Test {
    
    private ProjectFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory
        factory = ProjectFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create a company C001
        Company company = factory.createCompany();
        
        // Create a department with ID: D001 and email: department1@example.com
        Department department = factory.createDepartment();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Add the department to the company C001
        company.getDepartments().add(department);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject productionProject = factory.createProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.getProjects().add(productionProject);
        
        // Hire 3 permanent employees named Alice, Bob, and Charlie
        Employee alice = factory.createEmployee();
        alice.setName("Alice");
        alice.setID("E001");
        alice.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(alice);
        productionProject.getWorkingEmployees().add(alice);
        
        Employee bob = factory.createEmployee();
        bob.setName("Bob");
        bob.setID("E002");
        bob.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(bob);
        productionProject.getWorkingEmployees().add(bob);
        
        Employee charlie = factory.createEmployee();
        charlie.setName("Charlie");
        charlie.setID("E003");
        charlie.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(charlie);
        productionProject.getWorkingEmployees().add(charlie);
        
        // Hire 2 temporary employees named David and Eve
        Employee david = factory.createEmployee();
        david.setName("David");
        david.setID("E004");
        david.setType(EmployeeType.TEMPORARY);
        department.getEmployees().add(david);
        productionProject.getWorkingEmployees().add(david);
        
        Employee eve = factory.createEmployee();
        eve.setName("Eve");
        eve.setID("E005");
        eve.setType(EmployeeType.TEMPORARY);
        department.getEmployees().add(eve);
        productionProject.getWorkingEmployees().add(eve);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create a company C002
        Company company = factory.createCompany();
        
        // Create two departments: D001 and D002
        Department department1 = factory.createDepartment();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        
        Department department2 = factory.createDepartment();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        
        // Add departments to the company C002
        company.getDepartments().add(department1);
        company.getDepartments().add(department2);
        
        // Add a production project titled "Factory Upgrade" with site code "FU456" to D001
        ProductionProject project1 = factory.createProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.getProjects().add(project1);
        
        // Hire 4 permanent employees for project1
        for (int i = 1; i <= 4; i++) {
            Employee emp = factory.createEmployee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.PERMANENT);
            department1.getEmployees().add(emp);
            project1.getWorkingEmployees().add(emp);
        }
        
        // Add a production project titled "New Product Development" with site code "NPD789" to D002
        ProductionProject project2 = factory.createProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.getProjects().add(project2);
        
        // Hire 3 temporary employees for project2
        for (int i = 5; i <= 7; i++) {
            Employee emp = factory.createEmployee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            department2.getEmployees().add(emp);
            project2.getWorkingEmployees().add(emp);
        }
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create a company C003
        Company company = factory.createCompany();
        
        // Create a department with ID: D003 and email: department3@example.com
        Department department = factory.createDepartment();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Add the department to the company C003
        company.getDepartments().add(department);
        
        // Add a research project titled "Market Research" (no production project)
        // Note: ResearchProject is not explicitly created in this test, but we're not adding any ProductionProject
        
        // Hire 2 permanent employees named Frank and Grace
        Employee frank = factory.createEmployee();
        frank.setName("Frank");
        frank.setID("E006");
        frank.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(frank);
        
        Employee grace = factory.createEmployee();
        grace.setName("Grace");
        grace.setID("E007");
        grace.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(grace);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create a company C004
        Company company = factory.createCompany();
        
        // Create a department with ID: D004 and email: department4@example.com
        Department department = factory.createDepartment();
        department.setID("D004");
        department.setEmail("department4@example.com");
        
        // Add the department to the company C004
        company.getDepartments().add(department);
        
        // Add a production project titled "Process Optimization" with site code "PO101"
        ProductionProject productionProject = factory.createProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.getProjects().add(productionProject);
        
        // Hire 2 temporary employees for the production project
        Employee temp1 = factory.createEmployee();
        temp1.setName("Temp1");
        temp1.setID("E011");
        temp1.setType(EmployeeType.TEMPORARY);
        department.getEmployees().add(temp1);
        productionProject.getWorkingEmployees().add(temp1);
        
        Employee temp2 = factory.createEmployee();
        temp2.setName("Temp2");
        temp2.setID("E012");
        temp2.setType(EmployeeType.TEMPORARY);
        department.getEmployees().add(temp2);
        productionProject.getWorkingEmployees().add(temp2);
        
        // Add a community project (no production project employees)
        // Add an education project (no production project employees)
        // These are added but don't contribute to the count
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create a company C005
        Company company = factory.createCompany();
        
        // Create a department with ID: D005 and email: department5@example.com
        Department department = factory.createDepartment();
        department.setID("D005");
        department.setEmail("department5@example.com");
        
        // Add the department to the company C005
        company.getDepartments().add(department);
        
        // The department has previously hired 3 permanent employees
        Employee henry = factory.createEmployee();
        henry.setName("Henry");
        henry.setID("E008");
        henry.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(henry);
        
        Employee ian = factory.createEmployee();
        ian.setName("Ian");
        ian.setID("E009");
        ian.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(ian);
        
        Employee jack = factory.createEmployee();
        jack.setName("Jack");
        jack.setID("E010");
        jack.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(jack);
        
        // No projects are currently ongoing in this department
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}