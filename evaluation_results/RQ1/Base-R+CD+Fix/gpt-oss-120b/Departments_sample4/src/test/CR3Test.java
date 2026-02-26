import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Department legalDept;
    private Department emptyDept;
    private Department soloDept;
    private Department remoteDept;
    private Department phantomDept;
    private Office officeL1;
    private Office officeL2;
    private Office officeO1;
    private Employee employeeE101;
    private Employee employeeE201;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        legalDept = new Department();
        emptyDept = new Department();
        soloDept = new Department();
        remoteDept = new Department();
        phantomDept = new Department();
        
        // Create offices
        officeL1 = new Office();
        officeL1.setIdentifier("L1");
        officeL2 = new Office();
        officeL2.setIdentifier("L2");
        officeO1 = new Office();
        officeO1.setIdentifier("O1");
        
        // Create employees
        employeeE101 = new Employee();
        employeeE101.setEmployeeId("E101");
        employeeE101.setName("John Doe");
        employeeE201 = new Employee();
        employeeE201.setEmployeeId("E201");
        employeeE201.setName("Jane Smith");
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDept);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        legalDept.addOffice(officeL1);
        legalDept.addOffice(officeL2);
        
        // SetUp: Set "L1" as headquarter
        legalDept.setHeadquarter(officeL1);
        
        // SetUp: Assign "E101" as manager to Legal
        company.addEmployee(employeeE101);
        company.assignManager(employeeE101, legalDept);
        
        // Verification before deletion
        assertTrue("Legal department should exist before deletion", 
                   company.getDepartments().contains(legalDept));
        assertTrue("Employee E101 should exist before deletion", 
                   company.getEmployees().contains(employeeE101));
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed", 
                    company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed", 
                    company.getEmployees().contains(employeeE101));
        
        // Offices are removed implicitly when department is deleted (no direct verification possible)
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // SetUp: Create department "Phantom" (but not added to company)
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Delete should return false for non-existent department", result);
        
        // Verification: Company remains empty
        assertTrue("Company should remain empty", company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        emptyDept.addOffice(officeO1);
        
        // Verification before deletion
        assertTrue("EmptyDept should exist before deletion", 
                   company.getDepartments().contains(emptyDept));
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for existing department", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed", 
                    company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDept);
        
        // Verification before deletion
        assertEquals("Company should have exactly 1 department", 
                     1, company.getDepartments().size());
        assertTrue("Solo department should exist", 
                   company.getDepartments().contains(soloDept));
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for existing department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company should be empty after deletion", 
                   company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDept);
        
        // SetUp: Add Employee "E201" to Remote
        company.addEmployee(employeeE201);
        
        // SetUp: Assign "E201" as manager
        company.assignManager(employeeE201, remoteDept);
        
        // Verification before deletion
        assertTrue("Remote department should exist before deletion", 
                   company.getDepartments().contains(remoteDept));
        assertTrue("Employee E201 should exist before deletion", 
                   company.getEmployees().contains(employeeE201));
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for existing department", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed", 
                    company.getEmployees().contains(employeeE201));
        
        // Verification: Department is removed
        assertFalse("Remote department should be removed", 
                    company.getDepartments().contains(remoteDept));
    }
}