import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Department departmentD001;
    private Department departmentD002;
    private Department departmentD003;
    private Department departmentD004;
    private Department departmentD005;
    private Department departmentD006;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Create department D001
        departmentD001 = new Department();
        departmentD001.setID("D001");
        departmentD001.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" for D001
        Project adsCampaign = new ResearchProject(); // Using ResearchProject as generic project
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudget(50000.0);
        adsCampaign.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        
        // Create project "Market Research" for D001
        Project marketResearch = new ResearchProject();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudget(30000.0);
        marketResearch.setDeadline(dateFormat.parse("2025-11-30 00:00:00"));
        
        // Add projects to department D001
        departmentD001.addProject(adsCampaign);
        departmentD001.addProject(marketResearch);
        
        // Create department D002
        departmentD002 = new Department();
        departmentD002.setID("D002");
        departmentD002.setEmail("development@company.com");
        
        // Create project for D002
        Project newFeatureDevelopment = new ResearchProject();
        newFeatureDevelopment.setTitle("New Feature Development");
        newFeatureDevelopment.setDescription("Developing a new feature");
        newFeatureDevelopment.setBudget(200000.0);
        newFeatureDevelopment.setDeadline(dateFormat.parse("2026-01-15 00:00:00"));
        departmentD002.addProject(newFeatureDevelopment);
        
        // Calculate average budget for department D001
        double averageBudget = departmentD001.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001
        departmentD001 = new Department();
        departmentD001.setID("D001");
        departmentD001.setEmail("hr@company.com");
        
        // Create project for D001
        Project employeeTraining = new ResearchProject();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudget(20000.0);
        employeeTraining.setDeadline(dateFormat.parse("2025-08-15 00:00:00"));
        departmentD001.addProject(employeeTraining);
        
        // Create department D002
        departmentD002 = new Department();
        departmentD002.setID("D002");
        departmentD002.setEmail("sales@company.com");
        
        // Create project for D002
        Project salesStrategy = new ResearchProject();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudget(40000.0);
        salesStrategy.setDeadline(dateFormat.parse("2025-09-15 00:00:00"));
        departmentD002.addProject(salesStrategy);
        
        // Create department D003
        departmentD003 = new Department();
        departmentD003.setID("D003");
        departmentD003.setEmail("it@company.com");
        
        // Create project for D003
        Project systemUpgrade = new ResearchProject();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudget(60000.0);
        systemUpgrade.setDeadline(dateFormat.parse("2025-10-01 00:00:00"));
        departmentD003.addProject(systemUpgrade);
        
        // Calculate average budgets for each department
        double avgD001 = departmentD001.calculateAverageBudget();
        double avgD002 = departmentD002.calculateAverageBudget();
        double avgD003 = departmentD003.calculateAverageBudget();
        
        // Calculate overall average across all three departments
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
        departmentD004 = new Department();
        departmentD004.setID("D004");
        departmentD004.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project budgetReview = new ResearchProject();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudget(0.0);
        budgetReview.setDeadline(dateFormat.parse("2025-07-30 00:00:00"));
        departmentD004.addProject(budgetReview);
        
        // Calculate average budget for department D004
        double averageBudget = departmentD004.calculateAverageBudget();
        
        // Expected: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        departmentD005 = new Department();
        departmentD005.setID("D005");
        departmentD005.setEmail("research@company.com");
        
        // Calculate average budget for department D005 (no projects)
        double averageBudget = departmentD005.calculateAverageBudget();
        
        // Expected: Average budget = 0 CNY (handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        departmentD006 = new Department();
        departmentD006.setID("D006");
        departmentD006.setEmail("product@company.com");
        
        // Create project "Product Launch"
        Project productLaunch = new ResearchProject();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudget(150000.0);
        productLaunch.setDeadline(dateFormat.parse("2025-12-01 00:00:00"));
        
        // Create project "Market Analysis"
        Project marketAnalysis = new ResearchProject();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudget(75000.0);
        marketAnalysis.setDeadline(dateFormat.parse("2025-10-15 00:00:00"));
        
        // Create project "Client Engagement"
        Project clientEngagement = new ResearchProject();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudget(50000.0);
        clientEngagement.setDeadline(dateFormat.parse("2025-11-30 00:00:00"));
        
        // Add all projects to department D006
        departmentD006.addProject(productLaunch);
        departmentD006.addProject(marketAnalysis);
        departmentD006.addProject(clientEngagement);
        
        // Calculate average budget for department D006
        double averageBudget = departmentD006.calculateAverageBudget();
        
        // Expected: Average budget = (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}