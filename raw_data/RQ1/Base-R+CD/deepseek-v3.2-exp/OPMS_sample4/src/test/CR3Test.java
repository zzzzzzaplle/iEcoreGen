import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = null;
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // Input: Calculate the number of employees working on production projects in a company
        
        // SetUp:
        // 1. Create a company C001. Create a department with ID: D001 and email: department1@example.com, and add the department to the company C001.
        company = new Company();
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // 2. Add a production project titled "Product Launch" with site code "PL123" to the department.
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // 3. Hire 3 permanent employees named Alice, Bob, and Charlie with employee IDs: E001, E002, and E003, respectively, for the project.
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        alice.setTemporary(false);
        productionProject.addEmployee(alice);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        bob.setTemporary(false);
        productionProject.addEmployee(bob);
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        charlie.setTemporary(false);
        productionProject.addEmployee(charlie);
        
        // 4. Hire 2 temporary employees named David and Eve with employee IDs: E004 and E005, respectively, for the project.
        Employee david = new Employee();
        david.setName("David");
        david.setEmployeeId("E004");
        david.setTemporary(true);
        productionProject.addEmployee(david);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        eve.setTemporary(true);
        productionProject.addEmployee(eve);
        
        // Expected Output: Total number of employees = 5
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 5 employees working on production projects", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Input: Calculate the number of employees working on production projects across multiple departments in a company
        
        // SetUp:
        // 1. Create a company C002. Create two departments: D001 (email: department1@example.com) and D002 (email: department2@example.com), and add the department to the company C002.
        company = new Company();
        
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        // 2. Add a production project titled "Factory Upgrade" with site code "FU456" to D001 and hire 4 permanent employees.
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        // Add 4 permanent employees
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            emp.setTemporary(false);
            project1.addEmployee(emp);
        }
        
        // 3. Add a production project titled "New Product Development" with site code "NPD789" to D002 and hire 3 temporary employees.
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        // Add 3 temporary employees
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            emp.setTemporary(true);
            project2.addEmployee(emp);
        }
        
        // Expected Output: Total number of employees = 7
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 7 employees working on production projects across departments", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects 
        // Input: Calculate the number of employees working on production projects in a department with no production project.
        
        // SetUp:
        // 1. Create a company C003. Create a department with ID: D003 and email: department3@example.com, and add the department to the company C003.
        company = new Company();
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // 2. Add a research project titled "Market Research" (no production project).
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // 3. Hire 2 permanent employees named Frank and Grace with employee IDs: E006 and E007, respectively.
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        frank.setTemporary(false);
        researchProject.addEmployee(frank);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        grace.setTemporary(false);
        researchProject.addEmployee(grace);
        
        // Expected Output: Total number of employees = 0
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 0 employees when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Input: Calculate the number of employees working on production projects in a department with mixed project types.
        
        // SetUp:
        // 1. Create a company C004. Create a department with ID: D004 and email: department4@example.com, and add the department to the company C004.
        company = new Company();
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // 2. Add a production project titled "Process Optimization" with site code "PO101" and hire 2 temporary employees.
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        // Add 2 temporary employees to production project
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setEmployeeId("E011");
        temp1.setTemporary(true);
        productionProject.addEmployee(temp1);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setEmployeeId("E012");
        temp2.setTemporary(true);
        productionProject.addEmployee(temp2);
        
        // 3. Add a community project and an education project (no production project employees).
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Add employees to non-production projects (should not be counted)
        Employee communityEmp = new Employee();
        communityEmp.setName("CommunityEmp");
        communityEmp.setEmployeeId("E013");
        communityEmp.setTemporary(false);
        communityProject.addEmployee(communityEmp);
        
        Employee educationEmp = new Employee();
        educationEmp.setName("EducationEmp");
        educationEmp.setEmployeeId("E014");
        educationEmp.setTemporary(false);
        educationProject.addEmployee(educationEmp);
        
        // Expected Output: Total number of employees = 2
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count only 2 employees from production project", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Input: Calculate the number of employees working on production projects in a department that has no active projects.
        
        // SetUp:
        // 1. Create a company C005. Create a department with ID: D005 and email: department5@example.com, and add the department to the company C005.
        company = new Company();
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // 2. The department has previously hired 3 permanent employees named Henry, Ian, and Jack with employee IDs: E008, E009, and E010, respectively.
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setEmployeeId("E008");
        henry.setTemporary(false);
        department.addEmployee(henry);
        
        Employee ian = new Employee();
        ian.setName("Ian");
        ian.setEmployeeId("E009");
        ian.setTemporary(false);
        department.addEmployee(ian);
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setEmployeeId("E010");
        jack.setTemporary(false);
        department.addEmployee(jack);
        
        // 3. No projects are currently ongoing in this department.
        // (No projects added to department)
        
        // Expected Output: Total number of employees = 0
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 0 employees when department has no projects", 0, result);
    }
}