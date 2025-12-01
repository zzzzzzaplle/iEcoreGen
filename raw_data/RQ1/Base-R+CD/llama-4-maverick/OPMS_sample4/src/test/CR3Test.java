import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
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
        // Set up company C001
        company = new Company();
        
        // Create department D001
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Create production project
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Create and add 3 permanent employees
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
        
        // Create and add 2 temporary employees
        Employee david = new Employee();
        david.setName("David");
        david.setID("E004");
        david.setType(EmployeeType.TEMPORARY);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setID("E005");
        eve.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to the production project
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        employees.add(charlie);
        employees.add(david);
        employees.add(eve);
        productionProject.setWorkingEmployees(employees);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Set up company C002
        company = new Company();
        
        // Create first department D001
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        // Create second department D002
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        // Create production project for D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        // Create 4 permanent employees for D001 project
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + (i + 5));
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        
        // Create production project for D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        // Create 3 temporary employees for D002 project
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Employee emp = new Employee();
            emp.setName("TempEmployee" + i);
            emp.setID("E01" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Set up company C003
        company = new Company();
        
        // Create department D003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Create research project (non-production)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Create 2 employees (but they won't be counted since they work on non-production project)
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
        researchProject.setWorkingEmployees(employees);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 0 (no production projects)", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Set up company C004
        company = new Company();
        
        // Create department D004
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Create production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setID("E011");
        temp1.setType(EmployeeType.TEMPORARY);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setID("E012");
        temp2.setType(EmployeeType.TEMPORARY);
        
        List<Employee> productionEmployees = new ArrayList<>();
        productionEmployees.add(temp1);
        productionEmployees.add(temp2);
        productionProject.setWorkingEmployees(productionEmployees);
        
        // Create community project (non-production)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Create education project (non-production)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Training Program");
        department.addProject(educationProject);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 2 (only production project employees)", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Set up company C005
        company = new Company();
        
        // Create department D005
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Create 3 employees but don't assign them to any project
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
        
        // Note: These employees are not assigned to any project, so they won't be counted
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 0 (no active projects)", 0, result);
    }
}