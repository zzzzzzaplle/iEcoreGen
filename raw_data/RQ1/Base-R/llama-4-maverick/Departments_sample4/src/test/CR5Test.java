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
        List<Department> departments = new ArrayList<>();
        departments.add(managedDept);
        departments.add(unmanagedDept);
        departments.add(newDept);
        company.setDepartments(departments);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(manager1);
        company.setEmployees(employees);
        
        // Assign manager to "Managed" department
        managedDept.assignManager(manager1);
        
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
        List<Department> departments = new ArrayList<>();
        departments.add(managedDept);
        departments.add(unmanagedDept);
        departments.add(newDept);
        company.setDepartments(departments);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(manager1);
        employees.add(manager2);
        employees.add(manager3);
        company.setEmployees(employees);
        
        // Assign managers to all departments
        managedDept.assignManager(manager1);
        unmanagedDept.assignManager(manager2);
        newDept.assignManager(manager3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Company is already initialized with empty department list by default
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        List<Department> departments = new ArrayList<>();
        departments.add(orphanDept);
        company.setDepartments(departments);
        
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
        List<Department> departments = new ArrayList<>();
        departments.add(volatileDept);
        company.setDepartments(departments);
        
        // Add employee to company
        List<Employee> employees = new ArrayList<>();
        employees.add(manager1);
        company.setEmployees(employees);
        
        // Assign manager M1 and then remove manager M1
        volatileDept.assignManager(manager1);
        volatileDept.setManager(null); // Remove manager by setting to null
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(volatileDept));
    }
}