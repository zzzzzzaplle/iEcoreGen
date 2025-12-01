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
        
        manager1.setId("M1");
        manager1.setName("Manager 1");
        
        // Add departments to company
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        
        // Add manager to company
        company.addEmployee(manager1);
        
        // Assign manager to "Managed" department
        company.assignManager(manager1, managedDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 2 departments without manager", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
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
        
        Employee m1 = new Employee();
        Employee m2 = new Employee();
        Employee m3 = new Employee();
        
        m1.setId("M1");
        m2.setId("M2");
        m3.setId("M3");
        
        // Add departments to company
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
        
        // Add managers to company
        company.addEmployee(m1);
        company.addEmployee(m2);
        company.addEmployee(m3);
        
        // Assign managers to each department
        company.assignManager(m1, dept1);
        company.assignManager(m2, dept2);
        company.assignManager(m3, dept3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // (company is already empty from setUp)
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null for empty company", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = new Department();
        orphanDept.setName("Orphan");
        
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 1 department without manager", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        Department volatileDept = new Department();
        volatileDept.setName("Volatile");
        
        Employee m1 = new Employee();
        m1.setId("M1");
        m1.setName("Manager 1");
        
        // Add department and manager to company
        company.addDepartment(volatileDept);
        company.addEmployee(m1);
        
        // Assign manager M1
        company.assignManager(m1, volatileDept);
        
        // Remove manager M1
        volatileDept.deleteEmployee(m1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 1 department without manager", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
    }
}