import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department legalDepartment;
    private Department emptyDept;
    private Department remoteDepartment;
    private Department soloDepartment;
    private Department phantomDepartment;
    private Office l1Office;
    private Office l2Office;
    private Office o1Office;
    private Employee e101Employee;
    private Employee e201Employee;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        legalDepartment = new Department();
        emptyDept = new Department();
        remoteDepartment = new Department();
        soloDepartment = new Department();
        phantomDepartment = new Department();
        
        // Create offices
        l1Office = new Office();
        l2Office = new Office();
        o1Office = new Office();
        
        // Create employees
        e101Employee = new Employee();
        e201Employee = new Employee();
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDepartment);
        
        // Add Offices "L1", "L2" to Legal
        legalDepartment.addOffice(l1Office);
        legalDepartment.addOffice(l2Office);
        
        // Set "L1" as headquarter
        legalDepartment.assignOfficeAsHeadquarter(l1Office);
        
        // Assign "E101" as manager to Legal
        company.addEmployee(e101Employee);
        company.assignManager(e101Employee, legalDepartment);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Company should not contain legal department", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Company should not contain E101 employee", 
                   company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed (implicitly verified by department removal)
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // SetUp: Create department "Phantom"
        // phantomDepartment already created in setUp()
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse("Deleting non-existent department should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // Add Office "O1" to EmptyDept
        emptyDept.addOffice(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Company should not contain empty department", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDepartment);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDepartment);
        
        // Add Employee "E201" to Remote
        company.addEmployee(e201Employee);
        
        // Assign "E201" as manager
        company.assignManager(e201Employee, remoteDepartment);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Company should not contain E201 employee", 
                   company.getEmployees().contains(e201Employee));
    }
}