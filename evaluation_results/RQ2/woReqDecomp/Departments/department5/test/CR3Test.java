package edu.department.department5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.department.DepartmentFactory;
import edu.department.DepartmentPackage;
import edu.department.Company;
import edu.department.Department;
import edu.department.Employee;
import edu.department.Office;
import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private DepartmentFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize the factory and create a new company for each test
        factory = DepartmentFactory.eINSTANCE;
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        Department legalDept = factory.createDepartment();
        legalDept.setCompany(company);
        
        // Add Offices "L1", "L2" to Legal
        Office officeL1 = new Office() {
            // Concrete implementation for testing
        };
        officeL1.setTitle("L1");
        officeL1.setDepartment(legalDept);
        
        Office officeL2 = new Office() {
            // Concrete implementation for testing
        };
        officeL2.setTitle("L2");
        officeL2.setDepartment(legalDept);
        
        // Set "L1" as headquarter
        legalDept.setHeadquarter(officeL1);
        
        // Assign "E101" as manager to Legal
        Employee managerE101 = factory.createEmployee();
        managerE101.setID("E101");
        managerE101.setName("Manager E101");
        company.getEmployees().add(managerE101);
        legalDept.setManager(managerE101);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Company should not contain Legal department", 
                   company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        boolean employeeRemoved = true;
        for (Employee emp : company.getEmployees()) {
            if ("E101".equals(emp.getID())) {
                employeeRemoved = false;
                break;
            }
        }
        assertTrue("Employee E101 should be removed from company", employeeRemoved);
        
        // Verification: Offices "L1", "L2" removed
        assertTrue("Department offices should be empty", legalDept.getOffices().isEmpty());
        assertNull("Headquarter should be null", legalDept.getHeadquarter());
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // Create department "Phantom" but don't add to company
        Department phantomDept = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Deleting non-existent department should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        Department emptyDept = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        emptyDept.setCompany(company);
        
        // Add Office "O1" to EmptyDept
        Office officeO1 = new Office() {
            // Concrete implementation for testing
        };
        officeO1.setTitle("O1");
        officeO1.setDepartment(emptyDept);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Company should not contain EmptyDept department", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        Department soloDept = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        soloDept.setCompany(company);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty", company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        Department remoteDept = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        remoteDept.setCompany(company);
        
        // Add Employee "E201" to Remote
        Employee employeeE201 = factory.createEmployee();
        employeeE201.setID("E201");
        employeeE201.setName("Employee E201");
        company.getEmployees().add(employeeE201);
        
        // Assign "E201" as manager
        remoteDept.setManager(employeeE201);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        boolean employeeRemoved = true;
        for (Employee emp : company.getEmployees()) {
            if ("E201".equals(emp.getID())) {
                employeeRemoved = false;
                break;
            }
        }
        assertTrue("Employee E201 should be removed from company", employeeRemoved);
        
        // Additional verification: Department should be removed from company
        assertFalse("Company should not contain Remote department", 
                   company.getDepartments().contains(remoteDept));
    }
}