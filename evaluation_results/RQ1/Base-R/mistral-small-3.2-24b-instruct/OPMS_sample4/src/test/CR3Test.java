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
        
        // Create department D001 and add to company
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        company.addDepartment(department);
        
        // Create production project
        ProductionProject project = new ProductionProject();
        project.setTitle("Product Launch");
        project.setSiteCode("PL123");
        department.addProject(project);
        
        // Hire 3 permanent employees
        PermanentEmployee emp1 = new PermanentEmployee();
        emp1.setName("Alice");
        emp1.setEmployeeId("E001");
        project.addEmployee(emp1);
        
        PermanentEmployee emp2 = new PermanentEmployee();
        emp2.setName("Bob");
        emp2.setEmployeeId("E002");
        project.addEmployee(emp2);
        
        PermanentEmployee emp3 = new PermanentEmployee();
        emp3.setName("Charlie");
        emp3.setEmployeeId("E003");
        project.addEmployee(emp3);
        
        // Hire 2 temporary employees
        TemporaryEmployee emp4 = new TemporaryEmployee();
        emp4.setName("David");
        emp4.setEmployeeId("E004");
        project.addEmployee(emp4);
        
        TemporaryEmployee emp5 = new TemporaryEmployee();
        emp5.setName("Eve");
        emp5.setEmployeeId("E005");
        project.addEmployee(emp5);
        
        // Calculate and verify total employees on production projects
        int result = company.countEmployeesOnProductionProjects();
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // Create company C002
        Company company = new Company();
        
        // Create first department D001 and add to company
        Department dept1 = new Department();
        dept1.setId("D001");
        dept1.setEmail("department1@example.com");
        company.addDepartment(dept1);
        
        // Add production project to first department with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        dept1.addProject(project1);
        
        for (int i = 1; i <= 4; i++) {
            PermanentEmployee emp = new PermanentEmployee();
            emp.setName("PermEmp" + i);
            emp.setEmployeeId("PE" + i);
            project1.addEmployee(emp);
        }
        
        // Create second department D002 and add to company
        Department dept2 = new Department();
        dept2.setId("D002");
        dept2.setEmail("department2@example.com");
        company.addDepartment(dept2);
        
        // Add production project to second department with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        dept2.addProject(project2);
        
        for (int i = 1; i <= 3; i++) {
            TemporaryEmployee emp = new TemporaryEmployee();
            emp.setName("TempEmp" + i);
            emp.setEmployeeId("TE" + i);
            project2.addEmployee(emp);
        }
        
        // Calculate and verify total employees on production projects
        int result = company.countEmployeesOnProductionProjects();
        assertEquals(7, result);
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // Create company C003
        Company company = new Company();
        
        // Create department D003 and add to company
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        company.addDepartment(department);
        
        // Add research project (not production project)
        ResearchProject project = new ResearchProject();
        project.setTitle("Market Research");
        department.addProject(project);
        
        // Hire 2 permanent employees
        PermanentEmployee emp1 = new PermanentEmployee();
        emp1.setName("Frank");
        emp1.setEmployeeId("E006");
        project.addEmployee(emp1);
        
        PermanentEmployee emp2 = new PermanentEmployee();
        emp2.setName("Grace");
        emp2.setEmployeeId("E007");
        project.addEmployee(emp2);
        
        // Calculate and verify total employees on production projects
        int result = company.countEmployeesOnProductionProjects();
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // Create company C004
        Company company = new Company();
        
        // Create department D004 and add to company
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        company.addDepartment(department);
        
        // Add production project with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        TemporaryEmployee emp1 = new TemporaryEmployee();
        emp1.setName("TempEmp1");
        emp1.setEmployeeId("TE1");
        productionProject.addEmployee(emp1);
        
        TemporaryEmployee emp2 = new TemporaryEmployee();
        emp2.setName("TempEmp2");
        emp2.setEmployeeId("TE2");
        productionProject.addEmployee(emp2);
        
        // Add community project (no production project)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add education project (no production project)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("STEM Education");
        department.addProject(educationProject);
        
        // Hire employees for non-production projects (should not be counted)
        PermanentEmployee emp3 = new PermanentEmployee();
        emp3.setName("PermEmp1");
        emp3.setEmployeeId("PE1");
        communityProject.addEmployee(emp3);
        
        PermanentEmployee emp4 = new PermanentEmployee();
        emp4.setName("PermEmp2");
        emp4.setEmployeeId("PE2");
        educationProject.addEmployee(emp4);
        
        // Calculate and verify total employees on production projects
        int result = company.countEmployeesOnProductionProjects();
        assertEquals(2, result);
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // Create company C005
        Company company = new Company();
        
        // Create department D005 and add to company
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        company.addDepartment(department);
        
        // Department has no projects, so even though employees exist in memory,
        // they are not associated with any project in this department
        // No setup for projects as specified
        
        // Calculate and verify total employees on production projects
        int result = company.countEmployeesOnProductionProjects();
        assertEquals(0, result);
    }
}