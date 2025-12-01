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
    private Office officeL1;
    private Office officeL2;
    private Office officeO1;
    private Employee employeeE101;
    private Employee employeeE201;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Initialize departments
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
        
        // Initialize offices
        officeL1 = new Office();
        officeL1.setId("L1");
        
        officeL2 = new Office();
        officeL2.setId("L2");
        
        officeO1 = new Office();
        officeO1.setId("O1");
        
        // Initialize employees
        employeeE101 = new Employee();
        employeeE101.setId("E101");
        employeeE101.setName("John Doe");
        
        employeeE201 = new Employee();
        employeeE201.setId("E201");
        employeeE201.setName("Jane Smith");
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDepartment);
        
        // Add Offices "L1", "L2" to Legal
        legalDepartment.addOffice(officeL1);
        legalDepartment.addOffice(officeL2);
        
        // Set "L1" as headquarter
        legalDepartment.assignOfficeAsHeadquarter(officeL1);
        
        // Assign "E101" as manager to Legal
        company.addEmployee(employeeE101);
        company.assignManager(employeeE101, legalDepartment);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Deleting existing department should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   company.getEmployees().contains(employeeE101));
        
        // Verification: Offices "L1", "L2" removed (implicit through department deletion)
        // Note: Offices are owned by department, so they become eligible for GC when department is removed
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Create department "Phantom" (but not added to company)
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse("Deleting non-existent department should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // Add Office "O1" to EmptyDept
        emptyDept.addOffice(officeO1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Deleting department with only offices should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept department should be removed from company", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDepartment);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue("Deleting last remaining department should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDepartment);
        
        // Add Employee "E201" to Remote
        company.addEmployee(employeeE201);
        
        // Assign "E201" as manager
        company.assignManager(employeeE201, remoteDepartment);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Deleting department with manager but no offices should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(employeeE201));
    }
}