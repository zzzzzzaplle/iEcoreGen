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
        // Create company C001
        Company company = new Company();
        
        // Create department D001 and add to company
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Create production project
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Create and add 3 permanent employees
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
        
        // Create and add 2 temporary employees
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
        
        // Verify total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create company C002
        Company company = new Company();
        
        // Create first department D001 and add to company
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        // Create second department D002 and add to company
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        // Add production project to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.PERMANENT);
            project1.addWorkingEmployee(emp);
        }
        
        // Add production project to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            project2.addWorkingEmployee(emp);
        }
        
        // Verify total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create company C003
        Company company = new Company();
        
        // Create department D003 and add to company
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add research project (not production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Create and add 2 permanent employees to the research project
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
        
        // Verify no employees counted for production projects
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create company C004
        Company company = new Company();
        
        // Create department D004 and add to company
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
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
        department.addProject(communityProject);
        
        // Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Verify only employees from production project are counted
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 2", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create company C005
        Company company = new Company();
        
        // Create department D005 and add to company
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Hire 3 permanent employees but no projects
        Employee henry = new Employee();
        henry.setID("E008");
        henry.setName("Henry");
        henry.setType(EmployeeType.PERMANENT);
        department.addEmployee(henry);
        
        Employee ian = new Employee();
        ian.setID("E009");
        ian.setName("Ian");
        ian.setType(EmployeeType.PERMANENT);
        department.addEmployee(ian);
        
        Employee jack = new Employee();
        jack.setID("E010");
        jack.setName("Jack");
        jack.setType(EmployeeType.PERMANENT);
        department.addEmployee(jack);
        
        // Verify no employees counted since no production projects exist
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
}