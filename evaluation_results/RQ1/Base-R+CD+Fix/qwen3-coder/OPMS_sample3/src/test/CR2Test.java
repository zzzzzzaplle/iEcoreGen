import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    
    private Department department1;
    private Department department2;
    private Department department3;
    private Department department4;
    private Department department5;
    private Department department6;
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Initialize departments for tests
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
        // SetUp for Test Case 1
        // Create projects for department D001
        Project project1 = new Project() {};
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31"));
        
        Project project2 = new Project() {};
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(dateFormat.parse("2025-11-30"));
        
        department1.addProject(project1);
        department1.addProject(project2);
        
        // Create project for department D002 (should not affect D001 calculation)
        Project project3 = new Project() {};
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(dateFormat.parse("2026-01-15"));
        department2.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = department1.calculateAverageBudget();
        
        // Expected Output: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // SetUp for Test Case 2
        // Department D001 setup
        Project project1 = new Project() {};
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        project1.setDeadline(dateFormat.parse("2025-08-15"));
        department1.setEmail("hr@company.com");
        department1.addProject(project1);
        
        // Department D002 setup
        Project project2 = new Project() {};
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(dateFormat.parse("2025-09-15"));
        department2.setEmail("sales@company.com");
        department2.addProject(project2);
        
        // Department D003 setup
        Project project3 = new Project() {};
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        project3.setDeadline(dateFormat.parse("2025-10-01"));
        department3.addProject(project3);
        
        // Calculate average budget for each department
        double avgD001 = department1.calculateAverageBudget();
        double avgD002 = department2.calculateAverageBudget();
        double avgD003 = department3.calculateAverageBudget();
        
        // Verify individual department averages
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        
        // Calculate overall average across all three departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Expected Output: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // SetUp for Test Case 3
        Project project = new Project() {};
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        project.setDeadline(dateFormat.parse("2025-07-30"));
        
        department4.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = department4.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // SetUp for Test Case 4 - Department D005 has no projects
        
        // Calculate average budget for department D005 (empty department)
        double averageBudget = department5.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY (or handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // SetUp for Test Case 5
        Project project1 = new Project() {};
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        project1.setDeadline(dateFormat.parse("2025-12-01"));
        
        Project project2 = new Project() {};
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(dateFormat.parse("2025-10-15"));
        
        Project project3 = new Project() {};
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(dateFormat.parse("2025-11-30"));
        
        department6.addProject(project1);
        department6.addProject(project2);
        department6.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = department6.calculateAverageBudget();
        
        // Expected Output: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}