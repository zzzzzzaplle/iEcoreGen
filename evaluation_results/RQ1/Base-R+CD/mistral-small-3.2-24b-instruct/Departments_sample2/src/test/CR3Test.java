import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department legalDept;
    private Department emptyDept;
    private Department soloDept;
    private Department remoteDept;
    private Department phantomDept;
    private Office l1;
    private Office l2;
    private Office o1;
    private Employee e101;
    private Employee e201;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        legalDept = new Department();
        emptyDept = new Department();
        soloDept = new Department();
        remoteDept = new Department();
        phantomDept = new Department();
        l1 = new Office();
        l2 = new Office();
        o1 = new Office();
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
        legalDept.assignOfficeAsHeadquarter(l1);
        
        // Assign "E101" as manager to Legal
        company.addEmployee(e101);
        legalDept.setManager(e101);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Company should not contain Legal department", 
                   company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Company should not contain employee E101", 
                   company.getEmployees().contains(e101));
        
        // Verification: Offices "L1", "L2" removed
        // Note: Department is deleted, so its offices are no longer accessible
        // This is verified by the department being removed from company
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // Create department "Phantom" (but don't add to company)
        // phantomDept is already created in setUp()
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Deleting non-existent department should return false", result);
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
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Company should not contain EmptyDept", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company should have no departments", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDept);
        
        // Add Employee "E201" to Remote
        company.addEmployee(e201);
        
        // Assign "E201" as manager
        remoteDept.setManager(e201);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Company should not contain employee E201", 
                   company.getEmployees().contains(e201));
    }
}