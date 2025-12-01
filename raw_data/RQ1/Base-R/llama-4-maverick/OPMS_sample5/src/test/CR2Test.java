import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Create department D001
        Department dept1 = new Department();
        dept1.setId("D001");
        dept1.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" for department D001
        Project project1 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudgetAmount(50000);
        project1.setDeadline("2025-12-31");
        dept1.addProject(project1);
        
        // Create project "Market Research" for department D001
        Project project2 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudgetAmount(30000);
        project2.setDeadline("2025-11-30");
        dept1.addProject(project2);
        
        // Create department D002
        Department dept2 = new Department();
        dept2.setId("D002");
        dept2.setEmail("development@company.com");
        
        // Create project "New Feature Development" for department D002
        Project project3 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudgetAmount(200000);
        project3.setDeadline("2026-01-15");
        dept2.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = dept1.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Create department D001
        Department dept1 = new Department();
        dept1.setId("D001");
        dept1.setEmail("hr@company.com");
        
        // Create project "Employee Training" for department D001
        Project project1 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudgetAmount(20000);
        project1.setDeadline("2025-08-15");
        dept1.addProject(project1);
        
        // Create department D002
        Department dept2 = new Department();
        dept2.setId("D002");
        dept2.setEmail("sales@company.com");
        
        // Create project "Sales Strategy" for department D002
        Project project2 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudgetAmount(40000);
        project2.setDeadline("2025-09-15");
        dept2.addProject(project2);
        
        // Create department D003
        Department dept3 = new Department();
        dept3.setId("D003");
        dept3.setEmail("it@company.com");
        
        // Create project "System Upgrade" for department D003
        Project project3 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudgetAmount(60000);
        project3.setDeadline("2025-10-01");
        dept3.addProject(project3);
        
        // Calculate average budget for each department
        double avgBudgetDept1 = dept1.calculateAverageBudget();
        double avgBudgetDept2 = dept2.calculateAverageBudget();
        double avgBudgetDept3 = dept3.calculateAverageBudget();
        
        // Expected outputs for individual departments
        assertEquals(20000, avgBudgetDept1, 0.001);
        assertEquals(40000, avgBudgetDept2, 0.001);
        assertEquals(60000, avgBudgetDept3, 0.001);
        
        // Calculate overall average across all three departments
        double overallAverage = (avgBudgetDept1 + avgBudgetDept2 + avgBudgetDept3) / 3;
        
        // Expected: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Create department D004
        Department dept4 = new Department();
        dept4.setId("D004");
        dept4.setEmail("finance@company.com");
        
        // Create project "Budget Review" with zero budget for department D004
        Project project = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudgetAmount(0);
        project.setDeadline("2025-07-30");
        dept4.addProject(project);
        
        // Calculate average budget for department D004
        double averageBudget = dept4.calculateAverageBudget();
        
        // Expected: 0 CNY
        assertEquals(0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department dept5 = new Department();
        dept5.setId("D005");
        dept5.setEmail("research@company.com");
        
        // Calculate average budget for department D005 (no projects)
        double averageBudget = dept5.calculateAverageBudget();
        
        // Expected: 0 CNY (or handled as "No projects available")
        assertEquals(0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Create department D006
        Department dept6 = new Department();
        dept6.setId("D006");
        dept6.setEmail("product@company.com");
        
        // Create project "Product Launch" for department D006
        Project project1 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudgetAmount(150000);
        project1.setDeadline("2025-12-01");
        dept6.addProject(project1);
        
        // Create project "Market Analysis" for department D006
        Project project2 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudgetAmount(75000);
        project2.setDeadline("2025-10-15");
        dept6.addProject(project2);
        
        // Create project "Client Engagement" for department D006
        Project project3 = new ResearchProject(); // Using ResearchProject as a concrete implementation
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudgetAmount(50000);
        project3.setDeadline("2025-11-30");
        dept6.addProject(project3);
        
        // Calculate average budget for department D006
        double averageBudget = dept6.calculateAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}