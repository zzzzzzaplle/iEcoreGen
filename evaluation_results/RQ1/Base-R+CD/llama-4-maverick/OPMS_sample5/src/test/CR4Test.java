import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

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
        // Create department D001
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        communityProject.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        
        // Create and set funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        fundingGroup.setName("Government Group");
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(communityProject);
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Should be GOVERNMENT type", FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create department D002
        department = new Department();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project (Private Group)
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15 00:00:00"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        fundingGroup1.setName("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project (Mixed Group)
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20 00:00:00"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        fundingGroup2.setName("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department
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
        // Create department D003 with no projects
        department = new Department();
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
        Department departmentD004 = new Department();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project for D004 (Mixed Group)
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        projectD004.setDeadline(dateFormat.parse("2025-12-01 00:00:00"));
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED);
        fundingGroupD004.setName("Mixed Group");
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // Create department D005
        Department departmentD005 = new Department();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project for D005 (Government Group)
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        fundingGroupD005.setName("Government Group");
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Retrieve funding group types for each department
        List<FundingGroupType> resultD004 = departmentD004.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> resultD005 = departmentD005.getFundingGroupTypeCommunityProjects();
        
        // Verify isolation between departments
        assertEquals("D004 should have exactly 1 funding group type", 1, resultD004.size());
        assertEquals("D004 should have MIXED type", FundingGroupType.MIXED, resultD004.get(0));
        
        assertEquals("D005 should have exactly 1 funding group type", 1, resultD005.size());
        assertEquals("D005 should have GOVERNMENT type", FundingGroupType.GOVERNMENT, resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a department that doesn't match the invalid ID (D999)
        Department validDepartment = new Department();
        validDepartment.setID("D001");
        validDepartment.setEmail("valid@example.com");
        
        // Try to access department D999 (which doesn't exist in our test setup)
        // In a real scenario, we might have a company repository, but for this test
        // we'll create a new department object with ID D999 that has no projects
        Department invalidDepartment = new Department();
        invalidDepartment.setID("D999");
        
        // Retrieve funding group types for invalid department
        List<FundingGroupType> result = invalidDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Invalid department should have 0 funding group types", 0, result.size());
    }
}