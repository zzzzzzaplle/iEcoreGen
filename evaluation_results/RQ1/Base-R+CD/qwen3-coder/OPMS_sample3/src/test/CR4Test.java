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
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // Create a department with ID: D001 and email: "department1@example.com"
        Department dept = new Department();
        dept.setID("D001");
        dept.setEmail("department1@example.com");
        
        // Create a community project in department D001
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        project.setDeadline(dateFormat.parse("2025-12-31"));
        
        // Assign the funding group type "Government Group" to the community project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project.setFundingGroup(fundingGroup);
        
        dept.addProject(project);
        
        // Retrieve funding group types for community projects
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Expected Output: There is 1 Funding group type: "Government Group"
        assertEquals(1, result.size());
        assertEquals(FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create a department with ID: D002 and email: "department2@example.com"
        Department dept = new Department();
        dept.setID("D002");
        dept.setEmail("department2@example.com");
        
        // Create first community project in department D002
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        dept.addProject(project1);
        
        // Create second community project in department D002
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        dept.addProject(project2);
        
        // Retrieve funding group types for community projects
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Expected Output: There are 2 Funding group type: "Private Group", "Mixed Group"
        assertEquals(2, result.size());
        assertTrue(result.contains(FundingGroupType.PRIVATE));
        assertTrue(result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create a department with ID: D003 and email: "department3@example.com"
        Department dept = new Department();
        dept.setID("D003");
        dept.setEmail("department3@example.com");
        
        // Ensure no community projects are created in department D003
        // Retrieve funding group types for community projects
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Expected Output: There is 0 Funding group type
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Create department D004
        Department dept4 = new Department();
        dept4.setID("D004");
        dept4.setEmail("department4@example.com");
        
        // Create community project in department D004
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudget(7500.0);
        project4.setDeadline(dateFormat.parse("2025-12-01"));
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setType(FundingGroupType.MIXED);
        project4.setFundingGroup(fundingGroup4);
        dept4.addProject(project4);
        
        // Create department D005
        Department dept5 = new Department();
        dept5.setID("D005");
        dept5.setEmail("department5@example.com");
        
        // Create community project in department D005
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improve library facilities");
        project5.setBudget(6000.0);
        project5.setDeadline(dateFormat.parse("2025-11-30"));
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setType(FundingGroupType.GOVERNMENT);
        project5.setFundingGroup(fundingGroup5);
        dept5.addProject(project5);
        
        // Retrieve funding group types for each department separately
        List<FundingGroupType> result4 = dept4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> result5 = dept5.getFundingGroupTypeCommunityProjects();
        
        // Expected Output: There is 1 Funding group type in D004 : "Mixed Group". 
        // There is 1 Funding group type in D005 : "Government Group"
        assertEquals(1, result4.size());
        assertEquals(FundingGroupType.MIXED, result4.get(0));
        
        assertEquals(1, result5.size());
        assertEquals(FundingGroupType.GOVERNMENT, result5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // This test case is handled by creating a new department object with the invalid ID
        // Since departments are independent objects, we simulate the scenario by creating a department with ID D999
        Department dept = new Department();
        dept.setID("D999");
        dept.setEmail("nonexistent@example.com");
        
        // No projects are added to this department
        // Retrieve funding group types for community projects
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Expected Output: There is 0 Funding group type
        assertEquals(0, result.size());
    }
}