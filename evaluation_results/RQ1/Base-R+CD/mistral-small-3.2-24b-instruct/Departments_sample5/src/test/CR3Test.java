import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department legalDept;
    private Department phantomDept;
    private Department emptyDept;
    private Department soloDept;
    private Department remoteDept;
    private Office l1;
    private Office l2;
    private Office o1;
    private Employee e101;
    private Employee e201;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        legalDept = new Department();
        phantomDept = new Department();
        emptyDept = new Department();
        soloDept = new Department();
        remoteDept = new Department();
        
        // Create offices
        l1 = new Office();
        l2 = new Office();
        o1 = new Office();
        
        // Create employees
        e101 = new Employee();
        e201 = new Employee();
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        legalDept.getOffices().add(l1);
        legalDept.getOffices().add(l2);
        
        // Set "L1" as headquarter
        legalDept.setHeadquarter(l1);
        
        // Assign "E101" as manager to Legal
        company.addEmployee(e101);
        legalDept.setManager(e101);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   company.getEmployees().contains(e101));
        
        // Verification: Offices "L1", "L2" removed (implicit in department deletion)
        // Note: The Office class doesn't maintain a reference to company, so we verify through department
        assertTrue("Legal department offices should be cleared", 
                  legalDept.getOffices().isEmpty());
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp
        
        // Action: Delete "Phantom" department (not added to company)
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Should return false when deleting non-existent department", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(o1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting department with only offices", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed from company", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDept);
        
        // Add Employee "E201" to Remote
        company.addEmployee(e201);
        remoteDept.setManager(e201);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(e201));
    }
}

// Supporting classes (normally these would be in separate files)
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;
    
    public Department() {
        this.offices = new java.util.ArrayList<>();
    }
    
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !offices.contains(office) || this.headquarter != null) {
            return false;
        }
        this.headquarter = office;
        return true;
    }
    
    public boolean hasManager() {
        return manager != null;
    }
    
    public boolean assignManager(Employee employee) {
        if (this.manager != null) {
            return false;
        }
        this.manager = employee;
        return true;
    }
    
    public boolean deleteOffice(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        if (this.headquarter != null && this.headquarter.equals(office)) {
            this.headquarter = null;
        }
        return offices.remove(office);
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
    
    public List<Employee> getEmployees() {
        return new java.util.ArrayList<>();
    }
}

class Employee {
    // Simple employee class for testing
}

class Office {
    // Simple office class for testing
}