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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() throws Exception {
        // Create company C001
        Company company = new Company();
        
        // Create department D001
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@company.com");
        company.addDepartment(department);
        
        // Create first project "Website Redevelopment"
        Project project1 = new ProductionProject();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31"));
        department.addProject(project1);
        
        // Create second project "Mobile App Development"
        Project project2 = new ProductionProject();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        project2.setDeadline(dateFormat.parse("2026-01-15"));
        department.addProject(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Create company C002
        Company company = new Company();
        
        // Create department D001
        Department department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@company.com");
        company.addDepartment(department1);
        
        // Create project "HR Software" in D001
        Project project1 = new ProductionProject();
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        department1.addProject(project1);
        
        // Create department D002
        Department department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@company.com");
        company.addDepartment(department2);
        
        // Create project "Sales Training Program" in D002
        Project project2 = new ProductionProject();
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        department2.addProject(project2);
        
        // Create project "Marketing Campaign" in D002
        Project project3 = new ProductionProject();
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
        // Create company C003
        Company company = new Company();
        
        // Create department D003 with no projects
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@company.com");
        company.addDepartment(department);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Create company C004
        Company company = new Company();
        
        // Create department D004
        Department department1 = new Department();
        department1.setID("D004");
        department1.setEmail("department4@company.com");
        company.addDepartment(department1);
        
        // Create education project "Scholarship Program" with government funding
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudget(50000.0);
        project1.setDeadline(dateFormat.parse("2026-05-31"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.GOVERNMENT);
        project1.setFundingGroup(fundingGroup1);
        department1.addProject(project1);
        
        // Create another project "R&D Initiative"
        Project project2 = new ProductionProject();
        project2.setTitle("R&D Initiative");
        project2.setBudget(70000.0);
        project2.setDeadline(dateFormat.parse("2026-07-15"));
        department1.addProject(project2);
        
        // Create department D005
        Department department2 = new Department();
        department2.setID("D005");
        department2.setEmail("department5@company.com");
        company.addDepartment(department2);
        
        // Create project "R&D5 Initiative" in D005
        Project project3 = new ProductionProject();
        project3.setTitle("R&D5 Initiative");
        project3.setBudget(70000.0);
        project3.setDeadline(dateFormat.parse("2026-07-19"));
        department2.addProject(project3);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Create company C005
        Company company = new Company();
        
        // Create department D006
        Department department = new Department();
        department.setID("D006");
        department.setEmail("department5@company.com");
        company.addDepartment(department);
        
        // Create community project "Community Health Awareness" with mixed funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudget(40000.0);
        project1.setDeadline(dateFormat.parse("2027-02-28"));
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        project1.setFundingGroup(fundingGroup);
        department.addProject(project1);
        
        // Create project "Environmental Clean-up Initiative"
        Project project2 = new ProductionProject();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudget(60000.0);
        project2.setDeadline(dateFormat.parse("2027-03-30"));
        department.addProject(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(100000.0, totalBudget, 0.001);
    }
}