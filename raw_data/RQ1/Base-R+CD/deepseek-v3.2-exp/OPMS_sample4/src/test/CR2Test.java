import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department departmentD001;
    private Department departmentD002;
    private Department departmentD003;
    private Department departmentD004;
    private Department departmentD005;
    private Department departmentD006;
    
    @Before
    public void setUp() {
        // Initialize departments for test cases
        departmentD001 = new Department();
        departmentD002 = new Department();
        departmentD003 = new Department();
        departmentD004 = new Department();
        departmentD005 = new Department();
        departmentD006 = new Department();
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Set up department D001 (Marketing)
        departmentD001.setId("D001");
        departmentD001.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" for department D001
        Project adsCampaign = new Project() {};
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudgetAmount(50000.0);
        adsCampaign.setDeadline("2025-12-31");
        departmentD001.addProject(adsCampaign);
        
        // Create project "Market Research" for department D001
        Project marketResearch = new Project() {};
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudgetAmount(30000.0);
        marketResearch.setDeadline("2025-11-30");
        departmentD001.addProject(marketResearch);
        
        // Set up department D002 (Development) with one project
        departmentD002.setId("D002");
        departmentD002.setEmail("development@company.com");
        
        Project newFeatureDevelopment = new Project() {};
        newFeatureDevelopment.setTitle("New Feature Development");
        newFeatureDevelopment.setDescription("Developing a new feature");
        newFeatureDevelopment.setBudgetAmount(200000.0);
        newFeatureDevelopment.setDeadline("2026-01-15");
        departmentD002.addProject(newFeatureDevelopment);
        
        // Calculate average budget for department D001
        double averageBudgetD001 = departmentD001.calculateAverageBudget();
        
        // Expected output: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudgetD001, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Set up department D001 (HR)
        departmentD001.setId("D001");
        departmentD001.setEmail("hr@company.com");
        
        Project employeeTraining = new Project() {};
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudgetAmount(20000.0);
        employeeTraining.setDeadline("2025-08-15");
        departmentD001.addProject(employeeTraining);
        
        // Set up department D002 (Sales)
        departmentD002.setId("D002");
        departmentD002.setEmail("sales@company.com");
        
        Project salesStrategy = new Project() {};
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudgetAmount(40000.0);
        salesStrategy.setDeadline("2025-09-15");
        departmentD002.addProject(salesStrategy);
        
        // Set up department D003 (IT)
        departmentD003.setId("D003");
        departmentD003.setEmail("it@company.com");
        
        Project systemUpgrade = new Project() {};
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudgetAmount(60000.0);
        systemUpgrade.setDeadline("2025-10-01");
        departmentD003.addProject(systemUpgrade);
        
        // Calculate average budget for each department
        double averageBudgetD001 = departmentD001.calculateAverageBudget();
        double averageBudgetD002 = departmentD002.calculateAverageBudget();
        double averageBudgetD003 = departmentD003.calculateAverageBudget();
        
        // Calculate overall average across all three departments
        double overallAverage = (averageBudgetD001 + averageBudgetD002 + averageBudgetD003) / 3;
        
        // Verify individual department averages
        assertEquals(20000.0, averageBudgetD001, 0.001);
        assertEquals(40000.0, averageBudgetD002, 0.001);
        assertEquals(60000.0, averageBudgetD003, 0.001);
        
        // Verify overall average: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Set up department D004 (Finance)
        departmentD004.setId("D004");
        departmentD004.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project budgetReview = new Project() {};
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudgetAmount(0.0);
        budgetReview.setDeadline("2025-07-30");
        departmentD004.addProject(budgetReview);
        
        // Calculate average budget for department D004
        double averageBudgetD004 = departmentD004.calculateAverageBudget();
        
        // Expected output: 0 CNY
        assertEquals(0.0, averageBudgetD004, 0.001);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testCase4_NoProjectsInDepartment() {
        // Set up department D005 (Research) with no projects
        departmentD005.setId("D005");
        departmentD005.setEmail("research@company.com");
        
        // Attempt to calculate average budget for department with no projects
        // This should throw ArithmeticException according to the method specification
        departmentD005.calculateAverageBudget();
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Set up department D006 (Product)
        departmentD006.setId("D006");
        departmentD006.setEmail("product@company.com");
        
        // Create project "Product Launch"
        Project productLaunch = new Project() {};
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudgetAmount(150000.0);
        productLaunch.setDeadline("2025-12-01");
        departmentD006.addProject(productLaunch);
        
        // Create project "Market Analysis"
        Project marketAnalysis = new Project() {};
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudgetAmount(75000.0);
        marketAnalysis.setDeadline("2025-10-15");
        departmentD006.addProject(marketAnalysis);
        
        // Create project "Client Engagement"
        Project clientEngagement = new Project() {};
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudgetAmount(50000.0);
        clientEngagement.setDeadline("2025-11-30");
        departmentD006.addProject(clientEngagement);
        
        // Calculate average budget for department D006
        double averageBudgetD006 = departmentD006.calculateAverageBudget();
        
        // Expected output: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudgetD006, 0.01);
    }
}