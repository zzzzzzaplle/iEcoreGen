import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private Department department;
    
    @Before
    public void setUp() {
        department = new Department();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // SetUp: Create department with ID: D001
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Create a community project
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudgetAmount(5000.0);
        communityProject.setDeadline("2025-12-31");
        
        // Assign funding group type "Government Group"
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setGroupType("Government Group");
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(communityProject);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypeOfAllCommunityProjects();
        
        // Expected Output: There is 1 Funding group type: "Government Group"
        assertEquals(1, fundingGroupTypes.size());
        assertEquals("Government Group", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department with ID: D002
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with "Private Group"
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setGroupType("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with "Mixed Group"
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setGroupType("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypeOfAllCommunityProjects();
        
        // Expected Output: There are 2 Funding group type: "Private Group", "Mixed Group"
        assertEquals(2, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.contains("Private Group"));
        assertTrue(fundingGroupTypes.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department with ID: D003
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Ensure no community projects are created (department has empty project list)
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypeOfAllCommunityProjects();
        
        // Expected Output: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create department D004
        Department departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project for D004 with "Mixed Group"
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500.0);
        projectD004.setDeadline("2025-12-01");
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setGroupType("Mixed Group");
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // SetUp: Create department D005
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project for D005 with "Government Group"
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Improve library facilities");
        projectD005.setBudgetAmount(6000.0);
        projectD005.setDeadline("2025-11-30");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setGroupType("Government Group");
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Retrieve funding group types for both departments
        List<String> fundingGroupTypesD004 = departmentD004.getFundingGroupTypeOfAllCommunityProjects();
        List<String> fundingGroupTypesD005 = departmentD005.getFundingGroupTypeOfAllCommunityProjects();
        
        // Expected Output: There is 1 Funding group type in D004 : "Mixed Group"
        assertEquals(1, fundingGroupTypesD004.size());
        assertEquals("Mixed Group", fundingGroupTypesD004.get(0));
        
        // Expected Output: There is 1 Funding group type in D005 : "Government Group"
        assertEquals(1, fundingGroupTypesD005.size());
        assertEquals("Government Group", fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: Assume no department exists with ID: D999
        // We'll create a new department object with ID D999 but with no projects
        Department invalidDepartment = new Department();
        invalidDepartment.setId("D999");
        invalidDepartment.setEmail("nonexistent@example.com");
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = invalidDepartment.getFundingGroupTypeOfAllCommunityProjects();
        
        // Expected Output: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
    }
}