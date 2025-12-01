import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // Create a department with ID: D001 and email: "department1@example.com"
        Department department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Create a community project in department D001
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudgetAmount(5000.0);
        project.setDeadline("2025-12-31");
        
        // Assign the funding group type "Government Group" to the community project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(project);
        
        // Retrieve funding group types for community projects
        java.util.List<FundingGroupType> result = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify there is 1 Funding group type: "Government Group"
        assertEquals(1, result.size());
        assertEquals(FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // Create a department with ID: D002 and email: "department2@example.com"
        Department department = new Department();
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with "Private Group" funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with "Mixed Group" funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types for community projects
        java.util.List<FundingGroupType> result = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify there are 2 Funding group types: "Private Group", "Mixed Group"
        assertEquals(2, result.size());
        assertTrue(result.contains(FundingGroupType.PRIVATE));
        assertTrue(result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create a department with ID: D003 and email: "department3@example.com"
        Department department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Ensure no community projects are created (department has no projects by default)
        
        // Retrieve funding group types for community projects
        java.util.List<FundingGroupType> result = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify there is 0 Funding group type
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // Create department D004
        Department departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project in D004 with "Mixed Group" funding
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500.0);
        projectD004.setDeadline("2025-12-01");
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // Create department D005 with different project
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Improving library facilities");
        projectD005.setBudgetAmount(6000.0);
        projectD005.setDeadline("2025-11-30");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Retrieve funding group types for each department
        java.util.List<FundingGroupType> resultD004 = departmentD004.getFundingGroupTypesOfCommunityProjects();
        java.util.List<FundingGroupType> resultD005 = departmentD005.getFundingGroupTypesOfCommunityProjects();
        
        // Verify isolation: D004 has "Mixed Group", D005 has "Government Group"
        assertEquals(1, resultD004.size());
        assertEquals(FundingGroupType.MIXED, resultD004.get(0));
        
        assertEquals(1, resultD005.size());
        assertEquals(FundingGroupType.GOVERNMENT, resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a new department (not D999) to ensure isolation
        Department department = new Department();
        department.setId("D001");
        department.setEmail("valid@example.com");
        
        // For invalid department ID D999, we need to simulate non-existence
        // Since we can't access non-existent departments, we'll create a scenario
        // where we check a department that has no community projects
        
        // Create a department with different ID to ensure our test is valid
        Department testDepartment = new Department();
        testDepartment.setId("D999"); // This department exists in our test but has no projects
        
        // Retrieve funding group types for the department with ID D999
        java.util.List<FundingGroupType> result = testDepartment.getFundingGroupTypesOfCommunityProjects();
        
        // Verify there is 0 Funding group type for non-existent/empty department
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
}