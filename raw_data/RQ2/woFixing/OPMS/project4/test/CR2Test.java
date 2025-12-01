package edu.project.project4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.project.ProjectFactory;
import edu.project.ProjectPackage;
import edu.project.Company;
import edu.project.Department;
import edu.project.Project;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private ProjectFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = ProjectFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department deptD001 = factory.createDepartment();
        deptD001.setID("D001");
        deptD001.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" in department D001
        Project project1 = factory.createProductionProject();
        project1.setTitle("Ads Campaign");
        project1.setDescription("Q1 advertising budget");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        deptD001.getProjects().add(project1);
        
        // Create project "Market Research" in department D001
        Project project2 = factory.createProductionProject();
        project2.setTitle("Market Research");
        project2.setDescription("Research on consumer behavior");
        project2.setBudget(30000.0);
        project2.setDeadline(dateFormat.parse("2025-11-30 00:00:00"));
        deptD001.getProjects().add(project2);
        
        // Create department D002
        Department deptD002 = factory.createDepartment();
        deptD002.setID("D002");
        deptD002.setEmail("development@company.com");
        
        // Create project in department D002 (should not affect D001 calculation)
        Project project3 = factory.createProductionProject();
        project3.setTitle("New Feature Development");
        project3.setDescription("Developing a new feature");
        project3.setBudget(200000.0);
        project3.setDeadline(dateFormat.parse("2026-01-15 00:00:00"));
        deptD002.getProjects().add(project3);
        
        // Calculate average budget for department D001
        double averageBudget = deptD001.calculateAverageBudget();
        
        // Expected: (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() throws Exception {
        // Create department D001
        Department deptD001 = factory.createDepartment();
        deptD001.setID("D001");
        deptD001.setEmail("hr@company.com");
        
        // Create project in department D001
        Project project1 = factory.createProductionProject();
        project1.setTitle("Employee Training");
        project1.setDescription("Training for employees");
        project1.setBudget(20000.0);
        project1.setDeadline(dateFormat.parse("2025-08-15 00:00:00"));
        deptD001.getProjects().add(project1);
        
        // Create department D002
        Department deptD002 = factory.createDepartment();
        deptD002.setID("D002");
        deptD002.setEmail("sales@company.com");
        
        // Create project in department D002
        Project project2 = factory.createProductionProject();
        project2.setTitle("Sales Strategy");
        project2.setDescription("New sales strategy implementation");
        project2.setBudget(40000.0);
        project2.setDeadline(dateFormat.parse("2025-09-15 00:00:00"));
        deptD002.getProjects().add(project2);
        
        // Create department D003
        Department deptD003 = factory.createDepartment();
        deptD003.setID("D003");
        deptD003.setEmail("it@company.com");
        
        // Create project in department D003
        Project project3 = factory.createProductionProject();
        project3.setTitle("System Upgrade");
        project3.setDescription("Upgrade company systems");
        project3.setBudget(60000.0);
        project3.setDeadline(dateFormat.parse("2025-10-01 00:00:00"));
        deptD003.getProjects().add(project3);
        
        // Calculate average budget for each department
        double avgD001 = deptD001.calculateAverageBudget();
        double avgD002 = deptD002.calculateAverageBudget();
        double avgD003 = deptD003.calculateAverageBudget();
        
        // Verify individual department averages
        assertEquals(20000.0, avgD001, 0.001);
        assertEquals(40000.0, avgD002, 0.001);
        assertEquals(60000.0, avgD003, 0.001);
        
        // Calculate overall average across all departments
        double overallAverage = (avgD001 + avgD002 + avgD003) / 3;
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() throws Exception {
        // Create department D004
        Department deptD004 = factory.createDepartment();
        deptD004.setID("D004");
        deptD004.setEmail("finance@company.com");
        
        // Create project with zero budget in department D004
        Project project = factory.createProductionProject();
        project.setTitle("Budget Review");
        project.setDescription("Review of the annual budget");
        project.setBudget(0.0);
        project.setDeadline(dateFormat.parse("2025-07-30 00:00:00"));
        deptD004.getProjects().add(project);
        
        // Calculate average budget for department D004
        double averageBudget = deptD004.calculateAverageBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 with no projects
        Department deptD005 = factory.createDepartment();
        deptD005.setID("D005");
        deptD005.setEmail("research@company.com");
        
        // Calculate average budget for department with no projects
        double averageBudget = deptD005.calculateAverageBudget();
        
        // Expected: 0 CNY (handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() throws Exception {
        // Create department D006
        Department deptD006 = factory.createDepartment();
        deptD006.setID("D006");
        deptD006.setEmail("product@company.com");
        
        // Create project "Product Launch" in department D006
        Project project1 = factory.createProductionProject();
        project1.setTitle("Product Launch");
        project1.setDescription("Launching new product");
        project1.setBudget(150000.0);
        project1.setDeadline(dateFormat.parse("2025-12-01 00:00:00"));
        deptD006.getProjects().add(project1);
        
        // Create project "Market Analysis" in department D006
        Project project2 = factory.createProductionProject();
        project2.setTitle("Market Analysis");
        project2.setDescription("Analysis of market trends");
        project2.setBudget(75000.0);
        project2.setDeadline(dateFormat.parse("2025-10-15 00:00:00"));
        deptD006.getProjects().add(project2);
        
        // Create project "Client Engagement" in department D006
        Project project3 = factory.createProductionProject();
        project3.setTitle("Client Engagement");
        project3.setDescription("Engaging with key clients");
        project3.setBudget(50000.0);
        project3.setDeadline(dateFormat.parse("2025-11-30 00:00:00"));
        deptD006.getProjects().add(project3);
        
        // Calculate average budget for department D006
        double averageBudget = deptD006.calculateAverageBudget();
        
        // Expected: (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}