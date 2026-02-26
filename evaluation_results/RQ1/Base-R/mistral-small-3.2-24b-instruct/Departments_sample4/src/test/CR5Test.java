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
        // Initialize common test objects
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
    public void testCase1_companyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments:
        // - "Managed" (has manager M1)
        // - "Unmanaged" (no manager)
        // - "New" (no manager)
        managedDept.setName("Managed");
        unmanagedDept.setName("Unmanaged");
        newDept.setName("New");
        
        // Assign manager to Managed department
        managedDept.setManager(manager1);
        
        // Add departments to company
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null when there are unmanaged departments", result);
        assertEquals("Should contain 2 unmanaged departments", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_fullyManagedCompany() {
        // SetUp: Create Company with 3 departments
        Department dept1 = new Department();
        Department dept2 = new Department();
        Department dept3 = new Department();
        
        dept1.setName("Dept1");
        dept2.setName("Dept2");
        dept3.setName("Dept3");
        
        // Assign manager M1, M2, M3 to each department
        dept1.setManager(manager1);
        dept2.setManager(manager2);
        dept3.setManager(manager3);
        
        // Add departments to company
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_emptyCompany() {
        // SetUp: Create Company with no departments
        // (company is already empty from setUp)
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when company has no departments", result);
    }
    
    @Test
    public void testCase4_singleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        orphanDept.setName("Orphan");
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
        assertEquals("Department name should be Orphan", "Orphan", result.get(0).getName());
    }
    
    @Test
    public void testCase5_afterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        volatileDept.setName("Volatile");
        company.addDepartment(volatileDept);
        
        // Assign manager M1 and then remove manager M1
        volatileDept.setManager(manager1);
        volatileDept.setManager(null); // Remove manager by setting to null
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
        assertEquals("Department name should be Volatile", "Volatile", result.get(0).getName());
    }
}