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
    private Employee m1;
    private Employee m2;
    private Employee m3;
    
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
        m1 = new Employee("M1");
        m2 = new Employee("M2");
        m3 = new Employee("M3");
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        company.addEmployee(m1);
        
        managedDept.setManager(m1); // Assign manager to "Managed" department
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 departments without manager", 2, result.size());
        assertTrue("Should contain Unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain New department", result.contains(newDept));
        assertFalse("Should not contain Managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        company.addEmployee(m1);
        company.addEmployee(m2);
        company.addEmployee(m3);
        
        managedDept.setManager(m1);
        unmanagedDept.setManager(m2);
        newDept.setManager(m3);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null for empty company", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        company.addDepartment(orphanDept);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department without manager", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        company.addDepartment(volatileDept);
        company.addEmployee(m1);
        
        volatileDept.setManager(m1); // Assign manager
        volatileDept.deleteEmployee(m1); // Remove manager
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department without manager", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
    }
}

// Helper classes to support the tests (since original classes don't have setName methods)
class Department {
    private String name;
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;
    
    public Department() {
        this.offices = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (offices.contains(office) && headquarter == null) {
            headquarter = office;
            return true;
        }
        return false;
    }
    
    public boolean hasManager() {
        return manager != null;
    }
    
    public boolean deleteEmployee(Employee employee) {
        if (manager == employee) {
            manager = null;
            return true;
        }
        return false;
    }
    
    public boolean deleteOffice(Office office) {
        if (headquarter == office) {
            headquarter = null;
        }
        return offices.remove(office);
    }
    
    public boolean addOffice(Office office) {
        if (!offices.contains(office)) {
            return offices.add(office);
        }
        return false;
    }
    
    public List<Office> getOffices() {
        return offices;
    }
    
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }
    
    public Office getHeadquarter() {
        return headquarter;
    }
    
    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }
    
    public Employee getManager() {
        return manager;
    }
    
    public void setManager(Employee manager) {
        this.manager = manager;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Department that = (Department) obj;
        return name != null ? name.equals(that.name) : that.name == null;
    }
    
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

class Employee {
    private String name;
    
    public Employee() {}
    
    public Employee(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee that = (Employee) obj;
        return name != null ? name.equals(that.name) : that.name == null;
    }
    
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

class Office {
    private String name;
    
    public Office() {}
    
    public Office(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}