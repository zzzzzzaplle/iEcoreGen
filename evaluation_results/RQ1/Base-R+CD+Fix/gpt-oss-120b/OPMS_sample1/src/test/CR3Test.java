import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department department;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleDepartmentWithProductionProjects() throws ParseException {
        // Create company C001
        company = new Company();
        
        // Create department D001 and add to company
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Create production project and add to department
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        productionProject.setDeadline(dateFormat.parse("2024-12-31 23:59:59"));
        department.addProject(productionProject);
        
        // Create 3 permanent employees and add to project
        Employee emp1 = new Employee();
        emp1.setID("E001");
        emp1.setName("Alice");
        emp1.setType(EmployeeType.PERMANENT);
        productionProject.addWorkingEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setID("E002");
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PERMANENT);
        productionProject.addWorkingEmployee(emp2);
        
        Employee emp3 = new Employee();
        emp3.setID("E003");
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.PERMANENT);
        productionProject.addWorkingEmployee(emp3);
        
        // Create 2 temporary employees and add to project
        Employee emp4 = new Employee();
        emp4.setID("E004");
        emp4.setName("David");
        emp4.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(emp4);
        
        Employee emp5 = new Employee();
        emp5.setID("E005");
        emp5.setName("Eve");
        emp5.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(emp5);
        
        // Verify total employees count
        assertEquals(5, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase2_MultipleDepartmentsWithProductionProjects() throws ParseException {
        // Create company C002
        company = new Company();
        
        // Create first department D001 and add to company
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        // Create production project for first department and add employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        project1.setDeadline(dateFormat.parse("2024-06-30 18:00:00"));
        dept1.addProject(project1);
        
        // Add 4 permanent employees to first project
        for (int i = 1; i <= 4; i++) {
            Employee emp = new Employee();
            emp.setID("E" + String.format("%03d", i));
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.PERMANENT);
            project1.addWorkingEmployee(emp);
        }
        
        // Create second department D002 and add to company
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@example.com");
        company.addDepartment(dept2);
        
        // Create production project for second department and add employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        project2.setDeadline(dateFormat.parse("2024-09-15 17:30:00"));
        dept2.addProject(project2);
        
        // Add 3 temporary employees to second project
        for (int i = 5; i <= 7; i++) {
            Employee emp = new Employee();
            emp.setID("E" + String.format("%03d", i));
            emp.setName("Employee" + i);
            emp.setType(EmployeeType.TEMPORARY);
            project2.addWorkingEmployee(emp);
        }
        
        // Verify total employees count across both departments
        assertEquals(7, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase3_NoProductionProjects() throws ParseException {
        // Create company C003
        company = new Company();
        
        // Create department D003 and add to company
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Create research project (non-production) and add to department
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        researchProject.setDeadline(dateFormat.parse("2024-08-15 16:00:00"));
        department.addProject(researchProject);
        
        // Create employees but they are not on production projects
        Employee emp1 = new Employee();
        emp1.setID("E006");
        emp1.setName("Frank");
        emp1.setType(EmployeeType.PERMANENT);
        researchProject.addWorkingEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setID("E007");
        emp2.setName("Grace");
        emp2.setType(EmployeeType.PERMANENT);
        researchProject.addWorkingEmployee(emp2);
        
        // Verify no employees counted (since no production projects)
        assertEquals(0, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase4_MixedProjectTypes() throws ParseException {
        // Create company C004
        company = new Company();
        
        // Create department D004 and add to company
        department = new Department();
        department.setID("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Create production project and add 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        productionProject.setDeadline(dateFormat.parse("2024-07-20 14:30:00"));
        department.addProject(productionProject);
        
        Employee emp1 = new Employee();
        emp1.setID("E011");
        emp1.setName("TempEmployee1");
        emp1.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setID("E012");
        emp2.setName("TempEmployee2");
        emp2.setType(EmployeeType.TEMPORARY);
        productionProject.addWorkingEmployee(emp2);
        
        // Create community project (non-production)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        communityProject.setDeadline(dateFormat.parse("2024-10-10 12:00:00"));
        department.addProject(communityProject);
        
        // Create education project (non-production)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        educationProject.setDeadline(dateFormat.parse("2024-11-30 15:45:00"));
        department.addProject(educationProject);
        
        // Verify only employees from production project are counted
        assertEquals(2, company.countEmployeesInProductionProjects());
    }
    
    @Test
    public void testCase5_DepartmentWithoutActiveProjects() {
        // Create company C005
        company = new Company();
        
        // Create department D005 and add to company
        department = new Department();
        department.setID("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Create employees but no projects
        Employee emp1 = new Employee();
        emp1.setID("E008");
        emp1.setName("Henry");
        emp1.setType(EmployeeType.PERMANENT);
        department.addEmployee(emp1);
        
        Employee emp2 = new Employee();
        emp2.setID("E009");
        emp2.setName("Ian");
        emp2.setType(EmployeeType.PERMANENT);
        department.addEmployee(emp2);
        
        Employee emp3 = new Employee();
        emp3.setID("E010");
        emp3.setName("Jack");
        emp3.setType(EmployeeType.PERMANENT);
        department.addEmployee(emp3);
        
        // Verify no employees counted (since no projects at all)
        assertEquals(0, company.countEmployeesInProductionProjects());
    }
}