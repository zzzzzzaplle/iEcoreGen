import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private Department department;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // SetUp: Create department D001
        Department deptD001 = new Department();
        deptD001.setID("D001");
        deptD001.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" for D001
        Project project1 = new ProductionProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31"));
        deptD001.addProject(project1);
        
        // Create project "Market Research" for D001
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(dateFormat.parse("2025-11-30"));
        deptD001.addProject(project2);
        
        // Create department D002
        Department deptD002 = new Department();
        deptD002.setID("D002");
        deptD002.setEmail("development@company.com");
        
        // Create project for D002
        Project project3 = new ProductionProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(dateFormat.parse("2026-01-15"));
        deptD002.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = deptD001.calculateAverageBudget();
        
        // Expected Output: Average budget = (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department deptD001 = new Department();
        deptD001.setID("D001");
        deptD001.setEmail("hr@company.com");
        
        // Create project for D001
        Project project1 = new EducationProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        project1.setDeadline(dateFormat.parse("2025-08-15"));
        deptD001.addProject(project1);
        
        // Create department D002
        Department deptD002 = new Department();
        deptD002.setID("D002");
        deptD002.setEmail("sales@company.com");
        
        // Create project for D002
        Project project2 = new ProductionProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(dateFormat.parse("2025-09-15"));
        deptD002.addProject(project2);
        
        // Create department D003
        Department deptD003 = new Department();
        deptD003.setID("D003");
        deptD003.setEmail("it@company.com");
        
        // Create project for D003
        Project project3 = new ResearchProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        project3.setDeadline(dateFormat.parse("2025-10-01"));
        deptD003.addProject(project3);
        
        // Calculate average budget for each department
        double avgD001 = deptD001.calculateAverageBudget();
        double avgD002 = deptD002.calculateAverageBudget();
        double avgD003 = deptD003.calculateAverageBudget();
        
        // Expected Output: 
        // Average budget of projects in D001: 20000 CNY
        // Average budget of projects in D002: 40000 CNY
        // Average budget of projects in D003: 60000 CNY
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        
        // Calculate average budget for all three departments combined
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Expected Output: Average budget for projects in all departments D001, D002, and D003: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004
        Department deptD004 = new Department();
        deptD004.setID("D004");
        deptD004.setEmail("finance@company.com");
        
        // Create project with zero budget for D004
        Project project = new CommunityProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        project.setDeadline(dateFormat.parse("2025-07-30"));
        deptD004.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = deptD004.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department deptD005 = new Department();
        deptD005.setID("D005");
        deptD005.setEmail("research@company.com");
        
        // Calculate average budget for department D005
        double averageBudget = deptD005.calculateAverageBudget();
        
        // Expected Output: Average budget = 0 CNY (or handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        Department deptD006 = new Department();
        deptD006.setID("D006");
        deptD006.setEmail("product@company.com");
        
        // Create project "Product Launch" for D006
        Project project1 = new ProductionProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        project1.setDeadline(dateFormat.parse("2025-12-01"));
        deptD006.addProject(project1);
        
        // Create project "Market Analysis" for D006
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(dateFormat.parse("2025-10-15"));
        deptD006.addProject(project2);
        
        // Create project "Client Engagement" for D006
        Project project3 = new CommunityProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(dateFormat.parse("2025-11-30"));
        deptD006.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = deptD006.calculateAverageBudget();
        
        // Expected Output: Average budget = (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}