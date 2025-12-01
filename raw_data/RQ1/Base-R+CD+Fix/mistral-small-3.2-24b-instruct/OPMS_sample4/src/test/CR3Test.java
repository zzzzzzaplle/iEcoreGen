import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department department;
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create a company C001
        company = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com, and add to company
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees for the project
        List<Employee> employees = new ArrayList<>();
        Employee alice = new Employee();
        alice.setType(EmployeeType.PERMANENT);
        alice.setName("Alice");
        alice.setID("E001");
        employees.add(alice);
        
        Employee bob = new Employee();
        bob.setType(EmployeeType.PERMANENT);
        bob.setName("Bob");
        bob.setID("E002");
        employees.add(bob);
        
        Employee charlie = new Employee();
        charlie.setType(EmployeeType.PERMANENT);
        charlie.setName("Charlie");
        charlie.setID("E003");
        employees.add(charlie);
        
        // Hire 2 temporary employees for the project
        Employee david = new Employee();
        david.setType(EmployeeType.TEMPORARY);
        david.setName("David");
        david.setID("E004");
        employees.add(david);
        
        Employee eve = new Employee();
        eve.setType(EmployeeType.TEMPORARY);
        eve.setName("Eve");
        eve.setID("E005");
        employees.add(eve);
        
        productionProject.setWorkingEmployees(employees);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create a company C002
        company = new Company();
        
        // Create first department D001 and add to company
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        // Add production project "Factory Upgrade" to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.PERMANENT);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        department1.addProject(project1);
        
        // Create second department D002 and add to company
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        // Add production project "New Product Development" to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        department2.addProject(project2);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create a company C003
        company = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com, and add to company
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add a research project titled "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees
        List<Employee> employees = new ArrayList<>();
        Employee frank = new Employee();
        frank.setType(EmployeeType.PERMANENT);
        frank.setName("Frank");
        frank.setID("E006");
        employees.add(frank);
        
        Employee grace = new Employee();
        grace.setType(EmployeeType.PERMANENT);
        grace.setName("Grace");
        grace.setID("E007");
        employees.add(grace);
        
        researchProject.setWorkingEmployees(employees);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create a company C004
        company = new Company();
        
        // Create a department with ID: D004 and email: department4@example.com, and add to company
        department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add a production project titled "Process Optimization" with site code "PO101" and 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        
        List<Employee> productionEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setName("TempEmployee" + i);
            emp.setID("TE00" + i);
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
        educationProject.setTitle("Employee Training");
        department.addProject(educationProject);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create a company C005
        company = new Company();
        
        // Create a department with ID: D005 and email: department5@example.com, and add to company
        department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // The department has previously hired 3 permanent employees but no projects
        List<Employee> departmentEmployees = new ArrayList<>();
        Employee henry = new Employee();
        henry.setType(EmployeeType.PERMANENT);
        henry.setName("Henry");
        henry.setID("E008");
        departmentEmployees.add(henry);
        
        Employee ian = new Employee();
        ian.setType(EmployeeType.PERMANENT);
        ian.setName("Ian");
        ian.setID("E009");
        departmentEmployees.add(ian);
        
        Employee jack = new Employee();
        jack.setType(EmployeeType.PERMANENT);
        jack.setName("Jack");
        jack.setID("E010");
        departmentEmployees.add(jack);
        
        department.setEmployees(departmentEmployees);
        
        // No projects are currently ongoing in this department
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}