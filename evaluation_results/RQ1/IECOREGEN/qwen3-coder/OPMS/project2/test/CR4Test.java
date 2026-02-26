package edu.project.project2.test;

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
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private ProjectFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = ProjectFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_RetrieveFundingGroupTypeForSingleCommunityProject() throws Exception {
        // Create department with ID: D001
        Department department = factory.createDepartment();
        department.setID("D001");
        department.setEmail("department1@example.com");
        
        // Create community project
        CommunityProject communityProject = factory.createCommunityProject();
        communityProject.setTitle("Community Clean-Up");
        communityProject.setDescription("A project to clean the local park");
        communityProject.setBudget(5000.0);
        communityProject.setDeadline(dateFormat.parse("2025-12-31"));
        
        // Create funding group with type "Government Group"
        FundingGroup fundingGroup = factory.createFundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        
        // Set up bidirectional reference
        communityProject.setFundingGroup(fundingGroup);
        
        // Add project to department
        department.getProjects().add(communityProject);
        
        // Retrieve funding group types
        EList<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 1 funding group type", 1, fundingGroupTypes.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, fundingGroupTypes.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create department with ID: D002
        Department department = factory.createDepartment();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with "Private Group" funding
        CommunityProject project1 = factory.createCommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = factory.createFundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        
        // Create second community project with "Mixed Group" funding
        CommunityProject project2 = factory.createCommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = factory.createFundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        
        // Add both projects to department
        department.getProjects().add(project1);
        department.getProjects().add(project2);
        
        // Retrieve funding group types
        EList<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have exactly 2 funding group types", 2, fundingGroupTypes.size());
        assertTrue("Should contain PRIVATE funding type", fundingGroupTypes.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding type", fundingGroupTypes.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department with ID: D003
        Department department = factory.createDepartment();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // No community projects created - department has empty projects list
        
        // Retrieve funding group types
        EList<FundingGroupType> fundingGroupTypes = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have 0 funding group types when no community projects exist", 0, fundingGroupTypes.size());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Create first department with ID: D004
        Department departmentD004 = factory.createDepartment();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project for D004 with "Mixed Group" funding
        CommunityProject projectD004 = factory.createCommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        projectD004.setDeadline(dateFormat.parse("2025-12-01"));
        
        FundingGroup fundingGroupD004 = factory.createFundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFundingGroup(fundingGroupD004);
        
        departmentD004.getProjects().add(projectD004);
        
        // Create second department with ID: D005
        Department departmentD005 = factory.createDepartment();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project for D005 with "Government Group" funding
        CommunityProject projectD005 = factory.createCommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Improving library facilities");
        projectD005.setBudget(6000.0);
        projectD005.setDeadline(dateFormat.parse("2025-11-30"));
        
        FundingGroup fundingGroupD005 = factory.createFundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFundingGroup(fundingGroupD005);
        
        departmentD005.getProjects().add(projectD005);
        
        // Retrieve funding group types for each department
        EList<FundingGroupType> fundingGroupTypesD004 = departmentD004.getFundingGroupTypeCommunityProjects();
        EList<FundingGroupType> fundingGroupTypesD005 = departmentD005.getFundingGroupTypeCommunityProjects();
        
        // Verify results - departments should be isolated
        assertEquals("D004 should have exactly 1 funding group type", 1, fundingGroupTypesD004.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, fundingGroupTypesD004.get(0));
        
        assertEquals("D005 should have exactly 1 funding group type", 1, fundingGroupTypesD005.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, fundingGroupTypesD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // This test simulates querying a non-existent department
        // Since we can't actually query by ID in the current model structure,
        // we'll create a scenario where we have valid departments but none with ID D999
        
        // Create some valid departments (but not D999)
        Department department1 = factory.createDepartment();
        department1.setID("D001");
        
        Department department2 = factory.createDepartment();
        department2.setID("D002");
        
        // Simulate querying for department with ID D999
        // In a real scenario, this would involve a lookup mechanism
        Department departmentD999 = null; // No department with ID D999 exists
        
        // If we had a department with ID D999, we would query it
        // Since we don't, we simulate the expected behavior
        EList<FundingGroupType> fundingGroupTypes = factory.createDepartment().getFundingGroupTypeCommunityProjects();
        // Using a new empty department to simulate the non-existent department behavior
        
        // Verify results - no funding group types for non-existent department
        assertEquals("Should have 0 funding group types for invalid department ID", 0, fundingGroupTypes.size());
    }
}