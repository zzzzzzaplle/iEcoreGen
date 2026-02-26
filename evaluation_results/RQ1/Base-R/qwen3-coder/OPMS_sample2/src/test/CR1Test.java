import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private Company company;
    private Department department;
    private Project project1;
    private Project project2;
    private EducationProject educationProject;
    private CommunityProject communityProject;
    private FundingGroup fundingGroup;

    @Before
    public void setUp() {
        company = new Company();
        department = new Department();
    }

    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() {
        // SetUp Step 1: Create a company C001
        Company c001 = new Company();
        
        // SetUp Step 2: Create a department with ID: D001 and email: department1@company.com, 
        // and add the department to the company C001
        Department d001 = new Department();
        d001.setId("D001");
        d001.setEmail("department1@company.com");
        c001.getDepartments().add(d001);
        
        // SetUp Step 3: Create a project titled "Website Redevelopment" with description 
        // "Redesigning the company website", budget amount: 10000 CNY, and deadline: 2025-12-31.
        // The project is associated with the department D001.
        Project websiteProject = new ResearchProject(); // Using ResearchProject as concrete implementation
        websiteProject.setTitle("Website Redevelopment");
        websiteProject.setDescription("Redesigning the company website");
        websiteProject.setBudgetAmount(10000.0);
        websiteProject.setDeadline("2025-12-31");
        d001.getProjects().add(websiteProject);
        
        // SetUp Step 4: Create another project titled "Mobile App Development" with description 
        // "Developing a customer service app", budget amount: 15000 CNY, and deadline: 2026-01-15.
        // The project is associated with the department D001.
        Project mobileAppProject = new ResearchProject(); // Using ResearchProject as concrete implementation
        mobileAppProject.setTitle("Mobile App Development");
        mobileAppProject.setDescription("Developing a customer service app");
        mobileAppProject.setBudgetAmount(15000.0);
        mobileAppProject.setDeadline("2026-01-15");
        d001.getProjects().add(mobileAppProject);
        
        // SetUp Step 5: Calculate the total budget for company C001
        double totalBudget = c001.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // SetUp Step 1: Create a company C002. Create Department D001 with email: department1@company.com,
        // and add the department to the company C002
        Company c002 = new Company();
        Department d001 = new Department();
        d001.setId("D001");
        d001.setEmail("department1@company.com");
        c002.getDepartments().add(d001);
        
        // SetUp Step 2: Create a project in D001 titled "HR Software" with budget amount: 20000 CNY.
        Project hrSoftwareProject = new ResearchProject(); // Using ResearchProject as concrete implementation
        hrSoftwareProject.setTitle("HR Software");
        hrSoftwareProject.setBudgetAmount(20000.0);
        d001.getProjects().add(hrSoftwareProject);
        
        // SetUp Step 3: Create Department D002 with email: department2@company.com.
        Department d002 = new Department();
        d002.setId("D002");
        d002.setEmail("department2@company.com");
        c002.getDepartments().add(d002);
        
        // SetUp Step 4: Create a project in D002 titled "Sales Training Program" with budget amount: 30000 CNY.
        // The project is associated with the department D002.
        Project salesTrainingProject = new ResearchProject(); // Using ResearchProject as concrete implementation
        salesTrainingProject.setTitle("Sales Training Program");
        salesTrainingProject.setBudgetAmount(30000.0);
        d002.getProjects().add(salesTrainingProject);
        
        // SetUp Step 5: Create another project in D002 titled "Marketing Campaign" with budget amount: 25000 CNY.
        // The project is associated with the department D002.
        Project marketingCampaignProject = new ResearchProject(); // Using ResearchProject as concrete implementation
        marketingCampaignProject.setTitle("Marketing Campaign");
        marketingCampaignProject.setBudgetAmount(25000.0);
        d002.getProjects().add(marketingCampaignProject);
        
        // SetUp Step 6: Calculate the total budget for company C002
        double totalBudget = c002.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // SetUp Step 1: Create a company C003. Create Department with ID: D003 and email: department3@company.com,
        // and add the department to the company C003
        Company c003 = new Company();
        Department d003 = new Department();
        d003.setId("D003");
        d003.setEmail("department3@company.com");
        c003.getDepartments().add(d003);
        
        // SetUp Step 2: Calculate the total budget for company C003
        double totalBudget = c003.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }

    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // SetUp Step 1: Create a company C004. Create a department with ID: D004 and email: department4@company.com,
        // and add the department to the company C004
        Company c004 = new Company();
        Department d004 = new Department();
        d004.setId("D004");
        d004.setEmail("department4@company.com");
        c004.getDepartments().add(d004);
        
        // SetUp Step 2: Create an education project titled "Scholarship Program" with budget amount: 50000 CNY,
        // deadline: 2026-05-31, and funding group type: government group. The project is associated with the department D004.
        EducationProject scholarshipProject = new EducationProject();
        scholarshipProject.setTitle("Scholarship Program");
        scholarshipProject.setBudgetAmount(50000.0);
        scholarshipProject.setDeadline("2026-05-31");
        FundingGroup govFundingGroup = new FundingGroup();
        govFundingGroup.setGroupType("government group");
        scholarshipProject.setFundingGroup(govFundingGroup);
        d004.getProjects().add(scholarshipProject);
        
        // SetUp Step 3: Create another project titled "R&D Initiative" with budget amount: 70000 CNY and deadline: 2026-07-15.
        // The project is associated with the department D004.
        Project rdInitiativeProject = new ResearchProject(); // Using ResearchProject as concrete implementation
        rdInitiativeProject.setTitle("R&D Initiative");
        rdInitiativeProject.setBudgetAmount(70000.0);
        rdInitiativeProject.setDeadline("2026-07-15");
        d004.getProjects().add(rdInitiativeProject);
        
        // SetUp Step 4: Create a department with ID: D005 and email: department5@company.com, and add the department to the company C004.
        Department d005 = new Department();
        d005.setId("D005");
        d005.setEmail("department5@company.com");
        c004.getDepartments().add(d005);
        
        // SetUp Step 5: Create another project titled "R&D5 Initiative" with budget amount: 70000 CNY and deadline: 2026-07-19.
        // The project is associated with the department D005.
        Project rd5InitiativeProject = new ResearchProject(); // Using ResearchProject as concrete implementation
        rd5InitiativeProject.setTitle("R&D5 Initiative");
        rd5InitiativeProject.setBudgetAmount(70000.0);
        rd5InitiativeProject.setDeadline("2026-07-19");
        d005.getProjects().add(rd5InitiativeProject);
        
        // SetUp Step 6: Calculate the total budget for company C004.
        double totalBudget = c004.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // SetUp Step 1: Create a company C005. Create a department with ID: D006 and email: department5@company.com,
        // and add the department to the company C005
        Company c005 = new Company();
        Department d006 = new Department();
        d006.setId("D006");
        d006.setEmail("department5@company.com");
        c005.getDepartments().add(d006);
        
        // SetUp Step 2: Create a community project titled "Community Health Awareness" with budget amount: 40000 CNY,
        // deadline: 2027-02-28, and funding group type: mixed group. The project is associated with the department D006.
        CommunityProject communityHealthProject = new CommunityProject();
        communityHealthProject.setTitle("Community Health Awareness");
        communityHealthProject.setBudgetAmount(40000.0);
        communityHealthProject.setDeadline("2027-02-28");
        FundingGroup mixedFundingGroup = new FundingGroup();
        mixedFundingGroup.setGroupType("mixed group");
        communityHealthProject.setFundingGroup(mixedFundingGroup);
        d006.getProjects().add(communityHealthProject);
        
        // SetUp Step 3: Create a project titled "Environmental Clean-up Initiative" with budget amount: 60000 CNY 
        // and deadline: 2027-03-30. The project is associated with the department D006.
        Project environmentalProject = new ResearchProject(); // Using ResearchProject as concrete implementation
        environmentalProject.setTitle("Environmental Clean-up Initiative");
        environmentalProject.setBudgetAmount(60000.0);
        environmentalProject.setDeadline("2027-03-30");
        d006.getProjects().add(environmentalProject);
        
        // SetUp Step 4: Calculate the total budget for company C005.
        double totalBudget = c005.getTotalBudgetOfAllProjects();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}