import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department legalDepartment;
    private Department emptyDept;
    private Department soloDepartment;
    private Department remoteDepartment;
    private Department phantomDepartment;
    private Office officeL1;
    private Office officeL2;
    private Office officeO1;
    private Employee employeeE101;
    private Employee employeeE201;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        legalDepartment = new Department();
        emptyDept = new Department();
        soloDepartment = new Department();
        remoteDepartment = new Department();
        phantomDepartment = new Department();
        
        // Create offices
        officeL1 = new Office();
        officeL2 = new Office();
        officeO1 = new Office();
        
        // Create employees
        employeeE101 = new Employee();
        employeeE201 = new Employee();
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDepartment);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        List<Office> legalOffices = new ArrayList<>();
        legalOffices.add(officeL1);
        legalOffices.add(officeL2);
        legalDepartment.setOffices(legalOffices);
        
        // SetUp: Set "L1" as headquarter
        legalDepartment.assignOfficeAsHeadquarter(officeL1);
        
        // SetUp: Assign "E101" as manager to Legal
        company.addEmployee(employeeE101);
        company.assignManager(employeeE101, legalDepartment);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   company.getEmployees().contains(employeeE101));
        
        // Verification: Offices "L1", "L2" removed (implicitly verified by department removal)
        assertNull("Manager should be removed from department", legalDepartment.getManager());
        assertNull("Headquarter should be removed from department", legalDepartment.getHeadquarter());
        assertTrue("Department offices should be cleared", legalDepartment.getOffices().isEmpty());
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // SetUp: Create department "Phantom"
        // phantomDepartment created in setUp()
        
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
        List<Office> emptyDeptOffices = new ArrayList<>();
        emptyDeptOffices.add(officeO1);
        emptyDept.setOffices(emptyDeptOffices);
        
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
        assertTrue("Last remaining department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDepartment);
        
        // SetUp: Add Employee "E201" to Remote
        company.addEmployee(employeeE201);
        
        // SetUp: Assign "E201" as manager
        company.assignManager(employeeE201, remoteDepartment);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Department with manager but no offices deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(employeeE201));
    }
}