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
        
        // SetUp: Add Offices "L1", "L2" to Legal
        l1Office.setAddress("L1");
        l2Office.setAddress("L2");
        legalDept.addOffice(l1Office);
        legalDept.addOffice(l2Office);
        
        // SetUp: Set "L1" as headquarter
        legalDept.assignHeadquarters(l1Office);
        
        // SetUp: Assign "E101" as manager to Legal
        e101Employee.setName("E101");
        legalDept.assignManager(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.removeDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDept));
        
        // Note: The provided Company class doesn't maintain employees at company level,
        // so we can only verify department removal. Employee removal would need to be
        // handled by the caller as per the specification.
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // SetUp: Create department "Phantom"
        // phantomDept is already created in setUp()
        
        // Action: Delete "Phantom" department
        boolean result = company.removeDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Non-existent department deletion should return false", result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        o1Office.setAddress("O1");
        emptyDept.addOffice(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.removeDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department with only offices deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed from company", 
                   company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.removeDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Last remaining department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDept);
        
        // SetUp: Add Employee "E201" to Remote
        // Note: The Department class doesn't have addEmployee method, so we just assign as manager
        e201Employee.setName("E201");
        
        // SetUp: Assign "E201" as manager
        remoteDept.assignManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.removeDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Department with manager but no offices deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        // Note: The provided Company class doesn't maintain employees at company level,
        // so we can only verify department removal. Employee removal would need to be
        // handled by the caller as per the specification.
    }
}