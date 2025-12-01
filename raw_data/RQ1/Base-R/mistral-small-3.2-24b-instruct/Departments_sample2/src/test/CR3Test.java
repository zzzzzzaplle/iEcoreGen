import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department legalDepartment;
    private Department phantomDepartment;
    private Department emptyDept;
    private Department soloDepartment;
    private Department remoteDepartment;
    private Employee employeeE101;
    private Employee employeeE201;
    private Office officeL1;
    private Office officeL2;
    private Office officeO1;

    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        legalDepartment = new Department();
        legalDepartment.setName("Legal");
        
        phantomDepartment = new Department();
        phantomDepartment.setName("Phantom");
        
        emptyDept = new Department();
        emptyDept.setName("EmptyDept");
        
        soloDepartment = new Department();
        soloDepartment.setName("Solo");
        
        remoteDepartment = new Department();
        remoteDepartment.setName("Remote");
        
        // Create employees
        employeeE101 = new Employee();
        employeeE101.setName("John Doe");
        employeeE101.setEmployeeId("E101");
        
        employeeE201 = new Employee();
        employeeE201.setName("Jane Smith");
        employeeE201.setEmployeeId("E201");
        
        // Create offices
        officeL1 = new Office();
        officeL1.setLocation("L1");
        
        officeL2 = new Office();
        officeL2.setLocation("L2");
        
        officeO1 = new Office();
        officeO1.setLocation("O1");
    }

    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDepartment);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        List<Office> legalOffices = legalDepartment.getOffices();
        legalOffices.add(officeL1);
        legalOffices.add(officeL2);
        
        // SetUp: Set "L1" as headquarter
        officeL1.setIsHeadquarters(true);
        
        // SetUp: Assign "E101" as manager to Legal
        legalDepartment.setManager(employeeE101);
        company.addEmployee(employeeE101);
        
        // Action: Delete "Legal" department
        boolean result = company.removeDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Should return true when deleting existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   company.getEmployees().contains(employeeE101));
        
        // Verification: Offices "L1", "L2" removed (implicitly verified by department removal)
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setup
        
        // SetUp: Create department "Phantom"
        // phantomDepartment is created but not added to company
        
        // Action: Delete "Phantom" department
        boolean result = company.removeDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse("Should return false when deleting non-existent department", result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        List<Office> emptyDeptOffices = emptyDept.getOffices();
        emptyDeptOffices.add(officeO1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.removeDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting department with only offices", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed from company", 
                   company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDepartment);
        
        // Action: Delete "Solo"
        boolean result = company.removeDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue("Should return true when deleting last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deletion", 
                  company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDepartment);
        
        // SetUp: Add Employee "E201" to Remote
        remoteDepartment.setManager(employeeE201);
        company.addEmployee(employeeE201);
        
        // Action: Delete "Remote"
        boolean result = company.removeDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Should return true when deleting department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(employeeE201));
    }
}