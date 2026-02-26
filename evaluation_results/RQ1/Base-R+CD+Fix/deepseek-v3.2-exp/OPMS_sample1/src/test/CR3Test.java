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
        // SetUp: Create company C001 and department D001
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Add production project with site code PL123
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.addProject(project);
        
        // Hire 3 permanent employees
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
        
        // Hire 2 temporary employees
        Employee david = new Employee();
        david.setName("David");
        david.setID("E004");
        david.setType(EmployeeType.TEMPORARY);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setID("E005");
        eve.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to the project
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        employees.add(charlie);
        employees.add(david);
        employees.add(eve);
        project.setWorkingEmployees(employees);
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // SetUp: Create company C002 and two departments
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        // Add production project to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        
        // Add production project to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // SetUp: Create company C003 and department D003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add research project (non-production)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // Hire 2 permanent employees
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setID("E006");
        frank.setType(EmployeeType.PERMANENT);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setID("E007");
        grace.setType(EmployeeType.PERMANENT);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(frank);
        employees.add(grace);
        project.setWorkingEmployees(employees);
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // SetUp: Create company C004 and department D004
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        List<Employee> productionEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setName("TempEmployee" + i);
            emp.setID("TE00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            productionEmployees.add(emp);
        }
        productionProject.setWorkingEmployees(productionEmployees);
        
        // Add community project (non-production)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Project");
        department.addProject(communityProject);
        
        // Add education project (non-production)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Education Project");
        department.addProject(educationProject);
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // SetUp: Create company C005 and department D005
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Hire 3 permanent employees (no projects assigned)
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
        
        // Employees are hired but not assigned to any project
        // Department has no projects (no projects added)
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, company.countEmployeesInProductionProjects());
    }
}