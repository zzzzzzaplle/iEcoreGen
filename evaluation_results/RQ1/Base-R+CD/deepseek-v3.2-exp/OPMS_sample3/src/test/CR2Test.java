import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department department;
    private Company company;
    
    @Before
    public void setUp() {
        department = new Department();
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Create department D001
        Department marketingDept = new Department();
        marketingDept.setId("D001");
        marketingDept.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" with budget 50000 CNY
        Project adsCampaign = new ProductionProject();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudgetAmount(50000.0);
        adsCampaign.setDeadline("2025-12-31");
        
        // Create project "Market Research" with budget 30000 CNY
        Project marketResearch = new ProductionProject();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudgetAmount(30000.0);
        marketResearch.setDeadline("2025-11-30");
        
        // Add projects to marketing department
        marketingDept.addProject(adsCampaign);
        marketingDept.addProject(marketResearch);
        
        // Create another department D002 with different project
        Department developmentDept = new Department();
        developmentDept.setId("D002");
        developmentDept.setEmail("development@company.com");
        
        Project newFeature = new ProductionProject();
        newFeature.setTitle("New Feature Development");
        newFeature.setDescription("Developing a new feature");
        newFeature.setBudgetAmount(200000.0);
        newFeature.setDeadline("2026-01-15");
        
        developmentDept.addProject(newFeature);
        
        // Calculate average budget for department D001
        double averageBudget = marketingDept.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Create department D001 with one project
        Department hrDept = new Department();
        hrDept.setId("D001");
        hrDept.setEmail("hr@company.com");
        
        Project employeeTraining = new ProductionProject();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudgetAmount(20000.0);
        employeeTraining.setDeadline("2025-08-15");
        
        hrDept.addProject(employeeTraining);
        
        // Create department D002 with one project
        Department salesDept = new Department();
        salesDept.setId("D002");
        salesDept.setEmail("sales@company.com");
        
        Project salesStrategy = new ProductionProject();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudgetAmount(40000.0);
        salesStrategy.setDeadline("2025-09-15");
        
        salesDept.addProject(salesStrategy);
        
        // Create department D003 with one project
        Department itDept = new Department();
        itDept.setId("D003");
        itDept.setEmail("it@company.com");
        
        Project systemUpgrade = new ProductionProject();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudgetAmount(60000.0);
        systemUpgrade.setDeadline("2025-10-01");
        
        itDept.addProject(systemUpgrade);
        
        // Calculate average budget for each department
        double avgD001 = hrDept.calculateAverageBudget();
        double avgD002 = salesDept.calculateAverageBudget();
        double avgD003 = itDept.calculateAverageBudget();
        
        // Calculate overall average across all departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Verify individual department averages
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        
        // Verify overall average: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Create department D004
        Department financeDept = new Department();
        financeDept.setId("D004");
        financeDept.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project budgetReview = new ProductionProject();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudgetAmount(0.0);
        budgetReview.setDeadline("2025-07-30");
        
        financeDept.addProject(budgetReview);
        
        // Calculate average budget
        double averageBudget = financeDept.calculateAverageBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department researchDept = new Department();
        researchDept.setId("D005");
        researchDept.setEmail("research@company.com");
        
        // Attempt to calculate average budget - should throw exception
        researchDept.calculateAverageBudget();
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Create department D006
        Department productDept = new Department();
        productDept.setId("D006");
        productDept.setEmail("product@company.com");
        
        // Create project "Product Launch" with budget 150000 CNY
        Project productLaunch = new ProductionProject();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudgetAmount(150000.0);
        productLaunch.setDeadline("2025-12-01");
        
        // Create project "Market Analysis" with budget 75000 CNY
        Project marketAnalysis = new ProductionProject();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudgetAmount(75000.0);
        marketAnalysis.setDeadline("2025-10-15");
        
        // Create project "Client Engagement" with budget 50000 CNY
        Project clientEngagement = new ProductionProject();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudgetAmount(50000.0);
        clientEngagement.setDeadline("2025-11-30");
        
        // Add all projects to department
        productDept.addProject(productLaunch);
        productDept.addProject(marketAnalysis);
        productDept.addProject(clientEngagement);
        
        // Calculate average budget
        double averageBudget = productDept.calculateAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}