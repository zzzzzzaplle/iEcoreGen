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
        
        // Create departments
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
        
        // Create employees
        manager1 = new Employee();
        manager1.setName("Manager1");
        manager1.setEmployeeId("M1");
        
        manager2 = new Employee();
        manager2.setName("Manager2");
        manager2.setEmployeeId("M2");
        
        manager3 = new Employee();
        manager3.setName("Manager3");
        manager3.setEmployeeId("M3");
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        List<Department> departments = new ArrayList<>();
        departments.add(managedDept);
        departments.add(unmanagedDept);
        departments.add(newDept);
        company.setDepartments(departments);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(manager1);
        company.setEmployees(employees);
        
        // Assign manager to "Managed" department
        company.assignManager(managedDept, manager1);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null when there are unmanaged departments", result);
        assertEquals("Should have 2 unmanaged departments", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
        
        // Verify department names in result
        List<String> resultNames = new ArrayList<>();
        for (Department dept : result) {
            resultNames.add(dept.getName());
        }
        assertTrue("Should contain 'Unmanaged'", resultNames.contains("Unmanaged"));
        assertTrue("Should contain 'New'", resultNames.contains("New"));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        List<Department> departments = new ArrayList<>();
        departments.add(managedDept);
        departments.add(unmanagedDept);
        departments.add(newDept);
        company.setDepartments(departments);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(manager1);
        employees.add(manager2);
        employees.add(manager3);
        company.setEmployees(employees);
        
        // Assign managers to all departments
        company.assignManager(managedDept, manager1);
        company.assignManager(unmanagedDept, manager2);
        company.assignManager(newDept, manager3);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // company already has empty department list from setUp()
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when company has no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        List<Department> departments = new ArrayList<>();
        departments.add(orphanDept);
        company.setDepartments(departments);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should have 1 unmanaged department", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
        assertEquals("Department name should be 'Orphan'", "Orphan", result.get(0).getName());
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        List<Department> departments = new ArrayList<>();
        departments.add(volatileDept);
        company.setDepartments(departments);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(manager1);
        company.setEmployees(employees);
        
        // Assign manager first
        company.assignManager(volatileDept, manager1);
        
        // Remove manager by setting it to null
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null when there is an unmanaged department", result);
        assertEquals("Should have 1 unmanaged department", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
        assertEquals("Department name should be 'Volatile'", "Volatile", result.get(0).getName());
    }
}