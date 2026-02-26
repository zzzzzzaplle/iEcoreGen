import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        company = new Company();
    }
    
    @Test
    public void testCase1_countEmployeesInSingleDepartmentWithProductionProjects() throws ParseException {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // Create a company C001
        Company company = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com
        Department department = new Department("D001", "department1@example.com");
        company.addDepartment(department);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        Date deadline = dateFormat.parse("2024-12-31 23:59:59");
        ProductionProject productionProject = new ProductionProject("Product Launch", "Launch new product", 50000.0, deadline, "PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees named Alice, Bob, and Charlie with employee IDs: E001, E002, and E003
        Employee alice = new Employee(EmployeeType.PERMANENT, "Alice", "alice@example.com", "E001", "1234567890");
        Employee bob = new Employee(EmployeeType.PERMANENT, "Bob", "bob@example.com", "E002", "1234567891");
        Employee charlie = new Employee(EmployeeType.PERMANENT, "Charlie", "charlie@example.com", "E003", "1234567892");
        
        // Hire 2 temporary employees named David and Eve with employee IDs: E004 and E005
        Employee david = new Employee(EmployeeType.TEMPORARY, "David", "david@example.com", "E004", "1234567893");
        Employee eve = new Employee(EmployeeType.TEMPORARY, "Eve", "eve@example.com", "E005", "1234567894");
        
        // Add all employees to the production project
        productionProject.addWorkingEmployee(alice);
        productionProject.addWorkingEmployee(bob);
        productionProject.addWorkingEmployee(charlie);
        productionProject.addWorkingEmployee(david);
        productionProject.addWorkingEmployee(eve);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals("Should count 5 employees working on production projects in single department", 5, result);
    }
    
    @Test
    public void testCase2_countEmployeesAcrossMultipleDepartments() throws ParseException {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Create a company C002
        Company company = new Company();
        
        // Create two departments: D001 and D002
        Department department1 = new Department("D001", "department1@example.com");
        Department department2 = new Department("D002", "department2@example.com");
        company.addDepartment(department1);
        company.addDepartment(department2);
        
        // Add a production project titled "Factory Upgrade" with site code "FU456" to D001
        Date deadline1 = dateFormat.parse("2024-11-30 23:59:59");
        ProductionProject project1 = new ProductionProject("Factory Upgrade", "Upgrade factory equipment", 75000.0, deadline1, "FU456");
        department1.addProject(project1);
        
        // Hire 4 permanent employees for project1
        for (int i = 1; i <= 4; i++) {
            Employee employee = new Employee(EmployeeType.PERMANENT, "Employee" + i, "emp" + i + "@example.com", "E00" + i, "12345678" + i);
            project1.addWorkingEmployee(employee);
        }
        
        // Add a production project titled "New Product Development" with site code "NPD789" to D002
        Date deadline2 = dateFormat.parse("2024-10-31 23:59:59");
        ProductionProject project2 = new ProductionProject("New Product Development", "Develop new product line", 100000.0, deadline2, "NPD789");
        department2.addProject(project2);
        
        // Hire 3 temporary employees for project2
        for (int i = 5; i <= 7; i++) {
            Employee employee = new Employee(EmployeeType.TEMPORARY, "Employee" + i, "emp" + i + "@example.com", "E00" + i, "12345678" + i);
            project2.addWorkingEmployee(employee);
        }
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals("Should count 7 employees working on production projects across multiple departments", 7, result);
    }
    
    @Test
    public void testCase3_countEmployeesWithNoProductionProjects() throws ParseException {
        // Test Case 3: Count Employees with No Production Projects 
        // Create a company C003
        Company company = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com
        Department department = new Department("D003", "department3@example.com");
        company.addDepartment(department);
        
        // Add a research project titled "Market Research" (no production project)
        Date deadline = dateFormat.parse("2024-09-30 23:59:59");
        ResearchProject researchProject = new ResearchProject("Market Research", "Conduct market analysis", 25000.0, deadline);
        department.addProject(researchProject);
        
        // Hire 2 permanent employees named Frank and Grace
        Employee frank = new Employee(EmployeeType.PERMANENT, "Frank", "frank@example.com", "E006", "1234567895");
        Employee grace = new Employee(EmployeeType.PERMANENT, "Grace", "grace@example.com", "E007", "1234567896");
        
        // Add employees to the research project (but they don't count towards production projects)
        researchProject.addWorkingEmployee(frank);
        researchProject.addWorkingEmployee(grace);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals("Should count 0 employees when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_countEmployeesWithMixedProjectTypes() throws ParseException {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Create a company C004
        Company company = new Company();
        
        // Create a department with ID: D004 and email: department4@example.com
        Department department = new Department("D004", "department4@example.com");
        company.addDepartment(department);
        
        // Add a production project titled "Process Optimization" with site code "PO101"
        Date deadline = dateFormat.parse("2024-08-31 23:59:59");
        ProductionProject productionProject = new ProductionProject("Process Optimization", "Optimize manufacturing processes", 60000.0, deadline, "PO101");
        department.addProject(productionProject);
        
        // Hire 2 temporary employees for the production project
        Employee temp1 = new Employee(EmployeeType.TEMPORARY, "Temp1", "temp1@example.com", "E011", "1234567897");
        Employee temp2 = new Employee(EmployeeType.TEMPORARY, "Temp2", "temp2@example.com", "E012", "1234567898");
        productionProject.addWorkingEmployee(temp1);
        productionProject.addWorkingEmployee(temp2);
        
        // Add a community project (no production project employees)
        FundingGroup communityFunding = new FundingGroup("Community Fund", FundingGroupType.GOVERNMENT);
        CommunityProject communityProject = new CommunityProject("Community Center", "Build community center", 80000.0, deadline, communityFunding);
        department.addProject(communityProject);
        
        // Add an education project (no production project employees)
        FundingGroup educationFunding = new FundingGroup("Education Fund", FundingGroupType.PRIVATE);
        EducationProject educationProject = new EducationProject("STEM Program", "Science education program", 45000.0, deadline, educationFunding);
        department.addProject(educationProject);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals("Should count only employees working on production projects in mixed project types", 2, result);
    }
    
    @Test
    public void testCase5_countEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Create a company C005
        Company company = new Company();
        
        // Create a department with ID: D005 and email: department5@example.com
        Department department = new Department("D005", "department5@example.com");
        company.addDepartment(department);
        
        // The department has previously hired 3 permanent employees
        Employee henry = new Employee(EmployeeType.PERMANENT, "Henry", "henry@example.com", "E008", "1234567899");
        Employee ian = new Employee(EmployeeType.PERMANENT, "Ian", "ian@example.com", "E009", "1234567800");
        Employee jack = new Employee(EmployeeType.PERMANENT, "Jack", "jack@example.com", "E010", "1234567801");
        
        // Add employees to department (but no projects assigned)
        department.addEmployee(henry);
        department.addEmployee(ian);
        department.addEmployee(jack);
        
        // No projects are currently ongoing in this department
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals("Should count 0 employees when department has no projects", 0, result);
    }
}