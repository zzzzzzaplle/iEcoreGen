import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // Setup
        Company company = new Company(); // C001
        
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Create and add 3 permanent employees
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
        
        // Create and add 2 temporary employees
        Employee david = new Employee();
        david.setName("David");
        david.setEmployeeId("E004");
        productionProject.addEmployee(david);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        productionProject.addEmployee(eve);
        
        // Test
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Verify
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Setup
        Company company = new Company(); // C002
        
        // Department 1
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        // Add 4 permanent employees to department 1's project
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project1.addEmployee(emp);
        }
        
        // Department 2
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        // Add 3 temporary employees to department 2's project
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project2.addEmployee(emp);
        }
        
        // Test
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Verify
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // Setup
        Company company = new Company(); // C003
        
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees for the research project
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        researchProject.addEmployee(frank);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        researchProject.addEmployee(grace);
        
        // Test
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Verify
        assertEquals("Total number of employees should be 0 when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Setup
        Company company = new Company(); // C004
        
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setEmployeeId("ET001");
        productionProject.addEmployee(temp1);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setEmployeeId("ET002");
        productionProject.addEmployee(temp2);
        
        // Add community project (no employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add education project (no employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Test
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Verify
        assertEquals("Total number of employees should be 2 (only from production project)", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Setup
        Company company = new Company(); // C005
        
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Hire 3 permanent employees but no projects
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
        
        // Test
        int result = company.getNumberOfEmployeesWorkingOnProductionProjects();
        
        // Verify
        assertEquals("Total number of employees should be 0 when no projects exist", 0, result);
    }
}