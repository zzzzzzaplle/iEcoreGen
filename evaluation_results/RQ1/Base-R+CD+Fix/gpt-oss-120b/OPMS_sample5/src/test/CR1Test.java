import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() throws Exception {
        // Test Case 1: Single Department Budget Calculation
        // SetUp step 1: Create a company C001
        Company c001 = new Company();
        
        // SetUp step 2: Create a department with ID: D001 and email: department1@company.com
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@company.com");
        c001.addDepartment(dept1);
        
        // SetUp step 3: Create project "Website Redevelopment"
        Project project1 = new ProductionProject();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        dept1.addProject(project1);
        
        // SetUp step 4: Create project "Mobile App Development"
        Project project2 = new ProductionProject();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        project2.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        dept1.addProject(project2);
        
        // SetUp step 5: Calculate total budget
        double totalBudget = c001.calculateTotalBudget();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Test Case 2: Multiple Departments Budget Calculation
        // SetUp step 1: Create company C002 and Department D001
        Company c002 = new Company();
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@company.com");
        c002.addDepartment(dept1);
        
        // SetUp step 2: Create project "HR Software" in D001
        Project project1 = new ProductionProject();
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        dept1.addProject(project1);
        
        // SetUp step 3-4: Create Department D002 and project "Sales Training Program"
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@company.com");
        c002.addDepartment(dept2);
        
        Project project2 = new ProductionProject();
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        dept2.addProject(project2);
        
        // SetUp step 5: Create project "Marketing Campaign" in D002
        Project project3 = new ProductionProject();
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000.0);
        dept2.addProject(project3);
        
        // SetUp step 6: Calculate total budget
        double totalBudget = c002.calculateTotalBudget();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Test Case 3: Budget Calculation with Zero Projects
        // SetUp step 1: Create company C003 and Department D003
        Company c003 = new Company();
        Department dept = new Department();
        dept.setID("D003");
        dept.setEmail("department3@company.com");
        c003.addDepartment(dept);
        
        // SetUp step 2: Calculate total budget (no projects added)
        double totalBudget = c003.calculateTotalBudget();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Test Case 4: Education Project Budget with Funding Group
        // SetUp step 1: Create company C004 and Department D004
        Company c004 = new Company();
        Department dept1 = new Department();
        dept1.setID("D004");
        dept1.setEmail("department4@company.com");
        c004.addDepartment(dept1);
        
        // SetUp step 2: Create education project "Scholarship Program" with government funding group
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2026-05-31 23:59:59"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.GOVERNMENT);
        project1.setFundingGroup(fundingGroup1);
        
        dept1.addProject(project1);
        
        // SetUp step 3: Create project "R&D Initiative" in D004
        Project project2 = new ResearchProject();
        project2.setTitle("R&D Initiative");
        project2.setBudget(70000.0);
        project2.setDeadline(dateFormat.parse("2026-07-15 23:59:59"));
        dept1.addProject(project2);
        
        // SetUp step 4: Create Department D005 and add to company
        Department dept2 = new Department();
        dept2.setID("D005");
        dept2.setEmail("department5@company.com");
        c004.addDepartment(dept2);
        
        // SetUp step 5: Create project "R&D5 Initiative" in D005
        Project project3 = new ResearchProject();
        project3.setTitle("R&D5 Initiative");
        project3.setBudget(70000.0);
        project3.setDeadline(dateFormat.parse("2026-07-19 23:59:59"));
        dept2.addProject(project3);
        
        // Calculate total budget
        double totalBudget = c004.calculateTotalBudget();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Test Case 5: Community Project Budget with Mixed Funding Group
        // SetUp step 1: Create company C005 and Department D006
        Company c005 = new Company();
        Department dept = new Department();
        dept.setID("D006");
        dept.setEmail("department5@company.com");
        c005.addDepartment(dept);
        
        // SetUp step 2: Create community project "Community Health Awareness" with mixed funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudget(40000.0);
        project1.setDeadline(dateFormat.parse("2027-02-28 23:59:59"));
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        project1.setFundingGroup(fundingGroup);
        
        dept.addProject(project1);
        
        // SetUp step 3: Create project "Environmental Clean-up Initiative"
        Project project2 = new CommunityProject();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudget(60000.0);
        project2.setDeadline(dateFormat.parse("2027-03-30 23:59:59"));
        dept.addProject(project2);
        
        // Calculate total budget
        double totalBudget = c005.calculateTotalBudget();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}