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
        Department department = new Department();
        department.setDepartmentId("D001");
        department.setName("Verified");
        company.getDepartments().add(department);
        
        // SetUp: Add Employee "E301" and assign as manager
        Employee manager = new Employee();
        manager.setId("E301");
        manager.setName("Manager Name");
        company.getEmployees().add(manager);
        company.assignManager(department, manager);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = company.hasManager(department);
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        Department department = new Department();
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
        Department department = new Department();
        department.setDepartmentId("D003");
        department.setName("Temp");
        company.getDepartments().add(department);
        
        // SetUp: Add and assign manager "E302"
        Employee manager = new Employee();
        manager.setId("E302");
        manager.setName("Temp Manager");
        company.getEmployees().add(manager);
        company.assignManager(department, manager);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(manager);
        
        // Action: Verify manager assignment
        boolean result = company.hasManager(department);
        
        // Expected Output: false
        assertFalse("Department should return false after manager removal", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // (No departments added - using default empty company from @Before)
        
        // Action: Verify manager for "Missing"
        Department nonExistentDepartment = new Department();
        nonExistentDepartment.setDepartmentId("D999");
        nonExistentDepartment.setName("Missing");
        
        boolean result = company.hasManager(nonExistentDepartment);
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        Department department = new Department();
        department.setDepartmentId("D004");
        department.setName("NewDept");
        company.getDepartments().add(department);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        Employee manager = new Employee();
        manager.setId("E303");
        manager.setName("New Manager");
        company.getEmployees().add(manager);
        company.assignManager(department, manager);
        
        // Action: Verify assignment
        boolean result = company.hasManager(department);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}