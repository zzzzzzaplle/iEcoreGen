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
        // Create department D001
        department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudgetAmount(5000.0);
        communityProject.setDeadline("2025-12-31");
        
        // Create and set funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT_GROUP);
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(communityProject);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getCommunityProjectFundingGroupTypes();
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, fundingGroupTypes.size());
        assertEquals("Funding group type should be GOVERNMENT_GROUP", 
                    FundingGroupType.GOVERNMENT_GROUP, fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // Create department D002
        department = new Department();
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with PRIVATE_GROUP funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE_GROUP);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with MIXED_GROUP funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED_GROUP);
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getCommunityProjectFundingGroupTypes();
        
        // Verify results
        assertEquals("Should have exactly 2 funding group types", 2, fundingGroupTypes.size());
        assertTrue("Should contain PRIVATE_GROUP", fundingGroupTypes.contains(FundingGroupType.PRIVATE_GROUP));
        assertTrue("Should contain MIXED_GROUP", fundingGroupTypes.contains(FundingGroupType.MIXED_GROUP));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department D003 with no community projects
        department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Add a non-community project to ensure it doesn't affect the result
        ProductionProject productionProject = new ProductionProject();
        productionProject.setTitle("Manufacturing Project");
        productionProject.setBudgetAmount(10000.0);
        department.addProject(productionProject);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getCommunityProjectFundingGroupTypes();
        
        // Verify results
        assertNotNull("Funding group types list should not be null", fundingGroupTypes);
        assertEquals("Should have 0 funding group types when no community projects exist", 
                    0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // Create department D004
        Department departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project for D004 with MIXED_GROUP funding
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500.0);
        projectD004.setDeadline("2025-12-01");
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED_GROUP);
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // Create department D005
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project for D005 with GOVERNMENT_GROUP funding
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setBudgetAmount(6000.0);
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT_GROUP);
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Add both departments to company
        company.addDepartment(departmentD004);
        company.addDepartment(departmentD005);
        
        // Retrieve funding group types for each department
        List<FundingGroupType> fundingGroupTypesD004 = departmentD004.getCommunityProjectFundingGroupTypes();
        List<FundingGroupType> fundingGroupTypesD005 = departmentD005.getCommunityProjectFundingGroupTypes();
        
        // Verify results for D004
        assertEquals("D004 should have exactly 1 funding group type", 1, fundingGroupTypesD004.size());
        assertEquals("D004 funding group type should be MIXED_GROUP", 
                    FundingGroupType.MIXED_GROUP, fundingGroupTypesD004.get(0));
        
        // Verify results for D005
        assertEquals("D005 should have exactly 1 funding group type", 1, fundingGroupTypesD005.size());
        assertEquals("D005 funding group type should be GOVERNMENT_GROUP", 
                    FundingGroupType.GOVERNMENT_GROUP, fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a department with ID D001
        Department validDepartment = new Department();
        validDepartment.setId("D001");
        validDepartment.setEmail("department1@example.com");
        
        // Create a community project for the valid department
        CommunityProject project = new CommunityProject();
        project.setTitle("Valid Project");
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.PRIVATE_GROUP);
        project.setFundingGroup(fundingGroup);
        
        validDepartment.addProject(project);
        
        // Create a new department to simulate the invalid department D999
        // Since we can't directly access a non-existent department, we'll test that
        // a department without community projects returns empty list
        Department invalidDepartment = new Department();
        invalidDepartment.setId("D999");
        
        // Retrieve funding group types for the invalid department
        List<FundingGroupType> fundingGroupTypes = invalidDepartment.getCommunityProjectFundingGroupTypes();
        
        // Verify results - invalid department should have no community projects
        assertNotNull("Funding group types list should not be null", fundingGroupTypes);
        assertEquals("Invalid department D999 should have 0 funding group types", 
                    0, fundingGroupTypes.size());
    }
}