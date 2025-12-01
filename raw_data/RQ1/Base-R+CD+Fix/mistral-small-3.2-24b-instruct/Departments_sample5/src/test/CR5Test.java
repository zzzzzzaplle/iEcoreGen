import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Company company;
    private Department department1;
    private Department department2;
    private Department department3;
    private Employee manager1;
    private Employee manager2;
    private Employee manager3;
    
    @Before
    public void setUp() {
        company = new Company();
        department1 = new Department();
        department2 = new Department();
        department3 = new Department();
        manager1 = new Employee();
        manager2 = new Employee();
        manager3 = new Employee();
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.addDepartment(department1);
        company.addDepartment(department2);
        company.addDepartment(department3);
        company.addEmployee(manager1);
        
        // Assign manager to first department only
        department1.assignManager(manager1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"] - department2 and department3
        assertNotNull("Result should not be null when there are unmanaged departments", result);
        assertEquals("Should contain 2 unmanaged departments", 2, result.size());
        assertTrue("Should contain department2", result.contains(department2));
        assertTrue("Should contain department3", result.contains(department3));
        assertFalse("Should not contain department1 (has manager)", result.contains(department1));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        company.addDepartment(department1);
        company.addDepartment(department2);
        company.addDepartment(department3);
        company.addEmployee(manager1);
        company.addEmployee(manager2);
        company.addEmployee(manager3);
        
        // Assign managers to all departments
        department1.assignManager(manager1);
        department2.assignManager(manager2);
        department3.assignManager(manager3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Company is already empty from setUp()
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when company has no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        company.addDepartment(department1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"] - department1
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain 1 unmanaged department", 1, result.size());
        assertTrue("Should contain department1", result.contains(department1));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        company.addDepartment(department1);
        company.addEmployee(manager1);
        
        // Assign manager
        department1.assignManager(manager1);
        
        // Remove manager
        department1.deleteEmployee(manager1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"] - department1
        assertNotNull("Result should not be null when department becomes unmanaged", result);
        assertEquals("Should contain 1 unmanaged department", 1, result.size());
        assertTrue("Should contain department1", result.contains(department1));
    }
}