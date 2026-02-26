import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR4Test {
    private Department department;
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleCommunityProject() throws Exception {
        // Create department with ID: D001
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        
        // Set deadline: 2025-12-31
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date deadline = sdf.parse("2025-12-31 23:59:59");
        project.setDeadline(deadline);
        
        // Create and assign funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(project);
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output
        assertEquals("Should have 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_MultipleCommunityProjects() throws Exception {
        // Create department with ID: D002
        department = new Department();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Create first community project
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(sdf.parse("2025-11-15 23:59:59"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        department.addProject(project1);
        
        // Create second community project
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(sdf.parse("2025-10-20 23:59:59"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output
        assertEquals("Should have 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE type", result.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED type", result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjects() {
        // Create department with ID: D003
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // No projects added to department
        
        // Retrieve funding group types
        List<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output
        assertEquals("Should have 0 funding group types", 0, result.size());
    }
    
    @Test
    public void testCase4_MultipleDepartmentsIsolation() throws Exception {
        // Create first department D004
        Department department4 = new Department();
        department4.setID("D004");
        department4.setEmail("department4@example.com");
        
        // Create community project for D004
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudget(7500.0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        project4.setDeadline(sdf.parse("2025-12-01 23:59:59"));
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setType(FundingGroupType.MIXED);
        project4.setFundingGroup(fundingGroup4);
        department4.addProject(project4);
        
        // Create second department D005
        Department department5 = new Department();
        department5.setID("D005");
        department5.setEmail("department5@example.com");
        
        // Create community project for D005
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improving library facilities");
        project5.setBudget(6000.0);
        project5.setDeadline(sdf.parse("2025-11-30 23:59:59"));
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setType(FundingGroupType.GOVERNMENT);
        project5.setFundingGroup(fundingGroup5);
        department5.addProject(project5);
        
        // Add both departments to company
        company.addDepartment(department4);
        company.addDepartment(department5);
        
        // Retrieve funding group types from each department
        List<FundingGroupType> result4 = department4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> result5 = department5.getFundingGroupTypeCommunityProjects();
        
        // Verify isolation - each department should only see its own projects
        assertEquals("D004 should have 1 funding group type", 1, result4.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, result4.get(0));
        
        assertEquals("D005 should have 1 funding group type", 1, result5.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result5.get(0));
    }
    
    @Test
    public void testCase5_InvalidDepartmentID() {
        // Create a department with different ID (not D999)
        Department existingDepartment = new Department();
        existingDepartment.setID("D001");
        existingDepartment.setEmail("valid@example.com");
        
        // Attempt to retrieve from non-existent department (D999)
        // Since we can't access a non-existent department directly, we'll test with a new department
        Department invalidDepartment = new Department();
        invalidDepartment.setID("D999");
        
        // Retrieve funding group types from the invalid department (which has no projects)
        List<FundingGroupType> result = invalidDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output
        assertEquals("Invalid department should have 0 funding group types", 0, result.size());
    }
}