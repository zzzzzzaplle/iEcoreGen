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
        remoteDepartment = new Department();
        soloDepartment = new Department();
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
        legalDepartment.getOffices().add(officeL1);
        legalDepartment.getOffices().add(officeL2);
        
        // SetUp: Set "L1" as headquarter
        legalDepartment.setHeadquarter(officeL1);
        
        // SetUp: Assign "E101" as manager to Legal
        company.addEmployee(employeeE101);
        employeeE101.setDepartment(legalDepartment);
        legalDepartment.setManager(employeeE101);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Company should not contain legal department", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Company should not contain employee E101", 
                   company.getEmployees().contains(employeeE101));
        
        // Verification: Offices "L1", "L2" removed (implicitly verified by department deletion)
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp
        
        // SetUp: Create department "Phantom"
        // phantomDepartment already created in setUp
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse("Deleting non-existent department should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(officeO1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Company should not contain empty department", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDepartment);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDepartment);
        
        // SetUp: Add Employee "E201" to Remote
        company.addEmployee(employeeE201);
        employeeE201.setDepartment(remoteDepartment);
        
        // SetUp: Assign "E201" as manager
        remoteDepartment.setManager(employeeE201);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Company should not contain employee E201", 
                   company.getEmployees().contains(employeeE201));
    }
}