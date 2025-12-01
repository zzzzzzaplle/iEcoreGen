import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private Company company;
    private Department department1;
    private Department department2;
    private Department department3;
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    
    @Before
    public void setUp() {
        company = new Company();
        department1 = new Department();
        department2 = new Department();
        department3 = new Department();
        employee1 = new Employee();
        employee2 = new Employee();
        employee3 = new Employee();
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        company.addDepartment(department1);
        
        // SetUp: Add Employee "E301" and assign as manager
        company.addEmployee(employee1);
        company.assignManager(employee1, department1);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = department1.hasManager();
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        company.addDepartment(department1);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = department1.hasManager();
        
        // Expected Output: false
        assertFalse("Department without manager should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        company.addDepartment(department1);
        
        // SetUp: Add and assign manager "E302"
        company.addEmployee(employee1);
        company.assignManager(employee1, department1);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(employee1);
        // Note: The department still has the manager reference, but the employee is removed from company
        
        // Action: Verify manager assignment
        boolean result = department1.hasManager();
        
        // Expected Output: false
        // Note: The test specification expects false after employee deletion
        // Since the department still holds reference to the manager, this might return true
        // But based on the specification, we expect false
        assertFalse("Department should not have manager after employee deletion", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp
        
        // Action: Verify manager for "Missing"
        // Since we can't directly check a non-existent department, we need to simulate this
        // Create a new department that is not in the company
        Department nonExistentDepartment = new Department();
        boolean result = nonExistentDepartment.hasManager();
        
        // Expected Output: false
        assertFalse("Non-existent department should return false for manager check", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        company.addDepartment(department1);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        company.addEmployee(employee1);
        company.assignManager(employee1, department1);
        
        // Action: Verify assignment
        boolean result = department1.hasManager();
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}