import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() {
        // Create a company C001
        Company company = new Company();
        
        // Create a department with ID: D001 and email: department1@company.com
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@company.com");
        
        // Add department to company
        company.addDepartment(department);
        
        // Create project 1: "Website Redevelopment"
        Project project1 = new Project() {};
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudgetAmount(10000.0);
        project1.setDeadline("2025-12-31");
        
        // Create project 2: "Mobile App Development"
        Project project2 = new Project() {};
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudgetAmount(15000.0);
        project2.setDeadline("2026-01-15");
        
        // Associate projects with department
        department.addProject(project1);
        department.addProject(project2);
        
        // Calculate total budget
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Expected: 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Create a company C002
        Company company = new Company();
        
        // Create Department D001
        Department dept1 = new Department();
        dept1.setId("D001");
        dept1.setEmail("department1@company.com");
        company.addDepartment(dept1);
        
        // Create project in D001: "HR Software"
        Project project1 = new Project() {};
        project1.setTitle("HR Software");
        project1.setBudgetAmount(20000.0);
        dept1.addProject(project1);
        
        // Create Department D002
        Department dept2 = new Department();
        dept2.setId("D002");
        dept2.setEmail("department2@company.com");
        company.addDepartment(dept2);
        
        // Create project 1 in D002: "Sales Training Program"
        Project project2 = new Project() {};
        project2.setTitle("Sales Training Program");
        project2.setBudgetAmount(30000.0);
        dept2.addProject(project2);
        
        // Create project 2 in D002: "Marketing Campaign"
        Project project3 = new Project() {};
        project3.setTitle("Marketing Campaign");
        project3.setBudgetAmount(25000.0);
        dept2.addProject(project3);
        
        // Calculate total budget
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Expected: 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create a company C003
        Company company = new Company();
        
        // Create Department D003
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@company.com");
        company.addDepartment(department);
        
        // Calculate total budget (department has no projects)
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Expected: 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Create a company C004
        Company company = new Company();
        
        // Create Department D004
        Department dept1 = new Department();
        dept1.setId("D004");
        dept1.setEmail("department4@company.com");
        company.addDepartment(dept1);
        
        // Create education project: "Scholarship Program"
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline("2026-05-31");
        
        // Create funding group for education project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("government group");
        project1.setFundingGroup(fundingGroup);
        
        dept1.addProject(project1);
        
        // Create another project: "R&D Initiative"
        Project project2 = new Project() {};
        project2.setTitle("R&D Initiative");
        project2.setBudgetAmount(70000.0);
        project2.setDeadline("2026-07-15");
        dept1.addProject(project2);
        
        // Create Department D005
        Department dept2 = new Department();
        dept2.setId("D005");
        dept2.setEmail("department5@company.com");
        company.addDepartment(dept2);
        
        // Create project in D005: "R&D5 Initiative"
        Project project3 = new Project() {};
        project3.setTitle("R&D5 Initiative");
        project3.setBudgetAmount(70000.0);
        project3.setDeadline("2026-07-19");
        dept2.addProject(project3);
        
        // Calculate total budget
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Expected: 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Create a company C005
        Company company = new Company();
        
        // Create Department D006
        Department department = new Department();
        department.setId("D006");
        department.setEmail("department5@company.com");
        company.addDepartment(department);
        
        // Create community project: "Community Health Awareness"
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudgetAmount(40000.0);
        project1.setDeadline("2027-02-28");
        
        // Create funding group for community project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("mixed group");
        project1.setFundingGroup(fundingGroup);
        
        department.addProject(project1);
        
        // Create another project: "Environmental Clean-up Initiative"
        Project project2 = new Project() {};
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudgetAmount(60000.0);
        project2.setDeadline("2027-03-30");
        department.addProject(project2);
        
        // Calculate total budget
        double totalBudget = company.getTotalBudgetOfAllProjects();
        
        // Expected: 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}