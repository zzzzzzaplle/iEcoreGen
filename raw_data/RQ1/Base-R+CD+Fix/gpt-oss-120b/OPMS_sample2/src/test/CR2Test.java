import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Test Case 1: "Single Project Average Budget Calculation"
        // Create department D001
        Department marketingDept = new Department();
        marketingDept.setID("D001");
        marketingDept.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" for D001
        Project adsCampaign = new ResearchProject();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudget(50000.0);
        adsCampaign.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        
        // Create project "Market Research" for D001
        Project marketResearch = new ResearchProject();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudget(30000.0);
        marketResearch.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        
        // Add projects to department D001
        marketingDept.addProject(adsCampaign);
        marketingDept.addProject(marketResearch);
        
        // Create department D002 with a project (should not affect D001 calculation)
        Department developmentDept = new Department();
        developmentDept.setID("D002");
        developmentDept.setEmail("development@company.com");
        
        Project newFeature = new ResearchProject();
        newFeature.setTitle("New Feature Development");
        newFeature.setDescription("Developing a new feature");
        newFeature.setBudget(200000.0);
        newFeature.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        developmentDept.addProject(newFeature);
        
        // Calculate average budget for department D001
        double averageBudget = marketingDept.calculateAverageBudget();
        
        // Expected Output: Average budget = (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Test Case 2: "Multiple Departments Average Budget Calculation"
        // Create department D001
        Department hrDept = new Department();
        hrDept.setID("D001");
        hrDept.setEmail("hr@company.com");
        
        // Create project for D001
        Project employeeTraining = new ResearchProject();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudget(20000.0);
        employeeTraining.setDeadline(dateFormat.parse("2025-08-15 23:59:59"));
        hrDept.addProject(employeeTraining);
        
        // Create department D002
        Department salesDept = new Department();
        salesDept.setID("D002");
        salesDept.setEmail("sales@company.com");
        
        // Create project for D002
        Project salesStrategy = new ResearchProject();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudget(40000.0);
        salesDept.setProjects(java.util.Arrays.asList(salesStrategy));
        
        // Create department D003
        Department itDept = new Department();
        itDept.setID("D003");
        itDept.setEmail("it@company.com");
        
        // Create project for D003
        Project systemUpgrade = new ResearchProject();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudget(60000.0);
        systemUpgrade.setDeadline(dateFormat.parse("2025-10-01 23:59:59"));
        itDept.addProject(systemUpgrade);
        
        // Calculate average budget for each department
        double avgD001 = hrDept.calculateAverageBudget();
        double avgD002 = salesDept.calculateAverageBudget();
        double avgD003 = itDept.calculateAverageBudget();
        
        // Calculate overall average across departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Expected Output:
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Test Case 3: "Single Project Calculation for Zero Budget"
        // Create department D004
        Department financeDept = new Department();
        financeDept.setID("D004");
        financeDept.setEmail("finance@company.com");
        
        // Create project with zero budget for D004
        Project budgetReview = new ResearchProject();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudget(0.0);
        budgetReview.setDeadline(dateFormat.parse("2025-07-30 23:59:59"));
        financeDept.addProject(budgetReview);
        
        // Calculate average budget for department D004
        double averageBudget = financeDept.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Test Case 4: "No Projects in Department"
        // Create department D005 with no projects
        Department researchDept = new Department();
        researchDept.setID("D005");
        researchDept.setEmail("research@company.com");
        
        // Calculate average budget for department D005
        double averageBudget = researchDept.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Test Case 5: "Projects with Different Budgets Calculation"
        // Create department D006
        Department productDept = new Department();
        productDept.setID("D006");
        productDept.setEmail("product@company.com");
        
        // Create project "Product Launch" for D006
        Project productLaunch = new ResearchProject();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudget(150000.0);
        productLaunch.setDeadline(dateFormat.parse("2025-12-01 23:59:59"));
        
        // Create project "Market Analysis" for D006
        Project marketAnalysis = new ResearchProject();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudget(75000.0);
        marketAnalysis.setDeadline(dateFormat.parse("2025-10-15 23:59:59"));
        
        // Create project "Client Engagement" for D006
        Project clientEngagement = new ResearchProject();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngaignment.setBudget(50000.0);
        clientEngagement.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        
        // Add all projects to department D006
        productDept.addProject(productLaunch);
        productDept.addProject(marketAnalysis);
        productDept.addProject(clientEngagement);
        
        // Calculate average budget for department D006
        double averageBudget = productDept.calculateAverageBudget();
        
        // Expected Output: Average budget = (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}