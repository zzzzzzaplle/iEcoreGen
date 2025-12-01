import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() {
        // Test Case 1: Single Department Budget Calculation
        // Create a company C001
        Company company = new Company();
        
        // Create a department with ID: D001 and email: department1@company.com, and add the department to the company C001
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@company.com");
        company.getDepartments().add(department);
        
        // Create a project titled "Website Redevelopment" with description "Redesigning the company website", 
        // budget amount: 10000 CNY, and deadline: 2025-12-31. The project is associated with the department D001.
        Project project1 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudgetAmount(10000.0);
        project1.setDeadline("2025-12-31");
        department.getProjects().add(project1);
        
        // Create another project titled "Mobile App Development" with description "Developing a customer service app", 
        // budget amount: 15000 CNY, and deadline: 2026-01-15. The project is associated with the department D001.
        Project project2 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudgetAmount(15000.0);
        project2.setDeadline("2026-01-15");
        department.getProjects().add(project2);
        
        // Calculate the total budget for company C001
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Test Case 2: Multiple Departments Budget Calculation
        // Create a company C002
        Company company = new Company();
        
        // Create Department D001 with email: department1@company.com, and add the department to the company C002
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@company.com");
        company.getDepartments().add(department1);
        
        // Create a project in D001 titled "HR Software" with budget amount: 20000 CNY
        Project project1 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project1.setTitle("HR Software");
        project1.setBudgetAmount(20000.0);
        department1.getProjects().add(project1);
        
        // Create Department D002 with email: department2@company.com
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@company.com");
        company.getDepartments().add(department2);
        
        // Create a project in D002 titled "Sales Training Program" with budget amount: 30000 CNY
        Project project2 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project2.setTitle("Sales Training Program");
        project2.setBudgetAmount(30000.0);
        department2.getProjects().add(project2);
        
        // Create another project in D002 titled "Marketing Campaign" with budget amount: 25000 CNY
        Project project3 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project3.setTitle("Marketing Campaign");
        project3.setBudgetAmount(25000.0);
        department2.getProjects().add(project3);
        
        // Calculate the total budget for company C002
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Test Case 3: Budget Calculation with Zero Projects
        // Create a company C003
        Company company = new Company();
        
        // Create Department with ID: D003 and email: department3@company.com, and add the department to the company C003
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@company.com");
        company.getDepartments().add(department);
        
        // Calculate the total budget for company C003
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Test Case 4: Education Project Budget with Funding Group
        // Create a company C004
        Company company = new Company();
        
        // Create a department with ID: D004 and email: department4@company.com, and add the department to the company C004
        Department department1 = new Department();
        department1.setId("D004");
        department1.setEmail("department4@company.com");
        company.getDepartments().add(department1);
        
        // Create an education project titled "Scholarship Program" with budget amount: 50000 CNY, 
        // deadline: 2026-05-31, and funding group type: government group
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Scholarship Program");
        educationProject.setBudgetAmount(50000.0);
        educationProject.setDeadline("2026-05-31");
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setGroupType("government group");
        educationProject.setFundingGroup(fundingGroup1);
        department1.getProjects().add(educationProject);
        
        // Create another project titled "R&D Initiative" with budget amount: 70000 CNY and deadline: 2026-07-15
        Project project2 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project2.setTitle("R&D Initiative");
        project2.setBudgetAmount(70000.0);
        project2.setDeadline("2026-07-15");
        department1.getProjects().add(project2);
        
        // Create a department with ID: D005 and email: department5@company.com, and add the department to the company C004
        Department department2 = new Department();
        department2.setId("D005");
        department2.setEmail("department5@company.com");
        company.getDepartments().add(department2);
        
        // Create another project titled "R&D5 Initiative" with budget amount: 70000 CNY and deadline: 2026-07-19
        Project project3 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project3.setTitle("R&D5 Initiative");
        project3.setBudgetAmount(70000.0);
        project3.setDeadline("2026-07-19");
        department2.getProjects().add(project3);
        
        // Calculate the total budget for company C004
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Test Case 5: Community Project Budget with Mixed Funding Group
        // Create a company C005
        Company company = new Company();
        
        // Create a department with ID: D006 and email: department5@company.com, and add the department to the company C005
        Department department = new Department();
        department.setId("D006");
        department.setEmail("department5@company.com");
        company.getDepartments().add(department);
        
        // Create a community project titled "Community Health Awareness" with budget amount: 40000 CNY, 
        // deadline: 2027-02-28, and funding group type: mixed group
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Health Awareness");
        communityProject.setBudgetAmount(40000.0);
        communityProject.setDeadline("2027-02-28");
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setGroupType("mixed group");
        communityProject.setFundingGroup(fundingGroup);
        department.getProjects().add(communityProject);
        
        // Create a project titled "Environmental Clean-up Initiative" with budget amount: 60000 CNY and deadline: 2027-03-30
        Project project2 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudgetAmount(60000.0);
        project2.setDeadline("2027-03-30");
        department.getProjects().add(project2);
        
        // Calculate the total budget for company C005
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}