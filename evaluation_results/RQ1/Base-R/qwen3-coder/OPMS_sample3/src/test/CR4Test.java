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
        company = new Company();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // SetUp: Create department D001 with one community project having "Government Group" funding
        department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudgetAmount(5000.0);
        communityProject.setDeadline("2025-12-31");
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("Government Group");
        communityProject.setFundingGroup(fundingGroup);
        
        department.addProject(communityProject);
        
        // Execute: Retrieve funding group types for department D001
        List<String> fundingGroupTypes = department.getFundingGroupTypeOfAllCommunityProjects();
        
        // Verify: There is 1 Funding group type: "Government Group"
        assertEquals(1, fundingGroupTypes.size());
        assertEquals("Government Group", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department D002 with two community projects having different funding groups
        department = new Department();
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // First community project with "Private Group" funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Second community project with "Mixed Group" funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        department.addProject(project1);
        department.addProject(project2);
        
        // Execute: Retrieve funding group types for department D002
        List<String> fundingGroupTypes = department.getFundingGroupTypeOfAllCommunityProjects();
        
        // Verify: There are 2 Funding group types: "Private Group", "Mixed Group"
        assertEquals(2, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.contains("Private Group"));
        assertTrue(fundingGroupTypes.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Add a non-community project to ensure only community projects are considered
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("AI Research");
        department.addProject(researchProject);
        
        // Execute: Retrieve funding group types for department D003
        List<String> fundingGroupTypes = department.getFundingGroupTypeOfAllCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create department D004 with one community project
        Department departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@example.com");
        
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500.0);
        projectD004.setDeadline("2025-12-01");
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType("Mixed Group");
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // SetUp: Create department D005 with different community project
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType("Government Group");
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Execute: Retrieve funding group types for both departments
        List<String> fundingGroupTypesD004 = departmentD004.getFundingGroupTypeOfAllCommunityProjects();
        List<String> fundingGroupTypesD005 = departmentD005.getFundingGroupTypeOfAllCommunityProjects();
        
        // Verify: There is 1 Funding group type in D004: "Mixed Group"
        assertEquals(1, fundingGroupTypesD004.size());
        assertEquals("Mixed Group", fundingGroupTypesD004.get(0));
        
        // Verify: There is 1 Funding group type in D005: "Government Group"
        assertEquals(1, fundingGroupTypesD005.size());
        assertEquals("Government Group", fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department exists with ID D999 (no setup needed)
        
        // Execute: Create a new department object but don't add it to company
        // The test specification assumes we're querying a non-existent department
        // Since the method is instance-based, we'll create a new department object
        Department nonExistentDepartment = new Department();
        nonExistentDepartment.setId("D999");
        
        // Execute: Retrieve funding group types for non-existent department
        List<String> fundingGroupTypes = nonExistentDepartment.getFundingGroupTypeOfAllCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
    }
}