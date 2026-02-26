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
        company = new Company();
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create a company C001
        company = new Company();
        
        // Create a department with ID: D001 and email: department1@example.com
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Add the department to the company C001
        company.addDepartment(department);
        
        // Add a production project titled "Product Launch" with site code "PL123" to the department
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees named Alice, Bob, and Charlie
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
        
        // Hire 2 temporary employees named David and Eve
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
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create a company C002
        company = new Company();
        
        // Create two departments: D001 and D002
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        
        // Add departments to the company C002
        company.addDepartment(department1);
        company.addDepartment(department2);
        
        // Add a production project titled "Factory Upgrade" with site code "FU456" to D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        // Hire 4 permanent employees for D001's project
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        
        // Add a production project titled "New Product Development" with site code "NPD789" to D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        // Hire 3 temporary employees for D002's project
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 7
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create a company C003
        company = new Company();
        
        // Create a department with ID: D003 and email: department3@example.com
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Add the department to the company C003
        company.addDepartment(department);
        
        // Add a research project titled "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees named Frank and Grace
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
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create a company C004
        company = new Company();
        
        // Create a department with ID: D004 and email: department4@example.com
        department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        
        // Add the department to the company C004
        company.addDepartment(department);
        
        // Add a production project titled "Process Optimization" with site code "PO101"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        // Hire 2 temporary employees for the production project
        List<Employee> productionEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setName("TempEmployee" + i);
            emp.setID("TE00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            productionEmployees.add(emp);
        }
        productionProject.setWorkingEmployees(productionEmployees);
        
        // Add a community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add an education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Hire employees for non-production projects (should not be counted)
        List<Employee> nonProductionEmployees = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Employee emp = new Employee();
            emp.setName("NonProdEmployee" + i);
            emp.setID("NE00" + i);
            emp.setType(EmployeeType.PERMANENT);
            nonProductionEmployees.add(emp);
        }
        communityProject.setWorkingEmployees(nonProductionEmployees);
        educationProject.setWorkingEmployees(new ArrayList<>(nonProductionEmployees));
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create a company C005
        company = new Company();
        
        // Create a department with ID: D005 and email: department5@example.com
        department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        
        // Add the department to the company C005
        company.addDepartment(department);
        
        // The department has previously hired 3 permanent employees
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
        
        // Add employees to department (but no projects)
        List<Employee> departmentEmployees = new ArrayList<>();
        departmentEmployees.add(employee1);
        departmentEmployees.add(employee2);
        departmentEmployees.add(employee3);
        department.setEmployees(departmentEmployees);
        
        // No projects are currently ongoing in this department
        
        // Calculate total number of employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Expected Output: Total number of employees = 0
        assertEquals(0, result);
    }
}