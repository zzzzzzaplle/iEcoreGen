import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // Create department D001
        Department dept = new Department();
        dept.setId("D001");
        dept.setEmail("department1@example.com");
        company.addDepartment(dept);
        
        // Create community project with Government funding group
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudgetAmount(5000.0);
        project.setDeadline(LocalDate.of(2025, 12, 31));
        
        GovernmentGroup fundingGroup = new GovernmentGroup();
        project.setFundingGroup(fundingGroup);
        
        dept.addProject(project);
        
        // Retrieve funding group types
        List<FundingGroupType> types = company.getFundingGroupTypesOfCommunityProjectsInDepartment(dept);
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, types.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, types.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // Create department D002
        Department dept = new Department();
        dept.setId("D002");
        dept.setEmail("department2@example.com");
        company.addDepartment(dept);
        
        // Create first community project with Private funding group
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline(LocalDate.of(2025, 11, 15));
        
        PrivateGroup fundingGroup1 = new PrivateGroup();
        project1.setFundingGroup(fundingGroup1);
        dept.addProject(project1);
        
        // Create second community project with Mixed funding group
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline(LocalDate.of(2025, 10, 20));
        
        MixedGroup fundingGroup2 = new MixedGroup();
        project2.setFundingGroup(fundingGroup2);
        dept.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> types = company.getFundingGroupTypesOfCommunityProjectsInDepartment(dept);
        
        // Verify results
        assertEquals("Should have exactly 2 funding group types", 2, types.size());
        assertTrue("Should contain PRIVATE type", types.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED type", types.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department D003 with no community projects
        Department dept = new Department();
        dept.setId("D003");
        dept.setEmail("department3@example.com");
        company.addDepartment(dept);
        
        // Retrieve funding group types
        List<FundingGroupType> types = company.getFundingGroupTypesOfCommunityProjectsInDepartment(dept);
        
        // Verify results
        assertTrue("Should have 0 funding group types when no community projects exist", types.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // Create department D004
        Department dept4 = new Department();
        dept4.setId("D004");
        dept4.setEmail("department4@example.com");
        company.addDepartment(dept4);
        
        // Create community project in D004 with Mixed funding group
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudgetAmount(7500.0);
        project4.setDeadline(LocalDate.of(2025, 12, 1));
        
        MixedGroup fundingGroup4 = new MixedGroup();
        project4.setFundingGroup(fundingGroup4);
        dept4.addProject(project4);
        
        // Create department D005
        Department dept5 = new Department();
        dept5.setId("D005");
        dept5.setEmail("department5@example.com");
        company.addDepartment(dept5);
        
        // Create community project in D005 with Government funding group
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improve library facilities");
        project5.setBudgetAmount(6000.0);
        project5.setDeadline(LocalDate.of(2025, 11, 30));
        
        GovernmentGroup fundingGroup5 = new GovernmentGroup();
        project5.setFundingGroup(fundingGroup5);
        dept5.addProject(project5);
        
        // Retrieve funding group types for both departments
        List<FundingGroupType> types4 = company.getFundingGroupTypesOfCommunityProjectsInDepartment(dept4);
        List<FundingGroupType> types5 = company.getFundingGroupTypesOfCommunityProjectsInDepartment(dept5);
        
        // Verify results - departments should be isolated
        assertEquals("D004 should have 1 funding group type", 1, types4.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, types4.get(0));
        
        assertEquals("D005 should have 1 funding group type", 1, types5.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, types5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a valid department to ensure company is not empty
        Department validDept = new Department();
        validDept.setId("D001");
        validDept.setEmail("valid@example.com");
        company.addDepartment(validDept);
        
        // Create an invalid department (not added to company)
        Department invalidDept = new Department();
        invalidDept.setId("D999");
        invalidDept.setEmail("invalid@example.com");
        
        // Try to retrieve funding group types for invalid department
        try {
            company.getFundingGroupTypesOfCommunityProjectsInDepartment(invalidDept);
            fail("Expected IllegalArgumentException for invalid department");
        } catch (IllegalArgumentException e) {
            // Expected behavior - department not found in company
            assertEquals("Exception message should match", 
                         "The supplied department does not belong to this company.", 
                         e.getMessage());
        }
    }
}