import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private Department department;
    
    @Before
    public void setUp() {
        department = new Department();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // SetUp: Create department D001
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudgetAmount(5000.0);
        project.setDeadline("2025-12-31");
        
        // Assign Government Group funding
        GovernmentGroup fundingGroup = new GovernmentGroup();
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(project);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output: There is 1 Funding group type: "Government Group"
        assertEquals(1, fundingGroupTypes.size());
        assertEquals("Government", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department D002
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with Private Group funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        PrivateGroup fundingGroup1 = new PrivateGroup();
        project1.setFundingGroup(fundingGroup1);
        department.addProject(project1);
        
        // Create second community project with Mixed Group funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        MixedGroup fundingGroup2 = new MixedGroup();
        project2.setFundingGroup(fundingGroup2);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output: There are 2 Funding group type: "Private Group", "Mixed Group"
        assertEquals(2, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.contains("Private"));
        assertTrue(fundingGroupTypes.contains("Mixed"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create department D004
        Department department4 = new Department();
        department4.setId("D004");
        department4.setEmail("department4@example.com");
        
        // Create community project for D004 with Mixed Group funding
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudgetAmount(7500.0);
        project4.setDeadline("2025-12-01");
        MixedGroup fundingGroup4 = new MixedGroup();
        project4.setFundingGroup(fundingGroup4);
        department4.addProject(project4);
        
        // Create department D005
        Department department5 = new Department();
        department5.setId("D005");
        department5.setEmail("department5@example.com");
        
        // Create community project for D005 with Government Group funding
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improving local library facilities");
        project5.setBudgetAmount(6000.0);
        project5.setDeadline("2025-11-30");
        GovernmentGroup fundingGroup5 = new GovernmentGroup();
        project5.setFundingGroup(fundingGroup5);
        department5.addProject(project5);
        
        // Retrieve funding group types for both departments
        List<String> fundingGroupTypes4 = department4.getFundingGroupTypesOfCommunityProjects();
        List<String> fundingGroupTypes5 = department5.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output: 
        // There is 1 Funding group type in D004: "Mixed Group"
        assertEquals(1, fundingGroupTypes4.size());
        assertEquals("Mixed", fundingGroupTypes4.get(0));
        
        // There is 1 Funding group type in D005: "Government Group"
        assertEquals(1, fundingGroupTypes5.size());
        assertEquals("Government", fundingGroupTypes5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department exists with ID D999, so create a new empty department
        Department invalidDepartment = new Department();
        invalidDepartment.setId("D999");
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = invalidDepartment.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
    }
}