package edu.project.project2.test;

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
import java.util.Date;
import java.text.SimpleDateFormat;

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
        // Create a company C001
        Company company = factory.createCompany();
        
        // Create a department with ID: D001 and email: department1@company.com
        Department department = factory.createDepartment();
        department.setID("D001");
        department.setEmail("department1@company.com");
        
        // Add department to company
        company.getDepartments().add(department);
        
        // Create project 1: "Website Redevelopment"
        Project project1 = factory.createProductionProject();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        
        // Create project 2: "Mobile App Development"
        Project project2 = factory.createProductionProject();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        project2.setDeadline(dateFormat.parse("2026-01-15 00:00:00"));
        
        // Add projects to department
        department.getProjects().add(project1);
        department.getProjects().add(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Create a company C002
        Company company = factory.createCompany();
        
        // Create Department D001
        Department department1 = factory.createDepartment();
        department1.setID("D001");
        department1.setEmail("department1@company.com");
        
        // Create Department D002
        Department department2 = factory.createDepartment();
        department2.setID("D002");
        department2.setEmail("department2@company.com");
        
        // Add departments to company
        company.getDepartments().add(department1);
        company.getDepartments().add(department2);
        
        // Create project in D001: "HR Software"
        Project project1 = factory.createProductionProject();
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        department1.getProjects().add(project1);
        
        // Create project in D002: "Sales Training Program"
        Project project2 = factory.createProductionProject();
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        department2.getProjects().add(project2);
        
        // Create another project in D002: "Marketing Campaign"
        Project project3 = factory.createProductionProject();
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000.0);
        department2.getProjects().add(project3);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() {
        // Create a company C003
        Company company = factory.createCompany();
        
        // Create Department D003
        Department department = factory.createDepartment();
        department.setID("D003");
        department.setEmail("department3@company.com");
        
        // Add department to company
        company.getDepartments().add(department);
        
        // Calculate total budget (no projects)
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Create a company C004
        Company company = factory.createCompany();
        
        // Create Department D004
        Department department1 = factory.createDepartment();
        department1.setID("D004");
        department1.setEmail("department4@company.com");
        
        // Create Department D005
        Department department2 = factory.createDepartment();
        department2.setID("D005");
        department2.setEmail("department5@company.com");
        
        // Add departments to company
        company.getDepartments().add(department1);
        company.getDepartments().add(department2);
        
        // Create education project: "Scholarship Program" with funding group
        EducationProject educationProject = factory.createEducationProject();
        educationProject.setTitle("Scholarship Program");
        educationProject.setBudget(50000.0);
        educationProject.setDeadline(dateFormat.parse("2026-05-31 00:00:00"));
        
        // Create funding group for education project
        FundingGroup fundingGroup1 = factory.createFundingGroup();
        fundingGroup1.setType(FundingGroupType.GOVERNMENT);
        educationProject.setFundinggroup(fundingGroup1);
        
        // Add education project to department D004
        department1.getProjects().add(educationProject);
        
        // Create project: "R&D Initiative"
        Project project1 = factory.createProductionProject();
        project1.setTitle("R&D Initiative");
        project1.setBudget(70000.0);
        project1.setDeadline(dateFormat.parse("2026-07-15 00:00:00"));
        department1.getProjects().add(project1);
        
        // Create project: "R&D5 Initiative" in department D005
        Project project2 = factory.createProductionProject();
        project2.setTitle("R&D5 Initiative");
        project2.setBudget(70000.0);
        project2.setDeadline(dateFormat.parse("2026-07-19 00:00:00"));
        department2.getProjects().add(project2);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Create a company C005
        Company company = factory.createCompany();
        
        // Create Department D006
        Department department = factory.createDepartment();
        department.setID("D006");
        department.setEmail("department5@company.com");
        
        // Add department to company
        company.getDepartments().add(department);
        
        // Create community project: "Community Health Awareness" with mixed funding group
        CommunityProject communityProject = factory.createCommunityProject();
        communityProject.setTitle("Community Health Awareness");
        communityProject.setBudget(40000.0);
        communityProject.setDeadline(dateFormat.parse("2027-02-28 00:00:00"));
        
        // Create funding group for community project
        FundingGroup fundingGroup = factory.createFundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        communityProject.setFudingGroup(fundingGroup);
        
        // Add community project to department
        department.getProjects().add(communityProject);
        
        // Create project: "Environmental Clean-up Initiative"
        Project project = factory.createProductionProject();
        project.setTitle("Environmental Clean-up Initiative");
        project.setBudget(60000.0);
        project.setDeadline(dateFormat.parse("2027-03-30 00:00:00"));
        department.getProjects().add(project);
        
        // Calculate total budget
        double totalBudget = company.calculateTotalBudget();
        
        // Verify expected output
        assertEquals(100000.0, totalBudget, 0.001);
    }
}