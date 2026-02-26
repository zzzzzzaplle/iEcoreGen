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
    private Office l1Office;
    private Office l2Office;
    private Office o1Office;
    private Employee e101Employee;
    private Employee e201Employee;

    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        legalDept = new Department();
        phantomDept = new Department();
        emptyDept = new Department();
        soloDept = new Department();
        remoteDept = new Department();
        l1Office = new Office();
        l2Office = new Office();
        o1Office = new Office();
        e101Employee = new Employee();
        e201Employee = new Employee();
    }

    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        legalDept.getOffices().add(l1Office);
        legalDept.getOffices().add(l2Office);
        
        // Set "L1" as headquarter
        legalDept.setHeadquarter(l1Office);
        
        // Assign "E101" as manager to Legal
        company.addEmployee(e101Employee);
        legalDept.setManager(e101Employee);
        legalDept.getEmployees().add(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Department should be removed from company", company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee should be removed from company", company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed (verified through department state)
        assertTrue("Department offices should be cleared", legalDept.getOffices().isEmpty());
        assertNull("Department headquarter should be cleared", legalDept.getHeadquarter());
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // Create department "Phantom" (but don't add to company)
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Delete should return false for non-existent department", result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for department with only offices", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Department should be removed from company", company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                   company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDept);
        
        // Add Employee "E201" to Remote
        company.addEmployee(e201Employee);
        remoteDept.getEmployees().add(e201Employee);
        
        // Assign "E201" as manager
        remoteDept.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee should be removed from company", company.getEmployees().contains(e201Employee));
    }
}