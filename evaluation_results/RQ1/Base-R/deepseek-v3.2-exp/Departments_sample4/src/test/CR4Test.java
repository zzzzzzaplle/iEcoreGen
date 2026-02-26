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
        employeeE301 = new Employee();
        employeeE301.setName("E301");
        
        employeeE302 = new Employee();
        employeeE302.setName("E302");
        
        employeeE303 = new Employee();
        employeeE303.setName("E303");
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        company.getDepartments().add(verifiedDept);
        
        // SetUp: Add Employee "E301" and assign as manager
        company.getEmployees().add(employeeE301);
        verifiedDept.setManager(employeeE301);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = company.hasManager(verifiedDept);
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        company.getDepartments().add(unmanagedDept);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = company.hasManager(unmanagedDept);
        
        // Expected Output: false
        assertFalse("Department without manager should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        company.getDepartments().add(tempDept);
        
        // SetUp: Add and assign manager "E302"
        company.getEmployees().add(employeeE302);
        tempDept.setManager(employeeE302);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(employeeE302);
        tempDept.setManager(null);
        
        // Action: Verify manager assignment
        boolean result = company.hasManager(tempDept);
        
        // Expected Output: false
        assertFalse("Department after manager removal should return false", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // (company is already empty from setUp method)
        
        // Create a department that's not in the company
        Department missingDept = new Department();
        missingDept.setName("Missing");
        
        // Action: Verify manager for "Missing"
        boolean result = company.hasManager(missingDept);
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        company.getDepartments().add(newDept);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        company.getEmployees().add(employeeE303);
        newDept.setManager(employeeE303);
        
        // Action: Verify assignment
        boolean result = company.hasManager(newDept);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}