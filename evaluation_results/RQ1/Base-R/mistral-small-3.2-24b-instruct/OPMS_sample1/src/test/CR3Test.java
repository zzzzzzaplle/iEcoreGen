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
        
        // 1. Create a company C001. Create a department with ID: D001 and email: department1@example.com
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // 2. Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.addProject(project);
        
        // 3. Hire 3 permanent employees named Alice, Bob, and Charlie
        PermanentEmployee emp1 = new PermanentEmployee();
        emp1.setName("Alice");
        emp1.setEmployeeId("E001");
        
        PermanentEmployee emp2 = new PermanentEmployee();
        emp2.setName("Bob");
        emp2.setEmployeeId("E002");
        
        PermanentEmployee emp3 = new PermanentEmployee();
        emp3.setName("Charlie");
        emp3.setEmployeeId("E003");
        
        // 4. Hire 2 temporary employees named David and Eve
        TemporaryEmployee emp4 = new TemporaryEmployee();
        emp4.setName("David");
        emp4.setEmployeeId("E004");
        
        TemporaryEmployee emp5 = new TemporaryEmployee();
        emp5.setName("Eve");
        emp5.setEmployeeId("E005");
        
        // Add all employees to the project
        project.addEmployee(emp1);
        project.addEmployee(emp2);
        project.addEmployee(emp3);
        project.addEmployee(emp4);
        project.addEmployee(emp5);
        
        // Expected Output: Total number of employees = 5
        int result = company.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // SetUp: Create company C002, two departments with production projects and employees
        
        // 1. Create a company C002. Create two departments: D001 and D002
        Department dept1 = new Department();
        dept1.setId("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        Department dept2 = new Department();
        dept2.setId("D002");
        dept2.setEmail("department2@example.com");
        company.addDepartment(dept2);
        
        // 2. Add a production project titled "Factory Upgrade" with site code "FU456" to D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.addProject(project1);
        
        // Hire 4 permanent employees for project1
        for (int i = 1; i <= 4; i++) {
            PermanentEmployee emp = new PermanentEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project1.addEmployee(emp);
        }
        
        // Add a production project titled "New Product Development" with site code "NPD789" to D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.addProject(project2);
        
        // Hire 3 temporary employees for project2
        for (int i = 5; i <= 7; i++) {
            TemporaryEmployee emp = new TemporaryEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project2.addEmployee(emp);
        }
        
        // Expected Output: Total number of employees = 7
        int result = company.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // SetUp: Create company C003, department with research project and employees
        
        // 1. Create a company C003. Create a department with ID: D003
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // 2. Add a research project titled "Market Research" (no production project)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // 3. Hire 2 permanent employees named Frank and Grace
        PermanentEmployee emp1 = new PermanentEmployee();
        emp1.setName("Frank");
        emp1.setEmployeeId("E006");
        
        PermanentEmployee emp2 = new PermanentEmployee();
        emp2.setName("Grace");
        emp2.setEmployeeId("E007");
        
        project.addEmployee(emp1);
        project.addEmployee(emp2);
        
        // Expected Output: Total number of employees = 0
        int result = company.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 0 when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // SetUp: Create company C004, department with mixed project types
        
        // 1. Create a company C004. Create a department with ID: D004
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // 2. Add a production project titled "Process Optimization" with site code "PO101"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        // Hire 2 temporary employees for the production project
        TemporaryEmployee emp1 = new TemporaryEmployee();
        emp1.setName("Temp1");
        emp1.setEmployeeId("E011");
        
        TemporaryEmployee emp2 = new TemporaryEmployee();
        emp2.setName("Temp2");
        emp2.setEmployeeId("E012");
        
        productionProject.addEmployee(emp1);
        productionProject.addEmployee(emp2);
        
        // Add a community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add an education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Expected Output: Total number of employees = 2
        int result = company.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 2 (only from production project)", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // SetUp: Create company C005, department with employees but no projects
        
        // 1. Create a company C005. Create a department with ID: D005
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // 2. The department has previously hired 3 permanent employees
        // Note: Since there are no projects, employees cannot be assigned to any project
        // The employees exist but are not assigned to any production project
        
        // 3. No projects are currently ongoing in this department
        // Department has empty project list by default
        
        // Expected Output: Total number of employees = 0
        int result = company.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 0 when no projects exist", 0, result);
    }
}