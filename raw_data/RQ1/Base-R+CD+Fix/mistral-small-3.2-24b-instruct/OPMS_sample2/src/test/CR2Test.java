import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department dept1 = new Department("D001", "marketing@company.com");
        
        // Create projects for department D001
        Project project1 = new ProductionProject("Ads Campaign", "Q1 advertising budget", 
                                                50000.0, dateFormat.parse("2025-12-31"), "SITE001");
        Project project2 = new ProductionProject("Market Research", "Research on consumer behavior", 
                                                30000.0, dateFormat.parse("2025-11-30"), "SITE002");
        
        // Add projects to department D001
        dept1.addProject(project1);
        dept1.addProject(project2);
        
        // Create another department D002 with a project (should not affect D001 calculation)
        Department dept2 = new Department("D002", "development@company.com");
        Project project3 = new ProductionProject("New Feature Development", "Developing a new feature", 
                                                200000.0, dateFormat.parse("2026-01-15"), "SITE003");
        dept2.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = dept1.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department dept1 = new Department("D001", "hr@company.com");
        Project project1 = new ProductionProject("Employee Training", "Training for employees", 
                                                20000.0, dateFormat.parse("2025-08-15"), "SITE001");
        dept1.addProject(project1);
        
        // Create department D002
        Department dept2 = new Department("D002", "sales@company.com");
        Project project2 = new ProductionProject("Sales Strategy", "New sales strategy implementation", 
                                                40000.0, dateFormat.parse("2025-09-15"), "SITE002");
        dept2.addProject(project2);
        
        // Create department D003
        Department dept3 = new Department("D003", "it@company.com");
        Project project3 = new ProductionProject("System Upgrade", "Upgrade company systems", 
                                                60000.0, dateFormat.parse("2025-10-01"), "SITE003");
        dept3.addProject(project3);
        
        // Calculate average budget for each department
        double avgDept1 = dept1.calculateAverageBudget();
        double avgDept2 = dept2.calculateAverageBudget();
        double avgDept3 = dept3.calculateAverageBudget();
        
        // Verify individual department averages
        assertEquals(20000.0, avgDept1, 0.001);
        assertEquals(40000.0, avgDept2, 0.001);
        assertEquals(60000.0, avgDept3, 0.001);
        
        // Calculate overall average across all three departments
        double overallAverage = (avgDept1 + avgDept2 + avgDept3) / 3;
        
        // Expected: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004
        Department dept4 = new Department("D004", "finance@company.com");
        
        // Create project with zero budget
        Project project = new ProductionProject("Budget Review", "Review of the annual budget", 
                                                0.0, dateFormat.parse("2025-07-30"), "SITE001");
        dept4.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = dept4.calculateAverageBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department dept5 = new Department("D005", "research@company.com");
        
        // Calculate average budget for empty department
        double averageBudget = dept5.calculateAverageBudget();
        
        // Expected: 0 CNY (handled as no projects available)
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        Department dept6 = new Department("D006", "product@company.com");
        
        // Create projects with varying budgets
        Project project1 = new ProductionProject("Product Launch", "Launching new product", 
                                                150000.0, dateFormat.parse("2025-12-01"), "SITE001");
        Project project2 = new ProductionProject("Market Analysis", "Analysis of market trends", 
                                                75000.0, dateFormat.parse("2025-10-15"), "SITE002");
        Project project3 = new ProductionProject("Client Engagement", "Engaging with key clients", 
                                                50000.0, dateFormat.parse("2025-11-30"), "SITE003");
        
        // Add projects to department
        dept6.addProject(project1);
        dept6.addProject(project2);
        dept6.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = dept6.calculateAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}