import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department department;
    private ProductionProject productionProject;
    private ResearchProject researchProject;
    private CommunityProject communityProject;
    private EducationProject educationProject;
    private Employee employee;
    
    @Before
    public void setUp() {
        company = new Company();
        department = new Department();
        productionProject = new ProductionProject();
        researchProject = new ResearchProject();
        communityProject = new CommunityProject();
        educationProject = new EducationProject();
        employee = new Employee();
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // SetUp: Create a company C001
        Company C001 = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com, and add the department to the company C001
        Department D001 = new Department();
        D001.setID("D001");
        D001.setEmail("department1@example.com");
        C001.addDepartment(D001);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        D001.addProject(project);
        
        // Hire 3 permanent employees named Alice, Bob, and Charlie with employee IDs: E001, E002, and E003, respectively, for the project
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setID("E001");
        alice.setType(EmployeeType.PERMANENT);
        project.addWorkingEmployee(alice);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setID("E002");
        bob.setType(EmployeeType.PERMANENT);
        project.addWorkingEmployee(bob);
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setID("E003");
        charlie.setType(EmployeeType.PERMANENT);
        project.addWorkingEmployee(charlie);
        
        // Hire 2 temporary employees named David and Eve with employee IDs: E004 and E005, respectively, for the project
        Employee david = new Employee();
        david.setName("David");
        david.setID("E004");
        david.setType(EmployeeType.TEMPORARY);
        project.addWorkingEmployee(david);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setID("E005");
        eve.setType(EmployeeType.TEMPORARY);
        project.addWorkingEmployee(eve);
        
        // Calculate the number of employees working on production projects in the company
        int result = C001.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // SetUp: Create a company C002
        Company C002 = new Company();
        
        // Create two departments: D001 (email: department1@example.com) and D002 (email: department2@example.com), and add the department to the company C002
        Department D001 = new Department();
        D001.setID("D001");
        D001.setEmail("department1@example.com");
        C002.addDepartment(D001);
        
        Department D002 = new Department();
        D002.setID("D002");
        D002.setEmail("department2@example.com");
        C002.addDepartment(D002);
        
        // Add a production project titled "Factory Upgrade" with site code "FU456" to D001 and hire 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        D001.addProject(project1);
        
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.PERMANENT);
            project1.addWorkingEmployee(emp);
        }
        
        // Add a production project titled "New Product Development" with site code "NPD789" to D002 and hire 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        D002.addProject(project2);
        
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            project2.addWorkingEmployee(emp);
        }
        
        // Calculate the number of employees working on production projects across multiple departments in the company
        int result = C002.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // SetUp: Create a company C003
        Company C003 = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com, and add the department to the company C003
        Department D003 = new Department();
        D003.setID("D003");
        D003.setEmail("department3@example.com");
        C003.addDepartment(D003);
        
        // Add a research project titled "Market Research" (no production project)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        D003.addProject(project);
        
        // Hire 2 permanent employees named Frank and Grace with employee IDs: E006 and E007, respectively
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setID("E006");
        frank.setType(EmployeeType.PERMANENT);
        project.addWorkingEmployee(frank);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setID("E007");
        grace.setType(EmployeeType.PERMANENT);
        project.addWorkingEmployee(grace);
        
        // Calculate the number of employees working on production projects in a department with no production project
        int result = C003.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // SetUp: Create a company C004
        Company C004 = new Company();
        
        // Create a department with ID: D004 and email: department4@example.com, and add the department to the company C004
        Department D004 = new Department();
        D004.setID("D004");
        D004.setEmail("department4@example.com");
        C004.addDepartment(D004);
        
        // Add a production project titled "Process Optimization" with site code "PO101" and hire 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        D004.addProject(productionProject);
        
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setID("E011");
        temp1.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(temp1);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setID("E012");
        temp2.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(temp2);
        
        // Add a community project and an education project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        D004.addProject(communityProject);
        
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        D004.addProject(educationProject);
        
        // Add employees to non-production projects (should not be counted)
        Employee communityEmp = new Employee();
        communityEmp.setName("CommunityEmp");
        communityEmp.setID("E013");
        communityEmp.setType(EmployeeType.PERMANENT);
        communityProject.addWorkingEmployee(communityEmp);
        
        Employee educationEmp = new Employee();
        educationEmp.setName("EducationEmp");
        educationEmp.setID("E014");
        educationEmp.setType(EmployeeType.PERMANENT);
        educationProject.addWorkingEmployee(educationEmp);
        
        // Calculate the number of employees working on production projects in a department with mixed project types
        int result = C004.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // SetUp: Create a company C005
        Company C005 = new Company();
        
        // Create a department with ID: D005 and email: department5@example.com, and add the department to the company C005
        Department D005 = new Department();
        D005.setID("D005");
        D005.setEmail("department5@example.com");
        C005.addDepartment(D005);
        
        // The department has previously hired 3 permanent employees named Henry, Ian, and Jack with employee IDs: E008, E009, and E010, respectively
        // Note: Since no projects are ongoing, these employees are not assigned to any project
        
        // No projects are currently ongoing in this department
        
        // Calculate the number of employees working on production projects in a department that has no active projects
        int result = C005.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}