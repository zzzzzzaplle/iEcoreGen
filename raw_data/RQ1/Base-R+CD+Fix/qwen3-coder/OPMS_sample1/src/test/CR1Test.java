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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() throws Exception {
        // Create company C001
        Company companyC001 = new Company();
        
        // Create department D001 and add to company
        Department departmentD001 = new Department();
        departmentD001.setID("D001");
        departmentD001.setEmail("department1@company.com");
        companyC001.addDepartment(departmentD001);
        
        // Create first project "Website Redevelopment"
        Project project1 = new Project() {};
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31"));
        departmentD001.addProject(project1);
        
        // Create second project "Mobile App Development"
        Project project2 = new Project() {};
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        project2.setDeadline(dateFormat.parse("2026-01-15"));
        departmentD001.addProject(project2);
        
        // Calculate total budget
        double totalBudget = companyC001.calculateTotalBudget();
        
        // Verify expected output: 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Create company C002
        Company companyC002 = new Company();
        
        // Create department D001 and add to company
        Department departmentD001 = new Department();
        departmentD001.setID("D001");
        departmentD001.setEmail("department1@company.com");
        companyC002.addDepartment(departmentD001);
        
        // Create project "HR Software" in D001
        Project project1 = new Project() {};
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        departmentD001.addProject(project1);
        
        // Create department D002 and add to company
        Department departmentD002 = new Department();
        departmentD002.setID("D002");
        departmentD002.setEmail("department2@company.com");
        companyC002.addDepartment(departmentD002);
        
        // Create project "Sales Training Program" in D002
        Project project2 = new Project() {};
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        departmentD002.addProject(project2);
        
        // Create project "Marketing Campaign" in D002
        Project project3 = new Project() {};
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000.0);
        departmentD002.addProject(project3);
        
        // Calculate total budget
        double totalBudget = companyC002.calculateTotalBudget();
        
        // Verify expected output: 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create company C003
        Company companyC003 = new Company();
        
        // Create department D003 and add to company
        Department departmentD003 = new Department();
        departmentD003.setID("D003");
        departmentD003.setEmail("department3@company.com");
        companyC003.addDepartment(departmentD003);
        
        // Calculate total budget
        double totalBudget = companyC003.calculateTotalBudget();
        
        // Verify expected output: 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }

    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Create company C004
        Company companyC004 = new Company();
        
        // Create department D004 and add to company
        Department departmentD004 = new Department();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@company.com");
        companyC004.addDepartment(departmentD004);
        
        // Create education project "Scholarship Program" with funding group
        EducationProject educationProject = new EducationProject();
        educationProject.setTitle("Scholarship Program");
        educationProject.setBudget(50000.0);
        educationProject.setDeadline(dateFormat.parse("2026-05-31"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.GOVERNMENT);
        educationProject.setFundingGroup(fundingGroup1);
        
        departmentD004.addProject(educationProject);
        
        // Create another project "R&D Initiative"
        Project project2 = new Project() {};
        project2.setTitle("R&D Initiative");
        project2.setBudget(70000.0);
        project2.setDeadline(dateFormat.parse("2026-07-15"));
        departmentD004.addProject(project2);
        
        // Create department D005 and add to company
        Department departmentD005 = new Department();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@company.com");
        companyC004.addDepartment(departmentD005);
        
        // Create project "R&D5 Initiative" in D005
        Project project3 = new Project() {};
        project3.setTitle("R&D5 Initiative");
        project3.setBudget(70000.0);
        project3.setDeadline(dateFormat.parse("2026-07-19"));
        departmentD005.addProject(project3);
        
        // Calculate total budget
        double totalBudget = companyC004.calculateTotalBudget();
        
        // Verify expected output: 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Create company C005
        Company companyC005 = new Company();
        
        // Create department D006 and add to company
        Department departmentD006 = new Department();
        departmentD006.setID("D006");
        departmentD006.setEmail("department5@company.com");
        companyC005.addDepartment(departmentD006);
        
        // Create community project "Community Health Awareness" with mixed funding group
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Health Awareness");
        communityProject.setBudget(40000.0);
        communityProject.setDeadline(dateFormat.parse("2027-02-28"));
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        communityProject.setFundingGroup(fundingGroup);
        
        departmentD006.addProject(communityProject);
        
        // Create project "Environmental Clean-up Initiative"
        Project project2 = new Project() {};
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudget(60000.0);
        project2.setDeadline(dateFormat.parse("2027-03-30"));
        departmentD006.addProject(project2);
        
        // Calculate total budget
        double totalBudget = companyC005.calculateTotalBudget();
        
        // Verify expected output: 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}