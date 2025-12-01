import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        // Setup
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
        
        // Execute
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify
        assertEquals("Should have exactly 1 funding group type", 1, fundingGroupTypes.size());
        assertEquals("Funding group type should be 'Government Group'", "Government Group", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // Setup
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // First community project
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        department.addProject(project1);
        
        // Second community project
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        department.addProject(project2);
        
        // Execute
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify
        assertEquals("Should have exactly 2 funding group types", 2, fundingGroupTypes.size());
        assertTrue("Should contain 'Private Group'", fundingGroupTypes.contains("Private Group"));
        assertTrue("Should contain 'Mixed Group'", fundingGroupTypes.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Setup
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // No community projects added to department
        
        // Execute
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify
        assertEquals("Should have 0 funding group types when no community projects exist", 0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // Setup department D004
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
        
        // Setup department D005
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setBudgetAmount(6000.0);
        projectD005.setDeadline("2025-11-30");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType("Government Group");
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Execute for both departments
        List<String> fundingGroupTypesD004 = departmentD004.getFundingGroupTypesOfCommunityProjects();
        List<String> fundingGroupTypesD005 = departmentD005.getFundingGroupTypesOfCommunityProjects();
        
        // Verify department D004
        assertEquals("D004 should have exactly 1 funding group type", 1, fundingGroupTypesD004.size());
        assertEquals("D004 funding group type should be 'Mixed Group'", "Mixed Group", fundingGroupTypesD004.get(0));
        
        // Verify department D005
        assertEquals("D005 should have exactly 1 funding group type", 1, fundingGroupTypesD005.size());
        assertEquals("D005 funding group type should be 'Government Group'", "Government Group", fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Setup - create a valid department but test with a non-existent department
        Department validDepartment = new Department();
        validDepartment.setId("D001");
        validDepartment.setEmail("department1@example.com");
        
        // Create a new department object to simulate invalid department ID D999
        Department invalidDepartment = new Department();
        invalidDepartment.setId("D999");
        
        // Execute for invalid department
        List<String> fundingGroupTypes = invalidDepartment.getFundingGroupTypesOfCommunityProjects();
        
        // Verify
        assertEquals("Invalid department should have 0 funding group types", 0, fundingGroupTypes.size());
    }
}