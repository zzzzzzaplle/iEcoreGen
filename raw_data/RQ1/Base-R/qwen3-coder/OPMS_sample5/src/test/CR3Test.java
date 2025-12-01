import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create a company C001
        Company company = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Add the department to the company C001
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        company.setDepartments(departments);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        
        // Hire 3 permanent employees named Alice, Bob, and Charlie
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        
        // Hire 2 temporary employees named David and Eve
        Employee david = new Employee();
        david.setName("David");
        david.setEmployeeId("E004");
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        
        // Add all employees to the production project
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        employees.add(charlie);
        employees.add(david);
        employees.add(eve);
        productionProject.setEmployees(employees);
        
        // Add the production project to the department
        List<Project> projects = new ArrayList<>();
        projects.add(productionProject);
        department.setProjects(projects);
        
        // Calculate total number of employees working on production projects
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create a company C002
        Company company = new Company();
        
        // Create two departments: D001 and D002
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        
        // Add departments to the company C002
        List<Department> departments = new ArrayList<>();
        departments.add(department1);
        departments.add(department2);
        company.setDepartments(departments);
        
        // Add a production project titled "Factory Upgrade" with site code "FU456" to D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        
        // Hire 4 permanent employees for project1
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            employees1.add(emp);
        }
        project1.setEmployees(employees1);
        
        // Add project1 to department1
        List<Project> projects1 = new ArrayList<>();
        projects1.add(project1);
        department1.setProjects(projects1);
        
        // Add a production project titled "New Product Development" with site code "NPD789" to D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        
        // Hire 3 temporary employees for project2
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            employees2.add(emp);
        }
        project2.setEmployees(employees2);
        
        // Add project2 to department2
        List<Project> projects2 = new ArrayList<>();
        projects2.add(project2);
        department2.setProjects(projects2);
        
        // Calculate total number of employees working on production projects
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create a company C003
        Company company = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Add the department to the company C003
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        company.setDepartments(departments);
        
        // Add a research project titled "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        
        // Hire 2 permanent employees named Frank and Grace
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        
        // Add employees to the research project
        List<Employee> employees = new ArrayList<>();
        employees.add(frank);
        employees.add(grace);
        researchProject.setEmployees(employees);
        
        // Add the research project to the department
        List<Project> projects = new ArrayList<>();
        projects.add(researchProject);
        department.setProjects(projects);
        
        // Calculate total number of employees working on production projects
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create a company C004
        Company company = new Company();
        
        // Create a department with ID: D004 and email: department4@example.com
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        
        // Add the department to the company C004
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        company.setDepartments(departments);
        
        // Add a production project titled "Process Optimization" with site code "PO101"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        
        // Hire 2 temporary employees for the production project
        List<Employee> productionEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setName("TempEmployee" + i);
            emp.setEmployeeId("TE00" + i);
            productionEmployees.add(emp);
        }
        productionProject.setEmployees(productionEmployees);
        
        // Add a community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        
        // Add an education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        
        // Add all projects to the department
        List<Project> projects = new ArrayList<>();
        projects.add(productionProject);
        projects.add(communityProject);
        projects.add(educationProject);
        department.setProjects(projects);
        
        // Calculate total number of employees working on production projects
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create a company C005
        Company company = new Company();
        
        // Create a department with ID: D005 and email: department5@example.com
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        
        // The department has previously hired 3 permanent employees
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setEmployeeId("E008");
        
        Employee ian = new Employee();
        ian.setName("Ian");
        ian.setEmployeeId("E009");
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setEmployeeId("E010");
        
        // Add employees to the department (but no projects)
        List<Employee> employees = new ArrayList<>();
        employees.add(henry);
        employees.add(ian);
        employees.add(jack);
        department.setEmployees(employees);
        
        // No projects are currently ongoing in this department
        // (department.projects remains empty)
        
        // Add the department to the company C005
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        company.setDepartments(departments);
        
        // Calculate total number of employees working on production projects
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}