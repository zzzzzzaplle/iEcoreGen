import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    private Company company;
    private Employee manager1;
    private Employee manager2;
    private Employee manager3;
    private Department dept1;
    private Department dept2;
    private Department dept3;
    
    @Before
    public void setUp() {
        company = new Company();
        manager1 = new Employee("M1");
        manager2 = new Employee("M2");
        manager3 = new Employee("M3");
    }
    
    @Test
    public void testCase1_companyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments
        dept1 = new Department("Managed");
        dept2 = new Department("Unmanaged");
        dept3 = new Department("New");
        
        // Add departments to company
        company.getDepartments().add(dept1);
        company.getDepartments().add(dept2);
        company.getDepartments().add(dept3);
        
        // Add managers to company employees
        company.getEmployees().add(manager1);
        
        // Assign manager only to dept1
        dept1.setManager(manager1);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Should return list of unmanaged departments", result);
        assertEquals("Should contain 2 departments", 2, result.size());
        assertTrue("Should contain Unmanaged department", containsDepartmentByName(result, "Unmanaged"));
        assertTrue("Should contain New department", containsDepartmentByName(result, "New"));
        assertFalse("Should not contain Managed department", containsDepartmentByName(result, "Managed"));
    }
    
    @Test
    public void testCase2_fullyManagedCompany() {
        // SetUp: Create Company with 3 departments
        dept1 = new Department("Department1");
        dept2 = new Department("Department2");
        dept3 = new Department("Department3");
        
        // Add departments to company
        company.getDepartments().add(dept1);
        company.getDepartments().add(dept2);
        company.getDepartments().add(dept3);
        
        // Add all managers to company employees
        company.getEmployees().add(manager1);
        company.getEmployees().add(manager2);
        company.getEmployees().add(manager3);
        
        // Assign managers to all departments
        dept1.setManager(manager1);
        dept2.setManager(manager2);
        dept3.setManager(manager3);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_emptyCompany() {
        // SetUp: Create Company with no departments
        // company already has empty departments list from constructor
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Should return null when company has no departments", result);
    }
    
    @Test
    public void testCase4_singleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = new Department("Orphan");
        company.getDepartments().add(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Should return list with one department", result);
        assertEquals("Should contain exactly one department", 1, result.size());
        assertEquals("Should contain Orphan department", "Orphan", result.get(0).getName());
    }
    
    @Test
    public void testCase5_afterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        Department volatileDept = new Department("Volatile");
        company.getDepartments().add(volatileDept);
        
        // Add manager to company employees
        company.getEmployees().add(manager1);
        
        // Assign manager and then remove manager
        volatileDept.setManager(manager1);
        volatileDept.setManager(null); // Remove manager
        
        // Action: List departments without manager
        List<Department> result = company.getDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Should return list with one department", result);
        assertEquals("Should contain exactly one department", 1, result.size());
        assertEquals("Should contain Volatile department", "Volatile", result.get(0).getName());
    }
    
    // Helper method to check if a list contains a department with given name
    private boolean containsDepartmentByName(List<Department> departments, String name) {
        for (Department dept : departments) {
            if (dept.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}