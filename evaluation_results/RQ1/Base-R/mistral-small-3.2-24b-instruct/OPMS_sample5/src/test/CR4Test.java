import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private Department department;
    private Company company;
    
    @Before
    public void setUp() {
        department = new Department();
        company = new Company();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // SetUp: Create department D001 with one community project
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudgetAmount(5000.0);
        project.setDeadline("2025-12-31");
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("Government Group");
        project.setFundingGroup(fundingGroup);
        
        department.addProject(project);
        
        // Execute: Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 1 Funding group type: "Government Group"
        assertEquals(1, fundingGroupTypes.size());
        assertEquals("Government Group", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department D002 with two community projects
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // First community project
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Second community project
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        department.addProject(project1);
        department.addProject(project2);
        
        // Execute: Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There are 2 Funding group types: "Private Group", "Mixed Group"
        assertEquals(2, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.contains("Private Group"));
        assertTrue(fundingGroupTypes.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Execute: Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create department D004 with one community project
        Department dept4 = new Department();
        dept4.setId("D004");
        dept4.setEmail("department4@example.com");
        
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudgetAmount(7500.0);
        project4.setDeadline("2025-12-01");
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setType("Mixed Group");
        project4.setFundingGroup(fundingGroup4);
        
        dept4.addProject(project4);
        
        // SetUp: Create department D005 with one community project (for isolation validation)
        Department dept5 = new Department();
        dept5.setId("D005");
        dept5.setEmail("department5@example.com");
        
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improve library facilities");
        project5.setBudgetAmount(10000.0);
        project5.setDeadline("2025-11-30");
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setType("Government Group");
        project5.setFundingGroup(fundingGroup5);
        
        dept5.addProject(project5);
        
        // Execute: Retrieve funding group types for both departments
        List<String> fundingGroupTypes4 = dept4.getFundingGroupTypesOfCommunityProjects();
        List<String> fundingGroupTypes5 = dept5.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 1 Funding group type in D004: "Mixed Group"
        assertEquals(1, fundingGroupTypes4.size());
        assertEquals("Mixed Group", fundingGroupTypes4.get(0));
        
        // Verify: There is 1 Funding group type in D005: "Government Group"
        assertEquals(1, fundingGroupTypes5.size());
        assertEquals("Government Group", fundingGroupTypes5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department exists with ID: D999
        // Create a new department with different ID to ensure isolation
        department.setId("D100");
        department.setEmail("department100@example.com");
        
        // Execute: Retrieve funding group types for non-existent department (by creating a new empty department)
        Department nonExistentDept = new Department();
        nonExistentDept.setId("D999");
        List<String> fundingGroupTypes = nonExistentDept.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
    }
}