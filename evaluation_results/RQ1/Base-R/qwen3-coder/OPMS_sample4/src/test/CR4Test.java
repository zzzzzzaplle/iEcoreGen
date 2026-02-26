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
        department = new Department();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // SetUp: Create department D001 with one community project having "Government Group" funding
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudgetAmount(5000.0);
        communityProject.setDeadline("2025-12-31");
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setGroupType("Government Group");
        communityProject.setFundingGroup(fundingGroup);
        
        List<Project> projects = new ArrayList<>();
        projects.add(communityProject);
        department.setProjects(projects);
        
        // Execute: Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 1 Funding group type: "Government Group"
        assertEquals(1, fundingGroupTypes.size());
        assertEquals("Government Group", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department D002 with two community projects
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // First community project with "Private Group" funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setGroupType("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Second community project with "Mixed Group" funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setGroupType("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        department.setProjects(projects);
        
        // Execute: Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There are 2 Funding group types: "Private Group", "Mixed Group"
        assertEquals(2, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.contains("Private Group"));
        assertTrue(fundingGroupTypes.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Department has no projects (empty list by default)
        
        // Execute: Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create two departments with community projects
        Department departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Community project for D004 with "Mixed Group" funding
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500.0);
        projectD004.setDeadline("2025-12-01");
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setGroupType("Mixed Group");
        projectD004.setFundingGroup(fundingGroupD004);
        
        List<Project> projectsD004 = new ArrayList<>();
        projectsD004.add(projectD004);
        departmentD004.setProjects(projectsD004);
        
        // Department D005 with different community project
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setGroupType("Government Group");
        projectD005.setFundingGroup(fundingGroupD005);
        
        List<Project> projectsD005 = new ArrayList<>();
        projectsD005.add(projectD005);
        departmentD005.setProjects(projectsD005);
        
        // Execute: Retrieve funding group types for each department
        List<String> fundingGroupTypesD004 = departmentD004.getFundingGroupTypesOfCommunityProjects();
        List<String> fundingGroupTypesD005 = departmentD005.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: D004 has 1 Funding group type: "Mixed Group", D005 has 1 Funding group type: "Government Group"
        assertEquals(1, fundingGroupTypesD004.size());
        assertEquals("Mixed Group", fundingGroupTypesD004.get(0));
        
        assertEquals(1, fundingGroupTypesD005.size());
        assertEquals("Government Group", fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department exists with ID D999 (using a new department object but not setting ID to D999)
        Department department = new Department();
        department.setId("D001"); // Different ID than D999
        
        // Execute: Retrieve funding group types (method operates on department object, not by ID lookup)
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 0 Funding group type (department has no projects)
        assertEquals(0, fundingGroupTypes.size());
    }
}