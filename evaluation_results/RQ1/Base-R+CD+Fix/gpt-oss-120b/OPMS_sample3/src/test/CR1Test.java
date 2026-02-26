import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {

    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() throws Exception {
        // Create a company C001
        Company company = new Company();
        
        // Create a department with ID: D001 and email: department1@company.com
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@company.com");
        
        // Add the department to the company C001
        company.addDepartment(department);
        
        // Create project 1: "Website Redevelopment"
        Project project1 = new Project();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        
        // Create project 2: "Mobile App Development"
        Project project2 = new Project();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        project2.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        
        // Associate projects with department D001
        department.addProject(project1);
        department.addProject(project2);
        
        // Calculate the total budget for company C001
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Create a company C002
        Company company = new Company();
        
        // Create Department D001 with email: department1@company.com
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@company.com");
        
        // Add the department to the company C002
        company.addDepartment(department1);
        
        // Create a project in D001: "HR Software"
        Project project1 = new Project();
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        department1.addProject(project1);
        
        // Create Department D002 with email: department2@company.com
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@company.com");
        
        // Add the department to the company C002
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
        
        // Calculate the total budget for company C002
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create a company C003
        Company company = new Company();
        
        // Create Department with ID: D003 and email: department3@company.com
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@company.com");
        
        // Add the department to the company C003
        company.addDepartment(department);
        
        // Calculate the total budget for company C003
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Create a company C004
        Company company = new Company();
        
        // Create a department with ID: D004 and email: department4@company.com
        Department department1 = new Department();
        department1.setID("D004");
        department1.setEmail("department4@company.com");
        
        // Add the department to the company C004
        company.addDepartment(department1);
        
        // Create an education project: "Scholarship Program"
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2026-05-31 23:59:59"));
        
        // Create funding group with type: government group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project1.setFundingGroup(fundingGroup);
        
        // Associate project with department D004
        department1.addProject(project1);
        
        // Create another project: "R&D Initiative"
        Project project2 = new Project();
        project2.setTitle("R&D Initiative");
        project2.setBudget(70000.0);
        project2.setDeadline(dateFormat.parse("2026-07-15 23:59:59"));
        department1.addProject(project2);
        
        // Create a department with ID: D005 and email: department5@company.com
        Department department2 = new Department();
        department2.setID("D005");
        department2.setEmail("department5@company.com");
        
        // Add the department to the company C004
        company.addDepartment(department2);
        
        // Create another project: "R&D5 Initiative"
        Project project3 = new Project();
        project3.setTitle("R&D5 Initiative");
        project3.setBudget(70000.0);
        project3.setDeadline(dateFormat.parse("2026-07-19 23:59:59"));
        department2.addProject(project3);
        
        // Calculate the total budget for company C004
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Create a company C005
        Company company = new Company();
        
        // Create a department with ID: D006 and email: department5@company.com
        Department department = new Department();
        department.setID("D006");
        department.setEmail("department5@company.com");
        
        // Add the department to the company C005
        company.addDepartment(department);
        
        // Create a community project: "Community Health Awareness"
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudget(40000.0);
        project1.setDeadline(dateFormat.parse("2027-02-28 23:59:59"));
        
        // Create funding group with type: mixed group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        project1.setFundingGroup(fundingGroup);
        
        // Associate project with department D006
        department.addProject(project1);
        
        // Create another project: "Environmental Clean-up Initiative"
        Project project2 = new Project();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudget(60000.0);
        project2.setDeadline(dateFormat.parse("2027-03-30 23:59:59"));
        department.addProject(project2);
        
        // Calculate the total budget for company C005
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}