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
        
        Department departmentHR = new Department();
        departmentHR.setId("D001");
        departmentHR.setEmail("hr@company.com");
        
        Department departmentSales = new Department();
        departmentSales.setId("D002");
        departmentSales.setEmail("sales@company.com");
        
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
        // Test Case 1: "Single Project Average Budget Calculation"
        // Set up department D001 with two projects
        Project project1 = new Project() {};
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline("2025-12-31");
        
        Project project2 = new Project() {};
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudgetAmount(30000.0);
        project2.setDeadline("2025-11-30");
        
        List<Project> projectsD001 = new ArrayList<>();
        projectsD001.add(project1);
        projectsD001.add(project2);
        department1.setProjects(projectsD001);
        
        // Set up department D002 with one project (should not affect D001 calculation)
        Project project3 = new Project() {};
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudgetAmount(200000.0);
        project3.setDeadline("2026-01-15");
        
        List<Project> projectsD002 = new ArrayList<>();
        projectsD002.add(project3);
        department2.setProjects(projectsD002);
        
        // Calculate average budget for department D001
        double actualAverage = department1.getAverageBudgetOfAllProjects();
        double expectedAverage = 40000.0; // (50000 + 30000) / 2 = 40000
        
        assertEquals("Average budget for department D001 should be 40000 CNY", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Test Case 2: "Multiple Departments Average Budget Calculation"
        // Set up department D001 (HR) with one project
        Department departmentHR = new Department();
        departmentHR.setId("D001");
        departmentHR.setEmail("hr@company.com");
        
        Project project1 = new Project() {};
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudgetAmount(20000.0);
        project1.setDeadline("2025-08-15");
        
        List<Project> projectsD001 = new ArrayList<>();
        projectsD001.add(project1);
        departmentHR.setProjects(projectsD001);
        
        // Set up department D002 (Sales) with one project
        Department departmentSales = new Department();
        departmentSales.setId("D002");
        departmentSales.setEmail("sales@company.com");
        
        Project project2 = new Project() {};
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudgetAmount(40000.0);
        project2.setDeadline("2025-09-15");
        
        List<Project> projectsD002 = new ArrayList<>();
        projectsD002.add(project2);
        departmentSales.setProjects(projectsD002);
        
        // Set up department D003 (IT) with one project
        Project project3 = new Project() {};
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudgetAmount(60000.0);
        project3.setDeadline("2025-10-01");
        
        List<Project> projectsD003 = new ArrayList<>();
        projectsD003.add(project3);
        department3.setProjects(projectsD003);
        
        // Calculate average budget for each department
        double avgD001 = departmentHR.getAverageBudgetOfAllProjects();
        double avgD002 = departmentSales.getAverageBudgetOfAllProjects();
        double avgD003 = department3.getAverageBudgetOfAllProjects();
        
        // Verify individual department averages
        assertEquals("Average budget for department D001 should be 20000 CNY", 
                     20000.0, avgD001, 0.001);
        assertEquals("Average budget for department D002 should be 40000 CNY", 
                     40000.0, avgD002, 0.001);
        assertEquals("Average budget for department D003 should be 60000 CNY", 
                     60000.0, avgD003, 0.001);
        
        // Calculate overall average across all three departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        double expectedOverallAverage = 40000.0; // (20000 + 40000 + 60000) / 3 = 40000
        
        assertEquals("Overall average budget for departments D001, D002, D003 should be 40000 CNY", 
                     expectedOverallAverage, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Test Case 3: "Single Project Calculation for Zero Budget"
        // Set up department D004 with one project having zero budget
        Project project = new Project() {};
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudgetAmount(0.0);
        project.setDeadline("2025-07-30");
        
        List<Project> projectsD004 = new ArrayList<>();
        projectsD004.add(project);
        department4.setProjects(projectsD004);
        
        // Calculate average budget for department D004
        double actualAverage = department4.getAverageBudgetOfAllProjects();
        double expectedAverage = 0.0;
        
        assertEquals("Average budget for department D004 with zero budget project should be 0 CNY", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Test Case 4: "No Projects in Department"
        // Department D005 has no projects (empty list by default)
        double actualAverage = department5.getAverageBudgetOfAllProjects();
        double expectedAverage = 0.0;
        
        assertEquals("Average budget for department with no projects should be 0 CNY", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Test Case 5: "Projects with Different Budgets Calculation"
        // Set up department D006 with three projects having different budgets
        Project project1 = new Project() {};
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudgetAmount(150000.0);
        project1.setDeadline("2025-12-01");
        
        Project project2 = new Project() {};
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudgetAmount(75000.0);
        project2.setDeadline("2025-10-15");
        
        Project project3 = new Project() {};
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudgetAmount(50000.0);
        project3.setDeadline("2025-11-30");
        
        List<Project> projectsD006 = new ArrayList<>();
        projectsD006.add(project1);
        projectsD006.add(project2);
        projectsD006.add(project3);
        department6.setProjects(projectsD006);
        
        // Calculate average budget for department D006
        double actualAverage = department6.getAverageBudgetOfAllProjects();
        double expectedAverage = 91666.67; // (150000 + 75000 + 50000) / 3 = 91666.666...
        
        assertEquals("Average budget for department D006 should be approximately 91666.67 CNY", 
                     expectedAverage, actualAverage, 0.01);
    }
}