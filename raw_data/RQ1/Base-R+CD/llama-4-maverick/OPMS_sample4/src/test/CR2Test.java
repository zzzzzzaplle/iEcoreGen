import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR2Test {
    private Department department;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        department = new Department();
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department marketingDept = new Department();
        marketingDept.setID("D001");
        marketingDept.setEmail("marketing@company.com");
        
        // Create Ads Campaign project
        Project adsCampaign = new ProductionProject();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudget(50000.0);
        adsCampaign.setDeadline(dateFormat.parse("2025-12-31"));
        marketingDept.addProject(adsCampaign);
        
        // Create Market Research project
        Project marketResearch = new ProductionProject();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudget(30000.0);
        marketResearch.setDeadline(dateFormat.parse("2025-11-30"));
        marketingDept.addProject(marketResearch);
        
        // Create another department D002
        Department developmentDept = new Department();
        developmentDept.setID("D002");
        developmentDept.setEmail("development@company.com");
        
        // Create New Feature Development project
        Project newFeatureDev = new ProductionProject();
        newFeatureDev.setTitle("New Feature Development");
        newFeatureDev.setDescription("Developing a new feature");
        newFeatureDev.setBudget(200000.0);
        newFeatureDev.setDeadline(dateFormat.parse("2026-01-15"));
        developmentDept.addProject(newFeatureDev);
        
        // Calculate average budget for department D001
        double averageBudget = marketingDept.calculateAverageBudget();
        
        // Verify expected output
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department hrDept = new Department();
        hrDept.setID("D001");
        hrDept.setEmail("hr@company.com");
        
        // Create Employee Training project
        Project employeeTraining = new ProductionProject();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudget(20000.0);
        employeeTraining.setDeadline(dateFormat.parse("2025-08-15"));
        hrDept.addProject(employeeTraining);
        
        // Create department D002
        Department salesDept = new Department();
        salesDept.setID("D002");
        salesDept.setEmail("sales@company.com");
        
        // Create Sales Strategy project
        Project salesStrategy = new ProductionProject();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudget(40000.0);
        salesStrategy.setDeadline(dateFormat.parse("2025-09-15"));
        salesDept.addProject(salesStrategy);
        
        // Create department D003
        Department itDept = new Department();
        itDept.setID("D003");
        itDept.setEmail("it@company.com");
        
        // Create System Upgrade project
        Project systemUpgrade = new ProductionProject();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudget(60000.0);
        systemUpgrade.setDeadline(dateFormat.parse("2025-10-01"));
        itDept.addProject(systemUpgrade);
        
        // Calculate average budget for each department
        double avgD001 = hrDept.calculateAverageBudget();
        double avgD002 = salesDept.calculateAverageBudget();
        double avgD003 = itDept.calculateAverageBudget();
        
        // Calculate overall average across all departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Verify expected outputs
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004
        Department financeDept = new Department();
        financeDept.setID("D004");
        financeDept.setEmail("finance@company.com");
        
        // Create Budget Review project with zero budget
        Project budgetReview = new ProductionProject();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudget(0.0);
        budgetReview.setDeadline(dateFormat.parse("2025-07-30"));
        financeDept.addProject(budgetReview);
        
        // Calculate average budget for department D004
        double averageBudget = financeDept.calculateAverageBudget();
        
        // Verify expected output
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department researchDept = new Department();
        researchDept.setID("D005");
        researchDept.setEmail("research@company.com");
        
        // Calculate average budget for department D005 (no projects)
        double averageBudget = researchDept.calculateAverageBudget();
        
        // Verify expected output
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        Department productDept = new Department();
        productDept.setID("D006");
        productDept.setEmail("product@company.com");
        
        // Create Product Launch project
        Project productLaunch = new ProductionProject();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudget(150000.0);
        productLaunch.setDeadline(dateFormat.parse("2025-12-01"));
        productDept.addProject(productLaunch);
        
        // Create Market Analysis project
        Project marketAnalysis = new ProductionProject();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudget(75000.0);
        marketAnalysis.setDeadline(dateFormat.parse("2025-10-15"));
        productDept.addProject(marketAnalysis);
        
        // Create Client Engagement project
        Project clientEngagement = new ProductionProject();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudget(50000.0);
        clientEngagement.setDeadline(dateFormat.parse("2025-11-30"));
        productDept.addProject(clientEngagement);
        
        // Calculate average budget for department D006
        double averageBudget = productDept.calculateAverageBudget();
        
        // Verify expected output
        assertEquals(91666.67, averageBudget, 0.01);
    }
}