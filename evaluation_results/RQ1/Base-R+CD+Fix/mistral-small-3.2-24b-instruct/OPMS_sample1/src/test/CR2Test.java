import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Department department1;
    private Department department2;
    private Department department3;
    private Department department4;
    private Department department5;
    private Department department6;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Initialize departments for reuse in tests
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
    public void testCase1_SingleProjectAverageBudgetCalculation() throws ParseException {
        // SetUp: Create Marketing department (D001) with two projects
        Project project1 = new ProductionProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(dateFormat.parse("2025-11-30 00:00:00"));
        
        department1.addProject(project1);
        department1.addProject(project2);
        
        // SetUp: Create Development department (D002) with one project
        Project project3 = new ProductionProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(dateFormat.parse("2026-01-15 00:00:00"));
        
        department2.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = department1.calculateAverageBudget();
        
        // Expected Output: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws ParseException {
        // SetUp: Create HR department (D001) with one project
        department1.setEmail("hr@company.com");
        Project project1 = new EducationProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        project1.setDeadline(dateFormat.parse("2025-08-15 00:00:00"));
        department1.addProject(project1);
        
        // SetUp: Create Sales department (D002) with one project
        department2.setEmail("sales@company.com");
        Project project2 = new ProductionProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(dateFormat.parse("2025-09-15 00:00:00"));
        department2.addProject(project2);
        
        // SetUp: Create IT department (D003) with one project
        Project project3 = new ProductionProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        project3.setDeadline(dateFormat.parse("2025-10-01 00:00:00"));
        department3.addProject(project3);
        
        // Calculate average budget for each department
        double avgD001 = department1.calculateAverageBudget();
        double avgD002 = department2.calculateAverageBudget();
        double avgD003 = department3.calculateAverageBudget();
        
        // Calculate overall average across all three departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Expected Output: Individual department averages
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        
        // Expected Output: Overall average = (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws ParseException {
        // SetUp: Create Finance department (D004) with one project having zero budget
        Project project = new CommunityProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        project.setDeadline(dateFormat.parse("2025-07-30 00:00:00"));
        department4.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = department4.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // SetUp: Research department (D005) has no projects
        
        // Calculate average budget for department D005
        double averageBudget = department5.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY (handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws ParseException {
        // SetUp: Create Product department (D006) with three projects having different budgets
        Project project1 = new ProductionProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        project1.setDeadline(dateFormat.parse("2025-12-01 00:00:00"));
        
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(dateFormat.parse("2025-10-15 00:00:00"));
        
        Project project3 = new CommunityProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(dateFormat.parse("2025-11-30 00:00:00"));
        
        department6.addProject(project1);
        department6.addProject(project2);
        department6.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = department6.calculateAverageBudget();
        
        // Expected Output: Average budget = (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}