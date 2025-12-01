import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() {
        // Create company C001
        Company company = new Company();
        
        // Create department D001 and add to company
        Department department = new Department();
        department.setDepartmentId("D001");
        department.setEmail("department1@company.com");
        
        // Create first project: Website Redevelopment
        Project project1 = new Project();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudgetAmount(10000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 31));
        
        // Create second project: Mobile App Development
        Project project2 = new Project();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudgetAmount(15000.0);
        project2.setDeadline(LocalDate.of(2026, 1, 15));
        
        // Add projects to department
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        department.setProjects(projects);
        
        // Add department to company
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        company.setDepartments(departments);
        
        // Calculate total budget
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Verify expected output: 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Create company C002
        Company company = new Company();
        
        // Create department D001
        Department department1 = new Department();
        department1.setDepartmentId("D001");
        department1.setEmail("department1@company.com");
        
        // Create project in D001: HR Software
        Project project1 = new Project();
        project1.setTitle("HR Software");
        project1.setBudgetAmount(20000.0);
        
        List<Project> projects1 = new ArrayList<>();
        projects1.add(project1);
        department1.setProjects(projects1);
        
        // Create department D002
        Department department2 = new Department();
        department2.setDepartmentId("D002");
        department2.setEmail("department2@company.com");
        
        // Create first project in D002: Sales Training Program
        Project project2 = new Project();
        project2.setTitle("Sales Training Program");
        project2.setBudgetAmount(30000.0);
        
        // Create second project in D002: Marketing Campaign
        Project project3 = new Project();
        project3.setTitle("Marketing Campaign");
        project3.setBudgetAmount(25000.0);
        
        List<Project> projects2 = new ArrayList<>();
        projects2.add(project2);
        projects2.add(project3);
        department2.setProjects(projects2);
        
        // Add both departments to company
        List<Department> departments = new ArrayList<>();
        departments.add(department1);
        departments.add(department2);
        company.setDepartments(departments);
        
        // Calculate total budget
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Verify expected output: 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create company C003
        Company company = new Company();
        
        // Create department D003 with no projects
        Department department = new Department();
        department.setDepartmentId("D003");
        department.setEmail("department3@company.com");
        
        // Add department to company
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        company.setDepartments(departments);
        
        // Calculate total budget
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Verify expected output: 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Create company C004
        Company company = new Company();
        
        // Create department D004
        Department department1 = new Department();
        department1.setDepartmentId("D004");
        department1.setEmail("department4@company.com");
        
        // Create education project: Scholarship Program with government funding group
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline(LocalDate.of(2026, 5, 31));
        
        GovernmentGroup fundingGroup1 = new GovernmentGroup();
        project1.setFundingGroup(fundingGroup1);
        
        // Create second project: R&D Initiative
        Project project2 = new Project();
        project2.setTitle("R&D Initiative");
        project2.setBudgetAmount(70000.0);
        project2.setDeadline(LocalDate.of(2026, 7, 15));
        
        List<Project> projects1 = new ArrayList<>();
        projects1.add(project1);
        projects1.add(project2);
        department1.setProjects(projects1);
        
        // Create department D005
        Department department2 = new Department();
        department2.setDepartmentId("D005");
        department2.setEmail("department5@company.com");
        
        // Create project in D005: R&D5 Initiative
        Project project3 = new Project();
        project3.setTitle("R&D5 Initiative");
        project3.setBudgetAmount(70000.0);
        project3.setDeadline(LocalDate.of(2026, 7, 19));
        
        List<Project> projects2 = new ArrayList<>();
        projects2.add(project3);
        department2.setProjects(projects2);
        
        // Add both departments to company
        List<Department> departments = new ArrayList<>();
        departments.add(department1);
        departments.add(department2);
        company.setDepartments(departments);
        
        // Calculate total budget
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Verify expected output: 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Create company C005
        Company company = new Company();
        
        // Create department D006
        Department department = new Department();
        department.setDepartmentId("D006");
        department.setEmail("department5@company.com");
        
        // Create community project: Community Health Awareness with mixed funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudgetAmount(40000.0);
        project1.setDeadline(LocalDate.of(2027, 2, 28));
        
        MixedGroup fundingGroup = new MixedGroup();
        project1.setFundingGroup(fundingGroup);
        
        // Create second project: Environmental Clean-up Initiative
        Project project2 = new Project();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudgetAmount(60000.0);
        project2.setDeadline(LocalDate.of(2027, 3, 30));
        
        // Add both projects to department
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        department.setProjects(projects);
        
        // Add department to company
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        company.setDepartments(departments);
        
        // Calculate total budget
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Verify expected output: 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}