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
        company = new Company();
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // Input: Calculate the number of employees working on production projects in a company
        
        // SetUp:
        // 1. Create a company C001. Create a department with ID: D001 and email: department1@example.com, and add the department to the company C001.
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.getDepartments().add(department);
        
        // 2. Add a production project titled "Product Launch" with site code "PL123" to the department.
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.getProjects().add(productionProject);
        
        // 3. Hire 3 permanent employees named Alice, Bob, and Charlie with employee IDs: E001, E002, and E003, respectively, for the project.
        Employee employee1 = new Employee();
        employee1.setName("Alice");
        employee1.setEmployeeId("E001");
        productionProject.getEmployees().add(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setEmployeeId("E002");
        productionProject.getEmployees().add(employee2);
        
        Employee employee3 = new Employee();
        employee3.setName("Charlie");
        employee3.setEmployeeId("E003");
        productionProject.getEmployees().add(employee3);
        
        // 4. Hire 2 temporary employees named David and Eve with employee IDs: E004 and E005, respectively, for the project.
        Employee employee4 = new Employee();
        employee4.setName("David");
        employee4.setEmployeeId("E004");
        productionProject.getEmployees().add(employee4);
        
        Employee employee5 = new Employee();
        employee5.setName("Eve");
        employee5.setEmployeeId("E005");
        productionProject.getEmployees().add(employee5);
        
        // Expected Output: Total number of employees = 5
        int result = company.getNumberOfEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Input: Calculate the number of employees working on production projects across multiple departments in a company
        
        // SetUp:
        // 1. Create a company C002. Create two departments: D001 (email: department1@example.com) and D002 (email: department2@example.com), and add the department to the company C002.
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        company.getDepartments().add(department1);
        
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        company.getDepartments().add(department2);
        
        // 2. Add a production project titled "Factory Upgrade" with site code "FU456" to D001 and hire 4 permanent employees.
        ProductionProject productionProject1 = new ProductionProject();
        productionProject1.setTitle("Factory Upgrade");
        productionProject1.setSiteCode("FU456");
        department1.getProjects().add(productionProject1);
        
        // Add 4 employees to project in department1
        for (int i = 1; i <= 4; i++) {
            Employee employee = new Employee();
            employee.setName("Employee" + i);
            employee.setEmployeeId("E00" + i);
            productionProject1.getEmployees().add(employee);
        }
        
        // 3. Add a production project titled "New Product Development" with site code "NPD789" to D002 and hire 3 temporary employees.
        ProductionProject productionProject2 = new ProductionProject();
        productionProject2.setTitle("New Product Development");
        productionProject2.setSiteCode("NPD789");
        department2.getProjects().add(productionProject2);
        
        // Add 3 employees to project in department2
        for (int i = 5; i <= 7; i++) {
            Employee employee = new Employee();
            employee.setName("Employee" + i);
            employee.setEmployeeId("E00" + i);
            productionProject2.getEmployees().add(employee);
        }
        
        // Expected Output: Total number of employees = 7
        int result = company.getNumberOfEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects 
        // Input: Calculate the number of employees working on production projects in a department with no production project.
        
        // SetUp:
        // 1. Create a company C003. Create a department with ID: D003 and email: department3@example.com, and add the department to the company C003.
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.getDepartments().add(department);
        
        // 2. Add a research project titled "Market Research" (no production project).
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.getProjects().add(researchProject);
        
        // 3. Hire 2 permanent employees named Frank and Grace with employee IDs: E006 and E007, respectively.
        Employee employee1 = new Employee();
        employee1.setName("Frank");
        employee1.setEmployeeId("E006");
        researchProject.getEmployees().add(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Grace");
        employee2.setEmployeeId("E007");
        researchProject.getEmployees().add(employee2);
        
        // Expected Output: Total number of employees = 0
        int result = company.getNumberOfEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Input: Calculate the number of employees working on production projects in a department with mixed project types.
        
        // SetUp:
        // 1. Create a company C004. Create a department with ID: D004 and email: department4@example.com, and add the department to the company C004.
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.getDepartments().add(department);
        
        // 2. Add a production project titled "Process Optimization" with site code "PO101" and hire 2 temporary employees.
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.getProjects().add(productionProject);
        
        // Add 2 employees to production project
        Employee employee1 = new Employee();
        employee1.setName("TempEmployee1");
        employee1.setEmployeeId("TE001");
        productionProject.getEmployees().add(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("TempEmployee2");
        employee2.setEmployeeId("TE002");
        productionProject.getEmployees().add(employee2);
        
        // 3. Add a community project and an education project (no production project employees).
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.getProjects().add(communityProject);
        
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.getProjects().add(educationProject);
        
        // Add employees to non-production projects (these should not be counted)
        Employee nonProductionEmployee1 = new Employee();
        nonProductionEmployee1.setName("CommunityEmployee");
        nonProductionEmployee1.setEmployeeId("CE001");
        communityProject.getEmployees().add(nonProductionEmployee1);
        
        Employee nonProductionEmployee2 = new Employee();
        nonProductionEmployee2.setName("EducationEmployee");
        nonProductionEmployee2.setEmployeeId("EE001");
        educationProject.getEmployees().add(nonProductionEmployee2);
        
        // Expected Output: Total number of employees = 2
        int result = company.getNumberOfEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 2", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Input: Calculate the number of employees working on production projects in a department that has no active projects.
        
        // SetUp:
        // 1. Create a company C005. Create a department with ID: D005 and email: department5@example.com, and add the department to the company C005.
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.getDepartments().add(department);
        
        // 2. The department has previously hired 3 permanent employees named Henry, Ian, and Jack with employee IDs: E008, E009, and E010, respectively.
        Employee employee1 = new Employee();
        employee1.setName("Henry");
        employee1.setEmployeeId("E008");
        department.getEmployees().add(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Ian");
        employee2.setEmployeeId("E009");
        department.getEmployees().add(employee2);
        
        Employee employee3 = new Employee();
        employee3.setName("Jack");
        employee3.setEmployeeId("E010");
        department.getEmployees().add(employee3);
        
        // 3. No projects are currently ongoing in this department.
        // (Department projects list remains empty)
        
        // Expected Output: Total number of employees = 0
        int result = company.getNumberOfEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
}