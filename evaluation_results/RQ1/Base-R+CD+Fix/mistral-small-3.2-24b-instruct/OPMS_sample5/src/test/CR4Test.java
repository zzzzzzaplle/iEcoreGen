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
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // SetUp: Create department D001
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create community project with Government Group funding
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        project.setDeadline(sdf.parse("2025-12-31"));
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project.setFundingGroup(fundingGroup);
        
        // Add project to department and department to company
        department.addProject(project);
        company.addDepartment(department);
        
        // Retrieve funding group types for department D001
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 1 Funding group type: "Government Group"
        assertEquals(1, result.size());
        assertEquals(FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // SetUp: Create department D002
        department = new Department();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Create first community project with Private Group funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(sdf.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with Mixed Group funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(sdf.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department and department to company
        department.addProject(project1);
        department.addProject(project2);
        company.addDepartment(department);
        
        // Retrieve funding group types for department D002
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 2 Funding group types: "Private Group", "Mixed Group"
        assertEquals(2, result.size());
        assertTrue(result.contains(FundingGroupType.PRIVATE));
        assertTrue(result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        company.addDepartment(department);
        
        // Retrieve funding group types for department D003
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 0 Funding group types
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // SetUp: Create department D004
        Department department4 = new Department();
        department4.setID("D004");
        department4.setEmail("department4@example.com");
        
        // Create community project for D004 with Mixed Group funding
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudget(7500.0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        project4.setDeadline(sdf.parse("2025-12-01"));
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setType(FundingGroupType.MIXED);
        project4.setFundingGroup(fundingGroup4);
        
        department4.addProject(project4);
        company.addDepartment(department4);
        
        // Create department D005 with different community project
        Department department5 = new Department();
        department5.setID("D005");
        department5.setEmail("department5@example.com");
        
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improving library facilities");
        project5.setBudget(10000.0);
        project5.setDeadline(sdf.parse("2025-11-30"));
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setType(FundingGroupType.GOVERNMENT);
        project5.setFundingGroup(fundingGroup5);
        
        department5.addProject(project5);
        company.addDepartment(department5);
        
        // Retrieve funding group types for both departments
        List<FundingGroupType> result4 = department4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> result5 = department5.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: D004 has 1 Funding group type: "Mixed Group", D005 has 1: "Government Group"
        assertEquals(1, result4.size());
        assertEquals(FundingGroupType.MIXED, result4.get(0));
        
        assertEquals(1, result5.size());
        assertEquals(FundingGroupType.GOVERNMENT, result5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: No department with ID D999 exists
        // Create a different department to ensure company has departments but not D999
        Department validDepartment = new Department();
        validDepartment.setID("D100");
        validDepartment.setEmail("valid@example.com");
        company.addDepartment(validDepartment);
        
        // Create a new department object with invalid ID to simulate retrieval attempt
        Department invalidDepartment = new Department();
        invalidDepartment.setID("D999");
        
        // Retrieve funding group types for non-existent department D999
        List<FundingGroupType> result = invalidDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: 0 Funding group types
        assertEquals(0, result.size());
    }
}