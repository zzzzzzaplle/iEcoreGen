import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // Set up test data
        Company company = new Company();
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        
        // Create and add employees
        List<Employee> employees = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setID("E001");
        emp1.setName("Alice");
        emp1.setType(EmployeeType.PERMANENT);
        employees.add(emp1);
        
        Employee emp2 = new Employee();
        emp2.setID("E002");
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PERMANENT);
        employees.add(emp2);
        
        Employee emp3 = new Employee();
        emp3.setID("E003");
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.PERMANENT);
        employees.add(emp3);
        
        Employee emp4 = new Employee();
        emp4.setID("E004");
        emp4.setName("David");
        emp4.setType(EmployeeType.TEMPORARY);
        employees.add(emp4);
        
        Employee emp5 = new Employee();
        emp5.setID("E005");
        emp5.setName("Eve");
        emp5.setType(EmployeeType.TEMPORARY);
        employees.add(emp5);
        
        project.setWorkingEmployees(employees);
        department.addProject(project);
        company.addDepartment(department);
        
        // Execute and verify
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 5", 5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Set up test data
        Company company = new Company();
        
        // Department 1
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        
        // Add 4 permanent employees to project1
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        dept1.addProject(project1);
        company.addDepartment(dept1);
        
        // Department 2
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@example.com");
        
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        
        // Add 3 temporary employees to project2
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setID("E00" + i);
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        dept2.addProject(project2);
        company.addDepartment(dept2);
        
        // Execute and verify
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 7", 7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Set up test data
        Company company = new Company();
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        
        // Add employees to research project (non-production)
        List<Employee> employees = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setID("E006");
        emp1.setName("Frank");
        emp1.setType(EmployeeType.PERMANENT);
        employees.add(emp1);
        
        Employee emp2 = new Employee();
        emp2.setID("E007");
        emp2.setName("Grace");
        emp2.setType(EmployeeType.PERMANENT);
        employees.add(emp2);
        
        project.setWorkingEmployees(employees);
        department.addProject(project);
        company.addDepartment(department);
        
        // Execute and verify
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Set up test data
        Company company = new Company();
        Department department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        
        // Production project with 2 employees
        ProductionProject prodProject = new ProductionProject();
        prodProject.setTitle("Process Optimization");
        prodProject.setSiteCode("PO101");
        
        List<Employee> prodEmployees = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setID("E011");
        emp1.setName("Temp1");
        emp1.setType(EmployeeType.TEMPORARY);
        prodEmployees.add(emp1);
        
        Employee emp2 = new Employee();
        emp2.setID("E012");
        emp2.setName("Temp2");
        emp2.setType(EmployeeType.TEMPORARY);
        prodEmployees.add(emp2);
        
        prodProject.setWorkingEmployees(prodEmployees);
        department.addProject(prodProject);
        
        // Community project (non-production)
        CommunityProject commProject = new CommunityProject();
        commProject.setTitle("Community Project");
        
        // Education project (non-production)
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Education Project");
        
        department.addProject(commProject);
        department.addProject(eduProject);
        company.addDepartment(department);
        
        // Execute and verify
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 2", 2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Set up test data
        Company company = new Company();
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        
        // Add employees to department (but no projects)
        List<Employee> employees = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setID("E008");
        emp1.setName("Henry");
        emp1.setType(EmployeeType.PERMANENT);
        employees.add(emp1);
        
        Employee emp2 = new Employee();
        emp2.setID("E009");
        emp2.setName("Ian");
        emp2.setType(EmployeeType.PERMANENT);
        employees.add(emp2);
        
        Employee emp3 = new Employee();
        emp3.setID("E010");
        emp3.setName("Jack");
        emp3.setType(EmployeeType.PERMANENT);
        employees.add(emp3);
        
        department.setEmployees(employees);
        company.addDepartment(department);
        
        // Execute and verify
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0", 0, result);
    }
}