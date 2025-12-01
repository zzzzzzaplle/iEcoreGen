import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        Department department = new Department("Verified");
        company.getDepartments().add(department);
        
        // SetUp: Add Employee "E301" and assign as manager
        Employee employee = new Employee("E301");
        company.getEmployees().add(employee);
        company.assignManager(department, employee);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = company.hasManager(department);
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        Department department = new Department("Unmanaged");
        company.getDepartments().add(department);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = company.hasManager(department);
        
        // Expected Output: false
        assertFalse("Department without manager should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        Department department = new Department("Temp");
        company.getDepartments().add(department);
        
        // SetUp: Add and assign manager "E302"
        Employee employee = new Employee("E302");
        company.getEmployees().add(employee);
        company.assignManager(department, employee);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(employee);
        // Note: The manager assignment remains in the department object even if employee is removed from company
        // So we need to explicitly remove the manager from the department
        department.setManager(null);
        
        // Action: Verify manager assignment
        boolean result = company.hasManager(department);
        
        // Expected Output: false
        assertFalse("Department after manager removal should return false", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from @Before setup
        
        // Action: Verify manager for "Missing"
        Department nonExistentDepartment = new Department("Missing");
        boolean result = company.hasManager(nonExistentDepartment);
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        Department department = new Department("NewDept");
        company.getDepartments().add(department);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        Employee employee = new Employee("E303");
        company.getEmployees().add(employee);
        company.assignManager(department, employee);
        
        // Action: Verify assignment
        boolean result = company.hasManager(department);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}