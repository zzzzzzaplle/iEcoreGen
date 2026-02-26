package edu.project.project5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.project.ProjectFactory;
import edu.project.ProjectPackage;
import edu.project.Company;
import edu.project.Department;
import edu.project.Project;
import edu.project.EducationProject;
import edu.project.CommunityProject;
import edu.project.FundingGroup;
import edu.project.FundingGroupType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private ProjectFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = ProjectFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleDepartmentBudgetCalculation() throws Exception {
        // Create company C001
        Company company = factory.createCompany();
        
        // Create department D001
        Department department = factory.createDepartment();
        department.setID("D001");
        department.setEmail("department1@company.com");
        company.getDepartments().add(department);
        
        // Create first project
        Project project1 = factory.createResearchProject();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        department.getProjects().add(project1);
        
        // Create second project
        Project project2 = factory.createResearchProject();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        project2.setDeadline(dateFormat.parse("2026-01-15 00:00:00"));
        department.getProjects().add(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify result
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Create company C002
        Company company = factory.createCompany();
        
        // Create department D001
        Department department1 = factory.createDepartment();
        department1.setID("D001");
        department1.setEmail("department1@company.com");
        company.getDepartments().add(department1);
        
        // Create project in D001
        Project project1 = factory.createResearchProject();
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        department1.getProjects().add(project1);
        
        // Create department D002
        Department department2 = factory.createDepartment();
        department2.setID("D002");
        department2.setEmail("department2@company.com");
        company.getDepartments().add(department2);
        
        // Create first project in D002
        Project project2 = factory.createResearchProject();
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        department2.getProjects().add(project2);
        
        // Create second project in D002
        Project project3 = factory.createResearchProject();
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000.0);
        department2.getProjects().add(project3);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify result
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() throws Exception {
        // Create company C003
        Company company = factory.createCompany();
        
        // Create department D003 with no projects
        Department department = factory.createDepartment();
        department.setID("D003");
        department.setEmail("department3@company.com");
        company.getDepartments().add(department);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify result
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Create company C004
        Company company = factory.createCompany();
        
        // Create department D004
        Department department1 = factory.createDepartment();
        department1.setID("D004");
        department1.setEmail("department4@company.com");
        company.getDepartments().add(department1);
        
        // Create education project with funding group
        EducationProject educationProject = factory.createEducationProject();
        educationProject.setTitle("Scholarship Program");
        educationProject.setBudget(50000.0);
        educationProject.setDeadline(dateFormat.parse("2026-05-31 00:00:00"));
        
        // Create funding group
        FundingGroup fundingGroup = factory.createFundingGroup();
        fundingGroup.setType(FundingGroupType.GOVERNMENT);
        educationProject.setFundinggroup(fundingGroup);
        
        department1.getProjects().add(educationProject);
        
        // Create another project
        Project project2 = factory.createResearchProject();
        project2.setTitle("R&D Initiative");
        project2.setBudget(70000.0);
        project2.setDeadline(dateFormat.parse("2026-07-15 00:00:00"));
        department1.getProjects().add(project2);
        
        // Create department D005
        Department department2 = factory.createDepartment();
        department2.setID("D005");
        department2.setEmail("department5@company.com");
        company.getDepartments().add(department2);
        
        // Create project in D005
        Project project3 = factory.createResearchProject();
        project3.setTitle("R&D5 Initiative");
        project3.setBudget(70000.0);
        project3.setDeadline(dateFormat.parse("2026-07-19 00:00:00"));
        department2.getProjects().add(project3);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify result
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Create company C005
        Company company = factory.createCompany();
        
        // Create department D006
        Department department = factory.createDepartment();
        department.setID("D006");
        department.setEmail("department5@company.com");
        company.getDepartments().add(department);
        
        // Create community project with mixed funding group
        CommunityProject communityProject = factory.createCommunityProject();
        communityProject.setTitle("Community Health Awareness");
        communityProject.setBudget(40000.0);
        communityProject.setDeadline(dateFormat.parse("2027-02-28 00:00:00"));
        
        // Create funding group
        FundingGroup fundingGroup = factory.createFundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        communityProject.setFudingGroup(fundingGroup);
        
        department.getProjects().add(communityProject);
        
        // Create another project
        Project project2 = factory.createResearchProject();
        project2.setTitle("Environmental Clean-up Initiative");
        project2.setBudget(60000.0);
        project2.setDeadline(dateFormat.parse("2027-03-30 00:00:00"));
        department.getProjects().add(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify result
        assertEquals(100000.0, totalBudget, 0.001);
    }
}