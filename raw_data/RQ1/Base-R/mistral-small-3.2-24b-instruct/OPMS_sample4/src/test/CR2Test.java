import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Create department D001 with email marketing@company.com
        Department marketingDept = new Department();
        marketingDept.setId("D001");
        marketingDept.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" with budget 50000 CNY
        Project adsCampaign = new Project();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudgetAmount(50000);
        adsCampaign.setDeadline("2025-12-31");
        marketingDept.addProject(adsCampaign);
        
        // Create project "Market Research" with budget 30000 CNY
        Project marketResearch = new Project();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudgetAmount(30000);
        marketResearch.setDeadline("2025-11-30");
        marketingDept.addProject(marketResearch);
        
        // Create another department D002 with email development@company.com
        Department developmentDept = new Department();
        developmentDept.setId("D002");
        developmentDept.setEmail("development@company.com");
        
        // Create project "New Feature Development" with budget 200000 CNY
        Project newFeatureDev = new Project();
        newFeatureDev.setTitle("New Feature Development");
        newFeatureDev.setDescription("Developing a new feature");
        newFeatureDev.setBudgetAmount(200000);
        newFeatureDev.setDeadline("2026-01-15");
        developmentDept.addProject(newFeatureDev);
        
        // Calculate average budget for department D001
        double averageBudget = marketingDept.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Create department D001 with email hr@company.com
        Department hrDept = new Department();
        hrDept.setId("D001");
        hrDept.setEmail("hr@company.com");
        
        // Create project "Employee Training" with budget 20000 CNY
        Project employeeTraining = new Project();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudgetAmount(20000);
        employeeTraining.setDeadline("2025-08-15");
        hrDept.addProject(employeeTraining);
        
        // Create department D002 with email sales@company.com
        Department salesDept = new Department();
        salesDept.setId("D002");
        salesDept.setEmail("sales@company.com");
        
        // Create project "Sales Strategy" with budget 40000 CNY
        Project salesStrategy = new Project();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudgetAmount(40000);
        salesStrategy.setDeadline("2025-09-15");
        salesDept.addProject(salesStrategy);
        
        // Create department D003 with email it@company.com
        Department itDept = new Department();
        itDept.setId("D003");
        itDept.setEmail("it@company.com");
        
        // Create project "System Upgrade" with budget 60000 CNY
        Project systemUpgrade = new Project();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudgetAmount(60000);
        systemUpgrade.setDeadline("2025-10-01");
        itDept.addProject(systemUpgrade);
        
        // Calculate average budget for each department
        double avgBudgetD001 = hrDept.calculateAverageBudget();
        double avgBudgetD002 = salesDept.calculateAverageBudget();
        double avgBudgetD003 = itDept.calculateAverageBudget();
        
        // Calculate average of all department averages
        double overallAverage = (avgBudgetD001 + avgBudgetD002 + avgBudgetD003) / 3;
        
        // Verify individual department averages
        assertEquals(20000, avgBudgetD001, 0.001);
        assertEquals(40000, avgBudgetD002, 0.001);
        assertEquals(60000, avgBudgetD003, 0.001);
        
        // Verify overall average: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Create department D004 with email finance@company.com
        Department financeDept = new Department();
        financeDept.setId("D004");
        financeDept.setEmail("finance@company.com");
        
        // Create project "Budget Review" with zero budget
        Project budgetReview = new Project();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudgetAmount(0);
        budgetReview.setDeadline("2025-07-30");
        financeDept.addProject(budgetReview);
        
        // Calculate average budget for department D004
        double averageBudget = financeDept.calculateAverageBudget();
        
        // Expected: 0 CNY
        assertEquals(0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with email research@company.com (no projects)
        Department researchDept = new Department();
        researchDept.setId("D005");
        researchDept.setEmail("research@company.com");
        
        // Calculate average budget for empty department
        double averageBudget = researchDept.calculateAverageBudget();
        
        // Expected: 0 CNY (handled as "No projects available")
        assertEquals(0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Create department D006 with email product@company.com
        Department productDept = new Department();
        productDept.setId("D006");
        productDept.setEmail("product@company.com");
        
        // Create project "Product Launch" with budget 150000 CNY
        Project productLaunch = new Project();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudgetAmount(150000);
        productLaunch.setDeadline("2025-12-01");
        productDept.addProject(productLaunch);
        
        // Create project "Market Analysis" with budget 75000 CNY
        Project marketAnalysis = new Project();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudgetAmount(75000);
        marketAnalysis.setDeadline("2025-10-15");
        productDept.addProject(marketAnalysis);
        
        // Create project "Client Engagement" with budget 50000 CNY
        Project clientEngagement = new Project();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudgetAmount(50000);
        clientEngagement.setDeadline("2025-11-30");
        productDept.addProject(clientEngagement);
        
        // Calculate average budget for department D006
        double averageBudget = productDept.calculateAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}