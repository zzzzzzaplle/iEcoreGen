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
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        department1 = new Department();
        department1.setId("D1");
        department1.setName("Verified");
        company.addDepartment(department1);
        
        // SetUp: Add Employee "E301" and assign as manager
        employee1 = new Employee();
        employee1.setId("E301");
        employee1.setName("Manager1");
        company.addEmployee(employee1);
        department1.addEmployee(employee1);
        
        // Assign manager to department
        company.assignManager(department1, employee1);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = company.hasManager(department1);
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        department1 = new Department();
        department1.setId("D1");
        department1.setName("Unmanaged");
        company.addDepartment(department1);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = company.hasManager(department1);
        
        // Expected Output: false
        assertFalse("Managerless department should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        department1 = new Department();
        department1.setId("D1");
        department1.setName("Temp");
        company.addDepartment(department1);
        
        // SetUp: Add and assign manager "E302"
        employee1 = new Employee();
        employee1.setId("E302");
        employee1.setName("Manager2");
        company.addEmployee(employee1);
        department1.addEmployee(employee1);
        company.assignManager(department1, employee1);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(employee1);
        department1.setManager(null);
        
        // Action: Verify manager assignment
        boolean result = company.hasManager(department1);
        
        // Expected Output: false
        assertFalse("Department after manager removal should return false", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        // No departments added to company
        
        // Action: Verify manager for "Missing"
        Department nonExistentDepartment = new Department();
        nonExistentDepartment.setId("D999");
        nonExistentDepartment.setName("Missing");
        
        boolean result = company.hasManager(nonExistentDepartment);
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        department1 = new Department();
        department1.setId("D1");
        department1.setName("NewDept");
        company.addDepartment(department1);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        employee1 = new Employee();
        employee1.setId("E303");
        employee1.setName("Manager3");
        company.addEmployee(employee1);
        department1.addEmployee(employee1);
        company.assignManager(department1, employee1);
        
        // Action: Verify assignment
        boolean result = company.hasManager(department1);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}