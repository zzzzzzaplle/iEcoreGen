import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CountEmployeesInSingleDepartmentWithProductionProjects() {
        // SetUp: Create company C001 and department D001
        Company companyC001 = new Company();
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        companyC001.addDepartment(department);
        
        // Add production project "Product Launch" with site code PL123
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Product Launch");
        productionProject.setSiteCode("PL123");
        department.addProject(productionProject);
        
        // Hire 3 permanent employees: Alice, Bob, Charlie
        PermanentEmployee emp1 = new PermanentEmployee();
        emp1.setName("Alice");
        emp1.setEmployeeId("E001");
        productionProject.addEmployee(emp1);
        
        PermanentEmployee emp2 = new PermanentEmployee();
        emp2.setName("Bob");
        emp2.setEmployeeId("E002");
        productionProject.addEmployee(emp2);
        
        PermanentEmployee emp3 = new PermanentEmployee();
        emp3.setName("Charlie");
        emp3.setEmployeeId("E003");
        productionProject.addEmployee(emp3);
        
        // Hire 2 temporary employees: David, Eve
        TemporaryEmployee emp4 = new TemporaryEmployee();
        emp4.setName("David");
        emp4.setEmployeeId("E004");
        productionProject.addEmployee(emp4);
        
        TemporaryEmployee emp5 = new TemporaryEmployee();
        emp5.setName("Eve");
        emp5.setEmployeeId("E005");
        productionProject.addEmployee(emp5);
        
        // Expected Output: Total number of employees = 5
        assertEquals("Total employees on production projects should be 5", 5, companyC001.getNumberOfEmployeesOnProductionProjects());
    }
    
    @Test
    public void testCase2_CountEmployeesAcrossMultipleDepartments() {
        // SetUp: Create company C002 and two departments
        Company companyC002 = new Company();
        
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@example.com");
        companyC002.addDepartment(department1);
        
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@example.com");
        companyC002.addDepartment(department2);
        
        // Add production project "Factory Upgrade" to D001 with 4 permanent employees
        ProductionProject project1 = new ProductionProject();
        project1.setTitle("Factory Upgrade");
        project1.setSiteCode("FU456");
        department1.addProject(project1);
        
        for (int i = 1; i <= 4; i++) {
            PermanentEmployee emp = new PermanentEmployee();
            emp.setName("Employee" + i);
            emp.setEmployeeId("E00" + i);
            project1.addEmployee(emp);
        }
        
        // Add production project "New Product Development" to D002 with 3 temporary employees
        ProductionProject project2 = new ProductionProject();
        project2.setTitle("New Product Development");
        project2.setSiteCode("NPD789");
        department2.addProject(project2);
        
        for (int i = 5; i <= 7; i++) {
            TemporaryEmployee emp = new TemporaryEmployee();
            emp.setName("TempEmployee" + i);
            emp.setEmployeeId("E00" + i);
            project2.addEmployee(emp);
        }
        
        // Expected Output: Total number of employees = 7
        assertEquals("Total employees on production projects across departments should be 7", 7, companyC002.getNumberOfEmployeesOnProductionProjects());
    }
    
    @Test
    public void testCase3_CountEmployeesWithNoProductionProjects() {
        // SetUp: Create company C003 and department D003
        Company companyC003 = new Company();
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        companyC003.addDepartment(department);
        
        // Add research project "Market Research" (no production project)
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("Market Research");
        department.addProject(researchProject);
        
        // Hire 2 permanent employees: Frank and Grace
        PermanentEmployee emp1 = new PermanentEmployee();
        emp1.setName("Frank");
        emp1.setEmployeeId("E006");
        researchProject.addEmployee(emp1);
        
        PermanentEmployee emp2 = new PermanentEmployee();
        emp2.setName("Grace");
        emp2.setEmployeeId("E007");
        researchProject.addEmployee(emp2);
        
        // Expected Output: Total number of employees = 0
        assertEquals("Total employees on production projects should be 0 when no production projects exist", 0, companyC003.getNumberOfEmployeesOnProductionProjects());
    }
    
    @Test
    public void testCase4_CountEmployeesWithMixedProjectTypes() {
        // SetUp: Create company C004 and department D004
        Company companyC004 = new Company();
        Department department = new Department();
        department.setId("D004");
        department.setEmail("department4@example.com");
        companyC004.addDepartment(department);
        
        // Add production project "Process Optimization" with 2 temporary employees
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Process Optimization");
        productionProject.setSiteCode("PO101");
        department.addProject(productionProject);
        
        TemporaryEmployee emp1 = new TemporaryEmployee();
        emp1.setName("Temp1");
        emp1.setEmployeeId("E011");
        productionProject.addEmployee(emp1);
        
        TemporaryEmployee emp2 = new TemporaryEmployee();
        emp2.setName("Temp2");
        emp2.setEmployeeId("E012");
        productionProject.addEmployee(emp2);
        
        // Add community project (no production project employees)
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Outreach");
        department.addProject(communityProject);
        
        // Add education project (no production project employees)
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Educational Program");
        department.addProject(educationProject);
        
        // Expected Output: Total number of employees = 2
        assertEquals("Total employees on production projects should be 2 with mixed project types", 2, companyC004.getNumberOfEmployeesOnProductionProjects());
    }
    
    @Test
    public void testCase5_CountEmployeesInDepartmentWithoutActiveProjects() {
        // SetUp: Create company C005 and department D005
        Company companyC005 = new Company();
        Department department = new Department();
        department.setId("D005");
        department.setEmail("department5@example.com");
        companyC005.addDepartment(department);
        
        // Department has hired 3 permanent employees but no active projects
        PermanentEmployee emp1 = new PermanentEmployee();
        emp1.setName("Henry");
        emp1.setEmployeeId("E008");
        department.addEmployee(emp1);
        
        PermanentEmployee emp2 = new PermanentEmployee();
        emp2.setName("Ian");
        emp2.setEmployeeId("E009");
        department.addEmployee(emp2);
        
        PermanentEmployee emp3 = new PermanentEmployee();
        emp3.setName("Jack");
        emp3.setEmployeeId("E010");
        department.addEmployee(emp3);
        
        // No projects are currently ongoing in this department
        
        // Expected Output: Total number of employees = 0
        assertEquals("Total employees on production projects should be 0 when department has no projects", 0, companyC005.getNumberOfEmployeesOnProductionProjects());
    }
}