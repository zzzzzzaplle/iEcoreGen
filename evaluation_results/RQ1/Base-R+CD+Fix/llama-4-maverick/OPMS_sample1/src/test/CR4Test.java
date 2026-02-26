import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // Create department D001
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        project.setDeadline(dateFormat.parse("2025-12-31"));
        
        // Create and assign funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(project);
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create department D002
        Department department = new Department();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with PRIVATE funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with MIXED funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE funding type", result.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding type", result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department D003 with no community projects
        Department department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have 0 funding group types when no community projects exist", 0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Create department D004
        Department department4 = new Department();
        department4.setID("D004");
        department4.setEmail("department4@example.com");
        
        // Create community project for D004 with MIXED funding
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudget(7500.0);
        project4.setDeadline(dateFormat.parse("2025-12-01"));
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setType(FundingGroupType.MIXED);
        project4.setFundingGroup(fundingGroup4);
        
        department4.addProject(project4);
        
        // Create department D005 (for isolation validation)
        Department department5 = new Department();
        department5.setID("D005");
        department5.setEmail("department5@example.com");
        
        // Create community project for D005 with GOVERNMENT funding
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setType(FundingGroupType.GOVERNMENT);
        project5.setFundingGroup(fundingGroup5);
        
        department5.addProject(project5);
        
        // Retrieve funding group types for both departments
        List<FundingGroupType> result4 = department4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> result5 = department5.getFundingGroupTypeCommunityProjects();
        
        // Verify isolation - each department should only see its own projects
        assertEquals("D004 should have exactly 1 funding group type", 1, result4.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, result4.get(0));
        
        assertEquals("D005 should have exactly 1 funding group type", 1, result5.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a valid department (not D999) to ensure test environment has departments
        Department department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // For invalid department ID D999, we simulate by using a department object that doesn't exist
        // Since we can't query by ID directly, we'll test with a new empty department
        Department invalidDepartment = new Department();
        invalidDepartment.setID("D999");
        
        // Retrieve funding group types from the invalid department
        List<FundingGroupType> result = invalidDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify results - invalid department should have no funding group types
        assertEquals("Invalid department should have 0 funding group types", 0, result.size());
    }
}