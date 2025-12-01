import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        Company company = new Company();
        
        // Create department D001
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create production project
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        
        // Create and add 3 permanent employees
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
        
        // Create and add 2 temporary employees
        Employee david = new Employee();
        david.setID("E004");
        david.setName("David");
        david.setType(EmployeeType.TEMPORARY);
        
        Employee eve = new Employee();
        eve.setID("E005");
        eve.setName("Eve");
        eve.setType(EmployeeType.TEMPORARY);
        
        // Add employees to project
        List<Employee> projectEmployees = new ArrayList<>();
        projectEmployees.add(alice);
        projectEmployees.add(bob);
        projectEmployees.add(charlie);
        projectEmployees.add(david);
        projectEmployees.add(eve);
        productionProject.setWorkingEmployees(projectEmployees);
        
        // Add project to department
        department.addProject(productionProject);
        
        // Add department to company
        company.addDepartment(department);
        
        // Verify total employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 5 employees working on production projects", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        Company company = new Company();
        
        // Create first department D001
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        
        // Create production project for first department
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        
        // Add 4 permanent employees to first project
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setID("E" + String.format("%03d", i));
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        department1.addProject(project1);
        
        // Create second department D002
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        
        // Create production project for second department
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        
        // Add 3 temporary employees to second project
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setID("E" + String.format("%03d", i));
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        department2.addProject(project2);
        
        // Add both departments to company
        company.addDepartment(department1);
        company.addDepartment(department2);
        
        // Verify total employees working on production projects across departments
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 7 employees working on production projects across departments", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        Company company = new Company();
        
        // Create department D003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Create research project (non-production)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        
        // Create and add 2 permanent employees
        Employee frank = new Employee();
        frank.setID("E006");
        frank.setName("Frank");
        frank.setType(EmployeeType.PERMANENT);
        
        Employee grace = new Employee();
        grace.setID("E007");
        grace.setName("Grace");
        grace.setType(EmployeeType.PERMANENT);
        
        // Add employees to research project
        List<Employee> projectEmployees = new ArrayList<>();
        projectEmployees.add(frank);
        projectEmployees.add(grace);
        researchProject.setWorkingEmployees(projectEmployees);
        
        // Add research project to department
        department.addProject(researchProject);
        
        // Add department to company
        company.addDepartment(department);
        
        // Verify no employees counted for production projects
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 0 employees when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        Company company = new Company();
        
        // Create department D004
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        
        // Create production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        
        List<Employee> productionEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setID("E" + String.format("%03d", i));
            emp.setName("TempEmployee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            productionEmployees.add(emp);
        }
        productionProject.setWorkingEmployees(productionEmployees);
        
        // Create community project
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        
        // Create education project
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        
        // Add all projects to department
        department.addProject(productionProject);
        department.addProject(communityProject);
        department.addProject(educationProject);
        
        // Add department to company
        company.addDepartment(department);
        
        // Verify only production project employees are counted
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 2 employees from production project only", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        Company company = new Company();
        
        // Create department D005
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        
        // Create and add 3 permanent employees to department (but no projects)
        List<Employee> departmentEmployees = new ArrayList<>();
        Employee henry = new Employee();
        henry.setID("E008");
        henry.setName("Henry");
        henry.setType(EmployeeType.PERMANENT);
        departmentEmployees.add(henry);
        
        Employee ian = new Employee();
        ian.setID("E009");
        ian.setName("Ian");
        ian.setType(EmployeeType.PERMANENT);
        departmentEmployees.add(ian);
        
        Employee jack = new Employee();
        jack.setID("E010");
        jack.setName("Jack");
        jack.setType(EmployeeType.PERMANENT);
        departmentEmployees.add(jack);
        
        department.setEmployees(departmentEmployees);
        
        // Add department to company
        company.addDepartment(department);
        
        // Verify no employees counted when no projects exist
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Should count 0 employees when no projects exist in department", 0, result);
    }
}