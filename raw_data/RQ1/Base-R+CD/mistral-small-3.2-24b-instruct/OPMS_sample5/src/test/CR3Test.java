import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() throws Exception {
        // Create a company C001
        Company c001 = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        c001.addDepartment(department);
        
        // Add a production project titled "Product Launch" with site code "PL123"
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        project.setDeadline(dateFormat.parse("2024-12-31 23:59:59"));
        
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
        project.getWorkingEmployees().add(alice);
        project.getWorkingEmployees().add(bob);
        project.getWorkingEmployees().add(charlie);
        project.getWorkingEmployees().add(david);
        project.getWorkingEmployees().add(eve);
        
        // Add project to department
        department.addProject(project);
        
        // Calculate total number of employees working on production projects
        int result = c001.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() throws Exception {
        // Create a company C002
        Company c002 = new Company();
        
        // Create first department D001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        c002.addDepartment(dept1);
        
        // Create second department D002
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@example.com");
        c002.addDepartment(dept2);
        
        // Add production project to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        project1.setDeadline(dateFormat.parse("2024-11-30 23:59:59"));
        
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.PERMANENT);
            project1.getWorkingEmployees().add(emp);
        }
        dept1.addProject(project1);
        
        // Add production project to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        project2.setDeadline(dateFormat.parse("2024-10-31 23:59:59"));
        
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            project2.getWorkingEmployees().add(emp);
        }
        dept2.addProject(project2);
        
        // Calculate total number of employees working on production projects
        int result = c002.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() throws Exception {
        // Create a company C003
        Company c003 = new Company();
        
        // Create department D003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        c003.addDepartment(department);
        
        // Add a research project (not production project)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        project.setDeadline(dateFormat.parse("2024-09-30 23:59:59"));
        
        // Hire 2 permanent employees
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setID("E006");
        frank.setType(EmployeeType.PERMANENT);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setID("E007");
        grace.setType(EmployeeType.PERMANENT);
        
        project.getWorkingEmployees().add(frank);
        project.getWorkingEmployees().add(grace);
        
        department.addProject(project);
        
        // Calculate total number of employees working on production projects
        int result = c003.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() throws Exception {
        // Create a company C004
        Company c004 = new Company();
        
        // Create department D004
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        c004.addDepartment(department);
        
        // Add a production project with 2 temporary employees
        ProductionProject prodProject = new ProductionProject();
        prodProject.setTitle("Process Optimization");
        prodProject.setSiteCode("PO101");
        prodProject.setDeadline(dateFormat.parse("2024-08-31 23:59:59"));
        
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setID("T001");
        temp1.setType(EmployeeType.TEMPORARY);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setID("T002");
        temp2.setType(EmployeeType.TEMPORARY);
        
        prodProject.getWorkingEmployees().add(temp1);
        prodProject.getWorkingEmployees().add(temp2);
        department.addProject(prodProject);
        
        // Add a community project (not production project)
        CommunityProject commProject = new CommunityProject();
        commProject.setTitle("Community Outreach");
        commProject.setDeadline(dateFormat.parse("2024-07-31 23:59:59"));
        department.addProject(commProject);
        
        // Add an education project (not production project)
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Employee Training");
        eduProject.setDeadline(dateFormat.parse("2024-06-30 23:59:59"));
        department.addProject(eduProject);
        
        // Calculate total number of employees working on production projects
        int result = c004.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create a company C005
        Company c005 = new Company();
        
        // Create department D005
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        
        // Hire 3 permanent employees (but no projects)
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
        
        department.getEmployees().add(henry);
        department.getEmployees().add(ian);
        department.getEmployees().add(jack);
        
        c005.addDepartment(department);
        
        // Calculate total number of employees working on production projects
        int result = c005.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}