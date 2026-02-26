import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // Create company C001
        Company company = new Company();
        
        // Create department D001 and add to company
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Add production project "Product Launch" with site code "PL123"
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.addProject(project);
        
        // Hire 3 permanent employees
        PermanentEmployee alice = new PermanentEmployee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        project.addEmployee(alice);
        
        PermanentEmployee bob = new PermanentEmployee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        project.addEmployee(bob);
        
        PermanentEmployee charlie = new PermanentEmployee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        project.addEmployee(charlie);
        
        // Hire 2 temporary employees
        TemporaryEmployee david = new TemporaryEmployee();
        david.setName("David");
        david.setEmployeeId("E004");
        project.addEmployee(david);
        
        TemporaryEmployee eve = new TemporaryEmployee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        project.addEmployee(eve);
        
        // Calculate total number of employees working on production projects
        int result = company.getNumberOfEmployeesOnProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Create company C002
        Company company = new Company();
        
        // Create first department D001 and add to company
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        // Add production project "Factory Upgrade" with site code "FU456" to D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        // Hire 4 permanent employees for project1
        for (int i = 1; i <= 4; i++) {
            PermanentEmployee emp = new PermanentEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project1.addEmployee(emp);
        }
        
        // Create second department D002 and add to company
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        // Add production project "New Product Development" with site code "NPD789" to D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        // Hire 3 temporary employees for project2
        for (int i = 5; i <= 7; i++) {
            TemporaryEmployee emp = new TemporaryEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project2.addEmployee(emp);
        }
        
        // Calculate total number of employees working on production projects
        int result = company.getNumberOfEmployeesOnProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // Create company C003
        Company company = new Company();
        
        // Create department D003 and add to company
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add a research project (not production project)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // Hire 2 permanent employees
        PermanentEmployee frank = new PermanentEmployee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        project.addEmployee(frank);
        
        PermanentEmployee grace = new PermanentEmployee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        project.addEmployee(grace);
        
        // Calculate total number of employees working on production projects
        int result = company.getNumberOfEmployeesOnProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Create company C004
        Company company = new Company();
        
        // Create department D004 and add to company
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add production project "Process Optimization" with site code "PO101"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        // Hire 2 temporary employees for production project
        TemporaryEmployee temp1 = new TemporaryEmployee();
        temp1.setName("Temp1");
        temp1.setEmployeeId("E011");
        productionProject.addEmployee(temp1);
        
        TemporaryEmployee temp2 = new TemporaryEmployee();
        temp2.setName("Temp2");
        temp2.setEmployeeId("E012");
        productionProject.addEmployee(temp2);
        
        // Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Education Program");
        department.addProject(educationProject);
        
        // Calculate total number of employees working on production projects
        int result = company.getNumberOfEmployeesOnProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Create company C005
        Company company = new Company();
        
        // Create department D005 and add to company
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Hire 3 permanent employees (but no projects assigned)
        PermanentEmployee henry = new PermanentEmployee();
        henry.setName("Henry");
        henry.setEmployeeId("E008");
        department.addEmployee(henry);
        
        PermanentEmployee ian = new PermanentEmployee();
        ian.setName("Ian");
        ian.setEmployeeId("E009");
        department.addEmployee(ian);
        
        PermanentEmployee jack = new PermanentEmployee();
        jack.setName("Jack");
        jack.setEmployeeId("E010");
        department.addEmployee(jack);
        
        // No projects are currently ongoing in this department
        
        // Calculate total number of employees working on production projects
        int result = company.getNumberOfEmployeesOnProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}