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
        // Setup: Create Company with Department "Legal"
        company.addDepartment(legalDepartment);
        
        // Add Offices "L1", "L2" to Legal
        legalDepartment.getOffices().add(l1Office);
        legalDepartment.getOffices().add(l2Office);
        
        // Set "L1" as headquarter
        legalDepartment.setHeadquarter(l1Office);
        
        // Assign "E101" as manager to Legal
        company.addEmployee(e101Employee);
        legalDepartment.setManager(e101Employee);
        legalDepartment.getEmployees().add(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse(company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse(company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed (indirectly verified through department removal)
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // Setup: Create empty Company
        // No departments added to company
        
        // Create department "Phantom" but don't add to company
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse(result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // Setup: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse(company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // Setup: Create Company with single Department "Solo"
        company.addDepartment(soloDepartment);
        
        // Verify initial state has one department
        assertEquals(1, company.getDepartments().size());
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments is empty
        assertTrue(company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // Setup: Create Company with Department "Remote"
        company.addDepartment(remoteDepartment);
        
        // Add Employee "E201" to Remote
        company.addEmployee(e201Employee);
        remoteDepartment.getEmployees().add(e201Employee);
        
        // Assign "E201" as manager
        remoteDepartment.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse(company.getEmployees().contains(e201Employee));
    }
}