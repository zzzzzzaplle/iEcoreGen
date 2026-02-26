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
    private Department orphanDept;
    private Employee manager1;
    private Employee manager2;
    private Employee manager3;
    private Office office1;
    private Office office2;
    
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
        
        // Create managers
        manager1 = new Employee();
        manager1.setName("M1");
        
        manager2 = new Employee();
        manager2.setName("M2");
        
        manager3 = new Employee();
        manager3.setName("M3");
        
        // Create offices
        office1 = new Office();
        office1.setLocation("Office1");
        
        office2 = new Office();
        office2.setLocation("Office2");
    }
    
    @Test
    public void testCase1_companyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        
        // Add departments to company
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Add manager to company and assign to managed department
        company.addEmployee(manager1);
        company.assignManager(managedDept, manager1);
        
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
    public void testCase2_fullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        
        // Create 3 departments
        Department dept1 = new Department();
        dept1.setName("Dept1");
        Department dept2 = new Department();
        dept2.setName("Dept2");
        Department dept3 = new Department();
        dept3.setName("Dept3");
        
        // Add departments to company
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
        
        // Add managers to company and assign to departments
        company.addEmployee(manager1);
        company.addEmployee(manager2);
        company.addEmployee(manager3);
        
        company.assignManager(dept1, manager1);
        company.assignManager(dept2, manager2);
        company.assignManager(dept3, manager3);
        
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
        
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null when there is a department without manager", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
        assertEquals("Department name should be Orphan", "Orphan", result.get(0).getName());
    }
    
    @Test
    public void testCase5_afterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        
        company.addDepartment(volatileDept);
        company.addEmployee(manager1);
        
        // Assign manager
        company.assignManager(volatileDept, manager1);
        
        // Remove manager by setting it to null
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null when there is a department without manager", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
        assertEquals("Department name should be Volatile", "Volatile", result.get(0).getName());
    }
}