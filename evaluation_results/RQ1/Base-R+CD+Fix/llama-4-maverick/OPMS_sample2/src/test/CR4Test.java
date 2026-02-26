import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Department department;
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // SetUp: Create department D001 with email
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // SetUp: Create community project with details
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        communityProject.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        
        // SetUp: Create funding group with type GOVERNMENT
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setName("Government Group");
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(communityProject);
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 1 Funding group type "Government Group"
        assertEquals(1, result.size());
        assertEquals(FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // SetUp: Create department D002 with email
        department = new Department();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        // SetUp: Create first community project with PRIVATE funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15 00:00:00"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setName("Private Group");
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // SetUp: Create second community project with MIXED funding group
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20 00:00:00"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setName("Mixed Group");
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 2 Funding group types "Private Group", "Mixed Group"
        assertEquals(2, result.size());
        assertTrue(result.contains(FundingGroupType.PRIVATE));
        assertTrue(result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with email and no community projects
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 0 Funding group types
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // SetUp: Create department D004
        Department departmentD004 = new Department();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@example.com");
        
        // SetUp: Create community project for D004 with MIXED funding group
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        projectD004.setDeadline(dateFormat.parse("2025-12-01 00:00:00"));
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setName("Mixed Group");
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // SetUp: Create different department D005
        Department departmentD005 = new Department();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@example.com");
        
        // SetUp: Create community project for D005 with GOVERNMENT funding group
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setName("Government Group");
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Retrieve funding group types for both departments
        List<FundingGroupType> resultD004 = departmentD004.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> resultD005 = departmentD005.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: D004 has 1 "Mixed Group", D005 has 1 "Government Group"
        assertEquals(1, resultD004.size());
        assertEquals(FundingGroupType.MIXED, resultD004.get(0));
        
        assertEquals(1, resultD005.size());
        assertEquals(FundingGroupType.GOVERNMENT, resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: Assume no department exists with ID D999
        // Create a new department object but don't add it to any company structure
        Department departmentD999 = new Department();
        departmentD999.setID("D999");
        
        // Retrieve funding group types - should be empty since no projects added
        List<FundingGroupType> result = departmentD999.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 0 Funding group types
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
}