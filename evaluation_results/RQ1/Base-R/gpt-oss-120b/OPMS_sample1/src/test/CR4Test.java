import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        // SetUp: Create department D001
        department.setDepartmentId("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudgetAmount(5000.0);
        project.setDeadline(LocalDate.of(2025, 12, 31));
        
        // Assign government funding group
        GovernmentGroup fundingGroup = new GovernmentGroup();
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.getProjects().add(project);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals(1, fundingGroupTypes.size());
        assertEquals("GOVERNMENT", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department D002
        department.setDepartmentId("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with private funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline(LocalDate.of(2025, 11, 15));
        
        PrivateGroup fundingGroup1 = new PrivateGroup();
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with mixed funding group
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline(LocalDate.of(2025, 10, 20));
        
        MixedGroup fundingGroup2 = new MixedGroup();
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department
        department.getProjects().add(project1);
        department.getProjects().add(project2);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals(2, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.contains("PRIVATE"));
        assertTrue(fundingGroupTypes.contains("MIXED"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department.setDepartmentId("D003");
        department.setEmail("department3@example.com");
        
        // Add a non-community project to ensure it doesn't affect the result
        ResearchProject researchProject = new ResearchProject();
        researchProject.setTitle("AI Research");
        department.getProjects().add(researchProject);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals(0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create department D004
        Department department4 = new Department();
        department4.setDepartmentId("D004");
        department4.setEmail("department4@example.com");
        
        // Create community project for D004 with mixed funding group
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudgetAmount(7500.0);
        project4.setDeadline(LocalDate.of(2025, 12, 1));
        
        MixedGroup fundingGroup4 = new MixedGroup();
        project4.setFundingGroup(fundingGroup4);
        
        department4.getProjects().add(project4);
        
        // SetUp: Create department D005
        Department department5 = new Department();
        department5.setDepartmentId("D005");
        department5.setEmail("department5@example.com");
        
        // Create community project for D005 with government funding group
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        
        GovernmentGroup fundingGroup5 = new GovernmentGroup();
        project5.setFundingGroup(fundingGroup5);
        
        department5.getProjects().add(project5);
        
        // Add both departments to company
        company.getDepartments().add(department4);
        company.getDepartments().add(department5);
        
        // Retrieve funding group types for each department
        List<String> fundingGroupTypesD004 = department4.getFundingGroupTypesOfCommunityProjects();
        List<String> fundingGroupTypesD005 = department5.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output for D004
        assertEquals(1, fundingGroupTypesD004.size());
        assertEquals("MIXED", fundingGroupTypesD004.get(0));
        
        // Verify expected output for D005
        assertEquals(1, fundingGroupTypesD005.size());
        assertEquals("GOVERNMENT", fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department exists with ID D999
        // Create a different department to ensure isolation
        Department validDepartment = new Department();
        validDepartment.setDepartmentId("D001");
        
        // Create community project in valid department
        CommunityProject project = new CommunityProject();
        GovernmentGroup fundingGroup = new GovernmentGroup();
        project.setFundingGroup(fundingGroup);
        validDepartment.getProjects().add(project);
        
        company.getDepartments().add(validDepartment);
        
        // Create a new department object with invalid ID (not added to company)
        Department invalidDepartment = new Department();
        invalidDepartment.setDepartmentId("D999");
        
        // Retrieve funding group types for invalid department
        List<String> fundingGroupTypes = invalidDepartment.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals(0, fundingGroupTypes.size());
    }
}