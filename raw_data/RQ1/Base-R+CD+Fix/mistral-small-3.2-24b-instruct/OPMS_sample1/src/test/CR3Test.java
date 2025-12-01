import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create company C001 and department D001
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Create production project
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.addProject(project);
        
        // Create and add 3 permanent employees
        Employee emp1 = new Employee();
        emp1.setType(EmployeeType.PERMANENT);
        emp1.setName("Alice");
        emp1.setID("E001");
        project.addWorkingEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setType(EmployeeType.PERMANENT);
        emp2.setName("Bob");
        emp2.setID("E002");
        project.addWorkingEmployee(emp2);
        
        Employee emp3 = new Employee();
        emp3.setType(EmployeeType.PERMANENT);
        emp3.setName("Charlie");
        emp3.setID("E003");
        project.addWorkingEmployee(emp3);
        
        // Create and add 2 temporary employees
        Employee emp4 = new Employee();
        emp4.setType(EmployeeType.TEMPORARY);
        emp4.setName("David");
        emp4.setID("E004");
        project.addWorkingEmployee(emp4);
        
        Employee emp5 = new Employee();
        emp5.setType(EmployeeType.TEMPORARY);
        emp5.setName("Eve");
        emp5.setID("E005");
        project.addWorkingEmployee(emp5);
        
        // Calculate total employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected result
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create company C002
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@example.com");
        company.addDepartment(dept2);
        
        // Add production project to first department with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.addProject(project1);
        
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.PERMANENT);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            project1.addWorkingEmployee(emp);
        }
        
        // Add production project to second department with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.addProject(project2);
        
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            project2.addWorkingEmployee(emp);
        }
        
        // Calculate total employees in production projects across departments
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected result
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create company C003 and department D003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add research project (non-production)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // Add 2 permanent employees to the research project
        Employee emp1 = new Employee();
        emp1.setType(EmployeeType.PERMANENT);
        emp1.setName("Frank");
        emp1.setID("E006");
        project.addWorkingEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setType(EmployeeType.PERMANENT);
        emp2.setName("Grace");
        emp2.setID("E007");
        project.addWorkingEmployee(emp2);
        
        // Calculate total employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected result (should be 0 since no production projects)
        assertEquals("Total number of employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create company C004 and department D004
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add production project with 2 temporary employees
        ProductionProject prodProject = new ProductionProject();
        prodProject.setTitle("Process Optimization");
        prodProject.setSiteCode("PO101");
        department.addProject(prodProject);
        
        Employee emp1 = new Employee();
        emp1.setType(EmployeeType.TEMPORARY);
        emp1.setName("TempEmployee1");
        emp1.setID("TE001");
        prodProject.addWorkingEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setType(EmployeeType.TEMPORARY);
        emp2.setName("TempEmployee2");
        emp2.setID("TE002");
        prodProject.addWorkingEmployee(emp2);
        
        // Add community project (non-production)
        CommunityProject commProject = new CommunityProject();
        commProject.setTitle("Community Outreach");
        department.addProject(commProject);
        
        // Add education project (non-production)
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Educational Program");
        department.addProject(eduProject);
        
        // Calculate total employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected result (only production project employees should be counted)
        assertEquals("Total number of employees should be 2", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create company C005 and department D005
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Add 3 permanent employees to department (but not assigned to any project)
        Employee emp1 = new Employee();
        emp1.setType(EmployeeType.PERMANENT);
        emp1.setName("Henry");
        emp1.setID("E008");
        
        Employee emp2 = new Employee();
        emp2.setType(EmployeeType.PERMANENT);
        emp2.setName("Ian");
        emp2.setID("E009");
        
        Employee emp3 = new Employee();
        emp3.setType(EmployeeType.PERMANENT);
        emp3.setName("Jack");
        emp3.setID("E010");
        
        // Note: Employees are not added to any project since department has no projects
        
        // Calculate total employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected result (should be 0 since no projects exist)
        assertEquals("Total number of employees should be 0", 0, result);
    }
}