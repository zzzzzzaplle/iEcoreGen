import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        managedDept = new Department();
        unmanagedDept = new Department();
        newDept = new Department();
        orphanDept = new Department();
        volatileDept = new Department();
        manager1 = new Employee();
        manager2 = new Employee();
        manager3 = new Employee();
    }

    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Assign manager only to managedDept
        company.addEmployee(manager1);
        company.assignManager(manager1, managedDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 2 departments without manager", 2, result.size());
        assertTrue("Should contain unmanagedDept", result.contains(unmanagedDept));
        assertTrue("Should contain newDept", result.contains(newDept));
        assertFalse("Should not contain managedDept", result.contains(managedDept));
    }

    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Assign managers to all departments
        company.addEmployee(manager1);
        company.addEmployee(manager2);
        company.addEmployee(manager3);
        company.assignManager(manager1, managedDept);
        company.assignManager(manager2, unmanagedDept);
        company.assignManager(manager3, newDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when all departments have managers", result);
    }

    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Company with no departments (already empty from setUp)
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when company has no departments", result);
    }

    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 1 department without manager", 1, result.size());
        assertTrue("Should contain orphanDept", result.contains(orphanDept));
    }

    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        company.addDepartment(volatileDept);
        company.addEmployee(manager1);
        
        // Assign manager M1 and then remove manager M1
        company.assignManager(manager1, volatileDept);
        volatileDept.setManager(null); // Simulate manager removal
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 1 department without manager", 1, result.size());
        assertTrue("Should contain volatileDept", result.contains(volatileDept));
    }
}