import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department department;
    private ProductionProject productionProject;
    private ResearchProject researchProject;
    private CommunityProject communityProject;
    private EducationProject educationProject;
    private Employee employee;
    
    @Before
    public void setUp() {
        company = new Company();
        department = new Department();
        productionProject = new ProductionProject();
        researchProject = new ResearchProject();
        communityProject = new CommunityProject();
        educationProject = new EducationProject();
        employee = new Employee();
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // SetUp: Create company C001
        Company company = new Company();
        
        // Create department D001 and add to company
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.getDepartments().add(department);
        
        // Add production project "Product Launch" with site code "PL123"
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.getProjects().add(project);
        
        // Hire 3 permanent employees: Alice, Bob, Charlie
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        project.getEmployees().add(alice);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        project.getEmployees().add(bob);
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        project.getEmployees().add(charlie);
        
        // Hire 2 temporary employees: David, Eve
        Employee david = new Employee();
        david.setName("David");
        david.setEmployeeId("E004");
        project.getEmployees().add(david);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        project.getEmployees().add(eve);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesOnProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals("Total employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // SetUp: Create company C002
        Company company = new Company();
        
        // Create first department D001 and add to company
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        company.getDepartments().add(department1);
        
        // Add production project "Factory Upgrade" with site code "FU456" to D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.getProjects().add(project1);
        
        // Hire 4 permanent employees for project1
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project1.getEmployees().add(emp);
        }
        
        // Create second department D002 and add to company
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        company.getDepartments().add(department2);
        
        // Add production project "New Product Development" with site code "NPD789" to D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.getProjects().add(project2);
        
        // Hire 3 temporary employees for project2
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project2.getEmployees().add(emp);
        }
        
        // Calculate total employees working on production projects across departments
        int result = company.countEmployeesOnProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals("Total employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // SetUp: Create company C003
        Company company = new Company();
        
        // Create department D003 and add to company
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.getDepartments().add(department);
        
        // Add research project "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.getProjects().add(researchProject);
        
        // Hire 2 permanent employees: Frank and Grace
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        researchProject.getEmployees().add(frank);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        researchProject.getEmployees().add(grace);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesOnProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals("Total employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // SetUp: Create company C004
        Company company = new Company();
        
        // Create department D004 and add to company
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.getDepartments().add(department);
        
        // Add production project "Process Optimization" with site code "PO101"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.getProjects().add(productionProject);
        
        // Hire 2 temporary employees for production project
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setEmployeeId("E011");
        productionProject.getEmployees().add(temp1);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setEmployeeId("E012");
        productionProject.getEmployees().add(temp2);
        
        // Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.getProjects().add(communityProject);
        
        // Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.getProjects().add(educationProject);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesOnProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals("Total employees should be 2", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // SetUp: Create company C005
        Company company = new Company();
        
        // Create department D005 and add to company
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.getDepartments().add(department);
        
        // Department has 3 permanent employees but no active projects
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setEmployeeId("E008");
        department.getEmployees().add(henry);
        
        Employee ian = new Employee();
        ian.setName("Ian");
        ian.setEmployeeId("E009");
        department.getEmployees().add(ian);
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setEmployeeId("E010");
        department.getEmployees().add(jack);
        
        // No projects are currently ongoing in this department
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesOnProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals("Total employees should be 0", 0, result);
    }
}