import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR4Test {
    
    private Department department;
    
    @Before
    public void setUp() {
        department = new Department();
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // SetUp: Create department with ID: D001
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        project.setDeadline(sdf.parse("2025-12-31"));
        
        // Assign funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(project);
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify result
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // SetUp: Create department with ID: D002
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Create first community project
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(sdf.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(sdf.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify result
        assertEquals("Should have exactly 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE funding group type", result.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding group type", result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department with ID: D003
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // No community projects added
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify result
        assertEquals("Should have 0 funding group types when no community projects exist", 0, result.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // SetUp: Create department D004
        Department departmentD004 = new Department();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@example.com");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Create community project for D004
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        projectD004.setDeadline(sdf.parse("2025-12-01"));
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.addProject(projectD004);
        
        // Create department D005 with different project
        Department departmentD005 = new Department();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@example.com");
        
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Improving library facilities");
        projectD005.setBudget(10000.0);
        projectD005.setDeadline(sdf.parse("2025-11-30"));
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.addProject(projectD005);
        
        // Retrieve funding group types for each department
        List<FundingGroupType> resultD004 = departmentD004.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> resultD005 = departmentD005.getFundingGroupTypeCommunityProjects();
        
        // Verify results are isolated
        assertEquals("D004 should have exactly 1 funding group type", 1, resultD004.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, resultD004.get(0));
        
        assertEquals("D005 should have exactly 1 funding group type", 1, resultD005.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: Create a department but don't use it
        Department validDepartment = new Department();
        validDepartment.setID("D001");
        validDepartment.setEmail("department1@example.com");
        
        // Create a new department that represents the invalid department D999
        Department invalidDepartment = new Department();
        invalidDepartment.setID("D999");
        
        // Retrieve funding group types for invalid department
        List<FundingGroupType> result = invalidDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify result
        assertEquals("Invalid department should have 0 funding group types", 0, result.size());
    }
}