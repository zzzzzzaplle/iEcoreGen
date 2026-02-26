import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Department department;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // Create a department with ID: D001 and email: "department1@example.com"
        Department dept = new Department();
        dept.setID("D001");
        dept.setEmail("department1@example.com");
        
        // Create a community project in department D001
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        project.setDeadline(dateFormat.parse("2025-12-31"));
        
        // Assign the funding group type "Government Group" to the community project
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project.setFundingGroup(fundingGroup);
        
        dept.addProject(project);
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify there is 1 Funding group type: "Government Group"
        assertEquals(1, result.size());
        assertEquals(FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create a department with ID: D002 and email: "department2@example.com"
        Department dept = new Department();
        dept.setID("D002");
        dept.setEmail("department2@example.com");
        
        // Create first community project with Private Group funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with Mixed Group funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        dept.addProject(project1);
        dept.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify there are 2 Funding group types: "Private Group", "Mixed Group"
        assertEquals(2, result.size());
        assertTrue(result.contains(FundingGroupType.PRIVATE));
        assertTrue(result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create a department with ID: D003 and email: "department3@example.com"
        Department dept = new Department();
        dept.setID("D003");
        dept.setEmail("department3@example.com");
        
        // Ensure no community projects are created in department D003
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify there is 0 Funding group type
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Create department D004 with Mixed Group community project
        Department deptD004 = new Department();
        deptD004.setID("D004");
        deptD004.setEmail("department4@example.com");
        
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        projectD004.setDeadline(dateFormat.parse("2025-12-01"));
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFundingGroup(fundingGroupD004);
        
        deptD004.addProject(projectD004);
        
        // Create department D005 with Government Group community project
        Department deptD005 = new Department();
        deptD005.setID("D005");
        deptD005.setEmail("department5@example.com");
        
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Library improvement project");
        projectD005.setBudget(6000.0);
        projectD005.setDeadline(dateFormat.parse("2025-11-30"));
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFundingGroup(fundingGroupD005);
        
        deptD005.addProject(projectD005);
        
        // Retrieve funding group types for both departments
        List<FundingGroupType> resultD004 = deptD004.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> resultD005 = deptD005.getFundingGroupTypeCommunityProjects();
        
        // Verify D004 has 1 Funding group type: "Mixed Group"
        assertEquals(1, resultD004.size());
        assertEquals(FundingGroupType.MIXED, resultD004.get(0));
        
        // Verify D005 has 1 Funding group type: "Government Group"
        assertEquals(1, resultD005.size());
        assertEquals(FundingGroupType.GOVERNMENT, resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a new empty department (simulating non-existent D999)
        Department dept = new Department();
        dept.setID("D999");
        dept.setEmail("nonexistent@example.com");
        
        // Retrieve funding group types (department exists but has no projects)
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify there is 0 Funding group type
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
}