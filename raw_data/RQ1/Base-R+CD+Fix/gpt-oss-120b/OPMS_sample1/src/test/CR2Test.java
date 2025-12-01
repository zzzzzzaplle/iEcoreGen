import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Create department D001 with email marketing@company.com
        Department deptD001 = new Department();
        deptD001.setID("D001");
        deptD001.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" with budget 50000 CNY
        Project project1 = new ResearchProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        deptD001.addProject(project1);
        
        // Create project "Market Research" with budget 30000 CNY
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        deptD001.addProject(project2);
        
        // Create department D002 with email development@company.com
        Department deptD002 = new Department();
        deptD002.setID("D002");
        deptD002.setEmail("development@company.com");
        
        // Create project "New Feature Development" with budget 200000 CNY
        Project project3 = new ResearchProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        deptD002.addProject(project3);
        
        // Calculate average budget for department D001
        double actualAverage = deptD001.calculateAverageBudget();
        double expectedAverage = 40000.0; // (50000 + 30000) / 2 = 40000
        
        assertEquals("Average budget calculation for department D001 with two projects", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001 with email hr@company.com
        Department deptD001 = new Department();
        deptD001.setID("D001");
        deptD001.setEmail("hr@company.com");
        
        // Create project "Employee Training" with budget 20000 CNY
        Project project1 = new ResearchProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        project1.setDeadline(dateFormat.parse("2025-08-15 23:59:59"));
        deptD001.addProject(project1);
        
        // Create department D002 with email sales@company.com
        Department deptD002 = new Department();
        deptD002.setID("D002");
        deptD002.setEmail("sales@company.com");
        
        // Create project "Sales Strategy" with budget 40000 CNY
        Project project2 = new ResearchProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(dateFormat.parse("2025-09-15 23:59:59"));
        deptD002.addProject(project2);
        
        // Create department D003 with email it@company.com
        Department deptD003 = new Department();
        deptD003.setID("D003");
        deptD003.setEmail("it@company.com");
        
        // Create project "System Upgrade" with budget 60000 CNY
        Project project3 = new ResearchProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        project3.setDeadline(dateFormat.parse("2025-10-01 23:59:59"));
        deptD003.addProject(project3);
        
        // Calculate average budget for each department individually
        double avgD001 = deptD001.calculateAverageBudget();
        double avgD002 = deptD002.calculateAverageBudget();
        double avgD003 = deptD003.calculateAverageBudget();
        
        // Calculate overall average across all departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Verify individual department averages
        assertEquals("Average budget for department D001 with one project", 
                     20000.0, avgD001, 0.001);
        assertEquals("Average budget for department D002 with one project", 
                     40000.0, avgD002, 0.001);
        assertEquals("Average budget for department D003 with one project", 
                     60000.0, avgD003, 0.001);
        
        // Verify overall average across all departments
        assertEquals("Overall average budget across departments D001, D002, and D003", 
                     40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004 with email finance@company.com
        Department deptD004 = new Department();
        deptD004.setID("D004");
        deptD004.setEmail("finance@company.com");
        
        // Create project "Budget Review" with zero budget
        Project project = new ResearchProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        project.setDeadline(dateFormat.parse("2025-07-30 23:59:59"));
        deptD004.addProject(project);
        
        // Calculate average budget for department D004
        double actualAverage = deptD004.calculateAverageBudget();
        double expectedAverage = 0.0;
        
        assertEquals("Average budget calculation for department D004 with zero budget project", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() throws Exception {
        // Create department D005 with email research@company.com (no projects added)
        Department deptD005 = new Department();
        deptD005.setID("D005");
        deptD005.setEmail("research@company.com");
        
        // Calculate average budget for department D005 with no projects
        double actualAverage = deptD005.calculateAverageBudget();
        double expectedAverage = 0.0;
        
        assertEquals("Average budget calculation for department D005 with no projects", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006 with email product@company.com
        Department deptD006 = new Department();
        deptD006.setID("D006");
        deptD006.setEmail("product@company.com");
        
        // Create project "Product Launch" with budget 150000 CNY
        Project project1 = new ResearchProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        project1.setDeadline(dateFormat.parse("2025-12-01 23:59:59"));
        deptD006.addProject(project1);
        
        // Create project "Market Analysis" with budget 75000 CNY
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(dateFormat.parse("2025-10-15 23:59:59"));
        deptD006.addProject(project2);
        
        // Create project "Client Engagement" with budget 50000 CNY
        Project project3 = new ResearchProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        deptD006.addProject(project3);
        
        // Calculate average budget for department D006
        double actualAverage = deptD006.calculateAverageBudget();
        double expectedAverage = 91666.66666666667; // (150000 + 75000 + 50000) / 3 = 91666.67
        
        assertEquals("Average budget calculation for department D006 with three different budget projects", 
                     expectedAverage, actualAverage, 0.001);
    }
}