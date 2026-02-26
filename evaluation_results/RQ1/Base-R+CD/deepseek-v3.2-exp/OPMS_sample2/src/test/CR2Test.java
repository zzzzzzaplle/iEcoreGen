import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Department departmentD001;
    private Department departmentD002;
    private Department departmentD003;
    private Department departmentD004;
    private Department departmentD005;
    private Department departmentD006;
    
    @Before
    public void setUp() throws Exception {
        // Initialize departments for test cases
        departmentD001 = new Department();
        departmentD001.setID("D001");
        departmentD001.setEmail("marketing@company.com");
        
        departmentD002 = new Department();
        departmentD002.setID("D002");
        departmentD002.setEmail("development@company.com");
        
        departmentD003 = new Department();
        departmentD003.setID("D003");
        departmentD003.setEmail("it@company.com");
        
        departmentD004 = new Department();
        departmentD004.setID("D004");
        departmentD004.setEmail("finance@company.com");
        
        departmentD005 = new Department();
        departmentD005.setID("D005");
        departmentD005.setEmail("research@company.com");
        
        departmentD006 = new Department();
        departmentD006.setID("D006");
        departmentD006.setEmail("product@company.com");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Test Case 1: "Single Project Average Budget Calculation"
        // Set up department D001 with two projects
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Project project1 = new ResearchProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        
        departmentD001.addProject(project1);
        departmentD001.addProject(project2);
        
        // Set up department D002 with one project (not used in calculation)
        Project project3 = new ResearchProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        departmentD002.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = departmentD001.calculateAverageBudget();
        
        // Expected Output: Average budget = (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Test Case 2: "Multiple Departments Average Budget Calculation"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Set up department D001 (HR) with one project
        Project project1 = new ResearchProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        project1.setDeadline(dateFormat.parse("2025-08-15 23:59:59"));
        departmentD001.setEmail("hr@company.com");
        departmentD001.addProject(project1);
        
        // Set up department D002 (Sales) with one project
        Project project2 = new ResearchProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(dateFormat.parse("2025-09-15 23:59:59"));
        departmentD002.setEmail("sales@company.com");
        departmentD002.addProject(project2);
        
        // Set up department D003 (IT) with one project
        Project project3 = new ResearchProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        project3.setDeadline(dateFormat.parse("2025-10-01 23:59:59"));
        departmentD003.setEmail("it@company.com");
        departmentD003.addProject(project3);
        
        // Calculate average budget for each department
        double avgD001 = departmentD001.calculateAverageBudget();
        double avgD002 = departmentD002.calculateAverageBudget();
        double avgD003 = departmentD003.calculateAverageBudget();
        
        // Calculate overall average across all three departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Expected Output: 
        // Average budget of projects in D001: 20000 CNY
        // Average budget of projects in D002: 40000 CNY
        // Average budget of projects in D003: 60000 CNY
        // Average budget for projects in all departments D001, D002, and D003: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Test Case 3: "Single Project Calculation for Zero Budget"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Set up department D004 with one project having zero budget
        Project project = new ResearchProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        project.setDeadline(dateFormat.parse("2025-07-30 23:59:59"));
        departmentD004.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = departmentD004.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Test Case 4: "No Projects in Department"
        // Department D005 has no projects (already set up in @Before)
        
        // Calculate average budget for department D005
        double averageBudget = departmentD005.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY (or handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Test Case 5: "Projects with Different Budgets Calculation"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Set up department D006 with three projects having different budgets
        Project project1 = new ResearchProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        project1.setDeadline(dateFormat.parse("2025-12-01 23:59:59"));
        
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(dateFormat.parse("2025-10-15 23:59:59"));
        
        Project project3 = new ResearchProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        
        departmentD006.addProject(project1);
        departmentD006.addProject(project2);
        departmentD006.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = departmentD006.calculateAverageBudget();
        
        // Expected Output: Average budget = (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}