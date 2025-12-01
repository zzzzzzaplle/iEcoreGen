import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department department1;
    private Department department2;
    private Department department3;
    private Department department4;
    private Department department5;
    private Department department6;
    
    @Before
    public void setUp() {
        // Initialize departments that will be used across multiple test cases
        department1 = new Department();
        department1.setId("D001");
        department1.setEmail("marketing@company.com");
        
        department2 = new Department();
        department2.setId("D002");
        department2.setEmail("development@company.com");
        
        department3 = new Department();
        department3.setId("D003");
        department3.setEmail("it@company.com");
        
        department4 = new Department();
        department4.setId("D004");
        department4.setEmail("finance@company.com");
        
        department5 = new Department();
        department5.setId("D005");
        department5.setEmail("research@company.com");
        
        department6 = new Department();
        department6.setId("D006");
        department6.setEmail("product@company.com");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Set up department D001 with two projects
        List<Project> projectsD001 = new ArrayList<>();
        
        Project project1 = new Project();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline("2025-12-31");
        projectsD001.add(project1);
        
        Project project2 = new Project();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudgetAmount(30000.0);
        project2.setDeadline("2025-11-30");
        projectsD001.add(project2);
        
        department1.setProjects(projectsD001);
        
        // Set up department D002 with one project (not used in calculation for this test case)
        List<Project> projectsD002 = new ArrayList<>();
        Project project3 = new Project();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudgetAmount(200000.0);
        project3.setDeadline("2026-01-15");
        projectsD002.add(project3);
        department2.setProjects(projectsD002);
        
        // Calculate average budget for department D001
        double actualAverage = department1.getAverageBudgetOfAllProjects();
        double expectedAverage = 40000.0; // (50000 + 30000) / 2 = 40000
        
        assertEquals("Average budget calculation for department D001 with two projects", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Set up department D001 with one project
        List<Project> projectsD001 = new ArrayList<>();
        Project project1 = new Project();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudgetAmount(20000.0);
        project1.setDeadline("2025-08-15");
        projectsD001.add(project1);
        department1.setProjects(projectsD001);
        
        // Set up department D002 with one project
        List<Project> projectsD002 = new ArrayList<>();
        Project project2 = new Project();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudgetAmount(40000.0);
        project2.setDeadline("2025-09-15");
        projectsD002.add(project2);
        department2.setProjects(projectsD002);
        
        // Set up department D003 with one project
        List<Project> projectsD003 = new ArrayList<>();
        Project project3 = new Project();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudgetAmount(60000.0);
        project3.setDeadline("2025-10-01");
        projectsD003.add(project3);
        department3.setProjects(projectsD003);
        
        // Calculate average budget for each department
        double avgD001 = department1.getAverageBudgetOfAllProjects();
        double avgD002 = department2.getAverageBudgetOfAllProjects();
        double avgD003 = department3.getAverageBudgetOfAllProjects();
        
        // Verify individual department averages
        assertEquals("Average budget for department D001", 20000.0, avgD001, 0.001);
        assertEquals("Average budget for department D002", 40000.0, avgD002, 0.001);
        assertEquals("Average budget for department D003", 60000.0, avgD003, 0.001);
        
        // Calculate combined average across all three departments
        // Note: This is the average of department averages, not project averages
        double combinedAverage = (avgD001 + avgD002 + avgD003) / 3;
        double expectedCombinedAverage = 40000.0; // (20000 + 40000 + 60000) / 3 = 40000
        
        assertEquals("Combined average budget for departments D001, D002, and D003", 
                     expectedCombinedAverage, combinedAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Set up department D004 with one project having zero budget
        List<Project> projectsD004 = new ArrayList<>();
        Project project = new Project();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudgetAmount(0.0);
        project.setDeadline("2025-07-30");
        projectsD004.add(project);
        department4.setProjects(projectsD004);
        
        // Calculate average budget for department D004
        double actualAverage = department4.getAverageBudgetOfAllProjects();
        double expectedAverage = 0.0;
        
        assertEquals("Average budget calculation for department D004 with zero budget project", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Department D005 has no projects (empty list by default)
        // Calculate average budget for department D005
        double actualAverage = department5.getAverageBudgetOfAllProjects();
        double expectedAverage = 0.0;
        
        assertEquals("Average budget calculation for department D005 with no projects", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Set up department D006 with three projects having different budgets
        List<Project> projectsD006 = new ArrayList<>();
        
        Project project1 = new Project();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudgetAmount(150000.0);
        project1.setDeadline("2025-12-01");
        projectsD006.add(project1);
        
        Project project2 = new Project();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudgetAmount(75000.0);
        project2.setDeadline("2025-10-15");
        projectsD006.add(project2);
        
        Project project3 = new Project();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudgetAmount(50000.0);
        project3.setDeadline("2025-11-30");
        projectsD006.add(project3);
        
        department6.setProjects(projectsD006);
        
        // Calculate average budget for department D006
        double actualAverage = department6.getAverageBudgetOfAllProjects();
        double expectedAverage = 91666.67; // (150000 + 75000 + 50000) / 3 = 91666.666...
        
        assertEquals("Average budget calculation for department D006 with three projects", 
                     expectedAverage, actualAverage, 0.01);
    }
}