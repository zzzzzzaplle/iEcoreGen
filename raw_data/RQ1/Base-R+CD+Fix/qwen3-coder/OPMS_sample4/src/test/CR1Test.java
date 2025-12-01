import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() {
        // Test Case 1: Single Department Budget Calculation
        // Create company C001
        Company company = new Company();
        
        // Create department D001
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@company.com");
        company.addDepartment(department);
        
        // Create first project "Website Redevelopment"
        Project project1 = new Project();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        try {
            project1.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-31"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        department.addProject(project1);
        
        // Create second project "Mobile App Development"
        Project project2 = new Project();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        try {
            project2.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2026-01-15"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        department.addProject(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Test Case 2: Multiple Departments Budget Calculation
        // Create company C002
        Company company = new Company();
        
        // Create department D001
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@company.com");
        company.addDepartment(department1);
        
        // Create project "HR Software" in D001
        Project project1 = new Project();
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        department1.addProject(project1);
        
        // Create department D002
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@company.com");
        company.addDepartment(department2);
        
        // Create project "Sales Training Program" in D002
        Project project2 = new Project();
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        department2.addProject(project2);
        
        // Create project "Marketing Campaign" in D002
        Project project3 = new Project();
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000.0);
        department2.addProject(project3);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Test Case 3: Budget Calculation with Zero Projects
        // Create company C003
        Company company = new Company();
        
        // Create department D003
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@company.com");
        company.addDepartment(department);
        
        // Calculate total budget (no projects added)
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Test Case 4: Education Project Budget with Funding Group
        // Create company C004
        Company company = new Company();
        
        // Create department D004
        Department department1 = new Department();
        department1.setID("D004");
        department1.setEmail("department4@company.com");
        company.addDepartment(department1);
        
        // Create education project "Scholarship Program" with funding group
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Scholarship Program");
        educationProject.setBudget(50000.0);
        try {
            educationProject.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2026-05-31"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.GOVERNMENT);
        educationProject.setFundingGroup(fundingGroup1);
        department1.addProject(educationProject);
        
        // Create project "R&D Initiative" in D004
        Project project1 = new Project();
        project1.setTitle("R&D Initiative");
        project1.setBudget(70000.0);
        try {
            project1.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2026-07-15"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        department1.addProject(project1);
        
        // Create department D005
        Department department2 = new Department();
        department2.setID("D005");
        department2.setEmail("department5@company.com");
        company.addDepartment(department2);
        
        // Create project "R&D5 Initiative" in D005
        Project project2 = new Project();
        project2.setTitle("R&D5 Initiative");
        project2.setBudget(70000.0);
        try {
            project2.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2026-07-19"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        department2.addProject(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Test Case 5: Community Project Budget with Mixed Funding Group
        // Create company C005
        Company company = new Company();
        
        // Create department D006
        Department department = new Department();
        department.setID("D006");
        department.setEmail("department5@company.com");
        company.addDepartment(department);
        
        // Create community project "Community Health Awareness" with mixed funding group
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Health Awareness");
        communityProject.setBudget(40000.0);
        try {
            communityProject.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2027-02-28"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        communityProject.setFundingGroup(fundingGroup);
        department.addProject(communityProject);
        
        // Create project "Environmental Clean-up Initiative"
        Project project = new Project();
        project.setTitle("Environmental Clean-up Initiative");
        project.setBudget(60000.0);
        try {
            project.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2027-03-30"));
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        department.addProject(project);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(100000.0, totalBudget, 0.001);
    }
}