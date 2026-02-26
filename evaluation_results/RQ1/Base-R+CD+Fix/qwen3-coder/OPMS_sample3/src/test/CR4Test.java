import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
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
        // Create department D001
        department = new Department();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject project = new CommunityProject();
        project.setTitle("Community Clean-Up");
        project.setDescription("A project to clean the local park");
        project.setBudget(5000.0);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline = dateFormat.parse("2025-12-31");
        project.setDeadline(deadline);
        
        // Create and assign funding group
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        project.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.addProject(project);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals(1, fundingTypes.size());
        assertEquals(FundingGroupType.GOVERNMENT, fundingTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create department D002
        department = new Department();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Create first community project
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        Date deadline1 = dateFormat.parse("2025-11-15");
        project1.setDeadline(deadline1);
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        Date deadline2 = dateFormat.parse("2025-10-20");
        project2.setDeadline(deadline2);
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add projects to department
        department.addProject(project1);
        department.addProject(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals(2, fundingTypes.size());
        assertTrue(fundingTypes.contains(FundingGroupType.PRIVATE));
        assertTrue(fundingTypes.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department D003
        department = new Department();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // No community projects added to department
        
        // Retrieve funding group types
        List<FundingGroupType> fundingTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals(0, fundingTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Create department D004
        Department department4 = new Department();
        department4.setID("D004");
        department4.setEmail("department4@example.com");
        
        // Create community project for D004
        CommunityProject project4 = new CommunityProject();
        project4.setTitle("Neighborhood Beautification");
        project4.setDescription("Enhancing community space");
        project4.setBudget(7500.0);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline4 = dateFormat.parse("2025-12-01");
        project4.setDeadline(deadline4);
        
        FundingGroup fundingGroup4 = new FundingGroup();
        fundingGroup4.setType(FundingGroupType.MIXED);
        project4.setFundingGroup(fundingGroup4);
        
        department4.addProject(project4);
        
        // Create department D005
        Department department5 = new Department();
        department5.setID("D005");
        department5.setEmail("department5@example.com");
        
        // Create community project for D005
        CommunityProject project5 = new CommunityProject();
        project5.setTitle("Local Library Improvement");
        project5.setDescription("Improving library facilities");
        project5.setBudget(6000.0);
        
        Date deadline5 = dateFormat.parse("2025-11-30");
        project5.setDeadline(deadline5);
        
        FundingGroup fundingGroup5 = new FundingGroup();
        fundingGroup5.setType(FundingGroupType.GOVERNMENT);
        project5.setFundingGroup(fundingGroup5);
        
        department5.addProject(project5);
        
        // Retrieve funding group types for each department
        List<FundingGroupType> fundingTypes4 = department4.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> fundingTypes5 = department5.getFundingGroupTypeCommunityProjects();
        
        // Verify results for D004
        assertEquals(1, fundingTypes4.size());
        assertEquals(FundingGroupType.MIXED, fundingTypes4.get(0));
        
        // Verify results for D005
        assertEquals(1, fundingTypes5.size());
        assertEquals(FundingGroupType.GOVERNMENT, fundingTypes5.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a valid department to ensure the system has departments
        Department validDepartment = new Department();
        validDepartment.setID("D001");
        validDepartment.setEmail("valid@example.com");
        
        company.addDepartment(validDepartment);
        
        // Try to find department with ID D999 (which doesn't exist in company)
        Department nonExistentDepartment = null;
        for (Department dept : company.getDepartments()) {
            if ("D999".equals(dept.getID())) {
                nonExistentDepartment = dept;
                break;
            }
        }
        
        // Since D999 doesn't exist, we need to handle this case
        if (nonExistentDepartment == null) {
            // Create a new department object to simulate the invalid case
            Department invalidDepartment = new Department();
            invalidDepartment.setID("D999");
            
            // Retrieve funding group types from invalid department
            List<FundingGroupType> fundingTypes = invalidDepartment.getFundingGroupTypeCommunityProjects();
            
            // Verify results
            assertEquals(0, fundingTypes.size());
        }
    }
}