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
        // Set up department D001 with projects
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Project project1 = new ResearchProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        project1.setDeadline(sdf.parse("2025-12-31 00:00:00"));
        
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(sdf.parse("2025-11-30 00:00:00"));
        
        departmentD001.addProject(project1);
        departmentD001.addProject(project2);
        
        // Set up department D002 with a project
        Project project3 = new ResearchProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(sdf.parse("2026-01-15 00:00:00"));
        
        departmentD002.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = departmentD001.calculateAverageBudget();
        
        // Verify expected output
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Set up department D001 with a project
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Project project1 = new ResearchProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        project1.setDeadline(sdf.parse("2025-08-15 00:00:00"));
        
        departmentD001.setEmail("hr@company.com");
        departmentD001.addProject(project1);
        
        // Set up department D002 with a project
        Project project2 = new ResearchProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(sdf.parse("2025-09-15 00:00:00"));
        
        departmentD002.setEmail("sales@company.com");
        departmentD002.addProject(project2);
        
        // Set up department D003 with a project
        Project project3 = new ResearchProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        project3.setDeadline(sdf.parse("2025-10-01 00:00:00"));
        
        departmentD003.addProject(project3);
        
        // Calculate average budgets for each department
        double averageD001 = departmentD001.calculateAverageBudget();
        double averageD002 = departmentD002.calculateAverageBudget();
        double averageD003 = departmentD003.calculateAverageBudget();
        
        // Calculate overall average across all three departments
        double overallAverage = (averageD001 + averageD002 + averageD003) / 3;
        
        // Verify expected outputs
        assertEquals(20000.0, averageD001, 0.001);
        assertEquals(40000.0, averageD002, 0.001);
        assertEquals(60000.0, averageD003, 0.001);
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Set up department D004 with a zero-budget project
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Project project = new ResearchProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        project.setDeadline(sdf.parse("2025-07-30 00:00:00"));
        
        departmentD004.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = departmentD004.calculateAverageBudget();
        
        // Verify expected output
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Department D005 has no projects (already set up in @Before)
        
        // Calculate average budget for department D005
        double averageBudget = departmentD005.calculateAverageBudget();
        
        // Verify expected output
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Set up department D006 with projects having varying budgets
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        Project project1 = new ResearchProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        project1.setDeadline(sdf.parse("2025-12-01 00:00:00"));
        
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(sdf.parse("2025-10-15 00:00:00"));
        
        Project project3 = new ResearchProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(sdf.parse("2025-11-30 00:00:00"));
        
        departmentD006.addProject(project1);
        departmentD006.addProject(project2);
        departmentD006.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = departmentD006.calculateAverageBudget();
        
        // Verify expected output
        assertEquals(91666.67, averageBudget, 0.01);
    }
}