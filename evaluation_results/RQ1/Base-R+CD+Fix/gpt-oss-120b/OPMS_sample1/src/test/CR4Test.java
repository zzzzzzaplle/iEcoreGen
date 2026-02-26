import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR4Test {
    
    private Department department;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // SetUp: Create department D001
        Department dept = new Department();
        dept.setID("D001");
        dept.setEmail("department1@example.com");
        
        // SetUp: Create community project with funding group
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        project.setDeadline(dateFormat.parse("2025-12-31"));
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        fundingGroup.setName("Government Group");
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        dept.addProject(project);
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 1 Funding group type: "Government Group"
        assertEquals(1, result.size());
        assertEquals(FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // SetUp: Create department D002
        Department dept = new Department();
        dept.setID("D002");
        dept.setEmail("department2@example.com");
        
        // SetUp: Create first community project with private funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        fundingGroup1.setName("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // SetUp: Create second community project with mixed funding group
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        fundingGroup2.setName("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        dept.addProject(project1);
        dept.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 2 Funding group types: "Private Group", "Mixed Group"
        assertEquals(2, result.size());
        assertTrue(result.contains(FundingGroupType.PRIVATE));
        assertTrue(result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() throws Exception {
        // SetUp: Create department D003 with no community projects
        Department dept = new Department();
        dept.setID("D003");
        dept.setEmail("department3@example.com");
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 0 Funding group types
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // SetUp: Create department D004
        Department dept4 = new Department();
        dept4.setID("D004");
        dept4.setEmail("department4@example.com");
        
        // SetUp: Create community project for D004 with mixed funding group
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudget(7500.0);
        project4.setDeadline(dateFormat.parse("2025-12-01"));
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setType(FundingGroupType.MIXED);
        fundingGroup4.setName("Mixed Group");
        project4.setFundingGroup(fundingGroup4);
        dept4.addProject(project4);
        
        // SetUp: Create department D005 with different community project
        Department dept5 = new Department();
        dept5.setID("D005");
        dept5.setEmail("department5@example.com");
        
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improve library facilities");
        project5.setBudget(6000.0);
        project5.setDeadline(dateFormat.parse("2025-11-30"));
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setType(FundingGroupType.GOVERNMENT);
        fundingGroup5.setName("Government Group");
        project5.setFundingGroup(fundingGroup5);
        dept5.addProject(project5);
        
        // Retrieve funding group types for each department
        List<FundingGroupType> result4 = dept4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> result5 = dept5.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: D004 has 1 Funding group type: "Mixed Group"
        assertEquals(1, result4.size());
        assertEquals(FundingGroupType.MIXED, result4.get(0));
        
        // Verify expected output: D005 has 1 Funding group type: "Government Group"
        assertEquals(1, result5.size());
        assertEquals(FundingGroupType.GOVERNMENT, result5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() throws Exception {
        // SetUp: No department exists with ID D999, so create a new empty department
        Department dept = new Department();
        dept.setID("D999");
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 0 Funding group types
        assertEquals(0, result.size());
    }
}