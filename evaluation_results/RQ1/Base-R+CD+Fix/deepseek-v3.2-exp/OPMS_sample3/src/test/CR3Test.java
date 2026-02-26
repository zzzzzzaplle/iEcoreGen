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
    public void testCase1_SingleDepartmentWithProductionProjects() throws Exception {
        // Create company C001
        Company c001 = new Company();
        
        // Create department D001 and add to company
        Department d001 = new Department();
        d001.setID("D001");
        d001.setEmail("department1@example.com");
        c001.addDepartment(d001);
        
        // Add production project
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        productionProject.setDeadline(dateFormat.parse("2024-12-31 23:59:59"));
        d001.addProject(productionProject);
        
        // Create and add permanent employees
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
        
        // Create and add temporary employees
        Employee david = new Employee();
        david.setName("David");
        david.setID("E004");
        david.setType(EmployeeType.TEMPORARY);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setID("E005");
        eve.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to the project
        ArrayList<Employee> projectEmployees = new ArrayList<>();
        projectEmployees.add(alice);
        projectEmployees.add(bob);
        projectEmployees.add(charlie);
        projectEmployees.add(david);
        projectEmployees.add(eve);
        productionProject.setWorkingEmployees(projectEmployees);
        
        // Calculate and verify total employees
        int result = c001.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsWithProductionProjects() throws Exception {
        // Create company C002
        Company c002 = new Company();
        
        // Create first department D001 and add to company
        Department d001 = new Department();
        d001.setID("D001");
        d001.setEmail("department1@example.com");
        c002.addDepartment(d001);
        
        // Add production project to D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        project1.setDeadline(dateFormat.parse("2024-06-30 23:59:59"));
        d001.addProject(project1);
        
        // Create and add 4 permanent employees to project1
        ArrayList<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        
        // Create second department D002 and add to company
        Department d002 = new Department();
        d002.setID("D002");
        d002.setEmail("department2@example.com");
        c002.addDepartment(d002);
        
        // Add production project to D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        project2.setDeadline(dateFormat.parse("2024-09-30 23:59:59"));
        d002.addProject(project2);
        
        // Create and add 3 temporary employees to project2
        ArrayList<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        
        // Calculate and verify total employees
        int result = c002.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_NoProductionProjects() throws Exception {
        // Create company C003
        Company c003 = new Company();
        
        // Create department D003 and add to company
        Department d003 = new Department();
        d003.setID("D003");
        d003.setEmail("department3@example.com");
        c003.addDepartment(d003);
        
        // Add research project (non-production)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        researchProject.setDeadline(dateFormat.parse("2024-03-31 23:59:59"));
        d003.addProject(researchProject);
        
        // Create employees (but they're on research project, not production)
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setID("E006");
        frank.setType(EmployeeType.PERMANENT);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setID("E007");
        grace.setType(EmployeeType.PERMANENT);
        
        // Add employees to research project
        ArrayList<Employee> researchEmployees = new ArrayList<>();
        researchEmployees.add(frank);
        researchEmployees.add(grace);
        researchProject.setWorkingEmployees(researchEmployees);
        
        // Calculate and verify total employees (should be 0 since no production projects)
        int result = c003.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_MixedProjectTypes() throws Exception {
        // Create company C004
        Company c004 = new Company();
        
        // Create department D004 and add to company
        Department d004 = new Department();
        d004.setID("D004");
        d004.setEmail("department4@example.com");
        c004.addDepartment(d004);
        
        // Add production project
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        productionProject.setDeadline(dateFormat.parse("2024-07-31 23:59:59"));
        d004.addProject(productionProject);
        
        // Create and add 2 temporary employees to production project
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setID("E011");
        temp1.setType(EmployeeType.TEMPORARY);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setID("E012");
        temp2.setType(EmployeeType.TEMPORARY);
        
        ArrayList<Employee> productionEmployees = new ArrayList<>();
        productionEmployees.add(temp1);
        productionEmployees.add(temp2);
        productionProject.setWorkingEmployees(productionEmployees);
        
        // Add community project (non-production)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        communityProject.setDeadline(dateFormat.parse("2024-08-31 23:59:59"));
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        communityProject.setFundingGroup(fundingGroup);
        d004.addProject(communityProject);
        
        // Add education project (non-production)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        educationProject.setDeadline(dateFormat.parse("2024-09-30 23:59:59"));
        FundingGroup eduFunding = new FundingGroup();
        eduFunding.setType(FundingGroupType.PRIVATE);
        educationProject.setFundingGroup(eduFunding);
        d004.addProject(educationProject);
        
        // Calculate and verify total employees (should only count production project employees)
        int result = c004.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 2", 2, result);
    }
    
    @Test
    public void testCase5_DepartmentWithoutActiveProjects() throws Exception {
        // Create company C005
        Company c005 = new Company();
        
        // Create department D005 and add to company
        Department d005 = new Department();
        d005.setID("D005");
        d005.setEmail("department5@example.com");
        c005.addDepartment(d005);
        
        // Create employees but don't assign them to any project
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
        
        // Add employees to department (but not to any project)
        ArrayList<Employee> departmentEmployees = new ArrayList<>();
        departmentEmployees.add(henry);
        departmentEmployees.add(ian);
        departmentEmployees.add(jack);
        d005.setEmployees(departmentEmployees);
        
        // No projects are added to the department
        
        // Calculate and verify total employees (should be 0 since no projects)
        int result = c005.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
}