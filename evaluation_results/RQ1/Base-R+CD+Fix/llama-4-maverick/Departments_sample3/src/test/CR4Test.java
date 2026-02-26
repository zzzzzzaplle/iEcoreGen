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
        // SetUp: Create Company with Department "Verified" and add Employee "E301" as manager
        company.addDepartment(department1);
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
        assertFalse("Managerless department should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp", add and assign manager "E302"
        company.addDepartment(department1);
        company.addEmployee(employee1);
        company.assignManager(employee1, department1);
        
        // Delete "E302" from company
        company.deleteDepartment(department1);
        
        // Action: Verify manager assignment
        boolean result = department1.hasManager();
        
        // Expected Output: false
        assertFalse("Department after manager removal should return false", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // No departments added
        
        // Create a new department that is not in the company
        Department nonExistentDepartment = new Department();
        
        // Action: Verify manager for "Missing"
        boolean result = nonExistentDepartment.hasManager();
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        company.addDepartment(department1);
        company.addEmployee(employee1);
        
        // Add Employee "E303" and immediately assign as manager
        company.assignManager(employee1, department1);
        
        // Action: Verify assignment
        boolean result = department1.hasManager();
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}