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
        department.setDepartmentId("D001");
        department.setName("Verified");
        company.getDepartments().add(department);
        
        // SetUp: Add Employee "E301" and assign as manager
        employee = new Employee();
        employee.setId("E301");
        employee.setName("Manager1");
        company.getEmployees().add(employee);
        
        // Action: Assign manager to department
        company.assignManager(department, employee);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = company.hasManager(department);
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        department = new Department();
        department.setDepartmentId("D002");
        department.setName("Unmanaged");
        company.getDepartments().add(department);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = company.hasManager(department);
        
        // Expected Output: false
        assertFalse("Managerless department should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        department = new Department();
        department.setDepartmentId("D003");
        department.setName("Temp");
        company.getDepartments().add(department);
        
        // SetUp: Add and assign manager "E302"
        employee = new Employee();
        employee.setId("E302");
        employee.setName("Manager2");
        company.getEmployees().add(employee);
        company.assignManager(department, employee);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(employee);
        // Note: The department still has the manager reference, but the employee is removed from company
        
        // Action: Verify manager assignment
        boolean result = company.hasManager(department);
        
        // Expected Output: false
        assertFalse("After manager removal from company, department should have no manager", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // No departments added
        
        // Action: Verify manager for "Missing" department
        Department nonExistentDepartment = new Department();
        nonExistentDepartment.setDepartmentId("D004");
        nonExistentDepartment.setName("Missing");
        
        boolean result = company.hasManager(nonExistentDepartment);
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        department = new Department();
        department.setDepartmentId("D005");
        department.setName("NewDept");
        company.getDepartments().add(department);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        employee = new Employee();
        employee.setId("E303");
        employee.setName("Manager3");
        company.getEmployees().add(employee);
        company.assignManager(department, employee);
        
        // Action: Verify assignment
        boolean result = company.hasManager(department);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}