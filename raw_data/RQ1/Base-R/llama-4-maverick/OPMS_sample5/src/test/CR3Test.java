import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create company C001
        Company company = new Company();
        
        // Create department D001 and add to company
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Create production project and add to department
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.addProject(project);
        
        // Create and add 3 permanent employees
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        project.addEmployee(alice);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        project.addEmployee(bob);
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        project.addEmployee(charlie);
        
        // Create and add 2 temporary employees
        Employee david = new Employee();
        david.setName("David");
        david.setEmployeeId("E004");
        project.addEmployee(david);
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        project.addEmployee(eve);
        
        // Calculate total employees on production projects
        int result = company.countEmployeesOnProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create company C002
        Company company = new Company();
        
        // Create first department D001 and add to company
        Department dept1 = new Department();
        dept1.setId("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        // Add production project to first department with 4 employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.addProject(project1);
        
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project1.addEmployee(emp);
        }
        
        // Create second department D002 and add to company
        Department dept2 = new Department();
        dept2.setId("D002");
        dept2.setEmail("department2@example.com");
        company.addDepartment(dept2);
        
        // Add production project to second department with 3 employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.addProject(project2);
        
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project2.addEmployee(emp);
        }
        
        // Calculate total employees on production projects
        int result = company.countEmployeesOnProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create company C003
        Company company = new Company();
        
        // Create department D003 and add to company
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add research project (not production project)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // Add 2 employees to the research project
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        project.addEmployee(frank);
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        project.addEmployee(grace);
        
        // Calculate total employees on production projects
        int result = company.countEmployeesOnProductionProjects();
        
        // Verify expected output (should be 0 since no production projects)
        assertEquals("Total number of employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create company C004
        Company company = new Company();
        
        // Create department D004 and add to company
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add production project with 2 employees
        ProductionProject prodProject = new ProductionProject();
        prodProject.setTitle("Process Optimization");
        prodProject.setSiteCode("PO101");
        department.addProject(prodProject);
        
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setEmployeeId("E011");
        prodProject.addEmployee(temp1);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setEmployeeId("E012");
        prodProject.addEmployee(temp2);
        
        // Add community project (should not be counted)
        CommunityProject commProject = new CommunityProject();
        commProject.setTitle("Community Outreach");
        department.addProject(commProject);
        
        Employee commEmp = new Employee();
        commEmp.setName("CommunityEmp");
        commEmp.setEmployeeId("E013");
        commProject.addEmployee(commEmp);
        
        // Add education project (should not be counted)
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Educational Program");
        department.addProject(eduProject);
        
        Employee eduEmp = new Employee();
        eduEmp.setName("EducationEmp");
        eduEmp.setEmployeeId("E014");
        eduProject.addEmployee(eduEmp);
        
        // Calculate total employees on production projects
        int result = company.countEmployeesOnProductionProjects();
        
        // Verify expected output (only production project employees should be counted)
        assertEquals("Total number of employees should be 2", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create company C005
        Company company = new Company();
        
        // Create department D005 and add to company
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Add 3 employees to department (but no projects)
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
        
        // Calculate total employees on production projects
        int result = company.countEmployeesOnProductionProjects();
        
        // Verify expected output (should be 0 since no projects exist)
        assertEquals("Total number of employees should be 0", 0, result);
    }
}