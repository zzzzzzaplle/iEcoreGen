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
        // SetUp: Create company C001 with department D001
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // SetUp: Add production project "Product Launch" with site code "PL123"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // SetUp: Hire 3 permanent employees (Alice, Bob, Charlie) for the project
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        productionProject.addEmployee(alice);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        productionProject.addEmployee(bob);
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        productionProject.addEmployee(charlie);
        
        // SetUp: Hire 2 temporary employees (David, Eve) for the project
        Employee david = new Employee();
        david.setName("David");
        david.setEmployeeId("E004");
        productionProject.addEmployee(david);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        productionProject.addEmployee(eve);
        
        // Input: Calculate number of employees working on production projects
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // SetUp: Create company C002
        // SetUp: Create two departments D001 and D002
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        // SetUp: Add production project "Factory Upgrade" to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        for (int i = 1; i <= 4; i++) {
            Employee employee = new Employee();
            employee.setName("PermEmployee" + i);
            employee.setEmployeeId("PE00" + i);
            project1.addEmployee(employee);
        }
        
        // SetUp: Add production project "New Product Development" to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        for (int i = 1; i <= 3; i++) {
            Employee employee = new Employee();
            employee.setName("TempEmployee" + i);
            employee.setEmployeeId("TE00" + i);
            project2.addEmployee(employee);
        }
        
        // Input: Calculate number of employees working on production projects
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // SetUp: Create company C003 with department D003
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // SetUp: Add research project "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // SetUp: Hire 2 permanent employees (Frank, Grace)
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        researchProject.addEmployee(frank);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        researchProject.addEmployee(grace);
        
        // Input: Calculate number of employees working on production projects
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // SetUp: Create company C004 with department D004
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // SetUp: Add production project "Process Optimization" with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setEmployeeId("TE011");
        productionProject.addEmployee(temp1);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setEmployeeId("TE012");
        productionProject.addEmployee(temp2);
        
        // SetUp: Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // SetUp: Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Input: Calculate number of employees working on production projects
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // SetUp: Create company C005 with department D005
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // SetUp: Department has 3 permanent employees (Henry, Ian, Jack) but no active projects
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setEmployeeId("E008");
        department.addEmployee(henry);
        
        Employee ian = new Employee();
        ian.setName("Ian");
        ian.setEmployeeId("E009");
        department.addEmployee(ian);
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setEmployeeId("E010");
        department.addEmployee(jack);
        
        // Input: Calculate number of employees working on production projects
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}