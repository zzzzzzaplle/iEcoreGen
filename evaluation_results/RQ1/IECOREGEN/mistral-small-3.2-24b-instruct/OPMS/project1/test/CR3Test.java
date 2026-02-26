package edu.project.project1.test;

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
        company.getDepartments().add(department);
        
        // Add a production project titled "Product Launch" with site code "PL123"
        ProductionProject project = factory.createProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.getProjects().add(project);
        
        // Hire 3 permanent employees
        Employee alice = factory.createEmployee();
        alice.setName("Alice");
        alice.setID("E001");
        alice.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(alice);
        project.getWorkingEmployees().add(alice);
        
        Employee bob = factory.createEmployee();
        bob.setName("Bob");
        bob.setID("E002");
        bob.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(bob);
        project.getWorkingEmployees().add(bob);
        
        Employee charlie = factory.createEmployee();
        charlie.setName("Charlie");
        charlie.setID("E003");
        charlie.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(charlie);
        project.getWorkingEmployees().add(charlie);
        
        // Hire 2 temporary employees
        Employee david = factory.createEmployee();
        david.setName("David");
        david.setID("E004");
        david.setType(EmployeeType.TEMPORARY);
        department.getEmployees().add(david);
        project.getWorkingEmployees().add(david);
        
        Employee eve = factory.createEmployee();
        eve.setName("Eve");
        eve.setID("E005");
        eve.setType(EmployeeType.TEMPORARY);
        department.getEmployees().add(eve);
        project.getWorkingEmployees().add(eve);
        
        // Calculate and verify total number of employees = 5
        int result = company.countEmployeesInProductionProjects();
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
        company.getDepartments().add(department1);
        
        Department department2 = factory.createDepartment();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        company.getDepartments().add(department2);
        
        // Add production project "Factory Upgrade" to D001 with 4 permanent employees
        ProductionProject project1 = factory.createProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.getProjects().add(project1);
        
        for (int i = 1; i <= 4; i++) {
            Employee emp = factory.createEmployee();
            emp.setName("EmpD1_" + i);
            emp.setID("ED1_" + i);
            emp.setType(EmployeeType.PERMANENT);
            department1.getEmployees().add(emp);
            project1.getWorkingEmployees().add(emp);
        }
        
        // Add production project "New Product Development" to D002 with 3 temporary employees
        ProductionProject project2 = factory.createProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.getProjects().add(project2);
        
        for (int i = 1; i <= 3; i++) {
            Employee emp = factory.createEmployee();
            emp.setName("EmpD2_" + i);
            emp.setID("ED2_" + i);
            emp.setType(EmployeeType.TEMPORARY);
            department2.getEmployees().add(emp);
            project2.getWorkingEmployees().add(emp);
        }
        
        // Calculate and verify total number of employees = 7
        int result = company.countEmployeesInProductionProjects();
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create a company C003
        Company company = factory.createCompany();
        
        // Create a department with ID: D003
        Department department = factory.createDepartment();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.getDepartments().add(department);
        
        // Add a research project (no production project)
        // Note: ResearchProject is not in the provided source, using ProductionProject as placeholder
        // but not adding any employees to it since it's not a production project
        ProductionProject researchProject = factory.createProductionProject();
        researchProject.setTitle("Market Research");
        researchProject.setSiteCode(""); // Empty site code to simulate non-production
        department.getProjects().add(researchProject);
        
        // Hire 2 permanent employees but don't assign them to production projects
        Employee frank = factory.createEmployee();
        frank.setName("Frank");
        frank.setID("E006");
        frank.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(frank);
        // Not adding to workingEmployees of any production project
        
        Employee grace = factory.createEmployee();
        grace.setName("Grace");
        grace.setID("E007");
        grace.setType(EmployeeType.PERMANENT);
        department.getEmployees().add(grace);
        // Not adding to workingEmployees of any production project
        
        // Calculate and verify total number of employees = 0
        int result = company.countEmployeesInProductionProjects();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create a company C004
        Company company = factory.createCompany();
        
        // Create a department with ID: D004
        Department department = factory.createDepartment();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.getDepartments().add(department);
        
        // Add a production project with 2 temporary employees
        ProductionProject productionProject = factory.createProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.getProjects().add(productionProject);
        
        Employee temp1 = factory.createEmployee();
        temp1.setName("Temp1");
        temp1.setID("ET1");
        temp1.setType(EmployeeType.TEMPORARY);
        department.getEmployees().add(temp1);
        productionProject.getWorkingEmployees().add(temp1);
        
        Employee temp2 = factory.createEmployee();
        temp2.setName("Temp2");
        temp2.setID("ET2");
        temp2.setType(EmployeeType.TEMPORARY);
        department.getEmployees().add(temp2);
        productionProject.getWorkingEmployees().add(temp2);
        
        // Add community project (no employees assigned to production)
        ProductionProject communityProject = factory.createProductionProject();
        communityProject.setTitle("Community Outreach");
        communityProject.setSiteCode(""); // Empty site code
        department.getProjects().add(communityProject);
        
        // Add education project (no employees assigned to production)
        ProductionProject educationProject = factory.createProductionProject();
        educationProject.setTitle("Education Program");
        educationProject.setSiteCode(""); // Empty site code
        department.getProjects().add(educationProject);
        
        // Calculate and verify total number of employees = 2
        int result = company.countEmployeesInProductionProjects();
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create a company C005
        Company company = factory.createCompany();
        
        // Create a department with ID: D005
        Department department = factory.createDepartment();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.getDepartments().add(department);
        
        // Hire 3 permanent employees but no projects
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
        
        // Calculate and verify total number of employees = 0
        int result = company.countEmployeesInProductionProjects();
        assertEquals(0, result);
    }
}