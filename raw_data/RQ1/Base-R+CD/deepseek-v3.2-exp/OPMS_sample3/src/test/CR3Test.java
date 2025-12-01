import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // SetUp: Create company C001, department D001, add production project with 5 employees
        
        // Step 1: Create company and department
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Step 2: Add production project
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Step 3: Hire 3 permanent employees
        PermanentEmployee alice = new PermanentEmployee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        productionProject.addWorkingEmployee(alice);
        
        PermanentEmployee bob = new PermanentEmployee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        productionProject.addWorkingEmployee(bob);
        
        PermanentEmployee charlie = new PermanentEmployee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        productionProject.addWorkingEmployee(charlie);
        
        // Step 4: Hire 2 temporary employees
        TemporaryEmployee david = new TemporaryEmployee();
        david.setName("David");
        david.setEmployeeId("E004");
        productionProject.addWorkingEmployee(david);
        
        TemporaryEmployee eve = new TemporaryEmployee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        productionProject.addWorkingEmployee(eve);
        
        // Expected Output: Total number of employees = 5
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 5 employees working on production project", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // SetUp: Create company C002, two departments with production projects and employees
        
        // Step 1: Create company and two departments
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        // Step 2: Add production project to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        for (int i = 1; i <= 4; i++) {
            PermanentEmployee emp = new PermanentEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("PE00" + i);
            project1.addWorkingEmployee(emp);
        }
        
        // Step 3: Add production project to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        for (int i = 1; i <= 3; i++) {
            TemporaryEmployee emp = new TemporaryEmployee();
            emp.setName("TempEmployee" + i);
            emp.setEmployeeId("TE00" + i);
            project2.addWorkingEmployee(emp);
        }
        
        // Expected Output: Total number of employees = 7
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 7 employees across multiple departments", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // SetUp: Create company C003, department with research project and employees
        
        // Step 1: Create company and department
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Step 2: Add research project (non-production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Step 3: Hire 2 permanent employees
        PermanentEmployee frank = new PermanentEmployee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        researchProject.addWorkingEmployee(frank);
        
        PermanentEmployee grace = new PermanentEmployee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        researchProject.addWorkingEmployee(grace);
        
        // Expected Output: Total number of employees = 0
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 0 employees when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // SetUp: Create company C004, department with mixed project types
        
        // Step 1: Create company and department
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Step 2: Add production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        TemporaryEmployee temp1 = new TemporaryEmployee();
        temp1.setName("Temp1");
        temp1.setEmployeeId("TE101");
        productionProject.addWorkingEmployee(temp1);
        
        TemporaryEmployee temp2 = new TemporaryEmployee();
        temp2.setName("Temp2");
        temp2.setEmployeeId("TE102");
        productionProject.addWorkingEmployee(temp2);
        
        // Step 3: Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Step 3: Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Expected Output: Total number of employees = 2
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count only employees from production projects", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // SetUp: Create company C005, department with employees but no projects
        
        // Step 1: Create company and department
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Step 2: Hire 3 permanent employees (but no projects)
        PermanentEmployee henry = new PermanentEmployee();
        henry.setName("Henry");
        henry.setEmployeeId("E008");
        department.addEmployee(henry);
        
        PermanentEmployee ian = new PermanentEmployee();
        ian.setName("Ian");
        ian.setEmployeeId("E009");
        department.addEmployee(ian);
        
        PermanentEmployee jack = new PermanentEmployee();
        jack.setName("Jack");
        jack.setEmployeeId("E010");
        department.addEmployee(jack);
        
        // Step 3: No projects are currently ongoing in this department
        
        // Expected Output: Total number of employees = 0
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 0 employees when department has no projects", 0, result);
    }
}