import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create a company C001
        Company company = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com, and add the department to the company C001
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees named Alice, Bob, and Charlie with employee IDs: E001, E002, and E003, respectively, for the project
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
        
        // Hire 2 temporary employees named David and Eve with employee IDs: E004 and E005, respectively, for the project
        Employee employee4 = new Employee();
        employee4.setName("David");
        employee4.setID("E004");
        employee4.setType(EmployeeType.TEMPORARY);
        
        Employee employee5 = new Employee();
        employee5.setName("Eve");
        employee5.setID("E005");
        employee5.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to the production project
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);
        productionProject.setWorkingEmployees(employees);
        
        // Calculate the number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create a company C002
        Company company = new Company();
        
        // Create two departments: D001 (email: department1@example.com) and D002 (email: department2@example.com), and add the department to the company C002
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        company.addDepartment(department1);
        
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        company.addDepartment(department2);
        
        // Add a production project titled "Factory Upgrade" with site code "FU456" to D001 and hire 4 permanent employees
        ProductionProject productionProject1 = new ProductionProject();
        productionProject1.setTitle("Factory Upgrade");
        productionProject1.setSiteCode("FU456");
        department1.addProject(productionProject1);
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee employee = new Employee();
            employee.setName("Employee" + i);
            employee.setID("E00" + i);
            employee.setType(EmployeeType.PERMANENT);
            employees1.add(employee);
        }
        productionProject1.setWorkingEmployees(employees1);
        
        // Add a production project titled "New Product Development" with site code "NPD789" to D002 and hire 3 temporary employees
        ProductionProject productionProject2 = new ProductionProject();
        productionProject2.setTitle("New Product Development");
        productionProject2.setSiteCode("NPD789");
        department2.addProject(productionProject2);
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee employee = new Employee();
            employee.setName("Employee" + i);
            employee.setID("E00" + i);
            employee.setType(EmployeeType.TEMPORARY);
            employees2.add(employee);
        }
        productionProject2.setWorkingEmployees(employees2);
        
        // Calculate the number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create a company C003
        Company company = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com, and add the department to the company C003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add a research project titled "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees named Frank and Grace with employee IDs: E006 and E007, respectively
        Employee employee1 = new Employee();
        employee1.setName("Frank");
        employee1.setID("E006");
        employee1.setType(EmployeeType.PERMANENT);
        
        Employee employee2 = new Employee();
        employee2.setName("Grace");
        employee2.setID("E007");
        employee2.setType(EmployeeType.PERMANENT);
        
        // Add employees to the research project
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        researchProject.setWorkingEmployees(employees);
        
        // Calculate the number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals("Total number of employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create a company C004
        Company company = new Company();
        
        // Create a department with ID: D004 and email: department4@example.com, and add the department to the company C004
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add a production project titled "Process Optimization" with site code "PO101" and hire 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        List<Employee> productionEmployees = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setName("Temp1");
        employee1.setID("E011");
        employee1.setType(EmployeeType.TEMPORARY);
        
        Employee employee2 = new Employee();
        employee2.setName("Temp2");
        employee2.setID("E012");
        employee2.setType(EmployeeType.TEMPORARY);
        
        productionEmployees.add(employee1);
        productionEmployees.add(employee2);
        productionProject.setWorkingEmployees(productionEmployees);
        
        // Add a community project and an education project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Project");
        department.addProject(communityProject);
        
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Education Project");
        department.addProject(educationProject);
        
        // Add employees to non-production projects (should not be counted)
        List<Employee> nonProductionEmployees = new ArrayList<>();
        Employee employee3 = new Employee();
        employee3.setName("NonProd1");
        employee3.setID("E013");
        employee3.setType(EmployeeType.PERMANENT);
        
        Employee employee4 = new Employee();
        employee4.setName("NonProd2");
        employee4.setID("E014");
        employee4.setType(EmployeeType.PERMANENT);
        
        nonProductionEmployees.add(employee3);
        nonProductionEmployees.add(employee4);
        communityProject.setWorkingEmployees(nonProductionEmployees);
        
        // Calculate the number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals("Total number of employees should be 2", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create a company C005
        Company company = new Company();
        
        // Create a department with ID: D005 and email: department5@example.com, and add the department to the company C005
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // The department has previously hired 3 permanent employees named Henry, Ian, and Jack with employee IDs: E008, E009, and E010, respectively
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
        
        // Add employees to department (but not to any project)
        List<Employee> departmentEmployees = new ArrayList<>();
        departmentEmployees.add(employee1);
        departmentEmployees.add(employee2);
        departmentEmployees.add(employee3);
        department.setEmployees(departmentEmployees);
        
        // No projects are currently ongoing in this department
        
        // Calculate the number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals("Total number of employees should be 0", 0, result);
    }
}