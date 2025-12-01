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
        // Create department D001
        Department dept = new Department();
        dept.setID("D001");
        dept.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        project.setDeadline(dateFormat.parse("2025-12-31"));
        
        // Create and set funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        dept.addProject(project);
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create department D002
        Department dept = new Department();
        dept.setID("D002");
        dept.setEmail("department2@example.com");
        
        // Create first community project with Private funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with Mixed funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department
        dept.addProject(project1);
        dept.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE funding type", result.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding type", result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department D003 with no projects
        Department dept = new Department();
        dept.setID("D003");
        dept.setEmail("department3@example.com");
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have 0 funding group types when no community projects exist", 0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Create department D004
        Department deptD004 = new Department();
        deptD004.setID("D004");
        deptD004.setEmail("department4@example.com");
        
        // Create community project for D004
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        projectD004.setDeadline(dateFormat.parse("2025-12-01"));
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFundingGroup(fundingGroupD004);
        
        deptD004.addProject(projectD004);
        
        // Create department D005 (for isolation validation)
        Department deptD005 = new Department();
        deptD005.setID("D005");
        deptD005.setEmail("department5@example.com");
        
        // Create community project for D005
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Improving library facilities");
        projectD005.setBudget(10000.0);
        projectD005.setDeadline(dateFormat.parse("2025-11-30"));
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFundingGroup(fundingGroupD005);
        
        deptD005.addProject(projectD005);
        
        // Retrieve funding group types for both departments
        List<FundingGroupType> resultD004 = deptD004.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> resultD005 = deptD005.getFundingGroupTypeCommunityProjects();
        
        // Verify results for D004
        assertEquals("D004 should have exactly 1 funding group type", 1, resultD004.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, resultD004.get(0));
        
        // Verify results for D005
        assertEquals("D005 should have exactly 1 funding group type", 1, resultD005.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, resultD005.get(0));
        
        // Verify isolation between departments
        assertNotEquals("Departments should have different funding group types", 
                       resultD004.get(0), resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a department but don't use ID D999
        Department dept = new Department();
        dept.setID("D001");
        dept.setEmail("department1@example.com");
        
        // Since we're not creating department D999, we simulate the scenario
        // by testing with an empty department object (as if D999 doesn't exist)
        Department nonExistentDept = new Department(); // Simulating D999
        
        // Retrieve funding group types for non-existent department
        List<FundingGroupType> result = nonExistentDept.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Non-existent department should have 0 funding group types", 0, result.size());
    }
}