import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Department department;
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // Test Case 1: Retrieve Funding Group Type for Single Community Project
        // Create a department with ID: D001 and email: "department1@example.com"
        Department dept = new Department();
        dept.setId("D001");
        dept.setEmail("department1@example.com");
        
        // Create a community project in department D001
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudgetAmount(5000.0);
        project.setDeadline(LocalDate.of(2025, 12, 31));
        
        // Assign the funding group type "Government Group" to the community project
        GovernmentGroup fundingGroup = new GovernmentGroup();
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        dept.addProject(project);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = dept.getCommunityProjectFundingGroupTypes();
        
        // Verify expected output: There is 1 Funding group type: "Government Group"
        assertEquals(1, fundingGroupTypes.size());
        assertEquals("GOVERNMENT", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // Test Case 2: Retrieve Funding Group Type for Multiple Community Projects
        // Create a department with ID: D002 and email: "department2@example.com"
        Department dept = new Department();
        dept.setId("D002");
        dept.setEmail("department2@example.com");
        
        // Create first community project with Private Group funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline(LocalDate.of(2025, 11, 15));
        PrivateGroup fundingGroup1 = new PrivateGroup();
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with Mixed Group funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline(LocalDate.of(2025, 10, 20));
        MixedGroup fundingGroup2 = new MixedGroup();
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        dept.addProject(project1);
        dept.addProject(project2);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = dept.getCommunityProjectFundingGroupTypes();
        
        // Verify expected output: There are 2 Funding group type: "Private Group", "Mixed Group"
        assertEquals(2, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.contains("PRIVATE"));
        assertTrue(fundingGroupTypes.contains("MIXED"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Test Case 3: No Community Projects in Department
        // Create a department with ID: D003 and email: "department3@example.com"
        Department dept = new Department();
        dept.setId("D003");
        dept.setEmail("department3@example.com");
        
        // Ensure no community projects are created in department D003
        // (department is created empty, so no projects are added)
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = dept.getCommunityProjectFundingGroupTypes();
        
        // Verify expected output: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // Test Case 4: Retrieve Funding Group Type with Multiple Departments
        // Create department D004
        Department deptD004 = new Department();
        deptD004.setId("D004");
        deptD004.setEmail("department4@example.com");
        
        // Create community project for D004 with Mixed Group funding
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500.0);
        projectD004.setDeadline(LocalDate.of(2025, 12, 1));
        MixedGroup fundingGroupD004 = new MixedGroup();
        projectD004.setFundingGroup(fundingGroupD004);
        deptD004.addProject(projectD004);
        
        // Create department D005 with different community project
        Department deptD005 = new Department();
        deptD005.setId("D005");
        deptD005.setEmail("department5@example.com");
        
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Improve library facilities");
        projectD005.setBudgetAmount(10000.0);
        projectD005.setDeadline(LocalDate.of(2025, 11, 30));
        GovernmentGroup fundingGroupD005 = new GovernmentGroup();
        projectD005.setFundingGroup(fundingGroupD005);
        deptD005.addProject(projectD005);
        
        // Retrieve funding group types for each department
        List<String> fundingGroupTypesD004 = deptD004.getCommunityProjectFundingGroupTypes();
        List<String> fundingGroupTypesD005 = deptD005.getCommunityProjectFundingGroupTypes();
        
        // Verify expected output: There is 1 Funding group type in D004 : "Mixed Group"
        assertEquals(1, fundingGroupTypesD004.size());
        assertEquals("MIXED", fundingGroupTypesD004.get(0));
        
        // Verify expected output: There is 1 Funding group type in D005 : "Government Group"
        assertEquals(1, fundingGroupTypesD005.size());
        assertEquals("GOVERNMENT", fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Test Case 5: Funding Group Type with Invalid Department ID
        // Assume no department exists with ID: D999
        
        // Since we cannot retrieve a department that doesn't exist, we'll create
        // a scenario where we have a department but it's not D999
        Department validDept = new Department();
        validDept.setId("D001");
        validDept.setEmail("valid@example.com");
        
        // For this test, we need to verify that when we query a non-existent department,
        // we get no funding group types. Since we can't query a non-existent object,
        // we'll create a scenario that simulates the expected behavior
        
        // Create a new department object (simulating a department that wasn't properly initialized)
        Department nonExistentDept = new Department();
        nonExistentDept.setId("D999"); // Set to invalid ID but object exists
        
        // Retrieve funding group types from the "non-existent" department
        List<String> fundingGroupTypes = nonExistentDept.getCommunityProjectFundingGroupTypes();
        
        // Verify expected output: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.isEmpty());
    }
}