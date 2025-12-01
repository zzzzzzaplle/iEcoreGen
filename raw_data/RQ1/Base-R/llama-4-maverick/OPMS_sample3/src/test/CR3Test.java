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
        // Create a company C001
        Company c001 = new Company();
        
        // Create department D001 and add to company
        Department dept1 = new Department();
        dept1.setId("D001");
        dept1.setEmail("department1@example.com");
        c001.getDepartments().add(dept1);
        
        // Add production project to department
        ProductionProject prodProject = new ProductionProject();
        prodProject.setTitle("Product Launch");
        prodProject.setSiteCode("PL123");
        dept1.getProjects().add(prodProject);
        
        // Hire 3 permanent employees
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        
        // Hire 2 temporary employees
        Employee david = new Employee();
        david.setName("David");
        david.setEmployeeId("E004");
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        
        // Add all employees to the project
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        employees.add(charlie);
        employees.add(david);
        employees.add(eve);
        prodProject.setEmployees(employees);
        
        // Calculate and verify total number of employees
        int result = c001.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Create a company C002
        Company c002 = new Company();
        
        // Create first department D001 and add to company
        Department dept1 = new Department();
        dept1.setId("D001");
        dept1.setEmail("department1@example.com");
        c002.getDepartments().add(dept1);
        
        // Create second department D002 and add to company
        Department dept2 = new Department();
        dept2.setId("D002");
        dept2.setEmail("department2@example.com");
        c002.getDepartments().add(dept2);
        
        // Add production project to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.getProjects().add(project1);
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            employees1.add(emp);
        }
        project1.setEmployees(employees1);
        
        // Add production project to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.getProjects().add(project2);
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("TempEmployee" + i);
            emp.setEmployeeId("E00" + i);
            employees2.add(emp);
        }
        project2.setEmployees(employees2);
        
        // Calculate and verify total number of employees
        int result = c002.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // Create a company C003
        Company c003 = new Company();
        
        // Create department D003 and add to company
        Department dept = new Department();
        dept.setId("D003");
        dept.setEmail("department3@example.com");
        c003.getDepartments().add(dept);
        
        // Add research project (non-production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        dept.getProjects().add(researchProject);
        
        // Hire 2 permanent employees
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(frank);
        employees.add(grace);
        researchProject.setEmployees(employees);
        
        // Calculate and verify total number of employees (should be 0 since it's not a production project)
        int result = c003.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 0 when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Create a company C004
        Company c004 = new Company();
        
        // Create department D004 and add to company
        Department dept = new Department();
        dept.setId("D004");
        dept.setEmail("department4@example.com");
        c004.getDepartments().add(dept);
        
        // Add production project with 2 temporary employees
        ProductionProject prodProject = new ProductionProject();
        prodProject.setTitle("Process Optimization");
        prodProject.setSiteCode("PO101");
        dept.getProjects().add(prodProject);
        
        List<Employee> prodEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setName("TempEmployee" + i);
            emp.setEmployeeId("TE00" + i);
            prodEmployees.add(emp);
        }
        prodProject.setEmployees(prodEmployees);
        
        // Add community project (non-production)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        dept.getProjects().add(communityProject);
        
        // Add education project (non-production)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Employee Training");
        dept.getProjects().add(educationProject);
        
        // Calculate and verify total number of employees (should only count production project employees)
        int result = c004.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 2 (only from production project)", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Create a company C005
        Company c005 = new Company();
        
        // Create department D005 and add to company
        Department dept = new Department();
        dept.setId("D005");
        dept.setEmail("department5@example.com");
        c005.getDepartments().add(dept);
        
        // Department has 3 permanent employees but no projects
        // Note: Employees without projects cannot be assigned, so they won't be counted
        // The department has no projects, so no employees can be assigned to projects
        
        // Calculate and verify total number of employees
        int result = c005.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 0 when no projects exist", 0, result);
    }
}