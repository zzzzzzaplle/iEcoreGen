import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Department department;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" for department D001
        Project project1 = new Project() {};
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31"));
        dept1.addProject(project1);
        
        // Create project "Market Research" for department D001
        Project project2 = new Project() {};
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(dateFormat.parse("2025-11-30"));
        dept1.addProject(project2);
        
        // Create department D002
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("development@company.com");
        
        // Create project for department D002
        Project project3 = new Project() {};
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(dateFormat.parse("2026-01-15"));
        dept2.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = dept1.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("hr@company.com");
        
        // Create project for department D001
        Project project1 = new Project() {};
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        project1.setDeadline(dateFormat.parse("2025-08-15"));
        dept1.addProject(project1);
        
        // Create department D002
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("sales@company.com");
        
        // Create project for department D002
        Project project2 = new Project() {};
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(dateFormat.parse("2025-09-15"));
        dept2.addProject(project2);
        
        // Create department D003
        Department dept3 = new Department();
        dept3.setID("D003");
        dept3.setEmail("it@company.com");
        
        // Create project for department D003
        Project project3 = new Project() {};
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        project3.setDeadline(dateFormat.parse("2025-10-01"));
        dept3.addProject(project3);
        
        // Calculate average budget for each department
        double avgDept1 = dept1.calculateAverageBudget();
        double avgDept2 = dept2.calculateAverageBudget();
        double avgDept3 = dept3.calculateAverageBudget();
        
        // Calculate overall average across all three departments
        double overallAverage = (avgDept1 + avgDept2 + avgDept3) / 3;
        
        // Verify individual department averages
        assertEquals(20000.0, avgDept1, 0.001);
        assertEquals(40000.0, avgDept2, 0.001);
        assertEquals(60000.0, avgDept3, 0.001);
        
        // Verify overall average: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004
        Department dept4 = new Department();
        dept4.setID("D004");
        dept4.setEmail("finance@company.com");
        
        // Create project with zero budget for department D004
        Project project = new Project() {};
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        project.setDeadline(dateFormat.parse("2025-07-30"));
        dept4.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = dept4.calculateAverageBudget();
        
        // Expected: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department dept5 = new Department();
        dept5.setID("D005");
        dept5.setEmail("research@company.com");
        
        // Calculate average budget for department D005 (no projects)
        double averageBudget = dept5.calculateAverageBudget();
        
        // Expected: Average budget = 0 CNY (handled as no projects available)
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        Department dept6 = new Department();
        dept6.setID("D006");
        dept6.setEmail("product@company.com");
        
        // Create project "Product Launch" for department D006
        Project project1 = new Project() {};
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        project1.setDeadline(dateFormat.parse("2025-12-01"));
        dept6.addProject(project1);
        
        // Create project "Market Analysis" for department D006
        Project project2 = new Project() {};
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(dateFormat.parse("2025-10-15"));
        dept6.addProject(project2);
        
        // Create project "Client Engagement" for department D006
        Project project3 = new Project() {};
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(dateFormat.parse("2025-11-30"));
        dept6.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = dept6.calculateAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}