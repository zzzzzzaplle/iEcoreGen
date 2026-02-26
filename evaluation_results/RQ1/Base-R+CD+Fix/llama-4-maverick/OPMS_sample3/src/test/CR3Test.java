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
        // Test Case 1: Count Employees in Single Department with Production Projects in a company
        // SetUp: Create company C001 and department D001
        company = new Company();
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Add production project "Product Launch" with site code "PL123"
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees: Alice, Bob, Charlie
        Employee emp1 = new Employee();
        emp1.setID("E001");
        emp1.setName("Alice");
        emp1.setType(EmployeeType.PERMANENT);
        
        Employee emp2 = new Employee();
        emp2.setID("E002");
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PERMANENT);
        
        Employee emp3 = new Employee();
        emp3.setID("E003");
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.PERMANENT);
        
        // Hire 2 temporary employees: David, Eve
        Employee emp4 = new Employee();
        emp4.setID("E004");
        emp4.setName("David");
        emp4.setType(EmployeeType.TEMPORARY);
        
        Employee emp5 = new Employee();
        emp5.setID("E005");
        emp5.setName("Eve");
        emp5.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to the production project
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        employees.add(emp5);
        productionProject.setWorkingEmployees(employees);
        
        // Expected Output: Total number of employees = 5
        assertEquals("Total number of employees should be 5", 5, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Test Case 2: Count Employees Across Multiple Departments in a company
        // SetUp: Create company C002
        company = new Company();
        
        // Create first department D001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        // Add production project "Factory Upgrade" to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.addProject(project1);
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        
        // Create second department D002
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@example.com");
        company.addDepartment(dept2);
        
        // Add production project "New Product Development" to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.addProject(project2);
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        
        // Expected Output: Total number of employees = 7
        assertEquals("Total number of employees across departments should be 7", 7, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Test Case 3: Count Employees with No Production Projects
        // SetUp: Create company C003 and department D003
        company = new Company();
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add research project "Market Research" (non-production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees: Frank and Grace
        Employee emp1 = new Employee();
        emp1.setID("E006");
        emp1.setName("Frank");
        emp1.setType(EmployeeType.PERMANENT);
        
        Employee emp2 = new Employee();
        emp2.setID("E007");
        emp2.setName("Grace");
        emp2.setType(EmployeeType.PERMANENT);
        
        // Add employees to the research project
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        researchProject.setWorkingEmployees(employees);
        
        // Expected Output: Total number of employees = 0 (since it's not a production project)
        assertEquals("Total number of employees should be 0 when no production projects exist", 0, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Test Case 4: Count Employees with Mixed Project Types in a company
        // SetUp: Create company C004 and department D004
        company = new Company();
        department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add production project "Process Optimization" with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        Employee emp1 = new Employee();
        emp1.setID("E011");
        emp1.setName("TempEmp1");
        emp1.setType(EmployeeType.TEMPORARY);
        
        Employee emp2 = new Employee();
        emp2.setID("E012");
        emp2.setName("TempEmp2");
        emp2.setType(EmployeeType.TEMPORARY);
        
        List<Employee> productionEmployees = new ArrayList<>();
        productionEmployees.add(emp1);
        productionEmployees.add(emp2);
        productionProject.setWorkingEmployees(productionEmployees);
        
        // Add community project (non-production)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add education project (non-production)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Expected Output: Total number of employees = 2 (only from production project)
        assertEquals("Total number of employees should be 2 from production project only", 2, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Test Case 5: Count Employees in Department Without Active Projects in a company
        // SetUp: Create company C005 and department D005
        company = new Company();
        department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Hire 3 permanent employees: Henry, Ian, Jack (but no projects)
        Employee emp1 = new Employee();
        emp1.setID("E008");
        emp1.setName("Henry");
        emp1.setType(EmployeeType.PERMANENT);
        
        Employee emp2 = new Employee();
        emp2.setID("E009");
        emp2.setName("Ian");
        emp2.setType(EmployeeType.PERMANENT);
        
        Employee emp3 = new Employee();
        emp3.setID("E010");
        emp3.setName("Jack");
        emp3.setType(EmployeeType.PERMANENT);
        
        // Add employees to department (but not to any project)
        List<Employee> departmentEmployees = new ArrayList<>();
        departmentEmployees.add(emp1);
        departmentEmployees.add(emp2);
        departmentEmployees.add(emp3);
        department.setEmployees(departmentEmployees);
        
        // No projects are currently ongoing in this department
        
        // Expected Output: Total number of employees = 0 (no production projects)
        assertEquals("Total number of employees should be 0 when department has no projects", 0, company.countEmployeesInProductionProjects());
    }
}