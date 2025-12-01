package edu.project.project5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.project.ProjectFactory;
import edu.project.ProjectPackage;
import edu.project.Department;
import edu.project.CommunityProject;
import edu.project.FundingGroup;
import edu.project.FundingGroupType;
import org.eclipse.emf.common.util.BasicEList;
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
        
        // Create funding group with type GOVERNMENT
        FundingGroup fundingGroup = factory.createFundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        
        // Set up bidirectional references
        communityProject.setFundingGroup(fundingGroup);
        department.getProjects().add(communityProject);
        communityProject.setDepartment(department);
        
        // Retrieve funding group types
        EList<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have 1 funding group type", 1, result.size());
        assertEquals("Funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, result.get(0));
    }
    
    @Test
    public void testCase2_RetrieveFundingGroupTypeForMultipleCommunityProjects() throws Exception {
        // Create department with ID: D002
        Department department = factory.createDepartment();
        department.setID("D002");
        department.setEmail("department2@example.com");
        
        // Create first community project with PRIVATE funding group
        CommunityProject project1 = factory.createCommunityProject();
        project1.setTitle("Food Drive");
        project1.setDescription("Collect food for the needy");
        project1.setBudget(3000.0);
        project1.setDeadline(dateFormat.parse("2025-11-15"));
        
        FundingGroup fundingGroup1 = factory.createFundingGroup();
        fundingGroup1.setType(FundingGroupType.PRIVATE);
        project1.setFundingGroup(fundingGroup1);
        department.getProjects().add(project1);
        project1.setDepartment(department);
        
        // Create second community project with MIXED funding group
        CommunityProject project2 = factory.createCommunityProject();
        project2.setTitle("Health Awareness Campaign");
        project2.setDescription("Promote health screenings");
        project2.setBudget(2000.0);
        project2.setDeadline(dateFormat.parse("2025-10-20"));
        
        FundingGroup fundingGroup2 = factory.createFundingGroup();
        fundingGroup2.setType(FundingGroupType.MIXED);
        project2.setFundingGroup(fundingGroup2);
        department.getProjects().add(project2);
        project2.setDepartment(department);
        
        // Retrieve funding group types
        EList<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertEquals("Should have 2 funding group types", 2, result.size());
        assertTrue("Should contain PRIVATE funding group type", result.contains(FundingGroupType.PRIVATE));
        assertTrue("Should contain MIXED funding group type", result.contains(FundingGroupType.MIXED));
    }
    
    @Test
    public void testCase3_NoCommunityProjectsInDepartment() {
        // Create department with ID: D003 and no community projects
        Department department = factory.createDepartment();
        department.setID("D003");
        department.setEmail("department3@example.com");
        
        // Retrieve funding group types
        EList<FundingGroupType> result = department.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertNotNull("Result should not be null", result);
        assertTrue("Should have 0 funding group types", result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveFundingGroupTypeWithMultipleDepartments() throws Exception {
        // Create department D004
        Department departmentD004 = factory.createDepartment();
        departmentD004.setID("D004");
        departmentD004.setEmail("department4@example.com");
        
        // Create community project for D004 with MIXED funding group
        CommunityProject projectD004 = factory.createCommunityProject();
        projectD004.setTitle("Neighborhood Beautification");
        projectD004.setDescription("Enhancing community space");
        projectD004.setBudget(7500.0);
        projectD004.setDeadline(dateFormat.parse("2025-12-01"));
        
        FundingGroup fundingGroupD004 = factory.createFundingGroup();
        fundingGroupD004.setType(FundingGroupType.MIXED);
        projectD004.setFundingGroup(fundingGroupD004);
        departmentD004.getProjects().add(projectD004);
        projectD004.setDepartment(departmentD004);
        
        // Create department D005
        Department departmentD005 = factory.createDepartment();
        departmentD005.setID("D005");
        departmentD005.setEmail("department5@example.com");
        
        // Create community project for D005 with GOVERNMENT funding group
        CommunityProject projectD005 = factory.createCommunityProject();
        projectD005.setTitle("Local Library Improvement");
        projectD005.setDescription("Improving library facilities");
        projectD005.setBudget(6000.0);
        projectD005.setDeadline(dateFormat.parse("2025-11-30"));
        
        FundingGroup fundingGroupD005 = factory.createFundingGroup();
        fundingGroupD005.setType(FundingGroupType.GOVERNMENT);
        projectD005.setFundingGroup(fundingGroupD005);
        departmentD005.getProjects().add(projectD005);
        projectD005.setDepartment(departmentD005);
        
        // Retrieve funding group types for both departments
        EList<FundingGroupType> resultD004 = departmentD004.getFundingGroupTypeCommunityProjects();
        EList<FundingGroupType> resultD005 = departmentD005.getFundingGroupTypeCommunityProjects();
        
        // Verify isolation between departments
        assertEquals("D004 should have 1 funding group type", 1, resultD004.size());
        assertEquals("D004 funding group type should be MIXED", FundingGroupType.MIXED, resultD004.get(0));
        
        assertEquals("D005 should have 1 funding group type", 1, resultD005.size());
        assertEquals("D005 funding group type should be GOVERNMENT", FundingGroupType.GOVERNMENT, resultD005.get(0));
    }
    
    @Test
    public void testCase5_FundingGroupTypeWithInvalidDepartmentID() {
        // Create a valid department (not D999) to ensure test isolation
        Department validDepartment = factory.createDepartment();
        validDepartment.setID("D100");
        validDepartment.setEmail("department100@example.com");
        
        // For invalid department ID D999, we simulate by not creating any department with that ID
        // In a real scenario, we would query a repository, but here we test with a non-existent department
        
        // Since we can't query by ID directly in the model, we test that a new department with no projects returns empty
        Department testDepartment = factory.createDepartment();
        testDepartment.setID("D999"); // This simulates our "invalid" department
        
        // Retrieve funding group types
        EList<FundingGroupType> result = testDepartment.getFundingGroupTypeCommunityProjects();
        
        // Verify results
        assertNotNull("Result should not be null", result);
        assertTrue("Should have 0 funding group types for invalid department", result.isEmpty());
    }
}