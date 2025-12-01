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
        Company companyC001 = new Company();
        
        // Create a department with ID: D001 and email: department1@company.com
        Department departmentD001 = new Department();
        departmentD001.setId("D001");
        departmentD001.setEmail("department1@company.com");
        
        // Add department to company C001
        companyC001.addDepartment(departmentD001);
        
        // Create project "Website Redevelopment"
        Project project1 = new Project() {};
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudgetAmount(10000.0);
        project1.setDeadline("2025-12-31");
        
        // Create project "Mobile App Development"
        Project project2 = new Project() {};
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudgetAmount(15000.0);
        project2.setDeadline("2026-01-15");
        
        // Associate projects with department D001
        departmentD001.addProject(project1);
        departmentD001.addProject(project2);
        
        // Calculate total budget for company C001
        double totalBudget = companyC001.calculateTotalBudget();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Create a company C002
        Company companyC002 = new Company();
        
        // Create Department D001 with email: department1@company.com
        Department departmentD001 = new Department();
        departmentD001.setId("D001");
        departmentD001.setEmail("department1@company.com");
        
        // Add department D001 to company C002
        companyC002.addDepartment(departmentD001);
        
        // Create project "HR Software" in D001
        Project project1 = new Project() {};
        project1.setTitle("HR Software");
        project1.setBudgetAmount(20000.0);
        departmentD001.addProject(project1);
        
        // Create Department D002 with email: department2@company.com
        Department departmentD002 = new Department();
        departmentD002.setId("D002");
        departmentD002.setEmail("department2@company.com");
        
        // Add department D002 to company C002
        companyC002.addDepartment(departmentD002);
        
        // Create project "Sales Training Program" in D002
        Project project2 = new Project() {};
        project2.setTitle("Sales Training Program");
        project2.setBudgetAmount(30000.0);
        departmentD002.addProject(project2);
        
        // Create project "Marketing Campaign" in D002
        Project project3 = new Project() {};
        project3.setTitle("Marketing Campaign");
        project3.setBudgetAmount(25000.0);
        departmentD002.addProject(project3);
        
        // Calculate total budget for company C002
        double totalBudget = companyC002.calculateTotalBudget();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create a company C003
        Company companyC003 = new Company();
        
        // Create Department with ID: D003 and email: department3@company.com
        Department departmentD003 = new Department();
        departmentD003.setId("D003");
        departmentD003.setEmail("department3@company.com");
        
        // Add department to company C003
        companyC003.addDepartment(departmentD003);
        
        // Calculate total budget for company C003
        double totalBudget = companyC003.calculateTotalBudget();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Create a company C004
        Company companyC004 = new Company();
        
        // Create a department with ID: D004 and email: department4@company.com
        Department departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@company.com");
        
        // Add department D004 to company C004
        companyC004.addDepartment(departmentD004);
        
        // Create an education project "Scholarship Program" with funding group
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Scholarship Program");
        educationProject.setBudgetAmount(50000.0);
        educationProject.setDeadline("2026-05-31");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("government group");
        educationProject.setFundingGroup(fundingGroup1);
        
        // Associate education project with department D004
        departmentD004.addProject(educationProject);
        
        // Create project "R&D Initiative"
        Project project2 = new Project() {};
        project2.setTitle("R&D Initiative");
        project2.setBudgetAmount(70000.0);
        project2.setDeadline("2026-07-15");
        
        // Associate project with department D004
        departmentD004.addProject(project2);
        
        // Create a department with ID: D005 and email: department5@company.com
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@company.com");
        
        // Add department D005 to company C004
        companyC004.addDepartment(departmentD005);
        
        // Create project "R&D5 Initiative"
        Project project3 = new Project() {};
        project3.setTitle("R&D5 Initiative");
        project3.setBudgetAmount(70000.0);
        project3.setDeadline("2026-07-19");
        
        // Associate project with department D005
        departmentD005.addProject(project3);
        
        // Calculate total budget for company C004
        double totalBudget = companyC004.calculateTotalBudget();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Create a company C005
        Company companyC005 = new Company();
        
        // Create a department with ID: D006 and email: department5@company.com
        Department departmentD006 = new Department();
        departmentD006.setId("D006");
        departmentD006.setEmail("department5@company.com");
        
        // Add department D006 to company C005
        companyC005.addDepartment(departmentD006);
        
        // Create a community project "Community Health Awareness" with funding group
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Health Awareness");
        communityProject.setBudgetAmount(40000.0);
        communityProject.setDeadline("2027-02-28");
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("mixed group");
        communityProject.setFundingGroup(fundingGroup);
        
        // Associate community project with department D006
        departmentD006.addProject(communityProject);
        
        // Create project "Environmental Clean-up Initiative"
        Project project2 = new Project() {};
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudgetAmount(60000.0);
        project2.setDeadline("2027-03-30");
        
        // Associate project with department D006
        departmentD006.addProject(project2);
        
        // Calculate total budget for company C005
        double totalBudget = companyC005.calculateTotalBudget();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}