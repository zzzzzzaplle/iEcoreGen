import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        company.getDepartments().add(department);
        
        // Create production project "Product Launch"
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.getProjects().add(project);
        
        // Create and add 3 permanent employees
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setEmployeeId("E001");
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setEmployeeId("E002");
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setEmployeeId("E003");
        
        // Create and add 2 temporary employees
        Employee david = new Employee();
        david.setName("David");
        david.setEmployeeId("E004");
        
        Employee eve = new Employee();
        eve.setName("Eve");
        eve.setEmployeeId("E005");
        
        // Add all employees to the project
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        employees.add(charlie);
        employees.add(david);
        employees.add(eve);
        project.setEmployees(employees);
        
        // Verify the total number of employees working on production projects
        assertEquals(5, company.getNumberOfEmployeesOnProductionProjects());
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // Create company C002
        Company company = new Company();
        
        // Create first department D001
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        company.getDepartments().add(department1);
        
        // Create production project for department1
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.getProjects().add(project1);
        
        // Add 4 permanent employees to project1
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            employees1.add(emp);
        }
        project1.setEmployees(employees1);
        
        // Create second department D002
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        company.getDepartments().add(department2);
        
        // Create production project for department2
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.getProjects().add(project2);
        
        // Add 3 temporary employees to project2
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            employees2.add(emp);
        }
        project2.setEmployees(employees2);
        
        // Verify total number of employees across both departments
        assertEquals(7, company.getNumberOfEmployeesOnProductionProjects());
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // Create company C003
        Company company = new Company();
        
        // Create department D003
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.getDepartments().add(department);
        
        // Add a research project (not production project)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.getProjects().add(project);
        
        // Hire 2 permanent employees for the research project
        Employee frank = new Employee();
        frank.setName("Frank");
        frank.setEmployeeId("E006");
        
        Employee grace = new Employee();
        grace.setName("Grace");
        grace.setEmployeeId("E007");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(frank);
        employees.add(grace);
        project.setEmployees(employees);
        
        // Verify that no employees are counted for production projects
        assertEquals(0, company.getNumberOfEmployeesOnProductionProjects());
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // Create company C004
        Company company = new Company();
        
        // Create department D004
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.getDepartments().add(department);
        
        // Add production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.getProjects().add(productionProject);
        
        List<Employee> productionEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setName("TempEmployee" + i);
            emp.setEmployeeId("TE00" + i);
            productionEmployees.add(emp);
        }
        productionProject.setEmployees(productionEmployees);
        
        // Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.getProjects().add(communityProject);
        
        // Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Employee Training");
        department.getProjects().add(educationProject);
        
        // Verify only production project employees are counted
        assertEquals(2, company.getNumberOfEmployeesOnProductionProjects());
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // Create company C005
        Company company = new Company();
        
        // Create department D005
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.getDepartments().add(department);
        
        // Hire 3 permanent employees but no projects
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setEmployeeId("E008");
        
        Employee ian = new Employee();
        ian.setName("Ian");
        ian.setEmployeeId("E009");
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setEmployeeId("E010");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(henry);
        employees.add(ian);
        employees.add(jack);
        department.setEmployees(employees);
        
        // Department has employees but no projects, so no production project employees
        assertEquals(0, company.getNumberOfEmployeesOnProductionProjects());
    }
}