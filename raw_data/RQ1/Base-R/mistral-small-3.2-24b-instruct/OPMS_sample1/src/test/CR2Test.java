import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private Department departmentD001;
    private Department departmentD002;
    private Department departmentD003;
    private Department departmentD004;
    private Department departmentD005;
    private Department departmentD006;
    
    @Before
    public void setUp() {
        // Initialize departments for reuse in tests
        departmentD001 = new Department();
        departmentD001.setId("D001");
        departmentD001.setEmail("marketing@company.com");
        
        departmentD002 = new Department();
        departmentD002.setId("D002");
        departmentD002.setEmail("development@company.com");
        
        departmentD003 = new Department();
        departmentD003.setId("D003");
        departmentD003.setEmail("it@company.com");
        
        departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("finance@company.com");
        
        departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("research@company.com");
        
        departmentD006 = new Department();
        departmentD006.setId("D006");
        departmentD006.setEmail("product@company.com");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // SetUp: Create department D001 with projects
        Project project1 = new ProductionProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudgetAmount(50000);
        project1.setDeadline("2025-12-31");
        
        Project project2 = new ProductionProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudgetAmount(30000);
        project2.setDeadline("2025-11-30");
        
        departmentD001.addProject(project1);
        departmentD001.addProject(project2);
        
        // SetUp: Create department D002 with project
        Project project3 = new ProductionProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudgetAmount(200000);
        project3.setDeadline("2026-01-15");
        
        departmentD002.addProject(project3);
        
        // Calculate average budget for department D001
        double actualAverage = departmentD001.calculateAverageBudget();
        double expectedAverage = 40000.0; // (50000 + 30000) / 2 = 40000 CNY
        
        assertEquals("Average budget calculation for department D001 should be correct", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // SetUp: Create department D001 with project
        Project project1 = new ProductionProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudgetAmount(20000);
        project1.setDeadline("2025-08-15");
        
        departmentD001.setEmail("hr@company.com");
        departmentD001.addProject(project1);
        
        // SetUp: Create department D002 with project
        Project project2 = new ProductionProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudgetAmount(40000);
        project2.setDeadline("2025-09-15");
        
        departmentD002.setEmail("sales@company.com");
        departmentD002.addProject(project2);
        
        // SetUp: Create department D003 with project
        Project project3 = new ProductionProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudgetAmount(60000);
        project3.setDeadline("2025-10-01");
        
        departmentD003.setEmail("it@company.com");
        departmentD003.addProject(project3);
        
        // Calculate average budget for each department
        double avgD001 = departmentD001.calculateAverageBudget();
        double avgD002 = departmentD002.calculateAverageBudget();
        double avgD003 = departmentD003.calculateAverageBudget();
        
        // Calculate average budget across all three departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Verify individual department averages
        assertEquals("Average budget for D001 should be 20000", 20000.0, avgD001, 0.001);
        assertEquals("Average budget for D002 should be 40000", 40000.0, avgD002, 0.001);
        assertEquals("Average budget for D003 should be 60000", 60000.0, avgD003, 0.001);
        
        // Verify overall average across departments
        double expectedOverallAverage = 40000.0; // (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals("Overall average budget should be 40000", expectedOverallAverage, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // SetUp: Create department D004 with zero budget project
        Project project = new ProductionProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudgetAmount(0);
        project.setDeadline("2025-07-30");
        
        departmentD004.addProject(project);
        
        // Calculate average budget for department D004
        double actualAverage = departmentD004.calculateAverageBudget();
        double expectedAverage = 0.0; // Single project with zero budget
        
        assertEquals("Average budget for zero budget project should be 0", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // SetUp: Department D005 has no projects (already initialized in setUp)
        
        // Calculate average budget for department D005 with no projects
        double actualAverage = departmentD005.calculateAverageBudget();
        double expectedAverage = 0.0; // No projects available
        
        assertEquals("Average budget for department with no projects should be 0", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // SetUp: Create department D006 with projects having different budgets
        Project project1 = new ProductionProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudgetAmount(150000);
        project1.setDeadline("2025-12-01");
        
        Project project2 = new ProductionProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudgetAmount(75000);
        project2.setDeadline("2025-10-15");
        
        Project project3 = new ProductionProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudgetAmount(50000);
        project3.setDeadline("2025-11-30");
        
        departmentD006.addProject(project1);
        departmentD006.addProject(project2);
        departmentD006.addProject(project3);
        
        // Calculate average budget for department D006
        double actualAverage = departmentD006.calculateAverageBudget();
        double expectedAverage = 91666.67; // (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        
        assertEquals("Average budget calculation for varying budgets should be correct", 
                     expectedAverage, actualAverage, 0.01); // Using delta 0.01 for floating point precision
    }
}