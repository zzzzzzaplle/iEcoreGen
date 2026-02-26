import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // SetUp: Create company and department
        Company company = new Company();
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // SetUp: Add production project
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.addProject(project);
        
        // SetUp: Hire 3 permanent employees
        Employee employee1 = new Employee();
        employee1.setName("Alice");
        employee1.setID("E001");
        employee1.setType(EmployeeType.PERMANENT);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setID("E002");
        employee2.setType(EmployeeType.PERMANENT);
        
        Employee employee3 = new Employee();
        employee3.setName("Charlie");
        employee3.setID("E003");
        employee3.setType(EmployeeType.PERMANENT);
        
        // SetUp: Hire 2 temporary employees
        Employee employee4 = new Employee();
        employee4.setName("David");
        employee4.setID("E004");
        employee4.setType(EmployeeType.TEMPORARY);
        
        Employee employee5 = new Employee();
        employee5.setName("Eve");
        employee5.setID("E005");
        employee5.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to the project
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);
        project.setWorkingEmployees(employees);
        
        // Execute: Count employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // SetUp: Create company
        Company company = new Company();
        
        // SetUp: Create first department with production project and 4 permanent employees
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee employee = new Employee();
            employee.setName("Employee" + i);
            employee.setID("E00" + i);
            employee.setType(EmployeeType.PERMANENT);
            employees1.add(employee);
        }
        project1.setWorkingEmployees(employees1);
        
        // SetUp: Create second department with production project and 3 temporary employees
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee employee = new Employee();
            employee.setName("Employee" + i);
            employee.setID("E00" + i);
            employee.setType(EmployeeType.TEMPORARY);
            employees2.add(employee);
        }
        project2.setWorkingEmployees(employees2);
        
        // Execute: Count employees in production projects across departments
        int result = company.countEmployeesInProductionProjects();
        
        // Verify: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // SetUp: Create company and department
        Company company = new Company();
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // SetUp: Add research project (non-production)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // SetUp: Hire 2 permanent employees
        Employee employee1 = new Employee();
        employee1.setName("Frank");
        employee1.setID("E006");
        employee1.setType(EmployeeType.PERMANENT);
        
        Employee employee2 = new Employee();
        employee2.setName("Grace");
        employee2.setID("E007");
        employee2.setType(EmployeeType.PERMANENT);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        project.setWorkingEmployees(employees);
        
        // Execute: Count employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify: Total number of employees = 0 (no production projects)
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // SetUp: Create company and department
        Company company = new Company();
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // SetUp: Add production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        Employee employee1 = new Employee();
        employee1.setName("Temp1");
        employee1.setID("E011");
        employee1.setType(EmployeeType.TEMPORARY);
        
        Employee employee2 = new Employee();
        employee2.setName("Temp2");
        employee2.setID("E012");
        employee2.setType(EmployeeType.TEMPORARY);
        
        List<Employee> productionEmployees = new ArrayList<>();
        productionEmployees.add(employee1);
        productionEmployees.add(employee2);
        productionProject.setWorkingEmployees(productionEmployees);
        
        // SetUp: Add community project (non-production)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Project");
        department.addProject(communityProject);
        
        // SetUp: Add education project (non-production)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Education Project");
        department.addProject(educationProject);
        
        // Execute: Count employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify: Total number of employees = 2 (only from production project)
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // SetUp: Create company and department
        Company company = new Company();
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // SetUp: The department has 3 permanent employees (but no projects)
        Employee employee1 = new Employee();
        employee1.setName("Henry");
        employee1.setID("E008");
        employee1.setType(EmployeeType.PERMANENT);
        
        Employee employee2 = new Employee();
        employee2.setName("Ian");
        employee2.setID("E009");
        employee2.setType(EmployeeType.PERMANENT);
        
        Employee employee3 = new Employee();
        employee3.setName("Jack");
        employee3.setID("E010");
        employee3.setType(EmployeeType.PERMANENT);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        department.setEmployees(employees);
        
        // Note: No projects are added to the department
        
        // Execute: Count employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify: Total number of employees = 0 (no production projects)
        assertEquals(0, result);
    }
}