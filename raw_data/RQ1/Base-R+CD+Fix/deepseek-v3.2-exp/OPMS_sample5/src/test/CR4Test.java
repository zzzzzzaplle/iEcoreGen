import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR4Test {
    
    private Department department;
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // Setup
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        communityProject.setDeadline(sdf.parse("2025-12-31"));
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        fundingGroup.setName("Government Group");
        communityProject.setFundingGroup(fundingGroup);
        
        department.addProject(communityProject);
        
        // Execute
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Setup
        department = new Department();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // First community project
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(sdf.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        fundingGroup1.setName("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Second community project
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(sdf.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        fundingGroup2.setName("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        department.addProject(project1);
        department.addProject(project2);
        
        // Execute
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify
        assertEquals("Should have exactly 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE funding group type", result.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding group type", result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Setup
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        // No community projects added
        
        // Execute
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify
        assertEquals("Should have 0 funding group types when no community projects exist", 0, result.size());
        assertTrue("Result list should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Setup two departments
        Department department4 = new Department();
        department4.setID("D004");
        department4.setEmail("department4@example.com");
        
        Department department5 = new Department();
        department5.setID("D005");
        department5.setEmail("department5@example.com");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Department D004 project
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudget(7500.0);
        project4.setDeadline(sdf.parse("2025-12-01"));
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setType(FundingGroupType.MIXED);
        fundingGroup4.setName("Mixed Group");
        project4.setFundingGroup(fundingGroup4);
        department4.addProject(project4);
        
        // Department D005 project
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improving library facilities");
        project5.setBudget(6000.0);
        project5.setDeadline(sdf.parse("2025-11-30"));
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setType(FundingGroupType.GOVERNMENT);
        fundingGroup5.setName("Government Group");
        project5.setFundingGroup(fundingGroup5);
        department5.addProject(project5);
        
        // Execute for both departments
        List<FundingGroupType> result4 = department4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> result5 = department5.getFundingGroupTypeCommunityProjects();
        
        // Verify department D004
        assertEquals("Department D004 should have 1 funding group type", 1, result4.size());
        assertEquals("Department D004 funding group type should be MIXED", FundingGroupType.MIXED, result4.get(0));
        
        // Verify department D005
        assertEquals("Department D005 should have 1 funding group type", 1, result5.size());
        assertEquals("Department D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Setup - create a department but don't use it for testing
        Department validDepartment = new Department();
        validDepartment.setID("D001");
        validDepartment.setEmail("valid@example.com");
        
        // Create a new department object with invalid ID (not added to any company)
        Department invalidDepartment = new Department();
        invalidDepartment.setID("D999");
        
        // Execute - get funding group types for the invalid department (which has no projects)
        List<FundingGroupType> result = invalidDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify
        assertEquals("Invalid department should have 0 funding group types", 0, result.size());
        assertTrue("Result list should be empty for invalid department", result.isEmpty());
    }
}