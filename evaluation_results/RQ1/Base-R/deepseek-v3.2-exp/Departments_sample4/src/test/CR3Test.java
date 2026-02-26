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
    }

    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        legalDept = new Department();
        legalDept.setName("Legal");
        company.getDepartments().add(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        l1 = new Office();
        l1.setLocation("L1");
        l1.setDepartment(legalDept);
        
        l2 = new Office();
        l2.setLocation("L2");
        l2.setDepartment(legalDept);
        
        legalDept.getOffices().add(l1);
        legalDept.getOffices().add(l2);
        
        // Set "L1" as headquarter
        legalDept.setHeadquarters(l1);
        
        // Assign "E101" as manager to Legal
        e101 = new Employee();
        e101.setName("E101");
        e101.setDepartment(legalDept);
        company.getEmployees().add(e101);
        legalDept.setManager(e101);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse(company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse(company.getEmployees().contains(e101));
        
        // Note: The specification mentions offices should be removed, but the Company class
        // doesn't maintain a list of offices, so we can't verify this directly
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Create department "Phantom" but don't add it to company
        phantomDept = new Department();
        phantomDept.setName("Phantom");
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse(result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        emptyDept = new Department();
        emptyDept.setName("EmptyDept");
        company.getDepartments().add(emptyDept);
        
        // Add Office "O1" to EmptyDept
        o1 = new Office();
        o1.setLocation("O1");
        o1.setDepartment(emptyDept);
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
        soloDept = new Department();
        soloDept.setName("Solo");
        company.getDepartments().add(soloDept);
        
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
        remoteDept = new Department();
        remoteDept.setName("Remote");
        company.getDepartments().add(remoteDept);
        
        // Add Employee "E201" to Remote
        e201 = new Employee();
        e201.setName("E201");
        e201.setDepartment(remoteDept);
        company.getEmployees().add(e201);
        
        // Assign "E201" as manager
        remoteDept.setManager(e201);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse(company.getEmployees().contains(e201));
    }
}