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
        // SetUp: Create department D001
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudgetAmount(5000.0);
        project.setDeadline("2025-12-31");
        
        // Create and set funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("Government Group");
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        List<Project> projects = department.getProjects();
        projects.add(project);
        department.setProjects(projects);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals("There should be 1 funding group type", 1, fundingGroupTypes.size());
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
        
        // Add projects to department
        List<Project> projects = department.getProjects();
        projects.add(project1);
        projects.add(project2);
        department.setProjects(projects);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals("There should be 2 funding group types", 2, fundingGroupTypes.size());
        assertTrue("Should contain 'Private Group'", fundingGroupTypes.contains("Private Group"));
        assertTrue("Should contain 'Mixed Group'", fundingGroupTypes.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals("There should be 0 funding group types", 0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create department D004
        Department department4 = new Department();
        department4.setId("D004");
        department4.setEmail("department4@example.com");
        
        // Create community project for D004
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudgetAmount(7500.0);
        project4.setDeadline("2025-12-01");
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setType("Mixed Group");
        project4.setFundingGroup(fundingGroup4);
        
        List<Project> projects4 = department4.getProjects();
        projects4.add(project4);
        department4.setProjects(projects4);
        
        // SetUp: Create department D005
        Department department5 = new Department();
        department5.setId("D005");
        department5.setEmail("department5@example.com");
        
        // Create community project for D005
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setType("Government Group");
        project5.setFundingGroup(fundingGroup5);
        
        List<Project> projects5 = department5.getProjects();
        projects5.add(project5);
        department5.setProjects(projects5);
        
        // Retrieve funding group types for both departments
        List<String> fundingGroupTypes4 = department4.getFundingGroupTypesOfCommunityProjects();
        List<String> fundingGroupTypes5 = department5.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals("D004 should have 1 funding group type", 1, fundingGroupTypes4.size());
        assertEquals("D004 funding group type should be 'Mixed Group'", "Mixed Group", fundingGroupTypes4.get(0));
        
        assertEquals("D005 should have 1 funding group type", 1, fundingGroupTypes5.size());
        assertEquals("D005 funding group type should be 'Government Group'", "Government Group", fundingGroupTypes5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department with ID D999 exists, so we test with a new department that has no projects
        Department nonExistentDepartment = new Department();
        nonExistentDepartment.setId("D999");
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = nonExistentDepartment.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals("There should be 0 funding group types for non-existent department", 0, fundingGroupTypes.size());
    }
}