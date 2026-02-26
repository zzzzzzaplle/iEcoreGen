import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private Department department;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department marketingDept = new Department();
        marketingDept.setID("D001");
        marketingDept.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign"
        Project adsCampaign = new Project();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudget(50000);
        adsCampaign.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        marketingDept.addProject(adsCampaign);
        
        // Create project "Market Research"
        Project marketResearch = new Project();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudget(30000);
        marketResearch.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        marketingDept.addProject(marketResearch);
        
        // Create department D002 (not relevant for this test)
        Department developmentDept = new Department();
        developmentDept.setID("D002");
        developmentDept.setEmail("development@company.com");
        
        // Create project for D002 (not relevant for this test)
        Project newFeatureDev = new Project();
        newFeatureDev.setTitle("New Feature Development");
        newFeatureDev.setDescription("Developing a new feature");
        newFeatureDev.setBudget(200000);
        newFeatureDev.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        developmentDept.addProject(newFeatureDev);
        
        // Calculate average budget for department D001
        double actualAverage = marketingDept.calculateAverageBudget();
        
        // Expected output: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department hrDept = new Department();
        hrDept.setID("D001");
        hrDept.setEmail("hr@company.com");
        
        // Create project for D001
        Project employeeTraining = new Project();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudget(20000);
        employeeTraining.setDeadline(dateFormat.parse("2025-08-15 23:59:59"));
        hrDept.addProject(employeeTraining);
        
        // Create department D002
        Department salesDept = new Department();
        salesDept.setID("D002");
        salesDept.setEmail("sales@company.com");
        
        // Create project for D002
        Project salesStrategy = new Project();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudget(40000);
        salesStrategy.setDeadline(dateFormat.parse("2025-09-15 23:59:59"));
        salesDept.addProject(salesStrategy);
        
        // Create department D003
        Department itDept = new Department();
        itDept.setID("D003");
        itDept.setEmail("it@company.com");
        
        // Create project for D003
        Project systemUpgrade = new Project();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudget(60000);
        systemUpgrade.setDeadline(dateFormat.parse("2025-10-01 23:59:59"));
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
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004
        Department financeDept = new Department();
        financeDept.setID("D004");
        financeDept.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project budgetReview = new Project();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudget(0);
        budgetReview.setDeadline(dateFormat.parse("2025-07-30 23:59:59"));
        financeDept.addProject(budgetReview);
        
        // Calculate average budget for department D004
        double actualAverage = financeDept.calculateAverageBudget();
        
        // Expected output: 0 CNY
        assertEquals(0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department researchDept = new Department();
        researchDept.setID("D005");
        researchDept.setEmail("research@company.com");
        
        // Calculate average budget for department D005
        double actualAverage = researchDept.calculateAverageBudget();
        
        // Expected output: 0 CNY (handled as "No projects available")
        assertEquals(0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        Department productDept = new Department();
        productDept.setID("D006");
        productDept.setEmail("product@company.com");
        
        // Create project "Product Launch"
        Project productLaunch = new Project();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudget(150000);
        productLaunch.setDeadline(dateFormat.parse("2025-12-01 23:59:59"));
        productDept.addProject(productLaunch);
        
        // Create project "Market Analysis"
        Project marketAnalysis = new Project();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudget(75000);
        marketAnalysis.setDeadline(dateFormat.parse("2025-10-15 23:59:59"));
        productDept.addProject(marketAnalysis);
        
        // Create project "Client Engagement"
        Project clientEngagement = new Project();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudget(50000);
        clientEngagement.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        productDept.addProject(clientEngagement);
        
        // Calculate average budget for department D006
        double actualAverage = productDept.calculateAverageBudget();
        
        // Expected output: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, actualAverage, 0.01);
    }
}