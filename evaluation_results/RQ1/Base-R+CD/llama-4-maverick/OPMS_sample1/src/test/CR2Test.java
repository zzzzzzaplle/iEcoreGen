import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class CR2Test {
    
    private Department department;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Set up Department D001
        Department marketingDept = new Department();
        marketingDept.setID("D001");
        marketingDept.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign"
        Project adsCampaign = new ProductionProject();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudget(50000);
        adsCampaign.setDeadline(dateFormat.parse("2025-12-31"));
        marketingDept.addProject(adsCampaign);
        
        // Create project "Market Research"
        Project marketResearch = new ProductionProject();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudget(30000);
        marketResearch.setDeadline(dateFormat.parse("2025-11-30"));
        marketingDept.addProject(marketResearch);
        
        // Set up Department D002 (not used in calculation but part of setup)
        Department developmentDept = new Department();
        developmentDept.setID("D002");
        developmentDept.setEmail("development@company.com");
        
        Project newFeature = new ProductionProject();
        newFeature.setTitle("New Feature Development");
        newFeature.setDescription("Developing a new feature");
        newFeature.setBudget(200000);
        newFeature.setDeadline(dateFormat.parse("2026-01-15"));
        developmentDept.addProject(newFeature);
        
        // Calculate average budget for department D001
        double actualAverage = marketingDept.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Set up Department D001
        Department hrDept = new Department();
        hrDept.setID("D001");
        hrDept.setEmail("hr@company.com");
        
        Project employeeTraining = new ProductionProject();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudget(20000);
        employeeTraining.setDeadline(dateFormat.parse("2025-08-15"));
        hrDept.addProject(employeeTraining);
        
        // Set up Department D002
        Department salesDept = new Department();
        salesDept.setID("D002");
        salesDept.setEmail("sales@company.com");
        
        Project salesStrategy = new ProductionProject();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudget(40000);
        salesStrategy.setDeadline(dateFormat.parse("2025-09-15"));
        salesDept.addProject(salesStrategy);
        
        // Set up Department D003
        Department itDept = new Department();
        itDept.setID("D003");
        itDept.setEmail("it@company.com");
        
        Project systemUpgrade = new ProductionProject();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudget(60000);
        systemUpgrade.setDeadline(dateFormat.parse("2025-10-01"));
        itDept.addProject(systemUpgrade);
        
        // Calculate average budget for each department
        double avgD001 = hrDept.calculateAverageBudget();
        double avgD002 = salesDept.calculateAverageBudget();
        double avgD003 = itDept.calculateAverageBudget();
        
        // Calculate overall average across departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Verify individual department averages
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        
        // Verify overall average: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Set up Department D004
        Department financeDept = new Department();
        financeDept.setID("D004");
        financeDept.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project budgetReview = new ProductionProject();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudget(0);
        budgetReview.setDeadline(dateFormat.parse("2025-07-30"));
        financeDept.addProject(budgetReview);
        
        // Calculate average budget for department D004
        double actualAverage = financeDept.calculateAverageBudget();
        
        // Expected: Average budget = 0 CNY
        assertEquals(0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Set up Department D005 with no projects
        Department researchDept = new Department();
        researchDept.setID("D005");
        researchDept.setEmail("research@company.com");
        
        // Calculate average budget for department D005 (no projects)
        double actualAverage = researchDept.calculateAverageBudget();
        
        // Expected: Average budget = 0 CNY
        assertEquals(0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Set up Department D006
        Department productDept = new Department();
        productDept.setID("D006");
        productDept.setEmail("product@company.com");
        
        // Create project "Product Launch"
        Project productLaunch = new ProductionProject();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudget(150000);
        productLaunch.setDeadline(dateFormat.parse("2025-12-01"));
        productDept.addProject(productLaunch);
        
        // Create project "Market Analysis"
        Project marketAnalysis = new ProductionProject();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudget(75000);
        marketAnalysis.setDeadline(dateFormat.parse("2025-10-15"));
        productDept.addProject(marketAnalysis);
        
        // Create project "Client Engagement"
        Project clientEngagement = new ProductionProject();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudget(50000);
        clientEngagement.setDeadline(dateFormat.parse("2025-11-30"));
        productDept.addProject(clientEngagement);
        
        // Calculate average budget for department D006
        double actualAverage = productDept.calculateAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, actualAverage, 0.01);
    }
}