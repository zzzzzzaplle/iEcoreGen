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
        
        // Create departments
        managedDept = new Department();
        managedDept.setIdentifier("Managed");
        
        unmanagedDept = new Department();
        unmanagedDept.setIdentifier("Unmanaged");
        
        newDept = new Department();
        newDept.setIdentifier("New");
        
        orphanDept = new Department();
        orphanDept.setIdentifier("Orphan");
        
        volatileDept = new Department();
        volatileDept.setIdentifier("Volatile");
        
        // Create managers
        manager1 = new Employee();
        manager1.setEmployeeId("M1");
        manager1.setName("Manager 1");
        
        manager2 = new Employee();
        manager2.setEmployeeId("M2");
        manager2.setName("Manager 2");
        
        manager3 = new Employee();
        manager3.setEmployeeId("M3");
        manager3.setName("Manager 3");
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.addDepartment(managedDept);
        company.addDepartment(unmanagedDept);
        company.addDepartment(newDept);
        company.addEmployee(manager1);
        company.assignManager(manager1, managedDept);
        
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
        Department dept1 = new Department();
        Department dept2 = new Department();
        Department dept3 = new Department();
        
        company.addDepartment(dept1);
        company.addDepartment(dept2);
        company.addDepartment(dept3);
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
        // SetUp: Create Company with no departments
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
        assertEquals("Should contain 1 department without manager", 1, result.size());
        assertTrue("Should contain Orphan department", result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile", assign manager M1 and then remove manager M1
        company.addDepartment(volatileDept);
        company.addEmployee(manager1);
        company.assignManager(manager1, volatileDept);
        
        // Remove manager
        volatileDept.deleteEmployee(manager1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department without manager", 1, result.size());
        assertTrue("Should contain Volatile department", result.contains(volatileDept));
    }
}

// Helper class to add identifier field for Department for testing purposes
class Department {
    private List<Office> offices = new ArrayList<>();
    private Office headquarter;
    private Employee manager;
    private String identifier;
    
    public String getIdentifier() {
        return identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
    
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null) {
            return false;
        }
        if (!offices.contains(office)) {
            return false;
        }
        if (headquarter != null) {
            return false;
        }
        headquarter = office;
        return true;
    }
    
    public boolean hasManager() {
        return manager != null;
    }
    
    public boolean deleteEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        if (Objects.equals(manager, employee)) {
            manager = null;
            return true;
        }
        return false;
    }
    
    public boolean deleteOffice(Office office) {
        if (office == null) {
            return false;
        }
        boolean removed = offices.remove(office);
        if (removed && Objects.equals(headquarter, office)) {
            headquarter = null;
        }
        return removed;
    }
    
    public boolean addOffice(Office office) {
        if (office == null || offices.contains(office)) {
            return false;
        }
        return offices.add(office);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(identifier, that.identifier);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}

// Other classes remain the same as provided
class Office {
    private String identifier;
    
    public String getIdentifier() {
        return identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office office = (Office) o;
        return Objects.equals(identifier, office.identifier);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}

class Employee {
    private String employeeId;
    private String name;
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}