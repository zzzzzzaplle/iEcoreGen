import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department department;
    
    @Before
    public void setUp() {
        department = new Department();
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Set up department D001 with two projects
        Department d001 = new Department();
        d001.setId("D001");
        d001.setEmail("marketing@company.com");
        
        Project project1 = new ProductionProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 31));
        
        Project project2 = new ProductionProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudgetAmount(30000.0);
        project2.setDeadline(LocalDate.of(2025, 11, 30));
        
        List<Project> projectsD001 = new ArrayList<>();
        projectsD001.add(project1);
        projectsD001.add(project2);
        d001.setProjects(projectsD001);
        
        // Set up department D002 with one project (not used in calculation)
        Department d002 = new Department();
        d002.setId("D002");
        d002.setEmail("development@company.com");
        
        Project project3 = new ProductionProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudgetAmount(200000.0);
        project3.setDeadline(LocalDate.of(2026, 1, 15));
        
        List<Project> projectsD002 = new ArrayList<>();
        projectsD002.add(project3);
        d002.setProjects(projectsD002);
        
        // Calculate average budget for department D001
        double averageBudget = d001.getAverageBudgetOfProjects();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Set up department D001 with one project
        Department d001 = new Department();
        d001.setId("D001");
        d001.setEmail("hr@company.com");
        
        Project project1 = new ProductionProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudgetAmount(20000.0);
        project1.setDeadline(LocalDate.of(2025, 8, 15));
        
        List<Project> projectsD001 = new ArrayList<>();
        projectsD001.add(project1);
        d001.setProjects(projectsD001);
        
        // Set up department D002 with one project
        Department d002 = new Department();
        d002.setId("D002");
        d002.setEmail("sales@company.com");
        
        Project project2 = new ProductionProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudgetAmount(40000.0);
        project2.setDeadline(LocalDate.of(2025, 9, 15));
        
        List<Project> projectsD002 = new ArrayList<>();
        projectsD002.add(project2);
        d002.setProjects(projectsD002);
        
        // Set up department D003 with one project
        Department d003 = new Department();
        d003.setId("D003");
        d003.setEmail("it@company.com");
        
        Project project3 = new ProductionProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudgetAmount(60000.0);
        project3.setDeadline(LocalDate.of(2025, 10, 1));
        
        List<Project> projectsD003 = new ArrayList<>();
        projectsD003.add(project3);
        d003.setProjects(projectsD003);
        
        // Calculate average budget for each department
        double averageD001 = d001.getAverageBudgetOfProjects();
        double averageD002 = d002.getAverageBudgetOfProjects();
        double averageD003 = d003.getAverageBudgetOfProjects();
        
        // Calculate overall average across departments
        double overallAverage = (averageD001 + averageD002 + averageD003) / 3;
        
        // Verify individual department averages
        assertEquals(20000.0, averageD001, 0.001);
        assertEquals(40000.0, averageD002, 0.001);
        assertEquals(60000.0, averageD003, 0.001);
        
        // Verify overall average: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Set up department D004 with one project having zero budget
        Department d004 = new Department();
        d004.setId("D004");
        d004.setEmail("finance@company.com");
        
        Project project = new ProductionProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudgetAmount(0.0);
        project.setDeadline(LocalDate.of(2025, 7, 30));
        
        List<Project> projects = new ArrayList<>();
        projects.add(project);
        d004.setProjects(projects);
        
        // Calculate average budget for department D004
        double averageBudget = d004.getAverageBudgetOfProjects();
        
        // Expected: 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Set up department D005 with no projects
        Department d005 = new Department();
        d005.setId("D005");
        d005.setEmail("research@company.com");
        
        // Department starts with empty project list, no need to set projects
        
        // Calculate average budget for department D005
        double averageBudget = d005.getAverageBudgetOfProjects();
        
        // Expected: 0 CNY (handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Set up department D006 with three projects having different budgets
        Department d006 = new Department();
        d006.setId("D006");
        d006.setEmail("product@company.com");
        
        Project project1 = new ProductionProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudgetAmount(150000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 1));
        
        Project project2 = new ProductionProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudgetAmount(75000.0);
        project2.setDeadline(LocalDate.of(2025, 10, 15));
        
        Project project3 = new ProductionProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudgetAmount(50000.0);
        project3.setDeadline(LocalDate.of(2025, 11, 30));
        
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        d006.setProjects(projects);
        
        // Calculate average budget for department D006
        double averageBudget = d006.getAverageBudgetOfProjects();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}