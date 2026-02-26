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
        // Reset company before each test
        company = null;
        department = null;
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // Create company C001
        company = new Company();
        
        // Create department with ID: D001 and email: department1@example.com
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Add department to company C001
        company.addDepartment(department);
        
        // Add production project titled "Product Launch" with site code "PL123"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees for the project
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
        
        // Hire 2 temporary employees for the project
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
        
        // Calculate and verify total number of employees = 5
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Create company C002
        company = new Company();
        
        // Create first department D001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        // Add production project "Factory Upgrade" to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.addProject(project1);
        
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
        
        // Add production project "New Product Development" to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.addProject(project2);
        
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            project2.addWorkingEmployee(emp);
        }
        
        // Calculate and verify total number of employees = 7
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees across departments should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // Create company C003
        company = new Company();
        
        // Create department D003
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add research project "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees
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
        
        // Calculate and verify total number of employees = 0 (no production projects)
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0 when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Create company C004
        company = new Company();
        
        // Create department D004
        department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add production project "Process Optimization" with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
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
        
        // Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Calculate and verify total number of employees = 2 (only from production project)
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 2 from production project only", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Create company C005
        company = new Company();
        
        // Create department D005
        department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Hire 3 permanent employees (no projects assigned)
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
        
        // Note: Employees are not added to any project, so they won't be counted
        
        // Calculate and verify total number of employees = 0 (no projects)
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0 when department has no projects", 0, result);
    }
}