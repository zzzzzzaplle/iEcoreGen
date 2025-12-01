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
        // Create company C001
        Company company = new Company();
        
        // Create department D001 and add to company
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
        
        // Verify total number of employees = 5
        assertEquals(5, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create company C002
        Company company = new Company();
        
        // Create first department D001 and add to company
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        // Create production project for first department
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.addProject(project1);
        
        // Add 4 permanent employees to first department's project
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.PERMANENT);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            project1.addWorkingEmployee(emp);
        }
        
        // Create second department D002 and add to company
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@example.com");
        company.addDepartment(dept2);
        
        // Create production project for second department
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.addProject(project2);
        
        // Add 3 temporary employees to second department's project
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            project2.addWorkingEmployee(emp);
        }
        
        // Verify total number of employees = 7
        assertEquals(7, company.countEmployeesInProductionProjects());
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
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // Create and add 2 permanent employees to the research project
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
        
        // Verify total number of employees = 0 (no production projects)
        assertEquals(0, company.countEmployeesInProductionProjects());
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
        ProductionProject prodProject = new ProductionProject();
        prodProject.setTitle("Process Optimization");
        prodProject.setSiteCode("PO101");
        department.addProject(prodProject);
        
        Employee emp1 = new Employee();
        emp1.setType(EmployeeType.TEMPORARY);
        emp1.setName("TempEmp1");
        emp1.setID("TE001");
        prodProject.addWorkingEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setType(EmployeeType.TEMPORARY);
        emp2.setName("TempEmp2");
        emp2.setID("TE002");
        prodProject.addWorkingEmployee(emp2);
        
        // Add community project (no production project employees)
        CommunityProject commProject = new CommunityProject();
        commProject.setTitle("Community Outreach");
        department.addProject(commProject);
        
        // Add education project (no production project employees)
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Educational Program");
        department.addProject(eduProject);
        
        // Verify total number of employees = 2 (only from production project)
        assertEquals(2, company.countEmployeesInProductionProjects());
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
        Employee emp1 = new Employee();
        emp1.setType(EmployeeType.PERMANENT);
        emp1.setName("Henry");
        emp1.setID("E008");
        department.addEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setType(EmployeeType.PERMANENT);
        emp2.setName("Ian");
        emp2.setID("E009");
        department.addEmployee(emp2);
        
        Employee emp3 = new Employee();
        emp3.setType(EmployeeType.PERMANENT);
        emp3.setName("Jack");
        emp3.setID("E010");
        department.addEmployee(emp3);
        
        // Verify total number of employees = 0 (no production projects)
        assertEquals(0, company.countEmployeesInProductionProjects());
    }
}