import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

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
        // Create company C001
        Company c001 = new Company();
        
        // Create department D001
        Department d001 = new Department();
        d001.setID("D001");
        d001.setEmail("department1@company.com");
        c001.addDepartment(d001);
        
        // Create project 1: Website Redevelopment
        Project project1 = new Project() {};
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        d001.addProject(project1);
        
        // Create project 2: Mobile App Development
        Project project2 = new Project() {};
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        project2.setDeadline(dateFormat.parse("2026-01-15 00:00:00"));
        d001.addProject(project2);
        
        // Calculate total budget
        double totalBudget = c001.calculateTotalBudget();
        
        // Expected: 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Test Case 2: Multiple Departments Budget Calculation
        // Create company C002
        Company c002 = new Company();
        
        // Create department D001
        Department d001 = new Department();
        d001.setID("D001");
        d001.setEmail("department1@company.com");
        c002.addDepartment(d001);
        
        // Create project in D001: HR Software
        Project project1 = new Project() {};
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        d001.addProject(project1);
        
        // Create department D002
        Department d002 = new Department();
        d002.setID("D002");
        d002.setEmail("department2@company.com");
        c002.addDepartment(d002);
        
        // Create project 1 in D002: Sales Training Program
        Project project2 = new Project() {};
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        d002.addProject(project2);
        
        // Create project 2 in D002: Marketing Campaign
        Project project3 = new Project() {};
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000.0);
        d002.addProject(project3);
        
        // Calculate total budget
        double totalBudget = c002.calculateTotalBudget();
        
        // Expected: 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Test Case 3: Budget Calculation with Zero Projects
        // Create company C003
        Company c003 = new Company();
        
        // Create department D003
        Department d003 = new Department();
        d003.setID("D003");
        d003.setEmail("department3@company.com");
        c003.addDepartment(d003);
        
        // Calculate total budget (no projects added)
        double totalBudget = c003.calculateTotalBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Test Case 4: Education Project Budget with Funding Group
        // Create company C004
        Company c004 = new Company();
        
        // Create department D004
        Department d004 = new Department();
        d004.setID("D004");
        d004.setEmail("department4@company.com");
        c004.addDepartment(d004);
        
        // Create education project: Scholarship Program
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2026-05-31 00:00:00"));
        
        // Create funding group for education project
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.GOVERNMENT);
        project1.setFundingGroup(fundingGroup1);
        
        d004.addProject(project1);
        
        // Create another project: R&D Initiative
        Project project2 = new Project() {};
        project2.setTitle("R&D Initiative");
        project2.setBudget(70000.0);
        project2.setDeadline(dateFormat.parse("2026-07-15 00:00:00"));
        d004.addProject(project2);
        
        // Create department D005
        Department d005 = new Department();
        d005.setID("D005");
        d005.setEmail("department5@company.com");
        c004.addDepartment(d005);
        
        // Create project in D005: R&D5 Initiative
        Project project3 = new Project() {};
        project3.setTitle("R&D5 Initiative");
        project3.setBudget(70000.0);
        project3.setDeadline(dateFormat.parse("2026-07-19 00:00:00"));
        d005.addProject(project3);
        
        // Calculate total budget
        double totalBudget = c004.calculateTotalBudget();
        
        // Expected: 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Test Case 5: Community Project Budget with Mixed Funding Group
        // Create company C005
        Company c005 = new Company();
        
        // Create department D006
        Department d006 = new Department();
        d006.setID("D006");
        d006.setEmail("department5@company.com");
        c005.addDepartment(d006);
        
        // Create community project: Community Health Awareness
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudget(40000.0);
        project1.setDeadline(dateFormat.parse("2027-02-28 00:00:00"));
        
        // Create funding group for community project
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.MIXED);
        project1.setFundingGroup(fundingGroup1);
        
        d006.addProject(project1);
        
        // Create another project: Environmental Clean-up Initiative
        Project project2 = new Project() {};
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudget(60000.0);
        project2.setDeadline(dateFormat.parse("2027-03-30 00:00:00"));
        d006.addProject(project2);
        
        // Calculate total budget
        double totalBudget = c005.calculateTotalBudget();
        
        // Expected: 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}