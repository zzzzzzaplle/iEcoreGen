import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Create company C001
        Company company = new Company();
        
        // Create department D001
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Add department to company
        company.addDepartment(department);
        
        // Create production project
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        
        // Add project to department
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
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        employees.add(emp5);
        project.setWorkingEmployees(employees);
        
        // Calculate total employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create company C002
        Company company = new Company();
        
        // Create first department D001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        
        // Create second department D002
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@example.com");
        
        // Add departments to company
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        
        // Create first production project for D001
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        
        // Add 4 permanent employees to first project
        ArrayList<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        dept1.addProject(project1);
        
        // Create second production project for D002
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        
        // Add 3 temporary employees to second project
        ArrayList<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        dept2.addProject(project2);
        
        // Calculate total employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create company C003
        Company company = new Company();
        
        // Create department D003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Add department to company
        company.addDepartment(department);
        
        // Create research project (not production project)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        
        // Add 2 permanent employees to research project
        Employee emp1 = new Employee();
        emp1.setID("E006");
        emp1.setName("Frank");
        emp1.setType(EmployeeType.PERMANENT);
        
        Employee emp2 = new Employee();
        emp2.setID("E007");
        emp2.setName("Grace");
        emp2.setType(EmployeeType.PERMANENT);
        
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        project.setWorkingEmployees(employees);
        
        department.addProject(project);
        
        // Calculate total employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 0 when no production projects exist", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create company C004
        Company company = new Company();
        
        // Create department D004
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        
        // Add department to company
        company.addDepartment(department);
        
        // Create production project
        ProductionProject prodProject = new ProductionProject();
        prodProject.setTitle("Process Optimization");
        prodProject.setSiteCode("PO101");
        
        // Add 2 temporary employees to production project
        Employee emp1 = new Employee();
        emp1.setID("E011");
        emp1.setName("Temp1");
        emp1.setType(EmployeeType.TEMPORARY);
        
        Employee emp2 = new Employee();
        emp2.setID("E012");
        emp2.setName("Temp2");
        emp2.setType(EmployeeType.TEMPORARY);
        
        ArrayList<Employee> prodEmployees = new ArrayList<>();
        prodEmployees.add(emp1);
        prodEmployees.add(emp2);
        prodProject.setWorkingEmployees(prodEmployees);
        
        // Create community project (not production)
        CommunityProject commProject = new CommunityProject();
        commProject.setTitle("Community Outreach");
        
        // Create education project (not production)
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Training Program");
        
        // Add all projects to department
        department.addProject(prodProject);
        department.addProject(commProject);
        department.addProject(eduProject);
        
        // Calculate total employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 2 (only production project employees counted)", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create company C005
        Company company = new Company();
        
        // Create department D005
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        
        // Add department to company
        company.addDepartment(department);
        
        // Hire 3 permanent employees (but no projects assigned)
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
        
        // Employees are hired but not assigned to any project (department has no projects)
        ArrayList<Employee> departmentEmployees = new ArrayList<>();
        departmentEmployees.add(emp1);
        departmentEmployees.add(emp2);
        departmentEmployees.add(emp3);
        department.setEmployees(departmentEmployees);
        
        // Calculate total employees in production projects
        int result = company.countEmployeesInProductionProjects();
        
        // Verify expected output
        assertEquals("Total number of employees should be 0 when no projects exist", 0, result);
    }
}