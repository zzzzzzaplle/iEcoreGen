import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {

    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }

    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() {
        // Create company C001
        Company c001 = new Company();
        
        // Create department D001 and add to company
        Department d001 = new Department();
        d001.setId("D001");
        d001.setEmail("department1@company.com");
        c001.addDepartment(d001);
        
        // Create project 1: Website Redevelopment
        Project project1 = new Project() {};
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudgetAmount(10000.0);
        project1.setDeadline(LocalDate.of(2025, 12, 31));
        d001.addProject(project1);
        
        // Create project 2: Mobile App Development
        Project project2 = new Project() {};
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudgetAmount(15000.0);
        project2.setDeadline(LocalDate.of(2026, 1, 15));
        d001.addProject(project2);
        
        // Calculate total budget
        double totalBudget = c001.getTotalBudget();
        
        // Expected: 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() {
        // Create company C002
        Company c002 = new Company();
        
        // Create department D001 and add to company
        Department d001 = new Department();
        d001.setId("D001");
        d001.setEmail("department1@company.com");
        c002.addDepartment(d001);
        
        // Create project in D001: HR Software
        Project project1 = new Project() {};
        project1.setTitle("HR Software");
        project1.setBudgetAmount(20000.0);
        d001.addProject(project1);
        
        // Create department D002 and add to company
        Department d002 = new Department();
        d002.setId("D002");
        d002.setEmail("department2@company.com");
        c002.addDepartment(d002);
        
        // Create project 1 in D002: Sales Training Program
        Project project2 = new Project() {};
        project2.setTitle("Sales Training Program");
        project2.setBudgetAmount(30000.0);
        d002.addProject(project2);
        
        // Create project 2 in D002: Marketing Campaign
        Project project3 = new Project() {};
        project3.setTitle("Marketing Campaign");
        project3.setBudgetAmount(25000.0);
        d002.addProject(project3);
        
        // Calculate total budget
        double totalBudget = c002.getTotalBudget();
        
        // Expected: 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create company C003
        Company c003 = new Company();
        
        // Create department D003 and add to company
        Department d003 = new Department();
        d003.setId("D003");
        d003.setEmail("department3@company.com");
        c003.addDepartment(d003);
        
        // Calculate total budget (no projects added)
        double totalBudget = c003.getTotalBudget();
        
        // Expected: 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }

    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() {
        // Create company C004
        Company c004 = new Company();
        
        // Create department D004 and add to company
        Department d004 = new Department();
        d004.setId("D004");
        d004.setEmail("department4@company.com");
        c004.addDepartment(d004);
        
        // Create education project: Scholarship Program with government funding group
        EducationProject project1 = new EducationProject();
        project1.setTitle("Scholarship Program");
        project1.setBudgetAmount(50000.0);
        project1.setDeadline(LocalDate.of(2026, 5, 31));
        
        GovernmentGroup fundingGroup1 = new GovernmentGroup();
        project1.setFundingGroup(fundingGroup1);
        d004.addProject(project1);
        
        // Create another project: R&D Initiative
        Project project2 = new Project() {};
        project2.setTitle("R&D Initiative");
        project2.setBudgetAmount(70000.0);
        project2.setDeadline(LocalDate.of(2026, 7, 15));
        d004.addProject(project2);
        
        // Create department D005 and add to company
        Department d005 = new Department();
        d005.setId("D005");
        d005.setEmail("department5@company.com");
        c004.addDepartment(d005);
        
        // Create project in D005: R&D5 Initiative
        Project project3 = new Project() {};
        project3.setTitle("R&D5 Initiative");
        project3.setBudgetAmount(70000.0);
        project3.setDeadline(LocalDate.of(2026, 7, 19));
        d005.addProject(project3);
        
        // Calculate total budget
        double totalBudget = c004.getTotalBudget();
        
        // Expected: 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }

    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() {
        // Create company C005
        Company c005 = new Company();
        
        // Create department D006 and add to company
        Department d006 = new Department();
        d006.setId("D006");
        d006.setEmail("department5@company.com");
        c005.addDepartment(d006);
        
        // Create community project: Community Health Awareness with mixed funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Community Health Awareness");
        project1.setBudgetAmount(40000.0);
        project1.setDeadline(LocalDate.of(2027, 2, 28));
        
        MixedGroup fundingGroup1 = new MixedGroup();
        project1.setFundingGroup(fundingGroup1);
        d006.addProject(project1);
        
        // Create another project: Environmental Clean-up Initiative
        Project project2 = new Project() {};
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudgetAmount(60000.0);
        project2.setDeadline(LocalDate.of(2027, 3, 30));
        d006.addProject(project2);
        
        // Calculate total budget
        double totalBudget = c005.getTotalBudget();
        
        // Expected: 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}