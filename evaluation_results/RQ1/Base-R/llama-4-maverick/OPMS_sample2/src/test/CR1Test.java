import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() {
        // Create company C001
        Company company = new Company();
        
        // Create department D001 and add to company
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@company.com");
        company.getDepartments().add(department);
        
        // Create first project "Website Redevelopment"
        Project project1 = new Project();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudgetAmount(10000.0);
        project1.setDeadline("2025-12-31");
        department.getProjects().add(project1);
        
        // Create second project "Mobile App Development"
        Project project2 = new Project();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudgetAmount(15000.0);
        project2.setDeadline("2026-01-15");
        department.getProjects().add(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Create company C002
        Company company = new Company();
        
        // Create department D001 and add to company
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@company.com");
        company.getDepartments().add(department1);
        
        // Create project "HR Software" in D001
        Project project1 = new Project();
        project1.setTitle("HR Software");
        project1.setBudgetAmount(20000.0);
        department1.getProjects().add(project1);
        
        // Create department D002 and add to company
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@company.com");
        company.getDepartments().add(department2);
        
        // Create project "Sales Training Program" in D002
        Project project2 = new Project();
        project2.setTitle("Sales Training Program");
        project2.setBudgetAmount(30000.0);
        department2.getProjects().add(project2);
        
        // Create project "Marketing Campaign" in D002
        Project project3 = new Project();
        project3.setTitle("Marketing Campaign");
        project3.setBudgetAmount(25000.0);
        department2.getProjects().add(project3);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create company C003
        Company company = new Company();
        
        // Create department D003 and add to company
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@company.com");
        company.getDepartments().add(department);
        
        // Calculate total budget (no projects added)
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Create company C004
        Company company = new Company();
        
        // Create department D004 and add to company
        Department department1 = new Department();
        department1.setId("D004");
        department1.setEmail("department4@company.com");
        company.getDepartments().add(department1);
        
        // Create education project "Scholarship Program" with funding group
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline("2026-05-31");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("government group");
        project1.setFundingGroup(fundingGroup1);
        
        department1.getProjects().add(project1);
        
        // Create another project "R&D Initiative" in D004
        Project project2 = new Project();
        project2.setTitle("R&D Initiative");
        project2.setBudgetAmount(70000.0);
        project2.setDeadline("2026-07-15");
        department1.getProjects().add(project2);
        
        // Create department D005 and add to company
        Department department2 = new Department();
        department2.setId("D005");
        department2.setEmail("department5@company.com");
        company.getDepartments().add(department2);
        
        // Create project "R&D5 Initiative" in D005
        Project project3 = new Project();
        project3.setTitle("R&D5 Initiative");
        project3.setBudgetAmount(70000.0);
        project3.setDeadline("2026-07-19");
        department2.getProjects().add(project3);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Create company C005
        Company company = new Company();
        
        // Create department D006 and add to company
        Department department = new Department();
        department.setId("D006");
        department.setEmail("department5@company.com");
        company.getDepartments().add(department);
        
        // Create community project "Community Health Awareness" with funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudgetAmount(40000.0);
        project1.setDeadline("2027-02-28");
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("mixed group");
        project1.setFundingGroup(fundingGroup);
        
        department.getProjects().add(project1);
        
        // Create project "Environmental Clean-up Initiative"
        Project project2 = new Project();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudgetAmount(60000.0);
        project2.setDeadline("2027-03-30");
        department.getProjects().add(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(100000.0, totalBudget, 0.001);
    }
}