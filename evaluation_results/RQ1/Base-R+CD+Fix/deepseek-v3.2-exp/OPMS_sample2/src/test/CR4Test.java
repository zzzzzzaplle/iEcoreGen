import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR4Test {
    
    private Department departmentD001;
    private Department departmentD002;
    private Department departmentD003;
    private Department departmentD004;
    private Department departmentD005;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Initialize departments for tests
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
        // SetUp: Create a community project with Government Group funding
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        communityProject.setDeadline(dateFormat.parse("2025-12-31"));
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        communityProject.setFundingGroup(fundingGroup);
        
        departmentD001.addProject(communityProject);
        
        // Retrieve funding group types
        List<FundingGroupType> result = departmentD001.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // SetUp: Create two community projects with different funding group types
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        departmentD002.addProject(project1);
        departmentD002.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> result = departmentD002.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE funding group type", result.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding group type", result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Department D003 has no community projects (already satisfied by setUp method)
        
        // Retrieve funding group types
        List<FundingGroupType> result = departmentD003.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have 0 funding group types when no community projects exist", 0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // SetUp: Create community projects in two different departments
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        projectD004.setDeadline(dateFormat.parse("2025-12-01"));
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFundingGroup(fundingGroupD004);
        
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Improving local library facilities");
        projectD005.setBudget(6000.0);
        projectD005.setDeadline(dateFormat.parse("2025-11-30"));
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD004.addProject(projectD004);
        departmentD005.addProject(projectD005);
        
        // Retrieve funding group types for both departments
        List<FundingGroupType> resultD004 = departmentD004.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> resultD005 = departmentD005.getFundingGroupTypeCommunityProjects();
        
        // Verify results are isolated per department
        assertEquals("D004 should have exactly 1 funding group type", 1, resultD004.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, resultD004.get(0));
        
        assertEquals("D005 should have exactly 1 funding group type", 1, resultD005.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department with ID D999 exists (already satisfied - no setup needed)
        
        // Since we cannot retrieve a non-existent department, we test with a department that has no community projects
        // This simulates the scenario where we try to get funding group types from a non-existent department
        Department nonExistentDepartment = new Department();
        nonExistentDepartment.setID("D999");
        
        // Retrieve funding group types from the department (which has no projects)
        List<FundingGroupType> result = nonExistentDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have 0 funding group types for non-existent department", 0, result.size());
    }
}