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
    private Department orphanDept;
    private Department volatileDept;
    private Employee manager1;
    private Employee manager2;
    private Employee manager3;

    @Before
    public void setUp() {
        company = new Company();
        
        // Initialize departments
        managedDept = new Department();
        managedDept.setName("Managed");
        
        unmanagedDept = new Department();
        unmanagedDept.setName("Unmanaged");
        
        newDept = new Department();
        newDept.setName("New");
        
        orphanDept = new Department();
        orphanDept.setName("Orphan");
        
        volatileDept = new Department();
        volatileDept.setName("Volatile");
        
        // Initialize managers
        manager1 = new Employee();
        manager1.setName("M1");
        
        manager2 = new Employee();
        manager2.setName("M2");
        
        manager3 = new Employee();
        manager3.setName("M3");
    }

    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments
        company.getDepartments().add(managedDept);
        company.getDepartments().add(unmanagedDept);
        company.getDepartments().add(newDept);
        
        // Add employees to company
        company.getEmployees().add(manager1);
        
        // Assign manager to "Managed" department
        managedDept.setManager(manager1);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Should return list of departments without managers", result);
        assertEquals("Should contain 2 departments without managers", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
    }

    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments
        Department dept1 = new Department();
        dept1.setName("Dept1");
        Department dept2 = new Department();
        dept2.setName("Dept2");
        Department dept3 = new Department();
        dept3.setName("Dept3");
        
        company.getDepartments().add(dept1);
        company.getDepartments().add(dept2);
        company.getDepartments().add(dept3);
        
        // Add employees to company
        company.getEmployees().add(manager1);
        company.getEmployees().add(manager2);
        company.getEmployees().add(manager3);
        
        // Assign manager M1, M2, M3 to each department
        dept1.setManager(manager1);
        dept2.setManager(manager2);
        dept3.setManager(manager3);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }

    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when company has no departments", result);
    }

    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        company.getDepartments().add(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Should return list with single department", result);
        assertEquals("Should contain exactly one department", 1, result.size());
        assertEquals("Should contain Orphan department", orphanDept, result.get(0));
    }

    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        company.getDepartments().add(volatileDept);
        company.getEmployees().add(manager1);
        
        // Assign manager M1 and then remove manager M1
        volatileDept.setManager(manager1);
        volatileDept.setManager(null); // Remove manager
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Should return list with Volatile department", result);
        assertEquals("Should contain exactly one department", 1, result.size());
        assertEquals("Should contain Volatile department", volatileDept, result.get(0));
    }
}