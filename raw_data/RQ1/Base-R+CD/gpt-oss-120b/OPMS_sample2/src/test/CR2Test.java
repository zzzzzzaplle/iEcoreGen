import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        // Initialize departments for reuse in tests
        departmentD001 = new Department();
        departmentD002 = new Department();
        departmentD003 = new Department();
        departmentD004 = new Department();
        departmentD005 = new Department();
        departmentD006 = new Department();
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Set up department D001
        departmentD001.setID("D001");
        departmentD001.setEmail("marketing@company.com");
        
        // Create project 1 for D001
        Project project1 = new ResearchProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        project1.setDeadline(sdf.parse("2025-12-31"));
        departmentD001.addProject(project1);
        
        // Create project 2 for D001
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(sdf.parse("2025-11-30"));
        departmentD001.addProject(project2);
        
        // Set up department D002 (not used in calculation but part of setup)
        departmentD002.setID("D002");
        departmentD002.setEmail("development@company.com");
        
        Project project3 = new ResearchProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(sdf.parse("2026-01-15"));
        departmentD002.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = departmentD001.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Set up department D001
        departmentD001.setID("D001");
        departmentD001.setEmail("hr@company.com");
        
        Project project1 = new ResearchProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        project1.setDeadline(sdf.parse("2025-08-15"));
        departmentD001.addProject(project1);
        
        // Set up department D002
        departmentD002.setID("D002");
        departmentD002.setEmail("sales@company.com");
        
        Project project2 = new ResearchProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(sdf.parse("2025-09-15"));
        departmentD002.addProject(project2);
        
        // Set up department D003
        departmentD003.setID("D003");
        departmentD003.setEmail("it@company.com");
        
        Project project3 = new ResearchProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        project3.setDeadline(sdf.parse("2025-10-01"));
        departmentD003.addProject(project3);
        
        // Calculate average budget for each department
        double avgD001 = departmentD001.calculateAverageBudget();
        double avgD002 = departmentD002.calculateAverageBudget();
        double avgD003 = departmentD003.calculateAverageBudget();
        
        // Calculate overall average across departments
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
        // Set up department D004
        departmentD004.setID("D004");
        departmentD004.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project project = new ResearchProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        project.setDeadline(sdf.parse("2025-07-30"));
        departmentD004.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = departmentD004.calculateAverageBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Set up department D005 with no projects
        departmentD005.setID("D005");
        departmentD005.setEmail("research@company.com");
        
        // Calculate average budget for department D005 (no projects)
        double averageBudget = departmentD005.calculateAverageBudget();
        
        // Expected: 0 CNY (handled as "No projects available" in implementation)
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Set up department D006
        departmentD006.setID("D006");
        departmentD006.setEmail("product@company.com");
        
        // Create project 1 for D006
        Project project1 = new ResearchProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        project1.setDeadline(sdf.parse("2025-12-01"));
        departmentD006.addProject(project1);
        
        // Create project 2 for D006
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(sdf.parse("2025-10-15"));
        departmentD006.addProject(project2);
        
        // Create project 3 for D006
        Project project3 = new ResearchProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(sdf.parse("2025-11-30"));
        departmentD006.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = departmentD006.calculateAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}