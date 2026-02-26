import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class CR3Test {
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
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
        emp1.setType(EmployeeType.PERMANENT);
        emp1.setName("Alice");
        emp1.setID("E001");
        
        Employee emp2 = new Employee();
        emp2.setType(EmployeeType.PERMANENT);
        emp2.setName("Bob");
        emp2.setID("E002");
        
        Employee emp3 = new Employee();
        emp3.setType(EmployeeType.PERMANENT);
        emp3.setName("Charlie");
        emp3.setID("E003");
        
        // Create and add 2 temporary employees
        Employee emp4 = new Employee();
        emp4.setType(EmployeeType.TEMPORARY);
        emp4.setName("David");
        emp4.setID("E004");
        
        Employee emp5 = new Employee();
        emp5.setType(EmployeeType.TEMPORARY);
        emp5.setName("Eve");
        emp5.setID("E005");
        
        // Add all employees to the project
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        employees.add(emp5);
        project.setWorkingEmployees(employees);
        
        // Calculate and verify the result
        int result = company.countEmployeesInProductionProjects();
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create company C002
        Company company = new Company();
        
        // Create first department D001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        // Create first production project with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.addProject(project1);
        
        List<Employee> employees1 = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.PERMANENT);
            emp.setID("E00" + i);
            employees1.add(emp);
        }
        project1.setWorkingEmployees(employees1);
        
        // Create second department D002
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@example.com");
        company.addDepartment(dept2);
        
        // Create second production project with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.addProject(project2);
        
        List<Employee> employees2 = new ArrayList<>();
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setID("E00" + i);
            employees2.add(emp);
        }
        project2.setWorkingEmployees(employees2);
        
        // Calculate and verify the result
        int result = company.countEmployeesInProductionProjects();
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create company C003
        Company company = new Company();
        
        // Create department D003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Create research project (not a production project)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // Create and add 2 permanent employees to research project
        Employee emp1 = new Employee();
        emp1.setType(EmployeeType.PERMANENT);
        emp1.setName("Frank");
        emp1.setID("E006");
        
        Employee emp2 = new Employee();
        emp2.setType(EmployeeType.PERMANENT);
        emp2.setName("Grace");
        emp2.setID("E007");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        project.setWorkingEmployees(employees);
        
        // Calculate and verify the result (should be 0 since no production projects)
        int result = company.countEmployeesInProductionProjects();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
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
        
        List<Employee> prodEmployees = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.TEMPORARY);
            emp.setID("E0" + (i + 10));
            prodEmployees.add(emp);
        }
        prodProject.setWorkingEmployees(prodEmployees);
        
        // Create community project (no employees)
        CommunityProject commProject = new CommunityProject();
        commProject.setTitle("Community Outreach");
        department.addProject(commProject);
        
        // Create education project (no employees)
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Educational Program");
        department.addProject(eduProject);
        
        // Calculate and verify the result (should only count production project employees)
        int result = company.countEmployeesInProductionProjects();
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create company C005
        Company company = new Company();
        
        // Create department D005
        Department department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Create employees (but no projects)
        List<Employee> employees = new ArrayList<>();
        for (int i = 8; i <= 10; i++) {
            Employee emp = new Employee();
            emp.setType(EmployeeType.PERMANENT);
            emp.setName((i == 8 ? "Henry" : i == 9 ? "Ian" : "Jack"));
            emp.setID("E0" + i);
            employees.add(emp);
        }
        department.setEmployees(employees);
        
        // Calculate and verify the result (should be 0 since no projects at all)
        int result = company.countEmployeesInProductionProjects();
        assertEquals(0, result);
    }
}