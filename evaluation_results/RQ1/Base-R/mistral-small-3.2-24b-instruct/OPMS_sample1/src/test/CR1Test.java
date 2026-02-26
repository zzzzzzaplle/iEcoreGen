import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() {
        // Test Case 1: Single Department Budget Calculation
        // Create company C001
        Company company = new Company();
        
        // Create department D001 and add to company
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@company.com");
        company.addDepartment(department);
        
        // Create first project "Website Redevelopment"
        Project project1 = new ProductionProject();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudgetAmount(10000);
        project1.setDeadline("2025-12-31");
        department.addProject(project1);
        
        // Create second project "Mobile App Development"
        Project project2 = new ProductionProject();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudgetAmount(15000);
        project2.setDeadline("2026-01-15");
        department.addProject(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output: 10000 + 15000 = 25000 CNY
        assertEquals(25000, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Test Case 2: Multiple Departments Budget Calculation
        // Create company C002
        Company company = new Company();
        
        // Create department D001 and add to company
        Department department1 = new Department();
        department1.setId("D001");
        department1.setEmail("department1@company.com");
        company.addDepartment(department1);
        
        // Create project in D001
        Project project1 = new ProductionProject();
        project1.setTitle("HR Software");
        project1.setBudgetAmount(20000);
        department1.addProject(project1);
        
        // Create department D002 and add to company
        Department department2 = new Department();
        department2.setId("D002");
        department2.setEmail("department2@company.com");
        company.addDepartment(department2);
        
        // Create first project in D002
        Project project2 = new ProductionProject();
        project2.setTitle("Sales Training Program");
        project2.setBudgetAmount(30000);
        department2.addProject(project2);
        
        // Create second project in D002
        Project project3 = new ProductionProject();
        project3.setTitle("Marketing Campaign");
        project3.setBudgetAmount(25000);
        department2.addProject(project3);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output: 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Test Case 3: Budget Calculation with Zero Projects
        // Create company C003
        Company company = new Company();
        
        // Create department D003 and add to company (no projects added)
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@company.com");
        company.addDepartment(department);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output: 0 CNY
        assertEquals(0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Test Case 4: Education Project Budget with Funding Group
        // Create company C004
        Company company = new Company();
        
        // Create department D004 and add to company
        Department department1 = new Department();
        department1.setId("D004");
        department1.setEmail("department4@company.com");
        company.addDepartment(department1);
        
        // Create education project with government funding group
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudgetAmount(50000);
        project1.setDeadline("2026-05-31");
        FundingGroup govGroup = new GovernmentGroup();
        project1.setFundingGroup(govGroup);
        department1.addProject(project1);
        
        // Create second project in D004
        Project project2 = new ProductionProject();
        project2.setTitle("R&D Initiative");
        project2.setBudgetAmount(70000);
        project2.setDeadline("2026-07-15");
        department1.addProject(project2);
        
        // Create department D005 and add to company
        Department department2 = new Department();
        department2.setId("D005");
        department2.setEmail("department5@company.com");
        company.addDepartment(department2);
        
        // Create project in D005
        Project project3 = new ProductionProject();
        project3.setTitle("R&D5 Initiative");
        project3.setBudgetAmount(70000);
        project3.setDeadline("2026-07-19");
        department2.addProject(project3);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output: 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Test Case 5: Community Project Budget with Mixed Funding Group
        // Create company C005
        Company company = new Company();
        
        // Create department D006 and add to company
        Department department = new Department();
        department.setId("D006");
        department.setEmail("department5@company.com");
        company.addDepartment(department);
        
        // Create community project with mixed funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudgetAmount(40000);
        project1.setDeadline("2027-02-28");
        FundingGroup mixedGroup = new MixedGroup();
        project1.setFundingGroup(mixedGroup);
        department.addProject(project1);
        
        // Create second project
        Project project2 = new ProductionProject();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudgetAmount(60000);
        project2.setDeadline("2027-03-30");
        department.addProject(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output: 40000 + 60000 = 100000 CNY
        assertEquals(100000, totalBudget, 0.001);
    }
}