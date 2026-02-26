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
        // Create a company C001
        Company c001 = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com, and add to company
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        c001.addDepartment(department);
        
        // Add a production project titled "Product Launch" with site code "PL123"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees for the project
        Employee alice = new Employee();
        alice.setID("E001");
        alice.setName("Alice");
        alice.setType(EmployeeType.PERMANENT);
        
        Employee bob = new Employee();
        bob.setID("E002");
        bob.setName("Bob");
        bob.setType(EmployeeType.PERMANENT);
        
        Employee charlie = new Employee();
        charlie.setID("E003");
        charlie.setName("Charlie");
        charlie.setType(EmployeeType.PERMANENT);
        
        // Hire 2 temporary employees for the project
        Employee david = new Employee();
        david.setID("E004");
        david.setName("David");
        david.setType(EmployeeType.TEMPORARY);
        
        Employee eve = new Employee();
        eve.setID("E005");
        eve.setName("Eve");
        eve.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to the project
        List<Employee> projectEmployees = new ArrayList<>();
        projectEmployees.add(alice);
        projectEmployees.add(bob);
        projectEmployees.add(charlie);
        projectEmployees.add(david);
        projectEmployees.add(eve);
        productionProject.setWorkingEmployees(projectEmployees);
        
        // Calculate and verify total number of employees = 5
        int result = c001.countEmployeesInProductionProjects();
        assertEquals("Should count 5 employees working on production project", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create a company C002
        Company c002 = new Company();
        
        // Create two departments: D001 and D002
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        
        c002.addDepartment(department1);
        c002.addDepartment(department2);
        
        // Add production project "Factory Upgrade" with site code "FU456" to D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        // Hire 4 permanent employees for project1
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setID("E" + (100 + i));
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        
        // Add production project "New Product Development" with site code "NPD789" to D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        // Hire 3 temporary employees for project2
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Employee emp = new Employee();
            emp.setID("E" + (200 + i));
            emp.setName("TempEmployee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        
        // Calculate and verify total number of employees = 7
        int result = c002.countEmployeesInProductionProjects();
        assertEquals("Should count 7 employees working on production projects across departments", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create a company C003
        Company c003 = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        c003.addDepartment(department);
        
        // Add a research project titled "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees named Frank and Grace
        Employee frank = new Employee();
        frank.setID("E006");
        frank.setName("Frank");
        frank.setType(EmployeeType.PERMANENT);
        
        Employee grace = new Employee();
        grace.setID("E007");
        grace.setName("Grace");
        grace.setType(EmployeeType.PERMANENT);
        
        // Add employees to the research project
        List<Employee> researchEmployees = new ArrayList<>();
        researchEmployees.add(frank);
        researchEmployees.add(grace);
        researchProject.setWorkingEmployees(researchEmployees);
        
        // Calculate and verify total number of employees = 0 (no production projects)
        int result = c003.countEmployeesInProductionProjects();
        assertEquals("Should count 0 employees since there are no production projects", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create a company C004
        Company c004 = new Company();
        
        // Create a department with ID: D004 and email: department4@example.com
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        c004.addDepartment(department);
        
        // Add a production project titled "Process Optimization" with site code "PO101"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        // Hire 2 temporary employees for the production project
        List<Employee> productionEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setID("E" + (300 + i));
            emp.setName("TempProdEmployee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            productionEmployees.add(emp);
        }
        productionProject.setWorkingEmployees(productionEmployees);
        
        // Add a community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add an education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Hire employees for non-production projects (should not be counted)
        List<Employee> nonProductionEmployees = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Employee emp = new Employee();
            emp.setID("E" + (400 + i));
            emp.setName("NonProdEmployee" + i);
            emp.setType(EmployeeType.PERMANENT);
            nonProductionEmployees.add(emp);
        }
        communityProject.setWorkingEmployees(nonProductionEmployees);
        educationProject.setWorkingEmployees(new ArrayList<>(nonProductionEmployees));
        
        // Calculate and verify total number of employees = 2 (only from production project)
        int result = c004.countEmployeesInProductionProjects();
        assertEquals("Should count only 2 employees from production project", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create a company C005
        Company c005 = new Company();
        
        // Create a department with ID: D005 and email: department5@example.com
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        c005.addDepartment(department);
        
        // The department has previously hired 3 permanent employees
        Employee henry = new Employee();
        henry.setID("E008");
        henry.setName("Henry");
        henry.setType(EmployeeType.PERMANENT);
        
        Employee ian = new Employee();
        ian.setID("E009");
        ian.setName("Ian");
        ian.setType(EmployeeType.PERMANENT);
        
        Employee jack = new Employee();
        jack.setID("E010");
        jack.setName("Jack");
        jack.setType(EmployeeType.PERMANENT);
        
        // No projects are currently ongoing in this department
        // Employees are not assigned to any project
        
        // Calculate and verify total number of employees = 0 (no projects)
        int result = c005.countEmployeesInProductionProjects();
        assertEquals("Should count 0 employees since there are no active projects", 0, result);
    }
}