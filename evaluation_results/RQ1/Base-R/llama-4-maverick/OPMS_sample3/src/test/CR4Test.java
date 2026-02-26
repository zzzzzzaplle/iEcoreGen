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
        // SetUp: Create department D001
        department = new Department();
        department.setId("D001");
        department.setEmail("department1@example.com");
        
        // Create community project with funding group
        CommunityProject communityProject = new CommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudgetAmount(5000.0);
        communityProject.setDeadline("2025-12-31");
        
        FundingGroup fundingGroup = new FundingGroup();
        fundingGroup.setType("Government Group");
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        List<Project> projects = new ArrayList<>();
        projects.add(communityProject);
        department.setProjects(projects);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals("Should have exactly 1 funding group type", 1, fundingGroupTypes.size());
        assertEquals("Funding group type should be 'Government Group'", "Government Group", fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // SetUp: Create department D002
        department = new Department();
        department.setId("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project
        CommunityProject project1 = new CommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudgetAmount(3000.0);
        project1.setDeadline("2025-11-15");
        
        FundingGroup fundingGroup1 = new FundingGroup();
        fundingGroup1.setType("Private Group");
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project
        CommunityProject project2 = new CommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudgetAmount(2000.0);
        project2.setDeadline("2025-10-20");
        
        FundingGroup fundingGroup2 = new FundingGroup();
        fundingGroup2.setType("Mixed Group");
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        department.setProjects(projects);
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals("Should have exactly 2 funding group types", 2, fundingGroupTypes.size());
        assertTrue("Should contain 'Private Group'", fundingGroupTypes.contains("Private Group"));
        assertTrue("Should contain 'Mixed Group'", fundingGroupTypes.contains("Mixed Group"));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // SetUp: Create department D003 with no community projects
        department = new Department();
        department.setId("D003");
        department.setEmail("department3@example.com");
        
        // Ensure no projects are added
        department.setProjects(new ArrayList<>());
        
        // Retrieve funding group types
        List<String> fundingGroupTypes = department.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals("Should have 0 funding group types when no community projects exist", 0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // SetUp: Create department D004
        Department departmentD004 = new Department();
        departmentD004.setId("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project for D004
        CommunityProject projectD004 = new CommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudgetAmount(7500.0);
        projectD004.setDeadline("2025-12-01");
        
        FundingGroup fundingGroupD004 = new FundingGroup();
        fundingGroupD004.setType("Mixed Group");
        projectD004.setFundingGroup(fundingGroupD004);
        
        List<Project> projectsD004 = new ArrayList<>();
        projectsD004.add(projectD004);
        departmentD004.setProjects(projectsD004);
        
        // Create department D005
        Department departmentD005 = new Department();
        departmentD005.setId("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project for D005
        CommunityProject projectD005 = new CommunityProject();
        projectD005.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroupD005 = new FundingGroup();
        fundingGroupD005.setType("Government Group");
        projectD005.setFundingGroup(fundingGroupD005);
        
        List<Project> projectsD005 = new ArrayList<>();
        projectsD005.add(projectD005);
        departmentD005.setProjects(projectsD005);
        
        // Add both departments to company for isolation validation
        List<Department> departments = new ArrayList<>();
        departments.add(departmentD004);
        departments.add(departmentD005);
        company.setDepartments(departments);
        
        // Retrieve funding group types for D004
        List<String> fundingGroupTypesD004 = departmentD004.getFundingGroupTypesOfCommunityProjects();
        
        // Retrieve funding group types for D005
        List<String> fundingGroupTypesD005 = departmentD005.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output for D004
        assertEquals("D004 should have exactly 1 funding group type", 1, fundingGroupTypesD004.size());
        assertEquals("D004 funding group type should be 'Mixed Group'", "Mixed Group", fundingGroupTypesD004.get(0));
        
        // Verify expected output for D005
        assertEquals("D005 should have exactly 1 funding group type", 1, fundingGroupTypesD005.size());
        assertEquals("D005 funding group type should be 'Government Group'", "Government Group", fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // SetUp: Assume no department exists with ID D999
        // Create a different department to ensure isolation
        Department validDepartment = new Department();
        validDepartment.setId("D100");
        validDepartment.setEmail("valid@example.com");
        
        // Add some projects to valid department to ensure isolation
        CommunityProject validProject = new CommunityProject();
        FundingGroup validFundingGroup = new FundingGroup();
        validFundingGroup.setType("Valid Group");
        validProject.setFundingGroup(validFundingGroup);
        
        List<Project> validProjects = new ArrayList<>();
        validProjects.add(validProject);
        validDepartment.setProjects(validProjects);
        
        company.getDepartments().add(validDepartment);
        
        // Create a new department object with invalid ID but don't add it to company
        Department invalidDepartment = new Department();
        invalidDepartment.setId("D999");
        invalidDepartment.setProjects(new ArrayList<>()); // No projects
        
        // Retrieve funding group types for invalid department
        List<String> fundingGroupTypes = invalidDepartment.getFundingGroupTypesOfCommunityProjects();
        
        // Verify expected output
        assertEquals("Should have 0 funding group types for invalid department ID", 0, fundingGroupTypes.size());
    }
}