import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    
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
        project1.setDeadline(dateFormat.parse("2025-11-30"));
        
        // Add projects to department D001
        dept1.addProject(project1);
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
        double actualAverage = dept1.calculateAverageBudget();
        double expectedAverage = 40000.0;
        
        assertEquals("Average budget for department D001 should be 40000 CNY", 
                      expectedAverage, actualAverage, 0.001);
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
        
        // Verify individual department averages
        assertEquals("Average budget for department D001 should be 20000 CNY", 
                      20000.0, avgDept1, 0.001);
        assertEquals("Average budget for department D002 should be 40000 CNY", 
                      40000.0, avgDept2, 0.001);
        assertEquals("Average budget for department D003 should be 60000 CNY", 
                      60000.0, avgDept3, 0.001);
        
        // Calculate average across all departments (manual calculation as specified)
        double overallAverage = (20000.0 + 40000.0 + 60000.0) / 3;
        double expectedOverallAverage = 40000.0;
        
        assertEquals("Overall average budget should be 40000 CNY", 
                      expectedOverallAverage, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004
        Department dept4 = new Department();
        dept4.setID("D004");
        dept4.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project project1 = new Project() {};
        project1.setTitle("Budget Review");
        project1.setDescription("Review of the annual budget");
        project1.setBudget(0.0);
        project1.setDeadline(dateFormat.parse("2025-07-30"));
        dept4.addProject(project1);
        
        // Calculate average budget for department D004
        double actualAverage = dept4.calculateAverageBudget();
        double expectedAverage = 0.0;
        
        assertEquals("Average budget for department D004 should be 0 CNY", 
                      expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department dept5 = new Department();
        dept5.setID("D005");
        dept5.setEmail("research@company.com");
        
        // Calculate average budget for department D005 (no projects)
        double actualAverage = dept5.calculateAverageBudget();
        double expectedAverage = 0.0;
        
        assertEquals("Average budget for department with no projects should be 0 CNY", 
                      expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        Department dept6 = new Department();
        dept6.setID("D006");
        dept6.setEmail("product@company.com");
        
        // Create projects with varying budgets
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
        
        // Add projects to department D006
        dept6.addProject(project1);
        dept6.addProject(project2);
        dept6.addProject(project3);
        
        // Calculate average budget for department D006
        double actualAverage = dept6.calculateAverageBudget();
        double expectedAverage = 91666.67; // (150000 + 75000 + 50000) / 3 = 91666.666...
        
        assertEquals("Average budget for department D006 should be approximately 91666.67 CNY", 
                      expectedAverage, actualAverage, 0.01); // Using delta of 0.01 for double comparison
    }
}