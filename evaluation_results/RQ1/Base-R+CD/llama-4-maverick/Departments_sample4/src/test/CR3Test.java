import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        company = new Company();
        legalDept = new Department();
        phantomDept = new Department();
        emptyDept = new Department();
        soloDept = new Department();
        remoteDept = new Department();
        l1Office = new Office("L1");
        l2Office = new Office("L2");
        o1Office = new Office("O1");
        e101Employee = new Employee("E101");
        e201Employee = new Employee("E201");
    }

    @Test
    public void testCase1_deleteExistingDepartmentWithOfficesAndEmployees() {
        // Set up: Create Company with Department "Legal"
        company.addDepartment(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        legalDept.addOffice(l1Office);
        legalDept.addOffice(l2Office);
        
        // Set "L1" as headquarter
        legalDept.assignOfficeAsHeadquarter(l1Office);
        
        // Assign "E101" as manager to Legal
        company.addEmployee(e101Employee);
        legalDept.setManager(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse(company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse(company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed (headquarter should be null and offices list empty)
        assertNull(legalDept.getHeadquarter());
        assertTrue(legalDept.getOffices().isEmpty());
    }

    @Test
    public void testCase2_deleteNonExistentDepartment() {
        // Set up: Create empty Company
        // Set up: Create department "Phantom"
        // Note: phantomDept is NOT added to company
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse(result);
    }

    @Test
    public void testCase3_deleteDepartmentWithOnlyOffices() {
        // Set up: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // Add Office "O1" to EmptyDept
        emptyDept.addOffice(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse(company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_deleteLastRemainingDepartment() {
        // Set up: Create Company with single Department "Solo"
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments is empty
        assertTrue(company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_deleteDepartmentWithManagerButNoOffices() {
        // Set up: Create Company with Department "Remote"
        company.addDepartment(remoteDept);
        
        // Add Employee "E201" to Remote
        company.addEmployee(e201Employee);
        
        // Assign "E201" as manager
        remoteDept.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse(company.getEmployees().contains(e201Employee));
    }
}