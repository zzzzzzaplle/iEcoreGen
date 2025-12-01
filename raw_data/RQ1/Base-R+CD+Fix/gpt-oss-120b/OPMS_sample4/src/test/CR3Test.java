import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department department;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // SetUp: Create company C001, department D001, add production project with 5 employees
        company = new Company();
        
        // Create department D001
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Create production project
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setID("E001");
        alice.setType(EmployeeType.PERMANENT);
        productionProject.addWorkingEmployee(alice);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setID("E002");
        bob.setType(EmployeeType.PERMANENT);
        productionProject.addWorkingEmployee(bob);
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setID("E003");
        charlie.setType(EmployeeType.PERMANENT);
        productionProject.addWorkingEmployee(charlie);
        
        // Hire 2 temporary employees
        Employee david = new Employee();
        david.setName("David");
        david.setID("E004");
        david.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(david);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setID("E005");
        eve.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(eve);
        
        // Expected Output: Total number of employees = 5
        int result = company.countEmployeesInProductionProjects();
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // SetUp: Create company C002, two departments with production projects and employees
        company = new Company();
        
        // Create first department D001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        // Add production project to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.addProject(project1);
        
        // Hire 4 permanent employees for D001
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.PERMANENT);
            project1.addWorkingEmployee(emp);
        }
        
        // Create second department D002
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@example.com");
        company.addDepartment(dept2);
        
        // Add production project to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.addProject(project2);
        
        // Hire 3 temporary employees for D002
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            project2.addWorkingEmployee(emp);
        }
        
        // Expected Output: Total number of employees = 7
        int result = company.countEmployeesInProductionProjects();
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // SetUp: Create company C003, department with research project and 2 employees
        company = new Company();
        
        // Create department D003
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add research project (non-production)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees for research project
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setID("E006");
        frank.setType(EmployeeType.PERMANENT);
        researchProject.addWorkingEmployee(frank);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setID("E007");
        grace.setType(EmployeeType.PERMANENT);
        researchProject.addWorkingEmployee(grace);
        
        // Expected Output: Total number of employees = 0 (no production projects)
        int result = company.countEmployeesInProductionProjects();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // SetUp: Create company C004, department with mixed projects
        company = new Company();
        
        // Create department D004
        department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        // Hire 2 temporary employees for production project
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setID("E011");
        temp1.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(temp1);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setID("E012");
        temp2.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(temp2);
        
        // Add community project (non-production)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add education project (non-production)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("STEM Education");
        department.addProject(educationProject);
        
        // Expected Output: Total number of employees = 2 (only from production project)
        int result = company.countEmployeesInProductionProjects();
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // SetUp: Create company C005, department with employees but no projects
        company = new Company();
        
        // Create department D005
        department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Hire 3 permanent employees (but no projects)
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setID("E008");
        henry.setType(EmployeeType.PERMANENT);
        department.addEmployee(henry);
        
        Employee ian = new Employee();
        ian.setName("Ian");
        ian.setID("E009");
        ian.setType(EmployeeType.PERMANENT);
        department.addEmployee(ian);
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setID("E010");
        jack.setType(EmployeeType.PERMANENT);
        department.addEmployee(jack);
        
        // No projects added to department
        
        // Expected Output: Total number of employees = 0 (no production projects)
        int result = company.countEmployeesInProductionProjects();
        assertEquals(0, result);
    }
}