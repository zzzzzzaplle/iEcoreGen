import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR4Test {
    
    private Department departmentD001;
    private Department departmentD002;
    private Department departmentD003;
    private Department departmentD004;
    private Department departmentD005;
    
    @Before
    public void setUp() {
        // Initialize departments for reuse across test cases
        departmentD001 = new Department();
        departmentD001.setDepartmentId("D001");
        departmentD001.setEmail("department1@example.com");
        
        departmentD002 = new Department();
        departmentD002.setDepartmentId("D002");
        departmentD002.setEmail("department2@example.com");
        
        departmentD003 = new Department();
        departmentD003.setDepartmentId("D003");
        departmentD003.setEmail("department3@example.com");
        
        departmentD004 = new Department();
        departmentD004.setDepartmentId("D004");
        departmentD004.setEmail("department4@example.com");
        
        departmentD005 = new Department();
        departmentD005.setDepartmentId("D005");
        departmentD005.setEmail("department5@example.com");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // SetUp: Create department D001 with one community project having Government Group funding
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudgetAmount(5000.0);
        communityProject.setDeadline(LocalDate.of(2025, 12, 31));
        
        GovernmentGroup fundingGroup = new GovernmentGroup();
        communityProject.setFundingGroup(fundingGroup);
        
        departmentD001.getProjects().add(communityProject);
        
        // Execute: Retrieve funding group types for community projects in department D001
        List<String> result = departmentD001.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 1 Funding group type: "GOVERNMENT"
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", "GOVERNMENT", result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department D002 with two community projects having different funding groups
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline(LocalDate.of(2025, 11, 15));
        
        PrivateGroup privateGroup = new PrivateGroup();
        project1.setFundingGroup(privateGroup);
        
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline(LocalDate.of(2025, 10, 20));
        
        MixedGroup mixedGroup = new MixedGroup();
        project2.setFundingGroup(mixedGroup);
        
        departmentD002.getProjects().add(project1);
        departmentD002.getProjects().add(project2);
        
        // Execute: Retrieve funding group types for community projects in department D002
        List<String> result = departmentD002.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There are 2 Funding group types: "PRIVATE", "MIXED"
        assertEquals("Should have exactly 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE funding group type", result.contains("PRIVATE"));
        assertTrue("Should contain MIXED funding group type", result.contains("MIXED"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Department D003 has no projects (empty by default)
        
        // Execute: Retrieve funding group types for community projects in department D003
        List<String> result = departmentD003.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals("Should have 0 funding group types when no community projects exist", 0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create department D004 with Mixed Group community project
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500.0);
        projectD004.setDeadline(LocalDate.of(2025, 12, 1));
        
        MixedGroup mixedGroup = new MixedGroup();
        projectD004.setFundingGroup(mixedGroup);
        departmentD004.getProjects().add(projectD004);
        
        // SetUp: Create department D005 with Government Group community project
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Improve library facilities");
        projectD005.setBudgetAmount(6000.0);
        projectD005.setDeadline(LocalDate.of(2025, 11, 30));
        
        GovernmentGroup governmentGroup = new GovernmentGroup();
        projectD005.setFundingGroup(governmentGroup);
        departmentD005.getProjects().add(projectD005);
        
        // Execute: Retrieve funding group types for both departments
        List<String> resultD004 = departmentD004.getFundingGroupTypesOfCommunityProjects();
        List<String> resultD005 = departmentD005.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: D004 has 1 Funding group type: "MIXED", D005 has 1 Funding group type: "GOVERNMENT"
        assertEquals("Department D004 should have exactly 1 funding group type", 1, resultD004.size());
        assertEquals("Department D004 funding group type should be MIXED", "MIXED", resultD004.get(0));
        
        assertEquals("Department D005 should have exactly 1 funding group type", 1, resultD005.size());
        assertEquals("Department D005 funding group type should be GOVERNMENT", "GOVERNMENT", resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: Create a new department that doesn't match D999 (no setup needed since we'll test with empty department)
        Department departmentD999 = new Department();
        departmentD999.setDepartmentId("D999");
        departmentD999.setEmail("department999@example.com");
        
        // Execute: Retrieve funding group types for department with ID D999 (no projects)
        List<String> result = departmentD999.getFundingGroupTypesOfCommunityProjects();
        
        // Verify: There is 0 Funding group type
        assertEquals("Should have 0 funding group types for department with no community projects", 0, result.size());
    }
}