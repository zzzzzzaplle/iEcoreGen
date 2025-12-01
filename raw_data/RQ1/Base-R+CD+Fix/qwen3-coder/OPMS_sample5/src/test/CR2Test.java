import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
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
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("marketing@company.com");
        
        // Create project 1 for D001
        Project project1 = new ProductionProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000);
        project1.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        
        // Create project 2 for D001
        Project project2 = new ProductionProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000);
        project2.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        
        // Add projects to department D001
        dept1.addProject(project1);
        dept1.addProject(project2);
        
        // Create department D002
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("development@company.com");
        
        // Create project for D002
        Project project3 = new ProductionProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000);
        project3.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        
        // Add project to department D002
        dept2.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = dept1.calculateAverageBudget();
        
        // Verify expected output
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("hr@company.com");
        
        // Create project for D001
        Project project1 = new ProductionProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000);
        project1.setDeadline(dateFormat.parse("2025-08-15 23:59:59"));
        dept1.addProject(project1);
        
        // Create department D002
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("sales@company.com");
        
        // Create project for D002
        Project project2 = new ProductionProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000);
        project2.setDeadline(dateFormat.parse("2025-09-15 23:59:59"));
        dept2.addProject(project2);
        
        // Create department D003
        Department dept3 = new Department();
        dept3.setID("D003");
        dept3.setEmail("it@company.com");
        
        // Create project for D003
        Project project3 = new ProductionProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000);
        project3.setDeadline(dateFormat.parse("2025-10-01 23:59:59"));
        dept3.addProject(project3);
        
        // Calculate average budget for each department
        double avgBudgetD1 = dept1.calculateAverageBudget();
        double avgBudgetD2 = dept2.calculateAverageBudget();
        double avgBudgetD3 = dept3.calculateAverageBudget();
        
        // Calculate overall average across departments
        double overallAverage = (avgBudgetD1 + avgBudgetD2 + avgBudgetD3) / 3;
        
        // Verify expected outputs
        assertEquals(20000.0, avgBudgetD1, 0.001);
        assertEquals(40000.0, avgBudgetD2, 0.001);
        assertEquals(60000.0, avgBudgetD3, 0.001);
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004
        Department dept4 = new Department();
        dept4.setID("D004");
        dept4.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project project = new ProductionProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0);
        project.setDeadline(dateFormat.parse("2025-07-30 23:59:59"));
        dept4.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = dept4.calculateAverageBudget();
        
        // Verify expected output
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
        
        // Verify expected output
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        Department dept6 = new Department();
        dept6.setID("D006");
        dept6.setEmail("product@company.com");
        
        // Create project 1
        Project project1 = new ProductionProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000);
        project1.setDeadline(dateFormat.parse("2025-12-01 23:59:59"));
        
        // Create project 2
        Project project2 = new ProductionProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000);
        project2.setDeadline(dateFormat.parse("2025-10-15 23:59:59"));
        
        // Create project 3
        Project project3 = new ProductionProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000);
        project3.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        
        // Add all projects to department D006
        dept6.addProject(project1);
        dept6.addProject(project2);
        dept6.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = dept6.calculateAverageBudget();
        
        // Verify expected output
        assertEquals(91666.67, averageBudget, 0.01);
    }
}