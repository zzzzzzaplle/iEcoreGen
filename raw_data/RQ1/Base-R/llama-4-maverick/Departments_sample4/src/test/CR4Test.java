import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    private Company company;
    private Department departmentVerified;
    private Department departmentUnmanaged;
    private Department departmentTemp;
    private Department departmentNewDept;
    private Employee employeeE301;
    private Employee employeeE302;
    private Employee employeeE303;
    
    @Before
    public void setUp() {
        company = new Company();
        departmentVerified = new Department();
        departmentUnmanaged = new Department();
        departmentTemp = new Department();
        departmentNewDept = new Department();
        employeeE301 = new Employee();
        employeeE302 = new Employee();
        employeeE303 = new Employee();
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        company.getDepartments().add(departmentVerified);
        
        // SetUp: Add Employee "E301" and assign as manager
        company.getEmployees().add(employeeE301);
        departmentVerified.assignManager(employeeE301);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = departmentVerified.hasManager();
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        company.getDepartments().add(departmentUnmanaged);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = departmentUnmanaged.hasManager();
        
        // Expected Output: false
        assertFalse("Department without manager should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        company.getDepartments().add(departmentTemp);
        
        // SetUp: Add and assign manager "E302"
        company.getEmployees().add(employeeE302);
        departmentTemp.assignManager(employeeE302);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(employeeE302);
        
        // Action: Verify manager assignment
        boolean result = departmentTemp.hasManager();
        
        // Expected Output: false
        assertFalse("Department should return false after manager removal", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // Create a new department that doesn't exist in the company
        Department missingDepartment = new Department();
        
        // Action: Verify manager for "Missing"
        boolean result = missingDepartment.hasManager();
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        company.getDepartments().add(departmentNewDept);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        company.getEmployees().add(employeeE303);
        departmentNewDept.assignManager(employeeE303);
        
        // Action: Verify assignment
        boolean result = departmentNewDept.hasManager();
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}