import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        // Set up department D001 with two projects
        Project project1 = new ResearchProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 31));
        
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudgetAmount(30000.0);
        project2.setDeadline(LocalDate.of(2025, 11, 30));
        
        departmentD001.addProject(project1);
        departmentD001.addProject(project2);
        
        // Set up department D002 with one project (for isolation)
        Project project3 = new ResearchProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudgetAmount(200000.0);
        project3.setDeadline(LocalDate.of(2026, 1, 15));
        departmentD002.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = departmentD001.getAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Set up department D001 with one project
        Project project1 = new ResearchProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudgetAmount(20000.0);
        project1.setDeadline(LocalDate.of(2025, 8, 15));
        departmentD001.setEmail("hr@company.com");
        departmentD001.addProject(project1);
        
        // Set up department D002 with one project
        Project project2 = new ResearchProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudgetAmount(40000.0);
        project2.setDeadline(LocalDate.of(2025, 9, 15));
        departmentD002.setEmail("sales@company.com");
        departmentD002.addProject(project2);
        
        // Set up department D003 with one project
        Project project3 = new ResearchProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudgetAmount(60000.0);
        project3.setDeadline(LocalDate.of(2025, 10, 1));
        departmentD003.addProject(project3);
        
        // Calculate average budget for each department
        double averageD001 = departmentD001.getAverageBudget();
        double averageD002 = departmentD002.getAverageBudget();
        double averageD003 = departmentD003.getAverageBudget();
        
        // Calculate average across all three departments
        double overallAverage = (averageD001 + averageD002 + averageD003) / 3;
        
        // Verify individual department averages
        assertEquals(20000.0, averageD001, 0.001);
        assertEquals(40000.0, averageD002, 0.001);
        assertEquals(60000.0, averageD003, 0.001);
        
        // Verify overall average: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Set up department D004 with one project having zero budget
        Project project = new ResearchProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudgetAmount(0.0);
        project.setDeadline(LocalDate.of(2025, 7, 30));
        departmentD004.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = departmentD004.getAverageBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Department D005 has no projects (already set up with empty project list)
        
        // Calculate average budget for department D005
        double averageBudget = departmentD005.getAverageBudget();
        
        // Expected: 0 CNY (handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Set up department D006 with three projects having different budgets
        Project project1 = new ResearchProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudgetAmount(150000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 1));
        
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudgetAmount(75000.0);
        project2.setDeadline(LocalDate.of(2025, 10, 15));
        
        Project project3 = new ResearchProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudgetAmount(50000.0);
        project3.setDeadline(LocalDate.of(2025, 11, 30));
        
        departmentD006.addProject(project1);
        departmentD006.addProject(project2);
        departmentD006.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = departmentD006.getAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}