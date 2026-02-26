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
        manager1 = new Employee("M1");
        manager2 = new Employee("M2");
        manager3 = new Employee("M3");
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Assign manager only to "Managed" department
        company.addEmployee(manager1);
        company.assignManager(manager1, managedDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null when there are unmanaged departments", result);
        assertEquals("Should contain 2 unmanaged departments", 2, result.size());
        assertTrue("Should contain unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain new department", result.contains(newDept));
        assertFalse("Should not contain managed department", result.contains(managedDept));
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
        
        // Assign manager to each department
        company.addEmployee(manager1);
        company.addEmployee(manager2);
        company.addEmployee(manager3);
        
        company.assignManager(manager1, dept1);
        company.assignManager(manager2, dept2);
        company.assignManager(manager3, dept3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments (already empty from setUp)
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when company has no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = new Department();
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertTrue("Should contain the orphan department", result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        Department volatileDept = new Department();
        company.addDepartment(volatileDept);
        
        // Assign manager M1
        company.addEmployee(manager1);
        company.assignManager(manager1, volatileDept);
        
        // Remove manager M1
        volatileDept.deleteEmployee(manager1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should contain exactly 1 department", 1, result.size());
        assertTrue("Should contain the volatile department", result.contains(volatileDept));
    }
}