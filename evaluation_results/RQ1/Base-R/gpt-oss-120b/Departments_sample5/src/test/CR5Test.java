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
        
        // Create departments
        managedDept = new Department();
        managedDept.setName("Managed");
        
        unmanagedDept = new Department();
        unmanagedDept.setName("Unmanaged");
        
        newDept = new Department();
        newDept.setName("New");
        
        volatileDept = new Department();
        volatileDept.setName("Volatile");
        
        orphanDept = new Department();
        orphanDept.setName("Orphan");
        
        // Create employees
        m1 = new Employee();
        m1.setEmployeeId("M1");
        m1.setName("Manager 1");
        
        m2 = new Employee();
        m2.setEmployeeId("M2");
        m2.setName("Manager 2");
        
        m3 = new Employee();
        m3.setEmployeeId("M3");
        m3.setName("Manager 3");
    }
    
    @Test
    public void testCase1_companyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        company.addEmployee(m1);
        
        // Assign manager to "Managed" department
        company.assignManager(managedDept, m1);
        
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
    public void testCase2_fullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        Department dept1 = new Department();
        dept1.setName("Dept1");
        Department dept2 = new Department();
        dept2.setName("Dept2");
        Department dept3 = new Department();
        dept3.setName("Dept3");
        
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
        company.addEmployee(m1);
        company.addEmployee(m2);
        company.addEmployee(m3);
        
        // Assign managers to all departments
        company.assignManager(dept1, m1);
        company.assignManager(dept2, m2);
        company.assignManager(dept3, m3);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_emptyCompany() {
        // SetUp: Create Company with no departments
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when company has no departments", result);
    }
    
    @Test
    public void testCase4_singleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
        assertEquals("Department name should be Orphan", "Orphan", result.get(0).getName());
    }
    
    @Test
    public void testCase5_afterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        company.addDepartment(volatileDept);
        company.addEmployee(m1);
        
        // Assign manager M1 and then remove manager M1
        company.assignManager(volatileDept, m1);
        
        // Remove manager by setting it to null
        volatileDept.setManager(null);
        m1.setDepartment(null);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
        assertEquals("Department name should be Volatile", "Volatile", result.get(0).getName());
    }
}