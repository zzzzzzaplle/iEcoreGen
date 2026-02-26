import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
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
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Set up department D001 for Test Case 1
        department1 = new Department();
        department1.setID("D001");
        department1.setEmail("marketing@company.com");
        
        Project project1 = new Project();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        department1.addProject(project1);
        
        Project project2 = new Project();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        department1.addProject(project2);
        
        // Set up department D002 for Test Case 1
        department2 = new Department();
        department2.setID("D002");
        department2.setEmail("development@company.com");
        
        Project project3 = new Project();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        department2.addProject(project3);
        
        // Set up department D001 for Test Case 2
        Department hrDepartment = new Department();
        hrDepartment.setID("D001");
        hrDepartment.setEmail("hr@company.com");
        
        Project project4 = new Project();
        project4.setTitle("Employee Training");
        project4.setDescription("Training for employees");
        project4.setBudget(20000.0);
        project4.setDeadline(dateFormat.parse("2025-08-15 23:59:59"));
        hrDepartment.addProject(project4);
        
        // Set up department D002 for Test Case 2
        Department salesDepartment = new Department();
        salesDepartment.setID("D002");
        salesDepartment.setEmail("sales@company.com");
        
        Project project5 = new Project();
        project5.setTitle("Sales Strategy");
        project5.setDescription("New sales strategy implementation");
        project5.setBudget(40000.0);
        project5.setDeadline(dateFormat.parse("2025-09-15 23:59:59"));
        salesDepartment.addProject(project5);
        
        // Set up department D003 for Test Case 2
        Department itDepartment = new Department();
        itDepartment.setID("D003");
        itDepartment.setEmail("it@company.com");
        
        Project project6 = new Project();
        project6.setTitle("System Upgrade");
        project6.setDescription("Upgrade company systems");
        project6.setBudget(60000.0);
        project6.setDeadline(dateFormat.parse("2025-10-01 23:59:59"));
        itDepartment.addProject(project6);
        
        // Set up department D004 for Test Case 3
        department4 = new Department();
        department4.setID("D004");
        department4.setEmail("finance@company.com");
        
        Project project7 = new Project();
        project7.setTitle("Budget Review");
        project7.setDescription("Review of the annual budget");
        project7.setBudget(0.0);
        project7.setDeadline(dateFormat.parse("2025-07-30 23:59:59"));
        department4.addProject(project7);
        
        // Set up department D005 for Test Case 4
        department5 = new Department();
        department5.setID("D005");
        department5.setEmail("research@company.com");
        // No projects added
        
        // Set up department D006 for Test Case 5
        department6 = new Department();
        department6.setID("D006");
        department6.setEmail("product@company.com");
        
        Project project8 = new Project();
        project8.setTitle("Product Launch");
        project8.setDescription("Launching new product");
        project8.setBudget(150000.0);
        project8.setDeadline(dateFormat.parse("2025-12-01 23:59:59"));
        department6.addProject(project8);
        
        Project project9 = new Project();
        project9.setTitle("Market Analysis");
        project9.setDescription("Analysis of market trends");
        project9.setBudget(75000.0);
        project9.setDeadline(dateFormat.parse("2025-10-15 23:59:59"));
        department6.addProject(project9);
        
        Project project10 = new Project();
        project10.setTitle("Client Engagement");
        project10.setDescription("Engaging with key clients");
        project10.setBudget(50000.0);
        project10.setDeadline(dateFormat.parse("2025-11-30 23:59:59"));
        department6.addProject(project10);
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Test: Calculate average budget for department D001 with 2 projects
        double result = department1.calculateAverageBudget();
        assertEquals(40000.0, result, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Set up departments for this test
        Department hrDepartment = new Department();
        hrDepartment.setID("D001");
        hrDepartment.setEmail("hr@company.com");
        
        Project project1 = new Project();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        hrDepartment.addProject(project1);
        
        Department salesDepartment = new Department();
        salesDepartment.setID("D002");
        salesDepartment.setEmail("sales@company.com");
        
        Project project2 = new Project();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        salesDepartment.addProject(project2);
        
        Department itDepartment = new Department();
        itDepartment.setID("D003");
        itDepartment.setEmail("it@company.com");
        
        Project project3 = new Project();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        itDepartment.addProject(project3);
        
        // Test individual department averages
        double hrAvg = hrDepartment.calculateAverageBudget();
        double salesAvg = salesDepartment.calculateAverageBudget();
        double itAvg = itDepartment.calculateAverageBudget();
        
        assertEquals(20000.0, hrAvg, 0.001);
        assertEquals(40000.0, salesAvg, 0.001);
        assertEquals(60000.0, itAvg, 0.001);
        
        // Test overall average across departments
        double overallAvg = (hrAvg + salesAvg + itAvg) / 3;
        assertEquals(40000.0, overallAvg, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Test: Calculate average budget for department D004 with zero budget project
        double result = department4.calculateAverageBudget();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testCase4_NoProjectsInDepartment() {
        // Test: Calculate average budget for department D005 with no projects
        // Should throw IllegalStateException
        department5.calculateAverageBudget();
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Test: Calculate average budget for department D006 with 3 projects of varying budgets
        double result = department6.calculateAverageBudget();
        assertEquals(91666.666, result, 0.001);
    }
}