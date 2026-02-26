import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_SingleDepartmentBudgetCalculation() throws Exception {
        // Create a company C001
        Company c001 = new Company();
        
        // Create a department with ID: D001 and email: department1@company.com
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@company.com");
        c001.addDepartment(department);
        
        // Create project 1: "Website Redevelopment"
        Project project1 = new Project();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000);
        project1.setDeadline(dateFormat.parse("2025-12-31 23:59:59"));
        department.addProject(project1);
        
        // Create project 2: "Mobile App Development"
        Project project2 = new Project();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000);
        project2.setDeadline(dateFormat.parse("2026-01-15 23:59:59"));
        department.addProject(project2);
        
        // Calculate total budget
        double totalBudget = c001.calculateTotalBudget();
        
        // Expected: 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Create a company C002
        Company c002 = new Company();
        
        // Create Department D001 with email: department1@company.com
        Department dept1 = new Department();
        dept1.setID("D001");
        dept1.setEmail("department1@company.com");
        c002.addDepartment(dept1);
        
        // Create project in D001: "HR Software"
        Project project1 = new Project();
        project1.setTitle("HR Software");
        project1.setBudget(20000);
        dept1.addProject(project1);
        
        // Create Department D002 with email: department2@company.com
        Department dept2 = new Department();
        dept2.setID("D002");
        dept2.setEmail("department2@company.com");
        c002.addDepartment(dept2);
        
        // Create project 1 in D002: "Sales Training Program"
        Project project2 = new Project();
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000);
        dept2.addProject(project2);
        
        // Create project 2 in D002: "Marketing Campaign"
        Project project3 = new Project();
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000);
        dept2.addProject(project3);
        
        // Calculate total budget
        double totalBudget = c002.calculateTotalBudget();
        
        // Expected: 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create a company C003
        Company c003 = new Company();
        
        // Create Department with ID: D003 and email: department3@company.com
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@company.com");
        c003.addDepartment(department);
        
        // Calculate total budget (department has no projects)
        double totalBudget = c003.calculateTotalBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Create a company C004
        Company c004 = new Company();
        
        // Create department D004
        Department dept4 = new Department();
        dept4.setID("D004");
        dept4.setEmail("department4@company.com");
        c004.addDepartment(dept4);
        
        // Create education project: "Scholarship Program"
        EducationProject eduProject = new EducationProject();
        eduProject.setTitle("Scholarship Program");
        eduProject.setBudget(50000);
        eduProject.setDeadline(dateFormat.parse("2026-05-31 23:59:59"));
        
        // Create funding group for education project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        eduProject.setFundingGroup(fundingGroup);
        dept4.addProject(eduProject);
        
        // Create another project: "R&D Initiative"
        Project project2 = new Project();
        project2.setTitle("R&D Initiative");
        project2.setBudget(70000);
        project2.setDeadline(dateFormat.parse("2026-07-15 23:59:59"));
        dept4.addProject(project2);
        
        // Create department D005
        Department dept5 = new Department();
        dept5.setID("D005");
        dept5.setEmail("department5@company.com");
        c004.addDepartment(dept5);
        
        // Create project in D005: "R&D5 Initiative"
        Project project3 = new Project();
        project3.setTitle("R&D5 Initiative");
        project3.setBudget(70000);
        project3.setDeadline(dateFormat.parse("2026-07-19 23:59:59"));
        dept5.addProject(project3);
        
        // Calculate total budget
        double totalBudget = c004.calculateTotalBudget();
        
        // Expected: 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Create a company C005
        Company c005 = new Company();
        
        // Create department D006
        Department department = new Department();
        department.setID("D006");
        department.setEmail("department5@company.com");
        c005.addDepartment(department);
        
        // Create community project: "Community Health Awareness"
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Health Awareness");
        communityProject.setBudget(40000);
        communityProject.setDeadline(dateFormat.parse("2027-02-28 23:59:59"));
        
        // Create funding group for community project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        communityProject.setFundingGroup(fundingGroup);
        department.addProject(communityProject);
        
        // Create another project: "Environmental Clean-up Initiative"
        Project project2 = new Project();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudget(60000);
        project2.setDeadline(dateFormat.parse("2027-03-30 23:59:59"));
        department.addProject(project2);
        
        // Calculate total budget
        double totalBudget = c005.calculateTotalBudget();
        
        // Expected: 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}