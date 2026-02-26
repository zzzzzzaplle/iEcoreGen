import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        // Initialize company and common test objects
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
        employeeE301.setName("Employee E301");
        employeeE301.setEmployeeId("E301");
        
        employeeE302 = new Employee();
        employeeE302.setName("Employee E302");
        employeeE302.setEmployeeId("E302");
        
        employeeE303 = new Employee();
        employeeE303.setName("Employee E303");
        employeeE303.setEmployeeId("E303");
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified" and add Employee "E301" as manager
        company.getDepartments().add(verifiedDept);
        company.getEmployees().add(employeeE301);
        company.assignManager(verifiedDept, employeeE301);
        
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
        // SetUp: Create Company with Department "Temp", add and assign manager "E302"
        company.getDepartments().add(tempDept);
        company.getEmployees().add(employeeE302);
        company.assignManager(tempDept, employeeE302);
        
        // Delete "E302" from company (remove employee who is manager)
        company.getEmployees().remove(employeeE302);
        
        // Action: Verify manager assignment
        boolean result = company.hasManager(tempDept);
        
        // Expected Output: false
        assertFalse("Department should return false after manager removal", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // No departments or employees added
        
        // Create a department that is not in the company
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
        company.getEmployees().add(employeeE303);
        
        // Add and immediately assign as manager
        company.assignManager(newDept, employeeE303);
        
        // Action: Verify assignment
        boolean result = company.hasManager(newDept);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}