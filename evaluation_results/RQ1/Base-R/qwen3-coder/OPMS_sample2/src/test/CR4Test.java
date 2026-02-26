import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private Department department;
    
    @Before
    public void setUp() {
        department = new Department();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // SetUp: Create department with ID: D001 and email
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
        List<Project> projects = department.getProjects();
        projects.add(communityProject);
        department.setProjects(projects);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output: There is 1 Funding group type: "Government Group"
        assertEquals(1, fundingGroupTypes.size());
        assertEquals("Government Group", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department with ID: D002 and email
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setGroupType("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setGroupType("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department
        List<Project> projects = department.getProjects();
        projects.add(project1);
        projects.add(project2);
        department.setProjects(projects);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output: There are 2 Funding group type: "Private Group", "Mixed Group"
        assertEquals(2, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.contains("Private Group"));
        assertTrue(fundingGroupTypes.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department with ID: D003 and email
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Ensure no community projects are created (department has empty projects list by default)
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.isEmpty());
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
        fundingGroupD004.setGroupType("Mixed Group");
        projectD004.setFundingGroup(fundingGroupD004);
        
        List<Project> projectsD004 = departmentD004.getProjects();
        projectsD004.add(projectD004);
        departmentD004.setProjects(projectsD004);
        
        // SetUp: Create department D005
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project for D005
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Improving library facilities");
        projectD005.setBudgetAmount(6000.0);
        projectD005.setDeadline("2025-11-30");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setGroupType("Government Group");
        projectD005.setFundingGroup(fundingGroupD005);
        
        List<Project> projectsD005 = departmentD005.getProjects();
        projectsD005.add(projectD005);
        departmentD005.setProjects(projectsD005);
        
        // Retrieve funding group types for both departments
        List<String> fundingGroupTypesD004 = departmentD004.getFundingGroupTypesOfCommunityProjects();
        List<String> fundingGroupTypesD005 = departmentD005.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output: There is 1 Funding group type in D004: "Mixed Group"
        assertEquals(1, fundingGroupTypesD004.size());
        assertEquals("Mixed Group", fundingGroupTypesD004.get(0));
        
        // Verify expected output: There is 1 Funding group type in D005: "Government Group"
        assertEquals(1, fundingGroupTypesD005.size());
        assertEquals("Government Group", fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: Create a valid department to test isolation
        Department validDepartment = new Department();
        validDepartment.setId("D100");
        validDepartment.setEmail("valid@example.com");
        
        // Create community project in valid department
        CommunityProject validProject = new CommunityProject();
        validProject.setTitle("Valid Project");
        validProject.setDescription("A valid community project");
        validProject.setBudgetAmount(1000.0);
        validProject.setDeadline("2025-12-31");
        
        FundingGroup validFundingGroup = new FundingGroup();
        validFundingGroup.setGroupType("Private Group");
        validProject.setFundingGroup(validFundingGroup);
        
        List<Project> validProjects = validDepartment.getProjects();
        validProjects.add(validProject);
        validDepartment.setProjects(validProjects);
        
        // Create a new department object with invalid ID D999 (simulating non-existent department)
        Department invalidDepartment = new Department();
        invalidDepartment.setId("D999");
        
        // Retrieve funding group types for invalid department (which has no projects)
        List<String> fundingGroupTypes = invalidDepartment.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.isEmpty());
        
        // Also verify that valid department still has its funding group type
        List<String> validFundingGroupTypes = validDepartment.getFundingGroupTypesOfCommunityProjects();
        assertEquals(1, validFundingGroupTypes.size());
        assertEquals("Private Group", validFundingGroupTypes.get(0));
    }
}