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
        Department department = new Department("D001", "department1@company.com");
        c001.addDepartment(department);
        
        // Create project 1: "Website Redevelopment"
        Date deadline1 = dateFormat.parse("2025-12-31 23:59:59");
        Project project1 = new ResearchProject("Website Redevelopment", 
                                             "Redesigning the company website", 
                                             10000.0, deadline1);
        department.addProject(project1);
        
        // Create project 2: "Mobile App Development"
        Date deadline2 = dateFormat.parse("2026-01-15 23:59:59");
        Project project2 = new ResearchProject("Mobile App Development", 
                                             "Developing a customer service app", 
                                             15000.0, deadline2);
        department.addProject(project2);
        
        // Calculate the total budget for company C001
        double totalBudget = c001.calculateTotalBudget();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Create a company C002
        Company c002 = new Company();
        
        // Create Department D001 with email: department1@company.com
        Department department1 = new Department("D001", "department1@company.com");
        c002.addDepartment(department1);
        
        // Create a project in D001: "HR Software"
        Date deadline1 = dateFormat.parse("2025-12-31 23:59:59");
        Project project1 = new ResearchProject("HR Software", 
                                             "HR management software development", 
                                             20000.0, deadline1);
        department1.addProject(project1);
        
        // Create Department D002 with email: department2@company.com
        Department department2 = new Department("D002", "department2@company.com");
        c002.addDepartment(department2);
        
        // Create project 1 in D002: "Sales Training Program"
        Date deadline2 = dateFormat.parse("2026-01-15 23:59:59");
        Project project2 = new ResearchProject("Sales Training Program", 
                                             "Sales team training program", 
                                             30000.0, deadline2);
        department2.addProject(project2);
        
        // Create project 2 in D002: "Marketing Campaign"
        Date deadline3 = dateFormat.parse("2026-02-28 23:59:59");
        Project project3 = new ResearchProject("Marketing Campaign", 
                                             "Quarterly marketing campaign", 
                                             25000.0, deadline3);
        department2.addProject(project3);
        
        // Calculate the total budget for company C002
        double totalBudget = c002.calculateTotalBudget();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create a company C003
        Company c003 = new Company();
        
        // Create Department with ID: D003 and email: department3@company.com
        Department department = new Department("D003", "department3@company.com");
        c003.addDepartment(department);
        
        // Calculate the total budget for company C003
        double totalBudget = c003.calculateTotalBudget();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Create a company C004
        Company c004 = new Company();
        
        // Create department with ID: D004 and email: department4@company.com
        Department department1 = new Department("D004", "department4@company.com");
        c004.addDepartment(department1);
        
        // Create an education project: "Scholarship Program" with government funding group
        Date deadline1 = dateFormat.parse("2026-05-31 23:59:59");
        FundingGroup govGroup = new FundingGroup("Government Education Fund", FundingGroupType.GOVERNMENT);
        Project project1 = new EducationProject("Scholarship Program", 
                                              "Student scholarship program", 
                                              50000.0, deadline1, govGroup);
        department1.addProject(project1);
        
        // Create another project: "R&D Initiative"
        Date deadline2 = dateFormat.parse("2026-07-15 23:59:59");
        Project project2 = new ResearchProject("R&D Initiative", 
                                             "Research and development initiative", 
                                             70000.0, deadline2);
        department1.addProject(project2);
        
        // Create department with ID: D005 and email: department5@company.com
        Department department2 = new Department("D005", "department5@company.com");
        c004.addDepartment(department2);
        
        // Create project: "R&D5 Initiative"
        Date deadline3 = dateFormat.parse("2026-07-19 23:59:59");
        Project project3 = new ResearchProject("R&D5 Initiative", 
                                             "Department 5 R&D initiative", 
                                             70000.0, deadline3);
        department2.addProject(project3);
        
        // Calculate the total budget for company C004
        double totalBudget = c004.calculateTotalBudget();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Create a company C005
        Company c005 = new Company();
        
        // Create department with ID: D006 and email: department5@company.com
        Department department = new Department("D006", "department5@company.com");
        c005.addDepartment(department);
        
        // Create a community project: "Community Health Awareness" with mixed funding group
        Date deadline1 = dateFormat.parse("2027-02-28 23:59:59");
        FundingGroup mixedGroup = new FundingGroup("Community Health Partnership", FundingGroupType.MIXED);
        Project project1 = new CommunityProject("Community Health Awareness", 
                                              "Public health awareness campaign", 
                                              40000.0, deadline1, mixedGroup);
        department.addProject(project1);
        
        // Create project: "Environmental Clean-up Initiative"
        Date deadline2 = dateFormat.parse("2027-03-30 23:59:59");
        Project project2 = new ResearchProject("Environmental Clean-up Initiative", 
                                             "Community environmental cleanup project", 
                                             60000.0, deadline2);
        department.addProject(project2);
        
        // Calculate the total budget for company C005
        double totalBudget = c005.calculateTotalBudget();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}