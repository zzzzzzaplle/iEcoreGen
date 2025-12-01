import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Create department D001 with email marketing@company.com
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" with budget 50000 CNY
        Project project1 = new ProductionProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        try {
            project1.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-31"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Create project "Market Research" with budget 30000 CNY
        Project project2 = new ProductionProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        try {
            project2.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2025-11-30"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add projects to department D001
        dept1.addProject(project1);
        dept1.addProject(project2);
        
        // Create department D002 with email development@company.com
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("development@company.com");
        
        // Create project "New Feature Development" with budget 200000 CNY
        Project project3 = new ProductionProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        try {
            project3.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2026-01-15"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add project to department D002
        dept2.addProject(project3);
        
        // Calculate average budget for department D001
        double averageBudget = dept1.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Create department D001 with email hr@company.com
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("hr@company.com");
        
        // Create project "Employee Training" with budget 20000 CNY
        Project project1 = new ProductionProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        try {
            project1.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2025-08-15"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        dept1.addProject(project1);
        
        // Create department D002 with email sales@company.com
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("sales@company.com");
        
        // Create project "Sales Strategy" with budget 40000 CNY
        Project project2 = new ProductionProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        try {
            project2.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2025-09-15"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        dept2.addProject(project2);
        
        // Create department D003 with email it@company.com
        Department dept3 = new Department();
        dept3.setID("D003");
        dept3.setEmail("it@company.com");
        
        // Create project "System Upgrade" with budget 60000 CNY
        Project project3 = new ProductionProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        try {
            project3.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-01"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        dept3.addProject(project3);
        
        // Calculate average budget for each department
        double avgDept1 = dept1.calculateAverageBudget();
        double avgDept2 = dept2.calculateAverageBudget();
        double avgDept3 = dept3.calculateAverageBudget();
        
        // Calculate overall average for all three departments
        double overallAverage = (avgDept1 + avgDept2 + avgDept3) / 3;
        
        // Verify individual department averages
        assertEquals(20000.0, avgDept1, 0.001);
        assertEquals(40000.0, avgDept2, 0.001);
        assertEquals(60000.0, avgDept3, 0.001);
        
        // Verify overall average: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Create department D004 with email finance@company.com
        Department dept = new Department();
        dept.setID("D004");
        dept.setEmail("finance@company.com");
        
        // Create project "Budget Review" with zero budget
        Project project = new ProductionProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        try {
            project.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2025-07-30"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add project to department
        dept.addProject(project);
        
        // Calculate average budget
        double averageBudget = dept.calculateAverageBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with email research@company.com (no projects)
        Department dept = new Department();
        dept.setID("D005");
        dept.setEmail("research@company.com");
        
        // Calculate average budget for department with no projects
        double averageBudget = dept.calculateAverageBudget();
        
        // Expected: 0 CNY (handled as "No projects available" in the method)
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Create department D006 with email product@company.com
        Department dept = new Department();
        dept.setID("D006");
        dept.setEmail("product@company.com");
        
        // Create project "Product Launch" with budget 150000 CNY
        Project project1 = new ProductionProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        try {
            project1.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-01"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Create project "Market Analysis" with budget 75000 CNY
        Project project2 = new ProductionProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        try {
            project2.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-15"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Create project "Client Engagement" with budget 50000 CNY
        Project project3 = new ProductionProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        try {
            project3.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2025-11-30"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add all projects to department
        dept.addProject(project1);
        dept.addProject(project2);
        dept.addProject(project3);
        
        // Calculate average budget
        double averageBudget = dept.calculateAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}