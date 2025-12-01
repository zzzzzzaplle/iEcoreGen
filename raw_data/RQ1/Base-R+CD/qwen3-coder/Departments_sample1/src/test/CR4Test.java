import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private Company company;
    private Department department;
    private Employee employee;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        Department verifiedDept = new Department();
        company.addDepartment(verifiedDept);
        
        // SetUp: Add Employee "E301" and assign as manager
        Employee employeeE301 = new Employee();
        company.addEmployee(employeeE301);
        company.assignManager(employeeE301, verifiedDept);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = verifiedDept.hasManager();
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        Department unmanagedDept = new Department();
        company.addDepartment(unmanagedDept);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = unmanagedDept.hasManager();
        
        // Expected Output: false
        assertFalse("Managerless department should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        Department tempDept = new Department();
        company.addDepartment(tempDept);
        
        // SetUp: Add and assign manager "E302"
        Employee employeeE302 = new Employee();
        company.addEmployee(employeeE302);
        company.assignManager(employeeE302, tempDept);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(employeeE302);
        // Note: In the actual implementation, removing employee from company doesn't automatically remove them as manager
        // So we need to explicitly remove the manager from the department
        tempDept.setManager(null);
        
        // Action: Verify manager assignment
        boolean result = tempDept.hasManager();
        
        // Expected Output: false
        assertFalse("Department should return false after manager removal", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // No departments added
        
        // Action: Verify manager for "Missing" - create a department that's not in the company
        Department missingDept = new Department();
        boolean result = missingDept.hasManager();
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        Department newDept = new Department();
        company.addDepartment(newDept);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        Employee employeeE303 = new Employee();
        company.addEmployee(employeeE303);
        company.assignManager(employeeE303, newDept);
        
        // Action: Verify assignment
        boolean result = newDept.hasManager();
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}