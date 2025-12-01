import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department department;
    private Company company;
    
    @Before
    public void setUp() {
        department = new Department();
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // SetUp: Create department D001 with two projects
        Department marketingDept = new Department();
        marketingDept.setId("D001");
        marketingDept.setEmail("marketing@company.com");
        
        // Create first project: Ads Campaign
        Project project1 = new ResearchProject(); // Using ResearchProject as concrete implementation
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline("2025-12-31");
        marketingDept.addProject(project1);
        
        // Create second project: Market Research
        Project project2 = new ResearchProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudgetAmount(30000.0);
        project2.setDeadline("2025-11-30");
        marketingDept.addProject(project2);
        
        // Create another department D002 with one project
        Department developmentDept = new Department();
        developmentDept.setId("D002");
        developmentDept.setEmail("development@company.com");
        
        Project project3 = new ResearchProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudgetAmount(200000.0);
        project3.setDeadline("2026-01-15");
        developmentDept.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = marketingDept.getAverageBudgetOfAllProjects();
        
        // Expected Output: Average budget = (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // SetUp: Create three departments with one project each
        Department hrDept = new Department();
        hrDept.setId("D001");
        hrDept.setEmail("hr@company.com");
        
        Project project1 = new ResearchProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudgetAmount(20000.0);
        project1.setDeadline("2025-08-15");
        hrDept.addProject(project1);
        
        Department salesDept = new Department();
        salesDept.setId("D002");
        salesDept.setEmail("sales@company.com");
        
        Project project2 = new ResearchProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudgetAmount(40000.0);
        project2.setDeadline("2025-09-15");
        salesDept.addProject(project2);
        
        Department itDept = new Department();
        itDept.setId("D003");
        itDept.setEmail("it@company.com");
        
        Project project3 = new ResearchProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudgetAmount(60000.0);
        project3.setDeadline("2025-10-01");
        itDept.addProject(project3);
        
        // Calculate average budget for each department
        double avgBudgetD001 = hrDept.getAverageBudgetOfAllProjects();
        double avgBudgetD002 = salesDept.getAverageBudgetOfAllProjects();
        double avgBudgetD003 = itDept.getAverageBudgetOfAllProjects();
        
        // Calculate overall average across departments
        double overallAverage = (avgBudgetD001 + avgBudgetD002 + avgBudgetD003) / 3;
        
        // Expected Output verification
        assertEquals(20000.0, avgBudgetD001, 0.001);
        assertEquals(40000.0, avgBudgetD002, 0.001);
        assertEquals(60000.0, avgBudgetD003, 0.001);
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // SetUp: Create department D004 with one project having zero budget
        Department financeDept = new Department();
        financeDept.setId("D004");
        financeDept.setEmail("finance@company.com");
        
        Project project = new ResearchProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudgetAmount(0.0);
        project.setDeadline("2025-07-30");
        financeDept.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = financeDept.getAverageBudgetOfAllProjects();
        
        // Expected Output: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // SetUp: Create department D005 with no projects
        Department researchDept = new Department();
        researchDept.setId("D005");
        researchDept.setEmail("research@company.com");
        
        // Calculate average budget for department D005 (which has no projects)
        double averageBudget = researchDept.getAverageBudgetOfAllProjects();
        
        // Expected Output: Average budget = 0 CNY (or handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // SetUp: Create department D006 with three projects having different budgets
        Department productDept = new Department();
        productDept.setId("D006");
        productDept.setEmail("product@company.com");
        
        // Create first project: Product Launch
        Project project1 = new ResearchProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudgetAmount(150000.0);
        project1.setDeadline("2025-12-01");
        productDept.addProject(project1);
        
        // Create second project: Market Analysis
        Project project2 = new ResearchProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudgetAmount(75000.0);
        project2.setDeadline("2025-10-15");
        productDept.addProject(project2);
        
        // Create third project: Client Engagement
        Project project3 = new ResearchProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudgetAmount(50000.0);
        project3.setDeadline("2025-11-30");
        productDept.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = productDept.getAverageBudgetOfAllProjects();
        
        // Expected Output: Average budget = (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}