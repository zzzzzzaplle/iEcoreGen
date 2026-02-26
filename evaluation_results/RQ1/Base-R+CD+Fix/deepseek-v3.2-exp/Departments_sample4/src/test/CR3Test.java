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
        legalDept = new Department();
        phantomDept = new Department();
        emptyDept = new Department();
        soloDept = new Department();
        remoteDept = new Department();
        
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
        
        // SetUp: Add Offices "L1", "L2" to Legal
        legalDept.getOffices().add(l1);
        legalDept.getOffices().add(l2);
        
        // SetUp: Set "L1" as headquarter
        legalDept.setHeadquarter(l1);
        
        // SetUp: Assign "E101" as manager to Legal
        company.addEmployee(e101);
        legalDept.setManager(e101);
        e101.setDepartment(legalDept);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse(company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse(company.getEmployees().contains(e101));
        
        // Verification: Offices "L1", "L2" removed (from department)
        assertTrue(legalDept.getOffices().isEmpty());
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp
        
        // SetUp: Create department "Phantom"
        // phantomDept already created in setUp
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse(result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(o1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse(company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments is empty
        assertTrue(company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDept);
        
        // SetUp: Add Employee "E201" to Remote
        company.addEmployee(e201);
        
        // SetUp: Assign "E201" as manager
        remoteDept.setManager(e201);
        e201.setDepartment(remoteDept);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse(company.getEmployees().contains(e201));
    }
}