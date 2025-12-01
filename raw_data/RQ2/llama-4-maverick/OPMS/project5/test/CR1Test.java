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
        
        // Add the department to the company C001
        company.getDepartments().add(department);
        
        // Create project 1: "Website Redevelopment"
        Project project1 = factory.createProductionProject();
        project1.setTitle("Website Redevelopment");
        project1.setDescription("Redesigning the company website");
        project1.setBudget(10000.0);
        project1.setDeadline(dateFormat.parse("2025-12-31 00:00:00"));
        
        // Associate project1 with department D001
        department.getProjects().add(project1);
        
        // Create project 2: "Mobile App Development"
        Project project2 = factory.createProductionProject();
        project2.setTitle("Mobile App Development");
        project2.setDescription("Developing a customer service app");
        project2.setBudget(15000.0);
        project2.setDeadline(dateFormat.parse("2026-01-15 00:00:00"));
        
        // Associate project2 with department D001
        department.getProjects().add(project2);
        
        // Calculate the total budget for company C001
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 10000 + 15000 = 25000 CNY
        assertEquals(25000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase2_MultipleDepartmentsBudgetCalculation() throws Exception {
        // Create a company C002
        Company company = factory.createCompany();
        
        // Create Department D001 with email: department1@company.com
        Department department1 = factory.createDepartment();
        department1.setID("D001");
        department1.setEmail("department1@company.com");
        
        // Add department1 to the company C002
        company.getDepartments().add(department1);
        
        // Create project in D001: "HR Software"
        Project project1 = factory.createProductionProject();
        project1.setTitle("HR Software");
        project1.setBudget(20000.0);
        
        // Associate project1 with department D001
        department1.getProjects().add(project1);
        
        // Create Department D002 with email: department2@company.com
        Department department2 = factory.createDepartment();
        department2.setID("D002");
        department2.setEmail("department2@company.com");
        
        // Add department2 to the company C002
        company.getDepartments().add(department2);
        
        // Create project in D002: "Sales Training Program"
        Project project2 = factory.createProductionProject();
        project2.setTitle("Sales Training Program");
        project2.setBudget(30000.0);
        
        // Associate project2 with department D002
        department2.getProjects().add(project2);
        
        // Create another project in D002: "Marketing Campaign"
        Project project3 = factory.createProductionProject();
        project3.setTitle("Marketing Campaign");
        project3.setBudget(25000.0);
        
        // Associate project3 with department D002
        department2.getProjects().add(project3);
        
        // Calculate the total budget for company C002
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 20000 + 30000 + 25000 = 75000 CNY
        assertEquals(75000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase3_BudgetCalculationWithZeroProjects() throws Exception {
        // Create a company C003
        Company company = factory.createCompany();
        
        // Create Department with ID: D003 and email: department3@company.com
        Department department = factory.createDepartment();
        department.setID("D003");
        department.setEmail("department3@company.com");
        
        // Add the department to the company C003
        company.getDepartments().add(department);
        
        // Calculate the total budget for company C003
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 0 CNY
        assertEquals(0.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase4_EducationProjectBudgetWithFundingGroup() throws Exception {
        // Create a company C004
        Company company = factory.createCompany();
        
        // Create a department with ID: D004 and email: department4@company.com
        Department department1 = factory.createDepartment();
        department1.setID("D004");
        department1.setEmail("department4@company.com");
        
        // Add department1 to the company C004
        company.getDepartments().add(department1);
        
        // Create an education project: "Scholarship Program"
        EducationProject educationProject = factory.createEducationProject();
        educationProject.setTitle("Scholarship Program");
        educationProject.setBudget(50000.0);
        educationProject.setDeadline(dateFormat.parse("2026-05-31 00:00:00"));
        
        // Create funding group for education project
        FundingGroup fundingGroup1 = factory.createFundingGroup();
        fundingGroup1.setType(FundingGroupType.GOVERNMENT);
        
        // Associate education project with funding group
        educationProject.setFundinggroup(fundingGroup1);
        
        // Associate education project with department D004
        department1.getProjects().add(educationProject);
        
        // Create another project: "R&D Initiative"
        Project project1 = factory.createProductionProject();
        project1.setTitle("R&D Initiative");
        project1.setBudget(70000.0);
        project1.setDeadline(dateFormat.parse("2026-07-15 00:00:00"));
        
        // Associate project1 with department D004
        department1.getProjects().add(project1);
        
        // Create a department with ID: D005 and email: department5@company.com
        Department department2 = factory.createDepartment();
        department2.setID("D005");
        department2.setEmail("department5@company.com");
        
        // Add department2 to the company C004
        company.getDepartments().add(department2);
        
        // Create another project: "R&D5 Initiative"
        Project project2 = factory.createProductionProject();
        project2.setTitle("R&D5 Initiative");
        project2.setBudget(70000.0);
        project2.setDeadline(dateFormat.parse("2026-07-19 00:00:00"));
        
        // Associate project2 with department D005
        department2.getProjects().add(project2);
        
        // Calculate the total budget for company C004
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 50000 + 70000 + 70000 = 190000 CNY
        assertEquals(190000.0, totalBudget, 0.001);
    }
    
    @Test
    public void testCase5_CommunityProjectBudgetWithMixedFundingGroup() throws Exception {
        // Create a company C005
        Company company = factory.createCompany();
        
        // Create a department with ID: D006 and email: department5@company.com
        Department department = factory.createDepartment();
        department.setID("D006");
        department.setEmail("department5@company.com");
        
        // Add the department to the company C005
        company.getDepartments().add(department);
        
        // Create a community project: "Community Health Awareness"
        CommunityProject communityProject = factory.createCommunityProject();
        communityProject.setTitle("Community Health Awareness");
        communityProject.setBudget(40000.0);
        communityProject.setDeadline(dateFormat.parse("2027-02-28 00:00:00"));
        
        // Create funding group for community project
        FundingGroup fundingGroup = factory.createFundingGroup();
        fundingGroup.setType(FundingGroupType.MIXED);
        
        // Associate community project with funding group
        communityProject.setFundingGroup(fundingGroup);
        
        // Associate community project with department D006
        department.getProjects().add(communityProject);
        
        // Create another project: "Environmental Clean-up Initiative"
        Project project = factory.createProductionProject();
        project.setTitle("Environmental Clean-up Initiative");
        project.setBudget(60000.0);
        project.setDeadline(dateFormat.parse("2027-03-30 00:00:00"));
        
        // Associate project with department D006
        department.getProjects().add(project);
        
        // Calculate the total budget for company C005
        double totalBudget = company.calculateTotalBudget();
        
        // Expected Output: Total budget = 40000 + 60000 = 100000 CNY
        assertEquals(100000.0, totalBudget, 0.001);
    }
}