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
    private Employee manager1;
    
    @Before
    public void setUp() {
        company = new Company();
        managedDept = new Department();
        unmanagedDept = new Department();
        newDept = new Department();
        manager1 = new Employee("M1");
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        company.addEmployee(manager1);
        
        // Assign manager to only one department
        company.assignManager(manager1, managedDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(unmanagedDept));
        assertTrue(result.contains(newDept));
        assertFalse(result.contains(managedDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments
        Department dept1 = new Department();
        Department dept2 = new Department();
        Department dept3 = new Department();
        
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
        
        // Create and add managers
        Employee m1 = new Employee("M1");
        Employee m2 = new Employee("M2");
        Employee m3 = new Employee("M3");
        
        company.addEmployee(m1);
        company.addEmployee(m2);
        company.addEmployee(m3);
        
        // Assign managers to all departments
        company.assignManager(m1, dept1);
        company.assignManager(m2, dept2);
        company.assignManager(m3, dept3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments (already empty from setUp)
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = new Department();
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        Department volatileDept = new Department();
        company.addDepartment(volatileDept);
        company.addEmployee(manager1);
        
        // Assign manager M1 and then remove manager M1
        company.assignManager(manager1, volatileDept);
        volatileDept.deleteEmployee(manager1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(volatileDept));
    }
}