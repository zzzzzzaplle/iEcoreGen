import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private Department departmentD001;
    private Department departmentD002;
    private Department departmentD003;
    private Department departmentD004;
    private Department departmentD005;
    private Department departmentD006;
    
    @Before
    public void setUp() {
        // Initialize departments that will be used across multiple test cases
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
        // Test Case 1: "Single Project Average Budget Calculation"
        
        // Set up department D001 with projects
        Project project1 = new ResearchProject(); // Using ResearchProject as base project type
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 31));
        departmentD001.addProject(project1);
        
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudgetAmount(30000.0);
        project2.setDeadline(LocalDate.of(2025, 11, 30));
        departmentD001.addProject(project2);
        
        // Set up department D002 with a project (not used in calculation but part of setup)
        Project project3 = new ResearchProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudgetAmount(200000.0);
        project3.setDeadline(LocalDate.of(2026, 1, 15));
        departmentD002.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = departmentD001.getAverageProjectBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Test Case 2: "Multiple Departments Average Budget Calculation"
        
        // Set up department D001 with project
        Project project1 = new ResearchProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudgetAmount(20000.0);
        project1.setDeadline(LocalDate.of(2025, 8, 15));
        departmentD001.addProject(project1);
        
        // Set up department D002 with project
        Project project2 = new ResearchProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudgetAmount(40000.0);
        project2.setDeadline(LocalDate.of(2025, 9, 15));
        departmentD002.addProject(project2);
        
        // Set up department D003 with project
        Project project3 = new ResearchProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudgetAmount(60000.0);
        project3.setDeadline(LocalDate.of(2025, 10, 1));
        departmentD003.addProject(project3);
        
        // Calculate average budget for each department
        double avgD001 = departmentD001.getAverageProjectBudget();
        double avgD002 = departmentD002.getAverageProjectBudget();
        double avgD003 = departmentD003.getAverageProjectBudget();
        
        // Calculate overall average across all three departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3.0;
        
        // Verify individual department averages
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        
        // Verify overall average: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Test Case 3: "Single Project Calculation for Zero Budget"
        
        // Set up department D004 with a project having zero budget
        Project project = new ResearchProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudgetAmount(0.0);
        project.setDeadline(LocalDate.of(2025, 7, 30));
        departmentD004.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = departmentD004.getAverageProjectBudget();
        
        // Expected: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Test Case 4: "No Projects in Department"
        
        // Department D005 has no projects (already set up in @Before)
        // Calculate average budget for department D005
        double averageBudget = departmentD005.getAverageProjectBudget();
        
        // Expected: Average budget = 0 CNY (or handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Test Case 5: "Projects with Different Budgets Calculation"
        
        // Set up department D006 with projects having varying budgets
        Project project1 = new ResearchProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudgetAmount(150000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 1));
        departmentD006.addProject(project1);
        
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudgetAmount(75000.0);
        project2.setDeadline(LocalDate.of(2025, 10, 15));
        departmentD006.addProject(project2);
        
        Project project3 = new ResearchProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudgetAmount(50000.0);
        project3.setDeadline(LocalDate.of(2025, 11, 30));
        departmentD006.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = departmentD006.getAverageProjectBudget();
        
        // Expected: Average budget = (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}