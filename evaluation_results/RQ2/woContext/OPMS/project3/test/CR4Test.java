package edu.project.project3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.project.ProjectFactory;
import edu.project.ProjectPackage;
import edu.project.Department;
import edu.project.CommunityProject;
import edu.project.FundingGroup;
import edu.project.FundingGroupType;
import org.eclipse.emf.common.util.EList;

public class CR4Test {
    
    private ProjectFactory factory;
    
    @Before
    public void setUp() {
        factory = ProjectFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() {
        // Create a department with ID: D001 and email: "department1@example.com"
        Department department = factory.createDepartment();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create a community project in department D001
        CommunityProject communityProject = factory.createCommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        
        // Create funding group with type "Government Group"
        FundingGroup fundingGroup = factory.createFundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        
        // Set up bidirectional references
        communityProject.setFudingGroup(fundingGroup);
        department.getProjects().add(communityProject);
        
        // Retrieve funding group types for community projects in department D001
        EList<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: There is 1 Funding group type: "Government Group"
        assertEquals(1, fundingGroupTypes.size());
        assertEquals(FundingGroupType.GOVERNMENT, fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() {
        // Create a department with ID: D002 and email: "department2@example.com"
        Department department = factory.createDepartment();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with funding group type "Private Group"
        CommunityProject project1 = factory.createCommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        
        FundingGroup fundingGroup1 = factory.createFundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFudingGroup(fundingGroup1);
        department.getProjects().add(project1);
        
        // Create second community project with funding group type "Mixed Group"
        CommunityProject project2 = factory.createCommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        
        FundingGroup fundingGroup2 = factory.createFundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFudingGroup(fundingGroup2);
        department.getProjects().add(project2);
        
        // Retrieve funding group types for community projects in department D002
        EList<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: There are 2 Funding group type: "Private Group", "Mixed Group"
        assertEquals(2, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.contains(FundingGroupType.PRIVATE));
        assertTrue(fundingGroupTypes.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create a department with ID: D003 and email: "department3@example.com"
        Department department = factory.createDepartment();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Ensure no community projects are created in department D003
        // Retrieve funding group types for community projects in department D003
        EList<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() {
        // Create department D004 with email: "department4@example.com"
        Department departmentD004 = factory.createDepartment();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project in D004 with funding group type "Mixed Group"
        CommunityProject projectD004 = factory.createCommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        
        FundingGroup fundingGroupD004 = factory.createFundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFudingGroup(fundingGroupD004);
        departmentD004.getProjects().add(projectD004);
        
        // Create different department D005 with email: "department5@example.com"
        Department departmentD005 = factory.createDepartment();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project in D005 with funding group type "Government Group"
        CommunityProject projectD005 = factory.createCommunityProject();
        projectD005.setTitle("Local Library Improvement");
        
        FundingGroup fundingGroupD005 = factory.createFundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFudingGroup(fundingGroupD005);
        departmentD005.getProjects().add(projectD005);
        
        // Retrieve funding group types for community projects in department D004
        EList<FundingGroupType> fundingGroupTypesD004 = departmentD004.getFundingGroupTypeCommunityProjects();
        
        // Retrieve funding group types for community projects in department D005
        EList<FundingGroupType> fundingGroupTypesD005 = departmentD005.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: There is 1 Funding group type in D004 : "Mixed Group"
        assertEquals(1, fundingGroupTypesD004.size());
        assertEquals(FundingGroupType.MIXED, fundingGroupTypesD004.get(0));
        
        // Verify expected output: There is 1 Funding group type in D005 : "Government Group"
        assertEquals(1, fundingGroupTypesD005.size());
        assertEquals(FundingGroupType.GOVERNMENT, fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a department object but don't set it up with any projects
        // This simulates the scenario where we're looking for department D999 which doesn't exist
        Department department = factory.createDepartment();
        department.setID("D001"); // Set some ID, but we won't use it for testing
        
        // Create a different department to show isolation
        Department otherDepartment = factory.createDepartment();
        otherDepartment.setID("D002");
        otherDepartment.setEmail("other@example.com");
        
        // Create community project in other department
        CommunityProject project = factory.createCommunityProject();
        project.setTitle("Some Project");
        
        FundingGroup fundingGroup = factory.createFundingGroup();
        fundingGroup.setType(FundingGroupType.PRIVATE);
        project.setFudingGroup(fundingGroup);
        otherDepartment.getProjects().add(project);
        
        // For our test department (simulating D999), we don't add any projects
        // Retrieve funding group types - should be empty
        EList<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify expected output: There is 0 Funding group type
        assertEquals(0, fundingGroupTypes.size());
        assertTrue(fundingGroupTypes.isEmpty());
        
        // Verify that other department still has its funding group type
        EList<FundingGroupType> otherFundingGroupTypes = otherDepartment.getFundingGroupTypeCommunityProjects();
        assertEquals(1, otherFundingGroupTypes.size());
        assertEquals(FundingGroupType.PRIVATE, otherFundingGroupTypes.get(0));
    }
}