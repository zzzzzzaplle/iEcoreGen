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
        company.setDepartments(new ArrayList<>());
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.getDepartments().add(department);
        
        // Add production project to department
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.setProjects(new ArrayList<>());
        department.getProjects().add(productionProject);
        
        // Hire 3 permanent employees (Alice, Bob, Charlie) for the project
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        alice.setProjects(new ArrayList<>());
        alice.getProjects().add(productionProject);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        bob.setProjects(new ArrayList<>());
        bob.getProjects().add(productionProject);
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        charlie.setProjects(new ArrayList<>());
        charlie.getProjects().add(productionProject);
        
        // Hire 2 temporary employees (David, Eve) for the project
        Employee david = new Employee();
        david.setName("David");
        david.setEmployeeId("E004");
        david.setProjects(new ArrayList<>());
        david.getProjects().add(productionProject);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        eve.setProjects(new ArrayList<>());
        eve.getProjects().add(productionProject);
        
        // Add all employees to department
        department.setEmployees(new ArrayList<>());
        department.getEmployees().add(alice);
        department.getEmployees().add(bob);
        department.getEmployees().add(charlie);
        department.getEmployees().add(david);
        department.getEmployees().add(eve);
        
        // Calculate and verify total number of employees working on production projects
        int result = company.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // SetUp: Create company C002 with two departments
        company.setDepartments(new ArrayList<>());
        
        // Department 1: D001
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        company.getDepartments().add(department1);
        
        // Add production project to department 1 and hire 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.setProjects(new ArrayList<>());
        department1.getProjects().add(project1);
        
        department1.setEmployees(new ArrayList<>());
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            emp.setProjects(new ArrayList<>());
            emp.getProjects().add(project1);
            department1.getEmployees().add(emp);
        }
        
        // Department 2: D002
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        company.getDepartments().add(department2);
        
        // Add production project to department 2 and hire 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.setProjects(new ArrayList<>());
        department2.getProjects().add(project2);
        
        department2.setEmployees(new ArrayList<>());
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            emp.setProjects(new ArrayList<>());
            emp.getProjects().add(project2);
            department2.getEmployees().add(emp);
        }
        
        // Calculate and verify total number of employees working on production projects
        int result = company.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // SetUp: Create company C003 with department D003
        company.setDepartments(new ArrayList<>());
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.getDepartments().add(department);
        
        // Add research project (non-production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.setProjects(new ArrayList<>());
        department.getProjects().add(researchProject);
        
        // Hire 2 permanent employees
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        frank.setProjects(new ArrayList<>());
        frank.getProjects().add(researchProject);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        grace.setProjects(new ArrayList<>());
        grace.getProjects().add(researchProject);
        
        department.setEmployees(new ArrayList<>());
        department.getEmployees().add(frank);
        department.getEmployees().add(grace);
        
        // Calculate and verify total number of employees working on production projects
        int result = company.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 0 when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // SetUp: Create company C004 with department D004
        company.setDepartments(new ArrayList<>());
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.getDepartments().add(department);
        
        department.setProjects(new ArrayList<>());
        department.setEmployees(new ArrayList<>());
        
        // Add production project and hire 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.getProjects().add(productionProject);
        
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setName("TempEmployee" + i);
            emp.setEmployeeId("TE00" + i);
            emp.setProjects(new ArrayList<>());
            emp.getProjects().add(productionProject);
            department.getEmployees().add(emp);
        }
        
        // Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.getProjects().add(communityProject);
        
        // Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("STEM Education");
        department.getProjects().add(educationProject);
        
        // Calculate and verify total number of employees working on production projects
        int result = company.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 2 (only those on production project)", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // SetUp: Create company C005 with department D005
        company.setDepartments(new ArrayList<>());
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.getDepartments().add(department);
        
        // Department has 3 permanent employees but no active projects
        department.setProjects(new ArrayList<>()); // No projects
        department.setEmployees(new ArrayList<>());
        
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setEmployeeId("E008");
        henry.setProjects(new ArrayList<>()); // No projects assigned
        
        Employee ian = new Employee();
        ian.setName("Ian");
        ian.setEmployeeId("E009");
        ian.setProjects(new ArrayList<>()); // No projects assigned
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setEmployeeId("E010");
        jack.setProjects(new ArrayList<>()); // No projects assigned
        
        department.getEmployees().add(henry);
        department.getEmployees().add(ian);
        department.getEmployees().add(jack);
        
        // Calculate and verify total number of employees working on production projects
        int result = company.countEmployeesOnProductionProjects();
        assertEquals("Total number of employees should be 0 when no projects exist", 0, result);
    }
}