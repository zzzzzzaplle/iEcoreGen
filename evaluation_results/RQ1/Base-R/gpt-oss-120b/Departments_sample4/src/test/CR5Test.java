import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        managedDept.setName("Managed");
        unmanagedDept = new Department();
        unmanagedDept.setName("Unmanaged");
        newDept = new Department();
        newDept.setName("New");
        
        manager1 = new Employee();
        manager1.setName("M1");
        manager2 = new Employee();
        manager2.setName("M2");
        manager3 = new Employee();
        manager3.setName("M3");
    }
    
    @Test
    public void testCase1_companyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Add employees to company
        company.addEmployee(manager1);
        
        // Assign manager to "Managed" department
        company.assignManager(managedDept, manager1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null when there are unmanaged departments", result);
        assertEquals("Should contain 2 departments", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_fullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        Department dept1 = new Department();
        dept1.setName("Department1");
        Department dept2 = new Department();
        dept2.setName("Department2");
        Department dept3 = new Department();
        dept3.setName("Department3");
        
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
        
        company.addEmployee(manager1);
        company.addEmployee(manager2);
        company.addEmployee(manager3);
        
        company.assignManager(dept1, manager1);
        company.assignManager(dept2, manager2);
        company.assignManager(dept3, manager3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_emptyCompany() {
        // SetUp: Create Company with no departments
        // Company is already empty from setUp()
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when company has no departments", result);
    }
    
    @Test
    public void testCase4_singleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = new Department();
        orphanDept.setName("Orphan");
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertEquals("Should contain the Orphan department", orphanDept, result.get(0));
    }
    
    @Test
    public void testCase5_afterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        Department volatileDept = new Department();
        volatileDept.setName("Volatile");
        company.addDepartment(volatileDept);
        company.addEmployee(manager1);
        
        // Assign manager M1 and then remove manager M1
        company.assignManager(volatileDept, manager1);
        
        // Remove manager by setting it to null
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertEquals("Should contain the Volatile department", volatileDept, result.get(0));
    }
}