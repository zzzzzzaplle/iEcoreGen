import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Department department1;
    private Department department2;
    private Department department3;
    private Department department4;
    private Department department5;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws ParseException {
        // SetUp: Create department D001
        department1 = new Department();
        department1.setID("D001");
        department1.setEmail("department1@example.com");
        
        // SetUp: Create community project with funding group
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        communityProject.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        communityProject.setFundingGroup(fundingGroup);
        
        department1.addProject(communityProject);
        
        // Input: Retrieve funding group types for department D001
        List<FundingGroupType> result = department1.getFundingGroupTypeCommunityProjects();
        
        // Expected Output: There is 1 Funding group type: "Government Group"
        assertEquals(1, result.size());
        assertEquals(FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws ParseException {
        // SetUp: Create department D002
        department2 = new Department();
        department2.setID("D002");
        department2.setEmail("department2@example.com");
        
        // SetUp: Create first community project with private funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15 00:00:00"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // SetUp: Create second community project with mixed funding group
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20 00:00:00"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        department2.addProject(project1);
        department2.addProject(project2);
        
        // Input: Retrieve funding group types for department D002
        List<FundingGroupType> result = department2.getFundingGroupTypeCommunityProjects();
        
        // Expected Output: There are 2 Funding group type: "Private Group", "Mixed Group"
        assertEquals(2, result.size());
        assertTrue(result.contains(FundingGroupType.PRIVATE));
        assertTrue(result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department3 = new Department();
        department3.setID("D003");
        department3.setEmail("department3@example.com");
        
        // Input: Retrieve funding group types for department D003
        List<FundingGroupType> result = department3.getFundingGroupTypeCommunityProjects();
        
        // Expected Output: There is 0 Funding group type
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws ParseException {
        // SetUp: Create department D004
        department4 = new Department();
        department4.setID("D004");
        department4.setEmail("department4@example.com");
        
        // SetUp: Create community project in D004 with mixed funding group
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudget(7500.0);
        project4.setDeadline(dateFormat.parse("2025-12-01 00:00:00"));
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setType(FundingGroupType.MIXED);
        project4.setFundingGroup(fundingGroup4);
        
        department4.addProject(project4);
        
        // SetUp: Create department D005 with different community project
        department5 = new Department();
        department5.setID("D005");
        department5.setEmail("department5@example.com");
        
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setType(FundingGroupType.GOVERNMENT);
        project5.setFundingGroup(fundingGroup5);
        
        department5.addProject(project5);
        
        // Input: Retrieve funding group types for department D004
        List<FundingGroupType> result4 = department4.getFundingGroupTypeCommunityProjects();
        
        // Input: Retrieve funding group types for department D005
        List<FundingGroupType> result5 = department5.getFundingGroupTypeCommunityProjects();
        
        // Expected Output: There is 1 Funding group type in D004 : "Mixed Group"
        assertEquals(1, result4.size());
        assertEquals(FundingGroupType.MIXED, result4.get(0));
        
        // Expected Output: There is 1 Funding group type in D005 : "Government Group"
        assertEquals(1, result5.size());
        assertEquals(FundingGroupType.GOVERNMENT, result5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department exists with ID D999 (nothing to set up)
        
        // Input: Attempt to retrieve funding group types for non-existent department D999
        // Since we can't retrieve a non-existent department, we test by creating a new department
        // and verifying it has no community projects by default
        Department nonExistentDepartment = new Department();
        nonExistentDepartment.setID("D999");
        
        List<FundingGroupType> result = nonExistentDepartment.getFundingGroupTypeCommunityProjects();
        
        // Expected Output: There is 0 Funding group type
        assertEquals(0, result.size());
    }
}