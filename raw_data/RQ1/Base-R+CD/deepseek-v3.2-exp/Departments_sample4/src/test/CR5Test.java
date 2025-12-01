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
        // SetUp: Create Company with Departments
        managedDept.setName("Managed");
        unmanagedDept.setName("Unmanaged");
        newDept.setName("New");
        
        // Add departments to company
        company.getDepartments().add(managedDept);
        company.getDepartments().add(unmanagedDept);
        company.getDepartments().add(newDept);
        
        // Set up manager for "Managed" department
        manager1.setName("M1");
        company.getEmployees().add(manager1);
        managedDept.setManager(manager1);
        
        // Action: List departments without manager
        List<Department> result = listDepartmentsWithoutManager(company);
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(unmanagedDept));
        assertTrue(result.contains(newDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments
        Department dept1 = new Department();
        Department dept2 = new Department();
        Department dept3 = new Department();
        dept1.setName("Dept1");
        dept2.setName("Dept2");
        dept3.setName("Dept3");
        
        company.getDepartments().add(dept1);
        company.getDepartments().add(dept2);
        company.getDepartments().add(dept3);
        
        // Assign manager M1, M2, M3 to each department
        manager1.setName("M1");
        manager2.setName("M2");
        manager3.setName("M3");
        
        company.getEmployees().add(manager1);
        company.getEmployees().add(manager2);
        company.getEmployees().add(manager3);
        
        dept1.setManager(manager1);
        dept2.setManager(manager2);
        dept3.setManager(manager3);
        
        // Action: List departments without manager
        List<Department> result = listDepartmentsWithoutManager(company);
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Action: List departments without manager
        List<Department> result = listDepartmentsWithoutManager(company);
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = new Department();
        orphanDept.setName("Orphan");
        company.getDepartments().add(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = listDepartmentsWithoutManager(company);
        
        // Expected Output: ["Orphan"]
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orphanDept, result.get(0));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        Department volatileDept = new Department();
        volatileDept.setName("Volatile");
        company.getDepartments().add(volatileDept);
        
        // Assign manager M1 and then remove manager M1
        Employee m1 = new Employee();
        m1.setName("M1");
        company.getEmployees().add(m1);
        volatileDept.setManager(m1);
        
        // Remove the manager
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = listDepartmentsWithoutManager(company);
        
        // Expected Output: ["Volatile"]
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(volatileDept, result.get(0));
    }
    
    /**
     * Implementation of computational requirement CR5:
     * List the departments of the company that have not been assigned a manager.
     * If all departments have already been assigned a manager, return null.
     */
    private List<Department> listDepartmentsWithoutManager(Company company) {
        if (company == null || company.getDepartments() == null || company.getDepartments().isEmpty()) {
            return null;
        }
        
        List<Department> departmentsWithoutManager = new ArrayList<>();
        for (Department dept : company.getDepartments()) {
            if (dept.getManager() == null) {
                departmentsWithoutManager.add(dept);
            }
        }
        
        // If all departments have managers, return null
        if (departmentsWithoutManager.isEmpty()) {
            return null;
        }
        
        return departmentsWithoutManager;
    }
}