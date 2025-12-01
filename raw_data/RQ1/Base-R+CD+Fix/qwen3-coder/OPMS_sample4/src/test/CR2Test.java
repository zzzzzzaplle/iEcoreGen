import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private Department department1;
    private Department department2;
    private Department department3;
    private Department department4;
    private Department department5;
    private Department department6;
    
    @Before
    public void setUp() throws Exception {
        // Initialize departments that will be reused across tests
        department1 = new Department();
        department1.setID("D001");
        department1.setEmail("marketing@company.com");
        
        department2 = new Department();
        department2.setID("D002");
        department2.setEmail("development@company.com");
        
        department3 = new Department();
        department3.setID("D003");
        department3.setEmail("it@company.com");
        
        department4 = new Department();
        department4.setID("D004");
        department4.setEmail("finance@company.com");
        
        department5 = new Department();
        department5.setID("D005");
        department5.setEmail("research@company.com");
        
        department6 = new Department();
        department6.setID("D006");
        department6.setEmail("product@company.com");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // SetUp Step 1: Create department D001 (already done in setUp)
        
        // SetUp Step 2: Create project "Ads Campaign" with budget 50000 CNY
        Project project1 = new ResearchProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        project1.setDeadline(sdf.parse("2025-12-31 00:00:00"));
        department1.addProject(project1);
        
        // SetUp Step 3: Create project "Market Research" with budget 30000 CNY
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(sdf.parse("2025-11-30 00:00:00"));
        department1.addProject(project2);
        
        // SetUp Step 4: Create department D002 (already done in setUp)
        
        // SetUp Step 5: Create project "New Feature Development" with budget 200000 CNY
        Project project3 = new ResearchProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(sdf.parse("2026-01-15 00:00:00"));
        department2.addProject(project3);
        
        // SetUp Step 6: Calculate average budget for department D001
        double actualAverage = department1.calculateAverageBudget();
        double expectedAverage = (50000.0 + 30000.0) / 2;
        
        // Expected Output: Average budget = 40000 CNY
        assertEquals("Average budget should be 40000 CNY", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // SetUp Step 1: Create department D001 (already done in setUp)
        
        // SetUp Step 2: Create project "Employee Training" with budget 20000 CNY
        Project project1 = new ResearchProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        project1.setDeadline(sdf.parse("2025-08-15 00:00:00"));
        department1.addProject(project1);
        
        // SetUp Step 3: Create department D002 (already done in setUp)
        
        // SetUp Step 4: Create project "Sales Strategy" with budget 40000 CNY
        Project project2 = new ResearchProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(sdf.parse("2025-09-15 00:00:00"));
        department2.addProject(project2);
        
        // SetUp Step 5: Create department D003 (already done in setUp)
        
        // SetUp Step 6: Create project "System Upgrade" with budget 60000 CNY
        Project project3 = new ResearchProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        project3.setDeadline(sdf.parse("2025-10-01 00:00:00"));
        department3.addProject(project3);
        
        // SetUp Step 7: Calculate average budget for each department
        double avgD001 = department1.calculateAverageBudget();
        double avgD002 = department2.calculateAverageBudget();
        double avgD003 = department3.calculateAverageBudget();
        
        // SetUp Step 8: Calculate overall average across all three departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Expected Output: Average budget of projects in D001: 20000 CNY
        assertEquals("Average budget for D001 should be 20000 CNY", 20000.0, avgD001, 0.001);
        
        // Expected Output: Average budget of projects in D002: 40000 CNY
        assertEquals("Average budget for D002 should be 40000 CNY", 40000.0, avgD002, 0.001);
        
        // Expected Output: Average budget of projects in D003: 60000 CNY
        assertEquals("Average budget for D003 should be 60000 CNY", 60000.0, avgD003, 0.001);
        
        // Expected Output: Overall average = 40000 CNY
        assertEquals("Overall average should be 40000 CNY", 40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // SetUp Step 1: Create department D004 (already done in setUp)
        
        // SetUp Step 2: Create project "Budget Review" with budget 0 CNY
        Project project1 = new ResearchProject();
        project1.setTitle("Budget Review");
        project1.setDescription("Review of the annual budget");
        project1.setBudget(0.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        project1.setDeadline(sdf.parse("2025-07-30 00:00:00"));
        department4.addProject(project1);
        
        // SetUp Step 3: Calculate average budget for department D004
        double actualAverage = department4.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY
        assertEquals("Average budget should be 0 CNY", 0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // SetUp Step 1: Create department D005 (already done in setUp)
        // Department D005 has no projects by default
        
        // SetUp Step 2: Calculate average budget for department D005
        double actualAverage = department5.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY
        assertEquals("Average budget should be 0 CNY for department with no projects", 0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // SetUp Step 1: Create department D006 (already done in setUp)
        
        // SetUp Step 2: Create project "Product Launch" with budget 150000 CNY
        Project project1 = new ResearchProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        project1.setDeadline(sdf.parse("2025-12-01 00:00:00"));
        department6.addProject(project1);
        
        // SetUp Step 3: Create project "Market Analysis" with budget 75000 CNY
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(sdf.parse("2025-10-15 00:00:00"));
        department6.addProject(project2);
        
        // SetUp Step 4: Create project "Client Engagement" with budget 50000 CNY
        Project project3 = new ResearchProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(sdf.parse("2025-11-30 00:00:00"));
        department6.addProject(project3);
        
        // SetUp Step 5: Calculate average budget for department D006
        double actualAverage = department6.calculateAverageBudget();
        double expectedAverage = (150000.0 + 75000.0 + 50000.0) / 3;
        
        // Expected Output: Average budget = 91666.67 CNY
        assertEquals("Average budget should be 91666.67 CNY", expectedAverage, actualAverage, 0.001);
    }
}