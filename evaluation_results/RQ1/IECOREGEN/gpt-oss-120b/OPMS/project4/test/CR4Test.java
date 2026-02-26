package edu.project.project4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.project.ProjectFactory;
import edu.project.ProjectPackage;
import edu.project.Department;
import edu.project.CommunityProject;
import edu.project.FundingGroup;
import edu.project.FundingGroupType;
import java.util.List;

public class CR4Test {
    
    private ProjectFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory
        factory = ProjectFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // Create department with ID: D001
        Department department = factory.createDepartment();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject communityProject = factory.createCommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        
        // Create funding group with Government type
        FundingGroup fundingGroup = factory.createFundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        
        // Set up bidirectional relationship
        communityProject.setFudingGroup(fundingGroup);
        
        // Add project to department
        department.getProjects().add(communityProject);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, fundingGroupTypes.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // Create department with ID: D002
        Department department = factory.createDepartment();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with Private funding group
        CommunityProject project1 = factory.createCommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        
        FundingGroup fundingGroup1 = factory.createFundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFudingGroup(fundingGroup1);
        
        // Create second community project with Mixed funding group
        CommunityProject project2 = factory.createCommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        
        FundingGroup fundingGroup2 = factory.createFundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFudingGroup(fundingGroup2);
        
        // Add both projects to department
        department.getProjects().add(project1);
        department.getProjects().add(project2);
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 2 funding group types", 2, fundingGroupTypes.size());
        assertTrue("Should contain PRIVATE funding group type", fundingGroupTypes.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding group type", fundingGroupTypes.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department with ID: D003
        Department department = factory.createDepartment();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // No community projects created - department has empty projects list
        
        // Retrieve funding group types
        List<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertNotNull("Funding group types list should not be null", fundingGroupTypes);
        assertEquals("Should have 0 funding group types when no community projects exist", 0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // Create first department with ID: D004
        Department departmentD004 = factory.createDepartment();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project for D004 with Mixed funding group
        CommunityProject projectD004 = factory.createCommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        
        FundingGroup fundingGroupD004 = factory.createFundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFudingGroup(fundingGroupD004);
        
        departmentD004.getProjects().add(projectD004);
        
        // Create second department with ID: D005
        Department departmentD005 = factory.createDepartment();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project for D005 with Government funding group
        CommunityProject projectD005 = factory.createCommunityProject();
        projectD005.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroupD005 = factory.createFundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFudingGroup(fundingGroupD005);
        
        departmentD005.getProjects().add(projectD005);
        
        // Retrieve funding group types for both departments
        List<FundingGroupType> fundingGroupTypesD004 = departmentD004.getFundingGroupTypeCommunityProjects();
        List<FundingGroupType> fundingGroupTypesD005 = departmentD005.getFundingGroupTypeCommunityProjects();
        
        // Verify results are isolated between departments
        assertEquals("D004 should have exactly 1 funding group type", 1, fundingGroupTypesD004.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, fundingGroupTypesD004.get(0));
        
        assertEquals("D005 should have exactly 1 funding group type", 1, fundingGroupTypesD005.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a valid department (not D999) to ensure test isolation
        Department validDepartment = factory.createDepartment();
        validDepartment.setID("D100");
        validDepartment.setEmail("department100@example.com");
        
        // For invalid department ID D999, we don't create any department object
        // In a real scenario, we would query a repository, but here we simulate no department found
        
        // Create a new empty department object to simulate the "invalid" case
        // In actual implementation, this would be a null or empty result from repository lookup
        Department invalidDepartment = null;
        
        // Since we can't test with a non-existent department in this isolated test,
        // we'll test with an empty department that has no projects
        Department emptyDepartment = factory.createDepartment();
        emptyDepartment.setID("D999");
        
        // Retrieve funding group types for the empty department
        List<FundingGroupType> fundingGroupTypes = emptyDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertNotNull("Funding group types list should not be null even for empty department", fundingGroupTypes);
        assertEquals("Should have 0 funding group types for department with no projects", 0, fundingGroupTypes.size());
    }
}