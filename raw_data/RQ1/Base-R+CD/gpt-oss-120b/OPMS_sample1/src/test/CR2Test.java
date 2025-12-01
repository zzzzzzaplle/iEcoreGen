import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private Department departmentD001;
    private Department departmentD002;
    private Department departmentD003;
    private Department departmentD004;
    private Department departmentD005;
    private Department departmentD006;
    
    @Before
    public void setUp() throws Exception {
        // Initialize departments for reuse in tests
        departmentD001 = new Department();
        departmentD002 = new Department();
        departmentD003 = new Department();
        departmentD004 = new Department();
        departmentD005 = new Department();
        departmentD006 = new Department();
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Set up department D001
        departmentD001.setID("D001");
        departmentD001.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" for department D001
        Project adsCampaign = new ResearchProject(); // Using ResearchProject as generic project
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudget(50000.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        adsCampaign.setDeadline(sdf.parse("2025-12-31"));
        departmentD001.addProject(adsCampaign);
        
        // Create project "Market Research" for department D001
        Project marketResearch = new ResearchProject();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudget(30000.0);
        marketResearch.setDeadline(sdf.parse("2025-11-30"));
        departmentD001.addProject(marketResearch);
        
        // Set up department D002 (not used in calculation but part of setup)
        departmentD002.setID("D002");
        departmentD002.setEmail("development@company.com");
        
        // Create project for department D002 (not used in calculation but part of setup)
        Project newFeatureDev = new ResearchProject();
        newFeatureDev.setTitle("New Feature Development");
        newFeatureDev.setDescription("Developing a new feature");
        newFeatureDev.setBudget(200000.0);
        newFeatureDev.setDeadline(sdf.parse("2026-01-15"));
        departmentD002.addProject(newFeatureDev);
        
        // Calculate average budget for department D001
        double averageBudget = departmentD001.calculateAverageBudget();
        
        // Verify expected output: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Set up department D001
        departmentD001.setID("D001");
        departmentD001.setEmail("hr@company.com");
        
        // Create project for department D001
        Project employeeTraining = new ResearchProject();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudget(20000.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        employeeTraining.setDeadline(sdf.parse("2025-08-15"));
        departmentD001.addProject(employeeTraining);
        
        // Set up department D002
        departmentD002.setID("D002");
        departmentD002.setEmail("sales@company.com");
        
        // Create project for department D002
        Project salesStrategy = new ResearchProject();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudget(40000.0);
        salesStrategy.setDeadline(sdf.parse("2025-09-15"));
        departmentD002.addProject(salesStrategy);
        
        // Set up department D003
        departmentD003.setID("D003");
        departmentD003.setEmail("it@company.com");
        
        // Create project for department D003
        Project systemUpgrade = new ResearchProject();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudget(60000.0);
        systemUpgrade.setDeadline(sdf.parse("2025-10-01"));
        departmentD003.addProject(systemUpgrade);
        
        // Calculate average budget for each department
        double avgD001 = departmentD001.calculateAverageBudget();
        double avgD002 = departmentD002.calculateAverageBudget();
        double avgD003 = departmentD003.calculateAverageBudget();
        
        // Calculate overall average across all three departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Verify expected outputs
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Set up department D004
        departmentD004.setID("D004");
        departmentD004.setEmail("finance@company.com");
        
        // Create project with zero budget for department D004
        Project budgetReview = new ResearchProject();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudget(0.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        budgetReview.setDeadline(sdf.parse("2025-07-30"));
        departmentD004.addProject(budgetReview);
        
        // Calculate average budget for department D004
        double averageBudget = departmentD004.calculateAverageBudget();
        
        // Verify expected output: 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() throws Exception {
        // Set up department D005 with no projects
        departmentD005.setID("D005");
        departmentD005.setEmail("research@company.com");
        
        // Calculate average budget for department D005 (no projects)
        double averageBudget = departmentD005.calculateAverageBudget();
        
        // Verify expected output: 0 CNY (handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Set up department D006
        departmentD006.setID("D006");
        departmentD006.setEmail("product@company.com");
        
        // Create project "Product Launch" for department D006
        Project productLaunch = new ResearchProject();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudget(150000.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        productLaunch.setDeadline(sdf.parse("2025-12-01"));
        departmentD006.addProject(productLaunch);
        
        // Create project "Market Analysis" for department D006
        Project marketAnalysis = new ResearchProject();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudget(75000.0);
        marketAnalysis.setDeadline(sdf.parse("2025-10-15"));
        departmentD006.addProject(marketAnalysis);
        
        // Create project "Client Engagement" for department D006
        Project clientEngagement = new ResearchProject();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudget(50000.0);
        clientEngagement.setDeadline(sdf.parse("2025-11-30"));
        departmentD006.addProject(clientEngagement);
        
        // Calculate average budget for department D006
        double averageBudget = departmentD006.calculateAverageBudget();
        
        // Verify expected output: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}