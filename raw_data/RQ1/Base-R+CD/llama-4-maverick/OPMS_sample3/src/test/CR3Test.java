import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        
        // Create 3 permanent employees
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setID("E001");
        emp1.setType(EmployeeType.PERMANENT);
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setID("E002");
        emp2.setType(EmployeeType.PERMANENT);
        
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setID("E003");
        emp3.setType(EmployeeType.PERMANENT);
        
        // Create 2 temporary employees
        Employee emp4 = new Employee();
        emp4.setName("David");
        emp4.setID("E004");
        emp4.setType(EmployeeType.TEMPORARY);
        
        Employee emp5 = new Employee();
        emp5.setName("Eve");
        emp5.setID("E005");
        emp5.setType(EmployeeType.TEMPORARY);
        
        // Add all employees to project
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        employees.add(emp5);
        project.setWorkingEmployees(employees);
        
        // Add project to department
        department.addProject(project);
        
        // Calculate and verify result
        int result = company.countEmployeesInProductionProjects();
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
        
        // Add both departments to company
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        
        // Create first production project for department 1
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        
        // Create 4 permanent employees for first project
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.PERMANENT);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        dept1.addProject(project1);
        
        // Create second production project for department 2
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        
        // Create 3 temporary employees for second project
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setName("Employee" + i);
            emp.setID("E00" + i);
            emp.setType(EmployeeType.TEMPORARY);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        dept2.addProject(project2);
        
        // Calculate and verify result
        int result = company.countEmployeesInProductionProjects();
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
        
        // Create 2 permanent employees
        Employee emp1 = new Employee();
        emp1.setName("Frank");
        emp1.setID("E006");
        emp1.setType(EmployeeType.PERMANENT);
        
        Employee emp2 = new Employee();
        emp2.setName("Grace");
        emp2.setID("E007");
        emp2.setType(EmployeeType.PERMANENT);
        
        // Add employees to project
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        project.setWorkingEmployees(employees);
        
        // Add project to department
        department.addProject(project);
        
        // Calculate and verify result
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0 (no production projects)", 0, result);
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
        
        // Create production project with 2 temporary employees
        ProductionProject prodProject = new ProductionProject();
        prodProject.setTitle("Process Optimization");
        prodProject.setSiteCode("PO101");
        
        Employee temp1 = new Employee();
        temp1.setName("Temp1");
        temp1.setID("E011");
        temp1.setType(EmployeeType.TEMPORARY);
        
        Employee temp2 = new Employee();
        temp2.setName("Temp2");
        temp2.setID("E012");
        temp2.setType(EmployeeType.TEMPORARY);
        
        List<Employee> tempEmployees = new ArrayList<>();
        tempEmployees.add(temp1);
        tempEmployees.add(temp2);
        prodProject.setWorkingEmployees(tempEmployees);
        department.addProject(prodProject);
        
        // Create community project (no production project)
        CommunityProject commProject = new CommunityProject();
        commProject.setTitle("Community Outreach");
        
        // Create education project (no production project)
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Training Program");
        
        // Add non-production projects to department
        department.addProject(commProject);
        department.addProject(eduProject);
        
        // Calculate and verify result
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 2 (only from production project)", 2, result);
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
        
        // Create 3 permanent employees but NO projects
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setID("E008");
        emp1.setType(EmployeeType.PERMANENT);
        
        Employee emp2 = new Employee();
        emp2.setName("Ian");
        emp2.setID("E009");
        emp2.setType(EmployeeType.PERMANENT);
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setID("E010");
        emp3.setType(EmployeeType.PERMANENT);
        
        // Add employees to department (but no projects)
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        department.setEmployees(employees);
        
        // Calculate and verify result
        int result = company.countEmployeesInProductionProjects();
        assertEquals("Total number of employees should be 0 (no active projects)", 0, result);
    }
}