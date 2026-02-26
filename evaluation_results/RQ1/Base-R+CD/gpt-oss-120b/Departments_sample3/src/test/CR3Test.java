import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        Department legalDept = new Department();
        company.addDepartment(legalDept);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        Office l1 = new Office("L1");
        Office l2 = new Office("L2");
        legalDept.addOffice(l1);
        legalDept.addOffice(l2);
        
        // SetUp: Set "L1" as headquarter
        legalDept.assignOfficeAsHeadquarter(l1);
        
        // SetUp: Assign "E101" as manager to Legal
        Employee e101 = new Employee("E101");
        company.addEmployee(e101);
        company.assignManager(e101, legalDept);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse(company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse(company.getEmployees().contains(e101));
        
        // Verification: Offices "L1", "L2" removed (department offices cleared)
        assertTrue(legalDept.getOffices().isEmpty());
        assertNull(legalDept.getHeadquarter());
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // SetUp: Create department "Phantom"
        Department phantomDept = new Department();
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        Department emptyDept = new Department();
        company.addDepartment(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        Office o1 = new Office("O1");
        emptyDept.addOffice(o1);
        
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
        Department soloDept = new Department();
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
        Department remoteDept = new Department();
        company.addDepartment(remoteDept);
        
        // SetUp: Add Employee "E201" to Remote
        Employee e201 = new Employee("E201");
        company.addEmployee(e201);
        
        // SetUp: Assign "E201" as manager
        company.assignManager(e201, remoteDept);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse(company.getEmployees().contains(e201));
    }
}