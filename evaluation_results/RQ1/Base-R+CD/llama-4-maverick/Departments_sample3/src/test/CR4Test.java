import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Company company;
    private Department departmentVerified;
    private Department departmentUnmanaged;
    private Department departmentTemp;
    private Department departmentNewDept;
    private Department departmentMissing;
    private Employee employeeE301;
    private Employee employeeE302;
    private Employee employeeE303;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        departmentVerified = new Department();
        departmentUnmanaged = new Department();
        departmentTemp = new Department();
        departmentNewDept = new Department();
        departmentMissing = new Department(); // This department won't be added to company
        
        // Create employees
        employeeE301 = new Employee();
        employeeE302 = new Employee();
        employeeE303 = new Employee();
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        company.addDepartment(departmentVerified);
        
        // SetUp: Add Employee "E301" and assign as manager
        company.addEmployee(employeeE301);
        company.assignManager(employeeE301, departmentVerified);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = departmentVerified.hasManager();
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        company.addDepartment(departmentUnmanaged);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = departmentUnmanaged.hasManager();
        
        // Expected Output: false
        assertFalse("Managerless department should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        company.addDepartment(departmentTemp);
        
        // SetUp: Add and assign manager "E302"
        company.addEmployee(employeeE302);
        company.assignManager(employeeE302, departmentTemp);
        
        // SetUp: Delete "E302" from company
        company.deleteDepartment(departmentTemp); // This will remove the department and all its employees
        
        // Action: Verify manager assignment
        boolean result = departmentTemp.hasManager();
        
        // Expected Output: false
        assertFalse("Department should not have manager after deletion", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company (no departments added)
        
        // Action: Verify manager for "Missing" (department not in company)
        boolean result = departmentMissing.hasManager();
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        company.addDepartment(departmentNewDept);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        company.addEmployee(employeeE303);
        company.assignManager(employeeE303, departmentNewDept);
        
        // Action: Verify assignment
        boolean result = departmentNewDept.hasManager();
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}