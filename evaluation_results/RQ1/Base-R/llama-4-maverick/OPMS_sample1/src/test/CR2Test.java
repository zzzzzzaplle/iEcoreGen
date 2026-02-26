import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Create department D001
        Department marketingDept = new Department();
        marketingDept.setId("D001");
        marketingDept.setEmail("marketing@company.com");
        
        // Create project 1 for D001
        Project adsCampaign = new ProductionProject();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudgetAmount(50000.0);
        adsCampaign.setDeadline("2025-12-31");
        marketingDept.addProject(adsCampaign);
        
        // Create project 2 for D001
        Project marketResearch = new ProductionProject();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudgetAmount(30000.0);
        marketResearch.setDeadline("2025-11-30");
        marketingDept.addProject(marketResearch);
        
        // Create department D002 (not used in calculation)
        Department developmentDept = new Department();
        developmentDept.setId("D002");
        developmentDept.setEmail("development@company.com");
        
        // Create project for D002
        Project newFeatureDev = new ProductionProject();
        newFeatureDev.setTitle("New Feature Development");
        newFeatureDev.setDescription("Developing a new feature");
        newFeatureDev.setBudgetAmount(200000.0);
        newFeatureDev.setDeadline("2026-01-15");
        developmentDept.addProject(newFeatureDev);
        
        // Calculate average budget for D001
        double averageBudget = marketingDept.calculateAverageBudget();
        
        // Verify the result
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Create department D001
        Department hrDept = new Department();
        hrDept.setId("D001");
        hrDept.setEmail("hr@company.com");
        
        // Create project for D001
        Project employeeTraining = new ProductionProject();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudgetAmount(20000.0);
        employeeTraining.setDeadline("2025-08-15");
        hrDept.addProject(employeeTraining);
        
        // Create department D002
        Department salesDept = new Department();
        salesDept.setId("D002");
        salesDept.setEmail("sales@company.com");
        
        // Create project for D002
        Project salesStrategy = new ProductionProject();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudgetAmount(40000.0);
        salesStrategy.setDeadline("2025-09-15");
        salesDept.addProject(salesStrategy);
        
        // Create department D003
        Department itDept = new Department();
        itDept.setId("D003");
        itDept.setEmail("it@company.com");
        
        // Create project for D003
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
        
        // Calculate overall average across all three departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Verify individual department averages
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        
        // Verify overall average
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Create department D004
        Department financeDept = new Department();
        financeDept.setId("D004");
        financeDept.setEmail("finance@company.com");
        
        // Create project with zero budget for D004
        Project budgetReview = new ProductionProject();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudgetAmount(0.0);
        budgetReview.setDeadline("2025-07-30");
        financeDept.addProject(budgetReview);
        
        // Calculate average budget for D004
        double averageBudget = financeDept.calculateAverageBudget();
        
        // Verify the result
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department researchDept = new Department();
        researchDept.setId("D005");
        researchDept.setEmail("research@company.com");
        
        // Calculate average budget for empty department
        double averageBudget = researchDept.calculateAverageBudget();
        
        // Verify the result (should be 0 for no projects)
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Create department D006
        Department productDept = new Department();
        productDept.setId("D006");
        productDept.setEmail("product@company.com");
        
        // Create project 1 for D006
        Project productLaunch = new ProductionProject();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudgetAmount(150000.0);
        productLaunch.setDeadline("2025-12-01");
        productDept.addProject(productLaunch);
        
        // Create project 2 for D006
        Project marketAnalysis = new ProductionProject();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudgetAmount(75000.0);
        marketAnalysis.setDeadline("2025-10-15");
        productDept.addProject(marketAnalysis);
        
        // Create project 3 for D006
        Project clientEngagement = new ProductionProject();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudgetAmount(50000.0);
        clientEngagement.setDeadline("2025-11-30");
        productDept.addProject(clientEngagement);
        
        // Calculate average budget for D006
        double averageBudget = productDept.calculateAverageBudget();
        
        // Verify the result with precision for decimal values
        assertEquals(91666.67, averageBudget, 0.01);
    }
}