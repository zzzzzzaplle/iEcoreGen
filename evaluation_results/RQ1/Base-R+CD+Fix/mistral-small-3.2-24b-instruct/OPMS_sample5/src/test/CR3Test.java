import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Company company;
    private Department department;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() throws Exception {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // Create company C001
        Company c001 = new Company();
        
        // Create department with ID: D001 and email: department1@example.com
        Department d001 = new Department();
        d001.setID("D001");
        d001.setEmail("department1@example.com");
        c001.addDepartment(d001);
        
        // Add production project titled "Product Launch" with site code "PL123"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        productionProject.setDeadline(dateFormat.parse("2024-12-31 23:59:59"));
        
        // Hire 3 permanent employees
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
        
        // Hire 2 temporary employees
        Employee david = new Employee();
        david.setType(EmployeeType.TEMPORARY);
        david.setName("David");
        david.setID("E004");
        
        Employee eve = new Employee();
        eve.setType(EmployeeType.TEMPORARY);
        eve.setName("Eve");
        eve.setID("E005");
        
        // Add employees to the project
        productionProject.getWorkingEmployees().add(alice);
        productionProject.getWorkingEmployees().add(bob);
        productionProject.getWorkingEmployees().add(charlie);
        productionProject.getWorkingEmployees().add(david);
        productionProject.getWorkingEmployees().add(eve);
        
        // Add project to department
        d001.addProject(productionProject);
        
        // Calculate total number of employees working on production projects
        int result = c001.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals("Should count all 5 employees working on production project", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() throws Exception {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Create company C002
        Company c002 = new Company();
        
        // Create first department D001
        Department d001 = new Department();
        d001.setID("D001");
        d001.setEmail("department1@example.com");
        c002.addDepartment(d001);
        
        // Create second department D002
        Department d002 = new Department();
        d002.setID("D002");
        d002.setEmail("department2@example.com");
        c002.addDepartment(d002);
        
        // Add production project "Factory Upgrade" to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        project1.setDeadline(dateFormat.parse("2024-10-31 23:59:59"));
        
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.PERMANENT);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            project1.getWorkingEmployees().add(emp);
        }
        d001.addProject(project1);
        
        // Add production project "New Product Development" to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        project2.setDeadline(dateFormat.parse("2024-11-30 23:59:59"));
        
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            project2.getWorkingEmployees().add(emp);
        }
        d002.addProject(project2);
        
        // Calculate total number of employees working on production projects
        int result = c002.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals("Should count employees from both departments (4 + 3 = 7)", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() throws Exception {
        // Test Case 3: Count Employees with No Production Projects
        // Create company C003
        Company c003 = new Company();
        
        // Create department D003
        Department d003 = new Department();
        d003.setID("D003");
        d003.setEmail("department3@example.com");
        c003.addDepartment(d003);
        
        // Add research project "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        researchProject.setDeadline(dateFormat.parse("2024-09-30 23:59:59"));
        
        // Hire 2 permanent employees
        Employee frank = new Employee();
        frank.setType(EmployeeType.PERMANENT);
        frank.setName("Frank");
        frank.setID("E006");
        
        Employee grace = new Employee();
        grace.setType(EmployeeType.PERMANENT);
        grace.setName("Grace");
        grace.setID("E007");
        
        researchProject.getWorkingEmployees().add(frank);
        researchProject.getWorkingEmployees().add(grace);
        
        d003.addProject(researchProject);
        
        // Calculate total number of employees working on production projects
        int result = c003.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals("Should return 0 when there are no production projects", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() throws Exception {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Create company C004
        Company c004 = new Company();
        
        // Create department D004
        Department d004 = new Department();
        d004.setID("D004");
        d004.setEmail("department4@example.com");
        c004.addDepartment(d004);
        
        // Add production project "Process Optimization" with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        productionProject.setDeadline(dateFormat.parse("2024-08-31 23:59:59"));
        
        Employee temp1 = new Employee();
        temp1.setType(EmployeeType.TEMPORARY);
        temp1.setName("Temp1");
        temp1.setID("T001");
        
        Employee temp2 = new Employee();
        temp2.setType(EmployeeType.TEMPORARY);
        temp2.setName("Temp2");
        temp2.setID("T002");
        
        productionProject.getWorkingEmployees().add(temp1);
        productionProject.getWorkingEmployees().add(temp2);
        d004.addProject(productionProject);
        
        // Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        communityProject.setDeadline(dateFormat.parse("2024-12-31 23:59:59"));
        d004.addProject(communityProject);
        
        // Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Training Program");
        educationProject.setDeadline(dateFormat.parse("2024-06-30 23:59:59"));
        d004.addProject(educationProject);
        
        // Calculate total number of employees working on production projects
        int result = c004.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals("Should count only employees from production project (2)", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Create company C005
        Company c005 = new Company();
        
        // Create department D005
        Department d005 = new Department();
        d005.setID("D005");
        d005.setEmail("department5@example.com");
        
        // Hire 3 permanent employees (but no projects)
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
        
        // Add employees to department (but not to any project since there are no projects)
        d005.getEmployees().add(henry);
        d005.getEmployees().add(ian);
        d005.getEmployees().add(jack);
        
        // Add department to company
        c005.addDepartment(d005);
        
        // Calculate total number of employees working on production projects
        int result = c005.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals("Should return 0 when department has no projects", 0, result);
    }
}