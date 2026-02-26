import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Department department1;
    private Department department2;
    private Department department3;
    private Department department4;
    private Department department5;
    private Department department6;
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
        department1 = new Department();
        department1.setID("D001");
        department1.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" for department D001
        Project project1 = new ProductionProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000);
        project1.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        department1.addProject(project1);
        
        // Create project "Market Research" for department D001
        Project project2 = new ProductionProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000);
        project2.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        department1.addProject(project2);
        
        // Create department D002
        department2 = new Department();
        department2.setID("D002");
        department2.setEmail("development@company.com");
        
        // Create project "New Feature Development" for department D002
        Project project3 = new ProductionProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000);
        project3.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        department2.addProject(project3);
        
        // Calculate average budget for department D001
        double actualAverageBudget = department1.calculateAverageBudget();
        double expectedAverageBudget = 40000.0;
        
        // Verify the average budget calculation
        assertEquals("Average budget should be 40000 CNY", expectedAverageBudget, actualAverageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001
        department1 = new Department();
        department1.setID("D001");
        department1.setEmail("hr@company.com");
        
        // Create project "Employee Training" for department D001
        Project project1 = new ProductionProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000);
        project1.setDeadline(dateFormat.parse("2025-08-15 23:59:59"));
        department1.addProject(project1);
        
        // Create department D002
        department2 = new Department();
        department2.setID("D002");
        department2.setEmail("sales@company.com");
        
        // Create project "Sales Strategy" for department D002
        Project project2 = new ProductionProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000);
        project2.setDeadline(dateFormat.parse("2025-09-15 23:59:59"));
        department2.addProject(project2);
        
        // Create department D003
        department3 = new Department();
        department3.setID("D003");
        department3.setEmail("it@company.com");
        
        // Create project "System Upgrade" for department D003
        Project project3 = new ProductionProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000);
        project3.setDeadline(dateFormat.parse("2025-10-01 23:59:59"));
        department3.addProject(project3);
        
        // Calculate average budget for each department
        double averageBudgetD001 = department1.calculateAverageBudget();
        double averageBudgetD002 = department2.calculateAverageBudget();
        double averageBudgetD003 = department3.calculateAverageBudget();
        
        // Calculate overall average budget across all three departments
        double overallAverageBudget = (averageBudgetD001 + averageBudgetD002 + averageBudgetD003) / 3;
        
        // Verify individual department average budgets
        assertEquals("Average budget for D001 should be 20000 CNY", 20000.0, averageBudgetD001, 0.001);
        assertEquals("Average budget for D002 should be 40000 CNY", 40000.0, averageBudgetD002, 0.001);
        assertEquals("Average budget for D003 should be 60000 CNY", 60000.0, averageBudgetD003, 0.001);
        
        // Verify overall average budget
        assertEquals("Overall average budget should be 40000 CNY", 40000.0, overallAverageBudget, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004
        department4 = new Department();
        department4.setID("D004");
        department4.setEmail("finance@company.com");
        
        // Create project "Budget Review" with zero budget for department D004
        Project project = new ProductionProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0);
        project.setDeadline(dateFormat.parse("2025-07-30 23:59:59"));
        department4.addProject(project);
        
        // Calculate average budget for department D004
        double actualAverageBudget = department4.calculateAverageBudget();
        double expectedAverageBudget = 0.0;
        
        // Verify the average budget calculation for zero budget project
        assertEquals("Average budget should be 0 CNY", expectedAverageBudget, actualAverageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        department5 = new Department();
        department5.setID("D005");
        department5.setEmail("research@company.com");
        
        // Calculate average budget for department with no projects
        double actualAverageBudget = department5.calculateAverageBudget();
        double expectedAverageBudget = 0.0;
        
        // Verify the average budget calculation for department with no projects
        assertEquals("Average budget should be 0 CNY when no projects exist", expectedAverageBudget, actualAverageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        department6 = new Department();
        department6.setID("D006");
        department6.setEmail("product@company.com");
        
        // Create project "Product Launch" for department D006
        Project project1 = new ProductionProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000);
        project1.setDeadline(dateFormat.parse("2025-12-01 23:59:59"));
        department6.addProject(project1);
        
        // Create project "Market Analysis" for department D006
        Project project2 = new ProductionProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000);
        project2.setDeadline(dateFormat.parse("2025-10-15 23:59:59"));
        department6.addProject(project2);
        
        // Create project "Client Engagement" for department D006
        Project project3 = new ProductionProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000);
        project3.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        department6.addProject(project3);
        
        // Calculate average budget for department D006
        double actualAverageBudget = department6.calculateAverageBudget();
        double expectedAverageBudget = 91666.67;
        
        // Verify the average budget calculation with different budget amounts
        assertEquals("Average budget should be 91666.67 CNY", expectedAverageBudget, actualAverageBudget, 0.01);
    }
}