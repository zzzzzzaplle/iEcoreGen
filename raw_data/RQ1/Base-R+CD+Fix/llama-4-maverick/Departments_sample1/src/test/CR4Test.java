import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Company company;
    private Department verifiedDept;
    private Department unmanagedDept;
    private Department tempDept;
    private Department newDept;
    private Employee employeeE301;
    private Employee employeeE302;
    private Employee employeeE303;
    
    @Before
    public void setUp() {
        company = new Company();
        verifiedDept = new Department();
        unmanagedDept = new Department();
        tempDept = new Department();
        newDept = new Department();
        employeeE301 = new Employee();
        employeeE302 = new Employee();
        employeeE303 = new Employee();
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        company.addDepartment(verifiedDept);
        
        // SetUp: Add Employee "E301" and assign as manager
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
        company.addDepartment(unmanagedDept);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = unmanagedDept.hasManager();
        
        // Expected Output: false
        assertFalse("Department without manager should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        company.addDepartment(tempDept);
        
        // SetUp: Add and assign manager "E302"
        company.addEmployee(employeeE302);
        company.assignManager(employeeE302, tempDept);
        
        // SetUp: Delete "E302" from company
        company.deleteDepartment(tempDept);
        
        // Action: Verify manager assignment
        boolean result = tempDept.hasManager();
        
        // Expected Output: false
        assertFalse("Department after manager removal should return false", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // No departments added to company
        
        // Action: Verify manager for "Missing"
        // Create a new department that's not in the company
        Department missingDept = new Department();
        boolean result = missingDept.hasManager();
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        company.addDepartment(newDept);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        company.addEmployee(employeeE303);
        company.assignManager(employeeE303, newDept);
        
        // Action: Verify assignment
        boolean result = newDept.hasManager();
        
        // Expected Output: true
        assertTrue("Department with recently assigned manager should return true", result);
    }
}