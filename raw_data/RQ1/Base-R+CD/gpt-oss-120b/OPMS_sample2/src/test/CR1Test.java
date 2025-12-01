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
        // Create a company C001
        Company c001 = new Company();
        
        // Create a department with ID: D001 and email: department1@company.com, and add the department to the company C001
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@company.com");
        c001.addDepartment(dept1);
        
        // Create a project titled "Website Redevelopment" with description "Redesigning the company website", 
        // budget amount: 10000 CNY, and deadline: 2025-12-31. The project is associated with the department D001.
        Project project1 = new ResearchProject(); // Using ResearchProject as base project type
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        dept1.addProject(project1);
        
        // Create another project titled "Mobile App Development" with description "Developing a customer service app", 
        // budget amount: 15000 CNY, and deadline: 2026-01-15. The project is associated with the department D001.
        Project project2 = new ResearchProject(); // Using ResearchProject as base project type
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        project2.setDeadline(dateFormat.parse("2026-01-15 00:00:00"));
        dept1.addProject(project2);
        
        // Calculate the total budget for company C001
        double totalBudget = c001.calculateTotalBudget();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Create a company C002
        Company c002 = new Company();
        
        // Create Department D001 with email: department1@company.com, and add the department to the company C002
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@company.com");
        c002.addDepartment(dept1);
        
        // Create a project in D001 titled "HR Software" with budget amount: 20000 CNY
        Project project1 = new ResearchProject();
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        dept1.addProject(project1);
        
        // Create Department D002 with email: department2@company.com
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@company.com");
        c002.addDepartment(dept2);
        
        // Create a project in D002 titled "Sales Training Program" with budget amount: 30000 CNY
        Project project2 = new ResearchProject();
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        dept2.addProject(project2);
        
        // Create another project in D002 titled "Marketing Campaign" with budget amount: 25000 CNY
        Project project3 = new ResearchProject();
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000.0);
        dept2.addProject(project3);
        
        // Calculate the total budget for company C002
        double totalBudget = c002.calculateTotalBudget();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create a company C003
        Company c003 = new Company();
        
        // Create Department with ID: D003 and email: department3@company.com, and add the department to the company C003
        Department dept = new Department();
        dept.setID("D003");
        dept.setEmail("department3@company.com");
        c003.addDepartment(dept);
        
        // Calculate the total budget for company C003
        double totalBudget = c003.calculateTotalBudget();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Create a company C004
        Company c004 = new Company();
        
        // Create a department with ID: D004 and email: department4@company.com, and add the department to the company C004
        Department dept1 = new Department();
        dept1.setID("D004");
        dept1.setEmail("department4@company.com");
        c004.addDepartment(dept1);
        
        // Create an education project titled "Scholarship Program" with budget amount: 50000 CNY, 
        // deadline: 2026-05-31, and funding group type: government group
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2026-05-31 00:00:00"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.GOVERNMENT);
        project1.setFundingGroup(fundingGroup1);
        
        dept1.addProject(project1);
        
        // Create another project titled "R&D Initiative" with budget amount: 70000 CNY and deadline: 2026-07-15
        Project project2 = new ResearchProject();
        project2.setTitle("R&D Initiative");
        project2.setBudget(70000.0);
        project2.setDeadline(dateFormat.parse("2026-07-15 00:00:00"));
        dept1.addProject(project2);
        
        // Create a department with ID: D005 and email: department5@company.com, and add the department to the company C004
        Department dept2 = new Department();
        dept2.setID("D005");
        dept2.setEmail("department5@company.com");
        c004.addDepartment(dept2);
        
        // Create another project titled "R&D5 Initiative" with budget amount: 70000 CNY and deadline: 2026-07-19
        Project project3 = new ResearchProject();
        project3.setTitle("R&D5 Initiative");
        project3.setBudget(70000.0);
        project3.setDeadline(dateFormat.parse("2026-07-19 00:00:00"));
        dept2.addProject(project3);
        
        // Calculate the total budget for company C004
        double totalBudget = c004.calculateTotalBudget();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Create a company C005
        Company c005 = new Company();
        
        // Create a department with ID: D006 and email: department5@company.com, and add the department to the company C005
        Department dept = new Department();
        dept.setID("D006");
        dept.setEmail("department5@company.com");
        c005.addDepartment(dept);
        
        // Create a community project titled "Community Health Awareness" with budget amount: 40000 CNY, 
        // deadline: 2027-02-28, and funding group type: mixed group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudget(40000.0);
        project1.setDeadline(dateFormat.parse("2027-02-28 00:00:00"));
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        project1.setFundingGroup(fundingGroup);
        
        dept.addProject(project1);
        
        // Create a project titled "Environmental Clean-up Initiative" with budget amount: 60000 CNY and deadline: 2027-03-30
        Project project2 = new ResearchProject();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudget(60000.0);
        project2.setDeadline(dateFormat.parse("2027-03-30 00:00:00"));
        dept.addProject(project2);
        
        // Calculate the total budget for company C005
        double totalBudget = c005.calculateTotalBudget();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}