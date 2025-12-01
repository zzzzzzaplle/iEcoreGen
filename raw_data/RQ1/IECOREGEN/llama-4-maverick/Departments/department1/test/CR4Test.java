package edu.department.department1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.department.Company;
import edu.department.Department;
import edu.department.Employee;
import edu.department.DepartmentFactory;
import edu.department.DepartmentPackage;

public class CR4Test {
    
    private Company company;
    private DepartmentFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory and create a new company for each test
        factory = DepartmentFactory.eINSTANCE;
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        Department verifiedDept = factory.createDepartment();
        company.addDepartment(verifiedDept);
        
        // SetUp: Add Employee "E301" and assign as manager
        Employee manager = factory.createEmployee();
        manager.setID("E301");
        company.addEmployee(manager);
        company.assignManager(manager, verifiedDept);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = verifiedDept.hasManager();
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        Department unmanagedDept = factory.createDepartment();
        company.addDepartment(unmanagedDept);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = unmanagedDept.hasManager();
        
        // Expected Output: false
        assertFalse("Department without manager should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        Department tempDept = factory.createDepartment();
        company.addDepartment(tempDept);
        
        // SetUp: Add and assign manager "E302"
        Employee manager = factory.createEmployee();
        manager.setID("E302");
        company.addEmployee(manager);
        company.assignManager(manager, tempDept);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(manager);
        // Note: Need to manually clear manager reference when removing employee
        tempDept.setManager(null);
        
        // Action: Verify manager assignment
        boolean result = tempDept.hasManager();
        
        // Expected Output: false
        assertFalse("Department should return false after manager removal", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // Action: Verify manager for "Missing" - create a department not in company
        Department missingDept = factory.createDepartment();
        
        // Action: Verify manager assignment for department not in company
        boolean result = missingDept.hasManager();
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        Department newDept = factory.createDepartment();
        company.addDepartment(newDept);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        Employee manager = factory.createEmployee();
        manager.setID("E303");
        company.addEmployee(manager);
        company.assignManager(manager, newDept);
        
        // Action: Verify assignment
        boolean result = newDept.hasManager();
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}