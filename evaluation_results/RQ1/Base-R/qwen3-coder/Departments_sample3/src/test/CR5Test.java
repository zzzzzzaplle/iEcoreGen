import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Company company;
    private Department managedDept;
    private Department unmanagedDept;
    private Department newDept;
    private Employee manager1;
    private Employee manager2;
    private Employee manager3;
    
    @Before
    public void setUp() {
        company = new Company();
        managedDept = new Department();
        unmanagedDept = new Department();
        newDept = new Department();
        manager1 = new Employee();
        manager2 = new Employee();
        manager3 = new Employee();
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments
        // "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        managedDept.setName("Managed");
        unmanagedDept.setName("Unmanaged");
        newDept.setName("New");
        
        manager1.setName("M1");
        manager1.setId(1);
        
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        company.addEmployee(manager1);
        
        // Assign manager to "Managed" department
        company.assignManager(managedDept, manager1);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null when there are unmanaged departments", result);
        assertEquals("Should contain 2 unmanaged departments", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign managers to each
        Department dept1 = new Department();
        Department dept2 = new Department();
        Department dept3 = new Department();
        
        dept1.setName("Department1");
        dept2.setName("Department2");
        dept3.setName("Department3");
        
        manager1.setName("M1");
        manager1.setId(1);
        manager2.setName("M2");
        manager2.setId(2);
        manager3.setName("M3");
        manager3.setId(3);
        
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
        company.addEmployee(manager1);
        company.addEmployee(manager2);
        company.addEmployee(manager3);
        
        // Assign managers to all departments
        company.assignManager(dept1, manager1);
        company.assignManager(dept2, manager2);
        company.assignManager(dept3, manager3);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Company is already empty from setUp()
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when company has no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = new Department();
        orphanDept.setName("Orphan");
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
        assertEquals("Department name should be Orphan", "Orphan", result.get(0).getName());
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        Department volatileDept = new Department();
        volatileDept.setName("Volatile");
        
        manager1.setName("M1");
        manager1.setId(1);
        
        company.addDepartment(volatileDept);
        company.addEmployee(manager1);
        
        // Assign manager M1 and then remove manager M1
        company.assignManager(volatileDept, manager1);
        
        // Remove manager by setting it to null
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
        assertEquals("Department name should be Volatile", "Volatile", result.get(0).getName());
    }
}