import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() {
        // Create a company C001
        Company company = new Company();
        
        // Create a department with ID: D001 and email: department1@company.com
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@company.com");
        
        // Add department to the company
        company.addDepartment(department);
        
        // Create project 1: "Website Redevelopment"
        Project project1 = new Project();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            project1.setDeadline(sdf.parse("2025-12-31"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Create project 2: "Mobile App Development"
        Project project2 = new Project();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            project2.setDeadline(sdf.parse("2026-01-15"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Calculate total budget for company C001
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Create a company C002
        Company company = new Company();
        
        // Create Department D001
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@company.com");
        company.addDepartment(department1);
        
        // Create project in D001: "HR Software"
        Project project1 = new Project();
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        department1.addProject(project1);
        
        // Create Department D002
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@company.com");
        company.addDepartment(department2);
        
        // Create project 1 in D002: "Sales Training Program"
        Project project2 = new Project();
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        department2.addProject(project2);
        
        // Create project 2 in D002: "Marketing Campaign"
        Project project3 = new Project();
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000.0);
        department2.addProject(project3);
        
        // Calculate total budget for company C002
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create a company C003
        Company company = new Company();
        
        // Create Department D003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@company.com");
        company.addDepartment(department);
        
        // Calculate total budget for company C003 (department has no projects)
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Create a company C004
        Company company = new Company();
        
        // Create Department D004
        Department department1 = new Department();
        department1.setID("D004");
        department1.setEmail("department4@company.com");
        company.addDepartment(department1);
        
        // Create education project: "Scholarship Program"
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Scholarship Program");
        educationProject.setBudget(50000.0);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            educationProject.setDeadline(sdf.parse("2026-05-31"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Create funding group for education project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        educationProject.setFundingGroup(fundingGroup);
        
        // Create another project: "R&D Initiative"
        Project project2 = new Project();
        project2.setTitle("R&D Initiative");
        project2.setBudget(70000.0);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            project2.setDeadline(sdf.parse("2026-07-15"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add projects to department D004
        department1.addProject(educationProject);
        department1.addProject(project2);
        
        // Create Department D005
        Department department2 = new Department();
        department2.setID("D005");
        department2.setEmail("department5@company.com");
        company.addDepartment(department2);
        
        // Create project in D005: "R&D5 Initiative"
        Project project3 = new Project();
        project3.setTitle("R&D5 Initiative");
        project3.setBudget(70000.0);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            project3.setDeadline(sdf.parse("2026-07-19"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        department2.addProject(project3);
        
        // Calculate total budget for company C004
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Create a company C005
        Company company = new Company();
        
        // Create Department D006
        Department department = new Department();
        department.setID("D006");
        department.setEmail("department5@company.com");
        company.addDepartment(department);
        
        // Create community project: "Community Health Awareness"
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Health Awareness");
        communityProject.setBudget(40000.0);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            communityProject.setDeadline(sdf.parse("2027-02-28"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Create funding group for community project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        communityProject.setFundingGroup(fundingGroup);
        
        // Create another project: "Environmental Clean-up Initiative"
        Project project2 = new Project();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudget(60000.0);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            project2.setDeadline(sdf.parse("2027-03-30"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Add projects to department
        department.addProject(communityProject);
        department.addProject(project2);
        
        // Calculate total budget for company C005
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}