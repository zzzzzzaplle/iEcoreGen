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
        unmanagedDept = new Department();
        newDept = new Department();
        orphanDept = new Department();
        volatileDept = new Department();
        
        // Create managers
        manager1 = new Employee();
        manager2 = new Employee();
        manager3 = new Employee();
        
        manager1.setName("M1");
        manager2.setName("M2");
        manager3.setName("M3");
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        List<Department> departments = new ArrayList<>();
        departments.add(managedDept);
        departments.add(unmanagedDept);
        departments.add(newDept);
        company.setDepartments(departments);
        
        // Add manager1 to company employees
        List<Employee> employees = new ArrayList<>();
        employees.add(manager1);
        company.setEmployees(employees);
        
        // Assign manager to managedDept only
        company.assignManager(managedDept, manager1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManagers();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Should return list of departments without managers", result);
        assertEquals("Should contain 2 departments without managers", 2, result.size());
        assertTrue("Should contain unmanagedDept", result.contains(unmanagedDept));
        assertTrue("Should contain newDept", result.contains(newDept));
        assertFalse("Should not contain managedDept", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        List<Department> departments = new ArrayList<>();
        departments.add(managedDept);
        departments.add(unmanagedDept);
        departments.add(newDept);
        company.setDepartments(departments);
        
        // Add all managers to company employees
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
        List<Department> result = company.listDepartmentsWithoutManagers();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManagers();
        
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
        List<Department> result = company.listDepartmentsWithoutManagers();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Should return list with single department", result);
        assertEquals("Should contain exactly one department", 1, result.size());
        assertTrue("Should contain orphanDept", result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        List<Department> departments = new ArrayList<>();
        departments.add(volatileDept);
        company.setDepartments(departments);
        
        // Add manager1 to company employees
        List<Employee> employees = new ArrayList<>();
        employees.add(manager1);
        company.setEmployees(employees);
        
        // Assign manager M1 and then remove manager M1
        company.assignManager(volatileDept, manager1);
        volatileDept.setManager(null); // Directly remove manager
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManagers();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Should return list with volatile department", result);
        assertEquals("Should contain exactly one department", 1, result.size());
        assertTrue("Should contain volatileDept", result.contains(volatileDept));
    }
}