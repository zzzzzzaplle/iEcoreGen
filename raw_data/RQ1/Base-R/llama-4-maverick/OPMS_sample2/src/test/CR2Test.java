import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // Create department D001 (Marketing)
        Department marketingDept = new Department();
        marketingDept.setId("D001");
        marketingDept.setEmail("marketing@company.com");
        
        // Create project "Ads Campaign" for D001
        Project adsCampaign = new ProductionProject();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudgetAmount(50000);
        adsCampaign.setDeadline("2025-12-31");
        
        // Create project "Market Research" for D001
        Project marketResearch = new ProductionProject();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudgetAmount(30000);
        marketResearch.setDeadline("2025-11-30");
        
        // Add projects to D001
        List<Project> marketingProjects = new ArrayList<>();
        marketingProjects.add(adsCampaign);
        marketingProjects.add(marketResearch);
        marketingDept.setProjects(marketingProjects);
        
        // Create department D002 (Development) with different project
        Department developmentDept = new Department();
        developmentDept.setId("D002");
        developmentDept.setEmail("development@company.com");
        
        Project newFeature = new ProductionProject();
        newFeature.setTitle("New Feature Development");
        newFeature.setDescription("Developing a new feature");
        newFeature.setBudgetAmount(200000);
        newFeature.setDeadline("2026-01-15");
        
        List<Project> developmentProjects = new ArrayList<>();
        developmentProjects.add(newFeature);
        developmentDept.setProjects(developmentProjects);
        
        // Calculate average budget for D001
        double actualAverage = marketingDept.calculateAverageBudget();
        double expectedAverage = 40000.0;
        
        assertEquals("Average budget for D001 should be 40000 CNY", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // Create department D001 (HR)
        Department hrDept = new Department();
        hrDept.setId("D001");
        hrDept.setEmail("hr@company.com");
        
        Project employeeTraining = new ProductionProject();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudgetAmount(20000);
        employeeTraining.setDeadline("2025-08-15");
        
        List<Project> hrProjects = new ArrayList<>();
        hrProjects.add(employeeTraining);
        hrDept.setProjects(hrProjects);
        
        // Create department D002 (Sales)
        Department salesDept = new Department();
        salesDept.setId("D002");
        salesDept.setEmail("sales@company.com");
        
        Project salesStrategy = new ProductionProject();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudgetAmount(40000);
        salesStrategy.setDeadline("2025-09-15");
        
        List<Project> salesProjects = new ArrayList<>();
        salesProjects.add(salesStrategy);
        salesDept.setProjects(salesProjects);
        
        // Create department D003 (IT)
        Department itDept = new Department();
        itDept.setId("D003");
        itDept.setEmail("it@company.com");
        
        Project systemUpgrade = new ProductionProject();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudgetAmount(60000);
        systemUpgrade.setDeadline("2025-10-01");
        
        List<Project> itProjects = new ArrayList<>();
        itProjects.add(systemUpgrade);
        itDept.setProjects(itProjects);
        
        // Calculate average budget for each department
        double hrAverage = hrDept.calculateAverageBudget();
        double salesAverage = salesDept.calculateAverageBudget();
        double itAverage = itDept.calculateAverageBudget();
        
        // Calculate overall average across departments
        double overallAverage = (hrAverage + salesAverage + itAverage) / 3;
        
        // Verify individual department averages
        assertEquals("Average budget for D001 should be 20000 CNY", 20000.0, hrAverage, 0.001);
        assertEquals("Average budget for D002 should be 40000 CNY", 40000.0, salesAverage, 0.001);
        assertEquals("Average budget for D003 should be 60000 CNY", 60000.0, itAverage, 0.001);
        assertEquals("Overall average should be 40000 CNY", 40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // Create department D004 (Finance)
        Department financeDept = new Department();
        financeDept.setId("D004");
        financeDept.setEmail("finance@company.com");
        
        // Create project with zero budget
        Project budgetReview = new ProductionProject();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudgetAmount(0);
        budgetReview.setDeadline("2025-07-30");
        
        List<Project> financeProjects = new ArrayList<>();
        financeProjects.add(budgetReview);
        financeDept.setProjects(financeProjects);
        
        // Calculate average budget
        double actualAverage = financeDept.calculateAverageBudget();
        double expectedAverage = 0.0;
        
        assertEquals("Average budget for zero-budget project should be 0 CNY", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // Create department D005 (Research) with no projects
        Department researchDept = new Department();
        researchDept.setId("D005");
        researchDept.setEmail("research@company.com");
        
        // Calculate average budget for empty department
        double actualAverage = researchDept.calculateAverageBudget();
        double expectedAverage = 0.0;
        
        assertEquals("Average budget for department with no projects should be 0 CNY", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // Create department D006 (Product)
        Department productDept = new Department();
        productDept.setId("D006");
        productDept.setEmail("product@company.com");
        
        // Create project "Product Launch"
        Project productLaunch = new ProductionProject();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudgetAmount(150000);
        productLaunch.setDeadline("2025-12-01");
        
        // Create project "Market Analysis"
        Project marketAnalysis = new ProductionProject();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudgetAmount(75000);
        marketAnalysis.setDeadline("2025-10-15");
        
        // Create project "Client Engagement"
        Project clientEngagement = new ProductionProject();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudgetAmount(50000);
        clientEngagement.setDeadline("2025-11-30");
        
        // Add all projects to department
        List<Project> productProjects = new ArrayList<>();
        productProjects.add(productLaunch);
        productProjects.add(marketAnalysis);
        productProjects.add(clientEngagement);
        productDept.setProjects(productProjects);
        
        // Calculate average budget
        double actualAverage = productDept.calculateAverageBudget();
        double expectedAverage = 91666.67;
        
        assertEquals("Average budget should be 91666.67 CNY", expectedAverage, actualAverage, 0.01);
    }
}