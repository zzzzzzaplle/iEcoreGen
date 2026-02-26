import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_SingleProjectAverageBudgetCalculation() {
        // SetUp
        Department marketingDept = new Department();
        marketingDept.setId("D001");
        marketingDept.setEmail("marketing@company.com");
        
        Project adsCampaign = new ProductionProject();
        adsCampaign.setTitle("Ads Campaign");
        adsCampaign.setDescription("Q1 advertising budget");
        adsCampaign.setBudgetAmount(50000.0);
        adsCampaign.setDeadline("2025-12-31");
        
        Project marketResearch = new ProductionProject();
        marketResearch.setTitle("Market Research");
        marketResearch.setDescription("Research on consumer behavior");
        marketResearch.setBudgetAmount(30000.0);
        marketResearch.setDeadline("2025-11-30");
        
        List<Project> marketingProjects = new ArrayList<>();
        marketingProjects.add(adsCampaign);
        marketingProjects.add(marketResearch);
        marketingDept.setProjects(marketingProjects);
        
        Department developmentDept = new Department();
        developmentDept.setId("D002");
        developmentDept.setEmail("development@company.com");
        
        Project newFeature = new ProductionProject();
        newFeature.setTitle("New Feature Development");
        newFeature.setDescription("Developing a new feature");
        newFeature.setBudgetAmount(200000.0);
        newFeature.setDeadline("2026-01-15");
        
        List<Project> developmentProjects = new ArrayList<>();
        developmentProjects.add(newFeature);
        developmentDept.setProjects(developmentProjects);
        
        // Calculate the average budget for department D001
        double averageBudget = marketingDept.getAverageBudgetOfAllProjects();
        
        // Expected Output: Average budget = (50000 + 30000) / 2 = 40000 CNY
        assertEquals(40000.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsAverageBudgetCalculation() {
        // SetUp
        Department hrDept = new Department();
        hrDept.setId("D001");
        hrDept.setEmail("hr@company.com");
        
        Project employeeTraining = new ProductionProject();
        employeeTraining.setTitle("Employee Training");
        employeeTraining.setDescription("Training for employees");
        employeeTraining.setBudgetAmount(20000.0);
        employeeTraining.setDeadline("2025-08-15");
        
        List<Project> hrProjects = new ArrayList<>();
        hrProjects.add(employeeTraining);
        hrDept.setProjects(hrProjects);
        
        Department salesDept = new Department();
        salesDept.setId("D002");
        salesDept.setEmail("sales@company.com");
        
        Project salesStrategy = new ProductionProject();
        salesStrategy.setTitle("Sales Strategy");
        salesStrategy.setDescription("New sales strategy implementation");
        salesStrategy.setBudgetAmount(40000.0);
        salesStrategy.setDeadline("2025-09-15");
        
        List<Project> salesProjects = new ArrayList<>();
        salesProjects.add(salesStrategy);
        salesDept.setProjects(salesProjects);
        
        Department itDept = new Department();
        itDept.setId("D003");
        itDept.setEmail("it@company.com");
        
        Project systemUpgrade = new ProductionProject();
        systemUpgrade.setTitle("System Upgrade");
        systemUpgrade.setDescription("Upgrade company systems");
        systemUpgrade.setBudgetAmount(60000.0);
        systemUpgrade.setDeadline("2025-10-01");
        
        List<Project> itProjects = new ArrayList<>();
        itProjects.add(systemUpgrade);
        itDept.setProjects(itProjects);
        
        // Calculate the average budget for projects in departments D001, D002, and D003
        double hrAverage = hrDept.getAverageBudgetOfAllProjects();
        double salesAverage = salesDept.getAverageBudgetOfAllProjects();
        double itAverage = itDept.getAverageBudgetOfAllProjects();
        
        // Expected Output: 
        assertEquals(20000.0, hrAverage, 0.001);
        assertEquals(40000.0, salesAverage, 0.001);
        assertEquals(60000.0, itAverage, 0.001);
        
        // Calculate the average budget for projects in all departments D001, D002, and D003
        double overallAverage = (hrAverage + salesAverage + itAverage) / 3;
        
        // Expected Output: (20000 + 40000 + 60000) / 3 = 40000 CNY
        assertEquals(40000.0, overallAverage, 0.001);
    }
    
    @Test
    public void testCase3_SingleProjectCalculationForZeroBudget() {
        // SetUp
        Department financeDept = new Department();
        financeDept.setId("D004");
        financeDept.setEmail("finance@company.com");
        
        Project budgetReview = new ProductionProject();
        budgetReview.setTitle("Budget Review");
        budgetReview.setDescription("Review of the annual budget");
        budgetReview.setBudgetAmount(0.0);
        budgetReview.setDeadline("2025-07-30");
        
        List<Project> financeProjects = new ArrayList<>();
        financeProjects.add(budgetReview);
        financeDept.setProjects(financeProjects);
        
        // Calculate the average budget for department D004
        double averageBudget = financeDept.getAverageBudgetOfAllProjects();
        
        // Expected Output: Average budget = 0 CNY
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase4_NoProjectsInDepartment() {
        // SetUp
        Department researchDept = new Department();
        researchDept.setId("D005");
        researchDept.setEmail("research@company.com");
        
        // Calculate the average budget for department D005 (which has no projects)
        double averageBudget = researchDept.getAverageBudgetOfAllProjects();
        
        // Expected Output: Average budget = 0 CNY (or handled as "No projects available")
        assertEquals(0.0, averageBudget, 0.001);
    }
    
    @Test
    public void testCase5_ProjectsWithDifferentBudgetsCalculation() {
        // SetUp
        Department productDept = new Department();
        productDept.setId("D006");
        productDept.setEmail("product@company.com");
        
        Project productLaunch = new ProductionProject();
        productLaunch.setTitle("Product Launch");
        productLaunch.setDescription("Launching new product");
        productLaunch.setBudgetAmount(150000.0);
        productLaunch.setDeadline("2025-12-01");
        
        Project marketAnalysis = new ProductionProject();
        marketAnalysis.setTitle("Market Analysis");
        marketAnalysis.setDescription("Analysis of market trends");
        marketAnalysis.setBudgetAmount(75000.0);
        marketAnalysis.setDeadline("2025-10-15");
        
        Project clientEngagement = new ProductionProject();
        clientEngagement.setTitle("Client Engagement");
        clientEngagement.setDescription("Engaging with key clients");
        clientEngagement.setBudgetAmount(50000.0);
        clientEngagement.setDeadline("2025-11-30");
        
        List<Project> productProjects = new ArrayList<>();
        productProjects.add(productLaunch);
        productProjects.add(marketAnalysis);
        productProjects.add(clientEngagement);
        productDept.setProjects(productProjects);
        
        // Calculate the average budget for department D006
        double averageBudget = productDept.getAverageBudgetOfAllProjects();
        
        // Expected Output: Average budget = (150000 + 75000 + 50000) / 3 = 91666.67 CNY
        assertEquals(91666.67, averageBudget, 0.01);
    }
}