import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Company company;
    private Department departmentWithManager;
    private Department departmentWithoutManager;
    private Department tempDepartment;
    private Department nonExistentDepartment;
    private Department newDepartment;
    private Employee employeeE301;
    private Employee employeeE302;
    private Employee employeeE303;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        departmentWithManager = new Department();
        departmentWithoutManager = new Department();
        tempDepartment = new Department();
        newDepartment = new Department();
        nonExistentDepartment = new Department();
        
        // Create employees
        employeeE301 = new Employee(301, "E301");
        employeeE302 = new Employee(302, "E302");
        employeeE303 = new Employee(303, "E303");
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified" and add Employee "E301" as manager
        company.addDepartment(departmentWithManager);
        company.addEmployee(employeeE301);
        company.assignManager(employeeE301, departmentWithManager);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = departmentWithManager.hasManager();
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        company.addDepartment(departmentWithoutManager);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = departmentWithoutManager.hasManager();
        
        // Expected Output: false
        assertFalse("Managerless department should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp", add and assign manager "E302", then delete "E302" from company
        company.addDepartment(tempDepartment);
        company.addEmployee(employeeE302);
        company.assignManager(employeeE302, tempDepartment);
        
        // Delete employee from company (which should remove manager from department)
        company.deleteDepartment(tempDepartment);
        
        // Action: Verify manager assignment
        boolean result = tempDepartment.hasManager();
        
        // Expected Output: false
        assertFalse("After manager removal, department should return false", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // Note: nonExistentDepartment is not added to company
        
        // Action: Verify manager for "Missing"
        boolean result = nonExistentDepartment.hasManager();
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept", add Employee "E303" and immediately assign as manager
        company.addDepartment(newDepartment);
        company.addEmployee(employeeE303);
        company.assignManager(employeeE303, newDepartment);
        
        // Action: Verify assignment
        boolean result = newDepartment.hasManager();
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}