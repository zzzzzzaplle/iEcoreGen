import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // Create department D001
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudgetAmount(5000);
        communityProject.setDeadline("2025-12-31");
        
        // Create and assign funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("Government Group");
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(communityProject);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, fundingGroupTypes.size());
        assertEquals("Funding group type should be 'Government Group'", "Government Group", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // Create department D002
        Department department = new Department();
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with Private Group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with Mixed Group
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 2 funding group types", 2, fundingGroupTypes.size());
        assertTrue("Should contain 'Private Group'", fundingGroupTypes.contains("Private Group"));
        assertTrue("Should contain 'Mixed Group'", fundingGroupTypes.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department D003 with no projects
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Retrieve funding group types from empty department
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify results
        assertEquals("Should have 0 funding group types when no community projects exist", 0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // Create department D004
        Department departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project for D004 with Mixed Group
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500);
        projectD004.setDeadline("2025-12-01");
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType("Mixed Group");
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // Create department D005 with different project
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType("Government Group");
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Retrieve funding group types for both departments
        List<String> fundingGroupTypesD004 = departmentD004.getFundingGroupTypesOfCommunityProjects();
        List<String> fundingGroupTypesD005 = departmentD005.getFundingGroupTypesOfCommunityProjects();
        
        // Verify isolation between departments
        assertEquals("D004 should have exactly 1 funding group type", 1, fundingGroupTypesD004.size());
        assertEquals("D004 funding group type should be 'Mixed Group'", "Mixed Group", fundingGroupTypesD004.get(0));
        
        assertEquals("D005 should have exactly 1 funding group type", 1, fundingGroupTypesD005.size());
        assertEquals("D005 funding group type should be 'Government Group'", "Government Group", fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a department with ID D999 (simulating the invalid department scenario)
        // Since we can't actually query for non-existent departments in the current API,
        // we'll create an empty department with ID D999 and verify it returns no funding group types
        
        Department department = new Department();
        department.setId("D999");
        
        // Retrieve funding group types from department with no projects
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify results - no funding group types should be returned
        assertEquals("Should have 0 funding group types for invalid department", 0, fundingGroupTypes.size());
    }
}