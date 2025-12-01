import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private Department marketingDepartment;
    private Department developmentDepartment;
    private Department hrDepartment;
    private Department salesDepartment;
    private Department itDepartment;
    private Department financeDepartment;
    private Department researchDepartment;
    private Department productDepartment;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Set up departments with IDs and emails as per test cases
        marketingDepartment = new Department();
        marketingDepartment.setID("D001");
        marketingDepartment.setEmail("marketing@company.com");
        
        developmentDepartment = new Department();
        developmentDepartment.setID("D002");
        developmentDepartment.setEmail("development@company.com");
        
        hrDepartment = new Department();
        hrDepartment.setID("D001");
        hrDepartment.setEmail("hr@company.com");
        
        salesDepartment = new Department();
        salesDepartment.setID("D002");
        salesDepartment.setEmail("sales@company.com");
        
        itDepartment = new Department();
        itDepartment.setID("D003");
        itDepartment.setEmail("it@company.com");
        
        financeDepartment = new Department();
        financeDepartment.setID("D004");
        financeDepartment.setEmail("finance@company.com");
        
        researchDepartment = new Department();
        researchDepartment.setID("D005");
        researchDepartment.setEmail("research@company.com");
        
        productDepartment = new Department();
        productDepartment.setID("D006");
        productDepartment.setEmail("product@company.com");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Create projects for marketing department (D001)
        Project adsCampaign = new Project();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudget(50000.0);
        adsCampaign.setDeadline(dateFormat.parse("2025-12-31"));
        
        Project marketResearch = new Project();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudget(30000.0);
        marketResearch.setDeadline(dateFormat.parse("2025-11-30"));
        
        marketingDepartment.addProject(adsCampaign);
        marketingDepartment.addProject(marketResearch);
        
        // Create project for development department (D002)
        Project newFeatureDevelopment = new Project();
        newFeatureDevelopment.setTitle("New Feature Development");
        newFeatureDevelopment.setDescription("Developing a new feature");
        newFeatureDevelopment.setBudget(200000.0);
        newFeatureDevelopment.setDeadline(dateFormat.parse("2026-01-15"));
        
        developmentDepartment.addProject(newFeatureDevelopment);
        
        // Calculate average budget for marketing department (D001)
        double actualAverage = marketingDepartment.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create project for HR department (D001)
        Project employeeTraining = new Project();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudget(20000.0);
        employeeTraining.setDeadline(dateFormat.parse("2025-08-15"));
        
        hrDepartment.addProject(employeeTraining);
        
        // Create project for sales department (D002)
        Project salesStrategy = new Project();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudget(40000.0);
        salesStrategy.setDeadline(dateFormat.parse("2025-09-15"));
        
        salesDepartment.addProject(salesStrategy);
        
        // Create project for IT department (D003)
        Project systemUpgrade = new Project();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudget(60000.0);
        systemUpgrade.setDeadline(dateFormat.parse("2025-10-01"));
        
        itDepartment.addProject(systemUpgrade);
        
        // Calculate average for each department
        double avgHR = hrDepartment.calculateAverageBudget();
        double avgSales = salesDepartment.calculateAverageBudget();
        double avgIT = itDepartment.calculateAverageBudget();
        
        // Verify individual department averages
        assertEquals(20000.0, avgHR, 0.001);
        assertEquals(40000.0, avgSales, 0.001);
        assertEquals(60000.0, avgIT, 0.001);
        
        // Calculate overall average across all three departments
        double overallAverage = (avgHR + avgSales + avgIT) / 3;
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create project with zero budget for finance department (D004)
        Project budgetReview = new Project();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudget(0.0);
        budgetReview.setDeadline(dateFormat.parse("2025-07-30"));
        
        financeDepartment.addProject(budgetReview);
        
        // Calculate average budget for finance department
        double actualAverage = financeDepartment.calculateAverageBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Research department (D005) has no projects
        double actualAverage = researchDepartment.calculateAverageBudget();
        
        // Expected: 0 CNY (handled as "No projects available")
        assertEquals(0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create projects with varying budgets for product department (D006)
        Project productLaunch = new Project();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudget(150000.0);
        productLaunch.setDeadline(dateFormat.parse("2025-12-01"));
        
        Project marketAnalysis = new Project();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudget(75000.0);
        marketAnalysis.setDeadline(dateFormat.parse("2025-10-15"));
        
        Project clientEngagement = new Project();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudget(50000.0);
        clientEngagement.setDeadline(dateFormat.parse("2025-11-30"));
        
        productDepartment.addProject(productLaunch);
        productDepartment.addProject(marketAnalysis);
        productDepartment.addProject(clientEngagement);
        
        // Calculate average budget for product department
        double actualAverage = productDepartment.calculateAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, actualAverage, 0.01);
    }
}