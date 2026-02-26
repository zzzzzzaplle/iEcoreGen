import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department department;
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department deptD001 = new Department();
        deptD001.setID("D001");
        deptD001.setEmail("marketing@company.com");
        
        // Create first project for D001
        Project project1 = new ResearchProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        deptD001.addProject(project1);
        
        // Create second project for D001
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        deptD001.addProject(project2);
        
        // Create department D002
        Department deptD002 = new Department();
        deptD002.setID("D002");
        deptD002.setEmail("development@company.com");
        
        // Create project for D002
        Project project3 = new ResearchProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        deptD002.addProject(project3);
        
        // Calculate average budget for department D001
        double actualAverage = deptD001.calculateAverageBudget();
        double expectedAverage = (50000.0 + 30000.0) / 2;
        
        assertEquals("Average budget for department D001 should be 40000 CNY", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department deptD001 = new Department();
        deptD001.setID("D001");
        deptD001.setEmail("hr@company.com");
        
        // Create project for D001
        Project project1 = new ResearchProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        project1.setDeadline(dateFormat.parse("2025-08-15 23:59:59"));
        deptD001.addProject(project1);
        
        // Create department D002
        Department deptD002 = new Department();
        deptD002.setID("D002");
        deptD002.setEmail("sales@company.com");
        
        // Create project for D002
        Project project2 = new ResearchProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(dateFormat.parse("2025-09-15 23:59:59"));
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
        project3.setDeadline(dateFormat.parse("2025-10-01 23:59:59"));
        deptD003.addProject(project3);
        
        // Calculate average budgets for individual departments
        double avgD001 = deptD001.calculateAverageBudget();
        double avgD002 = deptD002.calculateAverageBudget();
        double avgD003 = deptD003.calculateAverageBudget();
        
        // Calculate overall average across all departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        
        // Verify individual department averages
        assertEquals("Average budget for D001 should be 20000 CNY", 20000.0, avgD001, 0.001);
        assertEquals("Average budget for D002 should be 40000 CNY", 40000.0, avgD002, 0.001);
        assertEquals("Average budget for D003 should be 60000 CNY", 60000.0, avgD003, 0.001);
        
        // Verify overall average
        assertEquals("Overall average budget should be 40000 CNY", 40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004
        Department deptD004 = new Department();
        deptD004.setID("D004");
        deptD004.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project project = new ResearchProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        project.setDeadline(dateFormat.parse("2025-07-30 23:59:59"));
        deptD004.addProject(project);
        
        // Calculate average budget
        double actualAverage = deptD004.calculateAverageBudget();
        
        assertEquals("Average budget for single zero-budget project should be 0 CNY", 
                     0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department deptD005 = new Department();
        deptD005.setID("D005");
        deptD005.setEmail("research@company.com");
        
        // Calculate average budget for department with no projects
        double actualAverage = deptD005.calculateAverageBudget();
        
        assertEquals("Average budget for department with no projects should be 0 CNY", 
                     0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        Department deptD006 = new Department();
        deptD006.setID("D006");
        deptD006.setEmail("product@company.com");
        
        // Create first project
        Project project1 = new ResearchProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        project1.setDeadline(dateFormat.parse("2025-12-01 23:59:59"));
        deptD006.addProject(project1);
        
        // Create second project
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(dateFormat.parse("2025-10-15 23:59:59"));
        deptD006.addProject(project2);
        
        // Create third project
        Project project3 = new ResearchProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        deptD006.addProject(project3);
        
        // Calculate average budget
        double actualAverage = deptD006.calculateAverageBudget();
        double expectedAverage = (150000.0 + 75000.0 + 50000.0) / 3;
        
        assertEquals("Average budget for department D006 should be 91666.67 CNY", 
                     expectedAverage, actualAverage, 0.001);
    }
}