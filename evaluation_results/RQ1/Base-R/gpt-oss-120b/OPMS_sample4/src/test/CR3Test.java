import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {

    private Company company;
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create a company C001
        company = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com
        Department department = new Department();
        department.setDepartmentId("D001");
        department.setEmail("department1@example.com");
        
        // Add the department to the company C001
        company.getDepartments().add(department);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.getProjects().add(productionProject);
        
        // Hire 3 permanent employees named Alice, Bob, and Charlie
        PermanentEmployee alice = new PermanentEmployee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        
        PermanentEmployee bob = new PermanentEmployee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        
        PermanentEmployee charlie = new PermanentEmployee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        
        // Hire 2 temporary employees named David and Eve
        TemporaryEmployee david = new TemporaryEmployee();
        david.setName("David");
        david.setEmployeeId("E004");
        
        TemporaryEmployee eve = new TemporaryEmployee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        
        // Add all employees to the production project
        productionProject.getEmployees().add(alice);
        productionProject.getEmployees().add(bob);
        productionProject.getEmployees().add(charlie);
        productionProject.getEmployees().add(david);
        productionProject.getEmployees().add(eve);
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, company.getNumberOfEmployeesWorkingOnProductionProjects());
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create a company C002
        company = new Company();
        
        // Create two departments: D001 and D002
        Department department1 = new Department();
        department1.setDepartmentId("D001");
        department1.setEmail("department1@example.com");
        
        Department department2 = new Department();
        department2.setDepartmentId("D002");
        department2.setEmail("department2@example.com");
        
        // Add departments to the company C002
        company.getDepartments().add(department1);
        company.getDepartments().add(department2);
        
        // Add a production project titled "Factory Upgrade" with site code "FU456" to D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.getProjects().add(project1);
        
        // Hire 4 permanent employees for project1
        for (int i = 1; i <= 4; i++) {
            PermanentEmployee emp = new PermanentEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project1.getEmployees().add(emp);
        }
        
        // Add a production project titled "New Product Development" with site code "NPD789" to D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.getProjects().add(project2);
        
        // Hire 3 temporary employees for project2
        for (int i = 5; i <= 7; i++) {
            TemporaryEmployee emp = new TemporaryEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project2.getEmployees().add(emp);
        }
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, company.getNumberOfEmployeesWorkingOnProductionProjects());
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create a company C003
        company = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com
        Department department = new Department();
        department.setDepartmentId("D003");
        department.setEmail("department3@example.com");
        
        // Add the department to the company C003
        company.getDepartments().add(department);
        
        // Add a research project titled "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.getProjects().add(researchProject);
        
        // Hire 2 permanent employees named Frank and Grace
        PermanentEmployee frank = new PermanentEmployee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        
        PermanentEmployee grace = new PermanentEmployee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        
        // Add employees to the research project (not a production project)
        researchProject.getEmployees().add(frank);
        researchProject.getEmployees().add(grace);
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, company.getNumberOfEmployeesWorkingOnProductionProjects());
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create a company C004
        company = new Company();
        
        // Create a department with ID: D004 and email: department4@example.com
        Department department = new Department();
        department.setDepartmentId("D004");
        department.setEmail("department4@example.com");
        
        // Add the department to the company C004
        company.getDepartments().add(department);
        
        // Add a production project titled "Process Optimization" with site code "PO101"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.getProjects().add(productionProject);
        
        // Hire 2 temporary employees for the production project
        TemporaryEmployee temp1 = new TemporaryEmployee();
        temp1.setName("Temp1");
        temp1.setEmployeeId("E011");
        
        TemporaryEmployee temp2 = new TemporaryEmployee();
        temp2.setName("Temp2");
        temp2.setEmployeeId("E012");
        
        productionProject.getEmployees().add(temp1);
        productionProject.getEmployees().add(temp2);
        
        // Add a community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.getProjects().add(communityProject);
        
        // Add an education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Training Program");
        department.getProjects().add(educationProject);
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, company.getNumberOfEmployeesWorkingOnProductionProjects());
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create a company C005
        company = new Company();
        
        // Create a department with ID: D005 and email: department5@example.com
        Department department = new Department();
        department.setDepartmentId("D005");
        department.setEmail("department5@example.com");
        
        // Add the department to the company C005
        company.getDepartments().add(department);
        
        // The department has previously hired 3 permanent employees but no projects
        // Employees are not assigned to any project since there are no projects
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, company.getNumberOfEmployeesWorkingOnProductionProjects());
    }
}