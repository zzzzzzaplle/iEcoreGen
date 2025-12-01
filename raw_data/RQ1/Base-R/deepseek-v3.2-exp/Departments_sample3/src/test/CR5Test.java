import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR5Test {
    
    private Company company;
    private Department managedDept;
    private Department unmanagedDept;
    private Department newDept;
    private Department volatileDept;
    private Employee manager1;
    private Employee manager2;
    private Employee manager3;
    
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
        
        // Create managers
        manager1 = new Employee();
        manager1.setName("M1");
        manager1.setEmployeeId("EMP001");
        
        manager2 = new Employee();
        manager2.setName("M2");
        manager2.setEmployeeId("EMP002");
        
        manager3 = new Employee();
        manager3.setName("M3");
        manager3.setEmployeeId("EMP003");
    }
    
    @Test
    public void testCase1_companyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.getDepartments().addAll(Arrays.asList(managedDept, unmanagedDept, newDept));
        company.getEmployees().add(manager1);
        
        // Assign manager to "Managed" department
        managedDept.setManager(manager1);
        manager1.setDepartment(managedDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Should not return null when there are departments without managers", result);
        assertEquals("Should contain 2 departments", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_fullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        company.getDepartments().addAll(Arrays.asList(managedDept, unmanagedDept, newDept));
        company.getEmployees().addAll(Arrays.asList(manager1, manager2, manager3));
        
        // Assign managers to all departments
        managedDept.setManager(manager1);
        unmanagedDept.setManager(manager2);
        newDept.setManager(manager3);
        manager1.setDepartment(managedDept);
        manager2.setDepartment(unmanagedDept);
        manager3.setDepartment(newDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_emptyCompany() {
        // SetUp: Create Company with no departments
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when company has no departments", result);
    }
    
    @Test
    public void testCase4_singleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = new Department();
        orphanDept.setName("Orphan");
        company.getDepartments().add(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Should not return null when there is a department without manager", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_afterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        company.getDepartments().add(volatileDept);
        company.getEmployees().add(manager1);
        
        // Assign manager and then remove it
        volatileDept.setManager(manager1);
        manager1.setDepartment(volatileDept);
        volatileDept.setManager(null); // Remove manager
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Should not return null when there is a department without manager", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
    }
}