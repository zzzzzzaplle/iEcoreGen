import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        Department dept = new Department();
        dept.setName("Verified");
        company.addDepartment(dept);
        
        // SetUp: Add Employee "E301" and assign as manager
        Employee emp = new Employee();
        emp.setEmployeeId("E301");
        emp.setName("Test Employee");
        company.addEmployee(emp);
        company.assignManager(dept, emp);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = company.hasManager(dept);
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        Department dept = new Department();
        dept.setName("Unmanaged");
        company.addDepartment(dept);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = company.hasManager(dept);
        
        // Expected Output: false
        assertFalse("Managerless department should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        Department dept = new Department();
        dept.setName("Temp");
        company.addDepartment(dept);
        
        // SetUp: Add and assign manager "E302"
        Employee emp = new Employee();
        emp.setEmployeeId("E302");
        emp.setName("Test Employee");
        company.addEmployee(emp);
        company.assignManager(dept, emp);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(emp);
        dept.setManager(null);
        
        // Action: Verify manager assignment
        boolean result = company.hasManager(dept);
        
        // Expected Output: false
        assertFalse("Department after manager removal should return false", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // (Already done in @Before setup)
        
        // Action: Verify manager for "Missing"
        Department nonExistentDept = new Department();
        nonExistentDept.setName("Missing");
        boolean result = company.hasManager(nonExistentDept);
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        Department dept = new Department();
        dept.setName("NewDept");
        company.addDepartment(dept);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        Employee emp = new Employee();
        emp.setEmployeeId("E303");
        emp.setName("Test Employee");
        company.addEmployee(emp);
        company.assignManager(dept, emp);
        
        // Action: Verify assignment
        boolean result = company.hasManager(dept);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}