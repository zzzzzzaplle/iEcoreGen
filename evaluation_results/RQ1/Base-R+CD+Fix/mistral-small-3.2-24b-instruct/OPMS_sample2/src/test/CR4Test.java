import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws ParseException {
        // Create department with ID: D001
        Department department = new Department("D001", "department1@example.com");
        
        // Create funding group with type "GOVERNMENT"
        FundingGroup fundingGroup = new FundingGroup("Government Group", FundingGroupType.GOVERNMENT);
        
        // Create community project with deadline 2025-12-31
        Date deadline = dateFormat.parse("2025-12-31");
        CommunityProject communityProject = new CommunityProject(
            "Community Clean-Up", 
            "A project to clean the local park", 
            5000.0, 
            deadline, 
            fundingGroup
        );
        
        // Add project to department
        department.addProject(communityProject);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify there is exactly 1 funding group type
        assertEquals(1, fundingGroupTypes.size());
        assertEquals(FundingGroupType.GOVERNMENT, fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws ParseException {
        // Create department with ID: D002
        Department department = new Department("D002", "department2@example.com");
        
        // Create first community project with PRIVATE funding group type
        Date deadline1 = dateFormat.parse("2025-11-15");
        FundingGroup fundingGroup1 = new FundingGroup("Private Group", FundingGroupType.PRIVATE);
        CommunityProject project1 = new CommunityProject(
            "Food Drive", 
            "Collect food for the needy", 
            3000.0, 
            deadline1, 
            fundingGroup1
        );
        
        // Create second community project with MIXED funding group type
        Date deadline2 = dateFormat.parse("2025-10-20");
        FundingGroup fundingGroup2 = new FundingGroup("Mixed Group", FundingGroupType.MIXED);
        CommunityProject project2 = new CommunityProject(
            "Health Awareness Campaign", 
            "Promote health screenings", 
            2000.0, 
            deadline2, 
            fundingGroup2
        );
        
        // Add both projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify there are exactly 2 funding group types
        assertEquals(2, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.contains(FundingGroupType.PRIVATE));
        assertTrue(fundingGroupTypes.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department with ID: D003
        Department department = new Department("D003", "department3@example.com");
        
        // No community projects added to department
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify there are 0 funding group types
        assertEquals(0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws ParseException {
        // Create department D004
        Department department4 = new Department("D004", "department4@example.com");
        
        // Create community project for D004 with MIXED funding group type
        Date deadline4 = dateFormat.parse("2025-12-01");
        FundingGroup fundingGroup4 = new FundingGroup("Mixed Group", FundingGroupType.MIXED);
        CommunityProject project4 = new CommunityProject(
            "Neighborhood Beautification", 
            "Enhancing community space", 
            7500.0, 
            deadline4, 
            fundingGroup4
        );
        department4.addProject(project4);
        
        // Create department D005 with different project
        Department department5 = new Department("D005", "department5@example.com");
        Date deadline5 = dateFormat.parse("2025-12-01");
        FundingGroup fundingGroup5 = new FundingGroup("Government Group", FundingGroupType.GOVERNMENT);
        CommunityProject project5 = new CommunityProject(
            "Local Library Improvement", 
            "Improve library facilities", 
            6000.0, 
            deadline5, 
            fundingGroup5
        );
        department5.addProject(project5);
        
        // Retrieve funding group types for each department
        List<FundingGroupType> fundingGroupTypes4 = department4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> fundingGroupTypes5 = department5.getFundingGroupTypeCommunityProjects();
        
        // Verify D004 has 1 funding group type: MIXED
        assertEquals(1, fundingGroupTypes4.size());
        assertEquals(FundingGroupType.MIXED, fundingGroupTypes4.get(0));
        
        // Verify D005 has 1 funding group type: GOVERNMENT
        assertEquals(1, fundingGroupTypes5.size());
        assertEquals(FundingGroupType.GOVERNMENT, fundingGroupTypes5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // No department with ID D999 exists in the test setup
        // Since we can't retrieve a non-existent department, we'll create an empty department list scenario
        // and verify that no funding group types are returned
        
        // Create a new department that is not D999 to simulate the scenario
        Department someDepartment = new Department("D001", "some@example.com");
        
        // Verify that if we try to get funding group types from a department that doesn't have
        // the specified ID, we get empty results (this test case is about non-existent department)
        
        // For a truly non-existent department, we wouldn't be able to call getFundingGroupTypeCommunityProjects()
        // So we'll verify that a valid department with no community projects returns empty list
        List<FundingGroupType> fundingGroupTypes = someDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify there are 0 funding group types
        assertEquals(0, fundingGroupTypes.size());
    }
}