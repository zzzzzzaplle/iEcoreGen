import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Company company;
    private Department verifiedDept;
    private Department unmanagedDept;
    private Department tempDept;
    private Department newDept;
    private Employee e301;
    private Employee e302;
    private Employee e303;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        verifiedDept = new Department();
        verifiedDept.setName("Verified");
        
        unmanagedDept = new Department();
        unmanagedDept.setName("Unmanaged");
        
        tempDept = new Department();
        tempDept.setName("Temp");
        
        newDept = new Department();
        newDept.setName("NewDept");
        
        // Create employees
        e301 = new Employee();
        e301.setName("E301");
        
        e302 = new Employee();
        e302.setName("E302");
        
        e303 = new Employee();
        e303.setName("E303");
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        company.addDepartment(verifiedDept);
        
        // SetUp: Add Employee "E301" and assign as manager
        company.addEmployee(e301);
        company.assignManager(verifiedDept, e301);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = company.hasManager(verifiedDept);
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        company.addDepartment(unmanagedDept);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = company.hasManager(unmanagedDept);
        
        // Expected Output: false
        assertFalse("Department without manager should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        company.addDepartment(tempDept);
        
        // SetUp: Add and assign manager "E302"
        company.addEmployee(e302);
        company.assignManager(tempDept, e302);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(e302);
        
        // Action: Verify manager assignment
        boolean result = company.hasManager(tempDept);
        
        // Expected Output: false
        assertFalse("Department should return false after manager removal", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // (No departments added)
        
        // Action: Verify manager for "Missing"
        Department missingDept = new Department();
        missingDept.setName("Missing");
        boolean result = company.hasManager(missingDept);
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        company.addDepartment(newDept);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        company.addEmployee(e303);
        company.assignManager(newDept, e303);
        
        // Action: Verify assignment
        boolean result = company.hasManager(newDept);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}