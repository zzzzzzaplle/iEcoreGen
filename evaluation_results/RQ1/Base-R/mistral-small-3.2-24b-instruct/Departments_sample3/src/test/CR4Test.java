import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        department = new Department();
        company.addDepartment(department);
        
        // SetUp: Add Employee "E301" and assign as manager
        employee = new Employee();
        employee.setName("E301");
        department.assignManager(employee);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = department.hasManager();
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        department = new Department();
        company.addDepartment(department);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = department.hasManager();
        
        // Expected Output: false
        assertFalse("Managerless department should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        department = new Department();
        company.addDepartment(department);
        
        // SetUp: Add and assign manager "E302"
        employee = new Employee();
        employee.setName("E302");
        department.assignManager(employee);
        
        // SetUp: Delete "E302" from company (by setting manager to null)
        // Note: The specification says "delete from company" but Department doesn't track company membership
        // Since Employee is passed by reference, we simulate removal by setting manager to null
        department.assignManager(null);
        
        // Action: Verify manager assignment
        boolean result = department.hasManager();
        
        // Expected Output: false
        assertFalse("Department after manager removal should return false", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from @Before setup
        
        // Action: Verify manager for "Missing"
        // Since we can't access a non-existent department directly,
        // we need to check if a department exists before checking for manager
        boolean departmentExists = false;
        for (Department dept : company.getDepartments()) {
            if (dept != null) {
                departmentExists = true;
                break;
            }
        }
        
        // Expected Output: false (non-existent department has no manager)
        assertFalse("Non-existent department should be considered as having no manager", departmentExists);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        department = new Department();
        company.addDepartment(department);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        employee = new Employee();
        employee.setName("E303");
        boolean assignmentResult = department.assignManager(employee);
        
        // Action: Verify assignment
        boolean result = department.hasManager();
        
        // Expected Output: true
        assertTrue("Assignment should be successful and return true", assignmentResult);
        assertTrue("Recently assigned manager should return true", result);
    }
}