import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Company company;
    private Department managedDept;
    private Department unmanagedDept;
    private Department newDept;
    private Department volatileDept;
    private Employee m1;
    private Employee m2;
    private Employee m3;
    
    @Before
    public void setUp() {
        company = new Company();
        managedDept = new Department();
        unmanagedDept = new Department();
        newDept = new Department();
        volatileDept = new Department();
        m1 = new Employee();
        m2 = new Employee();
        m3 = new Employee();
    }
    
    @Test
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments: "Managed" (has manager M1), "Unmanaged" (no manager), "New" (no manager)
        company.setDepartments(new ArrayList<Department>() {{
            add(managedDept);
            add(unmanagedDept);
            add(newDept);
        }});
        company.setEmployees(new ArrayList<Employee>() {{
            add(m1);
            add(m2);
            add(m3);
        }});
        
        // Assign manager to "Managed" department
        managedDept.setManager(m1);
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Unmanaged", "New"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 departments", 2, result.size());
        assertTrue("Should contain unmanagedDept", result.contains(unmanagedDept));
        assertTrue("Should contain newDept", result.contains(newDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments and assign manager M1, M2, M3 to each department
        company.setDepartments(new ArrayList<Department>() {{
            add(managedDept);
            add(unmanagedDept);
            add(newDept);
        }});
        company.setEmployees(new ArrayList<Employee>() {{
            add(m1);
            add(m2);
            add(m3);
        }});
        
        // Assign managers to all departments
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
        company.setDepartments(new ArrayList<Department>());
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when there are no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        company.setDepartments(new ArrayList<Department>() {{
            add(volatileDept);  // Using volatileDept as "Orphan" department
        }});
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain the orphan department", result.contains(volatileDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        company.setDepartments(new ArrayList<Department>() {{
            add(volatileDept);
        }});
        company.setEmployees(new ArrayList<Employee>() {{
            add(m1);
        }});
        
        // Assign manager M1 and then remove manager M1
        volatileDept.setManager(m1);
        volatileDept.setManager(null);  // Remove manager by setting to null
        
        // Action: List departments without manager
        List<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 department", 1, result.size());
        assertTrue("Should contain the volatile department", result.contains(volatileDept));
    }
}

// Supporting classes (required for compilation)
class Employee {
    // Minimal implementation for testing
}

class Department {
    private List<Office> offices;
    private Employee manager;
    private List<Employee> employees;
    
    public Department() {
        this.offices = new ArrayList<>();
        this.employees = new ArrayList<>();
    }
    
    public List<Office> getOffices() {
        return offices;
    }
    
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }
    
    public Employee getManager() {
        return manager;
    }
    
    public void setManager(Employee manager) {
        this.manager = manager;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public boolean assignManager(Employee employee) {
        if (this.manager != null) {
            return false;
        }
        this.manager = employee;
        return true;
    }
    
    public boolean hasManager() {
        return this.manager != null;
    }
    
    public void removeOffice(Office office) {
        this.offices.remove(office);
    }
    
    public boolean assignHeadquarter(Office office) {
        if (!this.offices.contains(office) || office.isHeadquarter()) {
            return false;
        }
        for (Office o : this.offices) {
            o.setHeadquarter(false);
        }
        office.setHeadquarter(true);
        return true;
    }
}

class Office {
    private boolean isHeadquarter;
    
    public Office() {
        this.isHeadquarter = false;
    }
    
    public boolean isHeadquarter() {
        return isHeadquarter;
    }
    
    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }
}