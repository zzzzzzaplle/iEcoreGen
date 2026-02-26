import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Company company;
    private Employee manager1;
    private Employee manager2;
    private Employee manager3;
    private Department managedDept;
    private Department unmanagedDept;
    private Department newDept;
    private Department orphanDept;
    private Department volatileDept;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        manager1 = new Employee();
        manager1.setId("M1");
        manager1.setName("Manager One");
        
        manager2 = new Employee();
        manager2.setId("M2");
        manager2.setName("Manager Two");
        
        manager3 = new Employee();
        manager3.setId("M3");
        manager3.setName("Manager Three");
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        managedDept = new Department();
        managedDept.setDepartmentId("D1");
        managedDept.setName("Managed");
        
        unmanagedDept = new Department();
        unmanagedDept.setDepartmentId("D2");
        unmanagedDept.setName("Unmanaged");
        
        newDept = new Department();
        newDept.setDepartmentId("D3");
        newDept.setName("New");
        
        // Add departments to company
        company.getDepartments().add(managedDept);
        company.getDepartments().add(unmanagedDept);
        company.getDepartments().add(newDept);
        
        // Add manager to company employees
        company.getEmployees().add(manager1);
        
        // Assign manager to "Managed" department
        company.assignManager(managedDept, manager1);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 departments without managers", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        Department dept1 = new Department();
        dept1.setDepartmentId("D1");
        dept1.setName("Department1");
        
        Department dept2 = new Department();
        dept2.setDepartmentId("D2");
        dept2.setName("Department2");
        
        Department dept3 = new Department();
        dept3.setDepartmentId("D3");
        dept3.setName("Department3");
        
        // Add departments to company
        company.getDepartments().add(dept1);
        company.getDepartments().add(dept2);
        company.getDepartments().add(dept3);
        
        // Add managers to company employees
        company.getEmployees().add(manager1);
        company.getEmployees().add(manager2);
        company.getEmployees().add(manager3);
        
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
        orphanDept = new Department();
        orphanDept.setDepartmentId("D1");
        orphanDept.setName("Orphan");
        
        company.getDepartments().add(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
        assertEquals("Department name should be Orphan", "Orphan", result.get(0).getName());
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        volatileDept = new Department();
        volatileDept.setDepartmentId("D1");
        volatileDept.setName("Volatile");
        
        company.getDepartments().add(volatileDept);
        company.getEmployees().add(manager1);
        
        // Assign manager to department
        company.assignManager(volatileDept, manager1);
        
        // Remove manager by setting it to null
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
        assertEquals("Department name should be Volatile", "Volatile", result.get(0).getName());
    }
}