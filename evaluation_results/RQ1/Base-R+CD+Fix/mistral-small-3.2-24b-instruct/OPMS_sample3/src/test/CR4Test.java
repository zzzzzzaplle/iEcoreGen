import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR4Test {
    
    private Department department1;
    private Department department2;
    private Department department3;
    private Department department4;
    private Department department5;
    
    @Before
    public void setUp() throws Exception {
        // Initialize departments that will be used across multiple tests
        department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        
        department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        
        department3 = new Department();
        department3.setID("D003");
        department3.setEmail("department3@example.com");
        
        department4 = new Department();
        department4.setID("D004");
        department4.setEmail("department4@example.com");
        
        department5 = new Department();
        department5.setID("D005");
        department5.setEmail("department5@example.com");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // Set up: Create department D001 with one community project having Government Group funding
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        communityProject.setDeadline(dateFormat.parse("2025-12-31"));
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setName("Government Group");
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        communityProject.setFundingGroup(fundingGroup);
        
        department1.addProject(communityProject);
        
        // Execute: Retrieve funding group types for community projects in department D001
        List<FundingGroupType> result = department1.getFundingGroupTypeCommunityProjects();
        
        // Verify: There is 1 Funding group type: "Government Group"
        assertEquals(1, result.size());
        assertEquals(FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Set up: Create department D002 with two community projects having different funding types
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        project1.setDeadline(dateFormat.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setName("Private Group");
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setName("Mixed Group");
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        department2.addProject(project1);
        department2.addProject(project2);
        
        // Execute: Retrieve funding group types for community projects in department D002
        List<FundingGroupType> result = department2.getFundingGroupTypeCommunityProjects();
        
        // Verify: There are 2 Funding group types: "Private Group", "Mixed Group"
        assertEquals(2, result.size());
        assertTrue(result.contains(FundingGroupType.PRIVATE));
        assertTrue(result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Set up: Department D003 has no projects (already initialized in setUp)
        
        // Execute: Retrieve funding group types for community projects in department D003
        List<FundingGroupType> result = department3.getFundingGroupTypeCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Set up: Create department D004 with one community project having Mixed Group funding
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudget(7500.0);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        project4.setDeadline(dateFormat.parse("2025-12-01"));
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setName("Mixed Group");
        fundingGroup4.setType(FundingGroupType.MIXED);
        project4.setFundingGroup(fundingGroup4);
        
        department4.addProject(project4);
        
        // Set up: Create department D005 with one community project having Government Group funding
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improving library facilities");
        project5.setBudget(6000.0);
        project5.setDeadline(dateFormat.parse("2025-11-30"));
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setName("Government Group");
        fundingGroup5.setType(FundingGroupType.GOVERNMENT);
        project5.setFundingGroup(fundingGroup5);
        
        department5.addProject(project5);
        
        // Execute: Retrieve funding group types for community projects in both departments
        List<FundingGroupType> resultD004 = department4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> resultD005 = department5.getFundingGroupTypeCommunityProjects();
        
        // Verify: There is 1 Funding group type in D004: "Mixed Group"
        assertEquals(1, resultD004.size());
        assertEquals(FundingGroupType.MIXED, resultD004.get(0));
        
        // Verify: There is 1 Funding group type in D005: "Government Group"
        assertEquals(1, resultD005.size());
        assertEquals(FundingGroupType.GOVERNMENT, resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Set up: No department exists with ID D999 (we'll create a new empty department to simulate this)
        Department invalidDepartment = new Department();
        invalidDepartment.setID("D999");
        
        // Execute: Retrieve funding group types for community projects in non-existent department
        List<FundingGroupType> result = invalidDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals(0, result.size());
    }
}