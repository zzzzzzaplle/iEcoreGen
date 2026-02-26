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
        // Create department D001
        Department dept1 = new Department();
        dept1.setDepartmentId("D001");
        dept1.setEmail("marketing@company.com");
        
        // Create project 1 for D001
        Project project1 = new Project();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 31));
        
        // Create project 2 for D001
        Project project2 = new Project();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudgetAmount(30000.0);
        project2.setDeadline(LocalDate.of(2025, 11, 30));
        
        // Add projects to D001
        List<Project> projectsDept1 = new ArrayList<>();
        projectsDept1.add(project1);
        projectsDept1.add(project2);
        dept1.setProjects(projectsDept1);
        
        // Create department D002 (should not affect D001 calculation)
        Department dept2 = new Department();
        dept2.setDepartmentId("D002");
        dept2.setEmail("development@company.com");
        
        Project project3 = new Project();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudgetAmount(200000.0);
        project3.setDeadline(LocalDate.of(2026, 1, 15));
        
        List<Project> projectsDept2 = new ArrayList<>();
        projectsDept2.add(project3);
        dept2.setProjects(projectsDept2);
        
        // Calculate average budget for department D001
        double averageBudget = dept1.getAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Create department D001
        Department dept1 = new Department();
        dept1.setDepartmentId("D001");
        dept1.setEmail("hr@company.com");
        
        Project project1 = new Project();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudgetAmount(20000.0);
        project1.setDeadline(LocalDate.of(2025, 8, 15));
        
        List<Project> projectsDept1 = new ArrayList<>();
        projectsDept1.add(project1);
        dept1.setProjects(projectsDept1);
        
        // Create department D002
        Department dept2 = new Department();
        dept2.setDepartmentId("D002");
        dept2.setEmail("sales@company.com");
        
        Project project2 = new Project();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudgetAmount(40000.0);
        project2.setDeadline(LocalDate.of(2025, 9, 15));
        
        List<Project> projectsDept2 = new ArrayList<>();
        projectsDept2.add(project2);
        dept2.setProjects(projectsDept2);
        
        // Create department D003
        Department dept3 = new Department();
        dept3.setDepartmentId("D003");
        dept3.setEmail("it@company.com");
        
        Project project3 = new Project();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudgetAmount(60000.0);
        project3.setDeadline(LocalDate.of(2025, 10, 1));
        
        List<Project> projectsDept3 = new ArrayList<>();
        projectsDept3.add(project3);
        dept3.setProjects(projectsDept3);
        
        // Calculate average budget for each department
        double avgDept1 = dept1.getAverageBudget();
        double avgDept2 = dept2.getAverageBudget();
        double avgDept3 = dept3.getAverageBudget();
        
        // Verify individual department averages
        assertEquals(20000.0, avgDept1, 0.001);
        assertEquals(40000.0, avgDept2, 0.001);
        assertEquals(60000.0, avgDept3, 0.001);
        
        // Calculate overall average across all departments
        double overallAverage = (avgDept1 + avgDept2 + avgDept3) / 3;
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Create department D004
        Department dept4 = new Department();
        dept4.setDepartmentId("D004");
        dept4.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project project = new Project();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudgetAmount(0.0);
        project.setDeadline(LocalDate.of(2025, 7, 30));
        
        List<Project> projects = new ArrayList<>();
        projects.add(project);
        dept4.setProjects(projects);
        
        // Calculate average budget
        double averageBudget = dept4.getAverageBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department dept5 = new Department();
        dept5.setDepartmentId("D005");
        dept5.setEmail("research@company.com");
        
        // Department has empty project list by default
        double averageBudget = dept5.getAverageBudget();
        
        // Expected: 0 CNY (handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Create department D006
        Department dept6 = new Department();
        dept6.setDepartmentId("D006");
        dept6.setEmail("product@company.com");
        
        // Create project 1
        Project project1 = new Project();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudgetAmount(150000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 1));
        
        // Create project 2
        Project project2 = new Project();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudgetAmount(75000.0);
        project2.setDeadline(LocalDate.of(2025, 10, 15));
        
        // Create project 3
        Project project3 = new Project();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudgetAmount(50000.0);
        project3.setDeadline(LocalDate.of(2025, 11, 30));
        
        // Add all projects to department
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        dept6.setProjects(projects);
        
        // Calculate average budget
        double averageBudget = dept6.getAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}