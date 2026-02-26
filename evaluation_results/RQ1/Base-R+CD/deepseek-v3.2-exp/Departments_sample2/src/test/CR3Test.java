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
        legalDepartment = new Department();
        emptyDept = new Department();
        remoteDepartment = new Department();
        soloDepartment = new Department();
        phantomDepartment = new Department();
        l1Office = new Office();
        l2Office = new Office();
        o1Office = new Office();
        e101Employee = new Employee();
        e201Employee = new Employee();
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDepartment);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        legalDepartment.getOffices().add(l1Office);
        legalDepartment.getOffices().add(l2Office);
        
        // SetUp: Set "L1" as headquarter
        legalDepartment.setHeadquarter(l1Office);
        
        // SetUp: Assign "E101" as manager to Legal
        company.addEmployee(e101Employee);
        legalDepartment.setManager(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed
        assertTrue("Legal department offices should be cleared", 
                  legalDepartment.getOffices().isEmpty());
        assertNull("Headquarter should be cleared", legalDepartment.getHeadquarter());
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp
        
        // SetUp: Create department "Phantom"
        // phantomDepartment already created in setUp
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse("Non-existent department deletion should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department with only offices deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed from company", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDepartment);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue("Last department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDepartment);
        
        // SetUp: Add Employee "E201" to Remote
        company.addEmployee(e201Employee);
        
        // SetUp: Assign "E201" as manager
        remoteDepartment.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Department with manager but no offices deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(e201Employee));
    }
}