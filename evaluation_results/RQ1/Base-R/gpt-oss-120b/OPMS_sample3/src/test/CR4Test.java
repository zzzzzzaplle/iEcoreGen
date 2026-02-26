import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR4Test {
    
    private Department department;
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // SetUp: Create department D001
        department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Create community project with Government Group funding
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudgetAmount(5000.0);
        communityProject.setDeadline(LocalDate.of(2025, 12, 31));
        
        GovernmentGroup fundingGroup = new GovernmentGroup();
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.getProjects().add(communityProject);
        
        // Execute: Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 1 Funding group type: "Government Group"
        assertEquals(1, result.size());
        assertEquals(FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department D002
        department = new Department();
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with Private Group funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline(LocalDate.of(2025, 11, 15));
        
        PrivateGroup fundingGroup1 = new PrivateGroup();
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with Mixed Group funding
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
        
        // Execute: Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There are 2 Funding group types: "Private Group", "Mixed Group"
        assertEquals(2, result.size());
        assertTrue(result.contains(FundingGroupType.PRIVATE));
        assertTrue(result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no projects
        department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Execute: Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create department D004
        Department departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project with Mixed Group funding for D004
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500.0);
        projectD004.setDeadline(LocalDate.of(2025, 12, 1));
        
        MixedGroup fundingGroupD004 = new MixedGroup();
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.getProjects().add(projectD004);
        
        // SetUp: Create department D005 (to validate isolation)
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project with Government Group funding for D005
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        
        GovernmentGroup fundingGroupD005 = new GovernmentGroup();
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.getProjects().add(projectD005);
        
        // Execute: Retrieve funding group types for both departments
        List<FundingGroupType> resultD004 = departmentD004.getFundingGroupTypesOfCommunityProjects();
        List<FundingGroupType> resultD005 = departmentD005.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 1 Funding group type in D004 : "Mixed Group"
        assertEquals(1, resultD004.size());
        assertEquals(FundingGroupType.MIXED, resultD004.get(0));
        
        // Verify: There is 1 Funding group type in D005 : "Government Group"
        assertEquals(1, resultD005.size());
        assertEquals(FundingGroupType.GOVERNMENT, resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department exists with ID: D999 (just check an empty department)
        department = new Department();
        department.setId("D999");
        
        // Execute: Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
}