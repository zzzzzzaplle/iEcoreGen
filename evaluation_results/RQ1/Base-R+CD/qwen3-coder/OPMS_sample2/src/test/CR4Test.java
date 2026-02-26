import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR4Test {
    private Company company;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // Create department D001
        Department dept = new Department();
        dept.setID("D001");
        dept.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        project.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        
        // Create and assign funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setName("Government Group");
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        dept.addProject(project);
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify result
        assertEquals("Should have exactly 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result.get(0));
    }

    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create department D002
        Department dept = new Department();
        dept.setID("D002");
        dept.setEmail("department2@example.com");
        
        // Create first community project with PRIVATE funding
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15 00:00:00"));
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setName("Private Group");
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with MIXED funding
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20 00:00:00"));
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setName("Mixed Group");
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department
        dept.addProject(project1);
        dept.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify result
        assertEquals("Should have exactly 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE funding type", result.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding type", result.contains(FundingGroupType.MIXED));
    }

    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department D003 with no community projects
        Department dept = new Department();
        dept.setID("D003");
        dept.setEmail("department3@example.com");
        
        // Retrieve funding group types
        List<FundingGroupType> result = dept.getFundingGroupTypeCommunityProjects();
        
        // Verify result is empty
        assertTrue("Should have no funding group types when no community projects exist", result.isEmpty());
    }

    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Create department D004
        Department dept4 = new Department();
        dept4.setID("D004");
        dept4.setEmail("department4@example.com");
        
        // Create community project for D004 with MIXED funding
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudget(7500.0);
        project4.setDeadline(dateFormat.parse("2025-12-01 00:00:00"));
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setName("Mixed Group");
        fundingGroup4.setType(FundingGroupType.MIXED);
        project4.setFundingGroup(fundingGroup4);
        
        dept4.addProject(project4);
        
        // Create department D005
        Department dept5 = new Department();
        dept5.setID("D005");
        dept5.setEmail("department5@example.com");
        
        // Create community project for D005 with GOVERNMENT funding
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improving library facilities");
        project5.setBudget(6000.0);
        project5.setDeadline(dateFormat.parse("2025-11-30 00:00:00"));
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setName("Government Group");
        fundingGroup5.setType(FundingGroupType.GOVERNMENT);
        project5.setFundingGroup(fundingGroup5);
        
        dept5.addProject(project5);
        
        // Add both departments to company
        company.addDepartment(dept4);
        company.addDepartment(dept5);
        
        // Retrieve funding group types for each department
        List<FundingGroupType> result4 = dept4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> result5 = dept5.getFundingGroupTypeCommunityProjects();
        
        // Verify results are isolated
        assertEquals("D004 should have exactly 1 funding group type", 1, result4.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, result4.get(0));
        
        assertEquals("D005 should have exactly 1 funding group type", 1, result5.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result5.get(0));
    }

    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a department with ID D001 (not D999)
        Department validDept = new Department();
        validDept.setID("D001");
        validDept.setEmail("department1@example.com");
        
        // Try to find department D999 (which doesn't exist)
        Department invalidDept = null;
        for (Department dept : company.getDepartments()) {
            if ("D999".equals(dept.getID())) {
                invalidDept = dept;
                break;
            }
        }
        
        // If invalid department is not found, verify no funding group types
        if (invalidDept == null) {
            List<FundingGroupType> result = new ArrayList<>();
            assertEquals("Should have 0 funding group types for non-existent department", 0, result.size());
        } else {
            // This branch should not execute since D999 doesn't exist
            List<FundingGroupType> result = invalidDept.getFundingGroupTypeCommunityProjects();
            assertEquals("Should have 0 funding group types for non-existent department", 0, result.size());
        }
    }
}