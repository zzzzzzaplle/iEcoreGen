import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        department.setName("Verified");
        company.addDepartment(department);
        
        // SetUp: Add Employee "E301" and assign as manager
        employee = new Employee();
        employee.setName("E301");
        company.addEmployee(employee);
        company.assignManager(department, employee);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = company.verifyDepartmentHasManager(department);
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        department = new Department();
        department.setName("Unmanaged");
        company.addDepartment(department);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = company.verifyDepartmentHasManager(department);
        
        // Expected Output: false
        assertFalse("Department without manager should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        department = new Department();
        department.setName("Temp");
        company.addDepartment(department);
        
        // SetUp: Add and assign manager "E302"
        employee = new Employee();
        employee.setName("E302");
        company.addEmployee(employee);
        company.assignManager(department, employee);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(employee);
        
        // Action: Verify manager assignment
        boolean result = company.verifyDepartmentHasManager(department);
        
        // Expected Output: false
        assertFalse("Department should return false after manager removal", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already created in setUp()
        
        // Action: Verify manager for "Missing"
        Department nonExistentDepartment = new Department();
        nonExistentDepartment.setName("Missing");
        boolean result = company.verifyDepartmentHasManager(nonExistentDepartment);
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        department = new Department();
        department.setName("NewDept");
        company.addDepartment(department);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        employee = new Employee();
        employee.setName("E303");
        company.addEmployee(employee);
        company.assignManager(department, employee);
        
        // Action: Verify assignment
        boolean result = company.verifyDepartmentHasManager(department);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}