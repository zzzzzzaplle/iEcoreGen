import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        // Initialize departments that will be reused across tests
        department1 = new Department();
        department2 = new Department();
        department3 = new Department();
        department4 = new Department();
        department5 = new Department();
        department6 = new Department();
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Set up department D001 with two projects
        department1.setDepartmentId("D001");
        department1.setEmail("marketing@company.com");
        
        List<Project> projects1 = new ArrayList<>();
        Project project1 = new Project() {};
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 31));
        projects1.add(project1);
        
        Project project2 = new Project() {};
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudgetAmount(30000.0);
        project2.setDeadline(LocalDate.of(2025, 11, 30));
        projects1.add(project2);
        
        department1.setProjects(projects1);
        
        // Set up department D002 with one project (not needed for this calculation but included per spec)
        department2.setDepartmentId("D002");
        department2.setEmail("development@company.com");
        
        List<Project> projects2 = new ArrayList<>();
        Project project3 = new Project() {};
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudgetAmount(200000.0);
        project3.setDeadline(LocalDate.of(2026, 1, 15));
        projects2.add(project3);
        
        department2.setProjects(projects2);
        
        // Calculate average budget for department D001
        double averageBudget = department1.getAverageBudgetOfProjects();
        
        // Expected: (50000 + 30000) / 2 = 40000
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Set up department D001 with one project
        department1.setDepartmentId("D001");
        department1.setEmail("hr@company.com");
        
        List<Project> projects1 = new ArrayList<>();
        Project project1 = new Project() {};
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudgetAmount(20000.0);
        project1.setDeadline(LocalDate.of(2025, 8, 15));
        projects1.add(project1);
        
        department1.setProjects(projects1);
        
        // Set up department D002 with one project
        department2.setDepartmentId("D002");
        department2.setEmail("sales@company.com");
        
        List<Project> projects2 = new ArrayList<>();
        Project project2 = new Project() {};
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudgetAmount(40000.0);
        project2.setDeadline(LocalDate.of(2025, 9, 15));
        projects2.add(project2);
        
        department2.setProjects(projects2);
        
        // Set up department D003 with one project
        department3.setDepartmentId("D003");
        department3.setEmail("it@company.com");
        
        List<Project> projects3 = new ArrayList<>();
        Project project3 = new Project() {};
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudgetAmount(60000.0);
        project3.setDeadline(LocalDate.of(2025, 10, 1));
        projects3.add(project3);
        
        department3.setProjects(projects3);
        
        // Calculate average budgets for each department
        double avgD1 = department1.getAverageBudgetOfProjects();
        double avgD2 = department2.getAverageBudgetOfProjects();
        double avgD3 = department3.getAverageBudgetOfProjects();
        
        // Calculate overall average across departments
        double overallAverage = (avgD1 + avgD2 + avgD3) / 3;
        
        // Verify individual department averages
        assertEquals(20000.0, avgD1, 0.001);
        assertEquals(40000.0, avgD2, 0.001);
        assertEquals(60000.0, avgD3, 0.001);
        
        // Verify overall average: (20000 + 40000 + 60000) / 3 = 40000
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Set up department D004 with one project having zero budget
        department4.setDepartmentId("D004");
        department4.setEmail("finance@company.com");
        
        List<Project> projects = new ArrayList<>();
        Project project = new Project() {};
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudgetAmount(0.0);
        project.setDeadline(LocalDate.of(2025, 7, 30));
        projects.add(project);
        
        department4.setProjects(projects);
        
        // Calculate average budget for department D004
        double averageBudget = department4.getAverageBudgetOfProjects();
        
        // Expected: 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Set up department D005 with no projects
        department5.setDepartmentId("D005");
        department5.setEmail("research@company.com");
        department5.setProjects(new ArrayList<>()); // Explicitly set empty list
        
        // Calculate average budget for department D005
        double averageBudget = department5.getAverageBudgetOfProjects();
        
        // Expected: 0 CNY (handled as no projects available)
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Set up department D006 with three projects having different budgets
        department6.setDepartmentId("D006");
        department6.setEmail("product@company.com");
        
        List<Project> projects = new ArrayList<>();
        
        Project project1 = new Project() {};
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudgetAmount(150000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 1));
        projects.add(project1);
        
        Project project2 = new Project() {};
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudgetAmount(75000.0);
        project2.setDeadline(LocalDate.of(2025, 10, 15));
        projects.add(project2);
        
        Project project3 = new Project() {};
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudgetAmount(50000.0);
        project3.setDeadline(LocalDate.of(2025, 11, 30));
        projects.add(project3);
        
        department6.setProjects(projects);
        
        // Calculate average budget for department D006
        double averageBudget = department6.getAverageBudgetOfProjects();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}