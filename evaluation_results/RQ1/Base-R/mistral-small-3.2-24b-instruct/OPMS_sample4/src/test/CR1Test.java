import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department department;
    private Project project;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() {
        // Test Case 1: Single Department Budget Calculation
        // Create company C001
        company = new Company();
        
        // Create department with ID: D001 and email: department1@company.com
        department = new Department();
        department.setId("D001");
        department.setEmail("department1@company.com");
        
        // Add department to company C001
        company.addDepartment(department);
        
        // Create project "Website Redevelopment"
        project = new Project();
        project.setTitle("Website Redevelopment");
        project.setDescription("Redesigning the company website");
        project.setBudgetAmount(10000.0);
        project.setDeadline("2025-12-31");
        
        // Associate project with department D001
        department.addProject(project);
        
        // Create project "Mobile App Development"
        Project project2 = new Project();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudgetAmount(15000.0);
        project2.setDeadline("2026-01-15");
        
        // Associate project with department D001
        department.addProject(project2);
        
        // Calculate total budget for company C001
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Test Case 2: Multiple Departments Budget Calculation
        // Create company C002
        company = new Company();
        
        // Create Department D001 with email: department1@company.com
        Department dept1 = new Department();
        dept1.setId("D001");
        dept1.setEmail("department1@company.com");
        
        // Add department to company C002
        company.addDepartment(dept1);
        
        // Create project in D001 titled "HR Software" with budget amount: 20000 CNY
        Project project1 = new Project();
        project1.setTitle("HR Software");
        project1.setBudgetAmount(20000.0);
        dept1.addProject(project1);
        
        // Create Department D002 with email: department2@company.com
        Department dept2 = new Department();
        dept2.setId("D002");
        dept2.setEmail("department2@company.com");
        
        // Add department to company C002
        company.addDepartment(dept2);
        
        // Create project in D002 titled "Sales Training Program" with budget amount: 30000 CNY
        Project project2 = new Project();
        project2.setTitle("Sales Training Program");
        project2.setBudgetAmount(30000.0);
        dept2.addProject(project2);
        
        // Create another project in D002 titled "Marketing Campaign" with budget amount: 25000 CNY
        Project project3 = new Project();
        project3.setTitle("Marketing Campaign");
        project3.setBudgetAmount(25000.0);
        dept2.addProject(project3);
        
        // Calculate total budget for company C002
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Test Case 3: Budget Calculation with Zero Projects
        // Create company C003
        company = new Company();
        
        // Create Department with ID: D003 and email: department3@company.com
        department = new Department();
        department.setId("D003");
        department.setEmail("department3@company.com");
        
        // Add department to company C003
        company.addDepartment(department);
        
        // Calculate total budget for company C003
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Test Case 4: Education Project Budget with Funding Group
        // Create company C004
        company = new Company();
        
        // Create department with ID: D004 and email: department4@company.com
        Department dept1 = new Department();
        dept1.setId("D004");
        dept1.setEmail("department4@company.com");
        
        // Add department to company C004
        company.addDepartment(dept1);
        
        // Create an education project titled "Scholarship Program"
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Scholarship Program");
        eduProject.setBudgetAmount(50000.0);
        eduProject.setDeadline("2026-05-31");
        
        // Create funding group with type: government group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("government group");
        eduProject.setFundingGroup(fundingGroup);
        
        // Associate project with department D004
        dept1.addProject(eduProject);
        
        // Create another project titled "R&D Initiative"
        Project project2 = new Project();
        project2.setTitle("R&D Initiative");
        project2.setBudgetAmount(70000.0);
        project2.setDeadline("2026-07-15");
        
        // Associate project with department D004
        dept1.addProject(project2);
        
        // Create department with ID: D005 and email: department5@company.com
        Department dept2 = new Department();
        dept2.setId("D005");
        dept2.setEmail("department5@company.com");
        
        // Add department to company C004
        company.addDepartment(dept2);
        
        // Create another project titled "R&D5 Initiative"
        Project project3 = new Project();
        project3.setTitle("R&D5 Initiative");
        project3.setBudgetAmount(70000.0);
        project3.setDeadline("2026-07-19");
        
        // Associate project with department D005
        dept2.addProject(project3);
        
        // Calculate total budget for company C004
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Test Case 5: Community Project Budget with Mixed Funding Group
        // Create company C005
        company = new Company();
        
        // Create department with ID: D006 and email: department5@company.com
        department = new Department();
        department.setId("D006");
        department.setEmail("department5@company.com");
        
        // Add department to company C005
        company.addDepartment(department);
        
        // Create a community project titled "Community Health Awareness"
        CommunityProject commProject = new CommunityProject();
        commProject.setTitle("Community Health Awareness");
        commProject.setBudgetAmount(40000.0);
        commProject.setDeadline("2027-02-28");
        
        // Create funding group with type: mixed group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("mixed group");
        commProject.setFundingGroup(fundingGroup);
        
        // Associate project with department D006
        department.addProject(commProject);
        
        // Create a project titled "Environmental Clean-up Initiative"
        Project project2 = new Project();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudgetAmount(60000.0);
        project2.setDeadline("2027-03-30");
        
        // Associate project with department D006
        department.addProject(project2);
        
        // Calculate total budget for company C005
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}