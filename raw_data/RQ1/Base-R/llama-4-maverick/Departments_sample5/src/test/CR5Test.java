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
        managedDept = new Department();
        unmanagedDept = new Department();
        newDept = new Department();
        manager1 = new Employee();
        manager2 = new Employee();
        manager3 = new Employee();
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        managedDept.setName("Managed");
        unmanagedDept.setName("Unmanaged");
        newDept.setName("New");
        
        manager1.setName("M1");
        company.getEmployees().add(manager1);
        managedDept.setManager(manager1);
        
        List<Department> departments = new ArrayList<>();
        departments.add(managedDept);
        departments.add(unmanagedDept);
        departments.add(newDept);
        company.setDepartments(departments);
        
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
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        Department dept1 = new Department();
        Department dept2 = new Department();
        Department dept3 = new Department();
        
        dept1.setName("Dept1");
        dept2.setName("Dept2");
        dept3.setName("Dept3");
        
        manager1.setName("M1");
        manager2.setName("M2");
        manager3.setName("M3");
        
        company.getEmployees().add(manager1);
        company.getEmployees().add(manager2);
        company.getEmployees().add(manager3);
        
        dept1.setManager(manager1);
        dept2.setManager(manager2);
        dept3.setManager(manager3);
        
        List<Department> departments = new ArrayList<>();
        departments.add(dept1);
        departments.add(dept2);
        departments.add(dept3);
        company.setDepartments(departments);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Company is already created in setUp() with empty departments list
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when company has no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = new Department();
        orphanDept.setName("Orphan");
        
        List<Department> departments = new ArrayList<>();
        departments.add(orphanDept);
        company.setDepartments(departments);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        Department volatileDept = new Department();
        volatileDept.setName("Volatile");
        
        manager1.setName("M1");
        company.getEmployees().add(manager1);
        
        // Assign manager first
        volatileDept.setManager(manager1);
        
        List<Department> departments = new ArrayList<>();
        departments.add(volatileDept);
        company.setDepartments(departments);
        
        // Remove the manager by setting it to null
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
    }
}