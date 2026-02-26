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
        
        // Create employees for managers
        m1 = new Employee();
        m1.setEmployeeId("M1");
        m1.setName("Manager 1");
        
        m2 = new Employee();
        m2.setEmployeeId("M2");
        m2.setName("Manager 2");
        
        m3 = new Employee();
        m3.setEmployeeId("M3");
        m3.setName("Manager 3");
        
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
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        managedDept.setManager(m1);
        
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Add employees to company
        company.addEmployee(m1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null when there are departments without managers", result);
        assertEquals("Should contain 2 departments without managers", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        managedDept.setManager(m1);
        unmanagedDept.setManager(m2);
        newDept.setManager(m3);
        
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Add employees to company
        company.addEmployee(m1);
        company.addEmployee(m2);
        company.addEmployee(m3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // (company is already empty from setUp)
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when company has no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null when there is a department without manager", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertEquals("Should contain the Orphan department", orphanDept, result.get(0));
        assertEquals("Department name should be 'Orphan'", "Orphan", result.get(0).getName());
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        volatileDept.setManager(m1);
        company.addDepartment(volatileDept);
        company.addEmployee(m1);
        
        // Remove the manager
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null when there is a department without manager", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertEquals("Should contain the Volatile department", volatileDept, result.get(0));
        assertEquals("Department name should be 'Volatile'", "Volatile", result.get(0).getName());
    }
}