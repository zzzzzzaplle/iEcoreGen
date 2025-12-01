import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Company company;
    private Department department1;
    private Department department2;
    private Department department3;
    private Employee manager1;
    private Employee manager2;
    private Employee manager3;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments
        department1 = new Department();
        department1.setName("Managed");
        department2 = new Department();
        department2.setName("Unmanaged");
        department3 = new Department();
        department3.setName("New");
        
        manager1 = new Employee();
        manager1.setName("M1");
        department1.setManager(manager1);
        
        List<Department> departments = new ArrayList<>();
        departments.add(department1);
        departments.add(department2);
        departments.add(department3);
        company.setDepartments(departments);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(department2));
        assertTrue(result.contains(department3));
        assertFalse(result.contains(department1));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments
        department1 = new Department();
        department1.setName("Department1");
        department2 = new Department();
        department2.setName("Department2");
        department3 = new Department();
        department3.setName("Department3");
        
        // Assign manager M1, M2, M3 to each department
        manager1 = new Employee();
        manager1.setName("M1");
        manager2 = new Employee();
        manager2.setName("M2");
        manager3 = new Employee();
        manager3.setName("M3");
        
        department1.setManager(manager1);
        department2.setManager(manager2);
        department3.setManager(manager3);
        
        List<Department> departments = new ArrayList<>();
        departments.add(department1);
        departments.add(department2);
        departments.add(department3);
        company.setDepartments(departments);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDepartment = new Department();
        orphanDepartment.setName("Orphan");
        
        List<Department> departments = new ArrayList<>();
        departments.add(orphanDepartment);
        company.setDepartments(departments);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orphanDepartment, result.get(0));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        Department volatileDepartment = new Department();
        volatileDepartment.setName("Volatile");
        
        // Assign manager M1
        Employee tempManager = new Employee();
        tempManager.setName("M1");
        volatileDepartment.setManager(tempManager);
        
        List<Department> departments = new ArrayList<>();
        departments.add(volatileDepartment);
        company.setDepartments(departments);
        
        // Remove manager M1
        volatileDepartment.setManager(null);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(volatileDepartment, result.get(0));
    }
}