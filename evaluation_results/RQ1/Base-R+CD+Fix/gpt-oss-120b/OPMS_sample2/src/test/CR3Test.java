import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create a company C001
        Company company = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Add the department to the company C001
        company.getDepartments().add(department);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees named Alice, Bob, and Charlie with employee IDs: E001, E002, and E003
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
        
        // Hire 2 temporary employees named David and Eve with employee IDs: E004 and E005
        Employee david = new Employee();
        david.setName("David");
        david.setID("E004");
        david.setType(EmployeeType.TEMPORARY);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setID("E005");
        eve.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to the production project
        productionProject.getWorkingEmployees().add(alice);
        productionProject.getWorkingEmployees().add(bob);
        productionProject.getWorkingEmployees().add(charlie);
        productionProject.getWorkingEmployees().add(david);
        productionProject.getWorkingEmployees().add(eve);
        
        // Calculate total number of employees working on production projects
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
        company.getDepartments().add(department1);
        company.getDepartments().add(department2);
        
        // Add production project "Factory Upgrade" with site code "FU456" to D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        // Hire 4 permanent employees for project1
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.PERMANENT);
            project1.getWorkingEmployees().add(emp);
        }
        
        // Add production project "New Product Development" with site code "NPD789" to D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        // Hire 3 temporary employees for project2
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
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
        Company company = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Add the department to the company C003
        company.getDepartments().add(department);
        
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
        
        // Add employees to the research project
        researchProject.getWorkingEmployees().add(frank);
        researchProject.getWorkingEmployees().add(grace);
        
        // Calculate total number of employees working on production projects
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
        company.getDepartments().add(department);
        
        // Add a production project titled "Process Optimization" with site code "PO101"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        // Hire 2 temporary employees for the production project
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setID("E011");
        temp1.setType(EmployeeType.TEMPORARY);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setID("E012");
        temp2.setType(EmployeeType.TEMPORARY);
        
        productionProject.getWorkingEmployees().add(temp1);
        productionProject.getWorkingEmployees().add(temp2);
        
        // Add a community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add an education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Training Program");
        department.addProject(educationProject);
        
        // Calculate total number of employees working on production projects
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
        company.getDepartments().add(department);
        
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
        
        // Add employees to department's employee list (but no projects)
        department.getEmployees().add(henry);
        department.getEmployees().add(ian);
        department.getEmployees().add(jack);
        
        // No projects are currently ongoing in this department
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}