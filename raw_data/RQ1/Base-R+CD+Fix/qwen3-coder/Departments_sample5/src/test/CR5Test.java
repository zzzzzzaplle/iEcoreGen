import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private Company company;
    private Department managedDept;
    private Department unmanagedDept;
    private Department newDept;
    private Department volatileDept;
    private Department orphanDept;
    private Employee manager1;
    private Employee manager2;
    private Employee manager3;
    
    @Before
    public void setUp() {
        company = new Company();
        managedDept = new Department();
        unmanagedDept = new Department();
        newDept = new Department();
        volatileDept = new Department();
        orphanDept = new Department();
        manager1 = new Employee();
        manager2 = new Employee();
        manager3 = new Employee();
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Assign manager only to managedDept
        company.addEmployee(manager1);
        managedDept.assignManager(manager1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 departments without managers", 2, result.size());
        assertTrue("Should contain unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain new department", result.contains(newDept));
        assertFalse("Should not contain managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        Department dept1 = new Department();
        Department dept2 = new Department();
        Department dept3 = new Department();
        
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
        
        company.addEmployee(manager1);
        company.addEmployee(manager2);
        company.addEmployee(manager3);
        
        dept1.assignManager(manager1);
        dept2.assignManager(manager2);
        dept3.assignManager(manager3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when company has no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain orphan department", result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        company.addDepartment(volatileDept);
        company.addEmployee(manager1);
        
        // Assign manager
        volatileDept.assignManager(manager1);
        
        // Remove manager by setting it to null
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain volatile department", result.contains(volatileDept));
    }
}