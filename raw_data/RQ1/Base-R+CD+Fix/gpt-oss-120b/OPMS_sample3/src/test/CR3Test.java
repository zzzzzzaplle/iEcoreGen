import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Company company;
    private Department department;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() throws Exception {
        // Create company C001
        Company c001 = new Company();
        
        // Create department D001 and add to company
        Department d001 = new Department();
        d001.setID("D001");
        d001.setEmail("department1@example.com");
        c001.addDepartment(d001);
        
        // Add production project "Product Launch" with site code "PL123"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        productionProject.setBudget(100000.0);
        productionProject.setDeadline(dateFormat.parse("2024-12-31 23:59:59"));
        d001.addProject(productionProject);
        
        // Hire 3 permanent employees for the project
        Employee alice = new Employee();
        alice.setID("E001");
        alice.setName("Alice");
        alice.setType(EmployeeType.PERMANENT);
        productionProject.addWorkingEmployee(alice);
        
        Employee bob = new Employee();
        bob.setID("E002");
        bob.setName("Bob");
        bob.setType(EmployeeType.PERMANENT);
        productionProject.addWorkingEmployee(bob);
        
        Employee charlie = new Employee();
        charlie.setID("E003");
        charlie.setName("Charlie");
        charlie.setType(EmployeeType.PERMANENT);
        productionProject.addWorkingEmployee(charlie);
        
        // Hire 2 temporary employees for the project
        Employee david = new Employee();
        david.setID("E004");
        david.setName("David");
        david.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(david);
        
        Employee eve = new Employee();
        eve.setID("E005");
        eve.setName("Eve");
        eve.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(eve);
        
        // Calculate and verify total number of employees
        int result = c001.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() throws Exception {
        // Create company C002
        Company c002 = new Company();
        
        // Create first department D001 and add to company
        Department d001 = new Department();
        d001.setID("D001");
        d001.setEmail("department1@example.com");
        c002.addDepartment(d001);
        
        // Add production project "Factory Upgrade" to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        project1.setBudget(200000.0);
        project1.setDeadline(dateFormat.parse("2024-06-30 23:59:59"));
        d001.addProject(project1);
        
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.PERMANENT);
            project1.addWorkingEmployee(emp);
        }
        
        // Create second department D002 and add to company
        Department d002 = new Department();
        d002.setID("D002");
        d002.setEmail("department2@example.com");
        c002.addDepartment(d002);
        
        // Add production project "New Product Development" to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        project2.setBudget(150000.0);
        project2.setDeadline(dateFormat.parse("2024-09-30 23:59:59"));
        d002.addProject(project2);
        
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            project2.addWorkingEmployee(emp);
        }
        
        // Calculate and verify total number of employees
        int result = c002.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() throws Exception {
        // Create company C003
        Company c003 = new Company();
        
        // Create department D003 and add to company
        Department d003 = new Department();
        d003.setID("D003");
        d003.setEmail("department3@example.com");
        c003.addDepartment(d003);
        
        // Add research project "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        researchProject.setBudget(50000.0);
        researchProject.setDeadline(dateFormat.parse("2024-03-31 23:59:59"));
        d003.addProject(researchProject);
        
        // Hire 2 permanent employees
        Employee frank = new Employee();
        frank.setID("E006");
        frank.setName("Frank");
        frank.setType(EmployeeType.PERMANENT);
        researchProject.addWorkingEmployee(frank);
        
        Employee grace = new Employee();
        grace.setID("E007");
        grace.setName("Grace");
        grace.setType(EmployeeType.PERMANENT);
        researchProject.addWorkingEmployee(grace);
        
        // Calculate and verify total number of employees
        int result = c003.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() throws Exception {
        // Create company C004
        Company c004 = new Company();
        
        // Create department D004 and add to company
        Department d004 = new Department();
        d004.setID("D004");
        d004.setEmail("department4@example.com");
        c004.addDepartment(d004);
        
        // Add production project "Process Optimization" with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        productionProject.setBudget(75000.0);
        productionProject.setDeadline(dateFormat.parse("2024-07-31 23:59:59"));
        d004.addProject(productionProject);
        
        Employee temp1 = new Employee();
        temp1.setID("E011");
        temp1.setName("Temp1");
        temp1.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(temp1);
        
        Employee temp2 = new Employee();
        temp2.setID("E012");
        temp2.setName("Temp2");
        temp2.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(temp2);
        
        // Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        communityProject.setBudget(30000.0);
        communityProject.setDeadline(dateFormat.parse("2024-05-31 23:59:59"));
        d004.addProject(communityProject);
        
        // Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Employee Training");
        educationProject.setBudget(40000.0);
        educationProject.setDeadline(dateFormat.parse("2024-04-30 23:59:59"));
        d004.addProject(educationProject);
        
        // Calculate and verify total number of employees
        int result = c004.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 2", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create company C005
        Company c005 = new Company();
        
        // Create department D005 and add to company
        Department d005 = new Department();
        d005.setID("D005");
        d005.setEmail("department5@example.com");
        c005.addDepartment(d005);
        
        // Hire 3 permanent employees (no projects assigned)
        Employee henry = new Employee();
        henry.setID("E008");
        henry.setName("Henry");
        henry.setType(EmployeeType.PERMANENT);
        d005.addEmployee(henry);
        
        Employee ian = new Employee();
        ian.setID("E009");
        ian.setName("Ian");
        ian.setType(EmployeeType.PERMANENT);
        d005.addEmployee(ian);
        
        Employee jack = new Employee();
        jack.setID("E010");
        jack.setName("Jack");
        jack.setType(EmployeeType.PERMANENT);
        d005.addEmployee(jack);
        
        // No projects are currently ongoing in this department
        
        // Calculate and verify total number of employees
        int result = c005.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
}