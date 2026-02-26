import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        // SetUp: Create Company with Department and add Employee as manager
        company.addDepartment(department1);
        company.addEmployee(employee1);
        
        // Assign employee as manager to department
        department1.assignManager(employee1);
        
        // Action: Verify manager assignment for department
        boolean result = department1.hasManager();
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department (no manager assigned)
        company.addDepartment(department1);
        
        // Action: Verify manager assignment for department
        boolean result = department1.hasManager();
        
        // Expected Output: false
        assertFalse("Department without manager should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department, add and assign manager
        company.addDepartment(department1);
        company.addEmployee(employee1);
        department1.assignManager(employee1);
        
        // Delete employee from company (this should not directly remove manager from department)
        // Note: Based on the source code, deleting employee from company doesn't automatically remove manager from department
        // So we need to manually remove the manager from the department
        department1.setManager(null);
        
        // Action: Verify manager assignment after removal
        boolean result = department1.hasManager();
        
        // Expected Output: false
        assertFalse("Department after manager removal should return false", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company (no departments added)
        
        // Action: Verify manager for non-existent department
        // Since we're testing a specific department instance that's not in the company,
        // we'll use a department that was never added to the company
        boolean result = department1.hasManager();
        
        // Expected Output: false (non-existent department has no manager)
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department, add Employee and immediately assign as manager
        company.addDepartment(department1);
        company.addEmployee(employee1);
        
        // Immediately assign as manager
        department1.assignManager(employee1);
        
        // Action: Verify assignment
        boolean result = department1.hasManager();
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}