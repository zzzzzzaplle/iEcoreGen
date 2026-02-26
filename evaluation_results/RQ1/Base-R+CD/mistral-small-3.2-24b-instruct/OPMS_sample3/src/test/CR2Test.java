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
    
    @Before
    public void setUp() throws Exception {
        // Initialize all departments once to avoid duplication in tests
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
        // Set up test data: Marketing department with 2 projects
        Project adsCampaign = new Project();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudget(50000.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        adsCampaign.setDeadline(sdf.parse("2025-12-31"));
        
        Project marketResearch = new Project();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudget(30000.0);
        marketResearch.setDeadline(sdf.parse("2025-11-30"));
        
        marketingDepartment.addProject(adsCampaign);
        marketingDepartment.addProject(marketResearch);
        
        // Development department with 1 project (should not affect the calculation)
        Project newFeatureDevelopment = new Project();
        newFeatureDevelopment.setTitle("New Feature Development");
        newFeatureDevelopment.setDescription("Developing a new feature");
        newFeatureDevelopment.setBudget(200000.0);
        newFeatureDevelopment.setDeadline(sdf.parse("2026-01-15"));
        
        developmentDepartment.addProject(newFeatureDevelopment);
        
        // Calculate average budget for marketing department
        double actualAverage = marketingDepartment.calculateAverageBudget();
        
        // Verify expected result: (50000 + 30000) / 2 = 40000
        assertEquals(40000.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Set up test data: Three departments with one project each
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // HR department with 1 project
        Project employeeTraining = new Project();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudget(20000.0);
        employeeTraining.setDeadline(sdf.parse("2025-08-15"));
        hrDepartment.addProject(employeeTraining);
        
        // Sales department with 1 project
        Project salesStrategy = new Project();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudget(40000.0);
        salesStrategy.setDeadline(sdf.parse("2025-09-15"));
        salesDepartment.addProject(salesStrategy);
        
        // IT department with 1 project
        Project systemUpgrade = new Project();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudget(60000.0);
        systemUpgrade.setDeadline(sdf.parse("2025-10-01"));
        itDepartment.addProject(systemUpgrade);
        
        // Calculate average for each department
        double hrAverage = hrDepartment.calculateAverageBudget();
        double salesAverage = salesDepartment.calculateAverageBudget();
        double itAverage = itDepartment.calculateAverageBudget();
        
        // Verify individual department averages
        assertEquals(20000.0, hrAverage, 0.001);
        assertEquals(40000.0, salesAverage, 0.001);
        assertEquals(60000.0, itAverage, 0.001);
        
        // Calculate overall average across all three departments
        double overallAverage = (hrAverage + salesAverage + itAverage) / 3;
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Set up test data: Finance department with 1 project having zero budget
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Project budgetReview = new Project();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudget(0.0);
        budgetReview.setDeadline(sdf.parse("2025-07-30"));
        
        financeDepartment.addProject(budgetReview);
        
        // Calculate average budget for finance department
        double actualAverage = financeDepartment.calculateAverageBudget();
        
        // Verify expected result: 0
        assertEquals(0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Research department has no projects (default empty list)
        // Calculate average budget for research department
        double actualAverage = researchDepartment.calculateAverageBudget();
        
        // Verify expected result: 0 (handled as "No projects available")
        assertEquals(0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Set up test data: Product department with 3 projects of varying budgets
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Project productLaunch = new Project();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudget(150000.0);
        productLaunch.setDeadline(sdf.parse("2025-12-01"));
        
        Project marketAnalysis = new Project();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudget(75000.0);
        marketAnalysis.setDeadline(sdf.parse("2025-10-15"));
        
        Project clientEngagement = new Project();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudget(50000.0);
        clientEngagement.setDeadline(sdf.parse("2025-11-30"));
        
        productDepartment.addProject(productLaunch);
        productDepartment.addProject(marketAnalysis);
        productDepartment.addProject(clientEngagement);
        
        // Calculate average budget for product department
        double actualAverage = productDepartment.calculateAverageBudget();
        
        // Verify expected result: (150000 + 75000 + 50000) / 3 = 91666.666...
        assertEquals(91666.666, actualAverage, 0.001);
    }
}