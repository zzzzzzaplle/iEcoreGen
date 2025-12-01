import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department department;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = null;
        department = null;
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // Setup
        company = new Company(); // Create company C001
        
        department = new Department(); // Create department D001
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        ProductionProject productionProject = new ProductionProject(); // Add production project
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
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
        
        // Add all employees to the project
        List<Employee> projectEmployees = new ArrayList<>();
        projectEmployees.add(alice);
        projectEmployees.add(bob);
        projectEmployees.add(charlie);
        projectEmployees.add(david);
        projectEmployees.add(eve);
        productionProject.setWorkingEmployees(projectEmployees);
        
        // Expected Output: Total number of employees = 5
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Setup
        company = new Company(); // Create company C002
        
        // Create first department D001
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        // Add production project to D001 and hire 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.PERMANENT);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        
        // Create second department D002
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        // Add production project to D002 and hire 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        
        // Expected Output: Total number of employees = 7
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // Setup
        company = new Company(); // Create company C003
        
        department = new Department(); // Create department D003
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add a research project (not production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees
        Employee frank = new Employee();
        frank.setType(EmployeeType.PERMANENT);
        frank.setName("Frank");
        frank.setID("E006");
        
        Employee grace = new Employee();
        grace.setType(EmployeeType.PERMANENT);
        grace.setName("Grace");
        grace.setID("E007");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(frank);
        employees.add(grace);
        researchProject.setWorkingEmployees(employees);
        
        // Expected Output: Total number of employees = 0
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Setup
        company = new Company(); // Create company C004
        
        department = new Department(); // Create department D004
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add a production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        List<Employee> productionEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setName("TempEmployee" + i);
            emp.setID("TE00" + i);
            productionEmployees.add(emp);
        }
        productionProject.setWorkingEmployees(productionEmployees);
        
        // Add a community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add an education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Expected Output: Total number of employees = 2
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 2", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Setup
        company = new Company(); // Create company C005
        
        department = new Department(); // Create department D005
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
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
        
        List<Employee> departmentEmployees = new ArrayList<>();
        departmentEmployees.add(henry);
        departmentEmployees.add(ian);
        departmentEmployees.add(jack);
        department.setEmployees(departmentEmployees);
        
        // No projects are added to the department
        
        // Expected Output: Total number of employees = 0
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
}