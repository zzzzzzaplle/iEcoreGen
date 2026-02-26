import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_SingleDepartmentWithProductionProjects() {
        // Create company C001
        Company company = new Company();
        
        // Create department D001
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
        
        // Create and add 2 temporary employees
        Employee emp4 = new Employee();
        emp4.setID("E004");
        emp4.setName("David");
        emp4.setType(EmployeeType.TEMPORARY);
        
        Employee emp5 = new Employee();
        emp5.setID("E005");
        emp5.setName("Eve");
        emp5.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to the project
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        employees.add(emp5);
        project.setWorkingEmployees(employees);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsWithProductionProjects() {
        // Create company C002
        Company company = new Company();
        
        // Create first department D001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        // Create production project for first department
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.addProject(project1);
        
        // Create and add 4 permanent employees to first project
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
        
        // Create production project for second department
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.addProject(project2);
        
        // Create and add 3 temporary employees to second project
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        
        // Calculate total employees working on production projects across departments
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_DepartmentWithNoProductionProjects() {
        // Create company C003
        Company company = new Company();
        
        // Create department D003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Create research project (not production)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // Create and add 2 permanent employees to the research project
        Employee emp1 = new Employee();
        emp1.setID("E006");
        emp1.setName("Frank");
        emp1.setType(EmployeeType.PERMANENT);
        
        Employee emp2 = new Employee();
        emp2.setID("E007");
        emp2.setName("Grace");
        emp2.setType(EmployeeType.PERMANENT);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        project.setWorkingEmployees(employees);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 0 (no production projects)", 0, result);
    }
    
    @Test
    public void testCase4_DepartmentWithMixedProjectTypes() {
        // Create company C004
        Company company = new Company();
        
        // Create department D004
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Create production project with 2 temporary employees
        ProductionProject prodProject = new ProductionProject();
        prodProject.setTitle("Process Optimization");
        prodProject.setSiteCode("PO101");
        department.addProject(prodProject);
        
        Employee emp1 = new Employee();
        emp1.setID("E011");
        emp1.setName("Temp1");
        emp1.setType(EmployeeType.TEMPORARY);
        
        Employee emp2 = new Employee();
        emp2.setID("E012");
        emp2.setName("Temp2");
        emp2.setType(EmployeeType.TEMPORARY);
        
        List<Employee> prodEmployees = new ArrayList<>();
        prodEmployees.add(emp1);
        prodEmployees.add(emp2);
        prodProject.setWorkingEmployees(prodEmployees);
        
        // Create community project (not production)
        CommunityProject commProject = new CommunityProject();
        commProject.setTitle("Community Outreach");
        department.addProject(commProject);
        
        // Create education project (not production)
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Training Program");
        department.addProject(eduProject);
        
        // Add employees to non-production projects (should not be counted)
        Employee emp3 = new Employee();
        emp3.setID("E013");
        emp3.setName("CommunityEmp");
        emp3.setType(EmployeeType.PERMANENT);
        
        Employee emp4 = new Employee();
        emp4.setID("E014");
        emp4.setName("EducationEmp");
        emp4.setType(EmployeeType.PERMANENT);
        
        List<Employee> commEmployees = new ArrayList<>();
        commEmployees.add(emp3);
        commProject.setWorkingEmployees(commEmployees);
        
        List<Employee> eduEmployees = new ArrayList<>();
        eduEmployees.add(emp4);
        eduProject.setWorkingEmployees(eduEmployees);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 2 (only production project employees)", 2, result);
    }
    
    @Test
    public void testCase5_DepartmentWithoutActiveProjects() {
        // Create company C005
        Company company = new Company();
        
        // Create department D005
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Create 3 permanent employees but no projects
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
        
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        department.setEmployees(employees);
        
        // Calculate total employees working on production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 0 (no projects)", 0, result);
    }
}