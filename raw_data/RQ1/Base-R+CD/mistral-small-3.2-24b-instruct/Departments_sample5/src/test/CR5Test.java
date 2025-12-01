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
    private Employee m1;
    private Employee m2;
    private Employee m3;

    @Before
    public void setUp() {
        company = new Company();
        managedDept = new Department();
        unmanagedDept = new Department();
        newDept = new Department();
        volatileDept = new Department();
        orphanDept = new Department();
        
        m1 = new Employee();
        m2 = new Employee();
        m3 = new Employee();
    }

    @Test
    public void testCase1_companyWithBothManagedAndUnmanagedDepartments() {
        // Setup: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        company.addEmployee(m1);
        company.assignManager(m1, managedDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 departments", 2, result.size());
        assertTrue("Should contain unmanagedDept", result.contains(unmanagedDept));
        assertTrue("Should contain newDept", result.contains(newDept));
        assertFalse("Should not contain managedDept", result.contains(managedDept));
    }

    @Test
    public void testCase2_fullyManagedCompany() {
        // Setup: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        Department dept1 = new Department();
        Department dept2 = new Department();
        Department dept3 = new Department();
        
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
        
        company.addEmployee(m1);
        company.addEmployee(m2);
        company.addEmployee(m3);
        
        company.assignManager(m1, dept1);
        company.assignManager(m2, dept2);
        company.assignManager(m3, dept3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when all departments have managers", result);
    }

    @Test
    public void testCase3_emptyCompany() {
        // Setup: Create Company with no departments
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when company has no departments", result);
    }

    @Test
    public void testCase4_singleUnmanagedDepartment() {
        // Setup: Create Company with only "Orphan" department
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain orphanDept", result.contains(orphanDept));
    }

    @Test
    public void testCase5_afterManagerRemoval() {
        // Setup: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        company.addDepartment(volatileDept);
        company.addEmployee(m1);
        company.assignManager(m1, volatileDept);
        
        // Remove manager by setting it to null
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain volatileDept", result.contains(volatileDept));
    }
}