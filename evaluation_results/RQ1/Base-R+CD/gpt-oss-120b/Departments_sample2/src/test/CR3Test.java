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
        
        // Add Offices "L1", "L2" to Legal
        Office l1 = new Office("L1");
        Office l2 = new Office("L2");
        legalDept.addOffice(l1);
        legalDept.addOffice(l2);
        
        // Set "L1" as headquarter
        legalDept.assignOfficeAsHeadquarter(l1);
        
        // Assign "E101" as manager to Legal
        Employee e101 = new Employee("E101");
        company.addEmployee(e101);
        company.assignManager(e101, legalDept);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Department should be removed from company", company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee should be removed from company", company.getEmployees().contains(e101));
        
        // Verification: Offices "L1", "L2" removed
        assertTrue("Department offices should be cleared", legalDept.getOffices().isEmpty());
        assertNull("Department headquarter should be cleared", legalDept.getHeadquarter());
        assertNull("Department manager should be cleared", legalDept.getManager());
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Create department "Phantom"
        Department phantomDept = new Department();
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Non-existent department deletion should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        Department emptyDept = new Department();
        company.addDepartment(emptyDept);
        
        // Add Office "O1" to EmptyDept
        Office o1 = new Office("O1");
        emptyDept.addOffice(o1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department with only offices deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Department should be removed from company", company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        Department soloDept = new Department();
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Last department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty", company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        Department remoteDept = new Department();
        company.addDepartment(remoteDept);
        
        // Add Employee "E201" to Remote
        Employee e201 = new Employee("E201");
        company.addEmployee(e201);
        
        // Assign "E201" as manager
        company.assignManager(e201, remoteDept);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Department with manager but no offices deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee should be removed from company", company.getEmployees().contains(e201));
    }
}