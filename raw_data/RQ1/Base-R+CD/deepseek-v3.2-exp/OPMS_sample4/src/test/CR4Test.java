import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private Department department;
    private Company company;
    
    @Before
    public void setUp() {
        department = new Department();
        company = new Company();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // SetUp: Create department D001
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudgetAmount(5000.0);
        communityProject.setDeadline("2025-12-31");
        
        // Create and assign funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("Government Group");
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(communityProject);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getCommunityProjectFundingGroupTypes();
        
        // Verify expected output
        assertEquals("There should be exactly 1 funding group type", 1, fundingGroupTypes.size());
        assertEquals("Funding group type should be 'Government Group'", "Government Group", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department D002
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getCommunityProjectFundingGroupTypes();
        
        // Verify expected output
        assertEquals("There should be exactly 2 funding group types", 2, fundingGroupTypes.size());
        assertTrue("Should contain 'Private Group'", fundingGroupTypes.contains("Private Group"));
        assertTrue("Should contain 'Mixed Group'", fundingGroupTypes.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Add a non-community project to ensure it doesn't affect the result
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("AI Research");
        researchProject.setBudgetAmount(10000.0);
        department.addProject(researchProject);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getCommunityProjectFundingGroupTypes();
        
        // Verify expected output
        assertEquals("There should be 0 funding group types when no community projects exist", 0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create department D004
        Department departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project for D004
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500.0);
        projectD004.setDeadline("2025-12-01");
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType("Mixed Group");
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // SetUp: Create department D005
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project for D005
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setBudgetAmount(6000.0);
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType("Government Group");
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Add both departments to company
        company.addDepartment(departmentD004);
        company.addDepartment(departmentD005);
        
        // Retrieve funding group types for each department
        List<String> fundingGroupTypesD004 = departmentD004.getCommunityProjectFundingGroupTypes();
        List<String> fundingGroupTypesD005 = departmentD005.getCommunityProjectFundingGroupTypes();
        
        // Verify expected output for D004
        assertEquals("D004 should have exactly 1 funding group type", 1, fundingGroupTypesD004.size());
        assertEquals("D004 funding group type should be 'Mixed Group'", "Mixed Group", fundingGroupTypesD004.get(0));
        
        // Verify expected output for D005
        assertEquals("D005 should have exactly 1 funding group type", 1, fundingGroupTypesD005.size());
        assertEquals("D005 funding group type should be 'Government Group'", "Government Group", fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department with ID D999 exists
        
        // Create a different department to ensure isolation
        Department existingDepartment = new Department();
        existingDepartment.setId("D001");
        existingDepartment.setEmail("existing@example.com");
        
        // Add a community project to the existing department
        CommunityProject project = new CommunityProject();
        project.setTitle("Existing Project");
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("Existing Group");
        project.setFundingGroup(fundingGroup);
        
        existingDepartment.addProject(project);
        
        // For invalid department D999, we simulate by creating a new empty department
        Department invalidDepartment = new Department();
        invalidDepartment.setId("D999");
        
        // Retrieve funding group types for invalid department
        List<String> fundingGroupTypes = invalidDepartment.getCommunityProjectFundingGroupTypes();
        
        // Verify expected output
        assertEquals("Invalid department should have 0 funding group types", 0, fundingGroupTypes.size());
    }
}