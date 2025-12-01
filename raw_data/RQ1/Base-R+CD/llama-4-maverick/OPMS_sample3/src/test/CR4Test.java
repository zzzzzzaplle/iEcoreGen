import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR4Test {
    
    private Department department;
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
        department = new Department();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // SetUp: Create department D001
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        project.setDeadline(sdf.parse("2025-12-31"));
        
        // Create funding group with Government type
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        fundingGroup.setName("Government Group");
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(project);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output
        assertEquals("There should be 1 funding group type", 1, fundingGroupTypes.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // SetUp: Create department D002
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Create first community project with Private funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(sdf.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        fundingGroup1.setName("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with Mixed funding group
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(sdf.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        fundingGroup2.setName("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output
        assertEquals("There should be 2 funding group types", 2, fundingGroupTypes.size());
        assertTrue("Should contain PRIVATE type", fundingGroupTypes.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED type", fundingGroupTypes.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output
        assertEquals("There should be 0 funding group types", 0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // SetUp: Create department D004
        Department department4 = new Department();
        department4.setID("D004");
        department4.setEmail("department4@example.com");
        
        // Create community project for D004 with Mixed funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Neighborhood Beautification");
        project1.setDescription("Enhancing community space");
        project1.setBudget(7500.0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        project1.setDeadline(sdf.parse("2025-12-01"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.MIXED);
        fundingGroup1.setName("Mixed Group");
        project1.setFundingGroup(fundingGroup1);
        
        department4.addProject(project1);
        
        // Create department D005 with different community project
        Department department5 = new Department();
        department5.setID("D005");
        department5.setEmail("department5@example.com");
        
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Local Library Improvement");
        project2.setBudget(6000.0);
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.GOVERNMENT);
        fundingGroup2.setName("Government Group");
        project2.setFundingGroup(fundingGroup2);
        
        department5.addProject(project2);
        
        // Add departments to company for organization (though not strictly needed for this test)
        company.addDepartment(department4);
        company.addDepartment(department5);
        
        // Retrieve funding group types for each department
        List<FundingGroupType> fundingGroupTypesD004 = department4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> fundingGroupTypesD005 = department5.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output
        assertEquals("D004 should have 1 funding group type", 1, fundingGroupTypesD004.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, fundingGroupTypesD004.get(0));
        
        assertEquals("D005 should have 1 funding group type", 1, fundingGroupTypesD005.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department created with ID D999 (department remains with default/null ID)
        department.setID("D001"); // Set some valid ID to show we're testing with a different scenario
        
        // Create a new department object that simulates not finding D999
        Department nonExistentDepartment = new Department();
        nonExistentDepartment.setID("D999"); // This department exists but has no projects
        
        // Retrieve funding group types for the non-existent (empty) department
        List<FundingGroupType> fundingGroupTypes = nonExistentDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output
        assertEquals("Non-existent department should have 0 funding group types", 0, fundingGroupTypes.size());
    }
}