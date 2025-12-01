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
        company.setCompanyId("COMP001");
        company.setName("Test Company");
        
        managedDept = new Department();
        managedDept.setDepartmentId("DEPT001");
        managedDept.setName("Managed");
        
        unmanagedDept = new Department();
        unmanagedDept.setDepartmentId("DEPT002");
        unmanagedDept.setName("Unmanaged");
        
        newDept = new Department();
        newDept.setDepartmentId("DEPT003");
        newDept.setName("New");
        
        manager1 = new Employee();
        manager1.setId("EMP001");
        manager1.setName("Manager One");
        
        manager2 = new Employee();
        manager2.setId("EMP002");
        manager2.setName("Manager Two");
        
        manager3 = new Employee();
        manager3.setId("EMP003");
        manager3.setName("Manager Three");
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.getDepartments().add(managedDept);
        company.getDepartments().add(unmanagedDept);
        company.getDepartments().add(newDept);
        
        company.getEmployees().add(manager1);
        
        // Assign manager to Managed department
        company.assignManager(managedDept, manager1);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 2 departments without managers", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        Department dept1 = new Department();
        dept1.setDepartmentId("DEPT001");
        dept1.setName("Department One");
        
        Department dept2 = new Department();
        dept2.setDepartmentId("DEPT002");
        dept2.setName("Department Two");
        
        Department dept3 = new Department();
        dept3.setDepartmentId("DEPT003");
        dept3.setName("Department Three");
        
        company.getDepartments().add(dept1);
        company.getDepartments().add(dept2);
        company.getDepartments().add(dept3);
        
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
        assertNull("Result should be null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Company is already initialized with empty departments list in setUp()
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when company has no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = new Department();
        orphanDept.setDepartmentId("DEPT001");
        orphanDept.setName("Orphan");
        
        company.getDepartments().add(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 1 department without manager", 1, result.size());
        assertEquals("Should contain Orphan department", orphanDept, result.get(0));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        Department volatileDept = new Department();
        volatileDept.setDepartmentId("DEPT001");
        volatileDept.setName("Volatile");
        
        company.getDepartments().add(volatileDept);
        company.getEmployees().add(manager1);
        
        // Assign manager M1 and then remove manager M1
        company.assignManager(volatileDept, manager1);
        
        // Remove manager by setting it to null
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 1 department without manager", 1, result.size());
        assertEquals("Should contain Volatile department", volatileDept, result.get(0));
    }
}