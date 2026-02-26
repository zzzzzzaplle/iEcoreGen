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
        // Create a company C001
        Company c001 = new Company();
        
        // Create a department with ID: D001 and email: department1@company.com
        Department department = new Department();
        department.setDepartmentId("D001");
        department.setEmail("department1@company.com");
        
        // Create project 1: "Website Redevelopment"
        Project project1 = new Project();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudgetAmount(10000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 31));
        
        // Create project 2: "Mobile App Development"
        Project project2 = new Project();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudgetAmount(15000.0);
        project2.setDeadline(LocalDate.of(2026, 1, 15));
        
        // Add projects to department
        department.getProjects().add(project1);
        department.getProjects().add(project2);
        
        // Add department to company
        c001.getDepartments().add(department);
        
        // Calculate total budget
        double totalBudget = c001.getTotalBudget();
        
        // Expected: 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Create a company C002
        Company c002 = new Company();
        
        // Create Department D001
        Department dept1 = new Department();
        dept1.setDepartmentId("D001");
        dept1.setEmail("department1@company.com");
        
        // Create project in D001: "HR Software"
        Project project1 = new Project();
        project1.setTitle("HR Software");
        project1.setBudgetAmount(20000.0);
        dept1.getProjects().add(project1);
        
        // Create Department D002
        Department dept2 = new Department();
        dept2.setDepartmentId("D002");
        dept2.setEmail("department2@company.com");
        
        // Create project 1 in D002: "Sales Training Program"
        Project project2 = new Project();
        project2.setTitle("Sales Training Program");
        project2.setBudgetAmount(30000.0);
        dept2.getProjects().add(project2);
        
        // Create project 2 in D002: "Marketing Campaign"
        Project project3 = new Project();
        project3.setTitle("Marketing Campaign");
        project3.setBudgetAmount(25000.0);
        dept2.getProjects().add(project3);
        
        // Add both departments to company
        c002.getDepartments().add(dept1);
        c002.getDepartments().add(dept2);
        
        // Calculate total budget
        double totalBudget = c002.getTotalBudget();
        
        // Expected: 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create a company C003
        Company c003 = new Company();
        
        // Create Department D003 with no projects
        Department department = new Department();
        department.setDepartmentId("D003");
        department.setEmail("department3@company.com");
        
        // Add department to company
        c003.getDepartments().add(department);
        
        // Calculate total budget
        double totalBudget = c003.getTotalBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }

    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Create a company C004
        Company c004 = new Company();
        
        // Create Department D004
        Department dept4 = new Department();
        dept4.setDepartmentId("D004");
        dept4.setEmail("department4@company.com");
        
        // Create education project with government funding group
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline(LocalDate.of(2026, 5, 31));
        
        GovernmentGroup govGroup = new GovernmentGroup();
        project1.setFundingGroup(govGroup);
        dept4.getProjects().add(project1);
        
        // Create another project
        Project project2 = new Project();
        project2.setTitle("R&D Initiative");
        project2.setBudgetAmount(70000.0);
        project2.setDeadline(LocalDate.of(2026, 7, 15));
        dept4.getProjects().add(project2);
        
        // Create Department D005
        Department dept5 = new Department();
        dept5.setDepartmentId("D005");
        dept5.setEmail("department5@company.com");
        
        // Create project in D005
        Project project3 = new Project();
        project3.setTitle("R&D5 Initiative");
        project3.setBudgetAmount(70000.0);
        project3.setDeadline(LocalDate.of(2026, 7, 19));
        dept5.getProjects().add(project3);
        
        // Add both departments to company
        c004.getDepartments().add(dept4);
        c004.getDepartments().add(dept5);
        
        // Calculate total budget
        double totalBudget = c004.getTotalBudget();
        
        // Expected: 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Create a company C005
        Company c005 = new Company();
        
        // Create Department D006
        Department department = new Department();
        department.setDepartmentId("D006");
        department.setEmail("department5@company.com");
        
        // Create community project with mixed funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudgetAmount(40000.0);
        project1.setDeadline(LocalDate.of(2027, 2, 28));
        
        MixedGroup mixedGroup = new MixedGroup();
        project1.setFundingGroup(mixedGroup);
        department.getProjects().add(project1);
        
        // Create another project
        Project project2 = new Project();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudgetAmount(60000.0);
        project2.setDeadline(LocalDate.of(2027, 3, 30));
        department.getProjects().add(project2);
        
        // Add department to company
        c005.getDepartments().add(department);
        
        // Calculate total budget
        double totalBudget = c005.getTotalBudget();
        
        // Expected: 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}