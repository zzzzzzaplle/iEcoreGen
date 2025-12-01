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
    private Department orphanDept;
    private Department volatileDept;
    private Employee volatileManager;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Setup departments for various test cases
        managedDept = new Department();
        managedDept.setId("D1");
        managedDept.setName("Managed");
        
        unmanagedDept = new Department();
        unmanagedDept.setId("D2");
        unmanagedDept.setName("Unmanaged");
        
        newDept = new Department();
        newDept.setId("D3");
        newDept.setName("New");
        
        orphanDept = new Department();
        orphanDept.setId("D4");
        orphanDept.setName("Orphan");
        
        volatileDept = new Department();
        volatileDept.setId("D5");
        volatileDept.setName("Volatile");
        
        // Setup managers
        manager1 = new Employee();
        manager1.setId("M1");
        manager1.setName("Manager1");
        
        manager2 = new Employee();
        manager2.setId("M2");
        manager2.setName("Manager2");
        
        manager3 = new Employee();
        manager3.setId("M3");
        manager3.setName("Manager3");
        
        volatileManager = new Employee();
        volatileManager.setId("VM1");
        volatileManager.setName("VolatileManager");
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // Setup: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Add manager to managed department
        company.addEmployee(manager1);
        managedDept.addEmployee(manager1);
        managedDept.setManager(manager1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 departments without managers", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // Setup: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        Department dept1 = new Department();
        dept1.setId("D1");
        dept1.setName("Dept1");
        
        Department dept2 = new Department();
        dept2.setId("D2");
        dept2.setName("Dept2");
        
        Department dept3 = new Department();
        dept3.setId("D3");
        dept3.setName("Dept3");
        
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
        
        // Add managers to company and departments
        company.addEmployee(manager1);
        company.addEmployee(manager2);
        company.addEmployee(manager3);
        
        dept1.addEmployee(manager1);
        dept1.setManager(manager1);
        
        dept2.addEmployee(manager2);
        dept2.setManager(manager2);
        
        dept3.addEmployee(manager3);
        dept3.setManager(manager3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // Setup: Create Company with no departments
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when company has no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // Setup: Create Company with only "Orphan" department
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // Setup: Create Company with Department "Volatile"
        company.addDepartment(volatileDept);
        company.addEmployee(volatileManager);
        volatileDept.addEmployee(volatileManager);
        
        // Assign manager M1 and then remove manager M1
        volatileDept.setManager(volatileManager);
        volatileDept.setManager(null); // Remove manager
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
    }
}