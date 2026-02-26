import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create a company C001
        Company company = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Add the department to the company C001
        company.addDepartment(department);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees named Alice, Bob, and Charlie
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setID("E001");
        alice.setType(EmployeeType.PERMANENT);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setID("E002");
        bob.setType(EmployeeType.PERMANENT);
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setID("E003");
        charlie.setType(EmployeeType.PERMANENT);
        
        // Hire 2 temporary employees named David and Eve
        Employee david = new Employee();
        david.setName("David");
        david.setID("E004");
        david.setType(EmployeeType.TEMPORARY);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setID("E005");
        eve.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to the production project
        List<Employee> workingEmployees = new ArrayList<>();
        workingEmployees.add(alice);
        workingEmployees.add(bob);
        workingEmployees.add(charlie);
        workingEmployees.add(david);
        workingEmployees.add(eve);
        productionProject.setWorkingEmployees(workingEmployees);
        
        // Calculate the number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create a company C002
        Company company = new Company();
        
        // Create two departments: D001 and D002
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        
        // Add departments to the company C002
        company.addDepartment(department1);
        company.addDepartment(department2);
        
        // Add production project "Factory Upgrade" to D001 and hire 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        department1.addProject(project1);
        
        // Add production project "New Product Development" to D002 and hire 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        department2.addProject(project2);
        
        // Calculate the number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create a company C003
        Company company = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Add the department to the company C003
        company.addDepartment(department);
        
        // Add a research project titled "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees named Frank and Grace
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setID("E006");
        frank.setType(EmployeeType.PERMANENT);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setID("E007");
        grace.setType(EmployeeType.PERMANENT);
        
        // Add employees to the research project (NOT a production project)
        List<Employee> workingEmployees = new ArrayList<>();
        workingEmployees.add(frank);
        workingEmployees.add(grace);
        researchProject.setWorkingEmployees(workingEmployees);
        
        // Calculate the number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create a company C004
        Company company = new Company();
        
        // Create a department with ID: D004 and email: department4@example.com
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        
        // Add the department to the company C004
        company.addDepartment(department);
        
        // Add a production project titled "Process Optimization" with site code "PO101" and hire 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        
        List<Employee> productionEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setName("TempEmployee" + i);
            emp.setID("TE00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            productionEmployees.add(emp);
        }
        productionProject.setWorkingEmployees(productionEmployees);
        department.addProject(productionProject);
        
        // Add a community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add an education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Training Program");
        department.addProject(educationProject);
        
        // Calculate the number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create a company C005
        Company company = new Company();
        
        // Create a department with ID: D005 and email: department5@example.com
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        
        // Add the department to the company C005
        company.addDepartment(department);
        
        // The department has previously hired 3 permanent employees
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setID("E008");
        henry.setType(EmployeeType.PERMANENT);
        
        Employee ian = new Employee();
        ian.setName("Ian");
        ian.setID("E009");
        ian.setType(EmployeeType.PERMANENT);
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setID("E010");
        jack.setType(EmployeeType.PERMANENT);
        
        // Employees are in the department but not assigned to any projects
        List<Employee> departmentEmployees = new ArrayList<>();
        departmentEmployees.add(henry);
        departmentEmployees.add(ian);
        departmentEmployees.add(jack);
        department.setEmployees(departmentEmployees);
        
        // No projects are currently ongoing in this department
        
        // Calculate the number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}