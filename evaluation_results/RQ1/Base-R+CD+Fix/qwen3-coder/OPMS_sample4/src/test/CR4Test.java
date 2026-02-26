import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        // Create a department with ID: D001 and email: "department1@example.com"
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create a community project in department D001
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline = dateFormat.parse("2025-12-31");
        communityProject.setDeadline(deadline);
        
        // Assign the funding group type "Government Group" to the community project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setName("Government Group");
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(communityProject);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: There is 1 Funding group type: "Government Group"
        assertEquals("Should have exactly 1 funding group type", 1, fundingGroupTypes.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create a department with ID: D002 and email: "department2@example.com"
        department = new Department();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Create first community project: "Food Drive" with funding group type "Private Group"
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setName("Private Group");
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project: "Health Awareness Campaign" with funding group type "Mixed Group"
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setName("Mixed Group");
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: There are 2 Funding group type: "Private Group", "Mixed Group"
        assertEquals("Should have exactly 2 funding group types", 2, fundingGroupTypes.size());
        assertTrue("Should contain PRIVATE funding group type", fundingGroupTypes.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding group type", fundingGroupTypes.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create a department with ID: D003 and email: "department3@example.com"
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Ensure no community projects are created in department D003
        // (no projects added to department)
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: There is 0 Funding group type
        assertEquals("Should have 0 funding group types when no community projects exist", 0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Create department D004 with community project
        Department departmentD004 = new Department();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@example.com");
        
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        projectD004.setDeadline(dateFormat.parse("2025-12-01"));
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setName("Mixed Group");
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // Create different department D005 with community project
        Department departmentD005 = new Department();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@example.com");
        
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setName("Government Group");
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Add both departments to company
        company.addDepartment(departmentD004);
        company.addDepartment(departmentD005);
        
        // Retrieve funding group types for D004
        List<FundingGroupType> fundingGroupTypesD004 = departmentD004.getFundingGroupTypeCommunityProjects();
        
        // Retrieve funding group types for D005
        List<FundingGroupType> fundingGroupTypesD005 = departmentD005.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: There is 1 Funding group type in D004 : "Mixed Group"
        assertEquals("D004 should have exactly 1 funding group type", 1, fundingGroupTypesD004.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, fundingGroupTypesD004.get(0));
        
        // Verify expected output: There is 1 Funding group type in D005 : "Government Group"
        assertEquals("D005 should have exactly 1 funding group type", 1, fundingGroupTypesD005.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a department with valid ID (to demonstrate isolation)
        Department validDepartment = new Department();
        validDepartment.setID("D001");
        
        // For invalid department ID D999, we need to simulate that no such department exists
        // Since we can't retrieve a non-existent department, we'll test with an empty department
        Department invalidDepartment = new Department();
        invalidDepartment.setID("D999"); // This department exists but has no projects
        
        // Retrieve funding group types for invalid department (empty department)
        List<FundingGroupType> fundingGroupTypes = invalidDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: There is 0 Funding group type
        assertEquals("Invalid department should have 0 funding group types", 0, fundingGroupTypes.size());
    }
}