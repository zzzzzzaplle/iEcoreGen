import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        // Create department D001 and add to company C001
        Department department = new Department();
        department.setDepartmentId("D001");
        department.setEmail("department1@example.com");
        
        // Create production project "Product Launch"
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        
        // Create and add 3 permanent employees
        PermanentEmployee alice = new PermanentEmployee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        
        PermanentEmployee bob = new PermanentEmployee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        
        PermanentEmployee charlie = new PermanentEmployee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        
        // Create and add 2 temporary employees
        TemporaryEmployee david = new TemporaryEmployee();
        david.setName("David");
        david.setEmployeeId("E004");
        
        TemporaryEmployee eve = new TemporaryEmployee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        
        // Add all employees to the project
        List<Employee> projectEmployees = new ArrayList<>();
        projectEmployees.add(alice);
        projectEmployees.add(bob);
        projectEmployees.add(charlie);
        projectEmployees.add(david);
        projectEmployees.add(eve);
        project.setAssignedEmployees(projectEmployees);
        
        // Add project to department
        department.getProjects().add(project);
        
        // Add department to company
        company.getDepartments().add(department);
        
        // Calculate and verify result
        int result = company.getNumberOfEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Create first department D001
        Department department1 = new Department();
        department1.setDepartmentId("D001");
        department1.setEmail("department1@example.com");
        
        // Create production project "Factory Upgrade" for D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        
        // Add 4 permanent employees to first project
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            PermanentEmployee emp = new PermanentEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            employees1.add(emp);
        }
        project1.setAssignedEmployees(employees1);
        department1.getProjects().add(project1);
        
        // Create second department D002
        Department department2 = new Department();
        department2.setDepartmentId("D002");
        department2.setEmail("department2@example.com");
        
        // Create production project "New Product Development" for D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        
        // Add 3 temporary employees to second project
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            TemporaryEmployee emp = new TemporaryEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            employees2.add(emp);
        }
        project2.setAssignedEmployees(employees2);
        department2.getProjects().add(project2);
        
        // Add both departments to company
        company.getDepartments().add(department1);
        company.getDepartments().add(department2);
        
        // Calculate and verify result
        int result = company.getNumberOfEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // Create department D003
        Department department = new Department();
        department.setDepartmentId("D003");
        department.setEmail("department3@example.com");
        
        // Create research project "Market Research"
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        
        // Create and add 2 permanent employees
        PermanentEmployee frank = new PermanentEmployee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        
        PermanentEmployee grace = new PermanentEmployee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        
        List<Employee> projectEmployees = new ArrayList<>();
        projectEmployees.add(frank);
        projectEmployees.add(grace);
        project.setAssignedEmployees(projectEmployees);
        
        // Add project to department
        department.getProjects().add(project);
        
        // Add department to company
        company.getDepartments().add(department);
        
        // Calculate and verify result
        int result = company.getNumberOfEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 0 when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Create department D004
        Department department = new Department();
        department.setDepartmentId("D004");
        department.setEmail("department4@example.com");
        
        // Create production project "Process Optimization"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        
        // Add 2 temporary employees to production project
        TemporaryEmployee temp1 = new TemporaryEmployee();
        temp1.setName("Temp1");
        temp1.setEmployeeId("E011");
        
        TemporaryEmployee temp2 = new TemporaryEmployee();
        temp2.setName("Temp2");
        temp2.setEmployeeId("E012");
        
        List<Employee> productionEmployees = new ArrayList<>();
        productionEmployees.add(temp1);
        productionEmployees.add(temp2);
        productionProject.setAssignedEmployees(productionEmployees);
        
        // Create community project
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        
        // Create education project
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Employee Training");
        
        // Add all projects to department
        department.getProjects().add(productionProject);
        department.getProjects().add(communityProject);
        department.getProjects().add(educationProject);
        
        // Add department to company
        company.getDepartments().add(department);
        
        // Calculate and verify result
        int result = company.getNumberOfEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 2 (only from production project)", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Create department D005
        Department department = new Department();
        department.setDepartmentId("D005");
        department.setEmail("department5@example.com");
        
        // Hire 3 permanent employees (but no projects)
        PermanentEmployee henry = new PermanentEmployee();
        henry.setName("Henry");
        henry.setEmployeeId("E008");
        
        PermanentEmployee ian = new PermanentEmployee();
        ian.setName("Ian");
        ian.setEmployeeId("E009");
        
        PermanentEmployee jack = new PermanentEmployee();
        jack.setName("Jack");
        jack.setEmployeeId("E010");
        
        // Add employees to department (but no projects assigned)
        department.getEmployees().add(henry);
        department.getEmployees().add(ian);
        department.getEmployees().add(jack);
        
        // Add department to company (department has employees but no projects)
        company.getDepartments().add(department);
        
        // Calculate and verify result
        int result = company.getNumberOfEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 0 when no projects exist", 0, result);
    }
}