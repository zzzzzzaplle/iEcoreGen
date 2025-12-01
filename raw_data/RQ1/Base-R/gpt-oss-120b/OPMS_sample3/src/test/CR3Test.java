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
        // SetUp: Create a company C001
        company = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Add the department to the company C001
        company.getDepartments().add(department);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.getProjects().add(project);
        
        // Hire 3 permanent employees named Alice, Bob, and Charlie
        List<Employee> employees = new ArrayList<>();
        
        PermanentEmployee alice = new PermanentEmployee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        employees.add(alice);
        
        PermanentEmployee bob = new PermanentEmployee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        employees.add(bob);
        
        PermanentEmployee charlie = new PermanentEmployee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        employees.add(charlie);
        
        // Hire 2 temporary employees named David and Eve
        TemporaryEmployee david = new TemporaryEmployee();
        david.setName("David");
        david.setEmployeeId("E004");
        employees.add(david);
        
        TemporaryEmployee eve = new TemporaryEmployee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        employees.add(eve);
        
        // Assign employees to the project
        project.setEmployees(employees);
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, company.getNumberOfEmployeesOnProductionProjects());
    }

    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // SetUp: Create a company C002
        company = new Company();
        
        // Create two departments: D001 and D002
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        
        // Add departments to the company C002
        company.getDepartments().add(department1);
        company.getDepartments().add(department2);
        
        // Add production project "Factory Upgrade" to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            PermanentEmployee emp = new PermanentEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            employees1.add(emp);
        }
        project1.setEmployees(employees1);
        department1.getProjects().add(project1);
        
        // Add production project "New Product Development" to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            TemporaryEmployee emp = new TemporaryEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            employees2.add(emp);
        }
        project2.setEmployees(employees2);
        department2.getProjects().add(project2);
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, company.getNumberOfEmployeesOnProductionProjects());
    }

    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // SetUp: Create a company C003
        company = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Add the department to the company C003
        company.getDepartments().add(department);
        
        // Add a research project titled "Market Research"
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.getProjects().add(project);
        
        // Hire 2 permanent employees named Frank and Grace
        List<Employee> employees = new ArrayList<>();
        
        PermanentEmployee frank = new PermanentEmployee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        employees.add(frank);
        
        PermanentEmployee grace = new PermanentEmployee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        employees.add(grace);
        
        // Assign employees to the research project (not a production project)
        project.setEmployees(employees);
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, company.getNumberOfEmployeesOnProductionProjects());
    }

    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // SetUp: Create a company C004
        company = new Company();
        
        // Create a department with ID: D004 and email: department4@example.com
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        
        // Add the department to the company C004
        company.getDepartments().add(department);
        
        // Add a production project titled "Process Optimization" with site code "PO101"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        
        // Hire 2 temporary employees for the production project
        List<Employee> productionEmployees = new ArrayList<>();
        
        TemporaryEmployee temp1 = new TemporaryEmployee();
        temp1.setName("Temp1");
        temp1.setEmployeeId("T001");
        productionEmployees.add(temp1);
        
        TemporaryEmployee temp2 = new TemporaryEmployee();
        temp2.setName("Temp2");
        temp2.setEmployeeId("T002");
        productionEmployees.add(temp2);
        
        productionProject.setEmployees(productionEmployees);
        department.getProjects().add(productionProject);
        
        // Add a community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        
        List<Employee> communityEmployees = new ArrayList<>();
        PermanentEmployee commEmp = new PermanentEmployee();
        commEmp.setName("Community Manager");
        commEmp.setEmployeeId("CM001");
        communityEmployees.add(commEmp);
        
        communityProject.setEmployees(communityEmployees);
        department.getProjects().add(communityProject);
        
        // Add an education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Training Program");
        
        List<Employee> educationEmployees = new ArrayList<>();
        PermanentEmployee eduEmp = new PermanentEmployee();
        eduEmp.setName("Trainer");
        eduEmp.setEmployeeId("TR001");
        educationEmployees.add(eduEmp);
        
        educationProject.setEmployees(educationEmployees);
        department.getProjects().add(educationProject);
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, company.getNumberOfEmployeesOnProductionProjects());
    }

    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // SetUp: Create a company C005
        company = new Company();
        
        // Create a department with ID: D005 and email: department5@example.com
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        
        // Add the department to the company C005
        company.getDepartments().add(department);
        
        // The department has previously hired 3 permanent employees
        // Employees exist but are not assigned to any projects since no projects are ongoing
        
        // No projects are currently ongoing in this department
        // department.getProjects() remains empty
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, company.getNumberOfEmployeesOnProductionProjects());
    }
}