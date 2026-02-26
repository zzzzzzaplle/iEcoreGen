import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        department1.setName("Verified");
        department2 = new Department();
        department2.setName("Unmanaged");
        department3 = new Department();
        department3.setName("Temp");
        
        employee1 = new Employee();
        employee1.setEmployeeId("E301");
        employee1.setName("John Doe");
        
        employee2 = new Employee();
        employee2.setEmployeeId("E302");
        employee2.setName("Jane Smith");
        
        employee3 = new Employee();
        employee3.setEmployeeId("E303");
        employee3.setName("Bob Johnson");
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified" and add Employee "E301" as manager
        company.getDepartments().add(department1);
        company.getEmployees().add(employee1);
        department1.setManager(employee1);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = company.hasManager(department1);
        
        // Expected Output: true
        assertTrue("Department 'Verified' should have a manager assigned", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        company.getDepartments().add(department2);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = company.hasManager(department2);
        
        // Expected Output: false
        assertFalse("Department 'Unmanaged' should not have a manager assigned", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp", add and assign manager "E302", then delete "E302" from company
        company.getDepartments().add(department3);
        company.getEmployees().add(employee2);
        department3.setManager(employee2);
        
        // Remove employee from company (simulating deletion)
        company.getEmployees().remove(employee2);
        
        // Action: Verify manager assignment
        boolean result = company.hasManager(department3);
        
        // Expected Output: false
        assertFalse("Department 'Temp' should not have a manager after employee removal", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        Department nonExistentDepartment = new Department();
        nonExistentDepartment.setName("Missing");
        
        // Action: Verify manager for "Missing" (non-existent in company)
        boolean result = company.hasManager(nonExistentDepartment);
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept" and add Employee "E303" and immediately assign as manager
        Department newDept = new Department();
        newDept.setName("NewDept");
        
        company.getDepartments().add(newDept);
        company.getEmployees().add(employee3);
        company.assignManager(newDept, employee3);
        
        // Action: Verify assignment
        boolean result = company.hasManager(newDept);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should be detected", result);
    }
}