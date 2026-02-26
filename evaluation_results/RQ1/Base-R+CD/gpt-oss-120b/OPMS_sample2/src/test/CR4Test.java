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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // Create department with ID: D001
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        communityProject.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        
        // Create and assign funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setName("Government Group");
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(communityProject);
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create department with ID: D002
        department = new Department();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with private funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15 00:00:00"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setName("Private Group");
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with mixed funding group
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
        
        // Verify results
        assertEquals("Should have exactly 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE type", result.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED type", result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department with ID: D003
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // No community projects added to department
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have 0 funding group types when no community projects exist", 0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Create first department with ID: D004
        Department department1 = new Department();
        department1.setID("D004");
        department1.setEmail("department4@example.com");
        
        // Create community project for first department
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Neighborhood Beautification");
        project1.setDescription("Enhancing community space");
        project1.setBudget(7500.0);
        project1.setDeadline(dateFormat.parse("2025-12-01 00:00:00"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setName("Mixed Group");
        fundingGroup1.setType(FundingGroupType.MIXED);
        project1.setFundingGroup(fundingGroup1);
        
        department1.addProject(project1);
        
        // Create second department with ID: D005
        Department department2 = new Department();
        department2.setID("D005");
        department2.setEmail("department5@example.com");
        
        // Create community project for second department
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Local Library Improvement");
        project2.setDescription("Improving library facilities");
        project2.setBudget(6000.0);
        project2.setDeadline(dateFormat.parse("2025-11-30 00:00:00"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setName("Government Group");
        fundingGroup2.setType(FundingGroupType.GOVERNMENT);
        project2.setFundingGroup(fundingGroup2);
        
        department2.addProject(project2);
        
        // Retrieve funding group types for both departments
        List<FundingGroupType> result1 = department1.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> result2 = department2.getFundingGroupTypeCommunityProjects();
        
        // Verify results are isolated between departments
        assertEquals("Department D004 should have 1 funding group type", 1, result1.size());
        assertEquals("Department D004 funding group type should be MIXED", FundingGroupType.MIXED, result1.get(0));
        
        assertEquals("Department D005 should have 1 funding group type", 1, result2.size());
        assertEquals("Department D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result2.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a department but don't use it - simulate invalid department ID D999
        // In actual implementation, we would need to test against a non-existent department
        // Since we're testing the method on a specific department instance, we'll create
        // a new department that represents the "invalid" case (no community projects)
        
        Department invalidDepartment = new Department();
        invalidDepartment.setID("D999");
        invalidDepartment.setEmail("invalid@example.com");
        
        // No projects added to simulate invalid department
        
        // Retrieve funding group types
        List<FundingGroupType> result = invalidDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Invalid department should have 0 funding group types", 0, result.size());
    }
}