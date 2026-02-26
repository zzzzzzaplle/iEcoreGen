import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() throws ParseException {
        // Test Case 1: Single Department Budget Calculation
        // Create company C001
        Company companyC001 = new Company();
        
        // Create department D001 and add to company
        Department departmentD001 = new Department();
        departmentD001.setID("D001");
        departmentD001.setEmail("department1@company.com");
        companyC001.addDepartment(departmentD001);
        
        // Create first project for department D001
        Project project1 = new Project();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        departmentD001.addProject(project1);
        
        // Create second project for department D001
        Project project2 = new Project();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        project2.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        departmentD001.addProject(project2);
        
        // Calculate total budget
        double totalBudget = companyC001.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws ParseException {
        // Test Case 2: Multiple Departments Budget Calculation
        // Create company C002
        Company companyC002 = new Company();
        
        // Create department D001 and add to company
        Department departmentD001 = new Department();
        departmentD001.setID("D001");
        departmentD001.setEmail("department1@company.com");
        companyC002.addDepartment(departmentD001);
        
        // Create project in D001
        Project project1 = new Project();
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        departmentD001.addProject(project1);
        
        // Create department D002 and add to company
        Department departmentD002 = new Department();
        departmentD002.setID("D002");
        departmentD002.setEmail("department2@company.com");
        companyC002.addDepartment(departmentD002);
        
        // Create first project in D002
        Project project2 = new Project();
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        departmentD002.addProject(project2);
        
        // Create second project in D002
        Project project3 = new Project();
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000.0);
        departmentD002.addProject(project3);
        
        // Calculate total budget
        double totalBudget = companyC002.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Test Case 3: Budget Calculation with Zero Projects
        // Create company C003
        Company companyC003 = new Company();
        
        // Create department D003 and add to company
        Department departmentD003 = new Department();
        departmentD003.setID("D003");
        departmentD003.setEmail("department3@company.com");
        companyC003.addDepartment(departmentD003);
        
        // Calculate total budget (no projects added)
        double totalBudget = companyC003.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws ParseException {
        // Test Case 4: Education Project Budget with Funding Group
        // Create company C004
        Company companyC004 = new Company();
        
        // Create department D004 and add to company
        Department departmentD004 = new Department();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@company.com");
        companyC004.addDepartment(departmentD004);
        
        // Create education project with funding group
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Scholarship Program");
        educationProject.setBudget(50000.0);
        educationProject.setDeadline(dateFormat.parse("2026-05-31 23:59:59"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.GOVERNMENT);
        educationProject.setFundingGroup(fundingGroup1);
        
        departmentD004.addProject(educationProject);
        
        // Create another project in D004
        Project project2 = new Project();
        project2.setTitle("R&D Initiative");
        project2.setBudget(70000.0);
        project2.setDeadline(dateFormat.parse("2026-07-15 23:59:59"));
        departmentD004.addProject(project2);
        
        // Create department D005 and add to company
        Department departmentD005 = new Department();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@company.com");
        companyC004.addDepartment(departmentD005);
        
        // Create project in D005
        Project project3 = new Project();
        project3.setTitle("R&D5 Initiative");
        project3.setBudget(70000.0);
        project3.setDeadline(dateFormat.parse("2026-07-19 23:59:59"));
        departmentD005.addProject(project3);
        
        // Calculate total budget
        double totalBudget = companyC004.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws ParseException {
        // Test Case 5: Community Project Budget with Mixed Funding Group
        // Create company C005
        Company companyC005 = new Company();
        
        // Create department D006 and add to company
        Department departmentD006 = new Department();
        departmentD006.setID("D006");
        departmentD006.setEmail("department5@company.com");
        companyC005.addDepartment(departmentD006);
        
        // Create community project with mixed funding group
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Health Awareness");
        communityProject.setBudget(40000.0);
        communityProject.setDeadline(dateFormat.parse("2027-02-28 23:59:59"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        communityProject.setFundingGroup(fundingGroup2);
        
        departmentD006.addProject(communityProject);
        
        // Create another project in D006
        Project project2 = new Project();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudget(60000.0);
        project2.setDeadline(dateFormat.parse("2027-03-30 23:59:59"));
        departmentD006.addProject(project2);
        
        // Calculate total budget
        double totalBudget = companyC005.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(100000.0, totalBudget, 0.001);
    }
}