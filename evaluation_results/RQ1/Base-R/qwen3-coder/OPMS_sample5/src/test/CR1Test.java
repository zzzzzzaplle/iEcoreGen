import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        Department dept = new Department();
        dept.setId("D001");
        dept.setEmail("department1@company.com");
        
        // Create project 1: "Website Redevelopment"
        Project project1 = new Project();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudgetAmount(10000.0);
        project1.setDeadline("2025-12-31");
        
        // Create project 2: "Mobile App Development"
        Project project2 = new Project();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudgetAmount(15000.0);
        project2.setDeadline("2026-01-15");
        
        // Add projects to department
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        dept.setProjects(projects);
        
        // Add department to company
        List<Department> departments = new ArrayList<>();
        departments.add(dept);
        c001.setDepartments(departments);
        
        // Calculate total budget
        double totalBudget = c001.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Create a company C002
        Company c002 = new Company();
        
        // Create Department D001
        Department dept1 = new Department();
        dept1.setId("D001");
        dept1.setEmail("department1@company.com");
        
        // Create project in D001: "HR Software"
        Project project1 = new Project();
        project1.setTitle("HR Software");
        project1.setBudgetAmount(20000.0);
        
        List<Project> projects1 = new ArrayList<>();
        projects1.add(project1);
        dept1.setProjects(projects1);
        
        // Create Department D002
        Department dept2 = new Department();
        dept2.setId("D002");
        dept2.setEmail("department2@company.com");
        
        // Create project 1 in D002: "Sales Training Program"
        Project project2 = new Project();
        project2.setTitle("Sales Training Program");
        project2.setBudgetAmount(30000.0);
        
        // Create project 2 in D002: "Marketing Campaign"
        Project project3 = new Project();
        project3.setTitle("Marketing Campaign");
        project3.setBudgetAmount(25000.0);
        
        List<Project> projects2 = new ArrayList<>();
        projects2.add(project2);
        projects2.add(project3);
        dept2.setProjects(projects2);
        
        // Add departments to company
        List<Department> departments = new ArrayList<>();
        departments.add(dept1);
        departments.add(dept2);
        c002.setDepartments(departments);
        
        // Calculate total budget
        double totalBudget = c002.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create a company C003
        Company c003 = new Company();
        
        // Create Department D003 with no projects
        Department dept = new Department();
        dept.setId("D003");
        dept.setEmail("department3@company.com");
        
        // Add department to company
        List<Department> departments = new ArrayList<>();
        departments.add(dept);
        c003.setDepartments(departments);
        
        // Calculate total budget
        double totalBudget = c003.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Create a company C004
        Company c004 = new Company();
        
        // Create Department D004
        Department dept1 = new Department();
        dept1.setId("D004");
        dept1.setEmail("department4@company.com");
        
        // Create education project: "Scholarship Program"
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline("2026-05-31");
        
        // Create funding group for education project
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("government group");
        project1.setFundingGroup(fundingGroup1);
        
        // Create another project: "R&D Initiative"
        Project project2 = new Project();
        project2.setTitle("R&D Initiative");
        project2.setBudgetAmount(70000.0);
        project2.setDeadline("2026-07-15");
        
        List<Project> projects1 = new ArrayList<>();
        projects1.add(project1);
        projects1.add(project2);
        dept1.setProjects(projects1);
        
        // Create Department D005
        Department dept2 = new Department();
        dept2.setId("D005");
        dept2.setEmail("department5@company.com");
        
        // Create project: "R&D5 Initiative"
        Project project3 = new Project();
        project3.setTitle("R&D5 Initiative");
        project3.setBudgetAmount(70000.0);
        project3.setDeadline("2026-07-19");
        
        List<Project> projects2 = new ArrayList<>();
        projects2.add(project3);
        dept2.setProjects(projects2);
        
        // Add departments to company
        List<Department> departments = new ArrayList<>();
        departments.add(dept1);
        departments.add(dept2);
        c004.setDepartments(departments);
        
        // Calculate total budget
        double totalBudget = c004.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Create a company C005
        Company c005 = new Company();
        
        // Create Department D006
        Department dept = new Department();
        dept.setId("D006");
        dept.setEmail("department5@company.com");
        
        // Create community project: "Community Health Awareness"
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudgetAmount(40000.0);
        project1.setDeadline("2027-02-28");
        
        // Create funding group for community project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("mixed group");
        project1.setFundingGroup(fundingGroup);
        
        // Create another project: "Environmental Clean-up Initiative"
        Project project2 = new Project();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudgetAmount(60000.0);
        project2.setDeadline("2027-03-30");
        
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        dept.setProjects(projects);
        
        // Add department to company
        List<Department> departments = new ArrayList<>();
        departments.add(dept);
        c005.setDepartments(departments);
        
        // Calculate total budget
        double totalBudget = c005.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}