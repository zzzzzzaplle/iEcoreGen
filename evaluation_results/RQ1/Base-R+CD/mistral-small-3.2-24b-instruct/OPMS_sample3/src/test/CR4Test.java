import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR4Test {
    private Department departmentD001;
    private Department departmentD002;
    private Department departmentD003;
    private Department departmentD004;
    private Department departmentD005;
    
    @Before
    public void setUp() throws Exception {
        // Set up departments for test cases
        departmentD001 = new Department();
        departmentD001.setID("D001");
        departmentD001.setEmail("department1@example.com");
        
        departmentD002 = new Department();
        departmentD002.setID("D002");
        departmentD002.setEmail("department2@example.com");
        
        departmentD003 = new Department();
        departmentD003.setID("D003");
        departmentD003.setEmail("department3@example.com");
        
        departmentD004 = new Department();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@example.com");
        
        departmentD005 = new Department();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@example.com");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // Create community project
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline = sdf.parse("2025-12-31");
        communityProject.setDeadline(deadline);
        
        // Create funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setName("Government Group");
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        departmentD001.addProject(communityProject);
        
        // Retrieve funding group types
        List<FundingGroupType> result = departmentD001.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create first community project with PRIVATE funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline1 = sdf.parse("2025-11-15");
        project1.setDeadline(deadline1);
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setName("Private Group");
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with MIXED funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        
        Date deadline2 = sdf.parse("2025-10-20");
        project2.setDeadline(deadline2);
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setName("Mixed Group");
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department
        departmentD002.addProject(project1);
        departmentD002.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> result = departmentD002.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE funding type", result.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding type", result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Department D003 has no projects (already set up in @Before)
        
        // Retrieve funding group types
        List<FundingGroupType> result = departmentD003.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have 0 funding group types when no community projects exist", 0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Create community project for D004 with MIXED funding
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline = sdf.parse("2025-12-01");
        projectD004.setDeadline(deadline);
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setName("Mixed Group");
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // Create community project for D005 with GOVERNMENT funding
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
        
        // Verify results are isolated by department
        assertEquals("D004 should have 1 funding group type", 1, resultD004.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, resultD004.get(0));
        
        assertEquals("D005 should have 1 funding group type", 1, resultD005.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a new department object that represents the invalid department
        Department invalidDepartment = new Department();
        invalidDepartment.setID("D999");
        
        // Retrieve funding group types for invalid department (no projects added)
        List<FundingGroupType> result = invalidDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Invalid department should have 0 funding group types", 0, result.size());
    }
}