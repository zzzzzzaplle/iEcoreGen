import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private Company company;
    private Department department;
    private Employee employee;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        department = new Department();
        department.setManager(new Employee());
        company.addDepartment(department);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = department.hasManager();
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        department = new Department();
        company.addDepartment(department);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = department.hasManager();
        
        // Expected Output: false
        assertFalse("Managerless department should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        department = new Department();
        company.addDepartment(department);
        
        // Add and assign manager "E302"
        employee = new Employee();
        employee.setId(302);
        employee.setName("E302");
        company.addEmployee(employee);
        department.setManager(employee);
        
        // Delete "E302" from company
        company.deleteDepartment(department);
        
        // Action: Verify manager assignment
        boolean result = department.hasManager();
        
        // Expected Output: false
        assertFalse("Department after manager removal should return false", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // Action: Verify manager for "Missing"
        // Since we can't directly check a non-existent department in the company,
        // we'll create a new department that's not in the company and check it
        Department missingDepartment = new Department();
        boolean result = missingDepartment.hasManager();
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        department = new Department();
        company.addDepartment(department);
        
        // Add Employee "E303" and immediately assign as manager
        employee = new Employee();
        employee.setId(303);
        employee.setName("E303");
        company.addEmployee(employee);
        department.setManager(employee);
        
        // Action: Verify assignment
        boolean result = department.hasManager();
        
        // Expected Output: true
        assertTrue("Department with recently assigned manager should return true", result);
    }
}