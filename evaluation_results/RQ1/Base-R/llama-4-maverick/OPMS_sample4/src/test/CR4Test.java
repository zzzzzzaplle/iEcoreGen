import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private Department departmentD001;
    private Department departmentD002;
    private Department departmentD003;
    private Department departmentD004;
    private Department departmentD005;
    
    @Before
    public void setUp() {
        // Initialize departments for reuse in tests
        departmentD001 = new Department();
        departmentD001.setId("D001");
        departmentD001.setEmail("department1@example.com");
        
        departmentD002 = new Department();
        departmentD002.setId("D002");
        departmentD002.setEmail("department2@example.com");
        
        departmentD003 = new Department();
        departmentD003.setId("D003");
        departmentD003.setEmail("department3@example.com");
        
        departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@example.com");
        
        departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // Test Case 1: Retrieve Funding Group Type for Single Community Project
        // Input: Retrieve funding group type for community projects in department with ID: D001
        
        // SetUp:
        // 1. Create a department with ID: D001 and email: "department1@example.com"
        Department department = departmentD001;
        
        // 2. Create a community project in department D001 titled "Community Clean-Up" 
        // with description "A project to clean the local park", budget amount: 5000 CNY, 
        // and deadline: "2025-12-31"
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudgetAmount(5000.0);
        communityProject.setDeadline("2025-12-31");
        
        // 3. Assign the funding group type "Government Group" to the community project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("Government Group");
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        List<Project> projects = new ArrayList<>();
        projects.add(communityProject);
        department.setProjects(projects);
        
        // Execute method under test
        List<String> result = department.getFundingGroupTypesOfCommunityProjects();
        
        // Expected Output: There is 1 Funding group type: "Government Group"
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be 'Government Group'", "Government Group", result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // Test Case 2: Retrieve Funding Group Type for Multiple Community Projects
        // Input: Retrieve funding group types for community projects in department with ID: D002
        
        // SetUp:
        // 1. Create a department with ID: D002 and email: "department2@example.com"
        Department department = departmentD002;
        
        // 2. Create two community projects in department D002:
        List<Project> projects = new ArrayList<>();
        
        // First community project: "Food Drive"
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("Private Group");
        project1.setFundingGroup(fundingGroup1);
        projects.add(project1);
        
        // Second community project: "Health Awareness Campaign"
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        projects.add(project2);
        
        department.setProjects(projects);
        
        // Execute method under test
        List<String> result = department.getFundingGroupTypesOfCommunityProjects();
        
        // Expected Output: There are 2 Funding group type: "Private Group", "Mixed Group"
        assertEquals("Should have exactly 2 funding group types", 2, result.size());
        assertTrue("Should contain 'Private Group'", result.contains("Private Group"));
        assertTrue("Should contain 'Mixed Group'", result.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Test Case 3: No Community Projects in Department
        // Input: Retrieve funding group type for community projects in department with ID: D003
        
        // SetUp:
        // 1. Create a department with ID: D003 and email: "department3@example.com"
        Department department = departmentD003;
        
        // 2. Ensure no community projects are created in department D003
        // (department has no projects by default)
        
        // Execute method under test
        List<String> result = department.getFundingGroupTypesOfCommunityProjects();
        
        // Expected Output: There is 0 Funding group type
        assertEquals("Should have 0 funding group types when no community projects exist", 0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // Test Case 4: Retrieve Funding Group Type with Multiple Departments
        // Input: Retrieve funding group types for community projects in department with ID: D004
        
        // SetUp:
        // 1. Create a department with ID: D004 and email: "department4@example.com"
        Department departmentD004 = this.departmentD004;
        
        // 2. Create a community project titled "Neighborhood Beautification" with 
        // description "Enhancing community space", budget amount: 7500 CNY, 
        // and deadline: "2025-12-01" with funding group type "Mixed Group"
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Neighborhood Beautification");
        project1.setDescription("Enhancing community space");
        project1.setBudgetAmount(7500.0);
        project1.setDeadline("2025-12-01");
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("Mixed Group");
        project1.setFundingGroup(fundingGroup1);
        
        List<Project> projectsD004 = new ArrayList<>();
        projectsD004.add(project1);
        departmentD004.setProjects(projectsD004);
        
        // 3. Create a different department D005 with a community project to validate isolation:
        // ID: D005, email: "department5@example.com", project titled "Local Library Improvement", 
        // funding group type "Government Group"
        Department departmentD005 = this.departmentD005;
        
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Local Library Improvement");
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType("Government Group");
        project2.setFundingGroup(fundingGroup2);
        
        List<Project> projectsD005 = new ArrayList<>();
        projectsD005.add(project2);
        departmentD005.setProjects(projectsD005);
        
        // Execute method under test for D004
        List<String> resultD004 = departmentD004.getFundingGroupTypesOfCommunityProjects();
        
        // Execute method under test for D005
        List<String> resultD005 = departmentD005.getFundingGroupTypesOfCommunityProjects();
        
        // Expected Output: There is 1 Funding group type in D004 : "Mixed Group". 
        // There is 1 Funding group type in D005 : "Government Group"
        assertEquals("D004 should have exactly 1 funding group type", 1, resultD004.size());
        assertEquals("D004 funding group type should be 'Mixed Group'", "Mixed Group", resultD004.get(0));
        
        assertEquals("D005 should have exactly 1 funding group type", 1, resultD005.size());
        assertEquals("D005 funding group type should be 'Government Group'", "Government Group", resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Test Case 5: Funding Group Type with Invalid Department ID
        // Input: Retrieve funding group types for community projects in department with an invalid ID: D999
        
        // SetUp:
        // 1. Assume no department exists with ID: D999
        // Create a new department object (not in the pre-initialized departments)
        Department departmentD999 = new Department();
        departmentD999.setId("D999");
        departmentD999.setEmail("nonexistent@example.com");
        // No projects are added to this department
        
        // Execute method under test
        List<String> result = departmentD999.getFundingGroupTypesOfCommunityProjects();
        
        // Expected Output: There is 0 Funding group type
        assertEquals("Should have 0 funding group types for department with no projects", 0, result.size());
    }
}