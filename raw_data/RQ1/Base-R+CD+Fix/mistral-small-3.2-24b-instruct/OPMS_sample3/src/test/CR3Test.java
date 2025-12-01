import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // Create company C001
        Company company = new Company();
        
        // Create department with ID: D001 and email: department1@example.com
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Add department to company C001
        company.addDepartment(department);
        
        // Add production project titled "Product Launch" with site code "PL123"
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.addProject(project);
        
        // Hire 3 permanent employees for the project
        Employee alice = new Employee();
        alice.setType(EmployeeType.PERMANENT);
        alice.setName("Alice");
        alice.setID("E001");
        
        Employee bob = new Employee();
        bob.setType(EmployeeType.PERMANENT);
        bob.setName("Bob");
        bob.setID("E002");
        
        Employee charlie = new Employee();
        charlie.setType(EmployeeType.PERMANENT);
        charlie.setName("Charlie");
        charlie.setID("E003");
        
        // Hire 2 temporary employees for the project
        Employee david = new Employee();
        david.setType(EmployeeType.TEMPORARY);
        david.setName("David");
        david.setID("E004");
        
        Employee eve = new Employee();
        eve.setType(EmployeeType.TEMPORARY);
        eve.setName("Eve");
        eve.setID("E005");
        
        // Add all employees to the project
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        employees.add(charlie);
        employees.add(david);
        employees.add(eve);
        project.setWorkingEmployees(employees);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Create company C002
        Company company = new Company();
        
        // Create first department D001
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        
        // Create second department D002
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        
        // Add both departments to company C002
        company.addDepartment(department1);
        company.addDepartment(department2);
        
        // Add production project "Factory Upgrade" to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.PERMANENT);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        
        // Add production project "New Product Development" to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // Create company C003
        Company company = new Company();
        
        // Create department with ID: D003 and email: department3@example.com
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Add department to company C003
        company.addDepartment(department);
        
        // Add a research project titled "Market Research" (no production project)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // Hire 2 permanent employees
        Employee frank = new Employee();
        frank.setType(EmployeeType.PERMANENT);
        frank.setName("Frank");
        frank.setID("E006");
        
        Employee grace = new Employee();
        grace.setType(EmployeeType.PERMANENT);
        grace.setName("Grace");
        grace.setID("E007");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(frank);
        employees.add(grace);
        project.setWorkingEmployees(employees);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Create company C004
        Company company = new Company();
        
        // Create department with ID: D004 and email: department4@example.com
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        
        // Add department to company C004
        company.addDepartment(department);
        
        // Add production project "Process Optimization" with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        List<Employee> productionEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setName("TempEmployee" + i);
            emp.setID("TE00" + i);
            productionEmployees.add(emp);
        }
        productionProject.setWorkingEmployees(productionEmployees);
        
        // Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add education project (no production project employees)
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
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Create company C005
        Company company = new Company();
        
        // Create department with ID: D005 and email: department5@example.com
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        
        // Add department to company C005
        company.addDepartment(department);
        
        // The department has previously hired 3 permanent employees
        Employee henry = new Employee();
        henry.setType(EmployeeType.PERMANENT);
        henry.setName("Henry");
        henry.setID("E008");
        
        Employee ian = new Employee();
        ian.setType(EmployeeType.PERMANENT);
        ian.setName("Ian");
        ian.setID("E009");
        
        Employee jack = new Employee();
        jack.setType(EmployeeType.PERMANENT);
        jack.setName("Jack");
        jack.setID("E010");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(henry);
        employees.add(ian);
        employees.add(jack);
        department.setEmployees(employees);
        
        // No projects are currently ongoing in this department
        // (department projects list remains empty)
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}